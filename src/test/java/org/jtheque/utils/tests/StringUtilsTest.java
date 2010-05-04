package org.jtheque.utils.tests;

import org.jtheque.utils.StringUtils;
import org.junit.Test;

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
 * A TestCase to test the StringUtils class.
 *
 * @author Baptiste Wicht
 */
public class StringUtilsTest {
    /**
     * Test the setFirstLetterUpper method.
     */
    @Test
    public void setFirstLetterOnlyUpper() {
        String test1 = "tASF";
        String test2 = "Test";
        String test3 = "teSt097896";
        String test4 = "000899tEst097896";
        String test5 = "";
        String test6 = null;

        assertEquals(StringUtils.setFirstLetterOnlyUpper(test1), "Tasf");
        assertEquals(StringUtils.setFirstLetterOnlyUpper(test2), "Test");
        assertEquals(StringUtils.setFirstLetterOnlyUpper(test3), "Test097896");
        assertEquals(StringUtils.setFirstLetterOnlyUpper(test4), "000899test097896");
        assertEquals(StringUtils.setFirstLetterOnlyUpper(test5), "");
        assertNull(StringUtils.setFirstLetterOnlyUpper(test6));
    }

    @Test
    public void setFirstLetterUpper() {
        String test1 = "tASF";
        String test2 = "Test";
        String test3 = "teSt097896";
        String test4 = "000899tEst097896";
        String test5 = "";
        String test6 = null;

        assertEquals(StringUtils.setFirstLetterUpper(test1), "TASF");
        assertEquals(StringUtils.setFirstLetterUpper(test2), "Test");
        assertEquals(StringUtils.setFirstLetterUpper(test3), "TeSt097896");
        assertEquals(StringUtils.setFirstLetterUpper(test4), "000899tEst097896");
        assertEquals(StringUtils.setFirstLetterUpper(test5), "");
        assertNull(StringUtils.setFirstLetterUpper(test6));
    }

    /**
     * Test the isEmpty method.
     */
    @Test
    public void testIsEmptyString() {
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty((String) null));
        assertFalse(StringUtils.isEmpty("asfht6goiunc9857cpnh"));
        assertFalse(StringUtils.isEmpty("a"));
        assertFalse(StringUtils.isEmpty("09787438934845238"));
    }

    @Test
    public void testIsNotEmptyString() {
        assertFalse(StringUtils.isNotEmpty(""));
        assertFalse(StringUtils.isNotEmpty((String) null));
        assertTrue(StringUtils.isNotEmpty("asfht6goiunc9857cpnh"));
    }

    @Test
    public void testIsEmptyStrings() {
        String[] empty1 = null;
        String[] empty2 = new String[]{};
        String[] empty3 = new String[2];
        String[] empty4 = new String[]{"", "", ""};
        String[] notEmpty1 = new String[]{"asdf"};
        String[] notEmpty2 = new String[]{"", "", "d"};

        assertTrue(StringUtils.isEmpty(empty1));
        assertTrue(StringUtils.isEmpty(empty2));
        assertTrue(StringUtils.isEmpty(empty3));
        assertTrue(StringUtils.isEmpty(empty4));
        assertFalse(StringUtils.isEmpty(notEmpty1));
        assertFalse(StringUtils.isEmpty(notEmpty2));
    }

    @Test
    public void testIsNotEmptyStrings() {
        String[] empty1 = null;
        String[] empty2 = new String[]{};
        String[] empty3 = new String[2];
        String[] empty4 = new String[]{"", "", ""};
        String[] notEmpty1 = new String[]{"asdf"};
        String[] notEmpty2 = new String[]{"", "", "d"};

        assertFalse(StringUtils.isNotEmpty(empty1));
        assertFalse(StringUtils.isNotEmpty(empty2));
        assertFalse(StringUtils.isNotEmpty(empty3));
        assertFalse(StringUtils.isNotEmpty(empty4));
        assertTrue(StringUtils.isNotEmpty(notEmpty1));
        assertTrue(StringUtils.isNotEmpty(notEmpty2));
    }

    /**
     * Test the removeLastSpace method.
     */
    @Test
    public void testRemoveLastChar() {
        StringBuilder builder1 = new StringBuilder("test");
        StringBuilder builder2 = new StringBuilder("");
        StringBuilder builder3 = new StringBuilder("T");

        StringUtils.removeLastSpace(builder1);
        StringUtils.removeLastSpace(builder2);
        StringUtils.removeLastSpace(builder3);

        assertEquals(builder1.toString(), "tes");
        assertEquals(builder2.toString(), "");
        assertEquals(builder3.toString(), "");
    }

    /**
     * Test the removeHTMLEntities method.
     */
    @Test
    public void testRemoveHTML() {
        String test1 = "<br />";
        String test2 = "<br/>";
        String test3 = "<br>";
        String test4 = "<br >";
        String test5 = " <br/> ";
        String test6 = "asdf7<br>7asdf";

        assertEquals(StringUtils.removeHTMLEntities(test1), "\n");
        assertEquals(StringUtils.removeHTMLEntities(test2), "\n");
        assertEquals(StringUtils.removeHTMLEntities(test3), "\n");
        assertEquals(StringUtils.removeHTMLEntities(test4), "\n");
        assertEquals(StringUtils.removeHTMLEntities(test5), " \n ");
        assertEquals(StringUtils.removeHTMLEntities(test6), "asdf7\n7asdf");
    }

    /**
     * Test the isSurroudedWith method.
     */
    @Test
    public void testIsSurroundedWith() {
        String test1 = "Test";
        String test2 = "";
        String test3 = "*asdfa";
        String test4 = "asdf*";
        String test5 = "*asdf*";

        assertFalse(StringUtils.isStringSurroundedWith(test1, '*'));
        assertFalse(StringUtils.isStringSurroundedWith(test2, '*'));
        assertFalse(StringUtils.isStringSurroundedWith(test3, '*'));
        assertFalse(StringUtils.isStringSurroundedWith(test4, '*'));
        assertTrue(StringUtils.isStringSurroundedWith(test5, '*'));
    }

    /**
     * Test the removeSurroudedChars method.
     */
    @Test
    public void testRemoveSurroundedChars() {
        String test1 = "Test";
        String test2 = "";
        String test3 = "*asdfa";
        String test4 = "asdf*";
        String test5 = "*asdf*";
        String test6 = "++asdf++";

        assertEquals(StringUtils.removeSurroundedChars(test1), "es");
        assertEquals(StringUtils.removeSurroundedChars(test2), "");
        assertEquals(StringUtils.removeSurroundedChars(test3), "asdf");
        assertEquals(StringUtils.removeSurroundedChars(test4), "sdf");
        assertEquals(StringUtils.removeSurroundedChars(test5), "asdf");
        assertEquals(StringUtils.removeSurroundedChars(test6), "+asdf+");
    }

    @Test
    public void endsWithOneof() {
        String test1 = "asdf.avi";
        String test2 = "asdf.mpg3";

        assertTrue(StringUtils.endsWithOneOf(test1, "avi", "mpg", "wma"));
        assertFalse(StringUtils.endsWithOneOf(test2, "avi", "mpg", "wma"));
    }

    @Test
    public void removeNumbers() {
        String test1 = "withoutNumbers";
        String test2 = "00884772with234num4345bers1234";

        assertEquals(StringUtils.removeNumbers(test1), test1);
        assertEquals(StringUtils.removeNumbers(test2), "withnumbers");
    }
}