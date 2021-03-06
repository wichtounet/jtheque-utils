package org.jtheque.utils;

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

import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class provides utilities function to manipulate String's object.
 *
 * @author Baptiste Wicht
 */
public final class StringUtils {
    public static final String EMPTY_STRING = "";
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern HTML_BR = Pattern.compile("<br[ ]?[/]?>");
    private static final Pattern HTML_SIMPLE_QUOTE = Pattern.compile("&#39;");

    /**
     * Construct a new StringUtils. This constructor is private, all the methods are static.
     */
    private StringUtils() {
        throw new AssertionError();
    }

    /**
     * Set the first letter of the String to upper. All the others chars will be lower case.
     *
     * @param word The string to modify.
     *
     * @return The modified string.
     */
    public static String setFirstLetterOnlyUpper(String word) {
        if (isEmpty(word)) {
            return word;
        }

        char[] chars = word.toLowerCase(Locale.getDefault()).toCharArray();

        chars[0] = Character.toUpperCase(chars[0]);

        return String.valueOf(chars);
    }

    /**
     * Set the first letter of the String to upper.
     *
     * @param word The string to modify.
     *
     * @return The modified string.
     */
    public static String setFirstLetterUpper(String word) {
        if (isEmpty(word)) {
            return word;
        }

        char[] chars = word.toCharArray();

        chars[0] = Character.toUpperCase(chars[0]);

        return String.valueOf(chars);
    }

    /**
     * Test if a string is empty or not.
     *
     * @param string The string to test.
     *
     * @return true if the string is empty else false.
     */
    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    /**
     * Return true if the string is not empty.
     *
     * @param string The string to test.
     *
     * @return true if the string is not empty else false.
     */
    public static boolean isNotEmpty(CharSequence string) {
        return !isEmpty(string);
    }

    /**
     * Remove the last space in the StringBuilder.
     *
     * @param builder The builder we must modify
     */
    public static void removeLastSpace(StringBuilder builder) {
        if (builder.length() > 0) {
            builder.replace(builder.length() - 1, builder.length(), EMPTY_STRING);
        }
    }

    /**
     * Remove the HTML entities of a string.
     *
     * @param htmlString The string to clear.
     *
     * @return The cleared string.
     */
    public static String removeHTMLEntities(CharSequence htmlString) {
        String cleaned = HTML_SIMPLE_QUOTE.matcher(htmlString).replaceAll("'");

        cleaned = HTML_BR.matcher(cleaned).replaceAll("\n");

        return cleaned;
    }

    /**
     * Indicate if the string is surrounded by the character c.
     *
     * @param str The string to test.
     * @param c   The char to test.
     *
     * @return true if the string is surrounded by c else false.
     */
    public static boolean isStringSurroundedWith(CharSequence str, char c) {
        if (isEmpty(str)) {
            return false;
        }

        return str.charAt(0) == c && str.charAt(str.length() - 1) == c;
    }

    /**
     * Remove the first and the test chars.
     *
     * @param str The string to edit.
     *
     * @return The string without his surrounded chars.
     */
    public static String removeSurroundedChars(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return str.substring(1, str.length() - 1);
    }

    /**
     * Remove the unicode characters from a string.
     *
     * @param value The string to clean.
     *
     * @return The cleaned string.
     */
    public static String removeUnicode(String value) {
        String refreshed = value.replace("\\\\u00e0", "\u00e0");
        refreshed = refreshed.replace("\\\\u00e2", "\u00e2");
        refreshed = refreshed.replace("\\\\u00e4", "\u00e4");
        refreshed = refreshed.replace("\\\\u00e7", "\u00e7");
        refreshed = refreshed.replace("\\\\u00e8", "\u00e8");
        refreshed = refreshed.replace("\\\\u00e9", "\u00e9");
        refreshed = refreshed.replace("\\\\u00ea", "\u00ea");
        refreshed = refreshed.replace("\\\\u00eb", "\u00eb");
        refreshed = refreshed.replace("\\\\u00ee", "\u00ee");
        refreshed = refreshed.replace("\\\\u00ef", "\u00ef");
        refreshed = refreshed.replace("\\\\u00f4", "\u00f4");
        refreshed = refreshed.replace("\\\\u00f6", "\u00f6");
        refreshed = refreshed.replace("\\\\u00f9", "\u00f9");
        refreshed = refreshed.replace("\\\\u00fb", "\u00fb");
        refreshed = refreshed.replace("\\\\u00fc", "\u00fc");

        return refreshed;
    }

    /**
     * Test if the String value ends with one of the specified ends.
     *
     * @param value The String value to test.
     * @param ends  The possible ends.
     *
     * @return true if the String value ends with one of the specified ends.
     */
    public static boolean endsWithOneOf(String value, String... ends) {
        for (String end : ends) {
            if (value.endsWith(end)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Test if an array of String is empty. An array of strings is empty if one of these rule matches : <ul> <li>The
     * array is empty</li> <li>The array contains only null and empty String<li> </ul>
     *
     * @param strings The array to test.
     *
     * @return true if the array is empty else false.
     */
    public static boolean isEmpty(String[] strings) {
        if (strings != null && strings.length > 0) {
            for (String s : strings) {
                if (isNotEmpty(s)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Test if an array of string is not empty.
     *
     * @param strings The array of Strings to test.
     *
     * @return true if the array is not empty else false.
     *
     * @see StringUtils#isEmpty(String[] strings);
     */
    public static boolean isNotEmpty(String[] strings) {
        return !isEmpty(strings);
    }

    /**
     * Remove all numbers of the given string.
     *
     * @param value The string.
     *
     * @return The given string without any numbers.
     */
    public static String removeNumbers(CharSequence value) {
        return NUMBER_PATTERN.matcher(value).replaceAll("");
    }

    /**
     * Delete from the source all the occurrences of the given char sequences.
     *
     * @param string    The string source.
     * @param toDeletes The char sequences to remove from the source.
     *
     * @return A new string representing the source without the given char sequences.
     */
    public static String delete(String string, Object... toDeletes) {
        String cleared = string;

        for (Object toDelete : toDeletes) {
            cleared = delete(cleared, toDelete.toString());
        }

        return cleared;
    }

    /**
     * Delete from the source string a sequence of char.
     *
     * @param string   The string source.
     * @param toDelete The char sequence to remove from the source.
     *
     * @return A new string representing the source without the given char sequence.
     */
    public static String delete(String string, CharSequence toDelete) {
        return string.replace(toDelete, "");
    }

    /**
     * Indicate if the value equals one of the other given value.
     *
     * @param value  The value to test.
     * @param values The other values to compare the first with.
     *
     * @return {@code true} if the value equals one of the other given value.
     */
    public static boolean equalsOneOf(String value, String... values) {
        for (String v : values) {
            if (value.equals(v)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return the lines of the message.
     *
     * @param message The message to cut in lines.
     *
     * @return An array containing all the lines of the message.
     */
    public static String[] getLines(String message) {
        Collection<String> tokens = CollectionUtils.newList(5);

        Scanner scanner = new Scanner(message);

        while (scanner.hasNextLine()) {
            tokens.add(scanner.nextLine());
        }

        return tokens.toArray(new String[tokens.size()]);
    }
}