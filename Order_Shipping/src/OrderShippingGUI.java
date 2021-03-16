import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class OrderShippingGUI implements ActionListener{
	/*GUI*/
	private JFrame frame;//Window of the shop
	//Create element for first window
	private JPanel productPanelAvailable = new JPanel(new BorderLayout());//Panel which contains the list of available product
	private  JList<String> productList; //list of available product	
	private  JButton AddButton = new JButton ("Add to the Shopping Cart");
	private  JButton GoShoppingCart = new JButton ("Access Shopping Cart");
	
	/*Classes*/
	private Company company = new Company();
	private StockEntity stock = new StockEntity();
	private ShippingEntity shippingEntity = new ShippingEntity();
	
	/*Variables*/
	private Scanner scan = new Scanner(System.in);
	List<Product> ProductsFromCompany = new ArrayList<Product>();
	List<Product> ProductShoppingCart = new ArrayList<Product>();
	List<Product> AvailableProductFromCompany = new ArrayList<Product>();
	List <Integer> numberItems = new ArrayList<Integer>();
	
	
	private OrderShippingGUI() {//Graphical User Interface of the Store
		
		//Create and initialize the window
		frame = new JFrame("OrderShipping");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//Get size of the screen
	    frame.setMinimumSize(screenSize);//full screen mode 
	    frame.pack();
	    frame.setVisible(true);
		
		/*Button interactions*/
		AddButton.setActionCommand("Add Product");
		AddButton.addActionListener((java.awt.event.ActionListener) this);
		
		GoShoppingCart.setActionCommand("Shopping Cart");
		GoShoppingCart.addActionListener((java.awt.event.ActionListener) this);
	    
	    ListofProduct();
	}
	
	private void ListofProduct() {
		
		//Configure screen
		productPanelAvailable.add(new JLabel("Available products"), BorderLayout.NORTH);//Add text to Panel
		frame.add(productPanelAvailable);
		
		//Get products
		DefaultListModel<String> list  =GetListProduct();
		productList = new JList<String>(list);
		
		//Update contents of the panel
		productPanelAvailable.removeAll();//Remove all elements from productPanelAvailable to be empty
		
		productPanelAvailable.add(new JLabel("Available products"), BorderLayout.NORTH);//Add a text to the Panel
		productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollPane = new JScrollPane(productList);//Add a Scroll Bar
		productPanelAvailable.add(scrollPane, BorderLayout.CENTER);//Add a Scroll Bar to the Panel
		productPanelAvailable.add(AddButton, BorderLayout.SOUTH);//Add the Add Button to the Panel
		//if(ProductShoppingCart.size()>0) {
			productPanelAvailable.add(GoShoppingCart,BorderLayout.EAST);//Add the Go to the Shopping Cart Panel
		//}
		frame.add(productPanelAvailable);
		
		productPanelAvailable.repaint();//tell a component to repaint itself.
		productPanelAvailable.revalidate();// tell the layout manager to reset based on the new component list
	}
	
	private DefaultListModel<String> GetListProduct(){//Get list of Product to display in a JList
		ProductsFromCompany = company.getProductInformation();//Get the list of product from the store
		DefaultListModel<String> l = new DefaultListModel<String>();
		int index = 0;
		
		if(AvailableProductFromCompany.size()!=0) {//If the list of available product is not empty
			AvailableProductFromCompany.clear();
		}
		
		for (int i=0; i<ProductsFromCompany.size(); i++) {//Go through the list of Products until the end
			if(ProductsFromCompany.get(i).getStock() != 0) {//If the product still have available items to buy, add it to the list to return
				l.add(index, "Name: "+ProductsFromCompany.get(i).getName()+" --------- Price: "+ProductsFromCompany.get(i).getPrice()+"--------- Stock: "+ProductsFromCompany.get(i).getStock());
				AvailableProductFromCompany.add(index, ProductsFromCompany.get(i));
				index ++;
			}
		}
		
		return l;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//Actions after the interaction with the buttons
		switch (e.getActionCommand()) {
		
		case "Add Product"://if press Select button in the Product Area, we must display add product into shopping cart
			
			int index = productList.getSelectedIndex();//Get the index of the selected product from the List
			Product currentProduct = AvailableProductFromCompany.get(index);//Get selected product according to its index

			//Update the list of product by calling add product to the shopping cart
			addProductIntoShoppingCart(currentProduct);
			
			//Update the screen
			ListofProduct();
			
		break;
		
		case "Shopping Cart"://Go to the shopping cart (next window)
			
		break;
		
		}
		
	}
	
	private void addProductIntoShoppingCart(Product p) {
		if(p.stock!=0) {//If the item is available
			if(ProductShoppingCart.size()>0) {
				for(int i=0 ; i<ProductShoppingCart.size(); i++) {//Check whether the product was already added
					if(p.id.equals(ProductShoppingCart.get(i).id)) {//If the product already exists
						//Increase the number of item
						int counter = numberItems.get(i)+1;
						numberItems.set(i, counter);
						//Refresh the stock
						stock.modifyStockForProduct(p);
						break;
						
					}else {//the product is not already added
						//add the product in the Shopping Cart
						ProductShoppingCart.add(p);//Add the product into the Shopping Cart
						numberItems.add(1);	
						//Refresh the stock
						stock.modifyStockForProduct(p);
						break;
						
					}
				}	
			}else {//First element to add into the list
				//add the product in the Shopping Cart
				ProductShoppingCart.add(p);//Add the product into the Shopping Cart
				numberItems.add(1);
				//Refresh the stock
				stock.modifyStockForProduct(p);
			}
			
		}
		
	}

	public static void main(String[] args) {
		new OrderShippingGUI();//run the Graphical User Interface
	}
	
	

}
