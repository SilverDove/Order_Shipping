
public class Customer {
	
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phoneNumber;
	
	public Customer(String firstName, String lastName, String adress, String email, String phoneNumber){//Constructor
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = adress;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	public String getFirstName() {//returns the customer's first name
		return firstName;
	}
	
	public String getLastName() {//returns the customer's last name
		return lastName;
	}
	
	public String getAdress() {//returns the customer's address
		return address;
	}
	
	public String getEmail() {//returns the customer's email
		return email;
	}
	
	public String getPhoneNumber() {//returns the customer's phone number
		return phoneNumber;
	}
	
	
	
	public String toString() {//returns the customer's information
		return "First name: "+firstName +"\n Last name: "+ lastName +"\n Address "+ address+"\n Email "+ email+"\n Phone number "+ phoneNumber;
	}
	

}
