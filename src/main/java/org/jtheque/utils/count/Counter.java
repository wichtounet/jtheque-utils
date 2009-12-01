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

/**
 * A counter.
 *
 * @author Baptiste Wicht
 */
public final class Counter {
    private int value;

    /**
     * Return the current value of the counter.
     *
     * @return The integer value of the counter.
     */
    public int getValue() {
        return value;
    }

    /**
     * Increment the counter of 1.
     */
    public void increment() {
        add(1);
    }

    /**
     * Decrement the counter of 1.
     */
    public void decrement() {
        remove(1);
    }

    /**
     * Add i to the counter.
     *
     * @param i The value to add.
     */
    public void add(int i) {
        value += i;
    }

    /**
     * Remove i from the counter.
     *
     * @param i The value to remove.
     */
    void remove(int i) {
        value -= i;
    }

    /**
     * Clear the counter. This method set the current counter value to 0.
     */
    public void clear() {
        value = 0;
    }
}