package ingenious.hci;

import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ingenious.board.Board;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.gui.BoardGUI;
import ingenious.gui.HandGUI;
import ingenious.gui.ScoreGUI;


/**
 *  Contains factory method to create player JPanel
 */
public class GUIGameState {
		
	/**
	 * Factory constructor to create a player panel with the given data
	 */
    public static JPanel createPlayerPanel(Board board, List<Tile> hand, Score score){

    	JPanel playerPanel = new JPanel();
    	playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));

    	JPanel boardPanel = new BoardGUI(board);
    	JPanel scorePanel = new ScoreGUI(score);
    	JPanel handPanel = new HandGUI(hand);


    	JPanel scoreAndHandPanel = new JPanel();
    	scoreAndHandPanel.setLayout(new BoxLayout(scoreAndHandPanel, BoxLayout.Y_AXIS));
    	scoreAndHandPanel.add(Box.createRigidArea(new Dimension(25,50)));
    	scoreAndHandPanel.add(scorePanel);
    	scoreAndHandPanel.add(handPanel);

    	playerPanel.add(boardPanel);
    	playerPanel.add(scoreAndHandPanel);

    	return playerPanel;
    }
    
    /**
     * Display the board with default title 
     *
     */
    public static void displayBoard(Board board){
    	displayBoard(board, "Board");
    }
    
    /**
     * Display the board with custom title 
     *
     */
    public static void displayBoard(Board board, String title){
    	JPanel boardPanel = new BoardGUI(board);
		JFrame frame = new JFrame();
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(900,300);
		frame.add(boardPanel);
		frame.setVisible(true);
    }
}   
    
    
    
   

