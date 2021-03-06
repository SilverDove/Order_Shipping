import java.util.ArrayList;
import java.util.List;

public class Company {
	
	private ShippingEntity shipping = new ShippingEntity();
	private static StockEntity stock = new StockEntity();
	
	
	public static List<Product> getProductInformation() {
		//TODO: read from file
		List<Product> arrayProduct = new ArrayList<Product>();
		String[][] arr = {{"1","item 1","20.00"}, {"2","item 2","5.35"}, {"3","item 3","10.58"}, {"4","item 4","8.30"}, {"5","item 5","200.99"}};//Information about company product
		int[] arr2 = stock.getStock();//Get stock for each product
		for(int i=0; i<arr.length;i++) {
			Product product = new Product(arr[i][0],arr[i][1],Double.parseDouble(arr[i][2]),arr2[i]);
			arrayProduct.add(product);
		}
		return arrayProduct;
	}
	
	public void writeBillInformation() {
		//write into file
	}
	
	public void modifyProductStock() {
		//modify the product stock by calling the function from StockEntity
		
	}

}
