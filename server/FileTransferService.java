import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileTransferService {

    private static final int BUFFER_SIZE = 4096;

    public static void sendFile(String path, OutputStream rawOut) {

        File file = new File(path);

        PrintWriter out = new PrintWriter(rawOut, true);

        if (!file.exists() || !file.isFile()) {
            out.println("[SERVER] File not found: " + path);
            return;
        }

        out.println("[FILE_START] " + file.getName() + " " + file.length());

        try (FileInputStream fis = new FileInputStream(file)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                rawOut.write(buffer, 0, bytesRead);
            }

            rawOut.flush();
            out.println();
            out.println("[FILE_END]");

        } catch (IOException e) {
            out.println("[SERVER] File transfer error: " + e.getMessage());
        }
    }
}