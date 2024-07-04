package code.netjava;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class Main1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mini_project","root","sneha");
			Statement st=con.createStatement();
		
			while(true) {
				System.out.println("welcome to MenstruEasy!\nmenu: \n1.insert new date \n2.view clg faciities \n3.view wallet \n4.display history \n5.check env impact \n6.exit");
				//wallet me we can keep an option of if she wanna see transaction history and then use trans_history table and show it 
				System.out.println("enter choice: ");
				int ch = sc.nextInt();
			
				switch(ch) {
			
				case 1:
					System.out.println("enter user id: ");
					int id = sc.nextInt();
					System.out.println("enter date of cycle (yyyy-mm-dd): ");
					String date = sc.next();
					System.out.println("enter blood flow (light/medium/heavy): ");
					String flow = sc.next();
					//if else to check
					if(flow.equalsIgnoreCase("light")|| flow.equalsIgnoreCase("medium")|| flow.equalsIgnoreCase("heavy")) {
						System.out.println("enter pain level (1-5): ");
						int d1 = sc.nextInt();
						if(d1==1||d1==2||d1==3||d1==4||d1==5) {
							System.out.println("enter no. of days of cycle: ");
							int d2 = sc.nextInt();
							
							int  result = st.executeUpdate(String.format("insert into user values(%d,'%s','%s',%d,%d)", id,date,flow,d1,d2));
							System.out.println("inserted successfully");
							System.out.println(result+ " row affected");
						}
						else {
							System.out.println("enter a valid pain level!");
							System.out.println();
							break;
						}
					}
					else {
						System.out.println("enter a valid blood flow!");
						System.out.println();
						break;
					}
					System.out.println();
					break;
				
				 case 2:
				    ResultSet rs=st.executeQuery("select * from clg_facilities");

					while(rs.next()) {
						System.out.println(String.format("Machine Id: %d\nLocation: %s\nNo of pads: %d\n", rs.getInt(1),rs.getString(2),rs.getInt(3)));
					}
					break;
				
				case 3:
					System.out.println("what would you like to check?: \n1.transaction history \n2.balance");
					int choice = sc.nextInt();
					
					if(choice==1) {
						//transaction history
						rs=st.executeQuery("select * from trans_history order by trans_date desc");

						while(rs.next()) {
							java.util.Date date1 = rs.getDate(2);
							SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
			                String d3 = dateFormat.format(date1);
							System.out.println(String.format("Transaction date: %s\nTransaction amt: %d\n",d3,rs.getInt(3)));
						}
					}
					else if(choice==2) {
						rs = st.executeQuery("select * from wallet order by date_of_trans desc limit 1");
						while(rs.next()) {
							System.out.println(String.format("Balance: %d\n", rs.getInt(3)));
						}
					}
					else {
						System.out.println("enter a valid option\n");
					}
					break;
				
				case 4:
					
					rs=st.executeQuery("select * from user order by date_of_cycle desc");

					while(rs.next()) {
						System.out.println(String.format("date: %s\nblood flow: %s\npain level: %d\nno. of days of cycle: %d",rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)));
						System.out.println("*******************************************************************************************");
						System.out.println();
					}
					System.out.println();
					break;
				
				case 5:
					rs=st.executeQuery("select * from wastage");

					while(rs.next()) {
						System.out.println(String.format("Machine Id: %d\nPads used: %d\nCarbon emmision: %f\n", rs.getInt(1),rs.getInt(2),rs.getFloat(3)));
					}
					break;
					
				
				case 6:
					System.exit(0);
					con.close();
				}
			}
		}
		catch(SQLException | ClassNotFoundException e) {
			System.out.println(e);
		}
		
	}
	
}
