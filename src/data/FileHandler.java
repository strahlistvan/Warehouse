package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class FileHandler 
{
	private static boolean isValidDatabaseProperties(HashMap<String, String> properties)
	{
		String[] validKeys = {"type", "port", "host", "dbname", "user", "password"};
		String[] requiredKeys = {"host", "dbname", "user", "password"};
		int foundRequiredKeyCount = 0;
		for (String key : properties.keySet())
		{
			//check if all keys are valid
			if (Arrays.asList(validKeys).indexOf(key) == -1)
			{
				System.out.println("Invalid connection property: "+key);
				return false;
			}
			
			if (Arrays.asList(requiredKeys).indexOf(key) != -1)
			{
				++foundRequiredKeyCount;
			}
		}
		
		//check if all required keys in the map
		if (requiredKeys.length != foundRequiredKeyCount)
		{
			System.out.println("Missing required connection property!");
			System.out.println("host, dbname, user and password are required");
			return false;
		}
		
		//check if the port value is numeric
		if (!properties.get("port").matches("\\d+"))
		{
			System.out.println("Invalid port number: "+properties.get("port"));
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
			System.out.println("adfasdf: "+array.length);
			if (array.length !=2)
			{
				System.out.println("Error with "+filePath+" file format.");
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
			System.out.println("Error: "+filePath+" properties file has invalid format!");
		}
		
		return result;
	}
}
