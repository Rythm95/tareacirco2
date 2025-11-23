package control;

import java.util.List;

import dao.CredencialesDAO;
import modelo.Credenciales;

public class CredencialesControl {

	public static List<Credenciales> getCredenciales(){
		return CredencialesDAO.listarCredenciales();
	}
	
}
