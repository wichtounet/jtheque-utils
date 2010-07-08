package org.jtheque.utils.io;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;

import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * An utility class for file manipulation.
 *
 * @author Baptiste Wicht
 */
public final class FileUtils {
    /**
     * The default size of buffer.
     */
    private static final int BUFFER_SIZE = 2048;

    /**
     * Construct a new FileUtils. This constructor is private because all methods are static.
     */
    private FileUtils() {
        super();
    }

    /**
     * Create the file if not exists.
     *
     * @param file The file to create.
     */
    public static void createIfNotExists(File file) {
        if (!file.exists()) {
            boolean created = file.mkdirs();

            if (!created) {
                LoggerFactory.getLogger(FileUtils.class).debug("The folder (" + file.getAbsolutePath() + ") can not be created. ");
            }
        }
    }

    /**
     * Return the text of the file.
     *
     * @param path The path to the file.
     *
     * @return The content of the file
     */
    public static String getTextOfSystemResource(String path) {
        InputStream stream = ClassLoader.getSystemResourceAsStream(path);

        return getContentOfStream(stream);
    }

    /**
     * Return the text of the file referenced by the specified path.
     *
     * @param path The path to the file.
     *
     * @return The text of the path.
     */
    public static String getTextOf(String path) {
        try {
            InputStream stream = asInputStream(path);

            return getContentOfStream(stream);
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(FileUtils.class).error("Unable to get the text of " + path, e);
        }

        return null;
    }

    /**
     * Return the content of the stream.
     *
     * @param stream The stream to read from.
     *
     * @return all the content of the stream in a <code>String</code> object.
     */
    private static String getContentOfStream(InputStream stream) {
        StringBuilder content = new StringBuilder(1000);

        Collection<String> lines = getLinesOf(stream);

        boolean first = true;

        for (String line : lines) {
            if (first) {
                first = false;
            } else {
                content.append('\n');
            }

            content.append(line);
        }

        return content.toString();
    }

    public static Collection<String> getLinesOf(File file) {
        try {
            return getLinesOf(asInputStream(file));
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(FileUtils.class).error("Exception occurred during reading", e);
        }

        return CollectionUtils.emptyList();
    }

    /**
     * Return the lines of a stream.
     *
     * @param stream The stream to count the lines from.
     *
     * @return The lines of the stream.
     */
    public static Collection<String> getLinesOf(InputStream stream) {
        Collection<String> lines = new ArrayList<String>(100);

        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedInputStream(stream));

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return lines;
    }

    /**
     * Copy a file.
     *
     * @param sourcePath The path of the source.
     * @param targetPath The path of the destination.
     *
     * @throws CopyException Thrown if an error occurs during the copy.
     */
    public static void copy(String sourcePath, String targetPath) throws CopyException {
        File source = new File(sourcePath);
        File target = new File(targetPath);

        copy(source, target);
    }

    /**
     * This method copy a source's file to a destination's file.
     *
     * @param source The source's file.
     * @param target The destination's file.
     *
     * @throws CopyException Thrown when a problem occurs during the copy.
     */
    public static void copy(File source, File target) throws CopyException {
        if (source.isDirectory()) {
            copyDirectory(source, target);
        } else if (source.exists()) {
            FileChannel in = null;
            FileChannel out = null;

            FileInputStream inStream = null;
            FileOutputStream outStream = null;

            try {
                inStream = new FileInputStream(source);
                outStream = new FileOutputStream(target);

                in = inStream.getChannel();
                out = outStream.getChannel();

                in.transferTo(0, in.size(), out);
            } catch (IOException e) {
                throw new CopyException("Unable to copy the file", e);
            } finally {
                close(inStream);
                close(in);
                close(outStream);
                close(out);
            }
        } else {
            throw new CopyException("File doesn't exist: " + source.getAbsolutePath());
        }
    }

    /**
     * Copy the input stream content into the given file.
     *
     * @param inputStream The input stream to read from.
     * @param file        The file to write from.
     *
     * @throws CopyException Thrown if an error occurs during the writing or reading.
     */
    public static void copy(InputStream inputStream, File file) throws CopyException {
        FilterInputStream input = null;
        OutputStream output = null;

        try {
            input = new BufferedInputStream(inputStream);
            output = asOutputStream(file);

            byte[] buffer = new byte[BUFFER_SIZE];
            int read = input.read(buffer);

            while (read > 0) {
                output.write(buffer, 0, read);

                read = input.read(buffer);
            }
        } catch (IOException e) {
            throw new CopyException("Unable to copy the file due to IOException", e);
        } finally {
            close(input);
            close(output);
        }
    }

    /**
     * Copy a directory.
     *
     * @param source The source.
     * @param target The target.
     *
     * @throws CopyException Thrown if an error occurs during the copying process.
     */
    private static void copyDirectory(File source, File target) throws CopyException {
        if (!target.exists() && !target.mkdirs()) {
            throw new CopyException("Unable to create the target directory " + target.getAbsolutePath());
        }

        File[] files = source.listFiles();

        for (File sourceFile : files) {
            if (sourceFile.isDirectory()) {
                copyDirectory(sourceFile, new File(source, sourceFile.getName()));
            }

            String path = sourceFile.getAbsolutePath();
            File targetFile = new File(target.getAbsolutePath() + path.substring(path.lastIndexOf(System.getProperty("file.separator"))));
            copy(sourceFile, targetFile);
        }
    }

    /**
     * Move a file.
     *
     * @param sourcePath The path of the source.
     * @param targetPath The path of the destination.
     *
     * @throws CopyException Thrown if an error occurs during the move.
     */
    public static void move(String sourcePath, String targetPath) throws CopyException {
        File source = new File(sourcePath);
        File target = new File(targetPath);

        copy(source, target);

        delete(source);
    }

    /**
     * Unzip a compressed file.
     *
     * @param source            The source buffer
     * @param destinationFolder The destination folder.
     *
     * @return All the files of the zip
     */
    public static Collection<File> unzip(InputStream source, String destinationFolder) {
        Collection<File> files = new ArrayList<File>(10);

        ZipInputStream zis = new ZipInputStream(source);

        try {
            ZipEntry entry = zis.getNextEntry();

            while (entry != null) {
                File file = readFile(destinationFolder, zis, entry);

                files.add(file);

                entry = zis.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(FileUtils.class).error("Exception occurred during unzipping " + e);
        } catch (IOException e) {
            LoggerFactory.getLogger(FileUtils.class).error("Exception occurred during unzipping " + e);
        } finally {
            close(zis);
        }

        return files;
    }

    /**
     * Read an entry from the zip and unzip them into the specified destination folder.
     *
     * @param destinationFolder The folder to store the unzipped entry to.
     * @param zis               The zip input stream.
     * @param entry             The current entry.
     *
     * @return The written file.
     *
     * @throws IOException If an error occurs during the file processing.
     */
    private static File readFile(String destinationFolder, ZipInputStream zis, ZipEntry entry) throws IOException {
        byte[] data = new byte[BUFFER_SIZE];

        File file = new File(destinationFolder + '/' + entry.getName());

        OutputStream outputStream = asOutputStream(file);

        int count = zis.read(data, 0, BUFFER_SIZE);

        while (count != -1) {
            outputStream.write(data, 0, count);

            count = zis.read(data, 0, BUFFER_SIZE);
        }

        close(outputStream);

        return file;
    }

    /**
     * Zip files to a destination.
     *
     * @param files       The files we have to zip.
     * @param destination The archive destination file.
     */
    public static void zip(Iterable<File> files, File destination) {
        try {
            zip(files, asOutputStream(destination));
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(FileUtils.class).error("Exception occurred during zipping " + e);
        }
    }

    /**
     * Zip files to a destination stream.
     *
     * @param files  The files we have to zip.
     * @param buffer The destination stream.
     */
    public static void zip(Iterable<File> files, OutputStream buffer) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(buffer);
            zos.setMethod(ZipOutputStream.DEFLATED);
            zos.setLevel(9);

            zipFiles(files, zos);
        } catch (Exception e) {
            LoggerFactory.getLogger(FileUtils.class).error("Exception occurred during zipping " + e);
        } finally {
            close(zos);
        }
    }

    /**
     * Zip a list of files into a zip output stream
     *
     * @param files The files to zip.
     * @param zos   The output stream to write in.
     *
     * @throws IOException If an IO problem occurs during the zipping.
     */
    private static void zipFiles(Iterable<File> files, ZipOutputStream zos) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];

        for (File f : files) {
            InputStream in = null;
            try {
                in = asInputStream(f);

                ZipEntry entry = new ZipEntry(f.getName());

                zos.putNextEntry(entry);

                int len = in.read(buf);
                while (len > 0) {
                    zos.write(buf, 0, len);

                    len = in.read(buf);
                }

                zos.closeEntry();
            } finally {
                close(in);
            }
        }
    }

    /**
     * Delete a file.
     *
     * @param path The path to the file.
     */
    public static void delete(String path) {
        File file = new File(path);

        delete(file);
    }

    /**
     * Delete a file.
     * 
     * @param files The files to delete
     */
    public static void delete(File... files) {
        for(File file : files){
            if (!file.exists()) {
                return;
            }

            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    delete(f.getAbsolutePath());
                }
            }

            boolean deleted = file.delete();

            if (!deleted) {
                LoggerFactory.getLogger(FileUtils.class).debug("The file (" + file.getAbsolutePath() + ") can not be deleted. ");
            }
        }
    }

    /**
     * Create an empty file.
     *
     * @param path The path to the empty file.
     */
    public static void createEmptyFile(String path) {
        File f = new File(path);

        if (!f.exists()) {
            try {
                boolean created = f.createNewFile();

                if (!created) {
                    LoggerFactory.getLogger(FileUtils.class).error("The file " + path + " cannot be created. ");
                }
            } catch (IOException e) {
                LoggerFactory.getLogger(FileUtils.class).error("Unable to create file " + e);
            }
        }
    }

    /**
     * Test if a file is in a directory.
     *
     * @param file   The file to test.
     * @param folder The folder to test.
     *
     * @return true if the file is in the directory.
     */
    public static boolean isFileInDirectory(File file, File folder) {
        return file.getAbsolutePath().contains(folder.getAbsolutePath());
    }

    /**
     * Clear the folder. It seems delete all files tags starting with deleted and the file who the start tags refer to.
     *
     * @param folder The folder to clean.
     */
    public static void clearFolder(File folder) {
        Collection<File> toDelete = new HashSet<File>(10);

        for (File f : folder.listFiles()) {
            if (f.isFile() && f.getName().startsWith("deleted")) {
                File f2 = new File(f.getAbsolutePath().replace(f.getName(), f.getName().replace("deleted", "")));

                if (f2.exists()) {
                    toDelete.add(f2);
                }

                toDelete.add(f);
            }
        }

        for (File f : toDelete) {
            delete(f);
        }
    }

    /**
     * Put the file into the directory.
     *
     * @param file   The file.
     * @param folder The folder.
     *
     * @return true if the file is now in directory.
     */
    public static boolean putFileInDirectoryIfNot(File file, File folder) {
        boolean correct = true;

        if (!isFileInDirectory(file, folder)) {
            try {
                move(file.getAbsolutePath(), folder.getAbsolutePath() + '/' + file.getName());
            } catch (CopyException e) {
                correct = false;
            }
        }

        return correct;
    }

    /**
     * Return an input stream to the path. The stream will be buffered.
     *
     * @param path The path to get an input stream for.
     *
     * @return An input stream to the file path.
     *
     * @throws FileNotFoundException If the path doesn't represent an existing file.
     */
    public static InputStream asInputStream(String path) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(path), BUFFER_SIZE);
    }

    /**
     * Return an input stream to the file. The stream will be buffered.
     *
     * @param file The file to get an input stream for.
     *
     * @return An input stream to the file.
     *
     * @throws FileNotFoundException If the file doesn't exist.
     */
    public static InputStream asInputStream(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
    }

    /**
     * Return an output stream to the file path. The stream will be buffered.
     *
     * @param path The file's path to get an output stream for.
     *
     * @return An output stream to the file's path.
     *
     * @throws FileNotFoundException If the file is a directory or cannot be opened.
     */
    public static OutputStream asOutputStream(String path) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(path), BUFFER_SIZE);
    }

    /**
     * Return an output stream to the file. The stream will be buffered.
     *
     * @param file The file to get an output stream for.
     *
     * @return An output stream to the file.
     *
     * @throws FileNotFoundException If the file is a directory or cannot be opened.
     */
    public static OutputStream asOutputStream(File file) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
    }

    /**
     * Close a closable resource.
     *
     * @param closable The closable resource to close.
     */
    public static void close(Closeable closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (IOException e) {
                LoggerFactory.getLogger(FileUtils.class).error("Unable to close stream", e);
            }
        }
    }

    /**
     * Close a zip file.
     *
     * @param zipFile The zip file to close.
     */
    public static void close(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException e) {
                LoggerFactory.getLogger(FileUtils.class).error("Unable to close zip file", e);
            }
        }
    }

    /**
     * Return an existing file.
     *
     * @return An existing file object.
     */
    public static File getAnExistingFile() {
        for (File root : File.listRoots()) {
            for (File f : root.listFiles()) {
                if (f.isFile() && f.length() > 0) {
                    return f;
                }
            }
        }

        return null;
    }

    /**
     * Return the size of the file specified by the path.
     *
     * @param path The path to the file.
     *
     * @return The size of the file or 0 if the file doesn't exist.
     */
    public static long getFileSize(String path) {
        if (StringUtils.isEmpty(path)) {
            return 0;
        }

        return getFileSize(new File(path));
    }

    /**
     * Return the size of the specified file.
     *
     * @param file The file to calc the size.
     *
     * @return The size of the file or 0 if the file doesn't exist.
     */
    public static long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }

        return file.length();
    }

    /**
     * Return the date of the last modification of the file denoted by the specified path.
     *
     * @param path The path to the file.
     *
     * @return The date of the last modification of the file or null if the file doesn't not exists.
     */
    public static Date getLastModifiedDate(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }

        return getLastModifiedDate(new File(path));
    }

    /**
     * Return the date of the last modification of the specified file.
     *
     * @param file The file.
     *
     * @return The date of the last modification of the file or null if the file doesn't not exists.
     */
    public static Date getLastModifiedDate(File file) {
        if (file == null || !file.exists()) {
            return null;
        }

        long lastModified = file.lastModified();

        return lastModified == 0L ? null : new Date(lastModified);
    }

    /**
     * Return the next free name for the specified name in the specified folder. If there is also a file named name in
     * the specified folder, it will search for files name[n].extension while it find a not existing file.
     *
     * @param folder The folder to search free name in.
     * @param name   The name to add.
     *
     * @return The next free name for the specified name in the specified folder.
     */
    public static String getFreeName(String folder, String name) {
        if (new File(folder, name).exists()) {
            int count = 1;

            String freeName;

            do {
                freeName = name.substring(0, name.lastIndexOf('.')) + '[' + count + ']' + name.substring(name.lastIndexOf('.'));
                count++;
            } while (new File(folder, freeName).exists());

            return freeName;
        }

        return name;
    }

    /**
     * Return all the files of the folder include the sub files and folders with no level limit.
     *
     * @param folder     The folder to get the files from.
     * @param fileFilter The filter to use to select the files.
     *
     * @return A Collection containing all the files of the folder and his sub folders selected by the specified file
     *         filter.
     */
    public static Collection<File> getFilesOfFolder(File folder, FileFilter fileFilter) {
        if (folder.isDirectory()) {
            Collection<File> files = new ArrayList<File>(50);

            readFolder(folder, files, fileFilter);

            return files;
        }

        return Collections.emptyList();
    }

    /**
     * Read the folder and all the files of the folder in the collection.
     *
     * @param folder     The folder to read.
     * @param files      The collection to add the files to.
     * @param fileFilter The filter to get the files with.
     */
    private static void readFolder(File folder, Collection<File> files, FileFilter fileFilter) {
        for (File file : folder.listFiles(fileFilter)) {
            if (file.isDirectory()) {
                readFolder(file, files, fileFilter);
            } else {
                files.add(file);
            }
        }
    }
}