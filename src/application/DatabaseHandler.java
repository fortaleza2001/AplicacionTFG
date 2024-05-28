package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    private static DatabaseHandler instance;
    public Connection connection;

    private DatabaseHandler() {
        // Constructor privado para prevenir instancias adicionales
    }

    public static synchronized DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:D:\\sqlite\\base_manos.db");
            System.out.println("Conexión a la base de datos establecida.");
            verificarYCrearTablas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void verificarYCrearTablas() {
        try (Statement stmt = connection.createStatement()) {
            String createTableSesiones = "CREATE TABLE IF NOT EXISTS sesiones (" +
                                         "nombre_sesion TEXT PRIMARY KEY," +
                                         "fecha_inicio TEXT)";
            stmt.execute(createTableSesiones);

            if (stmt.getUpdateCount() == 0) {
                System.out.println("La tabla sesiones ya está creada.");
            } else {
                System.out.println("La tabla sesiones ha sido creada.");
            }

            String createTableManos = "CREATE TABLE IF NOT EXISTS manos (" +
                                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                      "sesion_nombre TEXT," +
                                      "numeroJugadores INT," +
                                      "tamanoMaximoMesa INT," +
                                      "flop TEXT," +
                                      "turn TEXT," +
                                      "river TEXT," +
                                      "modoJuego VARCHAR(255)," +
                                      "acciones TEXT," +
                                      "cartasJugadores TEXT," +
                                      "posiciones TEXT," +
                                      "button INT," +
                                      "fecha DATE," +
                                      "stacks TEXT," +
                                      "FOREIGN KEY (sesion_nombre) REFERENCES sesiones(nombre_sesion))";
            stmt.execute(createTableManos);

            if (stmt.getUpdateCount() == 0) {
                System.out.println("La tabla manos ya está creada.");
            } else {
                System.out.println("La tabla manos ha sido creada.");
            }

            String createTableRecuerdoSesion = "CREATE TABLE IF NOT EXISTS recuerdosesion (" +
                                               "nota TEXT)";
            stmt.execute(createTableRecuerdoSesion);

            if (stmt.getUpdateCount() == 0) {
                System.out.println("La tabla recuerdosesion ya está creada.");
            } else {
                System.out.println("La tabla recuerdosesion ha sido creada.");
            }

            System.out.println("Tablas creadas correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarSesion(String nombreSesion, String fechaInicio) {
        String insertSesionSQL = "INSERT INTO sesiones (nombre_sesion, fecha_inicio) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSesionSQL)) {
            pstmt.setString(1, nombreSesion);
            pstmt.setString(2, fechaInicio);
            pstmt.executeUpdate();
            System.out.println("Sesión insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarMano(String nombreSesion,String fecha, Mano mano) {
        String insertManoSQL = "INSERT INTO manos (sesion_nombre, numeroJugadores, tamanoMaximoMesa, flop, turn, river, modoJuego, acciones, cartasJugadores, posiciones, button, fecha,stacks) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            // Verificar si la sesión existe
            if (!existeSesion(nombreSesion)) {
                // Si la sesión no existe, se crea
                insertarSesion(nombreSesion, fecha);
                System.out.println("Sesión creada correctamente: " + nombreSesion);
            }

            // Preparar la inserción de la mano
            try (PreparedStatement pstmt = connection.prepareStatement(insertManoSQL)) {
                pstmt.setString(1, nombreSesion);
                pstmt.setInt(2, mano.getNumeroJugadores());
                pstmt.setInt(3, mano.getTamanoMaximoMesa());
                pstmt.setString(4, String.join(",", mano.getFlop()));  // Suponiendo que el flop es una lista de cartas
                pstmt.setString(5, mano.getTurn());
                pstmt.setString(6, mano.getRiver());
                pstmt.setString(7, mano.getModoJuego());
                pstmt.setString(8, accionesToString(mano.getAcciones()));
                pstmt.setString(9, cartasJugadoresToString(mano.getCartasJugadores()));
                pstmt.setString(10, posicionesToString(mano.getPosiciones()));
                pstmt.setInt(11, mano.getButton());
                pstmt.setDate(12, new Date(mano.getFecha().getTime()));
                pstmt.setString(13, stacksToString(mano.getStacks()));
                pstmt.executeUpdate();
                System.out.println("Mano insertada correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String cargarSesion() {
        String nota = "";
        String selectNotaSQL = "SELECT nota FROM recuerdosesion";
        try (PreparedStatement pstmt = connection.prepareStatement(selectNotaSQL);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                nota = rs.getString("nota");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nota;
    }
    
    public void guardarSesion(String nuevoRegistro) {
        try {
            // Eliminar cualquier registro existente
            String deleteSQL = "DELETE FROM recuerdosesion";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
                pstmt.executeUpdate();
            }
            
            // Insertar el nuevo registro
            String insertSQL = "INSERT INTO recuerdosesion (nota) VALUES (?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setString(1, nuevoRegistro);
                pstmt.executeUpdate();
                System.out.println("Registro de sesión guardado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public List<Mano> obtenerManosDeSesion(String nombreSesion) {
        List<Mano> manos = new ArrayList<>();
        String selectManosSQL = "SELECT * FROM manos WHERE sesion_nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectManosSQL)) {
            pstmt.setString(1, nombreSesion);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Obtener los datos de la mano de la fila actual
                int numeroJugadores = rs.getInt("numeroJugadores");
                int tamanoMaximoMesa = rs.getInt("tamanoMaximoMesa");
                String flop = rs.getString("flop");
                String turn = rs.getString("turn");
                String river = rs.getString("river");
                String modoJuego = rs.getString("modoJuego");
                String accionesString = rs.getString("acciones");
                List<AccionJugador> acciones = accionesFromString(accionesString);
                java.util.Date fecha = rs.getDate("fecha");
                String stacksString = rs.getString("stacks");
                // Obtener otros campos de la mano...

                // Crear un objeto Mano y agregarlo a la lista de manos
                Mano mano = new Mano();
                
                String flopString = rs.getString("flop");

             // Dividir la cadena por las comas y obtener un array de cartas
             String[] cartasArray = flopString.split(",");

             // Crear una lista para almacenar las cartas
             List<String> flopList = new ArrayList<>();

             // Agregar cada carta a la lista
             for (String carta : cartasArray) {
                 flopList.add(carta);
             }
             int button = rs.getInt("button");
             String posicionesString = rs.getString("posiciones");

             // Convertir la cadena de posiciones de nuevo a un mapa
             Map<String, Integer> posiciones = new HashMap<>();
             if (posicionesString != null && !posicionesString.isEmpty()) {
                 String[] posicionesArray = posicionesString.split(";");
                 for (String posicion : posicionesArray) {
                     String[] partes = posicion.split(":");
                     if (partes.length == 2) {
                         String jugador = partes[0];
                         int posicionJugador = Integer.parseInt(partes[1]);
                         posiciones.put(jugador, posicionJugador);
                     }
                 }
             }
          // Obtener las cartas de los jugadores de la fila actual
             String cartasJugadoresString = rs.getString("cartasJugadores");

             // Convertir la cadena de cartas de nuevo a un mapa
             Map<String, List<String>> cartasJugadores = new HashMap<>();
             if (cartasJugadoresString != null && !cartasJugadoresString.isEmpty()) {
                 String[] cartasJugadoresArray = cartasJugadoresString.split(";");
                 for (String cartasJugador : cartasJugadoresArray) {
                     String[] partes = cartasJugador.split(":");
                     if (partes.length == 2) {
                         String jugador = partes[0];
                         String cartas = partes[1];
                         List<String> listaCartas = Arrays.asList(cartas.split(","));
                         cartasJugadores.put(jugador, listaCartas);
                     }
                 }
             }
             
             
            

          // Convertir la cadena de stacks de nuevo a un mapa
          Map<String, Double> stacks = new HashMap<>();
          if (stacksString != null && !stacksString.isEmpty()) {
              String[] stacksArray = stacksString.split(";");
              for (String stackEntry : stacksArray) {
                  String[] parts = stackEntry.split(":");
                  if (parts.length == 2) {
                      String jugador = parts[0];
                      double stackJugador = Double.parseDouble(parts[1]);
                      stacks.put(jugador, stackJugador);
                  }
              }
          }

             // Ahora, establece los valores en tu objeto Mano
             mano.setCartasJugadores(cartasJugadores);
             // Ahora, establece los valores en tu objeto Mano
             	mano.setButton(button);
             	mano.setPosiciones(posiciones);
                mano.setFlop(flopList);
                mano.setTurn(turn);
                mano.setRiver(river);
                mano.setNumeroJugadores(numeroJugadores);
                mano.setTamanoMaximoMesa(tamanoMaximoMesa);
                mano.setModoJuego(modoJuego);
                mano.setAcciones(accionesFromString(accionesString));
                mano.setFecha(fecha);
                mano.setStacks(stacks);
                
                // Obtener otros campos de la mano...
                manos.add(mano);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manos;
    }

    
    public List<Mano> obtenerManosDeSesionOrdenadasPorFecha(String nombreSesion) {
        // Obtener la lista de manos
        List<Mano> manos = obtenerManosDeSesion(nombreSesion);

        // Ordenar la lista por fecha
        Collections.sort(manos, (mano1, mano2) -> mano1.getFecha().compareTo(mano2.getFecha()));

        // Devolver la lista ordenada
        return manos;
    }
    private List<AccionJugador> accionesFromString(String accionesString) {
        List<AccionJugador> acciones = new ArrayList<>();
        String[] accionesArray = accionesString.split(";");
        for (String accionString : accionesArray) {
            String[] partesAccion = accionString.split(":");
            
            if (partesAccion.length >= 4) { // Al menos debe haber nombre, cantidad, tipo de acción y cartas
                String nombreJugador = partesAccion[0];
                double cantidad = Double.parseDouble(partesAccion[1]);
                String tipoAccion = partesAccion[2];
                List<String> cartas = null;
                if (!partesAccion[3].equals("null")) { // Si hay cartas, analizarlas
                    cartas = Arrays.asList(partesAccion[3].split(", "));
                }
                AccionJugador accionJugador = new AccionJugador(nombreJugador, cantidad, tipoAccion, cartas);
                acciones.add(accionJugador);
            }
        }
        return acciones;
    }

    private boolean existeSesion(String nombreSesion) throws SQLException {
        String query = "SELECT COUNT(*) FROM sesiones WHERE nombre_sesion = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nombreSesion);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    public void saveSesionConManos(String nombreSesion, String fechaInicio, List<Mano> manos) {
        String insertSesionSQL = "INSERT INTO sesiones (nombre_sesion, fecha_inicio) VALUES (?, ?)";
        try (PreparedStatement pstmtSesion = connection.prepareStatement(insertSesionSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmtSesion.setString(1, nombreSesion);
            pstmtSesion.setString(2, fechaInicio);
            pstmtSesion.executeUpdate();

            ResultSet generatedKeys = pstmtSesion.getGeneratedKeys();
            if (generatedKeys.next()) {
                long sesionId = generatedKeys.getLong(1);
                for (Mano mano : manos) {
                    saveMano(sesionId, mano);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveMano(long sesionId, Mano mano) {
        String insertManoSQL = "INSERT INTO manos (sesion_id, numeroJugadores, tamanoMaximoMesa, flop, turn, river, modoJuego, acciones, cartasJugadores, posiciones, button, fecha,stacks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement pstmtMano = connection.prepareStatement(insertManoSQL)) {
            pstmtMano.setLong(1, sesionId);
            pstmtMano.setInt(2, mano.getNumeroJugadores());
            pstmtMano.setInt(3, mano.getTamanoMaximoMesa());
            pstmtMano.setString(4, String.join(",", mano.getFlop()));
            pstmtMano.setString(5, mano.getTurn());
            pstmtMano.setString(6, mano.getRiver());
            pstmtMano.setString(7, mano.getModoJuego());
            pstmtMano.setString(8, accionesToString(mano.getAcciones()));
            pstmtMano.setString(9, cartasJugadoresToString(mano.getCartasJugadores()));
            pstmtMano.setString(10, posicionesToString(mano.getPosiciones()));
            pstmtMano.setInt(11, mano.getButton());
            pstmtMano.setDate(12, new Date(mano.getFecha().getTime()));
            pstmtMano.setString(13, stacksToString(mano.getStacks()));
            pstmtMano.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String accionesToString(List<AccionJugador> acciones) {
        StringBuilder sb = new StringBuilder();
        for (AccionJugador accion : acciones) {
            sb.append(accion.getNombreJugador()).append(":")
              .append(accion.getCantidad()).append(":")
              .append(accion.getTipoAccion()).append(":")
              .append(accion.getCartas()).append(";");
        }
        return sb.toString();
    }
    
    public Sesion obtenerSesionPorNombre(String nombre) {
        Sesion sesion = null;
        String selectSesionSQL = "SELECT nombre_sesion, fecha_inicio FROM sesiones WHERE nombre_sesion = ? LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSesionSQL)) {
            pstmt.setString(1, nombre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nombreSesion = rs.getString("nombre_sesion");
                    String fechaInicio = rs.getString("fecha_inicio");
                    sesion = new Sesion(nombreSesion, fechaInicio);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sesion;
    }


    
    public  List<Sesion> obtenerTodasSesiones() {
        List<Sesion> sesiones = new ArrayList<>();
        String selectSesionesSQL = "SELECT nombre_sesion, fecha_inicio FROM sesiones";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSesionesSQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String nombreSesion = rs.getString("nombre_sesion");
                String fechaInicio = rs.getString("fecha_inicio");
                Sesion sesion = new Sesion(nombreSesion, fechaInicio);
                sesiones.add(sesion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sesiones;
    }

    private String cartasJugadoresToString(Map<String, List<String>> cartasJugadores) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : cartasJugadores.entrySet()) {
            sb.append(entry.getKey()).append(":")
              .append(String.join(",", entry.getValue())).append(";");
        }
        return sb.toString();
    }

    private String posicionesToString(Map<String, Integer> posiciones) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : posiciones.entrySet()) {
            sb.append(entry.getKey()).append(":")
              .append(entry.getValue()).append(";");
        }
        return sb.toString();
    }
    
    private String stacksToString(Map<String, Double> stacks) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : stacks.entrySet()) {
            sb.append(entry.getKey()).append(":")
              .append(entry.getValue()).append(";");
        }
        return sb.toString();
    }


    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
