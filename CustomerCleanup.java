package travel_and_Tourism_Organisation_System;

import java.sql.*;
import java.util.*;

public class CustomerCleanup {
    public static void main(String[] args) {
        clean("MySQL", "jdbc:mysql://localhost:3306/tms", "root", "jayesh0311");
        clean("SQLite", "jdbc:sqlite:tms.db", null, null);
    }

    private static void clean(String dbType, String url, String user, String pass) {
        System.out.println("Cleaning " + dbType + "...");
        try {
            if (dbType.equals("MySQL")) Class.forName("com.mysql.cj.jdbc.Driver");
            else Class.forName("org.sqlite.JDBC");

            Connection c = (user == null) ? DriverManager.getConnection(url) : DriverManager.getConnection(url, user, pass);
            Statement s = c.createStatement();
            
            // Get all usernames
            ResultSet rs = s.executeQuery("SELECT DISTINCT username FROM customer");
            List<String> usernames = new ArrayList<>();
            while (rs.next()) usernames.add(rs.getString("username"));
            rs.close();

            for (String uname : usernames) {
                // For each user, find if they have multiple records
                rs = s.executeQuery("SELECT * FROM customer WHERE username = '" + uname + "'");
                List<Map<String, String>> records = new ArrayList<>();
                while (rs.next()) {
                    Map<String, String> rec = new HashMap<>();
                    rec.put("username", rs.getString("username"));
                    rec.put("id", rs.getString("id"));
                    rec.put("number", rs.getString("number"));
                    // ... etc
                    records.add(rec);
                }
                rs.close();

                if (records.size() > 1) {
                    System.out.println("Found " + records.size() + " records for user: " + uname);
                    // Find if any record has data
                    boolean hasCompleteRecord = false;
                    for (Map<String, String> rec : records) {
                        String id = rec.get("id");
                        if (id != null && !id.isEmpty() && !id.equals("null")) {
                            hasCompleteRecord = true;
                            break;
                        }
                    }

                    if (hasCompleteRecord) {
                        // Delete records that are missing ID
                        PreparedStatement ps = c.prepareStatement("DELETE FROM customer WHERE username = ? AND (id IS NULL OR id = '' OR id = 'null')");
                        ps.setString(1, uname);
                        ps.executeUpdate();
                        ps.close();
                    }
                }
            }
            c.close();
            System.out.println(dbType + " cleaned successfully.\n");
        } catch (Exception e) {
            System.out.println(dbType + " cleanup failed: " + e.getMessage());
        }
    }
}
