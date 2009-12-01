package org.jtheque.utils.bean;

import org.apache.commons.logging.LogFactory;
import org.jtheque.utils.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
 * An utility class for Reflection.
 *
 * @author Baptiste Wicht
 */
public final class ReflectionUtils {
    private static final PropertyDescriptor[] EMPTY_PROPERTY_DESCRIPTOR_ARRAY = {};

    /**
     * Utility class, not instanciable.
     */
    private ReflectionUtils() {
        super();
    }

    /**
     * Return the name of the getter for the specified property name.
     *
     * @param property The property name.
     * @return The name of the getter for the specified property.
     */
    public static String getGetter(String property) {
        return "get" + StringUtils.setFirstLetterUpper(property);
    }

    /**
     * Return the getter method for the property of the specified bean.
     *
     * @param bean     The bean to get the property from.
     * @param property The property name.
     * @return The getter Method object or null if there is no getter for this property.
     */
    public static Method getGetterMethod(Object bean, String property) {
        try {
            String getter = getGetter(property);

            return bean.getClass().getMethod(getter);
        } catch (NoSuchMethodException e) {
            LogFactory.getLog(ReflectionUtils.class).error("The getter doesn't exists", e);
        }

        return null;
    }

    /**
     * Return the value of the property of the specified bean.
     *
     * @param bean     The bean to get the value from.
     * @param property The property name.
     * @return The value of the property or null if there is no getter method.
     */
    public static Object getPropertyValue(Object bean, String property) {
        try {
            Method m = getGetterMethod(bean, property);

            return m.invoke(bean);
        } catch (IllegalAccessException e) {
            LogFactory.getLog(ReflectionUtils.class).error("Unable to access the property getter method", e);
        } catch (InvocationTargetException e) {
            LogFactory.getLog(ReflectionUtils.class).error("Problem during getter invocation", e);
        }

        return null;
    }

    /**
     * Return the value of a property.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean     The bean to get the property value from.
     * @param property The property.
     * @return the value of the property.
     */
    public static Object getProperty(Object bean, PropertyDescriptor property) {
        Method m = property.getReadMethod();

        Object o = null;

        try {
            o = m.invoke(bean);
        } catch (IllegalAccessException e) {
            LogFactory.getLog(ReflectionUtils.class).error(e);
        } catch (InvocationTargetException e) {
            LogFactory.getLog(ReflectionUtils.class).error(e);
        }

        return o;
    }

    /**
     * Return all the properties of a bean. The results are cached using the Introspector class.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean The bean to extract the properties from.
     * @return An array containing all the properties of the bean or an empty array if there was an error during the
     *         property retrieving process.
     */
    public static PropertyDescriptor[] getProperties(Object bean) {
        try {
            BeanInfo info = Introspector.getBeanInfo(bean.getClass(), Object.class);

            return info.getPropertyDescriptors();
        } catch (IntrospectionException e) {
            LogFactory.getLog(ReflectionUtils.class).error("Unable to get the properties", e);

            return EMPTY_PROPERTY_DESCRIPTOR_ARRAY;
        }
    }

    /**
     * Return the value of a property.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean     The bean to get the property value from.
     * @param property The property.
     * @return the value of the property.
     */
    public static Object getProperty(Object bean, String property) {
        for (PropertyDescriptor p : getProperties(bean)) {
            if (p.getName().equals(property)) {
                return getProperty(bean, p);
            }
        }

        return null;
    }
}
