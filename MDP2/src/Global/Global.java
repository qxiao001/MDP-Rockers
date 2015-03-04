package Global;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import connector.Client;

public class Global {
	
	public static int[][] robotMap = new int[20][15];
	public static int[][] realMap= new int[20][15];
	public static int[][] exploreMap= new int[20][15];
	public static int  currCX = 18, currCY = 1, currFX = 17, currFY = 1;
	//we need to declare the start point also :)
	public static int senseRange = 3;
	public static int goalX = 1, goalY = 13;
	public static int port=7777;
	public static int backlock=40;
	public static InetAddress ipAddress;
	public static InetAddress addr ;//InetAddress.getByName("64.69.35.190");
	public static ServerSocket sc;
	public static DatagramSocket client;
	public static Client c= new Client();
	public static String moveForward="F010";
	public static String turnLeft="L000";
	public static String turnRight="R000";
	
}
