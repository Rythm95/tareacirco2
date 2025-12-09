package prueba;

import modelo.Persona;

public class Payaso extends Persona {

	private Long idPay;
	private Color colorNariz = Color.Roja;

	public Payaso(Long idPersona, String email, String nombre, String nacionalidad, Long idPay, Color colorNariz) {
		super(idPersona, email, nombre, nacionalidad);
		this.idPay = idPay;
		this.colorNariz = colorNariz;
	}
	
	public Payaso(Long idPersona, String email, String nombre, String nacionalidad, Long idPay) {
		super(idPersona, email, nombre, nacionalidad);
		this.idPay = idPay;
	}

	public Long getIdPay() {
		return idPay;
	}

	public void setIdPay(Long idPay) {
		this.idPay = idPay;
	}

	public Color getColorNariz() {
		return colorNariz;
	}

	public void setColorNariz(Color colorNariz) {
		this.colorNariz = colorNariz;
	}
	
}
