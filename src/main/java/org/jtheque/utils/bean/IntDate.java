package org.jtheque.utils.bean;

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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * This class represent an int data in format yyyymmdd.
 *
 * @author Baptiste Wicht
 */
public final class IntDate implements Serializable, Comparable<IntDate> {
    private static final long serialVersionUID = -5493916511728423774L;
    private static final int DATE_STR_LENGTH = 20;

    /**
     * This internal class contains all the different fields of an IntDate.
     *
     * @author Baptiste Wicht
     */
    public static final class Fields {
        public static final int MONTH = Calendar.MONTH;
        public static final int DAY = Calendar.DAY_OF_MONTH;
        public static final int YEAR = Calendar.YEAR;

        /**
         * Create a new Fields. This class isn't instanciable
         */
        private Fields() {

        }

        /**
         * Test the validity of a field.
         *
         * @param field The field to test.
         * @return <code>true</code> if the field is valid else <code>false</code>.
         */
        public static boolean isValid(int field) {
            return field == MONTH || field == DAY || field == YEAR;
        }
    }

    private static final IntDate TODAY_DATE = new IntDate(Calendar.getInstance());

    private final DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private final Calendar calendar;

    /**
     * Construct a new int date from the int representation of the date.
     *
     * @param date The int representation (yyyymmdd) of the date.
     */
    public IntDate(int date) {
        super();

        calendar = Calendar.getInstance();
        calendar.clear();

        String dateString = Integer.toString(date);

        int day = Integer.parseInt(dateString.substring(6, 8));
        int month = Integer.parseInt(dateString.substring(4, 6)) - 1;
        int year = Integer.parseInt(dateString.substring(0, 4));

        calendar.set(year, month, day);
    }

    /**
     * Construct a new date from a Calendar instance. Not all the precision of the Calendar object will be used.
     *
     * @param calendar The calendar instance of the date.
     */
    private IntDate(Calendar calendar) {
        super();

        this.calendar = calendar;
    }

    /**
     * Construct a new IntDate from an another one.
     *
     * @param value The another IntDate.
     */
    public IntDate(IntDate value) {
        super();

        calendar = (Calendar) value.calendar.clone();
    }

    /**
     * Return the day.
     *
     * @return The day.
     */
    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Return the year.
     *
     * @return The year.
     */
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Return the month.
     *
     * @return The month.
     */
    public int getMonth() {
        //We add 1 because calendar starts at 0 for the months
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Add a value to specified field of the date.
     *
     * @param field The field to add the value to.
     * @param toAdd The value to add.
     * @throws IllegalArgumentException if the specified field is not valid.
     */
    public void add(int field, int toAdd) {
        if (Fields.isValid(field)) {
            calendar.add(field, toAdd);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Set the value of a specified field of the date.
     *
     * @param field The field to set the value to.
     * @param value The value to set to the field.
     */
    public void set(final int field, final int value) {
        if (Fields.isValid(field)) {
            if (field == Fields.MONTH) {
                calendar.set(Fields.MONTH, value - 1);
            } else {
                calendar.set(field, value);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Return the <code>String</code> representation of the date.
     *
     * @return A <code>String</code> representing the date.
     */
    public String getStrDate() {
        StringBuilder builder = new StringBuilder(DATE_STR_LENGTH);

        builder.append(getYear());

        if (getMonth() < 10) {
            builder.append('0');
        }

        builder.append(getMonth());

        if (getDay() < 10) {
            builder.append('0');
        }

        builder.append(getDay());

        return builder.toString();
    }

    /**
     * Return the IntDate of today.
     *
     * @return The today IntDate
     */
    public static IntDate today() {
        return TODAY_DATE;
    }

    /**
     * Return the int value of the date.
     *
     * @return A int representation of the date
     */
    public int intValue() {
        return Integer.parseInt(getStrDate());
    }

    @Override
    public int compareTo(IntDate o) {
        if (getYear() > o.getYear()) {
            return 1;
        } else if (getYear() < o.getYear()) {
            return -1;
        }

        if (getMonth() > o.getMonth()) {
            return 1;
        } else if (getMonth() < o.getMonth()) {
            return -1;
        }

        if (getDay() > o.getDay()) {
            return 1;
        } else if (getDay() < o.getDay()) {
            return -1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntDate other = (IntDate) o;

        return calendar.equals(other.calendar);

    }

    @Override
    public int hashCode() {
        return calendar.hashCode();
    }

    @Override
    public String toString() {
        return format.format(calendar.getTime());
    }
}