
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Company {
	
	private ShippingEntity shipping = new ShippingEntity();
	private static StockEntity stock = new StockEntity();
	

	public List<Product> getProductInformation() {//Get the list of product from the xml file
		
		List<Product> arrayProduct = new ArrayList<Product>();
		
		String filepath = ".\\src\\Product.xml";//Where is the file
		File fXmlFile = new File(filepath);//Create a file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			NodeList nList = doc.getElementsByTagName("product");
		
			for (int temp = 0; temp < nList.getLength(); temp++) {//Go through all the document
				Node nNode = nList.item(temp);	
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					
					String name;
					double price;
					int stock;
					UUID id;

					/*Retrieve all common characteristics of a product*/
					name = eElement.getElementsByTagName("name").item(0).getTextContent();
					price = Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()); ;
					stock = Integer.parseInt(eElement.getElementsByTagName("stock").item(0).getTextContent());
					id = UUID.fromString(eElement.getElementsByTagName("id").item(0).getTextContent());	
					
					Product product = new Product(id,name,price,stock);
					arrayProduct.add(product);
					
				}
			}
		    } catch (Exception e) {
		    	e.printStackTrace();
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
