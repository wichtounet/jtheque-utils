package org.jtheque.utils.collections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * This class provide utility methods to the use of arrays.
 *
 * @author Baptiste Wicht
 */
public final class ArrayUtils {
    public static final Object[] ZERO_LENGTH_ARRAY = new Object[0];

    /**
     * Construct a new <code>ArrayUtils</code>. The constructor is private, all methods are static.
     */
    private ArrayUtils() {
        super();
    }

    /**
     * Inverse the order of the array and return it.
     *
     * @param array The array to inverse.
     */
    public static void reverse(Object[] array) {
        int len = array.length;
        int hlen = len / 2;

        for (int i = 0; i < hlen; ++i) {
            Object temp = array[i];
            array[i] = array[len - 1 - i];
            array[len - 1 - i] = temp;
        }
    }

    /**
     * Return the index of the object on the tab.
     *
     * @param object The object to search  for.
     * @param tab    The tab to search in.
     *
     * @return The index of the objet in the tab else -1 if the object is not present in the tab.
     */
    public static int indexOf(Object object, Object[] tab) {
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == object) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Reverse the array.
     *
     * @param array The array to reverse.
     */
    public static void reverse(int[] array) {
        int len = array.length;
        int hlen = len / 2;

        for (int i = 0; i < hlen; ++i) {
            int temp = array[i];
            array[i] = array[len - 1 - i];
            array[len - 1 - i] = temp;
        }
    }

    /**
     * Test if the array is empty or contains only <code>null</code> values.
     *
     * @param array The array to test.
     *
     * @return true if the array size is 0 or if the array contains only <code>null</code> values.
     */
    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length <= 0) {
            return true;
        }

        for (Object o : array) {
            if (o != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Return a copy of the array.
     *
     * @param array The array to copy.
     * @param <T>   The type of objects.
     *
     * @return The copy of the array.
     */
    public static <T> T[] copyOf(T[] array) {
        return Arrays.copyOf(array, array.length);
    }

    /**
     * Create a Set containing all the given values.
     *
     * @param values The values to put into the set.
     * @param <T>    The type of objects.
     *
     * @return A Set containing all the given values.
     */
    public static <T> Set<T> asSet(T... values) {
        return new HashSet<T>(Arrays.asList(values));
    }

    /**
     * Create a List containing all the given values.
     *
     * @param values The values to put into the list.
     * @param <T>    The type of objects.
     *
     * @return A List containing all the given values.
     */
    public static <T> List<T> asList(T... values) {
        return Arrays.asList(values);
    }

    /**
     * Test if the given array contains the given value.
     *
     * @param values   The array to search in.
     * @param searched The value to search.
     * @param <T>      The type of objecsts.
     *
     * @return <code>true</code> if the array contains the searched value otherwise <code>false</code>.
     */
    public static <T> boolean contains(T[] values, T searched) {
        for (T value : values) {
            if (value.equals(searched)) {
                return true;
            }
        }

        return false;
    }
}
