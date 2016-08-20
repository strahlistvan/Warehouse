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
		{
			String mySKU = SkuList.get(i);
			System.out.println(mySKU+" count: "+list.getQuantity(mySKU));
			System.out.println(mySKU+" sale price: "+list.getSalePrice(mySKU));
			System.out.println(list.getImageUrl(mySKU));
			System.out.println(list.getBarCode(mySKU));
			System.out.println(list.getWarehouseSet(mySKU));
		}
	}

}
