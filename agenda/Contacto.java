package agenda;

import java.io.Serializable;

public class Contacto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apellido;
	private int telefono;
	private String email;
	
	
	
	public Contacto(String nombre, String apellido, int telefono, String email) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

@Override
	public boolean equals(Object obj) {
		Contacto a = (Contacto) obj;
		return this.nombre.equalsIgnoreCase(a.getNombre()) && this.apellido.equalsIgnoreCase(a.getApellido());
	}
	//	
	@Override
	public String toString() {
		return "Contacto [nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", email=" + email
				+ "]";
	}
	
	
	
}
