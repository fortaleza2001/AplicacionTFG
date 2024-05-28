package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LectorManos {
    private DatabaseHandler dbHandler;
    
    public List<Mano> manos = new ArrayList<>();

    public LectorManos() {
        dbHandler = DatabaseHandler.getInstance();
        dbHandler.connect();
    }

    public void leerArchivoYMandarInfo(File archivo) {
    	 this.manos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Mano manoActual = null;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("#Game No :")) {
                    if (manoActual != null) {
                        // Procesar la mano anterior
                        procesarMano(manoActual,archivo);
                    }
                    manoActual = new Mano();
                    manoActual.setIdSesion(archivo.getName());
                    // Procesar el modo de juego
                    manoActual.setModoJuego(linea.split("\\s+", 5)[4]);
                } else if (linea.startsWith("Table Closed")) {
                    // Omitir la lectura de la mano si la mesa está cerrada
                    manoActual = null;
                } else if (linea.contains("Texas Hold'em")) {
                	String [] partes = linea.split("-");
                	
                    manoActual.setModoJuego(partes[0]);
                    
                    String dateString =partes[1].trim();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, HH:mm:ss z yyyy", Locale.ENGLISH);
                    // Analiza la cadena y conviértela en un objeto Date
                    Date date = dateFormat.parse(dateString);
                    manoActual.setFecha(date);
                   
                } else if (linea.startsWith("Total number of players")) {
                    String[] partes = linea.split(":")[1].trim().split("/");
                    manoActual.setNumeroJugadores(Integer.parseInt(partes[0]));
                    manoActual.setTamanoMaximoMesa(Integer.parseInt(partes[1]));
                } else if (linea.startsWith("** Dealing Flop **")) {
                    // Procesar el flop
                    String[] partes = linea.split("\\[")[1].split(", "); // Obtener las cartas del flop
                    List<String> flop = new ArrayList<>();
                    for (String carta : partes) {
                        flop.add(carta.trim()); // Añadir cada carta al flop después de eliminar los espacios en blanco
                    }
                    
                    manoActual.getAcciones().add(new AccionJugador("DEALER",  "DRAW - FLOP",flop));
                    manoActual.setFlop(flop);
                } else if (linea.startsWith("** Dealing Turn **")) {
                    // Procesar el turn
                	String cartaTurn = linea.split("\\[")[1].replace("]", "").trim();
                    manoActual.setTurn(cartaTurn);
                    List<String> turnList = new ArrayList<>();
                    turnList.add(cartaTurn);
                    manoActual.getAcciones().add(new AccionJugador("DEALER", "DEAL - TURN", turnList));
                 
                } else if (linea.startsWith("** Dealing River **")) {
                    // Procesar el river
                	String cartaRiver = linea.split("\\[")[1].replace("]", "").trim();
                    manoActual.setTurn(cartaRiver);
                    List<String> turnList = new ArrayList<>();
                    turnList.add(cartaRiver);
                    manoActual.getAcciones().add(new AccionJugador("DEALER", "DEAL - River", turnList));
                }  else if (linea.startsWith("Dealt to ")) {
                    // Procesar las cartas repartidas a un jugador
                    String[] partes = linea.split("Dealt to ")[1].split(" \\[");
                    String nombreJugador = partes[0].trim();
                    String[] cartas = partes[1].replace("]", "").trim().split(" ");
                    manoActual.agregarCartasJugador(nombreJugador, Arrays.asList(cartas));
                }
                
                else if (linea.contains("is the button")) {
                    // Procesar las cartas repartidas a un jugador
                    String[] partes = linea.split(" ");
               
                    manoActual.setButton(Integer.parseInt(partes[1]));
                   
                }
                else if (linea.contains("wins")) {
                    // Procesar las cartas repartidas a un jugador
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0].trim();
                    double cantidad =  Double.parseDouble(partes[2].replace("€", "")) ;
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador,cantidad, "Win"));
                   
                }
                
                else if (linea.matches("^Seat \\d+: .*")) {
                    // Procesar las posiciones de los jugadores y sus stacks
                    String[] partes = linea.split(" ");
                    int numeroAsiento = Integer.parseInt(partes[1].replace(":", ""));
                    String nombreJugador = partes[2].trim();
                    double stack = Double.parseDouble(partes[4].replace("EUR", "").replace(",", "").replace("€", ""));
                    manoActual.agregarPosicionJugador(nombreJugador,numeroAsiento);
                    manoActual.agregarStackJugador(nombreJugador, stack);
                    // Agregar el nombre del jugador y su stack a tus estructuras de datos
                    // Por ejemplo, podrías almacenarlos en un mapa donde la clave sea el número de asiento y el valor sea un objeto Jugador
                    // Si prefieres mantener un mapa de nombre de jugador a stack, simplemente hazlo así:
                    // manoActual.agregarPosicionJugador(nombreJugador, stack);
                }

                else if (linea.contains("posts small blind [")) {
                 // Procesar acciones como posts small blind, posts big blind, folds, calls, raises
                 String[] partes = linea.split(" ");
                 String nombreJugador = partes[0];
                 String tipoAccion = partes[1] + (partes.length > 2 ? " " + partes[2] : "");
                 double cantidad = 0;
                
                 Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                 Matcher matcher = pattern.matcher(linea);
                 String cantidadStr = "";
                 if (matcher.find()) {
                     cantidadStr = matcher.group(1).replace("€", "").trim();
                     
                 }
                 
                 cantidadStr = cantidadStr.replace("EUR", "");
                   

               
                    cantidad = Double.parseDouble(cantidadStr);
                 

             

                 // Añadir la acción del jugador
                 manoActual.getAcciones().add(new AccionJugador(nombreJugador, cantidad, tipoAccion));
             }
                
                
                else if (linea.contains("posts big blind [")) {
                    // Procesar acciones como posts small blind, posts big blind, folds, calls, raises
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0];
                    String tipoAccion = partes[1] + (partes.length > 2 ? " " + partes[2] : "");
                    double cantidad = 0;
                   
                    Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                    Matcher matcher = pattern.matcher(linea);
                    String cantidadStr = "";
                    if (matcher.find()) {
                        cantidadStr = matcher.group(1).replace("€", "").trim();
                        
                    }
                    
                    cantidadStr = cantidadStr.replace("EUR", "");
                      

                  
                       cantidad = Double.parseDouble(cantidadStr);
                    

                

                    // Añadir la acción del jugador
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador, cantidad, tipoAccion));
                }
                
                
                else if (linea.contains("shows [")) 
                {
                	// Procesar las cartas repartidas a un jugador
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0].trim();
                    String carta1 = partes[3].replace("]", "").replace(",", "");
                    String carta2 = partes[4].replace("]", "");
                    String resto = "";
                    for(int x=5;x<partes.length;x++)
                    {
                    	resto+=partes[x];
                    }
                    List<String> cartasListas = new ArrayList<>();
                    cartasListas.add(carta1);
                    cartasListas.add(carta2);
                    
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador, "SHOW - " + resto.replace(".", "").replace("]", ""), cartasListas));
                    manoActual.agregarCartasJugador(nombreJugador, cartasListas);
                    
                }
                else if (linea.contains("raises [")) {
                    // Procesar acciones como posts small blind, posts big blind, folds, calls, raises
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0];
                    String tipoAccion = "" +partes[1] ;
                    double cantidad = 0;
                   
                    Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                    Matcher matcher = pattern.matcher(linea);
                    String cantidadStr = "";
                    if (matcher.find()) {
                        cantidadStr = matcher.group(1).replace("€", "").trim();
                        
                    }
                    
                    
                    cantidadStr = cantidadStr.replace("EUR", "");
                      

                  
                       cantidad = Double.parseDouble(cantidadStr);
                    

                

                    // Añadir la acción del jugador
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador, cantidad, tipoAccion));
                }
                
                else if (linea.contains("bets [")) {
                    // Procesar acciones como posts small blind, posts big blind, folds, calls, raises
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0];
                    String tipoAccion = "" +partes[1] ;
                    double cantidad = 0;
                   
                    Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                    Matcher matcher = pattern.matcher(linea);
                    String cantidadStr = "";
                    if (matcher.find()) {
                        cantidadStr = matcher.group(1).replace("€", "").trim();
                        
                    }
                    
                    
                    cantidadStr = cantidadStr.replace("EUR", "");
                      

                  
                       cantidad = Double.parseDouble(cantidadStr);
                    

                

                    // Añadir la acción del jugador
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador, cantidad, tipoAccion));
                }
                
                else if (linea.contains("all-In  [")) {
                    // Procesar acciones como posts small blind, posts big blind, folds, calls, raises
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0];
                    String tipoAccion = "" +partes[2] ;
                    double cantidad = 0;
                   
                    Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                    Matcher matcher = pattern.matcher(linea);
                    String cantidadStr = "";
                    if (matcher.find()) {
                        cantidadStr = matcher.group(1).replace("€", "").trim();
                        
                    }
                    
                    
                    cantidadStr = cantidadStr.replace("EUR", "");
                      

                  
                       cantidad = Double.parseDouble(cantidadStr);
                    

                

                    // Añadir la acción del jugador
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador, cantidad, tipoAccion));
                }
                
                else if (linea.contains("calls [")) {
                    // Procesar acciones como posts small blind, posts big blind, folds, calls, raises
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0];
                    String tipoAccion = "" +partes[1] ;
                    double cantidad = 0;
                   
                    Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                    Matcher matcher = pattern.matcher(linea);
                    String cantidadStr = "";
                    if (matcher.find()) {
                        cantidadStr = matcher.group(1).replace("€", "").trim();
                        
                    }
                    
                    
                    cantidadStr = cantidadStr.replace("EUR", "");
                      

                  
                       cantidad = Double.parseDouble(cantidadStr);
                    

                

                    // Añadir la acción del jugador
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador, cantidad, tipoAccion));
                }
                
                
                else if (linea.contains("checks")) {
                	
                	 // Procesar acciones como posts small blind, posts big blind, folds, calls, raises
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0];
                    String tipoAccion = partes[1] + (partes.length > 2 ? " " + partes[2] : ""); 
                    // Añadir la acción del jugador
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador, tipoAccion));
                }
                
                
                else if (linea.contains("folds")) {
                	
                	 // Procesar acciones como posts small blind, posts big blind, folds, calls, raises
                    String[] partes = linea.split(" ");
                    String nombreJugador = partes[0];
                    String tipoAccion = partes[1] + (partes.length > 2 ? " " + partes[2] : ""); 
                    // Añadir la acción del jugador
                    manoActual.getAcciones().add(new AccionJugador(nombreJugador, tipoAccion));
                }
            }
            // Procesar la última mano
            if (manoActual != null) {
                procesarMano(manoActual,archivo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    
    public void procesarMano(Mano mano,File f) {
        System.out.println("Mano: " + mano);
        this.manos.add(mano);
        
       
        
        
       // dbHandler.insertarMano(mano.getIdSesion(),""+f.lastModified(), mano);
        
      
    }


    

    public void cerrarConexion() {
        dbHandler.disconnect();
    }

    public static void main(String[] args) {
        LectorManos lector = new LectorManos();
        // Reemplaza con la ruta a tu archivo
        File archivo = new File("C:\\Programs\\PartyEspana\\PartyPokerES\\HandHistory\\carras01\\20240524\\Sevilla.txt");
        lector.leerArchivoYMandarInfo(archivo);
        lector.cerrarConexion();
    }
}
