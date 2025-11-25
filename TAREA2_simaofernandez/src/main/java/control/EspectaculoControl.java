package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.EspectaculoDAO;
import modelo.Espectaculo;

public class EspectaculoControl {

	public static List<Espectaculo> getEspectaculos(){
		return EspectaculoDAO.listarEspectaculos();
	}
	
	public static Map<Long, String> getMapEspectaculos() {

		Map<Long, String> mapEspectaculos = new LinkedHashMap<>();

		List<Espectaculo> espectaculos = EspectaculoDAO.listarEspectaculos();

		for (Espectaculo e : espectaculos) {
			mapEspectaculos.put(e.getId(), e.getNombre());
		}

		return mapEspectaculos;
	}

	public static boolean existeEspectaculo(String name) {
	
		List<Espectaculo> espectaculos = EspectaculoDAO.listarEspectaculos();
	
		for (Espectaculo e : espectaculos) {
			if (e.getNombre().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
}
