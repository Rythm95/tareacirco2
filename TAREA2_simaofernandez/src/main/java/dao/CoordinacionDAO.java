/**
* Clase CoordinacionDAO.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import modelo.Coordinacion;

public class CoordinacionDAO {

	private static final Logger logger = Logger.getLogger(CoordinacionDAO.class.getName());

	public static Long insertarCoordinacion(Coordinacion c, Long idPersona) {
		String sql = "INSERT INTO coordinacion(idPersona, senior, fechaSenior) VALUES (?, ?, ?)";

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setLong(1, idPersona);
			ps.setBoolean(2, c.isSenior());

			if (c.isSenior())
				ps.setDate(3, Date.valueOf(c.getFechasenior()));
			else
				ps.setNull(3, java.sql.Types.DATE);

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

	public static List<Coordinacion> listarCoordinacion() {
		String sql = "SELECT p.id, p.email, p.nombre_persona, p.nacionalidad, c.idCoordinacion, c.senior, c.fechaSenior FROM coordinacion c INNER JOIN personas p ON c.idPersona = p.id";
		List<Coordinacion> resp = new ArrayList<>();

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			Coordinacion coordinacion;
			while (rs.next()) {
				Long id = rs.getLong("id");
				String email = rs.getString("email");
				String nombre = rs.getString("nombre_persona");
				String nacionalidad = rs.getString("nacionalidad");

				Long idCoordinacion = rs.getLong("idCoordinacion");
				boolean senior = rs.getBoolean("senior");
				Date fechaSeniorDate = rs.getDate("fechaSenior");

				LocalDate fechaSenior;
				if (fechaSeniorDate == null)
					fechaSenior = null;
				else
					fechaSenior = fechaSeniorDate.toLocalDate();

				coordinacion = new Coordinacion(id, email, nombre, nacionalidad, idCoordinacion, senior, fechaSenior);
				resp.add(coordinacion);
			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;

	}

	public static Map<String, Long> credencialesCoordinacion() {
		String sql = "SELECT c.idCoordinacion, cr.usuario  FROM credenciales cr INNER JOIN coordinacion c ON cr.idPersona = c.idPersona";
		Map<String, Long> usuarioCoordinacion = new HashMap<>();

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Long idCoordinacion = rs.getLong("idCoordinacion");
				String usuario = rs.getString("usuario");

				usuarioCoordinacion.put(usuario, idCoordinacion);
			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return usuarioCoordinacion;

	}

	public static void actualizarCoordinacion(Coordinacion c, Long idPersona) {
		String sql = "UPDATE coordinacion SET senior = ?, fechaSenior = ? WHERE idPersona = ?";

		try (Connection con = ConexionDB.getInstance().conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setBoolean(1, c.isSenior());

			if (c.isSenior())
				ps.setDate(2, Date.valueOf(c.getFechasenior()));
			else
				ps.setNull(2, java.sql.Types.DATE);

			ps.setLong(3, idPersona);

			ps.executeUpdate();

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
	}

}
