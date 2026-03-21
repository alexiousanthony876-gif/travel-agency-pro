package travel_and_Tourism_Organisation_System;

import java.sql.*;

public class DBCleanup {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:tms.db");
            Statement s = c.createStatement();
            
            System.out.println("Cleaning up 'account' table...");
            
            // Delete all alex entries first to be sure
            s.executeUpdate("DELETE FROM account WHERE username = 'alex'");
            
            // Insert correct alex entry
            String hashedAlex = SecurityUtil.hashPassword("alex");
            s.executeUpdate("INSERT INTO account(username, name, password, security, answer, role) " +
                           "VALUES('alex', 'Alexious', '" + hashedAlex + "', 'Your Lucky Number?', '1234', 'Admin')");
            
            System.out.println("Alex account updated with password 'alex'.");
            
            // Also ensure admin is there
            s.executeUpdate("DELETE FROM account WHERE username = 'admin'");
            s.executeUpdate("INSERT INTO account(username, name, password, security, answer, role) " +
                           "VALUES('admin', 'Admin', 'admin', 'Your Lucky Number?', '1234', 'Admin')");
            
            System.out.println("Admin account updated with password 'admin'.");
            
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
