package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import dao.CoordinacionDAO;
import dao.CredencialesDAO;
import dao.EspectaculoDAO;
import dao.NumeroDAO;
import dao.PersonaDAO;
import modelo.Credenciales;
import modelo.Espectaculo;
import modelo.Numero;
import modelo.Perfil;
import modelo.Persona;

public class Validaciones {

	private static final Logger logger = Logger.getLogger(CoordinacionDAO.class.getName());

	public static Perfil validarSesion(String name, String contra) {

		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			logger.warning("Error al leer el fichero de propiedades: " + e.getMessage());
		}

		String usAdmin = properties.getProperty("usuarioAdmin");
		String pasAdmin = properties.getProperty("passwordAdmin");

		if (name.equals(usAdmin) && contra.equals(pasAdmin))
			return Perfil.ADMIN;
		
		else {
			List<Credenciales> credenciales = CredencialesControl.getCredenciales();
			
			for (Credenciales cr : credenciales) {
				if (cr.getUser().equals(name)) {
					return cr.getPerfil();
				}
			}
		}

		return Perfil.INVITADO;
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

		List<Numero> numeros = NumeroDAO.listarNumero();

		for (Numero n : numeros) {
			if (n.getNombre().equals(name))
				return true;
		}
		return false;
	}

	public static boolean numeroEnEspectaculo(Long idNumero, Long idEsp) {

		List<Numero> numeros = NumeroDAO.listarNumero();

		for (Numero n : numeros) {
			if (n.getId().equals(idNumero) && n.getIdEspec() != null) {
				if (n.getIdEspec().equals(idEsp)) {
					return false;
				}
				return true;
				
			}

		}
		return false;
	}
}
