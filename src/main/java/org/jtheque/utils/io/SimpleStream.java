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
 * A simple on demand stream.
 *
 * @author Baptiste Wicht
 */
public class SimpleStream implements OnDemandStream {
    private final InputStream stream;

    /**
     * A simple on demand stream for the given stream.
     *
     * @param stream The stream to be directly returned by the on demand stream. 
     */
    public SimpleStream(InputStream stream) {
        super();

        this.stream = stream;
    }

    @Override
    public InputStream get() {
        return stream;
    }
}
