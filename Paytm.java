package travel_and_Tourism_Organisation_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Paytm extends JFrame implements ActionListener {
    
    Paytm(){
        setLayout(null);
        setBounds(340, 80, 800, 600);
        
        JLabel label = new JLabel("Scan QR Code to Pay");
        label.setFont(new Font("Raleway", Font.BOLD, 40));
        label.setBounds(50, 20, 500, 45);
        add(label);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("travel_and_Tourism_Organisation_System/icons/upi_uploaded.jpg"));
        Image i2 = i1.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l2 = new JLabel(i3);
        l2.setBounds(250, 100, 300, 300);
        add(l2);
        
        JButton back = new JButton("Back");
        back.addActionListener(this);
        back.setBounds(610, 20, 80, 40);
        add(back);
        
        getContentPane().setBackground(Color.WHITE);
        
        setSize(800,600);
        setLocation(340,80);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Payment();
    }
    
    public static void main(String[] args){
        new Paytm().setVisible(true);
    }
}