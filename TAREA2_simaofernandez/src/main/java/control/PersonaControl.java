package control;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.ArtistaDAO;
import dao.CoordinacionDAO;
import dao.PersonaDAO;
import modelo.Artista;
import modelo.Coordinacion;
import modelo.Persona;

public class PersonaControl {

	public static List<Persona> listarPersonas() {
		List<Persona> personas = new ArrayList<>();

		List<Artista> artistas = ArtistaDAO.listarArtista();
		List<Coordinacion> coordinadores = CoordinacionDAO.listarCoordinacion();

		for (Artista a : artistas)
			personas.add(a);

		for (Coordinacion c : coordinadores)
			personas.add(c);

		return personas;
	}

	public static Map<Long, String> getMapPersonas() {

		Map<Long, String> mapPersonas = new LinkedHashMap<>();

		List<Persona> personas = PersonaDAO.listarPersonas();

		for (Persona p : personas) {
			mapPersonas.put(p.getidPersona(), p.getNombre());
		}

		return mapPersonas;

	}
	
	public static Map<Long, String> getMapPersonasEmail() {

		Map<Long, String> mapPersonas = new LinkedHashMap<>();

		List<Persona> personas = PersonaDAO.listarPersonas();

		for (Persona p : personas) {
			mapPersonas.put(p.getidPersona(), p.getEmail());
		}

		return mapPersonas;

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

}
