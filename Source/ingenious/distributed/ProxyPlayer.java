package ingenious.distributed;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.google.java.contract.Requires;

import ingenious.board.Board;
import ingenious.admin.GameUpdate;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.board.Tile;
import ingenious.board.util.Constants;
import ingenious.player.StrategyException;
import ingenious.suite.network.AcceptXML;
import ingenious.suite.network.EndXML;
import ingenious.suite.network.FalseXML;
import ingenious.suite.network.PlacementXML;
import ingenious.suite.network.RerackXML;
import ingenious.suite.network.RerackableXML;
import ingenious.suite.network.TileXML;
import ingenious.suite.network.TrueXML;
import ingenious.suite.network.XMLConverter;
import ingenious.suite.network.PlayersXML;
import ingenious.suite.network.ResultXML;
import ingenious.suite.network.TurnXML;
import ingenious.player.IPlayer;
import ingenious.turn.Turn;

public class ProxyPlayer implements IPlayer{
	private final Socket socket;
	private final BufferedReader reader;
	private Timer timeOutTimer;
	private int numberOfPlayers;
	
	private enum State{RERACKABLE_RERACK, RERACK, END};
	private State state;
	
	public ProxyPlayer(Socket socket, BufferedReader reader) {
		this.socket = socket;
		this.reader = reader;
		timeOutTimer = new Timer(Constants.NETWORK_TIMEOUT_INTERVAL, new TimeOutListener());
	}
	
	public void close() throws IOException{
		socket.close();
	}

	@Override
	public void acceptNumberOfPlayersAndHand(int numberOfPlayers, List<Tile> tiles) {
		this.numberOfPlayers = numberOfPlayers;
		try {
			AcceptXML acceptPojo = new AcceptXML(numberOfPlayers, tiles);
			SocketSendUtil.send(socket, acceptPojo);
		}
		catch (IOException e) {
			throw new StateException(e.getMessage());
		}
		
	}

	@Override
	public void take(Turn turn, List<Placement> currentPlacements)
			throws StateException, StrategyException {
		try {
			if (reader.ready()) {
				throw new IOException("Player sent message/s to the admin when it was not his turn");
			}
			TurnXML turnPojo = new TurnXML(numberOfPlayers, currentPlacements, turn.getHand(), turn.getPlayerScore());
			SocketSendUtil.send(socket, turnPojo);
			timeOutTimer.start();
			playAllMoves(turn);
			rerackAndEnd(turn);
			timeOutTimer.stop();
		}
		catch (IOException e) {
			throw new StateException(e.getMessage());
		}
	}

	@Override
	public void receiveUpdate(GameUpdate update) {
		try {
			ResultXML resultPojo = new ResultXML(update.getName(), update.getPlacements());
			SocketSendUtil.send(socket, resultPojo);
		}
		catch (IOException e) {
			throw new StateException(e);
		}
	}

	@Override
	public void acceptPlayerNames(List<String> playerNames) {
		try {
			PlayersXML playersPojo = new PlayersXML(playerNames);
			SocketSendUtil.send(socket, playersPojo);
		}
		catch (IOException e) {
			throw new StateException(e);
		}
	}
	
	/**
	 *  play all required moves for the turn. 
	 *  throws state exception if message received is not a placement. 
	 */
	private void playAllMoves(Turn turn) throws IOException, StateException {
		while(turn.allowedToPlaceMoreTiles()){
			Object message = SocketSendUtil.receive(reader);
			if (!message.getClass().equals(PlacementXML.class))
				throw new StateException("Did not receive appropiate placement message");
			PlacementXML placementXML = (PlacementXML)message; 
			Tile tile = turn.placeTile(placementXML.getPlacement());
			if(tile.isNull())
				SocketSendUtil.send(socket, new FalseXML());
			else
				SocketSendUtil.send(socket, new TileXML(tile));
		}
	}
	
	/**
	 * stateful method to handle rerackable, rerack, and end messages
	 * otherwise, throw a StateException
	 *   
	 */
	@Requires({
		"turn != null"
	})
	private void rerackAndEnd(Turn turn) throws IOException {
		state = State.RERACKABLE_RERACK;
		boolean turnOver = false; 
		while(!turnOver){
			Object message = SocketSendUtil.receive(reader);
			if (message.getClass().equals(RerackableXML.class) && 
				state.equals(State.RERACKABLE_RERACK)){
				Object rerackablePojo;
				if (turn.isBagRerackable())
					rerackablePojo = new TrueXML();
				else
					rerackablePojo = new FalseXML();
				SocketSendUtil.send(socket, rerackablePojo);
				state.equals(State.RERACK);
			}
			else if (message.getClass().equals(RerackXML.class) &&
					    (state.equals(State.RERACKABLE_RERACK) || 
					     state.equals(State.RERACK))){
				turn.rerack();
				SocketSendUtil.send(socket, new RerackXML(turn.getHand()));
				state = State.END;
			}
			else if (message.getClass().equals(EndXML.class)){
				turnOver = true; 
			}
			else
				throw new StateException("Did not receive appropiate XML message");
		}	
	}
	
	/**
	 * Closes socket if the player runs out of time for the turn.  
	 */
	class TimeOutListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			timeOutTimer.stop();
			try {
				socket.close();
			} catch (IOException e1) {
			}
		}	
	}
}
