package org.jtheque.utils;

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