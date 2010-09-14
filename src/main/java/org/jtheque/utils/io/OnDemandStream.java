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

import java.io.InputStream;

/**
 * A stream on demand. The stream must be opened only on the get() method invocation.
 *
 * @author Baptiste Wicht
 */
public interface OnDemandStream {
    /**
     * Return the InputStream. Create the stream if it has not been created before.
     *
     * @return The input stream. 
     */
    InputStream get();
}
