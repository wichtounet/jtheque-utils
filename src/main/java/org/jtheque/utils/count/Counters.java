package org.jtheque.utils.count;

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