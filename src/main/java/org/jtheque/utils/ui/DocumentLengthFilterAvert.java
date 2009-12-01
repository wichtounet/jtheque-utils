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

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Toolkit;

/**
 * A document filter used to limit the length of a field. This filter avert the user with a popup when he go to
 * the limit.
 *
 * @author Baptiste Wicht
 */
public final class DocumentLengthFilterAvert extends DocumentLengthFilter {
    private DisplayPopupThread thread;
    private final JTextField field;

    /**
     * Construct a new <code>DocumentLengthFilterAvert</code> associated with a specific field and with a
     * maximum number of characters.
     *
     * @param max   The maximum numbers of characters.
     * @param field The field we want to associate to fhe filter.
     */
    public DocumentLengthFilterAvert(int max, JTextField field) {
        super(max);

        this.field = field;
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        replace(fb, offset, length, "", null);
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str,
                        AttributeSet attrs) throws BadLocationException {
        if (str != null) {
            int newLength = fb.getDocument().getLength() - length + str.length();

            if (thread != null) {
                thread.close();
            }

            if (newLength <= getMax()) {
                fb.replace(offset, length, str, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();

                thread = new DisplayPopupThread(field);
                thread.start();
            }
        }
    }
}