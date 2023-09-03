import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.*;

public class WelcomePage extends JFrame implements ActionListener{

	JLabel welcome,name,message,password,assistantLabel;
	JPasswordField passText;
	JTextField nameText;
	JButton done,reset,show;
	JComboBox<String> teachernamecombo; 
	
	JCheckBox checking;
	
	Connection con;
	Statement st;
	
	int x=0;
	
	public WelcomePage(){
		
		welcome=new JLabel("Welcome To My System");
		Font font3 = new Font("Arial", Font.CENTER_BASELINE, 25);
		welcome.setFont(font3);
		//welcome.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		message=new JLabel();
	
		name=new JLabel("UserName:*");
	    Font font1 = new Font("Arial", Font.PLAIN, 20);
	    Font font2 = new Font("Arial", Font.PLAIN, 15);
		name.setFont(font1);
		nameText=new JTextField();
		nameText.setFont(font2);
		
		password=new JLabel("Password:*");
		passText=new JPasswordField ();
		password.setFont(font1);
		passText.setFont(font2);
		
		assistantLabel=new JLabel("<html>If you assistant please<br/>Enter the teacher name:*</html>");
		Font font = new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 18);
		assistantLabel.setFont(font);
		teachernamecombo=new JComboBox();
		teachernamecombo.addItem("Choose the teacher name--V");
		
		checking =new JCheckBox("Assistant");
		checking.setFont(font);
		
		done=new JButton("Done"); 
		reset=new JButton("Reset"); 
		show=new JButton("SHOW"); 
		
	this.setLayout(null);	
	welcome.setBounds(130,40,340 ,50 );
	name.setBounds(50,120,150 ,50 );
	nameText.setBounds(160, 130, 130, 28);
	password.setBounds(50,160,150,50);
	passText.setBounds(160,170,130,28);
	show.setBounds(320,170,75,30);
	
	assistantLabel.setBounds(50,230,200,100);
	teachernamecombo.setBounds(255,275,200,30);
	
	checking.setBounds(100,200,100,50);
	
	done.setBounds(230,340, 70, 30);
	message.setBounds(100,300,200,30);
	reset.setBounds(100,340, 70, 30);
	
	add(welcome);
	add(name);
	add(nameText);
	add(password);
	add(passText);
	add(assistantLabel);
	add(teachernamecombo);
	add(checking);
	
	add(done);
	add(message);
	add(reset);
	add(show);
reset.setVisible(false);
assistantLabel.setVisible(false);
teachernamecombo.setVisible(false);

	






	
	done.addActionListener(this);
	reset.addActionListener(this);
	checking.addActionListener(this);
	
	show.addMouseListener(new MouseAdapter () {
		public void mousePressed(MouseEvent e) {
			passText.setEchoChar((char)0);
		}
		public void mouseReleased(MouseEvent e) {
			passText.setEchoChar('\u2022');
		}
		
	});
	
	this.setVisible(true);
	this.setSize(600,450);
	this.setLocationRelativeTo(null);
	this.setTitle("Welcome Page");
	
	}
	
	public void actionPerformed(ActionEvent e) {
		    if(!nameText.getText().isEmpty()) {
		    	
		    	if(e.getSource()==checking && checking.isSelected()) {
		    		
		    		try {
		    			db();
		    	        st = con.createStatement();
		    	 
		    	        String username = nameText.getText();
		    		        PreparedStatement statement = con.prepareStatement("SELECT firstname, lastname FROM courseAdmins WHERE username = ?");
		    		        statement.setString(1, username);
		    		        ResultSet resultSet = statement.executeQuery();
                             
		    		        String fullName = null;
		    		        
		    		        while (resultSet.next()) {
		    		            String firstName = resultSet.getString("firstname");
		    		            String lastName = resultSet.getString("lastname");
		    		            fullName = firstName + " " + lastName;
		    		          
		    		        }
		    		        resultSet.close();
		    		        
		    		        ResultSet re = con.createStatement().executeQuery("SELECT * FROM assistant where assistantName='"+fullName+"'");
                           
		    		        ResultSetMetaData metaData = re.getMetaData();
			    	        int columnCount = metaData.getColumnCount();

			    	        String[] columnNames = new String[columnCount];
			    	        for (int i = 1; i <= columnCount; i++) {
			    	            columnNames[i - 1] = metaData.getColumnName(i);
			    	        }
			    	        
		    		        while(re.next()) {
                        	   if(fullName.equals(re.getString(1))) {
                        		   assistantLabel.setVisible(true);
               		    		teachernamecombo.setVisible(true);
                        		   for (int i = 1; i <+ columnCount; i++) {
                        			  teachernamecombo.addItem(re.getString(columnNames[i]));
           		    	        }	   
                        	   }
                           }
                           re.close();
		    	        
		    	        }catch(Exception ex) {System.out.println(ex);}
		    	
		    	}
		    	
		    	
	if(e.getSource()==done) {
			   
			 try {  
			   db();
			   st=con.createStatement();
			   ResultSet re=st.executeQuery("select* from courseAdmins");
			   //ResultSet re1=st.executeQuery("select* from assistant");
			   
                  int y=0;
			   
			  while(re.next()) {
				//System.out.println(re.getString(1)+"    "+re.getString(2)); 
    if((nameText.getText().equals(re.getString(5))) && re.getString(7).equals("Assistant") && passText.getText().equals(re.getString(6)) && re.getString(8).equals("true")){
                    
    	  if(checking.isSelected()) {
                    	
                if(teachernamecombo.getItemCount() > 0 && !teachernamecombo.getSelectedItem().equals("Choose the teacher name--V")) {
                    this.setVisible(false);
          			studentInfo s=new studentInfo(nameText.getText(),(String)teachernamecombo.getSelectedItem()); 
                    x=1;
				   break;
			   }
                    	else {
                    		 x=1;
                        	int option=JOptionPane.showConfirmDialog(null,"Please choose teacher who you work with him!!");
                    	}
                    }
                    else {
                    	 x=1;
                    	int option=JOptionPane.showConfirmDialog(null,"Please Check The assiatant!!");
                    }
			   }
			   
			   
	else if((nameText.getText().equals(re.getString(5))) && re.getString(7).equals("Assistant") && passText.getText().equals(re.getString(6)) && re.getString(8).equals("false")){
				   if(checking.isSelected()) {
                   	if(teachernamecombo.getItemCount() > 0 && !teachernamecombo.getSelectedItem().equals("Choose the teacher name--V")) {
				   this.setVisible(false);
				   confirmation con =new confirmation();   
				   x=1;
				   break;
			   }
                    	else {
                    		 x=1;
                        	int option=JOptionPane.showConfirmDialog(null,"Please choose teacher who you work with him!!");
                    	}
                    }
                    else {
                    	 x=1;
                    	int option=JOptionPane.showConfirmDialog(null,"Please Check The assiatant!!");
                    }  
			   }
			   
			   
	else if((nameText.getText().equals(re.getString(5))) && re.getString(7).equals("Manager") && passText.getText().equals(re.getString(6)) && re.getString(8).equals("true")){
				   this.setVisible(false);
				   admin s=new admin("Sayed"); 
				   x=1;
				   break;
			   }
			   
			   
	else if ((nameText.getText().equals(re.getString(5))) && re.getString(7).equals("Teacher") && passText.getText().equals(re.getString(6))&& re.getString(8).equals("true")){
				  
		 if(checking.isSelected()) {
			 int option=JOptionPane.showConfirmDialog(null,"you not assistant,please remove the check mark");
			 x=1;
			 break;
		 }
		
		       this.setVisible(false);
				//teacher1 t=new teacher1(nameText.getText());
				teacher t=new teacher(nameText.getText());
				x=1;
				break;
			  }
			   
	 else if ((nameText.getText().equals(re.getString(5))) && re.getString(7).equals("Teacher") && passText.getText().equals(re.getString(6))&& re.getString(8).equals("false")){
		 if(checking.isSelected()) {
			 int option=JOptionPane.showConfirmDialog(null,"you not assistant,please remove the check mark");
			 x=1;
			 break;
		 }		   
		 
		 this.setVisible(false);
		confirmation con =new confirmation();
				   
			   
			  }
			  
			  }
			  
			 
			  
			   if(x==0) {
				  reset.setVisible(true);
				   message.setForeground(Color.red);
					message.setText("invalid name or Password ");
		
			   }
			   
			   
			  
			
			con.close();
			 }catch(Exception ex) {System.out.println(ex);}
			
		}
		   if(e.getSource()==reset) {
			   message.setText("");
			   nameText.setText("");
			   passText.setText("");
			   checking.setSelected(false);
		   }
	
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
