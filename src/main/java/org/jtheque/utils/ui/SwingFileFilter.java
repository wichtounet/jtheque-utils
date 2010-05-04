package org.jtheque.utils.ui;

import org.jtheque.utils.io.SimpleFilter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

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
 * A file chooser filter who use an IO filter to filter the file chooser.
 *
 * @author Baptiste Wicht
 */
public final class SwingFileFilter extends FileFilter {
    private final SimpleFilter filter;

    /**
     * Construct a new SwingFileFilter with a SimpleFilter.
     *
     * @param filter The filter to use.
     */
    public SwingFileFilter(SimpleFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean accept(File f) {
        return filter.accept(f);
    }

    @Override
    public String getDescription() {
        return filter.getDescription();
    }
}
