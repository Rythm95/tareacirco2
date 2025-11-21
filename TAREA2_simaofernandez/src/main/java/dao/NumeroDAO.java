package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import modelo.Artista;
import modelo.Especialidad;
import modelo.Numero;

public class NumeroDAO {
	private static final Logger logger = Logger.getLogger(ArtistaDAO.class.getName());

	public static Long insertarNumero(Numero n) {
		String sql = "INSERT INTO numeros (orden, nombre, duracion) VALUES (?,?,?)";

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, n.getOrden());
			ps.setString(2, n.getNombre());
			ps.setDouble(3, n.getDuracion());

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
	
	public static List<Numero> listarNumero() {
		String sql = "SELECT id, orden, nombre, duracion, idEspectaculo FROM numeros";
		List<Numero> resp = new ArrayList<>();

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			Numero numero;
			while (rs.next()) {
				Long id = rs.getLong("id");
				int orden = rs.getInt("orden");
				String nombre = rs.getString("nombre");
				double duracion = rs.getDouble("duracion");
				Long idEspectaculo = rs.getLong("idEspectaculo");

				numero = new Numero(id, orden, nombre, duracion, idEspectaculo);
				resp.add(numero);

			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;
	}
	
	public boolean actualizarEspectaculoNumero(Long idNumero, Long idEspectaculo) {
		String sql = "UPDATE numeros SET idEspectaculo = ? WHERE id = ?";
		
		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setLong(1, idEspectaculo);
			ps.setLong(2, idNumero);
			
		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		
		return false;
	}

}
