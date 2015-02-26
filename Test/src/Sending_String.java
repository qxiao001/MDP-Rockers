import java.util.*;

import java.io.*;
import java.net.*;

public class Sending_String{
	private static Socket con;
      String ipAddress="10.27.49.92";
      int port=7777;
      String msg="";
     
     public Sending_String(){}
      
      public void connect()
   {
   	try{
   		con= new Socket(ipAddress,port);
   	   System.out.println("This is connected to the server.");
     while(true)
   	{DataInputStream socketIn= new DataInputStream(con.getInputStream());
	  		
   		String msgSend= socketIn.readUTF();
         System.out.println("The Message sent is : "+msgSend);
   	}
   	}
   	catch(Exception e){
   		System.out.println("***"+e.getMessage()+"***\n");
   	}
   }
   public void sendMessage(String msg)
{
		try{
		DataOutputStream socketOut= new DataOutputStream(con.getOutputStream());
		
				socketOut.writeUTF(msg);

		if(msg.equalsIgnoreCase("Terminate"))
			System.exit(0);
	}
	catch(IOException e)
	{
		System.out.println("Could not listen to prot 7777.\n");
	}
}

    public static void main(String[] args)
   {
      String msgSent="";
      Scanner sc=new Scanner(System.in);
      Sending_String here=new Sending_String();
      here.connect();
      
      
           
           
   }

}