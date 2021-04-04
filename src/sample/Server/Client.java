package sample.Server;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Frame {

    private Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedReader networkIn = null;
    private static String fileName = "";

    //we can read this from the user too
    public static String SERVER_ADDRESS = "localhost";
    public static int    port = 8080;

    /**
     * Creates an instance of the client class, one per user on the server
     * Instantiates a socket and attempts to read input from the user
     * Calls transfer method
     * @throws IOException if given file is invalid
     */
    public Client() throws IOException {
        try{
            socket = new Socket(SERVER_ADDRESS, port);
            in = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e){
            System.err.println("Unknown host: " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("IOException while connecting to server: " + SERVER_ADDRESS);
        }
        if(socket==null){
            System.err.println("Socket is null");
        }
        try{
            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("IOException while opening a read/write connection");
        }
        in = new BufferedReader(new InputStreamReader(System.in));

        transfer();

        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * User interface and leads to upload/download methods
     * @return String user input
     */
    protected static String transfer() {
        String input = null;

        System.out.println("Commands: ");
        System.out.println("1 - Upload");
        System.out.println("2 - Download");
        System.out.print("Command> ");

        try {
            input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert input != null;
        if (input.equals("1")) {
            downloadFile();
        }if (input.equals("2")) {
            uploadFile(fileName);
        }else{
            System.out.println("Invalid command");
            transfer();
        }
        return input;
    }

    /**
     * Copies contents of local file to file in shared folder (unfinished)
     * @param fileName name of local file user wishes to upload
     */
    public static void uploadFile(String fileName) {
        try {
            File file = new File("src/sample/Shared"+fileName);
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            FileWriter write = new FileWriter(file);

            String line;
            while ((line = br.readLine()) != null){
                write.write(line);
                write.write("\n");
                write.flush();
            }
            write.close();
            System.out.println("File "+ file +" uploaded to client");
        } catch (IOException e) {
            System.err.println("Client error. Connection closed.");
        }
    }

    /**
     * Copies contents from file in shared folder to local folder
     */
    public static void downloadFile() {
        try {
            System.out.print("Enter file name: ");
            File file = new File(networkIn.readLine());
            BufferedReader br = new BufferedReader(new FileReader(file));
            FileWriter write = new FileWriter(file);

            String line;
            while ((line = br.readLine()) != null){
                write.write(line);
                write.write("\n");
                write.flush();
            }
            write.close();
            System.out.println("File "+ file +" downloaded from client");
        } catch (IOException e) {
            System.err.println("Client error. Connection closed.");
        }
    }

    /**
     * Creates an instance of the Client object
     * @param args arguments passed by default when running main method
     * @throws IOException in case of any input/output exceptions
     */
    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }

}