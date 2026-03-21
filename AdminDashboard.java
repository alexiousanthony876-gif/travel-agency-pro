package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame implements ActionListener {

    static String username;
    JButton viewAllCustomers, viewAllBookings, manageHotels, managePackages, logout, userView;

    public AdminDashboard(String username) {
        AdminDashboard.username = username;
        System.out.println("Initializing Admin Dashboard for: " + username);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(153, 0, 0)); // Dark Red for Admin
        p1.setBounds(0, 0, 1600, 65);
        add(p1);

        JLabel l2 = new JLabel("Admin Dashboard - Travel Wizard");
        l2.setFont(new Font("Tahoma", Font.BOLD, 30));
        l2.setForeground(Color.WHITE);
        l2.setBounds(80, 10, 600, 40);
        p1.add(l2);

        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(new Color(153, 0, 0));
        p2.setBounds(0, 65, 300, 900);
        add(p2);

        viewAllCustomers = new JButton("View All Customers");
        viewAllCustomers.setBackground(new Color(153, 0, 0));
        viewAllCustomers.setFont(new Font("Tahoma", Font.PLAIN, 20));
        viewAllCustomers.setForeground(Color.WHITE);
        viewAllCustomers.setBounds(0, 0, 300, 50);
        viewAllCustomers.addActionListener(this);
        p2.add(viewAllCustomers);

        viewAllBookings = new JButton("View All Bookings");
        viewAllBookings.setBackground(new Color(153, 0, 0));
        viewAllBookings.setFont(new Font("Tahoma", Font.PLAIN, 20));
        viewAllBookings.setForeground(Color.WHITE);
        viewAllBookings.setBounds(0, 50, 300, 50);
        viewAllBookings.addActionListener(this);
        p2.add(viewAllBookings);

        manageHotels = new JButton("Manage Hotels");
        manageHotels.setBackground(new Color(153, 0, 0));
        manageHotels.setFont(new Font("Tahoma", Font.PLAIN, 20));
        manageHotels.setForeground(Color.WHITE);
        manageHotels.setBounds(0, 100, 300, 50);
        manageHotels.addActionListener(this);
        p2.add(manageHotels);

        managePackages = new JButton("Manage Packages");
        managePackages.setBackground(new Color(153, 0, 0));
        managePackages.setFont(new Font("Tahoma", Font.PLAIN, 20));
        managePackages.setForeground(Color.WHITE);
        managePackages.setBounds(0, 150, 300, 50);
        managePackages.addActionListener(this);
        p2.add(managePackages);

        userView = new JButton("Switch to User View");
        userView.setBackground(new Color(153, 0, 0));
        userView.setFont(new Font("Tahoma", Font.PLAIN, 20));
        userView.setForeground(Color.WHITE);
        userView.setBounds(0, 200, 300, 50);
        userView.addActionListener(this);
        p2.add(userView);

        logout = new JButton("Logout");
        logout.setBackground(new Color(153, 0, 0));
        logout.setFont(new Font("Tahoma", Font.PLAIN, 20));
        logout.setForeground(Color.WHITE);
        logout.setBounds(0, 250, 300, 50);
        logout.addActionListener(this);
        p2.add(logout);

        // --- Main Content Area: Simplified ---
        JPanel mainContent = new JPanel();
        mainContent.setLayout(null);
        mainContent.setBounds(300, 65, 1300, 900);
        mainContent.setBackground(Color.WHITE);
        add(mainContent);

        JLabel l1 = new JLabel("Admin Panel - Restricted Access");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 50));
        l1.setForeground(Color.BLACK);
        l1.setBounds(100, 250, 800, 70);
        mainContent.add(l1);

        setVisible(true);
    }

    private JPanel createStatCard(String title, String val, Color highlight) {
        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, highlight));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setForeground(new Color(100, 116, 139));
        t.setBounds(25, 30, 200, 20);
        p.add(t);

        JLabel v = new JLabel(val);
        v.setFont(new Font("Segoe UI Bold", Font.BOLD, 48));
        v.setForeground(new Color(15, 23, 42));
        v.setBounds(25, 60, 200, 60);
        p.add(v);

        return p;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == logout) {
            setVisible(false);
            new Login().setVisible(true);
        } else if (ae.getSource() == userView) {
            setVisible(false);
            new Dashboard(username).setVisible(true);
        } else if (ae.getSource() == viewAllCustomers) {
            new AdminViewCustomers().setVisible(true);
        } else if (ae.getSource() == viewAllBookings) {
            new AdminViewBookings().setVisible(true);
        } else if (ae.getSource() == manageHotels) {
            new AdminManageHotels().setVisible(true);
        } else if (ae.getSource() == managePackages) {
            new CheckPackage(username).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Feature under development...");
        }
    }

    public static void main(String[] args) {
        new AdminDashboard(username);
    }
}
