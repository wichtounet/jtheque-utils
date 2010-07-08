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

import org.jtheque.utils.io.FileUtils;

import org.slf4j.LoggerFactory;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.RepaintManager;
import javax.swing.text.JTextComponent;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Window;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;

/**
 * This class provide utility to print.
 *
 * @author Baptiste Wicht
 */
public final class PrintUtils {
    /* Variables */
    private static final Paper A4_PAPER = new A4();

    /**
     * Construct a new <code>PrintUtils</code>. This class is an utils code
     */
    private PrintUtils() {
        throw new AssertionError();
    }

    /**
     * Print a String.
     *
     * @param toPrint The String to print
     */
    public static void printString(String toPrint) {
        Window frame = new JFrame();

        JTextComponent area = new JTextArea();
        area.setText(toPrint);

        frame.add(area);
        frame.pack();

        printComponent(area);

        frame.dispose();
    }

    /**
     * Print the component.
     *
     * @param component the component to print
     */
    private static void printComponent(Component component) {
        Printable printer = new ComponentPrinter(component);

        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printJob.defaultPage();

        pageFormat.setPaper(A4_PAPER);

        printJob.setPrintable(printer, pageFormat);

        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException pe) {
                LoggerFactory.getLogger(PrintUtils.class).debug("Unable to print", pe);
            }
        }
    }

    /**
     * Print a list of <code>String</code>.
     *
     * @param list The list of <code>String</code> we want to print
     */
    public static void printArrayString(Collection<String> list) {
        PrintRequestAttributeSet request = new HashPrintRequestAttributeSet();

        request.add(OrientationRequested.LANDSCAPE);

        PrinterJob job = PrinterJob.getPrinterJob();

        job.setPageable(new ArrayPrinter(list));

        if (job.printDialog(request)) {
            try {
                job.print();
            } catch (PrinterException pe) {
                LoggerFactory.getLogger(PrintUtils.class).debug("Unable to print", pe);
            }
        }
    }

    /**
     * Modify the state of the double buffering on the component.
     *
     * @param doubleBuffer The new state of double buffering
     * @param component    The component for which we want to change the state of double buffering
     */
    static void setDoubleBuffered(boolean doubleBuffer, Component component) {
        RepaintManager currentManager = RepaintManager.currentManager(component);
        currentManager.setDoubleBufferingEnabled(doubleBuffer);
    }

    /**
     * Return the total number of pages for the component .
     *
     * @param pgFormat  The <code>PageFormat</code> in which we want to print
     * @param component The component to print
     *
     * @return The number of pages
     */
    static int getTotalPageForComponent(PageFormat pgFormat, Component component) {
        int nbPages;

        double heightDoc = component.getHeight();
        double heightPg = pgFormat.getImageableHeight();

        nbPages = (int) (heightDoc / heightPg);

        if (nbPages == 0) {
            nbPages = 1;
        }

        return nbPages;
    }

    /**
     * Return the necessary number of pages to print a list of lines.
     *
     * @param lines      The number of lines
     * @param metrics    The <code>FontMetrics</code> of the component
     * @param pageFormat The PageFormat in which we want to print
     * @param increment  The space between the lines
     *
     * @return The necessary number of pages
     */
    public static int getTotalPageForList(int lines, FontMetrics metrics, PageFormat pageFormat, int increment) {
        int nbPages;

        double heightDoc = lines * (metrics.getHeight() + increment);
        double heightPg = pageFormat.getImageableHeight();

        nbPages = (int) (heightDoc / heightPg);

        if (nbPages == 0) {
            nbPages = 1;
        }

        return nbPages;
    }

    /**
     * Print each line of the stream.
     *
     * @param stream The stream.
     */
    public static void printLineFiles(InputStream stream) {
        printArrayString(FileUtils.getLinesOf(stream));
    }

    public static void printLineFiles(File file) {
        printArrayString(FileUtils.getLinesOf(file));
    }
}