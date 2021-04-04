package sample.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer{

    protected ServerSocket fileServerSocket = null;
    protected Socket clientSocket = null;

    private static int port = 8080;

    /**
     * Constructs the server and gives information like port number, computer name, and shared file path
     * Will accept client connections while active and start the corresponding thread
     * Otherwise produces an IOException
     */
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
            System.out.println("Shared file path: "+ sample.UI.Main.getPathName());
            while(true){
                clientSocket = fileServerSocket.accept();
                System.out.println("Client " + sample.UI.Main.getComputerName() + " connected.");
                Thread thread = new Thread(new FileServerThread(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("IOException while creating server connection.");
        }
    }

    /**
     * If using command line, port number can be changed from default(8080)
     * Creates an instance of the server class
     */
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
        }
        FileServer server = new FileServer();
    }
}