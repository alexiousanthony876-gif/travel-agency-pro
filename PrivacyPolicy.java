package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import java.awt.*;

public class PrivacyPolicy extends JFrame {
    public PrivacyPolicy() {
        setTitle("Privacy Policy");
        setBounds(400, 100, 600, 600);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Privacy Policy", JLabel.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(heading, BorderLayout.NORTH);

        JEditorPane content = new JEditorPane();
        content.setContentType("text/html");
        content.setEditable(false);
        content.setText("<html><body style='padding: 20px; font-family: Arial;'>" +
                "<h3>1. Information We Collect</h3>" +
                "<p>We collect personal details such as Name, Email, Address, and Phone Number solely for booking purposes.</p>"
                +
                "<h3>2. How We Use Your Data</h3>" +
                "<p>Your data is used to process bookings, send itinerary confirmations, and improve our services. We do not sell your data to third parties.</p>"
                +
                "<h3>3. Data Security</h3>" +
                "<p>We use industry-standard encryption for sensitive data like passwords and payments.</p>" +
                "<h3>4. User Rights</h3>" +
                "<p>You have the right to access, update, or delete your personal information at any time via the dashboard.</p>"
                +
                "</body></html>");

        JScrollPane scroll = new JScrollPane(content);
        add(scroll, BorderLayout.CENTER);

        JButton back = new JButton("Close");
        back.addActionListener(e -> setVisible(false));
        add(back, BorderLayout.SOUTH);

        setVisible(true);
    }
}
