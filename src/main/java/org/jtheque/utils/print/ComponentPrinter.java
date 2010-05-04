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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

/**
 * The class permit to print a component.
 *
 * @author Baptiste Wicht
 */
final class ComponentPrinter implements Printable {
    private final Component component;

    /**
     * Construct a new <code>ComponentPrinter</code> with a specific <code>Component</code>.
     *
     * @param component The component to be printed
     */
    ComponentPrinter(Component component) {
        super();

        this.component = component;
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        int status = NO_SUCH_PAGE;

        if (pageIndex < PrintUtils.getTotalPageForComponent(pageFormat, component)) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pageFormat.getImageableX(),
                    pageFormat.getImageableY() - pageIndex * pageFormat.getImageableHeight());

            PrintUtils.setDoubleBuffered(false, component);
            component.paint(g2d);
            PrintUtils.setDoubleBuffered(true, component);

            status = PAGE_EXISTS;
        }

        return status;
    }
}
