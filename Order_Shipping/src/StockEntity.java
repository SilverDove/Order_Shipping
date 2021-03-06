
public class StockEntity {
	
	public static int[] getStock() {
		//read from the file
		int[] arr = {0,1,5,10,12};
		return arr;
	}
	
	public void modifyStockForProduct(Product product) {
		//write in the file
		System.out.println("We modify the stock information for the product "+product.getName());	
	}

}
