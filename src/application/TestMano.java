package application;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class TestMano {

	
	
	
	   private SessionManager sessionManager;
	   private LectorManos lector;

	    @Before
	    public void setUp() {
	        sessionManager = new SessionManager();
	        lector = new LectorManos();
	        File archivo = new File("C:\\Programs\\PartyEspana\\PartyPokerES\\HandHistory\\carras01\\20240524\\Sevilla.txt");
	        lector.leerArchivoYMandarInfo(archivo);
	    	
	    }

	    @Test
	    public void testcreateMano() {
	    	Mano mano= lector.manos.get(0);
	    
	    	
	    	boolean respuesta = (mano.getCartasJugadores()!=null & mano.getAcciones()!=null & mano.getNumeroJugadores()!=0 & mano.getIdSesion()!=null);
	        assertTrue(respuesta);
	        
	        
	    }

	    
	   
}
