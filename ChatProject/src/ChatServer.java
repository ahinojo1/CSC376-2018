import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	static int port;
	static Socket socket;
	public static List<DataOutputStream> listOfClients = new ArrayList<DataOutputStream>();

	public static void main(String[] args) {
		if(args.length == 1) {
			port = Integer.valueOf(args[0]);
			//threaded listener for client connections
			createListener();

		}else {
			System.err.println("Invalid use");
		}

	}
	
	@SuppressWarnings("resource")
	public static void createListener() {
		try {
			
			ServerSocket serverSocket = new ServerSocket(port);
			while(true) {
				socket = serverSocket.accept();
				System.out.println("Client connected");
				ConnectionListener connectionListener = new ConnectionListener(socket, listOfClients);
				Thread clThread = new Thread(connectionListener);
				clThread.setDaemon(true);
				clThread.start();
			}
		} catch(EOFException e) {
			System.exit(0);
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
	}

}
