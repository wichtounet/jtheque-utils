package org.jtheque.utils.tests;

import org.jtheque.utils.OSUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

/**
 * @author Baptiste Wicht
 */
public class OSUtilsTest {
    @Test
    public void isWindows() {
        System.setProperty("os.name", "Windows XP");

        assertTrue(OSUtils.isWindows());

        System.setProperty("os.name", "FreeBSD");

        assertFalse(OSUtils.isWindows());
    }

    @Test
    public void isLinux() {
        System.setProperty("os.name", "Linux");

        assertTrue(OSUtils.isLinux());

        System.setProperty("os.name", "FreeBSD");

        assertFalse(OSUtils.isLinux());
    }

    @Test
    public void isMac() {
        System.setProperty("os.name", "Mac OS X");

        assertTrue(OSUtils.isMac());

        System.setProperty("os.name", "FreeBSD");

        assertFalse(OSUtils.isMac());
    }
}