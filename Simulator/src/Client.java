import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Client {
	private Socket socket = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private boolean isConnected = false;

	public Client() {

	}

	public void communicate() {

		while (!isConnected) {
			try {
				socket = new Socket("192.168.13.13", 5143);
				System.out.println("Connected");
				isConnected = true;
			} catch (SocketException se) {
				se.printStackTrace();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	public void send(String str) {
		try {
			outputStream = new ObjectOutputStream(new BufferedOutputStream(
					socket.getOutputStream()));
			System.out.println("Object to be written at client = " + str);
			outputStream.writeObject(str);
			outputStream.flush();
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void receive() {
		try {
			inputStream = new ObjectInputStream(new BufferedInputStream(
					socket.getInputStream()));
			String st = (String) inputStream.readObject();
			System.out.println("Object received = " + st);

			switch (st) {
			case ("server : 123456"):
				System.out.println("correct");
				break;
			case ("123"):
				System.out.println("wrong");
				break;
			}
		} catch (SocketException se) {
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.communicate();
		client.receive();
		String string = new String("F100");
		client.send(string);
		
	}
}
