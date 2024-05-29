package application;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TestDatabase {
	
	
	 

	    @Test
	    public void testEstablecerConexion() {
	    	
	        try
	        {
	        	
	        	DatabaseHandler.getInstance().connect();
	        	
	        	assertTrue(true);
	        }
	        catch(Exception e)
	        {
	        	assertTrue(false);
	        }
	        
	    }
	    
	    @Test
	    public void testObtenerSesiones() {
	    	
	        try
	        {
	        	
	        	List<Sesion> lista = DatabaseHandler.getInstance().obtenerTodasSesiones();
	        	
	        	assertTrue(true);
	        }
	        catch(Exception e)
	        {
	        	assertTrue(false);
	        }
	        
	    }
	    @Test
	    public void testObtenerSesionInventada() {
	    	
	        try
	        {
	        	
	        	Sesion sesion = DatabaseHandler.getInstance().obtenerSesionPorNombre("Prueba");
	        	
	        	if(sesion==null)
	        	{
	        		System.out.println("La sesion es nula pero se controla");
	        	}
	        	assertTrue(true);
	        }
	        catch(Exception e)
	        {
	        	assertTrue(false);
	        }
	        
	    }

}
