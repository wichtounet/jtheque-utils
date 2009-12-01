package org.jtheque.utils.io;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileFilter;

import static org.jtheque.utils.unit.file.FileUnit.*;
import static org.junit.Assert.*;

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
