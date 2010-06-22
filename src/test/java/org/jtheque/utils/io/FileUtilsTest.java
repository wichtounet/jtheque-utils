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

import org.jtheque.utils.CryptoUtils;
import org.jtheque.utils.Hasher;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.jtheque.utils.unit.file.FileUnit.*;
import static org.junit.Assert.*;

/**
 * @author Baptiste Wicht
 */
public class FileUtilsTest {
    @BeforeClass
    public static void prepare() {
        initTestFileSystem();
    }

    @AfterClass
    public static void clear() {
        clearTestFileSystem();
    }

    @Test
    public void initOK() {
        assertInitOK();
    }

    @Test
    public void createIfNotExists() {
        FileUtils.createIfNotExists(getFile("file.txt"));

        assertFileExists("file.txt");
    }

    @Test
    public void deleteNonExistingFile() {
        FileUtils.delete(getFile("non-existing.txt"));
    }

    @Test
    public void deleteExistingFile() {
        addFile("existing.txt");

        FileUtils.delete(getFile("existing.txt"));

        assertFileNotExists("existing.txt");
    }

    @Test
    public void deleteEmptyFolder() {
        addFolder("folder");

        FileUtils.delete(getFile("folder"));

        assertFileNotExists("folder");
    }

    @Test
    public void deleteNonEmptyFolder() {
        addFolder("folder2");
        addFile("folder2", "file1.txt");
        addFile("folder2", "file2.txt");
        addFile("folder2", "file3.txt");
        addFile("folder2", "folder1");

        FileUtils.delete(getFile("folder2"));

        assertFileNotExists("folder2");
    }

    @Test
    public void createEmptyFile() {
        FileUtils.createEmptyFile(getPath("file.test"));

        assertFileExists("file.test");
    }

    @Test
    public void isFileInDirectory() {
        addFolder("folder2");
        addFile("folder2", "file1.txt");
        addFile("file2.txt");

        assertTrue(FileUtils.isFileInDirectory(getFile("folder2", "file1.txt"), getFile("folder2")));
        assertFalse(FileUtils.isFileInDirectory(getFile("file2.txt"), getFile("folder2")));
    }

    @Test
    public void putFileInDirectory() {
        addFolder("folder3");
        addFile("folder3", "file1.txt");
        addFolder("folder4");

        FileUtils.putFileInDirectoryIfNot(getFile("folder3", "file1.txt"), getFile("folder4"));

        assertDirectoryContains("folder4", "file1.txt");
    }

    @Test
    public void clearFolder() {
        addFolder("folder5");
        addFile("folder5", "deletedfile2.txt");
        addFile("folder5", "file2.txt");
        addFile("folder5", "deletedasdf.txt");
        addFile("folder5", "asdf.txt");
        addFile("folder5", "deletedqwer.txt");
        addFile("folder5", "asfd1234.txt");

        FileUtils.clearFolder(getFile("folder5"));

        assertDirectorySize("folder5", 1);
        assertDirectoryContains("folder5", "asfd1234.txt");
    }

    @Test
    public void getTextOf() {
        addFile("test28.txt");

        String expectedContent = "Salut la compagnie, c'est moi Arthur le Booli \n A+";

        setContent("test28.txt", expectedContent);

        String content = FileUtils.getTextOf(getPath("test28.txt"));

        assertEquals(expectedContent, content);
    }

    @Test
    public void getLinesOf() {
        addFile("test23.txt");

        setContent("test23.txt", "Salut la compagnie, c'est moi Arthur le Booli \nA+");

        List<String> lines = new ArrayList<String>(FileUtils.getLinesOf(getInputStream("test23.txt")));

        assertEquals(2, lines.size());
        assertEquals("Salut la compagnie, c'est moi Arthur le Booli ", lines.get(0));
        assertEquals("A+", lines.get(1));
    }

    @Test(expected = CopyException.class)
    public void copyNonExistingFile() throws CopyException {
        FileUtils.copy(getPath("umaga.txt"), getPath("umaga2.txt"));
    }

    @Test
    public void copyFile() {
        addFile("tocopy.txt");

        setContent("tocopy.txt", "Salut la compagnie, c'est moi Arthur le Booli \nA+");

        try {
            FileUtils.copy(getPath("tocopy.txt"), getPath("copy.txt"));
        } catch (CopyException e) {
            fail("Unable to copy due to " + e.getMessage());
        }

        assertFileExists("tocopy.txt");
        assertFileExists("copy.txt");

        assertFileContentEquals("tocopy.txt", "Salut la compagnie, c'est moi Arthur le Booli \nA+");
        assertFileContentEquals("copy.txt", "Salut la compagnie, c'est moi Arthur le Booli \nA+");
    }

    @Test
    public void moveFile() {
        addFile("tocopy2.txt");

        setContent("tocopy2.txt", "Salut la compagnie, c'est moi Arthur le Booli \nA+");

        try {
            FileUtils.move(getPath("tocopy2.txt"), getPath("copy2.txt"));
        } catch (CopyException e) {
            fail("Unable to copy due to " + e.getMessage());
        }

        assertFileNotExists("tocopy2.txt");
        assertFileExists("copy2.txt");

        assertFileContentEquals("copy2.txt", "Salut la compagnie, c'est moi Arthur le Booli \nA+");
    }

    @Test
    public void copyFolder() {
        addFolder("foldertocopy");
        addFile("foldertocopy", "tst1.txt");
        addFile("foldertocopy", "tst2.txt");
        addFolder("foldertocopy", "sub1");
        addFile("foldertocopy/sub1", "tst1.txt");

        try {
            FileUtils.copy(getPath("foldertocopy"), getPath("foldercopy"));
        } catch (CopyException e) {
            fail("Unable to copy due to " + e.getMessage());
        }

        assertFileExists("foldertocopy");
        assertFileExists("foldercopy");
        assertFileExists("foldercopy", "tst1.txt");
        assertFileExists("foldercopy", "tst2.txt");
        assertFileExists("foldercopy", "sub1");
        assertFileExists("foldercopy/sub1", "tst1.txt");
    }

    @Test
    public void zip() {
        addFolder("tozip");
        addFile("tozip", "file1.txt");
        addFile("tozip", "file2.txt");
        addFile("tozip", "file3.txt");
        addFile("tozip", "file4.txt");

        setContent("tozip", "file1.txt", "This a test, This is file 1");
        setContent("tozip", "file2.txt", "This a test, This is file 2");
        setContent("tozip", "file3.txt", "This a test, This is file 3");
        setContent("tozip", "file4.txt", "This a test, This is file 4");

        Collection<File> files = Arrays.asList(getFile("tozip", "file1.txt"), getFile("tozip", "file2.txt"), getFile("tozip", "file3.txt"),
                getFile("tozip", "file4.txt"));

        FileUtils.zip(files, getFile("zip.zip"));

        assertFileExists("zip.zip");
        assertZipContains("zip.zip", "file1.txt", "file2.txt", "file3.txt", "file4.txt");
    }

    @Test
    public void unzip() {
        addFolder("tozip2");
        addFile("tozip2", "file1.txt");
        addFile("tozip2", "file2.txt");
        addFile("tozip2", "file3.txt");
        addFile("tozip2", "file4.txt");

        setContent("tozip2", "file1.txt", "This a test, This is file 1");
        setContent("tozip2", "file2.txt", "This a test, This is file 2");
        setContent("tozip2", "file3.txt", "This a test, This is file 3");
        setContent("tozip2", "file4.txt", "This a test, This is file 4");

        Collection<File> files = Arrays.asList(getFile("tozip2", "file1.txt"), getFile("tozip2", "file2.txt"),
                getFile("tozip2", "file3.txt"), getFile("tozip2", "file4.txt"));

        FileUtils.zip(files, getFile("zip2.zip"));

        FileUtils.unzip(getInputStream("zip2.zip"), getRootFolder());

        assertFileExists("zip2.zip");
        assertFileExists("file1.txt");
        assertFileExists("file2.txt");
        assertFileExists("file3.txt");
        assertFileExists("file4.txt");

        assertFileContentEquals("file1.txt", "This a test, This is file 1");
        assertFileContentEquals("file2.txt", "This a test, This is file 2");
        assertFileContentEquals("file3.txt", "This a test, This is file 3");
        assertFileContentEquals("file4.txt", "This a test, This is file 4");
    }

    @Test(expected = AssertionError.class)
    public void close() throws IOException {
        addFile("file.txt");

        InputStream stream = getInputStream(getPath("file.txt"));

        FileUtils.close(stream);

        stream.read();
    }

    @Test
    public void hashMessage() {
        String key = "superclé";
        String encrypted = CryptoUtils.hashMessage(key, Hasher.SHA512);

        assertFalse(key.equals(encrypted));
        assertEquals(encrypted, CryptoUtils.hashMessage(key, Hasher.SHA512));
    }

    @Test
    public void download() throws FileException {
        addFile("downloaded.txt");

        FileUtils.downloadFile("http://baptiste-wicht.developpez.com/dl.txt", getPath("downloaded.txt"));

        assertFileExists("downloaded.txt");
        assertFileContentEquals("downloaded.txt", "download me !");
    }
}