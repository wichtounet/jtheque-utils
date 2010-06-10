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
