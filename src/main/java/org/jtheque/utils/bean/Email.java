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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * An email.
 *
 * @author Baptiste Wicht
 */
public final class Email {
    private String from;
    private String[] to;
    private String subject;
    private String message;
    private final Collection<File> attachedFiles = new ArrayList<File>(5);

    /**
     * Return the from address.
     *
     * @return The address from.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Set the from address.
     *
     * @param from The from address.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Return the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message.
     *
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the subject.
     *
     * @return The subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject.
     *
     * @param subject The subject.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Return the to addresses.
     *
     * @return A array of String containing all the addresses to.
     */
    public String[] getTo() {
        return Arrays.copyOf(to, to.length);
    }

    /**
     * Set the addresses to.
     *
     * @param to An array containing all the destination addresses.
     */
    public void setTo(String[] to) {
        this.to = Arrays.copyOf(to, to.length);
    }

    /**
     * Attach a file to the mail.
     *
     * @param f The file to attach.
     */
    public void attachFile(File f) {
        attachedFiles.add(f);
    }

    /**
     * Attach files to the mail.
     *
     * @param c The collection of files to attach to the mail.
     */
    public void attachFiles(Collection<File> c) {
        attachedFiles.addAll(c);
    }

    /**
     * Return the files attached to the mail.
     *
     * @return A List containing all the files attached to the mail.
     */
    public Collection<File> getAttachedFiles() {
        return attachedFiles;
    }
}