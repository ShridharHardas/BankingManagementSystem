package MainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class BankingApp {
	private static String url="jdbc:mysql:///bankapp";
	private static String user="root";
	private static String password="@sHRI1188";

	public static void main(String[] args) {
		try
		{
		Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			Connection conn=DriverManager.getConnection(url,user,password);
			Scanner sc=new Scanner(System.in);
			Account account=new Account(conn, sc);
			UserInfo user=new UserInfo(conn, sc);
			AccountManager ac=new AccountManager(conn, sc);
			String email;
			long account_number;
			while(true)
			{
				System.out.println("**************Bank Mangament System**********************");
				System.out.println("1.Register");
				System.out.println("2.Login");
				System.out.println("3.Exit");
				System.out.println("Enter Your Choice code:");
				int choice=sc.nextInt();
				switch(choice)
				{
				case 1:
					user.Register();
					break;
				case 2:
					email=user.Login();
					if(email!=null)
					{
						System.out.println("User loged in!!");
						if(!account.Account_exit(email))
						{
							System.out.println("1.Open your Bank account:");
							System.out.println("2.Exit");
							if(sc.nextInt()==1)
							{
								account_number=account.open_Account();
								System.out.println("Created Account Succefully!");
								System.out.println("Your Account Number"+account_number);
							}
							else
							{
								break;
							}
							
						}
						account_number=account.getAccountNumber(email);
						int choice2=0;
						while(choice2!=5)
						{
							System.out.println("1.creadit Money");
							System.out.println("2.Debit Money");
							System.out.println("3.Transfer  Money");
							System.out.println("4.Check Money");
							System.out.println("5.ExitLog Out");
							System.out.println("Enter Your choice");
							int choice3=sc.nextInt();
							switch(choice3)
							{
							case 1:
								ac.Credited_Money(account_number);
								break;
							case 2:
								ac.debit_Money(account_number);
								break;
							case 3:
								ac.transfer_money(account_number);
								break;
							case 4:
								ac.getbalance(account_number);
								break;
							case 5:
								System.out.println("Log Out Your Account");
								break;
							default:
								System.out.println("Enter valid choice");
								break;
								
							}
						
						}
					}
					else
					{
						System.out.println("Invalid Email and password");
					}
					
				case 3:
					System.out.println("Exit the Bank System");
					return;
					
				default:
					System.out.println("Enter the valid choice");
					break;
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
