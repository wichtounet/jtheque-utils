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

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * A builder for a button bar.
 *
 * @author Baptiste Wicht
 */
public final class LinedButtonBarBuilder {
    private final JPanel panel;
    private final GridBagUtils gbc;

    private final int[] column;

    /**
     * Construct a new ButtonBarBuilder.
     *
     * @param lines The number of lines.
     */
    public LinedButtonBarBuilder(int lines) {
        super();

        column = new int[lines];

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);

        gbc = new GridBagUtils();
        gbc.setDefaultInsets(2, 2, 2, 2);

        for (int i = 0; i < lines; i++) {
            panel.add(Box.createHorizontalGlue(), gbc.gbcSet(column[i], i, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, 1.0, 1.0));
            column[i]++;
        }
    }

    /**
     * Add an action to the bar.
     *
     * @param action The action to add.
     * @param line   The line.
     */
    private void addAction(Action action, int line) {
        int lineIndex = line - 1;

        panel.add(new JButton(action), gbc.gbcSet(column[lineIndex], lineIndex, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START));
        column[lineIndex]++;
    }

    /**
     * Add actions to the bar.
     *
     * @param line    The line to add the actions in.
     * @param actions The actions to add.
     */
    public void addActions(int line, Action... actions) {
        for (Action action : actions) {
            addAction(action, line);
        }
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