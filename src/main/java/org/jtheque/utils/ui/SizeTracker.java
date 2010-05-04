package org.jtheque.utils.ui;

import javax.swing.JComponent;

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