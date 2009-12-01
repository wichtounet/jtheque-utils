package org.jtheque.utils.collections;

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
 * An expander. It seems an objet who treat an object and return an another object
 * from the first.
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
     * @return The expander object.
     */
    T expand(S o);
}