import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ElectionCount {

	//Stores the votes information at class level
	private Map<Integer, Integer> votes;
	
	public ElectionCount() {
		votes = new HashMap<Integer, Integer>();
	}
	
	public void processElection() throws IOException {
		boolean quit = false;
		do {
			System.out.println("Choose option: Add - A, Find - F, Count - C, Quit - q");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			//Process user entered option(s) until user calls Quit
			String option = br.readLine();
			switch(option.toUpperCase()) {
				case "A": 
					add();
					break;
				case "F":
					find();
					break;
				case "C": 
					count();
					break;
				case "Q":
					quit = true;
					break;
				default:
					System.out.println("Invalid option provided.");
			}
		
		}while (quit != true);
	}

	private void count() throws IOException {
		System.out.println("Please enter Candidate Id:");
	   	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   	
	   	//Filter by CandidateId, and print the count of votes received. 
	   	int candidateId = Integer.parseInt(br.readLine());
		int totalVotes = (int) votes.values()
								.stream()
								.filter(c -> c == candidateId)
								.count();
		if (totalVotes > 0)
			System.out.println(String.format("Candidate received %d votes.", totalVotes));
		else
			System.out.println("Either Candidate doesn't exists or received 0 votes.");
	}

	private void find() throws IOException  {
		System.out.println("Please enter Voter Id:");
	   	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   	
	   	//Find VoterID from Class member variable  _votes
	   	int voterId = Integer.parseInt(br.readLine());
		Integer votedFor = votes.get(voterId);
		if (votedFor != null)
			System.out.println(String.format("Voter voted for %d.", votedFor));
		else
			System.out.println("Voter Id not found.");
	}

	private void add() throws IOException {
		System.out.println("Please enter vote(s) separated by Voter Id and Candidate Id:");
	   	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   	boolean quit = false;
	   	do {
	   		//Parse the votes information (VoterID  CandidateId), till user enters blank line
	    	String vote = br.readLine();
	        if (vote.trim().length() <= 0) {
	        	quit = true;
	        	break;
	        }
	        
	        //Store votes in Class member variable
			String[] voteInfo = vote.split("\\s");
	        if (voteInfo.length == 2)
	        	votes.put(Integer.parseInt(voteInfo[0]), Integer.parseInt(voteInfo[1]));
	        else
	        	System.out.println("Invalid vote info");
	   	}while (quit != true);
	}

}
