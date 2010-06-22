package org.jtheque.utils;

import java.util.Scanner;

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
 * Utility class to work with Scanner.
 *
 * @author Baptiste Wicht
 */
public class ScannerUtils {
    /**
     * Utility class, not instantiable.
     */
    private ScannerUtils() {
        super();
    }

    /**
     * Return the line starting with starts using the given scanner. The scanner will be closed if we read all the
     * lines.
     *
     * @param scanner The scanner to search in.
     * @param starts  The String we search a line starting with.
     *
     * @return The first line starting with starts in the scanner else an empty String if there is no line starting with
     *         starts.
     */
    public static String getLineStartingWith(Scanner scanner, String starts) {
        if (scanner != null) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith(starts)) {
                    scanner.close();

                    return line;
                }
            }

            scanner.close();
        }

        return "";
    }
}