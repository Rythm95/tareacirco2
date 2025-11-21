package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.ArtistaDAO;
import modelo.Artista;

public class ArtistaControl {

	public static Map<String, Long> getMapArtista() {

		Map<String, Long> mapArtistas = new LinkedHashMap<>();

		List<Artista> artistas = ArtistaDAO.listarArtista();

		for (Artista a : artistas) {
			mapArtistas.put(a.getNombre(), a.getIdArt());
		}

		return mapArtistas;
	}

}
