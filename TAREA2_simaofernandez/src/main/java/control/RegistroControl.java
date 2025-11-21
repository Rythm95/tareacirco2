package control;

import dao.ArtistaDAO;
import dao.CoordinacionDAO;
import dao.CredencialesDAO;
import dao.EspectaculoDAO;
import dao.PersonaDAO;
import modelo.Artista;
import modelo.Coordinacion;
import modelo.Credenciales;
import modelo.Espectaculo;
import modelo.Persona;

public class RegistroControl {

	public static void registrarPersona(Persona p, Credenciales cr) {
		
		Long personaId = PersonaDAO.insertarPersona(p);
		
		if (p instanceof Artista) {
			Artista a = (Artista) p;
			ArtistaDAO.insertarArtista(a, personaId);
		}
		else {
			Coordinacion c = (Coordinacion) p;
			CoordinacionDAO.insertarCoordinacion(c, personaId);
		}
		
		CredencialesDAO.insertarCredenciales(cr, personaId);
			
	}
	
	public static void registrarEspectaculo(Espectaculo e) {
		
		Long espectaculoId = EspectaculoDAO.insertarEspectaculo(e);
	}
	
}
