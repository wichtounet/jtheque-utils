package org.jtheque.utils.ui;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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

/**
 * A builder for a button bar.
 *
 * @author Baptiste Wicht
 */
public final class ButtonBarBuilder {
    private final JPanel panel;
    private final GridBagUtils gbc;

    private int column;

    /**
     * Construct a new ButtonBarBuilder.
     */
    public ButtonBarBuilder() {
        super();

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);
        gbc = new GridBagUtils();
        gbc.setDefaultInsets(2, 2, 2, 2);
    }

    /**
     * Add an action to the bar.
     *
     * @param action The action to add.
     */
    private void addAction(Action action) {
        panel.add(new JButton(action), gbc.gbcSet(column, 0, GridBagConstraints.NONE, GridBagConstraints.BASELINE));
        column++;
    }

    /**
     * Add actions to the bar.
     *
     * @param actions The actions to add.
     */
    public void addActions(Action... actions) {
        for (Action action : actions) {
            addAction(action);
        }
    }

    /**
     * A a glue to the bar.
     */
    public void addGlue() {
        panel.add(Box.createHorizontalGlue(), gbc.gbcSet(column, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BASELINE, 1.0, 1.0));
        column++;
    }

    /**
     * Return the panel.
     *
     * @return The panel.
     */
    public Component getPanel() {
        return panel;
    }
}