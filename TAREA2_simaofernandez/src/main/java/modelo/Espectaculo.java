package modelo;

import java.time.LocalDate;

public class Espectaculo implements Comparable<Espectaculo> {
	private Long id;
	private String nombre;
	private LocalDate fechaini;
	private LocalDate fechacfin;
	private Long idCoordinacion;


	public Espectaculo(Long id, String nombre, LocalDate fechaini, LocalDate fechacfin, Long idCoordinacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaini = fechaini;
		this.fechacfin = fechacfin;
		this.idCoordinacion = idCoordinacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaini() {
		return fechaini;
	}

	public void setFechaini(LocalDate fechaini) {
		this.fechaini = fechaini;
	}

	public LocalDate getFechacfin() {
		return fechacfin;
	}

	public void setFechacfin(LocalDate fechacfin) {
		this.fechacfin = fechacfin;
	}

	public Long getIdCoordinacion() {
		return idCoordinacion;
	}

	public void setIdCoordinacion(Long idCoordinacion) {
		this.idCoordinacion = idCoordinacion;
	}

	@Override
	public String toString() {
		return "Espectáculo " + id + " - " + nombre + " [De " + fechaini + " a " + fechacfin + "]";
	}

	@Override
	public int compareTo(Espectaculo o) {

		return Long.compare(this.id, o.id);
	}

}
