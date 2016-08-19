package entity;

import java.util.Date;

public class Product 
{
	private String SKU = "";
	private Date itemReceivedDate = null;
	private String warehouseLocation = "";
	private String barcode = "";
	private Integer retailPrice;
	
	public Product() { }
	
	public Product(String SKU, Date itemReceivedDate, String warehouseLocation, String barcode, Integer retailPrice) 
	{
		this.SKU = SKU;
		this.itemReceivedDate = itemReceivedDate;
		this.warehouseLocation = warehouseLocation;
		this.barcode = barcode;
		this.retailPrice = retailPrice;
	}
	
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public Date getItemReceivedDate() {
		return itemReceivedDate;
	}
	public void setItemReceivedDate(Date itemReceivedDate) {
		this.itemReceivedDate = itemReceivedDate;
	}
	public String getWarehouseLocation() {
		return warehouseLocation;
	}
	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Integer getretailPrice() {
		return retailPrice;
	}
	public void setretailPrice(Integer retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("{SKU: '"+this.SKU+"',");
		result.append("itemReceivedDate: '"+itemReceivedDate.toString()+"', ");
		result.append("warehouseLocation: '"+warehouseLocation+"', ");
		result.append("barcode: '"+barcode+"', ");
		result.append("retailPrice: "+retailPrice+" }\n");
		
		return result.toString();
	}
	
}
