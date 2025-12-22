import java.io.BufferedReader;
import java.io.IOException;

public class ServerMessageReceiver implements Runnable {

    private BufferedReader serverIn;

    public ServerMessageReceiver(BufferedReader serverIn) {
        this.serverIn = serverIn;
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = serverIn.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("[CLIENT] Disconnected from server.");
        }
    }
}