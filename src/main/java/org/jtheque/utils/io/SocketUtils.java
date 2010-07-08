package org.jtheque.utils.io;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
 * An utility class for the Socket use.
 *
 * @author Baptiste Wicht
 */
public final class SocketUtils {
    /**
     * Construct a new SocketUtils. This class is an utility class, it cannot be instantiated.
     */
    private SocketUtils() {
        throw new AssertionError();
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
