package ingenious.distributed;

import java.io.IOException;
import java.net.Socket;

import ingenious.board.StateException;
import ingenious.player.IPlayer;
import ingenious.player.Player;
import ingenious.strategy.IPlayerStrategy;
import ingenious.strategy.IncreaseSmallestScoreStrategy;

/**
 * Client for distributed Ingenious games
 */
public class Client {
	private ProxyAdministrator proxyAdmin;
	private String playerName;
	private IPlayer player;
	
	public Client(String serverIpAddress, 
				  int serverPort, 
				  String playerName, 
				  IPlayerStrategy strategy){
		Socket socket;
		try {
			 socket = new Socket(serverIpAddress, serverPort);
			 this.proxyAdmin = new ProxyAdministrator(socket);
		}
		catch (IOException e) {
			System.out.println(e);
		}
		
		this.playerName = playerName;
		this.player = new Player(strategy);
	}
	
	/**
	 * register the player to the server
	 */
	public void register(){
		if (!proxyAdmin.register(playerName, player))
			System.out.println("Client could not register with admin");
	}
	
	/**
	 * close the connection to the server
	 */
	public void close() throws IOException{
		proxyAdmin.close();
	}
	
	/**
	 * run client
	 * args: Name, ServerAdress, serverPort
	 */
	public static void main(String [] args){
		String myName = args[0];
		String serverAddress = args[1];
		int serverPort = Integer.parseInt(args[2]);
		IPlayerStrategy strategy = new IncreaseSmallestScoreStrategy();
		
		Client client = new Client(serverAddress, 
								   serverPort, 
								   myName, 
								   strategy);
		client.register();
		try {
			client.proxyAdmin.runGame();
		}
		catch (StateException e) {
			System.out.println("Error in running game: " + e);
		}
		
		try {
			client.close();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
}
