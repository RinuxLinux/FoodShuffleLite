/*
 * http://www.sqlitetutorial.net/sqlite-java/select/
 */
package net.sqlitetutorial;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author re
 */
public class SelectApp {

    /**
     * Connect to the database
     *
     * @return the Connection object
     */
    public Connection connect() {
        // SQLite connection string
        String dbpath = "Z:/Dropbox/LABO-DBX/new-myebooks/";
        String dbname = "180811.db";
        String url = "jdbc:sqlite:" + dbpath + dbname;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Select all rows in the warehouse table
     *
     */
    public void selectAll() {

        String sql = "SELECT * FROM warehouse";

        try (
                Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Execute request SELECT ... FROM warehouse WHERE ...
     *
     * @param capacity capacity to test is greater than the one in db
     */
    public void getCapacityGreaterThan(double capacity) {

        String sql = "SELECT id, name, capacity "
                + " FROM warehouse WHERE capacity > ?";

        try (
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the value 
            pstmt.setDouble(1, capacity);

            // 
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            System.out.println("Resultat de la requete :\n" + sql);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t"
                        + rs.getString("name") + "\t"
                        + rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
