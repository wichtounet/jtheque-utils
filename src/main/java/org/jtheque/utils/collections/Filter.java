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
 * A collection filter.
 *
 * @author Baptiste Wicht
 */
public interface Filter<T> {
    /**
     * Indicate if we accept the object or not.
     *
     * @param object The object to test.
     * @return true if we accept the object else false.
     */
    boolean accept(T object);
}
