import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.*;
import java.util.ArrayList;

//Not needed anymore

//FOR SERVER TO ACCEPT CLIENT CONNECTIONS, GET NAMES, AND ADD OUTPUTSTREAMS TO LIST
public class ConnectionListener implements Runnable {
	ServerSocket ss;
	Socket s;
	DataInputStream client_in;
	DataOutputStream client_out;
	ArrayList<DataOutputStream> clientList; //let clients handle because they know their names

	public ConnectionListener(Socket socket, ArrayList<DataOutputStream> clientList) {
		this.s = socket;
		this.clientList = clientList;
	}

	public void run() {
		try {
			String client_name;
			client_in = new DataInputStream(s.getInputStream());
			client_out = new DataOutputStream(s.getOutputStream());
			clientList.add(client_out);

			client_name = client_in.readUTF();
			System.out.print("A client has connected... ");
			System.out.println(client_name);
			String message;
			while (true) {
				message = client_in.readUTF();
				if (message.charAt(0) == 'm') {
					System.out.println(message.substring(1, message.length()));
					// client_out.writeUTF("Hello World");
					for (DataOutputStream other : clientList) {
						if (other != client_out) {
							// client_out.writeUTF(message);
							other.writeUTF(message.substring(1, message.length()));
						}
					}
				} else if (message.charAt(0) == 'f') {
					String owner = client_in.readUTF();
					String filename = client_in.readUTF();
					FileRelay fileRelay = new FileRelay(owner, filename);
					Thread frThread = new Thread(fileRelay);
					frThread.start();
				} else if (message.charAt(0) == 'x') {
					clientList.remove(client_out);
				}
			}
		} catch(EOFException eof) {
			clientList.remove(client_out);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
