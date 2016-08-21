package main;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import data.DAO;
import data.FileHandler;
import entity.ProductList;

public class Main 
{
	public static void main(String[] args)  
	{
		System.out.println("Select all data");
		DAO db = new DAO("database_properties.csv");
		ProductList list = db.selectAllProduct();
		list.print();
		
		System.out.println(list.getQuantityPerWarehouse("SOCKS47W"));
		System.out.println(list.getQuantityPerWarehouse("SOCKS43B"));
				
		try 
		{
			HashMap<String, String> prop =FileHandler.readFtpProperties("ftp_properties.csv");
			System.out.println(prop);
			
			FileHandler.writeProductDataToCSV(list, "output"+(new Date()).getTime()+".txt");
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
