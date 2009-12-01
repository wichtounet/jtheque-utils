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