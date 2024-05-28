package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TableRowData {
    private final SimpleIntegerProperty numero;
    private final SimpleObjectProperty<Image> image1;
    private final SimpleObjectProperty<Image> image2;
    private final SimpleDoubleProperty ganancias;
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty jugadores;


    public TableRowData(int numero, Image image1, Image image2, double ganancias,String fecha,String jugadores) {
        this.numero = new SimpleIntegerProperty(numero);
        this.image1 = new SimpleObjectProperty<>(image1);
        this.image2 = new SimpleObjectProperty<>(image2);
        this.ganancias = new SimpleDoubleProperty(ganancias);
        this.fecha = new SimpleStringProperty(fecha);
        this.jugadores = new SimpleStringProperty(jugadores);
    }

    
    public SimpleStringProperty getJugadores() {
		return jugadores;
	}


	public SimpleStringProperty getFecha() {
		return fecha;
	}

	public int getNumero() {
        return numero.get();
    }

    public SimpleIntegerProperty numeroProperty() {
        return numero;
    }

    public Image getImage1() {
        return image1.get();
    }

    public SimpleObjectProperty<Image> image1Property() {
        return image1;
    }

    public Image getImage2() {
        return image2.get();
    }

    public SimpleObjectProperty<Image> image2Property() {
        return image2;
    }

    public double getGanancias() {
        return ganancias.get();
    }

    public SimpleDoubleProperty gananciasProperty() {
        return ganancias;
    }
}

