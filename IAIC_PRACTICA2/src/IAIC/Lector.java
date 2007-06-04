/**
 * 
 */
package IAIC;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Luis
 *
 */
public class Lector {

	private static final char CHAR_SEPARADOR = ';';
	
	/**
	 * Método que devuelve un vector con los puntos contenidos en el
	 * fichero pasado, siempre que este contenga el formato especificado.
	 * @param Ruta Ruta donde se encuentra el fichero con los puntos.
	 * @return Vector<Punto> Vector con los puntos.
	 */
	public static Vector<Punto> leer(String Ruta){
		BufferedReader buffer;
		Vector<Punto> v_puntos = new Vector<Punto>();
		int tamano_puntos;
		try {
			buffer = new BufferedReader(new FileReader (Ruta));
		} catch (FileNotFoundException e) {			
			System.out.println("Error, no se encuentra el fichero");
			System.exit(-1);	
			return null;
		}
		String str;
		try {
			str=buffer.readLine();
			tamano_puntos = Integer.parseInt(str);
			while (((str=buffer.readLine()) != null ) && !(str.equals(""))){
				double array[] = new double[tamano_puntos];
				int ultimo_sep = 0;
				int sep_anterior = -1;
				
				// Obtenemos tantos doubles como sea necesario.
				for (int i=0;i<tamano_puntos;i++){
					String str2;
					double num;
					sep_anterior++;
					ultimo_sep = str.indexOf(CHAR_SEPARADOR,sep_anterior);
					str2 = str.substring(sep_anterior,ultimo_sep);
					num = Double.parseDouble(str2);
					array[i]=num;
					sep_anterior = ultimo_sep;					
				}
				Punto punt = new Punto(tamano_puntos,array);
				v_puntos.add(punt);
			}
		} catch (IOException e) {
			System.out.println("Error, fallo en el fichero");
			System.exit(-1);
		}
		return v_puntos;
	}
	
	
	
	public static void main (String args[]) throws Exception{		
		Lector.leer("fichero.txt");
	}
}
