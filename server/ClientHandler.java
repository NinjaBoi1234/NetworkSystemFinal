import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true
            );

            // ---- Username handshake ----
            out.println("Enter username:");
            String username = in.readLine();

            if (username == null || username.isBlank()) {
                socket.close();
                return;
            }

            out.println("[SERVER] Welcome " + username);

            // ---- Main message loop ----
            String line;
            while ((line = in.readLine()) != null) {

                // quit command
                if (line.equalsIgnoreCase("/quit") ||
                    line.equalsIgnoreCase("exit")) {
                    out.println("[SERVER] Bye!");
                    break;
                }

                // Echo back to client
                out.println(username + ": " + line);

                // Log on server
                System.out.println("[" + username + "] " + line);
            }

        } catch (IOException e) {
            System.out.println("[SERVER] Client error: " + e.getMessage());

        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}

            System.out.println("[SERVER] Client disconnected");
        }
    }
}