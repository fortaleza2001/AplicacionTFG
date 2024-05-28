package application;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Mano {
	private String idSesion;
    private int numeroJugadores;
    private int tamanoMaximoMesa;
    private List<String> flop;
    private String turn;
    private String river;
    private String modoJuego;
    private List<AccionJugador> acciones;
    private Map<String, List<String>> cartasJugadores = new HashMap<>();;
    private Map<String, Integer> posiciones = new HashMap<>(); ;
    private Map<String, Double> stacks = new HashMap<>();;
    
    public Map<String, Double> getStacks() {
		return stacks;
	}

	public void setStacks(Map<String, Double> stacks) {
		this.stacks = stacks;
	}

	private int button;
    private Date fecha;
    
    
 

	public int getButton() {
		return button;
	}

	public void setButton(int button) {
		this.button = button;
	}

	
	public Map<String, Integer> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(Map<String, Integer> posiciones) {
		this.posiciones = posiciones;
	}



    public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Mano() {
        flop = new ArrayList<>();
        acciones = new ArrayList<>();
        cartasJugadores = new HashMap<>();
        posiciones = new HashMap<>(); // Inicializa el Map vacío
    }

    // Getters y Setters

    public int getNumeroJugadores() {
        return numeroJugadores;
    }

    public void setNumeroJugadores(int numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
    }

    public int getTamanoMaximoMesa() {
        return tamanoMaximoMesa;
    }

    public void setTamanoMaximoMesa(int tamanoMaximoMesa) {
        this.tamanoMaximoMesa = tamanoMaximoMesa;
    }

    public List<String> getFlop() {
        return flop;
    }

    public void setFlop(List<String> flop) {
        this.flop = flop;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getRiver() {
        return river;
    }

    public void setRiver(String river) {
        this.river = river;
    }

    public String getModoJuego() {
        return modoJuego;
    }

    public void setModoJuego(String modoJuego) {
        this.modoJuego = modoJuego;
    }

    public List<AccionJugador> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<AccionJugador> acciones) {
        this.acciones = acciones;
    }

    public Map<String, List<String>> getCartasJugadores() {
        return cartasJugadores;
    }

    public void setCartasJugadores(Map<String, List<String>> cartasJugadores) {
        this.cartasJugadores = cartasJugadores;
    }

    // Otros métodos

    public void procesarAccionJugador(String nombreJugador, double cantidad, String tipoAccion) {
        acciones.add(new AccionJugador(nombreJugador, cantidad, tipoAccion));
    }

    public void agregarCartasJugador(String nombreJugador, List<String> cartas) {
        cartasJugadores.put(nombreJugador, cartas);
    }

    
    public void agregarPosicionJugador(String nombreJugador, int posicion) {
        posiciones.put(nombreJugador, posicion);
    }
    
    public void agregarStackJugador(String nombreJugador, double cantidad) {
        stacks.put(nombreJugador, cantidad);
    }
    @Override
    public String toString() {
        return "Mano{" +
        		"fecha=" + fecha +" ,"+
        		"botton=" + button +" ,"+
        		"Stacks=" + stacks +" ,"+
        		"posiciones=" + posiciones +" ,"+
                "numeroJugadores=" + numeroJugadores +
                 ", fecha=" + fecha +
                ", tamanoMaximoMesa=" + tamanoMaximoMesa +
                ", flop=" + flop +
                ", turn='" + turn + '\'' +
                ", river='" + river + '\'' +
                ", modoJuego='" + modoJuego + '\'' +
                ", acciones=" + acciones +
                ", cartasJugadores=" + cartasJugadores +
                '}';
    }

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}
	
	public double calcularGananciasEntreManos( Mano mano2, String nombreJugador) {
	    // Verificar si el jugador está presente en ambas manos
	    if (!this.getStacks().containsKey(nombreJugador) || !mano2.getStacks().containsKey(nombreJugador)) {
	        System.out.println("El jugador " + nombreJugador + " no está presente en ambas manos.");
	        return 0.0;
	    }

	    // Obtener el stack del jugador en la mano 1
	    double stackMano1 = this.getStacks().get(nombreJugador);

	    // Obtener el stack del jugador en la mano 2
	    double stackMano2 = mano2.getStacks().get(nombreJugador);

	    // Calcular las ganancias del jugador entre las dos manos
	    double ganancias = stackMano2 - stackMano1;
	    
	   
	    // Crear un objeto DecimalFormat para formatear el número con dos decimales
        DecimalFormat formato = new DecimalFormat("#0.00");
        
        // Obtener los símbolos del formato decimal
        DecimalFormatSymbols simbolos = formato.getDecimalFormatSymbols();

        // Establecer la coma como separador decimal
        simbolos.setDecimalSeparator('.');

        // Establecer los nuevos símbolos del formato decimal
        formato.setDecimalFormatSymbols(simbolos);
        // Formatear el número con dos decimales
        String numeroFormateado = formato.format(ganancias);

	    ganancias = Double.parseDouble( numeroFormateado);

	    System.out.println("Ganancias de " + nombreJugador + " entre las dos manos: " + ganancias);
	    return ganancias;
	}
	
	
	public double calcularGananciasPerdidas( String nombreJugador) {
	    double gananciasPerdidas = 0.0;

	    for (AccionJugador accion : acciones) {
	        // Verificar si la acción involucra al jugador de interés
	        if (accion.getNombreJugador().equals(nombreJugador)) {
	            if (accion.getTipoAccion() == "posts small" || accion.getTipoAccion() == "posts big" || accion.getTipoAccion() == "raises" || accion.getTipoAccion() == "calls" || accion.getTipoAccion() == "all-In" ) {
	            	
	                gananciasPerdidas -= accion.getCantidad();
	                System.out.println(gananciasPerdidas + " se ha restado " +accion.getCantidad());
	            } else if (accion.getTipoAccion() == "Win") {
	                gananciasPerdidas += accion.getCantidad();
	                
	                System.out.println(gananciasPerdidas + " se ha sumado " +accion.getCantidad());
	            }
	        }
	    }

	    // Formatear el número con dos decimales
	    DecimalFormat formato = new DecimalFormat("#0.00");
	    DecimalFormatSymbols simbolos = formato.getDecimalFormatSymbols();
	    simbolos.setDecimalSeparator('.');
	    formato.setDecimalFormatSymbols(simbolos);
	    String gananciasPerdidasFormateadas = formato.format(gananciasPerdidas);

	    gananciasPerdidas = Double.parseDouble(gananciasPerdidasFormateadas);

	    System.out.println("Ganancias/Pérdidas de " + nombreJugador + ": " + gananciasPerdidas);
	    return gananciasPerdidas;
	}
	
	public double Preflop_Raise( String nombreJugador) 
	{
		boolean primero = true;
		boolean raise = false;
		for (AccionJugador accion : acciones) 
		{
	        // Verificar si la acción involucra al jugador de interés
	        if (accion.getNombreJugador().equals(nombreJugador) && primero) {
	            if ( accion.getTipoAccion().equals("raises")  || accion.getTipoAccion().equals("all-In")   ) 
	            {
	            	raise=true;
	            } 
	        }
	        else
	        {
	        	if ( accion.getTipoAccion().equals("raises")  || accion.getTipoAccion().equals("all-In")   ) 
	            {
	            	primero=false;
	            } 
	        }
	        
	        
	        
	        
	        
	            if ( accion.getTipoAccion().equals("DRAW - FLOP")  ) 
	            {
	            	break;
	            } 
	        
	        
	    }
		
		if(raise)
		{
			return 1.00;
		}
		else
		{
			return 0.00;
		}
	   
	}
	
	
	public double Preflop_3_Bet( String nombreJugador) 
	{
		boolean primero = false;
		boolean segundo = true;
		boolean open_other = true;
		boolean bet3 = false;
		boolean respuesta=false;
		for (AccionJugador accion : acciones) 
		{
	        // Verificar si la acción involucra al jugador de interés
	        if (accion.getNombreJugador().equals(nombreJugador)) {
	            if ( accion.getTipoAccion().equals("raises")  || accion.getTipoAccion().equals("all-In")   ) 
	            {
	            	
	            	if(primero==true && segundo ==true)
	            	{
	            		respuesta=true;
	            		return 1.00;
	            	}
	            		
	            } 
	        }
	        else 
	        {
	        	if ( accion.getTipoAccion().equals("raises")  || accion.getTipoAccion().equals("all-In")   ) 
	            {
	        		if(primero==false)
	        		{
	        			primero=true;
	        		}
	        		else
	        		{
	        			segundo=false;
	        		}
	            } 
	        }
	        
	        
	        
	            if ( accion.getTipoAccion().equals("DRAW - FLOP")  ) 
	            {
	            	break;
	            } 
	        
	        
	    }
		
		if(respuesta)
		{
			return 1.00;
		}
		else
		{
			return 0.00;
		}
	   
	}
	
	
	public double Preflop_3_Bet_Fold( String nombreJugador) 
	{
		boolean primero = false;
		boolean segundo = true;
		boolean cbet=false;
	
		for (AccionJugador accion : acciones) 
		{
			
			
				   if (accion.getNombreJugador().equals(nombreJugador)) {
			            if ( accion.getTipoAccion().equals("folds")    ) 
			            {
			            	
			            	
			            	if(  cbet==true)
			            	{
			            		
			            		
			            		return 1.00;
			            	}
			            		
			            } 
				   }
				
				
				
				
		        // Verificar si la acción involucra al jugador de interés
		        if (accion.getNombreJugador().equals(nombreJugador) == (false)) {
		        	
		        	
		            if ( accion.getTipoAccion().equals("raises")  || accion.getTipoAccion().equals("all-In")   ) 
		            {
		            	
		            	if(primero==true && segundo ==true)
		            	{
		            		
		            		
		            		cbet=true;
		            		
		            		
		            		
		            	}
		            		
		            } 
		        }
		       
		        
		        	if ( accion.getTipoAccion().equals("raises")  || accion.getTipoAccion().equals("all-In")   ) 
		            {
		        		if(primero==false)
		        		{
		        			primero=true;
		        		}
		        		else
		        		{
		        			segundo=false;
		        		}
		        		
		        		
		        		
		            } 
		        
		        
		        
		        
		            if ( accion.getTipoAccion().equals("DRAW - FLOP")  ) 
		            {
		            	break;
		            } 
		        
			
		}
		
		if(cbet)
		{
			return 1.00;
		}
		else
		{
			return 0.00;
		}
	}
	
	
	public Double beneficios(List<AccionJugador> acciones,String jugador) {
		  double respuesta =0; 
		  Map<String ,Double> Botee = new HashMap<>();
		  double raisemano=0;
		
		for(int i =0;i<acciones.size();i++)
		{
			AccionJugador accion = acciones.get(i);
			if (accion.getTipoAccion().equals("checks")) {
		        for (int x = 0; x < this.getPosiciones().size(); x++) {
		            if (((String) this.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador())) {
		                
		            }
		        }
		    }

		    if (accion.getTipoAccion().equals("folds")) {
		        for (int x = 0; x < this.getPosiciones().size(); x++) {
		            if (accion.getNombreJugador().equals(jugador)) {
		            	 if(Botee.containsKey(jugador))
				    		{
				    				respuesta -= Botee.get(jugador) ;
				    				Botee.clear();
				    	    }
		                
		            }
		        }
		    }

		    if (accion.getTipoAccion().equals("bets")) {
		        for (int x = 0; x < this.getPosiciones().size(); x++) {
		            if (((String) this.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador())) {
		               
		                Botee.put(accion.getNombreJugador(), accion.getCantidad());
		            }
		        }
		    }

		    if (accion.getTipoAccion().equals("raises")) {
		        for (int x = 0; x < this.getPosiciones().size(); x++) {
		            if (((String) this.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador())) {
		                
		                raisemano = accion.getCantidad();
		                Botee.put(accion.getNombreJugador(), accion.getCantidad());
		            }
		        }
		    }

		    if (accion.getTipoAccion().equals("calls")) {
		        for (int x = 0; x < this.getPosiciones().size(); x++) {
		            if (((String) this.getPosiciones().keySet().toArray()[x]).equals(accion.getNombreJugador())) {
		                double valor = accion.getCantidad();
		                if (raisemano != -1) {
		                    valor = raisemano;
		                    
		                } else {
		                   
		                }
		                Botee.put(accion.getNombreJugador(), valor);
		                
		            }
		        }
		    }

		    if (accion.getTipoAccion().equals("posts small")) {
		        for (int x = 0; x < this.getPosiciones().size(); x++) {
		            if (((String) this.getPosiciones().keySet().toArray()[x]).equals(jugador)) {
		                
		                Botee.put(accion.getNombreJugador(), accion.getCantidad());
		            }
		        }
		    }

		    if (accion.getTipoAccion().equals("posts big")) {
		        for (int x = 0; x < this.getPosiciones().size(); x++) {
		        	   raisemano =accion.getCantidad() ;
		            if (((String) this.getPosiciones().keySet().toArray()[x]).equals(jugador)) {
		               
		                Botee.put(accion.getNombreJugador(), accion.getCantidad());
		            }
		        }
		    }

		    if (accion.getTipoAccion().equals("Win") && accion.getNombreJugador().equals(jugador)) {
		        
		    	
		        
		                respuesta += accion.getCantidad();
		                if(Botee.containsKey(jugador))
			    		{
			    				respuesta -= Botee.get(jugador) ;
			    				Botee.clear();
			    	    }
		            
		        
		    } else if(accion.getTipoAccion().equals("Win"))
		    {
		    	if(Botee.containsKey(jugador))
	    		{
	    				respuesta -= Botee.get(jugador) ;
	    				 Botee.clear();
	    	    }
		    }

		    if (accion.getTipoAccion().equals("DRAW - FLOP")) {
		 

		       
		    	if(Botee.containsKey(jugador))
	    		{
	    				respuesta -= Botee.get(jugador) ;
	    				 
	    	    }
		        
		        Botee.clear();
		    

		        raisemano = -1;
		      
		    }

		    if (accion.getTipoAccion().equals("DEAL - TURN")) {
		    	
		    		if(Botee.containsKey(jugador))
		    		{
		    				respuesta -= Botee.get(jugador) ;
		    	    }
			        
			        Botee.clear();
			    

			        raisemano = -1;
			      
		    }

		    if (accion.getTipoAccion().equals("DEAL - River")) {
		    	if(Botee.containsKey(jugador))
	    		{
	    				respuesta -= Botee.get(jugador) ;
	    	    }
			        Botee.clear();
			    

			        raisemano = -1;
			      
		    }

		   

		   
		}
	    
		return respuesta;
	}
	
	
	
	public double PreflopSteal(String nombreJugador)
	{
		boolean bb = false;
		boolean raise = false;
		for (AccionJugador accion : acciones) 
		{
	        // Verificar si la acción involucra al jugador de interés
	        if (accion.getNombreJugador().equals(nombreJugador) ) {
	            if ( accion.getTipoAccion().equals("posts big")   ) 
	            {
	            	bb=true;
	            } 
	            
	            if ( accion.getTipoAccion().equals("folds") && bb && raise ) 
	            {
	            	return 1.00;
	            }
	        }
	        
	        
	        if (accion.getNombreJugador().equals(nombreJugador)==false)
	        {
	        	if ( accion.getTipoAccion().equals("raises")  || accion.getTipoAccion().equals("all-In")   ) 
	            {
	            	if(bb)
	            	{
	            		raise = true;
	            	}
	            } 
	        }
	        
	        	

	        
	        
	        
	        
	        
	            if ( accion.getTipoAccion().equals("DRAW - FLOP")  ) 
	            {
	            	break;
	            } 
	            
	            
	        
	        
	    }
		
		return 0.00;
	}

	
	
	
	public double Cbet(String nombreJugador)
	{
		boolean flop = false;
		boolean primero = true;
		boolean raise = false;
		for (AccionJugador accion : acciones) 
		{
	        // Verificar si la acción involucra al jugador de interés
	        if (accion.getNombreJugador().equals(nombreJugador) ) {
	            if ( accion.getTipoAccion().equals("bets")  || accion.getTipoAccion().equals("all-In")  ) 
	            {
	            	if(flop && primero)
	            	{
	            		raise=true;
	            	}
	            } 
	            
	           
	        }
	        
	        
	        if (accion.getNombreJugador().equals(nombreJugador)==false)
	        {
	        	if ( accion.getTipoAccion().equals("bets")  || accion.getTipoAccion().equals("all-In")   ) 
	            {
	            	if(flop)
	            	{
	            		primero = false;
	            	}
	            } 
	        }
	        
	        	

	        
	        
	        
	        
	        
	            if ( accion.getTipoAccion().equals("DRAW - FLOP")  ) 
	            {
	            	flop=true;
	            } 
	            
	            if ( accion.getTipoAccion().equals("DEAL - TURN")  ) 
	            {
	            	break;
	            } 
	            
	        
	        
	    }
		
		if(raise)
		{
			return 1.00;
		}
		
		return 0.00;
	}
	public void maquetarPosiciones() {
	    // Obtener las posiciones actuales
	    Map<String, Integer> posicionesActuales = this.getPosiciones();

	    // Ordenar las claves del mapa de posiciones por sus valores
	    List<String> jugadoresOrdenados = new ArrayList<>(posicionesActuales.keySet());
	    jugadoresOrdenados.sort(Comparator.comparingInt(posicionesActuales::get));

	    // Crear un nuevo mapa para las posiciones reordenadas
	    Map<String, Integer> posicionesReordenadas = new LinkedHashMap<>();

	    // Asignar posiciones consecutivas a partir de 1
	    for (int i = 0; i < jugadoresOrdenados.size(); i++) {
	        posicionesReordenadas.put(jugadoresOrdenados.get(i), i + 1);
	    }

	    // Actualizar el mapa de posiciones en manoActual
	    this.setPosiciones(posicionesReordenadas);

	    // Imprimir el mapa reordenado para verificar (opcional)
	    System.out.println("Posiciones reordenadas: " + posicionesReordenadas);
	}

}
