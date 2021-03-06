
public class Product {
	
	protected String name;
	protected double price;
	protected int stock;
	protected String id;
	
	public Product(String id, String name, double price, int stock) {
		this.id=id;
		this.name=name;
		this.price=price;
		this.stock=stock;
	}
	
	public String getID() {
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