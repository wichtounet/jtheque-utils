package org.jtheque.utils.tests;

import org.jtheque.utils.bean.Duration;
import org.junit.Test;

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
 * A TestCase for the Duration class.
 *
 * @author Baptiste Wicht
 */
public class DurationTest {
    /**
     * Test the constructors.
     */
    @Test
    public void testConstructors() {
        Duration d1 = new Duration(120);
        Duration d2 = new Duration(0);
        Duration d3 = new Duration();
        Duration d4 = new Duration(138);

        assertEquals(2, d1.getHours());
        assertEquals(0, d1.getMinutes());
        assertEquals(0, d2.getHours());
        assertEquals(0, d2.getMinutes());
        assertEquals(0, d3.getHours());
        assertEquals(0, d3.getMinutes());
        assertEquals(2, d4.getHours());
        assertEquals(18, d4.getMinutes());
    }

    /**
     * Test the setters.
     */
    @Test
    public void testSets() {
        Duration d1 = new Duration(138);
        Duration d2 = new Duration();

        d1.setHours(6);

        assertEquals(d1.getHours(), 6);
        assertEquals(d1.getMinutes(), 18);

        d1.setMinutes(22);

        assertEquals(d1.getHours(), 6);
        assertEquals(d1.getMinutes(), 22);

        d2.setHours(2);
        d2.setMinutes(25);

        assertEquals(d2.getHours(), 2);
        assertEquals(d2.getMinutes(), 25);
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testTostring() {
        Object d1 = new Duration(138);
        Object d2 = new Duration();

        assertEquals(d1.toString(), "2:18");
        assertEquals(d2.toString(), "0:0");
    }

    /**
     * Test equals and hashCode methods.
     */
    @Test
    public void testEqualsAndHashCode() {
        Object d1 = new Duration(138);
        Object d2 = new Duration(138);
        Object d3 = new Duration(2255);

        assertTrue(d1.hashCode() == d2.hashCode());
        assertFalse(d1.hashCode() == d3.hashCode());
        assertFalse(d2.hashCode() == d3.hashCode());

        assertEquals(d1, d1);
        assertEquals(d1, d2);
        assertEquals(d2, d1);

        assertNotSame("test1", d1);
        assertNotSame("test2", d2);
        assertNotSame("test3", d3);

        assertFalse(d1.equals(d3));
        assertFalse(d3.equals(d1));

        assertFalse(d3.equals(d2));
        assertFalse(d2.equals(d3));
    }
}