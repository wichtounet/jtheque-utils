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
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * A simple file filter.
 *
 * @author Baptiste Wicht
 */
public class SimpleFilter implements FileFilter {
    private final String description;
    private final List<String> acceptedExtensions = new ArrayList<String>(5);

    /**
     * Construct a new FilterSimple.
     *
     * @param description The description of the filter.
     * @param extensions  The extension accepted by the filter, in comma-separated format.
     */
    public SimpleFilter(String description, String extensions) {
        super();

        if (description == null || extensions == null) {
            throw new IllegalArgumentException("Neither the description or the extension list can be null. ");
        }

        this.description = description;

        StringTokenizer token = new StringTokenizer(extensions, ",");

        while (token.hasMoreTokens()) {
            acceptedExtensions.add(token.nextToken());
        }
    }

    @Override
    public final boolean accept(File file) {
        String fileName = file.getName().toLowerCase(Locale.getDefault());

        if (file.isDirectory()) {
            return true;
        }

        for (String extension : acceptedExtensions) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return the description of the filter.
     *
     * @return The description of the filter.
     */
    public final String getDescription() {
        return description;
    }
}