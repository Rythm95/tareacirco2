package prueba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.ArtistaDAO;
import dao.ConexionDB;

public class PayasoDAO {

	private static final Logger logger = Logger.getLogger(ArtistaDAO.class.getName());

	public static Long insertarPayaso(Payaso p, Long idPersona) {
		String sql = "INSERT INTO payasos (idPersona, colorNariz) VALUES (?,?)";

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setLong(1, idPersona);
			if (p.getColorNariz() != null)
				ps.setString(2, p.getColorNariz().toString());

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

	public static List<Payaso> listarPayaso() {
		String sql = "SELECT pe.id, pe.email, pe.nombre_persona, pe.nacionalidad, pa.idPayaso, pa.colorNariz FROM payasos pa INNER JOIN personas p ON pa.idPersona = pe.id";
		List<Payaso> resp = new ArrayList<>();

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			Payaso payaso;
			while (rs.next()) {
				Long id = rs.getLong("id");
				String email = rs.getString("email");
				String nombre = rs.getString("nombre_persona");
				String nacionalidad = rs.getString("nacionalidad");

				Long idPayaso = rs.getLong("idPayaso");
				String color = rs.getString("colorNariz").toUpperCase();

				payaso = new Payaso(id, email, nombre, nacionalidad, idPayaso, Color.valueOf(color));
				resp.add(payaso);
			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;
	}
}
