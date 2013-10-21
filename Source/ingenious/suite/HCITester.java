package ingenious.suite;

import java.io.InputStream;

import javax.swing.SwingUtilities;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.StateException;
import ingenious.hci.HCIPlayerStrategy;
import ingenious.hci.TimerException;
/**
 * takes in a player xml format on the standard.in which contains board, hand, score
 * allows a human to input a placement, prints <timeout /> or <placement> 
 */
public class HCITester {
    
    public static void main(String[] args) {
		  execute(System.in);		        
    }
    
    /*
     * Organizes the hand score and board panels
     */
    public static void execute(InputStream input) {
    	HCIScriptExecutor executor = HCIScriptExecutor.getInstance();
        
        HCIXML pojo = (HCIXML) executor.readXml(input);
        try {
        	HCIPlayerStrategy strategy = new HCIPlayerStrategy(pojo.getTimeout());
        	/*Player player = new Player(strategy);
        	List<Tile> tileBag = new ArrayList<Tile>();
        	Turn turn = new Turn(getBoard(pojo), pojo.getPlayerHand(), pojo.getPlayerScore(), tileBag);
        	
        	player.startGame(pojo.getNumberOfPlayers(), new ArrayList<String>(), pojo.getPlayerHand());
        	try {
        		player.take(turn, getBoard(pojo).getPlacements());
        	}
        	catch () {
        		
        	}*/
        	Placement placement = strategy.getAction(getBoard(pojo), pojo.getPlayerHand(), pojo.getPlayerScore());
        	String str = "<placement c0=\"" + placement.getTile().getColor0() + "\"" +
        						   " d0=\"" + placement.getCoordinate0().getDistance() + "\"" +
        						   " a0=\"" + placement.getCoordinate0().getAngle() + "\"" +
        						   " c1=\"" + placement.getTile().getColor1() + "\"" +
        						   " d1=\"" + placement.getCoordinate1().getDistance() + "\"" +
        						   " a1=\"" + placement.getCoordinate1().getAngle() + " />";
        	System.out.println(str);
        	//executor.writeXml(placement);
        }
        catch (TimerException e) {
        	System.out.println("<timeout />");
        	//System.out.println(executor.writeXml(e));
        }
    }
    
    private static Board getBoard(HCIXML pojo) throws StateException{
    	Board board = new Board(pojo.getNumberOfPlayers());
    	
    	for (Placement placement : pojo.getCurrentPlacements()) {
    		board.place(placement);
    	}
    	return board;
    }
}
    