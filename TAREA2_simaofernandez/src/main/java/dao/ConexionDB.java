/**
* Clase ConexionBD.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
* 
* Clase que gestiona la conexión a la base de datos.
* 
*/
package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConexionDB {

	private static ConexionDB instance;
	private static final Logger logger = Logger.getLogger(ConexionDB.class.getName());
	
	private static String url;
	private static String user;
	private static String password;

	// Constructor Single
	public ConexionDB() {
		Properties properties = new Properties();

		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
			properties.load(fis);
			url = properties.getProperty("urlbd");
			user = properties.getProperty("usubd");
			password = properties.getProperty("passbd");

		} catch (IOException e) {
			logger.warning("Error al cargar las propiedades: "+e.getMessage());
		}
	}
	
	public static ConexionDB getInstance() {
		if (instance == null) {
			instance = new ConexionDB();
		}
		return instance;
	}

	public Connection conectar() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			logger.warning("Error al conectar con la base de dato: "+e.getMessage());
			return null;
		}
	}

}