package org.jtheque.utils.tests;

import org.jtheque.utils.bean.IntDate;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

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
 * A Test for the IntDate class.
 *
 * @author Baptiste Wicht
 */
public class IntDateTest {
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

        assertEquals(date.getDay(), 1);
        assertEquals(date.getYear(), 2000);
        assertEquals(date.getMonth(), 1);
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

        assertEquals(date.getDay(), 6);

        date.add(IntDate.Fields.MONTH, 5);

        assertEquals(date.getMonth(), 6);

        date.add(IntDate.Fields.YEAR, 5);

        assertEquals(date.getYear(), 2005);
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

        assertEquals(date.toString(), "05.01.2008");
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        IntDate date = new IntDate(20080105);

        assertEquals(date.getStrDate(), "20080105");
    }

    /**
     * Test the getters.
     */
    @Test
    public void testGetters() {
        IntDate date = new IntDate(20090409);

        assertEquals(date.getDay(), 9);
        assertEquals(date.getYear(), 2009);
        assertEquals(date.getMonth(), 4);
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
