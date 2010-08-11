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

public class SimpleTimedCache<T> {
    private final long timeout;
    private final Collection<TimedElement> elements = CollectionUtils.newConcurrentList();

    public SimpleTimedCache(long timeout) {
        super();

        if(timeout <= 0){
            throw new IllegalArgumentException("The timeout is less than or equals to zero. ");
        }

        this.timeout = timeout;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, ThreadUtils.daemonThreadFactory());

        executor.scheduleAtFixedRate(new EvictionTask(), timeout, timeout, TimeUnit.MILLISECONDS);
    }

    public void add(T element){
        if(element == null){
            throw new IllegalArgumentException("The cache doesn't support null elements");
        }

        elements.add(new TimedElement(element, System.currentTimeMillis()));
    }

    public boolean contains(T element){
        for(TimedElement timedElement : elements){
            if(timedElement.get().equals(element)){
                return true;
            }
        }

        return false;
    }

    private final class TimedElement {
        private final T element;
        private final long time;

        private TimedElement(T element, long time) {
            this.element = element;
            this.time = time;
        }

        private boolean isTooOld(){
            return System.currentTimeMillis() > time + timeout;
        }

        private T get(){
            return element;
        }
    }

    private class EvictionTask implements Runnable {
        @Override
        public void run() {
            for(TimedElement element : elements){
                if(element.isTooOld()){
                    elements.remove(element);
                }
            }
        }
    }
}