import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloWorld {

	/*
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		int wrongAssignment = 'c';
//		System.out.println(wrongAssignment);
//		
////		boolean val = true;
////		//char input = ‘c’;
////		int number1 = 257;
////		//byte number2 = 257;
////		short number3 = 257;
////		float number4 = 257;
////		Integer x = 10;
////		x = number1;
////		number1 = x;
////		System.out.println(number1);
//		
//		int number1 = 126;
//		float number2 = 10.9f;
//	    //This is an example of typecasting. In typecasting, 
//	    //a variable with different datatype      
//	    //is converted to another datatype
//		number1 = (int) number2;
//		System.out.println(number1);
//		
//		
//	}
 * 
 */
	int i = 0; //class variable declaration

	public static void main1 (String [] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	/*Scan the array length and then the array elements (using buffered reader) and do the required operation on each element and print.*/
        int numOfElements = Integer.parseInt(br.readLine());
        String numLine = br.readLine();
        String[] nums = numLine.split(" ");
        for(int i=0;i<numOfElements;i++) {
        	int num = Integer.parseInt(nums[i]);
        	if(num%2==0)
        		System.out.print(num + " ");
        	else
        		System.out.print(num+1 + " ");
        }
        /*
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("Enter value for a: ");
//    	int a = Integer.parseInt(br.readLine());
//    	System.out.println("Enter value for b: ");
//    	int b = Integer.parseInt(br.readLine());
//    	Operations ops = new Operations(a, b);
//    	long result = ops.multiply();
		
//		int newarray[5];
//		
//		int result = method();
//    	System.out.println(result);
		
//		int [] input = {1,2,3,4,5};
//		for (int i=0 ; i<=5 ; i++) {
//			System.out.print(input[i]+ " ");
//		}
		
//		int count = 2;
//		int [] input = {1,2,3,4,5};
//		for(int i=1;i<=count;i++)
//			arrayOperation(input);
//		for(int i=0;i<input.length; i++)
//			System.out.print(input[i]+" ");
 * 
 */
	}
	
	public static void arrayOperation(int[] input) {
		int temp = input[0];
		for(int i=0;i<input.length-1;i++)
			input[i] = input[i+1];
		input[input.length-1] = temp;
	}
	
	public static int method(){
		int[] input = {1,3,2,4,5,6};
		int num1 = -1000; int num2 = -1000;
		for ( int i = 0; i < input.length ; i++) {
			if( num1 < input[i] ){
				num2 = num1;
		        num1 = input[i];
	        } else if ( num2 < input[i]){
	        	num2 = input[i];
		    }
		 }
		return num2;
	}
}

/*
//class Operations{
//    private int a;
//    private int b;
//    public Operations(int a, int b){
//        this.a = a;
//        this.b = b;
//    }
//	public long addition() {
//	    return this.a + this.b;
//	}
//	public long subtraction() {
//	    return this.a - this.b;
//	}
//	public long multiply() {
//	    //(a+b)*(a-b)
//	    long add = addition();
//	    long subt = subtraction();
//	    long result = add * subt;
//	    return result;
//	}
//}
 */
 

