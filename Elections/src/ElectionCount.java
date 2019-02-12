import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public abstract class ElectionCount {
	
	//Following abstract methods are implemented by Child classes - (ElectionCountDirect and ElectionCountHashTable)
	//to set/retrieve the voting information, as per the data-structure chosen by user
	protected abstract void setVote(int voterId, int candidateId) throws Exception;
	protected abstract Integer getVotedFor(int voterId);
	
	//Keeps the count of votes that any candidate received, 
	//it is maintained to keep the time complexity of O(1)
	private int[] totalVotesForCandidates = new int[1000];
	
	public void processElection() throws IOException {
		boolean quit = false;
		do {
			//User can choose the below option(s) any number of times, until Quit(q) is called.
			System.out.println("Choose option- Add Votes: A, Find Vote: F, Count Votes: C, Quit - q");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			//Process user entered option(s) until user calls Quit
			String option = br.readLine();
			
			switch(option.toUpperCase()) {
				case "A":	//Add Voting information, user can load multiple files using this option
					add();
					break;
				case "F":	//Find for which candidate the voter voted for
					find();
					break;
				case "C": 	//Find the number of votes received by the candidate
					count();
					break;
				case "Q":	//Quit
					quit = true;
					break;
				default:
					System.out.println("Invalid option provided.");
			}
		
		}while (quit != true);
	}
	
	private void add() throws IOException{
		//Reading the user provided file path and loading data 
		System.out.println("Please provide file path containing Voter Id and Candidate Id:");
	   	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   	String filePath = br.readLine();
	   	File file = new File(filePath);
	   	System.out.println("Attempting to read from file: "+ file.getCanonicalPath());
	   	Scanner sc = new Scanner(file);
	   	int voteCount = 0;
	   	
	   	while (sc.hasNext()) {
	   		//Parse the votes information [VoterID  CandidateId] from each line
	        String vote = sc.nextLine().trim();
	        if(vote.length() <= 0) continue;	//Ignore the blank lines
	        
	        //Store votes in Class member variable
			String[] voteInfo = vote.split("\\s+");
	        if (voteInfo.length == 2) {
	        	try {
	        		//Parse record and validate them as per the given range
	        		Integer voterId = Integer.parseInt(voteInfo[0]);
	        		Integer candidateId = Integer.parseInt(voteInfo[1]);
	        		validateVoterId(voterId);
	        		validateCandidateId(candidateId);
	        		
	        		//Set the voting information using implementation provided in Child classes
					setVote(voterId, candidateId);
					voteCount++;	///Increment the count only for valid vote.
				} 
	        	catch (Exception e) {
					System.out.println(String.format("Invalid vote info: [%s] - %s", vote, e.getMessage()));
				}
	        }
	        else
	        	System.out.println(String.format("Invalid vote info: [%s]", vote));
	   	};
	   	sc.close();	//Close the read stream
	   	System.out.println(String.format("Read %d votes from given file.", voteCount));
	}
	
	//Finds the Candidate Id for which Voter voted for
	private void find() throws IOException  {
		System.out.println("Please enter Voter Id:");
	   	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   	
	   	//Find VoterID from Class member variable  _votes
	   	int voterId = Integer.parseInt(br.readLine());
	   	try {
	   		validateVoterId(voterId);
			Integer votedFor = getVotedFor(voterId);
			if (votedFor != null && votedFor > 0)
				System.out.println(String.format("Voter voted for Candidate Id: %d.", votedFor));
			else
				System.out.println(String.format("Either Voter Id: %d not found or didn't voted for any candidate.", voterId));
	   	}
	   	catch (NumberFormatException ex) {
	   		System.out.println("Error while finding Voter voted for: " + ex.getMessage());
	   	}
	}
	
	//Finds total votes received by the Candidate
	private void count() throws IOException {
		System.out.println("Please enter Candidate Id:");
	   	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   	
	   	//Read the Candidate Id, as input by user
	   	int candidateId = Integer.parseInt(br.readLine());
	   	try {
		   	validateCandidateId(candidateId);
			int totalVotes = totalVotesForCandidates[candidateId]; 
			
			//Print the count of votes received. 
			if (totalVotes > 0)
				System.out.println(String.format("Candidate received %d votes.", totalVotes));
			else
				System.out.println(String.format("Either Candidate Id: %d doesn't exists or received 0 votes.", candidateId));
	   	}
	   	catch(Exception ex) {
	   		System.out.println("Error while finding votes count for candidate: " + ex.getMessage());
	   	}
	}
	
	//Keeps the count of votes that any candidate received
	protected void incrementVoteCountForCandidate(int candidateId) {
		totalVotesForCandidates[candidateId] += 1;
	}
	
	private void validateVoterId(int voterId) {
		//Raises validation exception if Voter Id is not between acceptable range
		if (voterId < 100000 || voterId > 999999)
			throw new NumberFormatException(String.format("Invalid Voter Id: %d, it should be between 100000 and 999999.", voterId));
	}
	
	private void validateCandidateId(int candidateId) {
		//Raises validation exception if Voter Id is not between acceptable range
		if (candidateId < 100 || candidateId > 999)
			throw new NumberFormatException(String.format("Invalid Candidate Id: %d, it should be between 100 and 999.", candidateId));
	}
		
	
}
