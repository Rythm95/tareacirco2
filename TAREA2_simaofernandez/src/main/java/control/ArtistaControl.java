package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.ArtistaDAO;
import dao.ArtistaNumeroDAO;
import modelo.Artista;

public class ArtistaControl {

	public static List<Artista> getArtista() {
		return ArtistaDAO.listarArtista();
	}
	
	public static Map<Long, String> getMapArtista() {

		Map<Long, String> mapArtistas = new LinkedHashMap<>();

		List<Artista> artistas = ArtistaDAO.listarArtista();

		for (Artista a : artistas) {
			mapArtistas.put(a.getIdArt(), a.getNombre());
		}

		return mapArtistas;
	}
	
	public static Long getArtistaId(String usuario) {
		Map<String, Long> credencialesArtista = ArtistaDAO.credencialesArtista();
		
		return credencialesArtista.get(usuario);
	}
	
	public static List<Long> getNumeroArtistas(Long idArt) {
		return ArtistaNumeroDAO.mapNumerosArtista(idArt);
	}

}
