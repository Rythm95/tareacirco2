package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import modelo.Espectaculo;

public class EspectaculoDAO {

	private static final Logger logger = Logger.getLogger(CoordinacionDAO.class.getName());

	public static Long insertarEspectaculo(Espectaculo e) {
		String sql = "INSERT INTO espectaculos(idCoordinacion, nombre, fecha_ini, fecha_fin) VALUES (?, ?, ?, ?)";

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setLong(1, e.getIdCoordinacion());
			ps.setString(2, e.getNombre());
			ps.setDate(3, Date.valueOf(e.getFechaini()));
			ps.setDate(4, Date.valueOf(e.getFechafin()));

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getLong(1);
			}

		} catch (SQLException ex) {
			logger.warning("Error al conectar con la base de datos: " + ex.getMessage());
		}

		return null;
	}

	public static List<Espectaculo> listarEspectaculos() {
		String sql = "SELECT id, idCoordinacion, nombre, fecha_ini, fecha_fin FROM espectaculos";
		List<Espectaculo> resp = new ArrayList<>();

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			Espectaculo espectaculo;
			while (rs.next()) {
				Long id = rs.getLong("id");
				Long idCoordinacion = rs.getLong("idCoordinacion");
				String nombre = rs.getString("nombre");
				Date fecha_ini = rs.getDate("fecha_ini");
				Date fecha_fin = rs.getDate("fecha_fin");

				espectaculo = new Espectaculo(id, idCoordinacion, nombre, fecha_ini.toLocalDate(),
						fecha_fin.toLocalDate());
				resp.add(espectaculo);
			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;

	}

	public static void actualizarEspectaculo(Espectaculo e, Long idEsp) {
		String sql = "UPDATE espectaculos SET idCoordinacion = ?, nombre = ?, fecha_ini = ?, fecha_fin = ? WHERE id = ?";

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, e.getIdCoordinacion());
			ps.setString(2, e.getNombre());
			ps.setDate(3, Date.valueOf(e.getFechaini()));
			ps.setDate(4, Date.valueOf(e.getFechafin()));
			ps.setLong(5, idEsp);

			ps.executeUpdate();

		} catch (SQLException ex) {
			logger.warning("Error al conectar con la base de datos: " + ex.getMessage());
		}

	}

}
