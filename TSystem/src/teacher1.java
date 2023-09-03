
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.DriverManager;

import javax.swing.*;

public class teacher1 extends JFrame implements ActionListener {
	
	JLabel welcomeT,note;
	JButton studentInfo,chemistrySt,bioSt,physicsSt,chemistry3,chemistry4,bio3,bio4,physics3,physics4;
	JButton count,countChemistry3,countChemistry4,countBio3,countBio4,countPhysics3,countPhysics4;
	JButton back1;
	
	private JTextArea outputTextArea;

	
	Connection con;
	Statement st;
	PreparedStatement stp;
	
	public teacher1(String nameT){
		
		welcomeT=new JLabel("Welcome Mr "+nameT);
		Font font1 = new Font("Arial", Font.PLAIN, 20);
		welcomeT.setFont(font1);
		welcomeT.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
	
		studentInfo=new JButton("Students information");
		chemistrySt=new JButton("Chemistry Students info");
		bioSt=new JButton("Biology Students info");
		physicsSt=new JButton("Physics Students info");
		chemistry3=new JButton("Chemistry Students info group 3");
		chemistry4=new JButton("Chemistry Students info group 4");
		bio3=new JButton("Biology Students info group 3");
		bio4=new JButton("Biology Students info group 4");
		physics3=new JButton("Physics Students info group 3");
		physics4=new JButton("Physics Students info group 4");
		
		
		count=new JButton("Number Of Students");
		countChemistry3=new JButton("Number of Chemistry Students group 3");
		countChemistry4=new JButton("Number of Chemistry Students group 4");
		countBio3=new JButton("Number of Biology Students group 3");
		countBio4=new JButton("Number of Biology Students group 4");
		countPhysics3=new JButton("Number of Physics Students group 3");
		countPhysics4=new JButton("Number of Physics Students group 4");
		
		back1=new JButton("back <--");
		
		note=new JLabel("This screen for show the data once click on a button");
		note.setForeground(Color.red);
		
		this.setLayout(null);
		
		welcomeT.setBounds(150,30,200,30);
		note.setBounds(30,80,300,50);
		
		studentInfo.setBounds(20,130,220,30);
		chemistrySt.setBounds(20,170,220,30);
		bioSt.setBounds(20,210,220,30);
		physicsSt.setBounds(20,250,220,30);
		chemistry3.setBounds(20,290,220,30);
		chemistry4.setBounds(20,330,220,30);
		bio3.setBounds(20,370,220,30);
		bio4.setBounds(20,410,220,30);
		physics3.setBounds(20,450,220,30);
		physics4.setBounds(20,490,220,30);
		
		count.setBounds(260,130,250,30);
		countChemistry3.setBounds(260,170,260,30);
		countChemistry4.setBounds(260,210,260,30);
		countBio3.setBounds(260,250,250,30);
		countBio4.setBounds(260,290,250,30);
		countPhysics3.setBounds(260,330,250,30);
		countPhysics4.setBounds(260,370,250,30);
		
		back1.setBounds(360,430,100,30);
		
		add(welcomeT);
		add(note);
		add(studentInfo);
		add(chemistrySt);
		add(bioSt);
		add(physicsSt);
		add(chemistry3);
		add(chemistry4);
		add(bio3);
		add(bio4);
		add(physics3);
		add(physics4);
		
		add(count);
		add(countChemistry3);
		add(countChemistry4);
		add(countBio3);
		add(countBio4);
		add(countPhysics3);
		add(countPhysics4);
		
		add(back1);
		    this.setVisible(true);
			this.setSize(600,600);
			this.setLocationRelativeTo(null);
			this.setTitle("Teacher");
			
			
			studentInfo.addActionListener(this);
			chemistrySt.addActionListener(this);
			bioSt.addActionListener(this);
			physicsSt.addActionListener(this);
			chemistry3.addActionListener(this);
			chemistry4.addActionListener(this);
			bio3.addActionListener(this);
			bio4.addActionListener(this);
			physics3.addActionListener(this);
            physics4.addActionListener(this);
            
            count.addActionListener(this);
            countChemistry3.addActionListener(this);
            countChemistry4.addActionListener(this);
            countBio3.addActionListener(this);
            countBio4.addActionListener(this);
            countPhysics3.addActionListener(this);
            countPhysics4.addActionListener(this);
            
			
			back1.addActionListener(this);
		
			
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		db();
		
		if(e.getSource()==studentInfo) {
			
			try {
				
				String sql="select  stu.ID,name,phone,stage,chemistry,timech,biology,timebio,physics,timeph from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID order by stu.ID";
                  st=con.createStatement();
                ResultSet rs= st.executeQuery(sql);
                /*
                while (re.next()) {
                	System.out.println(re.getInt(1)+"  "+re.getString(2) +"  "+re.getString(3)+"  "+re.getString(4) +"  "+re.getInt(5) +"   "+re.getInt(6) +"   "+re.getInt(7) +"   "+re.getString(8) +"   "+re.getString(9) +"   "+re.getString(10));	
                }
                  */
                out o=new out();
                String output;
                 output="StudentID    StudentName\tPhoneNumber\t   Stage\t  Chemistry\tTimeChemistry\t    Biology\tTimeBiology\tPhysics\tTimePhysics";
                o.outputTextArea.append(output+"\n\n");
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String phone = rs.getString(3);
                    String stage = rs.getString(4);
                    int chemistry = rs.getInt(5);
                    String timech = rs.getString(6);
                    int biology = rs.getInt(7);
                    String timebio = rs.getString(8);
                    int physics = rs.getInt(9); 
                    String timeph = rs.getString(10);

                    output = "   "+id + "\t" + name + "\t" + phone + "\t" + stage + "       " + chemistry + "\t " + timech+ "\t    " + biology + "\t " +timebio+ "\t" + physics + "\t " +timeph;  
                    o.outputTextArea.append(output + "\n\n\n");
                }
                
                 
                
                con.close();  
			}catch(Exception ex ) {System.out.println(ex);}
			
		}
		
		
         else if(e.getSource()==chemistrySt) {
        	 show( "select stu.ID,name,phone,stage,chemistry,timech from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where chemistry=1  order by stu.ID","Chemistry","TimeChemistry");
		}
	

		else if(e.getSource()==bioSt) {
       	 show( "select stu.ID,name,phone,stage,biology,timebio from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where biology=1  order by stu.ID","Biology","TimeBiology");
		}
	
        else if(e.getSource()==physicsSt) {
          	 show( "select stu.ID,name,phone,stage,physics,timeph from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where physics=1  order by stu.ID","Phyics","TimePhysics");
		}
	
		
 else if(e.getSource()==chemistry3) {
	
	 show("select stu.ID,name,phone,stage,chemistry,timech from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where chemistry=1 and timech=3  order by stu.ID","Chemistry","TimeChemistry");
}


else if(e.getSource()==chemistry4) {
	 show("select stu.ID,name,phone,stage,chemistry,timech from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where chemistry=1 and timech=4  order by stu.ID","Chemistry","TimeChemistry");	
}

else if(e.getSource()==bio3) {
	 show("select stu.ID,name,phone,stage,biology,timebio from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where biology=1 and timebio=3  order by stu.ID","Biology","TimeBiology");	
}

else if(e.getSource()==bio4) {
	 show("select stu.ID,name,phone,stage,biology,timebio from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where biology=1 and timebio=4  order by stu.ID","Biology","TimeBiology");
}		
		
else if(e.getSource()==physics3) {
	 show("select stu.ID,name,phone,stage,physics,timeph from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where physics=1 and timeph=3  order by stu.ID","Phyics","TimePhysics");
}

else if(e.getSource()==physics4) {
	 show("select stu.ID,name,phone,stage,physics,timeph from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where physics=1 and timeph=4  order by stu.ID","Phyics","TimePhysics");	
}			
		
   else if(e.getSource()==count) {
	   count("select count(*) As Count from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID");
   } 
   else if(e.getSource()==countChemistry3) {
	   count("select count(*) As Count from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where chemistry=1 and timech=3 ");
   } 
   else if(e.getSource()==countChemistry4) {
	   count("select count(*) As Count from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where chemistry=1 and timech=4 ");
   } 	
   else if(e.getSource()==countBio3) {
	   count("select count(*) As Count from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where biology=1 and timebio=3 ");
   } 
   else if(e.getSource()==countBio4) {
	   count("select count(*) As Count from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where biology=1 and timebio=4 ");
   } 		
		
   else if(e.getSource()==countPhysics3) {
	   count("select count(*) As Count from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where physics=1 and timeph=3 ");
   } 
   else if(e.getSource()==countPhysics4) {
	   count("select count(*) As Count from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where physics=1 and timeph=4 ");
   } 
		
		if(e.getSource()==back1) {
			this.setVisible(false);
			WelcomePage wel=new WelcomePage();
		}
		
		
	}
	
	
public void db() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			   con=DriverManager.getConnection(
					   "jdbc:oracle:thin:@localhost:1521:xe","shahd","shahd"); 
	
			
		}catch(Exception ex) {System.out.println(ex);}
			
	}
	
public  void show(String y,String x,String z) {
	
	
	db();
	try {
		
		//String sql="select stu.ID,name,phone,stage,chemistry,timech from studentInfo stu  JOIN  subjects sub on sub.ID=stu.ID where chemistry=1 "+ y+" order by stu.ID";
          st=con.createStatement();
        ResultSet rs= st.executeQuery(y);
        /*
        while (re.next()) {
        	System.out.println(re.getInt(1)+"  "+re.getString(2) +"  "+re.getString(3)+"  "+re.getString(4) +"  "+re.getInt(5) +"   "+re.getString(6));   	
        }
          */
        out o=new out();
        String output;
         output="StudentID    StudentName\tPhoneNumber\t   Stage\t"+  x+"\t"+z;
        o.outputTextArea.append(output+"\n\n");
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String phone = rs.getString(3);
            String stage = rs.getString(4);
            int chemistry = rs.getInt(5);
            String timech = rs.getString(6);

            output = "   "+id + "\t" + name + "\t" + phone + "\t" + stage + "       " + chemistry + "\t " + timech;  
            o.outputTextArea.append(output + "\n\n\n");
        }
        
        
        con.close();  
	}catch(Exception ex ) {System.out.println(ex);}
	
	
}


public void count(String y) {
	try {
		
          st=con.createStatement();
      ResultSet re = st.executeQuery(y);
      if (re.next()) {
          int rowCount = re.getInt("Count");
          //System.out.println("The Number of Student: " + rowCount);
          int option=JOptionPane.showConfirmDialog(null,"The Number of Student: " + rowCount);
      }
        	con.close();  
	}catch(Exception ex ) {System.out.println(ex);}
}



}
