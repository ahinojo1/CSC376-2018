import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Messenger {
	static Socket socket;
	static ServerSocket serversocket;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length == 2 && args[0].equals("-l")) {
			//listen for connections
			try {
				int port = Integer.valueOf(args[1]);
				serversocket = new ServerSocket(port);
				serversocket.setReuseAddress(true);
				socket = serversocket.accept();
				
			}catch(Exception e) {
				System.err.println(e.getMessage());
				
			}
			
		}else if (args.length == 1) {
			//connect to server as a client
			int port = Integer.valueOf(args[0]);
			try {
				socket = new Socket("localhost", port);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}else {
			//wrong use
			System.err.println("Invalid arguments. Use -l option for listening mode");
		}
		
		try {
			BufferedReader usr_in = new BufferedReader(new InputStreamReader(System.in));
			DataOutputStream d_out = new DataOutputStream(socket.getOutputStream());
			DataInputStream d_in = new DataInputStream(socket.getInputStream());
			
			//create thread for receiving and displaying messages
			Receiver receiver = new Receiver(d_in);
			Thread receiverThread = new Thread(receiver);
			receiverThread.setDaemon(true);
			receiverThread.start();
			
			//let user enter and send messages
			String message = usr_in.readLine();
			while(message != null) {
				d_out.writeUTF(message);
				message = usr_in.readLine();
			}
			exitProgram();
		}catch(EOFException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void exitProgram() {
		try {
			socket.shutdownOutput();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

} 
