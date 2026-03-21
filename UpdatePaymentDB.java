package travel_and_Tourism_Organisation_System;

import java.sql.*;

public class UpdatePaymentDB {
    public static void main(String[] args) {
        Conn c = new Conn();
        try {
            // Create payment table
            String sql = "create table if not exists payment(" +
                    "username varchar(30), " +
                    "transactionId varchar(50), " +
                    "amount varchar(20), " +
                    "method varchar(20), " +
                    "status varchar(20), " +
                    "date varchar(30))";
            c.s.executeUpdate(sql);
            System.out.println("Payment table created/verified.");

            try {
                c.s.executeUpdate("ALTER TABLE bookHotel ADD COLUMN status VARCHAR(20) DEFAULT 'Pending'");
                System.out.println("Column 'status' added to bookHotel");
            } catch (Exception e) {
                System.out.println("Column 'status' might already exist in bookHotel: " + e.getMessage());
            }

            try {
                c.s.executeUpdate("ALTER TABLE bookPackage ADD COLUMN status VARCHAR(20) DEFAULT 'Pending'");
                System.out.println("Column 'status' added to bookPackage");
            } catch (Exception e) {
                System.out.println("Column 'status' might already exist in bookPackage: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
