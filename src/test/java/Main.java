/*
 * Testing Lab
 */
package test.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static String APPDIR = ".fsp_reloaded";
    public static String DBNAME = "test02.db";
    public static String PARENTDIR = System.getenv("USERPROFILE");
    public static File INSTALLPATH = Paths.get(PARENTDIR, APPDIR).toFile();
    public static File DBFULLPATH = Paths.get(PARENTDIR, APPDIR, DBNAME).toFile();

    /**
     *
     */
    public static String URL = "jdbc:sqlite:" + DBFULLPATH;

    /**
     * Test pour first setup.
     *
     * @param args
     * @throws java.io.FileNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        
        new testDBsetup().checkExistence();
        
        String sql = gogetstring();
        executeBigSQL(sql);
    }

    private static void executeBigSQL(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement();
        try {
            // create new table = execute sql
            stmt.execute(sql);
            System.out.println("La requete a été exécutée.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        conn.close();
    }

    private static String gogetstring() {
        //new testDBsetup().checkExistence();
        try {
            // InputStream is = new FileInputStream("..\\resources\\sqlite\\create_tables.sql");
            String mypath = "Z:\\Dropbox\\LABO-DBX\\Netbeans-projects\\FoodShuffleLite\\src\\test\\resources\\sqlite\\create_tables.sql";

            InputStream is = new FileInputStream(mypath);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            String fileAsString = sb.toString();
            //System.out.println("Contents : " + fileAsString);

            return fileAsString;

            // execute sql
            // SQLite Statement for table creation
        } catch (FileNotFoundException f) {
            System.out.println("File not found");
        } catch (IOException i) {
            System.out.println("IOException");
        }
        return null;
    }
}
