/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java;

import java.io.FileReader;
import java.util.List;

/**
 *
 * @author re
 */
public class RunScriptRunner {

    private static void main(String[] args) {
        // Creating object of ScriptRunner class
        ScriptRunner scriptRunner = new ScriptRunner(con, false, true);

        String aSQLScriptFilePath = "path/to/sql/script.sql";
        // Executing SQL Script
        scriptRunner.runScript(new FileReader(aSQLScriptFilePath));

        // Optional Part...
        List<Table> tableList; // Used to store result of 'SELECT' Query execution
        List<String> sqlOutput; // Used to store result of any quires except 'SELECT' quires

        tableList = scriptRunner.getTableList();
        sqlOutput = scriptRunner.getSqlOutput();

// Now iterate through tableList to display all tables with headers & rows
// Iterate through sqlOutput to display how many rows are affected.
// End of Optional Part...

        /*
The above are optional part as because the output of Execution of all SQL Statements 
will be displayed automatically in Standard Output area. 
You may store the result if you want to display anywhere else.
         */
    }

}
