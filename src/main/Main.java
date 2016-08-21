package main;
import java.io.IOException;
import java.util.Date;
import data.DAO;
import data.FileHandler;
import data.FtpConnection;
import entity.ProductList;


/** Warehouse simulator program.
 * Reads a list of items from the database and outputs the latest list of stock.
 * Program using properties file to access database and FTP server.
 * Default files: database_properties.csv and ftp_properties.csv 
 * but you can use other files if add names to program's command line arguments.
 * (Tab separated CSV text files)
 * 
 * Program usage: 
 * java -jar Warehouse.jar [output csv name - OPTIONAL] [db property file - OPTIONAL] [ftp property file - OPTIONAL]
 * 
 * @author Istvan
 */
public class Main 
{
	/** main method of the program
	 * @param args String[] array
	 */
	public static void main(String[] args)  
	{
		String dbPropFile = "database_properties.csv";
		String ftpPropFile = "ftp_properties.csv";
		String csvFileName = "output_"+(new Date()).getTime()+".csv";
		
		//Command line arguments 
		if (args.length >= 1)
			csvFileName = args[0];
		if (args.length >= 2)
			dbPropFile = args[1];
		if (args.length >= 3)
			ftpPropFile = args[2];
			
		System.out.println("Database connection properties: "+dbPropFile);
		System.out.println("FTP connection properties: "+ftpPropFile);
		System.out.println("Importable CSV file name: "+csvFileName);
		
		//Select data from database
		System.out.print("\nSelect relevant data from database...");
		DAO db = new DAO(dbPropFile);
		ProductList list = db.selectAllProduct();
		System.out.println("Done!\nPrint data:");
		list.print();
		
		try 
		{
			//Write the relevant data to a CSV file
			System.out.print("\nWrite data to CSV file...");
			FileHandler.writeProductDataToCSV(list, csvFileName);
			System.out.println("Done!");
			//upload CSV to FTP server
			System.out.print("\nUpload "+csvFileName+" to FTP server...");
			FtpConnection ftp = new FtpConnection(ftpPropFile);
			ftp.uploadFile(csvFileName);
			System.out.println("Done!");
			
			System.out.println("\nProgram successfully terminated.");
			System.out.println("Press any key to exit program...");
			System.in.read();
		} 
		catch (IOException ex) 
		{
			System.err.println("Can not open: "+csvFileName);
			ex.printStackTrace();
		}
	}

}
