import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.io.IOException;

public class ChatServer {

    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Please provide a port number as an argument.");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Chat server started on port" + port);
            // Continuously accept new client connections
            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
            // Handle each client connection in a new thread
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
            }
        
        }
        catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
        
    }
    
}
