/**
 * Illustrating BANK MANAGEMENT SYSTEM TO CREATE BANK ACCOUNT ADD ACCOUNT DETAILS TO DATABASE AND PRINT DATA OF ONE OR ALL ACCOUNT USING DATA ACCESS OBJECT
 * PERFORMING MONEY DEPOSIT AND MONEY WITHDRAWING FROM ACCOUNT BALANCE
 * By Renu
 */

package com.slot2jdbc;
import java.sql.*;
public class bankingdao {
	
	Connection con = null;
	
	public void connect() throws Exception {
		
		//Getting Connection with DataBase
		Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/anudipdeeptech","root","96430");
	
	}
	
	//Register Account
	public int registerCustomer(bankingCustomer b1) throws Exception {
		
		String username = b1.cname;
		String quer2 = "select * from banking where cname='"+username+"'";
		Statement st = con.createStatement();
	    ResultSet rs = st.executeQuery(quer2);
	    
	    if(rs.next()) {
	    	return -1;
	    	
	    }
	    else {
	    	
	         String query = "insert into banking(cname,cpassword,cphone,caccbal) values(?,?,?,?)";
		
		     PreparedStatement pst = con.prepareStatement(query);
		     pst.setString(1,b1.cname);
		     pst.setString(2, b1.cpassword);
		     pst.setString(3, b1.cphone);
		     pst.setInt(4, b1.caccbal);
		     int count = pst.executeUpdate();
		
		     return count;
		
		}
	}
	
	public int login(String username, String pwd)throws Exception{
		
		//Fetching the Account details by Username
		String query = "Select * from banking where cname= '"+username+"'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		if(rs.next()) {
			
			//Fetching the Password from DataBase
			String cpwd = rs.getString(3);
		    
			//Matching the DataBase Password with Entered Password
		    if(cpwd.equals(pwd)) {
			int eid = rs.getInt(1);
			//If Login success we can return cid
			return eid;
			
		    }
		    else {
			return 0;
			}
		    }
		    else {
			return -1;
		    }
		
     	}
	
	public int withdraw(int cid, int amount)throws Exception {
		
		//Getting Account Details  is Based on cid 
		String query = "Select * from banking where cid ="+cid;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.next();
		
		   
		//Fetching Account Balance
	    int accbal = rs.getInt(5);
		//If Account Balance is greater than Withdraw Amount we can allow to Withdraw
		if(accbal>amount) {
		accbal -= amount;
		String query2 = "Update banking set caccbal="+accbal+" where cid="+cid;
		//Updating the Accbalance after Withdraw
		Statement st2 = con.createStatement();
		int res = st2.executeUpdate(query2);
		return accbal;
		}
		else {
			
			 return -1;
				
			}
			
		}


    public int deposit(int cid, int amount)throws Exception{
		
	        //Depositing
		String query = "select * from banking where cid="+cid;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		if(rs.next()) {
			
			int accbal = rs.getInt(5);
			//Adding Amount to Account Balance
			accbal+=amount;
			
			//Update Account Balance
			String query2 = "Update banking set caccbal="+accbal+" where cid= "+cid;
			PreparedStatement pst = con.prepareStatement(query2);
			pst.executeUpdate();
			return accbal;
		    }
	     	else {
			return -1;
		    }
		
        }
        
    public int checkBalance(int cid) throws Exception{
	
	//Checking Account Balance
	String query = "select * from banking where cid ="+cid;
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery(query);
	rs.next();
	int accbal = rs.getInt(5);
	return accbal;
}

    public int pinChange(int cid, String oldPin,String newPin) throws Exception{
	
	//Getting Details of the User
	String query = "select * from banking where cid ="+cid;
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery(query);
	rs.next();
	String pwd = rs.getString(3);
	
	//Matching Present Password to Update New Password 
	if(pwd.equals(oldPin)) {
	
		String query2 = "Update banking set cpassword="+newPin+" where cid="+cid;
		PreparedStatement pst = con.prepareStatement(query2);
		int count = pst.executeUpdate();
		return count;
	}
	else {
		return -1;
	}

	
}
}













	

	


