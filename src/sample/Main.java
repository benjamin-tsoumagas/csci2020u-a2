package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("File Sharer v1.0");

        //Sets application icon as custom file sharer image
        Image icon = new Image("file:icon.png");
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(new Scene(root, 600,600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
