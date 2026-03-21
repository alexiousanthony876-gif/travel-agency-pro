package travel_and_Tourism_Organisation_System;

import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class BookPackage extends JFrame implements ActionListener {

	JTextField t1;
	JLabel l1, l2, l3, l4, l5;
	Choice c1, cDay, cMonth, cYear;
	JButton b1, b2, b3;
	static String username;

	// Theme Colors
	Color primaryDark = new Color(15, 23, 42); // Midnight Blue
	Color accentColor = new Color(0, 122, 255); // iOS Blue
	Color mainBg = new Color(245, 247, 250); // Off-white
	Color textDark = new Color(33, 43, 54); // Dark Grey

	public BookPackage(String username) {
		this.username = username;
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Confirm Your Booking - Travel Wizard");
		setLayout(new BorderLayout());
		getContentPane().setBackground(mainBg);

		// --- Header ---
		JPanel header = new JPanel();
		header.setBackground(primaryDark);
		header.setPreferredSize(new Dimension(1920, 80));
		header.setLayout(null);
		add(header, BorderLayout.NORTH);

		JLabel title = new JLabel("SECURE PACKAGE BOOKING");
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

		// --- Main Content (Split Screen) ---
		JPanel contentArea = new JPanel(null);
		contentArea.setBackground(mainBg);
		add(contentArea, BorderLayout.CENTER);

		// Left Side: Booking Form Card
		JPanel formCard = new JPanel(null);
		formCard.setBackground(Color.WHITE);
		formCard.setBounds(80, 50, 600, 650);
		formCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
		contentArea.add(formCard);

		JLabel formHeader = new JLabel("Booking Details");
		formHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
		formHeader.setForeground(primaryDark);
		formHeader.setBounds(40, 30, 300, 40);
		formCard.add(formHeader);

		int lblX = 40, fieldX = 250, y = 100, step = 50;

		// Form Fields
		addFormLabel(formCard, "Username", lblX, y);
		l1 = new JLabel(username);
		l1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		l1.setBounds(fieldX, y, 300, 25);
		formCard.add(l1);

		y += step;
		addFormLabel(formCard, "Select Package", lblX, y);
		c1 = new Choice();
		for (String[] pack : PackageData.PACKAGES) {
			c1.add(pack[1]);
		}
		c1.setBounds(fieldX, y, 300, 30);
		c1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		formCard.add(c1);

		y += step;
		addFormLabel(formCard, "Total Persons", lblX, y);
		t1 = new JTextField("1");
		t1.setBounds(fieldX, y, 300, 30);
		t1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		t1.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(203, 213, 225)),
				new EmptyBorder(5, 10, 5, 10)));
		formCard.add(t1);

		y += step;
		addFormLabel(formCard, "Travel Date", lblX, y);
		JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		datePanel.setOpaque(false);
		datePanel.setBounds(fieldX, y, 320, 40);

		cDay = new Choice();
		for (int i = 1; i <= 31; i++)
			cDay.add("" + i);
		cDay.setPreferredSize(new Dimension(60, 30));
		datePanel.add(cDay);

		cMonth = new Choice();
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		for (String m : months)
			cMonth.add(m);
		cMonth.setPreferredSize(new Dimension(80, 30));
		datePanel.add(cMonth);

		cYear = new Choice();
		for (int i = 2024; i <= 2030; i++)
			cYear.add("" + i);
		cYear.setPreferredSize(new Dimension(80, 30));
		datePanel.add(cYear);
		formCard.add(datePanel);

		y += step + 10;
		addFormLabel(formCard, "Document ID", lblX, y);
		l2 = new JLabel("-");
		l2.setBounds(fieldX, y, 300, 25);
		l2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		formCard.add(l2);

		y += step;
		addFormLabel(formCard, "ID Number", lblX, y);
		l3 = new JLabel("-");
		l3.setBounds(fieldX, y, 300, 25);
		l3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		formCard.add(l3);

		y += step;
		addFormLabel(formCard, "Price Estimate", lblX, y);
		l5 = new JLabel("Rs 0 / person");
		l5.setFont(new Font("Segoe UI", Font.BOLD, 20));
		l5.setForeground(new Color(21, 128, 61));
		l5.setBounds(fieldX, y, 300, 30);
		formCard.add(l5);

		// Buttons
		b1 = createStyledButton("Check Price", 40, 500, new Color(100, 116, 139));
		formCard.add(b1);

		b2 = createStyledButton("Confirm Booking", 210, 500, accentColor);
		formCard.add(b2);

		b3 = createStyledButton("Cancel", 380, 500, new Color(239, 68, 68));
		formCard.add(b3);

		// Right Side: Graphic Section
		JPanel graphicPanel = new JPanel(null);
		graphicPanel.setBounds(720, 50, 600, 650);
		graphicPanel.setBackground(primaryDark);
		contentArea.add(graphicPanel);

		ImageIcon i1 = new ImageIcon(
				ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/bookpackage.jpg"));
		Image i2 = i1.getImage().getScaledInstance(600, 650, Image.SCALE_SMOOTH);
		JLabel la1 = new JLabel(new ImageIcon(i2));
		la1.setBounds(0, 0, 600, 650);

		// Add text on image
		JLabel imgText = new JLabel("Your journey begins here.");
		imgText.setFont(new Font("Segoe UI", Font.BOLD, 32));
		imgText.setForeground(Color.WHITE);
		imgText.setBounds(40, 550, 500, 50);
		la1.add(imgText);

		graphicPanel.add(la1);

		// Load Customer Data Initial
		try {
			Conn c = new Conn();
			ResultSet rs = c.s.executeQuery("select * from customer where username = '" + username + "'");
			if (rs.next()) {
				l2.setText(rs.getString("id"));
				l3.setText(rs.getString("number"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		setVisible(true);
	}

	private void addFormLabel(JPanel p, String text, int x, int y) {
		JLabel l = new JLabel(text);
		l.setFont(new Font("Segoe UI", Font.BOLD, 14));
		l.setForeground(new Color(71, 85, 105));
		l.setBounds(x, y, 200, 25);
		p.add(l);
	}

	private JButton createStyledButton(String text, int x, int y, Color bg) {
		JButton b = new JButton(text);
		b.setBounds(x, y, 150, 40);
		b.setBackground(bg);
		b.setForeground(Color.WHITE);
		b.setFont(new Font("Segoe UI", Font.BOLD, 14));
		b.setFocusPainted(false);
		b.setBorder(null);
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b.addActionListener(this);
		return b;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			String pack = c1.getSelectedItem();
			int cost = PackageData.getPrice(pack);
			try {
				int p = Integer.parseInt(t1.getText());
				int total = cost * p;
				if (total > 0) {
					l5.setText("Rs " + total);
				} else {
					JOptionPane.showMessageDialog(null, "Please enter valid persons count!");
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid Number");
			}
		} else if (e.getSource() == b2) {
			try {
				if (l5.getText().contains("Rs 0") || l5.getText().equals("Rs 0")) {
					JOptionPane.showMessageDialog(null, "Please estimate price first");
				} else {
					Conn c = new Conn();
					String date = cDay.getSelectedItem() + "-" + cMonth.getSelectedItem() + "-"
							+ cYear.getSelectedItem();
					String q1 = "insert into bookPackage (username, package, persons, id, number, price, travelDate) values('"
							+ username + "','"
							+ c1.getSelectedItem() + "', '"
							+ t1.getText() + "', '"
							+ l2.getText() + "', '"
							+ l3.getText() + "', '"
							+ l5.getText() + "', '"
							+ date + "')";
					c.s.executeUpdate(q1);
					JOptionPane.showMessageDialog(null, "Booking Confirmed Successfully!\nDeparture: " + date);
					setVisible(false);
				}
			} catch (Exception ee) {
				JOptionPane.showMessageDialog(null, "Booking Error: " + ee.getMessage());
			}
		} else if (e.getSource() == b3) {
			setVisible(false);
		}
	}

	public static void main(String[] args) {
		new BookPackage(username);
	}
}
