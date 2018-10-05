import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import java.net.Socket;
import java.util.List;


public class ConnectionListener implements Runnable {
	int port;
	Socket socket;
	String name;
	String message;
	DataInputStream d_in;
	DataOutputStream d_out;
	List<DataOutputStream> listOfClients;
	
	public ConnectionListener(Socket socket, List<DataOutputStream> listOfClients)
	{
		this.socket = socket;
		this.listOfClients = listOfClients;
	}
	
	
	public void run() {
		System.out.println("Server connection/message listener running");
		try {
			d_in = new DataInputStream(socket.getInputStream());
			d_out = new DataOutputStream(socket.getOutputStream());
			listOfClients.add(d_out);
			
			name = d_in.readUTF();
			
			System.out.println("Now connected with " + name);
			while(true) {
				message = d_in.readUTF();
				System.out.println(name + ": " + message);
				for(DataOutputStream o : listOfClients) {
					if(o != d_out) {
						o.writeUTF(name + " says: " + message);
					}
				}
				
			}
		} catch(EOFException eof){
			listOfClients.remove(d_out);
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
