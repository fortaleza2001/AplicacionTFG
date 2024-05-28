package application;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SampleController3 {
    @FXML
    public ImageView c;

    @FXML
    public void initialize() {
        // Ruta relativa a la imagen en el directorio de recursos
    	
        String imagePath = getClass().getResource("/resources/Fondo/fondo.jpg").toExternalForm();
        System.out.println(imagePath);
        Image image = new Image(imagePath);
        c.setImage(image);
    }
}
