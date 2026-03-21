package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Destination extends JFrame {

    // --- Premium Color Palette ---
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color mainBg = new Color(245, 247, 250); // Off-white/Light Gray
    Color accentColor = new Color(0, 122, 255); // iOS Blue
    Color textDark = new Color(33, 43, 54); // Dark Grey
    Color textLight = new Color(99, 115, 129); // Muted Slate

    // Extensions for first 10 images since they vary
    String[] firstTenExts = { "jpeg", "png", "jpg", "jpg", "jpg", "jpg", "jpeg", "jpg", "jpg", "jpg" };

    String[] destNames = {
            "Eiffel Tower, Paris", "Statue of Liberty, NY", "Taj Mahal, India", "Great Wall of China",
            "Machu Picchu, Peru", "Colosseum, Rome", "Christ the Redeemer, Rio", "Pyramids of Giza, Egypt",
            "Sydney Opera House", "Santorini, Greece", "Burj Khalifa, Dubai", "Mount Fuji, Japan",
            "Grand Canyon, USA", "Petra, Jordan", "Maldives Beaches", "Bora Bora, Tahiti",
            "Niagara Falls, Canada", "Stonehenge, UK", "Forbidden City, Beijing", "Acropolis of Athens",
            "Angkor Wat, Cambodia", "Chichen Itza, Mexico", "Ha Long Bay, Vietnam", "Venice Canals, Italy",
            "Yellowstone, USA", "Bali, Indonesia", "Louvre Museum, Paris", "Golden Gate Bridge",
            "Times Square, NY", "Mount Everest, Nepal", "Victoria Falls, Africa", "Kyoto Temples, Japan",
            "Great Barrier Reef", "Matterhorn, Switzerland", "St. Basil's Cathedral", "Neuschwanstein Castle",
            "Galapagos Islands", "Dubai Marina", "Buckingham Palace", "Central Park, NY"
    };

    public Destination() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Explore World Wonders - Travel Wizard");
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainBg);

        // ================= HEADER =================
        JPanel header = new JPanel();
        header.setBackground(primaryDark);
        header.setPreferredSize(new Dimension(1920, 80));
        header.setLayout(null);
        add(header, BorderLayout.NORTH);

        JLabel title = new JLabel("TOP GLOBAL DESTINATIONS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(50, 20, 500, 40);
        header.add(title);

        JButton backBtn = new JButton("← Back to Dashboard");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorder(null);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBounds(1100, 25, 200, 30);
        backBtn.addActionListener(e -> setVisible(false));
        header.add(backBtn);

        // ================= MAIN CONTENT (GRID) =================
        JPanel gridPanel = new JPanel();
        gridPanel.setBackground(mainBg);
        gridPanel.setBorder(new EmptyBorder(40, 50, 40, 50));
        gridPanel.setLayout(new GridLayout(0, 3, 40, 40)); // 3 columns for larger cards

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        add(scrollPane, BorderLayout.CENTER);

        // Add Destination Cards
        for (int i = 0; i < destNames.length; i++) {
            gridPanel.add(createDestCard(i));
        }

        setVisible(true);
    }

    private JPanel createDestCard(int index) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(400, 400));
        card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        // 1. Destination Image (Top)
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        String ext = (index < 10) ? firstTenExts[index] : "jpg";
        String imagePath = "travel_and_Tourism_Organisation_System/icons/dest" + (index + 1) + "." + ext;

        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(imagePath));
            Image img = icon.getImage().getScaledInstance(450, 280, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imgLabel.setText("Image Missing");
            imgLabel.setBackground(new Color(241, 245, 249));
            imgLabel.setOpaque(true);
        }
        card.add(imgLabel, BorderLayout.NORTH);

        // 2. Info Panel (Bottom)
        JPanel info = new JPanel();
        info.setLayout(null);
        info.setBackground(Color.WHITE);
        info.setPreferredSize(new Dimension(400, 60)); // Reduced height since names are removed
        card.add(info, BorderLayout.CENTER);

        JButton exploreBtn = new JButton("Explore Destination");
        exploreBtn.setBounds(20, 15, 160, 30);
        exploreBtn.setBackground(accentColor);
        exploreBtn.setForeground(Color.WHITE);
        exploreBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        exploreBtn.setBorder(null);
        exploreBtn.setFocusPainted(false);
        exploreBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Link to packages for this destination (simplified to just open packages)
        exploreBtn.addActionListener(e -> {
            new CheckPackage("Guest").setVisible(true);
        });

        info.add(exploreBtn);

        // Add Card Hover Effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(accentColor, 1));
                info.setBackground(new Color(248, 250, 252));
            }

            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
                info.setBackground(Color.WHITE);
            }
        });

        return card;
    }

    public static void main(String args[]) {
        new Destination();
    }
}
