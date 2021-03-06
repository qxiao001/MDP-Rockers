
/**
 *
 * @author Qianru
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream inStream = null;
	private ObjectOutputStream outStream = null;

	public Server() {

	}

	public void communicate() {

		try {
			serverSocket = new ServerSocket(4445);
			socket = serverSocket.accept();
			System.out.println("Connected");

		} catch (SocketException se) {
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send() {
		try {
			outStream = new ObjectOutputStream(new BufferedOutputStream(
					socket.getOutputStream()));

			String string = new String("server : 123456");
			System.out.println("Object to be written at server = " + string);
			outStream.writeObject(string);
			
			outStream.flush();
			
		} catch (SocketException se) {
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receive() {
		try {
			inStream = new ObjectInputStream(new BufferedInputStream(
					socket.getInputStream()));
			String st = (String) inStream.readObject();
			System.out.println("Object received = " + st);

			switch (st) {
			case ("123456"):
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
		Server server = new Server();
		server.communicate();
		server.send();
		server.receive();
	}
}