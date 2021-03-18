package Utilities;

import java.util.UUID;

public class Item {
	
	private UUID productID;
	private String productName;
	private int numberItems;
	private double productPrice;
	
	public Item(UUID productID, String productName, int numberItems, double productPrice) {
		this.productID = productID;
		this.productName = productName;
		this.numberItems = numberItems;
		this.productPrice = productPrice;
	}
	
	public UUID getProducID() {
		return productID;
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getNumberItems() {
		return numberItems;
	}
	public void setNumberItems(int numberItems) {
		this.numberItems = numberItems;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
}
