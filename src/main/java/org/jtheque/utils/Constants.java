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

/**
 * Some utility constants.
 *
 * @author Baptiste Wicht
 */
public final class Constants {
    public static final int HASH_CODE_START = 17;
    public static final int HASH_CODE_PRIME = 37;
    public static final int MINUTES_IN_AN_HOUR = 60;

    /**
     * This class is an utility class, not instanciable.
     */
    private Constants() {
        super();
    }
}
