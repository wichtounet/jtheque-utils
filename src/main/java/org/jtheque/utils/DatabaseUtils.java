package org.jtheque.utils;

import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

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
 * An utility class for database use.
 *
 * @author Baptiste Wicht
 */
public final class DatabaseUtils {
    /**
     * Construct a new DatabaseUtils. This class is an utility class, it cannot be instantiated.
     */
    private DatabaseUtils() {
        super();
    }

    /**
     * Close the result set.
     *
     * @param rs The result set to close.
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LoggerFactory.getLogger(DatabaseUtils.class).error("Unable to close resultset", e);
            }
        }
    }

    /**
     * Close the statement.
     *
     * @param statement The statement to close.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LoggerFactory.getLogger(DatabaseUtils.class).error("Unable to close statement", e);
            }
        }
    }

    /**
     * Close the connection.
     *
     * @param connection The connection to close.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LoggerFactory.getLogger(DatabaseUtils.class).error("Unable to close connection", e);
            }
        }
    }

    /**
     * Return all the results of the specified column.
     *
     * @param rs     The result set.
     * @param column The column to get the data from.
     * @return A collection of Integer containing all the results of the specified column.
     */
    public static Collection<Integer> getAllIntResults(ResultSet rs, String column) {
        Collection<Integer> results = new ArrayList<Integer>(25);

        try {
            while (rs.next()) {
                results.add(rs.getInt(column));
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(DatabaseUtils.class).error("Unable to read result set", e);
        }

        return results;
    }
}
