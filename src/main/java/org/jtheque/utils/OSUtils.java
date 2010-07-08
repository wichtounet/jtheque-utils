package org.jtheque.utils;

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
 * Some utility methods for OS.
 *
 * @author Baptiste Wicht
 */
public final class OSUtils {
    /**
     * Utility class, not instanciable.
     */
    private OSUtils() {
        throw new AssertionError();
    }

    /**
     * Test if the running operating system is Windows or not.
     *
     * @return true if the running operating system is Windows else false.
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").contains("Windows");
    }

    /**
     * Test if the running operating system is Linux or not.
     *
     * @return true if the running operating system is Linux else false.
     */
    public static boolean isLinux() {
        return System.getProperty("os.name").contains("Linux");
    }

    /**
     * Test if the running operating system is Mac or not.
     *
     * @return true if the running operating system is Mac else false.
     */
    public static boolean isMac() {
        return System.getProperty("os.name").contains("Mac");
    }
}