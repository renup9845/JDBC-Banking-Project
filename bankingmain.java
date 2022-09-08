/**
 * Illustrating BANK MANAGEMENT SYSTEM TO CREATE BANK ACCOUNT ADD ACCOUNT DETAILS TO DATABASE AND PRINT DATA OF ONE OR ALL ACCOUNT USING DATA ACCESS OBJECT
 * PERFORMING MONEY DEPOSIT AND MONEY WITHDRAWING FROM ACCOUNT BALANCE
 * By Renu
 */

package com.slot2jdbc;
import java.util.*;


public class bankingmain {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		Scanner bs = new Scanner(System.in);
		bankingdao dao = new bankingdao();
		bankingCustomer b1 = new bankingCustomer();
		
		System.out.println("\t\t-----WELCOME TO STATE BANK OF INDIA-----");
		
		System.out.println("Select Operation : \n1 Press 1 For Register Account\n2 Press 2 For Login");
		int op = bs.nextInt();
		
		switch(op) {
		
		case 1->{
			//Creating New Account
			System.out.println("Enter Customer Details to create new Account : ");
			System.out.print("Enter Customer Name : ");
			String cname = bs.next();
			System.out.print("Create Customer Password : ");
			String cpwd = bs.next();
			System.out.print("Enter Customer Phone : ");
			String cphone = bs.next();
			System.out.print("Enter Acc balance : ");
			int caccbal = bs.nextInt();
			b1.cname = cname;
			b1.cpassword = cpwd;
			b1.cphone = cphone;
			b1.caccbal = caccbal;
			
			dao.connect();
			int res=dao.registerCustomer(b1);
			if(res>0) {
				System.out.println("\t\t--Account Created--");
			}
			else {
				System.out.println("User already existed in System");
				
			}
			
		  }
		
		case 2->{
			//Login into my Account
			System.out.println("Enter Customer Details to Login : ");
			System.out.print("Enter Customer Name : ");
			String cname = bs.next();
			System.out.print("Enter Customer Password : ");
			String cpwd = bs.next();
			
			dao.connect();
			int res = dao.login(cname , cpwd);
			if(res==0) {
				System.out.println("--Ooops!!--Wrong Username or password!!!--");
				
			}
			else if(res==-1) {
				System.out.println("Account not found\n Please register yourself");
				
			}
			else {
				System.out.println("Login success!!!");
				
				//We are giving access to Withdraw or Deposit
				int op2=0;
				while(op2<5) {
				        System.out.println("Select Operation :\n1 For Withdraw \n2 For deposit \n3 For check balance \n4 For pin change \n5 For Exit");
				        op2 = bs.nextInt();
				        
				        switch(op2) {
				
				case 1->{
					
					//Withdraw Amount Method
					System.out.println("Enter amount to withdraw : ");
					int amt = bs.nextInt();
					int res2 = dao.withdraw(res,amt);
					if(res2<=0) {
						System.out.println("--Something went wrong--");
						
					}
					else {
						System.out.println("--Success!!--Withdraw done, updated balance "+res2);
					}
					
				  }
				case 2->{
					 
				  //Depositing Method
					System.out.println("Enter Amount to Deposit : ");
					int amount = bs.nextInt();
					int res2 = dao.deposit(res , amount);
					if(res2 ==-1) {
						System.out.println("Something went wrong");
						
					}
					else {
						System.out.println("Deposit Done Updated Balance is " +res2);
					}
				}
				
				case 3->{
					
				  //Balance Checking Method
					System.out.println("Your Balance is +"+dao.checkBalance(res));
					
				}
				
				case 4->{
					
				  //Pin Change Method
					System.out.println("Enter Your password");
					String pwd = bs.next();
					System.out.println("Enter New Password");
					String newPin = bs.next();
					int count = dao.pinChange(res , pwd,newPin);
					
					if(count<=0) {
						System.out.println("--Sorry!!--Password Not Matched--");
						
					}
					else {
						System.out.println("Password Updated");
					}
				  }
				  }
				
			}
			}
		  }
		  }
		
		//Closing the Scanner 
		bs.close();
		}
		
}

