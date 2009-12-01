package org.jtheque.utils.tests;

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

import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A TestCase to test the ArrayUtils class.
 *
 * @author Baptiste Wicht
 */
public class ArrayUtilsTest {
    /**
     * Test the indexOf method.
     */
    @Test
    public void testIndexOf() {
        Object[] objects = {"Tests", 6.0, 4, new Version("1.2")};

        assertTrue(ArrayUtils.indexOf(objects[1], objects) == 1);
        assertTrue(ArrayUtils.indexOf(objects[3], objects) == 3);
        assertTrue(ArrayUtils.indexOf("Bonjour", objects) == -1);
        assertTrue(ArrayUtils.indexOf(new Version("1.2"), objects) == -1);
    }

    /**
     * Test the reverse method.
     */
    @Test
    public void testReverseObjectArray() {
        Object[] array = {"Tests", 6.0, 4, new Version("1.2")};
        List<Object> list = Arrays.<Object>asList("Tests", 6.0, 4, new Version("1.2"));

        ArrayUtils.reverse(array);
        Collections.reverse(list);

        for (int i = 0; i < array.length; i++) {
            assertTrue(array[i].equals(list.get(i)));
        }
    }

    /**
     * Test the reverse method.
     */
    @Test
    public void testReverseIntArray() {
        int[] array = {1, 100, 67, 34, 0, 44};
        List<Integer> list = Arrays.asList(1, 100, 67, 34, 0, 44);

        ArrayUtils.reverse(array);
        Collections.reverse(list);

        for (int i = 0; i < array.length; i++) {
            assertTrue(array[i] == list.get(i));
        }
    }

    @Test
    public void isEmpty() {
        Object[] a1 = new Object[]{};
        Object[] a2 = {null};
        Object[] a3 = {"test"};

        assertTrue(ArrayUtils.isEmpty(a1));
        assertTrue(ArrayUtils.isEmpty(a2));
        assertFalse(ArrayUtils.isEmpty(a3));
    }

    @Test
    public void copyOf() {
        String[] toCopy = {"Big Show", "The Undertaker"};
        String[] copy = ArrayUtils.copyOf(toCopy);

        assertFalse(copy == toCopy);
        assertTrue(copy[0].equals(toCopy[0]));
        assertTrue(copy[1].equals(toCopy[1]));
    }
}
