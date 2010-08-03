package org.jtheque.utils.collections;

import org.jtheque.utils.annotations.GuardedBy;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;

import java.lang.ref.WeakReference;
import java.util.EventListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

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
 * A simple thread safe weak listener list. All the listeners are stored using WeakReference. The list use
 * a CopyOnWriteArrayList to make sure that the iteration over the listeners is thread safe. The iterator
 * does not permit modifications. 
 *
 * @param <T> The type of listener to store in the list.
 *
 * @see java.lang.ref.WeakReference
 * @see java.util.concurrent.CopyOnWriteArrayList
 */
@ThreadSafe
public class WeakEventListenerList<T extends EventListener> implements Iterable<T> {
    @GuardedInternally
    @GuardedBy("weakListeners")
    private final CopyOnWriteArrayList<WeakReference<T>> weakListeners = new CopyOnWriteArrayList<WeakReference<T>>();
    
    private WeakEventListenerList() {
        super();
    }

    public static <T extends EventListener> WeakEventListenerList<T> create(){
        return new WeakEventListenerList<T>();
    }

    /**
     * Add the listener to the list. This operation is provided in O(1) + the cost of the add() operation of the
     * CopyOnWriteArrayList.
     *
     * @param listener The listener to add.
     */
    public void add(T listener){
        if(listener == null){
            return;
        }

        synchronized (weakListeners) {
            weakListeners.add(new WeakReference<T>(listener));
        }
    }

    /**
     * Add the listener to the list. This operation is provided in O(n) + the cost of the n remove() operation of the
     * CopyOnWriteArrayList.
     *
     * The weak references are cleaned during the remove operation. 
     *
     * @param listener The listener to remove.
     */
    public void remove(T listener) {
        if (listener == null) {
            return;
        }
        
        synchronized (weakListeners){
            for(int i = 0; i < weakListeners.size(); i++){
                if(weakListeners.get(i).get() == null || weakListeners.get(i).get() == listener){
                    weakListeners.remove(i);
                }
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        ListenersIterator iterator;

        synchronized (weakListeners) {
            for (int i = 0; i < weakListeners.size(); i++) {
                if (weakListeners.get(i).get() == null) {
                    weakListeners.remove(i);
                }
            }

            iterator = new ListenersIterator(weakListeners.iterator());
        }

        return iterator;
    }

    private class ListenersIterator implements Iterator<T> {
        private final Iterator<WeakReference<T>> weakReferenceIterator;

        private ListenersIterator(Iterator<WeakReference<T>> weakReferenceIterator) {
            super();

            this.weakReferenceIterator = weakReferenceIterator;
        }

        @Override
        public boolean hasNext() {
            return weakReferenceIterator.hasNext();
        }

        @Override
        public T next() {
            return weakReferenceIterator.next().get();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot be modified during iteration");
        }
    }
}