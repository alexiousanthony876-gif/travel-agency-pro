package travel_and_Tourism_Organisation_System;

import java.sql.*;

public class UpdateDB {
    public static void main(String[] args) {
        try {
            Conn c = new Conn();
            // Add checkIn column to bookHotel if it doesn't exist
            // SQLite/MySQL syntax 'ADD COLUMN'
            // We use try-catch to ignore error if column exists
            try {
                c.s.executeUpdate("ALTER TABLE bookHotel ADD COLUMN checkIn VARCHAR(30)");
                System.out.println("column checkIn added to bookHotel");
            } catch (Exception e) {
                System.out.println("checkIn column might already exist in bookHotel: " + e.getMessage());
            }

            try {
                c.s.executeUpdate("ALTER TABLE bookHotel ADD COLUMN checkOut VARCHAR(30)");
                System.out.println("column checkOut added to bookHotel");
            } catch (Exception e) {
                System.out.println("checkOut column might already exist in bookHotel: " + e.getMessage());
            }

            try {
                c.s.executeUpdate("ALTER TABLE bookPackage ADD COLUMN travelDate VARCHAR(30)");
                System.out.println("column travelDate added to bookPackage");
            } catch (Exception e) {
                System.out.println("travelDate column might already exist in bookPackage: " + e.getMessage());
            }

            try {
                c.s.executeUpdate("ALTER TABLE account ADD COLUMN role VARCHAR(20)");
                c.s.executeUpdate("UPDATE account SET role = 'Admin' WHERE username = 'admin'");
                c.s.executeUpdate("UPDATE account SET role = 'User' WHERE role IS NULL");
                System.out.println("column role added and updated in account");
            } catch (Exception e) {
                System.out.println("role column might already exist in account: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
