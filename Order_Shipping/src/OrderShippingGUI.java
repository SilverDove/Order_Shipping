import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class OrderShippingGUI implements ActionListener{
	/*GUI*/
	private JFrame frame;//Window of the shop
	//Create elements for first window
	private JPanel productPanelAvailable = new JPanel(new BorderLayout());//Panel which contains the list of available product
	private  JList<String> productList; //list of available product	
	private  JButton AddButton = new JButton ("Add to the Shopping Cart");
	private  JButton GoShoppingCart = new JButton ("Access Shopping Cart");
	
	//Create elements second window
	private JPanel shoppingCartPanel = new JPanel(new GridLayout(1,2));//Main Panel
	/*Contents of customerPanel(1,1)*/
	private JPanel OrderFormPanel = new JPanel(new GridLayout(6,1));//Panel which contains the space where to log in
			/*Contents of customerPanelSearch(1,1)*/
	private JPanel OrderFormPanelFirstName = new JPanel(new BorderLayout());//Panel which contains elements to indicate First Name
			/*Contents of customerPanelSearch(2,1)*/
	private JPanel OrderFormPanelLastName = new JPanel(new BorderLayout());//Panel which contains elements to indicate Last Name
			/*Contents of customerPanelSearch(3,1)*/
	private JPanel OrderFormPanelAddress = new JPanel(new BorderLayout());//Panel which contains elements to indicate Address
		/*Contents of customerPanelSearch(4,1)*/
	private JPanel OrderFormPanelEmail = new JPanel(new BorderLayout());//Panel which contains elements to indicate Email
		/*Contents of customerPanelSearch(5,1)*/
	private JPanel OrderFormPanelPhoneNumber = new JPanel(new BorderLayout());//Panel which contains elements to indicate Phone number
		/*Contents of customerPanelSearch(5,1)*/
	private JButton orderButton = new JButton ("Order");//Button to check if customer exists or not
		/*Contents of customerPanel(1,2)*/
	private JPanel billPanelInfo = new JPanel(new BorderLayout());//Panel which contains information about the product
			/*Contents of customerPanelInfo*/
	private JLabel billInfoLabel = new JLabel();//Description of the product
	
	//JTextField
	private JTextField customerFirstName = new JTextField(20);
	private JTextField customerLastName = new JTextField(20);
	private JTextField customerAddress = new JTextField(20);
	private JTextField customerEmail = new JTextField(20);
	private JTextField customerPhoneNumber = new JTextField(20);
	
	//Create elements last window
	private JPanel ValidationPanel = new JPanel(new BorderLayout());//Validation purchase
	
	/*Classes*/
	private StockEntity stock;
	private Company company;
	private ShippingEntity shippingEntity;
	
	/*Variables*/
	private Scanner scan = new Scanner(System.in);
	List<Product> ProductsFromCompany = new ArrayList<Product>();
	List<Product> ProductShoppingCart = new ArrayList<Product>();
	List<Product> AvailableProductFromCompany = new ArrayList<Product>();
	List <Integer> numberItems = new ArrayList<Integer>();
	
	
	private OrderShippingGUI() {//Graphical User Interface of the Store
		//Initialize entities
		stock = new StockEntity();
		company = new Company();
		shippingEntity = new ShippingEntity();
		
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
		
		orderButton.setActionCommand("Order");
		orderButton.addActionListener((java.awt.event.ActionListener) this);
	    
	    ListofProduct();
	}
	
	private void ListofProduct() {
		
		//Configure screen
		productPanelAvailable.add(new JLabel("Available products"), BorderLayout.NORTH);//Add text to Panel
		frame.add(productPanelAvailable);
		
		//Get products
		DefaultListModel<String> list=GetListProduct();
		productList = new JList<String>(list);
		
		//Update contents of the panel
		productPanelAvailable.removeAll();//Remove all elements from productPanelAvailable to be empty
		
		productPanelAvailable.add(new JLabel("Available products"), BorderLayout.NORTH);//Add a text to the Panel
		productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollPane = new JScrollPane(productList);//Add a Scroll Bar
		productPanelAvailable.add(scrollPane, BorderLayout.CENTER);//Add a Scroll Bar to the Panel
		productPanelAvailable.add(AddButton, BorderLayout.SOUTH);//Add the Add Button to the Panel
		if(ProductShoppingCart.size()>0) {
			productPanelAvailable.add(GoShoppingCart,BorderLayout.EAST);//Add the Go to the Shopping Cart Panel
		}
		frame.add(productPanelAvailable);
		
		productPanelAvailable.repaint();//tell a component to repaint itself.
		productPanelAvailable.revalidate();// tell the layout manager to reset based on the new component list
	}
	
	private void ShoppingCart() {
		productPanelAvailable.setVisible(false);
		
		/*Order form area*/
		OrderFormPanelFirstName.add(new JLabel("First name"), BorderLayout.NORTH);//Add text to Panel
		OrderFormPanelFirstName.add(customerFirstName, BorderLayout.CENTER);//Add Text field to Panel
		OrderFormPanel.add(OrderFormPanelFirstName);
		
		OrderFormPanelLastName.add(new JLabel("Last name"), BorderLayout.NORTH);//Add text to Panel
		OrderFormPanelLastName.add(customerLastName, BorderLayout.CENTER);//Add Text field to Panel
		OrderFormPanel.add(OrderFormPanelLastName);
		
		OrderFormPanelAddress.add(new JLabel("Address"), BorderLayout.NORTH);//Add text to Panel
		OrderFormPanelAddress.add(customerAddress, BorderLayout.CENTER);//Add Text field to Panel
		OrderFormPanel.add(OrderFormPanelAddress);
		
		OrderFormPanelEmail.add(new JLabel("Email"), BorderLayout.NORTH);//Add text to Panel
		OrderFormPanelEmail.add(customerEmail, BorderLayout.CENTER);//Add Text field to Panel
		OrderFormPanel.add(OrderFormPanelEmail);
		
		OrderFormPanelPhoneNumber.add(new JLabel("PhoneNumber"), BorderLayout.NORTH);//Add text to Panel
		OrderFormPanelPhoneNumber.add(customerPhoneNumber, BorderLayout.CENTER);//Add Text field to Panel
		OrderFormPanel.add(OrderFormPanelPhoneNumber);
		
		OrderFormPanel.add(orderButton);//Add Search Button to Panel
		shoppingCartPanel.add(OrderFormPanel);
			/*Bill information area*/
		billPanelInfo.add(new JLabel("Bill Information"), BorderLayout.NORTH);//Add text to Panel
		billPanelInfo.add(billInfoLabel, BorderLayout.CENTER);//Add Label to Panel
		shoppingCartPanel.add(billPanelInfo);
		//TODO: Display information of the bill
		
		frame.add(shoppingCartPanel);
		
		shoppingCartPanel.repaint();//tell a component to repaint itself.
		shoppingCartPanel.revalidate();// tell the layout manager to reset based on the new component list
	}
	
	private void ValidationMessage() {
		shoppingCartPanel.setVisible(false);
		
		/*validation area*/
		ValidationPanel.add(new JLabel("Thank you! Your order has been taken into account"), BorderLayout.CENTER);
		frame.add(ValidationPanel);

	}
	
	private DefaultListModel<String> GetListProduct(){//Get list of Product to display in a JList
		ProductsFromCompany = company.getCompanyProducts();//Get the list of product from the store
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
			ShoppingCart();
		break;
		
		case "Order":
			//TODO: save information + change window
			
			//Update the Screen
			ValidationMessage();
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
						stock.modifyStockForProduct(p , ProductsFromCompany);
						System.out.println("1");
						break;
						
					}else {//the product is not already added
						//add the product in the Shopping Cart
						ProductShoppingCart.add(p);//Add the product into the Shopping Cart
						numberItems.add(1);	
						//Refresh the stock
						stock.modifyStockForProduct(p, ProductsFromCompany);
						System.out.println("2");
						break;
						
					}
				}	
			}else {//First element to add into the list
				//add the product in the Shopping Cart
				ProductShoppingCart.add(p);//Add the product into the Shopping Cart
				numberItems.add(1);
				//Refresh the stock
				stock.modifyStockForProduct(p, ProductsFromCompany);
				System.out.println("3");
			}
			company.modifyProductStock();
		}
		
	}

	public static void main(String[] args) {
		new OrderShippingGUI();//run the Graphical User Interface
	}
	
	

}
