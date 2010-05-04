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

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * A TestCase to test the EqualsUtils class.
 *
 * @author Baptiste Wicht
 */
public class EqualsUtilsTest {
    /**
     * Test the areObjectIncompatible method.
     */
    @Test
    public void testAreObjectIncompatible() {
        Object version1 = new Version("1.2");
        Object version2 = new Version("1.3");
        String version3 = "1.4";

        assertTrue(EqualsUtils.areObjectIncompatible(version3, version1));
        assertTrue(EqualsUtils.areObjectIncompatible(version1, version3));

        assertFalse(EqualsUtils.areObjectIncompatible(version1, version2));
    }

    /**
     * Test the areNotEquals method.
     */
    @Test
    public void testAreNotEquals() {
        Object version1 = "1.2";
        Object version2 = "1.3";
        Object version3 = null;
        Object version4 = null;
        Object version5 = "1.2";

        assertTrue(EqualsUtils.areNotEquals(version1, version2));
        assertTrue(EqualsUtils.areNotEquals(version2, version1));
        assertTrue(EqualsUtils.areNotEquals(version1, version3));
        assertTrue(EqualsUtils.areNotEquals(version3, version1));

        assertFalse(EqualsUtils.areNotEquals(version3, version4));
        assertFalse(EqualsUtils.areNotEquals(version4, version3));
        assertFalse(EqualsUtils.areNotEquals(version1, version5));
        assertFalse(EqualsUtils.areNotEquals(version5, version1));
    }

    /**
     * Test the areNotSameFile method.
     */
    @Test
    public void testAreNotSameFile() {
        File f1 = new File(System.getProperty("java.io.tmpdir"), "f1.txt");
        File f2 = new File(System.getProperty("java.io.tmpdir"), "f2.txt");
        File f3 = new File(System.getProperty("java.io.tmpdir"), "f1.txt");

        assertTrue(EqualsUtils.areNotSameFile(f1, f2));
        assertTrue(EqualsUtils.areNotSameFile(f2, f1));

        assertFalse(EqualsUtils.areNotSameFile(f1, f3));
        assertFalse(EqualsUtils.areNotSameFile(f3, f1));
    }
}
