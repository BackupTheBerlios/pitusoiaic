package Vista;
import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 
 */

/**
 * Clase que crea un fliro de archivos para el tipo txt.
 */
public class FiltroArchivos extends FileFilter{

	/**
	 * Metodo que verifica el tipo de archivo.
	 * @return booleano que si se acepta un archivo
	 */
	public boolean accept(File archivo) {
		if (archivo!=null) {
			if (archivo.isDirectory()) return false;
			String extension = devolverExtension(archivo);
			if (extension!=null)
				if (extension.compareTo("txt")==0) 
					return true;
		}
		return false;
	}

	/**
	 * Metodo que devuelve una descipcion del tipo de archivo de juego.
	 */
	public String getDescription() {
		return ("Archivos del juego (.txt)");
	}
	
	/**
	 * Metoto que devuelve ña extension de un archivo.
	 * @param f es un archivo que se pasa por parametro
	 * @return un string que es la extension del archivo
	 */
    public static String devolverExtension(File f) {
    	if(f != null) {
    	    String nombre = f.getName();
    	    int i = nombre.lastIndexOf('.');
    	    if((i>0) && (i<nombre.length()-1)) {
    	    	return nombre.substring(i+1).toLowerCase();
    	    };
    	}
    	return null;
    }
}
