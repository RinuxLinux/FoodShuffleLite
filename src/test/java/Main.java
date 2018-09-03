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
    /* public void test01() {
        String path = System.getenv("LOCALAPPDATA");
        String dirname = "FSP Reloaded";
        String dbname = "test01.db";

        Path dpath = Paths.get(path, dirname);
        Path fpath = Paths.get(path, dirname, dbname);

        int flag = 0;

        // isFile(1) isDir(2) isWrite(4)
        if (fpath.toFile().exists()) {
            flag += 1;
        }
        if (dpath.toFile().exists()) {
            flag += 2;
            if (dpath.toFile().canWrite()) {
                flag += 4;
            }
        }

        System.out.println("| isFile | isDir | isWrite |");
        System.out.println("+--------+-------+---------+");

        switch (flag) {

            case 1:
                System.out.println("|   x    |   x   |    -    |");
                System.out.println("+--------+-------+---------+");
                break;
            case 2:
                System.out.println("|   -    |   x   |    -    |");
                System.out.println("+--------+-------+---------+");
                break;
            case 3:
                System.out.println("|   x    |   x   |    -    |");
                System.out.println("+--------+-------+---------+");
                break;
            case 4:
                System.out.println("|   ?    |   ?   |    ?    |");
                System.out.println("+--------+-------+---------+");
                break;
            case 5:
                System.out.println("|   ?    |   ?   |    ?    |");
                System.out.println("+--------+-------+---------+");
                break;
            case 6:
                System.out.println("|   -    |   x   |    x    |");
                System.out.println("+--------+-------+---------+");
                break;
            case 7:
                System.out.println("|   x    |   x   |    x    |");
                System.out.println("+--------+-------+---------+");
                break;
        }

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        File file = new File(directoryName + "/" + fileName);
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

     */
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
