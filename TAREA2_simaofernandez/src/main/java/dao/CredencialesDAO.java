package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import modelo.Credenciales;
import modelo.Perfil;

public class CredencialesDAO {

	private static final Logger logger = Logger.getLogger(CredencialesDAO.class.getName());

	public static Long insertarCredenciales(Credenciales c, Long idPersona) {
		String sql = "INSERT INTO credenciales (usuario, password, perfil, idPersona) VALUES (?, ?, ?, ?)";

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, c.getUser());
			ps.setString(2, c.getPassword());
			ps.setString(3, c.getPerfil().name());
			ps.setLong(4, idPersona);

			ps.executeUpdate();

			// Id Credenciales
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				c.setId(rs.getLong(1));
				return rs.getLong(1);
			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}

		return null;

	}
	
	public static List<Credenciales> listarCredenciales() {
		String sql = "SELECT id, usuario, password, perfil FROM credenciales";
		List<Credenciales> resp = new ArrayList<>();

		try (Connection con = ConexionDB.getInstance().conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			Credenciales creds;
			while (rs.next()) {
				Long id = rs.getLong("id");
				String usuario = rs.getString("usuario");
				String password = rs.getString("password");
				String perfil = rs.getString("perfil");

				creds = new Credenciales(id, usuario, password, Perfil.valueOf(perfil.toUpperCase()));
				resp.add(creds);
			}

		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
		return resp;

	}
}
