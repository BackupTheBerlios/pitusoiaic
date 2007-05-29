package IAIC;

import java.util.Vector;

public class Lloyd implements Algoritmos {
	
	private Vector<Punto> v_centros;
	
	public Lloyd(){
		v_centros = new Vector<Punto>(); 
	}

	public void Aprendizaje(int clases, Vector<Clase> centros) {		
		for (int i= 0;i<clases;i++){
			v_centros.add(centros.get(i).getCentro());
		}
	}
	
	private int getClase(Punto punto,Vector<Punto> puntos){
		if (puntos.size() == 0){
			System.out.println("No hay ningun punto con el que comparar.");
			return 0;
		}
		double dist_min;
		int clase=0;
		dist_min = puntos.get(0).distancia(punto);
		for (int i=1;i<puntos.size();i++){
			double aux = puntos.get(i).distancia(punto);
			if (aux < dist_min){
				dist_min = aux;
				clase = i;
			}
		}
		return clase;				
	}

	public int clase(Punto punto) {
		Vector<Punto> v = new Vector<Punto>();
		v.add(punto);
		return clase(1,0,v);
	}
	
	public int clase(double factor, int k,Vector<Punto> puntos){
		if (v_centros.size() == 0){
			System.out.println("No hay ningun punto con el que comparar.");
			return 0;
		}
		int clase=0;
		Vector<Punto> aux = new Vector<Punto>(v_centros);		
		for (int i=0;i<k;i++){
			clase = this.getClase(puntos.get(i),aux);
			actualizaVector(factor, clase,puntos.get(i),aux);
		}		
		return clase;
	}

	
	private void actualizaVector(double factor,int clase, Punto punto,
			Vector<Punto> vector) {
		Punto p = vector.get(clase);
		Punto paux = new Punto(p.coordenadas);
		for (int i=0;i<p.coordenadas;i++){
			paux.numeros[i] = p.numeros[i]
			   - (factor*(p.numeros[i] - punto.numeros[i])); 
		}
		vector.set(clase,paux);
	}
}
