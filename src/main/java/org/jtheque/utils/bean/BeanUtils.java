package org.jtheque.utils.bean;

import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

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