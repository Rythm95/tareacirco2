package modelo;

public class Persona {
	protected Long idPersona;
	protected String email;
	protected String nombre;
	protected String nacionalidad;

	public Persona(Long idPersona, String email, String nombre, String nacionalidad) {
		super();
		this.idPersona = idPersona;
		this.email = email;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
	}

	public Long getidPersona() {
		return idPersona;
	}

	public void setidPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

}
