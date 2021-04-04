package sample.Server;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Client extends Frame {

    private Socket socket = null;
    private static BufferedReader in = null;
    private PrintWriter networkOut = null;
    private static BufferedReader networkIn = null;
    private static String fileName = "";
    private PrintStream os = null;

    //we can read this from the user too
    public static String SERVER_ADDRESS = "localhost";
    public static int    port = 8080;

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
            networkOut = new PrintWriter(socket.getOutputStream(),true);
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
    protected static String transfer() throws IOException{
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
        } else if (input.equals("2")) {
            uploadFile(fileName);
        }
        return input;
    }

    protected int getErrorCode(String message) {
        StringTokenizer st = new StringTokenizer(message);
        String code = st.nextToken();
        return Integer.parseInt(code);
    }

    protected String getErrorMessage(String message) {
        StringTokenizer st = new StringTokenizer(message);
        String code = st.nextToken();
        String errorMessage = null;
        if (st.hasMoreTokens()) {
            errorMessage = message.substring(code.length()+1, message.length());
        }
        return errorMessage;
    }

    public static void uploadFile(String fileName) {

    }

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
            System.out.println("File "+ file +" received from client");
        } catch (IOException e) {
            System.err.println("Client error. Connection closed.");
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }

//    The client will have a simple user interface. When the client is started,
//    the computer name and shared folder path are passed as command-line arguments.
//    The client will then show a split screen showing two lists.
//    Both lists will consist of filenames.
//    On the left will be the list of all files in the shared folder of the local client.
//    On the right will be the list of files in the shared folder of the server.


}