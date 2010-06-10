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