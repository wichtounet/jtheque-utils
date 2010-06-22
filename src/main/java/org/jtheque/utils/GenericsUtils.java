package org.jtheque.utils;

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

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A generic class utils.
 *
 * @author Ian Robertson
 * @author Baptiste Wicht
 */
public final class GenericsUtils {
    /**
     * Construct a new GenericsUtils.
     */
    private GenericsUtils() {
        super();
    }

    /**
     * Get the actual type arguments a child class has used to extend a generic base class.
     *
     * @param baseClass  the base class
     * @param childClass the child class
     * @param <T>        The type.
     *
     * @return a list of the raw classes for the actual type arguments.
     */
    public static <T> Collection<Class<?>> getTypeArguments(Class<T> baseClass, Type childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>(10);
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit baseClass
        while (!getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just
                // keep going.
                type = ((Class<?>) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class<?>) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();

                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        return determineRawClasses(resolvedTypes, type);
    }

    /**
     * Determine the raw classes of the resolved types.
     *
     * @param resolvedTypes The resolved types.
     * @param type          The type.
     *
     * @return The raw types.
     */
    private static Collection<Class<?>> determineRawClasses(Map<Type, Type> resolvedTypes, Type type) {
        Type[] actualTypeArguments = type instanceof Class ? ((GenericDeclaration) type).getTypeParameters() : ((ParameterizedType) type).getActualTypeArguments();

        Collection<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>(actualTypeArguments.length);
        // resolve types by chasing down type variables.
        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }

            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }

    /**
     * Get the underlying class for a type, or null if the type is a variable type.
     *
     * @param type the type
     *
     * @return the underlying class
     */
    private static Class<?> getClass(Type type) {
        Class<?> typeClass = null;

        if (type instanceof Class) {
            typeClass = (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            typeClass = getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);

            if (componentClass != null) {
                typeClass = Array.newInstance(componentClass, 0).getClass();
            }
        }

        return typeClass;
    }
}