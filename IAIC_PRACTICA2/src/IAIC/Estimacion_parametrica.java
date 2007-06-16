package IAIC;


import java.util.LinkedList;
import java.util.Vector;

public class Estimacion_parametrica implements Algoritmos{
	
	private LinkedList <ConjuntoM_C> vector_aprendido;
	
	private boolean error;
	
	
	public Estimacion_parametrica()
	{
		error=false;
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
	
	public int clase (Punto punto)
	{
		error=false;
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
		Matriz aux =new Matriz(punto.getCoordenadas(),1
				,punto.getNumeros(),true);
		Matriz resta=aux.resta(this.vector_aprendido.get(i).getM());
		aux=resta.traspuesta();
		Matriz inversa =this.vector_aprendido.get(i).getC().Inverse();
		if (inversa.getError()) error= true;
		aux=aux.multiplica(inversa);
		aux=aux.multiplica(resta);
		return resultado*aux.getMatriz()[0][0];
	}
	
	public static void main(String[] arg)
	{
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
		centros.add(c1);
		centros.add(c2);
		Estimacion_parametrica prueba = new Estimacion_parametrica();
		prueba.Aprendizaje(2,centros);
		System.out.println("Clase de  "+dos.toString()+" "+prueba.clase(dos));
		System.out.println("Clase de  "+tres.toString()+" "+prueba.clase(tres));
		System.out.println("Clase de  "+cuatro.toString()+" "+prueba.clase(cuatro));
		System.out.println("Clase de  "+cinco.toString()+" "+prueba.clase(cinco));
		System.out.println("Clase de  "+seis.toString()+" "+prueba.clase(seis));
		System.out.println("Clase de  "+siete.toString()+" "+prueba.clase(siete));
		System.out.println("Clase de  "+ocho.toString()+" "+prueba.clase(ocho));
		System.out.println("Clase de  "+uno.toString()+" "+prueba.clase(uno));
		
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
