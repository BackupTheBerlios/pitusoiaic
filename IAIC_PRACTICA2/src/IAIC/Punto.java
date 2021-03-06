package IAIC;

public class Punto {

	private int coordenadas;
	private double []numeros;
	
	public Punto(int coordenadas,double[] lista)
	{
		this.coordenadas=coordenadas;
		this.numeros=lista;
	}
	
	public Punto(int coordenadas){
		this.coordenadas = coordenadas;
		this.numeros = new double[coordenadas];
		for (int i=0;i<coordenadas;i++){
			numeros[i] = 0;
		}
	}
	


	public int getCoordenadas() {
		return coordenadas;
	}


	public void setCoordenadas(int coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	public Punto suma(Punto punto)
	{
		double suma[] = new double [this.coordenadas];
		for(int i=0;i<this.coordenadas;i++)
			suma[i]=0;
		
		Punto aux = new Punto(this.coordenadas,suma);
		
		for(int i=0;i<this.coordenadas;i++)
		{
			aux.numeros[i]=this.numeros[i]+punto.numeros[i];
		}
		return(aux);
	}
	
	public Punto resta(Punto punto)
	{
		double suma[] = new double [this.coordenadas];
		for(int i=0;i<this.coordenadas;i++)
			suma[i]=0;
		
		Punto aux = new Punto(this.coordenadas,suma);
		
		for(int i=0;i<this.coordenadas;i++)
		{
			aux.numeros[i]=this.numeros[i]-punto.numeros[i];
		}
		return(aux);
	}
	
	public void multiplica (double numero)
	{
		for(int i=0;i<this.coordenadas;i++)
		{
			this.numeros[i]*=numero;
		}
	}
	
	
	public void divide(int numero)
	{
		for(int i=0;i<this.coordenadas;i++)
		{
			this.numeros[i]/=numero;
		}
	}
	
	
	public double distancia(Punto punto)
	{
		double aux=0.0;
		double distancia=0.0;
		for(int i=0;i<this.coordenadas;i++)
		{
			aux=this.numeros[i]-punto.getNumeros()[i];
			aux*=aux;
			distancia+=aux;
		}
		return(Math.sqrt(distancia));
	}
	
	 public static void main(String[] arg)
	 {
	 
		 double entrada1 []={200,160,120};
		 double entrada2 []={90,130,60};
		 Punto uno = new Punto(3,entrada1);
		 Punto dos = new Punto(3,entrada2);
		 System.out.println(uno.distancia(dos));
		 System.out.println(uno.stringVentana());
		 

	 }

	public String toString()
	{
		String nueva = new String("(");
		for (int i=0;i<this.coordenadas;i++)
		{
			if (i+1!=this.coordenadas) nueva+=this.numeros[i]+",";
			else nueva+=this.numeros[i]+")";
		}
		return(nueva);
	}
	 

	public double[] getNumeros() {
		return numeros;
	}


	public void setNumeros(double[] numeros) {
		this.numeros = numeros;
	}
	
	public Punto copia()
	{
		double[] nuevo = new double[this.coordenadas];
		for(int i=0;i<this.coordenadas;i++)
			nuevo[i]=this.numeros[i];
		Punto aux = new Punto(this.coordenadas,nuevo);
		return(aux);
	}

	public String stringVentana() {
		String aux = new String("(0");
		for (int i=1;i<this.coordenadas;i++)
			aux+=",0";
		aux+=")";
		return aux;
	}
}
