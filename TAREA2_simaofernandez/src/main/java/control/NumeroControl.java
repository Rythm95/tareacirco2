package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.ArtistaDAO;
import dao.NumeroDAO;
import modelo.Artista;
import modelo.Numero;

public class NumeroControl {

	public static Map<Long, String> getMapNumero() {

		Map<Long, String> mapNumeros = new LinkedHashMap<>();

		List<Numero> numeros = NumeroDAO.listarNumero();

		for (Numero n : numeros) {
			mapNumeros.put(n.getId(), n.getNombre());
		}

		return mapNumeros;
	}

}
