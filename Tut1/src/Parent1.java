public class Parent1 
{
    public static void main(String [] args) 
    {
        int result = 0;

        Boolean b1 = new Boolean("TRUE");
        Boolean b2 = new Boolean("true");

        if (b1 == b2) {
        	result = 1;
        	System.out.println("==");
        	System.out.println(b1);
        	System.out.println(b2);
        }
        if (b1.equals(b2))
        	{
        		result = result + 10;
        		System.out.println(b1);
        		System.out.println(b2);
        	}
        System.out.println(result);
    }
}