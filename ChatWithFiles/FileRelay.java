//FOR SERVER TO ASK FOR FILE FROM OWNER AND SEND TO REQUESTING CLIENT
public class FileRelay implements Runnable {
	String owner, file_name;
	public FileRelay(String owner, String file_name) {
		this.owner = owner;
		this.file_name = file_name;
	}
	public void run() {
		System.out.println("File relay thread received owner: " + owner + " and file name: " + file_name);
	}
}