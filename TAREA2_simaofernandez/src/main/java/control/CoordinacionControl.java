package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.CoordinacionDAO;
import modelo.Coordinacion;

public class CoordinacionControl {

	public static Map<Long, String> getMapCoordinadores() {

		Map<Long, String> mapCoordinadores = new LinkedHashMap<>();

		List<Coordinacion> coordinadores = CoordinacionDAO.listarCoordinacion();

		for (Coordinacion c : coordinadores) {
			mapCoordinadores.put(c.getIdCoord(), c.getNombre());
		}

		return mapCoordinadores;
	}

}
