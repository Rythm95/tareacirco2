package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import dao.CredencialesDAO;
import dao.EspectaculoDAO;
import dao.PersonaDAO;
import modelo.Credenciales;
import modelo.Espectaculo;
import modelo.Numero;
import modelo.Perfil;
import modelo.Persona;

public class Validaciones {
	public static Perfil validarSesion(String name, String contra) {

		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			return Perfil.INVITADO;
		}

		String usAdmin = properties.getProperty("usuarioAdmin");
		String pasAdmin = properties.getProperty("passwordAdmin");

		if (name.equals(usAdmin) && contra.equals(pasAdmin)) {
			return Perfil.ADMIN;
		}

		return Perfil.INVITADO;
	}

	// Cambiar a SQL
	public static boolean existeUsuario(String user) {

		List<Credenciales> creds = CredencialesDAO.listarCredenciales();

		if (!creds.isEmpty()) {
			for (Credenciales c : creds)
				if (c.getUser().equals(user))
					return true;
		}

		return false;
	}

	public static boolean existeEmail(String email) {

		List<Persona> personas = PersonaDAO.listarPersonas();

		if (!personas.isEmpty())
			for (Persona p : personas) {
				if (p.getNombre().equals(email)) {
					return true;
				}
			}

		return false;
	}

	public static boolean existeEspectaculo(String name) {

		List<Espectaculo> espectaculos = EspectaculoDAO.listarEspectaculos();

		for (Espectaculo e : espectaculos) {
			if (e.getNombre().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public static boolean existeNumero(String name) {

		List<Numero> numeros = null;

		for (Numero n : numeros) {
			if (n.getNombre().equals(name))
				return true;
		}
		return false;
	}
}
