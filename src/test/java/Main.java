/*
 * Testing Lab
 */
package test.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static String APPDIR = ".fsp_reloaded";
    public static String DBNAME = "test02.db";
    public static String PARENTDIR = System.getenv("USERPROFILE");
    public static File INSTALLPATH = Paths.get(PARENTDIR, APPDIR).toFile();
    public static File DBFULLPATH = Paths.get(PARENTDIR, APPDIR, DBNAME).toFile();
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

        //String sql = gogetstring();
        //executeBigSQL(sql);
        fileToString("src/test/resources/sqlite/create_tables.sql");
        fileToString("src/test/resources/sqlite/insert.sql");

    }

    private static void fileToString(String mypath) throws IOException, SQLException {
        //String mypath = "src/test/resources/sqlite/create_tables.sql";
        //String mypath = "Z:\\Dropbox\\LABO-DBX\\Netbeans-projects\\FoodShuffleLite\\src\\test\\resources\\sqlite\\create_tables.sql";

        String fileString = new String(Files.readAllBytes(Paths.get(mypath)), StandardCharsets.UTF_8);
        String Str = fileString.replaceAll("\r\n", "\n");
        List<String> request = new ArrayList<>();
        StringBuilder rqt = new StringBuilder();

        System.out.println("Return Value :");
        for (String retval : Str.split(";\n")) {
            System.out.println(retval);
            request.add(retval + ";");
            rqt.append(retval + ";");
        }

        for (String el : request) {
            System.out.println(el);
        }

        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
        // It's even easier in Java 8
        //System.out.print("Contents (Java 8) : ");
        //Files.lines(Paths.get(mypath), StandardCharsets.UTF_8).forEach(System.out::println);
    }

    private static void readSQLScriptAndExecuteQueries() throws IOException, SQLException {
        String mypath = "Z:\\Dropbox\\LABO-DBX\\Netbeans-projects\\FoodShuffleLite\\src\\test\\resources\\sqlite\\create_tables.sql";
        String fileString = new String(Files.readAllBytes(Paths.get(mypath)), StandardCharsets.UTF_8);
        //System.out.println("Contents (Java 7 with character encoding ) : " + fileString);

        System.out.println("Return Value :");

        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            for (String retval : fileString.split(";\n")) {
                System.out.println(retval);
                // create new table = execute sql
                //stmt.execute(retval);
                System.out.println("La requete a été exécutée.");
            }
            // It's even easier in Java 8
            //System.out.print("Contents (Java 8) : ");
            //Files.lines(Paths.get(mypath), StandardCharsets.UTF_8).forEach(System.out::println);
        }
    }

    public Main() {
        this.URL = "jdbc:sqlite:" + DBFULLPATH;
    }

}
