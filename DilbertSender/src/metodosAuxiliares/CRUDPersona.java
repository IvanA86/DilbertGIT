package metodosAuxiliares;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import clases.Persona;

public class CRUDPersona {
	
	private static String NOM_FICHERO = "personas.dse";
	
	public static ArrayList<Persona> addPersona(Persona p){
		ArrayList<Persona> personas = leerFichero();
		
		if (personas!=null){
			personas.add(p);
		}
		
		return personas;
	}

	public static ArrayList<Persona> quitaPersona(Persona p){
		ArrayList<Persona> personas = leerFichero();
		
		if (personas!=null){
			if (personas.contains(p)){
				personas.remove(p);
			}
		}
		
		return personas;
	}

	@SuppressWarnings("unchecked")
	private static ArrayList<Persona> leerFichero() {
		ArrayList<Persona> p = new ArrayList<Persona>();
		
		try{
			InputStream file = new FileInputStream(NOM_FICHERO);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(buffer);
			
			p = (ArrayList<Persona>)ois.readObject();
			
			ois.close();
			buffer.close();
			file.close();
		}
		catch (Exception e){
			p = null;
		}
		return p;
	}
}
