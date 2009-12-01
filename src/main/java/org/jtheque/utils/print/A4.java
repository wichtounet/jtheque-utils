package org.jtheque.utils.print;

import java.awt.print.Paper;

/**
 * Represents an A4 Sheet.
 *
 * @author Baptiste Wicht
 */
final class A4 extends Paper {
    private static final double WIDTH = 25.4;
    private static final int HEIGHT = 72;
    private static final double MARGIN = 10.0;

    /**
     * Construct a new <code>A4</code>.
     */
    A4() {
        super();

        double left = MARGIN * HEIGHT / WIDTH;
        setImageableArea(left, 0.0, getWidth() - 2 * left, getHeight());
    }
}
