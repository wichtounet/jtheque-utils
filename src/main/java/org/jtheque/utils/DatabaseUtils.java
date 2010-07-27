package org.jtheque.utils;

import org.jtheque.utils.collections.CollectionUtils;

import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

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
 * An utility class for database use.
 *
 * @author Baptiste Wicht
 */
public final class DatabaseUtils {
    /**
     * Construct a new DatabaseUtils. This class is an utility class, it cannot be instantiated.
     */
    private DatabaseUtils() {
        throw new AssertionError();
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
     *
     * @return A collection of Integer containing all the results of the specified column.
     */
    public static Collection<Integer> getAllIntResults(ResultSet rs, String column) {
        Collection<Integer> results = CollectionUtils.newList(25);

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
