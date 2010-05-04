package org.jtheque.utils.bean;

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

import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * @author Baptiste Wicht
 */
public class ReflectionUtilsTest {
    @Test
    public void getGetter() {
        String getter1 = ReflectionUtils.getGetter("test");
        String getter2 = ReflectionUtils.getGetter("Test");

        assertEquals(getter1, "getTest");
        assertEquals(getter2, "getTest");
    }

    @Test
    public void getGetterMethod() {
        Method m = ReflectionUtils.getGetterMethod(new TestClass(), "test");

        assertNotNull(m);
        assertEquals(m.getName(), "getTest");
    }

    @Test
    public void getPropertyValue() {
        Object o = ReflectionUtils.getPropertyValue(new TestClass(), "test");

        assertNotNull(o);
        assertEquals(o, "Test");
    }

    @Test
    public void getProperty() {
        Object o = ReflectionUtils.getProperty(new TestClass(), "test");

        assertNotNull(o);
        assertEquals(o, "Test");
    }


    @Test
    public void testGetProperties() {
        TestClass2 instance = new TestClass2();

        PropertyDescriptor[] properties = ReflectionUtils.getProperties(instance);

        assertEquals(properties.length, 3);

        for (PropertyDescriptor p : properties) {
            if (!"property1".equals(p.getName()) && !"property2".equals(p.getName()) && !"property3".equals(p.getName())) {
                fail("Incorrect property " + p.getName());
            }

            assertNotNull(p.getWriteMethod());
            assertNotNull(p.getReadMethod());
        }
    }

    @Test
    public void testGetPropertyValues() {
        TestClass2 instance = new TestClass2();

        Object property1 = ReflectionUtils.getPropertyValue(instance, "property1");

        assertTrue(property1 instanceof Integer);
        assertTrue(property1.equals(0));

        instance.setProperty1(33);

        property1 = ReflectionUtils.getPropertyValue(instance, "property1");

        assertTrue(property1.equals(33));
    }

    @Test
    public void testGetPropertyMethods() {
        TestClass2 instance = new TestClass2();

        try {
            PropertyDescriptor[] properties = ReflectionUtils.getProperties(instance);

            for (PropertyDescriptor p : properties) {
                if ("property1".equals(p.getName())) {
                    p.getWriteMethod().invoke(instance, 3);

                    assertEquals(instance.getProperty1(), 3);

                    Object result = p.getReadMethod().invoke(instance);

                    assertEquals(result, 3);
                }
            }
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }
    }

    public class TestClass {
        public String getTest() {
            return "Test";
        }
    }

    public static final class TestClass2 {
        private int property1;
        private long property2;
        private String property3;

        public int getProperty1() {
            return property1;
        }

        public void setProperty1(int property1) {
            this.property1 = property1;
        }

        public long getProperty2() {
            return property2;
        }

        public void setProperty2(long property2) {
            this.property2 = property2;
        }

        public String getProperty3() {
            return property3;
        }

        public void setProperty3(String property3) {
            this.property3 = property3;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o != null && getClass() == o.getClass()) {

                TestClass2 that = (TestClass2) o;

                if (property1 != that.property1) {
                    return false;
                }

                if (property2 != that.property2) {
                    return false;
                }

                return !(property3 != null ? !property3.equals(that.property3) : that.property3 != null);
            } else {
                return false;
            }
        }
    }
}
