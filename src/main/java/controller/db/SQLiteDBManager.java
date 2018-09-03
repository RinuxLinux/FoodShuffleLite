/*
 * http://www.sqlitetutorial.net/sqlite-java/
 */
package main.java.controller.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author re
 */
public class SQLiteDBManager {

    /**
     * Connect to a database
     *
     * @param url e.g "jdbc:sqlite:" + dbpath + dbname;
     */
    public void connect(String url) {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to database has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connect to a database.
     *
     * @param filename the database file name
     */
    public static void createNewDatabase(String mypath) {

        //String mypath = "Z:/Dropbox/LABO-DBX/new-myebooks/";
        String url = "jdbc:sqlite:" + mypath;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The Driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in given database.
     * TODO: factoriser noms db et table
     *
     * @usage obj.createNewTable("dbname.db")
     *
     * @param dbname name of database e.g "test.db"
     */
    public static void createNewTable(String dbname) {

        // SQLite connection string
        String mypath = "Z:/Dropbox/LABO-DBX/new-myebooks/";
        String url = "jdbc:sqlite:" + mypath + dbname;

        // SQLite Statement for table creation
        String sql = "CREATE TABLE IF NOT EXISTS warehouse (\n"
                + "id integer PRIMARY KEY,\n"
                + "name text NOT NULL,\n"
                + "capacity real\n"
                + ");";

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create new table = execute sql
            stmt.execute(sql);
            System.out.println("La requete a été exécutée.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
