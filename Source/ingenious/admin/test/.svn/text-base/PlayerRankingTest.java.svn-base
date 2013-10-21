package ingenious.admin.test;

import java.util.ArrayList;
import java.util.List;

import ingenious.admin.GameResult;
import ingenious.admin.PlayerRanking;
import ingenious.admin.PlayerRecord;
import ingenious.board.Color;
import ingenious.board.Score;

import junit.framework.TestCase;

public class PlayerRankingTest extends TestCase{
	ArrayList<PlayerRecord> records;
	@Override
    protected void setUp() throws Exception {
		PlayerRecord player1 = new PlayerRecord("test1", null);
		Score score1 = new Score();
		score1.addValue(Color.GREEN, 2);
		player1.setScore(score1);
		PlayerRecord player2 = new PlayerRecord("test2", null);
		Score score2 = new Score();
		score2.addValue(Color.GREEN, 2);
		player2.setScore(score2);
		records = new ArrayList<PlayerRecord>();
		records.add(player1);
		records.add(player2);
		
    }
	
	// simple tie
	public void testTie() {
		GameResult result = new GameResult(records);
		List <PlayerRanking> rankings = result.getPlayerRankings();
		
		assertEquals(1, rankings.get(0).getRank());
		assertEquals(1, rankings.get(1).getRank());
	}
	
	public void testTieWithThirdPerson() {
		PlayerRecord player3 = new PlayerRecord("test3", null);
		Score score3 = new Score();
		score3.addValue(Color.RED, 1);
		player3.setScore(score3);
		records.add(player3);
		
		GameResult result = new GameResult(records);
		List <PlayerRanking> rankings = result.getPlayerRankings();
		
		assertEquals(1, rankings.get(0).getRank());
		assertEquals(1, rankings.get(1).getRank());
		assertEquals(3, rankings.get(2).getRank());
		assertEquals("test3", rankings.get(2).getPlayerRecord().getName());
	}
	
	public void testNormalWinner() {
		PlayerRecord player3 = new PlayerRecord("test3", null);
		Score score3 = new Score();
		score3.addValue(Color.RED, 1);
		score3.addValue(Color.GREEN, 3);
		score3.addValue(Color.BLUE, 6);
		score3.addValue(Color.ORANGE, 2);
		score3.addValue(Color.PURPLE, 1);
		player3.setScore(score3);
		records.add(player3);
		
		PlayerRecord player4 = new PlayerRecord("test4", null);
		Score score4 = new Score();
		score4.addValue(Color.RED, 1);
		score4.addValue(Color.GREEN, 3);
		score4.addValue(Color.BLUE, 7);
		score4.addValue(Color.ORANGE, 2);
		score4.addValue(Color.PURPLE, 1);
		player4.setScore(score4);
		records.add(player4);
		
		GameResult result = new GameResult(records);
		List <PlayerRanking> rankings = result.getPlayerRankings();
		
		assertEquals(1, rankings.get(0).getRank());
		assertEquals(2, rankings.get(1).getRank());
		assertEquals(3, rankings.get(2).getRank());
		assertEquals(3, rankings.get(3).getRank());
		assertEquals("test4", rankings.get(0).getPlayerRecord().getName());
		assertEquals("test3", rankings.get(1).getPlayerRecord().getName());
	}
}
