package org.jtheque.utils;

import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
 * Utility class for cryptographic operations.
 *
 * @author Baptiste Wicht
 */
public final class CryptoUtils {
    /**
     * Utility class, not instantiable.
     */
    private CryptoUtils() {
        throw new AssertionError();
    }

    /**
     * Hash the string with the given algorithm.
     *
     * @param message The message to hash.
     * @param hasher  The hasher to use.
     *
     * @return The encrypted key.
     */
    public static String hashMessage(String message, Hasher hasher) {
        String encoded = "";
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(hasher.getAlgorithm());

            md.update(message.getBytes());

            encoded = new String(md.digest());
        } catch (NoSuchAlgorithmException e) {
            LoggerFactory.getLogger(CryptoUtils.class).error("Unable to encrypt message " + e);
        } finally {
            if (md != null) {
                md.reset();
            }
        }

        return encoded;
    }
}