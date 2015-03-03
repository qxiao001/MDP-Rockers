package connector;

import java.net.DatagramSocket;
import java.net.Socket;

class Session extends Thread{
	private DatagramSocket client;
	public Session(DatagramSocket c)
	{
		client=c;
	}
	public void run()
	{
		try
		{
			System.out.println("I am a session and it is running.");
			//Receive String
			//update map
			//call explore function
			//send back action
			//receive move finished ack
			
			
			//DataInputStream socketIn =
		}
		catch (Exception e)
		{
			System.out.println("Got some error in session.");
		}
	}
}
