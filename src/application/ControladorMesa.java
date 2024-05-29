package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ControladorMesa {

	@FXML
	public Label boteEtiqueta;
	private double boteCantidad=0;
	
	private Mano manoActual;
	private int punteroAccion=0;
	
    // Círculos de los jugadores
    @FXML
    public Circle jugador1Circle;
    @FXML
    public Circle jugador2Circle;
    @FXML
    public Circle jugador3Circle;
    @FXML
    public Circle jugador4Circle;
    @FXML
    public Circle jugador5Circle;
    @FXML
    public Circle jugador6Circle;
    @FXML
    public Circle jugador7Circle;
    @FXML
    public Circle jugador8Circle;

    // Arreglo que contiene los círculos de los jugadores
    private Circle[] jugadoresCircle;

    // Cartas de los jugadores
    @FXML
    public Rectangle jugador1Carta1;
    @FXML
    public Rectangle jugador1Carta2;
    @FXML
    public Rectangle jugador2Carta1;
    @FXML
    public Rectangle jugador2Carta2;
    @FXML
    public Rectangle jugador3Carta1;
    @FXML
    public Rectangle jugador3Carta2;
    @FXML
    public Rectangle jugador4Carta1;
    @FXML
    public Rectangle jugador4Carta2;
    @FXML
    public Rectangle jugador5Carta1;
    @FXML
    public Rectangle jugador5Carta2;
    @FXML
    public Rectangle jugador6Carta1;
    @FXML
    public Rectangle jugador6Carta2;
    @FXML
    public Rectangle jugador7Carta1;
    @FXML
    public Rectangle jugador7Carta2;
    @FXML
    public Rectangle jugador8Carta1;
    @FXML
    public Rectangle jugador8Carta2;
    
 // Matriz que contiene las cartas de los jugadores
    private Rectangle[][] jugadoresCartas;
    
    
    @FXML
    public Label jugador1Nombre;
    @FXML
    public Label jugador2Nombre;
    @FXML
    public Label jugador3Nombre;
    @FXML
    public Label jugador4Nombre;
    @FXML
    public Label jugador5Nombre;
    @FXML
    public Label jugador6Nombre;
    @FXML
    public Label jugador7Nombre;
    @FXML
    public Label jugador8Nombre;
  

    // Arreglo que contiene los círculos de los jugadores
    private Label[] jugadoresNombres;

    @FXML
    public Label jugador1Accion;
    @FXML
    public Label jugador2Accion;
    @FXML
    public Label jugador3Accion;
    @FXML
    public Label jugador4Accion;
    @FXML
    public Label jugador5Accion;
    @FXML
    public Label jugador6Accion;
    @FXML
    public Label jugador7Accion;
    @FXML
    public Label jugador8Accion;

    // Arreglo que contiene los círculos de los jugadores
    private Label[] jugadoresAcciones;
    
    
    
    @FXML
    public Rectangle cartaFlop1;
    @FXML
    public Rectangle cartaFlop2;
    @FXML
    public Rectangle cartaFlop3;
    @FXML
    public Rectangle cartaTurn;
    @FXML
    public Rectangle cartaRiver;
    
     Map<String ,Double> Botee = new HashMap<>();
    
    private  double raisemano =-1;

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
        cartaImagenMap.put(" ", "Volteada.PNG");
        // Agrega más cartas según sea necesario
    }
    
    boolean enseñado=false;

    // Método para obtener la imagen de la carta según su nombre
    public ImagePattern obtenerImagenCarta(String nombreCarta) {
        String imagePath = getClass().getResource("/resources/Cartas/" + cartaImagenMap.get(nombreCarta)).toExternalForm();
        if (imagePath != null) {
            Image image = new Image(imagePath);
            return new ImagePattern(image);
        } else {
            // Devuelve una imagen de carta por defecto si el nombre de la carta no se encuentra
            Image image = new Image("file:C:/Users/juanc/Downloads/default.png");
            return new ImagePattern(image);
        }
    }

    @FXML
    public void initialize() {
    	initallItems();
    	
    	
    }
    
    
    public void initallItems()
    {
    	// Construir el arreglo de círculos de los jugadores
        jugadoresCircle = new Circle[]{
            jugador1Circle, jugador2Circle, jugador3Circle, jugador4Circle,
            jugador5Circle, jugador6Circle, jugador7Circle, jugador8Circle
        };

        // Construir la matriz de cartas de los jugadores
        jugadoresCartas = new Rectangle[][]{
            {jugador1Carta1, jugador1Carta2}, {jugador2Carta1, jugador2Carta2},
            {jugador3Carta1, jugador3Carta2}, {jugador4Carta1, jugador4Carta2},
            {jugador5Carta1, jugador5Carta2}, {jugador6Carta1, jugador6Carta2},
            {jugador7Carta1, jugador7Carta2}, {jugador8Carta1, jugador8Carta2}
        };
        
      

        jugadoresNombres = new Label[]{
        		jugador1Nombre, jugador2Nombre, jugador3Nombre, jugador4Nombre,
        		jugador5Nombre, jugador6Nombre, jugador7Nombre, jugador8Nombre
            };
        
        jugadoresAcciones = new Label[]{
        		jugador1Accion, jugador2Accion, jugador3Accion, jugador4Accion,
        		jugador5Accion, jugador6Accion, jugador7Accion, jugador8Accion
            };
        // Ruta a tu imagen (asegúrate de usar el prefijo "file:")
        String imagePath = getClass().getResource("/resources/Jugador/Jugador.png" ).toExternalForm();
      

        // Cargar la imagen
        Image image = new Image(imagePath);

        // Crear un ImagePattern con la imagen
        ImagePattern imagePattern = new ImagePattern(image);

        // Inicializar cada jugador
        for (int i = 0; i < jugadoresCircle.length; i++) {
            // Configurar el círculo del jugador
            jugadoresCircle[i].setFill(imagePattern);

            // Configurar las cartas del jugador
            jugadoresCartas[i][0].setFill(obtenerImagenCarta("Ah"));
            jugadoresCartas[i][1].setFill(obtenerImagenCarta("Ac"));
        }
    }
    
    public void mostrarNumeroJugadores(int numeroJugadores) {
        for (int i = 0; i < jugadoresCircle.length; i++) {
            if (i < numeroJugadores) {
                jugadoresCircle[i].setVisible(true);
                for (int j = 0; j < 2; j++) {
                    jugadoresCartas[i][j].setVisible(true);
                }
                jugadoresNombres[i].setVisible(true);
                jugadoresAcciones[i].setVisible(true);
            } else {
                jugadoresCircle[i].setVisible(false);
                for (int j = 0; j < 2; j++) {
                    jugadoresCartas[i][j].setVisible(false);
                }
                jugadoresNombres[i].setVisible(false);
                jugadoresAcciones[i].setVisible(false);
            }
        }
    }

    
    
    
    
    public void Setear_mano(Mano mano) {
        this.manoActual = mano;
        this.manoActual.maquetarPosiciones();
        
        setearNombresCartas(mano);
        actualizarGUIConAccion(mano.getAcciones().get(punteroAccion));
      
        
    }
    
    @FXML
    private void avanzarAccion() {
    	if(punteroAccion==manoActual.getAcciones().size()-1)
    	{
    		
    	}
    	else
    	{
    		punteroAccion++;
    		AccionJugador accion = manoActual.getAcciones().get(punteroAccion);
            actualizarGUIConAccion(accion);
    	}
        
    }

    @FXML
    private void retrocederAccion() {
    	if(punteroAccion==0)
    	{
    		
    	}
    	else
    	{
    		punteroAccion--;
    		reset();
    		for(int x=0;x<punteroAccion+1;x++)
    		{
    			AccionJugador accion = manoActual.getAcciones().get(x);
                actualizarGUIConAccion(accion);
    		}
    		
    	}
    }

    private void actualizarGUIConAccion(AccionJugador accion) {
        if (accion == null) {
            return;
        }
        
        if(accion.getTipoAccion().equals("checks"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("CHECK");
        		}
        		
        	}
        }
        
        if(accion.getTipoAccion().equals("folds"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("FOLD");
        		}
        		
        	}
        }
        if(accion.getTipoAccion().equals("bets"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("BET : " + accion.getCantidad());
        			
        			//boteCantidad  += accion.getCantidad();
        			this.Botee.put(accion.getNombreJugador(), accion.getCantidad());
        		}
        		
        	}
        }
         
        if(accion.getTipoAccion().equals("raises"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("RAISE : " + accion.getCantidad());
        			raisemano=  accion.getCantidad();
        			//boteCantidad  += accion.getCantidad();
        			this.Botee.put(accion.getNombreJugador(), accion.getCantidad());
        		}
        		
        	}
        }
        if(accion.getTipoAccion().equals("calls"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{
        			double valor = accion.getCantidad();
        			//boteCantidad += valor;
        			if(raisemano!=-1)
        			{
        				
        				valor =raisemano;
        				
        			}
        			else
        			{
        				
        				
        			}
        			
        			this.Botee.put(accion.getNombreJugador(), valor);
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("CALL : " + valor);
        			
        		}
        		
        	}
        }
        
        if(accion.getTipoAccion().equals("posts small"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("POST SMALL BLIND : " + accion.getCantidad());
        			
        			//boteCantidad+= accion.getCantidad();
        			this.Botee.put(accion.getNombreJugador(), accion.getCantidad());
        		}
        		
        	}
        	
        }
        
        if(accion.getTipoAccion().equals("posts big"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{raisemano=  accion.getCantidad();
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("POST BIG BLIND : " + accion.getCantidad());
        			
        			//boteCantidad+= accion.getCantidad();
        			this.Botee.put(accion.getNombreJugador(), accion.getCantidad());
        		}
        		
        	}
        	
        }
        
        if(accion.getTipoAccion().equals("all-In"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{raisemano=  accion.getCantidad();
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("All-IN: " + accion.getCantidad());
        			
        			//boteCantidad+= accion.getCantidad();
        			this.Botee.put(accion.getNombreJugador(), accion.getCantidad());
        		}
        		
        	}
        	
        }
        
        if(accion.getTipoAccion().equals("Win"))
        {
        	for(int x =0;x<this.manoActual.getPosiciones().size();x++)
        	{
        		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
        		{
        			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("Ganador del bote : ("+ accion.getCantidad()  +")  ");
        			
        			
        		}
        		
        	}
        	
        }
        
         
       
        
          if(accion.getTipoAccion().equals("DRAW - FLOP"))
        {
        	
        	cartaFlop1.setFill(obtenerImagenCarta(accion.getCartas().get(0).replace("[", "")));
        	
        	cartaFlop2.setFill(obtenerImagenCarta(accion.getCartas().get(1)));
        	cartaFlop3.setFill(obtenerImagenCarta(accion.getCartas().get(2).replace("]", "").trim()));
        	
        	for(int x =0;x<this.Botee.keySet().toArray().length;x++)
        	{
        		boteCantidad += this.Botee.get(this.Botee.keySet().toArray()[x]);
        	}
        	Botee.clear();
        	boteEtiqueta.setText(String.format("%.2f", boteCantidad));


        	
        	raisemano=-1;
        	borraracciones();
        }
        
          if(accion.getTipoAccion().equals("DEAL - TURN"))
        {
        	cartaTurn.setFill(obtenerImagenCarta(accion.getCartas().get(0).replace("[", "").replace("]", "")));
        	for(int x =0;x<this.Botee.keySet().toArray().length;x++)
        	{
        		boteCantidad += this.Botee.get(this.Botee.keySet().toArray()[x]);
        	}
        	Botee.clear();
        	boteEtiqueta.setText(String.format("%.2f", boteCantidad));
        	raisemano=-1;
        	
        	borraracciones();
        }
        
          if(accion.getTipoAccion().equals("DEAL - River"))
        {
        	cartaRiver.setFill(obtenerImagenCarta(accion.getCartas().get(0).replace("[", "").replace("]", "")));
        	for(int x =0;x<this.Botee.keySet().toArray().length;x++)
        	{
        		boteCantidad += this.Botee.get(this.Botee.keySet().toArray()[x]);
        	}
        	Botee.clear();
        	boteEtiqueta.setText(String.format("%.2f", boteCantidad));
        	raisemano=-1;
        	borraracciones();
        }
          if(accion.getTipoAccion().contains("SHOW"))
          {
        	  
        	  if(enseñado==false)
    			{
    				borraracciones();
    				enseñado =true;
    				for(int i =0;i<this.Botee.keySet().toArray().length;i++)
                	{
                		boteCantidad += this.Botee.get(this.Botee.keySet().toArray()[i]);
                	}
    				Botee.clear();
                	boteEtiqueta.setText(String.format("%.2f", boteCantidad));
    			}
        	  
        	  for(int x =0;x<this.manoActual.getPosiciones().size();x++)
          	{
          		if(((String)this.manoActual.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador()))
          			
          		{	
          			
          			
          			jugadoresAcciones[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1].setText("SHOW");
          			jugadoresCartas[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1][0].setFill(obtenerImagenCarta(accion.getCartas().get(0).replace("[", "")));
                    jugadoresCartas[manoActual.getPosiciones().get(((String)this.manoActual.getPosiciones().keySet().toArray()[x]))-1][1].setFill(obtenerImagenCarta(accion.getCartas().get(1).replace("]", "")));
                   
          		}
          		
          		
            	
          		
          	}
        	  
        	  boteEtiqueta.setText(String.format("%.2f", boteCantidad));
          	
          	raisemano=-1;
          }
        
        
        
        
    }

    
    private void setearNombresCartas(Mano mano)
    {
    	for (int x = 0; x < mano.getPosiciones().size(); x++) {
    	    jugadoresNombres[mano.getPosiciones().get(((String) mano.getPosiciones().keySet().toArray()[x]))-1].setText(((String) mano.getPosiciones().keySet().toArray()[x]));
    	    
    	    
    	    if(((String) mano.getPosiciones().keySet().toArray()[x]).equals("carras01") )
    	    {
    	    	
    	    	List<String> cartas = mano.getCartasJugadores().get((String) mano.getPosiciones().keySet().toArray()[x]);
                
                // Suponiendo que tengas un método en la clase Mano para obtener las imágenes de las cartas
    	    	ImagePattern carta1 = obtenerImagenCarta(cartas.get(0));
    	    	ImagePattern carta2 =  obtenerImagenCarta(cartas.get(1));
                jugadoresCartas[mano.getPosiciones().get("carras01")-1][0].setFill(carta1);
                jugadoresCartas[mano.getPosiciones().get("carras01")-1][1].setFill(carta2);
                
                
    	    }
    	    else
    	    {
    	    	 // Suponiendo que tengas un método en la clase Mano para obtener las imágenes de las cartas
    	    	ImagePattern carta1 = obtenerImagenCarta(" ");
    	    	ImagePattern carta2 =  obtenerImagenCarta(" ");
                jugadoresCartas[mano.getPosiciones().get(((String) mano.getPosiciones().keySet().toArray()[x]))-1][0].setFill(carta1);
                jugadoresCartas[mano.getPosiciones().get(((String) mano.getPosiciones().keySet().toArray()[x]))-1][1].setFill(carta2);
    	    }
    	    
    	    
    	}
    	
    	ImagePattern carta = obtenerImagenCarta(" ");

    	cartaFlop1.setFill(carta);
    	cartaFlop2.setFill(carta);
    	cartaFlop3.setFill(carta);
    	cartaTurn.setFill(carta);
    	cartaRiver.setFill(carta);

    }
    
 // Método para obtener la imagen de la carta según su nombre
    public Image obtenerImagenCarta_(String nombreCarta) {
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
    
    
    public void borraracciones()
    {
    	for(int x =0;x<jugadoresAcciones.length;x++)
    	{
    		if(jugadoresAcciones[x].getText().equals("FOLD")==false)
    		{
    			jugadoresAcciones[x].setText("");
    		}
    	}
    	this.enseñado=false;
    }
    
    public void reset()
    {
    	raisemano=-1;
    	for(int x =0;x<jugadoresAcciones.length;x++)
    	{
    		
    			jugadoresAcciones[x].setText("");
    		
    	}
    	
    	ImagePattern carta = obtenerImagenCarta(" ");

    	cartaFlop1.setFill(carta);
    	cartaFlop2.setFill(carta);
    	cartaFlop3.setFill(carta);
    	cartaTurn.setFill(carta);
    	cartaRiver.setFill(carta);
    	
    	boteCantidad=0;
    	boteEtiqueta.setText("");
    	setearNombresCartas(manoActual);
    }
    

}
