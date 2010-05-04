package org.jtheque.utils;

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
 * A database exception. It seems an error who occurs when a problem appear during a database manipulation.
 *
 * @author Baptiste Wicht
 */
public final class DatabaseException extends Exception {
    private static final long serialVersionUID = -4815498919256992184L;

    /**
     * Construct a DatabaseException with a specific message and a specific cause.
     *
     * @param message The text of the exception
     * @param e       The cause of the exception
     */
    public DatabaseException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Construct a DatabaseException for a specific cause.
     *
     * @param e The cause of the exception.
     */
    public DatabaseException(Throwable e) {
        super(e);
    }
}