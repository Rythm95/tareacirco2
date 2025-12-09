package prueba;

import dao.PersonaDAO;

public class PruebaMain {

	public static void main(String[] args) {

		Payaso p1 = new Payaso(0L, "prueba@mail.com", "Chuckles", "Bremen", 0L, Color.Verde);
		Payaso p2 = new Payaso(0L, "prueba2@mail.com", "Giggle", "Shritchzland", 0L);

		Long personaId;

		personaId = PersonaDAO.insertarPersona(p2);
		PayasoDAO.insertarPayaso(p2, personaId);

		System.out.println("Done 2");

	}

}
