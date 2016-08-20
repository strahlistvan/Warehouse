package main;
import java.util.ArrayList;

import data.DAO;
import entity.ProductList;

public class Main 
{
	public static void main(String[] args)  
	{
		System.out.println("Select all data");
		DAO db = new DAO();
		ProductList list = db.selectAllProduct();
		list.print();
		
		ArrayList<String> SkuList = db.selectAllSku();
		
		for (int i=0; i<SkuList.size(); ++i)
			System.out.println(SkuList.get(i)+" count: "+list.getQuantity(SkuList.get(i)));
	
		list.getSalePrice("asdf");
	}

}
