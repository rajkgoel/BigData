import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class DuplicateString {

	public static void main1(String[] args) {
		LinkedList<String> myStrings = new LinkedList<String>();
	    myStrings.add("hello");
	    myStrings.add("world");
	    myStrings.add("to you");
	    myStrings.add("friend");
	    myStrings.add("hello");
	    myStrings.add("world");
	    myStrings.add("to you");
	    myStrings.add("too");
	    
	    //TODO write your function here
	    Map dictionary = new HashMap();
	    for(String i: myStrings) {
	    	if(!dictionary.containsKey(i)) {
	    		dictionary.put(i,  1);
	    	}
	    	else {
	    		Integer value = Integer.parseInt(dictionary.get(i).toString());
	    		if(value == 1)
	    			System.out.println(i);
	    		else
	    			dictionary.replace(i, value, value + 1);
	    	}
	    }

	}

}
