
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
	
	public static int[] getStock() {
		//read from the file
		int[] arr = {0,1,5,10,12};
		return arr;
	}
	
	public void modifyStockForProduct(Product p, List<Product> productList) {
		//write in the file
		System.out.println("We modify the stock information for the product "+p.getName());
		
		//Modify the list
		for(int i=0 ;i<productList.size(); i++) {
			if(p.getID().compareTo(productList.get(i).getID()) == 0) {//If found the product inside the list
				//Update the list
				productList.get(i).setStock(productList.get(i).getStock()-1);
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
			
			for(int i=0 ; i<productList.size(); i++) {//Go through the list of product
				
				// product elements
				Element product = doc.createElement("product");
				rootElement.appendChild(product);
				
				// identifier elements
				Element identifier = doc.createElement("id");
				identifier.appendChild(doc.createTextNode(productList.get(i).getID().toString()));
				product.appendChild(identifier);

				// name elements
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(productList.get(i).getName()));
				product.appendChild(name);

				// stock elements
				Element stock = doc.createElement("stock");
				stock.appendChild(doc.createTextNode(String.valueOf(productList.get(i).getStock())));
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
