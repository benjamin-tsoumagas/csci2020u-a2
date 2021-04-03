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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    /**
     * @param event activates when user presses 'download' button
     *              Tells user the button has been pressed
     */
    public void handleDownload(ActionEvent event) {
        System.out.println("Downloading Files");
        //Download files from right table(shared) to left table(local)
        //Transfer is a copy of every character in the file
    }

    /**
     * @param event activates when user presses 'upload' button
     *              Tells user the button has been pressed
     */
    public void handleUpload(ActionEvent event) {
        System.out.println("Uploading Files");
        //Upload files from left table(local) to right table(shared)
        //Transfer is a copy of every character in the file
    }

    /**
     *
     * @return computerName of the person running this program
     */
    public static String getComputerName(){

        String computerName = "---";

        try{
            InetAddress address;
            address = InetAddress.getLocalHost();
            computerName = address.getHostName();

        } catch (UnknownHostException uhe){
            uhe.printStackTrace();
        }
        return computerName;
    }

    /**
     *
     * @return get the absolute path to the shared folder relative to the user
     */
    public static String getPathName(){

        return (new File("src/sample/shared").getAbsolutePath());
    }

    /**
     *
     * @param primaryStage takes a stage which displays the UI
     * @throws Exception in case there is no fxml file to read from/load
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        //Loads .fxml file and sets stage title
        Parent root = FXMLLoader.load(getClass().getResource("a2.fxml"));
        primaryStage.setTitle("File Sharer v1.0");

        //Sets application icon as custom file sharer image
        Image icon = new Image("file:icon.png");
        primaryStage.getIcons().add(icon);

        getPathName();

        //sets scene as contents of fxml with specified width and height
        primaryStage.setScene(new Scene(root, 600,600));

        //displays stage
        primaryStage.show();
    }

    /**
     *
     * @param args takes VM arguments from run configuration to display a user interface window
     */
    public static void main(String[] args) {
        launch(args);
    }
}