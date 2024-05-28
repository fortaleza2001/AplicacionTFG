package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
        	DatabaseHandler.getInstance().connect();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Image icono = new Image( getClass().getResource("/resources/Logo/Logo.PNG" ).toExternalForm());
            primaryStage.getIcons().add(icono);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Ejecutando Sample.fxml");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        
        super.stop();
    }
}

