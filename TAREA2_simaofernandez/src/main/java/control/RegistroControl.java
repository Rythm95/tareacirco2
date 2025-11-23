package control;

import java.util.List;

import dao.ArtistaDAO;
import dao.CoordinacionDAO;
import dao.CredencialesDAO;
import dao.EspectaculoDAO;
import dao.NumeroDAO;
import dao.PersonaDAO;
import modelo.Artista;
import modelo.Coordinacion;
import modelo.Credenciales;
import modelo.Espectaculo;
import modelo.Numero;
import modelo.Persona;

public class RegistroControl {

	public static void registrarPersona(Persona p, Credenciales cr) {

		Long personaId = PersonaDAO.insertarPersona(p);

		if (p instanceof Artista) {
			Artista a = (Artista) p;
			ArtistaDAO.insertarArtista(a, personaId);
		} else {
			Coordinacion c = (Coordinacion) p;
			CoordinacionDAO.insertarCoordinacion(c, personaId);
		}

		CredencialesDAO.insertarCredenciales(cr, personaId);

	}
	
	public static void modificarPersonas(Persona p, Long idPersona) {
		
		PersonaDAO.actualizarPersona(p, idPersona);
		
		if (p instanceof Artista) {
			Artista a = (Artista) p;
			ArtistaDAO.actualizarArtista(a, idPersona);
		} else {
			Coordinacion c = (Coordinacion) p;
			CoordinacionDAO.actualizarCoordinacion(c, idPersona);
		}
		
	}

	public static void registrarEspectaculo(Espectaculo e, List<Long> id) {

		Long espectaculoId = EspectaculoDAO.insertarEspectaculo(e);
		int orden = 0;

		for (Long i : id) {
			++orden;
			NumeroDAO.actualizarNumeroEspectaculo(i, orden, espectaculoId);
		}

	}

	public static void modificarEspectaculo(Espectaculo e, Long idesp, List<Long> id) {

		EspectaculoDAO.actualizarEspectaculo(e, idesp);
		List<Numero> numeros = NumeroDAO.listarNumero();
		int orden = 0;

		for (Numero n : numeros) {
			if (n.getIdEspec() != null && n.getIdEspec().equals(idesp)) {
				NumeroDAO.actualizarNumeroEspectaculo(n.getId(), 0, null);
			}
		}
		
		for (Long i : id) {
			++orden;
			NumeroDAO.actualizarNumeroEspectaculo(i, orden, idesp);
		}
	}

}
