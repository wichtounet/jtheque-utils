package org.jtheque.utils.bean;

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
 * A simple utility class to compare numbers.
 *
 * @author Baptiste Wicht
 */
public final class Numbers {
    /**
     * Utility class, not instantiable.
     */
    private Numbers() {
        throw new AssertionError();
    }

    /**
     * Compare the two numbers and return the compare code.
     *
     * @param i1 The first number.
     * @param i2 The second number.
     *
     * @return 1 if the first number is greater than the second, -1 if the second is greater and 0 if they are equals.
     */
    public static int compare(int i1, int i2) {
        return i1 < i2 ? -1 : i1 == i2 ? 0 : 1;
    }

    /**
     * Compare the two numbers and return the compare code.
     *
     * @param d1 The first number.
     * @param d2 The second number.
     *
     * @return 1 if the first number is greater than the second, -1 if the second is greater and 0 if they are equals.
     */
    public static int compare(double d1, double d2) {
        return d1 < d2 ? -1 : d1 == d2 ? 0 : 1;
    }

    /**
     * Compare the two numbers and return the compare code.
     *
     * @param l1 The first number.
     * @param l2 The second number.
     *
     * @return 1 if the first number is greater than the second, -1 if the second is greater and 0 if they are equals.
     */
    public static int compare(long l1, long l2) {
        return l1 < l2 ? -1 : l1 == l2 ? 0 : 1;
    }
}