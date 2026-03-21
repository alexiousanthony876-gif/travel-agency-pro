package travel_and_Tourism_Organisation_System;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.awt.event.*;

public class AddCustomers extends JFrame implements ActionListener {

	private JTextField t1, t3, t5, t6, t8;
	private JLabel labelusername, labelname, decorImgLabel;
	private JComboBox<String> comboBox;
	private JRadioButton r1, r2;
	private JButton addBtn, backBtn;
	private String username;

	// Premium Color Palette
	Color primaryDark = new Color(15, 23, 42); // Midnight Blue
	Color mainBg = new Color(245, 247, 250); // Off-white
	Color accentColor = new Color(37, 99, 235); // Royal Blue
	Color textDark = new Color(33, 43, 54); // Dark Slate
	Color textLight = new Color(100, 116, 139); // Slate 500

	public AddCustomers(String username) {
		this.username = username;
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
		setTitle("Customer Onboarding | Travel Wizard");
		setLayout(new BorderLayout());
		getContentPane().setBackground(mainBg);

		// --- Header ---
		JPanel header = new JPanel();
		header.setBackground(primaryDark);
		header.setPreferredSize(new Dimension(1920, 80));
		header.setLayout(null);
		add(header, BorderLayout.NORTH);

		JLabel title = new JLabel("CUSTOMER REGISTRATION");
		title.setFont(new Font("Segoe UI", Font.BOLD, 24));
		title.setForeground(Color.WHITE);
		title.setBounds(50, 20, 500, 40);
		header.add(title);

		backBtn = new JButton("← Back to Dashboard");
		backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
		backBtn.setForeground(Color.WHITE);
		backBtn.setContentAreaFilled(false);
		backBtn.setBorder(null);
		backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backBtn.setBounds(1100, 25, 200, 30);
		backBtn.addActionListener(this);
		header.add(backBtn);

		// --- Main Content Area with Scroll Support ---
		JPanel mainContainer = new JPanel(null);
		mainContainer.setBackground(mainBg);
		mainContainer.setPreferredSize(new Dimension(1300, 850));

		JScrollPane scrollPane = new JScrollPane(mainContainer);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		add(scrollPane, BorderLayout.CENTER);

		// Form Card (Left)
		JPanel formCard = new JPanel(null);
		formCard.setBackground(Color.WHITE);
		formCard.setBounds(80, 40, 650, 630);
		formCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
		mainContainer.add(formCard);

		JLabel formHeader = new JLabel("Membership Details");
		formHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
		formHeader.setForeground(primaryDark);
		formHeader.setBounds(40, 20, 400, 40);
		formCard.add(formHeader);

		int lblX = 40, fieldX = 260, y = 80, step = 45;

		// Form Fields
		addFormLabel(formCard, "Account Username", lblX, y);
		labelusername = new JLabel(username);
		labelusername.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		labelusername.setBounds(fieldX, y, 300, 25);
		formCard.add(labelusername);

		y += step;
		addFormLabel(formCard, "Identification Type", lblX, y);
		comboBox = new JComboBox<>(new String[] { "Passport", "Aadhar Card", "Voter Id", "Driving License" });
		comboBox.setBounds(fieldX, y, 350, 32);
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		comboBox.setBackground(Color.WHITE);
		formCard.add(comboBox);

		y += step;
		addFormLabel(formCard, "Identification No.", lblX, y);
		t1 = addFormField(formCard, fieldX, y, 350);

		y += step;
		addFormLabel(formCard, "Legal Name", lblX, y);
		labelname = new JLabel("---");
		labelname.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		labelname.setBounds(fieldX, y, 350, 25);
		formCard.add(labelname);

		y += step;
		addFormLabel(formCard, "Gender Selection", lblX, y);
		r1 = new JRadioButton("Male");
		r1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		r1.setBackground(Color.WHITE);
		r1.setBounds(fieldX, y, 100, 25);
		r1.addActionListener(this);
		formCard.add(r1);

		r2 = new JRadioButton("Female");
		r2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		r2.setBackground(Color.WHITE);
		r2.setBounds(fieldX + 110, y, 100, 25);
		r2.addActionListener(this);
		formCard.add(r2);

		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(r1);
		bGroup.add(r2);
		r1.setSelected(true); // Default to Male

		y += step;
		addFormLabel(formCard, "Country of Origin", lblX, y);
		t3 = addFormField(formCard, fieldX, y, 350);

		y += step;
		addFormLabel(formCard, "Residential Address", lblX, y);
		t5 = addFormField(formCard, fieldX, y, 350);

		y += step;
		addFormLabel(formCard, "Email Address", lblX, y);
		t8 = addFormField(formCard, fieldX, y, 350);

		y += step + 15;
		addBtn = new JButton("COMPLETE REGISTRATION");
		addBtn.setBounds(40, y, 570, 50);
		addBtn.setBackground(accentColor);
		addBtn.setForeground(Color.WHITE);
		addBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
		addBtn.setFocusPainted(false);
		addBtn.setBorder(null);
		addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		addBtn.addActionListener(this);

		// Button Hover Effect
		addBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				addBtn.setBackground(accentColor.brighter());
			}

			public void mouseExited(MouseEvent e) {
				addBtn.setBackground(accentColor);
			}
		});
		formCard.add(addBtn);

		// Right Decoration Pane
		JPanel rightPane = new JPanel(null);
		rightPane.setBackground(primaryDark);
		rightPane.setBounds(760, 40, 550, 630);
		mainContainer.add(rightPane);

		decorImgLabel = new JLabel();
		decorImgLabel.setBounds(50, 25, 450, 580);
		rightPane.add(decorImgLabel);
		updateDynamicImage(); // Set initial image (Male)

		fetchAccountData();
		setVisible(true);
	}

	private void addFormLabel(JPanel p, String text, int x, int y) {
		JLabel l = new JLabel(text);
		l.setFont(new Font("Segoe UI", Font.BOLD, 14));
		l.setForeground(textLight);
		l.setBounds(x, y, 200, 25);
		p.add(l);
	}

	private JTextField addFormField(JPanel p, int x, int y, int w) {
		JTextField t = new JTextField();
		t.setBounds(x, y, w, 32);
		t.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		t.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(226, 232, 240)),
				new EmptyBorder(0, 10, 0, 10)));
		p.add(t);
		return t;
	}

	private void updateDynamicImage() {
		String imgPath = "travel_and_Tourism_Organisation_System/icons/newcustomer.jpg"; // Default Male

		if (r2.isSelected()) {
			// Using the stylish female traveler character icon
			imgPath = "travel_and_Tourism_Organisation_System/icons/female.png";
		}

		try {
			java.net.URL imgURL = ClassLoader.getSystemResource(imgPath);
			if (imgURL == null) {
				// Absolute fallback to male image
				imgURL = ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/newcustomer.jpg");
			}

			ImageIcon i1 = new ImageIcon(imgURL);
			int width = 450;
			int height = 580;

			// Character positioning and scaling
			if (r2.isSelected()) {
				// Portrait character looks best at a taller scale
				width = 450;
				height = 580;
				decorImgLabel.setBounds(50, 60, 450, 580);
			} else {
				decorImgLabel.setBounds(50, 60, 450, 580);
			}

			Image i2 = i1.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			decorImgLabel.setIcon(new ImageIcon(i2));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fetchAccountData() {
		try {
			Conn c = new Conn();
			ResultSet rs = c.s.executeQuery("select * from account where username = '" + username + "'");
			if (rs.next()) {
				labelusername.setText(rs.getString("username"));
				labelname.setText(rs.getString("name"));
			}
			rs.close();
		} catch (Exception e) {
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == r1 || ae.getSource() == r2) {
			updateDynamicImage();
		} else if (ae.getSource() == addBtn) {
			processRegistration();
		} else if (ae.getSource() == backBtn) {
			setVisible(false);
		}
	}

	private void processRegistration() {
		if (t1.getText().isEmpty() || t3.getText().isEmpty() || t5.getText().isEmpty()
				|| t8.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill all required fields.");
			return;
		}

		try {
			String gender = r1.isSelected() ? "Male" : "Female";
			Conn c = new Conn();
			String query = "insert into customer (username, id, number, name, gender, country, address, email) values('"
					+ labelusername.getText() + "', '" + comboBox.getSelectedItem() + "', '" + t1.getText() + "' , '"
					+ labelname.getText() + "', '" + gender + "', '" + t3.getText() + "', '" + t5.getText() + "', '"
					+ t8.getText() + "')";

			c.s.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "Registration Complete!");
			setVisible(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Registration Failed: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		new AddCustomers("alex");
	}
}
