


/**
 *
 * @author qianru
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
 
 
    public Server() {
 
    }
 
    public void communicate() {
        
        try {
            serverSocket = new ServerSocket(4445);
            socket = serverSocket.accept();
            System.out.println("Connected");
            
            Student student1 = new Student(1, "Ace");
            
     
            inStream = new ObjectInputStream(new BufferedInputStream (socket.getInputStream()));
            //Student student = (Student) inStream.readObject();
            //System.out.println(student.getName());
            //System.out.println("Object received = " + student);
            
            String st= (String)inStream.readObject();
            System.out.println("Object received = " + st);
            
            switch(st){
                case ("123456"): System.out.println("correct"); break;
                case ("123"): System.out.println("wrong"); break;
        
        
              }
                   
            
           // ArrayList<String> st= (ArrayList<String>)inStream.readObject();
           // System.out.println("Object received = " + st.get(0)+"/"+st.get(1));
        
            
            socket.close();
 
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
    }
}