package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import dao.PersonaDAO;
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

//		try (BufferedReader br = new BufferedReader(new FileReader("ficheros/credenciales.txt"))) {
//			String line;
//			while ((line = br.readLine()) != null) {
//				String[] creds = line.split("\\|");
//				if (creds[1].equalsIgnoreCase(user))
//					return true;
//			}
//		} catch (IOException e) {
//			// Si da error no esta repetido.
//		}
		return false;
	}

	public static boolean existeEmail(String email) {

		List<Persona> personas = PersonaDAO.listarPersonas();

		if (!personas.isEmpty())
			for (Persona p : personas) {
				if (p.getNombre().equals(email)) {
					return false;
				}
			}

		return true;
	}
}
