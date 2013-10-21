package ingenious.distributed;

import ingenious.admin.TileBag;
import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.NullTile;
import ingenious.board.Placement;
import ingenious.board.PlacementLocation;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.board.util.Constants;
import ingenious.suite.network.BooleanXML;
import ingenious.suite.network.PlacementXML;
import ingenious.suite.network.TileXML;
import ingenious.suite.network.XMLConverter;
import ingenious.suite.network.RerackXML;
import ingenious.suite.network.RerackableXML;
import ingenious.suite.network.FalseXML;
import ingenious.turn.ITurn;
import ingenious.turn.Turn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public class ProxyTurn extends Turn{
	private final Socket socket;
	private final BufferedReader reader;
		
	public ProxyTurn(Socket socket, 
	                 Board board, 
	                 List<Tile> playerHand, 
	                 Score playerScore) throws IOException{
		super(board, playerHand, playerScore, new TileBag(0));
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Requires({
		"!kickFlag",
		"checkLegalPlacement(placement)"
	})
	@Ensures("result != null")
	@Override
	public Tile placeTile(Placement placement) {
		super.placeTile(placement);
		Tile tileFromAdmin;
		try {
			SocketSendUtil.send(socket, new PlacementXML(placement));
			Object tileResponse = SocketSendUtil.receive(reader);
			if (tileResponse.getClass().equals(TileXML.class)) {
				tileFromAdmin = ((TileXML)tileResponse).getTile();
				playerHand.add(tileFromAdmin);
			}
			else if(tileResponse.getClass().equals(FalseXML.class)) {
				tileFromAdmin = new NullTile();	
			}
			else {
				throw new IOException("Did not receive correct tile response from admin.");
			}
		} catch (IOException e) {
			System.out.println(e);
			tileFromAdmin = new NullTile();
		}
		return tileFromAdmin;
	}

	@Requires({
		"!kickFlag",
		"checkRerack()",
		"!allowedToPlaceMoreTiles()"
	})
	@Override
	public void rerack() {
		RerackXML rerackXML = null;
		try {
			SocketSendUtil.send(socket, new RerackXML(new ArrayList<Tile> ()));
			rerackXML = (RerackXML)SocketSendUtil.receive(reader);
			
			hasReracked = true;
			playerHand.clear();
			playerHand.addAll(rerackXML.getTiles());			
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public boolean isBagRerackable() {
		boolean rerackable = false;
		try {
			SocketSendUtil.send(socket, new RerackableXML());

			Object message = SocketSendUtil.receive(reader);
			if (message instanceof BooleanXML)
				rerackable = ((BooleanXML)message).getBoolean();
			else
				throw new IOException("Expected a boolean message but received something else");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		return rerackable;
	}
}
