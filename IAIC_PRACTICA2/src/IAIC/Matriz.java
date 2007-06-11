package IAIC;

public class Matriz {
	
	private int filas;
	
	private int columnas;
	
	private  double matriz [][];
	
	private int iDF = 0;
	
	public boolean error;

	public Matriz(int filas, int columnas,double [] punto,boolean tipo)
	{
		this.filas=filas;
		this.columnas=columnas;
		this. matriz = new double[filas] [columnas];
		this.error=false;
		if (tipo)
		{
		for (int i=0;i<filas;i++)
			for (int j=0;j<columnas;j++)
				this.matriz[i][j]=punto[i];
		}
		else
		{
			for (int i=0;i<filas;i++)
				for (int j=0;j<columnas;j++)
					this.matriz[i][j]=punto[j];
		}
	}
	
	
	public Matriz(int filas, int columnas)
	{
		this.filas=filas;
		this.columnas=columnas;
		this. matriz = new double[filas] [columnas];
		for (int i=0;i<filas;i++)
			for (int j=0;j<columnas;j++)
				this.matriz[i][j]=0;
	}
	
	private void aux()
	{
		int num=0;
		for (int i=0;i<this.filas;i++)
			for (int j=0;j<this.columnas;j++)
			{
				this.matriz[i][j]=num;
				num++;
			}
	}
	
	public Matriz traspuesta()
	{

		int filas=this.columnas;
		int columnas=this.filas;
		Matriz traspuesta = new Matriz(filas,columnas);
		for (int i=0;i<filas;i++)
			for (int j=0;j<columnas;j++)
				traspuesta.matriz[i][j]=this.matriz[j][i];
		
		return traspuesta;
	}
	
	public void suma(Matriz suma2)
	{
		if ((this.columnas==suma2.getColumnas())&&(this.filas==suma2.getFilas()))
		{
			for (int i=0;i<this.filas;i++)
				for (int j=0;j<this.columnas;j++)
				{
					this.matriz[i][j]+=suma2.getMatriz()[i][j];
				}
		}
	}
	
	public Matriz multiplica(Matriz multiplica)
	{
		if (this.columnas==multiplica.getFilas())
		{
			Matriz mult = new Matriz(this.filas,multiplica.columnas);
			for (int i=0;i<mult.filas;i++)
				for (int j=0;j<mult.columnas;j++)
				{
					mult.matriz[i][j]=multiplica_posicion(i,j,multiplica);
				}
			return mult;
		}
		return null;
	}
	
	
	private double multiplica_posicion(int i, int j, Matriz multiplica) {
		double aux=0.0;
		for (int k=0;k<this.columnas;k++)
			aux+=this.matriz[i][k]*multiplica.matriz[k][j];
		
		return(aux);
	}
	
	public Matriz resta(Matriz resta)
	{
		if ((this.columnas==resta.getColumnas())&&(this.filas==resta.getFilas()))
		{
			Matriz diferencia = new Matriz(this.filas,this.columnas);
			for (int i=0;i<this.filas;i++)
				for (int j=0;j<this.columnas;j++)
				{
					diferencia.matriz[i][j]=this.matriz[i][j]-resta.matriz[i][j];
				}
			return diferencia;
		}
		return null;
	}


	public void divide(int divide)
	{
		for (int i=0;i<this.filas;i++)
			for (int j=0;j<this.columnas;j++)
					this.matriz[i][j]/=divide;
	}
	
	public void multiplicanum(int num)
	{
		for (int i=0;i<this.filas;i++)
			for (int j=0;j<this.columnas;j++)
					this.matriz[i][j]*=num;
	}
	

	public int getColumnas() {
		return columnas;
	}


	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}


	public int getFilas() {
		return filas;
	}


	public void setFilas(int filas) {
		this.filas = filas;
	}


	public double[][] getMatriz() {
		return matriz;
	}


	public void setMatriz(double[][] matriz) {
		this.matriz = matriz;
	}
	
	
	public Matriz Inverse() {

		int tms = this.filas;
		Matriz m = new Matriz (this.filas,this.columnas);
		Matriz mm = this.Adjoint();

		double det = this.Determinant();
		double dd = 0;

		if (det == 0) {
			m.error=true;
				System.out.println("Determinant Equals 0, Not Invertible.");

		} else {
			m.error=false;
			dd = 1 / det;
		}

		for (int i = 0; i < this.filas; i++)
			for (int j = 0; j < this.filas; j++) {
				m.matriz[i][j] = dd * mm.matriz[i][j];
			}

		return m;
	}
	
	public Matriz Adjoint() {

		int tms = this.columnas;

		Matriz m = new Matriz (this.filas,this.columnas);

		int ii, jj, ia, ja;
		double det;

		for (int i = 0; i < tms; i++)
			for (int j = 0; j < tms; j++) {
				ia = ja = 0;

				Matriz ap = new Matriz (this.filas-1,this.columnas-1);

				for (ii = 0; ii < tms; ii++) {
					for (jj = 0; jj < tms; jj++) {

						if ((ii != i) && (jj != j)) {
							ap.matriz[ia][ja] = this.matriz[ii][jj];
							ja++;
						}

					}
					if ((ii != i) && (jj != j)) {
						ia++;
					}
					ja = 0;
				}

				det = ap.Determinant();
				m.matriz[i][j] = (float) Math.pow(-1, i + j) * det;
			}

		m.traspuesta();

		return m;
	}
	
	
	public double Determinant() {
		
		int tms = this.columnas;

		double det = 1;

		Matriz matriz = this.UpperTriangle();

		for (int i = 0; i < tms; i++) {
			det = det * matriz.matriz[i][i];
		} // multiply down diagonal

		det = det * iDF; // adjust w/ determinant factor


		return det;
	}



public Matriz UpperTriangle() {

	double f1 = 0;
	double temp = 0;
	int tms = this.columnas; // get This Matrix Size (could be smaller than
						// global)
	int v = 1;

	iDF = 1;

	Matriz m = this.copia();
	for (int col = 0; col < tms - 1; col++) {
		for (int row = col + 1; row < tms; row++) {
			v = 1;

			outahere: while (m.matriz[col][col] == 0) // check if 0 in diagonal
			{ // if so switch until not
				if (col + v >= tms) // check if switched all rows
				{
					iDF = 0;
					break outahere;
				} else {
					for (int c = 0; c < tms; c++) {
						temp = m.matriz[col][c];
						m.matriz[col][c] = m.matriz[col + v][c]; // switch rows
						m.matriz[col + v][c] = temp;
					}
					v++; // count row switchs
					iDF = iDF * -1; // each switch changes determinant
									// factor
				}
			}

			if (m.matriz[col][col] != 0) {

				try {
					f1 = (-1) * m.matriz[row][col] / m.matriz[col][col];
					for (int i = col; i < tms; i++) {
						m.matriz[row][i] = f1 * m.matriz[col][i] + m.matriz[row][i];
					}
				} catch (Exception e) {
					System.out.println("Still Here!!!");
				}

			}

		}
	}

	return m;
}


	public void imprimir() {
	 Matriz nueva = new Matriz(this.filas,this.columnas); 
			for (int i=0;i<filas;i++)
			{
				System.out.println();
				for (int j=0;j<columnas;j++)
					System.out.print(" "+this.matriz[i][j]);
			}
	}
	
	
	 private Matriz copia() {
	 Matriz nueva = new Matriz(this.filas,this.columnas); 
			for (int i=0;i<filas;i++)
				for (int j=0;j<columnas;j++)
					nueva.matriz[i][j]=this.matriz[i][j];
	return(nueva);
}


	public static void main(String[] arg)
	 {
		 Matriz uno = new Matriz(3,3);
		 uno.aux();
		 uno.matriz [0][0]=2;
		 uno.matriz [0][1]=-2;
		 uno.matriz [0][2]=1;
		 uno.matriz [1][0]=-1;
		 uno.matriz [1][1]=1;
		 uno.matriz [1][2]=1;
		 uno.matriz [2][0]=-1;
		 uno.matriz [2][1]=3;
		 uno.matriz [2][2]=5;
		 Matriz inversa = new Matriz(3,3);
		 System.out.println(uno.Determinant());
		 try {
			inversa=uno.Inverse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//inversa.multiplicanum(6);
		inversa=inversa.traspuesta();
		Matriz mult=uno.multiplica(inversa);
		 Matriz dos = uno.traspuesta();
	 }
}
