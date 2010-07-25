package org.jtheque.utils.bean;

import org.jtheque.utils.collections.CollectionUtils;

import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

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
 * Utility class to work with beans.
 *
 * @author Baptiste Wicht
 */
public final class BeanUtils {
    /**
     * Utility class, not instanciable.
     */
    private BeanUtils() {
        throw new AssertionError();
    }

    /**
     * Create a memento for the bean. A memento is a clone of all the properties of the bean.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean The bean to create the memento from.
     * @param type The type of bean.
     * @param <T>  The class of the bean.
     *
     * @return The memento.
     */
    public static <T> T createMemento(T bean, Class<T> type) {
        T instance = null;

        try {
            instance = type.newInstance();

            for (PropertyDescriptor property : ReflectionUtils.getProperties(bean)) {
                if (property.getReadMethod() == null || property.getWriteMethod() == null) {
                    continue;
                }

                Object value = property.getReadMethod().invoke(bean);

                setValue(instance, property, value);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(BeanUtils.class).error(e.getMessage(), e);
        }

        return instance;
    }

    /**
     * Restore the state of the memento. It seems to copy all the properties values from the memento to the bean.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean    The bean.
     * @param memento The memento.
     */
    public static void restoreMemento(Object bean, Object memento) {
        try {
            for (PropertyDescriptor property : ReflectionUtils.getProperties(bean)) {
                if (property.getReadMethod() == null || property.getWriteMethod() == null) {
                    continue;
                }

                Object value = ReflectionUtils.getProperty(memento, property);

                setValue(bean, property, value);
            }
        } catch (InvocationTargetException e) {
            LoggerFactory.getLogger(BeanUtils.class).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(BeanUtils.class).error(e.getMessage(), e);
        }
    }

    /**
     * Test if two object are equals referring to a certain list of properties.
     *
     * @param bean       The first bean.
     * @param other      The other bean.
     * @param properties The properties to use.
     *
     * @return <code>true</code> if the two objects are equals else <code>false</code>.
     */
    public static boolean areEquals(Object bean, Object other, String... properties) {
        if (bean == other) {
            return true;
        }

        if (EqualsUtils.areObjectIncompatible(bean, other)) {
            return false;
        }

        for (String property : properties) {
            Object propertyBean = getPropertyQuickly(bean, property);
            Object propertyOther = getPropertyQuickly(other, property);

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
     * Return the value of the property. This method parse the entire class, but use a cache, so it's better to use it
     * when we have to access a lot of times the same class.
     *
     * @param bean     The bean to get the property value from.
     * @param property The property.
     *
     * @return the value of the property.
     */
    public static Object getProperty(Object bean, String property) {
        return ReflectionUtils.getProperty(bean, property);
    }

    /**
     * Return the value of the property. This method doesn't parse the entire class, so it's quicker than getProperty()
     * but it doesn't use cache so if you've to use it many times, use getProperty().
     *
     * @param bean     The bean to get the property value from.
     * @param property The property.
     *
     * @return the value of the property.
     */
    public static Object getPropertyQuickly(Object bean, String property) {
        return ReflectionUtils.getPropertyValue(bean, property);
    }

    /**
     * Generate a toString() String based on all the properties of the bean.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean The bean.
     *
     * @return A String representation of all the properties of the bean.
     */
    public static String toString(Object bean) {
        StringBuilder builder = new StringBuilder(bean.getClass().getSimpleName());

        builder.append('{');

        for (PropertyDescriptor property : ReflectionUtils.getProperties(bean)) {
            if (property.getReadMethod() == null) {
                continue;
            }

            builder.append(property.getName()).append('=');
            builder.append(ReflectionUtils.getProperty(bean, property));
            builder.append(", ");
        }

        builder.delete(builder.lastIndexOf(", "), builder.lastIndexOf(", ") + 2);

        builder.append('}');

        return builder.toString();
    }

    /**
     * Set the value to the bean on the bean.
     *
     * @param bean     The bean to edit.
     * @param property The property to set.
     * @param value    The value to set to the property.
     *
     * @throws IllegalAccessException    If the property cannot be accessed.
     * @throws InvocationTargetException If there is a problem during the setting process.
     */
    private static void setValue(Object bean, PropertyDescriptor property, Object value) throws IllegalAccessException, InvocationTargetException {
        if (value != null && List.class.isAssignableFrom(value.getClass())) {
            property.getWriteMethod().invoke(bean, CollectionUtils.copyOf((Collection<?>) value));
        } else {
            property.getWriteMethod().invoke(bean, value);
        }
    }

    /**
     * Create a quick memento using the specified fields.
     *
     * @param bean   The bean to create the memento from.
     * @param type   The type of data.
     * @param fields The fields to use.
     * @param <T>    The type of data.
     *
     * @return A memento of the bean.
     */
    public static <T> T createQuickMemento(T bean, Class<T> type, String... fields) {
        T instance = null;

        try {
            instance = type.cast(bean.getClass().newInstance());

            restoreQuickMemento(instance, bean, fields);
        } catch (Exception e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }

        return instance;
    }

    /**
     * Restore the quick memento to the bean.
     *
     * @param bean    The bean to restore.
     * @param memento The memento to restore.
     * @param fields  The fields to use.
     */
    public static void restoreQuickMemento(Object bean, Object memento, String... fields) {
        try {
            for (String field : fields) {
                Object value = ReflectionUtils.getPropertyValue(memento, field);

                ReflectionUtils.getSetterMethod(bean, field).invoke(bean, value);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }
    }

    /**
     * Modify the value of the bean.
     *
     * @param bean  The bean to modify.
     * @param field The field to modify.
     * @param value The value to set to the field.
     */
    public static void set(Object bean, String field, Object value) {
        try {
            Field f = bean.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(bean, value);
        } catch (NoSuchFieldException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }
    }

    /**
     * Return the value of the field of the bean.
     *
     * @param bean  The bean to get the value for.
     * @param type  The type of the field.
     * @param field The field to get the value for.
     * @param <T>   The type of the field.
     *
     * @return The value of the field else null if an error occurs.
     */
    public static <T> T get(Object bean, Class<T> type, String field) {
        try {
            Field f = bean.getClass().getDeclaredField(field);
            f.setAccessible(true);
            return type.cast(f.get(bean));
        } catch (NoSuchFieldException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Return the value of the static field of the class.
     *
     * @param type The class to get the static field for.
     * @param field  The field to get the value for.
     * @param <T>    The type of the field.
     *
     * @return The value of the field else null if an error occurs.
     */
    public static <T> T getStatic(Class<T> type, String field) {
        return get(null, type, field);
    }
}