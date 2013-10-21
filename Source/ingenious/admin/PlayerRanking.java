package ingenious.admin;

public class PlayerRanking {
	private final int rank;
	private final PlayerRecord playerRecord;
	
	public PlayerRanking(int rank, PlayerRecord playerRecord) {
		this.rank = rank;
		this.playerRecord = playerRecord;
	}

	public int getRank() {
		return rank;
	}

	public PlayerRecord getPlayerRecord() {
		return playerRecord;
	}
	
	public boolean equals (Object obj) {
		if (!(obj instanceof PlayerRanking))
			return false;
		PlayerRanking otherRanking = (PlayerRanking)obj;
		return rank == otherRanking.getRank() &&
				playerRecord.equals(otherRanking.getPlayerRecord());
			
	}
}
