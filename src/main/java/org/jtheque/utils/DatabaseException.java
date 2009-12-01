package org.jtheque.utils;

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