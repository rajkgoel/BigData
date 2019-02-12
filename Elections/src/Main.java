import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	/*
	 * ElectionCountDirect and ElectionCountHashTable classes derives from ElectionCount.
	 * ElectionCount contains high-level implementation of required problem statement.
	 * ElectionCountDirect and ElectionCountHashTable only contains the data-structure 
	 * required for holding the voting information, along with assign/retrieval of 
	 * voting information.
	 * After running the program, user can input the option (1/2) to use the underlying
	 * data-structure as Array or HashTable.
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Press 1 for Direct Addressing, 2 for HashTable.");
	   	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   	int option = Integer.parseInt(br.readLine());
	   	
	   	//Process voting using the data-structure as selected by user.
		ElectionCount election;
		if (option == 1) 
			election = new ElectionCountDirect();
		else if (option == 2)
			election = new ElectionCountHashTable();
		else {
			System.out.println("Invalid option... exiting");
			return;
		}
		election.processElection();
		System.out.println("Goodbye");
	}
}
