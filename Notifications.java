package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Notifications extends JFrame {
    String username;
    JPanel listPanel;

    // Premium Color Palette
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color mainBg = new Color(245, 247, 250); // Muted gray
    Color accentColor = new Color(0, 122, 255); // Blue
    Color successColor = new Color(34, 197, 94); // Green
    Color alertColor = new Color(239, 44, 44); // Red
    Color warningColor = new Color(245, 158, 11); // Amber

    public Notifications(String username) {
        this.username = username;
        setTitle("Activity Center | Travel Wizard");
        setBounds(450, 100, 500, 700);
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainBg);

        // ================= HEADER =================
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryDark);
        headerPanel.setPreferredSize(new Dimension(500, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(0, 25, 0, 25));

        JLabel heading = new JLabel("Activity & Alerts");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        headerPanel.add(heading, BorderLayout.WEST);

        JLabel iconLabel = new JLabel("🔔");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setForeground(Color.WHITE);
        headerPanel.add(iconLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // ================= LIST PANEL =================
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(mainBg);
        listPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        add(scrollPane, BorderLayout.CENTER);

        // ================= FOOTER ACTION =================
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        JButton simPush = new JButton("Simulate Real-time Offer") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        simPush.setPreferredSize(new Dimension(250, 45));
        simPush.setContentAreaFilled(false);
        simPush.setBorder(null);
        simPush.setForeground(Color.WHITE);
        simPush.setFont(new Font("Segoe UI", Font.BOLD, 14));
        simPush.setCursor(new Cursor(Cursor.HAND_CURSOR));

        simPush.addActionListener(e -> {
            addNotification("Flash Deal Unlocked! ✨",
                    "Get 25% instant discount on Maldives Luxury Package. Valid for 24h!", "Offer");
            JOptionPane.showMessageDialog(this, "New flash deal pushed to your activity feed!", "System Notification",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        footerPanel.add(simPush);
        add(footerPanel, BorderLayout.SOUTH);

        loadNotifications();
        setVisible(true);
    }

    private void loadNotifications() {
        listPanel.removeAll();

        // Welcome Notification
        addNotification("System Welcome", "Welcome to the Travel Wizard ecosystem. We're excited to help you explore.",
                "Info");

        try {
            Conn c = new Conn();
            // Hotel Alerts
            ResultSet rs = c.s.executeQuery("select * from bookHotel where username = '" + username + "'");
            while (rs.next()) {
                String hotel = rs.getString("hotel");
                String status = rs.getString("status");
                if (status == null)
                    status = "Pending";

                if ("Paid".equalsIgnoreCase(status)) {
                    addNotification("Booking Verified", "Payment received for stay at " + hotel + ". Enjoy your visit!",
                            "Success");
                } else {
                    addNotification("Action Required",
                            "Payment is still pending for " + hotel + ". Secure your room now.", "Alert");
                }
            }
            rs.close();

            // Package Alerts
            rs = c.s.executeQuery("select * from bookPackage where username = '" + username + "'");
            while (rs.next()) {
                String pkg = rs.getString("package");
                String date = rs.getString("travelDate");
                String status = rs.getString("status");
                if (status == null)
                    status = "Pending";

                if ("Paid".equalsIgnoreCase(status)) {
                    addNotification("Itinerary Ready", "Confirmed: your " + pkg + " trip starts on " + date + ".",
                            "Success");
                } else {
                    addNotification("Pending Booking", "Complete your " + pkg + " package payment to finalize travel.",
                            "Alert");
                }
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private void addNotification(String title, String message, String type) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(460, 95));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 10, 0),
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1)));

        // Status Indicator Stripe
        Color indicatorColor = type.equals("Alert") ? alertColor
                : type.equals("Success") ? successColor : type.equals("Offer") ? warningColor : accentColor;

        JPanel stripe = new JPanel();
        stripe.setPreferredSize(new Dimension(5, 95));
        stripe.setBackground(indicatorColor);
        card.add(stripe, BorderLayout.WEST);

        // Content Panel
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(12, 10, 12, 15));

        JLabel tLabel = new JLabel(title);
        tLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tLabel.setForeground(indicatorColor);
        content.add(tLabel);
        content.add(Box.createVerticalStrut(4));

        JLabel mLabel = new JLabel("<html><body style='width: 330px;'>" + message + "</body></html>");
        mLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        mLabel.setForeground(new Color(71, 85, 105));
        content.add(mLabel);

        card.add(content, BorderLayout.CENTER);

        // Hover Effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 250, 252));
                content.setBackground(new Color(248, 250, 252));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                content.setBackground(Color.WHITE);
            }
        });

        listPanel.add(card);
        listPanel.add(Box.createVerticalStrut(2));
        listPanel.revalidate();
    }

    public static void main(String[] args) {
        new Notifications("testuser");
    }
}
