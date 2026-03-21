package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class RateExperience extends JFrame implements ActionListener {
    String username;
    JToggleButton[] starButtons;
    JTextArea tComment;
    JButton submit, back;
    int selectedRating = 5;

    // --- Premium Color Palette ---
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color accentBlue = new Color(0, 122, 255); // iOS Blue
    Color mainBg = new Color(245, 247, 250); // Soft Gray
    Color cardBg = Color.WHITE;
    Color textDark = new Color(30, 41, 59); // Slate 800
    Color textMuted = new Color(100, 116, 139); // Slate 500
    Color starGold = new Color(250, 204, 21); // Warning Yellow

    public RateExperience(String username) {
        this.username = username;
        setTitle("Share Your Story | Travel Wizard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(null);
        getContentPane().setBackground(mainBg);

        // ================= MAIN CONTAINER =================
        JPanel container = new JPanel(null);
        container.setBounds(0, 0, 1000, 650);
        container.setBackground(mainBg);
        add(container);

        // ================= SIDEBAR (Hero Section) =================
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Using a scenic icon from the project
                    ImageIcon i1 = new ImageIcon(
                            ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/dest5.jpg"));
                    if (i1.getIconWidth() == -1)
                        i1 = new ImageIcon(ClassLoader
                                .getSystemResource("travel_and_Tourism_Organisation_System/icons/about.jpg"));

                    Image i2 = i1.getImage().getScaledInstance(400, 650, Image.SCALE_SMOOTH);
                    g.drawImage(i2, 0, 0, null);

                    // Premium Gradient Overlay
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gp = new GradientPaint(0, 0, new Color(15, 23, 42, 30), 0, 650,
                            new Color(15, 23, 42, 240));
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, 400, 650);
                } catch (Exception e) {
                    g.setColor(primaryDark);
                    g.fillRect(0, 0, 400, 650);
                }
            }
        };
        sidebar.setBounds(0, 0, 400, 650);
        sidebar.setLayout(null);
        container.add(sidebar);

        JLabel overlayTitle = new JLabel("Your Opinion");
        overlayTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        overlayTitle.setForeground(Color.WHITE);
        overlayTitle.setBounds(45, 480, 300, 45);
        sidebar.add(overlayTitle);

        JLabel overlaySub = new JLabel("Help us refine the Travel Wizard experience.");
        overlaySub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        overlaySub.setForeground(new Color(220, 220, 220));
        overlaySub.setBounds(45, 525, 320, 25);
        sidebar.add(overlaySub);

        // ================= FORM SECTION =================
        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(400, 0, 600, 650);
        formPanel.setBackground(cardBg);
        container.add(formPanel);

        // Close Button
        JButton closeBtn = new JButton("✕");
        closeBtn.setBounds(550, 15, 30, 30);
        closeBtn.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 20));
        closeBtn.setForeground(textMuted);
        closeBtn.setBorder(null);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> setVisible(false));
        formPanel.add(closeBtn);

        JLabel heading = new JLabel("Rate Your Journey");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
        heading.setForeground(primaryDark);
        heading.setBounds(50, 60, 400, 40);
        formPanel.add(heading);

        JPanel detailLine = new JPanel();
        detailLine.setBounds(50, 105, 40, 4);
        detailLine.setBackground(accentBlue);
        formPanel.add(detailLine);

        // --- Rating Selector ---
        JLabel lRating = new JLabel("OVERALL SATISFACTION");
        lRating.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lRating.setForeground(textMuted);
        lRating.setBounds(50, 150, 200, 20);
        formPanel.add(lRating);

        JPanel starContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        starContainer.setBackground(cardBg);
        starContainer.setBounds(45, 180, 500, 60);
        formPanel.add(starContainer);

        starButtons = new JToggleButton[5];
        ButtonGroup group = new ButtonGroup();

        for (int i = 0; i < 5; i++) {
            final int rating = i + 1;
            starButtons[i] = new JToggleButton(rating + " ★");
            starButtons[i].setFont(new Font("Segoe UI", Font.BOLD, 16));
            starButtons[i].setPreferredSize(new Dimension(85, 45));
            starButtons[i].setFocusPainted(false);
            starButtons[i].setBackground(new Color(248, 250, 252));
            starButtons[i].setForeground(textMuted);
            starButtons[i].setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
            starButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            starButtons[i].addActionListener(e -> {
                selectedRating = rating;
                updateStarUI();
            });

            group.add(starButtons[i]);
            starContainer.add(starButtons[i]);
        }
        starButtons[4].setSelected(true); // Default 5 star
        updateStarUI();

        // --- Comment Section ---
        JLabel lComment = new JLabel("DETAILED FEEDBACK");
        lComment.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lComment.setForeground(textMuted);
        lComment.setBounds(50, 270, 200, 20);
        formPanel.add(lComment);

        tComment = new JTextArea("Share your favorite moments or things we could improve...");
        tComment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tComment.setLineWrap(true);
        tComment.setWrapStyleWord(true);
        tComment.setForeground(textMuted);
        tComment.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Clear placeholder on focus
        tComment.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tComment.getText().startsWith("Share your")) {
                    tComment.setText("");
                    tComment.setForeground(textDark);
                }
            }
        });

        JScrollPane sp = new JScrollPane(tComment);
        sp.setBounds(50, 300, 500, 180);
        sp.setBorder(null);
        formPanel.add(sp);

        // --- Action Buttons ---
        submit = new JButton("Submit Review") {
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
        submit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submit.setForeground(Color.WHITE);
        submit.setBounds(50, 520, 220, 50);
        submit.setContentAreaFilled(false);
        submit.setBorder(null);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submit.addActionListener(this);
        formPanel.add(submit);

        back = new JButton("Cancel") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(new Color(226, 232, 240));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setForeground(textMuted);
        back.setBounds(290, 520, 140, 50);
        back.setContentAreaFilled(false);
        back.setBorder(null);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(this);
        formPanel.add(back);

        setVisible(true);
    }

    private void updateStarUI() {
        for (int i = 0; i < 5; i++) {
            if (i < selectedRating) {
                starButtons[i].setBackground(new Color(239, 246, 255)); // Light Blue
                starButtons[i].setForeground(accentBlue);
                starButtons[i].setBorder(BorderFactory.createLineBorder(accentBlue, 2));
            } else {
                starButtons[i].setBackground(new Color(248, 250, 252));
                starButtons[i].setForeground(textMuted);
                starButtons[i].setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String comment = tComment.getText();
            if (comment.startsWith("Share your"))
                comment = "No detailed feedback.";
            String date = LocalDate.now().toString();

            try {
                Conn c = new Conn();
                String query = "insert into reviews values('" + username + "', 'Overall Experience', '" + selectedRating
                        + "', '" + comment + "', '" + date + "')";
                c.s.executeUpdate(query);

                // Success Feedback Animation/Message
                UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
                JOptionPane.showMessageDialog(this,
                        "★ Thank you! Your review helps us create magic for every traveler.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "System connectivity error. Please try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new RateExperience("Guest");
    }
}
