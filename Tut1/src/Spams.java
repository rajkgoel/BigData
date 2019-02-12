import java.util.ArrayList;
import java.util.HashMap;

public class Spams {

    static HashMap<Long, String> spams = new HashMap<Long, String>();
	/* Add your code here */
	private static String isSpam(Long number) {
	    if (spams.containsKey(number))
	    	return "SPAM";
	    else
	    	return "NOT SPAM";
	}

	public static void main(String[] args) {
	    spams.put(111L, "SPAM");
        spams.put(113L, "SPAM");

		ArrayList<Long> phoneNum = new ArrayList<Long>();
		phoneNum.add(111L);
		phoneNum.add(101L);
		phoneNum.add(105L);
		phoneNum.add(113L);
		phoneNum.add(118L);

		for (Long ln : phoneNum) {
			System.out.println(ln + ":" + isSpam(ln));
		}

	}

}

