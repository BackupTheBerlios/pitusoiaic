package IAIC;

public class ConjuntoM_C {
	
	Matriz c;
	
	Matriz m;
	
	int clase;

	public ConjuntoM_C()
	{
		this.c=null;
		this.m=null;
		clase=-1;
	}

	public Matriz getC() {
		return c;
	}

	public void setC(Matriz c) {
		this.c = c;
	}

	public int getClase() {
		return clase;
	}

	public void setClase(int clase) {
		this.clase = clase;
	}

	public Matriz getM() {
		return m;
	}

	public void setM(Matriz matriz) {
		this.m = matriz;
	}
	
}
