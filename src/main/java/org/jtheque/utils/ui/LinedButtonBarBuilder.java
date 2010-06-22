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