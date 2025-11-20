package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import modelo.Artista;
import modelo.Especialidad;

public class ArtistaDAO {

	private static final Logger logger = Logger.getLogger(ArtistaDAO.class.getName());

	public static void insertarArtista(Artista a, Long idPersona) {
		String sql = "INSERT INTO artistas (idPersona, apodo, especialidades) VALUES (?,?,?)";

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setLong(1, idPersona);
			ps.setString(2, a.getApodo());
			ps.setString(3, a.especialidadesToString());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			Long idArtista = null;

			
		} catch (SQLException e) {
			logger.warning("Error al conectar con la b	ase de datos: " + e.getMessage());
		}

	}
}
