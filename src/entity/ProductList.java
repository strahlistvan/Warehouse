package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The class contains all the selected Product
 * It has several methods to get important data to CSV report
 * 
 * @author Istvan
 */
public class ProductList 
{
	private ArrayList<Product> productList;
	
	public ProductList() { }
	
	/**
	 * Constructor
	 * @param ArrayList< Product > productList
	 */
	public ProductList(ArrayList<Product> productList)
	{
		this.productList = productList;
	}

	/**
	 * Give a list of Stock Keeping Units (SKU) which are currently in inventory
	 * @return ArrayList < String > SkuList
	 */
	public ArrayList<String> getAllSku()
	{
		ArrayList<String> result = new ArrayList<String>();
		for (Product prod: this.productList)
		{
			String sku = prod.getSKU();
			if (sku!=null && !result.contains(sku))
				result.add(sku);
		}
		return result;
	}
	
	/**
	 * Return the count of SKU in the whole warehouse (in all locations)
	 * @param SKU - Stock Keeping Unit (String)
	 * @return Integer quantity 
	 */
	public Integer getQuantity(String SKU)
	{
		if (SKU == null)
			return 0;
		
		Integer countOfSKU = 0;
		for (Product prod: productList)
		{
			if (SKU.equals(prod.getSKU()))
				++countOfSKU;
		}
		return countOfSKU;
	}	
	
	/** Return a Map about SKU counts per warehouse
	 *  Key = Warehouse Location 
	 *  Value = How many SKU are in the 'Key' location
	 * @param SKU - Stock Keeping Unit (String)
	 * @return HashMap < String, Integer > countPerWarehouse
	 */ 
	public HashMap<String, Integer> getQuantityPerWarehouse(String SKU)
	{
		if (SKU == null)
			return null;
		
		HashMap<String, Integer> countPerWh = new HashMap<String, Integer>();
		for (Product prod: this.productList)
		{
			if (SKU.equals(prod.getSKU()))
			{
				String WhKey = prod.getWarehouseLocation();
				if (countPerWh.containsKey(WhKey))
				{
					int oldVal = countPerWh.get(WhKey);
					countPerWh.put(WhKey, oldVal+1);
				}
				else
				{
					countPerWh.put(WhKey, 1);
				}
			}
		}
		return countPerWh;
	}
	
	/**
	 * How many days ago was the given date?
	 * @param Date date
	 * @return Integer days
	 */
	private static Long howManyDaysBefore(Date date)
	{
		Date today = new Date();
		Long diff = (today.getTime() - date.getTime()) / (1000*60*60*24);
	//	System.out.println(date+" was "+diff+" days ago");
		return diff;
	}
	
	/**
	 * Calculate the sale price of the given SKU 
	 * (it depends on the item's retail price)
	 * @param SKU String
	 * @return Double sale price
	 */
	public Double getSalePrice(String SKU)
	{
		if (SKU == null)
			return null;
		if (getQuantity(SKU) == 0)
			return null;
		
		Long maxDays = 0L;
		Double retPrice = 0.0;
		for (Product prod: this.productList)
		{
			if (SKU.equals(prod.getSKU()))
			{
				retPrice = prod.getRetailPrice();
				//System.out.println(SKU+"'s retail price: "+prod.getRetailPrice());
				Long days = ProductList.howManyDaysBefore(prod.getItemReceivedDate());
				if (days > maxDays)
					maxDays = days;
			}
		}
		
		if (maxDays == 0L)
		{
			System.out.println("There is no "+SKU+" SKU in database.");
			return null;
		}
		
		//System.out.println(SKU+" oldest stock: "+maxDays+" old");
		if (maxDays < 7)
			return (0.65*retPrice);
		else if (maxDays <= 13)
			return (0.5*retPrice);
		else if (maxDays <= 28)
			return (0.33*retPrice);
		else if (maxDays <= 50)
			return (0.25*retPrice);
		else
			return (0.195*retPrice);
	}
	
	/**
	 * Return the image URL of item
	 * @param SKU
	 * @return String Image Url
	 */
	public String getImageUrl(String SKU)
	{
		return String.format("http://testImageUrl.excercise/%s.png", SKU);
	}
	
	/**
	 * Return the bar code of item
	 * @param SKU
	 * @return
	 */
	public String getBarCode(String SKU)
	{
		if (SKU == null)
			return null;
		
		for (Product prod : this.productList)
		{
			if (SKU.equals(prod.getSKU()))
				return prod.getBarcode();
		}
		System.out.println("\nThere is no "+SKU+" SKU in database.");
		return null;
	}
	
	/**
	 * Return all locations where we can find the item 
	 * @param SKU String
	 * @return HashSet < String > List of warehouses
	 */
	public HashSet<String> getWarehouseSet(String SKU)
	{
		if (SKU == null)
			return null;
		
		HashSet<String> resultSet = new HashSet<String>();
		for (Product prod : this.productList)
		{
			if (SKU.equals(prod.getSKU()))
				resultSet.add(prod.getWarehouseLocation());
		}
		if (resultSet.isEmpty())
			System.out.println("\nThere is no "+SKU+" SKU in database.");
		
		return resultSet;
	}
	
	public ArrayList<Product> getproductList() 
	{
		return productList;
	}

	public void setproductList(ArrayList<Product> productList) 
	{
		this.productList = productList;
	}
	
	public void print()
	{
		for (Product prod : this.productList)
		{
			System.out.println(prod);
		}
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (Product prod : this.productList)
		{
			builder.append(prod.toString());
		}
		return builder.toString();
	}
}
