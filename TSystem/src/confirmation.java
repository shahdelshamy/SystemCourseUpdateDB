import java.awt.Color;
import java.awt.Font; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.sql.DriverManager;

import javax.swing.*;

public class confirmation extends JFrame implements ActionListener {
     JLabel welcome,username,password,conpassword,message; 
     JTextField userText;
     JPasswordField passText,conpassText;
     JButton save,back,show1,show2;
     private boolean passwordVisible;
     
     Connection con;
     Statement st;
     PreparedStatement stp;
     
     
     public confirmation() {
    	 
    	 welcome=new JLabel("Welcome To My System");
 		Font font3 = new Font("Arial", Font.CENTER_BASELINE, 25);
		Font font5 = new Font("Arial", Font.HANGING_BASELINE, 15);
 		welcome.setFont(font3);
 		 message=new JLabel("<html>Note:*<br/>You Can't Change The UserName</html>");
 	    message.setForeground(Color.red);  
 	    message.setFont(font5);
 		
 		
 		Font font1=new Font("Arial", Font.PLAIN, 20);
 		Font font2=new Font("Arial", Font.PLAIN, 15);
 		username=new JLabel("UserName:*");
 		username.setFont(font1);
 		password=new JLabel("Password:*");
 		password.setFont(font1);
 		conpassword=new JLabel("Confirm the password:*");
 		conpassword.setFont(font1);
 		
 		userText=new JTextField();
 		userText.setFont(font2);
 		passText=new JPasswordField();
 		passText.setFont(font2);
 		conpassText=new JPasswordField();
 		conpassText.setFont(font2);
 		
 		show1=new JButton("SHOW");
 		show2=new JButton("SHOW");
 		back=new JButton("Back");
 		save=new JButton("Save");
 		
 		
 		
 		this.setLayout(null);	
 		welcome.setBounds(100,40,340 ,50 );
 		username.setBounds(50,130,150,30);
 		password.setBounds(50,180,150,30);
 		conpassword.setBounds(30,230,230,30);
 		
 		userText.setBounds(170,130,150,30);
 		passText.setBounds(170,180,150,30);
 		conpassText.setBounds(250,230,150,30);
 		message.setBounds(365,90,150,100);
 		
 		show1.setBounds(350,180,80,30);
 		show2.setBounds(440,230,80,30);
 		save.setBounds(230,300,80,30);
 		back.setBounds(90,300,80,30);
 		
 		
 		add(welcome);
 		add(username);
 		add(password);
 		add(conpassword);
 		add(userText);
 		add(passText);
 		add(conpassText);
 		add(show1);
 		add(show2);
 		add(save);
 		add(back);
 		add(message);
 		
 		this.setVisible(true);
 		this.setSize(600,450);
 		this.setTitle("confirmation");
 		this.setLocationRelativeTo(null);
 		
 		
 		save.addActionListener(this);
 		back.addActionListener(this);
 		
 		show1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                passText.setEchoChar((char) 0);
            }

            public void mouseReleased(MouseEvent e) {
            	passText.setEchoChar('\u2022');
            }
        }); 
 		
 		show2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                conpassText.setEchoChar((char) 0);
            }

            public void mouseReleased(MouseEvent e) {
            	conpassText.setEchoChar('\u2022');
            }
        }); 
    	 
     }
     
    
     public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==save) {
    		int x=0;
    		if(!userText.getText().isEmpty()) {
    			if(!passText.getText().isEmpty()) {
    				if(passText.getText().equals(conpassText.getText())) {
    					
    					db();
    					try {
    						st=con.createStatement();
    					int update1= st.executeUpdate("update courseAdmins set password='"+passText.getText()+"' where username='"+userText.getText()+"'");
    					int update2= st.executeUpdate("update courseAdmins set status='true' where username='"+userText.getText()+"'");
    					System.out.println(update1+"\n"+update2);
    					x=1;
    					con.close();
    					}catch(Exception ex) {System.out.println(ex);}
    					
    				}
    				else {
    					int option=JOptionPane.showConfirmDialog(null,"The password and confirmation not matching,Please cheake it!!");
    				}
    				
    			}
    			else {
        			int option=JOptionPane.showConfirmDialog(null, "Please Enter a password!!");
    			}
    		}
    		else {
    			int option=JOptionPane.showConfirmDialog(null, "Please Enter the userName!!");
    		}
    		
    		
    		if(x==1) {
    			save.setText("saved");
    			save.setForeground(Color.red);
    			int option=JOptionPane.showConfirmDialog(null, "**saved**");
    		}
    		
    		
    	}
    	
    	if(e.getSource()==back) {
    		this.setVisible(false);
    		WelcomePage we=new WelcomePage();
    		
    	}
    	 
     }
     
	
	
	
	public void db() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			   con=DriverManager.getConnection(
					   "jdbc:oracle:thin:@localhost:1521:xe","test","test"); 
	
		}catch(Exception ex) {System.out.println(ex);}
		
		
	}
	
	
}
