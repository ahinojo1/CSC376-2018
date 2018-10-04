import java.io.*;
import java.net.*;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;

public class ChatServer {
	static int port;
	static ServerSocket ss;
	static Socket s;
	static DataInputStream socket_in; 
	//public static Map<Socket, String> clientList = new HashMap<Socket, String>();
	public static ArrayList<DataOutputStream> clientList = new ArrayList<DataOutputStream>();
	
	private static void beginServer() {
		//TODO run client connection listener and message listener
		System.out.println("Server running...");
		try {
			ss = new ServerSocket(port);
			while(true) {
				s = ss.accept();
				ConnectionListener connectionListener = new ConnectionListener(s, clientList);
				Thread clThread = new Thread(connectionListener);
				clThread.start();
				//MessageRelay messageRelay = new MessageRelay(s, clientList);
				//Thread mrThread = new Thread(messageRelay);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	public static void main(String[] args) {
		if(args.length == 1) {
			try {
				port = Integer.valueOf(args[0]);
				
				beginServer();
			}catch(Exception e) {
				System.out.println( e.getMessage() );
			}
		}else {
			System.err.println("Usage: java ChatServer <port number>");
		}
	}
}
