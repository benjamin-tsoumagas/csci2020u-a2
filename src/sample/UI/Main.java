package sample.UI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends Application {

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

    public void handleDownload(ActionEvent event) {
    }

    public void handleUpload(ActionEvent event) {
    }

    protected boolean processUserInput() {
        return true;
    }

    protected boolean login() {
        return true;
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

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("a2.fxml"));
        primaryStage.setTitle("File Sharer v1.0");

        //Sets application icon as custom file sharer image
        Image icon = new Image("file:icon.png");
        primaryStage.getIcons().add(icon);

        getComputerName();

        primaryStage.setScene(new Scene(root, 600,600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}