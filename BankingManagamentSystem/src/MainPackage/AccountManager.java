package MainPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
	Connection conn;
	Scanner sc;
	ResultSet result;
	public AccountManager(Connection conn,Scanner sc) {
		this.conn=conn;
		this.sc=sc;
	}
	public void debit_Money(long account_number) throws SQLException
	{
		System.out.println("Enter the Debited Amount:");
		double amount=sc.nextDouble();
		System.out.println("Enter the Security_pin:");
		int security_pin=sc.nextInt();
		
		if(account_number!=0)
		{
			conn.setAutoCommit(false);
			String query="select * from Account where account_number=? and security_pin=?";
			PreparedStatement preparedStatement=conn.prepareStatement(query);
			preparedStatement.setLong(1, account_number);
			preparedStatement.setInt(2, security_pin);
			result=preparedStatement.executeQuery();
			if(result.next())
			{   double current_balance=result.getDouble("balance");
			if(amount>=current_balance)
			{
				String query1="update Account set balance=balance-? where account_number=? ";
				preparedStatement=conn.prepareStatement(query1);
				preparedStatement.setDouble(1, amount);
				preparedStatement.setLong(2, account_number);
				int rowaffected=preparedStatement.executeUpdate();
				if(rowaffected>0)
				{
					System.out.println("debited RS."+amount+" Succesfully!");
					conn.commit();
					conn.setAutoCommit(true);
					return;
				}
				else
				{
					System.out.println("Transcation Failed");
					conn.rollback();
					conn.setAutoCommit(true);
				}
			}
			else
			{
				System.out.println("Insufficient account balance");
			}}
		}
		else
		{
			System.out.println("Enter the valid Security_pin");
		}
	

}
	public void Credited_Money(long account_number) throws SQLException
	 {

		System.out.println("Enter the Debited Amount:");
		double amount=sc.nextDouble();
		System.out.println("Enter the Security_pin:");
		int security_pin=sc.nextInt();
		
		if(account_number!=0)
		{
			conn.setAutoCommit(false);
			String query="select * from Account where account_number=? and security_pin=?";
			PreparedStatement preparedStatement=conn.prepareStatement(query);
			preparedStatement.setLong(1, account_number);
			preparedStatement.setInt(2, security_pin);
			result=preparedStatement.executeQuery();
			if(result.next())
			{   
				String query1="update Account set balance=balance+? where account_number=? ";
				preparedStatement=conn.prepareStatement(query1);
				preparedStatement.setDouble(1, amount);
				preparedStatement.setLong(2, account_number);
				int rowaffected=preparedStatement.executeUpdate();
				if(rowaffected>0)
				{
					System.out.println("Credited RS."+amount+" Succesfully!");
					conn.commit();
					conn.setAutoCommit(true);
					return;
				}
				else
				{
					System.out.println("Transcation Failed");
					conn.rollback();
					conn.setAutoCommit(true);
				}
			}
			else
			{
				System.out.println("Induvual Security Pin ");
			}
		}
		else
		{
			System.out.println("Enter the valid Security_pin");
		}
	 }
	public void getbalance(long account_number)

	{
		System.out.println("Enter the Security Pin:");
		int security_pin=sc.nextInt();
		
		 try {
			PreparedStatement preparedStatement=conn.prepareStatement("select * from acount where account_number=? and security_pin=?");
			preparedStatement.setLong(1, account_number);
			preparedStatement.setInt(2, security_pin);
			result=preparedStatement.executeQuery();
			if(result.next())
			{
				double balance=result.getDouble("balance");
				System.out.println("Account balance:"+balance);
			}
			else
			{
				System.out.println("Invalid Security pin");
			}
		 } catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public void transfer_money(long sender_account_number)
	{
		System.out.println("Enter the Receiver Account_Number:");
		long receiver_account_number=sc.nextLong();
		System.out.println("Enter the amount:");
		double amount=sc.nextDouble();
		System.out.println("Enter the Security pin:");
		int securuty_pin=sc.nextInt();
		
		try {
			conn.setAutoCommit(false);
			PreparedStatement preparedStatement= conn.prepareStatement("select * from account where account_number=? and sequrity_pin=?");
			if(receiver_account_number!=0 && sender_account_number!=0)
			{
			
			preparedStatement.setLong(1, sender_account_number);
			preparedStatement.setInt(2, securuty_pin);
			result=preparedStatement.executeQuery();
			if(result.next())
			{
				double current_amount=result.getDouble("balance");
				if(amount>=current_amount)
				{   String s1="update Account set balance=balance-? where account_number=?";
					String r1="update Account set balance=balance+? where account_number=?";
					PreparedStatement senderpreaparedStatment=conn.prepareStatement(s1);
					PreparedStatement receiverpreaparedStatment=conn.prepareStatement(r1);
					senderpreaparedStatment.setDouble(1, amount);
					receiverpreaparedStatment.setDouble(1, amount);
					senderpreaparedStatment.setLong(2, sender_account_number);
					receiverpreaparedStatment.setLong(2, receiver_account_number);
					int row1=senderpreaparedStatment.executeUpdate();
					int row2=receiverpreaparedStatment.executeUpdate();
					if(row1>0 && row2>0)
					{
						System.out.println("Transfer amount succesfully");
						conn.commit();
						conn.setAutoCommit(true);
					}
					else
					{
						System.out.println("Transfer failed");
						conn.rollback();
						conn.setAutoCommit(false);
						
					}
					
				}
				else
				{
					System.out.println("Insuffisient account balance");
				}
			}
			else
			{
				System.out.println("Invalid Security pin");
			}
		}
		else {
			System.out.println("Enter the Valid Account Number");
		}}
			catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
}
 