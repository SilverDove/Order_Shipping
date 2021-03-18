package ShippingEntity;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.xml.sax.SAXException;

import Utilities.Customer;
import Utilities.Product;
import Utilities.Transaction;

public class ShippingEntity {
	
	public void UpdateShippingDetails(Transaction detailTransaction) {
		//TODO: write into file (metagang's part)
		System.out.println("Writting into the shipping.xml ....");
		
		try {
			
			String filepath = ".\\src\\ShippingEntity\\transaction.xml";//Where the document is
			File xmlFile = new File(filepath);//Create a file
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFile);
			
			//Get the root element
			Node transactions = doc.getFirstChild();
			
			//append a new node to transaction
			Element purchase = doc.createElement("purchase");
			transactions.appendChild(purchase);
			
			//customerFirstName elements
			Element customerFirstName = doc.createElement("customerFirstName");
			customerFirstName.appendChild(doc.createTextNode(detailTransaction.getCustomer().getFirstName()));
			purchase.appendChild(customerFirstName);
			
			//customerLastName elements
			Element customerLastName = doc.createElement("customerLastName");
			customerLastName.appendChild(doc.createTextNode(detailTransaction.getCustomer().getLastName()));
			purchase.appendChild(customerLastName);
			
			//customerAddress
			Element customerAddress = doc.createElement("customerAddress");
			customerAddress.appendChild(doc.createTextNode(detailTransaction.getCustomer().getAdress()));
			purchase.appendChild(customerAddress);
		
			
			//time elements
			Element time = doc.createElement("time");
			SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
			String s = dformat.format(detailTransaction.getDatePurchase());
			time.appendChild(doc.createTextNode(s));
			purchase.appendChild(time);
			
			//Products
			Element listproducts = doc.createElement("products");
			//for each product
			for(int i=0 ; i<detailTransaction.getCompanyProducts().size(); i++) {
				
				Element product = doc.createElement("product");
				
				//productID;
				Element productID = doc.createElement("ID");
				productID .appendChild(doc.createTextNode(detailTransaction.getCompanyProducts().get(i).getProducID().toString()));
				product.appendChild(productID );
				
				// productName;
				Element productName = doc.createElement("Name");
				productName.appendChild(doc.createTextNode(detailTransaction.getCompanyProducts().get(i).getProductName()));
				product.appendChild(productName);
				
				//numberItems
				Element productNumberItems = doc.createElement("NumberItems");
				productNumberItems.appendChild(doc.createTextNode(String.valueOf(detailTransaction.getCompanyProducts().get(i).getNumberItems())));
				product.appendChild(productNumberItems);
				
				listproducts.appendChild(product);
				
			}
			
			purchase.appendChild(listproducts);
			
			//total price
			Element totalPrice = doc.createElement("totalPrice");
			totalPrice.appendChild(doc.createTextNode(String.valueOf(detailTransaction.getTotalPrice())));
			purchase.appendChild(totalPrice);

			
			
			//Write modifications into the xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			
		}catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
