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

import org.jtheque.utils.Constants;
import org.jtheque.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * A version of a module or an other part of the application.
 *
 * @author Baptiste Wicht
 */
public final class Version implements Comparable<Version> {
    private final String strVersion;

    private int hash;
    private final boolean snapshot;

    private static final Map<String, Integer> CODES;

    private static final int HIGHEST_CODE = 10;

    private static final Pattern SNAPSHOT_PATTERN = Pattern.compile("-SNAPSHOT");
    private static final Pattern SNAPSHOT_PATTERN_2 = Pattern.compile("-snapshot");

    static {
        CODES = new LinkedHashMap<String, Integer>(11);

        CODES.put("beta", 2);
        CODES.put("b", 2);
        CODES.put("B", 2);

        CODES.put("alpha", 1);
        CODES.put("a", 1);
        CODES.put("A", 1);

        CODES.put("milestone", 3);
        CODES.put("m", 3);
        CODES.put("M", 3);

        CODES.put("rc", 4);
        CODES.put("RC", 4);
    }

    /**
     * Construct a new JThequeVersion with the String version.
     *
     * @param version The version
     */
    public Version(String version) {
        super();

        if (version.endsWith("-SNAPSHOT") || version.endsWith("-snapshot")) {
            snapshot = true;
            strVersion = SNAPSHOT_PATTERN_2.matcher(SNAPSHOT_PATTERN.matcher(version).replaceAll("")).replaceAll("");
        } else {
            snapshot = false;
            strVersion = version;
        }
    }

    /**
     * Indicate if the version is greater than an other version.
     *
     * @param otherVersion The version we want to compare to
     * @return true if the current version is greater than the other else false
     */
    public boolean isGreaterThan(Version otherVersion) {
        return compareTo(otherVersion) > 0;
    }

    /**
     * Compare the codes of two version.
     *
     * @param current        The current version.
     * @param other          The other version.
     * @param codeStrCurrent The code of the current version.
     * @param codeStrOther   The code of the other version.
     * @return true if the current code is most valued than the other.
     */
    private static boolean compareCode(String current, String other, String codeStrCurrent, String codeStrOther) {
        int codeCurrent = intCode(codeStrCurrent);
        int codeOther = intCode(codeStrOther);

        if (codeCurrent == codeOther) {
            String strCurrent = current.replace(codeStrCurrent, StringUtils.EMPTY_STRING);
            String strOther = other.replace(codeStrOther, StringUtils.EMPTY_STRING);

            return Integer.valueOf(strCurrent).compareTo(Integer.valueOf(strOther)) >= 0;
        } else {
            return codeCurrent > codeOther;
        }
    }

    /**
     * Return the int code of the string code.
     *
     * @param strCode The string code.
     * @return The int value of the string code.
     */
    private static int intCode(String strCode) {
        return StringUtils.isEmpty(strCode) ? HIGHEST_CODE : CODES.get(strCode);
    }

    /**
     * Extract the code contained in the version.
     *
     * @param version The version string.
     * @return The code contained in the version or an empty String if there is no code in this version.
     */
    private static String extractCode(String version) {
        for (Map.Entry<String, Integer> entry : CODES.entrySet()) {
            if (version.contains(entry.getKey())) {
                return entry.getKey();
            }
        }

        return "";
    }

    /**
     * Returns the string version.
     *
     * @return The version
     */
    public String getVersion() {
        return strVersion;
    }

    @Override
    public String toString() {
        return strVersion + (snapshot ? "-SNAPSHOT" : "");
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Constants.HASH_CODE_START;

            if (strVersion != null) {
                hash = Constants.HASH_CODE_PRIME * hash + strVersion.hashCode();
            }

            hash = Constants.HASH_CODE_PRIME * hash + (snapshot ? 1 : 0);
        }

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (EqualsUtils.areObjectIncompatible(this, obj)) {
            return false;
        }

        Version version = (Version) obj;

        if (version.snapshot != snapshot) {
            return false;
        }

        return !EqualsUtils.areNotEquals(strVersion, version.strVersion);

    }

    @Override
    public int compareTo(Version o) {
        if (equals(o)) {
            return 0;
        }

        StringTokenizer currentTokens = new StringTokenizer(strVersion, ".");
        StringTokenizer otherTokens = new StringTokenizer(o.strVersion, ".");

        while (currentTokens.hasMoreTokens()) {
            String current = currentTokens.nextToken();

            if (otherTokens.hasMoreTokens()) {
                String other = otherTokens.nextToken();

                int compare = compareTwoToken(current, other);
                
                //If the two tokens are not equals, we could assume than the result of the comparison is the result of the
                //comparison of the complete version
                if(compare != 0){
                    return compare;
                }
            } else {
                return StringUtils.isEmpty(extractCode(current)) ? 1 : -1;
            }
        }

        return -1;
    }

    /**
     * Compare two tokens of two version. 
     * 
     * @param current The current token. 
     * @param other The other token. 
     * 
     * @return  a negative integer, zero, or a positive integer as this object
     *		is less than, equal to, or greater than the specified object.
     */
    private int compareTwoToken(String current, String other) {
        String codeStrCurrent = extractCode(current);
        String codeStrOther = extractCode(other);

        if (StringUtils.isEmpty(codeStrCurrent) && StringUtils.isEmpty(codeStrOther)) {
            int compare = Integer.valueOf(current).compareTo(Integer.valueOf(other));

            return compare;
        } else {
            return compareCode(current, other, codeStrCurrent, codeStrOther) ? 1 : -1;
        }
    }
}