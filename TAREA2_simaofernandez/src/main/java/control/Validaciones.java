package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import modelo.Perfil;

public class Validaciones {
	public static Perfil validarSesion(String name, String contra) {
		
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")){
			properties.load(fis);
		}
		catch (IOException e) {
			return Perfil.INVITADO;
		}
		
		String usAdmin = properties.getProperty("usuarioAdmin");
		String pasAdmin = properties.getProperty("passwordAdmin");
		
		if (name.equals(usAdmin) && contra.equals(pasAdmin)) {
			return Perfil.ADMIN;
			System.out.println("Has iniciado sesión como Administrador.");

		}
		
		return Perfil.INVITADO;
	}
}
