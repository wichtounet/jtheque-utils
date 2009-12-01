package org.jtheque.utils.bean;

import org.jtheque.utils.Constants;

import java.beans.PropertyDescriptor;

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
 * @author Baptiste Wicht
 */
public final class HashCodeUtils {
    private static final int NUMBER_BIT_LENGTH = 32;

    /**
     * Utility class, not instanciable.
     */
    private HashCodeUtils() {
        super();
    }

    /**
     * Generate a hash code for the bean.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean The bean.
     * @return A hash code based on all the properties of the bean.
     */
    public static int hashCode(Object bean) {
        int result = Constants.HASH_CODE_START;

        for (PropertyDescriptor property : ReflectionUtils.getProperties(bean)) {
            if (property.getReadMethod() == null) {
                continue;
            }

            Object value = ReflectionUtils.getProperty(bean, property);

            result = computeValue(result, value);
        }

        return result;
    }

    /**
     * Return the hash code of an object, using the properties in the given list.
     *
     * @param bean       The bean to generate the hash code from.
     * @param properties The properties to use to generate the hash code.
     * @return The hash code of the bean . If there is no properties, the hash code will be 17.
     */
    public static int hashCode(Object bean, String... properties) {
        int result = Constants.HASH_CODE_START;

        for (String property : properties) {
            Object value = ReflectionUtils.getPropertyValue(bean, property);

            result = computeValue(result, value);
        }

        return result;
    }

    /**
     * Compute the value with the current result.
     *
     * @param result The current hash code result.
     * @param value  The value to compute to the result.
     * @return The result computed with the value.
     */
    private static int computeValue(int result, Object value) {
        int hash;

        if (value instanceof Double) {
            Long temp = Double.doubleToLongBits((Double) value);
            hash = Constants.HASH_CODE_PRIME * result + (int) (temp ^ temp >>> NUMBER_BIT_LENGTH);
        } else if (value instanceof Long) {
            Long temp = (Long) value;
            hash = Constants.HASH_CODE_PRIME * result + (int) (temp ^ temp >>> NUMBER_BIT_LENGTH);
        } else if (value instanceof Boolean) {
            hash = Constants.HASH_CODE_PRIME * result + ((Boolean) value ? 0 : 1);
        } else if (value instanceof Float) {
            hash = Constants.HASH_CODE_PRIME * result + Float.floatToIntBits((Float) value);
        } else if (value instanceof Number) {
            hash = Constants.HASH_CODE_PRIME * result + ((Number) value).intValue();
        } else {
            hash = Constants.HASH_CODE_PRIME * result + (value == null ? 0 : value.hashCode());
        }

        return hash;
    }
}