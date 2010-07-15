package org.jtheque.utils.tests;

import org.jtheque.utils.bean.Duration;

import org.junit.Test;

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