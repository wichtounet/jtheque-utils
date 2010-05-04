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
