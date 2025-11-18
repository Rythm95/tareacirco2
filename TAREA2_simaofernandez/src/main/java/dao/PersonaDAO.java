/**
* Clase ConsultaBD.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Persona;


//PersonaDAO y todo lo que tenga que ver con personas (Connection con, PreparedStatement ps, ResultSet rs)
public class PersonaDAO {

				// Lista de Persona
	public static List<Persona> listarPersonas() {
		String sql = "SELECT id, email, nombre_persona, nacionalidad FROM personas";
		List<Persona> resp = new ArrayList<>();

		try (Connection con = ConexionDB.conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			Persona persona;
			while (rs.next()) {
				Long id = rs.getLong("id");
				String email = rs.getString("email");
				String nombre_persona = rs.getString("nombre_persona");
				String nacionalidad = rs.getString("nacionalidad");

				persona = new Persona(id, email, nombre_persona, nacionalidad);
				resp.add(persona);
			}

		} catch (SQLException e) {
			// Empty

		}
		return resp;

	}

}
