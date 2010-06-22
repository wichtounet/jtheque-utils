package org.jtheque.utils.bean;

import org.jtheque.utils.StringUtils;

import java.util.regex.Pattern;

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
     *
     * @return An array containing at his index 0 the name of the person and at his index 1 the first name of the
     *         person.
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
