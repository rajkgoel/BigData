import java.util.ArrayList;
import java.util.HashMap;

class GenerateHashCode {
	/* Add your code here */
	private int generateHashCode(String s) {
	    int length = s.length() * (int)s.toCharArray()[0];
	    return length;
	    
	    
	}

	public static void main(String args[]) {
			
		ArrayList<String> states = new ArrayList<String>();
		states.add("Barun");
		states.add("Mayank");
		states.add("Karan");
		states.add("Shubham");

		Source hd = new Source();
		for (String s : states)
			System.out.println(hd.generateHashCode(s));
	}
}
