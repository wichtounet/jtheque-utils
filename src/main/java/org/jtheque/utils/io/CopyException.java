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
 * An exception during the copy process.
 *
 * @author Baptiste Wicht
 */
public final class CopyException extends FileException {
    private static final long serialVersionUID = -2917421221194430303L;

    /**
     * Construct a new CopyException with a specific message.
     *
     * @param message The text of the exception
     */
    CopyException(String message) {
        super(message);
    }

    /**
     * Construct a new CopyException with a specific message and exception.
     *
     * @param message The text of the exception
     * @param cause   The cause of the exception
     */
    public CopyException(String message, Throwable cause) {
        super(message, cause);
    }
}