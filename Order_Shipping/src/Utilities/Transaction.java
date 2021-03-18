package Utilities;

	
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
	
	private Customer customer;
	private Timestamp datePurchase;
	private List<Item> CompanyProducts = new ArrayList<Item>();
	private double totalPrice;
	
	
	public Transaction(Customer customer, Timestamp datePurchase, List<Item> companyProducts, double totalPrice) {
		super();
		this.customer = customer;
		this.datePurchase = datePurchase;
		CompanyProducts = companyProducts;
		this.totalPrice = totalPrice;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Timestamp getDatePurchase() {
		return datePurchase;
	}
	public void setDatePurchase(Timestamp datePurchase) {
		this.datePurchase = datePurchase;
	}
	public List<Item> getCompanyProducts() {
		return CompanyProducts;
	}
	public void setCompanyProducts(List<Item> companyProducts) {
		CompanyProducts = companyProducts;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
