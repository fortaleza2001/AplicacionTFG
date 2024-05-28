package application;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestSessionManager {

    private SessionManager sessionManager;

    @Before
    public void setUp() {
        sessionManager = new SessionManager();
    }

    @Test
    public void testStartSession() {
       String prueba = sessionManager.loadSession();
       System.out.println(prueba);
        assertTrue(prueba!=null & sessionManager.llena);
    }

    
    @Test
    public void testLoadSession() {
    	
       String prueba ="Juan";
       sessionManager.saveSession(prueba);
       System.out.println(prueba);
       String resultado = sessionManager.loadSession();
       assertTrue(resultado.equals(prueba));
    }
    
    
    
}
