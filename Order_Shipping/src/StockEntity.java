
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class StockEntity {
	
	private List<Product> entityProducts = new ArrayList<Product>();
	
	public StockEntity() {
		GetStockFromFile();
	}
	
	public List<Product> getStock(){
		return entityProducts;
	}
	
	public void GetStockFromFile() {
		//read from the file 
		
		if(entityProducts.size()>0) {
			entityProducts.clear();
		}
		
		String filepath = ".\\src\\Product_stock.xml";//Where is the file
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
					int stock;
					UUID id;

					/*Retrieve all common characteristics of a product*/
					name = eElement.getElementsByTagName("name").item(0).getTextContent();
					stock = Integer.parseInt(eElement.getElementsByTagName("stock").item(0).getTextContent());
					id = UUID.fromString(eElement.getElementsByTagName("id").item(0).getTextContent());	
					
					Product product = new Product(id,name,-1,stock);
					entityProducts.add(product);
					
				}
			}
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }	
	}
	
	public void modifyStockForProduct(Product p, List<Product> productList) {
		//write in the file
		System.out.println("We modify the stock information for the product "+p.getName());
		
		//Modify the list
		for(int i=0 ;i<productList.size(); i++) {
			System.out.println(productList.get(i).getID().toString()+" and product is "+ p.getID().toString());
			if(p.getID().compareTo(productList.get(i).getID()) == 0) {//If found the product inside the list
				//Update the list
				int newStock = productList.get(i).getStock()-1;
				System.out.println("New stock is "+newStock);
				productList.get(i).setStock(newStock);
				entityProducts.get(i).setStock(newStock);
				System.out.println("entityProducts.get(i).setStock(newStock)= "+ entityProducts.get(i).getStock());
				break;
			}
		}
		
		//Rewrite the file
		try{
    		File file = new File(".\\src\\Product_stock.xml");//Where is the file to edit	
    		file.delete();//Delete the file
    		
    		String filepath = ".\\src\\Product_stock.xml";//Where we want to create the file
    		File fic = new File(filepath);//Create a file
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = (Document) docBuilder.newDocument();
			
			// root elements
			Element rootElement = doc.createElement("products");
			doc.appendChild(rootElement);
			
			for(int i=0 ; i<entityProducts.size(); i++) {//Go through the list of product
				
				// product elements
				Element product = doc.createElement("product");
				rootElement.appendChild(product);
				
				// identifier elements
				Element identifier = doc.createElement("id");
				identifier.appendChild(doc.createTextNode(entityProducts.get(i).getID().toString()));
				product.appendChild(identifier);

				// name elements
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(entityProducts.get(i).getName()));
				product.appendChild(name);

				// stock elements
				Element stock = doc.createElement("stock");
				System.out.println("Here "+ entityProducts.get(i).getStock());
				stock.appendChild(doc.createTextNode(String.valueOf(entityProducts.get(i).getStock())));
				product.appendChild(stock);
			}


			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			
		}catch (ParserConfigurationException pce) {
	        pce.printStackTrace();
	    }catch (TransformerException tfe) {
	        tfe.printStackTrace();
	    }
	}

}
