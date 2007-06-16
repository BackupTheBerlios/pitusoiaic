package IAIC;

import java.util.LinkedList;
import java.util.Vector;

public class Estimacion_no_parametrica implements Algoritmos{
	
	
	private LinkedList <ConjuntoM_C> vector_aprendido;
	private Vector<Clase> centros;
	
	private boolean error;
	
	
	public Estimacion_no_parametrica()
	{
		error=false;
		this.vector_aprendido= new LinkedList <ConjuntoM_C> () ;
	}
	
	public void Aprendizaje(int clases,Vector<Clase> centros)
	{
		this.centros = centros;
		for (int i=0;i<clases;i++)
		{
			this.vector_aprendido.add(new ConjuntoM_C());
			this.vector_aprendido.get(i).setM(calcular_media(i,centros));
			this.vector_aprendido.get(i).setC(calcular_covarianza(i,centros));
			this.vector_aprendido.get(i).setClase(i);
		}
	}
	
	private Matriz calcular_covarianza(int i, Vector<Clase> centros) {
		
		int tamaño=centros.get(i).getCentro().getCoordenadas();
		Matriz suma= new Matriz(tamaño,tamaño);
		Matriz resta1= new Matriz(tamaño,tamaño);
		Matriz resta2= new Matriz(tamaño,tamaño);
		Punto aux;
		Matriz nueva;
		for (int j=0;j<centros.get(i).getMuestras().size();j++)
		{			
			/* Obtengo la matriz de restarle a cada coordenada de los puntos
			 * contenidos en cada una de las restas la media previamente calculada.
			 * Esa matriz la traspongo y la multiplico por ella misma.
			 * Calculo la media de hacerlo con todos los puntos.
			 */ 
			aux=centros.get(i).getMuestras().get(j);
			nueva=new Matriz(tamaño,1,aux.getNumeros(),true);
			resta1=nueva.resta(this.vector_aprendido.get(i).getM());
			resta2=resta1.traspuesta();
			suma.suma(resta1.multiplica(resta2));
		}
		suma.divide(centros.get(i).getMuestras().size());
		return suma;
	}
	
	
	private Matriz calcular_media(int i, Vector<Clase> centros) {
		
		int tamaño=centros.get(i).getCentro().getCoordenadas();
		Matriz media = new Matriz(tamaño,1);
		Punto aux;
		for (int j=0;j<centros.get(i).getMuestras().size();j++)
		{
			aux=centros.get(i).getMuestras().get(j);
			media.suma(new Matriz(tamaño,1,aux.getNumeros(),true));
		}
		media.divide(centros.get(i).getMuestras().size());
		return(media);
	}
	
	public int clase (Punto punto) throws Exception{
		throw new Exception("Estimación no paramétrica ->Método no valido.");
	}
	
	public double funcion_verosimilitud (Punto punto, double lado_hiper,
			int dimension_hiper, int numero_clase){
		double prob = 0;
		double factor = calculaFactor(lado_hiper,dimension_hiper,numero_clase);
		double acumulado = 0; 
		if (numero_clase >= centros.size()) return 0;
		Vector<Punto> muestras = centros.get(numero_clase).getMuestras();
		for (int num_muestra=0; num_muestra<muestras.size();num_muestra++){
			acumulado += Math.exp(calculaExponente(numero_clase,num_muestra,
					lado_hiper,punto));
		}		
		prob = factor*acumulado/muestras.size();
		return prob;
		
	}
	
	
	
	private double calculaExponente(int numero_clase, int num_muestra, double lado_hiper, Punto punto) {
		double resultado=-0.5;
		Punto punto_muestra = centros.get(numero_clase).getMuestras().get(num_muestra);
		Matriz matriz_muestra = new Matriz (punto_muestra.getCoordenadas(),
				1,punto_muestra.getNumeros(),true);
		Matriz covarianza = vector_aprendido.get(numero_clase).getC();
		
		Matriz aux =new Matriz(punto.getCoordenadas(),1,
				punto.getNumeros(),true);		
		Matriz resta=aux.resta(matriz_muestra);
		aux=resta.traspuesta();
		Matriz inversa =covarianza.Inverse();
		if (inversa.getError()) error= true;
		
		aux=aux.multiplica(inversa);
		aux=aux.multiplica(resta);
		
		resultado = resultado * aux.getMatriz()[0][0];
		return resultado;
	}
	
	private double calculaFactor(double lado_hiper, int dimension_hiper, int numero_clase) {
		Matriz covarianza = vector_aprendido.get(numero_clase).getC();
		double factor = 0;
		double f1 = 1/Math.pow(2*Math.PI,dimension_hiper/2.0);
		double f2 = 1/(Math.pow(lado_hiper,dimension_hiper)*
				Math.pow(covarianza.Determinant(),2.0));
		factor = f1 * f2;
		return factor;
	}
	
	
	public static void main(String[] arg)
	{
		double entrada1 []={1,1,1};
		double entrada2 []={1,0,0};
		double entrada3 []={0,0,0};
		double entrada4 []={1,1,0};
		double entrada5 []={1,0,1};
		double entrada6 []={0,0,1};
		double entrada7 []={0,1,1};
		double entrada8 []={0,1,0};		
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
		c1.setCentro(dos);
		c1.getMuestras().add(dos);
		c1.getMuestras().add(tres);
		c1.getMuestras().add(cinco);
		c1.getMuestras().add(siete);
		Clase c2 = new Clase();
		c2.setCentro(cuatro);
		c2.getMuestras().add(seis);
		c2.getMuestras().add(siete);
		c2.getMuestras().add(ocho);
		c2.getMuestras().add(uno);
		centros.add(c1);
		centros.add(c2);
		Estimacion_no_parametrica prueba = new Estimacion_no_parametrica();
		prueba.Aprendizaje(2,centros);
		System.out.println("Probabilidad de  "+dos.toString()+" en la clase 0: "+ prueba.funcion_verosimilitud(dos,0.5,3,0));
		System.out.println("Probabilidad de  "+dos.toString()+" en la clase 1: "+ prueba.funcion_verosimilitud(dos,0.5,3,1));
		System.out.println("Probabilidad de  "+tres.toString()+" en la clase 0: "+ prueba.funcion_verosimilitud(tres,0.5,3,0));
		System.out.println("Probabilidad de  "+tres.toString()+" en la clase 1: "+ prueba.funcion_verosimilitud(tres,0.5,3,1));
		System.out.println("Probabilidad de  "+cuatro.toString()+" en la clase 0: "+ prueba.funcion_verosimilitud(cuatro,0.5,3,0));
		System.out.println("Probabilidad de  "+cuatro.toString()+" en la clase 1: "+ prueba.funcion_verosimilitud(cuatro,0.5,3,1));
		System.out.println("Probabilidad de  "+seis.toString()+" en la clase 0: "+ prueba.funcion_verosimilitud(seis,0.5,3,0));
		System.out.println("Probabilidad de  "+seis.toString()+" en la clase 1: "+ prueba.funcion_verosimilitud(seis,0.5,3,1));
	}
	
	public LinkedList<ConjuntoM_C> getVector_aprendido() {
		return vector_aprendido;
	}
	
	public void setVector_aprendido(LinkedList<ConjuntoM_C> vector_aprendido) {
		this.vector_aprendido = vector_aprendido;
	}
	
	public boolean getError(){
		return error;
	}
}
