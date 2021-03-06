package org.jtheque.utils.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

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
 * Some utilities to Graphics.
 *
 * @author Baptiste Wicht
 */
public final class GraphicsUtils {
    /**
     * Utility class, not instantiable.
     */
    private GraphicsUtils() {
        throw new AssertionError();
    }

    /**
     * Set hints of the Graphics.
     *
     * @param g The graphics.
     */
    public static void setFilthyHints(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * Paint a filthy panel.
     *
     * @param g      The graphics.
     * @param width  The width of the panel. 
     * @param height The height of the panel.
     */
    public static void paintFilthyPanel(Graphics g, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;

        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));

        g2.setColor(Color.white);

        RoundRectangle2D background;
        background = new RoundRectangle2D.Double(3.0, 3.0,
                (double) width - 10.0 - 3.0,
                (double) height - 6.0,
                12, 12);

        g2.fill(background);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3.0f));
        g2.draw(background);
        g2.setStroke(stroke);

        g2.setComposite(composite);
    }
}