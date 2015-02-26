import java.io.IOException;
import java.io.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                socket = new Socket("localHost", 4445);
                System.out.println("Connected");
                isConnected = true;
                outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
               
               // Student student = new Student(1, "2006 ACE");
                //System.out.println("Object to be written = " + student);
                //outputStream.writeObject(student);
              String string =new String("123456");
               System.out.println("Object to be written = " + string);
                outputStream.writeObject(string);
                
                
              /*  ArrayList<String>  strings =new ArrayList<String>();
                strings.add("123");
                strings.add("321");
               System.out.println("Object to be written = " + strings.get(0)+"/"+strings.get(1));
                outputStream.writeObject(strings);  */
                
                
                outputStream.flush();
 
            } catch (SocketException se) {
                se.printStackTrace();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    public static void main(String[] args) {
        Client client = new Client();
        client.communicate();
    }
}
