package control;

import java.util.List;

import dao.CredencialesDAO;
import modelo.Credenciales;

public class CredencialesControl {

	public static List<Credenciales> getCredenciales(){
		return CredencialesDAO.listarCredenciales();
	}

	public static boolean existeUsuario(String user) {
	
		List<Credenciales> creds = CredencialesDAO.listarCredenciales();
	
		if (!creds.isEmpty()) {
			for (Credenciales c : creds)
				if (c.getUser().equals(user))
					return true;
		}
	
		return false;
	}
	
}
