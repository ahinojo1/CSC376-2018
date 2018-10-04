import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	static int port;
	static Socket socket;
	static String name;
	public static void main(String[] args) {
		if(args.length == 1) {
			port = Integer.valueOf(args[0]);
			try {
				socket = new Socket("localhost", port);
				DataOutputStream d_out = new DataOutputStream(socket.getOutputStream());
				DataInputStream d_in = new DataInputStream(socket.getInputStream());
				BufferedReader user_in = new BufferedReader(new InputStreamReader(System.in));
				
				//create Receiver thread to read and display incoming messages
				Receiver receiver = new Receiver(d_in);
				Thread receiverThread = new Thread(receiver);
				receiverThread.setDaemon(true);
				receiverThread.start();
				
				//prompt user to enter name
				System.out.println("Enter name: ");
				name = user_in.readLine();
				
				//allow user to send messages to chat server
				String message = user_in.readLine();
				while(message != null) {
					d_out.writeUTF(name + " says: " + message);
					message = user_in.readLine();
				}
				exitProgram();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
			System.err.println("Invalid use");
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
