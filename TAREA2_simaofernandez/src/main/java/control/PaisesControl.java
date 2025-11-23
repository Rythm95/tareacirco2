package control;

import java.util.Map;

import dao.AccesoPaises;

public class PaisesControl {

	public static Map<String, String> getPaises() {
		return AccesoPaises.loadPaises();
	}
	
}
