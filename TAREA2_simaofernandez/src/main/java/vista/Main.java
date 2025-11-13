/**
* Clase main.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package vista;

import dao.ConsultaBD;
import dao.InsertarPersona;
import modelo.Persona;

public class Main {

	public static void main(String[] args) {
		
		Persona p = new Persona(1L,"mail@ejemplo.es", "Ejemplo", "Bruselas");
		
		InsertarPersona.insertarPersona(p);
		
		
		System.out.println(ConsultaBD.listarPersonas());
		
		
	}

}
