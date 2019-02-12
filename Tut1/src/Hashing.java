import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hashing {
	static int  ind = 0;
	static int hashFunction(String key) {
		ind = ind + 1;
		return ind;
	}

	public static void main(String[] args) throws IOException {
		int[] array = new int[3];
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 3; i++) {
			//System.out.println("Enter a string..");
			array[i] = hashFunction(br.readLine());
		}
		int flag = 0;
		for (int j = 0; j < 3; j++) {
			for (int k = j + 1; k < 3; k++) {
				if (array[k] > 9 || array[j] > 9 || (k != j && array[k] == array[j])) {
					flag = 1;
					//System.out.println("Inside break");
					break;
				}
			}
			if (flag == 1)
			{
				//System.out.println("Outside break");
				break;
			}
		}
		if (flag == 0)
			System.out.println("Correct");
	}
}