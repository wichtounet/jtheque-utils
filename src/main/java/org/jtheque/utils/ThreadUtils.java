package org.jtheque.utils;

import org.slf4j.LoggerFactory;

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

public class ThreadUtils {
    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static int processors(){
        return PROCESSORS;
    }

    public static void joinAll(Iterable<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                LoggerFactory.getLogger(ThreadUtils.class).error(e.getMessage(), e);
            }
        }
    }
}
