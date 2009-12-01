package org.jtheque.utils.bean;

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
