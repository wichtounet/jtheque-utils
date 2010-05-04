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

public final class BeanUtils {
    /**
     * Utility class, not instanciable.
     */
    private BeanUtils() {
        super();
    }

    /**
     * Create a quick memento using the specified fields.
     *
     * @param bean The bean to create the memento from.
     * @param fields The fields to use.
     *
     * @param <T> The type of data.
     *
     * @return A memento of the bean.
     */
    public static <T> T createQuickMemento(T bean, String... fields) {
        T instance = null;

        try {
            instance = (T) bean.getClass().newInstance();

            for (String field : fields) {
                Object value = ReflectionUtils.getPropertyValue(bean, field);

                ReflectionUtils.getSetterMethod(instance, field).invoke(instance, value);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }

        return instance;
    }

    /**
     * Restore the quick memento to the bean.
     *
     * @param bean The bean to restore.
     * @param memento The memento to restore.
     * @param fields The fields to use.
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

    public static void set(Object bean, String field, Object value){
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

    public static <T> T get(Object bean, String field) {
        try {
            Field f = bean.getClass().getDeclaredField(field);
            f.setAccessible(true);
            return (T) f.get(bean);
        } catch (NoSuchFieldException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }

        return null;
    }

    public static <T> T getStatic(Class<?> aClass, String field) {
        try {
            Field f = aClass.getDeclaredField(field);
            f.setAccessible(true);
            return (T) f.get(null);
        } catch (NoSuchFieldException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }

        return null;
    }
}