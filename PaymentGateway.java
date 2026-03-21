package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PaymentGateway extends JFrame implements ActionListener {
    String username;
    JButton payBtn, backBtn, refundBtn;
    JPanel methodPanel, contentArea;
    JComboBox<String> pendingBookings;
    JLabel amountLabel, itemLabel, typeLabel;

    // Modern Colors
    Color primaryColor = new Color(37, 99, 235); // Modern Blue
    Color successColor = new Color(22, 163, 74); // Modern Green
    Color dangerColor = new Color(220, 38, 38); // Modern Red
    Color bgColor = new Color(248, 250, 252); // Slate 50
    Color cardColor = Color.WHITE;
    Color textColor = new Color(15, 23, 42); // Slate 900
    Color textMuted = new Color(100, 116, 139); // Slate 500

    // Form fields
    JTextField cardNumber, cvv, expiry, upiId;
    JComboBox<String> bankList;
    JPasswordField bankPass;
    String selectedMethod = "Card";

    public PaymentGateway(String username) {
        this.username = username;
        setTitle("Secure Checkout | Travel Wizard");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Open as a whole page
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgColor);

        // --- Header with Pattern ---
        JPanel header = new GradientPanel(primaryColor, new Color(17, 24, 39));
        header.setPreferredSize(new Dimension(1600, 100));
        header.setLayout(new BorderLayout());

        JPanel titleGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 30));
        titleGroup.setOpaque(false);

        JLabel brand = new JLabel("TRAVEL WIZARD ");
        brand.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        brand.setForeground(new Color(255, 255, 255, 180));
        titleGroup.add(brand);

        JLabel sep0 = new JLabel("|");
        sep0.setForeground(new Color(255, 255, 255, 80));
        titleGroup.add(sep0);

        JLabel brand2 = new JLabel("SECURE CHECKOUT");
        brand2.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brand2.setForeground(Color.WHITE);
        titleGroup.add(brand2);

        header.add(titleGroup, BorderLayout.WEST);

        // Stepper Visual
        JPanel stepper = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 35));
        stepper.setOpaque(false);
        stepper.add(createStepLabel("Cart", false));
        stepper.add(createStepLine());
        stepper.add(createStepLabel("Payment", true));
        stepper.add(createStepLine());
        stepper.add(createStepLabel("Confirm", false));
        header.add(stepper, BorderLayout.CENTER);

        JLabel userLabel = new JLabel("Hi, " + username + "  ");
        userLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        header.add(userLabel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // --- Main Body Container ---
        JPanel mainBody = new JPanel(new GridBagLayout());
        mainBody.setBackground(bgColor);
        mainBody.setBorder(new EmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // --- Left Section: Order Summary ---
        gbc.gridx = 0;
        gbc.weightx = 0.40;
        gbc.insets = new Insets(0, 0, 0, 30);

        RoundedPanel summaryCard = new RoundedPanel(cardColor, 25);
        summaryCard.setLayout(null);

        JLabel sTitle = new JLabel("Order Summary");
        sTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        sTitle.setBounds(40, 30, 250, 35);
        summaryCard.add(sTitle);

        JLabel lSelect = new JLabel("Select active booking");
        lSelect.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lSelect.setForeground(textMuted);
        lSelect.setBounds(40, 85, 250, 20);
        summaryCard.add(lSelect);

        pendingBookings = new JComboBox<>();
        pendingBookings.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        pendingBookings.setBounds(40, 110, 380, 45);
        pendingBookings.addActionListener(e -> updateDetails());
        summaryCard.add(pendingBookings);

        // Details Grid
        int detaY = 190;
        summaryCard.add(createSummaryRow("Category", typeLabel = new JLabel("-"), detaY));
        summaryCard.add(createSummaryRow("Selection", itemLabel = new JLabel("-"), detaY + 40));
        summaryCard.add(createSummaryRow("Tax (GST 18%)", new JLabel("Included"), detaY + 80));
        summaryCard.add(createSummaryRow("Discount", new JLabel("- ₹ 0.00"), detaY + 120));

        JSeparator sep = new JSeparator();
        sep.setBounds(40, 340, 380, 1);
        summaryCard.add(sep);

        JLabel lTotal = new JLabel("Total Payable");
        lTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lTotal.setBounds(40, 360, 200, 20);
        summaryCard.add(lTotal);

        amountLabel = new JLabel("₹ 0.00");
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        amountLabel.setForeground(textColor);
        amountLabel.setBounds(40, 390, 380, 50);
        summaryCard.add(amountLabel);

        // Security Badges
        JPanel securityBadges = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        securityBadges.setOpaque(false);
        securityBadges.setBounds(40, 470, 400, 40);
        securityBadges.add(createBadge("🛡 PCI DSS"));
        securityBadges.add(createBadge("🔒 256-BIT SSL"));
        summaryCard.add(securityBadges);

        refundBtn = createModernButton("Raise Dispute / Refund", new Color(241, 245, 249), textMuted, 40, 540, 380, 45);
        refundBtn.addActionListener(this);
        summaryCard.add(refundBtn);

        mainBody.add(summaryCard, gbc);

        // --- Right Section: Payment Methods ---
        gbc.gridx = 1;
        gbc.weightx = 0.55;
        gbc.insets = new Insets(0, 0, 0, 0);

        RoundedPanel paymentCard = new RoundedPanel(cardColor, 20);
        paymentCard.setLayout(new BorderLayout());

        // Tabs Header
        JPanel tabsHeader = new JPanel(new GridLayout(1, 3, 10, 0));
        tabsHeader.setBackground(cardColor);
        tabsHeader.setBorder(new EmptyBorder(30, 30, 10, 30));

        JButton cardTab = createTabButton("Credit/Debit Card", true);
        JButton upiTab = createTabButton("UPI / QR", false);
        JButton nbTab = createTabButton("Net Banking", false);

        tabsHeader.add(cardTab);
        tabsHeader.add(upiTab);
        tabsHeader.add(nbTab);
        paymentCard.add(tabsHeader, BorderLayout.NORTH);

        // Content Area
        contentArea = new JPanel(new CardLayout());
        contentArea.setBackground(cardColor);
        contentArea.setBorder(new EmptyBorder(20, 30, 30, 30));

        contentArea.add(createCardPanel(), "Card");
        contentArea.add(createUPIPanel(), "UPI");
        contentArea.add(createNBPanel(), "NetBanking");

        paymentCard.add(contentArea, BorderLayout.CENTER);

        // Footer Actions
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 30));
        footer.setBackground(cardColor);
        footer.setBorder(new EmptyBorder(0, 0, 20, 30));

        backBtn = createModernButton("Cancel", Color.WHITE, dangerColor, 0, 0, 120, 45);
        backBtn.setBorder(BorderFactory.createLineBorder(dangerColor));
        backBtn.addActionListener(this);

        payBtn = createModernButton("Complete Payment", successColor, Color.WHITE, 0, 0, 220, 45);
        payBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        payBtn.addActionListener(this);

        footer.add(backBtn);
        footer.add(payBtn);
        paymentCard.add(footer, BorderLayout.SOUTH);

        mainBody.add(paymentCard, gbc);
        add(mainBody, BorderLayout.CENTER);

        // Tab Actions
        cardTab.addActionListener(e -> {
            switchTab(cardTab, upiTab, nbTab, "Card");
        });
        upiTab.addActionListener(e -> {
            switchTab(upiTab, cardTab, nbTab, "UPI");
        });
        nbTab.addActionListener(e -> {
            switchTab(nbTab, cardTab, upiTab, "NetBanking");
        });

        loadPendingBookings();
        setVisible(true);
    }

    private void switchTab(JButton active, JButton i1, JButton i2, String name) {
        selectedMethod = name;
        active.setBackground(primaryColor);
        active.setForeground(Color.WHITE);
        i1.setBackground(new Color(241, 245, 249));
        i1.setForeground(textColor);
        i2.setBackground(new Color(241, 245, 249));
        i2.setForeground(textColor);
        CardLayout cl = (CardLayout) contentArea.getLayout();
        cl.show(contentArea, name);
    }

    private JButton createTabButton(String text, boolean active) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (active) {
            btn.setBackground(primaryColor);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(241, 245, 249));
            btn.setForeground(textColor);
        }
        return btn;
    }

    private JPanel createCardPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(cardColor);

        JLabel l1 = new JLabel("Card Number");
        l1.setBounds(0, 20, 200, 20);
        p.add(l1);
        cardNumber = new JTextField();
        cardNumber.setBounds(0, 45, 500, 45);
        cardNumber.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(0, 15, 0, 15)));
        p.add(cardNumber);

        JLabel l2 = new JLabel("Expiry Date (MM/YY)");
        l2.setBounds(0, 110, 150, 20);
        p.add(l2);
        expiry = new JTextField();
        expiry.setBounds(0, 135, 230, 45);
        expiry.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(0, 15, 0, 15)));
        p.add(expiry);

        JLabel l3 = new JLabel("CVV");
        l3.setBounds(270, 110, 100, 20);
        p.add(l3);
        cvv = new JPasswordField();
        cvv.setBounds(270, 135, 230, 45);
        cvv.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(0, 15, 0, 15)));
        p.add(cvv);

        return p;
    }

    private JPanel createUPIPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(cardColor);

        JLabel l1 = new JLabel("Scan QR Code to Pay Fast");
        l1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        l1.setBounds(0, 0, 300, 25);
        p.add(l1);

        try {
            ImageIcon i1 = new ImageIcon(
                    ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/upi_uploaded.jpg"));
            if (i1.getIconWidth() == -1) {
                i1 = new ImageIcon(
                        ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/paytm.jpeg"));
            }
            Image i2 = i1.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            JLabel qr = new JLabel(new ImageIcon(i2));
            qr.setBounds(0, 40, 180, 180);
            qr.setBorder(BorderFactory.createLineBorder(new Color(241, 245, 249), 5));
            p.add(qr);
        } catch (Exception e) {
        }

        JLabel l2 = new JLabel("Or Enter UPI ID");
        l2.setBounds(220, 60, 200, 20);
        p.add(l2);
        upiId = new JTextField("username@upi");
        upiId.setBounds(220, 85, 280, 45);
        upiId.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(0, 15, 0, 15)));
        p.add(upiId);

        return p;
    }

    private JPanel createNBPanel() {
        JPanel p = new JPanel(null);
        p.setBackground(cardColor);

        JLabel l1 = new JLabel("Select Your Bank");
        l1.setBounds(0, 20, 200, 20);
        p.add(l1);

        String[] banks = { "State Bank of India", "HDFC Bank", "ICICI Bank", "Axis Bank", "Kotak Mahindra" };
        bankList = new JComboBox<>(banks);
        bankList.setBounds(0, 45, 500, 45);
        p.add(bankList);

        JLabel l2 = new JLabel("NetBanking Password");
        l2.setBounds(0, 110, 200, 20);
        p.add(l2);
        bankPass = new JPasswordField();
        bankPass.setBounds(0, 135, 500, 45);
        bankPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(0, 15, 0, 15)));
        p.add(bankPass);

        return p;
    }

    private JButton createModernButton(String text, Color bg, Color fg, int x, int y, int w, int h) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (x != 0 || y != 0)
            btn.setBounds(x, y, w, h);
        else
            btn.setPreferredSize(new Dimension(w, h));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.darker());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    private void loadPendingBookings() {
        Conn c = new Conn();
        try {
            // Check Hotels
            ResultSet rs = c.s.executeQuery("select * from bookHotel where username = '" + username
                    + "' AND (status IS NULL OR status != 'Paid')");
            while (rs.next()) {
                pendingBookings.addItem("Hotel: " + rs.getString("hotel") + " | ₹ " + rs.getString("price"));
            }
            rs.close();

            // Check Packages
            rs = c.s.executeQuery("select * from bookPackage where username = '" + username
                    + "' AND (status IS NULL OR status != 'Paid')");
            while (rs.next()) {
                pendingBookings.addItem("Package: " + rs.getString("package") + " | ₹ " + rs.getString("price"));
            }
            rs.close();

            if (pendingBookings.getItemCount() > 0) {
                updateDetails();
            } else {
                itemLabel.setText("No pending payments!");
                payBtn.setEnabled(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDetails() {
        if (pendingBookings.getSelectedItem() != null) {
            String selected = (String) pendingBookings.getSelectedItem();
            String type = selected.startsWith("Hotel") ? "Hotel Booking" : "Travel Package";
            String item = selected.split(":")[1].split("\\|")[0].trim();
            String price = selected.split("\\| ₹ ")[1];
            // Clean price if it contains "Rs"
            String cleanedPrice = price.replace("Rs ", "").replace("Rs", "").trim();

            typeLabel.setText("Category: " + type);
            itemLabel.setText(item);
            amountLabel.setText("₹ " + cleanedPrice);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == payBtn) {
            validateAndProcess();
        } else if (ae.getSource() == refundBtn) {
            String tid = JOptionPane.showInputDialog(this, "Enter Transaction ID for Refund:", "Refund Help",
                    JOptionPane.QUESTION_MESSAGE);
            if (tid != null && !tid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Request Registered.\nStatus: Processing (3-5 Days)", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (ae.getSource() == backBtn) {
            setVisible(false);
        }
    }

    private void validateAndProcess() {
        if (selectedMethod.equals("Card")) {
            if (cardNumber.getText().length() < 16) {
                showError("Invalid Card Number!");
                return;
            }
        } else if (selectedMethod.equals("NetBanking")) {
            if (new String(bankPass.getPassword()).isEmpty()) {
                showError("Please enter your bank password!");
                return;
            }
        }

        processPayment(selectedMethod);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    private void processPayment(String method) {
        String selected = (String) pendingBookings.getSelectedItem();
        String type = selected.startsWith("Hotel") ? "Hotel" : "Package";
        String itemName = selected.split(":")[1].split("\\|")[0].trim();
        String amount = amountLabel.getText().replace("₹ ", "");
        String transactionId = "TRX" + System.currentTimeMillis();
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try {
            Conn c = new Conn();
            String q1 = "insert into payment values('" + username + "', '" + transactionId + "', '" + amount + "', '"
                    + method + "', 'Success', '" + date + "')";
            c.s.executeUpdate(q1);

            if (type.equals("Hotel")) {
                c.s.executeUpdate("update bookHotel set status='Paid' where username='" + username + "' AND hotel='"
                        + itemName + "'");
            } else {
                c.s.executeUpdate("update bookPackage set status='Paid' where username='" + username + "' AND package='"
                        + itemName + "'");
            }

            showSuccess(transactionId, itemName, amount, method, date);
            setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
            showError("Payment Failed: " + e.getMessage());
        }
    }

    private void showSuccess(String tid, String item, String amt, String method, String date) {
        JDialog d = new JDialog(this, "Payment Successful", true);
        d.setLayout(new BorderLayout());
        d.setSize(400, 500);
        d.setLocationRelativeTo(this);

        JPanel p = new JPanel(null);
        p.setBackground(Color.WHITE);

        JLabel icon = new JLabel("✔");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 80));
        icon.setForeground(successColor);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setBounds(0, 40, 400, 100);
        p.add(icon);

        JLabel t = new JLabel("Payment Received!");
        t.setFont(new Font("Segoe UI", Font.BOLD, 24));
        t.setHorizontalAlignment(SwingConstants.CENTER);
        t.setBounds(0, 150, 400, 30);
        p.add(t);

        String html = "<html><body style='width: 250px; text-align: center; color: #64748b; font-family: Segoe UI;'>"
                + "Your booking for <b>" + item + "</b> has been confirmed.<br><br>"
                + "<b>Transaction ID:</b> " + tid + "<br>"
                + "<b>Amount:</b> ₹ " + amt + "<br>"
                + "<b>Method:</b> " + method + "<br>"
                + "<b>Date:</b> " + date + "</body></html>";
        JLabel details = new JLabel(html);
        details.setBounds(50, 200, 300, 150);
        p.add(details);

        JButton ok = createModernButton("Download Receipt", primaryColor, Color.WHITE, 100, 380, 200, 45);
        ok.addActionListener(e -> d.setVisible(false));
        p.add(ok);

        d.add(p);
        d.setVisible(true);
    }

    public static void main(String[] args) {
        new PaymentGateway("testuser");
    }

    // --- Helper Methods for New UI ---

    private JPanel createSummaryRow(String key, JLabel valueLabel, int y) {
        JPanel p = new JPanel(null);
        p.setOpaque(false);
        p.setBounds(40, y, 380, 30);

        JLabel kPl = new JLabel(key);
        kPl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        kPl.setForeground(textMuted);
        kPl.setBounds(0, 0, 150, 25);
        p.add(kPl);

        valueLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        valueLabel.setForeground(textColor);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel.setBounds(150, 0, 230, 25);
        p.add(valueLabel);

        return p;
    }

    private JLabel createBadge(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(textMuted);
        l.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(4, 8, 4, 8)));
        return l;
    }

    private JLabel createStepLabel(String text, boolean active) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(active ? Color.WHITE : new Color(255, 255, 255, 100));
        return l;
    }

    private JPanel createStepLine() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(50, 2));
        p.setBackground(new Color(255, 255, 255, 50));
        return p;
    }

    // --- Custom Components ---

    class GradientPanel extends JPanel {
        Color c1, c2;

        GradientPanel(Color c1, Color c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background Gradient
            GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Decorative Glow
            g2d.setPaint(new RadialGradientPaint(
                    new Point(getWidth(), 0), 400,
                    new float[] { 0f, 1f },
                    new Color[] { new Color(255, 255, 255, 30), new Color(255, 255, 255, 0) }));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    class RoundedPanel extends JPanel {
        private Color backgroundColor;
        private int cornerRadius;

        public RoundedPanel(Color bgColor, int radius) {
            super();
            this.backgroundColor = bgColor;
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Subtle Blur-like Shadow
            for (int i = 0; i < 5; i++) {
                graphics.setColor(new Color(0, 0, 0, 3));
                graphics.drawRoundRect(i, i, getWidth() - (i * 2), getHeight() - (i * 2), cornerRadius, cornerRadius);
            }

            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Border
            graphics.setColor(new Color(226, 232, 240, 150));
            graphics.setStroke(new BasicStroke(1));
            graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        }
    }
}
