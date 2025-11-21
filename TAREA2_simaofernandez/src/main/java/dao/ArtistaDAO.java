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

import modelo.Artista;
import modelo.Coordinacion;
import modelo.Especialidad;

public class ArtistaDAO {

	private static final Logger logger = Logger.getLogger(ArtistaDAO.class.getName());

	public static Long insertarArtista(Artista a, Long idPersona) {
		String sql = "INSERT INTO artistas (idPersona, apodo, especialidades) VALUES (?,?,?)";

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setLong(1, idPersona);
			ps.setString(2, a.getApodo());
			ps.setString(3, a.especialidadesToString());

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

	public static List<Artista> listarArtista() {
		String sql = "SELECT p.id, p.email, p.nombre_persona, p.nacionalidad, a.idArtista, a.apodo, a.especialidades FROM artistas a INNER JOIN personas p ON a.idPersona = p.id";
		List<Artista> resp = new ArrayList<>();

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			Artista artista;
			while (rs.next()) {
				Long id = rs.getLong("id");
				String email = rs.getString("email");
				String nombre = rs.getString("nombre_persona");
				String nacionalidad = rs.getString("nacionalidad");

				Long idArtista = rs.getLong("idArtista");
				String apodo = rs.getString("apodo");
				String especialidades = rs.getString("especialidades");

				String[] especialidadesSplit = especialidades.toUpperCase().split(",");
				List<Especialidad> listaEspecialidades = new ArrayList<>();

				for (String es : especialidadesSplit) {
					listaEspecialidades.add(Especialidad.valueOf(es.trim()));
				}
				artista = new Artista(id, email, nombre, nacionalidad, idArtista, apodo, listaEspecialidades);
				resp.add(artista);

			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;
	}

}
