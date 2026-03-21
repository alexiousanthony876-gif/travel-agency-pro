package travel_and_Tourism_Organisation_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Payment extends JFrame implements ActionListener {
    JButton pay, back;
    static String username = ""; // This should ideally be passed from Dashboard

    public Payment() {
        setTitle("Payment Entry");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(248, 250, 252));

        // Center Panel
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        add(center, BorderLayout.CENTER);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(800, 600));
        card.setBackground(Color.WHITE);
        card.setLayout(null);
        card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        JLabel title = new JLabel("Fast & Secure Payments");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBounds(50, 50, 500, 45);
        card.add(title);

        JLabel sub = new JLabel("Choose your preferred method and complete your travel booking instantly.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sub.setForeground(new Color(100, 116, 139));
        sub.setBounds(50, 100, 600, 30);
        card.add(sub);

        // Illustration Placeholder or Icon
        try {
            ImageIcon i7 = new ImageIcon(
                    ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/upi_uploaded.jpg"));
            if (i7.getIconWidth() == -1)
                i7 = new ImageIcon(
                        ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/paytm.jpeg"));
            Image i8 = i7.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img = new JLabel(new ImageIcon(i8));
            img.setBounds(250, 160, 300, 300);
            card.add(img);
        } catch (Exception e) {
        }

        pay = new JButton("Open Payment Gateway");
        pay.setBackground(new Color(37, 99, 235));
        pay.setForeground(Color.WHITE);
        pay.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pay.setBounds(100, 480, 280, 55);
        pay.setFocusPainted(false);
        pay.setBorder(null);
        pay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pay.addActionListener(this);
        card.add(pay);

        back = new JButton("Dashboard");
        back.setBackground(Color.WHITE);
        back.setForeground(new Color(71, 85, 105));
        back.setFont(new Font("Segoe UI", Font.BOLD, 18));
        back.setBounds(420, 480, 280, 55);
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(this);
        card.add(back);

        center.add(card);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            setVisible(false);
            new PaymentGateway(Dashboard.username); // Use the static username from Dashboard
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Payment().setVisible(true);
    }
}
