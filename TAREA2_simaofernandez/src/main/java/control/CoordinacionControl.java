package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.CoordinacionDAO;
import modelo.Coordinacion;

public class CoordinacionControl {

	public static List<Coordinacion> getCoordinacion(){
		return CoordinacionDAO.listarCoordinacion();
	}
	
	public static Map<Long, String> getMapCoordinadores() {

		Map<Long, String> mapCoordinadores = new LinkedHashMap<>();

		List<Coordinacion> coordinadores = CoordinacionDAO.listarCoordinacion();

		for (Coordinacion c : coordinadores) {
			mapCoordinadores.put(c.getIdCoord(), c.getNombre());
		}

		return mapCoordinadores;
	}
	
	public static Long getCoordinadorId(String usuario) {
		Map<String, Long> credencialesCoordinacion = CoordinacionDAO.credencialesCoordinacion();
		
		return credencialesCoordinacion.get(usuario);
		
	}

}
