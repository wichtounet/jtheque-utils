package org.jtheque.utils.io;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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