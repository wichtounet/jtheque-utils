package org.jtheque.utils.io;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileFilter;

import static org.jtheque.utils.unit.file.FileUnit.*;
import static org.junit.Assert.*;

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
public class SimpleFilterTest {
    @BeforeClass
    public static void setUp() {
        initTestFileSystem();
    }

    @AfterClass
    public static void dispose() {
        clearTestFileSystem();
    }

    @Test
    public void acceptFiles() {
        addFile("file.txt");
        addFile("file.avi");
        addFile("file.cpp");
        addFile("file.java");

        FileFilter filter1 = new SimpleFilter("", "txt,avi");
        FileFilter filter2 = new SimpleFilter("", "txt");
        FileFilter filter3 = new SimpleFilter("", "cpp,java");

        assertTrue(filter1.accept(getFile("file.txt")));
        assertTrue(filter2.accept(getFile("file.txt")));
        assertFalse(filter3.accept(getFile("file.txt")));

        assertTrue(filter1.accept(getFile("file.avi")));
        assertFalse(filter2.accept(getFile("file.avi")));
        assertFalse(filter3.accept(getFile("file.avi")));

        assertFalse(filter1.accept(getFile("file.cpp")));
        assertFalse(filter2.accept(getFile("file.cpp")));
        assertTrue(filter3.accept(getFile("file.cpp")));

        assertFalse(filter1.accept(getFile("file.java")));
        assertFalse(filter2.accept(getFile("file.java")));
        assertTrue(filter3.accept(getFile("file.java")));
    }

    @Test
    public void acceptFolders() {
        addFolder("folder1");
        addFolder("folder1", "sub1");

        FileFilter filter1 = new SimpleFilter("", "txt,avi");

        assertTrue(filter1.accept(getFile("folder1")));
        assertTrue(filter1.accept(getFile("folder1", "sub1")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void argument1() {
        new SimpleFilter(null, "asdf");
    }

    @Test(expected = IllegalArgumentException.class)
    public void argument2() {
        new SimpleFilter("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void argument3() {
        new SimpleFilter(null, null);
    }

    @Test
    public void description() {
        assertEquals("test", new SimpleFilter("test", "avi").getDescription());
    }
}
