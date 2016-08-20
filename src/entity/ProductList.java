package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class ProductList 
{
	private ArrayList<Product> productList;
	
	public ProductList() { }
	
	public ProductList(ArrayList<Product> productList)
	{
		this.productList = productList;
	}

	public Integer getQuantity(String SKU)
	{
		HashMap<String, Integer> countOfSKU = new HashMap<String, Integer>();
		for (Product prod: productList)
		{
			if (countOfSKU.containsKey(prod.getSKU()) )
			{
				int oldSize = countOfSKU.get(prod.getSKU());
				countOfSKU.put(prod.getSKU(), oldSize+1);
			}
			else
			{
				countOfSKU.put(prod.getSKU(), 1);
			}
		}
		//return quantity:
		return (countOfSKU.containsKey(SKU))? countOfSKU.get(SKU) : 0;
	}
		
	
	private static Long howManyDaysBefore(Date date)
	{
		Date today = new Date();
		Long diff = (today.getTime() - date.getTime()) / (1000*60*60*24);
	//	System.out.println(date+" was "+diff+" days ago");
		return diff;
	}
	
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
	
	public String getImageUrl(String SKU)
	{
		return String.format("http://testImageUrl.excercise/%s.png", SKU);
	}
	
	public String getBarCode(String SKU)
	{
		if (SKU == null)
			return null;
		
		for (Product prod : this.productList)
		{
			if (SKU.equals(prod.getSKU()))
				return prod.getBarcode();
		}
		System.out.println("There is no "+SKU+" SKU in database.");
		return null;
	}
	
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
		{
			System.out.println("There is no "+SKU+" SKU in database.");
			return null;
		}
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
