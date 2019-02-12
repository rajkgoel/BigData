import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class CheckSumHashmap
{
    static int getPairsCount(int n, int sum, int[] arr)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i=0;i<arr.length-1;i++) {
            for (int j=i+1;j<arr.length;j++) {
            	String key = arr[i] + "#" + arr[j];
            	String value = arr[j] + "#" + arr[i];
            	System.out.println(key);
            	if(arr[i] + arr[j] == sum && i != j && 
            			!map.containsKey(key) &&
            			!map.containsKey(value)) {
            		map.put(key, value);
            		System.out.println(map.values());
            	}
            }
        }
        return map.size();
    }

    public static void main(String[] args) throws IOException{
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int count = Integer.parseInt(br.readLine());
    	String[] sValues = br.readLine().split(" ");
    	int[] values = new int[sValues.length];
    	for(int i=0;i<sValues.length;i++)
    		values[i] = Integer.parseInt(sValues[i]);
    	
    	int sum = Integer.parseInt(br.readLine());
    	int total = getPairsCount(count, sum, values);
    	System.out.println(total);
    }
}

