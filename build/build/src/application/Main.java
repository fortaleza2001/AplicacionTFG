package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML desde el paquete application
            Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));

            // Crear la escena
            Scene scene = new Scene(root);

            // Establecer la escena en el escenario principal
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ejecutando Sample.fxml");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
