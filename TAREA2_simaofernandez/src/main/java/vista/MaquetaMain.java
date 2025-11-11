/**
* Clase Main.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/

package vista;


/**
 * 
 * Main de la Tarea 1, simplemente como ejemplo.
 * 
 */





import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modelo.*;

public class MaquetaMain {

	static private Scanner read = new Scanner(System.in);
	static private Sesion sesion = new Sesion("Invitado", Perfil.INVITADO);
	static private TreeSet<Espectaculo> espectaculos = new TreeSet<>();

//	public static void main(String[] args) {
//
//		System.out.println("Bienbenido/a.\nSeleccione la acción que desea realizar:");
//		boolean salir = false;
//		do {
//			switch (sesion.getPerfil()) {
//			case INVITADO:
//
//				salir = menuInvitado();
//				break;
//
//			case ADMIN:
//
//				menuAdmin();
//				break;
//
//			case COORDINACION:
//				menuCoordinacion();
//				break;
//
//			case ARTISTA:
//				menuArtista();
//				break;
//
//			default:
//				System.out.println("Ha habido algún error al procesar su sesión. Por favor, vuelva a intentarlo.");
//				sesion.setPerfil(Perfil.INVITADO);
//
//			}
//
//		} while (!salir);
//
//	}

//\\// Menus de perfiles
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
				System.out.println("Error al cargarl as propiedades.");
			}
			
			
			String usAdmin = properties.getProperty("usuarioAdmin");
			String pasAdmin = properties.getProperty("passwordAdmin");
			
			if (name.equals(usAdmin) && password.equals(pasAdmin)) {
				sesion.setNombre(name);
				sesion.setPerfil(Perfil.ADMIN);
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

	private static void iniciarSesion(String name, String password) {
		File creFile = new File("ficheros/credenciales.txt");

		if (!creFile.exists()) {
			System.out.println("Usuario o contraseña incorrectos. Vuelva a intentarlo.");
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(creFile))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] creds = line.split("\\|");
				if (creds.length > 6 && name.equalsIgnoreCase(creds[1]) && password.equals(creds[2])) {
					sesion.setNombre(name);
					sesion.setPerfil(Perfil.valueOf(creds[6]));
					return;

				}

			}

		} catch (IOException e) {
		}
	}

	// Cerrar Sesión
	private static void cerrarSesion() {
		sesion.setPerfil(Perfil.INVITADO);
		System.out.println("Su sesión ha sido cerrada");
	}

	// Menu Admin
	private static void menuAdmin() {

		System.out.println("Seleccione la acción que desea realizar.");
		System.out
				.println("3 - Gestionar Personas\n2 - Gestionar Espectáculos\n1 - Ver Espectáculos\n0 - Cerrar Sesión");
		char menu = read.next().charAt(0);
		switch (menu) {

		case '3':
			gestionPersonas();
			System.out.println();
			break;

		case '2':
			System.out.println();
			gestionEspectaculos();
			System.out.println();
			break;

		case '1':
			verEspectaculos();
			break;

		case '0':
			cerrarSesion();

			return;

		default:
			System.out.println("Ha introducido un valor incorrecto. Por favor, vuelva a  intentarlo.");

		}
	}

	// Menu Coordinación
	private static void menuCoordinacion() {
		System.out.println("Seleccione la acción que desea realizar.");
		System.out.println("2 - Gestionar Espectáculos\n1 - Ver Espectáculos\n0 - Cerrar Sesión");
		char menu = read.next().charAt(0);
		switch (menu) {

		case '2':
			System.out.println();
			gestionEspectaculos();
			System.out.println();
			break;

		case '1':
			verEspectaculos();
			break;
		case '0':
			cerrarSesion();

			return;

		default:
			System.out.println("Ha introducido un valor incorrecto. Por favor, vuelva a  intentarlo.");

		}

	}

	// Menu Artista
	private static void menuArtista() {
		System.out.println("Seleccione la acción que desea realizar.");
		char menu;
		System.out.println("1 - Ver Espectáculos\n0 - Cerrar sesión");
		menu = read.next().charAt(0);
		switch (menu) {
		case '1':
			verEspectaculos();
			break;

		case '0':
			cerrarSesion();
			break;

		default:
			System.out.println("Ha introducido un valor incorrecto. Por favor, vuelva a  intentarlo.");

		}

	}

//\\// Espectáculos

	// Menu de gestión de espectáculos
	private static void gestionEspectaculos() {
		char menu;

		do {
			System.out.println("¿Cómo desea gestionar los Espectaculos?");
			System.out.println(
					"3 - Crear o modificar espectáculo\n2 - Crear o modificar número\n1 - Asignar artistas\n0 - Cancelar");
			menu = read.next().charAt(0);

			switch (menu) {
			case '3': // Crear y modificar espectáculos
				cmEspectaculo();
				break;
			case '2': // Sin Implementar
				System.out.println("La función aún no ha sido implementada.");
				break;
			case '1': // Sin Implementar
				System.out.println("La función aún no ha sido implementada.");
				break;
			case '0':
				System.out.println();
				break;
			default:
				System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");

			}

		} while ( menu != '0');

	}

	// Crear [y modificar] espectáculos
	private static void cmEspectaculo() {
		char menu;
		do {
			System.out.println("2 - Crear espectáculo\n1 - Modificar espectáculo existente\n0 - Cancelar");
			menu = read.next().charAt(0);
			read.nextLine();
			switch (menu) {
			// Crear espectáculo
			case '2':
				System.out.println("Introduzca un nombre único para el espectáculo.");
				String name = read.nextLine();
				if (name.length() > 25) {
					System.out.println("El nombre no debe superar los 25 caracteres.");
					break;
				}
				LocalDate dateSt;
				System.out.println("Introduzca la fecha inicial del espectáculo. (yyyy-mm-dd)");
				try {
					dateSt = LocalDate.parse(read.next());
					read.nextLine();
				} catch (DateTimeParseException e) {
					System.out.println("La fecha no es válida.");
					break;
				}
				LocalDate dateEn;
				System.out.println("Introduzca la fecha final del espectáculo. (yyyy-mm-dd)");
				try {
					dateEn = LocalDate.parse(read.next());
					read.nextLine();

					if (dateEn.isBefore(dateSt)) {
						System.out.println("La fecha final no puede ser anterior a la fecha inicial.");
						break;
					} else if (dateSt.plusYears(1).isBefore(dateEn)) {
						System.out.println("La duración del espectáculo no debe ser superior a un año.");
						break;
					}

				} catch (DateTimeParseException e) {
					System.out.println("La fecha no es válida.");
					break;
				}

				espectaculos = loadEspectaculos();

				// Verificar si ya hay un espectáculo con el mismo nombre
				boolean repetido = false;
				for (Espectaculo e : espectaculos) {
					if (e.getNombre().equalsIgnoreCase(name)) {
						System.out.println("Ya existe un espectáculo con ese nombre.");
						repetido = true;
						break;
					}

				}

				if (repetido)
					break;

				Long idCoordinador = 0L;

				if (sesion.getPerfil() == Perfil.COORDINACION) {

					// Obtener el ID del coordinador en sesion
					for (Map.Entry<String, Long> en : getCoordinadores().entrySet()) {
						if (en.getKey().equalsIgnoreCase(sesion.getNombre())) {
							idCoordinador = en.getValue();
						}
					}
					if (idCoordinador == 0) {
						System.out.println("Error al obtener el Id");
						break;
					}

				} else {

					if (getCoordinadores().isEmpty()) {
						System.out.println("No existen coordinadores. Registra uno antes de crear un espectáculo.");
						break;
					}

					do {
					System.out.println("Elige un coordinador para el espectáculo");
					System.out.println("Coordinadores: ");
					for (Map.Entry<String, Long> en : getCoordinadores().entrySet()) {

						System.out.println("- " + en.getKey()+" [Id "+en.getValue()+" ]");
					}

					String nombre = read.next().toLowerCase().trim();
					idCoordinador = getCoordinadores().get(nombre);
					if(idCoordinador==null) {
						System.out.println("El coordinador elegido no existe. Inténtelo de nuevo.\n");
					}
					
					} while(idCoordinador==null);

				}

				// Id incremental
				Long id = (espectaculos.isEmpty() ? 1 : espectaculos.last().getId() + 1);

				System.out.println("El nuevo espectáculo se ve así:");
				System.out.println("ID: " + id + "\nNombre: " + name + "\nFecha Inicio: " + dateSt + "\nFecha Fin: "
						+ dateEn + "\nID de Coordinador: " + idCoordinador);
				char info;
				do {
					System.out.println("¿La información es correcta? [Y/N]");
					info = read.next().toUpperCase().charAt(0);
					read.nextLine();
					switch (info) {
					case 'Y':
						Espectaculo esp = new Espectaculo(id, name, dateSt, dateEn, idCoordinador);
						espectaculos.add(esp);
						saveEspectaculos();
						break;

					case 'N':
						break;

					default:
						System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
					}
				} while (info != 'Y' && info != 'N');

				break;

			// Modificar espectáculo
			case '1':
				System.out.println("La función aún no ha sido implementada.");
				break;

			// Cancelar
			case '0':
				System.out.println("Operación cancelada.");
				break;

			default:
				System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");

			}
		} while (menu != '0');
	}

	// Ficheros de Espectáculos

	private static void saveEspectaculos() {

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ficheros/espectaculos.dat"))) {
			oos.writeObject(espectaculos);
			System.out.println("El espectáculo se ha creado correctamente.");
		} catch (IOException e) {
			System.out.println("Error al crear el espectáculo.");
		}

	}

	private static TreeSet<Espectaculo> loadEspectaculos() {
		File espFile = new File("ficheros/espectaculos.dat");

		if (!espFile.exists())
			return espectaculos;

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(espFile))) {
			espectaculos = (TreeSet<Espectaculo>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error al leer los espectáculos");
		}
		return espectaculos;

	}

	// \\ Otros métodos

	// Ver Espectáculos
	private static void verEspectaculos() {
		espectaculos = loadEspectaculos();
		if (espectaculos.isEmpty()) {
			System.out.println("No hay espectáculos programados.");
			return;
		}
		System.out.println("Estos son los espectáculos programados:");
		for (Espectaculo e : espectaculos) {
			System.out.println(" "+e.toString());
		}
		System.out.println();
	}

//\\// Personas y Credenciales

	// Menu de gestion de personas
	private static void gestionPersonas() {
		char menu;
		System.out.println("¿Cómo desea gestionar las Personas?");
		do {
			System.out.println("1 - Registrar Persona\n0 - Cancelar");
			menu = read.next().charAt(0);

			switch (menu) {
			// Registrar persona
			case '1':
				read.nextLine();
				System.out.println("Introduce los datos de la persona que quieres registrar:");
				System.out.print("Nombre real: ");
				String name = read.nextLine().trim();
				System.out.print("Email: ");
				String email = read.next().trim();
				// Validar email
				if (!Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email)) {
					System.out.println("El email no es válido.");
					menu = '0';
					break;
				}

				Map<String, String> paises = loadPaises();
				for (Map.Entry<String, String> en : paises.entrySet()) {
					System.out.println(en.getKey() + " - " + en.getValue());
				}
				String nacionalidad;
				do {
					System.out.print("\nNacionalidad (introduzca el código del país): ");
					nacionalidad = paises.get(read.next().toUpperCase());
					if (nacionalidad == null)
						System.out.println(
								"El código introducido no se corresponde con ningún país. Vuelva a intentarlo.");
				} while (nacionalidad == null);
				String perfCA;
				do {
					System.out.print("Perfil (COORDINACION o ARTISTA): ");
					perfCA = read.next();
					if (!perfCA.equalsIgnoreCase("COORDINACION") && !perfCA.equalsIgnoreCase("ARTISTA")) {
						System.out.println("Valor de Perfil incorrecto. Vuelva a intentarlo.");
					}
				} while (!perfCA.equalsIgnoreCase("COORDINACION") && !perfCA.equalsIgnoreCase("ARTISTA"));
				Perfil perfil = Perfil.valueOf(perfCA.toUpperCase());
				Persona persona;
				boolean senior = false;
				LocalDate seniorFecha = null;
				String apodo = null;
				List<Especialidad> listaEspecialidades = null;

				if (perfil == Perfil.COORDINACION) {

					char esSenior;
					do {
						System.out.print("¿Es senior? [Y/N]: ");
						esSenior = read.next().toUpperCase().charAt(0);
						switch (esSenior) {
						case 'Y':
							senior = true;
							System.out.print("¿Desde qué fecha? (yyyy-mm-dd):");
							try {
								seniorFecha = LocalDate.parse(read.next());
								read.nextLine();
							} catch (DateTimeParseException e) {
								System.out.println("La fecha no es válida.");
								esSenior = 'X'; // Se repite el switch
								break;
							}
							break;

						case 'N':
							read.nextLine();
							senior = false;
							break;

						default:
							System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
						}
					} while (esSenior != 'Y' && esSenior != 'N');

					persona = new Coordinacion(personaID(), email, name, nacionalidad, perfilID(perfil), senior,
							seniorFecha);
				} else {
					char tieneApodo;

					do {
						System.out.print("¿Tiene apodo? [Y/N]: ");
						tieneApodo = read.next().toUpperCase().charAt(0);
						read.nextLine();
						switch (tieneApodo) {
						case 'Y':

							System.out.print("Apodo: ");
							apodo = read.nextLine().trim();
							break;

						case 'N':
							break;

						default:
							System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
						}
					} while (tieneApodo != 'Y' && tieneApodo != 'N');

					boolean listError = true;
					do {
						System.out.println(
								"Especialidades (ACROBACIA,HUMOR,MAGIA,EQUILIBRIO,MALABARISMO Separadas por ',')");

						String[] especialidades = read.nextLine().toUpperCase().split(",");
						listaEspecialidades = new ArrayList<>();
						listError = false;
						for (String es : especialidades) {
							try {
								listaEspecialidades.add(Especialidad.valueOf(es.trim()));
							} catch (IllegalArgumentException e) {
								System.out.println("La especialidad no es válida. Vuelva a intentarlo.");
								listError = true;
								break;
							}
						}
					} while (listError);

					persona = new Artista(personaID(), email, name, nacionalidad, perfilID(perfil), apodo,
							listaEspecialidades);
				}
				String user = "";
				do {
					System.out.print("Nombre de usuario: ");
					user = read.nextLine().toLowerCase();
					if (user == "")
						System.out.println("El nombre de usuario no debe estar vacío.");
					if (user.length() <= 2) {
						System.out.println("El nombre de usuario debe contener más de 2 caracteres");
						user = "";
					}
					if (user.contains(" ")) {
						System.out.println("El nombre de usuario no debe contener espacios");
						user = "";
					}
					if (!Pattern.matches("^[a-z]+$", user)) {
						System.out.println(
								"El nombre de usuario no debe contener números ni letras con tíldes o dieresis");
						user = "";
					}
				} while (user == "");

				String password = "";
				do {
					System.out.print("Contraseña: ");
					password = read.nextLine();
					if (password == "")
						System.out.println("La contraseña no debe estar vacía.");
					if (password.length() <= 2) {
						System.out.println("La contraseña debe contener más de 2 caracteres");
						password = "";
					}
					if (password.contains(" ")) {
						System.out.println("La contraseña no debe contener espacios");
						password = "";
					}
				} while (password == "");

				if (existeUsuario(user)) {
					System.out.println("Ya hay un usuario regirstrado con ese nombre.");
					return;
				}

				if (existeEmail(email)) {
					System.out.println("Ya hay un usuario regirstrado con ese email.");
					return;
				}

				System.out.println("\nEstos son los datos introducidos:");
				System.out.println("Nombre: " + name + "\nEmail: " + email + "\nNacionalidad: " + nacionalidad
						+ "\nPerfil: " + perfil);
				if (perfil == Perfil.COORDINACION) {
					System.out.println("Senior: " + senior);
					if (senior) {
						System.out.println(" ^ Desde: " + seniorFecha);
					}
				} else {
					if (apodo != null) {
						System.out.println("Apodo: " + apodo);
					}
					System.out.print("Especialidades: ");
					for (Especialidad e : listaEspecialidades) {
						System.out.print(e + " ");
					}
					System.out.println();
				}
				System.out.println("Nombre de usuario: " + user + "\nContraseña: " + password);

				char info;
				do {
					System.out.println("¿La información es correcta? [Y/N]");
					info = read.next().toUpperCase().charAt(0);
					read.nextLine();
					switch (info) {
					case 'Y':
						Credenciales credenciales = new Credenciales(personaID(), user, password, perfil);
						saveCredenciales(persona, credenciales);
						break;

					case 'N':
						break;

					default:
						System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
					}
				} while (info != 'Y' && info != 'N');

				break;
			case '0':
				System.out.println("Operación cancelada.");
				break;

			default:
				System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");

			}
		} while ( menu != '0');
	}

	// Cargar paises
	private static Map<String, String> loadPaises() {
		Map<String, String> paises = new LinkedHashMap<>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse("src/main/resources/paises.xml");
			document.getDocumentElement().normalize();

			NodeList list = document.getElementsByTagName("pais");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String id = element.getElementsByTagName("id").item(0).getTextContent();
					String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
					paises.put(id, nombre);
				}
			}

		} catch (ParserConfigurationException | IOException | SAXException e) {
			System.out.println("Ha habido un error al intentar leer el archivo paises.xml");
		}
		return paises;

	}

	// \\ IDs

	// Obtiene el nuevo ID de Persona
	private static Long personaID() {
		Long idMax = 0L;
		Long id = 0L;
		try (BufferedReader br = new BufferedReader(new FileReader("ficheros/credenciales.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] creds = line.split("\\|");
				if (creds.length > 1 && !creds[0].isEmpty())
					id = Long.parseLong(creds[0]);
				if (id > idMax)
					idMax = id;
			}
		} catch (IOException e) {
		}
		return idMax + 1;
	}

	// Obtiene el nuevo ID correspondiente de Artista o Coordinador
	private static Long perfilID(Perfil perfil) {
		Long idMax = 0L;
		Long id = 0L;
		try (BufferedReader br = new BufferedReader(new FileReader("ficheros/credenciales.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] creds = line.split("\\|");
				if (creds.length > 6 && creds[6].equalsIgnoreCase(perfil.toString()))
					id++;
				if (id > idMax)
					idMax = id;
			}
		} catch (IOException e) {
		}
		return idMax + 1;

	}

	// \\ Ficheros de Personas y Credenciales

	// Guardar las credenciales en "credenciales.txt"
	private static void saveCredenciales(Persona p, Credenciales c) {
		File creFile = new File("ficheros/credenciales.txt");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(creFile, true))) {
			creFile.createNewFile();
			bw.write(p.getidPersona() + "|" + c.getUser() + "|" + c.getPassword() + "|" + p.getEmail() + "|"
					+ p.getNombre() + "|" + p.getNacionalidad() + "|" + c.getPerfil());
			bw.newLine();
			System.out.println("La persona se ha registrado con éxito.");
		} catch (IOException e) {
			System.out.println("Error al guardar credenciales.");
		}
	}

	// Obtener ID y nombre de los coordinadores
	private static Map<String, Long> getCoordinadores() {
		File creFile = new File("ficheros/credenciales.txt");
		Map<String, Long> coordinadores = new LinkedHashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(creFile))) {
			creFile.createNewFile();
			String line;

			while ((line = br.readLine()) != null) {
				String[] creds = line.split("\\|");
				if (Perfil.valueOf(creds[6]) == Perfil.COORDINACION) {
					coordinadores.put(creds[1], Long.parseLong(creds[0]));
				}

			}
		} catch (IOException e) {
		}
		return coordinadores;
	}

//\\// Validaciones
	private static boolean existeUsuario(String user) {

		try (BufferedReader br = new BufferedReader(new FileReader("ficheros/credenciales.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] creds = line.split("\\|");
				if (creds[1].equalsIgnoreCase(user))
					return true;
			}
		} catch (IOException e) {
			// Si da error no esta repetido.
		}
		return false;
	}

	private static boolean existeEmail(String email) {

		try (BufferedReader br = new BufferedReader(new FileReader("ficheros/credenciales.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] creds = line.split("\\|");
				if (creds.length > 3 && creds[3].equalsIgnoreCase(email))
					return true;
			}
		} catch (IOException e) {
			// Si da error no esta repetido.
		}
		return false;
	}

}
