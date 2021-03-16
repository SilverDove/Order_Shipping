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
	
	/*Classes*/
	private Company company = new Company();
	private StockEntity stock = new StockEntity();
	private ShippingEntity shippingEntity = new ShippingEntity();
	
	/*Variables*/
	private Scanner scan = new Scanner(System.in);
	List<Product> ProductsFromCompany = new ArrayList<Product>();
	List<Product> ProductShoppingCart = new ArrayList<Product>();
	List <Integer> numberItems = new ArrayList<Integer>();
	
	
	private OrderShippingGUI() {//Graphical User Interface of the Store
		
		//Create and initialize the window
		frame = new JFrame("OrderShipping");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//Get size of the screen
	    frame.setMinimumSize(screenSize);//full screen mode 
	    frame.pack();
	    frame.setVisible(true);
	    
	    ListofProduct();
	}
	
	private void ListofProduct() {
		/*Create element*/
		JPanel productPanelAvailable = new JPanel(new BorderLayout());//Panel which contains the list of available product
		JList<String> productList; //list of available product	
		JButton AddButton = new JButton ("Add to the Shopping Cart");
		JButton GoShoppingCart = new JButton ("Access Shopping Cart");
		
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
		productPanelAvailable.add(AddButton, BorderLayout.SOUTH);//Add the Search Button to the Panel
		frame.add(productPanelAvailable);
		
		productPanelAvailable.repaint();//tell a component to repaint itself.
		productPanelAvailable.revalidate();// tell the layout manager to reset based on the new component list
	}
	
	private DefaultListModel<String> GetListProduct(){//Get list of Product to display in a JList
		ProductsFromCompany = company.getProductInformation();//Get the list of product from the store
		DefaultListModel<String> l = new DefaultListModel<String>();
		int index = 0;
		
		for (int i=0; i<ProductsFromCompany.size(); i++) {//Go through the list of Products until the end
			if(ProductsFromCompany.get(i).getStock() != 0) {//If the product still have available items to buy, add it to the list to return
				l.add(index, "Name: "+ProductsFromCompany.get(i).getName()+" --------- Price: "+ProductsFromCompany.get(i).getPrice()+"--------- Stock: "+ProductsFromCompany.get(i).getStock());
				index ++;
			}
		}
		
		return l;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {//Actions after the interaction with the buttons
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new OrderShippingGUI();//run the Graphical User Interface
	}
	
	

}
