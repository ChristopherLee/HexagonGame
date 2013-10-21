package ingenious.distributed;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import com.google.java.contract.Requires;

import ingenious.admin.Administrator;
import ingenious.admin.GameResult;
import ingenious.board.util.Constants;
import ingenious.distributed.ProxyPlayer.TimeOutListener;
import ingenious.suite.network.XMLConverter;
import ingenious.suite.network.RegisterXML;

public class Server {
	private final static int PLAYER_TIMEOUT = 10000; // in milliseconds
	private final Administrator admin;
	private Map<String, ProxyPlayer> proxyPlayers;
	private final int serverPort;
	@Requires({
		"numberOfPlayers >= 2",
		"numberOfPlayers <= 6"
	})
	public Server(int numberOfPlayers, int serverPort) {
		this.admin = new Administrator(numberOfPlayers);
		this.proxyPlayers = new HashMap<String, ProxyPlayer> ();
		this.serverPort = serverPort;
	}
	
	public void run() throws IOException{
		ServerSocket socket = new ServerSocket(serverPort);
		registration(socket);
		System.out.println("----------------------- runGame ---------------------------------");
		GameResult gameResult = admin.runGame();
		System.out.println(gameResult);
		socket.close();
		System.out.println("-----------------------------------------------------------------");
		closePlayerSockets();
	}
	
	private void registration(ServerSocket socket){
		while(!admin.isEnoughPlayersRegistered()) {
			try {
				Socket serviceSocket = socket.accept();
				Timer timeOutTimer = new Timer(Constants.NETWORK_TIMEOUT_INTERVAL, new TimeOutListener(serviceSocket));
				timeOutTimer.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
				RegisterXML registerXML = (RegisterXML)SocketSendUtil.receive(reader);
				timeOutTimer.stop();
				ProxyPlayer player = new ProxyPlayer(serviceSocket, reader);
				boolean registrationSuccessful = admin.register(registerXML.getName(), player);
				if (registrationSuccessful)
					proxyPlayers.put(registerXML.getName(), player);
			} catch (IOException e) {
				System.out.println("Player Failed to Register");
			}
		}
	}
	
	public void closePlayerSockets() throws IOException{
		for (ProxyPlayer player : proxyPlayers.values())
			player.close();
	}
	
	public static void main (String[] args) {
		int numberOfPlayers = Integer.parseInt(args[0]);
		int serverPort = Integer.parseInt(args[1]);
		Server server = new Server(numberOfPlayers, serverPort);
		try {
			server.run();
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
	
	class TimeOutListener implements ActionListener{
		private Socket socket;
		public TimeOutListener(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				socket.close();
			} catch (IOException e1) {
			}
		}	
	}
}
