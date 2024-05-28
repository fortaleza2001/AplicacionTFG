package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SessionManager {
	private DatabaseHandler dbHandler= DatabaseHandler.getInstance();
	public boolean llena = false;
	
	public SessionManager()
	{
		if(dbHandler.connection==null)
		{
			dbHandler.connect();
		}
	}
    public void saveSession(String sessionId) 
    {
    	 dbHandler.guardarSesion(sessionId);
    	 this.llena=true;
    }

  

    public String loadSession()
    {
    	 String sessionId =dbHandler.cargarSesion() ;
    	 
    	 if(sessionId==null)
    	 {
    		 this.llena=false;
    		 return "";
    	 }
    	 else
    	 {
    		 this.llena=true;
    		 return sessionId;
    	 }
    	 
     }
    }

    

