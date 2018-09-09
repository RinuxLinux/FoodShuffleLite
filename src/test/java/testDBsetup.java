/*
 * Test : hard-coding db setup.
 */
package test.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author re
 */
public class testDBsetup {

    public static String APPDIR = ".fsp_reloaded";
    public static String DBNAME = "test02.db";
    public static String PARENTDIR = System.getenv("USERPROFILE");
    public static File INSTALLPATH = Paths.get(PARENTDIR, APPDIR).toFile();
    public static File DBFULLPATH = Paths.get(PARENTDIR, APPDIR, DBNAME).toFile();
    public static String URL = "jdbc:sqlite:" + DBFULLPATH;

    /**
     * Check existence of install dir and db file.
     *
     * @throws java.sql.SQLException
     * @since Java 7
     */
    public void checkExistence() throws SQLException {

        // Install path exists ?
        if (!INSTALLPATH.exists()) {
            System.out.println(INSTALLPATH + " does not exist and will be created.");
            // create dirs
            // TODO: try catch PermissionError
            INSTALLPATH.mkdirs();
            try {
                // set folder attr to hidden (dos only)  // Java 7+
                Files.setAttribute(INSTALLPATH.toPath(), "dos:hidden", true);
            } catch (IOException ex) {
                Logger.getLogger(testDBsetup.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Install dir has been found at " + INSTALLPATH + ".");
        }

        // Check existence and integrity of db
        if (!DBFULLPATH.exists()) {
            System.out.println("Database " + DBNAME + " does not exists and will be created.");
            // db is created when connected to. (?)
            Connection conn = connect(URL);

            // populate db
            // 1. creer les tables via code source
            // 2. exporter les schema dans un script sql
            // 3. ???
            disconnect(connect(URL));

        } else {
            System.out.println("Database " + DBNAME + " has been found.");
            // TODO: test integrité
        }

        // check db integrity
        String URL = "jdbc:sqlite:" + DBFULLPATH;

    }

    /**
     * Create table.
     */
    private static void createTables() {

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
        try (Connection conn = DriverManager.getConnection(URL);
                Statement stmt = conn.createStatement()) {
            // create new table = execute sql
            //int count = 0;
            for (int i = 0; i < request.size(); i++) {
                stmt.execute(request.get(i));
                System.out.println("La requete " + i + " a été exécutée.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connect to the database.
     *
     * @return the Connection object
     */
    private Connection connect(String url) {

        // SQLite connection string
        //String dbpath = "Z:/Dropbox/LABO-DBX/new-myebooks/";
        //String dbname = "180811.db";
        //String url = "jdbc:sqlite:" + dbpath + dbname;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to database has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Close given Connection object.
     *
     * @param conn the Connection objet connected to sqlite db.
     */
    private void disconnect(Connection conn) {
        try {
            conn.close();
            System.out.println("Connection to database has been terminated.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean executeDBScripts(String aSQLScriptFilePath, Statement stmt) throws IOException, SQLException {
        boolean isScriptExecuted = false;
        try {
            StringBuilder sb;
            try (BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath))) {
                String str;
                sb = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    StringBuilder append = sb.append(str).append("\n ");
                }
            }
            stmt.executeUpdate(sb.toString());
            isScriptExecuted = true;
        } catch (IOException | SQLException e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath + ". The error is" + e.getMessage());
        }
        return isScriptExecuted;
    }

}
