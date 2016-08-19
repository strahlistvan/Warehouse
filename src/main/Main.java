package main;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import data.FileHandler;


public class Main {

	public static void main(String[] args)  
	{
		Connection conn;
		System.out.println("Select all data");
		try 
		{
			HashMap<String, String> dbconn = FileHandler.readDatabaseProperties("database_properties.csv");
			
			String sql = "SELECT * from inventory";
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "root", "");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next())
			{
				String str = rs.getString("sku");
				System.out.println(str);
			}
			
		} 
		catch (SQLException ex) 
		{
			System.out.println("Error with SQL Connection: "+ex);
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error while reading properties file");
			ex.printStackTrace();
		}
		
	}

}
