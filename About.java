package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class About extends JFrame implements ActionListener {

    // Premium Color Palette
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color mainBg = new Color(245, 247, 250); // Light Gray Background
    Color accentColor = new Color(0, 122, 255); // iOS-Style Blue
    Color cardBg = Color.WHITE;

    public About() {
        setTitle("About the Project | Travel Wizard");
        setBounds(350, 100, 900, 600);
        setLayout(new BorderLayout());
        setUndecorated(false);

        // ================= MAIN CONTAINER =================
        JPanel container = new JPanel(null);
        container.setBackground(mainBg);
        add(container, BorderLayout.CENTER);

        // ================= SIDEBAR (Scenic Section) =================
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Using an icon already available in the project to ensure it loads
                    ImageIcon i1 = new ImageIcon(
                            ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/about.jpg"));
                    if (i1.getIconWidth() == -1) {
                        // Fallback if specific icon doesn't exist
                        i1 = new ImageIcon(ClassLoader
                                .getSystemResource("travel_and_Tourism_Organisation_System/icons/dest1.jpg"));
                    }
                    Image i2 = i1.getImage().getScaledInstance(350, 600, Image.SCALE_SMOOTH);
                    g.drawImage(i2, 0, 0, null);

                    // Gradient Overlay
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(0, 0, new Color(15, 23, 42, 40), 0, 600,
                            new Color(15, 23, 42, 230));
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, 350, 600);
                } catch (Exception e) {
                    g.setColor(primaryDark);
                    g.fillRect(0, 0, 350, 600);
                }
            }
        };
        sidebar.setBounds(0, 0, 350, 600);
        sidebar.setLayout(null);
        container.add(sidebar);

        JLabel sideTitle = new JLabel("System Info");
        sideTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        sideTitle.setForeground(Color.WHITE);
        sideTitle.setBounds(40, 420, 300, 45);
        sidebar.add(sideTitle);

        JLabel sideSub = new JLabel("Building the future of travel.");
        sideSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sideSub.setForeground(new Color(220, 220, 220));
        sideSub.setBounds(40, 465, 300, 25);
        sidebar.add(sideSub);

        // ================= CONTENT CARD =================
        JPanel contentCard = new JPanel();
        contentCard.setBounds(380, 30, 480, 500);
        contentCard.setBackground(cardBg);
        contentCard.setLayout(null);
        contentCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        container.add(contentCard);

        JLabel heading = new JLabel("About Travel Wizard");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(primaryDark);
        heading.setBounds(35, 30, 400, 40);
        contentCard.add(heading);

        JPanel underline = new JPanel();
        underline.setBounds(35, 75, 40, 4);
        underline.setBackground(accentColor);
        contentCard.add(underline);

        // Stylized Text Area
        JTextPane description = new JTextPane();
        description.setContentType("text/html");
        description.setEditable(false);
        String htmlContent = "<html><body style='font-family: \"Segoe UI\", sans-serif; font-size: 11pt; color: #475569; padding: 10px;'>"
                +
                "<p style='margin-bottom: 10px;'>The <b>Travel Wizard</b> ecosystem is a high-performance automation suite designed to modernize global tourism management.</p>"
                +
                "<p>Our architecture replaces manual coordination with a unified digital platform for travelers and administrators.</p>"
                +
                "<br><b>Project Objectives:</b>" +
                "<table style='margin-left: 10px;'>" +
                "<tr><td>• <b>Automation:</b></td><td>End-to-end digital travel lifecycle.</td></tr>" +
                "<tr><td>• <b>Efficiency:</b></td><td>90% reduction in manual documentation.</td></tr>" +
                "<tr><td>• <b>Insights:</b></td><td>Real-time tracking and itineraries.</td></tr>" +
                "</table>" +
                "<br><b>Premium Features:</b>" +
                "<ul>" +
                "<li>Dynamic, secure booking infrastructure.</li>" +
                "<li>Advanced AI travel planning assistant.</li>" +
                "<li>Real-time verification notifications.</li>" +
                "<li>User-centric design & interactive UI.</li>" +
                "</ul>" +
                "</body></html>";
        description.setText(htmlContent);
        description.setBounds(15, 100, 450, 320);
        description.setBackground(cardBg);
        contentCard.add(description);

        JButton exitBtn = new JButton("Close System Info") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(primaryDark);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        exitBtn.setBounds(35, 435, 180, 40);
        exitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setBorder(null);
        exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitBtn.addActionListener(this);
        contentCard.add(exitBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String args[]) {
        new About();
    }
}