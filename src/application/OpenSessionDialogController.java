package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class OpenSessionDialogController {

 

	 @FXML
	    private TableView<Sesion> sesionesTable;
	 
	 @FXML
	 private TableColumn<Sesion, String> sesionColumn;

	 @FXML
	 private TableColumn<Sesion, String> fechaColumn;

	    private Sesion sesionSeleccionada;

	    public void setSesionSeleccionada(Sesion sesionSeleccionada) {
			this.sesionSeleccionada = sesionSeleccionada;
		}
	    public Sesion getSesionSeleccionada() {
	        return sesionSeleccionada;
	    }




		public void initSesionesDisponibles(ObservableList<Sesion> sesiones) {
			sesionesTable.setPlaceholder( 
				    new Label("No hay filas para mostrar"));
	        sesionesTable.setItems(sesiones);
	        sesionColumn.setCellValueFactory(new PropertyValueFactory<>("nombreSesion"));
	        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
	    }





	  @FXML
	   private void onOkButtonClicked() {
		   sesionSeleccionada = sesionesTable.getSelectionModel().getSelectedItem();
		  closeDialog();
	 }

    @FXML
    private void onCancelButtonClicked() {
        sesionSeleccionada = null;
        closeDialog();
    }

    

    private void closeDialog() {
        Stage stage = (Stage) sesionesTable.getScene().getWindow();
        stage.close();
    }

    private void displayAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
