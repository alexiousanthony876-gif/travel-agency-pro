package travel_and_Tourism_Organisation_System;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Image;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewPackage extends JFrame {
	private JPanel contentPane;
	Choice c1;
	static String username;

	public static void main(String[] args) {

		ViewPackage frame = new ViewPackage(username);
		frame.setVisible(true);
	}

	public ViewPackage(String username) {
		this.username = username;
		setBounds(340, 150, 850, 450);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon i1 = new ImageIcon(
				ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/bookedDetails.jpg"));
		Image i3 = i1.getImage().getScaledInstance(500, 350, Image.SCALE_DEFAULT);
		ImageIcon i2 = new ImageIcon(i3);
		JLabel la1 = new JLabel(i2);
		la1.setBounds(450, 40, 350, 350);
		add(la1);

		JLabel lblName = new JLabel("VIEW PACKAGE DETAILS");
		lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		lblName.setBounds(88, 11, 350, 53);
		contentPane.add(lblName);

		JLabel lb3 = new JLabel("Username :");
		lb3.setBounds(35, 70, 200, 14);
		contentPane.add(lb3);

		JLabel l1 = new JLabel();
		l1.setBounds(271, 70, 200, 14);
		contentPane.add(l1);

		JLabel lblId = new JLabel("Package :");
		lblId.setBounds(35, 110, 200, 14);
		contentPane.add(lblId);

		JLabel l2 = new JLabel();
		l2.setBounds(271, 110, 200, 14);
		contentPane.add(l2);

		JLabel lb2 = new JLabel("Number of Persons :");
		lb2.setBounds(35, 150, 200, 14);
		contentPane.add(lb2);

		JLabel l3 = new JLabel();
		l3.setBounds(271, 150, 200, 14);
		contentPane.add(l3);

		JLabel lblName_1 = new JLabel("ID :");
		lblName_1.setBounds(35, 190, 200, 14);
		contentPane.add(lblName_1);

		JLabel l4 = new JLabel();
		l4.setBounds(271, 190, 200, 14);
		contentPane.add(l4);

		JLabel lblGender = new JLabel("Number :");
		lblGender.setBounds(35, 230, 200, 14);
		contentPane.add(lblGender);

		JLabel l5 = new JLabel();
		l5.setBounds(271, 230, 200, 14);
		contentPane.add(l5);

		JLabel lblReserveRoomNumber = new JLabel("Price :");
		lblReserveRoomNumber.setBounds(35, 310, 200, 14);
		contentPane.add(lblReserveRoomNumber);

		JLabel l7 = new JLabel();
		l7.setBounds(271, 310, 200, 14);
		contentPane.add(l7);

		Conn c = new Conn();
		try {

			ResultSet rs = c.s.executeQuery("select * from bookPackage where username = '" + username + "'");
			while (rs.next()) {
				l1.setText(rs.getString(1));
				l2.setText(rs.getString(2));
				l3.setText(rs.getString(3));
				l4.setText(rs.getString(4));
				l5.setText(rs.getString(5));
				l7.setText(rs.getString(6));

			}

			rs.close();
		} catch (SQLException e) {
		}

		JButton btnExit = new JButton("Back");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnExit.setBounds(60, 350, 100, 30);
		btnExit.setBackground(Color.BLACK);
		btnExit.setForeground(Color.WHITE);
		contentPane.add(btnExit);

		JButton btnWhatsApp = new JButton("WhatsApp");
		btnWhatsApp.setBounds(175, 350, 110, 30);
		btnWhatsApp.setBackground(new Color(37, 211, 102));
		btnWhatsApp.setForeground(Color.WHITE);
		btnWhatsApp.addActionListener(e -> {
			String details = "Check this package from Travel Wizard:\n" +
					"Package: " + l2.getText() + "\n" +
					"Price: " + l7.getText();
			try {
				String url = "https://wa.me/?text=" + java.net.URLEncoder.encode(details, "UTF-8");
				Desktop.getDesktop().browse(new java.net.URI(url));
			} catch (Exception ex) {
			}
		});
		contentPane.add(btnWhatsApp);

		JButton btnEmail = new JButton("Email");
		btnEmail.setBounds(300, 350, 100, 30);
		btnEmail.setBackground(new Color(234, 67, 53));
		btnEmail.setForeground(Color.WHITE);
		btnEmail.addActionListener(e -> {
			String subject = "Travel Package Details";
			String body = "Explore this package at Travel Wizard:\n\n" +
					"Package: " + l2.getText() + "\n" +
					"Persons: " + l3.getText() + "\n" +
					"Full Price: " + l7.getText();
			try {
				String mailto = "mailto:?subject=" + java.net.URLEncoder.encode(subject, "UTF-8") +
						"&body=" + java.net.URLEncoder.encode(body, "UTF-8");
				Desktop.getDesktop().browse(new java.net.URI(mailto.replace("+", "%20")));
			} catch (Exception ex) {
			}
		});
		contentPane.add(btnEmail);

		getContentPane().setBackground(Color.WHITE);
	}
}