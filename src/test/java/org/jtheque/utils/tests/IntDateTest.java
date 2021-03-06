package org.jtheque.utils.tests;

import org.jtheque.utils.bean.IntDate;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

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
 * A Test for the IntDate class.
 *
 * @author Baptiste Wicht
 */
public final class IntDateTest {
    /**
     * Test the constructor copy.
     */
    @Test
    public void copyConstructor() {
        IntDate first = new IntDate(20090409);

        IntDate second = new IntDate(first);

        assertEquals(first.getStrDate(), second.getStrDate());
        assertEquals(first.intValue(), second.intValue());
        assertEquals(first.intValue(), second.intValue());
    }

    /**
     * Test the Fields class.
     */
    @Test
    public void fields() {
        assertFalse(IntDate.Fields.isValid(25));
        assertFalse(IntDate.Fields.isValid(1000));

        assertTrue(IntDate.Fields.isValid(IntDate.Fields.MONTH));
        assertTrue(IntDate.Fields.isValid(IntDate.Fields.DAY));
        assertTrue(IntDate.Fields.isValid(IntDate.Fields.YEAR));
    }

    /**
     * Test the setters.
     */
    @Test
    public void set() {
        IntDate date = new IntDate(20080105);

        date.set(IntDate.Fields.DAY, 1);
        date.set(IntDate.Fields.YEAR, 2000);
        date.set(IntDate.Fields.MONTH, 1);

        assertEquals(1, date.getDay());
        assertEquals(2000, date.getYear());
        assertEquals(1, date.getMonth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalSet() {
        IntDate date = new IntDate(20080105);

        date.set(2005, 3);
    }

    /**
     * Test the add methods.
     */
    @Test
    public void add() {
        IntDate date = new IntDate(20000101);

        date.add(IntDate.Fields.DAY, 5);

        assertEquals(6, date.getDay());

        date.add(IntDate.Fields.MONTH, 5);

        assertEquals(6, date.getMonth());

        date.add(IntDate.Fields.YEAR, 5);

        assertEquals(2005, date.getYear());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addWithException() {
        IntDate date = new IntDate(20000101);

        date.add(259999, 1000);
    }

    /**
     * Test the formatter.
     */
    @Test
    public void format() {
        Object date = new IntDate(20080105);

        assertEquals("05.01.2008", date.toString());
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        IntDate date = new IntDate(20080105);

        assertEquals("20080105", date.getStrDate());
    }

    /**
     * Test the getters.
     */
    @Test
    public void testGetters() {
        IntDate date = new IntDate(20090409);

        assertEquals(9, date.getDay());
        assertEquals(2009, date.getYear());
        assertEquals(4, date.getMonth());
    }

    /**
     * Test the today method.
     */
    @Test
    public void today() {
        IntDate today = IntDate.today();

        assertEquals(today.getDay(), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        assertEquals(today.getMonth(), Calendar.getInstance().get(Calendar.MONTH) + 1);
        assertEquals(today.getYear(), Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * Test the compareTo method.
     */
    @Test
    public void compare() {
        IntDate c2 = new IntDate(30000910);
        IntDate c3 = new IntDate(20090910);
        IntDate c4 = new IntDate(20090910);

        assertTrue(c2.compareTo(c3) > 0);
        assertTrue(c2.compareTo(c4) > 0);

        assertTrue(c3.compareTo(c2) < 0);
        assertTrue(c4.compareTo(c2) < 0);

        assertSame(0, c3.compareTo(c4));
    }

    @Test
    public void compareGreater() {
        IntDate c1 = new IntDate(20050910);
        IntDate c2 = new IntDate(20091010);
        Comparable<IntDate> c3 = new IntDate(20091011);

        assertTrue(c2.compareTo(c1) > 0);
        assertTrue(c3.compareTo(c1) > 0);
        assertTrue(c3.compareTo(c2) > 0);
    }

    /**
     * Test the hashCode().
     */
    @Test
    public void testHashCode() {
        Object first = new IntDate(20090409);
        Object second = new IntDate(20090409);
        Object third = new IntDate(20050409);

        assertEquals(first.hashCode(), second.hashCode());
        assertNotSame(third.hashCode(), second.hashCode());
    }

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        Object first = new IntDate(20090409);
        Object second = new IntDate(20090409);
        Object third = new IntDate(20050409);

        assertEquals(first, first);
        assertEquals(second, second);
        assertEquals(first, second);
        assertEquals(second, first);
        assertFalse(second.equals(third));
        assertFalse(third.equals(second));
        assertFalse(first.equals(null));
    }
}
