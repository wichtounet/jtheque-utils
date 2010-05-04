package org.jtheque.utils.print;

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

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class permit to print an array of lines.
 *
 * @author Baptiste Wicht
 */
final class ArrayPrinter implements Pageable {
    private final List<String> list;
    private int nbreElementsByPage;

    private final int numberOfPages;
    private final PageFormat format;

    private static final int LINE_HEIGHT = 14;
    private static final int HEIGHT_MARGIN = 150;

    /**
     * Construct a new <code>ArrayPrinter</code> with a list of lines.
     *
     * @param list The list of the lines we want to print
     */
    ArrayPrinter(Collection<String> list) {
        super();

        this.list = new ArrayList<String>(list);

        format = new PageFormat();
        format.setOrientation(PageFormat.PORTRAIT);
        format.setPaper(new A4());

        nbreElementsByPage = (int) Math.floor((format.getImageableHeight() - HEIGHT_MARGIN) / LINE_HEIGHT);
        numberOfPages = list.size() / nbreElementsByPage;
    }

    @Override
    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public PageFormat getPageFormat(int pageIndex) {
        return format;
    }

    @Override
    public Printable getPrintable(int pageIndex) {
        if (pageIndex > numberOfPages) {
            throw new IndexOutOfBoundsException();
        }

        int start = nbreElementsByPage * pageIndex;
        int stop = nbreElementsByPage * (pageIndex + 1) - 1;

        stop = Math.max(stop, list.size());

        return new ArrayPrintable(start, stop);
    }

    /**
     * A printable for an array of lines.
     *
     * @author Baptiste Wicht
     */
    private final class ArrayPrintable implements Printable {
        private int start;
        private final int stop;

        private static final int MARGIN = 75;
        private static final int FONT_SIZE = 12;

        /**
         * Construct a new ArrayPrintable.
         *
         * @param start The start index.
         * @param stop  The end index.
         */
        private ArrayPrintable(int start, int stop) {
            super();

            this.start = start;
            this.stop = stop;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            FontMetrics metrics = graphics.getFontMetrics();

            graphics.setFont(graphics.getFont().deriveFont(FONT_SIZE));

            int increment = metrics.getHeight() + 2;

            if (nbreElementsByPage == 0) {
                double heightPg = pageFormat.getImageableHeight();
                nbreElementsByPage = (int) (heightPg / increment);
            }

            if (pageIndex >= numberOfPages) {
                return NO_SUCH_PAGE;
            } else {
                int vertical = MARGIN;

                while (start <= stop) {
                    vertical += increment;
                    graphics.drawString(list.get(start), MARGIN, vertical);
                    start++;
                }

                return PAGE_EXISTS;
            }
        }
    }
}
