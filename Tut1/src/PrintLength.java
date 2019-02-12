import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PrintLength {

	public static void main1(String[] args) throws IOException {
		// TODO Auto-generated method stub
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String val = br.readLine();
	    if(val == null) return;
		int count = Integer.parseInt(val);
		int[] result = new int[count];
		for(int i=0;i<count; i++){
			String[] strNums1 = br.readLine().split("\\s");
    	    result[i] = strNums1.length;
		}
		
		for(int i=count-1; i >= 0; i--)
		    System.out.println(result[i]);
	}

}
