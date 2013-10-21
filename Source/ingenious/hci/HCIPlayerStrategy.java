package ingenious.hci;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.strategy.IPlayerStrategy;
import ingenious.hci.HCIInput;

/**
 * Strategy class to allow human user input
 */
public class HCIPlayerStrategy implements IPlayerStrategy{
	private final int timeout;
	
	/**
	 * creates an HCIPlayerStrategy with a time limit of timeout (in seconds)
	 */
	public HCIPlayerStrategy(int timeout){
		this.timeout = timeout; 
	}

	
	/**
	 * returns a valid placement that the user provides, or throws a TimerException
	 * if the user does not input a valid placement within the given time frame 
	 */
	@Override
	public Placement getAction(Board board, 
							   List<Tile> playerHand,
							   Score playerScore) {
		
		HCIInput input = new HCIInput(board, playerHand, playerScore,  timeout, this);
		input.display();
		
		synchronized(this) {
			try {
				wait();
			}
			catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		
		// HCIInput will notify "this" when either a player has timed out or submitted a valid placement
		input.close();
		if (!input.isTimedOut())
			return input.getPlacement();
		else {
			throw new TimerException();
		}
	
	}
	
	public boolean wantToRerack(Board board, 
			   					List<Tile> playerHand,
			   					Score playerScore){
		
		JPanel playerPanel = GUIGameState.createPlayerPanel(board, playerHand, playerScore);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setSize(950,700);
		frame.add(playerPanel);
		frame.setVisible(true);
		int result = JOptionPane.showConfirmDialog(frame, "Would you like to rerack?", "Rerack?", JOptionPane.YES_NO_OPTION);
		frame.setVisible(false);
		return result == 0 ? true : false;
			
	}
			
}
