package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controller {

    @FXML
    public TableColumn shared;
    @FXML
    public TableColumn local;
    @FXML
    public Button download;
    @FXML
    public Button upload;
    @FXML
    public TableView localTable;
    @FXML
    public TableView sharedTable;

    public void handleDownload(ActionEvent event) {
    }

    public void handleUpload(ActionEvent event) {
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

//    The client will have a simple user interface. When the client is started,
//    the computer name and shared folder path are passed as command-line arguments.
//    The client will then show a split screen showing two lists.
//    Both lists will consist of filenames.
//    On the left will be the list of all files in the shared folder of the local client.
//    On the right will be the list of files in the shared folder of the server.

}
