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