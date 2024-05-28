package application;


public class Sesion {

	private String nombreSesion;
    private String fechaInicio;

    public Sesion(String nombreSesion, String fechaInicio) {
        this.nombreSesion = nombreSesion;
        this.fechaInicio = fechaInicio;
    }
    
    public String getNombreSesion() {
		return nombreSesion;
	}

	public void setNombreSesion(String nombreSesion) {
		this.nombreSesion = nombreSesion;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

}