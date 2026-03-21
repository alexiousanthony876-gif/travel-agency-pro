package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminViewBookings extends JFrame implements ActionListener {
    JTable table;
    JButton back, refresh;
    DefaultTableModel model;

    public AdminViewBookings() {
        setTitle("Admin - View All Bookings");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(153, 0, 0));
        p1.setPreferredSize(new Dimension(1600, 60));
        p1.setLayout(null);
        add(p1, BorderLayout.NORTH);

        JLabel label = new JLabel("ALL SYSTEM BOOKINGS");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.BOLD, 24));
        label.setBounds(50, 10, 500, 40);
        p1.add(label);

        back = new JButton("Back");
        back.setBounds(1100, 15, 100, 30);
        back.addActionListener(this);
        p1.add(back);

        refresh = new JButton("Refresh");
        refresh.setBounds(1220, 15, 100, 30);
        refresh.addActionListener(e -> fetchData());
        p1.add(refresh);

        String[] columnNames = { "Type", "Username", "Item (Hotel/Package)", "Source/AC", "Destination/Food",
                "Date/Days", "Persons", "Price", "Status" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        fetchData();
        setVisible(true);
    }

    private void fetchData() {
        model.setRowCount(0);
        try {
            Conn c = new Conn();

            // Hotel Bookings
            ResultSet rs = c.s.executeQuery("select * from bookHotel");
            while (rs.next()) {
                model.addRow(new Object[] {
                        "Hotel",
                        rs.getString("username"),
                        rs.getString("hotel"),
                        rs.getString("ac"),
                        rs.getString("food"),
                        rs.getString("days"),
                        rs.getString("persons"),
                        rs.getString("price"),
                        "Active"
                });
            }
            rs.close();

            // Package Bookings
            rs = c.s.executeQuery("select * from bookPackage");
            while (rs.next()) {
                model.addRow(new Object[] {
                        "Package",
                        rs.getString("username"),
                        rs.getString("package"),
                        "-",
                        "-",
                        "-",
                        rs.getString("persons"),
                        rs.getString("price"),
                        "Active"
                });
            }
            rs.close();

            // Transport Bookings
            rs = c.s.executeQuery("select * from bookTransport");
            while (rs.next()) {
                model.addRow(new Object[] {
                        "Transport",
                        rs.getString("username"),
                        rs.getString("type"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getString("date"),
                        rs.getString("persons"),
                        rs.getString("price"),
                        "Active"
                });
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AdminViewBookings();
    }
}
