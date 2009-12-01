package org.jtheque.utils.tests;

import org.jtheque.utils.count.Counter;
import org.jtheque.utils.count.Counters;
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
 * A TestCase to test the Couters class.
 *
 * @author Baptiste Wicht
 */
public class CountersTest {
    /**
     * Test the testAndGet method.
     */
    @Test
    public void testAddAndGet() {
        Counters counters = new Counters();

        counters.addCounter("test1");
        counters.addCounter("test2");

        Counter counter2 = counters.getCounter("test2");
        Counter counter1 = counters.getCounter("test1");

        assertEquals(counter1, counters.getCounter("test1"));
        assertEquals(counter2, counters.getCounter("test2"));

        assertNotNull(counter1);
        assertNotNull(counter2);

        assertNull(counters.getCounter("test3"));

        assertNotNull(counters.getCounterOrAdd("test3"));
        assertNotNull(counters.getCounter("test3"));
    }

    /**
     * Test the testCounter method.
     */
    @Test
    public void testCounter() {
        Counters counters = new Counters();

        Counter testCounter = counters.getCounterOrAdd("test");

        assertEquals(testCounter.getValue(), 0);

        testCounter.increment();

        assertEquals(testCounter.getValue(), 1);

        testCounter.decrement();

        assertEquals(testCounter.getValue(), 0);

        int baseValue = 150;

        testCounter.add(baseValue);

        assertEquals(testCounter.getValue(), baseValue);

        testCounter.increment();

        assertEquals(testCounter.getValue(), baseValue + 1);

        testCounter.decrement();

        assertEquals(testCounter.getValue(), baseValue);

        testCounter.clear();

        assertEquals(testCounter.getValue(), 0);

        testCounter.add(baseValue);

        Counter c = counters.getCounter("test");

        assertEquals(c.getValue(), testCounter.getValue());
    }
}
