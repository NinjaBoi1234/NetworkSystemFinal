import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class UserInputHandler implements Runnable {

    private PrintWriter serverOut;

    public UserInputHandler(PrintWriter serverOut) {
        this.serverOut = serverOut;
    }

    @Override
    public void run() {
        try {
            BufferedReader userIn =
                    new BufferedReader(new InputStreamReader(System.in));

            String input;
            while ((input = userIn.readLine()) != null) {
                serverOut.println(input);

                if (input.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("[CLIENT] Error reading user input.");
        }
    }
}