import java.util.*;
import java.io.*;
import java.net.*;

//FOR SERVER TO TAKE MESSAGE FROM CLIENT AND SEND TO ALL OTHERS
public class MessageRelay implements Runnable {
	Socket s;
	DataOutputStream client_output;
	ArrayList<DataOutputStream> clientList;
	String msg;
	
	public MessageRelay(Socket s, DataOutputStream out,  ArrayList<DataOutputStream> clientList,String msg) {
		this.s = s;
		client_output = out;
		this.msg = msg;
	}
	public void run() {
		try {
			DataInputStream client_in;
			client_in = new DataInputStream(s.getInputStream());
			while(true) {
				msg = client_in.readUTF();
				for (DataOutputStream other: clientList) {
					if(other != client_output) {
						client_output.writeUTF(msg);
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
