package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
		System.out.println(date+" was "+diff+" days ago");
		return diff;
	}
	
	public Integer getSalePrice(String SKU)
	{
		if (SKU == null)
			return null;
		
		Integer salePrice = 0;
		
		for (Product prod: this.productList)
		{
			ProductList.howManyDaysBefore(prod.getItemReceivedDate());
			if (SKU.equals(prod.getSKU()))
			{
				
			}
			
		}
		
		return salePrice;
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
