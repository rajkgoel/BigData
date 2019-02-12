class Node{
	protected String data;
	protected Node next;
	public Node() {
		next = null;
		data = "";
	}

	public Node(String d,Node n) {
		data = d;
		next = n;
	}

	public void setLink(Node n)
	{
		next = n;
	}    

	public void setData(String d)
	{
		data = d;
	}    

	public Node getLink()
	{
		return next;
	}    

	public String getData()
	{
		return data;
	}
}

public class StackLinkedList {
	static final int MAX = 6;
	protected Node top ;
	protected int size ;

	boolean isEmpty()
	{
		return top == null;

	}

	public void createStack() {
		top = null;
		size = 0;
	}

	public void deleteStack() {
		createStack();
	}

	public int getSize()
	{
		return size;
	}   

	public boolean push(String data)
	{
		if(MAX != size) {
			Node nptr = new Node (data, null);
			if (top == null)
				top = nptr;
			else
			{
				nptr.setLink(top);
				top = nptr;
			}
			size++ ;
			return true;
		}else {
			System.out.println("Stack overflow");
			return false;
		}
	}    

	public String pop()
	{
		if (isEmpty() )
			return  "Stack Underflow";
		Node ptr = top;
		top = ptr.getLink();
		size-- ;
		return ptr.getData();
	} 
	
	public void displayStack()
    {
        System.out.print("\nStack = ");
        if (size == 0) 
        {
            System.out.print("Empty\n");
            return ;
        }
        Node ptr = top;
        while (ptr != null)
        {
            System.out.print(ptr.getData()+" ");
            ptr = ptr.getLink();
        }
        System.out.println();        
    }
}
