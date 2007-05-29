package IAIC;


import java.util.LinkedList;
import java.util.Vector;

public class Estimacion_parametrica implements Algoritmos{

	LinkedList <ConjuntoM_C> vector_aprendido;
	
	
	public Estimacion_parametrica()
	{
		this.vector_aprendido= new LinkedList <ConjuntoM_C> () ;
	}
	
	public void Aprendizaje(int clases,Vector<Clase> centros)
	{
		for (int i=0;i<clases;i++)
		{
			
			this.vector_aprendido.add(new ConjuntoM_C());
			this.vector_aprendido.get(i).setM(calcular_media(i,centros));
			this.vector_aprendido.get(i).setC(calcular_covarianza(i,centros));
			this.vector_aprendido.get(i).setClase(i);
		}
	}

	private Matriz calcular_covarianza(int i, Vector<Clase> centros) {
		
		int tamaño=centros.get(i).centro.coordenadas;
		Matriz suma= new Matriz(tamaño,tamaño);
		Matriz resta1= new Matriz(tamaño,tamaño);
		Matriz resta2= new Matriz(tamaño,tamaño);
		Punto aux;
		Matriz nueva;
		for (int j=0;j<centros.get(i).muestras.size();j++)
		{			
			/* Obtengo la matriz de restarle a cada coordenada de los puntos
			 * contenidos en cada una de las restas la media previamente calculada.
			 * Esa matriz la traspongo y la multiplico por ella misma.
			 * Calculo la media de hacerlo con todos los puntos.
			 */ 
			aux=centros.get(i).muestras.get(j);
			nueva=new Matriz(tamaño,1,aux.numeros,true);
			resta1=nueva.resta(this.vector_aprendido.get(i).m);
			resta2=resta1.traspuesta();
			suma.suma(resta1.multiplica(resta2));
		}
		suma.divide(centros.get(i).muestras.size());
		return suma;
	}
	

	private Matriz calcular_media(int i, Vector<Clase> centros) {
		
		int tamaño=centros.get(i).centro.coordenadas;
		Matriz media = new Matriz(tamaño,1);
		Punto aux;
		for (int j=0;j<centros.get(i).muestras.size();j++)
		{
			aux=centros.get(i).muestras.get(j);
			media.suma(new Matriz(tamaño,1,aux.numeros,true));
		}
		media.divide(centros.get(i).muestras.size());
		return(media);
	}
	
	public int clase (Punto punto)
	{
		return(this.maxima_verosimilitud(punto));
	}
	
	public int maxima_verosimilitud(Punto punto)
	{
		int clase=0;
		double aux=0;
		double valor_maximo=0;
		
		for(int i=0;i<this.vector_aprendido.size();i++)
		{
			if (i==0)
			{
				valor_maximo=funcion_verosimilitud(i,punto);
			}
			else
			{
				aux=funcion_verosimilitud(i,punto);
				if (aux>valor_maximo)
				{
					valor_maximo=aux;
					clase=i;
				}
			}
		}
		
		System.out.println("CLASE "+clase);
		return(clase);
		
	}
	
	
	 private double funcion_verosimilitud(int i, Punto punto) {
		double resultado =0;
		double exponente=calcula_exponente(i,punto);
		resultado=Math.exp(exponente);
		return resultado;
	}

	private double calcula_exponente(int i,Punto punto) {
		double resultado=-0.5;
		Matriz aux =new Matriz(punto.coordenadas,1,punto.numeros,true);
		Matriz resta=aux.resta(this.vector_aprendido.get(i).m);
		aux=resta.traspuesta();
		Matriz inversa =this.vector_aprendido.get(i).c.Inverse();
		aux=aux.multiplica(inversa);
		aux=aux.multiplica(resta);
		return resultado*aux.getMatriz()[0][0];
	}

	public static void main(String[] arg)
	 {
	 
		 /*double entrada1 []={200,160,120};
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
		 Estimacion_parametrica prueba = new Estimacion_parametrica();
		 prueba.Aprendizaje(algoritmo.clases,algoritmo.centros);
		 System.out.println(uno.distancia(dos));
		 System.out.println("RESULTADO "+prueba.maxima_verosimilitud(uno));*/
		 double entrada2 []={1,0,0};
		 double entrada3 []={0,0,0};
		 double entrada4 []={1,1,0};
		 double entrada5 []={1,0,1};
		 double entrada6 []={0,0,1};
		 double entrada7 []={0,1,1};
		 double entrada8 []={0,1,0};
		 double entrada1 []={1,1,1};
		 Punto uno = new Punto(3,entrada1);
		 Punto dos = new Punto(3,entrada2);
		 Punto tres = new Punto(3,entrada3);
		 Punto cuatro = new Punto(3,entrada4);
		 Punto cinco = new Punto(3,entrada5);
		 Punto seis = new Punto(3,entrada6);
		 Punto siete = new Punto(3,entrada7);
		 Punto ocho = new Punto(3,entrada8);
		Vector<Clase> centros = new Vector<Clase>();
		Clase c1 = new Clase();
		c1.centro=dos;
		c1.muestras.add(dos);
		c1.muestras.add(tres);
		c1.muestras.add(cuatro);
		c1.muestras.add(cinco);
		Clase c2 = new Clase();
		c2.centro=seis;
		c2.muestras.add(seis);
		c2.muestras.add(siete);
		c2.muestras.add(ocho);
		c2.muestras.add(uno);
		centros.add(c1);
		centros.add(c2);
		Estimacion_parametrica prueba = new Estimacion_parametrica();
		prueba.Aprendizaje(2, centros);
		System.out.println(uno.distancia(dos));
		System.out.println("RESULTADO "+prueba.clase(uno));
		 

	 }
}
