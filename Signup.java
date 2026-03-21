package travel_and_Tourism_Organisation_System;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Signup extends JFrame implements ActionListener {

	private JTextField textField, textField_1, textField_2;
	private JButton b1, b2;
	private JComboBox<String> roleCombo;

	// Premium Color Palette
	Color primaryDark = new Color(15, 23, 42); // Midnight Blue
	Color accentColor = new Color(37, 99, 235); // Modern Royal Blue
	Color textDim = new Color(148, 163, 184); // Slate 400
	Color borderColor = new Color(226, 232, 240); // Slate 200

	public static void main(String[] args) {
		new Signup().setVisible(true);
	}

	public Signup() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
		setTitle("Join Travel Wizard | Create Account");
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		// Main Container Panel
		JPanel mainWrapper = new JPanel(new GridLayout(1, 2));
		add(mainWrapper, BorderLayout.CENTER);

		// --- LEFT SIDE: Brand & Value Prop ---
		JPanel leftPanel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				ImageIcon i1 = new ImageIcon(
						ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/dest2.png"));
				g2d.drawImage(i1.getImage(), 0, 0, getWidth(), getHeight(), null);
				g2d.setPaint(new GradientPaint(0, 0, new Color(15, 23, 42, 230), getWidth(), getHeight(),
						new Color(15, 23, 42, 120)));
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		mainWrapper.add(leftPanel);

		int contentX = 80;
		JLabel brandName = new JLabel("TRAVEL WIZARD");
		brandName.setFont(new Font("Segoe UI", Font.BOLD, 56));
		brandName.setForeground(Color.WHITE);
		brandName.setBounds(contentX, 140, 600, 70);
		leftPanel.add(brandName);

		JLabel tagline = new JLabel("Start your luxury journey today.");
		tagline.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		tagline.setForeground(new Color(200, 200, 200));
		tagline.setBounds(contentX, 210, 600, 40);
		leftPanel.add(tagline);

		// --- RIGHT SIDE: Professional Signup Form ---
		JPanel rightPanel = new JPanel(null);
		rightPanel.setBackground(Color.WHITE);
		mainWrapper.add(rightPanel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width / 4) - 250, 20, 500, 800);
		scrollPane.setBorder(null);
		rightPanel.add(scrollPane);

		JPanel signupCard = new JPanel(null);
		signupCard.setBackground(Color.WHITE);
		signupCard.setPreferredSize(new Dimension(460, 950));
		scrollPane.setViewportView(signupCard);

		JLabel welcomeTitle = new JLabel("Create an account");
		welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
		welcomeTitle.setForeground(primaryDark);
		welcomeTitle.setBounds(0, 0, 460, 45);
		signupCard.add(welcomeTitle);

		JLabel welcomeSub = new JLabel("Join thousands of travelers worldwide.");
		welcomeSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		welcomeSub.setForeground(textDim);
		welcomeSub.setBounds(0, 50, 460, 25);
		signupCard.add(welcomeSub);

		int y = 100, step = 75;

		textField = addField(signupCard, "Username", y);
		y += step;
		textField_1 = addField(signupCard, "Full Name", y);
		y += step;
		textField_2 = addPasswordField(signupCard, "Password", y);
		y += step;

		// Account Type Section
		JLabel lblRole = new JLabel("Account Type");
		lblRole.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblRole.setForeground(primaryDark);
		lblRole.setBounds(0, y, 460, 20);
		signupCard.add(lblRole);

		roleCombo = new JComboBox<>(new String[] { "User", "Admin" });
		roleCombo.setBounds(0, y + 25, 460, 45);
		roleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		roleCombo.setBackground(Color.WHITE);
		signupCard.add(roleCombo);

		y += step;

		b1 = new JButton("Create Account");
		b1.setBounds(0, y, 460, 50);
		b1.setFont(new Font("Segoe UI Bold", Font.BOLD, 16));
		b1.setBackground(accentColor);
		b1.setForeground(Color.WHITE);
		b1.setBorder(null);
		b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b1.addActionListener(this);
		signupCard.add(b1);

		b2 = new JButton("Already have an account? Log in");
		b2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		b2.setForeground(accentColor);
		b2.setContentAreaFilled(false);
		b2.setBorder(null);
		b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b2.setBounds(0, y + 60, 460, 25);
		b2.addActionListener(this);
		signupCard.add(b2);

		setVisible(true);
	}

	private JTextField addField(JPanel p, String label, int y) {
		JLabel l = new JLabel(label);
		l.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		l.setForeground(primaryDark);
		l.setBounds(0, y, 460, 20);
		p.add(l);

		JTextField t = new JTextField();
		t.setBounds(0, y + 30, 460, 48);
		t.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		t.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				new EmptyBorder(0, 15, 0, 15)));
		p.add(t);
		return t;
	}

	private JTextField addPasswordField(JPanel p, String label, int y) {
		JLabel l = new JLabel(label);
		l.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		l.setForeground(primaryDark);
		l.setBounds(0, y, 460, 20);
		p.add(l);

		JPasswordField t = new JPasswordField();
		t.setBounds(0, y + 30, 460, 48);
		t.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		t.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				new EmptyBorder(0, 15, 0, 15)));
		p.add(t);
		return t;
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			try {
				String user = textField.getText();
				String name = textField_1.getText();
				String pass = textField_2.getText();

				if (user.isEmpty() || name.isEmpty() || pass.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill all fields.");
					return;
				}

				Conn con = new Conn();

				// 1. Insert into Account Table
				String sqlAccount = "insert into account(username, name, password, security, answer, role) values(?, ?, ?, ?, ?, ?)";
				PreparedStatement stAcc = con.c.prepareStatement(sqlAccount);
				stAcc.setString(1, user);
				stAcc.setString(2, name);
				stAcc.setString(3, SecurityUtil.hashPassword(pass));
				stAcc.setString(4, "Standard Account"); 
				stAcc.setString(5, "N/A");
				stAcc.setString(6, (String) roleCombo.getSelectedItem());
				stAcc.executeUpdate();

				// 2. Insert into Customer Table (No Phone)
				String sqlCustomer = "insert into customer(username, name) values(?, ?)";
				PreparedStatement stCust = con.c.prepareStatement(sqlCustomer);
				stCust.setString(1, user);
				stCust.setString(2, name);
				stCust.executeUpdate();

				JOptionPane.showMessageDialog(null, "Account Created Successfully! You can now log in.");
				setVisible(false);
				new Login().setVisible(true);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
				e.printStackTrace();
			}
		} else if (ae.getSource() == b2) {
			setVisible(false);
			new Login().setVisible(true);
		}
	}
}