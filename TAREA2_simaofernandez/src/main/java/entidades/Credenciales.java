package entidades;

public class Credenciales implements Comparable<Credenciales>{
	private Long id;
	private String user;
	private String password;
	private Perfil perfil;

	public Credenciales(Long id, String user, String password, Perfil perfil) {
		super();
		this.id = id;
		this.user = user;
		this.password = password;
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String nombre) {
		this.user = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	@Override
	public int compareTo(Credenciales o) {
		
		return Long.compare(this.id, o.id);
	}

}
