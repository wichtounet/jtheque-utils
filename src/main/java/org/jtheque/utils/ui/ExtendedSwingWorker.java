package org.jtheque.utils.ui;

import javax.swing.SwingWorker;

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
 * An extended swing worker allowing to execute something in the EDT before the current task. You must call the
 * start() method to guarantee that the before() will be executed.
 *
 * @param <T> the result type returned by this SwingWorker's doInBackground and get methods
 * @param <V>the type used for carrying out intermediate results by this SwingWorker's publish and process methods
 *
 * @author Baptiste Wicht
 */
public abstract class ExtendedSwingWorker<T, V> extends SwingWorker<T, V> {
    /**
     * Executed before the execute() of the swing worker.
     */
    protected void before() {
        //Nothing by default
    }

    /**
     * Launch the swing worker. 
     */
    public void start() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                before();
            }
        });

        execute();
    }
}