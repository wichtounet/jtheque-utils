package org.jtheque.utils.tests;

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

import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A TestCase to test the ArrayUtils class.
 *
 * @author Baptiste Wicht
 */
public final class ArrayUtilsTest {
    /**
     * Test the indexOf method.
     */
    @Test
    public void testIndexOf() {
        Object[] objects = {"Tests", 6.0, 4, Version.get("1.2")};

        assertEquals(1, ArrayUtils.indexOf(objects[1], objects));
        assertEquals(3, ArrayUtils.indexOf(objects[3], objects));
        assertEquals(-1, ArrayUtils.indexOf("Bonjour", objects));
        assertEquals(3, ArrayUtils.indexOf(Version.get("1.2"), objects));
    }

    /**
     * Test the reverse method.
     */
    @Test
    public void testReverseObjectArray() {
        Object[] array = {"Tests", 6.0, 4, Version.get("1.2")};
        List<Object> list = Arrays.<Object>asList("Tests", 6.0, 4, Version.get("1.2"));

        ArrayUtils.reverse(array);
        Collections.reverse(list);

        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], list.get(i));
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
            assertEquals((Integer) array[i], list.get(i));
        }
    }

    @Test
    public void isEmpty() {
        Object[] a1 = {};
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

        assertNotSame(copy, toCopy);
        assertEquals(copy[0], toCopy[0]);
        assertEquals(copy[1], toCopy[1]);
    }
}
