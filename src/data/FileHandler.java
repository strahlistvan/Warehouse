package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import entity.ProductList;

public class FileHandler 
{
	private static boolean isValidDatabaseProperties(HashMap<String, String> properties)
	{
		String[] validKeys = {"type", "port", "host", "dbname", "user", "password"};
		String[] requiredKeys = {"host", "dbname", "user", "password"};
			
		//if optional keys not set, put the default values
		if (!properties.containsKey("port"))
			properties.put("port", "3306");
		if (!properties.containsKey("type"))
			properties.put("type", "mysql");
		
		int foundRequiredKeyCount = 0;
		for (String key : properties.keySet())
		{
			//check if all keys are valid
			if (Arrays.asList(validKeys).indexOf(key) == -1)
			{
				System.err.println("Invalid connection property: "+key);
				return false;
			}
			//Count founded required keys
			if (Arrays.asList(requiredKeys).indexOf(key) != -1)
			{
				++foundRequiredKeyCount;
			}
		}
		
		//check if all required keys in the map
		if (requiredKeys.length != foundRequiredKeyCount)
		{
			System.err.println("Missing required connection property!");
			System.err.println("host, dbname, user and password are required");
			return false;
		}
		
		//check if the port value is numeric
		if (!properties.get("port").matches("\\d+"))
		{
			System.err.println("Invalid port number: "+properties.get("port"));
			return false;
		}		
		return true;
	}
	
	public static HashMap<String, String> readDatabaseProperties(String filePath) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		HashMap<String, String> result = new HashMap<String, String>();
		
		String line = reader.readLine();
		while (line != null)
		{
			String[] array = line.split("\t");
			if (array.length !=2)
			{
				System.err.println("Error with "+filePath+" file format.");
				reader.close();
				return null;
			}
			
			array[0] = array[0].trim().replaceAll("\"", "");
			array[1] = array[1].trim().replaceAll("\"", "");
			result.put(array[0], array[1]);
			
			line = reader.readLine();
		}
		reader.close();
		
		if (!FileHandler.isValidDatabaseProperties(result))
		{
			System.err.println("Error: "+filePath+" properties file has invalid format!");
			throw new IOException();
		}
		
		return result;
	}
	
	public static void writeProductDataToCSV(ProductList list, String filePath) throws IOException
	{
		ArrayList<String> SkuList = list.getAllSku();
		System.out.println(SkuList);
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		
		//TODO: ask about the structure
		for (String sku: SkuList)
		{
			HashSet<String> warehouses = list.getWarehouseSet(sku);
			for (String wh: warehouses)
			{
				writer.write("\""+sku+"\"\t");
				writer.write(list.getQuantity(sku)+"\t");
				writer.write(list.getSalePrice(sku)+"\t");
				writer.write("\""+list.getImageUrl(sku)+"\"\t");
				writer.write("\""+list.getBarCode(sku)+"\"\t");
				
				writer.write("\""+wh+"\"");
				writer.newLine();
			}
		}
		
		writer.close();
	}
}
