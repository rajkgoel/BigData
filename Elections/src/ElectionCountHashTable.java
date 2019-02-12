import java.util.*;

public class ElectionCountHashTable extends ElectionCount {

	//Stores the votes information at child-class level
	private Hashtable<Integer, Integer> votes;
	
	public ElectionCountHashTable() {
		votes = new Hashtable<Integer, Integer>();
	}
	
	//Get called from Parent class - ElectionCount.add(..) method
	@Override
	protected void setVote(int voterId, int candidateId) throws Exception {
	
		//Sets the vote information for given voterId, in local data-structure
		if(votes.containsKey(voterId))
			throw new Exception(String.format("Duplicate vote, given voter %d has already voted", voterId));
		votes.put(voterId, candidateId);
		
		//Increment Vote count for given candidate id, using method defined in parent class
		incrementVoteCountForCandidate(candidateId);
	}
	
	//Get called from Parent class - ElectionCount.find(..) method
	//Returns the Candidate Id for whom given Voter voted for, using the local data-structure hash-table
	@Override
	protected Integer getVotedFor(int voterId) {
		return votes.get(voterId);
	}
	
	//Old implementation - This was implemented before the session held on 15 Nov, where it was mentioned 
	//that O(1) need to be achieved for count(..) method.
	//Get called from Parent class - ElectionCount.count(..) method
//	@Override 
//	protected int getTotalVotes(int candidateId) {
//		return (int) votes.values()
//				.stream()
//				.filter(c -> c == candidateId)
//				.count();
//	}
	
}
