import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class show extends JFrame implements ActionListener {
	
	
	JButton search,back; 
	JTextField searchText;
	JLabel message;
	
	JComboBox<String>[]hours;
	JLabel[]subjectsName;
	
	Connection con;
	Statement st;
	
	String stage;
	String teacherName;

	
	int countSubjects;
	String hourSelected;
	String subName;
	out o;
	
	DefaultTableModel model = null;
	 JTable table;
	
	public show(String stage1,String name) {
		
		stage=stage1;
		teacherName=name;
		
      	Font font = new Font("Arial", Font.HANGING_BASELINE, 15);
		message=new JLabel("<html>Note:<br/>If you want to use the search<br/>The first two letters must be correct<html>");
		message.setForeground(Color.red);
		message.setFont(font);
		
		searchText=new JTextField(170);
		searchText.setForeground(Color.gray);
		searchText.setText("Enter Name Of The Student"); 
		
		
		searchText.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                if (searchText.getText().equals("Enter Name Of The Student")) {
	                	searchText.setText("");
	                	searchText.setForeground(Color.BLACK);
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                if (searchText.getText().isEmpty()) {
	                	searchText.setForeground(Color.GRAY);
	                	searchText.setText("Enter Name Of The Student");
	                }
	            }
	        });
		
		
		search =new JButton("Search");
		back =new JButton("Back<---");
		
		
		this.setLayout(null);
		
		subjectsName=new JLabel[7];
		hours=new JComboBox[7];
		
		try {
			int count=0;
			db();
			//String sql="select* from subStudent subSt join studentInfo stInfo on stInfo.firstName ||' '|| stInfo.lastName =subSt.studentName where stInfo.teacherName='"+teacherName+"' "  ;
			String sql="select* from subTeacher where teacherName='"+teacherName+"'";
			ResultSet re=con.createStatement().executeQuery(sql);
			ResultSetMetaData reMeta=re.getMetaData();
			while(re.next()) {
				
			for(int i=2;i<=8;i++) {	
			   if(re.getInt(reMeta.getColumnName(i))==1) {   
				   subjectsName[count]=new JLabel(reMeta.getColumnName(i));
				   hours[count]=new JComboBox();
				   hours[count].addItem("Choose the time--V");
				   count++;
			   }
			}
                countSubjects=count;
			break;
			}
			
		}catch(Exception ex) {System.out.println(ex);}
		
		
		
		for(int count=0;count<countSubjects;count++) {
			subjectsName[count].setBounds(50,(70*count)+120,100,30);
			this.add(subjectsName[count]);
			hours[count].setBounds(140,(70*count)+120,160,30);
			this.add(hours[count]);
		}

	
		 
		message.setBounds(330,0,250,150);
		searchText.setBounds(50,50,170,30);
	     search.setBounds(230,50,75,30);
	     back.setBounds(50,260,100,30);
	
              add(searchText);
              add(search);
          add(message);
              add(back);
              
              this.setVisible(true);
              this.setSize(600,400);
              this.setLocationRelativeTo(null);
              this.setTitle("Display Screen For "+stage1);
              
      
     search.addActionListener(this); 
     back.addActionListener(this); 
      for(int i=0;i<countSubjects;i++) {
    	  hours[i].addActionListener(this);
      }
     
     
     int hour=0;
		if(stage.equals("First Secondary")) {
			hour=0;
		}
		else if(stage.equals("Second Secondary")) {
			hour=2;
		}
		else if(stage.equals("Third Secondary")) {
			hour=5;
		}
		
		
	     for(int count=0;count<countSubjects;count++) {
          
         	  if(count==0) {
         	  hours[count].addItem("Saturday And Tuesday At "+(hour+1));
					 hours[count].addItem("Saturday And Tuesday At "+(hour+2));
         	  }
         	  else if(count==1) {
         		  hours[count].addItem("Sunday And Wenesday At "+(hour+1));
				 hours[count].addItem("Sunday And Wenesday At "+(hour+2));
         	  }
         	  
         	 else if(count==2) {
				 hours[count].addItem("Monday And Thursday At "+(hour+1));
				 hours[count].addItem("Monday And Thursday At "+(hour+2));
				 
			 } 
         	    	  
         }  
	  	
		
              
}
	
	public void actionPerformed(ActionEvent e) {
		db();

		for(int i=0;i<countSubjects;i++) {
	    	  if(e.getSource()==hours[i]) {
	    		   hourSelected=hours[i].getSelectedItem().toString();
	    		   subName=subjectsName[i].getText();
	    		// o=new out();
	    		//display1("select count(*) as count from studentinfo stInfo join  subStudent subSt on stInfo.firstName ||' '|| stInfo.lastName =subSt.studentName where stInfo.teachername='"+teacherName+"' and stInfo.stage='"+stage+"' and subSt."+subName+"=1");
	    		 display2("select* from  studentInfo stInfo join  subStudent subSt on stInfo.firstName ||' '|| stInfo.lastName =subSt.studentName where stInfo.teacherName='"+teacherName+"' and stInfo.stage='"+stage+"' and subSt."+subName+"=1 and subSt.time"+subName+"='"+hourSelected+"' order by stInfo.ID","Time "+subName, subName);
	    		   
	    		  
	    	  }
	      }
		
		
		
		
		if(e.getSource()==search) {
			if(!searchText.getText().equals("Enter Name Of The Student")) {
				
				try {
					
					st=con.createStatement();
					String firstChars=searchText.getText().substring(0,2);
					ResultSet re=st.executeQuery("select* from  studentInfo stInfo join  subStudent subSt on stInfo.firstName ||' '|| stInfo.lastName =subSt.studentName where stInfo.teacherName='"+teacherName+"' and stInfo.stage='"+stage+"' and subSt.studentName LIKE '" + firstChars + "%'");					
					int x=1;
					
					ArrayList<Object[]>list=new ArrayList<>();
					
					ResultSetMetaData reMeta=re.getMetaData();
					String []columns=new String[17];
					columns[0]="StudentID";
					columns[1]="Name";
					columns[2]="phone";
					columns[3]="stage";
										
					for(int i=8;i<reMeta.getColumnCount();i++) {
						columns[i-4]=reMeta.getColumnName(i);
					}
					
					
					ArrayList<Object>List=new ArrayList<>();
					
					
					while(re.next()) {
							
							int []subject=new int[7];
							String []time=new String[7];
							Object[]object=new Object[18];
							
                              x=0;   
							int id=re.getInt(1);
							String name1=re.getString(7);
							String phone=re.getString(4);
							String stage=re.getString(5);
							int again=0;
							for(int i=0;i<7;i++) {
								subject[i]=re.getInt(i+8+again);
								time[i]=re.getString(i+9+again);
								again++;
								
							}
							 again=0;
							object[0]=id;
							object[1]=name1;
							object[2]=phone;
							object[3]=stage;
							for(int i=0;i<7;i++) {
							object[i+4+again]=subject[i];
							object[i+5+again]=time[i];
							 again++;
							}
						
							
							
							list.add(object);
								
						

						
					}
					
					Object[][]data=new Object[list.size()][];
					list.toArray(data);
					table(data,columns,1,1);
					
					
					if (x==1) {
						int option = JOptionPane.showConfirmDialog(null, "this student not exist!!!");						
					}
					
					con.close();
				} catch (SQLException e1) {System.out.println(e1);}
				
				
				
			}
			
		}
		
		if(e.getSource()==back) {
			this.setVisible(false);
			teacher t=new teacher(teacherName);
		}
		
}
		
	
	public void display1(String sql) {
		db();
		
		try {
			
			 LocalDateTime now = LocalDateTime.now(); 
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); 
			
			st=con.createStatement();
			ResultSet re=st.executeQuery(sql);
			int count = 0;
			if(re.next()) {
				count=re.getInt(1);
			}
	
			o.outputTextArea.append("\n\t\t Now in the the date and time "+dtf.format(now)+"\n\t\t We have "+ count+" student(s) in "+stage+"\n\t\t BY details:\n\n\n");
			
		} catch (SQLException e) {System.out.println(e);		}
				
		
		
	}
	
	
	
	public void display2(String quary,String out,String sub) {
		try {
			
			db();
			
			
			 
			 int count=0;
			st=con.createStatement();
			ResultSet re=st.executeQuery(quary);
			ResultSet re1=con.createStatement().executeQuery("select count(*) from  studentInfo stInfo join  subStudent subSt on stInfo.firstName ||' '|| stInfo.lastName =subSt.studentName where stInfo.teacherName='"+teacherName+"' and stInfo.stage='"+stage+"' and subSt."+subName+"=1 and subSt.time"+subName+"='"+hourSelected+"'");
			while(re1.next()) {
				 count=re1.getInt(1);
			}
			
	        ArrayList<Object> dataList = new ArrayList<>();

			 
			String[] columns={"StudentID","StudentName","PhoneNumber","Stage",sub,out}; 
			
			//o.outputTextArea.append("StudentID \t StudentName \t    PhoneNumber      Stage \t"+out +"\n\n");
			
			ResultSetMetaData reMeta=re.getMetaData();
			int columnNumber = -1;
			
		  int columnCount = reMeta.getColumnCount();
		    for (int i = 1; i <= columnCount; i++) {
		        String columnName = reMeta.getColumnName(i);
		        if (columnName.equalsIgnoreCase(sub)) {
		            columnNumber = i;
		            break;
		        }
		    }
		    int id=0;
		    String name1="";
		    String phone="";
		    String stage="";
		    int subject1=0;
		    String time1 = "";
		    
		    int empty=0;
		    int x=0;
			while(re.next()) {
				x=0;
		     	 id=re.getInt(1);
				 name1=re.getString(7);
				 phone=re.getString(4);
				 stage=re.getString(5);
				subject1=re.getInt(sub);
				 time1=re.getString(columnNumber+1);
				 empty=1;
		
			    
				 
				//o.outputTextArea.append("  "+id+" \t"+name1+"\t   "+phone+"      "+stage+"     "+subject1+"\t "+time1);
				//o.outputTextArea.append("\n\n");
				 Object[] row = {id, name1, phone, stage, subject1, time1};

		            dataList.add(row);
		            x++;
				  
			}
			

			 
			 
			 Object[][] data = new Object[dataList.size()][];
		        dataList.toArray(data);
			
			
			
			
			
			table(data,columns,count,empty);
	        
	        
	        
			con.close();
		} catch (SQLException e) {System.out.println(e);} 	
	

	}
	
	
	
public void db() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			   con=DriverManager.getConnection(
					   "jdbc:oracle:thin:@localhost:1521:xe","test","test"); 
	
			
		}catch(Exception ex) {System.out.println(ex);}
			
	}

public void table(Object[][]arr,String []columns,int count,int empty) {
	 
	 
	 JFrame frame = new JFrame("The OutPut");
	 
	 LocalDateTime now = LocalDateTime.now(); 
     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); 
	
	 
	 JLabel text=new JLabel("<html><br/>Now in the the date and time "+dtf.format(now)+"<br/>We have "+ count+" student(s) in "+stage+"<br/>BY details:<br/><br/></html>");
	
	 
	 model = new DefaultTableModel(arr, columns);
	 table = new JTable(model);
	 
	 if(empty==0) {
			model.setRowCount(0);
		}
	
	 JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
     centerPanel.add(text);
		
		JScrollPane scrollPane = new JScrollPane(table);
		frame.setLayout(new BorderLayout());
		frame.add(centerPanel,BorderLayout.NORTH);
		frame.add(scrollPane,BorderLayout.CENTER);
		frame.setSize(960,300);
		frame.setLocationRelativeTo(null);
     frame.setVisible(true);
}
	
}