package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminManageHotels extends JFrame implements ActionListener {
    JTextField t1, t2, t3, t4;
    JButton addBtn, back, refresh;
    JTable table;
    DefaultTableModel model;

    public AdminManageHotels() {
        setTitle("Admin - Manage Hotels");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(153, 0, 0));
        p1.setBounds(0, 0, 1600, 60);
        p1.setLayout(null);
        add(p1);

        JLabel label = new JLabel("MANAGE HOTELS");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.BOLD, 24));
        label.setBounds(50, 10, 500, 40);
        p1.add(label);

        back = new JButton("Back");
        back.setBounds(1100, 15, 100, 30);
        back.addActionListener(this);
        p1.add(back);

        // Sidebar Add Form
        JPanel form = new JPanel();
        form.setLayout(null);
        form.setBounds(50, 100, 400, 500);
        form.setBorder(BorderFactory.createTitledBorder("Add New Hotel"));
        add(form);

        JLabel l1 = new JLabel("Hotel Name:");
        l1.setBounds(20, 40, 150, 25);
        form.add(l1);
        t1 = new JTextField();
        t1.setBounds(150, 40, 200, 25);
        form.add(t1);

        JLabel l2 = new JLabel("Cost Per Person:");
        l2.setBounds(20, 90, 150, 25);
        form.add(l2);
        t2 = new JTextField();
        t2.setBounds(150, 90, 200, 25);
        form.add(t2);

        JLabel l3 = new JLabel("AC Room Cost:");
        l3.setBounds(20, 140, 150, 25);
        form.add(l3);
        t3 = new JTextField();
        t3.setBounds(150, 140, 200, 25);
        form.add(t3);

        JLabel l4 = new JLabel("Food Charge:");
        l4.setBounds(20, 190, 150, 25);
        form.add(l4);
        t4 = new JTextField();
        t4.setBounds(150, 190, 200, 25);
        form.add(t4);

        addBtn = new JButton("Add Hotel");
        addBtn.setBounds(150, 250, 100, 30);
        addBtn.addActionListener(this);
        form.add(addBtn);

        // Table
        String[] columnNames = { "Name", "Cost/Person", "AC Cost", "Food Cost" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(500, 100, 750, 500);
        add(sp);

        fetchData();
        setVisible(true);
    }

    private void fetchData() {
        model.setRowCount(0);
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from hotel");
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("name"),
                        rs.getString("costperperson"),
                        rs.getString("acroom"),
                        rs.getString("foodincluded")
                });
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            String name = t1.getText();
            String cpp = t2.getText();
            String ac = t3.getText();
            String food = t4.getText();
            try {
                Conn c = new Conn();
                String q = "insert into hotel values('" + name + "', '" + cpp + "', '" + food + "', '" + ac + "')";
                c.s.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "Hotel Added Successfully");
                fetchData();
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AdminManageHotels();
    }
}
