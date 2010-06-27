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

import org.jtheque.utils.StringUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * A version of a module or an other part of the application.
 *
 * @author Baptiste Wicht
 */
public final class Version implements Comparable<Version>, Serializable {
    private static final long serialVersionUID = -7956061709109064519L;
    private static final Map<String, Integer> CODES;
    private static final int HIGHEST_CODE = 10;
    private static final Pattern SNAPSHOT_PATTERN = Pattern.compile("-SNAPSHOT");
    private static final Pattern SNAPSHOT_PATTERN_2 = Pattern.compile("-snapshot");

    private final String strVersion;
    private final boolean snapshot;

    private transient int hash;

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
     * Construct a new Version with the String version.
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
     * Construct a new Version with the given Version.
     *
     * @param version The version to copy.
     */
    public Version(Version version){
        this(version.strVersion);
    }

    /**
     * Indicate if the version is greater than an other version.
     *
     * @param otherVersion The version we want to compare to
     *
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
     *
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
     *
     * @return The int value of the string code.
     */
    private static int intCode(String strCode) {
        return StringUtils.isEmpty(strCode) ? HIGHEST_CODE : CODES.get(strCode);
    }

    /**
     * Extract the code contained in the version.
     *
     * @param version The version string.
     *
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
            hash = HashCodeUtils.hashCodeDirect(strVersion, snapshot);
        }

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Version){
            Version other = (Version)obj;

            return EqualsBuilder.newBuilder(this, obj).
                    addField(strVersion, other.strVersion).
                    addField(snapshot, other.snapshot).
                    areEquals();
        } else {
            return false;
        }
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
                if (compare != 0) {
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
     * @param other   The other token.
     *
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     *         the specified object.
     */
    private static int compareTwoToken(String current, String other) {
        String codeStrCurrent = extractCode(current);
        String codeStrOther = extractCode(other);

        if (StringUtils.isEmpty(codeStrCurrent) && StringUtils.isEmpty(codeStrOther)) {
            return Integer.valueOf(current).compareTo(Integer.valueOf(other));
        } else {
            return compareCode(current, other, codeStrCurrent, codeStrOther) ? 1 : -1;
        }
    }
}