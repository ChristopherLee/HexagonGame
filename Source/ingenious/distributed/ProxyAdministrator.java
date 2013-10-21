package ingenious.distributed;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import ingenious.admin.GameResult;
import ingenious.admin.GameUpdate;
import ingenious.admin.IAdministrator;
import ingenious.admin.PlayerRecord;
import ingenious.admin.GameState;
import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.StateException;
import ingenious.player.IPlayer;
import ingenious.player.Player;
import ingenious.player.StrategyException;
import ingenious.suite.network.AcceptXML;
import ingenious.suite.network.EndXML;
import ingenious.suite.network.ResultXML;
import ingenious.suite.network.PlayersXML;
import ingenious.suite.network.RegisterXML;
import ingenious.suite.network.TurnXML;
import ingenious.turn.Turn;

/**
 * Class to facilitate messages between the server and the client
 */
public class ProxyAdministrator implements IAdministrator{
	private Socket socket; 
	private Player player;
	private BufferedReader reader;
	private GameState state;
	
	public ProxyAdministrator(Socket socket) {
		this.socket = socket;
		state = GameState.REGISTRATION;
	}
	
	/**
	 * close the connection to the server
	 */
	public void close() throws IOException{
		socket.close();
	}

	@Override
	/**
	 * register new player and give them their initial hand
	 */
	public boolean register(String name, IPlayer player) {
		try {
	        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			RegisterXML registerPojo = new RegisterXML(name);
	        SocketSendUtil.send(socket, registerPojo);
	        
	        AcceptXML acceptPojo = (AcceptXML)SocketSendUtil.receive(reader);
	        
	        player.acceptNumberOfPlayersAndHand(acceptPojo.getNumberOfPlayers(), 
	                                            acceptPojo.getTiles());
		}
		catch (IOException e) {
			System.out.println(e);
		}
		this.player = (Player)player;
		return true;
	}

	@Override
	/**
	 * loops for duration of game, passing along messages to player  
	 */
	public GameResult runGame() {
		state = GameState.RUNGAME;
		try {
			PlayersXML playersXML = (PlayersXML)SocketSendUtil.receive(reader);
			player.acceptPlayerNames(playersXML.getNames());
			
			while(player.getBoard().getAvailablePositionsForTile().size() != 0) {
				Object message = SocketSendUtil.receive(reader);
				if (message.getClass().equals(ResultXML.class)) {
					ResultXML resultXML = (ResultXML)message;
					player.receiveUpdate(new GameUpdate(resultXML.getName(), 
					                                    resultXML.getPlacements()));
				}
				else if (message.getClass().equals(TurnXML.class)) {
					TurnXML turnXML = (TurnXML)message;
					playTurn(turnXML);
				}
				else {
					System.out.println("Received unknown message");
				}
			}
		}
		catch(IOException e) {
			throw new StateException(e);
		}
		catch(Exception e) {
			throw new StateException(e);
		}
		state = GameState.GAMEOVER;
		// we don't know game result so we return an empty result
		return new GameResult(new ArrayList<PlayerRecord>());
	}
	
	/**
	 * Creates a ProxyTurn to communicate with a player over network. 
	 */
	private void playTurn(TurnXML turnXML) throws IOException{
		Board board = new Board(turnXML.getNumberOfPlayers());
		for (Placement placement : turnXML.getCurrentPlacements())
			board.place(placement);
		Turn proxyTurn = new ProxyTurn(socket, 
									   board, 
									   turnXML.getPlayerHand(), 
									   turnXML.getPlayerScore());
		try {
			player.take(proxyTurn, turnXML.getCurrentPlacements());
		}
		catch (StrategyException e) {
			System.out.println("Player did not make enough moves, strategy exception");
		}
		SocketSendUtil.send(socket, new EndXML());

	}

	@Override
	public boolean isGameInRegistration() {
		return state == GameState.REGISTRATION;
	}

	@Override
	public boolean isEnoughPlayersRegistered() {
		return true;
	}

}
