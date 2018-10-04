import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	static int port;
	public static List<Socket> listOfClients = new ArrayList<Socket>();

	public static void main(String[] args) {
		if(args.length == 1) {
			port = Integer.valueOf(args[0]);
			//threaded listener for client connections
			createListener();
			//message relay
			createRelay();
		}else {
			System.err.println("Invalid use");
		}

	}
	
	public static void createListener() {
		ConnectionListener connectionListener = new ConnectionListener(port);
		Thread listenerThread = new Thread (connectionListener);
		listenerThread.setDaemon(true);
		listenerThread.start();
	}
	
	public static void createRelay() {
		while(true) {
			
			for (Socket s : listOfClients) {
				try {
					DataInputStream d_in = new DataInputStream(s.getInputStream());
					String messageToSend = d_in.readUTF();
					
					for (Socket t : listOfClients) {
						if(!t.equals(s)) {
							DataOutputStream d_out = new DataOutputStream(s.getOutputStream());
							d_out.writeUTF(messageToSend);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}

}
