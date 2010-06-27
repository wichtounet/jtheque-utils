package org.jtheque.utils.bean;

import java.util.ArrayList;
import java.util.List;

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

public class EqualsBuilder {
    private final Object o1;
    private final Object o2;

    private final List<Object> fields1 = new ArrayList<Object>(5);
    private final List<Object> fields2 = new ArrayList<Object>(5);

    private EqualsBuilder(Object o1, Object o2) {
        super();

        this.o1 = o1;
        this.o2 = o2;
    }

    public static EqualsBuilder newBuilder(Object o1, Object o2){
        return new EqualsBuilder(o1, o2);
    }

    public EqualsBuilder addField(Object f1, Object f2){
        fields1.add(f1);
        fields2.add(f2);

        return this;
    }

    public boolean areEquals() {
        if (o1 == o2) {
            return true;
        }

        if (EqualsUtils.areObjectIncompatible(o1, o2)) {
            return false;
        }

        for(int i = 0; i < fields1.size(); i++){
            if(EqualsUtils.areNotEquals(fields1.get(i), fields2.get(i))){
                return false;
            }
        }

        return true;
    }
}