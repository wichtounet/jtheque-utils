package org.jtheque.utils.bean;

import org.jtheque.utils.StringUtils;

import java.util.regex.Pattern;

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

/**
 * An utility class for specific data.
 *
 * @author Baptiste Wicht
 */
public final class DataUtils {
    private static final Pattern SPACE_PATTERN = Pattern.compile(" ");

    /**
     * Utility class, not instanciable.
     */
    private DataUtils() {
        super();
    }

    /**
     * Return the name and the first name of the complete name of a person.
     *
     * @param name The name of a person.
     * @return An array containing at his index 0 the name of the person and at his index 1 the first name of the person.
     */
    public static String[] getNameAndFirstName(CharSequence name) {
        if (StringUtils.isEmpty(name)) {
            return new String[]{"", ""};
        }

        String[] nameFirstName = SPACE_PATTERN.split(name);

        StringBuilder nom = new StringBuilder(25);

        for (int i = 1; i < nameFirstName.length; ++i) {
            nom.append(StringUtils.setFirstLetterOnlyUpper(nameFirstName[i])).append(' ');
        }

        StringUtils.removeLastSpace(nom);

        return new String[]{nom.toString(), StringUtils.setFirstLetterOnlyUpper(nameFirstName[0])};
    }
}
