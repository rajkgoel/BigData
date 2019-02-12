import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Linkedlist{
	Node head;
	Node first, last;
	public void moveLastElementToHead()
	{
		last.next = first;
		first = last;
		last.previous.next = null;
	}
	
	public void push(int data)
   	 {
		if(head == null)
		{
	        first = new Node(data, null);
	        head = first;
		}
		else{
		    head.next = new Node(data, head);
		    head = head.next;
		    last = head;
		}
	}
	public void printList()
   	{
		if(first==null) return;
		Node toPrint = first;
        do{
            System.out.print(toPrint.data + " ");
            toPrint = toPrint.next;
        }while(toPrint != null);
   	}
class Node
	{
		int data;
		Node next;
		Node previous;
		Node(int d, Node current) 
		{
			this.data = d; 
			this.next = null;
			this.previous = current;
		}
	}
}

public class LinkedListEg {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	//TODO
    	int numOfElements = Integer.parseInt(br.readLine());
        String numLine = br.readLine();
        String[] nums = numLine.split(" ");
        Linkedlist lList = new Linkedlist();
        for(int i=0;i<numOfElements;i++) {
        	int num = Integer.parseInt(nums[i]);
        	lList.push(num);
        }
        lList.moveLastElementToHead();
        lList.printList();

    }
}