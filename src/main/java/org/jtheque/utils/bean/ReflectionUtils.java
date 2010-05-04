package org.jtheque.utils.bean;

import org.jtheque.utils.StringUtils;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            LoggerFactory.getLogger(ReflectionUtils.class).error("The getter doesn't exists", e);
        }

        return null;
    }

    /**
     * Return the name of the setter for the specified property name.
     *
     * @param property The property name.
     *
     * @return The name of the setter for the specified property.
     */
    public static String getSetter(String property) {
        return "set" + StringUtils.setFirstLetterUpper(property);
    }

    /**
     * Return the setter method for the property of the specified bean.
     *
     * @param bean     The bean to set the property to.
     * @param property The property name.
     *
     * @return The setter Method object or null if there is no setter for this property.
     */
    public static Method getSetterMethod(Object bean, String property) {
        String setter = getSetter(property);

        for(Method m : bean.getClass().getMethods()){
            if(m.getName().equals(setter)){
                return m;
            }
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
            LoggerFactory.getLogger(ReflectionUtils.class).error("Unable to access the property getter method", e);
        } catch (InvocationTargetException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error("Problem during getter invocation", e);
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
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
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
            LoggerFactory.getLogger(ReflectionUtils.class).error("Unable to get the properties", e);

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
    
    public static Method getMethod(Class<? extends Annotation> annotationClass, Class<?> aClass) {
        for(Method method : aClass.getMethods()){
            if(method.isAnnotationPresent(annotationClass)){
                method.setAccessible(true);

                return method;
            }
        }

        return null;
    }
}
