package clases;

import java.io.Serializable;

public class Persona implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String email;
	private boolean mandar;

	public Persona(String nombre, String email, boolean mandar) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.mandar = mandar;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	public boolean isMandar() {
		return mandar;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMandar(boolean mandar) {
		this.mandar = mandar;
	}

}
