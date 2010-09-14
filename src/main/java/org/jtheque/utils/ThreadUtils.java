package org.jtheque.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
 * An utility class to manipulate threads.
 *
 * @author Baptiste Wicht
 */
public class ThreadUtils {
    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();

    /**
     * Utility class, not instantiable.
     */
    private ThreadUtils() {
        throw new AssertionError();
    }

    /**
     * Return the number of processors of the computer.
     *
     * @return The number of processors of the computer.
     */
    public static int processors() {
        return PROCESSORS;
    }

    /**
     * Run the given runnable in a new thread.
     *
     * @param runnable The runnable to run in a new thread.
     */
    public static void inNewThread(Runnable runnable) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(runnable);

        executor.shutdown();
    }

    /**
     * Join all the threads.
     *
     * @param threads The threads to join.
     *
     * @throws InterruptedException If the thread is interrupted during joining.
     */
    public static void joinAll(Iterable<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    /**
     * Create a thread factory were all the threads are daemon threads.
     *
     * @return The thread factory.
     */
    public static ThreadFactory daemonThreadFactory() {
        return new DaemonThreadFactory();
    }

    /**
     * A ThreadFactory to create daemon threads.
     *
     * @author Baptiste Wicht
     */
    private static class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);

            t.setDaemon(true);

            return t;
        }
    }
}
