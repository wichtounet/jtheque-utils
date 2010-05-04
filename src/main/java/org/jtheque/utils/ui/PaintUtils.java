package org.jtheque.utils.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

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
 * Utility class to paint with Java2D.
 * <p/>
 * The method tileStretchPaint has been recuperated from the Aerith Project.
 *
 * @author Baptiste Wicht
 * @author Romain Guy/Chet Haase
 */
public final class PaintUtils {
    /**
     * Utility class, cannot be instantiated.
     */
    private PaintUtils() {
        super();
    }

    /**
     * Init the base hints for painting.
     *
     * @param g2 The graphics to init.
     */
    public static void initHints(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    /**
     * Draw a translucent image.
     *
     * @param g2    The graphics to paint with.
     * @param image The image to paint.
     * @param x     The x position of the image.
     * @param y     The y position of the image.
     * @param alpha The alpha value of the image painting.
     */
    public static void drawAlphaImage(Graphics2D g2, BufferedImage image, int x, int y, float alpha) {
        drawAlphaImage(g2, image, x, y, image.getWidth(), image.getHeight(), alpha);
    }

    /**
     * Draw a translucent image with specified size.
     *
     * @param g2     The graphics to paint with.
     * @param image  The image to paint.
     * @param x      The x position of the image.
     * @param y      The y position of the image.
     * @param width  The width of the image.
     * @param height The height of the image.
     * @param alpha  The alpha value of the image painting.
     */
    public static void drawAlphaImage(Graphics2D g2, Image image, int x, int y, int width, int height, float alpha) {
        Composite oldComposite = g2.getComposite();

        g2.setComposite(AlphaComposite.SrcOver.derive(alpha));

        g2.drawImage(image, x, y, width, height, null);

        g2.setComposite(oldComposite);
    }

    /**
     * Draw a translucent image with specified source and destination rectangles.
     *
     * @param g2    The graphics to paint with.
     * @param image The image to paint.
     * @param dx1   the <i>x</i> coordinate of the first corner of the destination rectangle.
     * @param dy1   the <i>y</i> coordinate of the first corner of the destination rectangle.
     * @param dx2   the <i>x</i> coordinate of the second corner of the destination rectangle.
     * @param dy2   the <i>y</i> coordinate of the second corner of the destination rectangle.
     * @param sx1   the <i>x</i> coordinate of the first corner of the source rectangle.
     * @param sy1   the <i>y</i> coordinate of the first corner of the source rectangle.
     * @param sx2   the <i>x</i> coordinate of the second corner of the source rectangle.
     * @param sy2   the <i>y</i> coordinate of the second corner of the source rectangle.
     * @param alpha The alpha value of the image painting.
     */
    public static void drawAlphaImage(Graphics2D g2, Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, float alpha) {
        Composite oldComposite = g2.getComposite();

        g2.setComposite(AlphaComposite.SrcOver.derive(alpha));

        g2.drawImage(image, //Image
                dx1, dy1, dx2, dy2, //Destination
                sx1, sy1, sx2, sy2, null); //Source

        g2.setComposite(oldComposite);
    }

    /**
     * Draw a string.
     *
     * @param g2     The graphics to paint with.
     * @param string The string to paint.
     * @param x      The x position to paint from.
     * @param y      The y position to paint from.
     * @param font   The font to use.
     */
    public static void drawString(Graphics g2, String string, int x, int y, Font font) {
        Font oldFont = g2.getFont();

        g2.setFont(font);

        g2.drawString(string, x, y);

        g2.setFont(oldFont);
    }

    /**
     * Draw a string.
     *
     * @param g2     The graphics to paint with.
     * @param string The string to paint.
     * @param x      The x position to paint from.
     * @param y      The y position to paint from.
     * @param font   The font to use.
     * @param color  The color to use.
     */
    public static void drawString(Graphics g2, String string, int x, int y, Font font, Color color) {
        Color oldColor = g2.getColor();

        g2.setColor(color);

        drawString(g2, string, x, y, font);

        g2.setColor(oldColor);
    }

    /**
     * Fill a rectangle.
     *
     * @param g2     The graphics to paint with.
     * @param x      The x position to paint from.
     * @param y      The y position to paint from.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param color  The color to use.
     */
    public static void fillRect(Graphics g2, int x, int y, int width, int height, Color color) {
        Color oldColor = g2.getColor();

        g2.setColor(color);

        g2.fillRect(x, y, width, height);

        g2.setColor(oldColor);
    }

    /**
     * Draws an image on top of a component by doing a 3x3 grid stretch of the image
     * using the specified insets.
     *
     * @param g         The graphics object to use.
     * @param component The component.
     * @param image     The image.
     * @param insets    The insets.
     */
    public static void tileStretchPaint(Graphics g, Component component, BufferedImage image, Insets insets) {
        int left = insets.left;
        int right = insets.right;
        int top = insets.top;
        int bottom = insets.bottom;

        // top
        g.drawImage(image,
                0, 0, left, top,
                0, 0, left, top,
                null);
        g.drawImage(image,
                left, 0,
                component.getWidth() - right, top,
                left, 0,
                image.getWidth() - right, top,
                null);
        g.drawImage(image,
                component.getWidth() - right, 0,
                component.getWidth(), top,
                image.getWidth() - right, 0,
                image.getWidth(), top,
                null);

        // middle
        g.drawImage(image,
                0, top,
                left, component.getHeight() - bottom,
                0, top,
                left, image.getHeight() - bottom,
                null);
        g.drawImage(image,
                left, top,
                component.getWidth() - right, component.getHeight() - bottom,
                left, top,
                image.getWidth() - right, image.getHeight() - bottom,
                null);
        g.drawImage(image,
                component.getWidth() - right, top,
                component.getWidth(), component.getHeight() - bottom,
                image.getWidth() - right, top,
                image.getWidth(), image.getHeight() - bottom,
                null);

        // bottom
        g.drawImage(image,
                0, component.getHeight() - bottom,
                left, component.getHeight(),
                0, image.getHeight() - bottom,
                left, image.getHeight(),
                null);
        g.drawImage(image,
                left, component.getHeight() - bottom,
                component.getWidth() - right, component.getHeight(),
                left, image.getHeight() - bottom,
                image.getWidth() - right, image.getHeight(),
                null);
        g.drawImage(image,
                component.getWidth() - right, component.getHeight() - bottom,
                component.getWidth(), component.getHeight(),
                image.getWidth() - right, image.getHeight() - bottom,
                image.getWidth(), image.getHeight(),
                null);
    }
}
