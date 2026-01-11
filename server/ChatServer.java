import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChatServer <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[SERVER] ChatServer started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[SERVER] New client connected");

                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            }

        } catch (Exception e) {
            System.out.println("[SERVER] Error: " + e.getMessage());
        }
    }
}