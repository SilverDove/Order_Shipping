package Utilities;
import java.util.UUID;

public class Product {
	
	private String name;
	private double price;
	private int stock;
	private UUID id;
	
	public Product(UUID id, String name, double price, int stock) {
		this.id=id;
		this.name=name;
		this.price=price;
		this.stock=stock;
	}
	
	public UUID getID() {
		return id;
	}
	
	
	public String getName() {//returns product's name
		return name;
	}
	
	public double getPrice() {//returns product's price
		return price;
	}
	
	public int getStock() {//returns product's stock
		return stock;
	}
	
	public String toString() {//returns product's information
		return "\n Product's name: "+ name +"\n Price: "+ price +"\n Available stock: "+ stock;
	}
	
	public void setStock(int newStock) {//update the stock of the product
		this.stock = newStock;
	}

}