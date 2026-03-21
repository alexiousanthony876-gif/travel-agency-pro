package travel_and_Tourism_Organisation_System;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLEditorKit;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ChatBot extends JFrame implements ActionListener {
    JTextPane chatArea;
    JTextField inputField;
    JButton sendButton, configButton;
    String username;
    StringBuilder chatHistory = new StringBuilder();
    private static String openaiApiKey = "YOUR_OPENAI_API_KEY_HERE"; // Enter your key via Settings button in the app
    private static String geminiApiKey = null; // Ready for Free Gemini Key
    private static String activeModel = "OpenAI";

    // Premium Color Palette
    Color primaryDark = new Color(15, 23, 42); // Midnight Blue
    Color accentColor = new Color(0, 122, 255); // iOS Blue
    Color geminiColor = new Color(138, 110, 255); // Purple for Gemini
    Color botBg = new Color(241, 245, 249); // Muted gray-blue
    Color userBg = new Color(219, 234, 254); // Light Blue

    public ChatBot(String username) {
        this.username = username;

        setTitle("AI Travel Assistant | Travel Wizard");
        setBounds(450, 150, 500, 700);
        setLayout(new BorderLayout());
        setResizable(false);

        // ================= HEADER =================
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryDark);
        headerPanel.setPreferredSize(new Dimension(500, 100));
        headerPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Travel Mate AI");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(25, 20, 200, 30);
        headerPanel.add(titleLabel);

        updateStatusLabel(headerPanel);

        configButton = new JButton("⚙ Settings");
        configButton.setBounds(360, 30, 100, 30);
        configButton.setBackground(new Color(255, 255, 255, 40));
        configButton.setForeground(Color.WHITE);
        configButton.setOpaque(true);
        configButton.setBorder(null);
        configButton.setFocusPainted(false);
        configButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configButton.addActionListener(e -> configureAPI());
        headerPanel.add(configButton);

        add(headerPanel, BorderLayout.NORTH);

        // ================= CHAT AREA =================
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setContentType("text/html");
        chatArea.setBackground(Color.WHITE);

        // CSS for message bubbles
        HTMLEditorKit kit = new HTMLEditorKit();
        chatArea.setEditorKit(kit);
        kit.getStyleSheet()
                .addRule("body { font-family: 'Segoe UI', sans-serif; padding: 10px; background-color: white; }");
        kit.getStyleSheet().addRule(
                ".bot-bubble { background-color: #f1f5f9; padding: 12px; margin-bottom: 15px; border-radius: 15px 15px 15px 0px; max-width: 85%; float: left; clear: both; color: #334155; border: 1px solid #e2e8f0; }");
        kit.getStyleSheet().addRule(
                ".user-bubble { background-color: #dbeafe; padding: 12px; margin-bottom: 15px; border-radius: 15px 15px 0px 15px; max-width: 85%; float: right; clear: both; color: #1e40af; border: 1px solid #bfdbfe; }");
        kit.getStyleSheet().addRule(
                ".sender-name { font-size: 10px; font-weight: bold; margin-bottom: 4px; display: block; opacity: 0.7; }");

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // ================= INPUT AREA =================
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(15, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(15, 20, 20, 20));
        bottomPanel.setPreferredSize(new Dimension(500, 90));

        inputField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(248, 250, 252));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.setColor(new Color(226, 232, 240));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        inputField.setOpaque(false);
        inputField.setBorder(new EmptyBorder(0, 15, 0, 15));
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        inputField.setForeground(primaryDark);
        bottomPanel.add(inputField, BorderLayout.CENTER);

        sendButton = new JButton("Send") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(activeModel.equals("Gemini") ? geminiColor : accentColor);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        sendButton.setOpaque(false);
        sendButton.setContentAreaFilled(false);
        sendButton.setBorder(null);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setPreferredSize(new Dimension(80, 45));
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendButton.addActionListener(this);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Initial Greeting
        greetUser();

        // Enter key action
        getRootPane().setDefaultButton(sendButton);

        setVisible(true);
    }

    private void updateStatusLabel(JPanel header) {
        String statusText = "● Offline - Local AI";
        Color statusColor = new Color(234, 179, 8);

        if (activeModel.equals("Gemini")) {
            statusText = "● Online - Gemini (Free AI)";
            statusColor = geminiColor;
        } else if (activeModel.equals("OpenAI")) {
            statusText = "● Online - ChatGPT Active";
            statusColor = new Color(34, 197, 94);
        }

        JLabel statusLabel = new JLabel(statusText);
        statusLabel.setForeground(statusColor);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setBounds(25, 50, 300, 20);
        header.add(statusLabel);
    }

    private void configureAPI() {
        String[] options = { "Google Gemini (Free Tier)", "OpenAI ChatGPT", "Reset to Local" };
        int choice = JOptionPane.showOptionDialog(this, "Select AI Model:", "AI Settings",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String key = JOptionPane.showInputDialog(this, "Enter Google Gemini API Key:", geminiApiKey);
            if (key != null && !key.trim().isEmpty()) {
                geminiApiKey = key.trim();
                activeModel = "Gemini";
                openaiApiKey = null;
                restart();
            }
        } else if (choice == 1) {
            String key = JOptionPane.showInputDialog(this, "Enter OpenAI API Key:", openaiApiKey);
            if (key != null && !key.trim().isEmpty()) {
                openaiApiKey = key.trim();
                activeModel = "OpenAI";
                geminiApiKey = null;
                restart();
            }
        } else if (choice == 2) {
            activeModel = "Local";
            openaiApiKey = null;
            geminiApiKey = null;
            restart();
        }
    }

    private void restart() {
        dispose();
        new ChatBot(username);
    }

    private void greetUser() {
        String greeting = "Hello " + username + "! ";
        if (activeModel.equals("Gemini"))
            greeting += "Google Gemini is active and ready to plan your trip for free!";
        else if (activeModel.equals("OpenAI"))
            greeting += "ChatGPT is connected. How can I assist you today?";
        else
            greeting += "Local AI is active. Configure a free Gemini key in settings for smarter advice.";
        appendMessage("Bot", greeting);
    }

    private void appendMessage(String sender, String message) {
        String bubbleClass = sender.equals("Bot") ? "bot-bubble" : "user-bubble";
        String senderLabel = sender.equals("Bot") ? activeModel : "You";

        chatHistory.append("<div class='").append(bubbleClass).append("'>");
        chatHistory.append("<div class='sender-name'>").append(senderLabel).append("</div>");
        chatHistory.append("<div style='padding-top: 5px;'>").append(message.replace("\n", "<br>")).append("</div>");
        chatHistory.append("</div>");

        chatArea.setText("<html><body>" + chatHistory.toString() + "</body></html>");
        SwingUtilities.invokeLater(() -> chatArea.setCaretPosition(chatArea.getDocument().getLength()));
    }

    public void actionPerformed(ActionEvent ae) {
        String msg = inputField.getText().trim();
        if (msg.length() > 0) {
            appendMessage("User", msg);
            inputField.setText("");

            new Thread(() -> {
                String response;
                if (activeModel.equals("Gemini")) {
                    response = fetchGeminiResponse(msg);
                } else if (activeModel.equals("OpenAI")) {
                    response = fetchChatGPTResponse(msg);
                } else {
                    try {
                        Thread.sleep(600);
                    } catch (Exception e) {
                    }
                    response = generateLocalResponse(msg.toLowerCase());
                }
                final String finalResponse = response;
                SwingUtilities.invokeLater(() -> appendMessage("Bot", finalResponse));
            }).start();
        }
    }

    private String fetchGeminiResponse(String userMsg) {
        if (geminiApiKey == null || geminiApiKey.trim().isEmpty()) {
            return "<b>Error:</b> Google Gemini API Key is missing. Please go to <b>Settings</b> and enter your 'AIza' key to enable this free model.";
        }
        try {
            URL url = new URL(
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                            + geminiApiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject()
                    .put("contents", new JSONArray()
                            .put(new JSONObject().put("parts", new JSONArray()
                                    .put(new JSONObject().put("text",
                                            "You are a professional travel assistant for Travel Wizard. Keep it concise. User asks: "
                                                    + userMsg)))));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                result.append(line);

            JSONObject response = new JSONObject(result.toString());
            return response.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts")
                    .getJSONObject(0).getString("text");
        } catch (Exception e) {
            return "Gemini Error: " + e.getMessage() + ". Try checking your key.";
        }
    }

    private String fetchChatGPTResponse(String userMsg) {
        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + openaiApiKey);
            conn.setDoOutput(true);

            String body = new JSONObject()
                    .put("model", "gpt-4o-mini")
                    .put("messages", new JSONArray()
                            .put(new JSONObject().put("role", "system").put("content",
                                    "Concise travel assistant."))
                            .put(new JSONObject().put("role", "user").put("content", userMsg)))
                    .toString();

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                result.append(line);

            JSONObject jsonResponse = new JSONObject(result.toString());
            return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

        } catch (Exception e) {
            if (e.getMessage().contains("429")) {
                return "<b>Notice:</b> OpenAI Quota Exhausted (429).";
            }
            return "Connection Error: " + e.getMessage();
        }
    }

    private String generateLocalResponse(String msg) {
        if (msg.contains("hello") || msg.contains("hi"))
            return "Hello! Ask about 'packages' or 'hotels'.";
        if (msg.contains("package"))
            return "<b>Packages:</b><br>• Gold ($1200)<br>• Silver ($1000)";
        if (msg.contains("hotel"))
            return "Recommended Stays:<br>• Taj Hotel<br>• Marriott<br>• Hyatt Regency";
        return "Local Assistant mode active. Use settings to enable Gemini for free!";
    }

    public static void main(String[] args) {
        new ChatBot("Guest");
    }
}
