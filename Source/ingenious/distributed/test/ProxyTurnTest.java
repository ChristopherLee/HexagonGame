package ingenious.distributed.test;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.java.contract.ContractAssertionError;

import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.distributed.ProxyTurn;
import ingenious.distributed.SocketSendUtil;
import ingenious.suite.network.TileXML;
import junit.framework.TestCase;

public class ProxyTurnTest extends TestCase{
	ProxyTurn turn1;
	@Override
	protected void setUp() throws Exception {
		ServerSocket serverSocket = new ServerSocket(8081);			
		Socket clientSocket1 = new Socket("localhost", 8081);
		Socket adminSocket1 = serverSocket.accept();
		SocketSendUtil.send(adminSocket1, new TileXML(new Tile(Color.GREEN, Color.GREEN)));
		
		Board board1 = new Board(2);
		ArrayList<Tile> hand1 = new ArrayList<Tile> ();
        hand1.add(new Tile(Color.GREEN, Color.YELLOW));
        hand1.add(new Tile(Color.GREEN, Color.YELLOW));
        hand1.add(new Tile(Color.GREEN, Color.YELLOW));
        Score score1 = new Score();
        
		turn1 = new ProxyTurn(clientSocket1, board1, hand1, score1);
	}
	
	 /**
     * Place tile not adjacent to any occupied cells
     */
    public void testPlaceFirstTurn() {
    	Placement placement = new Placement(Color.GREEN, 0, 0, Color.YELLOW, 1, 1);
        try {
            turn1.placeTile(placement);
            fail("It's not legal to put tile without any adjacent occupied cells");
        } catch (ContractAssertionError e) {
            // passed
        }
    }
    
}
