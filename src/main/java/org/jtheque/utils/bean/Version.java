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
import org.jtheque.utils.annotations.Immutable;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * A version of a module or an other part of the application. Instances of the version class are immutable. Moreover,
 * instance of these classes are unique, namely, two instances are equals only if this the same instance. The instances
 * are stored using weak references.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class Version implements Comparable<Version>, Serializable {
    private static final long serialVersionUID = -7956061709109064519L;
    
    private static final Map<String, Integer> CODES = new LinkedHashMap<String, Integer>(7);
    private static final Pattern SNAPSHOT_PATTERN = Pattern.compile("-SNAPSHOT");

    private static final Map<String, WeakReference<Version>> VERSIONS = new HashMap<String, WeakReference<Version>>(16);

    private final String strVersion;
    private final boolean snapshot;

    private transient int hash;

    private static final Object LOCK = new Object();

    static {
        CODES.put("BETA", 2);
        CODES.put("B", 2);

        CODES.put("ALPHA", 1);
        CODES.put("A", 1);

        CODES.put("MILESTONE", 3);
        CODES.put("M", 3);

        CODES.put("RC", 4);
    }

    /**
     * Construct a new Version with the String version.
     *
     * @param version The version
     */
    private Version(String version) {
        super();

        String normalisedVersion = version.toUpperCase(Locale.getDefault());

        if (normalisedVersion.endsWith("-SNAPSHOT")) {
            snapshot = true;
            strVersion = SNAPSHOT_PATTERN.matcher(normalisedVersion).replaceAll("");
        } else {
            snapshot = false;
            strVersion = normalisedVersion;
        }
    }

    /**
     * This method return the Version corresponding to the given String. This method is thread safe using
     * the double check idiom. 
     *
     * @param version The version name to get the instance for.
     *
     * @return The Version or null if the passed version is empty or invalid.
     */
    public static Version get(String version) {
        if(StringUtils.isEmpty(version)){
            return null;
        }

        WeakReference<Version> v = VERSIONS.get(version);

        if (v == null || v.get() == null) {
            synchronized (LOCK) {
                v = VERSIONS.get(version);

                if (v == null || v.get() == null) {
                    v = new WeakReference<Version>(new Version(version));

                    VERSIONS.put(version, v);
                }
            }
        }

        return v.get();
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
        return this == obj; //Only two equals instance are equals
    }

    @Override
    public int compareTo(Version o) {
        if (this == o) {
            return 0;
        }

        //Snapshot versions are lower than normal versions
        if(strVersion.equals(o.strVersion)){
            return snapshot ? -1 : 1;
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
            return compareCode(current, other, codeStrCurrent, codeStrOther);
        }
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
     * Compare the codes of two version.
     *
     * @param current        The current version.
     * @param other          The other version.
     * @param codeStrCurrent The code of the current version.
     * @param codeStrOther   The code of the other version.
     *
     * @return true if the current code is most valued than the other.
     */
    private static int compareCode(String current, String other, String codeStrCurrent, String codeStrOther) {
        int codeCurrent = intCode(codeStrCurrent);
        int codeOther = intCode(codeStrOther);

        if (codeCurrent == codeOther) {
            String strCurrent = StringUtils.delete(current, codeStrCurrent);
            String strOther = StringUtils.delete(other, codeStrOther);

            return Integer.valueOf(strCurrent).compareTo(Integer.valueOf(strOther));
        } else {
            return new Integer(codeCurrent).compareTo(codeOther);
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
        return StringUtils.isEmpty(strCode) ? Integer.MAX_VALUE : CODES.get(strCode);
    }
}