package application;

import java.util.List;

public class AccionJugador {
    private String nombreJugador;
    private double cantidad;
    private String tipoAccion;
    private List<String> cartas;
    
   

    public AccionJugador(String nombreJugador, double cantidad, String tipoAccion,List<String> cartas) {
        this.nombreJugador = nombreJugador;
        this.cantidad = cantidad;
        this.tipoAccion = tipoAccion;
        this.cartas = cartas;
    }
    
    public AccionJugador(String nombreJugador, String tipoAccion,List<String> cartas) {
        this.nombreJugador = nombreJugador;
        this.cantidad = 0;
        this.tipoAccion = tipoAccion;
        this.cartas = cartas;
    }
    
	public AccionJugador(String nombreJugador, double cantidad, String tipoAccion) {
        this.nombreJugador = nombreJugador;
        this.cantidad = cantidad;
        this.tipoAccion = tipoAccion;
        this.cartas = null;
    }

    
    public AccionJugador(String nombreJugador, String tipoAccion) {
        this.nombreJugador = nombreJugador;
        this.cantidad = 0;
        this.tipoAccion = tipoAccion;
        this.cartas = null;
    }
    
    // Getters y Setters
    
    
    public List<String> getCartas() {
		return cartas;
	}


	public void setCartas(List<String> cartas) {
		this.cartas = cartas;
	}

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    

    @Override
    public String toString() {
        return "AccionJugador{" +
                "nombreJugador='" + nombreJugador + '\'' +
                ", cantidad=" + cantidad +
                ", tipoAccion='" + tipoAccion + '\'' +
                ", cartas=" + cartas +
               
                '}';
    }
}
