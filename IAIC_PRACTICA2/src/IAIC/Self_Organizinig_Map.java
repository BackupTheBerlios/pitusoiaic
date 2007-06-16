package IAIC;

import java.util.Vector;

public class Self_Organizinig_Map implements Algoritmos {

	double alpha_inicial;
	
	double alpha_final;
	
	int kmax;
	
	double T;
	
	Vector<Punto> centros;
	
	public Self_Organizinig_Map(double inicial, double _final,int kmax,double T)
	{
		this.alpha_final=_final;
		this.alpha_inicial=inicial;
		this.kmax=kmax;
		this.T=T;
		this.centros=new Vector<Punto> ();
	}
	
	public void Aprendizaje(int clases, Vector<Clase> centros) {
		
	Vector<Punto> muestras = new Vector<Punto> ();
	inicializa(muestras,centros);
	double razonaprendidaje=0.0;
	double alpha=0;
	double aux;
	for (int i=1;i<=kmax;i++)
	{
		aux=10+i;
		razonaprendidaje=1/aux;
		alpha=calcula_alpha(i);
		calcula_centros(i,muestras,razonaprendidaje,alpha);
	}
	
	}

	private void calcula_centros(int i, Vector<Punto> muestras, double razonaprendidaje, double alpha) {
		
		double vecindad=0.0;
		
		for (int j=0;j<muestras.size();j++)
		{
			for (int k=0;k<this.centros.size();k++)
			{
				vecindad=calcula_vecindad(muestras.get(j),centros.get(k),alpha);
				if (vecindad>this.T)
				{
					Punto resta=muestras.get(j).resta(centros.get(k));
					resta.multiplica(vecindad*razonaprendidaje);
					resta=resta.suma(centros.get(k));
					centros.set(k, resta);
				}
			}
		}

		
	}

	private double calcula_vecindad(Punto punto, Punto punto2, double alpha) {
		double distancia =punto.distancia(punto2);
		distancia*=distancia;
		double alpha_aux=alpha*alpha*2;
		double division=distancia/alpha_aux;
		double e=Math.exp(-division);
		return e;
	}

	private double calcula_alpha(int i) {
		double aux=this.alpha_final/this.alpha_inicial;
		double i_double=i;
		aux=Math.pow(aux,(i_double/(double)this.kmax));
		aux*=this.alpha_inicial;
		return aux;
	}

	private void inicializa(Vector<Punto> muestras, Vector<Clase> centros2) {
		for (int i=0;i<centros2.size();i++)
		{
			this.centros.add(centros2.get(i).getCentro());
			for (int j=0;j<centros2.get(i).getMuestras().size();j++)
			{
				muestras.add(centros2.get(i).getMuestras().get(j));
			}
		}
	}

	public int clase(Punto punto) {
		int clase=0;
		double distancia_min=0;
		double aux=0;
		for (int i=0;i<centros.size();i++)
		{
			if (i==0)
			{
				distancia_min=centros.get(i).distancia(punto);
			}
			else
			{
				aux=centros.get(i).distancia(punto);
				if (aux<distancia_min) 
					{
					distancia_min=aux;
					clase=i;
					}
			}
		}
		return clase;
	}
	
	public static void main(String[] arg)
	 {
		 double centro1 []={1,4};
		 double centro2 []={7,2};
		 double entrada2 []={1,3};
		 double entrada3 []={1,6};
		 double entrada4 []={2,2};
		 double entrada5 []={6,3};
		 double entrada6 []={6,4};
		 double entrada7 []={7,3};
		 Punto centro11 = new Punto(2,centro1);
		 Punto centro12 = new Punto(2,centro2);
		 Punto dos = new Punto(2,entrada2);
		 Punto tres = new Punto(2,entrada3);
		 Punto cuatro = new Punto(2,entrada4);
		 Punto cinco = new Punto(2,entrada5);
		 Punto seis = new Punto(2,entrada6);
		 Punto siete = new Punto(2,entrada7);
		 double nuevo  []={6,2};
		 Punto nuevo1 = new Punto(2,nuevo);
		 Vector<Clase> centros = new Vector<Clase>();
		 Clase c1 = new Clase();
		 c1.setCentro(centro11);
		 c1.getMuestras().add(dos);
		 c1.getMuestras().add(tres);
		 c1.getMuestras().add(cuatro);
		 c1.getMuestras().add(cinco);
		 Clase c2 = new Clase();
		 c2.setCentro(centro12);
		 c2.getMuestras().add(seis);
		 c2.getMuestras().add(siete);
		 centros.add(c1);
		 centros.add(c2);
		 Self_Organizinig_Map prueba = new Self_Organizinig_Map(1.0, 0.8, 5, 0.001);
		 prueba.Aprendizaje(2, centros);
		 System.out.println("Centros : c1 "+prueba.centros.get(0).toString()+" c2"+prueba.centros.get(1).toString());
		 System.out.println("Clase de  "+nuevo1.toString()+" "+prueba.clase(nuevo1));
		 System.out.println("Clase de  "+dos.toString()+" "+prueba.clase(dos));
		 System.out.println("Clase de  "+tres.toString()+" "+prueba.clase(tres));
		 System.out.println("Clase de  "+cuatro.toString()+" "+prueba.clase(cuatro));
		 System.out.println("Clase de  "+cinco.toString()+" "+prueba.clase(cinco));
		 System.out.println("Clase de  "+seis.toString()+" "+prueba.clase(seis));
		 System.out.println("Clase de  "+siete.toString()+" "+prueba.clase(siete));
	 
	 }

}