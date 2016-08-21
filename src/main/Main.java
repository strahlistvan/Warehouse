package main;
import java.io.IOException;

import data.DAO;
import data.FileHandler;
import entity.ProductList;

public class Main 
{
	public static void main(String[] args)  
	{
		System.out.println("Select all data");
		DAO db = new DAO();
		ProductList list = db.selectAllProduct();
		list.print();
		try {
			FileHandler.writeProductDataToCSV(list, "output.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
