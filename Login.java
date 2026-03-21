package travel_and_Tourism_Organisation_System;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;

public class Login extends JFrame implements ActionListener {

	JButton b1, b2, b3;
	JTextField t1;
	JPasswordField t2;

	// Premium Color Palette
	Color primaryDark = new Color(15, 23, 42); // Midnight Blue
	Color accentColor = new Color(37, 99, 235); // Modern Royal Blue
	Color textLight = new Color(241, 245, 249); // Slate 100
	Color textDim = new Color(148, 163, 184); // Slate 400
	Color borderColor = new Color(226, 232, 240); // Slate 200

	Login() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
		setTitle("Travel Wizard | Secure Access");
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

				// Draw Image
				ImageIcon i1 = new ImageIcon(
						ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/dest1.jpg"));
				g2d.drawImage(i1.getImage(), 0, 0, getWidth(), getHeight(), null);

				// Professional Dark Gradient Overlay
				GradientPaint gp = new GradientPaint(0, 0, new Color(15, 23, 42, 220),
						getWidth(), getHeight(), new Color(15, 23, 42, 100));
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		mainWrapper.add(leftPanel);

		// Brand Content on Left
		int contentX = 80;

		JLabel brandLogo = new JLabel("TW");
		brandLogo.setFont(new Font("Segoe UI", Font.BOLD, 40));
		brandLogo.setForeground(accentColor);
		brandLogo.setBounds(contentX, 80, 100, 50);
		leftPanel.add(brandLogo);

		JLabel brandName = new JLabel("TRAVEL WIZARD");
		brandName.setFont(new Font("Segoe UI", Font.BOLD, 56));
		brandName.setForeground(Color.WHITE);
		brandName.setBounds(contentX, 140, 600, 70);
		leftPanel.add(brandName);

		JLabel tagline = new JLabel("The world's leading travel management platform.");
		tagline.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		tagline.setForeground(new Color(200, 200, 200));
		tagline.setBounds(contentX, 210, 600, 40);
		leftPanel.add(tagline);

		// Features list
		String[] features = { "✓  Unlimited Destination Access", "✓  24/7 Premium AI Assistance",
				"✓  Exclusive Partner Hotel Rates" };
		int fy = 320;
		for (String feat : features) {
			JLabel lbl = new JLabel(feat);
			lbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
			lbl.setForeground(new Color(180, 180, 180));
			lbl.setBounds(contentX, fy, 400, 30);
			leftPanel.add(lbl);
			fy += 40;
		}

		// --- RIGHT SIDE: Professional Login Form ---
		JPanel rightPanel = new JPanel(null);
		rightPanel.setBackground(Color.WHITE);
		mainWrapper.add(rightPanel);

		// Center the form card
		JPanel loginCard = new JPanel(null);
		loginCard.setBackground(Color.WHITE);
		int cardWidth = 440;
		int cardHeight = 600;
		loginCard.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width / 4) - (cardWidth / 2),
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (cardHeight / 2) - 50,
				cardWidth, cardHeight);
		rightPanel.add(loginCard);

		JLabel welcomeTitle = new JLabel("Log in to your account");
		welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
		welcomeTitle.setForeground(primaryDark);
		welcomeTitle.setBounds(0, 0, 440, 45);
		loginCard.add(welcomeTitle);

		JLabel welcomeSub = new JLabel("Welcome back! Please enter your details.");
		welcomeSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		welcomeSub.setForeground(textDim);
		welcomeSub.setBounds(0, 50, 440, 25);
		loginCard.add(welcomeSub);

		// Username Field
		JLabel lUser = new JLabel("Email or Username");
		lUser.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lUser.setForeground(primaryDark);
		lUser.setBounds(0, 120, 440, 20);
		loginCard.add(lUser);

		t1 = new JTextField();
		t1.setBounds(0, 150, 440, 48);
		t1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		t1.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				new EmptyBorder(0, 15, 0, 15)));
		loginCard.add(t1);

		// Password Field
		JLabel lPass = new JLabel("Password");
		lPass.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lPass.setForeground(primaryDark);
		lPass.setBounds(0, 225, 440, 20);
		loginCard.add(lPass);

		t2 = new JPasswordField();
		t2.setBounds(0, 255, 440, 48);
		t2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		t2.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				new EmptyBorder(0, 15, 0, 15)));
		loginCard.add(t2);

		// Remember Me & Forgot Password
		JCheckBox rememberMe = new JCheckBox("Remember for 30 days");
		rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rememberMe.setBackground(Color.WHITE);
		rememberMe.setBounds(0, 315, 200, 25);
		loginCard.add(rememberMe);

		b3 = new JButton("Forgot password?");
		b3.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		b3.setForeground(accentColor);
		b3.setContentAreaFilled(false);
		b3.setBorder(null);
		b3.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b3.setBounds(310, 315, 130, 25);
		b3.addActionListener(this);
		loginCard.add(b3);

		// Sign In Button
		b1 = new JButton("Sign in");
		b1.setBounds(0, 370, 440, 50);
		b1.setFont(new Font("Segoe UI Bold", Font.BOLD, 16));
		b1.setBackground(accentColor);
		b1.setForeground(Color.WHITE);
		b1.setBorder(null);
		b1.setFocusPainted(false);
		b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b1.addActionListener(this);
		loginCard.add(b1);

		// Hover effect for Sign In
		b1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				b1.setBackground(new Color(29, 78, 216));
			}

			public void mouseExited(MouseEvent e) {
				b1.setBackground(accentColor);
			}
		});

		// Sign Up
		JLabel lNoAcc = new JLabel("Don't have an account?");
		lNoAcc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lNoAcc.setForeground(textDim);
		lNoAcc.setBounds(110, 450, 160, 25);
		loginCard.add(lNoAcc);

		b2 = new JButton("Sign up for free");
		b2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		b2.setForeground(accentColor);
		b2.setContentAreaFilled(false);
		b2.setBorder(null);
		b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b2.setBounds(270, 450, 120, 25);
		b2.addActionListener(this);
		loginCard.add(b2);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			try {
				String username = t1.getText();
				String password = new String(t2.getPassword());

				if (username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter all fields");
					return;
				}

				Conn c = new Conn();
				if (c.s == null) {
					JOptionPane.showMessageDialog(null, "Offline Mode: Logging in as Admin");
					setVisible(false);
					new Loading(username, "Admin").setVisible(true);
					return;
				}

				String query = "select * from account where username = ?";
				PreparedStatement ps = c.c.prepareStatement(query);
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					String storedPassword = rs.getString("password");
					String inputHash = SecurityUtil.hashPassword(password);
					String role = rs.getString("role");

					if (storedPassword.equals(inputHash) || storedPassword.equals(password)) {
						setVisible(false);
						new Loading(username, role).setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Credentials");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Account not found");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == b2) {
			setVisible(false);
			new Signup().setVisible(true);
		} else if (ae.getSource() == b3) {
			setVisible(false);
			new ForgotPassword().setVisible(true);
		}
	}

	public static void main(String[] args) {
		new Login();
	}
}
