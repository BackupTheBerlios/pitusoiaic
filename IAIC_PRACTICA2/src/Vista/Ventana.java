package Vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import IAIC.Clase;
import IAIC.Cuantizacion_vectorial;
import IAIC.Estimacion_no_parametrica;
import IAIC.Estimacion_parametrica;
import IAIC.Lector;
import IAIC.Lloyd;
import IAIC.Punto;
import IAIC.Self_Organizinig_Map;

/**
 * Clase Ventana que es la aplicaion. Desde aqui se puede cargar el archivo
 * y resolver el laberinto de las habitaciones
 */
public class Ventana extends JFrame{
	/**
	 * Atributo que es panel Oeste
	 */
	private JPanel _panelOeste;
	/**
	 * Atributo que es el panel Central
	 */
	private JPanel _panelCentral;
	/**
	 * Atributo que es panel este.
	 */
	private JPanel _panelEste;
	/**
	 * Atributo que es panel norte.
	 */
	private JPanel _panelNorte;
	/**
	 * Atributo que es panel sur.
	 */
	private JPanel _panelSur;
	/**
	 * Atributo que es un campo de texto por donde se muestra la informacion
	 */
	private JTextArea texto;
		
	private Vector<Clase> clases;

	private Cuantizacion_vectorial algoritmo;
	
	private static final long serialVersionUID = 1L;
	
	private int situacion; //0 no se ha cargado archivo //1 se ha cargado archivo se debe de aprender //2 se puede preguntar

	private Estimacion_parametrica parametrica;
	
	private Estimacion_no_parametrica no_parametrica;
	
	private Lloyd lloyd;
	
	private Self_Organizinig_Map sof;
	
	private Punto preguntado;
	
	private String respuesta;
	
	private int T;
	
	private Vector<Punto> puntos;
	
	/**
	 * Construvtor de la clase Ventana,
	 * Aqui se crea la ventana de la aplicacion y se arranca desde aqui
	 */
	public Ventana()
	{
		super("Clasificador de puntos");
		this.parametrica=new Estimacion_parametrica();
		this.no_parametrica=new Estimacion_no_parametrica();
		this.lloyd=new Lloyd();
		this.sof=new  Self_Organizinig_Map(1.0, 0.8, 5, 0.001);
		setLocation(100,100);
		setSize(200,100);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		establecerCierre();
		JMenuBar barraMenu = new JMenuBar();
		configurarMenu (barraMenu);//Se configuran los menus
		setSize(800,600);
		_panelCentral=new JPanel();
		_panelNorte=new JPanel();
		_panelEste=new JPanel();
		_panelSur=new JPanel();
		_panelOeste=new JPanel();
		add(_panelCentral, BorderLayout.CENTER);
		add(_panelNorte, BorderLayout.NORTH);
		add(_panelSur, BorderLayout.SOUTH);
		add(_panelEste, BorderLayout.EAST);
		add(_panelOeste, BorderLayout.WEST);
		configurarPanelNorte();
		setJMenuBar(barraMenu);
		situacion=0;
		this.respuesta= new String();
		preguntado=null;
		construirVista(0);
		T=20;
		this.setVisible(true);
	}
	/**
	 * Metodo que configura el panel del norte
	 */
	public void configurarPanelNorte() {
		Font arial = new Font("Arial",Font.PLAIN, 24);
		JLabel titulo=new JLabel("Clasificador de Puntos", JLabel.CENTER);
		titulo.setFont(arial);		
		_panelNorte.add(titulo);
	}
	/**
	 * Metodo que indica lo que se debe de hacer cuando se cierra la ventana
	 *
	 */
	public void establecerCierre() {
		addWindowListener( //Creamos un oyente de ventana
				new WindowAdapter () { //Creamos una clase interna anónima
					public void windowClosing (WindowEvent evento) { //Reescribimos el metodo de cerrar la ventana
						System.exit(0);}
				}
		);
	}
	
	/**
	 * Metodo que configura el menu principal
	 * @param barraMenu de la clase JMenUBar al cual se añaden los menus
	 */
	public void configurarMenu (JMenuBar barraMenu) {
		JMenu menuArchivo = new JMenu("Archivo");
		configurarMenuArchivo(menuArchivo);
		barraMenu.add(menuArchivo);
		barraMenu.setVisible(true);
	}
	/**
	 * Metodo que configura el menu de cargar
	 * @param menuArchivo elemento de la clase JMenu que se va a modificar
	 */
	public void configurarMenuArchivo(JMenu menuArchivo) {
		menuArchivo.setToolTipText("Menú Archivo: Cargar,Configurar T ");
		menuArchivo.setMnemonic('A');
		JMenuItem opcionCargar = new JMenuItem ("Cargar Puntos", 'C'); 
		menuArchivo.add(opcionCargar);
		JMenuItem opcionT = new JMenuItem ("Configurar T", 'C'); 
		menuArchivo.add(opcionT);
		establecerOyenteCargar(opcionCargar);
		establecerOyenteOpcionT(opcionT);
	}

	private void establecerOyenteOpcionT(JMenuItem opcionT) {
		opcionT.setToolTipText("Modificar el valor de T");
		opcionT.addActionListener(
				new ActionListener () {
					public void actionPerformed (ActionEvent evento) {
						ventanaT();	
					}
				}
		);
	}
	private void ventanaT() {
			JPanel panel = new JPanel (new GridLayout(1,1,10,1));
			JTextField punto = new JTextField (this.T);
			JLabel etiqpunto = new JLabel (" Punto:",SwingConstants.CENTER);
			panel.add(etiqpunto);
			panel.add(punto);
			validarPeticion (panel,punto);
			construirVista(this.situacion);
		}
		
		//private void validarPeticion (JPanel panel, JRadioButton tabRegular, JRadioButton tabIrregular, JTextField campoFilas, JTextField campoColumnas) {
		private void validarPeticion (JPanel panel,JTextField punto) {
			boolean valido=false;
			Object[] opciones = {"Aceptar", "Cancelar"};
			int opcion=0;
			while (!valido) {
				try {
					opcion=JOptionPane.showOptionDialog(this,panel,"Datos del valor de T (un entero)",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,null,opciones,opciones[0]);
					if (opcion==0) {
						this.T=Integer.parseInt(punto.getText());
							valido=this.T>0;
						if (!valido) JOptionPane.showMessageDialog(this,"El valor de T no puede ser negativo ni igual a cero","Error en los datos",JOptionPane.ERROR_MESSAGE,null);
					}
					else valido=true;
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(this,"El valor de T no puede ser negativo ni igual a cero","Error en los datos",JOptionPane.ERROR_MESSAGE,null);
				} 
			}
		}
		
	/**
	 * Metodo que establece la estrategia
	 * @param numero un entero que indica la estrategia
	 * @param opcion un booleano que indica si es general o no lo es
	 */
	/**
	 * Metodo para establecer el oyente del boton cuando se desea cargar un juego.
	 * @param opcionCargar es el boton que se configura como oyente.
	 */
	public void establecerOyenteCargar(AbstractButton opcionCargar) {
		opcionCargar.setToolTipText("Cargar una serie de puntos");
		opcionCargar.setIcon(new ImageIcon("IconoCargar.gif"));
		opcionCargar.addActionListener(
				new ActionListener () {
					public void actionPerformed (ActionEvent evento) {
						File archivo =pedir();
						if (archivo!=null)
						{
						if (cargar(archivo))
							{
							try {
								comienzo();
								construirVista(1);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
						}
						
					}
				}
		);
	}

	private void comienzo()  {
		 this.algoritmo= new Cuantizacion_vectorial(this.T);
		 algoritmo.calcula(this.puntos);
		 clases=algoritmo.getCentros();
		 this.situacion=1;
	}
	
	/**
	 * Metodo usado para crgar el archivo
	 * @param archivoS de la clase File que es el archivo que deseamos cargar
	 * @return un booleano indicando si se ha hecho correctamente
	 */
	private boolean cargar (File archivoS)
	{
		try {
		this.puntos= Lector.leer(archivoS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return(false);
		}
		this.situacion=0;
		return(true);
	}
	
	/**
	 * Metodo que pide el archivo que deseamos cargar
	 * @return un File que es el archivo que hay que cargar
	 */
	private File pedir ()
	{
		JFileChooser selector = new JFileChooser();
	    FiltroArchivos filter = new FiltroArchivos();
	    selector.setFileFilter(filter);
	    int opcion = selector.showOpenDialog(this);
	    if(opcion == JFileChooser.APPROVE_OPTION) return selector.getSelectedFile();
	    return null;
	}

	/**
	 * Metodo que construye la vista
	 * @param i un booleano que indica la informacion que debe presentar
	 */
	public void construirVista(final int i) {
		_panelSur.removeAll();
		System.out.println(this.situacion+" SIT");
		_panelCentral.removeAll();
		JButton boton= null;
		if (i==1 ) boton = new JButton("APRENDER");
		else  if (i==2)boton = new JButton("PREGUNTAR");
		else  if (i==0)boton = new JButton("APRENDER");
		JPanel centro = new JPanel();
		JPanel sur = new JPanel();
		boton.addActionListener(
				new ActionListener () {
					public void actionPerformed(ActionEvent evento) {
						if (i==1)
						{
						aprender();
						limpia();
						
						}
						else if (i==2)
						{
							System.out.println("VENTANA EMERGENTE");
							aux();
							
						}
					}
				}
		);
		
		
		this.texto = new JTextArea("CLASES CALCULADAS \n \n");
		centro.add(this.texto);
		if (boton!=null) sur.add(boton);
		this._panelCentral.add(this.texto);
		this._panelSur.add(boton);
		if (this.situacion==2) this.texto.append(this.algoritmo.toString()+this.respuesta);
		if (this.situacion==0) this.texto.append("INTRUCCIONES GENERALES \n 1.Cargar un archivo que tenga una serie de puntos" +
				" \n 2.Pulsar sobre aprender para que aprendan los algoritmos \n " +
				"3.Despues de que han aprendido los algoritmos se puede preguntar por puntos");
		repaint();
		setVisible(true);
		
	}
	
	protected void nuevo_texto(String estrategia) {
	
		this.respuesta= new String();
		int clase =0;
		if (estrategia.equalsIgnoreCase("Estimacion_no_parametrica"))
		{
			//clase=this.no_parametrica.clase(this.preguntado);
			//respuesta="\n El punto "+this.preguntado.toString()+" pertenece a la clase "+clase+" ."; 
		}
		else if (estrategia.equalsIgnoreCase("Estimacion Parametrica"))
		{
			clase=this.parametrica.clase(this.preguntado);
			if (this.parametrica.getError()) respuesta="\n Se ha producido una matriz inversa que no tiene solucion \n no se puede calcular su clase"; 
			else respuesta="\n El punto "+this.preguntado.toString()+" pertenece a la clase "+clase+" ."; 
		}
		else if (estrategia.equalsIgnoreCase("Algoritmo de Lloyd"))
		{
			//clase=this.lloyd.clase(this.preguntado);
			//respuesta="\n El punto "+this.preguntado.toString()+" pertenece a la clase "+clase+" ."; 
		}
		else if (estrategia.equalsIgnoreCase("Self Organizing Map"))
		{
			clase=this.sof.clase(this.preguntado);
			clase++;
			respuesta="\n El punto "+this.preguntado.toString()+" pertenece a la clase "+clase+" ."; 
		}
		System.out.println(this.respuesta);
		
	}
	private void aux() {
		
		JPanel panel = new JPanel (new GridLayout(4,1,10,1));
		ButtonGroup tipoEstrategia = new ButtonGroup();
	    JComboBox combo = new JComboBox();
	    combo.addItem("Estimacion Parametrica");
	    combo.addItem("Estimacion_no_parametrica");
	    combo.addItem("Algoritmo de Lloyd");
	    combo.addItem("Self Organizing Map");
	    String puntotxt= this.clases.get(0).getCentro().stringVentana();
		JTextField punto = new JTextField (puntotxt,1);
		JLabel etiqpunto = new JLabel (" Punto:",SwingConstants.CENTER);
		JLabel estrateg = new JLabel (" Estrategia:",SwingConstants.CENTER);
		panel.add(etiqpunto);
		panel.add(punto);
		panel.add(estrateg);
		panel.add(combo);
		validarPeticion (panel,punto,combo);
		construirVista(this.situacion);
	}
	
	//private void validarPeticion (JPanel panel, JRadioButton tabRegular, JRadioButton tabIrregular, JTextField campoFilas, JTextField campoColumnas) {
	private void validarPeticion (JPanel panel,JTextField punto, JComboBox combo) {
		boolean valido=false;
		int filas=0;
		int columnas=0;
		Object[] opciones = {"Preguntar", "Cancelar"};
		int opcion=0;
		while (!valido) {
			try {
				opcion=JOptionPane.showOptionDialog(this,panel,"Datos del Punto",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,null,opciones,opciones[0]);
				if (opcion==0) {
					String cadena=punto.getText();
						if (valida(cadena)) valido=true;
					if (!valido) JOptionPane.showMessageDialog(this,"El punto introduce no tiene suficientes o demasiado componentes","Error en los datos",JOptionPane.ERROR_MESSAGE,null);
				}
				else valido=true;
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this,"Compruebe los valores introducidos","Error en los datos",JOptionPane.ERROR_MESSAGE,null);
			} 
		}
		String estrategia=(String)combo.getSelectedItem();
		if (opcion==0) nuevo_texto(estrategia);
	}
	
	public boolean valida(String cadena)
	{
		boolean valido=false;
		boolean error= false;
		int caso=0;
		double numero=0;
		double potencia=0;
		int indice=0;
		char letra;
		boolean negativo = false;
		this.preguntado= new Punto(this.clases.get(0).getCentro().getCoordenadas());
		char aux []= cadena.toCharArray();
		for (int i=0;((i<cadena.length() )&&(!error));i++)
		{
			letra=aux[i];
			switch (caso)
			{
			case 0:
			{
				if (aux[i]=='(') caso=1;
			}
			break;
			case 1:
			{
				potencia=1;
				numero=0;
				if ((aux[i]<='9') &&(aux[i]>='0')) 
				{
					numero+=pasaentero(aux[i]);
					caso=2;
				}
				else if (aux[i]=='-') 
				{
					negativo=true;
					caso=1;
				}
				else error=true;
			}break;
			case 2:
			{
				if ((aux[i]<='9') &&(aux[i]>='0')) 
				{
					potencia*=10;
					numero*=potencia;
					numero+=pasaentero(aux[i]);
				}
				else if (aux[i]==',') 
				{
					caso=1;
					if (negativo) this.preguntado.getNumeros()[indice]=numero*-1;
					else this.preguntado.getNumeros()[indice]=numero;
					negativo=false;
					indice++;
				}
				else if (aux[i]=='.') 
					{caso=3;
					potencia=1;
				}
				else if (aux[i]==')')
				{
					if (negativo) this.preguntado.getNumeros()[indice]=numero*-1;
					else this.preguntado.getNumeros()[indice]=numero;
					negativo=false;
					indice++;
				}
				else error=true;
			}break;
			case 3:
			{
				if ((aux[i]<='9') &&(aux[i]>='0')) 
				{
					potencia/=10;
					numero+=pasaentero(aux[i])*potencia;
				}
				else if (aux[i]==',') 
				{
					caso=1;
					if (negativo) this.preguntado.getNumeros()[indice]=numero*-1;
					else this.preguntado.getNumeros()[indice]=numero;
					negativo=false;
					indice++;
				}
				else if (aux[i]==')')
				{
					if (negativo) this.preguntado.getNumeros()[indice]=numero*-1;
					else this.preguntado.getNumeros()[indice]=numero;
					negativo=false;
					indice++;
				}
				else error=true;
			}break;
			}
		}
		System.out.println(this.preguntado.toString());
		if (indice==this.preguntado.getCoordenadas()) return(true);
		else return(false);
	}
	
	
	private float pasaentero(char c) {
		switch (c)
		{
		case '0':return 0;
		case '1':return 1;
		case '2':return 2;
		case '3':return 3;
		case '4':return 4;
		case '5':return 5;
		case '6':return 6;
		case '7':return 7;
		case '8':return 8;
		case '9':return 9;
		}
		return 0;
	}
	private void limpia() {
		_panelCentral.removeAll();
		JPanel centro = new JPanel();
		centro.add(this.texto);
		this.texto = new JTextArea("CLASES CALCULADASSSSSSSS \n \n");
		if (this.situacion==2) this.texto.append(this.algoritmo.toString());
		this._panelCentral.add(this.texto);
		construirVista(this.situacion);
		
	}
	
	private void aprender() {
		
		this.situacion=2;
		this.parametrica.Aprendizaje(this.algoritmo.getClases(), this.algoritmo.getCentros());
		//this.no_parametrica.Aprendizaje(this.algoritmo.getClases(), this.algoritmo.getCentros());
		this.lloyd.Aprendizaje(this.algoritmo.getClases(), this.algoritmo.getCentros());
		this.sof.Aprendizaje(this.algoritmo.getClases(), this.algoritmo.getCentros());
	}
	
	 /**
	  * Getter de serialVersionUID
	  * @return serialVersionUID
	  */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	/**
	 * metodo princiopal de la aplicacion
	 * @param arg
	 */
	 public static void main(String[] arg)
	 {
	 Ventana aux =new Ventana();
	 //aux.valida(new String("(55.89,10,0.01)"));

	 }

}