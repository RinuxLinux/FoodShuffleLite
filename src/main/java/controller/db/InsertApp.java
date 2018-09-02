/*
 * http://www.sqlitetutorial.net/sqlite-java/insert/
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
public class InsertApp {

    /**
     * Connect to the database
     *
     * @return the Connection object
     */
    private Connection connect(String dbname) {

        // SQLite connection string
        String mypath = "Z:/Dropbox/LABO-DBX/new-myebooks/";
        String url = "jdbc:sqlite:" + mypath + dbname;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the warehouse table
     *
     * @param name
     * @param capacity
     */
    public void insert(String name, double capacity) {

        String sql = "INSERT INTO warehouse (name, capacity) VALUES (?, ?)";
        String dbname = "180811.db";

        try (
                Connection conn = this.connect(dbname);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
