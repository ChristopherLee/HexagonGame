package ingenious.board.test;

import java.util.List;

import junit.framework.TestCase;
import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.Score;

public class ScoreTest extends TestCase{
	private Score score;

	@Override
	protected void tearDown() throws Exception {
		score = null;
	}
	@Override
	protected void setUp() throws Exception {
		score = new Score();
	}

	public void test1() {
		Score score1 = new Score();
		Score score2 = new Score();
		score2.addValue(Color.BLUE, 1);
		Score score3 = new Score();

		assertEquals(score2, score1.pickBestSmallestScore(score2, score3));
	}

	public  void test2() {
		Score score1 = new Score();
		Score score2 = new Score();
		Score score3 = new Score();

		assertEquals(score2, score1.pickBestSmallestScore(score2, score3));
	}

	public  void test3() {
		Score score1 = new Score();
		Score score2 = new Score();
		score2.addValue(Color.BLUE, 5);
		score2.addValue(Color.RED, 1);
		Score score3 = new Score();
		score3.addValue(Color.GREEN, 2);

		assertEquals(score2, score1.pickBestSmallestScore(score2, score3));
	}


	

	public void testOneScore() {
		Score score1 = new Score();
		score1.setValue(Color.BLUE, 2);
		score1.setValue(Color.GREEN, 3);
		score1.setValue(Color.ORANGE, 4);
		score1.setValue(Color.PURPLE, 5);
		score1.setValue(Color.RED, 6);
		score1.setValue(Color.YELLOW, 7);

		score.add(score1);
		assertEquals(2, (int)score.get(Color.BLUE));
		assertEquals(3, (int)score.get(Color.GREEN));
		assertEquals(4, (int)score.get(Color.ORANGE));
		assertEquals(5, (int)score.get(Color.PURPLE));
		assertEquals(6, (int)score.get(Color.RED));
		assertEquals(7, (int)score.get(Color.YELLOW));
	}

	public void testTwoScore() {
		Score score1 = new Score();
		score1.setValue(Color.BLUE, 2);
		score1.setValue(Color.GREEN, 3);
		score1.setValue(Color.ORANGE, 4);
		score1.setValue(Color.PURPLE, 5);
		score1.setValue(Color.RED, 6);
		score1.setValue(Color.YELLOW, 7);

		Score score2 = new Score();
		score2.setValue(Color.BLUE, 2);
		score2.setValue(Color.GREEN, 3);
		score2.setValue(Color.ORANGE, 4);
		score2.setValue(Color.PURPLE, 5);
		score2.setValue(Color.RED, 6);
		score2.setValue(Color.YELLOW, 7);

		score1.add(score2);
		assertEquals(4, (int)score1.get(Color.BLUE));
		assertEquals(6, (int)score1.get(Color.GREEN));
		assertEquals(8, (int)score1.get(Color.ORANGE));
		assertEquals(10, (int)score1.get(Color.PURPLE));
		assertEquals(12, (int)score1.get(Color.RED));
		assertEquals(14, (int)score1.get(Color.YELLOW));
	}

	public  void testScoreCompareTo1() {
		Score score2 = new Score();
		score2.addValue(Color.BLUE, 5);
		score2.addValue(Color.RED, 1);
		Score score3 = new Score();
		score3.addValue(Color.BLUE, 5);
		score3.addValue(Color.RED, 1);

		assertEquals(0, score2.compareTo(score3));
	}

	public  void testScoreCompareTo2() {
		Score score2 = new Score();
		score2.addValue(Color.BLUE, 5);
		score2.addValue(Color.RED, 1);
		Score score3 = new Score();
		score3.addValue(Color.BLUE, 6);
		score3.addValue(Color.RED, 1);

		assertEquals(-1, score2.compareTo(score3));
	}
	
	public  void testGetLowestScoringColors1() {
		Score score = new Score();
		List<Color> lowestScores = score.getLowestScoringColors();
		assertTrue(lowestScores.contains(Color.BLUE));
		assertTrue(lowestScores.contains(Color.RED));
		assertTrue(lowestScores.contains(Color.GREEN));
		assertTrue(lowestScores.contains(Color.YELLOW));
		assertTrue(lowestScores.contains(Color.PURPLE));
		assertTrue(lowestScores.contains(Color.ORANGE));
	}
	
	private Score createScore(int blue, int red, int green, int yellow, int purple, int orange) {
		Score score = new Score();
		score.addValue(Color.BLUE, blue);
		score.addValue(Color.RED, red);
		score.addValue(Color.GREEN, green);
		score.addValue(Color.YELLOW, yellow);
		score.addValue(Color.PURPLE, purple);
		score.addValue(Color.ORANGE, orange);
		return score;
	}
	
	public void testGetLowestScoringColors2() {
		Score score = createScore(1, 2, 2, 2, 2, 2);
		List<Color> lowestScores = score.getLowestScoringColors();
		assertTrue(lowestScores.contains(Color.BLUE));
	}
	
	public  void testGetLowestScoringColors3() {
		Score score = createScore(2, 1, 1, 2, 2, 2);
		List<Color> lowestScores = score.getLowestScoringColors();
		assertTrue(lowestScores.contains(Color.RED));
		assertTrue(lowestScores.contains(Color.GREEN));
	}
}
