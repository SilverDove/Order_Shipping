import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderShippingFacade {
	private Company company = new Company();
	private StockEntity stock = new StockEntity();
	
	private Scanner scan = new Scanner(System.in);
	List<Product> ProductsFromCompany = new ArrayList<Product>();
	List<Product> ProductShoppingCart = new ArrayList<Product>();
	
	public OrderShippingFacade() {
		
		String yORn;
		do {
			int choice=-1;
			do {//Select products
				System.out.println("\nSelect the product you want to add into your Shopping Cart.");
				ViewListProduct();
				choice = scan.nextInt();	
			}while(choice<0 || choice>ProductsFromCompany.size());
			
			AddProductToTheShoppingCart(choice);
			System.out.println("\nDo you want to add another product?(y/n)");
			yORn= scan.next();
		}while(yORn.charAt(0) != 'n');
		//If user wants to pay
		ViewBill();
		OrderForm();
		
	}
	
	public void ViewListProduct() {
		
		System.out.println("Available Products:");
		if(ProductsFromCompany.isEmpty()) {//If there are products inside, remove them
			ProductsFromCompany.clear();
		}
		
		ProductsFromCompany = company.getProductInformation();
		
		for(int i=0; i<ProductsFromCompany.size(); i++) {
			if(ProductsFromCompany.get(i).stock != 0) {//If the product is available
				System.out.println(i+") "+ProductsFromCompany.get(i).toString());
			}
		}
	}
	
	public void AddProductToTheShoppingCart(int index) {
		Product currentProduct = new Product(ProductsFromCompany.get(index).id, ProductsFromCompany.get(index).name, ProductsFromCompany.get(index).price, ProductsFromCompany.get(index).stock);
		if(currentProduct.stock!=0) {//If the item is available
			ProductShoppingCart.add(ProductsFromCompany.get(index));//Add the product into the Shopping Cart
			//Refresh the stock
			stock.modifyStockForProduct(currentProduct);
		}
	}
	
	public void OrderForm() {
		
		
	}
	
	public void ViewBill() {
		//Limit the number of digits print
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		
		int counter=0;
		double subtotal=0.0;
		double TaxRate = 6.5;
		double discount=0.0;
		double salesTaxes=0.0;
		double totalPrice=0.0;
		double deliveryCharges=10;
		System.out.println("--- Bill Information ---");
		for(int i=0; i<ProductShoppingCart.size(); i++) {
			counter=0;
			for(int j=0; j<ProductShoppingCart.size();j++ ) {
				if(ProductShoppingCart.get(i).id.equals(ProductShoppingCart.get(j).id)) {//If the user want to buy more than one a specific product
					counter++;
				}
			}
			System.out.println("Quantity:"+counter);
			System.out.println("Desciption:"+ProductShoppingCart.get(i).name);
			System.out.println("Unit Price:"+ProductShoppingCart.get(i).price);
			System.out.println("Amount:"+numberFormat.format(ProductShoppingCart.get(i).price*counter));
			System.out.println("---------------------------");
			subtotal=subtotal+(ProductShoppingCart.get(i).price*counter);
		}
		System.out.println("Subtotal: "+numberFormat.format(subtotal));
		System.out.println("Discount: "+discount);
		System.out.println("Tax Rate: "+TaxRate+"%");
		salesTaxes = (TaxRate*subtotal)/100.0;
		System.out.println("Sales Taxes "+TaxRate+"%: "+ numberFormat.format(salesTaxes));
		System.out.println("Delivery Charges "+ deliveryCharges);
		totalPrice = subtotal + deliveryCharges + salesTaxes;
		System.out.println("Total Price :"+numberFormat.format(totalPrice)+"€");
	}
	
	public void acknowledgementMessage() {
		System.out.println("Thank your for your purchase! Your items will be delivred soon.");
	}
	
	public static void main(String[] args) {
		new OrderShippingFacade();
	}

}
