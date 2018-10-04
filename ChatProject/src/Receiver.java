import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

//Receiver class for ChatClient
public class Receiver implements Runnable{
	DataInputStream d_in;
	public Receiver(DataInputStream d_in) {
		this.d_in = d_in;
	}
	
	public void run() {
		try{
			String incoming = d_in.readUTF();
			while(incoming != null) {
				System.out.println(incoming);
				incoming = d_in.readUTF();
			}
			ChatClient.exitProgram();
		}catch(EOFException e) {
			ChatClient.exitProgram();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
