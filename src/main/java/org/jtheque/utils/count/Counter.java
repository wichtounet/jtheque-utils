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