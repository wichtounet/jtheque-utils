package org.jtheque.utils.bean;

import org.jtheque.utils.Constants;

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
 * This class represent a duration of time. A Duration is a period of hours and minutes.
 *
 * @author Baptiste Wicht
 */
public final class Duration {
    private int minutes;
    private int hours;

    /**
     * Construct a new duration with a certain number of hours and a certain number of minutes.
     *
     * @param hours   The hours of the duration
     * @param minutes The minutes of the duration
     */
    private Duration(int hours, int minutes) {
        super();

        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * Construct a new duration with only minutes. If there is more than 60 minutes, we dispatch the
     * time to the hours.
     *
     * @param minutes The minutes of the duration
     */
    public Duration(int minutes) {
        this(minutes / Constants.MINUTES_IN_AN_HOUR, minutes % Constants.MINUTES_IN_AN_HOUR);
    }

    /**
     * Construct a new empty duration.
     */
    public Duration() {
        this(0, 0);
    }

    /**
     * Returns the hours of the duration.
     *
     * @return The number of hours in the duration
     */
    public int getHours() {
        return hours;
    }

    /**
     * Set the hours of the duration.
     *
     * @param hours The new number of hours in the duration
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * Returns the minutes of the duration.
     *
     * @return The number of minutes in the duration
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Set the minutes of the duration.
     *
     * @param minutes The new number of minutes
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return hours + ":" + minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Duration duration = (Duration) o;

        if (hours != duration.hours) {
            return false;
        }

        return minutes == duration.minutes;

    }

    @Override
    public int hashCode() {
        return Constants.HASH_CODE_START +
                Constants.HASH_CODE_PRIME * minutes +
                Constants.HASH_CODE_PRIME * hours;
    }
}