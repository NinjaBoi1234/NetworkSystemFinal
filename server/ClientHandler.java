import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // 入出力ストリームの準備
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Welcome to the Chat Server!");
            out.println("Type 'exit' to quit.");

            String line;

            // クライアントからの入力を待ち続ける
            while ((line = in.readLine()) != null) {

                // 終了コマンド
                if (line.equalsIgnoreCase("exit")) {
                    out.println("Goodbye!");
                    break;
                }

                // とりあえず echo（後で broadcast / DM に変更）
                System.out.println(
                        "[CLIENT " + clientSocket.getRemoteSocketAddress() + "] " + line
                );
                out.println("Server received: " + line);
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        } finally {
            // 後始末
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing socket");
            }
        }
    }
}