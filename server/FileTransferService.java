import java.io.*;
import java.net.Socket;

public class FileTransferService {

    private static final int BUFFER_SIZE = 4096;

    public static void receiveFile(
            Socket socket,
            String filename,
            long fileSize
    ) throws IOException {

        File dir = new File("received_files");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(dir, filename);

        try (
            InputStream in = socket.getInputStream();
            FileOutputStream fileOut = new FileOutputStream(file)
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            long remaining = fileSize;

            while (remaining > 0) {
                int read = in.read(
                        buffer,
                        0,
                        (int) Math.min(buffer.length, remaining)
                );
                if (read == -1) break;

                fileOut.write(buffer, 0, read);
                remaining -= read;
            }

            fileOut.flush();
            System.out.println("[SERVER] File received: " + filename);
        }
    }

    public static void sendFile(
            Socket socket,
            File file
    ) throws IOException {

        try (
            OutputStream out = socket.getOutputStream();
            FileInputStream fileIn = new FileInputStream(file)
        ) {
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("FILE_RECV " + file.getName() + " " + file.length());

            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            out.flush();
            System.out.println("[SERVER] File sent: " + file.getName());
        }
    }
}