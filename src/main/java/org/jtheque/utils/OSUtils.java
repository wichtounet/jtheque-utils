package org.jtheque.utils;

/**
 * Some utility methods for OS.
 *
 * @author Baptiste Wicht
 */
public final class OSUtils {
    /**
     * Utility class, not instanciable.
     */
    private OSUtils() {
        super();
    }

    /**
     * Test if the running operating system is Windows or not.
     *
     * @return true if the running operating system is Windows else false.
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").contains("Windows");
    }

    /**
     * Test if the running operating system is Linux or not.
     *
     * @return true if the running operating system is Linux else false.
     */
    public static boolean isLinux() {
        return System.getProperty("os.name").contains("Linux");
    }

    /**
     * Test if the running operating system is Mac or not.
     *
     * @return true if the running operating system is Mac else false.
     */
    public static boolean isMac() {
        return System.getProperty("os.name").contains("Mac");
    }
}