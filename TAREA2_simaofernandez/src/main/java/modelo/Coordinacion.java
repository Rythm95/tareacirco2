package modelo;

import java.time.LocalDate;

public class Coordinacion extends Persona {

	private Long idCoord;
	private boolean senior;
	private LocalDate fechasenior;
	
	public Coordinacion(Long id, String email, String nombre, String nacionalidad, Long idCoord) {
		super(id, email, nombre, nacionalidad);
		this.idCoord = idCoord;
		this.senior = false;
		this.fechasenior = null;
	}
	
	public Coordinacion(Long id, String email, String nombre, String nacionalidad, Long idCoord, boolean senior,
			LocalDate fechasenior) {
		super(id, email, nombre, nacionalidad);
		this.idCoord = idCoord;
		this.senior = senior;
		this.fechasenior = fechasenior;
	}

	public Long getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(Long idCoord) {
		this.idCoord = idCoord;
	}

	public boolean isSenior() {
		return senior;
	}

	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	public LocalDate getFechasenior() {
		return fechasenior;
	}

	public void setFechasenior(LocalDate fechasenior) {
		this.fechasenior = fechasenior;
	}
	
}
