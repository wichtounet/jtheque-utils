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

import org.apache.commons.logging.LogFactory;
import org.jtheque.utils.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for image processing.
 *
 * @author Baptiste Wicht
 */
public final class ImageUtils {
    private static final float ALPHA_REFLECTION = 0.75f;

    /**
     * Construct a new ImageUtils. This constructor is private, all the methods are static.
     */
    private ImageUtils() {
        super();
    }

    /**
     * Create a thumbnail of an image.
     *
     * @param image              The source image.
     * @param requestedThumbSize The requested size.
     * @return The thumbnail
     */
    public static BufferedImage createThumbnail(BufferedImage image, int requestedThumbSize) {
        float ratio = (float) image.getWidth() / (float) image.getHeight();
        int width = image.getWidth();
        boolean divide = requestedThumbSize < width;

        BufferedImage thumb = image;

        do {
            if (divide) {
                width /= 2;

                if (width < requestedThumbSize) {
                    width = requestedThumbSize;
                }
            } else {
                width *= 2;

                if (width > requestedThumbSize) {
                    width = requestedThumbSize;
                }
            }

            BufferedImage temp = createCompatibleImage(width, (int) (width / ratio), image.getTransparency());

            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();

            thumb = temp;
        } while (width != requestedThumbSize);

        return thumb;
    }

    /**
     * Open an image from an input stream.
     *
     * @param stream The stream to read from.
     * @return The buffered image from the stream.
     */
    public static BufferedImage read(InputStream stream) {
        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            LogFactory.getLog(ImageUtils.class).error("Unable to read image", e);
        }

        return null;
    }

    /**
     * Create a compatible image.
     *
     * @param width  The width.
     * @param height The height.
     * 
     * @return A compatible image.
     */
    public static Image createCompatibleImage(int width, int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height);
    }

    /**
     * Create a compatible image.
     *
     * @param width  The width.
     * @param height The height.
     * @param type   The type of the image.
     * @return A compatible image.
     */
    public static BufferedImage createCompatibleImage(int width, int height, int type) {
        return getGraphicsConfiguration().createCompatibleImage(width, height, type);
    }

    /**
     * Return the graphics configuration of the system.
     *
     * @return The graphics configuration of the environment.
     */
    private static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * Create a compatible image from an existing image.
     *
     * @param image The image to make compatible.
     * @return A compatible image filled with the base image.
     */
    public static BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = getGraphicsConfiguration();

        if (image.getColorModel().equals(gc.getColorModel())) {
            return image;
        } else {
            BufferedImage compatibleImage = createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

            Graphics g = compatibleImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            return compatibleImage;
        }
    }

    /**
     * Create a reflection of the image.
     *
     * @param image The image to reflect.
     * @return The reflection image.
     */
    public static BufferedImage createReflection(BufferedImage image) {
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(image.getWidth(), height * 2, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = result.createGraphics();

        // Paints original image
        g2.drawImage(image, 0, 0, null);

        // Paints mirrored image
        g2.scale(1.0, -1.0);
        g2.drawImage(image, 0, -height - height, null);
        g2.scale(1.0, -1.0);

        // Move to the origin of the clone
        g2.translate(0, height);

        // Creates the alpha mask
        Paint mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, ALPHA_REFLECTION),
                0, height / 2, new Color(1.0f, 1.0f, 1.0f, 0.0f));

        g2.setPaint(mask);

        // Sets the alpha composite
        g2.setComposite(AlphaComposite.DstIn);

        // Paints the mask
        g2.fillRect(0, 0, image.getWidth(), height);

        g2.dispose();

        return result;
    }

    /**
     * Open a image from the classpath at the specified path.
     *
     * @param path The path to the image.
     * @return The image.
     */
    public static BufferedImage openCompatibleImageFromClassPath(String path) {
        return openCompatibleImage(ImageUtils.class.getResourceAsStream(path));
    }

    /**
     * Open an image from the file system at the specified path.
     *
     * @param path The path to the image.
     * @return The image or null if the file doesn't exist.
     */
    public static BufferedImage openCompatibleImageFromFileSystem(String path) {
        try {
            return openCompatibleImage(FileUtils.asInputStream(path));
        } catch (FileNotFoundException e) {
            LogFactory.getLog(ImageUtils.class).error("Unable to open file", e);

            return null;
        }
    }

    /**
     * Open a compatible image form an input stream.
     *
     * @param stream The stream to use to open the image.
     * @return The image.
     */
    public static BufferedImage openCompatibleImage(InputStream stream) {
        return createCompatibleImage(read(stream));
    }
}