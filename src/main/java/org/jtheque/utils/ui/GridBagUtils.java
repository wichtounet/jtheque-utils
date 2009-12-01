package org.jtheque.utils.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

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