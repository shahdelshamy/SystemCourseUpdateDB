import javax.swing.JFrame;


import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


import javax.swing.*;

public class studentInfo extends JFrame implements ActionListener  {
	
	JLabel hello,firstName,lastName,phoneNumber,stage,subject,message;
	JTextField FText,LText,phoneText;
	
	JComboBox<String> stages;
	JLabel [] time;
    JComboBox<String>[] hours;
    JCheckBox[] sujects;
  
	
	JButton save;
	JButton back;
	JButton reset;
	
	Connection con;
	Statement st;
	PreparedStatement pst,pst1;
	
	String teacherName;
	String[] columnNames;
	int countSubjects=0;
	
	private int saveInt;
	
	 public studentInfo() {
		 
	 }
	
	  public studentInfo(String name1,String teacherNamecons){
		 teacherName=teacherNamecons;
		  
		Font font1 = new Font("Arial", Font.CENTER_BASELINE, 20);
      	Font font2 = new Font("Arial", Font.CENTER_BASELINE, 14);
      	Font font3 = new Font("Arial", Font.HANGING_BASELINE, 15);

	    hello=new JLabel("Hello "+name1);
	    hello.setFont(font1);
	    message=new JLabel("<html>Note:<br/>The chemistry is Saturday and Tuesday<br/>The biology is Sunday and Wednesday</html>");
	    message.setForeground(Color.red);  
	    message.setFont(font3);
	    
	    firstName=new JLabel("FirstName: *");
	    firstName.setFont(font2);
	    lastName=new JLabel("LastName: *");
	    lastName.setFont(font2);
		phoneNumber=new JLabel("PhoneNumber: *");
		phoneNumber.setFont(font2);
		stage=new JLabel("Stage: *");
		stage.setFont(font2);
		subject=new JLabel("Subjects: *");
		subject.setFont(font2);
		
		FText=new JTextField();
		LText=new JTextField();
		phoneText=new JTextField(); 
		
		save=new JButton("Save");
		reset=new JButton("Reset");
		back=new JButton("Back <---");
		
		stages = new JComboBox<>(new String[] { "Choose the stage--V","First Secondary", "Second Secondary", "Third Secondary" }); 
		
		sujects=new JCheckBox[7];
		time=new JLabel[7];
		hours=new JComboBox[7];
		
		
		
		  this.setLayout(null);
		
		db();
		//WelcomePage we=new WelcomePage();
		//teacherName=we.assistantText.getText();
		
		
		try {
			
			ResultSet re = con.createStatement().executeQuery("SELECT * FROM subteacher WHERE teacherName = '" + teacherName + "' AND (araby = 1 OR ENGLISH = 1 OR FRENCH = 1 OR CHEMISTRY = 1 OR BIOLOGY = 1 OR PHYSICS = 1 OR MATH = 1)");

			ResultSetMetaData reMeta = re.getMetaData();
			int countColumn = reMeta.getColumnCount();
			 columnNames = new String[countColumn];

			for (int i = 0; i < countColumn; i++) {
			    columnNames[i] = reMeta.getColumnName(i + 1);
			}

			int x=0;
			
			while (re.next()) {
			   for(int i=0;i<7;i++) {
			        int value = re.getInt(columnNames[x+1]);
			       
			        if (value == 1) {
			        		sujects[countSubjects]=new JCheckBox(columnNames[x+1]);
			        		sujects[countSubjects].setBounds((110*countSubjects)+50,300,100,30);
			        		this.add(sujects[countSubjects]);
			        	  sujects[countSubjects].addActionListener(this);
			        		
			        		
			        		time[countSubjects]=new JLabel("TIME "+columnNames[i+1]+":");
							 time[countSubjects].setBounds((280*countSubjects)+10,350,100,30);
							 this.add(time[countSubjects]);			 
							 time[countSubjects].setVisible(false);
					
							 hours[countSubjects]=new JComboBox();
							 
							 int View=0;		 
					
								
								 if(countSubjects==0) {
									 hours[countSubjects].addItem("Choose The Time--V");
								 hours[countSubjects].addItem("Saturday And Tuesday At "+(countSubjects+1));
								 hours[countSubjects].addItem("Saturday And Tuesday At "+(countSubjects+2));
								 View=1;
							 }
								 else if(countSubjects==1) {
									 hours[countSubjects].addItem("Choose The Time--V");
									 hours[countSubjects].addItem("Sunday And Wenesday At "+(countSubjects));
									 hours[countSubjects].addItem("Sunday And Wenesday At "+(countSubjects+1));
									 View=1;
								 } 
								 else if(countSubjects==2) {
									 hours[countSubjects].addItem("Choose The Time--V");
									 hours[countSubjects].addItem("Monday And Thursday At "+(countSubjects-1));
									 hours[countSubjects].addItem("Monday And Thursday At "+(countSubjects));
									 View=1;
								 } 
								 
					 
								
							 
							 if(View==1) {
								 hours[countSubjects].setBounds((270*countSubjects)+120,350,160,30);
								 this.add(hours[countSubjects]);			 
								 hours[countSubjects].setVisible(false);
							 }
							 countSubjects++;
							 
			    }
			        x++;
			}
			}
		}catch(Exception ex) {System.out.println(ex);}
		
		
		
		
		
	    
	  hello.setBounds(250,0,150,60); 
	  firstName.setBounds(20, 45, 100, 50);
	  lastName.setBounds(20, 92, 100, 50);
	  phoneNumber.setBounds(9, 140,125, 50);
	  message.setBounds(310,50,300,150);
	  
	  FText.setBounds(130,55,150,25);
	  LText.setBounds(130,105,150,25);
	  phoneText.setBounds(130,150,150,25);
	  stage.setBounds(30, 200, 100, 50);
	  stages.setBounds(125, 210, 150, 30);
	  
	  
	  subject.setBounds(20,250, 150, 50);
	  save.setBounds(330,475,75,25);
	  back.setBounds(80,475,100,25);
	  reset.setBounds(210,475,75,25);
	   
	   add(hello);
	   add(firstName);
	   add(lastName);
	   add(phoneNumber);
	   add(stage);
	   add(subject);
	   add(stages);
	   add(FText);
	   add(LText);
	   add(phoneText);
	   add(stages);

		add(save);
		add(back);
		add(message);
		add(reset);
		   
		message.setVisible(false);
		
		if(teacherName.equals("mahmed helal")) {
			message.setVisible(true);
		}

	    save.addActionListener(this);
	    back.addActionListener(this);
	    reset.addActionListener(this);
	    stages.addActionListener(this);
		
	    for(int i=0;i<countSubjects;i++) {
	    	sujects[i].addActionListener(this);
	    }
	    
	    
		   this.setVisible(true);
			this.setSize(650,600);
			this.setLocationRelativeTo(null);
			this.setTitle("Student Information");
		
		if(countSubjects==3) {
			this.setSize(850,600);
		}
			
			
		
	}

	  
	  
	  
	public void actionPerformed(ActionEvent e) {
		
		 int check=0,check1 = 0;
		int stageSelected=0;
		for(int i=0;i<countSubjects;i++) {
			if(e.getSource()==sujects[i]) {
			if(sujects[i].isSelected() && !stages.getSelectedItem().equals("Choose the stage--V") ) {
		       if( stages.getSelectedItem().equals("First Secondary")) {
		    	   
		          } 
		       
		       else if(stages.getSelectedItem().equals("Second Secondary")) {
			 hours[i].removeAllItems();
			 hours[i].addItem("Choose The Time--V");
			 if(i==0) {	
				 hours[i].addItem("Saturday And Tuesday At "+(i+3));
				 hours[i].addItem("Saturday And Tuesday At "+(i+4));
				
			 }
				 else if(i==1) {
					 hours[i].addItem("Sunday And Wenesday At "+(i+2));
					 hours[i].addItem("Sunday And Wenesday At "+(i+3));
					
				 } 
				 else if(i==2) {
					 hours[i].addItem("Monday And Thursday At "+(i+1));
					 hours[i].addItem("Monday And Thursday At "+(i+2));
					 
				 } 
		     
		 }
		       else if(stages.getSelectedItem().equals("Third Secondary")) {
					 hours[i].removeAllItems();
					 hours[i].addItem("Choose The Time--V");
					 if(i==0) {
						 hours[i].addItem("Saturday And Tuesday At "+(i+6));
						 hours[i].addItem("Saturday And Tuesday At "+(i+7));
						 
					 }
						 else if(i==1) {
							 hours[i].addItem("Sunday And Wenesday At "+(i+5));
							 hours[i].addItem("Sunday And Wenesday At "+(i+6));
							
						 } 
						 else if(i==2) {
							 hours[i].addItem("Monday And Thursday At "+(i+4));
							 hours[i].addItem("Monday And Thursday At "+(i+5));
							 
						 } 
				     
				 }
		       
		        stageSelected=1;
		        time[i].setVisible(true);
			      hours[i].setVisible(true);  
			      
			}
			
		        
		 
		 else { 
			 if(sujects[i].isSelected() && stages.getSelectedItem().equals("Choose the stage--V")) {
				 int option=JOptionPane.showConfirmDialog(null,"Please select a stage");
				 time[i].setVisible(false);
				 hours[i].setVisible(false);
				 sujects[i].setSelected(false);
				break;
		      }
			 
		}	
		
			
		}
		}
		
		if(e.getSource()==save) {
			 if(!FText.getText().isEmpty() && !LText.getText().isEmpty() && !phoneText.getText().isEmpty()) {
				 
				 for(int i=0;i<countSubjects;i++) {
					 
					// System.out.println(hours[i].getSelectedItem());
					// System.out.println(sujects[i].isSelected());
					 
			          if(stages.getSelectedItem().equals("Choose the stage--V")) {
				      sujects[i].setSelected(false);
				      check=1;
				      }
			          if(hours[i].getSelectedItem().equals("Choose The Time--V") && sujects[i].isSelected()) {
			        	  int option1=JOptionPane.showConfirmDialog(null, "Pleae choose a time");
				           break;
			          }
			      }
			       if(check==1 ) {
			           int option1=JOptionPane.showConfirmDialog(null, "Pleae select a stage");
			           
		            } 
				 

		  db();
		  int max = 0;
			try {
				String sql="insert into studentinfo values (?,?,?,?,?,?)";
				pst=con.prepareStatement(sql);
				
				ResultSet re=con.createStatement().executeQuery("select max(id) from studentinfo");
				while(re.next()) {
					max=re.getInt(1);
				}
				max=max+1;
				
				pst.setInt(1, max);
				pst.setString(2,FText.getText());
				pst.setString(3,LText.getText());
				pst.setInt(4,Integer.parseInt(phoneText.getText()));
				pst.setString(6,teacherName);
				Object selectedValue = stages.getSelectedItem();

				if (!selectedValue.equals("Choose the stage--V")) {
				    String selectedString = selectedValue.toString();
				    pst.setString(5,selectedString);
				}
				
				
				String sqlSt="?";
				for(int x=0;x<15;x++) {
					if(x>0)
					sqlSt+=",?";
				}
				//System.out.println(sqlSt);
				
				String sql1="insert into subStudent values ("+sqlSt+")";
				pst1=con.prepareStatement(sql1);
				
				ResultSet re1=con.createStatement().executeQuery("select*from subStudent");
				ResultSetMetaData re2=re1.getMetaData();
				
				
				
				
				String fullName=FText.getText()+" "+LText.getText(); 
				pst1.setString(1, fullName);
				
				String selectedString = null;
				
	for(int i=0;i<countSubjects;i++) {
		if(sujects[i].isSelected()){
			String columnName=sujects[i].getText();
			Object Value = hours[i].getSelectedItem();
		for(int j=2;j<=15;j=j+2) {	
							if(columnName.equals(re2.getColumnName(j))) {
								if (!Value.equals("Choose The Time--V")) {
								    selectedString = Value.toString();
								    pst1.setInt(j,1);
								    pst1.setString(j+1,selectedString);	
								   if(i>0 && sujects[i-1].isSelected()) {
											Object Value1 = hours[i-1].getSelectedItem();
							     		    pst1.setInt((j-2),1);
							     		    pst1.setString((j-1),Value1.toString());	 
							     		
							         }
								   
								   
								}
		
							}
							else {
								
								pst1.setInt(j,0);
							pst1.setString(j+1,null);}
						}
			
		
		}else {}
		         
		         
	}
				
				System.out.println(pst.executeUpdate());
				System.out.println(pst1.executeUpdate());

				 saveInt=1;
				
				
				con.close();
			}catch(Exception ex) {System.out.println(ex);}
			
			if(saveInt==1) {
				save.setText("saved");
				save.setForeground(Color.red);
			    int option=JOptionPane.showConfirmDialog(null, "The studentID is "+max+"\n **save**");        
			}
		
			}
		else {
       int option=JOptionPane.showConfirmDialog(null, "Something is uncorrect,Please check");        
	 }
	 
		}
	 
		
		if(e.getSource()==back) {
			this.setVisible(false);
			WelcomePage w=new WelcomePage();
		}
		
		if(e.getSource()==reset) {
			FText.setText("");
			LText.setText("");
			phoneText.setText("");
			stages.setSelectedItem("Choose the stage--V");
			for(int i=0;i<countSubjects;i++) {
				if(stages.getSelectedItem().equals("Choose the stage--V")) {
				sujects[i].setSelected(false);
				 time[i].setVisible(false);
			      hours[i].setVisible(false); 
			}
			}
			save.setText("Save");
			save.setForeground(Color.black);
			
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
