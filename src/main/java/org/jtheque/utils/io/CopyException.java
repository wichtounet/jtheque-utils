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