//package connector;
//import Global.*;
//import java.util.*;
//import java.io.*;
//import java.net.*;
//
//public class Server {
//	
//	public Server(int port,int backlog)
//	{
//		try
//		{
//			Global.sc=new ServerSocket(port,backlog);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	public void connect()
//	{
//		try
//		{
////			while(true)
////			{
////			I assume only one client will be connected it is Raspberry Pi 
//				System.out.println("I can call the method.");
//				System.out.println("**"+InetAddress.getLocalHost()+"**\n");
//				Global.client=Global.sc.accept();
//				Session sec=new Session(Global.client);
//				System.out.println("Rpi have connected to server");
//				sec.start();
//				
////			}
//		}
//		catch (IOException e)
//		{
//			
//		}
//	}
//}
