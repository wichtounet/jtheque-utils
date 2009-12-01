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

import org.jtheque.utils.collections.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class to test the CollectionUtils class.
 *
 * @author Baptiste Wicht
 */
public class CollectionUtilsTest {
    /**
     * Test the copyOf method.
     */
    @Test
    public void testCopyOf() {
        Collection<String> strings = new ArrayList<String>(2);

        strings.add("Test 1");
        strings.add("Test 2");

        Collection<String> copyStrings = CollectionUtils.copyOf(strings);

        assertEquals(strings.size(), copyStrings.size());
        assertEquals(strings, copyStrings);

        copyStrings.clear();

        assertEquals(strings.size(), 2);
    }

    @Test
    public void testReverse() {
        List<String> strings = new ArrayList<String>(2);

        strings.add("Test 1");
        strings.add("Test 2");

        CollectionUtils.reverse(strings);

        assertEquals("Test 2", strings.get(0));
        assertEquals("Test 1", strings.get(1));
    }

    @Test
    public void testEmptyList() {
        List<String> emptyList = CollectionUtils.emptyList();

        assertTrue(emptyList.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEmptyListModification() {
        List<String> emptyList = CollectionUtils.emptyList();

        emptyList.add("Test");
    }

    @Test
    public void first() {
        Iterable<String> list = Arrays.asList("asdf", "bsadf", "rtert");

        assertEquals(CollectionUtils.first(list), "asdf");
    }
}
