package org.jtheque.utils.ui;

import javax.swing.JComponent;

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
 * A tracker for the size of a <code>JComponent</code>
 *
 * @author Baptiste Wicht
 */
public final class SizeTracker {
    private int width = -1;
    private int height = -1;

    private final JComponent component;

    /**
     * Construct a new SizeTracker.
     *
     * @param c The component to track the sizes.
     */
    public SizeTracker(JComponent c) {
        super();

        component = c;
    }

    /**
     * Update the cached size.
     */
    public void updateSize() {
        width = component.getWidth();
        height = component.getHeight();
    }

    /**
     * Indicate if the size has changed from the last org.jtheque.update.
     *
     * @return <code>true</code> if the size has changed else <code>false</code>.
     */
    public boolean hasSizeChanged() {
        return width != component.getWidth() || height != component.getHeight();
    }
}