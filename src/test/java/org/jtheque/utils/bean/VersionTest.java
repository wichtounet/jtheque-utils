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

import static org.junit.Assert.*;

/**
 * A TestCase to test the Version class.
 *
 * @author Baptiste Wicht
 */
public class VersionTest {
    /**
     * Test the isGreaterThan method.
     */
    @Test
    public void testIsGreaterThan() {
        Version version1 = new Version("1.0.beta2");
        Version version2 = new Version("5.0.5.beta19");
        Version version3 = new Version("5.5.6.9");
        Version version4 = new Version("1.0.0.0.0.1.beta9");
        Version version5 = new Version("1.0.beta5");

        assertFalse(version2.isGreaterThan(version2));

        assertFalse(version1.isGreaterThan(version2));
        assertFalse(version1.isGreaterThan(version3));
        assertFalse(version1.isGreaterThan(version4));
        assertFalse(version1.isGreaterThan(version5));

        assertTrue(version2.isGreaterThan(version1));
        assertFalse(version2.isGreaterThan(version3));
        assertTrue(version2.isGreaterThan(version4));

        assertTrue(version3.isGreaterThan(version1));
        assertTrue(version3.isGreaterThan(version2));
        assertTrue(version3.isGreaterThan(version4));

        assertTrue(version4.isGreaterThan(version1));
        assertFalse(version4.isGreaterThan(version2));
        assertFalse(version4.isGreaterThan(version3));

        assertTrue(version5.isGreaterThan(version1));
    }

    @Test
    public void testIsGreaterThanEquals() {
        Version versionTest1 = new Version("1.0");
        Version versionTest2 = new Version("1.0");

        assertFalse(versionTest1.isGreaterThan(versionTest2));
        assertFalse(versionTest2.isGreaterThan(versionTest1));
    }

    /**
     * Test the getVersion method.
     */
    @Test
    public void testGetVersion() {
        Version version1 = new Version("1.0.beta2");
        Version version2 = new Version("5.0.5.beta19");
        Version version3 = new Version("5.5.6.9");
        Version version4 = new Version("1.0.0.0.0.1.beta9");

        assertEquals(version1.getVersion(), "1.0.beta2");
        assertEquals(version2.getVersion(), "5.0.5.beta19");
        assertEquals(version3.getVersion(), "5.5.6.9");
        assertEquals(version4.getVersion(), "1.0.0.0.0.1.beta9");
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        Object version1 = new Version("1.0.beta2");
        Object version2 = new Version("5.0.5.beta19");
        Object version3 = new Version("5.5.6.9");
        Object version4 = new Version("1.0.0.0.0.1.beta9");
        Object version5 = new Version("1.0.1-SNAPSHOT");

        assertEquals(version1.toString(), "1.0.beta2");
        assertEquals(version2.toString(), "5.0.5.beta19");
        assertEquals(version3.toString(), "5.5.6.9");
        assertEquals(version4.toString(), "1.0.0.0.0.1.beta9");
        assertEquals(version5.toString(), "1.0.1-SNAPSHOT");
    }

    /**
     * Test the hashCode method.
     */
    @Test
    public void testHashCode() {
        Object version1 = new Version("1.0.beta2");
        Object version2 = new Version("5.0.beta2");
        Object version3 = new Version("1.0");
        Object version4 = new Version("1.0");
        Object version5 = new Version("1.0.1-snapshot");
        Object version6 = new Version("1.0.1-SNAPSHOT");

        assertEquals(version3.hashCode(), version4.hashCode());
        assertEquals(version4.hashCode(), version3.hashCode());

        assertEquals(version5.hashCode(), version6.hashCode());
        assertEquals(version6.hashCode(), version5.hashCode());

        assertEquals(version3.hashCode(), version4.hashCode());

        assertNotSame(version1.hashCode(), version2.hashCode());
        assertNotSame(version1.hashCode(), version3.hashCode());
        assertNotSame(version1.hashCode(), version4.hashCode());
        assertNotSame(version1.hashCode(), version5.hashCode());

        assertNotSame(version2.hashCode(), version3.hashCode());
        assertNotSame(version2.hashCode(), version4.hashCode());
        assertNotSame(version2.hashCode(), version5.hashCode());
    }

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        Object version1 = new Version("1.0.beta2");
        Object version2 = new Version("5.0.beta2");
        Object version3 = new Version("1.0");
        Object version4 = new Version("1.0");
        Object version5 = new Version("1.0.1-SNAPSHOT");
        Object string = "asdf";

        assertEquals(version3, version4);
        assertEquals(version4, version3);
        assertEquals(version4, version4);
        assertEquals(version3, version3);

        assertFalse(version1.equals(version2));
        assertFalse(version1.equals(version3));
        assertFalse(version1.equals(version4));
        assertFalse(version1.equals(version5));
        assertFalse(version1.equals(string));

        assertFalse(version2.equals(version3));
        assertFalse(version2.equals(version4));

        assertNotSame(version3.hashCode(), version4.hashCode());
    }

    @Test
    public void testCodes() {
        Version versionAlpha1 = new Version("1.0.alpha1");
        Version versionAlpha2 = new Version("1.0.alpha2");
        Version versionAlpha3 = new Version("1.0.a1");
        Version versionBeta = new Version("1.0.beta1");
        Version versionRC = new Version("1.0.rc1");

        assertTrue(versionAlpha2.isGreaterThan(versionAlpha1));
        assertFalse(versionAlpha1.isGreaterThan(versionAlpha2));
        assertNotSame(versionAlpha1, versionAlpha3);
        assertNotSame(versionAlpha1, versionAlpha2);
        assertTrue(versionBeta.isGreaterThan(versionAlpha1));
        assertTrue(versionBeta.isGreaterThan(versionAlpha2));
        assertTrue(versionBeta.isGreaterThan(versionAlpha3));
        assertTrue(versionRC.isGreaterThan(versionBeta));
    }
}
