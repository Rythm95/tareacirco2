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
import java.util.logging.Logger;

import modelo.Coordinacion;

public class CoordinacionDAO {

	private static final Logger logger = Logger.getLogger(CoordinacionDAO.class.getName());

	public static void insertarCoordinacion(Coordinacion c, Long idPersona) {
		String sql = "INSERT INTO coordinacion(idPersona, senior, fechaSenior) VALUES (?, ?, ?)";

		try (Connection con = ConexionDB.getInstance().conectar(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setLong(1, c.getidPersona());
			ps.setBoolean(2, c.isSenior());
			
			if (c.isSenior()) 
				ps.setDate(3, Date.valueOf(c.getFechasenior()));
			else
				ps.setNull(3, java.sql.Types.DATE);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de datos: " + e.getMessage());
		}

	}

}
