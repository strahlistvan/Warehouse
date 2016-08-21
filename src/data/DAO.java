package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import entity.Product;
import entity.ProductList;

/**
 * Database Access Object. We can connect with SQL server 
 * and select all the relevant data with an instance of the class.
 * 
 * @author Istvan
 *
 */
public class DAO 
{
	private Connection conn = null;
	private String connectionFile = "database_properties.csv";
	
	public DAO() 
	{
		init();
	}
	
	/**
	 * Constructor. Connect server with the data given in the parameter file.
	 * The parameter must be Tab separated CSV file with valid properties
	 * (host, user, password, dbname, type, port)
	 * @param String connectionFile -  Path and file name of the SQL properties file.
	 */
	public DAO(String connectionFile)
	{
		this.connectionFile = connectionFile;
		init();
	}
	
	/**
	 * Initialize object and establish SQL Connection
	 * (Using JDBC MySQL connector)
	 * Used in constructors.
	 */
	public void init()
	{
		String connStr = "";
		try 
		{
			HashMap <String, String> dbprop = FileHandler.readDatabaseProperties(connectionFile);
		    connStr = String.format("jdbc:%s://%s:%s/%s", dbprop.get("type"), 
					dbprop.get("host"), dbprop.get("port"), dbprop.get("dbname"));
			conn = DriverManager.getConnection(connStr, dbprop.get("user"), dbprop.get("password"));
		} 
		catch (IOException e) 
		{
			System.err.println("Error while read '"+connectionFile+"' file.");
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			System.err.println("Error while connecting SQL database: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Select all data from the join of the two tables.
	 * @return ProductList 
	 */
	public ProductList selectAllProduct()
	{
		ArrayList<Product> resultList = new ArrayList<Product>();
		String sql = "SELECT * FROM inventory INNER JOIN sku_data ON (inventory.sku = sku_data.sku) ORDER BY sku_data.sku;";
		Statement statement;

		try 
		{
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next())
			{
				String sku = rs.getString("SKU");
				Date itemRec = rs.getDate("ItemReceivedDate");
				String location = rs.getString("WarehouseLocation");
				String barcode = rs.getString("Barcode");
				Double retailPrice = rs.getDouble("RetailPrice");
				Product product = new Product(sku, itemRec, location, barcode, retailPrice);
				resultList.add(product);
			}
		} 
		catch (SQLException ex) 
		{
			System.err.println("SQL error: "+sql);
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			System.err.println("Fatal error while executing query: "+sql);
			ex.printStackTrace();
		}
		//return the list of prodcut items
		ProductList result = new ProductList(resultList);
		return result;
	}

	/**
	 * Select all item's SKU
	 * (Not used in this project)
	 * @return ArrayList < String > - list of item SKU
	 */
	public ArrayList<String> selectAllSku()
	{
		ArrayList<String> result = new ArrayList<String>();
		String sql = "SELECT SKU FROM sku_data;";
		try 
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next())
			{
				String sku = rs.getString("SKU");
				result.add(sku);
			}
		} 
		catch (SQLException ex) 
		{
			System.err.println("Error while executing query: "+sql);
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Reset the connection with other properties CSV file
	 * @param String filePath
	 */
	public void setConnectionFile(String filePath)
	{
		this.connectionFile = filePath;
		init();
	}
}
