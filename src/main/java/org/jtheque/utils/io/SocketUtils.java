package org.jtheque.utils.io;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
 * An utility class for the Socket use.
 *
 * @author Baptiste Wicht
 */
public final class SocketUtils {
    /**
     * Construct a new SocketUtils. This class is an utility class, it cannot be instantiated.
     */
    private SocketUtils() {
        super();
    }

    /**
     * Close the socket.
     *
     * @param socket The socket set to close.
     */
    public static void close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                LoggerFactory.getLogger(SocketUtils.class).error(e.getMessage(), e);
            }
        }
    }

    /**
     * Close the server socket.
     *
     * @param socket The socket to close.
     */
    public static void close(ServerSocket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                LoggerFactory.getLogger(SocketUtils.class).error(e.getMessage(), e);
            }
        }
    }
}
