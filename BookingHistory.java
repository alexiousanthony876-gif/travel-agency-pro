package travel_and_Tourism_Organisation_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.EmptyBorder;

public class BookingHistory extends JFrame {
    String username;
    JTable hotelTable, packageTable, transportTable;
    DefaultTableModel hotelModel, packageModel, transportModel;

    // Premium Color Palette
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color mainBg = new Color(245, 247, 250); // Off-white
    Color accentColor = new Color(37, 99, 235); // Royal Blue
    Color textDark = new Color(33, 43, 54); // Dark Slate
    Color textLight = new Color(148, 163, 184); // Muted Slate

    public BookingHistory(String username) {
        this.username = username;
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
        setTitle("Booking Management | Travel Wizard");
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainBg);

        // --- Header ---
        JPanel header = new JPanel();
        header.setBackground(primaryDark);
        header.setPreferredSize(new Dimension(1920, 80));
        header.setLayout(null);
        add(header, BorderLayout.NORTH);

        JLabel title = new JLabel("MY BOOKING HISTORY");
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

        // --- Main Tabs ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tabbedPane.setBackground(primaryDark);
        tabbedPane.setForeground(Color.WHITE);

        tabbedPane.addTab("  Package Bookings  ", createPackagePanel());
        tabbedPane.addTab("  Hotel Bookings  ", createHotelPanel());
        tabbedPane.addTab("  Transport Bookings  ", createTransportPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createHotelPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(mainBg);
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));

        String[] cols = { "Hotel Name", "Persons", "Days", "Total Cost", "Check-In", "Check-Out", "Status" };
        hotelModel = new DefaultTableModel(cols, 0);
        hotelTable = createModernTable(hotelModel);

        JScrollPane sp = new JScrollPane(hotelTable);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panel.add(sp, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        btnPanel.setOpaque(false);

        JButton refreshBtn = createStyledButton("Refresh List", new Color(100, 116, 139));
        refreshBtn.addActionListener(e -> loadHotelData());
        btnPanel.add(refreshBtn);

        JButton invoiceBtn = createStyledButton("Download Invoice", accentColor);
        invoiceBtn.addActionListener(e -> generateHotelInvoice());
        btnPanel.add(invoiceBtn);

        JButton whatsappBtn = createStyledButton("Share via WhatsApp", new Color(37, 211, 102));
        whatsappBtn.addActionListener(e -> shareWhatsApp("Hotel"));
        btnPanel.add(whatsappBtn);

        JButton emailBtn = createStyledButton("Send via Email", new Color(234, 67, 53));
        emailBtn.addActionListener(e -> shareEmail("Hotel"));
        btnPanel.add(emailBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        loadHotelData();
        return panel;
    }

    private JPanel createPackagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(mainBg);
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));

        String[] cols = { "Package Name", "Persons", "Total Cost", "Travel Date", "Status" };
        packageModel = new DefaultTableModel(cols, 0);
        packageTable = createModernTable(packageModel);

        JScrollPane sp = new JScrollPane(packageTable);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panel.add(sp, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        btnPanel.setOpaque(false);

        JButton refreshBtn = createStyledButton("Refresh List", new Color(100, 116, 139));
        refreshBtn.addActionListener(e -> loadPackageData());
        btnPanel.add(refreshBtn);

        JButton invoiceBtn = createStyledButton("Download Invoice", accentColor);
        invoiceBtn.addActionListener(e -> generatePackageInvoice());
        btnPanel.add(invoiceBtn);

        JButton whatsappBtn = createStyledButton("Share via WhatsApp", new Color(37, 211, 102));
        whatsappBtn.addActionListener(e -> shareWhatsApp("Package"));
        btnPanel.add(whatsappBtn);

        JButton emailBtn = createStyledButton("Send via Email", new Color(234, 67, 53));
        emailBtn.addActionListener(e -> shareEmail("Package"));
        btnPanel.add(emailBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        loadPackageData();
        return panel;
    }

    private JPanel createTransportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(mainBg);
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));

        String[] cols = { "Type", "Source", "Destination", "Class", "Travel Date", "Guests", "Total Cost" };
        transportModel = new DefaultTableModel(cols, 0);
        transportTable = createModernTable(transportModel);

        JScrollPane sp = new JScrollPane(transportTable);
        sp.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panel.add(sp, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        btnPanel.setOpaque(false);

        JButton refreshBtn = createStyledButton("Refresh List", new Color(100, 116, 139));
        refreshBtn.addActionListener(e -> loadTransportData());
        btnPanel.add(refreshBtn);

        JButton invoiceBtn = createStyledButton("Download Invoice", accentColor);
        invoiceBtn.addActionListener(e -> generateTransportInvoice());
        btnPanel.add(invoiceBtn);

        JButton whatsappBtn = createStyledButton("Share via WhatsApp", new Color(37, 211, 102));
        whatsappBtn.addActionListener(e -> shareWhatsApp("Transport"));
        btnPanel.add(whatsappBtn);

        JButton emailBtn = createStyledButton("Send via Email", new Color(234, 67, 53));
        emailBtn.addActionListener(e -> shareEmail("Transport"));
        btnPanel.add(emailBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        loadTransportData();
        return panel;
    }

    private JTable createModernTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(248, 250, 252));
        table.getTableHeader().setForeground(textDark);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));
        table.setSelectionBackground(new Color(239, 246, 255));
        table.setSelectionForeground(accentColor);
        table.setGridColor(new Color(241, 245, 249));
        table.setShowVerticalLines(false);

        return table;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(180, 45));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(null);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void loadHotelData() {
        hotelModel.setRowCount(0);
        try {
            Conn c = new Conn();
            String query = "select * from bookHotel where username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                String hotel = rs.getString("hotel");
                String persons = rs.getString("persons");
                String days = rs.getString("days");
                String price = rs.getString("price");
                String checkIn = "N/A", checkOut = "N/A", status = "Pending";
                try {
                    checkIn = rs.getString("checkIn");
                } catch (Exception e) {
                }
                try {
                    checkOut = rs.getString("checkOut");
                } catch (Exception e) {
                }
                try {
                    status = rs.getString("status");
                } catch (Exception e) {
                }
                if (status == null)
                    status = "Pending";
                hotelModel.addRow(new Object[] { hotel, persons, days, price, checkIn, checkOut, status });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPackageData() {
        packageModel.setRowCount(0);
        try {
            Conn c = new Conn();
            String query = "select * from bookPackage where username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                String pack = rs.getString("package");
                String persons = rs.getString("persons");
                String price = rs.getString("price");
                String date = "N/A", status = "Pending";
                try {
                    date = rs.getString("travelDate");
                } catch (Exception e) {
                }
                try {
                    status = rs.getString("status");
                } catch (Exception e) {
                }
                if (status == null)
                    status = "Pending";
                packageModel.addRow(new Object[] { pack, persons, price, date, status });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTransportData() {
        transportModel.setRowCount(0);
        try {
            Conn c = new Conn();
            String query = "select * from bookTransport where username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                String type = rs.getString("type");
                String source = rs.getString("source");
                String dest = rs.getString("destination");
                String category = rs.getString("class");
                String date = rs.getString("date");
                String persons = rs.getString("persons");
                String price = rs.getString("price");
                transportModel.addRow(new Object[] { type, source, dest, category, date, persons, price });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateHotelInvoice() {
        int row = hotelTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a booking first.");
            return;
        }
        showInvoiceDialog(createInvoiceText("Hotel", row, hotelTable));
    }

    private void generatePackageInvoice() {
        int row = packageTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a booking first.");
            return;
        }
        showInvoiceDialog(createInvoiceText("Package", row, packageTable));
    }

    private void generateTransportInvoice() {
        int row = transportTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a booking first.");
            return;
        }
        showInvoiceDialog(createInvoiceText("Transport", row, transportTable));
    }

    private String createInvoiceText(String type, int row, JTable table) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------\n");
        sb.append("         TRAVEL WIZARD OFFICIAL INVOICE    \n");
        sb.append("-------------------------------------------\n\n");
        sb.append("Date: ").append(new java.util.Date()).append("\n");
        sb.append("Customer: ").append(username).append("\n\n");

        for (int i = 0; i < table.getColumnCount(); i++) {
            sb.append(table.getColumnName(i)).append(": ").append(table.getValueAt(row, i)).append("\n");
        }

        sb.append("\n-------------------------------------------\n");
        sb.append("Status: PAID & VERIFIED\n");
        sb.append("-------------------------------------------\n");
        return sb.toString();
    }

    private void showInvoiceDialog(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setEditable(false);
        area.setBorder(new EmptyBorder(20, 20, 20, 20));
        JScrollPane pane = new JScrollPane(area);
        pane.setPreferredSize(new Dimension(500, 600));
        JOptionPane.showMessageDialog(null, pane, "Official Invoice", JOptionPane.PLAIN_MESSAGE);
    }

    private void shareWhatsApp(String type) {
        int row = -1;
        JTable table = null;
        if (type.equals("Hotel")) {
            row = hotelTable.getSelectedRow();
            table = hotelTable;
        } else if (type.equals("Package")) {
            row = packageTable.getSelectedRow();
            table = packageTable;
        } else {
            row = transportTable.getSelectedRow();
            table = transportTable;
        }

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a booking from the table first!");
            return;
        }

        try {
            String phone = null; // No longer fetching from DB as phone column is removed
            if (phone == null || phone.isEmpty()) {
                phone = JOptionPane.showInputDialog("Enter WhatsApp Phone Number (with country code):");
            }
            if (phone == null)
                return;

            String message = "Travel Wizard Booking Info:\n" + formatDetailsForShare(type, row, table);
            String url = "https://wa.me/" + phone.replaceAll("[^0-9]", "") + "?text="
                    + java.net.URLEncoder.encode(message, "UTF-8");
            Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not open WhatsApp: " + e.getMessage());
        }
    }

    private void shareEmail(String type) {
        int row = -1;
        JTable table = null;
        if (type.equals("Hotel")) {
            row = hotelTable.getSelectedRow();
            table = hotelTable;
        } else if (type.equals("Package")) {
            row = packageTable.getSelectedRow();
            table = packageTable;
        } else {
            row = transportTable.getSelectedRow();
            table = transportTable;
        }

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a booking from the table first!");
            return;
        }

        try {
            String email = getUserContact("email");
            if (email == null || email.isEmpty()) {
                email = JOptionPane.showInputDialog("Enter your Email Address:");
            }
            if (email == null)
                return;

            String subject = java.net.URLEncoder.encode("Travel Wizard Booking Details", "UTF-8").replace("+", "%20");
            String body = java.net.URLEncoder.encode(formatDetailsForShare(type, row, table), "UTF-8").replace("+",
                    "%20");
            String mailto = "mailto:" + email + "?subject=" + subject + "&body=" + body;
            Desktop.getDesktop().browse(new java.net.URI(mailto));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not open Email Client: " + e.getMessage());
        }
    }

    private String getUserContact(String field) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select " + field + " from customer where username = '" + username + "'");
            if (rs.next())
                return rs.getString(field);
        } catch (Exception e) {
        }
        return null;
    }

    private String formatDetailsForShare(String type, int row, JTable table) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ").append(type.toUpperCase()).append(" BOOKING ---\n");
        for (int i = 0; i < table.getColumnCount(); i++) {
            sb.append(table.getColumnName(i)).append(": ").append(table.getValueAt(row, i)).append("\n");
        }
        sb.append("Generated by Travel Wizard App");
        return sb.toString();
    }

    public static void main(String[] args) {
        new BookingHistory("alex");
    }
}
