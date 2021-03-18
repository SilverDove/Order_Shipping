
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Company {
	
	private ShippingEntity shipping = new ShippingEntity();
	private static StockEntity stock = new StockEntity();
	
	private List<Product> CompanyProducts = new ArrayList<Product>();
	
	public Company() {
		GetProductsFromFile();
		modifyProductStock();//Check that have last version according to the shipping entity
	}
	
	public List<Product> getCompanyProducts(){
		System.out.println("getCompanyProducts");
		for(int i=0; i<CompanyProducts.size(); i++) {
			System.out.println(CompanyProducts.get(i).toString());
		}
		
		return CompanyProducts;
	}

	public void GetProductsFromFile() {//Get the list of product from the xml file
		
		if(CompanyProducts.size()>0) {
			CompanyProducts.clear();
		}
		
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
					CompanyProducts.add(product);
					
				}
			}
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		
			//this.modifyProductStock();
		
	}
	
	public void writeBillInformation() {
		//write into file
	}
	
	public void modifyProductStock() {
		//modify the product stock by calling the function from StockEntity
		stock.GetStockFromFile();
		List<Product> stockInfo = stock.getStock();
		
		for(int i=0; i<CompanyProducts.size(); i++) {
			for(int j=0; j<stockInfo.size(); j++) {
				if(CompanyProducts.get(i).getID().compareTo(stockInfo.get(j).getID()) == 0) {//If find the same product
					CompanyProducts.get(i).setStock(stockInfo.get(j).getStock());
					System.out.println(stockInfo.get(j).getStock());
					break;
				}
			}
		}
		
		//Modify file
		try{
    		File file = new File(".\\src\\Product.xml");//Where is the file to edit	
    		file.delete();//Delete the file
    		
    		String filepath = ".\\src\\Product.xml";//Where we want to create the file
    		File fic = new File(filepath);//Create a file
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = (Document) docBuilder.newDocument();
			
			// root elements
			Element rootElement = doc.createElement("products");
			doc.appendChild(rootElement);
			
			for(int i=0 ; i<CompanyProducts.size(); i++) {//Go through the list of product
				
				// product elements
				Element product = doc.createElement("product");
				rootElement.appendChild(product);
				
				// identifier elements
				Element identifier = doc.createElement("id");
				identifier.appendChild(doc.createTextNode(String.valueOf(CompanyProducts.get(i).getID())));
				product.appendChild(identifier);
				
				// name elements
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(CompanyProducts.get(i).getName()));
				product.appendChild(name);

				// price elements
				Element price = doc.createElement("price");
				price.appendChild(doc.createTextNode(String.valueOf(CompanyProducts.get(i).getPrice())));
				product.appendChild(price);

				// stock elements
				Element stock = doc.createElement("stock");
				stock.appendChild(doc.createTextNode(String.valueOf(CompanyProducts.get(i).getStock())));
				product.appendChild(stock);
					
			}


			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
	}

}
