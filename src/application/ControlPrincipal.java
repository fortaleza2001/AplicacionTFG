package application;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ControlPrincipal {

    @FXML
    public Label estadisticaPrefol1Label;
    @FXML
    public Label estadisticaPrefol2Label;
    @FXML
    public Label estadisticaPrefol3Label;
    
    @FXML
    public Label estadisticaPrefol4Label;
    @FXML
    public Label estadisticaPrefol5Label;
    @FXML
    public Label fechaLabel;
    
    
	private List <Mano> manosActuales = new ArrayList<>();
    @FXML
    public LineChart<String, Number> grafico;

    private double estadisticaPreflop=0;
    private double estadisticaPreflop2=0;
    private double estadisticaPreflop3=0;
    private double estadisticaPreflop4=0;
    private double estadisticaPreflop5=0;
    private XYChart.Series<String, Number> beneficiosSeries;
    private Sesion sesionActual;
    private DatabaseHandler dbHandler= DatabaseHandler.getInstance();
    @FXML
    public TableView<TableRowData> tabla;

    @FXML
    public TableColumn<TableRowData, Number> numero;

    @FXML
    public TableColumn<TableRowData, TableRowData> cartas;

    @FXML
    public TableColumn<TableRowData, Number> ganancias;

    @FXML
    public TableColumn<TableRowData, Void> botones;
    
    @FXML
    public TableColumn<TableRowData, String> fecha;
    
    @FXML
    public TableColumn<TableRowData, String> numeroJugadores;

    @FXML
    public Label titulo;
    
    // Mapa que relaciona el nombre de la carta con la ruta de la imagen
    private static final Map<String, String> cartaImagenMap = new HashMap<>();

    static {
        // Agrega las cartas y sus rutas de imagen al mapa
        cartaImagenMap.put("Kh", "Kcorazones.PNG");
        cartaImagenMap.put("Kd", "Kdiamantes.PNG");
        cartaImagenMap.put("Ks", "Kpicas.PNG");
        cartaImagenMap.put("Kc", "Ktreboles.PNG");
        cartaImagenMap.put("Qh", "Qcorazones.PNG");
        cartaImagenMap.put("Qd", "Qdiamantes.PNG");
        cartaImagenMap.put("Qs", "Qpicas.PNG");
        cartaImagenMap.put("Qc", "Qtreboles.PNG");
        cartaImagenMap.put("Jh", "Jcorazones.PNG");
        cartaImagenMap.put("Jd", "Jdiamantes.PNG");
        cartaImagenMap.put("Js", "Jpicas.PNG");
        cartaImagenMap.put("Jc", "Jtreboles.PNG");
        cartaImagenMap.put("Th", "10corazones.PNG");
        cartaImagenMap.put("Td", "10diamantes.PNG");
        cartaImagenMap.put("Ts", "10picas.PNG");
        cartaImagenMap.put("Tc", "10treboles.PNG");
        cartaImagenMap.put("9h", "9corazones.PNG");
        cartaImagenMap.put("9d", "9diamantes.PNG");
        cartaImagenMap.put("9s", "9picas.PNG");
        cartaImagenMap.put("9c", "9treboles.PNG");
        cartaImagenMap.put("8h", "8corazones.PNG");
        cartaImagenMap.put("8d", "8diamantes.PNG");
        cartaImagenMap.put("8s", "8picas.PNG");
        cartaImagenMap.put("8c", "8treboles.PNG");
        cartaImagenMap.put("7h", "7corazones.PNG");
        cartaImagenMap.put("7d", "7diamantes.PNG");
        cartaImagenMap.put("7s", "7picas.PNG");
        cartaImagenMap.put("7c", "7treboles.PNG");
        cartaImagenMap.put("6h", "6corazones.PNG");
        cartaImagenMap.put("6d", "6diamantes.PNG");
        cartaImagenMap.put("6s", "6picas.PNG");
        cartaImagenMap.put("6c", "6treboles.PNG");
        cartaImagenMap.put("5h", "5corazones.PNG");
        cartaImagenMap.put("5d", "5diamantes.PNG");
        cartaImagenMap.put("5s", "5picas.PNG");
        cartaImagenMap.put("5c", "5treboles.PNG");
        cartaImagenMap.put("4h", "4corazones.PNG");
        cartaImagenMap.put("4d", "4diamantes.PNG");
        cartaImagenMap.put("4s", "4picas.PNG");
        cartaImagenMap.put("4c", "4treboles.PNG");
        cartaImagenMap.put("3h", "3corazones.PNG");
        cartaImagenMap.put("3d", "3diamantes.PNG");
        cartaImagenMap.put("3s", "3picas.PNG");
        cartaImagenMap.put("3c", "3treboles.PNG");
        cartaImagenMap.put("2h", "2corazones.PNG");
        cartaImagenMap.put("2d", "2diamantes.PNG");
        cartaImagenMap.put("2s", "2picas.PNG");
        cartaImagenMap.put("2c", "2treboles.PNG");
        
        cartaImagenMap.put("Ah", "Ascorazones.PNG");
        cartaImagenMap.put("Ad", "Asdiamantes.PNG");
        cartaImagenMap.put("As", "Aspicas.PNG");
        cartaImagenMap.put("Ac", "Astreboles.PNG");
        // Agrega más cartas según sea necesario
    }
    
    
    
    
    
    
    @FXML
    public void initialize() {
        configurarTabla();
        
        configurarGrafico();
        
       
        String sessionId =dbHandler.cargarSesion() ;
        if (sessionId != null) {
          
            this.cargarSesion(sessionId);
         
            // Restaurar el estado de la aplicación basado en la sesión cargada
        } 
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        	if(this.sesionActual!=null)
        	{
        		 dbHandler.guardarSesion(this.sesionActual.getNombreSesion());
                 
        	}
           
           
            DatabaseHandler.getInstance().disconnect();
        }));
       
    }

    private void configurarGrafico() {
      

        // Crear el gráfico de líneas
        
       

        // Configurar la serie de datos
        beneficiosSeries = new XYChart.Series<>();
        beneficiosSeries.setName("Beneficios");

        grafico.getData().add(beneficiosSeries);

       
       
    }
    
   

    private void agregarDatosGrafico() {
    
    	beneficiosSeries.getData().clear();
         SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        for (int i =0;i<manosActuales.size();i++) {
        	 Mano mano = manosActuales.get(i);
            // Obtener el timestamp y el beneficio de cada mano
            String timestamp = dateFormat.format(mano.getFecha());
            double beneficio =0;
            
            	beneficio = mano.beneficios(mano.getAcciones(),"carras01");
            
            
            
         

            // Crear un punto de datos y agregarlo a la serie
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(""+(i+1), beneficio);
            beneficiosSeries.getData().add(dataPoint);

            // Crear un Tooltip para mostrar información del punto de datos y asociarlo al nodo del punto de datos
            Tooltip tooltip = new Tooltip("Tiempo: " + timestamp + "\nBeneficio: " + String.format("%.2f", beneficio));
            Tooltip.install(dataPoint.getNode(), tooltip);
            
            
            grafico.applyCss();
            grafico.layout();
            
        }
        
    }

    private void configurarTabla() {
        // Configurar la columna de número
        numero.setCellValueFactory(cellData -> cellData.getValue().numeroProperty());
        numero.setCellFactory(column -> new TableCell<TableRowData, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setAlignment(Pos.CENTER); // Centramos el texto en la celda
                }
            }
        });

        // Configurar la columna de ganancias
        ganancias.setCellValueFactory(cellData -> cellData.getValue().gananciasProperty());
        ganancias.setCellFactory(column -> new TableCell<TableRowData, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                
                	if(item.doubleValue()>0)
                	{
                		setTextFill(Color.GREEN);// Centramos el texto en la celda
                	}
                	else if(item.doubleValue()<0)
                	{
                		setTextFill(Color.RED);// Centramos el texto en la celda
                	}
                	else
                	{
                		setTextFill(Color.BLACK);
                	}
                	 setText(String.format("%.2f", item.doubleValue()));
                    setAlignment(Pos.CENTER);
                    
                }
            }
        });

        // Configurar la columna de cartas para mostrar dos imágenes
        cartas.setCellValueFactory(cellData -> new SimpleObjectProperty<TableRowData>(cellData.getValue()));
        cartas.setCellFactory(new Callback<TableColumn<TableRowData, TableRowData>, TableCell<TableRowData, TableRowData>>() {
            @Override
            public TableCell<TableRowData, TableRowData> call(TableColumn<TableRowData, TableRowData> param) {
                return new TableCell<TableRowData, TableRowData>() {
                    @Override
                    protected void updateItem(TableRowData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            HBox hBox = new HBox(5);
                            hBox.setAlignment(Pos.CENTER); // Centramos el HBox
                            ImageView imageView1 = new ImageView(item.getImage1());
                            ImageView imageView2 = new ImageView(item.getImage2());
                            imageView1.setFitHeight(70);
                            imageView1.setFitWidth(60);
                            imageView2.setFitHeight(70);
                            imageView2.setFitWidth(60);
                            hBox.getChildren().addAll(imageView1, imageView2);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });

        // Configurar la columna de botones para abrir la ventana de mesa
        botones.setCellFactory(new Callback<TableColumn<TableRowData, Void>, TableCell<TableRowData, Void>>() {
            @Override
            public TableCell<TableRowData, Void> call(TableColumn<TableRowData, Void> param) {
                return new TableCell<TableRowData, Void>() {
                    private final Button btn = new Button("ANALIZAR");

                    {
                        btn.setOnAction(event -> {
                            TableRowData rowData = getTableView().getItems().get(getIndex());
                            System.out.println(rowData);
                           
                            abrirVentanaMesa(rowData.getNumero()-1);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            VBox vbox = new VBox(btn);
                            vbox.setAlignment(Pos.CENTER); // Centramos el VBox
                            setGraphic(vbox);
                        }
                    }
                };
            }
        });
        
     // Configurar la columna de fecha
        fecha.setCellValueFactory(cellData -> cellData.getValue().getFecha());

        fecha.setCellFactory(column -> new TableCell<TableRowData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Aquí puedes agregar lógica adicional para formatear la fecha si es necesario
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        
     // Configurar la columna de jugadores
        numeroJugadores.setCellValueFactory(cellData -> cellData.getValue().getJugadores());

        numeroJugadores.setCellFactory(column -> new TableCell<TableRowData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Aquí puedes agregar lógica adicional para formatear la fecha si es necesario
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });

    }

    private void abrirVentanaMesa(int nºmano) {
        try { 
        	
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mesa.fxml"));
            Parent root = loader.load();
            
            // Obtener el controlador de la ventana de mesa
            ControladorMesa mesaController = loader.getController();
            
            // Configurar el número de mesa en el controlador de la ventana de mesa
            mesaController.mostrarNumeroJugadores(manosActuales.get(nºmano).getNumeroJugadores());
            
            mesaController.Setear_mano(manosActuales.get(nºmano));
            
            // Crear una nueva escena con el contenido del archivo FXML
            Scene scene = new Scene(root);
            
            // Crear una nueva ventana y mostrar la escena
            Stage stage = new Stage();
            Image icono = new Image( getClass().getResource("/resources/Logo/Logo.PNG" ).toExternalForm());
        	stage.getIcons().add(icono);
            stage.setScene(scene);
            stage.setTitle("Mesa : Mano nº " +( nºmano +1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        

    private void cargarDatosEjemplo(List<Mano> manos) {
        ObservableList<TableRowData> datos = FXCollections.observableArrayList();

        for (int i = 0; i < manos.size(); i++) {
            Mano mano = manos.get(i);
            
            double beneficio =0;
            
            	beneficio = mano.beneficios(mano.getAcciones(),"carras01");
            
            
           
            List<String> cartas = mano.getCartasJugadores().get("carras01");
            
            // Suponiendo que tengas un método en la clase Mano para obtener las imágenes de las cartas
            Image carta1 = obtenerImagenCarta(cartas.get(0));
            Image carta2 =  obtenerImagenCarta(cartas.get(1));
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String fecha = dateFormat.format(mano.getFecha());
            String jugadores = "" +mano.getNumeroJugadores() +"/" + mano.getTamanoMaximoMesa();

            datos.add(new TableRowData(i + 1, carta1, carta2, beneficio,fecha,jugadores));
        }

        tabla.setItems(datos);
    }
    

 
    // Método para obtener la imagen de la carta según su nombre
    public Image obtenerImagenCarta(String nombreCarta) {
        String imagePath = getClass().getResource("/resources/Cartas/" + cartaImagenMap.get(nombreCarta)).toExternalForm();
        if (imagePath != null) {
            Image image = new Image(imagePath);
            return image;
           
        } else {
            // Devuelve una imagen de carta por defecto si el nombre de la carta no se encuentra
            Image image = new Image("file:C:/Users/juanc/Downloads/default.png");
            return image;
        }
    }
   
    @FXML
    public void onOpenSession() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OpenSessionDialog.fxml"));
            Parent root = loader.load();

            // Obteniendo la instancia del controlador
            OpenSessionDialogController controller = loader.getController();

            // Llamar al método initSesionesDisponibles
            List<Sesion> sesiones = dbHandler.obtenerTodasSesiones(); // Debes implementar este método
            ObservableList<Sesion> observableSesiones = FXCollections.observableArrayList(sesiones);
            controller.initSesionesDisponibles(observableSesiones);

            // Crear un nuevo escenario para la ventana de diálogo
            Stage dialogStage = new Stage();
            Image icono = new Image( getClass().getResource("/resources/Logo/Logo.PNG" ).toExternalForm());
            dialogStage.getIcons().add(icono);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("Sesiones Disponibles");
            dialogStage.setScene(new Scene(root));

            // Mostrar la ventana de diálogo y esperar hasta que se cierre
            dialogStage.showAndWait();
            
            Sesion sesionSeleccionada = controller.getSesionSeleccionada();
            if(sesionSeleccionada==null)
            {
            	System.out.println("No se ha seleccionado ninguna sesion.");
            	return;
            }
            
           
      
           
            cargarSesion(sesionSeleccionada.getNombreSesion());
           
          
        
            

            
          
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


 
    public void cargarSesion(String sesion)
    {
    	 
         Sesion s = this.dbHandler.obtenerSesionPorNombre(sesion);
         if(s==null)
         {
        	 System.out.println("No se encuentra la sesion en base de datos no se carga nada");
         }
         else
         {
        	 this.sesionActual =  this.dbHandler.obtenerSesionPorNombre(sesion);
             List<Mano> manos = dbHandler.obtenerManosDeSesionOrdenadasPorFecha(sesion);
             
             manosActuales = manos;
            
             SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
             String fechaFormateada = dateFormat.format(manosActuales.get(0).getFecha());
             this.fechaLabel.setText("FECHA DE LA SESION: " + fechaFormateada);

             lis();
             las();
             los();
             lol();
             lol2();
            
             cargarDatosEjemplo(manos);
             agregarDatosGrafico();
             titulo.setText(sesion);
         }
        
    }

   

    public void lis()
    {
    	double cantidad=0;
    	for(int x =0;x<manosActuales.size();x++)
    	{
    		cantidad += manosActuales.get(x).Preflop_Raise("carras01");
    	}
    	
    	 // Crear una instancia de DecimalFormat con el patrón para dos decimales
       
        
        estadisticaPreflop = (cantidad / manosActuales.size()) *100;
       

        // Formatear el resultado a dos decimales y mostrarlo
        DecimalFormat df = new DecimalFormat("0.00");
     
        estadisticaPrefol1Label.setText(" " +(df.format(estadisticaPreflop) ) +"%");
    	
    }
    
    
    public void las()
    {
    	double cantidad=0;
    	for(int x =0;x<manosActuales.size();x++)
    	{
    		cantidad += manosActuales.get(x).Preflop_3_Bet("carras01");
    	}
    	
    	 // Crear una instancia de DecimalFormat con el patrón para dos decimales
       
        
        estadisticaPreflop2 = (cantidad / manosActuales.size()) *100;
       

        // Formatear el resultado a dos decimales y mostrarlo
        DecimalFormat df = new DecimalFormat("0.00");
     
        estadisticaPrefol2Label.setText(" " +(df.format(estadisticaPreflop2) ) +"%");
    	
    }
    
    
    public void los()
    {
    	double cantidad=0;
    	for(int x =0;x<manosActuales.size();x++)
    	{
    		cantidad += manosActuales.get(x).Preflop_3_Bet_Fold("carras01");
    	}
    	
    	 // Crear una instancia de DecimalFormat con el patrón para dos decimales
       
        
        estadisticaPreflop3 = (cantidad / manosActuales.size()) *100;
       

        // Formatear el resultado a dos decimales y mostrarlo
        DecimalFormat df = new DecimalFormat("0.00");
     
        estadisticaPrefol3Label.setText(" " +(df.format(estadisticaPreflop3) ) +"%");
    	
    }


    public void lol()
    {
    	double cantidad=0;
    	for(int x =0;x<manosActuales.size();x++)
    	{
    		cantidad += manosActuales.get(x).PreflopSteal("carras01");
    	}
    	
    	 // Crear una instancia de DecimalFormat con el patrón para dos decimales
       
        
        estadisticaPreflop4 = (cantidad / manosActuales.size()) *100;
       

        // Formatear el resultado a dos decimales y mostrarlo
        DecimalFormat df = new DecimalFormat("0.00");
     
        estadisticaPrefol4Label.setText(" " +(df.format(estadisticaPreflop4) ) +"%");
    	
    }

    
    public void lol2()
    {
    	double cantidad=0;
    	for(int x =0;x<manosActuales.size();x++)
    	{
    		cantidad += manosActuales.get(x).Cbet("carras01");
    	}
    	
    	 // Crear una instancia de DecimalFormat con el patrón para dos decimales
       
        
        estadisticaPreflop5 = (cantidad / manosActuales.size()) *100;
       

        // Formatear el resultado a dos decimales y mostrarlo
        DecimalFormat df = new DecimalFormat("0.00");
     
        estadisticaPrefol5Label.setText(" " +(df.format(estadisticaPreflop5) ) +"%");
    	
    }
    
   
    @FXML
    public void abrirAyuda() {
        try {
            // Ruta del archivo de ayuda
            String rutaArchivo = "C:\\Documento\\DocumentoAyuda.pdf";

            // Crea un objeto File con la ruta del archivo
            File archivo = new File(rutaArchivo);
            System.out.println("Ruta absoluta del archivo: " + archivo.getAbsolutePath());

            // Verifica si el archivo existe
            if (archivo.exists()) {
                // Verifica si Desktop es compatible con el sistema operativo
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(archivo); // Abre el archivo con la aplicación predeterminada
                } else {
                    System.out.println("Desktop no es compatible con el sistema operativo.");
                }
            } else {
                System.out.println("El archivo de ayuda no se encuentra en la ruta especificada: " + rutaArchivo);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Maneja cualquier excepción imprevista
        }
    }


    
 
}
