package travel_and_Tourism_Organisation_System;

import java.sql.*;

public class DBCleanupAll {
    public static void main(String[] args) {
        // Try MySQL
        System.out.println("Attempting to clean up MySQL...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "jayesh0311");
            cleanup(c);
            System.out.println("MySQL cleanup successful.");
            c.close();
        } catch (Exception e) {
            System.out.println("MySQL cleanup failed: " + e.getMessage());
        }

        // Try SQLite
        System.out.println("\nAttempting to clean up SQLite (tms.db)...");
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:tms.db");
            cleanup(c);
            System.out.println("SQLite cleanup successful.");
            c.close();
        } catch (Exception e) {
            System.out.println("SQLite cleanup failed: " + e.getMessage());
        }
    }

    private static void cleanup(Connection c) throws SQLException {
        Statement s = c.createStatement();
        
        // Delete duplicates and ensure alex exists with password 'alex'
        s.executeUpdate("DELETE FROM account WHERE LOWER(username) = 'alex'");
        
        String hashedAlex = SecurityUtil.hashPassword("alex");
        s.executeUpdate("INSERT INTO account(username, name, password, security, answer, role) " +
                       "VALUES('alex', 'Alexious', '" + hashedAlex + "', 'Your Lucky Number?', '1234', 'Admin')");
        
        // Ensure admin exists
        s.executeUpdate("DELETE FROM account WHERE LOWER(username) = 'admin'");
        s.executeUpdate("INSERT INTO account(username, name, password, security, answer, role) " +
                       "VALUES('admin', 'Admin', 'admin', 'Your Lucky Number?', '1234', 'Admin')");
    }
}
