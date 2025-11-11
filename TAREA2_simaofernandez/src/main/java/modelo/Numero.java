package modelo;

public class Numero {
	private Long id;
	private int orden;
	private String nombre;
	private double duracion;
	private Long idEspec;

	public Numero(Long id, int orden, String nombre, double duracion, Long idEspec) {
		super();
		this.id = id;
		this.orden = orden;
		this.nombre = nombre;
		this.duracion = duracion;
		this.idEspec = idEspec;
	}

	public Long getIdEspec() {
		return idEspec;
	}

	public void setIdEspec(Long idEspec) {
		this.idEspec = idEspec;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

}
