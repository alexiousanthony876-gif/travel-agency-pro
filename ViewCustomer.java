package travel_and_Tourism_Organisation_System;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.sql.*;

public class ViewCustomer extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
    JButton back;
    String username;

    // Premium Color Palette
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color mainBg = new Color(245, 247, 250); // Off-white
    Color accentColor = new Color(37, 99, 235); // Royal Blue
    Color textDark = new Color(33, 43, 54); // Dark Slate
    Color textLight = new Color(100, 116, 139); // Slate 500

    public ViewCustomer(String username) {
        this.username = username;
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
        setTitle("My Profile | Travel Wizard");
        setLayout(new BorderLayout());
        getContentPane().setBackground(mainBg);

        // --- Header ---
        JPanel header = new JPanel();
        header.setBackground(primaryDark);
        header.setPreferredSize(new Dimension(1920, 80));
        header.setLayout(null);
        add(header, BorderLayout.NORTH);

        JLabel title = new JLabel("YOUR PROFILE DETAILS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(50, 20, 400, 40);
        header.add(title);

        back = new JButton("← Back to Dashboard");
        back.setFont(new Font("Segoe UI", Font.BOLD, 14));
        back.setForeground(Color.WHITE);
        back.setContentAreaFilled(false);
        back.setBorder(null);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.setBounds(1100, 25, 200, 30);
        back.addActionListener(this);
        header.add(back);

        // --- Main Content Area with Scroll Support ---
        JPanel mainContainer = new JPanel(null);
        mainContainer.setBackground(mainBg);
        mainContainer.setPreferredSize(new Dimension(1300, 850));

        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);

        // Profile Card (Left)
        JPanel profileCard = new JPanel(null);
        profileCard.setBackground(Color.WHITE);
        profileCard.setBounds(80, 40, 600, 630);
        profileCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        mainContainer.add(profileCard);

        JLabel cardHeader = new JLabel("Personal Information");
        cardHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
        cardHeader.setForeground(primaryDark);
        cardHeader.setBounds(40, 20, 400, 40);
        profileCard.add(cardHeader);

        int lblX = 40, valX = 260, y = 100, step = 45;

        // labels
        addLabel(profileCard, "Username", lblX, y);
        l1 = addValueLabel(profileCard, valX, y);

        y += step;
        addLabel(profileCard, "Identification ID", lblX, y);
        l2 = addValueLabel(profileCard, valX, y);

        y += step;
        addLabel(profileCard, "Identification No.", lblX, y);
        l3 = addValueLabel(profileCard, valX, y);

        y += step;
        addLabel(profileCard, "Full Name", lblX, y);
        l4 = addValueLabel(profileCard, valX, y);

        y += step;
        addLabel(profileCard, "Gender", lblX, y);
        l5 = addValueLabel(profileCard, valX, y);

        y += step;
        addLabel(profileCard, "Country", lblX, y);
        l6 = addValueLabel(profileCard, valX, y);

        y += step;
        addLabel(profileCard, "Permanent Address", lblX, y);
        l7 = addValueLabel(profileCard, valX, y);

        y += step;
        addLabel(profileCard, "Email Address", lblX, y);
        l9 = addValueLabel(profileCard, valX, y);

        // Right Side Decoration
        JPanel rightDecor = new JPanel(null);
        rightDecor.setBackground(primaryDark);
        rightDecor.setBounds(720, 40, 550, 630);
        mainContainer.add(rightDecor);

        ImageIcon i1 = new ImageIcon(
                ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/viewall.jpg"));
        if (i1.getImageLoadStatus() != MediaTracker.COMPLETE) {
            i1 = new ImageIcon(
                    ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/update.png"));
        }

        try {
            Image i2 = i1.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            JLabel decorImg = new JLabel(new ImageIcon(i2));
            decorImg.setBounds(75, 50, 400, 400);
            rightDecor.add(decorImg);
        } catch (Exception e) {
        }

        JLabel infoText = new JLabel("Your Profile Summary");
        infoText.setFont(new Font("Segoe UI", Font.BOLD, 22));
        infoText.setForeground(Color.WHITE);
        infoText.setBounds(0, 460, 550, 40);
        infoText.setHorizontalAlignment(SwingConstants.CENTER);
        rightDecor.add(infoText);

        JLabel subInfo = new JLabel("This information is used for your travel bookings.");
        subInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subInfo.setForeground(new Color(200, 200, 200));
        subInfo.setBounds(0, 500, 550, 20);
        subInfo.setHorizontalAlignment(SwingConstants.CENTER);
        rightDecor.add(subInfo);

        fetchData();
        setVisible(true);
    }

    private void addLabel(JPanel p, String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(textLight);
        l.setBounds(x, y, 200, 25);
        p.add(l);
    }

    private JLabel addValueLabel(JPanel p, int x, int y) {
        JLabel l = new JLabel("");
        l.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        l.setForeground(textDark);
        l.setBounds(x, y, 300, 25);
        p.add(l);
        return l;
    }

    private void fetchData() {
        try {
            Conn c = new Conn();
            String query = "select * from customer where username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                l1.setText(rs.getString("username"));
                l2.setText(rs.getString("id"));
                l3.setText(rs.getString("number"));
                l4.setText(rs.getString("name"));
                l5.setText(rs.getString("gender"));
                l6.setText(rs.getString("country"));
                l7.setText(rs.getString("address"));
                l9.setText(rs.getString("email"));
            } else {
                JOptionPane.showMessageDialog(null, "No profile found for this user.");
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
        new ViewCustomer("alex");
    }
}
