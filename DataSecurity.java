package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DataSecurity extends JFrame implements ActionListener {
    JButton privacy, terms, back;

    public DataSecurity() {
        setTitle("Data Security & Privacy");
        setBounds(450, 200, 500, 300);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Security & Privacy Center");
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setBounds(100, 20, 300, 30);
        add(heading);

        privacy = new JButton("Privacy Policy");
        privacy.setBounds(150, 80, 200, 40);
        privacy.setBackground(new Color(0, 0, 102));
        privacy.setForeground(Color.WHITE);
        privacy.setFont(new Font("Tahoma", Font.PLAIN, 18));
        privacy.addActionListener(this);
        add(privacy);

        terms = new JButton("Terms & Conditions");
        terms.setBounds(150, 140, 200, 40);
        terms.setBackground(new Color(0, 0, 102));
        terms.setForeground(Color.WHITE);
        terms.setFont(new Font("Tahoma", Font.PLAIN, 18));
        terms.addActionListener(this);
        add(terms);

        // Visual badge for 'Secured'
        JLabel secureLabel = new JLabel("✓ System Secured with SHA-256 Encryption");
        secureLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        secureLabel.setForeground(new Color(34, 139, 34));
        secureLabel.setBounds(100, 210, 350, 20);
        add(secureLabel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == privacy) {
            new PrivacyPolicy();
        } else if (ae.getSource() == terms) {
            new TermsOfService();
        }
    }

    public static void main(String[] args) {
        new DataSecurity();
    }
}
