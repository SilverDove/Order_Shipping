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
	
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getStock() {
		return stock;
	}
	
	public String toString() {
		return "\n Product's name: "+ name +"\n Price: "+ price +"\n Available stock: "+ stock;
	}
	
	public void setStock(int newStock) {
		this.stock = newStock;
	}

}