package MainPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Account {
	private Connection conn;
	private Scanner sc;
	public Account(Connection conn,Scanner sc)
	{
		this.conn=conn;
		this.sc=sc;
	}
	ResultSet result;
	PreparedStatement preparedStatement;
	String email;
	public long open_Account()
	{   String insert="insert into Account values(?,?,?,?,?)";
		System.out.println("Enter the Account holder full Name:");
		String name=sc.next();
		System.out.println("Enter the Account balance:");
		double balance=sc.nextDouble();
		System.out.println("Enter the security_pin:");
		int pin=sc.nextInt();
		try {
			long ac_number=generateAccountNumber();
			PreparedStatement preparedStatement=conn.prepareStatement(insert);
			preparedStatement.setLong(1, ac_number);
			preparedStatement.setString(2,name);
			preparedStatement.setString(3, email);
			preparedStatement.setDouble(4, balance);
			preparedStatement.setInt(5, pin);
			int rowaffect=preparedStatement.executeUpdate();
			if(rowaffect>0)
			{
				return ac_number;
			}
			else
			{
				System.out.println("Account creation Failed");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} throw new RuntimeException("Account creation Failed");
	}
	public long getAccountNumber(String email)
	{
	try {
		PreparedStatement preparedStatement=conn.prepareStatement("select * from account where email=?");
		preparedStatement.setString(1, email);
		result=preparedStatement.executeQuery();
		if(result.next())
		{
			return result.getLong("account_number");
		}
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	} throw new RuntimeException("Account Dose Not Exit's");
	
	}
	public long generateAccountNumber() throws SQLException
	{
		Statement statement=conn.createStatement();
		try {
			result=statement.executeQuery("select Account_number from account order by account_number desc limit 1");
			if(result.next())
			{
				long account_number=result.getLong("account_number");
				return account_number+1;
			}
			return 100000;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 100000;
	}
	public boolean Account_exit(String email)
	{
		try {
			PreparedStatement preparedStatement=conn.prepareStatement("select * from Account where email_id=?");
			preparedStatement.setString(1, email);
			result=preparedStatement.executeQuery();
			if(result.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
}
