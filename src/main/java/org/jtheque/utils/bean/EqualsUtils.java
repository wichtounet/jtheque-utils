package org.jtheque.utils.bean;

import java.io.File;

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
	 * @param bean The bean to test.
	 * @param other The other bean to test for equality with the first one.
	 * @param properties The properties to compare one by one. The properties n is compared to the property
	 * n + (properties.length / 2). This array must be pair.
	 *
	 * @return A boolean indicating if the two objects are equals or not.
	 */
	public static boolean areEqualsDirect(Object bean, Object other, Object... properties){
		if (bean == other){
			return true;
		}

		if (EqualsUtils.areObjectIncompatible(bean, other)){
			return false;
		}

        int numberOfProperties = properties.length / 2;

		for (int i = 0; i < numberOfProperties; i++){
			Object propertyBean = properties[i];
			Object propertyOther = properties[i + numberOfProperties];

			if (propertyBean == null){
				if (propertyOther != null){
					return false;
				}
			} else if (!propertyBean.equals(propertyOther)){
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