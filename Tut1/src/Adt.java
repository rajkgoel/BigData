import java.util.*;  
import java.util.LinkedList;

class Book {  
	int id;  
	String name,author;  
	int issueDate, returnDate;  
}

class ListOfBooks{
    List<Book> list;
    
	//declare 'list' as a linked list in the constructor using Java's built in API's
    public ListOfBooks(){
        this.list = new LinkedList<Book>();
    }
	public void add(int id, String name, String author, int issueDate, int returnDate) {
		//Wrap all the details into an object of class Book and add it to the linked list
		// Print: 'Successfully added: book_id', where book_id is the id of the book added
		Book book = new Book();
		book.id = id;
		book.name = name;
		book.author = author;
		book.issueDate = issueDate;
		book.returnDate = returnDate;
		this.list.add(book);
		System.out.println("Successfully added: " + id);
		
	}
	
	public void edit(int id, String name, String author, int issueDate, int returnDate) {
		//Check if the book ID exists
		// If it doesn't print: 'No such Book ID exists'      
		//else update the item in the linked list having the given Book ID
		// Print: 'Successfully edited: book_id', where book_id is the id of the book edited
		for(Book b: list) {
	    	if(b.id == id) {
	    		b.name = name;
	    		b.author = author;
	    		b.issueDate = issueDate;
	    		b.returnDate = returnDate;
	    		System.out.println("Successfully edited: " + id);
	    		return;
	    	}
	    }
		System.out.println("No such Book ID exists");
	}
	
	public void delete(int id) {
		//Check if the book ID exists
		// If it doesn't print: 'No such Book ID exists'      
		//else delete the item in the linked list having the given Book ID
		// Print: 'Successfully deleted: book_id', where book_id is the id of the book deleted
		boolean removed = list.removeIf(book -> book.id == id);
		if(removed) 
			System.out.println("Successfully deleted: " + id);
		else
			System.out.println("No such Book ID exists");
	}
	public void printDatabase() {
		System.out.println("List of books:");
		// Print the details of all the books in the list in the following format:
		//ID: 2, Name: ABC, Author: DEF, Date of Issue: 2, Date of Return: 3
		//Note that the above is just an example
		for(Book b: list) {
			System.out.println(String.format("ID: %d, Name: %s, Author: %s, Date of Issue: %d, Date of Return: %d", 
					b.id, b.name, b.author, b.issueDate, b.returnDate));
		}
	}
}  
public class Adt {  
	public static void main(String[] args) {  
		ListOfBooks database = new ListOfBooks();
	    database.add(234,"Hamlet","W. Shakespeare",27,29); 
	    database.add(35,"Relativity","Albert Einstein",15,30);
	    database.add(2,"Data Sciences","Shan B",1,4); 
	    database.edit(2,"Data Sciences","Shan B",1,8);
	    database.delete(35);
	    database.printDatabase();
	    database.add(120,"Crooked House","Agatha Christie",15,28);
	    database.edit(235,"Hamlet","W. Shakespeare",28,29);
	    database.add(121,"The final problem","Arthur Doyle",13,20);
	    database.delete(234);
	    database.printDatabase();      
	}  
}