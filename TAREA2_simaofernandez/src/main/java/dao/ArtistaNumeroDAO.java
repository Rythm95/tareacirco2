package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ArtistaNumeroDAO {
	private static final Logger logger = Logger.getLogger(ArtistaDAO.class.getName());

	public static void asignarArtistaNumero(Long idArtista, Long idNumero) {
		String sql = "INSERT INTO artista_numero (idArtista, idNumero) VALUES (?, ?)";

		try (Connection con = ConexionDB.getInstance().conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, idArtista);
			ps.setLong(2, idNumero);

			ps.executeUpdate();
		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
	}

	public static List<Long> mapArtistaNumero(Long idNum) {
		String sql = "SELECT idArtista FROM artista_numero WHERE idNumero = ?";

		List<Long> resp = new ArrayList<>();

		try (Connection con = ConexionDB.getInstance().conectar();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setLong(1, idNum);

		        try (ResultSet rs = ps.executeQuery()) {
		            while (rs.next()) {
		                Long idArtista = rs.getLong("idArtista");
		                resp.add(idArtista);
		            }
		        }

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;
	}
	
	public static List<Long> mapNumerosArtista(Long idArt) {
		String sql = "SELECT idNumero FROM artista_numero WHERE idArtista = ?";

		List<Long> resp = new ArrayList<>();

		try (Connection con = ConexionDB.getInstance().conectar();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setLong(1, idArt);

		        try (ResultSet rs = ps.executeQuery()) {
		            while (rs.next()) {
		                Long idNumero = rs.getLong("idNumero");
		                resp.add(idNumero);
		            }
		        }

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;
	}

	public static void eliminarArtistaNumero(Long idNumero) {
		String sql = "DELETE FROM artista_numero WHERE idNumero = ?";

		try (Connection con = ConexionDB.getInstance().conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, idNumero);

			ps.executeUpdate();
		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}

	}

}
