package ejercicio1;

import java.sql.Date;

public class Tenista {
	private int idTenista;
	private String nombre;
	private Date fechaNacimiento;
	private int puntos;
	private int titulosGS;
	
	
	
	
	
	public Tenista(int idTenista, String nombre, Date fechaNacimiento, int puntos, int titulosGS) {
		this.idTenista = idTenista;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.puntos = puntos;
		this.titulosGS = titulosGS;
	}
	
	
	
	
	
	public Tenista() {
		super();
	}





	@Override
	public String toString() {
		return "Tenista [idTenista=" + idTenista + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", puntos=" + puntos + ", titulosGS=" + titulosGS + "]";
	}

	
	
	
	public int getIdTenista() {
		return idTenista;
	}

	public void setIdTenista(int idTenista) {
		this.idTenista = idTenista;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getTitulosGS() {
		return titulosGS;
	}

	public void setTitulosGS(int titulosGS) {
		this.titulosGS = titulosGS;
	}
}
