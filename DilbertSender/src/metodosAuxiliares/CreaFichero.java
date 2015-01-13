package metodosAuxiliares;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import clases.Persona;

public class CreaFichero {

	public static void main(String[] args) {

		// Guardar en el fichero los nombres iniciales

		ArrayList<Persona> aux = new ArrayList<Persona>();
		Persona pAux;

		pAux = new Persona("Gunther", "gunther.auer@acciona.com", true);
		aux.add(pAux);
		pAux = new Persona("Ysabel", "ycarrilero@hotmail.com", true);
		aux.add(pAux);
		pAux = new Persona("Ander", "ander.arrieta.z@gmail.com", true);
		aux.add(pAux);
		pAux = new Persona("Andrea","amolina@acciona.com",true);
		aux.add(pAux);
		pAux = new Persona("Iván", "ivanagos@gmail.com", true);
		aux.add(pAux);
		
		try{
			OutputStream file = new FileOutputStream("personas.dse");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			
			output.writeObject(aux);
			
			output.flush();
			output.close();
			buffer.close();
			file.close();
			
			System.out.println("Fichero creado correctamente.");
		}
		catch(Exception e){
			System.out.println("Error. "+e.getLocalizedMessage());
		}
	}

}
