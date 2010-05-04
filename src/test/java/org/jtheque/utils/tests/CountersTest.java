package org.jtheque.utils.tests;

import org.jtheque.utils.count.Counter;
import org.jtheque.utils.count.Counters;
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
