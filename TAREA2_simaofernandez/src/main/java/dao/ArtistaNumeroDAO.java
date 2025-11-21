package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ArtistaNumeroDAO {
	private static final Logger logger = Logger.getLogger(ArtistaDAO.class.getName());
	
	public void asignarArtistaANumero(int idArtista, int idNumero) {
	    String sql = "INSERT INTO artista_numero (idArtista, idNumero) VALUES (?, ?)";

	    try (Connection con = ConexionDB.getInstance().conectar();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, idArtista);
	        ps.setInt(2, idNumero);

	        ps.executeUpdate();
	    } catch (SQLException e) {
	    	logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}
	}

}
