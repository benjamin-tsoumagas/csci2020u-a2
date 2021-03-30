package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Frame {

    @FXML
    public TableColumn local;
    @FXML
    public TableColumn server;
    @FXML
    public TableView localTable;
    @FXML
    public TableView serverTable;
    @FXML
    public Button download;
    @FXML
    public Button upload;

    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter networkOut = null;
    private BufferedReader networkIn = null;

    //we can read this from the user too
    public static String SERVER_ADDRESS = "localhost";
    public static int    port = 8080;

//    public Client(){
//        try{
//            socket = new Socket(SERVER_ADDRESS, port);
//        } catch (UnknownHostException e){
//            System.err.println("Unknown host: " + SERVER_ADDRESS);
//        } catch (IOException e) {
//            System.err.println("IOException while connecting to server: " + SERVER_ADDRESS);
//        }
//        if(socket==null){
//            System.err.println("Socket is null");
//        }
//        try{
//            networkOut = new PrintWriter(socket.getOutputStream(),true);
//            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        } catch (IOException e) {
//            System.err.println("IOException while opening a read/write connection");
//        }
//        in = new BufferedReader(new InputStreamReader(System.in));
//
//        boolean verified = login();
//
//        if(!verified){
//            System.exit(0);
//        }
//
//        verified = true;
//        while(verified){
//            verified = processUserInput();
//        }
//
//        try{
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    protected boolean processUserInput() {
        return true;
    }

    protected boolean login() {
        return true;
    }


    public void handleDownload(ActionEvent event) {
    }

    public void handleUpload(ActionEvent event) {
    }

    //Returns computer name
    public static String getComputerName(){

        String computerName = "null";

        try{
            InetAddress address;
            address = InetAddress.getLocalHost();
            computerName = address.getHostName();

        } catch (UnknownHostException uhe){
            uhe.printStackTrace();
        }
        System.out.println(computerName);
        return computerName;
    }

//    The client will have a simple user interface. When the client is started,
//    the computer name and shared folder path are passed as command-line arguments.
//    The client will then show a split screen showing two lists.
//    Both lists will consist of filenames.
//    On the left will be the list of all files in the shared folder of the local client.
//    On the right will be the list of files in the shared folder of the server.


}
