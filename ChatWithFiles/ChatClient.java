import java.io.*;
import java.net.*;

public class ChatClient {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static String name;
	static Socket socket;
	static DataInputStream socket_in;
	static DataOutputStream socket_out;
	static boolean running = true;
	
	private static void intro() {
		System.out.println("Please enter your name: ");
		try {
			name = br.readLine();
			//sends name in connect()
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void connect(int p) {
		try {
			socket = new Socket("localhost", p);
			socket_in = new DataInputStream(socket.getInputStream());
			socket_out = new DataOutputStream(socket.getOutputStream());
			socket_out.writeUTF(name);
			socket_out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void clientMenu() {
		System.out.println("Enter an option ('m', 'f', 'x'):");
		System.out.println("(M)essage (send)");
		System.out.println("(F)ile (request)");
		System.out.println("e(x)it");
		//TODO read in user option and run corresponding method
		try {
			String op = br.readLine();
			if(op.equalsIgnoreCase("m")) {
				messageOption();
			}
			else if(op.equalsIgnoreCase("f")) {
				fileOption();
			}else if(op.equalsIgnoreCase("x")) {
				exitOption();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void messageOption() {
		//TODO ask for message to be sent
		//TODO also write code for thread to listen for messages (might go somewhere else)
		System.out.println("Enter your message: ");
		try {
			String message = br.readLine();
			socket_out.writeUTF("m" + name + ": " + message);
			socket_out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void fileOption() {
		//TODO ask for file owner and file name
		//TODO also write code for thread to receive files
		
		try {
			socket_out.writeUTF("f");
			socket_out.flush();
			System.out.println("Who owns the file? ");
			String owner = br.readLine();
			System.out.println("Which file do you want? ");
			String filename = br.readLine();
			socket_out.writeUTF(owner);
			socket_out.writeUTF(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void exitOption() throws IOException {
		running = false;
		socket_out.writeUTF("x");
		socket.shutdownOutput();
		socket.close();
		System.exit(0);
	}
	private static void createListener() {
		MessageListener listener = new MessageListener(socket_in);
		Thread listenerThread = new Thread(listener);
		listenerThread.start();
	}
	public static void main(String[] args) {
		if(args.length == 4 && args[0].equals("-l") && args[2].equals("-p")) {
			try {
				int port = Integer.valueOf(args[3]);
				int l_port = Integer.valueOf(args[1]);
				intro();
				System.out.println("Hello, " + name);
				connect(port);
				//TODO create listening thread here
				createListener();
				while(running) {
					clientMenu();
				}
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.err.println("Usage: ");
		}
	}
}
