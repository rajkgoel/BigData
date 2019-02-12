import java.util.*;

class Record1 {    
	String name;  
	long number; 
	
	public Record1(String nm, long num){
		this.name = nm;
		this.number = num;
	}
}

class AddressBook1{
	List<Record1> list;
	
	public AddressBook1() {
		list = new LinkedList<Record1>();
	}

	//declare 'list' as a linked list in the constructor using Java's built in API's 
	
	public void add(String name, long number) {
		//Wrap all the details into an object of class Record and add it to the linked list
		// Print: 'Successfully added: contact_name', where contact_name is the name of the contact added
		list.add(new Record1(name, number));
		System.out.println("Successfully added: " + name);
	}
	public void findByNumber(long number) {	
		//Check if the number exists
		// If it doesn't, print: 'No such Number exists'      
		//else Print: 'Name: contact_name', where contact_name is the name of the contact having that number
		for(int index=0;index<list.size();index++)
		{
			Record1 rec = list.get(index);
			if(rec.number == number) {
				System.out.println("Name: " + rec.name);
				return;
			}
		}
		System.out.println("No such Number exists");
	}
	
	public void delete(long number) {
		//Check if the number exists
		// If it doesn't, print: 'No such Number exists'      
		//else delete the item in the linked list having the given number
		// Print: 'Successfully deleted: contact_number', where contact_number is the number to be deleted
		boolean removed = list.removeIf(r -> r.number == number);
		if (removed)
			System.out.println("Successfully deleted: " + number);
		else
			System.out.println("No such Number exists");
	}
	public void printAddressBook() {
		System.out.println("List of contacts:"); 
		// Print the details of all the contacts in the list in the following format:
		//Name: ABC Number: 123456789
		//Note that the above is just an example	      
		list.forEach(r -> System.out.println("Name: " + r.name + " Number: " + r.number));
	}
}  
public class PhoneBookLinked {  
public static void main(String[] args) {  
	AddressBook1 myContacts = new AddressBook1();
    myContacts.add("John", 9876123450l);
    myContacts.add("Mellisa", 8360789114l);
    myContacts.add("Daman",9494149900l);
    myContacts.findByNumber(9998760333l);
    myContacts.printAddressBook();
    myContacts.delete(9876123450l);
    myContacts.add("Gregory",7289880988l);
    myContacts.printAddressBook();
    myContacts.findByNumber(8360789114l);
    myContacts.add("Mary",7205678901l);
    myContacts.delete(9876123450l);
    myContacts.findByNumber(7205678901l);
    myContacts.printAddressBook();      
}  
}