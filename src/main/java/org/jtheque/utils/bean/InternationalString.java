package org.jtheque.utils.bean;

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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * An international string.
 *
 * @author Baptiste Wicht
 */
public final class InternationalString {
    private final Map<String, String> resources = new HashMap<String, String>(10);

    /**
     * Put a new value.
     *
     * @param language The language.
     * @param resource The message resource.
     */
    public void put(String language, String resource) {
        resources.put(language, resource);
    }

    @Override
    public String toString() {
        return resources.get(Locale.getDefault().getLanguage());
    }
}