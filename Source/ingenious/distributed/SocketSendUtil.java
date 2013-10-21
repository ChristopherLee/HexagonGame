package ingenious.distributed;

import ingenious.suite.network.XMLConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.thoughtworks.xstream.io.xml.CompactWriter;

public class SocketSendUtil {
	/**
	 * parse the next message on the bufferedReader into an Object from XML
	 */
	public static Object receive(BufferedReader reader) throws IOException{
		XMLConverter executor = XMLConverter.getInstance();
		//System.out.println("Waiting for message");
		String line = reader.readLine();
		if(line == null)
			throw new IOException("Got a null line in receive");
		Object pojo = executor.readXml(line);
		//System.out.println("receive: " + pojo);
		return pojo;
	}

	/**
	 * send the given object as an XML message over the socket
	 */
	public static void send(Socket socket, Object pojo) throws IOException{
		XMLConverter executor = XMLConverter.getInstance();
		String xml = executor.writeXml(pojo);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		//System.out.println("send: " + xml);
		out.println(xml);
		out.flush();
	}
}
