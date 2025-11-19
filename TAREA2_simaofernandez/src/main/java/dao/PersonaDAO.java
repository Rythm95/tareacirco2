/**
* Clase PersonaDAO.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import modelo.Persona;

// Esto en principio no??
//PersonaDAO y todo lo que tenga que ver con personas (Connection con, PreparedStatement ps, ResultSet rs)
public class PersonaDAO {

	private static final Logger logger = Logger.getLogger(PersonaDAO.class.getName());

	public static List<Persona> listarPersonas() {
		String sql = "SELECT id, email, nombre_persona, nacionalidad FROM personas";
		List<Persona> resp = new ArrayList<>();

		try (Connection con = ConexionDB.conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			
			con.setAutoCommit(false);
			
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
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;

	}

	public Long insertarPersona(Persona p) {
		String sql = "INSERT INTO personas (email, nombre_persona, nacionalidad) VALUES (?,?,?)";

		try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, p.getEmail());
			ps.setString(2, p.getNombre());
			ps.setString(3, p.getNacionalidad());

			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getLong(1);
			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return null;
	}

}
