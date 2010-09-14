package org.jtheque.utils.io;

import org.jtheque.utils.collections.SimpleTimedCache;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

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

/**
 * A simple utility class to manipulate web things.
 *
 * @author Baptiste Wicht
 */
public final class WebUtils {
    private static final SimpleTimedCache<URL> URLS = new SimpleTimedCache<URL>(60000);//60 Seconds of timeout

    private static final String INTERNET_URL = "http://www.google.com";

    /**
     * Utility class, not instantiable.
     */
    private WebUtils() {
        throw new AssertionError();
    }

    /**
     * Download a file.
     *
     * @param filePath    The path to the file to download.
     * @param destination The path to the destination's file.
     *
     * @throws FileException If an error occurs during the downloading process
     */
    public static void downloadFile(String filePath, String destination) throws FileException {
        InputStream is = null;

        try {
            URL url = new URL(filePath);

            URLConnection connection = url.openConnection();

            int length = connection.getContentLength();

            if (length == -1) {
                throw new IOException("Empty file (" + filePath + ')');
            }

            is = new BufferedInputStream(connection.getInputStream());

            byte[] data = downloadData(is, length);

            writeData(destination, data);
        } catch (MalformedURLException e) {
            throw new FileException("Exception occurred during downloading", e);
        } catch (IOException e) {
            throw new FileException("Exception occurred during downloading", e);
        } finally {
            FileUtils.close(is);
        }
    }

    /**
     * Download data from a stream and return the data as a byte array.
     *
     * @param is     The input stream.
     * @param length The length to read.
     *
     * @return The data into a byte array.
     *
     * @throws IOException If an IO problem occurs during the reading.
     */
    private static byte[] downloadData(InputStream is, int length) throws IOException {
        byte[] data = new byte[length];

        int offset = 0;

        while (offset < length) {
            int currentBit = is.read(data, offset, data.length - offset);

            if (currentBit == -1) {
                break;
            }

            offset += currentBit;
        }

        if (offset != length) {
            throw new IOException("The file has not been fully read (Only " + offset + " of " + length + ')');
        }
        return data;
    }

    /**
     * Write data to a file.
     *
     * @param destination The destination file path.
     * @param data        The data to write.
     *
     * @throws IOException If an IO problem occurs during the writing.
     */
    private static void writeData(String destination, byte[] data) throws IOException {
        OutputStream destinationFile = null;
        try {
            destinationFile = FileUtils.asOutputStream(destination);

            destinationFile.write(data);

            destinationFile.flush();
        } finally {
            FileUtils.close(destinationFile);
        }
    }

    /**
     * Indicate if the internet is not reachable.
     *
     * @return {@code true} if the internet is not reachable otherwise {@code false}.
     */
    public static boolean isInternetNotReachable() {
        return !isURLReachable(INTERNET_URL);
    }

    /**
     * Indicate if the internet is reachable.
     *
     * @return {@code true} if the internet is reachable otherwise {@code false}.
     */
    public static boolean isInternetReachable() {
        return isURLReachable(INTERNET_URL);
    }

    /**
     * Indicate if the URL is not reachable or not.
     *
     * @param url The URL to test.
     *
     * @return {@code true} if the URL is not reachable otherwise {@code false}.
     */
    public static boolean isURLNotReachable(String url) {
        return !isURLReachable(url);
    }

    /**
     * Indicate if the URL is reachable or not.
     *
     * @param url The URL to test.
     *
     * @return {@code true} if the URL is reachable otherwise {@code false}.
     */
    public static boolean isURLReachable(String url) {
        try {
            return isURLReachable(new URL(url));
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * Indicate if the URL is not reachable or not.
     *
     * @param url The URL to test.
     *
     * @return {@code true} if the URL is not reachable otherwise {@code false}.
     */
    public static boolean isURLNotReachable(URL url) {
        return !isURLReachable(url);
    }

    /**
     * Indicate if the URL is reachable or not.
     *
     * @param url The URL to test.
     *
     * @return {@code true} if the URL is reachable otherwise {@code false}.
     */
    public static boolean isURLReachable(URL url) {
        if (URLS.contains(url)) {
            return true;
        }

        try {
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

            urlConnect.getInputStream();

            if (urlConnect.getResponseCode() >= 300) {
                return false;
            }

            urlConnect.disconnect();
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        URLS.add(url);

        return true;
    }
}