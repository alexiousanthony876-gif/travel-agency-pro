package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class ItineraryView extends JFrame {
    String username;

    // Premium Color Palette
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color mainBg = new Color(245, 247, 250); // Off-white
    Color accentColor = new Color(37, 99, 235); // Royal Blue
    Color goldColor = new Color(245, 158, 11); // Amber

    public ItineraryView(String username) {
        this.username = username;
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
        setTitle("Travel Documents | Travel Wizard");
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainBg);

        // --- Header ---
        JPanel header = new JPanel();
        header.setBackground(primaryDark);
        header.setPreferredSize(new Dimension(1920, 80));
        header.setLayout(null);
        add(header, BorderLayout.NORTH);

        JLabel title = new JLabel("MY TRAVEL ITINERARY");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(50, 20, 400, 40);
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

        // --- Content Wrapper ---
        JPanel contentCard = new JPanel(new BorderLayout());
        contentCard.setBackground(Color.WHITE);
        contentCard.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(40, 100, 40, 100),
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1)));
        add(contentCard, BorderLayout.CENTER);

        JEditorPane itineraryPane = new JEditorPane();
        itineraryPane.setContentType("text/html");
        itineraryPane.setEditable(false);
        itineraryPane.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(itineraryPane);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        contentCard.add(scrollPane, BorderLayout.CENTER);

        // --- HTML Content Generation ---
        StringBuilder html = new StringBuilder();
        html.append(
                "<html><body style='font-family: \"Segoe UI\", Tahoma, sans-serif; padding: 40px; color: #1e293b; background-color: white;'>");
        html.append(
                "<h1 style='color: #0f172a; border-bottom: 2px solid #3b82f6; padding-bottom: 10px; margin-bottom: 30px;'>Official Travel Itinerary</h1>");

        try {
            Conn c = new Conn();
            boolean hasData = false;

            // 1. Fetch Package Details
            String qPackage = "select * from bookPackage where username = '" + username + "'";
            ResultSet rsPackage = c.s.executeQuery(qPackage);

            while (rsPackage.next()) {
                hasData = true;
                String packName = rsPackage.getString("package");
                String travelDate = rsPackage.getString("travelDate");

                html.append(
                        "<div style='background-color: #f8fafc; border: 1px solid #e2e8f0; padding: 25px; margin-bottom: 40px; border-left: 5px solid #2563eb;'>");
                html.append("<h2 style='color: #2563eb; margin-top: 0;'>\uD83C\uDF0D " + packName + "</h2>");
                html.append("<p style='font-size: 18px; color: #64748b; font-weight: bold;'>Scheduled Departure: "
                        + travelDate + "</p>");

                if (packName.contains("Gold")) {
                    html.append(getGoldItinerary());
                } else if (packName.contains("Silver")) {
                    html.append(getSilverItinerary());
                } else {
                    html.append(getBronzeItinerary());
                }
                html.append("</div>");
            }
            rsPackage.close();

            // 2. Fetch Hotel Details
            String qHotel = "select * from bookHotel where username = '" + username + "'";
            ResultSet rsHotel = c.s.executeQuery(qHotel);

            boolean hasHotel = false;
            StringBuilder hotelHtml = new StringBuilder();
            hotelHtml.append(
                    "<div style='background-color: #fffbeb; border: 1px solid #fde68a; padding: 25px; margin-bottom: 40px; border-left: 5px solid #d97706;'>");
            hotelHtml.append("<h2 style='color: #d97706; margin-top: 0;'>\uD83C\uDFE8 Accommodation Details</h2>");

            while (rsHotel.next()) {
                hasHotel = true;
                hasData = true;
                String hotel = rsHotel.getString("hotel");
                String days = rsHotel.getString("days");
                String checkIn = rsHotel.getString("checkIn");

                hotelHtml.append(
                        "<div style='margin-bottom: 20px; padding-bottom: 15px; border-bottom: 1px dashed #fcd34d;'>");
                hotelHtml.append(
                        "<p style='font-size: 18px; margin: 5px 0;'><b>Hotel:</b> <span style='color: #92400e;'>"
                                + hotel + "</span></p>");
                hotelHtml.append("<p style='margin: 5px 0; color: #475569;'><b>Duration:</b> " + days + " Nights</p>");
                hotelHtml.append("<p style='margin: 5px 0; color: #475569;'><b>Check-In:</b> " + checkIn + "</p>");
                hotelHtml.append(
                        "<p style='font-size: 12px; font-style: italic; color: #78350f;'>* Note: Standard Check-in 14:00 | Check-out 11:00</p>");
                hotelHtml.append("</div>");
            }

            if (hasHotel) {
                html.append(hotelHtml.toString()).append("</div>");
            }
            rsHotel.close();

            if (!hasData) {
                html.append("<div style='text-align: center; padding: 100px;'>");
                html.append("<h2 style='color: #94a3b8;'>No active travel itineraries found.</h2>");
                html.append("<p style='color: #cbd5e1;'>Book your next adventure to see it here!</p>");
                html.append("</div>");
            }

        } catch (Exception e) {
            html.append("<p>Error loading secure itinerary data. Please contact support.</p>");
        }

        html.append(
                "<p style='text-align: center; color: #94a3b8; font-size: 12px; margin-top: 50px;'>© 2024 Travel Wizard | Premium Experience System</p>");
        html.append("</body></html>");
        itineraryPane.setText(html.toString());

        setVisible(true);
    }

    private String getGoldItinerary() {
        return "<h3 style='color: #0f172a; margin-top: 25px;'>\uD83D\uDCC5 YOUR JOURNEY MAP</h3>" +
                "<div style='margin-left: 20px; border-left: 2px solid #e2e8f0; padding-left: 20px;'>" +
                "<p><b>DAY 01: Arrival & Luxury Welcome</b><br><span style='color: #64748b;'>Private chauffeured pickup & 5-star check-in with gala dinner.</span></p>"
                +
                "<p><b>DAY 02: Elite City Exploration</b><br><span style='color: #64748b;'>Guided cultural tours, museum fast-passes, and lunch at a 3-Michelin star venue.</span></p>"
                +
                "<p><b>DAY 03: Adventure & Spa</b><br><span style='color: #64748b;'>Morning private yacht/trek and afternoon premium spa rejuvenation.</span></p>"
                +
                "<p><b>DAY 04: Farewell</b><br><span style='color: #64748b;'>Buffet breakfast and private airport drop-off via executive lounge.</span></p>"
                +
                "</div>";
    }

    private String getSilverItinerary() {
        return "<h3 style='color: #0f172a; margin-top: 25px;'>\uD83D\uDCC5 TOUR TIMELINE</h3>" +
                "<div style='margin-left: 20px; border-left: 2px solid #e2e8f0; padding-left: 20px;'>" +
                "<p><b>DAY 01: Arrival</b><br><span style='color: #64748b;'>Standard pickup and evening local market walking tour.</span></p>"
                +
                "<p><b>DAY 02: City Tour</b><br><span style='color: #64748b;'>Full-day panoramic city tour via AC transport.</span></p>"
                +
                "<p><b>DAY 03: Departure</b><br><span style='color: #64748b;'>Morning leisure shop and airport drop-off.</span></p>"
                +
                "</div>";
    }

    private String getBronzeItinerary() {
        return "<h3 style='color: #0f172a; margin-top: 25px;'>\uD83D\uDCC5 BASIC SCHEDULE</h3>" +
                "<div style='margin-left: 20px; border-left: 2px solid #e2e8f0; padding-left: 20px;'>" +
                "<p><b>DAY 01: Check-in</b><br><span style='color: #64748b;'>Self-transfer to accommodation and leisure evening.</span></p>"
                +
                "<p><b>DAY 02: Sightseeing</b><br><span style='color: #64748b;'>Half-day shared group city tour.</span></p>"
                +
                "<p><b>DAY 03: Departure</b><br><span style='color: #64748b;'>Morning checkout at 11:00 AM.</span></p>"
                +
                "</div>";
    }

    public static void main(String[] args) {
        new ItineraryView("Guest");
    }
}
