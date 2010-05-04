package org.jtheque.utils.count;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A collections of counters.
 *
 * @author Baptiste Wicht
 */
public final class Counters implements Iterable<Entry<String, Counter>> {
    private final Map<String, Counter> counters;

    /**
     * Construct a new Collection of counters.
     */
    public Counters() {
        super();

        counters = new HashMap<String, Counter>(10);
    }

    /**
     * Add a counter with a specific name to the collection.
     *
     * @param name The name of the new counter.
     */
    public void addCounter(String name) {
        counters.put(name, new Counter());
    }

    /**
     * Return the counter named with the specific name.
     *
     * @param name The name of the counter we search.
     * @return The searched counter.
     */
    public Counter getCounter(String name) {
        return counters.get(name);
    }

    /**
     * Return the named counter or a new if the counter doesn't exist.
     *
     * @param name The name of the counter.
     * @return The searched counter or a new if we doesn't find one counter.
     */
    public Counter getCounterOrAdd(String name) {
        Counter counter = counters.get(name);

        if (counter == null) {
            counter = new Counter();
            counters.put(name, counter);
        }

        return counter;
    }

    @Override
    public Iterator<Entry<String, Counter>> iterator() {
        return counters.entrySet().iterator();
    }
}