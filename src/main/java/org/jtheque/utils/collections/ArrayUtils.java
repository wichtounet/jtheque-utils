package org.jtheque.utils.collections;

import java.util.Arrays;

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
     * @return true if the array size is 0 or if the array contains only <code>null</code> values.
     */
    public static boolean isEmpty(Object[] array) {
        if(array == null || array.length <= 0){
            return true;
        }
        
        for(Object o : array){
            if(o != null){
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
     * @return The copy of the array.
     */
    public static <T> T[] copyOf(T[] array) {
        return Arrays.copyOf(array, array.length);
    }
}
