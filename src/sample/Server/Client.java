package sample.Server;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Client extends Frame {

    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter networkOut = null;
    private BufferedReader networkIn = null;

    //we can read this from the user too
    public static String SERVER_ADDRESS = "localhost";
    public static int    port = 8080;

    public Client(){
        try{
            socket = new Socket(SERVER_ADDRESS, port);
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

        boolean verified = login();

        if(!verified){
            System.exit(0);
        }

        verified = true;
        while(verified){
            verified = processUserInput();
        }

        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Logcin function
    protected boolean login() {
        String input = null;
        String message = null;
        int errorCode = 0;

        try {
            message = networkIn.readLine(); //Welcome to chat
            System.out.println(message);
            message = networkIn.readLine(); //200 Message serves is ready
            System.out.println(message);
        } catch (IOException e) {
            System.err.println("Error reading initial greeting from socket.");
        }


        while(errorCode != 200) {
            // get userID
            System.out.print("Type your username (quit to exit): ");
            try {
                input = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equalsIgnoreCase("quit")) {
                return false;
            }
            networkOut.println("UID "+input);
            try {
                message = networkIn.readLine();
            } catch (IOException e) {
                System.err.println("Error reading response to UID.");
            }

            // get password
            System.out.print("Passcode: ");
            try {
                input = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            networkOut.println("PWD "+input);
            try {
                message = networkIn.readLine();
            } catch (IOException e) {
                System.err.println("Error reading response to UID.");
            }

            errorCode = getErrorCode(message);
            if (errorCode != 200) {
                System.out.println("Login unsuccessful: "+getErrorMessage(message));
                return false;
            }
        }
        return true;
    }

    protected boolean processUserInput() {
        String input = null;

        // print the menu
        System.out.println("Commands: ");
        System.out.println("1 - List All Messages");
        System.out.println("2 - Add New Message");
        System.out.println("3 - Quit");
        System.out.print("Command> ");

        try {
            input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (input.equals("1")) {
            listAllMessages();
        } else if (input.equals("2")) {
            addNewMessage();
        } else if (input.equals("3")) {
            return false;
        }
        return true;
    }

    protected int getErrorCode(String message) {
        StringTokenizer st = new StringTokenizer(message);
        String code = st.nextToken();
        return (new Integer(code)).intValue();
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

    public void addNewMessage() {
        String message = null;
        String input = null;

        System.out.print("Please type message: ");
        try {
            input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        networkOut.println("ADDMSG "+input);

        // read and ignore response
        try {
            message = networkIn.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from socket.");
        }
    }

    public void listAllMessages() {
        String message = null;

        networkOut.println("LASTMSG");

        // read response, store id
        int id = -1;
        try {
            message = networkIn.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from socket.");
        }
        String strID = message.substring(message.indexOf(':')+1);
        id = (new Integer(strID.trim())).intValue();
        for (int i = 0; i <= id; i++) {
            networkOut.println("GETMSG "+i);
            try {
                message = networkIn.readLine();
            } catch (IOException e) {
                System.err.println("Error reading from socket.");
            }
            int index = message.indexOf(':')+1;
            String msg = message.substring(index);
            System.out.println(msg);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }

//    The client will have a simple user interface. When the client is started,
//    the computer name and shared folder path are passed as command-line arguments.
//    The client will then show a split screen showing two lists.
//    Both lists will consist of filenames.
//    On the left will be the list of all files in the shared folder of the local client.
//    On the right will be the list of files in the shared folder of the server.


}