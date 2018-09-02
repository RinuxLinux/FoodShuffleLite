/*
 * http://www.sqlitetutorial.net/sqlite-java/delete/
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
public class DeleteApp {

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
     * Delete from table where id = ?
     *
     * @param id id to be deleted
     */
    public void delete(int id) {

        String sql = "DELETE FROM warehouse WHERE id = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);

            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("L'entrée d'id " + id + " a été supprimée avec succès.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
