package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Dashboard extends JFrame implements ActionListener {

	static String username;
	JButton addPersonalDetails, updatePersonalDetails, viewPersonalDetails, deletePersonalDetails, checkPackages,
			bookPackage, viewpackage, viewhotels, bookhotel, viewBookedHotel, destinations, payments,
			notepad, about, viewItinerary, viewRatings, notifications, bookTransportation, adminPanel;

	public Dashboard(String username) {
		this.username = username;
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(null);

		JPanel p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(0, 0, 102));
		p1.setBounds(0, 0, 1600, 65);
		add(p1);

		ImageIcon i1 = new ImageIcon(
				ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/home.jpg"));
		Image i2 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l3 = new JLabel(i3);
		l3.setBounds(5, 0, 70, 70);
		p1.add(l3);

		JLabel l2 = new JLabel("Dashboard");
		l2.setFont(new Font("Tahoma", Font.BOLD, 30));
		l2.setForeground(Color.WHITE);
		l2.setBounds(80, 10, 300, 40);
		p1.add(l2);

		JPanel p2 = new JPanel();
		p2.setLayout(null);
		p2.setBackground(new Color(0, 0, 102));
		p2.setPreferredSize(new Dimension(300, 1100)); // Increased height for scrolling

		JScrollPane scrollPane = new JScrollPane(p2);
		scrollPane.setBounds(0, 65, 300, 900);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground(new Color(0, 0, 102));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);

		addPersonalDetails = new JButton("Add Personal Details");
		addPersonalDetails.setBackground(new Color(0, 0, 102));
		addPersonalDetails.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addPersonalDetails.setForeground(Color.WHITE);
		addPersonalDetails.setBounds(0, 0, 300, 50);
		addPersonalDetails.addActionListener(this);
		p2.add(addPersonalDetails);

		updatePersonalDetails = new JButton("Update Personal Details");
		updatePersonalDetails.setBackground(new Color(0, 0, 102));
		updatePersonalDetails.setFont(new Font("Tahoma", Font.PLAIN, 20));
		updatePersonalDetails.setForeground(Color.WHITE);
		updatePersonalDetails.setBounds(0, 50, 300, 50);
		updatePersonalDetails.addActionListener(this);
		p2.add(updatePersonalDetails);

		viewPersonalDetails = new JButton("View Details");
		viewPersonalDetails.setBackground(new Color(0, 0, 102));
		viewPersonalDetails.setFont(new Font("Tahoma", Font.PLAIN, 20));
		viewPersonalDetails.setForeground(Color.WHITE);
		viewPersonalDetails.setBounds(0, 100, 300, 50);
		viewPersonalDetails.addActionListener(this);
		p2.add(viewPersonalDetails);

		deletePersonalDetails = new JButton("Delete Personal Details");
		deletePersonalDetails.setBackground(new Color(0, 0, 102));
		deletePersonalDetails.setFont(new Font("Tahoma", Font.PLAIN, 20));
		deletePersonalDetails.setForeground(Color.WHITE);
		deletePersonalDetails.setBounds(0, 150, 300, 50);
		deletePersonalDetails.addActionListener(this);
		p2.add(deletePersonalDetails);

		checkPackages = new JButton("Check Package");
		checkPackages.setBackground(new Color(0, 0, 102));
		checkPackages.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkPackages.setForeground(Color.WHITE);
		checkPackages.setBounds(0, 200, 300, 50);
		checkPackages.addActionListener(this);
		p2.add(checkPackages);

		bookPackage = new JButton("Book Package");
		bookPackage.setBackground(new Color(0, 0, 102));
		bookPackage.setFont(new Font("Tahoma", Font.PLAIN, 20));
		bookPackage.setForeground(Color.WHITE);
		bookPackage.setBounds(0, 250, 300, 50);
		bookPackage.addActionListener(this);
		p2.add(bookPackage);

		viewpackage = new JButton("View Package");
		viewpackage.setBackground(new Color(0, 0, 102));
		viewpackage.setFont(new Font("Tahoma", Font.PLAIN, 20));
		viewpackage.setForeground(Color.WHITE);
		viewpackage.setBounds(0, 300, 300, 50);
		viewpackage.addActionListener(this);
		p2.add(viewpackage);

		viewhotels = new JButton("View Hotels");
		viewhotels.setBackground(new Color(0, 0, 102));
		viewhotels.setFont(new Font("Tahoma", Font.PLAIN, 20));
		viewhotels.setForeground(Color.WHITE);
		viewhotels.setBounds(0, 350, 300, 50);
		viewhotels.addActionListener(this);
		p2.add(viewhotels);

		bookhotel = new JButton("Book Hotel");
		bookhotel.setBackground(new Color(0, 0, 102));
		bookhotel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		bookhotel.setForeground(Color.WHITE);
		bookhotel.setBounds(0, 400, 300, 50);
		bookhotel.addActionListener(this);
		p2.add(bookhotel);

		viewBookedHotel = new JButton("View Booked Hotel");
		viewBookedHotel.setBackground(new Color(0, 0, 102));
		viewBookedHotel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		viewBookedHotel.setForeground(Color.WHITE);
		viewBookedHotel.setBounds(0, 450, 300, 50);
		viewBookedHotel.addActionListener(this);
		p2.add(viewBookedHotel);

		destinations = new JButton("Destinations");
		destinations.setBackground(new Color(0, 0, 102));
		destinations.setFont(new Font("Tahoma", Font.PLAIN, 20));
		destinations.setForeground(Color.WHITE);
		destinations.setBounds(0, 500, 300, 50);
		destinations.addActionListener(this);
		p2.add(destinations);

		payments = new JButton("Payments");
		payments.setBackground(new Color(0, 0, 102));
		payments.setFont(new Font("Tahoma", Font.PLAIN, 20));
		payments.setForeground(Color.WHITE);
		payments.setBounds(0, 550, 300, 50);
		payments.addActionListener(this);
		p2.add(payments);

		notepad = new JButton("Notepad");
		notepad.setBackground(new Color(0, 0, 102));
		notepad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		notepad.setForeground(Color.WHITE);
		notepad.setBounds(0, 600, 300, 50);
		notepad.addActionListener(this);
		p2.add(notepad);

		viewItinerary = new JButton("Itinerary");
		viewItinerary.setBackground(new Color(0, 0, 102));
		viewItinerary.setFont(new Font("Tahoma", Font.PLAIN, 20));
		viewItinerary.setForeground(Color.WHITE);
		viewItinerary.setBounds(0, 650, 300, 50);
		viewItinerary.addActionListener(this);
		p2.add(viewItinerary);

		viewRatings = new JButton("Rate Experience");
		viewRatings.setBackground(new Color(0, 0, 102));
		viewRatings.setFont(new Font("Tahoma", Font.PLAIN, 20));
		viewRatings.setForeground(Color.WHITE);
		viewRatings.setBounds(0, 700, 300, 50);
		viewRatings.addActionListener(this);
		p2.add(viewRatings);

		bookTransportation = new JButton("Transport");
		bookTransportation.setBackground(new Color(0, 0, 102));
		bookTransportation.setFont(new Font("Tahoma", Font.PLAIN, 20));
		bookTransportation.setForeground(Color.WHITE);
		bookTransportation.setBounds(0, 750, 300, 50);
		bookTransportation.addActionListener(this);
		p2.add(bookTransportation);

		about = new JButton("About");
		about.setBackground(new Color(0, 0, 102));
		about.setFont(new Font("Tahoma", Font.PLAIN, 20));
		about.setForeground(Color.WHITE);
		about.setBounds(0, 800, 300, 50);
		about.addActionListener(this);
		p2.add(about);

		if (username.equals("admin")) {
			adminPanel = new JButton("Admin Panel");
			adminPanel.setBackground(new Color(153, 0, 0));
			adminPanel.setFont(new Font("Tahoma", Font.BOLD, 20));
			adminPanel.setForeground(Color.WHITE);
			adminPanel.setBounds(0, 850, 300, 50);
			adminPanel.addActionListener(this);
			p2.add(adminPanel);
		}

		ImageIcon i4 = new ImageIcon(
				ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/home.jpg"));
		Image i5 = i4.getImage().getScaledInstance(1550, 1000, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l1 = new JLabel(i6);
		l1.setBounds(0, 0, 1550, 1000);
		add(l1);

		JLabel l4 = new JLabel("Travel and Tourism Organisation System");
		l4.setFont(new Font("Tahoma", Font.PLAIN, 50));
		l4.setForeground(Color.WHITE);
		l4.setBounds(400, 80, 1000, 70);
		l1.add(l4);

		getContentPane().setBackground(Color.WHITE);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == addPersonalDetails) {
			new AddCustomers(username);
		} else if (ae.getSource() == viewPersonalDetails) {
			new ViewCustomer(username);
		} else if (ae.getSource() == updatePersonalDetails) {
			new UpdateCustomer(username);
		} else if (ae.getSource() == checkPackages) {
			new CheckPackage(username);
		} else if (ae.getSource() == bookPackage) {
			new BookPackage(username);
		} else if (ae.getSource() == viewpackage) {
			new BookingHistory(username).setVisible(true);
		} else if (ae.getSource() == viewhotels) {
			new CheckHotels().setVisible(true);
		} else if (ae.getSource() == destinations) {
			new Destination().setVisible(true);
		} else if (ae.getSource() == bookhotel) {
			new BookHotel(username).setVisible(true);
		} else if (ae.getSource() == viewBookedHotel) {
			new BookingHistory(username).setVisible(true);
		} else if (ae.getSource() == payments) {
			new PaymentGateway(username);
		} else if (ae.getSource() == about) {
			new About();

		} else if (ae.getSource() == viewItinerary) {
			new ItineraryView(username);
		} else if (ae.getSource() == viewRatings) {
			new RateExperience(username);
		} else if (ae.getSource() == bookTransportation) {
			new BookTransportation(username).setVisible(true);

		} else if (ae.getSource() == notepad) {
			try {
				Runtime.getRuntime().exec("notepad.exe");
			} catch (Exception e) {
			}
		} else if (ae.getSource() == deletePersonalDetails) {
			try {
				new DeleteCustomer().setVisible(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == adminPanel) {
			setVisible(false);
			new AdminDashboard(username).setVisible(true);
		}
	}

	public static void main(String[] args) {
		new Dashboard(username);
	}
}
