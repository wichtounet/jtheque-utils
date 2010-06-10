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
    public void replace(FilterBypass fb, int offset, int length, String str,
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