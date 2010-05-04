package org.jtheque.utils.bean;

import org.jtheque.utils.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
 * @author Baptiste Wicht
 */
public class HashCodeUtilsTest {
    @Test
    public void testHashCode() {
        TestClass instance = new TestClass();

        assertEquals(instance.hashCode(), HashCodeUtils.hashCode(instance));

        instance.setProperty1(3);
        instance.setProperty2(44);
        instance.setProperty3("test");

        assertEquals(instance.hashCode(), HashCodeUtils.hashCode(instance));
    }

    public static final class TestClass {
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

                TestClass that = (TestClass) o;

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

        @Override
        public int hashCode() {
            int result = Constants.HASH_CODE_START;

            result = Constants.HASH_CODE_PRIME * result + property1;
            result = Constants.HASH_CODE_PRIME * result + (int) (property2 ^ property2 >>> 32);
            result = Constants.HASH_CODE_PRIME * result + (property3 != null ? property3.hashCode() : 0);

            return result;
        }
    }
}