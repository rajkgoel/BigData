public class ElectionCountDirect extends ElectionCount {
	//Stores the votes information in an array for Direct Addressing
	private int[] votes;
		
	public ElectionCountDirect() {
		votes = new int[900000];
	}

	//Get called from Parent class - ElectionCount.add(..) method
	@Override
	protected void setVote(int voterId, int candidateId) throws Exception {
		//Sets the vote information for given voterId, in local array data-structure for direct addressing
		int hashedVoterId = getHashedVoterId(voterId);
		if (votes[hashedVoterId] > 0)
			throw new Exception(String.format("Duplicate vote, given voter %d has already voted", voterId));
		votes[hashedVoterId] = candidateId;
		
		//Increment Vote count for given candidate id, using method defined in parent class
		incrementVoteCountForCandidate(candidateId);
	}

	//Get called from Parent class - ElectionCount.find(..) method
	//Returns the Candidate Id for whom given Voter voted for, using the local array data-structure array
	@Override
	protected Integer getVotedFor(int voterId) {
		return votes[getHashedVoterId(voterId)];
	}
	
	//Provides hashing for VoterId, to ignore the array indexes for Ids between 0 to 99999
	private int getHashedVoterId(int voterId) throws NumberFormatException {
		//Verbose implementation of hashing
		int hashed = voterId - 100000;
		return hashed;
	}

}
