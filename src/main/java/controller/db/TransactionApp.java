/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sqlitetutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author re
 */
public class TransactionApp {

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
     * Create a new material and add initial quantity to the warehouse
     *
     * @param material
     * @param warehouseId
     * @param qty
     */
    public void addInventory(String material, int warehouseId, double qty) {

        // SQL for creating a new material
        String sqlMaterial = "INSERT INTO material (description) VALUES (?)";

        // SQL for posting inventory
        String sqlInventory = "INSERT INTO inventory (warehouse_id, material_id, qty)"
                + "VALUES (?, ?, ?)";

        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null, pstmt2 = null;

        try {
            // connect to the database
            conn = this.connect();
            if (conn == null) {
                return;
            }

            // set auto-commit mode to false
            conn.setAutoCommit(false);

            // 1. insert a new material
            pstmt1 = conn.prepareStatement(sqlMaterial, Statement.RETURN_GENERATED_KEYS);
            pstmt1.setString(1, material);
            int rowAffected = pstmt1.executeUpdate();

            // get the material id
            rs = pstmt1.getGeneratedKeys();
            int materialId = 0;
            if (rs.next()) {
                materialId = rs.getInt(1);
            }

            if (rowAffected != 1) {
                conn.rollback();
            }

            // 2. insert the inventory
            pstmt2 = conn.prepareStatement(sqlInventory);
            pstmt2.setInt(1, warehouseId);
            pstmt2.setInt(2, materialId);
            pstmt2.setDouble(3, qty);
            //
            pstmt2.executeUpdate();
            // commit work
            conn.commit();

        } catch (SQLException e1) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt1 != null) {
                    pstmt1.close();
                }
                if (pstmt2 != null) {
                    pstmt2.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }

/**
 * Main de TransactionApp.java
 * 
 * @param args CLI arguments
 */
    public static void main(String[] args) {
        TransactionApp app = new TransactionApp();
        app.addInventory("HP Laptop", 3, 100);
    }
}