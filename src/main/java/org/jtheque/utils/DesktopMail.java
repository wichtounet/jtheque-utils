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