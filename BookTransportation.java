package travel_and_Tourism_Organisation_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BookTransportation extends JFrame implements ActionListener {

    JTextField tGuests;
    Choice cType, cVehicle, cFrom, cTo, cDay, cMonth, cYear;
    JLabel lUser, lPrice;
    JButton btnEstimate, btnBook, btnCancel;
    static String username;

    // Premium Color Palette (Consistent with other screens)
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color accentColor = new Color(0, 122, 255); // iOS Blue
    Color mainBg = new Color(245, 247, 250); // Off-white
    Color textDark = new Color(33, 43, 54); // Dark Grey

    public BookTransportation(String username) {
        this.username = username;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Transportation Booking - Travel Wizard");
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainBg);

        // --- Header ---
        JPanel header = new JPanel();
        header.setBackground(new Color(30, 41, 59));
        header.setPreferredSize(new Dimension(1920, 80));
        header.setLayout(null);
        add(header, BorderLayout.NORTH);

        JLabel title = new JLabel("TRANSPORTATION BOOKING");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
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

        // --- Main Content Area ---
        JPanel mainContainer = new JPanel(null);
        mainContainer.setBackground(mainBg);
        mainContainer.setPreferredSize(new Dimension(1350, 850));

        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);

        // Left Side: Booking Form Card
        JPanel formCard = new JPanel(null);
        formCard.setBackground(Color.WHITE);
        formCard.setBounds(60, 40, 680, 600);
        formCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        mainContainer.add(formCard);

        JLabel formHeader = new JLabel("Journey Details");
        formHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
        formHeader.setForeground(primaryDark);
        formHeader.setBounds(40, 20, 300, 40);
        formCard.add(formHeader);

        int lblX = 40, fieldX = 240, y = 80, step = 45;

        // Form Fields
        addFormLabel(formCard, "Account", lblX, y);
        lUser = new JLabel(username);
        lUser.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        lUser.setBounds(fieldX, y, 300, 25);
        formCard.add(lUser);

        y += step;
        addFormLabel(formCard, "Transport Type", lblX, y);
        cType = new Choice();
        cType.add("Flight");
        cType.add("Train");
        cType.add("Private Cab");
        cType.add("Luxury Bus");
        cType.setBounds(fieldX, y, 350, 30);
        cType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formCard.add(cType);

        y += step;
        addFormLabel(formCard, "From (Source)", lblX, y);
        cFrom = new Choice();
        String[] cities = { "Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata", "Hyderabad", "Pune", "Goa",
                "Jaipur" };
        for (String city : cities)
            cFrom.add(city);
        cFrom.setBounds(fieldX, y, 350, 30);
        cFrom.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formCard.add(cFrom);

        y += step;
        addFormLabel(formCard, "To (Destination)", lblX, y);
        cTo = new Choice();
        for (String city : cities)
            cTo.add(city);
        cTo.select(1); // Default to a different city
        cTo.setBounds(fieldX, y, 350, 30);
        cTo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formCard.add(cTo);

        y += step;
        addFormLabel(formCard, "Class / Category", lblX, y);
        cVehicle = new Choice();
        cVehicle.add("Economy / General");
        cVehicle.add("Business / First Class");
        cVehicle.add("Sleeper / AC");
        cVehicle.add("Premium / SUV");
        cVehicle.setBounds(fieldX, y, 350, 30);
        cVehicle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formCard.add(cVehicle);

        y += step;
        addFormLabel(formCard, "Travel Date", lblX, y);
        cDay = new Choice();
        for (int i = 1; i <= 31; i++)
            cDay.add("" + i);
        cMonth = new Choice();
        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        for (String m : months)
            cMonth.add(m);
        cYear = new Choice();
        for (int i = 2024; i <= 2026; i++)
            cYear.add("" + i);

        cDay.setBounds(fieldX, y, 50, 30);
        cMonth.setBounds(fieldX + 60, y, 70, 30);
        cYear.setBounds(fieldX + 140, y, 80, 30);
        formCard.add(cDay);
        formCard.add(cMonth);
        formCard.add(cYear);

        y += step;
        addFormLabel(formCard, "Total Passengers", lblX, y);
        tGuests = new JTextField("1");
        tGuests.setBounds(fieldX, y, 150, 30);
        styleTextField(tGuests);
        formCard.add(tGuests);

        y += step + 10;
        addFormLabel(formCard, "Total Price", lblX, y);
        lPrice = new JLabel("Estimation Pending");
        lPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lPrice.setForeground(new Color(21, 128, 61));
        lPrice.setBounds(fieldX, y - 5, 300, 30);
        formCard.add(lPrice);

        // Buttons
        y += step + 30;
        btnEstimate = createStyledButton("Check Price", 40, y, 180, 45, new Color(100, 116, 139));
        formCard.add(btnEstimate);

        btnBook = createStyledButton("Confirm Booking", 240, y, 220, 45, accentColor);
        formCard.add(btnBook);

        btnCancel = createStyledButton("Cancel", 480, y, 150, 45, new Color(239, 68, 68));
        formCard.add(btnCancel);

        // Right Side: Graphic/Info Panel
        JPanel graphicPanel = new JPanel(null);
        graphicPanel.setBounds(780, 40, 520, 600);
        graphicPanel.setBackground(primaryDark);
        mainContainer.add(graphicPanel);

        // Placeholder for transport image
        try {
            ImageIcon i1 = new ImageIcon(
                    ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/dest13.jpg")); // Re-using
                                                                                                               // a nice
                                                                                                               // scenic
                                                                                                               // image
            Image i2 = i1.getImage().getScaledInstance(520, 350, Image.SCALE_SMOOTH);
            JLabel imgLbl = new JLabel(new ImageIcon(i2));
            imgLbl.setBounds(0, 0, 520, 350);
            graphicPanel.add(imgLbl);
        } catch (Exception e) {
        }

        JPanel infoTextPanel = new JPanel();
        infoTextPanel.setBackground(new Color(30, 41, 59));
        infoTextPanel.setBounds(0, 350, 520, 250);
        infoTextPanel.setLayout(new BoxLayout(infoTextPanel, BoxLayout.Y_AXIS));
        infoTextPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        graphicPanel.add(infoTextPanel);

        addInfoText(infoTextPanel, "Travel Safe & Comfortably:", true);
        addInfoText(infoTextPanel, "• Verified vehicles and certified pilots", false);
        addInfoText(infoTextPanel, "• Real-time journey tracking", false);
        addInfoText(infoTextPanel, "• 24/7 Roadside & Air assistance", false);
        addInfoText(infoTextPanel, "• Flexible rescheduling options", false);

        setVisible(true);
    }

    private void addFormLabel(JPanel p, String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(new Color(71, 85, 105));
        l.setBounds(x, y, 200, 25);
        p.add(l);
    }

    private void styleTextField(JTextField t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(5, 10, 5, 10)));
    }

    private JButton createStyledButton(String text, int x, int y, int w, int h, Color bg) {
        JButton b = new JButton(text);
        b.setBounds(x, y, w, h);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(null);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addActionListener(this);
        return b;
    }

    private void addInfoText(JPanel p, String text, boolean isHeader) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", isHeader ? Font.BOLD : Font.PLAIN, isHeader ? 18 : 14));
        l.setBorder(new EmptyBorder(0, 0, 10, 0));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEstimate) {
            calculatePrice();
        } else if (e.getSource() == btnBook) {
            processBooking();
        } else if (e.getSource() == btnCancel) {
            setVisible(false);
        }
    }

    private void calculatePrice() {
        try {
            int numGuests = Integer.parseInt(tGuests.getText());
            if (numGuests <= 0)
                throw new NumberFormatException();

            int baseRate = 0;
            String type = cType.getSelectedItem();
            if (type.equals("Flight"))
                baseRate = 5000;
            else if (type.equals("Train"))
                baseRate = 1200;
            else if (type.equals("Private Cab"))
                baseRate = 2500;
            else
                baseRate = 800;

            String category = cVehicle.getSelectedItem();
            if (category.contains("Business") || category.contains("Premium"))
                baseRate *= 2.5;
            else if (category.contains("Sleeper") || category.contains("AC"))
                baseRate *= 1.5;

            int totalPrice = baseRate * numGuests;
            lPrice.setText("Rs " + totalPrice);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred during price calculation.");
            lPrice.setText("Estimation Pending");
        }
    }

    private void processBooking() {
        if (lPrice.getText().equals("Estimation Pending") || lPrice.getText().equals("Invalid Route")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid route and estimate fare first!");
            return;
        }

        String from = cFrom.getSelectedItem();
        String to = cTo.getSelectedItem();
        if (from.equals(to)) {
            JOptionPane.showMessageDialog(null, "Source and Destination cannot be the same!");
            return;
        }

        try {
            String date = cDay.getSelectedItem() + "-" + cMonth.getSelectedItem() + "-" + cYear.getSelectedItem();
            String type = cType.getSelectedItem();
            String source = cFrom.getSelectedItem();
            String dest = cTo.getSelectedItem();
            String category = cVehicle.getSelectedItem();
            String guests = tGuests.getText();
            String price = lPrice.getText();

            Conn c = new Conn();
            String q = "insert into bookTransport values('" + username + "', '" + type + "', '" + source + "', '" + dest
                    + "', '" + category + "', '" + date + "', '" + guests + "', '" + price + "')";

            c.s.executeUpdate(q);
            JOptionPane.showMessageDialog(null,
                    "Booking Successfully confirmed for " + date + "!\nRoute: " + source + " to " + dest);
            setVisible(false);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Booking failed. Please try again.");
        }
    }

    public static void main(String[] args) {
        new BookTransportation("Guest");
    }
}
