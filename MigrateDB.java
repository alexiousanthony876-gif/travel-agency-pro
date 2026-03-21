package travel_and_Tourism_Organisation_System;

public class MigrateDB {
    public static void main(String[] args) {
        System.out.println("Starting Database Migration...");
        new Conn(); // This will trigger the migrations in initializeDatabase
        System.out.println("Migration Complete!");
    }
}
