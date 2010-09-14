package org.jtheque.utils.collections;

import org.jtheque.utils.ThreadUtils;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
 * A simple cached with a timeout on each element. The eviction thread is a daemon, so there is no need to shutdown the
 * cache. This cache doesn't support null objects.
 *
 * @author Baptiste Wicht
 * @param <T> The type of object in the cache.
 */
public class SimpleTimedCache<T> {
    private final long timeout;
    private final Collection<TimedElement> elements = CollectionUtils.newConcurrentList();

    /**
     * Create a new SimpleTimedCache.
     *
     * @param timeout The timeout to evict the objects.
     */
    public SimpleTimedCache(long timeout) {
        super();

        if (timeout <= 0) {
            throw new IllegalArgumentException("The timeout is less than or equals to zero. ");
        }

        this.timeout = timeout;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, ThreadUtils.daemonThreadFactory());

        executor.scheduleAtFixedRate(new EvictionTask(), timeout, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Add the given element to the cache.
     *
     * @param element The element to add to the cache.
     *
     * @throws IllegalArgumentException If the element is null.
     */
    public void add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("The cache doesn't support null elements");
        }

        elements.add(new TimedElement(element));
    }

    /**
     * Indicate if the cache contains the given element or not.
     *
     * @param element The element to test.
     *
     * @return {@code true} if the cache contains the object otherwise {@code false}.
     */
    public boolean contains(T element) {
        for (TimedElement timedElement : elements) {
            if (timedElement.get().equals(element)) {
                return true;
            }
        }

        return false;
    }

    /**
     * A timed element.
     *
     * @author Baptiste Wicht
     */
    private final class TimedElement {
        private final T element;
        private final long time;

        /**
         * Create a new TimedElement.
         *
         * @param element The element.
         */
        private TimedElement(T element) {
            super();

            this.element = element;

            time = System.currentTimeMillis();
        }

        /**
         * Indicate if the element is too old.
         *
         * @return {@code true} if the element is too old otherwise {@code false}.
         */
        private boolean isTooOld() {
            return System.currentTimeMillis() > time + timeout;
        }

        /**
         * Return the element.
         *
         * @return The element.
         */
        private T get() {
            return element;
        }
    }

    /**
     * The task to evict the old elements.
     *
     * @author Baptiste Wicht
     */
    private class EvictionTask implements Runnable {
        @Override
        public void run() {
            for (TimedElement element : elements) {
                if (element.isTooOld()) {
                    elements.remove(element);
                }
            }
        }
    }
}