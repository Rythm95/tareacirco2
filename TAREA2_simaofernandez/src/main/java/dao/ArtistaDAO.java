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

	public Long insertarArtista(Artista a, Long idPersona) {
		String sql = "INSERT INTO artistas (apodo, idPersona) VALUES (?,?)";

		try (Connection con = ConexionDB.conectar();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, a.getApodo());
			ps.setLong(2, idPersona);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			Long idArtista = null;

			if (rs.next())
				idArtista = rs.getLong(1);

			// Relación Artista_Especialidad
			String sqlArt_Esp = "INSERT INTO artista_especialidad (idArtista, especialidad) VALUES (?, ?)";
			try (PreparedStatement ps2 = con.prepareStatement(sqlArt_Esp)) {
				for (Especialidad esp : a.getEspecialidades()) {
					ps2.setLong(1, a.getIdArt());
					ps2.setString(2, esp.name());
					ps2.executeUpdate();
				}
				
				return idArtista;
			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la b	ase de datos: " + e.getMessage());
			return null;
		}

	}
}
