/**
* Clase ConexionBD.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

	private static String url;
	private static String user;
	private static String password;
	
	static {
	
		Properties properties = new Properties();
	
		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
			properties.load(fis);
			url = properties.getProperty("urlbd");
			user = properties.getProperty("usubd");
			password = properties.getProperty("passbd");
			
		} catch (IOException e) {
			System.out.println("Error al cargar las propiedades.");
		}

	}
	
	public static Connection conectar(){
		try {
			return DriverManager.getConnection(url,user,password);
		}
		catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos.");
			return null;
		}
	}
	
}