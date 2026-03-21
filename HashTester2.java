package travel_and_Tourism_Organisation_System;

public class HashTester2 {
    public static void main(String[] args) {
        String[] passwords = {"1234", "admin", "alex", "password"};
        for (String p : passwords) {
            System.out.println("Password: " + p + " -> Hash: " + SecurityUtil.hashPassword(p));
        }
    }
}
