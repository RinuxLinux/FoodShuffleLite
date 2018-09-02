/*
 * Read a Binary Large OBject
 * http://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
 */
package net.sqlitetutorial;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Read a Binary Large OBject
 *
 * @author re
 * @link http://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
 */
public class BLOBApp {

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
     * Read the file and returns the byte array
     *
     * @param file
     * @return the bytes of the file
     */
    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    /**
     * Update picture for a specific material
     *
     * @param materialId
     * @param filename
     */
    public void updatePicture(int materialId, String filename) {
        // update sql
        String updateSQL = "UPDATE material "
                + "SET picture = ? "
                + "WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pst = conn.prepareStatement(updateSQL)) {
            // set paramaters
            pst.setBytes(1, readFile(filename));
            pst.setInt(2, materialId);

            pst.executeUpdate();
            System.out.println("Stored the file in the BLOB column.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Read the pic file and insert into the material master table
     *
     * @param materialId
     * @param filename
     */
    public void readPicture(int materialId, String filename) {
        // update sql
        String selectSQL = "SELECT picture FROM material WHERE id = ?";
        ResultSet rs = null;
        FileOutputStream fos = null;
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = connect();
            pst = conn.prepareStatement(selectSQL);
            pst.setInt(1, materialId);
            rs = pst.executeQuery();

            // write binary stream into file
            File file = new File(filename);
            fos = new FileOutputStream(file);

            System.out.println("Writing BLOB to file " + file.getAbsolutePath());
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("picture");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    fos.write(buffer);
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }

                if (conn != null) {
                    conn.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (SQLException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void mainBlob(String[] args) {
        // insert blob
        BLOBApp app = new BLOBApp();
        app.updatePicture(1, "Z:\\temp\\HP_Laptop.jpg");

        // read blob and write data into a file
        app.readPicture(1, "Z:\\Temp\\laptop.jpg");
    }
}
