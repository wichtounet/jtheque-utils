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

        return fileName.endsWith(acceptedExtensions.get(acceptedExtensions.size() - 1));
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