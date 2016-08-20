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

public class DAO 
{
	private Connection conn = null;
	
	public DAO() 
	{
		String connStr = "";
		try 
		{
			HashMap <String, String> dbprop = FileHandler.readDatabaseProperties("database_properties.csv");
		    connStr = String.format("jdbc:%s://%s:%s/%s", dbprop.get("type"), 
					dbprop.get("host"), dbprop.get("port"), dbprop.get("dbname"));
			conn = DriverManager.getConnection(connStr, dbprop.get("user"), dbprop.get("password"));
		} 
		catch (IOException e) 
		{
			System.err.println("Error while read 'database_properties.csv' file.");
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			System.err.println("Error while connecting SQL database: ");
			e.printStackTrace();
		}
		
	}
		
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
				Integer retailPrice = rs.getInt("RetailPrice");
				Product product = new Product(sku, itemRec, location, barcode, retailPrice);
				resultList.add(product);
			}
			
		} 
		catch (SQLException ex) 
		{
			System.out.println("SQL error: "+sql);
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			System.out.println("Fatal error while executing query: "+sql);
			ex.printStackTrace();
		}
	
		System.out.println("stopped successfully");
		
		//return the list of prodcut items
		ProductList result = new ProductList(resultList);
		return result;
	}

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
}
