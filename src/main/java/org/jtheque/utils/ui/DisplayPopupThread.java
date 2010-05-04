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

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import java.awt.Color;

/**
 * A thread to display a popup on a field.
 *
 * @author Baptiste Wicht
 */
final class DisplayPopupThread extends Thread {
    private final PopupFactory factory = PopupFactory.getSharedInstance();
    private final JTextField owner;
    private final JComponent content;

    private Popup popup;

    private static final int DISPLAY_TIME = 2000;

    /**
     * Construct a new <code>DisplayPopupThread</code> for a specific field.
     *
     * @param field the JTextField associated in which we display popup.
     */
    DisplayPopupThread(JTextField field) {
        super();

        owner = field;

        content = new JPanel();
        content.add(new JLabel("Too long"));
        content.setBackground(Color.white);
        content.setBorder(BorderFactory.createEtchedBorder());
    }

    @Override
    public void run() {
        popup = createPopup();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                popup.show();
            }
        });

        try {
            Thread.sleep(DISPLAY_TIME);
        } catch (InterruptedException e) {
            //Nothing to do.
        }

        interrupt();
    }

    @Override
    public void interrupt() {
        close();

        super.interrupt();
    }

    /**
     * Create a popup for the actual field. The popup is configured to appear at the middle of the field.
     *
     * @return The popup configured at the good position.
     */
    private Popup createPopup() {
        return factory.getPopup(
                owner, content,
                owner.getLocationOnScreen().x + owner.getWidth() / 2,
                owner.getLocationOnScreen().y + owner.getHeight() / 2);
    }

    /**
     * Close the current popup.
     */
    void close() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                popup.hide();
            }
        });
    }
}
