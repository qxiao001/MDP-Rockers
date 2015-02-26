import java.io.*;
import java.net.*;

public class Client {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 5678);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        sentence = inFromUser.readLine();
        while (sentence.equals("exit")){
     //   sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println(modifiedSentence); 
        sentence = inFromUser.readLine();
        }
        
        clientSocket.close();
    }
}