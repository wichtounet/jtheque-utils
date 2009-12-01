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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Provide some utilities operations on collections.
 *
 * @author Baptiste Wicht
 */
public final class CollectionUtils {
    private static final Object EMPTY_LIST = Collections.unmodifiableList(new ArrayList<Object>(0));

    /**
     * Construct a new CollectionUtils. This constructor is private because all the methods are
     * static.
     */
    private CollectionUtils() {
        super();
    }

    /**
     * Perform a closure on all the objects of the collection.
     *
     * @param collection The collection of the objects.
     * @param closure    The closure.
     */
    public static <T> void forAllDo(Iterable<T> collection, Closure<T> closure) {
        for (T object : collection) {
            closure.execute(object);
        }
    }

    /**
     * Return a copy of the list.
     *
     * @param list The List to copy.
     * @param <T>  The type contained in the list.
     * @return A copy of the list.
     */
    public static <T> List<T> copyOf(Collection<T> list) {
        return new ArrayList<T>(list);
    }

    /**
     * Return a collection expanded.
     *
     * @param <T>        The type of object in the collection.
     * @param collection The collection to expand.
     * @param expander   The expander object.
     * @return The expanded collection.
     */
    public static <S, T> Collection<T> expand(Collection<S> collection, Expander<S, T> expander) {
        Collection<T> expanded = new ArrayList<T>(collection.size());

        for (S o : collection) {
            expanded.add(expander.expand(o));
        }

        return expanded;
    }

    /**
     * Filter a collection with a filter.
     *
     * @param collection The collection to filter.
     * @param filter     The filter.
     * @param <T>        The type of object in the collection.
     */
    public static <T> void filter(Collection<T> collection, Filter<T> filter) {
        Iterator<T> i = collection.iterator();

        while (i.hasNext()) {
            if (!filter.accept(i.next())) {
                i.remove();
            }
        }
    }

    /**
     * Reverse the order of a map. This method provide correct result only for map who retain the insertion order.
     *
     * @param map The map to reverse.
     * @param <T> The Key type.
     * @param <K> The value type.
     */
    public static <T, K> void reverse(Map<T, K> map) {
        List<Entry<T, K>> entries = new ArrayList<Entry<T, K>>(map.entrySet());

        map.clear();

        for (int i = entries.size() - 1; i >= 0; i--) {
            Entry<T, K> entry = entries.get(i);

            map.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Reverse a list.
     *
     * @param list The list to reverse.
     */
    public static void reverse(List<?> list) {
        Collections.reverse(list);
    }

    /**
     * Sort a list.
     *
     * @param list       The list to sort.
     * @param comparator The comparator to use to sort the list.
     * @param <T>        The type of object in the collection.
     */
    public static <T> void sort(List<T> list, Comparator<T> comparator) {
        Collections.sort(list, comparator);
    }

    /**
     * Return an empty list. This list is unmodifiable.
     *
     * @param <T> The Type of object to store in the list.
     * @return The empty list.
     */
    public static <T> List<T> emptyList() {
        return (List<T>) EMPTY_LIST;
    }

    /**
     * Move the iterator to the first element.
     *
     * @param iterator The list iterator.
     * @param <T>      The type of object stored in the iterator.
     */
    public static <T> void goToFirst(ListIterator<T> iterator) {
        while (iterator.hasPrevious()) {
            iterator.previous();
        }
    }

    /**
     * Move the iterator to the last element.
     *
     * @param iterator The iterator.
     * @param <T>      The type of object stored in the iterator.
     */
    public static <T> void goToLast(Iterator<T> iterator) {
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    /**
     * Convert the enumeration to a collection.
     *
     * @param enumeration The enumeration to convert to Collection.
     * @param <T>         The type of object stored in the enumeration.
     * @return A Collection containing all the elements of the enumeration.
     */
    public static <T> Collection<T> toCollection(Enumeration<T> enumeration) {
        Collection<T> collection = new ArrayList<T>(25);

        while (enumeration.hasMoreElements()) {
            collection.add(enumeration.nextElement());
        }

        return collection;
    }

    /**
     * Return the first element of the collection.
     *
     * @param collection The collection.
     * @param <T>        The objects stored in the collection.
     * @return The first element of the collection or null if the collection is empty.
     */
    public static <T> T first(Iterable<T> collection) {
        return collection.iterator().next();
    }
}