package org.jtheque.utils;

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

import org.slf4j.LoggerFactory;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utility class for the class Desktop.
 *
 * @author Baptiste Wicht
 */
public final class DesktopUtils {
    /**
     * Construct a new DesktopUtils. This class isn't instanciable
     */
    private DesktopUtils() {
        super();
    }

    /**
     * Open the mailer.
     */
    public static void mail() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
            try {
                Desktop.getDesktop().mail();
            } catch (IOException e) {
                LoggerFactory.getLogger(DesktopUtils.class).error("Unable to open mailer", e);
            }
        }
    }

    /**
     * Open the mailer with specifics information.
     *
     * @param mail The mail to send
     */
    public static void mail(DesktopMail mail) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
            try {
                Desktop.getDesktop().mail(mail.getURI());
            } catch (IOException e) {
                LoggerFactory.getLogger(DesktopUtils.class).error("Unable to open mailer", e);
            } catch (URISyntaxException e) {
                LoggerFactory.getLogger(DesktopUtils.class).error("Unable to open mailer", e);
            }
        }
    }

    /**
     * Open the file.
     *
     * @param file The file to open.
     */
    public static void open(File file) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                LoggerFactory.getLogger(DesktopUtils.class).error("Unable to open file", e);
            }
        }
    }

    /**
     * Open the browser at the url.
     *
     * @param url The URL to browse.
     */
    public static void browse(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException e) {
                LoggerFactory.getLogger(DesktopUtils.class).error("Unable to open browser", e);
            } catch (URISyntaxException e) {
                LoggerFactory.getLogger(DesktopUtils.class).error("Unable to open browser", e);
            }
        }
    }
}