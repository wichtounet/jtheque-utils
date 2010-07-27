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

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * An email. This class is immutable.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class Email {
    private final String from;
    private final String[] to;
    private final String subject;
    private final String message;
    private final Collection<File> attachedFiles;

    public Email(String from, String[] to, String subject, String message, Collection<File> attachedFiles) {
        super();

        this.from = from;
        this.to = ArrayUtils.copyOf(to);
        this.subject = subject;
        this.message = message;
        this.attachedFiles = CollectionUtils.copyOf(attachedFiles);
    }

    /**
     * Return the from address.
     *
     * @return The address from.
     */
    public String getFrom() {
        return from;
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
     * Return the subject.
     *
     * @return The subject.
     */
    public String getSubject() {
        return subject;
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
     * Return the files attached to the mail.
     *
     * @return A List containing all the files attached to the mail.
     */
    public Collection<File> getAttachedFiles() {
        return Collections.unmodifiableCollection(attachedFiles);
    }
}