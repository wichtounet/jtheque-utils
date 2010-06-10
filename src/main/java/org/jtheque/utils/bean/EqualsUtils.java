package org.jtheque.utils.bean;

import java.io.File;

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
 * An utility class for equality test.
 *
 * @author Baptiste Wicht
 */
public final class EqualsUtils {
    /**
     * Construct a new DatabaseUtils. This class is an utility class, it cannot be instantiated.
     */
    private EqualsUtils() {
        super();
    }

    /**
     * Test if 2 objects are incompatible for equality test.
     *
     * @param object The first object to test.
     * @param other  The second object to test.
     *
     * @return true if the objects are incompatibles else false.
     */
    public static boolean areObjectIncompatible(Object object, Object other) {
        if (other == null) {
            return true;
        }

        return object.getClass() != other.getClass();
    }

    /**
     * Test if 2 object are not equals.
     *
     * @param object The first object to test.
     * @param other  The second object to test.
     *
     * @return true if the objects aren't equals else false.
     */
    public static boolean areNotEquals(Object object, Object other) {
        if (object == null) {
            if (other != null) {
                return true;
            }
        } else if (!object.equals(other)) {
            return true;
        }

        return false;
    }

    /**
     * Test if the two objects are equals.
     *
     * @param bean       The bean to test.
     * @param other      The other bean to test for equality with the first one.
     * @param properties The properties to compare one by one. The properties n is compared to the property n +
     *                   (properties.length / 2). This array must be pair.
     *
     * @return A boolean indicating if the two objects are equals or not.
     */
    public static boolean areEqualsDirect(Object bean, Object other, Object... properties) {
        if (bean == other) {
            return true;
        }

        if (areObjectIncompatible(bean, other)) {
            return false;
        }

        int numberOfProperties = properties.length / 2;

        for (int i = 0; i < numberOfProperties; i++) {
            Object propertyBean = properties[i];
            Object propertyOther = properties[i + numberOfProperties];

            if (propertyBean == null) {
                if (propertyOther != null) {
                    return false;
                }
            } else if (!propertyBean.equals(propertyOther)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Test if 2 files are not the sames files.
     *
     * @param file  The first file to test.
     * @param other The second file to test.
     *
     * @return true if the files are not the same else false.
     */
    public static boolean areNotSameFile(File file, File other) {
        if (file == null) {
            if (other != null) {
                return true;
            }
        } else if (!file.getAbsolutePath().equals(other.getAbsolutePath())) {
            return true;
        }

        return false;
    }
}