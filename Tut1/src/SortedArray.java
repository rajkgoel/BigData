import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.*;

class SortedArray {
	public static void main(String args[]) throws IOException {
		/* Add your code here */
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String val = br.readLine();
		int count = Integer.parseInt(val);
		int[] model = new int[count];
		int[][] version = new int[count][2];
		for(int i=0; i< count; i++) {
			String[] nums = br.readLine().split("\\s+");
			model[i] = Integer.parseInt(nums[0]);
			version[i][0] = Integer.parseInt(nums[0]);
			version[i][1] = Integer.parseInt(nums[1]);
		}
		
		Arrays.sort(model);
		
		for (int i = 0; i < count; i++) {
			ArrayList<Integer> sortedVersion = getSortedVersions(model[i], version);
			for(int ver: sortedVersion) {
				System.out.println(model[i] + " " + ver);//Edit the print statement as per you code
			}
			i += sortedVersion.size() -1;
		}
	}

	private static ArrayList<Integer> getSortedVersions(int model, int[][] version) {
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		for(int i=0;i< version.length;i++) {
			if(version[i][0] == model)
				sorted.add(version[i][1]);
		}
		Collections.sort(sorted);
		return sorted;
	}
}
