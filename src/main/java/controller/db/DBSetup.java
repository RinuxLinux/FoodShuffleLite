/*
 *
 */
package main.java.controller.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author re
 */
public class DBSetup {

    /**
     * Create table
     */
    private static void createTables() {

        // SQLite connection string
        String mypath = "Z:/Dropbox/LABO-DBX/new-myebooks/";
        String dbname = "180811.db";
        String url = "jdbc:sqlite:" + mypath + dbname;

        // SQLite Statement for table creation
        List<String> request = new ArrayList<>();

        request.add("CREATE TABLE IF NOT EXISTS warehouse (\n"
                + "id integer PRIMARY KEY,\n"
                + "name text NOT NULL,\n"
                + "capacity real\n"
                + ");\n");
        request.add("CREATE TABLE IF NOT EXISTS material (\n"
                + " id integer PRIMARY KEY,\n"
                + " description text NOT NULL,\n"
                + " picture blob\n"
                + ");\n");
        request.add("CREATE TABLE IF NOT EXISTS inventory (\n"
                + " warehouse_id integer,\n"
                + " material_id integer,\n"
                + " qty real,\n"
                + " PRIMARY KEY (warehouse_id, material_id),\n"
                + " FOREIGN KEY (warehouse_id) REFERENCES warehouses (id),\n"
                + " FOREIGN KEY (material_id) REFERENCES materials (id)\n"
                + ");");

        //System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create new table = execute sql
            //int count = 0;
            for (int i=0; i<request.size(); i++) {
                stmt.execute(request.get(i));
                System.out.println("La requete " + i + " a été exécutée.");
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connect to the database
     *
     * @return the Connection object
     */
    private Connection connect() {

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
     * Executing connect() and createTables()
     */
    public void setup() {

        connect();
        createTables();
        System.out.println("Tables created.");

    }

}
