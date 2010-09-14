package org.jtheque.utils.bean;

import org.jtheque.utils.collections.CollectionUtils;

import java.util.List;

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
 * A simple utility class to compare to object to equality. This class uses fluent methods, it seems that they
 * return the current instance.
 *
 * @author Baptiste Wicht
 */
public class EqualsBuilder {
    private final Object o1;
    private final Object o2;

    private final List<Object> fields1 = CollectionUtils.newList(5);
    private final List<Object> fields2 = CollectionUtils.newList(5);

    /**
     * Construct a new EqualsBuilder.
     *
     * @param o1 The first object to compare.
     * @param o2 The second object to compare.
     */
    private EqualsBuilder(Object o1, Object o2) {
        super();

        this.o1 = o1;
        this.o2 = o2;
    }

    /**
     * Create a new builder for the two objects.
     *
     * @param o1 The first object to compare.
     * @param o2 The second object to compare.
     *
     * @return The builder for the two objects.
     */
    public static EqualsBuilder newBuilder(Object o1, Object o2) {
        return new EqualsBuilder(o1, o2);
    }

    /**
     * Add a field to the builder.
     *
     * @param f1 The field for the first object.
     * @param f2 The field for the second object.
     *
     * @return The builder.
     */
    public EqualsBuilder addField(Object f1, Object f2) {
        fields1.add(f1);
        fields2.add(f2);

        return this;
    }

    /**
     * Test if the two instances are equals or not.
     *
     * @return {@code true} if the two instances are equals {@code false} otherwise. 
     */
    public boolean areEquals() {
        if (o1 == o2) {
            return true;
        }

        if (EqualsUtils.areObjectIncompatible(o1, o2)) {
            return false;
        }

        for (int i = 0; i < fields1.size(); i++) {
            if (EqualsUtils.areNotEquals(fields1.get(i), fields2.get(i))) {
                return false;
            }
        }

        return true;
    }
}