package connector;
import Global.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	//private InetAddress ipAddress;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	boolean readyToReadNext=false;
	public Client()
	{
		try 
		{
			Global.ipAddress=  InetAddress.getByName("192.168.13.13");
			Global.client=new DatagramSocket();
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*public void connect()
	{
		//Session sec=new Session(Global.client);
		//sec.start();
		System.out.println("Com have connected to server");
		
	}*/
	public void mySend(String sendString) throws IOException
	{
		String sentence = sendString;
	    System.out.println("Iam sending you sth"+ sendString);
	    sendData=sentence.getBytes();
	    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, Global.ipAddress, 5143);
	    //System.out.println("It is connected");
	    Global.client.send(sendPacket);
	}
	public String myReceive() throws IOException
	{
		//check condition for syncronization
		//everytime they have done the insturction they send us "OK"
	
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    Global.client.receive(receivePacket);
	    String modifiedSentence = new String(receivePacket.getData());
	
	    //System.out.println(modifiedSentence);
	    return modifiedSentence;
	}
}
