package org.jtheque.utils.collections;

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
 * An expander. It seems an objet who treat an object and return an another object from the first.
 *
 * @author wichtounet
 * @param <S> The class of objects in collections.
 * @param <T> The extended class
 */
public interface Expander<S, T> {
    /**
     * Expand the object to an another.
     *
     * @param o The object.
     *
     * @return The expander object.
     */
    T expand(S o);
}