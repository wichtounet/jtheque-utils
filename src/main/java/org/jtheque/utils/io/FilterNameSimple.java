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

import java.io.File;
import java.io.FilenameFilter;

/**
 * A filter for file chooser. This filter operate on the file name.
 *
 * @author Baptiste Wicht
 */
final class FilterNameSimple implements FilenameFilter {
    private final String extension;

    /**
     * Construct a new <code>FilterNameSimple</code> for a specific extension.
     *
     * @param extension The extension we search.
     */
    FilterNameSimple(String extension) {
        super();

        this.extension = extension;
    }

    @Override
    public boolean accept(File file, String name) {
        return name.endsWith(extension);
    }
}