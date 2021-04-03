package sample.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class FileServer{

    protected ServerSocket fileServerSocket = null;
    protected Socket clientSocket = null;
    protected FileServerThread[] threads = null;
    protected int numClients = 0;
    //change this
    protected Vector messages = new Vector();

    private static int port = 8080;
    private static String path = "";
    public static int clientCap = 25;

    public FileServer(){
        try {
            fileServerSocket = new ServerSocket(port);
            System.out.println("---------------------------");
            System.out.println("File Server Application is running");
            System.out.println("---------------------------");
            System.out.println("Listening to port: "+ port);
            System.out.println("---------------------------");
            System.out.println("Computer name: "+ sample.UI.Main.getComputerName());
            System.out.println("---------------------------");
            System.out.println("Shared file path: "+ path);
            threads = new FileServerThread[clientCap];
            while(true){
                clientSocket = fileServerSocket.accept();
                System.out.println("Client #" + (numClients+1) + " connected.");
                //change this
                threads[numClients] = new FileServerThread(clientSocket, messages);
                threads[numClients].start();
                numClients++;
            }
        } catch (IOException e) {
            System.out.println("IOException while creating server connection.");
        }
    }

    public static void main(String[] args){
        //port to listen default 8080, or the port from the argument
        if (args.length > 0){
            if(args[0] != null){
                try {
                    port = Integer.parseInt(args[0]);
                } catch (NumberFormatException e){
                    System.err.println("Invalid Port ID");
                }
            } else {
                port = 8080;
            }
            if(args[2] != null){
                path = args[2];
            } else {
                path = "No given path";
            }
        }
        FileServer server = new FileServer();
    }
    //Server takes commands DIR, UPLOAD(filename), and DOWNLOAD(filename)
    //DIR returns a listing of the contents of the shared folder, on the server's machine
    //The server will disconnect immediately after sending the list of files to the client
    //UPLOAD immediately after the newline after this command will be the contents of a text file
    //The server will connect the text from this text file, and save it as new file filename
    //The server will disconnect immediately after saving the text file's contents
    //DOWNLOAD the server will load the text from the text file filename,
    //and will immediately send that text to the client and disconnect

    //Server must be multithreaded and doesn't need a UI
    //Each incoming client conn should be handled with a separate thread (ClientConnectionHandler)
    //This thread and corresponding socket stays open only until the command is handled
}