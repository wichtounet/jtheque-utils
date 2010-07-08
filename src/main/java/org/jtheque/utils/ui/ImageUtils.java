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

import org.jtheque.utils.io.FileUtils;

import org.slf4j.LoggerFactory;

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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for image processing. This class support headless environment.
 *
 * @author Baptiste Wicht
 */
public final class ImageUtils {
    private static final float ALPHA_REFLECTION = 0.75f;

    /**
     * Construct a new ImageUtils. This constructor is private, all the methods are static.
     */
    private ImageUtils() {
        throw new AssertionError();
    }

    /**
     * Create a thumbnail of an image.
     *
     * @param image              The source image.
     * @param requestedThumbSize The requested size.
     *
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
     * Read an image from a file.
     *
     * @param file The file to read from.
     *
     * @return The buffered image from the file.
     */
    public static BufferedImage read(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            LoggerFactory.getLogger(ImageUtils.class).error("Unable to read image", e);
        }

        return null;
    }

    /**
     * Open an image from an input stream.
     *
     * @param stream The stream to read from.
     *
     * @return The buffered image from the stream.
     */
    public static BufferedImage read(InputStream stream) {
        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            LoggerFactory.getLogger(ImageUtils.class).error("Unable to read image", e);
        }

        return null;
    }

    /**
     * Create a compatible image.In headless mode, this method will return a simple BufferedImage of type ARGB.
     *
     * @param width  The width.
     * @param height The height.
     *
     * @return A compatible image.
     *
     * @see ImageUtils#isHeadless()
     */
    public static Image createCompatibleImage(int width, int height) {
        if (isHeadless()) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        return getGraphicsConfiguration().createCompatibleImage(width, height);
    }

    /**
     * Create a compatible image. In headless mode, this method will return a simple BufferedImage.
     *
     * @param width  The width.
     * @param height The height.
     * @param type   The type of the image.
     *
     * @return A compatible image.
     *
     * @see ImageUtils#isHeadless()
     */
    public static BufferedImage createCompatibleImage(int width, int height, int type) {
        if (isHeadless()) {
            return new BufferedImage(width, height, type);
        }

        return getGraphicsConfiguration().createCompatibleImage(width, height, type);
    }

    /**
     * Return the graphics configuration of the system.
     *
     * @return The graphics configuration of the environment or null if we are in headless mode.
     *
     * @see ImageUtils#isHeadless()
     */
    private static GraphicsConfiguration getGraphicsConfiguration() {
        if (isHeadless()) {
            return null;
        }

        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * Create a compatible image from an existing image. In headless mode, this method will not affect the image.
     *
     * @param image The image to make compatible.
     *
     * @return A compatible image filled with the base image.
     */
    public static BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = getGraphicsConfiguration();

        if (image.getColorModel().equals(gc.getColorModel()) || isHeadless()) {
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
     * Indicate if we are in a headless mode or normal.
     *
     * @return true if we are in headless mode else false.
     */
    public static boolean isHeadless() {
        return GraphicsEnvironment.isHeadless() || GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance();
    }

    /**
     * Create a reflection of the image.
     *
     * @param image The image to reflect.
     *
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
     *
     * @return The image.
     */
    public static BufferedImage openCompatibleImageFromClassPath(String path) {
        return openCompatibleImage(ImageUtils.class.getResourceAsStream(path));
    }

    /**
     * Open an image from the file system at the specified path.
     *
     * @param path The path to the image.
     *
     * @return The image or null if the file doesn't exist.
     */
    public static BufferedImage openCompatibleImageFromFileSystem(String path) {
        if (isHeadless()) {
            return read(new File(path));
        }

        try {
            return openCompatibleImage(FileUtils.asInputStream(path));
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(ImageUtils.class).error("Unable to open file", e);

            return null;
        }
    }

    /**
     * Open a compatible image form an input stream.
     *
     * @param stream The stream to use to open the image.
     *
     * @return The image.
     */
    public static BufferedImage openCompatibleImage(InputStream stream) {
        return createCompatibleImage(read(stream));
    }

    /**
     * Open a compatible image of the given file.
     *
     * @param file The file of the image.
     *
     * @return The image of the file.
     */
    public static BufferedImage openCompatibleImage(File file) {
        if (isHeadless()) {
            return read(file);
        }

        try {
            InputStream stream = FileUtils.asInputStream(file);

            return openCompatibleImage(stream);
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(ImageUtils.class).error("The specified file doesn't exist", e);

            return null;
        }
    }
}