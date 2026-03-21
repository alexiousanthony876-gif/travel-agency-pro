package travel_and_Tourism_Organisation_System;

public class HashTester {
    public static void main(String[] args) {
        String password = "alex";
        String hash = SecurityUtil.hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
        
        String plainAdminHash = SecurityUtil.hashPassword("admin");
        System.out.println("Admin Hash: " + plainAdminHash);
    }
}
