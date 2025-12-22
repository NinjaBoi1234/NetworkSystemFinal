import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ChatClient {

    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            System.out.println("Usage: java ChatClient <server_ip> <port> <username>");
            return;
        }

        String serverIp = args[0];
        int port = Integer.parseInt(args[1]);
        String username = args[2];

        Socket socket = new Socket(serverIp, port);

        BufferedReader serverIn =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter serverOut =
                new PrintWriter(socket.getOutputStream(), true);

        serverOut.println("LOGIN " + username);

        new Thread(new ServerMessageReceiver(serverIn)).start();
        new Thread(new UserInputHandler(serverOut)).start();
    }
}