
public class StackArray {

	static final int MAX = 6;
	int top;
	String a[];
	boolean isEmpty()
	{
		return (top < 0);
	}

	public void createStack() {
		top = -1;
		a = new String[MAX];
	}
	
	public void deleteStack() {
		top = -1;
		a = null;
	}

	public boolean push(String x)
	{
		if (top >= MAX-1)
		{
			System.out.println("Stack Overflow");
			return false;
		
		}
		else
		{
			a[++top] = x;
			return true;
		}
	}

	public String pop()
	{
		if (top < 0)
		{
			return "Stack Underflow";
		}
		else
		{
			String x = a[top--];
			return x;
		}
	}
	
	public void displayStack() {
		while (top!=-1) {
			System.out.println(a[top]);
			top--;
		}
	}

}
