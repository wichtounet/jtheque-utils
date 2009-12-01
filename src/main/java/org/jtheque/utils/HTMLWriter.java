package org.jtheque.utils;

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

import java.io.Closeable;
import java.io.PrintWriter;

/**
 * A HTML Writer.
 *
 * @author Baptiste Wicht
 */
public final class HTMLWriter implements Closeable {
    private final PrintWriter writer;

    /**
     * Construct a new HTML Writer.
     *
     * @param writer The print writer in which we write.
     */
    public HTMLWriter(PrintWriter writer) {
        super();

        this.writer = writer;
    }

    @Override
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }

    /**
     * Writer an HTML Header.
     *
     * @param title The title of the page.
     */
    public void writeHeader(String title) {
        writer.println("<html><head><title>" + title + "</title></head><body>");
    }

    /**
     * Write the footer.
     */
    public void writeFooter() {
        writer.println("</body></html>");
    }

    /**
     * Write a Hx title.
     *
     * @param h    The level of the title.
     * @param text The text of the title part.
     */
    public void writeHx(int h, String text) {
        writer.println("<h" + h + '>' + text + "</h" + h + '>');
    }

    /**
     * Write a table.
     *
     * @param width   The width of the table.
     * @param borders The border configuration.
     * @param widths  The widths of the columns.
     * @param headers The headers of the table.
     * @param data    The data of the table.
     */
    public void writeTable(int width, String borders, int[] widths,
                           String[] headers, Iterable<Iterable<String>> data) {
        writer.println("<table width=\"" + width + "\" border=\"" + borders + "\">");

        writer.println("<tr>");

        int i = 0;
        for (String header : headers) {
            writer.println("<th width=\"" + widths[i] + "%\">" + header + "</th>");
            i++;
        }

        writer.println("</tr>");

        for (Iterable<String> row : data) {
            writer.println("<tr>");

            for (String column : row) {
                writer.println("<td>" + column + "</td>");
            }

            writer.println("</tr>");
        }

        writer.println("</table>");
    }
}