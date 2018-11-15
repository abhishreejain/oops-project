/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Register;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author simran
 */
public class SignIn {
    
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/HotelBookingPortal";
    
    static final String USER = "root";
    static final String PASS = "1234";
    
    private UserInfo newUser = new UserInfo();
    
    JFrame frame;
    JTextField username;
    JPasswordField password;
    JButton submitButton;
            
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt;
    ResultSet rs = null;
            
    SignIn() {
    
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        }
                
        catch (ClassNotFoundException cnf) {
                
            System.out.println ("Class not found Exception occurred.");
                    
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
                    
            catch (SQLException se) {
                
                System.out.println("SQL Excepton occurred.");   
            }
        }
                
        catch (SQLException se) {
                
            System.out.println("SQL Excepton occurred.");   
                   
        }
                
        catch (Exception e){
                
            System.out.println ("Exception occurred.");    
        }
        
        frame = new JFrame("Sign In");
	frame.setLayout(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            
	Font labelFont = new Font ("Century Gothic", Font.PLAIN, 30);
        Font enterFont = new Font ("BANKGOTHIC MD BT", Font.PLAIN, 25);
		
	JLabel title = new JLabel();
	title.setText("Sign In");
	Font titleFont = new Font ("Century Gothic", Font.PLAIN, 50);
	title.setFont (titleFont);
        title.setBounds(420, 50, 200, 100);
        frame.add(title);
        
        JLabel usernameLabel = new JLabel("Enter username: ");
        username = new JTextField();
        usernameLabel.setBounds(200, 200, 300, 50);
        username.setBounds(500, 200, 300, 50);
	usernameLabel.setFont(labelFont);
        username.setFont(enterFont);
        username.setHorizontalAlignment(JTextField.CENTER);
	frame.add(usernameLabel);
        frame.add(username);
		
        JLabel passwordLabel = new JLabel("Enter password: ");
	password = new JPasswordField();
        passwordLabel.setBounds(200, 300, 300, 50);
        password.setBounds(500, 300, 300, 50);
        passwordLabel.setFont(labelFont);
        password.setFont(enterFont);
        password.setHorizontalAlignment(JTextField.CENTER);
	frame.add(passwordLabel);
        frame.add(password);
        
        Font submitFont = new Font("Arial", Font.BOLD, 20);
        submitButton = new JButton("Submit");
        submitButton.setBounds(420, 450, 150, 50);
        submitButton.setFont(submitFont);
        submitButton.addActionListener(new ActionListener() {
                   
            public void actionPerformed(ActionEvent e) {
                        
                newUser.username = username.getText();
                newUser.password = new String(password.getPassword());
                
                try {
                    String sql = "SELECT Username, Password FROM UserInformation WHERE Username = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, newUser.username);
                    rs = pstmt.executeQuery();
                    
                    if (!rs.isBeforeFirst()) { 
                        
                        try {
                            JOptionPane.showMessageDialog(submitButton, "Username does not exist. Please try again.");      
                            username.setText(null);
                            password.setText(null);
                            newUser.username = username.getText();
                            newUser.password = new String(password.getPassword());
                        }
                        
                        catch (HeadlessException he){
                            System.out.println("Headless Exception occurred.");
                        }
                    } 
                    
                    else {
                    
                        while (rs.next()) {
                    
                            String tempPass = rs.getString("Password");
                            if (!newUser.password.equals(tempPass)) {
                        
                                JOptionPane.showMessageDialog(submitButton, "Wrong password. Please try again.");
                                password.setText(null);
                                newUser.password = new String(password.getPassword());
                            }
                            
                            if (!newUser.username.equals("") && !newUser.password.equals("")) {
                            
                                try {
                                    JOptionPane.showMessageDialog(submitButton, "You have signed in successfully!");
                                }   
                            
                                catch (HeadlessException he) {
                                    System.out.println("Headless Exception occurred.");
                                }
                            
                            }
                        }
                    }
                }
                            
                    catch (SQLException se) {
                    
                        System.out.println("SQL Exception occurred.");
                    }
            }
        });
        
        frame.add(submitButton);
	frame.setSize(1000,800);
	frame.setVisible(true);
        
    }
}
