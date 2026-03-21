package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class CheckHotels extends JFrame {

    // --- Premium Color Palette ---
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color mainBg = new Color(245, 247, 250); // Off-white/Light Gray
    Color accentColor = new Color(0, 122, 255); // iOS Blue
    Color textDark = new Color(33, 43, 54); // Dark Grey
    Color textLight = new Color(99, 115, 129); // Muted Slate

    String[] hotelNames = {
            "JW Marriott, Mumbai", "Mandarin Oriental, NY", "Four Seasons, Paris", "Radisson Blue, Delhi",
            "Classio Hotel, Goa", "The Bay Club, Seychelles", "Breeze Blows Resort", "Quick Stop Motel",
            "Happy Mornings Inn", "Moss View Cottage", "Grand Plaza, London", "Ocean View Resort, Maldives",
            "Mountain Peak Lodge, Aspen", "City Lights Hotel, Tokyo", "Golden Sands, Dubai",
            "Royal Palace, Jaipur", "Emerald Bay, Vietnam", "Sunset Boulevard, LA", "Heritage Inn, Rome",
            "Skyline Suites, Singapore", "Riverfront Resort, Bali", "The Palms, Miami", "Crystal Tower, Shanghai",
            "Blue Horizon, Santorini", "The Majestic, Barcelona", "Silver Leaf, Toronto", "Crown Jewel, Sydney",
            "Paradise Cove, Fiji", "Urban Retreat, Berlin", "Highland Stay, Scotland", "Sea Breeze Inn, California",
            "The Oasis, Cairo", "Maple Leaf Lodge, Canada", "Starburst Hotel, Vegas", "Orchid Garden, Thailand",
            "Velvet Room, Milan", "Diamond Suites, Monaco", "Lakeside Resort, Zurich", "Aurora Hotel, Iceland",
            "Summit Hotel, Alps"
    };

    public CheckHotels() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Luxury Hotel Gallery - Travel Wizard");
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainBg);

        // ================= HEADER =================
        JPanel header = new JPanel();
        header.setBackground(primaryDark);
        header.setPreferredSize(new Dimension(1920, 80));
        header.setLayout(null);
        add(header, BorderLayout.NORTH);

        JLabel title = new JLabel("OUR PARTNER LUXURY HOTELS");
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

        // Use a dynamic grid or FlowLayout inside a ScrollPane
        // For 40 hotels, a GridBagLayout or a GridLayout with fixed columns is best
        gridPanel.setLayout(new GridLayout(0, 4, 30, 30)); // 4 columns, auto rows

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);

        // Add Hotel Cards
        for (int i = 0; i < 40; i++) {
            gridPanel.add(createHotelCard(i));
        }

        setVisible(true);
    }

    private JPanel createHotelCard(int index) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(300, 350));
        card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        // 1. Hotel Image (Top)
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        String imagePath = "travel_and_Tourism_Organisation_System/icons/hotel" + (index + 1) + ".jpg";
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(imagePath));
            Image img = icon.getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imgLabel.setText("Image Missing");
            imgLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        }
        card.add(imgLabel, BorderLayout.NORTH);

        // 2. Info Panel (Bottom)
        JPanel info = new JPanel();
        info.setLayout(null);
        info.setBackground(Color.WHITE);
        info.setPreferredSize(new Dimension(300, 150));
        card.add(info, BorderLayout.CENTER);

        JLabel nameLbl = new JLabel(hotelNames[index]);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLbl.setForeground(textDark);
        nameLbl.setBounds(15, 15, 280, 25);
        info.add(nameLbl);

        JLabel locIcon = new JLabel("📍 Premium Destination");
        locIcon.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        locIcon.setForeground(textLight);
        locIcon.setBounds(15, 45, 280, 20);
        info.add(locIcon);

        JLabel rating = new JLabel("★★★★★");
        rating.setForeground(new Color(251, 191, 36)); // Amber Gold
        rating.setBounds(15, 75, 100, 20);
        info.add(rating);

        JButton viewBtn = new JButton("Book Stay");
        viewBtn.setBounds(15, 105, 120, 30);
        viewBtn.setBackground(accentColor);
        viewBtn.setForeground(Color.WHITE);
        viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        viewBtn.setBorder(null);
        viewBtn.setFocusPainted(false);
        viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Link to BookHotel
        viewBtn.addActionListener(e -> {
            new BookHotel("Guest").setVisible(true); // Default to Guest if no context
        });

        info.add(viewBtn);

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

    public static void main(String[] args) {
        new CheckHotels();
    }
}