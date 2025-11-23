/**
* Clase main.java
*
* @author Simao Fernandez Gervasoni
* @version 2.0
*/
package vista;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import control.ArtistaControl;
import control.CoordinacionControl;
import control.EspectaculoControl;
import control.NumeroControl;
import control.PaisesControl;
import control.PersonaControl;
import control.RegistroControl;
import control.Validaciones;
import modelo.Artista;
import modelo.Coordinacion;
import modelo.Credenciales;
import modelo.Especialidad;
import modelo.Espectaculo;
import modelo.Numero;
import modelo.Perfil;
import modelo.Persona;
import modelo.Sesion;

// Separar en clases por sesiones
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

			sesion.setPerfil(Validaciones.validarSesion(name, password));

			if (sesion.getPerfil() == Perfil.INVITADO) {
				System.out.println("Error al iniciar sesión. El nombre o la contraseña no son correctos.");
			} else {

				if (sesion.getPerfil() == Perfil.ADMIN)
					System.out.println("Has iniciado sesión como Administrador.");

				else if (sesion.getPerfil() == Perfil.ARTISTA)
					System.out.println("Has iniciado sesión como Artista");

				else if (sesion.getPerfil() == Perfil.COORDINACION)
					System.out.println("Has iniciado sesión como Coordinador");

				sesion.setNombre(name);
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

	private static void menuAdmin() {

		System.out.println("Seleccione la acción que desea realizar.");
		System.out
				.println("3 - Gestionar Personas\n2 - Gestionar Espectáculos\n1 - Ver Espectáculos\n0 - Cerrar Sesión");
		char menu = read.next().charAt(0);
		read.nextLine();
		switch (menu) {

		case '3':
			gestionPersonas();
			System.out.println();
			break;

		case '2':
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

	private static void gestionPersonas() {
		char menu;
		System.out.println("¿Cómo desea gestionar las Personas?");
		do {
			System.out.println("2 - Registrar Persona\n1 - Modificar Persona\n0 - Cancelar");
			menu = read.next().charAt(0);
			read.nextLine();

			switch (menu) {
			// Registrar persona
			case '2':

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

				// Cargar los Paises
				Map<String, String> paises = PaisesControl.getPaises();
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

					persona = new Coordinacion(0L, email, name, nacionalidad, 0L, senior, seniorFecha);
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
								if (!listaEspecialidades.contains(Especialidad.valueOf(es.trim())))
									listaEspecialidades.add(Especialidad.valueOf(es.trim()));
							} catch (IllegalArgumentException e) {
								System.out.println("La especialidad no es válida. Vuelva a intentarlo.");
								listError = true;
								break;
							}
						}
					} while (listError);

					persona = new Artista(0L, email, name, nacionalidad, 0L, apodo, listaEspecialidades);
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

				if (Validaciones.existeUsuario(user)) {
					System.out.println("Ya hay un usuario registrado con ese nombre.");
					return;
				}

				if (Validaciones.existeEmail(email)) {
					System.out.println("Ya hay un usuario registrado con ese email.");
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
						Credenciales credenciales = new Credenciales(0L, user, password, perfil);
						RegistroControl.registrarPersona(persona, credenciales);
						break;

					case 'N':
						break;

					default:
						System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
					}
				} while (info != 'Y' && info != 'N');

				break;
			case '1': // Modificar Persona
				if (PersonaControl.getMapPersonas().isEmpty()) {
					System.out.println("No hay personas registradas.");
					break;
				}

				boolean elegirPersona = true;
				Long idper = 0L;
				System.out.println(
						"¿Qué persona desea modificar? (Introduzca su id).\nIntroduzca 0 si desea cancelar la operación.");

				do {
					System.out.println("Personas: ");
					for (Map.Entry<Long, String> en : PersonaControl.getMapPersonas().entrySet()) {

						System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
					}

					try {
						idper = read.nextLong();
						read.nextLine();
						elegirPersona = false;

						if (idper == 0) {
							System.out.println("Operación cancelada.");
							elegirPersona = false;

						} else if (!PersonaControl.getMapPersonas().containsKey(idper)) {
							System.out.println(
									"El id introducido no está pertenece a ninguna persona. Inténtelo de nuevo.");
							elegirPersona = true;
						}

					} catch (InputMismatchException e) {
						System.out.println("Se ha introducido un valor no válido.");
						read.nextLine();
					}

				} while (elegirPersona);
				if (idper != 0) {
					System.out.println("Introduce los nuevos datos de la persona:");
					System.out.print("Nombre real: ");
					String newName = read.nextLine().trim();
					System.out.print("Email: ");
					String newEmail = read.next().trim();
					// Validar email
					if (!Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", newEmail)) {
						System.out.println("El email no es válido.");
						menu = '0';
						break;
					}

					// Cargar los Paises
					paises = PaisesControl.getPaises();
					for (Map.Entry<String, String> en : paises.entrySet()) {
						System.out.println(en.getKey() + " - " + en.getValue());
					}

					do {
						System.out.print("\nNacionalidad (introduzca el código del país): ");
						nacionalidad = paises.get(read.next().toUpperCase());
						if (nacionalidad == null)
							System.out.println(
									"El código introducido no se corresponde con ningún país. Vuelva a intentarlo.");
					} while (nacionalidad == null);

					List<Persona> personas = PersonaControl.listarPersonas();
					Perfil personaPerfil = Perfil.INVITADO;
					Long idPerfil = 0L;
					for (Persona p : personas) {
						if (p.getidPersona().equals(idper)) {
							if (p instanceof Artista) {
								personaPerfil = Perfil.ARTISTA;
								idPerfil = ((Artista) p).getIdArt();
							} else if (p instanceof Coordinacion) {
								personaPerfil = Perfil.COORDINACION;
								idPerfil = ((Coordinacion) p).getIdCoord();
							}
						}
					}

					senior = false;
					seniorFecha = null;
					apodo = null;
					listaEspecialidades = null;

					if (personaPerfil == Perfil.COORDINACION) {

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

						persona = new Coordinacion(idper, newEmail, newName, nacionalidad, idPerfil, senior,
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
									if (!listaEspecialidades.contains(Especialidad.valueOf(es.trim())))
										listaEspecialidades.add(Especialidad.valueOf(es.trim()));
								} catch (IllegalArgumentException e) {
									System.out.println("La especialidad no es válida. Vuelva a intentarlo.");
									listError = true;
									break;
								}
							}
						} while (listError);

						persona = new Artista(idper, newEmail, newName, nacionalidad, idPerfil, apodo,
								listaEspecialidades);
					}

					if (!newEmail.equals(PersonaControl.getMapPersonasEmail().get(idper))
							&& Validaciones.existeEmail(newEmail)) {
						System.out.println("Ya hay un usuario registrado con ese email.");
						return;
					}

					System.out.println("\nEstos son los datos introducidos:");
					System.out.println("Nombre: " + newName + "\nEmail: " + newEmail + "\nNacionalidad: " + nacionalidad
							+ "\nPerfil: " + personaPerfil);
					if (personaPerfil == Perfil.COORDINACION) {
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

					do {
						System.out.println("¿La información es correcta? [Y/N]");
						info = read.next().toUpperCase().charAt(0);
						read.nextLine();
						switch (info) {
						case 'Y':
							RegistroControl.modificarPersonas(persona, idper);
							break;

						case 'N':
							break;

						default:
							System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
						}
					} while (info != 'Y' && info != 'N');
				}

				break;

			case '0':
				System.out.println("Operación cancelada.");
				break;

			default:
				System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");

			}
		} while (menu != '0');
	}

	private static void gestionEspectaculos() {
		char menu;

		do {
			System.out.println("¿Cómo desea gestionar los Espectaculos?");
			System.out.println("2 - Crear o modificar espectáculo\n1 - Crear o modificar número\n0 - Cancelar");
			menu = read.next().charAt(0);
			read.nextLine();

			switch (menu) {
			case '2': // Crear y modificar espectáculos
				cmEspectaculo();
				break;

			case '1': // Crear y modificar numeros
				cmNumero();
				break;

			case '0':
				System.out.println();
				break;
			default:
				System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");

			}

		} while (menu != '0');

	}

	private static void menuCoordinacion() {
		System.out.println("Seleccione la acción que desea realizar.");
		System.out.println("2 - Gestionar Espectáculos\n1 - Ver Espectáculos\n0 - Cerrar Sesión");
		char menu = read.next().charAt(0);
		read.nextLine();
		switch (menu) {

		case '2':
			gestionEspectaculos();
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

	private static void menuArtista() {
		System.out.println("Seleccione la acción que desea realizar.");
		char menu;
		System.out.println("2 - Ver Ficha\n1 - Ver Espectáculos\n0 - Cerrar sesión");
		menu = read.next().charAt(0);
		read.nextLine();
		switch (menu) {

		case '2':
			verFicha();
			break;

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

	private static void verFicha() {
		System.out.println("Esta es su ficha de Artista: ");
		List<Artista> artistas = ArtistaControl.getArtista();
		Long idArtista = ArtistaControl.getArtistaId(sesion.getNombre());
		Map<Long, String> espectaculos = EspectaculoControl.getMapEspectaculos();
		List<Long> numerosArtista = ArtistaControl.getNumeroArtistas(idArtista);
		List<Numero> numeros = NumeroControl.getNumero();

		for (Artista a : artistas) {
			if (a.getIdArt() == idArtista) {
				System.out.print("Nombre: " + a.getNombre() + "\nEmail: " + a.getEmail() + "\nNacionalidad: "
						+ a.getNacionalidad());
				if (a.getApodo() != null) {
					System.out.print("\nApodo: " + a.getApodo()+"\n");
				}
				System.out.println("Especialidades: " + a.especialidadesToString() + "\n");
			}
		}
		if (!numerosArtista.isEmpty()) {
			System.out.println("Números participados:");
			for (Numero n : numeros) {
				if (numerosArtista.contains(n.getId())) {
					System.out.println(" - " + n.getNombre() + " [Id"+n.getId()+"] del espectáculo "+espectaculos.get(n.getIdEspec())+" [Id"+n.getIdEspec()+"]");
				}
			}
		}

	}

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
				if (name.isEmpty()) {
					System.out.println("El nombre no debe estar vacío.");
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

				if (Validaciones.existeEspectaculo(name)) {
					System.out.println("Ya existe un espectáculo con ese nombre.");
					break;
				}

				Long idCoordinador = 0L;
				Map<Long, String> coordinadores = CoordinacionControl.getMapCoordinadores();

				if (sesion.getPerfil() == Perfil.COORDINACION) {

					// Obtener el ID del coordinador en sesion

					idCoordinador = CoordinacionControl.getCoordinadorId(sesion.getNombre());

				} else {

					if (coordinadores.isEmpty()) {
						System.out.println("No existen coordinadores. Registra uno antes de crear un espectáculo.");
						break;
					}

					boolean idVal = false;
					do {
						System.out.println("Elige un coordinador para el espectáculo (elije su id).");
						System.out.println("Coordinadores: ");
						for (Map.Entry<Long, String> en : coordinadores.entrySet()) {

							System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
						}

						try {

							idCoordinador = read.nextLong();
							read.nextLine();

							if (!coordinadores.containsKey(idCoordinador))
								System.out.println(
										"El id seleccionado no pertenece a ningún coordinador. Inténtelo de nuevo.\n");

							else
								idVal = true;

						} catch (InputMismatchException e) {
							System.out.println("Se ha introducido un valor no válido. Inténtelo de nuevo.\n");
							read.nextLine();
						}

					} while (!idVal);

				}

				System.out.println("El nuevo espectáculo se ve así:");
				System.out.println("Nombre: " + name + "\nFecha Inicio: " + dateSt + "\nFecha Fin: " + dateEn
						+ "\nCoordinador/a: " + coordinadores.get(idCoordinador));
				char info;
				do {
					System.out.println("¿La información es correcta? [Y/N]");
					info = read.next().toUpperCase().charAt(0);
					read.nextLine();
					switch (info) {
					case 'Y':
						Espectaculo esp = new Espectaculo(0L, idCoordinador, name, dateSt, dateEn);

						if (NumeroControl.getMapNumero().size() < 3) {
							System.out.println(
									"No hay suficientes números. Registra al menos 3 antes de crear un nuevo espectáculo.");
							break;
						}

						int numContador = 0;
						boolean elegirNumeros = true;
						List<Long> idNums = new ArrayList<>();
						Long idnum = 0L;
						System.out.println(
								"Elige al menos 3 números para el espectáculo. El orden de elección representará el orden de los números en el espectáculo.\nIntroduzca 0 si desea cancelar la operación.");
						do {
							System.out.println("Números: ");
							for (Map.Entry<Long, String> en : NumeroControl.getMapNumero().entrySet()) {

								System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
							}

							try {
								idnum = read.nextLong();
								read.nextLine();

								if (idnum == 0) {
									System.out.println("Operación cancelada.");
									elegirNumeros = false;
								} else if (!NumeroControl.getMapNumero().containsKey(idnum))
									System.out.println(
											"El id introducido no está asociado a ningún número. Inténtelo de nuevo.");

								else if (Validaciones.numeroEnEspectaculo(idnum, 0L))
									System.out.println("El número ya está asociado a otro espectáculo.");

								else if (idNums.contains(idnum))
									System.out.println(
											"No puede asociar el mismo número dos veces a un solo espectáculo. Inténtelo de nuevo.");
								else {
									idNums.add(idnum);
									System.out.println("Añadido: " + NumeroControl.getMapNumero().get(idnum));
									++numContador;
								}

								if (numContador >= 3) {
									char info2;
									do {
										System.out.println(
												"Ha añadido suficientes números. ¿Desea seguir añadiendo? [Y/N]");
										info2 = read.next().toUpperCase().charAt(0);
										read.nextLine();
										switch (info2) {
										case 'Y':
											break;

										case 'N':
											elegirNumeros = false;
											break;

										default:
											System.out.println(
													"Se ha introducido un valor no válido. Vuelva a intentarlo.");
										}
									} while (info2 != 'Y' && info2 != 'N');

								}
							} catch (InputMismatchException e) {
								System.out.println("Se ha introducido un valor no válido.");
								read.nextLine();
							}

						} while (elegirNumeros);

						if (idnum != 0) {
							RegistroControl.registrarEspectaculo(esp, idNums);
							System.out.println("Espectáculo registrado con éxito.");
						}
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

				if (EspectaculoControl.getMapEspectaculos().isEmpty()) {
					System.out.println("No hay espectáculos registrados.");
					break;
				}

				boolean elegirEspectaculo = true;
				Long idesp = 0L;
				System.out.println(
						"¿Qué espectáculo desea modificar? (Introduzca su id).\nIntroduzca 0 si desea cancelar la operación.");

				do {
					System.out.println("Espectáculos: ");
					for (Map.Entry<Long, String> en : EspectaculoControl.getMapEspectaculos().entrySet()) {

						System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
					}

					try {
						idesp = read.nextLong();
						read.nextLine();
						elegirEspectaculo = false;

						if (idesp == 0) {
							System.out.println("Operación cancelada.");
							elegirEspectaculo = false;

						} else if (!EspectaculoControl.getMapEspectaculos().containsKey(idesp)) {
							System.out.println(
									"El id introducido no está asociado a ningún espectáculo. Inténtelo de nuevo.");
							elegirEspectaculo = true;
						}

					} catch (InputMismatchException e) {
						System.out.println("Se ha introducido un valor no válido.");
						read.nextLine();
					}

				} while (elegirEspectaculo);

				if (idesp != 0) {

					System.out.println("Introduzca un nombre único para el espectáculo.");
					String newName = read.nextLine();
					if (newName.length() > 25) {
						System.out.println("El nombre no debe superar los 25 caracteres.");
						break;
					}
					if (newName.isEmpty()) {
						System.out.println("El nombre no debe estar vacío.");
						break;
					}
					System.out.println("Introduzca una nueva fecha inicial para el espectáculo. (yyyy-mm-dd)");
					try {
						dateSt = LocalDate.parse(read.next());
						read.nextLine();
					} catch (DateTimeParseException e) {
						System.out.println("La fecha no es válida.");
						break;
					}
					System.out.println("Introduzca una nueva fecha final para el espectáculo. (yyyy-mm-dd)");
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

					if (!newName.equals(EspectaculoControl.getMapEspectaculos().get(idesp))
							&& Validaciones.existeEspectaculo(newName)) {
						System.out.println("Ya existe un espectáculo con ese nombre.");
						break;
					}

					idCoordinador = 0L;
					coordinadores = CoordinacionControl.getMapCoordinadores();

					if (sesion.getPerfil() == Perfil.COORDINACION) {

						// Obtener el ID del coordinador en sesion
						idCoordinador = CoordinacionControl.getCoordinadorId(sesion.getNombre());

					} else {

						if (coordinadores.isEmpty()) {
							System.out.println(
									"No existen coordinadores. Registra uno antes de modificar un espectáculo, lo cual no sería posible en teoría si no existen coordinadores... huh...");
							break;
						}

						boolean idVal = false;
						do {
							System.out.println("Elige un nuevo coordinador para el espectáculo (elije su id).");
							System.out.println("Coordinadores: ");
							for (Map.Entry<Long, String> en : coordinadores.entrySet()) {

								System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
							}

							try {

								idCoordinador = read.nextLong();
								read.nextLine();

								if (!coordinadores.containsKey(idCoordinador))
									System.out.println(
											"El id seleccionado no pertenece a ningún coordinador. Inténtelo de nuevo.\n");

								else
									idVal = true;

							} catch (InputMismatchException e) {
								System.out.println("Se ha introducido un valor no válido. Inténtelo de nuevo.\n");
								read.nextLine();
							}

						} while (!idVal);

					}

					System.out.println("El nuevo espectáculo se ve así:");
					System.out.println("Nombre: " + newName + "\nFecha Inicio: " + dateSt + "\nFecha Fin: " + dateEn
							+ "\nCoordinador/a: " + coordinadores.get(idCoordinador));

					do {
						System.out.println("¿La información es correcta? [Y/N]");
						info = read.next().toUpperCase().charAt(0);
						read.nextLine();
						switch (info) {
						case 'Y':
							Espectaculo esp = new Espectaculo(0L, idCoordinador, newName, dateSt, dateEn);

							if (NumeroControl.getMapNumero().size() < 3) {
								System.out.println(
										"No hay suficientes números. Registra al menos 3 antes de modificar un espectáculo.");
								break;
							}

							int numContador = 0;
							boolean elegirNumeros = true;
							List<Long> idNums = new ArrayList<>();
							Long idnum = 0L;
							System.out.println(
									"Elige al menos 3 números para el espectáculo. El orden de elección representará el orden de los números en el espectáculo.\nIntroduzca 0 si desea cancelar la operación.");
							do {
								System.out.println("Números: ");
								for (Map.Entry<Long, String> en : NumeroControl.getMapNumero().entrySet()) {
									System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
								}

								try {
									idnum = read.nextLong();
									read.nextLine();

									if (idnum == 0) {
										System.out.println("Operación cancelada.");
										elegirNumeros = false;
									} else if (!NumeroControl.getMapNumero().containsKey(idnum))
										System.out.println(
												"El id introducido no está asociado a ningún número. Inténtelo de nuevo.");

									else if (Validaciones.numeroEnEspectaculo(idnum, idesp))
										System.out.println("El número ya está asociado a otro espectáculo.");

									else if (idNums.contains(idnum))
										System.out.println(
												"No puede asociar el mismo número dos veces a un solo espectáculo. Inténtelo de nuevo.");
									else {
										idNums.add(idnum);
										System.out.println("Añadido: " + NumeroControl.getMapNumero().get(idnum));
										++numContador;
									}

									if (numContador >= 3) {
										char info2;
										do {
											System.out.println(
													"Ha añadido suficientes números. ¿Desea seguir añadiendo? [Y/N]");
											info2 = read.next().toUpperCase().charAt(0);
											read.nextLine();
											switch (info2) {
											case 'Y':
												break;

											case 'N':
												elegirNumeros = false;
												break;

											default:
												System.out.println(
														"Se ha introducido un valor no válido. Vuelva a intentarlo.");
											}
										} while (info2 != 'Y' && info2 != 'N');

									}
								} catch (InputMismatchException e) {
									System.out.println("Se ha introducido un valor no válido.");
									read.nextLine();
								}

							} while (elegirNumeros);

							if (idnum != 0) {
								RegistroControl.modificarEspectaculo(esp, idesp, idNums);
								System.out.println("El espectáculo se ha modificado con éxito.");
							}
							break;

						case 'N':
							break;

						default:
							System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
						}
					} while (info != 'Y' && info != 'N');

					break;

				}

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

	private static void verEspectaculos() {
		List<Espectaculo> espectaculos = EspectaculoControl.getEspectaculos();
		if (espectaculos.isEmpty()) {
			System.out.println("No hay espectáculos programados.");
			return;
		}

		if (sesion.getPerfil() == Perfil.INVITADO) {
			System.out.println("Estos son los espectáculos programados:");
			for (Espectaculo e : espectaculos) {
				System.out.println(" " + e.toString());
			}
			System.out.println();
		} else {

			List<Coordinacion> coordinadores = CoordinacionControl.getCoordinacion();
			List<Numero> numeros = NumeroControl.getNumero();
			List<Artista> artistas = ArtistaControl.getArtista();
			Long idesp = 0L;
			boolean elegirEspectaculo = false;

			do {
				System.out.println("Estos son los espectáculos programados:");
				for (Espectaculo e : espectaculos) {
					System.out.println(" " + e.toString());
				}
				System.out.println("Introduce su id para ver sus datos o 0 para cancelar.");

				try {
					idesp = read.nextLong();
					read.nextLine();

					if (idesp == 0) {
						System.out.println("Operación cancelada.");
						elegirEspectaculo = false;

					} else if (!EspectaculoControl.getMapEspectaculos().containsKey(idesp)) {
						System.out.println(
								"El id introducido no está asociado a ningún espectáculo. Inténtelo de nuevo.");
						elegirEspectaculo = true;
					}

				} catch (InputMismatchException e) {
					System.out.println("Se ha introducido un valor no válido.");
					read.nextLine();
				}

			} while (elegirEspectaculo);

			Long idNum;
			if (idesp != 0) {
				for (Espectaculo e : espectaculos) {
					if (e.getId() == idesp) {
						System.out.println(" " + e.toString());
						for (Coordinacion c : coordinadores) {
							if (c.getIdCoord() == e.getIdCoordinacion()) {
								System.out.println(" ^ Coordinador/a:" + "\n\tNombre: " + c.getNombre() + "\n\tEmail: "
										+ c.getEmail() + "\n\tSenior: " + c.isSenior());
								for (Numero n : numeros) {
									if (n.getIdEspec() == idesp) {
										System.out.println(" ^ Números:");
										System.out.println("\t" + n.toString());
										idNum = n.getId();
										System.out.println("\t ^ Artistas participantes:");
										for (Long id : NumeroControl.getArtistasNumero(idNum)) {
											for (Artista a : artistas) {
												if (a.getIdArt() == id) {
													System.out.print("\t\tNombre: " + a.getNombre()
															+ "\n\t\tNacionalidad: " + a.getNacionalidad()
															+ "\n\t\tEspecialidades: " + a.especialidadesToString());
													if (a.getApodo() != null) {
														System.out.print("\n\t\tApodo: " + a.getApodo());
													}
													System.out.println("\n");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static void cmNumero() {
		char menu;
		do {
			System.out.println("2 - Crear número\n1 - Modificar número existente\n0 - Cancelar");
			menu = read.next().charAt(0);
			read.nextLine();

			switch (menu) {
			// Crear numero
			case '2':
				System.out.println("Introduzca un nombre único para el número.");
				String name = read.nextLine();

				System.out.println("Introduce la duración del número (min).");
				double dur;
				try {
					dur = read.nextDouble();
					read.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("Se ha introducido un valor no válido.");
					read.nextLine();
					break;
				}
				if ((dur % 0.5) != 0) {
					System.out.println("La duración del numero debe acabar en ,5 o ,0.");
					break;
				}

				if (Validaciones.existeNumero(name)) {
					System.out.println("Ya existe un número con ese nombre.");
					break;
				}

				System.out.println("El nuevo número se ve así:");
				System.out.println("Nombre: " + name + "\nDuración: " + dur + " min");
				char info;
				do {
					System.out.println("¿La información es correcta? [Y/N]");
					info = read.nextLine().toUpperCase().charAt(0);
					switch (info) {
					case 'Y':
						Numero nu = new Numero(0L, name, dur);

						if (ArtistaControl.getMapArtista().isEmpty()) {
							System.out.println(
									"No hay artistas registrados. Registra al menos 1 antes de crear un nuevo número.");
							break;
						}

						boolean elegirArtistas = true;
						List<Long> idArts = new ArrayList<>();
						Long idart = 0L;
						System.out.println(
								"Elige al menos 1 artista que participará en el número. \nIntroduzca 0 si desea cancelar la operación.");
						do {
							System.out.println("Artistas: ");
							for (Map.Entry<Long, String> en : ArtistaControl.getMapArtista().entrySet()) {

								System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
							}

							try {
								idart = read.nextLong();
								read.nextLine();

								if (idart == 0) {
									System.out.println("Operación cancelada.");
									elegirArtistas = false;

								} else if (!ArtistaControl.getMapArtista().containsKey(idart))
									System.out.println(
											"El id introducido no pertenece a ningún artista. Inténtelo de nuevo.");

								else if (idArts.contains(idart))
									System.out.println(
											"No puede asociar el mismo artista dos veces a un solo número. Inténtelo de nuevo.");
								else {
									idArts.add(idart);
									System.out.println("Añadido: " + ArtistaControl.getMapArtista().get(idart));
								}

								if (!idArts.isEmpty()) {
									char info2;
									do {
										System.out.println("¿Desea añadir otro artista más? [Y/N]");
										info2 = read.next().toUpperCase().charAt(0);
										read.nextLine();
										switch (info2) {
										case 'Y':
											break;

										case 'N':
											elegirArtistas = false;
											break;

										default:
											System.out.println(
													"Se ha introducido un valor no válido. Vuelva a intentarlo.");
										}
									} while (info2 != 'Y' && info2 != 'N');

								}
							} catch (InputMismatchException e) {
								System.out.println("Se ha introducido un valor no válido.");
								read.nextLine();
							}

						} while (elegirArtistas);

						if (idart != 0) {
							NumeroControl.setNumero(nu, idArts);
							System.out.println("El número se ha registrado con exito.");
						}
						break;

					case 'N':
						break;

					default:
						System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
					}
				} while (info != 'Y' && info != 'N');

				break;

			// Modificar numero
			case '1':
				if (NumeroControl.getMapNumero().isEmpty()) {
					System.out.println("No hay números registrados.");
					break;
				}

				boolean elegirNumero = true;
				Long idnum = 0L;
				System.out.println(
						"¿Qué número desea modificar? (Introduzca su id).\nIntroduzca 0 si desea cancelar la operación.");

				do {
					System.out.println("Números: ");
					for (Map.Entry<Long, String> en : NumeroControl.getMapNumero().entrySet()) {

						System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
					}

					try {
						idnum = read.nextLong();
						read.nextLine();
						elegirNumero = false;

						if (idnum == 0) {
							System.out.println("Operación cancelada.");
							elegirNumero = false;

						} else if (!NumeroControl.getMapNumero().containsKey(idnum)) {
							System.out
									.println("El id introducido no está asociado a ningún número. Inténtelo de nuevo.");
							elegirNumero = true;
						}

					} catch (InputMismatchException e) {
						System.out.println("Se ha introducido un valor no válido.");
						read.nextLine();
					}

				} while (elegirNumero);

				if (idnum != 0) {
					System.out.println("Introduzca un nuevo nombre para el número.");
					String newName = read.nextLine();

					System.out.println("Introduce la duración del número (min).");

					try {
						dur = read.nextDouble();
						read.nextLine();
					} catch (InputMismatchException e) {
						System.out.println("Se ha introducido un valor no válido.");
						read.nextLine();
						break;
					}
					if ((dur % 0.5) != 0) {
						System.out.println("La duración del numero debe acabar en ,5 o ,0.");
						break;
					}

					if (!newName.equals(NumeroControl.getMapNumero().get(idnum))
							&& Validaciones.existeNumero(newName)) {
						System.out.println("Ya existe un número con ese nombre.");
						break;
					}

					System.out.println("El nuevo número se ve así:");
					System.out.println("Nombre: " + newName + "\nDuración: " + dur + " min");

					do {
						System.out.println("¿La información es correcta? [Y/N]");
						info = read.nextLine().toUpperCase().charAt(0);
						switch (info) {
						case 'Y':
							Numero nu = new Numero(0L, newName, dur);

							if (ArtistaControl.getMapArtista().isEmpty()) {
								System.out.println(
										"No hay artistas registrados. Registra al menos 1 antes de crear un nuevo número.");
								break;
							}

							boolean elegirArtistas = true;
							List<Long> idArts = new ArrayList<>();
							Long idart = 0L;
							System.out.println(
									"Elige al menos 1 artista que participará en el número. \nIntroduzca 0 si desea cancelar la operación.");
							do {
								System.out.println("Artistas: ");
								for (Map.Entry<Long, String> en : ArtistaControl.getMapArtista().entrySet()) {

									System.out.println("- " + en.getValue() + " [Id " + en.getKey() + "]");
								}

								try {
									idart = read.nextLong();
									read.nextLine();

									if (idart == 0) {
										System.out.println("Operación cancelada.");
										elegirArtistas = false;

									} else if (!ArtistaControl.getMapArtista().containsKey(idart))
										System.out.println(
												"El id introducido no pertenece a ningún artista. Inténtelo de nuevo.");

									else if (idArts.contains(idart))
										System.out.println(
												"No puede asociar el mismo artista dos veces a un solo número. Inténtelo de nuevo.");
									else {
										idArts.add(idart);
										System.out.println("Añadido: " + ArtistaControl.getMapArtista().get(idart));
									}

									if (!idArts.isEmpty()) {
										char info2;
										do {
											System.out.println("¿Desea añadir otro artista más? [Y/N]");
											info2 = read.next().toUpperCase().charAt(0);
											read.nextLine();
											switch (info2) {
											case 'Y':
												break;

											case 'N':
												elegirArtistas = false;
												break;

											default:
												System.out.println(
														"Se ha introducido un valor no válido. Vuelva a intentarlo.");
											}
										} while (info2 != 'Y' && info2 != 'N');

									}
								} catch (InputMismatchException e) {
									System.out.println("Se ha introducido un valor no válido.");
									read.nextLine();
								}

							} while (elegirArtistas);

							if (idart != 0) {
								NumeroControl.modificarNumero(idnum, nu, idArts);
								System.out.println("El número se ha registrado con exito.");
							}

							break;

						case 'N':
							break;

						default:
							System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");
						}
					} while (info != 'Y' && info != 'N');

					break;
				}

				// Cancelar
			case '0':
				System.out.println("Operación cancelada.");
				break;

			default:
				System.out.println("Se ha introducido un valor no válido. Vuelva a intentarlo.");

			}
		} while (menu != '0');
	}

	private static void cerrarSesion() {
		sesion.setPerfil(Perfil.INVITADO);
		sesion.setNombre("Invitado");
		System.out.println("Su sesión ha sido cerrada");
	}
}
