/*
 * Testing Lab
 */
package test.java;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import main.java.controller.db.SQLiteDBManager;

public class Main {

    /**
     * Test pour first setup.
     */
    public String locpath = System.getenv("LOCALAPPDATA");
    public String dirname = "FSP Reloaded";
    public String dbname = "test01.db";

    private static void testCreateTables() {

        // SQLite connection string
        String url = "jdbc:sqlite:" + DBpath + dbname;

        // SQLite Statement for table creation
        List<String> request = new ArrayList<>();

        // TABLE designation ex-Recette
        request.add("CREATE TABLE IF NOT EXISTS designation (\n"
                + "   id int PRIMARY KEY NOT NULL,\n"
                + "   nomPlat varchar(250),\n"
                + "   regime varchar(250)\n"
                + ");\n");


    public static void main(String[] args) {
        String locpath = System.getenv("LOCALAPPDATA");
        String dirname = "FSP Reloaded";
        String dbname = "test01.db";

        File dir0 = new File(locpath);

        Path dpath = Paths.get(locpath, dirname);
        Path fpath = Paths.get(locpath, dirname, dbname);

        System.out.println(dpath);
        System.out.println(fpath);
        System.out.println(fpath.toFile().exists());

        // check a few things ...
        boolean create_locpath = !dir0.exists();
        boolean create_db = !fpath.toFile().exists();
        boolean create_wdir = !dpath.toFile().exists();
        boolean locpath_isWriteable = dir0.canWrite();

        // DB exists ?
        System.out.println("Create LOCALAPPDATA ? " + create_locpath);
        System.out.println("Create DB ? " + create_db);
        System.out.println("Create FSP dir ? " + create_wdir);
        System.out.println("Is LOCALAPPDATA writeable ? " + locpath_isWriteable);

        //mkdir() doesnt throw exception but return 0 or 1
        if (dpath.toFile().mkdirs()) {
            System.out.println("Directories created in " + dpath);
        } else {
            System.out.println("Directories already exist.");
        }

        try {
            //SQLiteDBManager session = new SQLiteDBManager();
            SQLiteDBManager.createNewDatabase(fpath.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
