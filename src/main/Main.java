package main;
import java.io.IOException;
import java.util.Date;
import data.DAO;
import data.FileHandler;
import data.FtpConnection;
import entity.ProductList;

public class Main 
{
	public static void main(String[] args)  
	{
		System.out.println("Select all data");
		DAO db = new DAO("database_properties.csv");
		ProductList list = db.selectAllProduct();
		list.print();
		
		String fileName = "";
		try 
		{
			fileName = "outputs/output_"+(new Date()).getTime()+".txt";
			FileHandler.writeProductDataToCSV(list, fileName);
			FtpConnection ftp = new FtpConnection("ftp_properties.csv");
			ftp.uploadFile(fileName);
			
		} 
		catch (IOException ex) 
		{
			System.err.println("Can not open: "+fileName);
			ex.printStackTrace();
		}
	}

}
