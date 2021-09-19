package com.kveldes;


//Class that initialize the SQLite Database and make a new table in it with name blockchain

import java.sql.*;

public class InitialDB {


    public InitialDB() {
        String url = "jdbc:sqlite:backchain.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS blockchain (\n"
                + "    aa_id integer PRIMARY KEY,\n"
                + "    code integer ,\n"
                + "    title text,\n"
                + "    dtime text,\n"
                + "    cost integer,\n"
                + "    desc text,\n"
                + "    categ text,\n"
                + "    hash text,\n"
                + "    prvhash text,\n"
                + "    data text,\n"
                + "    timestamp long,\n"
                + "    nonce integer\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}

