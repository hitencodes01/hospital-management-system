package database;

import java.sql.*;

public class ConnectionDB {
    final String url = "jdbc:mysql://localhost:3306/minor";
    final String user = "root";
    final String password = "1234";
    Connection con;
    public Connection connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
            con = DriverManager.getConnection(url,user,password);
            System.out.println("Database Connected");
            return con;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
