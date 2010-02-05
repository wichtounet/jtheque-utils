package org.jtheque.utils;

import java.util.Scanner;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

public class ScannerUtils {
    public static String getLineStartingWith(Scanner scanner, String starts){
        if(scanner != null){
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