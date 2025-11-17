/**
* Clase ConsultaBD.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaBD {

	public static String listarPersonas() {
		String sql = "Select id, email, nombre_persona, nacionalidad FROM personas";
		
		try (Connection con = ConexionBD.conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			
			String resp= "Personas registradas:\n";
			
			while (rs.next()) {
				Long id = rs.getLong("id");
				String email = rs.getString("email");
				String nombre_persona = rs.getString("nombre_persona");
				String nacionalidad = rs.getString("nacionalidad");
				
				resp = resp.concat(String.format("%d, %s, %s, %s%n", id, email, nombre_persona, nacionalidad));
			}
			
			return resp;
		}
		catch(SQLException e){
			return "Error al consultar la base de datos";
		}
		
	}
	
}
