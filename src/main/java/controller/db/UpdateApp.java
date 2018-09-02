/*
 * http://www.sqlitetutorial.net/sqlite-java/update/
 */
package net.sqlitetutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author re
 */
public class UpdateApp {

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {

        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void update(int id, String name, double capacity) {
        /**
         * Update data of a warehouse specified by the id
         *
         * @param id
         * @param name name of the warehouse
         * @param capacity capacity of the warehouse
         */
        
        String sql = "UPDATE warehouses SET name = ? , "
                + "capacity = ? "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.setInt(3, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
