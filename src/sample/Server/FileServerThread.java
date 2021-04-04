package sample.Server;

import java.io.*;
import java.net.Socket;

public class FileServerThread implements Runnable {

    private final Socket clientSocket;
    private BufferedReader in = null;

    /**
     *
     * @param client takes a client to instantiate a socket
     */
    public FileServerThread(Socket client) {
        this.clientSocket = client;
    }

    /**
     * Awaits user input, 1 for download and 2 for upload, client closes after each
     * Otherwise, client crashes
     */
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientSelection;
            while ((clientSelection = in.readLine()) != null) {
                switch (clientSelection) {
                    case "1" -> {
                        downloadFile();
                        System.exit(0);
                    }
                    case "2" -> {
                        String outFile;
                        while ((outFile = in.readLine()) != null) {
                            uploadFile(outFile);
                            System.exit(0);
                        }
                    }
                    default -> {
                        System.err.println("Incorrect command received.");
                        System.exit(1);
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Could not read from "+sample.UI.Main.getComputerName());
        }
    }

    /**
     * Reads from target shared file and creates a local copy
     */
    public void downloadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(clientSocket.getInputStream())));
            FileWriter write = new FileWriter(sample.UI.Main.getComputerName()+ clientSocket.getInputStream());

            String line;
            while ((line = br.readLine()) != null){
                write.write(line);
                write.write("\n");
                write.flush();
            }
            write.close();
            System.out.println("File "+ clientSocket.getInputStream() +" downloaded from client");
        } catch (IOException e) {
            System.err.println("Client error. Connection closed.");
        }
    }

    /**
     *
     * @param fileName name of local file to upload
     *                 Reads from local file and creates a copy in the shared folder
     */
    public void uploadFile(String fileName) {
        try {
            File dir = new File("src/sample/Shared");

            String src = dir.getAbsolutePath() + "/" + fileName;
            String dest = dir.getAbsolutePath() + "/" + fileName;

            File f = new File(src);
            FileInputStream fin = new FileInputStream(f);
            BufferedReader in = new BufferedReader(new InputStreamReader(fin));

            FileWriter write = new FileWriter(dest);

            String line;
            while ((line = in.readLine()) != null) {
                write.write(line);
                write.write("\n");
                write.flush();
            }
            write.close();
            System.out.println("File " + fileName + " uploaded to client");

        } catch (IOException e) {
            System.err.println("Client error. Connection closed.");
        }
    }
}