/**
* Clase InsertarPersona.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Persona;

public class InsertarDB {
	
	public static boolean insertarPersona(Persona p) {
		String sql = "INSERT INTO personas (email, nombre_persona, nacionalidad) VALUES (?,?,?)";
		
		try(Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)){
			
			ps.setString(1, p.getEmail());
			ps.setString(2, p.getNombre());
			ps.setString(3, p.getNacionalidad());
			
			ps.executeUpdate();
			return true;
			
			
			
		} catch (SQLException e) {
			return false;
		}
		
	}

}
