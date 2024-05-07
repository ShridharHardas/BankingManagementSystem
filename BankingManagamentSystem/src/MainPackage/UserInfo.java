package MainPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInfo {
     private Connection conn;
     private Scanner sc;
     PreparedStatement preparedStatement;
     ResultSet result;
    public UserInfo(Connection conn,Scanner sc)
    {
    	this.conn=conn;
    	this.sc=sc;
    }
     public void Register()
     {
    	 System.out.println("Enter the Full Name of user--->");
    	 String name=sc.next();
    	 System.out.println("Enter the Email Id of user--->");
    	 String email=sc.next();
    	 if(user_exit(email))
    	 {
    		 System.out.println("User Already Exits..!");
    	 }
    	 String password=sc.next();
    	 System.out.println("Enter the Password--->");
    	 
    	 try {
			PreparedStatement preparedStatement=conn.prepareStatement("insert into userInfo(Full_name,Email_Id,Password)values(?,?,?)");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
			int rowaffected=preparedStatement.executeUpdate();
			if(rowaffected>0)
			{
				System.out.println("Adding User Info Succesfully!..");
			}
			else
			{
				System.out.println("Please cheack user email and Password");
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	 
     }
     public String Login()
     {
    	 System.out.println("Login the user by using Your Email_Id and Password");
    	 System.out.println("Email:"+" ");
    	 String email=sc.next();
    	 System.out.println("Passwod:"+" ");
    	 String password=sc.next();
    	 try {
			PreparedStatement preparedStatement=conn.prepareStatement("select * from userInfo where email_id=? and password=?");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			result=preparedStatement.executeQuery();
			if(result.next())
			{
				return email;
			}
			else
			{
				return null;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	 return null;
     }
     public boolean user_exit(String email)
     {
    	 String query="select * from userInfo where email_id=?";
    	 try {
			PreparedStatement preparedStatement=conn.prepareStatement(query);
			preparedStatement.setString(1, email);
			result=preparedStatement.executeQuery();
			if(result.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			
			e.printStackTrace();
		} return false;
    	 
     }
}
