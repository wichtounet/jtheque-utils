package org.jtheque.utils.collections;

import java.util.Comparator;

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
 * Comparator that uses multiple comparators to make chained comparisons. If the first comparator indicates that the 2
 * objects are equals, we use the next comparator until we've used all the comparators. If all the comparators indicates
 * that the two objects are equals, then this comparator return that the two objects are equals. The comparator are
 * using in the order they are given.
 *
 * @author Baptiste Wicht
 */
public class MultipleComparator<T> implements Comparator<T> {
    private final Comparator<T>[] comparators;

    /**
     * Construct a new MultipleComparator using the given comparators.
     *
     * @param comparators The comparators to use to make the comparison. They will used in the given order.
     */
    public MultipleComparator(Comparator<T>... comparators) {
        super();

        this.comparators = comparators;
    }

    @Override
    public int compare(T o1, T o2) {
        for (Comparator<T> comparator : comparators) {
            int compare = comparator.compare(o1, o2);

            if (compare != 0) {
                return compare;
            }
        }

        return 0;
    }
}