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

import java.net.URI;
import java.net.URISyntaxException;

/**
 * A desktop mail. It seems a mail to display on the default mailer.
 *
 * @author Baptiste Wicht
 */
public final class DesktopMail {
    private String subject;
    private String body;
    private String to;

    /**
     * Return the URI for this mail.
     *
     * @return The URI.
     * @throws URISyntaxException When an errors occurs during the URI construction.
     */
    public URI getURI() throws URISyntaxException {
        StringBuilder builder = new StringBuilder("mailto:");
        boolean first = true;

        builder.append(to);

        if (subject != null && !subject.isEmpty()) {
            builder.append("?subject=");
            builder.append(subject);
            first = false;
        }

        if (body != null && !body.isEmpty()) {
            if (first) {
                builder.append('?');
            } else {
                builder.append('&');
            }

            builder.append("body=");
            builder.append(body);
        }

        return new URI(builder.toString());
    }

    /**
     * Return the body of the mail.
     *
     * @return The body of the mail.
     */
    public String getBody() {
        return body;
    }

    /**
     * Set the body of the mail.
     *
     * @param body The body of the mail.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Return the subject of the mail.
     *
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject of the mail.
     *
     * @param subject The new subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Return the to address.
     *
     * @return The to address.
     */
    public String getTo() {
        return to;
    }

    /**
     * Set the to address.
     *
     * @param to The to address.
     */
    public void setTo(String to) {
        this.to = to;
    }
}