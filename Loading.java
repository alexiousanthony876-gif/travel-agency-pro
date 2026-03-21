package travel_and_Tourism_Organisation_System;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class Loading extends JFrame implements Runnable {

    private JPanel contentPane;
    private JProgressBar progressBar;
    String username, role;
    Thread th;

    // Theme Colors
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color accentColor = new Color(0, 122, 255); // iOS Blue
    Color textLight = new Color(241, 245, 249); // Slate 100
    Color textDim = new Color(148, 163, 184); // Slate 400

    public static void main(String[] args) {
        new Loading("Guest", "User").setVisible(true);
    }

    public void setUploading() {
        th.start();
    }

    public void run() {
        try {
            for (int i = 0; i <= 100; i++) {
                progressBar.setValue(i);
                if (i == 100) {
                    setVisible(false);
                    if ("Admin".equalsIgnoreCase(role)) {
                        new AdminDashboard(username).setVisible(true);
                    } else {
                        new Dashboard(username).setVisible(true);
                    }
                    break;
                }
                // Faster but smoother increments
                Thread.sleep(30);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Loading(String username, String role) {
        this.username = username;
        this.role = role;
        th = new Thread(this);

        setSize(600, 350);
        setLocationRelativeTo(null); // Center on screen
        setUndecorated(true);

        contentPane = new JPanel();
        contentPane.setBackground(primaryDark);
        contentPane.setBorder(BorderFactory.createLineBorder(new Color(30, 41, 59), 2));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Branding
        JLabel brandName = new JLabel("TRAVEL WIZARD");
        brandName.setFont(new Font("Segoe UI", Font.BOLD, 36));
        brandName.setForeground(Color.WHITE);
        brandName.setHorizontalAlignment(SwingConstants.CENTER);
        brandName.setBounds(0, 60, 600, 50);
        contentPane.add(brandName);

        JLabel statusLabel = new JLabel("Securing connection and loading your adventure...");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusLabel.setForeground(textDim);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBounds(0, 110, 600, 30);
        contentPane.add(statusLabel);

        // Modern Progress Bar
        progressBar = new JProgressBar();
        progressBar.setBounds(100, 180, 400, 10);
        progressBar.setBackground(new Color(30, 41, 59));
        progressBar.setForeground(accentColor);
        progressBar.setBorder(null);
        progressBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            protected Color getSelectionBackground() {
                return new Color(0, 0, 0, 0);
            }

            protected Color getSelectionForeground() {
                return new Color(0, 0, 0, 0);
            }
        });
        contentPane.add(progressBar);

        JLabel welcomeMsg = new JLabel("Welcome, " + username);
        welcomeMsg.setFont(new Font("Segoe UI", Font.BOLD, 14));
        welcomeMsg.setForeground(accentColor);
        welcomeMsg.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeMsg.setBounds(0, 210, 600, 30);
        contentPane.add(welcomeMsg);

        // Footer version or detail
        JLabel footer = new JLabel("Version 2.0 • Premium Experience");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(new Color(71, 85, 105));
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBounds(0, 310, 600, 20);
        contentPane.add(footer);

        setUploading();
    }
}
