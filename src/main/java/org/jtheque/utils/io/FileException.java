package org.jtheque.utils.io;

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
 * A file Exception.
 *
 * @author Baptiste Wicht.
 */
public class FileException extends Exception {
    private static final long serialVersionUID = -8945357520443366528L;

    /**
     * Construct a new FileException with a specific message.
     *
     * @param message The message of the exception.
     */
    public FileException(String message) {
        super(message);
    }

    /**
     * Construct a new FileException with a specific message and a cause.
     *
     * @param message The message of the exception.
     * @param cause   The cause of the exception.
     */
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a FileException with a specific cause.
     *
     * @param e The cause of the exception.
     */
    public FileException(Throwable e) {
        super(e);
    }
}