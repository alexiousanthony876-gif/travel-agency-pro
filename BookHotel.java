package travel_and_Tourism_Organisation_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BookHotel extends JFrame implements ActionListener {

    JTextField t1, t2;
    Choice c1, c2, c3, cDay, cMonth, cYear, cOutDay, cOutMonth, cOutYear;
    JLabel l1, l2, l3, l4, l5;
    JButton b1, b2, b3, checkAvail, btnCheckPrice, btnBookNow;
    static String username;

    // Premium Color Palette
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color accentColor = new Color(0, 122, 255); // iOS Blue
    Color mainBg = new Color(245, 247, 250); // Off-white
    Color textDark = new Color(33, 43, 54); // Dark Grey

    public BookHotel(String username) {
        this.username = username;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Luxury Accommodation Booking - Travel Wizard");
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainBg);

        // --- Header ---
        JPanel header = new JPanel();
        header.setBackground(primaryDark);
        header.setPreferredSize(new Dimension(1920, 80));
        header.setLayout(null);
        add(header, BorderLayout.NORTH);

        JLabel title = new JLabel("HOTEL RESERVATIONS");
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

        // --- Main Content Area with Scroll Support ---
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
        formCard.setBounds(60, 40, 680, 650);
        formCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        mainContainer.add(formCard);

        JLabel formHeader = new JLabel("Reservation Details");
        formHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
        formHeader.setForeground(primaryDark);
        formHeader.setBounds(40, 20, 300, 40);
        formCard.add(formHeader);

        int lblX = 40, fieldX = 240, y = 75, step = 42;

        // Form Fields
        addFormLabel(formCard, "Account", lblX, y);
        l1 = new JLabel(username);
        l1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        l1.setBounds(fieldX, y, 300, 25);
        formCard.add(l1);

        y += step;
        addFormLabel(formCard, "Select Luxury Hotel", lblX, y);
        c1 = new Choice();
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from hotel");
            while (rs.next()) {
                c1.add(rs.getString("name"));
            }
            rs.close();
        } catch (Exception e) {
        }
        c1.setBounds(fieldX, y, 350, 30);
        c1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formCard.add(c1);

        y += step;
        addFormLabel(formCard, "Total Guests", lblX, y);
        t1 = new JTextField("1");
        t1.setBounds(fieldX, y, 150, 30);
        styleTextField(t1);
        formCard.add(t1);

        y += step;
        addFormLabel(formCard, "Duration (Days)", lblX, y);
        t2 = new JTextField("1");
        t2.setBounds(fieldX, y, 150, 30);
        styleTextField(t2);
        formCard.add(t2);

        y += step;
        addFormLabel(formCard, "Check-In Date", lblX, y);
        cDay = new Choice();
        for (int i = 1; i <= 31; i++)
            cDay.add("" + i);
        cMonth = new Choice();
        String[] ms = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        for (String m : ms)
            cMonth.add(m);
        cYear = new Choice();
        for (int i = 2024; i <= 2030; i++)
            cYear.add("" + i);

        cDay.setBounds(fieldX, y, 50, 30);
        cMonth.setBounds(fieldX + 60, y, 70, 30);
        cYear.setBounds(fieldX + 140, y, 80, 30);
        formCard.add(cDay);
        formCard.add(cMonth);
        formCard.add(cYear);

        checkAvail = createStyledButton("Real-time Check", fieldX + 230, y - 5, 150, 32, new Color(100, 116, 139));
        formCard.add(checkAvail);

        y += step;
        addFormLabel(formCard, "Check-Out Date", lblX, y);
        cOutDay = new Choice();
        for (int i = 1; i <= 31; i++)
            cOutDay.add("" + i);
        cOutMonth = new Choice();
        for (String m : ms)
            cOutMonth.add(m);
        cOutYear = new Choice();
        for (int i = 2024; i <= 2030; i++)
            cOutYear.add("" + i);

        cOutDay.setBounds(fieldX, y, 50, 30);
        cOutMonth.setBounds(fieldX + 60, y, 70, 30);
        cOutYear.setBounds(fieldX + 140, y, 80, 30);
        formCard.add(cOutDay);
        formCard.add(cOutMonth);
        formCard.add(cOutYear);

        y += step;
        addFormLabel(formCard, "Room Preference", lblX, y);
        c2 = new Choice();
        c2.add("Standard (Non-AC)");
        c2.add("Deluxe (AC)");
        c2.setBounds(fieldX, y, 200, 30);
        formCard.add(c2);

        y += step;
        addFormLabel(formCard, "Dining Option", lblX, y);
        c3 = new Choice();
        c3.add("Room Only");
        c3.add("Full Board (Food Inc.)");
        c3.setBounds(fieldX, y, 200, 30);
        formCard.add(c3);

        y += step;
        addFormLabel(formCard, "Total Price", lblX, y);
        l5 = new JLabel("Estimation Pending");
        l5.setFont(new Font("Segoe UI", Font.BOLD, 18));
        l5.setForeground(new Color(21, 128, 61));
        l5.setBounds(fieldX, y - 5, 220, 30);
        formCard.add(l5);

        // Buttons
        y += step + 30;
        b1 = createStyledButton("Check Price", 40, y, 180, 45, new Color(100, 116, 139));
        formCard.add(b1);

        b2 = createStyledButton("Confirm Booking", 240, y, 220, 45, accentColor);
        formCard.add(b2);

        b3 = createStyledButton("Cancel", 480, y, 150, 45, new Color(239, 68, 68));
        formCard.add(b3);

        // Right Side: Graphic/Policy Section
        JPanel graphicPanel = new JPanel(null);
        graphicPanel.setBounds(780, 40, 520, 650);
        graphicPanel.setBackground(primaryDark);
        mainContainer.add(graphicPanel);

        ImageIcon i1 = new ImageIcon(
                ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/book (1).jpg"));
        Image i2 = i1.getImage().getScaledInstance(520, 400, Image.SCALE_SMOOTH);
        JLabel la1 = new JLabel(new ImageIcon(i2));
        la1.setBounds(0, 0, 520, 400);
        graphicPanel.add(la1);

        JPanel infoTextPanel = new JPanel();
        infoTextPanel.setBackground(new Color(30, 41, 59));
        infoTextPanel.setBounds(0, 400, 520, 300);
        infoTextPanel.setLayout(new BoxLayout(infoTextPanel, BoxLayout.Y_AXIS));
        infoTextPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        graphicPanel.add(infoTextPanel);

        addInfoText(infoTextPanel, "Premium Reservation Benefits:", true);
        addInfoText(infoTextPanel, "• Instant Confirmation on availability", false);
        addInfoText(infoTextPanel, "• Transparent pricing with no hidden fees", false);
        addInfoText(infoTextPanel, "• Concierge assistance included", false);
        addInfoText(infoTextPanel, "• Best Rate Guarantee for all partners", false);

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
        if (e.getSource() == b1) { // Check Price
            estimatePrice();
        } else if (e.getSource() == b2) { // Book
            processBooking();
        } else if (e.getSource() == b3) { // Back
            setVisible(false);
        } else if (e.getSource() == checkAvail) {
            checkAvailabilityStatus();
        }
    }

    private void estimatePrice() {
        try {
            Conn c = new Conn();
            String q1 = "select * from hotel where name = '" + c1.getSelectedItem() + "'";
            ResultSet rs = c.s.executeQuery(q1);
            if (rs.next()) {
                int cost = rs.getInt("costperperson");
                int food = rs.getInt("foodincluded");
                int ac = rs.getInt("acroom");

                int persons = Integer.parseInt(t1.getText());
                int days = Integer.parseInt(t2.getText());

                if (persons * days > 0) {
                    int total = cost;
                    if (c2.getSelectedItem().contains("AC"))
                        total += ac;
                    if (c3.getSelectedItem().contains("Food"))
                        total += food;
                    total = total * persons * days;
                    l5.setText("Rs " + total);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter valid number of guests/days.");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error calculating price: " + ex.getMessage());
        }
    }

    private void processBooking() {
        if (l5.getText().equals("Estimation Pending") || l5.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Please Estimate Price first!");
            return;
        }

        // Manual simple check (using the provided logic)
        if (!checkAvailabilityStatus())
            return;

        try {
            String checkInDate = cDay.getSelectedItem() + "-" + cMonth.getSelectedItem() + "-"
                    + cYear.getSelectedItem();
            String checkOutDate = cOutDay.getSelectedItem() + "-" + cOutMonth.getSelectedItem() + "-"
                    + cOutYear.getSelectedItem();

            Conn c = new Conn();
            String q1 = "insert into bookHotel (username, hotel, persons, days, ac, food, price, checkIn, checkOut) values('"
                    + username + "', '" + c1.getSelectedItem() + "', '" + t1.getText() + "', '"
                    + t2.getText() + "', '" + (c2.getSelectedItem().contains("AC") ? "AC" : "Non-AC") + "', '"
                    + (c3.getSelectedItem().contains("Food") ? "Yes" : "No") + "', '" + l5.getText()
                    + "', '" + checkInDate + "', '" + checkOutDate + "')";

            c.s.executeUpdate(q1);
            JOptionPane.showMessageDialog(null, "Reservation Successful!\nCheck-In: " + checkInDate);
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Booking Failed: " + ex.getMessage());
        }
    }

    private boolean checkAvailabilityStatus() {
        try {
            Conn c = new Conn();
            String date = cDay.getSelectedItem() + "-" + cMonth.getSelectedItem() + "-" + cYear.getSelectedItem();
            String q = "select count(*) from bookHotel where hotel = '" + c1.getSelectedItem() + "' AND checkIn = '"
                    + date + "'";
            ResultSet rs = c.s.executeQuery(q);
            if (rs.next()) {
                int count = rs.getInt(1);
                int limit = 5;
                if (count >= limit) {
                    JOptionPane.showMessageDialog(null, "Fully Booked for " + date);
                    return false;
                } else {
                    JOptionPane.showMessageDialog(null, "Available! (" + (limit - count) + " rooms remaining)");
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return true;
    }

    public static void main(String[] args) {
        new BookHotel("Guest");
    }
}