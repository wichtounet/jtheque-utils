package org.jtheque.utils.bean;

import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

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