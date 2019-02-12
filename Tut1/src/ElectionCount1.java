import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
public class ElectionCount {
		int voter_id;
		int candidate_id;
		ArrayList<String> list = new ArrayList<String>();
		Map<Integer,Integer> h1 = new Hashtable<Integer,Integer>();
    
	  public void readFile(String filePath) throws FileNotFoundException {
//		    try {  
		      Scanner scanner = new Scanner(new File(filePath));

		      // read the file and find max and min values to initialize the array
		      while (scanner.hasNext()) {
	
		    	String line = scanner.nextLine();
		        String[] splited = line.split("\\s+");
		        //int voter_id = Integer.parseInt(splited[0]);
		        //int candidate_id = Integer.parseInt(splited[1]);
		        //min = Math.min(min, voter_id);
		        //max = Math.max(max, voter_id);
		        list.add(line);
		      //// System.out.println("Test:");
			      //System.out.println("Candidate" +candidate_id);
		      }

		      //arr = new int[max - min + 1];
		      scanner.close();
		      //System.out.println("Voter:" +voter_id);
		      //System.out.println("Candidate" +candidate_id);

		      // build array now from ArrayList
		      for (String line : list)
		        add(line);

//		    } 
//		    catch (FileNotFoundException e) {
//		      e.printStackTrace();
//		    }
		  }
	  
	  public void add(String line) {
		    String[] splited = line.split("\\s+");
		    int voter_id = Integer.parseInt(splited[0]);
		    int candidate_id = Integer.parseInt(splited[1]);
		    //Map<Integer,Integer> h1 = new Hashtable<Integer,Integer>();
		    h1.put(voter_id, candidate_id);
		    Set<Entry<Integer, Integer>> s = h1.entrySet();  
		    System.out.println("display values:" +s); 
		    
		    //int index = voter_id - min;
		    //arr[index] = candidate_id;
		    }

	 		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			//result = prime * result + voter_id.hashCode();
			return result;
		}


public static void main(String[] args) {
	try {
	    String fileName = "c:\\Projects\\data.txt";
	    ElectionCount ec1=new ElectionCount();
	    ec1.readFile(fileName);
  
    } 
    catch (FileNotFoundException e) {
      e.printStackTrace();
    
    //ec1.add(line);
    //Map<Integer,Integer> h1 = new Hashtable<Integer,Integer>();
    //h1.put(ec1.voter_id,ec1.candidate_id);
    
       
     }
    
   

   // System.out.printf("Votes received by %d is %d \n", 135, count(135));
    //System.out.printf("Canidate chosen by %d is %d \n", 151026, find(151026));
  }
}