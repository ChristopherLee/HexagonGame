package ingenious.suite;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import com.google.java.contract.ContractAssertionError;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.gui.BoardGUI;
import ingenious.gui.HandGUI;
import ingenious.gui.ScoreGUI;
import ingenious.player.Player;
import ingenious.player.StrategyException;
import ingenious.strategy.PlayerStrategy;
import ingenious.suite.PlayerResult;
import ingenious.suite.TurnXML;
import ingenious.turn.Turn;

public class GUITester {
    
    public static void main(String[] args) {
    	SwingUtilities.invokeLater( new Runnable() {
            public void run() {
		    	JPanel playerPanel = execute(System.in);
		    	JFrame frame = new JFrame();
		    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    	frame.setSize(new Dimension(900, 650));
		    	frame.setContentPane(playerPanel);
		    	frame.setVisible(true);
		        
		    }
    	});
    }
    /*
     * Organizes the hand score and board panels
     */
    public static JPanel execute(InputStream input) {
        //HexagonGUI hexagon = new HexagonGUI();
        GUIScriptExecutor executor = GUIScriptExecutor.getInstance();
        
        GUIXML pojo = (GUIXML) executor.readXml(input);
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
        
        JPanel boardPanel = drawBoard(pojo);
        JPanel scorePanel = drawScore(pojo);
        JPanel handPanel = drawHand(pojo);
        
        
        JPanel scoreAndHandPanel = new JPanel();
        scoreAndHandPanel.setLayout(new BoxLayout(scoreAndHandPanel, BoxLayout.Y_AXIS));
        scoreAndHandPanel.add(Box.createRigidArea(new Dimension(25,50)));
        scoreAndHandPanel.add(scorePanel);
        scoreAndHandPanel.add(handPanel);
        
        
        playerPanel.add(boardPanel);
        playerPanel.add(scoreAndHandPanel);
        

        
        return playerPanel;
    }
    
    /*
     * instantiates Board object from xml input
     */
    private static Board createBoard(GUIXML pojo) throws ContractAssertionError{
        Board board = new Board(pojo.getNumberOfPlayers());
        for (Placement placement : pojo.getCurrentPlacements()) {
            board.place(placement);
        }
        return board;
    }
    
    private static JPanel drawBoard(GUIXML pojo) {
    	Board board = createBoard(pojo);
    	return new BoardGUI(board);
    }
    
    private static JPanel drawScore(GUIXML pojo) {
    	return new ScoreGUI(pojo.getPlayerScore());
    }
    
    private static JPanel drawHand(GUIXML pojo) {
    	return new HandGUI(pojo.getPlayerHand());
    	
    }

}

