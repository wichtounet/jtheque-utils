package org.jtheque.utils.tests;

import org.jtheque.utils.OSUtils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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