class Item{
	public int number1;
	public int number2;
	public int result;
	public char operation;
	
	public Item(int num1, int num2, char op, int res) {
		this.number1 = num1;
		this.number2 = num2;
		this.operation = op;
		this.result = res;
	}
}

class Stack {
   private int maxSize;
   private Item[] stackArray;
   private int top;
 
   public Stack(int s) {
      //Assign s to the maxSize, declare the stack of array elements, initialize top
	   maxSize = s;
	   stackArray = new Item[maxSize];
	   top = -1;
   }
   public void push(Item j) {
      //Push the whole operation item at the top of the stack
	   stackArray[++top] = j;
   }
   public void displayOperationTop5() {
	//Print the last 5 (or  all elements if the size of stack is less than or equal to 5) operations in the following format:
    //'number1' 'operation' 'number2' = 'result'
	   	int index = top;
		while (index!=-1 && index > top-5) {
			Item item = stackArray[index];
			System.out.println(String.format("%d %s %d = %d", item.number1, item.operation, item.number2, item.result));
			index--;
		}
   }
   public int peek() {
	//return the result of last operation. 
    //Hint: Top of the stack holds information for the last operation
	   	if(top < 0) return 0;
		Item item = stackArray[top];
		return item.result;
   }
   
   public void add(int number1, int number2) {
	   //Print the result (e.g. If number1 is 2 and number2 is 3 then print 'Result: 5') and push the item into the stack
	   int result = number1 + number2;
	   Item item = new Item(number1, number2, '+', result);
	   this.push(item);
	   System.out.println("Result: " + result);
   }
   public void subtract(int number1, int number2) {
	   //Print the result (e.g. If number1 is 3 and number2 is 2 then print 'Result: 1') and push the item into the stack
	   int result = number1 - number2;
	   Item item = new Item(number1, number2, '-', result);
	   this.push(item);
	   System.out.println("Result: " + result);
   }
   public void multiply(int number1, int number2) {
	   //Print the result (e.g.  If number1 is 3 and number2 is 2 then print 'Result: 6') and push the item into the stack
	   int result = number1 * number2;
	   Item item = new Item(number1, number2, '*', result);
	   this.push(item);
	   System.out.println("Result: " + result);
   }
   public void divide(int number1, int number2) {
	   //Print the result (e.g. If number1 is 6 and number2 is 3 then print 'Result: 2') and push the item into the stack
	   int result = number1 / number2;
	   Item item = new Item(number1, number2, '/', result);
	   this.push(item);
	   System.out.println("Result: " + result);
   }
}

public class Calculator{
	public static void main(String[] args) {
	      Stack newStack = new Stack(10); 
	      newStack.add(2,3);
	      newStack.multiply(8,9);
	      newStack.displayOperationTop5();
	      newStack.divide(6,3);
	      newStack.subtract(45,44);
	      newStack.add(18,0);
	      newStack.multiply(6,newStack.peek());
	      newStack.displayOperationTop5();
	      newStack.divide(2,3);
	      newStack.subtract(3,newStack.peek());
	   }
}