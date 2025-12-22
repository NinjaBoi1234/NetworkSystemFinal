import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientSession implements Runnable {

    private final BufferedReader in;
    private final PrintWriter out;
    private final String username;
    private final MessageDispatcher dispatcher;

    public ClientSession(
            String username,
            BufferedReader in,
            PrintWriter out,
            MessageDispatcher dispatcher
    ) {
        this.username = username;
        this.in = in;
        this.out = out;
        this.dispatcher = dispatcher;
    }

    public String getUsername() {
        return username;
    }

    public void send(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {

                if (line.equalsIgnoreCase("QUIT")) {
                    break;
                }

                dispatcher.dispatch(username, line);
            }
        } catch (IOException e) {
            System.out.println("[SERVER] Connection lost: " + username);
        } finally {
            dispatcher.unregister(username);
            out.close();
            try {
                in.close();
            } catch (IOException ignored) {}
        }
    }
}