package ingenious.suite;

import ingenious.admin.TileBag;
import ingenious.board.Board;
import ingenious.board.StateException;
import ingenious.distributed.ProxyTurn;
import ingenious.distributed.SocketSendUtil;
import ingenious.suite.network.IAdminResponse;
import ingenious.suite.network.ITurnAction;
import ingenious.suite.network.XMLConverter;
import ingenious.turn.Turn;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.java.contract.ContractAssertionError;

public class ProxyTurnTester {
	public static void main(String[] args) {                
		ResultsXML results = execute(System.in);
		XMLConverter executor = XMLConverter.getInstance();
		String xml = executor.writeXml(results);
		System.out.println(xml);
	}

	/**
	 * Create the ProxyTurn and simulate its actions over a fake localhost socket pair
	 */
	public static ResultsXML execute(InputStream input) {
		try {
			ServerSocket serverSocket = new ServerSocket(8081);			
			Socket clientSocket = new Socket("localhost", 8081);
			Socket adminSocket = serverSocket.accept();
			
			XMLConverter executor = XMLConverter.getInstance();
			TurnTestXML turnTestXML = (TurnTestXML)executor.readXml(input);
						
			writeToAdminSocket(adminSocket, turnTestXML.getResponses());
		
			ProxyTurn turn = new ProxyTurn(clientSocket, turnTestXML.getBoard(), turnTestXML.getHand(), turnTestXML.getScore());
			ResultsXML result = simulate(turn, turnTestXML.getActions(), turnTestXML.getResponses());
			return result;
		}
		catch (IOException e) {
			System.out.println("Error: " + e);
		}
		return null;
	}
	
	/**
	 * has the proxyturn perform each action in order
	 */
	private static ResultsXML simulate(ProxyTurn turn, 
	                                   List<ITurnAction> actions, 
	                                   List<IAdminResponse> responsesLeft){		
		ResultsXML resultsXML;
		List<IAdminResponse> adminResponses = new ArrayList<IAdminResponse>();
		try{
			for (ITurnAction action : actions) {
				if (responsesLeft.isEmpty())
					throw new StateException("broken contract");
				IAdminResponse result = action.perform(turn);
				adminResponses.add(result);
				responsesLeft.remove(0);
			}
			if (turn.allowedToPlaceMoreTiles())
				throw new StateException("Did not make enough placements");
				
			resultsXML = new ResultsXML(adminResponses);
		}
		catch(ContractAssertionError e){
			resultsXML = new ResultsXML(adminResponses, e.getMessage());
		}
		catch(StateException e) {
			resultsXML = new ResultsXML(adminResponses, e.getMessage());
		}
		return resultsXML;
	}
	
	private static void writeToAdminSocket(Socket socket,
	                                       List<IAdminResponse> responses) 
										   throws IOException{
		for (IAdminResponse response : responses)
			SocketSendUtil.send(socket, response);
	}
}