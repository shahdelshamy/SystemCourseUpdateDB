import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class admin extends JFrame implements ActionListener {
	
	JLabel message,firstName,lastName,phone,job,subject,teacherName,userName,password;
	JTextField FText,LText,phoneText,jobText,userText,passText;
	
    JComboBox<String> jobs;
  
    JCheckBox[] subjects;
    JCheckBox[] teachers;
    
    int n;
    
	JButton save,reset,back;
	Connection con;
	Statement st;
	PreparedStatement stp1,stp2;
	JCheckBox[] result;
	
	public admin(String name) {
		
		
		Font font1 = new Font("Arial", Font.CENTER_BASELINE, 20);
		message=new JLabel("Hello "+name);
		message.setFont(font1);
		firstName=new JLabel ("FirstName:*");
		lastName=new JLabel ("LastName:*");
		phone=new JLabel ("PhoneNumber:*");
		job=new JLabel ("Job:*");
		subject=new JLabel ("Subject:*");
		teacherName=new JLabel ("TeacherName:*");
		userName=new JLabel ("UserName:*");
		password=new JLabel ("Password:*");
		
		FText=new JTextField();
		LText=new JTextField();
		phoneText=new JTextField();
		jobText=new JTextField();
		userText=new JTextField();
		passText=new JTextField();
		
		save=new JButton("Save");
		back=new JButton("Back<--");
		reset=new JButton("Reset");
		
		jobs=new JComboBox <>(new String[] {"Choose a job--V","Teacher","Assistant"});
		
		subjects = new JCheckBox[7];
		subjects[0]=new JCheckBox("Araby"); 
		subjects[1]=new JCheckBox("English");
		subjects[2]=new JCheckBox("Franch"); 
		subjects[3]=new JCheckBox("Chemistry"); 
		subjects[4]=new JCheckBox("Biology"); 
		subjects[5]=new JCheckBox("Physics");
		subjects[6]=new JCheckBox("Math");

		
		db();
		try {
			st=con.createStatement();
			ResultSet re=st.executeQuery("select count(*) from courseAdmins where job='Teacher'");
			while(re.next()) {
				n=re.getInt(1);
			}
			
		} catch (SQLException e) {System.out.println(e);}
		
		
		
		teachers = new JCheckBox[n];
		 result=array(teachers);
		
		for(int i=0;i<result.length;i++) {
			result[i].setBounds(i*130,380,130,50);
			this.add(result[i]);
			result[i].setVisible(false);
		}
		
		
		for(int i=0;i<subjects.length;i++) {
			subjects[i].setBounds(i*85,380,85,50);
			this.add(subjects[i]);
			subjects[i].setVisible(false);
		}
		
		
       
        
		this.setLayout(null);
		
		message.setBounds(200,20,130,50);
		firstName.setBounds(50,70,80,50);
		lastName.setBounds(50,120,80,50);
		phone.setBounds(40,170,95,50);
		userName.setBounds(50,225,70,30);
		password.setBounds(50,270,70,30);
		
		job.setBounds(60,310,70,30);
		jobs.setBounds(95,345,130,30);
		
		
		FText.setBounds(130,85,120,25);
		LText.setBounds(130,135,120,25);
		phoneText.setBounds(135,185,120,25);
		userText.setBounds(130,230,120,25);
		passText.setBounds(130,272,120,25);
		
		back.setBounds(50,450,80,30);
		reset.setBounds(150,450,70,30);
		save.setBounds(240,450,70,30);

		add(message);
		add(firstName);
		add(lastName);
		add(phone);
		add(job);
		add(jobs);
		add(FText);
		add(LText);
		add(phoneText);
		add(userName);
		add(password);
		add(userText);
		add(passText);
		add(save);
		add(back);
		add(reset);
		
		jobs.addActionListener(this);
		save.addActionListener(this);
		back.addActionListener(this);
		reset.addActionListener(this);
		
		
		 this.setVisible(true);
			this.setSize(650,600);
			this.setLocationRelativeTo(null);
			this.setTitle("Admin Page");	
		
		
		
	}
	
	public JCheckBox[]array(JCheckBox [] arr){
		
		db();
		try {
			st=con.createStatement();
			ResultSet re = st.executeQuery("SELECT firstname || ' ' || lastname AS name FROM courseAdmins WHERE job = 'Teacher'");
			int y=0;
			while(re.next()) {
				String name=re.getString(1);
				arr[y]=new JCheckBox(name);
				y++;
			}
			con.close();
		} catch (SQLException e) {System.out.println(e);}
		
		
		
		return arr;
		
	
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jobs) {
			
			if (jobs.getSelectedItem().equals("Teacher")) {
				for(int i=0;i<subjects.length;i++) {
					subjects[i].setVisible(true);
				}
			}
			else {
				for(int i=0;i<subjects.length;i++) {
					subjects[i].setVisible(false);
				}
			}
				
				if (jobs.getSelectedItem().equals("Assistant")) {
					for(int i=0;i<result.length;i++) {
						result[i].setVisible(true);
					}
					}
				
				else {
					for(int i=0;i<result.length;i++) {
						result[i].setVisible(false);
					}
				}
			
		}
		
		if(e.getSource()==save) {
			int pass=0;
			boolean atLeastOneChecked = false;
			if(!FText.getText().isEmpty() && !LText.getText().isEmpty() && !phoneText.getText().isEmpty() && !userText.getText().isEmpty() && !passText.getText().isEmpty()) {
				if(jobs.getSelectedItem().equals("Teacher") || jobs.getSelectedItem().equals("Assistant")) {	
					db();
					try {
						
						String sql="insert into courseAdmins values (?,?,?,?,?,?,?,?)";
						 stp1=con.prepareStatement(sql);
						 
						 st=con.createStatement();
						 ResultSet res=st.executeQuery("select max(adminID) from courseAdmins");
						 int max = 0;
						 while(res.next()) {
							 max=res.getInt(1);
						 }
						 max=max+1;
						 
						 stp1.setInt(1,max);
						 stp1.setString(2, FText.getText());
						 stp1.setString(3, LText.getText());
						 stp1.setString(4,phoneText.getText());
						 stp1.setString(5, userText.getText());
						 stp1.setString(6, passText.getText());
						 
						 if(jobs.getSelectedItem().equals("Teacher")) {
							 stp1.setString(7,"Teacher");
						 }
						 if(jobs.getSelectedItem().equals("Assistant")) {
							 stp1.setString(7,"Assistant");
						 }
						 stp1.setString(8,"false");
						 
						 
					}catch(Exception ex) {System.out.println(ex);}


				}
				
				if(jobs.getSelectedItem().equals("Teacher")) {	
					
					for (int i = 0; i < subjects.length; i++) {
					    if (subjects[i].isSelected()) {
					        atLeastOneChecked = true;
					        break;
					    }
					}
					if(atLeastOneChecked) {
								
							try {
							 String sql1 = "INSERT INTO subteacher VALUES (?, ?, ?, ?, ?, ?, ?,?)";
							 stp2 = con.prepareStatement(sql1);
							 
							 String name= FText.getText()+" "+LText.getText();
							 
							 stp2.setString(1, name);
							 
							 for(int i=0;i<subjects.length;i++) {
								 if(subjects[i].isSelected()) {
									 stp2.setInt(i+2,1);
								 }
								 else {
									 stp2.setInt(i+2,0);
								 }
							 }
							 
							 stp1.executeUpdate();
							 stp2.executeUpdate();

							 st=con.createStatement();
							 ResultSet re=st.executeQuery("select count(*) from all_tab_columns where table_name = 'ASSISTANT'");
							 int number = 0;
							 if (re.next()) {
								    number = re.getInt(1) ;
								}
								String columnName = "teacher" + number;
							 
								int update = st.executeUpdate("ALTER TABLE ASSISTANT ADD " + columnName + " VARCHAR(100)");
								System.out.println(update);
								
								pass=1; 
							con.close();
						}catch(Exception ex) {System.out.println(ex);}
							
					
					}
								
				}
				
				else if(jobs.getSelectedItem().equals("Assistant")) {
					for (int i = 0; i < result.length; i++) {
					    if (result[i].isSelected()) {
					        atLeastOneChecked = true;
					        break;
					    }
					}
					
					if(atLeastOneChecked) {
						                            
							try { 
								String tableName = "ASSISTANT";
								String sql1 = "INSERT INTO " + tableName + " VALUES (";

								DatabaseMetaData metaData = con.getMetaData();
								ResultSet rsColumns = metaData.getColumns(null, null, tableName, null);

								int columnCount = 0;
								while (rsColumns.next()) {
								    String columnName = rsColumns.getString("COLUMN_NAME");
								    if (columnCount > 0) {
								        sql1 += ", ";
								    }
								    sql1 += "?";
								    columnCount++;
								}
								sql1 += ")";
								stp2 = con.prepareStatement(sql1);


							 String name= FText.getText()+" "+LText.getText(); 
							 stp2.setString(1, name);
							 
							
							 					 
							 for(int i=0;i<result.length;i++) {
								 if(result[i].isSelected()) {
									 stp2.setString(i+2,result[i].getText());
								 }
								 else {
									 stp2.setString(i+2,null);
								 }
							 }
							 
							 
							 stp1.executeUpdate();
							 stp2.executeUpdate();
							
							 pass=1;
							con.close();
						}catch(Exception ex) {System.out.println(ex);}
						
					}
		
				
				}
				
				
				if(pass==1) {
				save.setText("saved");
				save.setForeground(Color.red);
				int option =JOptionPane.showConfirmDialog(null,"The information is saved");
				}
				else {
					int option =JOptionPane.showConfirmDialog(null,"Something in registration is incomplete,Please check it");
				}
		
				
			}
			
			else {
				int option =JOptionPane.showConfirmDialog(null,"Something in registration is incomplete,Please check it");
			}
			
		}
		
		if(e.getSource()==reset) {
			FText.setText("");
			LText.setText("");
			phoneText.setText("");
			userText.setText("");
			passText.setText("");
			jobs.setSelectedItem("Choose a job--V");;
			for(int i=0;i<subjects.length;i++) {
				subjects[i].setSelected(false);
			}
			for(int i=0;i<result.length;i++) {
				result[i].setSelected(false);
			}
			save.setText("save");
			save.setForeground(Color.black);
		}
		
		if(e.getSource()==back) {
			this.setVisible(false);
			WelcomePage p=new WelcomePage();
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
