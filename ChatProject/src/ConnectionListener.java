//import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener implements Runnable {
	int port;
	
	public ConnectionListener(int port)
	{
		this.port = port;
	}
	
	public void run() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(true) {
				Socket socket = serverSocket.accept();
				//create Data Streams for accepted socket
				//DataInputStream d_in = new DataInputStream(socket.getInputStream());
				//DataOutputStream d_out = new DataOutputStream(socket.getOutputStream());
				
				ChatServer.listOfClients.add(socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
