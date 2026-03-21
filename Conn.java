package travel_and_Tourism_Organisation_System;

import java.sql.*;

public class Conn {
    public static Connection c;
    public static Statement s;
    private static boolean isInitialized = false;

    public Conn() {
        try {
            // Only initialize once to improve performance
            if (c == null || c.isClosed()) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // MySQL database connection - using a persistent connection
                    c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms", "root", "jayesh0311");
                    System.out.println("MySQL Database connection established.");
                } catch (Exception e) {
                    System.out.println("MySQL Connection failed, attempting SQLite fallback...");
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:tms.db");
                    System.out.println("SQLite Database connection established.");
                }
                
                s = c.createStatement();

                if (!isInitialized) {
                    initializeDatabase(s);
                    isInitialized = true;
                }
            }
        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
        }
    }

    private void initializeDatabase(Statement s) {
        try {
            // Optimized table creation with indices for speed
            s.executeUpdate(
                    "create table if not exists account(username varchar(30) primary key, name varchar(30), password varchar(255), security varchar(50), answer varchar(50), role varchar(20) default 'User')");

            // Insert default admin if not exists
            ResultSet rs = s.executeQuery("select count(*) from account where username='admin'");
            if (rs.next() && rs.getInt(1) == 0) {
                s.executeUpdate(
                        "insert into account(username, name, password, security, answer, role) values('admin', 'Admin', 'admin', 'Your Lucky Number?', '1234', 'Admin')");
            }
            rs.close();

            // Insert default alex if not exists
            try {
                rs = s.executeQuery("select count(*) from account where username='alex'");
                if (rs.next() && rs.getInt(1) == 0) {
                    String hashedHex = SecurityUtil.hashPassword("alex");
                    s.executeUpdate(
                            "insert into account(username, name, password, security, answer, role) values('alex', 'Alex', '" + hashedHex + "', 'Your Lucky Number?', '1234', 'User')");
                    System.out.println("Default user 'alex' created.");
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error creating 'alex' user: " + e.getMessage());
            }

            s.executeUpdate(
                    "create table if not exists customer(username varchar(30) primary key, id varchar(30), number varchar(30), name varchar(30), gender varchar(20), country varchar(30), address varchar(50), email varchar(40))");

            s.executeUpdate(
                    "create table if not exists bookPackage(username varchar(30), package varchar(30), persons varchar(20), id varchar(30), number varchar(30), price varchar(20))");

            s.executeUpdate(
                    "create table if not exists bookHotel(username varchar(30), hotel varchar(30), persons varchar(20), days varchar(20), ac varchar(10), food varchar(10), id varchar(30), number varchar(30), price varchar(20))");

            s.executeUpdate(
                    "create table if not exists bookTransport(username varchar(30), type varchar(30), source varchar(30), destination varchar(30), class varchar(30), date varchar(30), persons varchar(20), price varchar(20))");

            s.executeUpdate(
                    "create table if not exists hotel(name varchar(30) primary key, costperperson varchar(20), foodincluded varchar(20), acroom varchar(20))");



            // Populate hotels if empty
            rs = s.executeQuery("select count(*) from hotel");
            if (rs.next() && rs.getInt(1) == 0) {
                String[][] hotels = {
                        { "Raddisson Blue Hotel", "2000", "1600", "1000" },
                        { "River View Hotel", "1800", "1200", "1000" },
                        { "The Taj Hotel", "5000", "2500", "2000" },
                        { "Marriott International", "4000", "2000", "1500" },
                        { "Hyatt Regency", "4500", "2200", "1800" },
                        { "Hotel Royal Orchid", "2500", "1500", "1200" }
                };
                for (String[] h : hotels) {
                    s.executeUpdate(
                            String.format("insert into hotel values('%s', '%s', '%s', '%s')", h[0], h[1], h[2], h[3]));
                }
            }
            rs.close();

            // Migration: Add role column to account table if it doesn't exist
            try {
                s.executeUpdate("ALTER TABLE account ADD COLUMN role varchar(20) DEFAULT 'User'");
                System.out.println("Role column added to account table.");
            } catch (SQLException e) {
                // Column probably already exists
            }
            
            // Ensure admin has Admin role
            try {
                s.executeUpdate("UPDATE account SET role = 'Admin' WHERE username = 'admin'");
            } catch (SQLException e) {
                System.out.println("Error updating admin role: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
