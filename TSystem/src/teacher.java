import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java .sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class teacher extends JFrame implements ActionListener{
	
	Connection con;
	Statement st;
	PreparedStatement stp;
	 
	JLabel message;
	JButton first,second,third,all,back;
	
	String teacherName;
	
	public teacher(String name){
		teacherName=name;
		
		
		message=new JLabel("Hi Mr "+name);
		Font font1 = new Font("Arial", Font.CENTER_BASELINE, 20);
		message.setFont(font1);
		
		first =new JButton("First Secondary");
		second =new JButton("Second Secondary");
		third =new JButton("Third Secondary");
		all =new JButton("All Students");
		back =new JButton("Back<---");
		
		this.setLayout(null);

		message.setBounds(200,30,200,50);
		first.setBounds(30,110,150,30);
		second.setBounds(200,110,150,30);
		third.setBounds(365,110,150,30);
		all.setBounds(160,170,150,30);
		back.setBounds(190,220,100,30);
		
		add(message);
		add(first);
		add(second);
		add(third);
		add(all);
		add(back);
		
		    this.setVisible(true);
			this.setSize(600,400);
			this.setLocationRelativeTo(null);
			this.setTitle("Teacher");
			
	first.addActionListener(this);	
	second.addActionListener(this);	
	third.addActionListener(this);	
	all.addActionListener(this);	
	back.addActionListener(this);	
	
	
	
			
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==first) {
			this.setVisible(false);
			show sh=new show("First Secondary",teacherName);
				
		}
		
		if(e.getSource()==second) {
			this.setVisible(false);
			show sh=new show("Second Secondary",teacherName);
			
		}
		
		if(e.getSource()==third) {
			this.setVisible(false);
			show sh=new show("Third Secondary",teacherName);
			
		}
		
		
		if(e.getSource()==all) {
			displayall();
			
		}
		
		if(e.getSource()==back) {
		this.setVisible(false);
		WelcomePage p=new WelcomePage();	
	}
	
}
	
	
	public void displayall() {
		db();
		try {
			
			JFrame frame=new JFrame();
			
			 LocalDateTime now = LocalDateTime.now(); 
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
			   	
			int count = 0;
			st=con.createStatement();
			ResultSet r=st.executeQuery("select count (*) as count from studentInfo stu join subStudent sub on stu.firstName||' '||stu.lastName = sub.studentName where stu.teacherName='"+teacherName+"'");
			if(r.next()) {
				count=r.getInt(1);
			}
			JLabel text=new JLabel("\n\t\t Now in the the date and time "+dtf.format(now)+ "\n\t\t We have in our center  "+ count+" student(s)\n\t\t BY details:\n\n\n");
			
			
			
			ResultSet r1=st.executeQuery("select * from studentInfo stInfo join subStudent subSt on stInfo.firstName||' '||stInfo.lastName = subST.studentName where stInfo.teacherName='"+teacherName+"' order by stInfo.ID");
			ResultSet r2=con.createStatement().executeQuery("select*from subteacher where teacherName='"+teacherName+"'");
			ResultSetMetaData reMeta1=r1.getMetaData();
			ResultSetMetaData reMeta2=r2.getMetaData();
			
			String []columnName= {};
			int again=0;
			int again1=4;
			int countSubject=0;
			int subCount=0;
			while(r2.next()) {
				for(int i=2;i<=8;i++) {
					if(r2.getInt(i)==1) {
						countSubject=countSubject+2; //for subject and it time
						subCount++;
					}
			}
				columnName=new String[countSubject+4];
				columnName[0]=reMeta1.getColumnName(1);
				columnName[1]=reMeta1.getColumnName(7);
				columnName[2]=reMeta1.getColumnName(4);
				columnName[3]=reMeta1.getColumnName(5);
				
				for(int i=2;i<=8;i++) {
					if(r2.getInt(i)==1) {
						columnName[again1+again]=reMeta2.getColumnName(i);
						columnName[again1+1+again]="Time"+reMeta2.getColumnName(i);
						again=again+2;
						
					}	
					}
			}
			
			ResultSet re = null;
			if (subCount == 2) {
			    re = con.createStatement().executeQuery("SELECT stInfo.id, subSt.studentName, stInfo.phone, stInfo.stage, subSt." + columnName[4] + ", subSt." + columnName[5] + ", subSt." + columnName[6] + ", subSt." + columnName[7] + " FROM studentInfo stInfo JOIN subStudent subSt ON stInfo.firstName || ' ' || stInfo.lastName = subSt.studentName WHERE stInfo.teacherName = '" + teacherName + "' ORDER BY stInfo.ID");
			}
			if (subCount == 1) {
			    re = con.createStatement().executeQuery("SELECT stInfo.id, subSt.studentName, stInfo.phone, stInfo.stage, subSt." + columnName[4] + ", subSt." + columnName[5] + " FROM studentInfo stInfo JOIN subStudent subSt ON stInfo.firstName || ' ' || stInfo.lastName = subSt.studentName WHERE stInfo.teacherName = '" + teacherName + "' ORDER BY stInfo.ID");
			}
			
			
				ArrayList<Object[]> list=new ArrayList<>();
				
				ResultSetMetaData reMetaData=re.getMetaData();
				
 			while(re.next()) {
 				int subject[] = new int [subCount];
				String time[]=new String [subCount];
				
				 int id=re.getInt(1);
					String name1=re.getString(2);
					String phone=re.getString(3);
					String stage=re.getString(4);
				
					int x=0;
				for(int i=5;i<reMetaData.getColumnCount();i=i+2) {
                   subject[x]=re.getInt(i);
                   if(re.getString(i+1)==null) {
                   time[x]="0";
                   }
                   else {
                	   time[x]=re.getString(i+1);
                   }
                  x++;
				}
					
					
					
					Object []row=new Object[8];
	
				row[0]=id;
				row[1]=name1;
				row[2]=phone;
				row[3]=stage;
				row[4]= subject[0];
				row[5]=time[0];
				row[6]= subject[1];
     			row[7]=time[1];
				
				  
					list.add(row);
					
				}
 			
			
			Object[][] o=new Object[list.size()][];
			list.toArray(o);
			DefaultTableModel model = new DefaultTableModel(o, columnName);
			JTable table = new JTable(model);
			JScrollPane scrollPane = new JScrollPane(table);

			
			JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		     centerPanel.add(text);
				
				frame.setLayout(new BorderLayout());
				frame.add(centerPanel,BorderLayout.NORTH);
				frame.add(scrollPane,BorderLayout.CENTER);
				frame.setSize(960,300);
				frame.setLocationRelativeTo(null);
		     frame.setVisible(true);

			
			con.close();
		} catch (SQLException e1) {System.out.println(e1);}
		
		
		
	}
	
public void db() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			   con=DriverManager.getConnection(
					   "jdbc:oracle:thin:@localhost:1521:xe","test","test"); 
	
			
		}catch(Exception ex) {System.out.println(ex);}
			
	}

}
