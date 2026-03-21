package travel_and_Tourism_Organisation_System;

import java.sql.*;

public class DBInspector {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:tms.db");
            Statement s = c.createStatement();
            
            System.out.println("Checking 'account' table...");
            ResultSet rs = s.executeQuery("SELECT * FROM account");
            while (rs.next()) {
                System.out.println("User: " + rs.getString("username") + 
                                   " | Name: " + rs.getString("name") + 
                                   " | Pass: " + rs.getString("password") + 
                                   " | Role: " + rs.getString("role"));
            }
            rs.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
