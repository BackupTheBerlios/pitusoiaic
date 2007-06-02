package IAIC;

import java.util.Vector;

public class Cuantizacion_vectorial {

	int clases;
	Vector<Clase> centros;
	double umbral;
	
	public Cuantizacion_vectorial(double umbral)
	{
		clases=0;
		centros= new Vector<Clase>();
		this.umbral=umbral;
	}
	
	public void calcula(Vector<Punto> puntos)
	{
		clases=1;
		Clase nueva = new Clase();
		centros.addElement(nueva);
		
		centros.elementAt(0).centro=puntos.elementAt(0).copia();
		centros.elementAt(0).muestras.add(puntos.elementAt(0));
		int clase=0;
		for (int i=1;i<puntos.size();i++)
		{
			clase=calculadistancias(i,puntos);
			if (clase>clases-1)
			{
				clases++;
				nueva = new Clase();
				centros.addElement(nueva);
				centros.elementAt(clase).centro=puntos.elementAt(i).copia();
				centros.elementAt(clase).muestras.add(puntos.elementAt(i));

			}
			else
			{
				centros.elementAt(clase).muestras.add(puntos.elementAt(i));
				centros.elementAt(clase).nuevoCentro();
			}
		}
	}
	
	public int calculadistancias(int indice,Vector<Punto> puntos)
	{
		double distancia=centros.elementAt(0).centro.distancia(puntos.elementAt(indice));
		double aux=0.0;
		int clase=0;
		for (int j=0;j<this.clases;j++)
		{
			aux=centros.elementAt(j).centro.distancia(puntos.elementAt(indice));
			if (aux<=distancia)
			{
				clase=j;
				distancia=aux;
			}
		}
		if (distancia>umbral) clase=clases; 
		return(clase);
	}
	
	 public static void main(String[] arg)
	 {
	 
		 double entrada1 []={200,160,120};
		 double entrada2 []={90,130,60};
		 double entrada3 []={210,170,130};
		 double entrada4 []={35,25,46};
		 double entrada5 []={215,172,133};
		 double entrada6 []={92,138,54};
		 double entrada7 []={87,128,66};
		 double entrada8 []={41,22,37};
		 Punto uno = new Punto(3,entrada1);
		 Punto dos = new Punto(3,entrada2);
		 Punto tres = new Punto(3,entrada3);
		 Punto cuatro = new Punto(3,entrada4);
		 Punto cinco = new Punto(3,entrada5);
		 Punto seis = new Punto(3,entrada6);
		 Punto siete = new Punto(3,entrada7);
		 Punto ocho = new Punto(3,entrada8);
		 Vector<Punto> aux= new Vector<Punto> ();
		 aux.add(uno);
		 aux.add(dos);
		 aux.add(tres);
		 aux.add(cuatro);
		 aux.add(cinco);
		 aux.add(seis);
		 aux.add(siete);
		 aux.add(ocho);
		 Cuantizacion_vectorial algoritmo= new Cuantizacion_vectorial(20);
		 algoritmo.calcula(aux);
		 System.out.println(algoritmo.toString());
		 

	 }

	public String toString()
	{
		String nueva= new String("\n");
		for (int i=0;i<this.clases;i++)
		{
			nueva+="CLASE "+(i+1)+" : REPRENSENTANTE "+this.centros.get(i).centro.toString()+"\n";
		}
		return nueva;
	}
	 
	public Vector<Clase> getCentros() {
		return centros;
	}

	public void setCentros(Vector<Clase> centros) {
		this.centros = centros;
	}

	public int getClases() {
		return clases;
	}

	public void setClases(int clases) {
		this.clases = clases;
	}
}
