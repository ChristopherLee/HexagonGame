package ingenious.gui.test;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.Placement;
import ingenious.gui.BoardGUI;
import ingenious.gui.HexagonGUI;
import junit.framework.TestCase;

public class BoardGUITest extends TestCase{
	Board twoPlayerBoard; 
	Board sixPlayerBoard;
	Board board1;
	@Override
	protected void setUp() throws Exception {
		twoPlayerBoard = new Board(2);
		sixPlayerBoard = new Board(6);
		board1 = new Board(2);
		board1.place(new Placement(Color.YELLOW, 4, 8, Color.RED, 5, 11));
		board1.place(new Placement(Color.YELLOW, 4, 16, Color.RED, 5, 19));
		board1.place(new Placement(Color.YELLOW, 4, 17, Color.RED, 4, 18));
	}
	
	public void testDrawInitialBoard2() {
		BoardGUI boardGUI = new BoardGUI(twoPlayerBoard);
		java.util.List<HexagonGUI> hexagons = boardGUI.getHexagons();
		assertTrue(91 == hexagons.size());
    }
	
	public void testDrawInitialBoard6() {
		BoardGUI boardGUI = new BoardGUI(sixPlayerBoard);
		java.util.List<HexagonGUI> hexagons = boardGUI.getHexagons();
		assertTrue(271 == hexagons.size());
    }
	

	public void testDrawInitialBoard() {
		BoardGUI boardGUI = new BoardGUI(board1);
		java.util.List<HexagonGUI> hexagons = boardGUI.getHexagons();
		assertTrue(91 == hexagons.size());
    }
	    

}
