package org.jtheque.utils.ui.edt;

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
 * A task.
 *
 * @author Baptiste Wicht
 */
public abstract class Task<T> extends SimpleTask {
    private T result;

    /**
     * Set the result of the task.
     *
     * @param result The result of the task.
     */
    public final void setResult(T result) {
        this.result = result;
    }

    /**
     * Return the result of the task.
     *
     * @return The result of the task.
     */
    public final T getResult() {
        return result;
    }
}