import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

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
			Messenger.exitProgram();
		}catch(EOFException e) {
			Messenger.exitProgram();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
