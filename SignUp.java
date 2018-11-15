package Register;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.util.Date;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import javax.swing.text.JTextComponent;

/** 
 * @class SignUp
 * Generates SignUp form
 * 
 * @author simran
 */

class SignUp { 

            static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
            static final String DB_URL = "jdbc:mysql://localhost/HotelBookingPortal";
            PreparedStatement pstmt;
            
            static final String USER = "root";
            static final String PASS = "1234";
  
            private UserInfo newUser = new UserInfo();
            JFrame frame;
            JTextField name;
            JDateChooser dob;
            JTextArea address;
            JTextField email;
            JTextField username;
            JPasswordField password;
            JPasswordField confirm;
            JButton submitButton;
            
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            SignUp() {
                
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
                
                frame = new JFrame("Sign Up");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
            
		Font labelFont = new Font ("Century Gothic", Font.PLAIN, 25);
                Font enterFont = new Font ("BANKGOTHIC MD BT", Font.PLAIN, 18);
		
		JLabel title = new JLabel();
		title.setText("Sign Up");
		Font titleFont = new Font ("Century Gothic", Font.PLAIN, 50);
		title.setFont (titleFont);
                title.setBounds(420, 50, 200, 100);
                frame.add(title);
		
		JLabel nameLabel = new JLabel("Name: ");
                name = new JTextField();
                nameLabel.setBounds(200, 200, 100, 30);
                name.setBounds(500, 200, 300, 30);
		nameLabel.setFont(labelFont);
                name.setFont(enterFont);
                name.setHorizontalAlignment(JTextField.CENTER);
		frame.add(nameLabel);
                frame.add(name);
		
                JLabel dobLabel = new JLabel("Date of birth: ");
		dob = new JDateChooser();
                dobLabel.setBounds(200, 250, 300, 25);
                dob.setBounds(500, 250, 300, 30);
                dobLabel.setFont(labelFont);
		frame.add(dobLabel);
                frame.add(dob);
               
                JLabel addressLabel = new JLabel("Residential Address: ");
		address = new JTextArea();
                addressLabel.setBounds(200, 300, 300, 30);
		address.setBounds(500, 300, 300, 100);
                addressLabel.setFont(labelFont);
                address.setFont(enterFont);
		frame.add(addressLabel);
                frame.add(address);
		
		JLabel emailLabel = new JLabel("Email-id: ");
		email = new JTextField();
                emailLabel.setBounds(200, 425, 300, 30);
		email.setBounds(500, 425, 300, 30);
                emailLabel.setFont(labelFont);
                email.setFont(enterFont);
                email.setHorizontalAlignment(JTextField.CENTER);
		frame.add(emailLabel);
                frame.add(email);
                
		JLabel usernameLabel = new JLabel("Choose username: ");
		username = new JTextField();
                usernameLabel.setBounds(200, 475, 300, 30);
		username.setBounds(500, 475, 300, 30);
                usernameLabel.setFont(labelFont);
                username.setFont(enterFont);
                username.setHorizontalAlignment(JTextField.CENTER);
		frame.add(usernameLabel);
                frame.add(username);
		
                JLabel passwordLabel = new JLabel("Choose password: ");
		password = new JPasswordField();
		passwordLabel.setBounds(200, 525, 300, 30);
		password.setBounds(500, 525, 300, 30);
                passwordLabel.setFont(labelFont);
                password.setFont(enterFont);
                password.setHorizontalAlignment(JTextField.CENTER);
		frame.add(passwordLabel);
                frame.add(password);
                
		JLabel confirmLabel = new JLabel("Confirm password: ");
		confirm = new JPasswordField();
		confirmLabel.setBounds(200, 575, 300, 30);
		confirm.setBounds(500, 575, 300, 30);
                confirmLabel.setFont(labelFont);
                confirm.setFont(enterFont);
                confirm.setHorizontalAlignment(JTextField.CENTER);
		frame.add(confirmLabel);
                frame.add(confirm);
                
                Font submitFont = new Font("Arial", Font.BOLD, 20);
                submitButton = new JButton("Submit");
                submitButton.setBounds(450, 650, 150, 50);
                submitButton.setFont(submitFont);
                submitButton.addActionListener(new ActionListener() {
                   
                    public void actionPerformed(ActionEvent e) {
                        
                        newUser.name = name.getText();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        newUser.dob = sdf.format(dob.getDate());
                        String tempAdd = address.getText();
                        newUser.address = tempAdd.replace('\n', ' ');
                        newUser.email = email.getText();
                        newUser.username = username.getText();
                        newUser.password = new String(password.getPassword());
                        String confirmIt = new String(confirm.getPassword());
                        
                        try {
                    
                            String sql = "SELECT * FROM UserInformation WHERE Username = ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, newUser.username);
                            if (pstmt.executeQuery().isBeforeFirst()) { 
                                try {
                                    JOptionPane.showMessageDialog(submitButton, "Username already taken. Choose a new one.");      
                                    username.setText(null);
                                    newUser.username = username.getText();
                                }
                        
                                catch (HeadlessException he){
                                    System.out.println("Headless Exception occurred.");
                                }
                            } 
                        }
                        
                        catch (SQLException se) {
                    
                            System.out.println("SQL Exception occurred.");
                        }
                
                        if (!confirmIt.equals(newUser.password)) {
                            try {
                        
                                JOptionPane.showMessageDialog(submitButton, "Passwords do not match. Please re-enter passwords");
                                password.setText(null);
                                confirm.setText(null);
                                newUser.password = new String(password.getPassword());
                            }
                
                            catch (HeadlessException he) {
                    
                                System.out.println("Headless Exception occurred.");
                            }
                        }
    
                       if (!newUser.username.equals("") && !newUser.password.equals("")) {
                           
                        try {
                            String sql = "INSERT INTO UserInformation (Username, Password, Name, DOB, Address, Email) VALUES (?, ?, ?, ?, ?, ?)";
                            
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, newUser.username);
                            pstmt.setString(2, newUser.password);
                            pstmt.setString(3, newUser.name);
                            pstmt.setString(4, newUser.dob);
                            pstmt.setString(5, newUser.address);
                            pstmt.setString(6, newUser.email);
                            
                        }
                        
                        catch (SQLException se) {
                    
                            System.out.println("SQL Exception occurred.");
                        }
                
                        try {
                            int c = pstmt.executeUpdate();
                        } 
                        
                        catch (SQLException se) {
                           
                            System.out.println("SQL Exception occurred.");
                        }
                        
                        try {
                            JOptionPane.showMessageDialog(submitButton, "You have signed up successfully!");
                        }   
                            
                        catch (HeadlessException he) {
                    
                            System.out.println("Headless Exception occurred.");
                        }
                       }
                    }
                });
                frame.add(submitButton);
		frame.setSize(1000,1000);
		frame.setVisible(true);
            }
}
                  
