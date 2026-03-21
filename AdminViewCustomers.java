package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminViewCustomers extends JFrame implements ActionListener {
    JTable table;
    JButton back, refresh;
    DefaultTableModel model;

    public AdminViewCustomers() {
        setTitle("Admin - View All Customers");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(153, 0, 0));
        p1.setPreferredSize(new Dimension(1600, 60));
        p1.setLayout(null);
        add(p1, BorderLayout.NORTH);

        JLabel label = new JLabel("ALL REGISTERED CUSTOMERS");
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
        refresh.addActionListener(refreshListener());
        p1.add(refresh);

        String[] columnNames = { "Username", "ID Type", "ID Number", "Name", "Gender", "Country", "Address",
                "Email" };
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
            String query = "select * from customer";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("username"),
                        rs.getString("id"),
                        rs.getString("number"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("country"),
                        rs.getString("address"),
                        rs.getString("email")
                });
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ActionListener refreshListener() {
        return e -> fetchData();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AdminViewCustomers();
    }
}
