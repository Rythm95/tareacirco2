/**
* Clase main.java
*
* @author Simao Fernandez Gervasoni
* @version 2.0
*/
package vista;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import control.Validaciones;
import dao.ConsultaBD;
import dao.InsertarPersona;
import modelo.Perfil;
import modelo.Persona;
import modelo.Sesion;

public class Main {

	static private Scanner read = new Scanner(System.in);
	static private Sesion sesion = new Sesion("Invitado", Perfil.INVITADO);
	
	public static void main(String[] args) {
		
		System.out.println("Bienbenido/a.\nSeleccione la acción que desea realizar:");
		boolean salir = false;
		do {
			switch (sesion.getPerfil()) {
			case INVITADO:

				salir = menuInvitado();
				break;

			case ADMIN:

				menuAdmin();
				break;

			case COORDINACION:
				menuCoordinacion();
				break;

			case ARTISTA:
				menuArtista();
				break;

			default:
				System.out.println("Ha habido algún error al procesar su sesión. Por favor, vuelva a intentarlo.");
				sesion.setPerfil(Perfil.INVITADO);

			}

		} while (!salir);

	}
	
	private static boolean menuInvitado() {
		char menu;
		String name;
		String password;

		System.out.println("2 - Iniciar Sesión\n1 - Ver Espectáculos\n0 - Salir");
		menu = read.next().charAt(0);
		switch (menu) {
		case '2':
			System.out.println("Introduce tu nombre de usuario:");
			name = read.next().trim();
			System.out.println("Introduce tu contraseña");
			password = read.next().trim();
			
			Properties properties = new Properties();
			try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")){
				properties.load(fis);
			}
			catch (IOException e) {
				System.out.println("Error al cargar las propiedades.");
			}
			
			
			String usAdmin = properties.getProperty("usuarioAdmin");
			String pasAdmin = properties.getProperty("passwordAdmin");
			
			if (name.equals(usAdmin) && password.equals(pasAdmin)) {
				sesion.setNombre(name);
				sesion.setPerfil(Validaciones.validarSesion(name, password));
				System.out.println("Has iniciado sesión como Administrador.");

			} else {
				iniciarSesion(name, password);
			}

			break;

		case '1':
			verEspectaculos();
			break;

		case '0':
			System.out.println("¡Adiós!");
			return true;

		default:
			System.out.println("Ha introducido un valor incorrecto. Por favor, vuelva a  intentarlo.");

		}
		return false;
	}

}
