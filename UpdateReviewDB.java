package travel_and_Tourism_Organisation_System;

import java.sql.*;

public class UpdateReviewDB {
    public static void main(String[] args) {
        Conn c = new Conn();
        try {
            // Create reviews table
            String sql = "create table if not exists reviews(" +
                    "username varchar(30), " +
                    "target varchar(50), " +
                    "rating varchar(10), " +
                    "comment varchar(200), " +
                    "date varchar(30))";
            c.s.executeUpdate(sql);
            System.out.println("Reviews table created/verified.");

            // Insert dummy reviews so we have some data to display average ratings
            c.s.executeUpdate(
                    "insert into reviews values('testuser', 'Gold Package', '5', 'Amazing experience!', '2024-02-06')");
            c.s.executeUpdate(
                    "insert into reviews values('admin', 'Gold Package', '4', 'Good experience.', '2024-02-05')");
            c.s.executeUpdate(
                    "insert into reviews values('testuser', 'Silver Package', '3', 'Average experience.', '2024-02-06')");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
