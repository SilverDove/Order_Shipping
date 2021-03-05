
public class Product {
	
	protected String name;
	protected double price;
	protected int stock;
	
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
	
	public void updateStock(int newStock) {//update the stock of the product
		this.stock = newStock;
	}

}