package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import java.awt.*;

public class TermsOfService extends JFrame {
    public TermsOfService() {
        setTitle("Terms & Conditions");
        setBounds(450, 100, 600, 600);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Terms & Conditions", JLabel.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(heading, BorderLayout.NORTH);

        JEditorPane content = new JEditorPane();
        content.setContentType("text/html");
        content.setEditable(false);
        content.setText("<html><body style='padding: 20px; font-family: Arial;'>" +
                "<h3>1. Acceptance of Terms</h3>" +
                "<p>By accessing and using this application, you accept and agree to be bound by the terms and provision of this agreement.</p>"
                +
                "<h3>2. Booking & Payments</h3>" +
                "<p>All bookings are subject to availability. Full payment must be made to confirm reservations.</p>" +
                "<h3>3. Cancellations & Refunds</h3>" +
                "<p>Cancellations must be made 48 hours prior to the travel date for a full refund. Late cancellations may incur a fee.</p>"
                +
                "<h3>4. Liability</h3>" +
                "<p>We are not responsible for any personal injury or loss of belongings during the trip.</p>" +
                "</body></html>");

        JScrollPane scroll = new JScrollPane(content);
        add(scroll, BorderLayout.CENTER);

        JButton back = new JButton("Close");
        back.addActionListener(e -> setVisible(false));
        add(back, BorderLayout.SOUTH);

        setVisible(true);
    }
}
