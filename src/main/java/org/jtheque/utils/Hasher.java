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
 * An hasher algorithm enum to use with CryptoUtils.
 * 
 * @author Baptiste Wicht
 */
public enum Hasher {
	MD5 ("MD5"),
	SHA256 ("SHA-256"),
	SHA384 ("SHA-384"),
	SHA512 ("SHA-512");

	private final String algorithm;

    /**
     * Create a new Hasher with the given algorithm.
     *
     * @param algorithm The algorithm.
     */
	Hasher(String algorithm) {
		this.algorithm = algorithm;
	}

    /**
     * Return the algorithm of the hasher.
     *
     * @return The algorithm of the hasher. 
     */
	public String getAlgorithm() {
		return algorithm;
	}
}
