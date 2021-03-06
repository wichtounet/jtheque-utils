package org.jtheque.utils.bean;

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

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Locale;
import java.util.Map;

/**
 * An international string.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class InternationalString {
    private final Map<String, String> resources;

    /**
     * Construct a new InternationalString.
     *
     * @param resources The internationalized values.
     */
    public InternationalString(Map<String, String> resources) {
        super();

        this.resources = CollectionUtils.copyOf(resources);
    }

    @Override
    public String toString() {
        return resources.get(Locale.getDefault().getLanguage());
    }
}