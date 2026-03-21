package travel_and_Tourism_Organisation_System;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CheckPackage extends JFrame {

  static String username;

  // Theme Colors
  Color primaryDark = new Color(15, 23, 42); // Midnight Blue
  Color accentColor = new Color(0, 122, 255); // iOS Blue
  Color goldColor = new Color(245, 158, 11); // Amber
  Color mainBg = new Color(245, 247, 250); // Off-white

  public static void main(String[] args) {
    new CheckPackage("Guest").setVisible(true);
  }

  public CheckPackage(String username) {
    this.username = username;
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setTitle("Explore Our Premium Packages");
    setLayout(new BorderLayout());

    // --- Header ---
    JPanel header = new JPanel();
    header.setBackground(primaryDark);
    header.setPreferredSize(new Dimension(1920, 80));
    header.setLayout(null);
    add(header, BorderLayout.NORTH);

    JLabel title = new JLabel("AVAILABLE DESTINATIONS & PACKAGES");
    title.setFont(new Font("Segoe UI", Font.BOLD, 24));
    title.setForeground(Color.WHITE);
    title.setBounds(50, 20, 600, 40);
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

    // --- Tabbed Pane ---
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
    tabbedPane.setBackground(new Color(30, 41, 59));
    tabbedPane.setForeground(Color.WHITE);

    // Custom Tab Styling (Standard Swing is limited, but we can improve appearance)
    UIManager.put("TabbedPane.selected", primaryDark);
    UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));

    for (int i = 0; i < PackageData.PACKAGES.length; i++) {
      JPanel p = createPackagePanel(PackageData.PACKAGES[i]);
      tabbedPane.addTab("  " + PackageData.PACKAGES[i][1] + "  ", p);
    }

    add(tabbedPane, BorderLayout.CENTER);
    setVisible(true);
  }

  private JPanel createPackagePanel(String[] pack) {
    JPanel container = new JPanel(null);
    container.setBackground(mainBg);

    // Left Content Section (Details)
    JPanel leftPanel = new JPanel(null);
    leftPanel.setBounds(50, 50, 600, 650);
    leftPanel.setBackground(Color.WHITE);
    leftPanel.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
    container.add(leftPanel);

    // Package Label (Amber Tag)
    JPanel tag = new JPanel();
    tag.setBackground(goldColor);
    tag.setBounds(0, 30, 200, 40);
    leftPanel.add(tag);

    JLabel tagLabel = new JLabel(pack[10]); // Tagline
    tagLabel.setForeground(primaryDark);
    tagLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    tag.add(tagLabel);

    JLabel nameLbl = new JLabel(pack[1]);
    nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 42));
    nameLbl.setForeground(primaryDark);
    nameLbl.setBounds(40, 90, 520, 60);
    leftPanel.add(nameLbl);

    JLabel durLbl = new JLabel("⏱ " + pack[2]);
    durLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
    durLbl.setForeground(accentColor);
    durLbl.setBounds(40, 150, 500, 30);
    leftPanel.add(durLbl);

    // Features Grid
    int y = 210;
    String[] features = { pack[3], pack[4], pack[5], pack[6], pack[7], pack[8] };
    for (String feat : features) {
      if (feat.equals("None"))
        continue;
      JLabel f = new JLabel("✓  " + feat);
      f.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      f.setForeground(new Color(51, 65, 85));
      f.setBounds(45, y, 500, 30);
      leftPanel.add(f);
      y += 40;
    }

    // Price Section
    JLabel priceTitle = new JLabel("Starting from");
    priceTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    priceTitle.setForeground(new Color(100, 116, 139));
    priceTitle.setBounds(45, 520, 200, 20);
    leftPanel.add(priceTitle);

    JLabel priceVal = new JLabel(pack[11]);
    priceVal.setFont(new Font("Segoe UI", Font.BOLD, 36));
    priceVal.setForeground(new Color(21, 128, 61)); // Green
    priceVal.setBounds(45, 545, 400, 50);
    leftPanel.add(priceVal);

    JButton bookBtn = new JButton("BOOK THIS PACKAGE");
    bookBtn.setBounds(45, 610, 300, 50);
    bookBtn.setBackground(accentColor);
    bookBtn.setForeground(Color.WHITE);
    bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
    bookBtn.setFocusPainted(false);
    bookBtn.setBorder(null);
    bookBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    bookBtn.addActionListener(e -> {
      new BookPackage(username);
      setVisible(false);
    });
    leftPanel.add(bookBtn);

    // Right Section (Hero Image & Stats)
    JLabel imgLbl = new JLabel();
    try {
      ImageIcon i1 = new ImageIcon(
          ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/" + pack[0]));
      Image i2 = i1.getImage().getScaledInstance(650, 450, Image.SCALE_SMOOTH);
      imgLbl.setIcon(new ImageIcon(i2));
    } catch (Exception e) {
    }
    imgLbl.setBounds(700, 50, 650, 450);
    imgLbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    container.add(imgLbl);

    // Info Card
    JPanel infoCard = new JPanel(null);
    infoCard.setBackground(primaryDark);
    infoCard.setBounds(700, 520, 650, 150);
    container.add(infoCard);

    JLabel ratingTitle = new JLabel("Customer Satisfaction");
    ratingTitle.setForeground(new Color(200, 200, 200));
    ratingTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    ratingTitle.setBounds(30, 30, 200, 30);
    infoCard.add(ratingTitle);

    // Fetch Rating
    String ratingStr = "No Ratings yet";
    try {
      Conn c = new Conn();
      String q = "SELECT AVG(CAST(rating AS FLOAT)) as avg_rating FROM reviews WHERE target = '" + pack[1] + "'";
      ResultSet rs = c.s.executeQuery(q);
      if (rs.next()) {
        double avg = rs.getDouble("avg_rating");
        if (avg > 0)
          ratingStr = String.format("★ %.1f / 5.0 Rating", avg);
      }
      rs.close();
    } catch (Exception e) {
    }

    JLabel ratingVal = new JLabel(ratingStr);
    ratingVal.setForeground(goldColor);
    ratingVal.setFont(new Font("Segoe UI", Font.BOLD, 28));
    ratingVal.setBounds(30, 65, 400, 40);
    infoCard.add(ratingVal);

    return container;
  }
}