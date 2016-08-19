package main;
import java.util.ArrayList;

import data.DAO;
import entity.Product;

public class Main 
{
	public static void main(String[] args)  
	{
		System.out.println("Select all data");
		DAO db = new DAO();
		ArrayList<Product> list = db.selectAll();
		
		for (int i=0; i<list.size(); ++i)
			System.out.println(list.get(i));
		
	}

}
