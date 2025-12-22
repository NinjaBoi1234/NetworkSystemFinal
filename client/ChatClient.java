import java.net.Socket;
// To read from and write to the socket
import java.io.IOException;
// TO handle exceptions
import java.io.InputStreamReader;
//convert the byte stream to character stream
import java.io.PrintWriter;
//class to write formatted data to the stream

public class ChatClient {
    public static void main(String[] args){
        if (args.length != 3){
            System.out.println("Usage: java ChatClient <server address> <port number> <username>");
            return;
        }

        String serverIp = args[0];
        int serverPort = Integer.parseInt(args[1]);
        String username = args[2];

        Socket socket = new Socket(serverIp, serverPort);
        // Create a socket to connect to the servercan you
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        // To read user input from the console
        PrinterWriter out = new PrintWriter(socket.getOutputStream(), true);
        // To send messages to the server
        
    }
}
