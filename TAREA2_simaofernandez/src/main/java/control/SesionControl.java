package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import dao.CoordinacionDAO;
import modelo.Credenciales;
import modelo.Perfil;

public class SesionControl {

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
}
