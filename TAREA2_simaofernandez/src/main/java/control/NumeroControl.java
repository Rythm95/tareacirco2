package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.ArtistaNumeroDAO;
import dao.NumeroDAO;
import modelo.Numero;

public class NumeroControl {

	public static List<Numero> getNumero() {
		return NumeroDAO.listarNumero();
	}
	
	public static void setNumero(Numero n, List<Long> artistas) {
		Long idNumero = NumeroDAO.insertarNumero(n);
		for (Long id : artistas) {
			ArtistaNumeroDAO.asignarArtistaNumero(id, idNumero);
		}
	}
	
	public static Map<Long, String> getMapNumero() {

		Map<Long, String> mapNumeros = new LinkedHashMap<>();

		List<Numero> numeros = NumeroDAO.listarNumero();

		for (Numero n : numeros) {
			mapNumeros.put(n.getId(), n.getNombre());
		}

		return mapNumeros;
	}

	public static void modificarNumero(Long idNum, Numero n, List<Long> artistas) {
		NumeroDAO.actualizarNumero(idNum, n);
		
		ArtistaNumeroDAO.eliminarArtistaNumero(idNum);
		
		for (Long id : artistas) {
			ArtistaNumeroDAO.asignarArtistaNumero(id, idNum);
		}
	}
	
	public static List<Long> getArtistasNumero(Long idNum) {
		return ArtistaNumeroDAO.mapArtistaNumero(idNum);
	}
	
}
