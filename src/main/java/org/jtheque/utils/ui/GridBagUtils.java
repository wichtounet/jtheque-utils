package org.jtheque.utils.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

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
 * An utility class to make easier the UI development with GridBagLayout.
 *
 * @author Baptiste Wicht
 */
public final class GridBagUtils extends GridBagConstraints {
    private Insets defaultInsets = new Insets(2, 2, 2, 2);

    /**
     * Init the GridBagConstraints object.
     */
    private void gbcInit() {
        gridx = 0;
        gridy = 0;
        gridwidth = 1;
        gridheight = 1;
        anchor = BASELINE_LEADING;
        fill = NONE;
        weightx = 0;
        weighty = 0;
        insets = defaultInsets;
        ipadx = 0;
        ipady = 0;
    }

    /**
     * Configure and return the GridBagConstraints object.
     *
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @return The GridBagConstraints object.
     */
    public GridBagConstraints gbcSet(int x, int y) {
        gbcInit();

        gridx = x;
        gridy = y;

        return this;
    }

    /**
     * Configure and return the GridBagConstraints object.
     *
     * @param x    The x position of the component.
     * @param y    The y position of the component.
     * @param fill The fill constraints.
     * @return The GridBagConstraints object.
     */
    public GridBagConstraints gbcSet(int x, int y, int fill) {
        gbcInit();

        gridx = x;
        gridy = y;
        this.fill = fill;

        return this;
    }

    /**
     * Configure and return the GridBagConstraints object.
     *
     * @param x      The x position of the component.
     * @param y      The y position of the component.
     * @param fill   The fill constraints.
     * @param anchor The anchor of the component.
     * @return The GridBagConstraints object.
     */
    public GridBagConstraints gbcSet(int x, int y, int fill, int anchor) {
        gbcInit();

        gridx = x;
        gridy = y;
        this.fill = fill;
        this.anchor = anchor;

        return this;
    }

    /**
     * Configure and return the GridBagConstraints object.
     *
     * @param x      The x position of the component.
     * @param y      The y position of the component.
     * @param fill   The fill constraints.
     * @param anchor The anchor of the component.
     * @param width  The col span.
     * @param height The row span.
     * @return The GridBagConstraints object.
     */
    public GridBagConstraints gbcSet(int x, int y, int fill, int anchor, int width, int height) {
        gbcInit();

        gridx = x;
        gridy = y;
        this.fill = fill;
        this.anchor = anchor;
        gridwidth = width;
        gridheight = height;

        return this;
    }

    /**
     * Configure and return the GridBagConstraints object.
     *
     * @param x       The x position of the component.
     * @param y       The y position of the component.
     * @param fill    The fill constraints.
     * @param anchor  The anchor of the component.
     * @param width   The col span.
     * @param height  The row span.
     * @param weightx The col fill weight.
     * @param weighty The row fill weight.
     * @return The GridBagConstraints object.
     */
    public GridBagConstraints gbcSet(int x, int y, int fill, int anchor, int width, int height, double weightx, double weighty) {
        gbcInit();

        gridx = x;
        gridy = y;
        this.fill = fill;
        this.anchor = anchor;
        gridwidth = width;
        gridheight = height;
        this.weightx = weightx;
        this.weighty = weighty;

        return this;
    }

    /**
     * Configure and return the GridBagConstraints object.
     *
     * @param x       The x position of the component.
     * @param y       The y position of the component.
     * @param fill    The fill constraints.
     * @param anchor  The anchor of the component.
     * @param width   The col span.
     * @param height  The row span.
     * @param weightx The col fill weight.
     * @param weighty The row fill weight.
     * @param ipadx   The x internal padding width.
     * @param ipady   The y internal padding height.
     * @return The <code>GridBagConstraints</code> object.
     */
    public GridBagConstraints gbcSet(int x, int y, int fill, int anchor, int width, int height, double weightx, double weighty, int ipadx, int ipady) {
        gbcInit();

        gridx = x;
        gridy = y;
        this.fill = fill;
        this.anchor = anchor;
        gridwidth = width;
        gridheight = height;
        this.weightx = weightx;
        this.weighty = weighty;
        this.ipadx = ipadx;
        this.ipady = ipady;

        return this;
    }

    /**
     * Configure and return the GridBagConstraints object.
     *
     * @param x       The x position of the component.
     * @param y       The y position of the component.
     * @param fill    The fill constraints.
     * @param anchor  The anchor of the component.
     * @param weightx The col fill weight.
     * @param weighty The row fill weight.
     * @return The <code>GridBagConstraints</code> object.
     */
    public GridBagConstraints gbcSet(int x, int y, int fill, int anchor, double weightx, double weighty) {
        gbcInit();

        gridx = x;
        gridy = y;
        this.fill = fill;
        this.anchor = anchor;
        this.weightx = weightx;
        this.weighty = weighty;

        return this;
    }

    /**
     * Set the default insets.
     *
     * @param defaultInsets The default insets.
     */
    public void setDefaultInsets(Insets defaultInsets) {
        this.defaultInsets = defaultInsets;
    }

    /**
     * Set the default insets.
     *
     * @param top    The top inset.
     * @param left   The left inset.
     * @param bottom The bottom inset.
     * @param right  The right inset.
     */
    public void setDefaultInsets(int top, int left, int bottom, int right) {
        defaultInsets = new Insets(top, left, bottom, right);
    }
}