package ingenious.suite.network.test;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.distributed.ProxyTurn;
import ingenious.suite.TurnTestXML;
import ingenious.suite.network.AcceptXML;
import ingenious.suite.network.BooleanXML;
import ingenious.suite.network.EndXML;
import ingenious.suite.network.FalseXML;
import ingenious.suite.network.IAdminResponse;
import ingenious.suite.network.ITurnAction;
import ingenious.suite.network.PlacementXML;
import ingenious.suite.network.PlayersXML;
import ingenious.suite.network.RegisterXML;
import ingenious.suite.network.RerackXML;
import ingenious.suite.network.RerackableXML;
import ingenious.suite.network.ResultXML;
import ingenious.suite.network.TileXML;
import ingenious.suite.network.TrueXML;
import ingenious.suite.network.TurnXML;
import ingenious.suite.network.XMLConverter;
import ingenious.turn.Turn;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;

public class XMLConverterTest extends XMLTestCase {
	String registerIn;
	String acceptIn;
	String acceptIn2;
	String playersIn1;
	String turnIn1;
	List<Tile> hand1;
	List<String> players1;
	
	@Override
    protected void setUp() throws Exception {
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalizeWhitespace(true);
		registerIn = "<register name=\"test-player\"/>";
		acceptIn = "<accept player=\"2\">\n" +
		   			"  <tile c0=\"RED\" c1=\"RED\"/>\n" +
		   			"  <tile c0=\"RED\" c1=\"RED\"/>\n" +
		   			"  <tile c0=\"RED\" c1=\"RED\"/>\n" + 
		   			"  <tile c0=\"RED\" c1=\"RED\"/>\n" +
		   			"  <tile c0=\"RED\" c1=\"RED\"/>\n" +
		   			"  <tile c0=\"RED\" c1=\"RED\"/>\n" +
		   			"</accept>";
		acceptIn2 = "<accept player=\"2\">" +
			"<tile c0=\"RED\" c1=\"RED\"/>" +
			"<tile c0=\"RED\" c1=\"RED\"/>" +
			"<tile c0=\"RED\" c1=\"RED\"/>" + 
			"<tile c0=\"RED\" c1=\"RED\"/>" +
			"<tile c0=\"RED\" c1=\"RED\"/>" +
			"<tile c0=\"RED\" c1=\"RED\"/>" +
			"</accept>";
		hand1 = new ArrayList<Tile>();
		hand1.add(new Tile(Color.RED, Color.RED));
		hand1.add(new Tile(Color.RED, Color.RED));
		hand1.add(new Tile(Color.RED, Color.RED));
		hand1.add(new Tile(Color.RED, Color.RED));
		hand1.add(new Tile(Color.RED, Color.RED));
		hand1.add(new Tile(Color.RED, Color.RED));
		
		playersIn1 = "<players>" +
					"<player name=\"test-player1\"/>" + 
					"<player name=\"test-player2\"/>" +
					"<player name=\"test-player3\"/>" +
					"<player name=\"test-player4\"/>" +
					"</players>";
		players1 = new ArrayList<String>();
		players1.add("test-player1");
		players1.add("test-player2");
		players1.add("test-player3");
		players1.add("test-player4");
		
		turnIn1 = "<turn><board players=\"2\"/>" +
				   "<score orange=\"0\" red=\"0\" green=\"0\" yellow=\"0\" purple=\"0\" blue=\"0\"/>" +
				   "<tile c0=\"RED\" c1=\"RED\"/>" +
				   "<tile c0=\"RED\" c1=\"RED\"/>" +
				   "<tile c0=\"RED\" c1=\"RED\"/>" +
				   "<tile c0=\"RED\" c1=\"RED\"/>" +
				   "<tile c0=\"RED\" c1=\"RED\"/>" +
				   "<tile c0=\"RED\" c1=\"RED\"/>" +
				  "</turn>";
	}
	
	public XMLConverterTest(String name) {
		super(name);
	}
	
	public void testRegisterIn() throws Exception {
		String actual = XMLConverter.getInstance().writeXml(new RegisterXML("test-player"));
		assertXMLEqual("Test register write xml", registerIn, actual);
	}
	
	public void testRegisterOut() throws Exception {
		String expectedName = "test-player";
		InputStream inputStream = new StringBufferInputStream(registerIn);
		RegisterXML actual = (RegisterXML)XMLConverter.getInstance().readXml(inputStream);
		assertEquals(expectedName, actual.getName());
	}
	
	/**
	 * Test for Accept message in response to registration
	 */
	public void testAcceptIn() throws Exception {		
		String actual = XMLConverter.getInstance().writeXml(new AcceptXML(2, hand1));
		Diff myDiff = new Diff(acceptIn, actual);
		assertTrue(myDiff.similar());
		
		Diff myDiff2 = new Diff(acceptIn2, actual);
		assertTrue(myDiff2.similar());
	}
	
	public void testAcceptOut() throws Exception {
		int expectedNumberOfPlayers = 2;
		InputStream inputStream = new StringBufferInputStream(acceptIn);
		AcceptXML actual = (AcceptXML)XMLConverter.getInstance().readXml(inputStream);
		assertEquals(expectedNumberOfPlayers, actual.getNumberOfPlayers());
		assertEquals(hand1, actual.getTiles());
	}
	
	
	/**
	 * Tests for Ready to start messages
	 */
	public void testReadyMessageIn() throws Exception {		
		String actual = XMLConverter.getInstance().writeXml(new PlayersXML(players1));
		
		Diff myDiff = new Diff(playersIn1, actual);
		assertTrue(myDiff.similar());
	}
	
	public void testReadyMessageOut() throws Exception {
		InputStream inputStream = new StringBufferInputStream(playersIn1);
		PlayersXML actual = (PlayersXML)XMLConverter.getInstance().readXml(inputStream);
		assertEquals(players1, actual.getNames());
	}
	
	/**
	 * Tests for Turn messages
	 */
	public void testTurnMessageIn() throws Exception {
		String actual = XMLConverter.getInstance().writeXml(new TurnXML(2, new ArrayList<Placement>(), hand1, new Score()));
		
		Diff myDiff = new Diff(turnIn1, actual);
		assertTrue(myDiff.similar());
	}
	
	/**
	 * Tests for sending Placement messages
	 */
	public void testWritePlacementMessage1() throws Exception {
		String placementMessage = "<placement c0=\"BLUE\" d0=\"4\" a0=\"12\" c1=\"PURPLE\" d1=\"5\" a1=\"14\"/>";
		InputStream inputStream = new StringBufferInputStream(placementMessage);
		String actual = XMLConverter.getInstance().writeXml(new PlacementXML(new Placement(Color.BLUE, 4, 12, Color.PURPLE, 5, 14)));
		assertXMLEqual(placementMessage, actual);
	}
	
	public void testWritePlacementMessage2() throws Exception {
		String placementMessage = "<placement c0=\"GREEN\" d0=\"0\" a0=\"0\" c1=\"RED\" d1=\"1\" a1=\"0\"/>";
		InputStream inputStream = new StringBufferInputStream(placementMessage);
		String actual = XMLConverter.getInstance().writeXml(new PlacementXML(new Placement(Color.GREEN, 0, 0, Color.RED, 1, 0)));
		assertXMLEqual(placementMessage, actual);
	}
	
	/**
	 * Tests for parsing Placement messages
	 */
	
	public void testReadPlacementMessage1() throws Exception {
		String placementMessage = "<placement c0=\"GREEN\" d0=\"0\" a0=\"0\" c1=\"RED\" d1=\"1\" a1=\"0\"/>";
		InputStream inputStream = new StringBufferInputStream(placementMessage);
		Placement expectedPlacement = new Placement(Color.GREEN, 0, 0, Color.RED, 1, 0);		
		Placement actual = ((PlacementXML)XMLConverter.getInstance().readXml(inputStream)).getPlacement();	
		assertTrue(expectedPlacement.equals(actual));		
	}
	
	public void testReadPlacementMessage2() throws Exception {
		String placementMessage = "<placement c0=\"GREEN\" d0=\"5\" a0=\"25\" c1=\"RED\" d1=\"5\" a1=\"24\"/>";
		InputStream inputStream = new StringBufferInputStream(placementMessage);
		Placement expectedPlacement = new Placement(Color.GREEN, 5, 25, Color.RED, 5, 24);		
		Placement actual = ((PlacementXML)XMLConverter.getInstance().readXml(inputStream)).getPlacement();	
		assertTrue(expectedPlacement.equals(actual));		
	}
	
	/**
	 * test writing False message
	 */
	public void testWriteFalseMessage() throws Exception {
		String falseMessage = "<false />";
		InputStream inputStream = new StringBufferInputStream(falseMessage);
		String actual = XMLConverter.getInstance().writeXml(new FalseXML());	
		assertXMLEqual(falseMessage, actual);		
	}
	
	/**
	 * test reading False message
	 */
	public void testReadFalseMessage() throws Exception {
		String falseMessage = "<false />";
		InputStream inputStream = new StringBufferInputStream(falseMessage);		
		Object actual = (Object)XMLConverter.getInstance().readXml(inputStream);
		assertTrue(actual.getClass().equals(FalseXML.class));		
	}
	
	/**
	 * test writing True message
	 */
	public void testWriteTrueMessage() throws Exception {
		String trueMessage = "<true />";
		InputStream inputStream = new StringBufferInputStream(trueMessage);
		String actual = XMLConverter.getInstance().writeXml(new TrueXML());	
		assertXMLEqual(trueMessage, actual);		
	}
	
	/**
	 * test reading True message
	 */
	public void testReadTrueMessage() throws Exception {
		String trueMessage = "<true />";
		InputStream inputStream = new StringBufferInputStream(trueMessage);		
		Object actual = (Object)XMLConverter.getInstance().readXml(inputStream);
		assertTrue(actual.getClass().equals(TrueXML.class));		
	}
	
		
	/**
	 * test writing Tile message
	 */
	public void testWriteTileMessage() throws Exception {
		String tileMessage = "<tile c0=\"PURPLE\" c1=\"RED\"/>";
		InputStream inputStream = new StringBufferInputStream(tileMessage);
		String actual = XMLConverter.getInstance().writeXml(new TileXML(new Tile(Color.PURPLE,Color.RED)));
		assertXMLEqual(tileMessage, actual);
	}
	
	/**
	 * test reading Tile message
	 */
	public void testReadTileMessage() throws Exception {
		String tileMessage = "<tile c0=\"BLUE\" c1=\"ORANGE\"/>";
		InputStream inputStream = new StringBufferInputStream(tileMessage);
		Tile expectedTile = new Tile(Color.BLUE, Color.ORANGE);
		Tile actual = ((TileXML)XMLConverter.getInstance().readXml(inputStream)).getTile();	
		assertTrue(expectedTile.equals(actual));
	}
	
	
	/**
	 * test writing rerackable message
	 */
	public void testWriteRerackableMessage() throws Exception {
		String rerackableMessage = "<rerackable />";
		InputStream inputStream = new StringBufferInputStream(rerackableMessage);
		String actual = XMLConverter.getInstance().writeXml(new RerackableXML());
		assertXMLEqual(rerackableMessage, actual);
	}
	
	/**
	 * test reading rerackable message
	 */
	public void testReadRerackableMessage() throws Exception {
		String rerackableMessage = "<rerackable />";
		InputStream inputStream = new StringBufferInputStream(rerackableMessage);
		Object actual = (Object)XMLConverter.getInstance().readXml(inputStream);	
		assertTrue(actual.getClass().equals(RerackableXML.class));
	}
	
	/**
	 * test writing rerack message
	 */
	public void testWriteRerackMessage() throws Exception {
		String rerackMessage = "<rerack />";
		InputStream inputStream = new StringBufferInputStream(rerackMessage);
		String actual = XMLConverter.getInstance().writeXml(new RerackXML());
		assertXMLEqual(rerackMessage, actual);
	}
	
	/**
	 * test reading rerack message
	 */
	//TODO is this right? 
	public void testReadRerackMessage() throws Exception {
		String rerackMessage = "<rerack />";
		InputStream inputStream = new StringBufferInputStream(rerackMessage);
		Object actual = (Object)XMLConverter.getInstance().readXml(inputStream);
		assertTrue(actual.getClass().equals(RerackXML.class));
	}
		
	/**
	 * test reading rerack message
	 */
	//TODO is this right? 
	public void testReadEndMessage() throws Exception {
		String endMessage = "<end />";
		InputStream inputStream = new StringBufferInputStream(endMessage);
		Object actual = (Object)XMLConverter.getInstance().readXml(inputStream);
		assertEquals(actual.getClass(), EndXML.class);
	}
	
	/**
	 * test writing result message
	 */
	public void testWriteResultMessage1() throws Exception {
		String resultMessage = "<result name=\"player-1\"></result>";
		InputStream inputStream = new StringBufferInputStream(resultMessage);
		List<Placement> placements = new ArrayList<Placement> ();
		String actual = XMLConverter.getInstance().writeXml(new ResultXML("player-1", placements));
		assertXMLEqual(resultMessage, actual);
	}
	
	/**
	 * test reading result message
	 */
	public void testReadResultMessage1() throws Exception {
		String resultMessage = "<result name=\"player-1\"></result>";
		InputStream inputStream = new StringBufferInputStream(resultMessage);
		List<Placement> placements = new ArrayList<Placement> ();
		ResultXML actual = (ResultXML)XMLConverter.getInstance().readXml(inputStream);
		assertEquals("player-1", actual.getName());
		assertEquals(placements, actual.getPlacements());
	}
	
	/**
	 * test writing result message
	 */
	public void testWriteResultMessage2() throws Exception {
		String resultMessage = 
			"<result name=\"player-1\">" +
				"<placement c0=\"BLUE\" d0=\"4\" a0=\"12\" c1=\"PURPLE\" d1=\"5\" a1=\"14\"/>" +
				"<placement c0=\"BLUE\" d0=\"3\" a0=\"11\" c1=\"PURPLE\" d1=\"4\" a1=\"13\"/></result>";
		InputStream inputStream = new StringBufferInputStream(resultMessage);
		List<Placement> placements = new ArrayList<Placement> ();
		placements.add(new Placement(Color.BLUE, 4, 12, Color.PURPLE, 5, 14));
		placements.add(new Placement(Color.BLUE, 3, 11, Color.PURPLE, 4, 13));
		String actual = XMLConverter.getInstance().writeXml(new ResultXML("player-1", placements));
		assertXMLEqual(resultMessage, actual);
	}
	
	/**
	 * test reading result message
	 */
	public void testReadResultMessage2() throws Exception {
		String resultMessage = 
			"<result name=\"player-1\">" +
				"<placement c0=\"BLUE\" d0=\"4\" a0=\"12\" c1=\"PURPLE\" d1=\"5\" a1=\"14\"/>" +
				"<placement c0=\"BLUE\" d0=\"3\" a0=\"11\" c1=\"PURPLE\" d1=\"4\" a1=\"13\"/></result>";
		InputStream inputStream = new StringBufferInputStream(resultMessage);
		List<Placement> placements = new ArrayList<Placement> ();
		placements.add(new Placement(Color.BLUE, 4, 12, Color.PURPLE, 5, 14));
		placements.add(new Placement(Color.BLUE, 3, 11, Color.PURPLE, 4, 13));
		ResultXML actual = (ResultXML)XMLConverter.getInstance().readXml(inputStream);
		assertEquals("player-1", actual.getName());
		assertEquals(placements, actual.getPlacements());
	}

	/**
	 * test reading result message
	 */
	public void testReadTurnTestMessage1() throws Exception {
		String turnTestMessage = 
			"<turntest><turn>" +
			"<board players=\"2\">" +
				"<placement c0=\"BLUE\" d0=\"4\" a0=\"0\" c1=\"RED\" d1=\"3\" a1=\"0\" />" +
				"<placement c0=\"ORANGE\" d0=\"5\" a0=\"6\" c1=\"PURPLE\" d1=\"5\" a1=\"7\" />" +
			"</board>" +
			"<score orange=\"0\" red=\"0\" green=\"0\"yellow=\"0\" purple=\"0\" blue=\"0\" />" +
			"<tile c0=\"RED\" c1=\"RED\"/><tile c0=\"RED\" c1=\"RED\"/><tile c0=\"RED\" c1=\"RED\"/>" +
			"<tile c0=\"RED\" c1=\"RED\"/><tile c0=\"RED\" c1=\"RED\"/><tile c0=\"RED\" c1=\"RED\"/>" +
			"</turn>" +
			"<administrator><false /><true /></administrator>" +
			"<actions> " +
				"<placement c0=\"BLUE\" d0=\"5\" a0=\"3\" c1=\"RED\" d1=\"5\" a1=\"4\" />" +
				"<rerackable /> " +
			"</actions></turntest>";
		InputStream inputStream = new StringBufferInputStream(turnTestMessage);
		
		List<Tile> hand = new ArrayList<Tile>();
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		List<Placement> placements = new ArrayList<Placement>();
		placements.add(new Placement(Color.BLUE, 4, 0, Color.RED, 3, 0));
		placements.add(new Placement(Color.ORANGE, 5, 6, Color.PURPLE, 5, 7));
		Board board = new Board(2);
		for (Placement placement : placements) {
			board.place(placement);
		}
		Score score = new Score();
		
		List<IAdminResponse> responses = new ArrayList<IAdminResponse>();
		responses.add(new FalseXML());
		responses.add(new TrueXML());
		
		List<ITurnAction> actions = new ArrayList<ITurnAction>();
		actions.add(new PlacementXML(new Placement(Color.BLUE, 5, 3, Color.RED, 5, 4)));
		actions.add(new RerackableXML());
		
		TurnTestXML actual = (TurnTestXML)XMLConverter.getInstance().readXml(inputStream);
		for (int i = 0; i < actual.getActions().size(); i++) {
			ITurnAction action = actual.getActions().get(i);
			ITurnAction expected = actions.get(i);
			if (action.getClass().equals(PlacementXML.class)) {
				assertEquals(((PlacementXML)expected).getPlacement(), ((PlacementXML)action).getPlacement());
			}
			else if (action instanceof BooleanXML) {
				assertEquals(((BooleanXML)expected).getBoolean(), ((BooleanXML)action).getBoolean());
			}
			else if (action.getClass().equals(RerackableXML.class)) {
				assertEquals(RerackableXML.class, expected.getClass());
			}
		}
		for (int i = 0; i < actual.getResponses().size(); i++) {
			IAdminResponse response = actual.getResponses().get(i);
			IAdminResponse expected = responses.get(i);
			if (response.getClass().equals(TileXML.class)) {
				assertEquals(((TileXML)expected).getTile(), ((TileXML)response).getTile());
			}
			else if (response instanceof BooleanXML) {
				assertEquals(((BooleanXML)expected).getBoolean(), ((BooleanXML)response).getBoolean());
			}
			else if (response.getClass().equals(RerackXML.class)) {
				assertEquals(RerackXML.class, expected.getClass());
				assertEquals(((RerackXML)expected).getTiles(), ((RerackXML)expected).getTiles());
			}			
		}
		assertEquals(score, actual.getScore());
		assertEquals(hand, actual.getHand());
		assertEquals(board.getPlacements(), actual.getBoard().getPlacements());
	}
	
	/**
	 * test reading result message
	 */
	public void testReadTurnTestMessage2() throws Exception {
		String turnTestMessage = 
			"<turntest><turn>" +
			"<board players=\"2\">" +
				"<placement c0=\"BLUE\" d0=\"4\" a0=\"0\" c1=\"RED\" d1=\"3\" a1=\"0\" />" +
				"<placement c0=\"ORANGE\" d0=\"5\" a0=\"6\" c1=\"PURPLE\" d1=\"5\" a1=\"7\" />" +
			"</board>" +
			"<score orange=\"0\" red=\"0\" green=\"0\"yellow=\"0\" purple=\"0\" blue=\"0\" />" +
			"<tile c0=\"RED\" c1=\"RED\"/><tile c0=\"RED\" c1=\"RED\"/><tile c0=\"RED\" c1=\"RED\"/>" +
			"<tile c0=\"RED\" c1=\"RED\"/><tile c0=\"RED\" c1=\"RED\"/><tile c0=\"RED\" c1=\"RED\"/>" +
			"</turn>" +
			"<administrator><false /><true />" +
			"<rerack>" +
				"<tile c0=\"RED\" c1=\"YELLOW\"/><tile c0=\"RED\" c1=\"YELLOW\"/><tile c0=\"RED\" c1=\"YELLOW\"/>" +
				"<tile c0=\"RED\" c1=\"YELLOW\"/><tile c0=\"RED\" c1=\"YELLOW\"/><tile c0=\"RED\" c1=\"YELLOW\"/>" +
			"</rerack></administrator>" +
			"<actions> " +
				"<placement c0=\"BLUE\" d0=\"5\" a0=\"3\" c1=\"RED\" d1=\"5\" a1=\"4\" />" +
				"<rerackable /> " +
				"<rerack />" +
			"</actions></turntest>";
		InputStream inputStream = new StringBufferInputStream(turnTestMessage);
		
		List<Tile> hand = new ArrayList<Tile>();
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		hand.add(new Tile(Color.RED, Color.RED));
		List<Placement> placements = new ArrayList<Placement>();
		placements.add(new Placement(Color.BLUE, 4, 0, Color.RED, 3, 0));
		placements.add(new Placement(Color.ORANGE, 5, 6, Color.PURPLE, 5, 7));
		Board board = new Board(2);
		for (Placement placement : placements) {
			board.place(placement);
		}
		Score score = new Score();
		
		List<IAdminResponse> responses = new ArrayList<IAdminResponse>();
		responses.add(new FalseXML());
		responses.add(new TrueXML());
		List<Tile> rerackTiles = new ArrayList<Tile>();
		rerackTiles.add(new Tile(Color.RED, Color.YELLOW));
		rerackTiles.add(new Tile(Color.RED, Color.YELLOW));
		rerackTiles.add(new Tile(Color.RED, Color.YELLOW));
		rerackTiles.add(new Tile(Color.RED, Color.YELLOW));
		rerackTiles.add(new Tile(Color.RED, Color.YELLOW));
		rerackTiles.add(new Tile(Color.RED, Color.YELLOW));
		responses.add(new RerackXML(rerackTiles));
		
		List<ITurnAction> actions = new ArrayList<ITurnAction>();
		actions.add(new PlacementXML(new Placement(Color.BLUE, 5, 3, Color.RED, 5, 4)));
		actions.add(new RerackableXML());
		actions.add(new RerackXML());
		
		TurnTestXML actual = (TurnTestXML)XMLConverter.getInstance().readXml(inputStream);
		for (int i = 0; i < actual.getActions().size(); i++) {
			ITurnAction action = actual.getActions().get(i);
			ITurnAction expected = actions.get(i);
			if (action.getClass().equals(PlacementXML.class)) {
				assertEquals(((PlacementXML)expected).getPlacement(), ((PlacementXML)action).getPlacement());
			}
			else if (action instanceof BooleanXML) {
				assertEquals(((BooleanXML)expected).getBoolean(), ((BooleanXML)action).getBoolean());
			}
			else if (action.getClass().equals(RerackableXML.class)) {
				assertEquals(RerackableXML.class, expected.getClass());
			}
		}
		for (int i = 0; i < actual.getResponses().size(); i++) {
			IAdminResponse response = actual.getResponses().get(i);
			IAdminResponse expected = responses.get(i);
			if (response.getClass().equals(TileXML.class)) {
				assertEquals(((TileXML)expected).getTile(), ((TileXML)response).getTile());
			}
			else if (response instanceof BooleanXML) {
				assertEquals(((BooleanXML)expected).getBoolean(), ((BooleanXML)response).getBoolean());
			}
			else if (response.getClass().equals(RerackXML.class)) {
				assertEquals(RerackXML.class, expected.getClass());
				assertEquals(((RerackXML)expected).getTiles(), ((RerackXML)expected).getTiles());
			}			
		}
		assertEquals(score, actual.getScore());
		assertEquals(hand, actual.getHand());
		assertEquals(board.getPlacements(), actual.getBoard().getPlacements());
	}
}
