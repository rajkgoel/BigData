import java.util.LinkedList;

public class Sort {

	public static void main1(String[] args) {

//		for(int i=0;i<n; i++)
//			System.out.println(input[i]);
		
//		char [] input = new char[5];
//		input[0] = 'a';
//		input[1] = 'b';
//		input[2] = 'c';
//		input[3] = 'd';
//		input[4] = 'e';
//		char c;
//		int i;
//		int j;
//		for (i = 0, j = input.length - 1; i < j; i++, j--) {
//			c = input[i];
//			input[i] = input[j];
//			input[j] = c;
//		}
//		System.out.println(input);
		
		int[] arr = { 1, 2, 3, 4, 5, 6, 7 };
		int low = 0, high = arr.length - 1;
		int element = 3;
		if (BinarySearch(arr, low, high, element) == 0) {
			System.out.println("Element does not exist");
		} else {
			System.out.println("Element exists");
		}
		LinkedList<String> myStrings = new LinkedList<String>();
		
	}
	
	public static int BinarySearch(int[] arr, int low, int high, int element) {
		while (low < high) {
			int middle = (low + high) / 2;
			if (arr[middle] > element) {
				high = middle;
			} else if (arr[middle] < element) {
				low = middle;
			} else if (arr[middle] == element) {
				return 1;
			}
		}
		return 0;
	}
	
	private static int[] BubbleSort(int[] input) {
		//int[] input = { 4, 78, 1, 12 };
		int n = input.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				if (input[i] < input[j]) {
					//swap(input[i], input[j]);
					int temp = input[i];
					input[i] = input[j];
					input[j] = temp;
				}
			}
		}
		return input;
	}

}
