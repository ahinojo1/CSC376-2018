import java.io.*;
//FOR CLIENT TO TAKE IN MESSAGES FROM SERVER
public class MessageListener implements Runnable {
	DataInputStream socket_in;
	String msg;
	public MessageListener(DataInputStream in) {
		socket_in = in;
	}
	public void run() {
		try {
			while(true) {
				msg = socket_in.readUTF();
				System.out.println(msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
