package org.jtheque.utils.ui;

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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Toolkit;


/**
 * A document filter used to limit the length of a field.
 *
 * @author Baptiste Wicht
 */
public class DocumentLengthFilter extends DocumentFilter {
    private final int max;

    private static final int DEFAULT_LENGTH_LIMIT = 50;

    /**
     * Construct a new <code>DocumentLengthFilter</code> with a default maximum number of characters. By default,
     * only 50 characters are accepted.
     */
    public DocumentLengthFilter() {
        this(DEFAULT_LENGTH_LIMIT);
    }

    /**
     * Construct a new <code>DocumentLengthFilter</code> with a maximum number of characters.
     *
     * @param max The maximum number of characters permitted in the document.
     */
    public DocumentLengthFilter(int max) {
        super();

        this.max = max;
    }

    @Override
    public final void insertString(DocumentFilter.FilterBypass fb, int offset, String str,
                                   AttributeSet attrs) throws BadLocationException {
        replace(fb, offset, 0, str, attrs);
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str,
                        AttributeSet attrs) throws BadLocationException {
        int newLength = fb.getDocument().getLength() - length + str.length();

        if (newLength <= max) {
            fb.replace(offset, length, str, attrs);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    /**
     * Return the maximum number of characters permitted in the document.
     *
     * @return The number.
     */
    protected final int getMax() {
        return max;
    }
}