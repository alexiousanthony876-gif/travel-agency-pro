package travel_and_Tourism_Organisation_System;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.sql.*;

public class UpdateCustomer extends JFrame implements ActionListener {

	JTextField t1, t2, t3, t4, t5, t6, t7, t8, t9;
	JButton back, update, check;
	String username;

	// Premium Color Palette
	Color primaryDark = new Color(15, 23, 42); // Midnight Blue
	Color mainBg = new Color(245, 247, 250); // Off-white
	Color accentColor = new Color(37, 99, 235); // Royal Blue
	Color textDark = new Color(33, 43, 54); // Dark Slate
	Color textLight = new Color(100, 116, 139); // Slate 500

	public UpdateCustomer(String username) {
		this.username = username;
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Full Screen
		setTitle("Update Profile | Travel Wizard");
		setLayout(new BorderLayout());
		getContentPane().setBackground(mainBg);

		// --- Header ---
		JPanel header = new JPanel();
		header.setBackground(primaryDark);
		header.setPreferredSize(new Dimension(1920, 80));
		header.setLayout(null);
		add(header, BorderLayout.NORTH);

		JLabel title = new JLabel("UPDATE YOUR PROFILE");
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

		// Form Card (Left)
		JPanel formCard = new JPanel(null);
		formCard.setBackground(Color.WHITE);
		formCard.setBounds(80, 40, 600, 630);
		formCard.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
		mainContainer.add(formCard);

		JLabel formHeader = new JLabel("Personal Information");
		formHeader.setFont(new Font("Segoe UI", Font.BOLD, 28));
		formHeader.setForeground(primaryDark);
		formHeader.setBounds(40, 20, 400, 40);
		formCard.add(formHeader);

		int lblX = 40, fieldX = 260, y = 80, step = 45;

		// Form Fields
		addFormLabel(formCard, "Username", lblX, y);
		t1 = addFormField(formCard, fieldX, y, 200, false); // Username should not be editable
		t1.setText(username);
		t1.setBackground(new Color(248, 250, 252));

		check = new JButton("Fetch Data");
		check.setBounds(470, y, 100, 32);
		check.setBackground(new Color(100, 116, 139));
		check.setForeground(Color.WHITE);
		check.setFont(new Font("Segoe UI", Font.BOLD, 12));
		check.setFocusPainted(false);
		check.setBorder(null);
		check.setCursor(new Cursor(Cursor.HAND_CURSOR));
		check.addActionListener(this);
		formCard.add(check);

		y += step;
		addFormLabel(formCard, "Identification ID", lblX, y);
		t2 = addFormField(formCard, fieldX, y, 300, true);

		y += step;
		addFormLabel(formCard, "Identification No.", lblX, y);
		t3 = addFormField(formCard, fieldX, y, 300, true);

		y += step;
		addFormLabel(formCard, "Full Name", lblX, y);
		t4 = addFormField(formCard, fieldX, y, 300, true);

		y += step;
		addFormLabel(formCard, "Gender", lblX, y);
		t5 = addFormField(formCard, fieldX, y, 300, true);

		y += step;
		addFormLabel(formCard, "Country", lblX, y);
		t6 = addFormField(formCard, fieldX, y, 300, true);

		y += step;
		addFormLabel(formCard, "Permanent Address", lblX, y);
		t7 = addFormField(formCard, fieldX, y, 300, true);

		y += step;
		addFormLabel(formCard, "Email Address", lblX, y);
		t9 = addFormField(formCard, fieldX, y, 300, true);

		y += step + 15;
		update = new JButton("SAVE CHANGES");
		update.setBounds(40, y, 520, 50);
		update.setBackground(accentColor);
		update.setForeground(Color.WHITE);
		update.setFont(new Font("Segoe UI", Font.BOLD, 18));
		update.setFocusPainted(false);
		update.setBorder(null);
		update.setCursor(new Cursor(Cursor.HAND_CURSOR));
		update.addActionListener(this);

		// Button Hover Effect
		update.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				update.setBackground(accentColor.brighter());
			}

			public void mouseExited(MouseEvent e) {
				update.setBackground(accentColor);
			}
		});
		formCard.add(update);

		// Right Side Decoration
		JPanel rightDecor = new JPanel(null);
		rightDecor.setBackground(primaryDark);
		rightDecor.setBounds(720, 40, 550, 630);
		mainContainer.add(rightDecor);

		ImageIcon i1 = new ImageIcon(
				ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/update.png"));
		Image i2 = i1.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
		JLabel decorImg = new JLabel(new ImageIcon(i2));
		decorImg.setBounds(100, 60, 350, 350);
		rightDecor.add(decorImg);

		JLabel infoText = new JLabel("Keep your profile data accurate.");
		infoText.setFont(new Font("Segoe UI", Font.BOLD, 22));
		infoText.setForeground(Color.WHITE);
		infoText.setBounds(0, 450, 550, 40);
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		rightDecor.add(infoText);

		JLabel subInfo = new JLabel("Accurate data ensures smooth booking and identity verification.");
		subInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		subInfo.setForeground(new Color(200, 200, 200));
		subInfo.setBounds(0, 490, 550, 20);
		subInfo.setHorizontalAlignment(SwingConstants.CENTER);
		rightDecor.add(subInfo);

		fetchData();
		setVisible(true);
	}

	private void addFormLabel(JPanel p, String text, int x, int y) {
		JLabel l = new JLabel(text);
		l.setFont(new Font("Segoe UI", Font.BOLD, 14));
		l.setForeground(textLight);
		l.setBounds(x, y, 200, 25);
		p.add(l);
	}

	private JTextField addFormField(JPanel p, int x, int y, int w, boolean editable) {
		JTextField t = new JTextField();
		t.setBounds(x, y, w, 32);
		t.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		t.setEditable(editable);
		t.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(226, 232, 240)),
				new EmptyBorder(0, 10, 0, 10)));
		p.add(t);
		return t;
	}

	private void fetchData() {
		try {
			Conn c = new Conn();
			String query = "select * from customer where username = '"
					+ (t1.getText().isEmpty() ? username : t1.getText()) + "'";
			ResultSet rs = c.s.executeQuery(query);
			if (rs.next()) {
				t1.setText(rs.getString("username"));
				t2.setText(rs.getString("id"));
				t3.setText(rs.getString("number"));
				t4.setText(rs.getString("name"));
				t5.setText(rs.getString("gender"));
				t6.setText(rs.getString("country"));
				t7.setText(rs.getString("address"));
				t9.setText(rs.getString("email"));
			}
			rs.close();
		} catch (Exception e) {
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == update) {
			String s1 = t1.getText().trim();
			String s2 = t2.getText().trim();
			String s3 = t3.getText().trim();
			String s4 = t4.getText().trim();
			String s5 = t5.getText().trim();
			String s6 = t6.getText().trim();
			String s7 = t7.getText().trim();
			String s9 = t9.getText().trim();

			if (s1.isEmpty() || s4.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Username and Full Name are required.");
				return;
			}

			try {
				Conn c = new Conn();
				// Try Update First
				String qUpdate = "update customer set id = '" + s2 + "', number = '" + s3 + "', name = '" + s4
						+ "', gender = '" + s5 + "', country = '" + s6 + "', address = '" + s7
						+ "', email = '" + s9 + "' where username = '" + s1 + "'";

				int rowsAffected = c.s.executeUpdate(qUpdate);

				if (rowsAffected == 0) {
					// Fallback to Insert if no record exists
					String qInsert = "insert into customer values('" + s1 + "', '" + s2 + "', '" + s3 + "', '" + s4
							+ "', '" + s5 + "', '" + s6 + "', '" + s7 + "', '" + s9 + "')";
					c.s.executeUpdate(qInsert);
					JOptionPane.showMessageDialog(null, "Profile Created Successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Profile Updated Successfully");
				}
				setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Database Error: "
						+ (e1.getMessage().contains("UNIQUE") ? "Record already exists." : e1.getMessage()));
			}
		} else if (ae.getSource() == check) {
			fetchData();
		} else {
			setVisible(false);
		}
	}

	public static void main(String[] args) {
		new UpdateCustomer("Guest");
	}
}
