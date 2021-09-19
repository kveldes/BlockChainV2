package com.kveldes;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Class that help me with the queries of db

public class DatabaseMng {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:backchain.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //--------Method that make a table---------------------------------------------------------------------------------/
    public void insert(int aa_id, int code, String title, String dtime, int cost, String desc, String categ, String hash, String prvhash, String data, long timestamp, int nonce) {
        String sql = "INSERT INTO blockchain(aa_id,code,title,dtime,cost,desc,categ,hash,prvhash,data,timestamp,nonce) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aa_id);
            pstmt.setInt(2, code);
            pstmt.setString(3, title);
            pstmt.setString(4, dtime);
            pstmt.setInt(5, cost);
            pstmt.setString(6, desc);
            pstmt.setString(7, categ);
            pstmt.setString(8, hash);
            pstmt.setString(9, prvhash);
            pstmt.setString(10, data);
            pstmt.setLong(11, timestamp);
            pstmt.setInt(12, nonce);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //--------Method that help me to view Products by Title------------------------------------------------------------/
    public int viewProductsByTitle() {
        int counter = 0;
        String sql = "SELECT DISTINCT title FROM blockchain";
        String url = "jdbc:sqlite:backchain.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("title"));
                //System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("capacity"));
                counter++;
            }
            System.out.println("Searching and Found " + counter + " unique products in BlockChain Database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return counter;
    }

    //--------Method that help me to view if DB is Empty---------------------------------------------------------------/
    public int viewIfDbIsEmpty() {
        int counter = 0;
        String sql = "SELECT title FROM blockchain";
        String url = "jdbc:sqlite:backchain.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                counter++;
            }
            System.out.println("Database not Empty!Searching and Found " + counter + " products in BlockChain Database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return counter;
    }

    //--------Method that help me to view Statistics of Products ------------------------------------------------------/
    public void viewStat(String title) {
        String sql = "SELECT dtime, cost FROM blockchain WHERE title = '" + title + "'";
        String url = "jdbc:sqlite:backchain.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println("Timestamp :" + rs.getString("dtime") + "\t Cost :" + rs.getString("cost"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //--------Method that help me to view Products by Title------------------------------------------------------------/
    public void searchProductByCode(int code){
        int counter = 0;
        String sql = "SELECT * FROM blockchain WHERE code = '" + code + "'";
        String url = "jdbc:sqlite:backchain.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println("A/A:"+rs.getInt("aa_id") + "\t" +"Code:"+rs.getInt("code")+ "\t" +"Title:" + rs.getString("title")+ "\t"+"Date and Time:"+ rs.getString("dtime") + "\t" +"Cost:"+rs.getInt("cost")+ "\t"+"Description:"+rs.getString("desc")+ "\t"+ "Category:"+rs.getString("categ"));
                counter++;
            }
            System.out.println("Searching and Found " + counter + " products in BlockChain Database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //--------Method that help me to search Products by Title----------------------------------------------------------/
    public void searchProductByTitle(String title){
        int counter = 0;
        String sql = "SELECT * FROM blockchain WHERE title = '" + title + "'";
        String url = "jdbc:sqlite:backchain.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println("A/A:"+rs.getInt("aa_id") + "\t" +"Code:"+rs.getInt("code")+ "\t" +"Title:" + rs.getString("title")+ "\t"+"Date and Time:"+ rs.getString("dtime") + "\t" +"Cost:"+rs.getInt("cost")+ "\t"+"Description:"+rs.getString("desc")+ "\t"+ "Category:"+rs.getString("categ"));
                counter++;
            }
            System.out.println("Searching and Found " + counter + " products in BlockChain Database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //--------Method that help me to pull data from DB-----------------------------------------------------------------/
    public  List<Product>  pullDataFromDB () {
        String sql = "SELECT aa_id,code,title,dtime,cost,desc,categ,hash,prvhash,data,timestamp,nonce FROM blockchain ";
        String url = "jdbc:sqlite:backchain.db";
        List<Product> productsList = new ArrayList<Product>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {


            while (rs.next()) {

                Product product = new Product();
                product.setAaId(rs.getInt("aa_id"));
                product.setCode(rs.getInt("code"));
                product.setTitle(rs.getString("title"));
                product.setTime(rs.getString("dtime"));
                product.setCost(rs.getInt("cost"));
                product.setDescription(rs.getString("desc"));
                product.setCategory(rs.getString("categ"));
                product.setHash(rs.getString("hash"));
                product.setPreviusHash(rs.getString("prvhash"));
                product.setData(rs.getString("data"));
                product.setTimestamp(rs.getLong("timestamp"));
                product.setNonce(rs.getInt("nonce"));

                productsList.add(product);


            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return productsList;
    }
}