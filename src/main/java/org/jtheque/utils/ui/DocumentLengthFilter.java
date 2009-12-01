package org.jtheque.utils.ui;

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