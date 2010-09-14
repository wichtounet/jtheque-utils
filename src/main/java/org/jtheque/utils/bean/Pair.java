package org.jtheque.utils.bean;

import org.jtheque.utils.annotations.Immutable;

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
 * A simple pair of object.
 * 
 * @param <A> The type of the first object.
 * @param <B> The type of the second object.
 *
 * @author Baptiste Wicht
 */
@Immutable
public class Pair<A, B> {
    private final A a;
    private final B b;

    /**
     * Create a Pair of object.
     *
     * @param a The first object.
     * @param b The second object.
     */
    public Pair(A a, B b) {
        super();

        this.a = a;
        this.b = b;
    }

    /**
     * Return the first object.
     *
     * @return The first object.
     */
    public A getA() {
        return a;
    }

    /**
     * Return the second object.
     *
     * @return The second object.
     */
    public B getB() {
        return b;
    }
}
