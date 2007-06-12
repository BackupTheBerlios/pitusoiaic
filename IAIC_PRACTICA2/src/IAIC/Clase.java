package IAIC;

import java.util.Vector;

public class Clase {

	public Vector<Punto> muestras;
	public Punto centro;
	
	public Clase()
	{
		muestras=new Vector<Punto> ();
		centro=null;
	}

	public Punto getCentro() {
		return centro;
	}

	public void setCentro(Punto centro) {
		this.centro = centro;
	}

	public Vector<Punto> getMuestras() {
		return muestras;
	}

	public void setMuestras(Vector<Punto> muestras) {
		this.muestras = muestras;
	}
	
	public void nuevoCentro()
	{
		double [] sumas = new double[centro.coordenadas];
		for (int j=0;j<centro.coordenadas;j++)
		{
			sumas[j]=0.0;
		}
		for (int i=0;i<muestras.size();i++)
		{
			for (int j=0;j<centro.coordenadas;j++)
			{
				sumas[j]+=muestras.elementAt(i).numeros[j];
			}
		}
		for (int j=0;j<centro.coordenadas;j++)
		{
			sumas[j]/=muestras.size();
		}
		this.centro.numeros=sumas;
	}

}
