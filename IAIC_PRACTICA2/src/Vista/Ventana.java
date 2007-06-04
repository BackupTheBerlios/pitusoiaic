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
	 * Atributo que es una etiqueta con el numero de habitacion actual
	 */
	private JLabel _habitacion;
	/**
	 * Atributo que es una etiqueta para el numero nodos abiertos.
	 */
	private JLabel _numabiertos;
	/**
	 * Atributo que es una etiqueta para el numero de nodos cerrados.
	 */
	private JLabel _numcerrados;
	/**
	 * Atributo que es un campo de texto por donde se muestra la informacion
	 */
	private JTextArea texto;
	/**
	 * Atributo que indica si se ha llegado al final de la resolucion del problema
	 */
	private boolean fin;
	/**
	 * Atributo que indica la estrategia general
	 */
	private int general;
	/**
	 * Atributo que indica la estrategia nogeneral
	 */
	private int nogeneral;
	/**
	 * Atributo que indica si se ha llegado a la solucion
	 */
	private boolean solucion;
	
	private boolean aprendizaje;
		
	private Vector<Clase> clases;

	private Cuantizacion_vectorial algoritmo;
	
	private static final long serialVersionUID = 1L;
	
	private int situacion; //0 no se ha cargado archivo //1 se ha cargado archivo se debe de aprender //2 se puede preguntar

	private Estimacion_parametrica parametrica;
	
	private Estimacion_no_parametrica no_parametrica;
	
	private Lloyd lloyd;
	
	private Self_Organizinig_Map sof;
	
	/**
	 * Construvtor de la clase Ventana,
	 * Aqui se crea la ventana de la aplicacion y se arranca desde aqui
	 */
	public Ventana()
	{
		super("Clasificador de puntos");
		this.general=1;
		this.nogeneral=1;
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
		configurarPaneEste();
		setJMenuBar(barraMenu);
		fin=false;
		solucion=true;
		situacion=0;
		construirVista(0);
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
		JMenu menuEstrategia = new JMenu("Eleccion de Estrategia ");
		configurarMenuArchivo(menuArchivo);
		configurarMenuEstrategia(menuEstrategia);
		barraMenu.add(menuArchivo);
		barraMenu.add(menuEstrategia);
		barraMenu.setVisible(true);
	}
	/**
	 * Metodo que configura el menu de estrategias tanto para las generales como las no generales
	 * @param menuEstrategia elemento de la clase Jmenu donde se añaden las cosas
	 * @param opcion booleano para distinguir entre la estrategia global y la no global
	 */
	private void configurarMenuEstrategia(JMenu menuEstrategia) {
		menuEstrategia.setToolTipText("Menú Estrategia: AEstrella - BusquedaVoraz - CosteUniforme - EscaladaMaxima - EscaladaSimple - PrimeroAnchura -PrimeroProfundidad");
		menuEstrategia.setMnemonic('A');
		JMenuItem estrategia1 = new JMenuItem ("Estimacion_no_parametrica", 'G'); 
		JMenuItem estrategia2 = new JMenuItem ("Estimacion Parametrica", 'G'); 
		JMenuItem estrategia3 = new JMenuItem ("Algoritmo de Lloyd", 'G'); 
		JMenuItem estrategia4 = new JMenuItem ("Self Organizing Map", 'G'); 
		menuEstrategia.add(estrategia1);
		menuEstrategia.add(estrategia2);
		menuEstrategia.add(estrategia3);
		menuEstrategia.add(estrategia4);
		establecerOyenteEstrategia(estrategia1,1);
		establecerOyenteEstrategia(estrategia2,2);
		establecerOyenteEstrategia(estrategia3,3);
		establecerOyenteEstrategia(estrategia4,4);
	}
	/**
	 * Metodo que configura el menu de cargar
	 * @param menuArchivo elemento de la clase JMenu que se va a modificar
	 */
	public void configurarMenuArchivo(JMenu menuArchivo) {
		menuArchivo.setToolTipText("Menú Archivo: Cargar ");
		menuArchivo.setMnemonic('A');
		JMenuItem opcionCargar = new JMenuItem ("Cargar Juego", 'C'); 
		opcionCargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
		menuArchivo.add(opcionCargar);
		establecerOyenteCargar(opcionCargar);
	}
	
	/**
	 * Metodo para establecer el oyente de la eleccion de la estrategia
	 * @param opcionNuevo elemento de la clase AbstarctButton que es el boton
	 * @param a entero que indica la estrategia
	 * @param opcion booleano para distinguir entre las estrategias
	 */
	private void establecerOyenteEstrategia(AbstractButton opcionNuevo,final int a) {
		//Estrategia(true);
	opcionNuevo.addActionListener(
				new ActionListener () {
					public void actionPerformed (ActionEvent evento) {
						establecer(a);
					}
				}
		);
	}

	/**
	 * Metodo que establece la estrategia
	 * @param numero un entero que indica la estrategia
	 * @param opcion un booleano que indica si es general o no lo es
	 */
	private void establecer(int numero) {
		this.general=numero;	;
	}
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
								fin=false;
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
		
		// Se llama a cuantizacion vectorial
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
		 this.algoritmo= new Cuantizacion_vectorial(20);
		 algoritmo.calcula(aux);
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
		//--------El letor de archivos
		//EstadoLaberinto estadoinicial;
		try {
		//	estadoinicial = new EstadoLaberinto(Lector.leerFichero(archivoS));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return(false);
		}
		actualiza(false);
		//estadoinicial.EstadoInicial();
		//this.estado=estadoinicial;
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
	 * Metodo para configurar el panel Este
	 *
	 */
	public void configurarPaneEste() {
		//_panelEste.removeAll();
		/*_panelEste.setLayout(new GridLayout(4,1));
		_panelEste.setBorder(new BevelBorder(BevelBorder.RAISED));
		_panelEste.add(new Label("INFORMACIÓN SOBRE EL JUEGO"));
		this._habitacion=new JLabel ("  Habitacion: "+numeroHabitacion()+"  ",JLabel.CENTER);
		this._numabiertos=new JLabel ("  Abiertos: "+numeroAbiertos()+"  ",JLabel.CENTER);
		this._numcerrados=new JLabel ("  Cerrados: "+numeroCerrados()+"  ",JLabel.CENTER);
		_panelEste .add(_habitacion);
		_panelEste.add(_numabiertos);
		_panelEste.add(_numcerrados);*/
	}
	/**
	 * Metodo que actualiza los valores del Panel Este
	 * @param opcion un booleano que indica el tipo de acrualizacion
	 */
	public void actualiza(boolean opcion)
	{
	/*if (opcion)
	{
	this._habitacion.setText("  Habitacion: "+numeroHabitacion()+"  ");
	this._numabiertos.setText("  Abiertos: "+numeroAbiertos()+"  ");
	this._numcerrados.setText("  Cerrados: "+numeroCerrados()+"  ");
	}
	else
	{
		this._habitacion.setText("  Habitacion: "+0+"  ");
		this._numabiertos.setText("  Abiertos: "+0+"  ");
		this._numcerrados.setText("  Cerrados: "+0+"  ");
	}*/
	}
	/**
	 * Metodo que devuelve el numero de habitacion actual si existe
	 * @return un entero que es el numero de la habitacion
	 */
	private int numeroHabitacion()
	{
		return general;
	}
	/**
	 * Metodo que devuelve el numero de nodos cerrados
	 * @return un entero que es el numero de nodos cerrados
	 */
	private int numeroCerrados() {
		return general;

		
	}
	
	/**
	 * Metodo que devuelve el numero de nodos abiertos
	 * @return un entero que es el numero de nodos abiertos
	 */
	private int numeroAbiertos() {
		return general;

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
						siguiente();
						
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
		//_panelESTE.removeAll();
		if (this.situacion==2) this.texto.append(this.algoritmo.toString());
		if (this.situacion==0) this.texto.append("INTRUCCIONES GENERALES \n 1.Elegir una estrategia general \n 2.Elegir el archivo de donde se lee las habitaciones \n 3.Antes de dar al boton seguir elegir la estrategia \n elegida para el problema de cada habitacion");
		repaint();
		setVisible(true);
		
	}
	
	private void aux() {
		
		JPanel panel = new JPanel (new GridLayout(1,2,10,1));
		//ButtonGroup tipoTablero = new ButtonGroup();
		//JRadioButton tabRegular = new JRadioButton ("Tablero regular", true);
		//JRadioButton tabIrregular = new JRadioButton ("Tablero irregular", false);
		//JTextField campoFilas = new JTextField ("10",2);
		JTextField punto = new JTextField ("10",1);
		//final JTextField campoColumnas = new JTextField ("10",2);
		//JLabel etiqFilas = new JLabel ("Número de filas:",SwingConstants.RIGHT);
		JLabel etiqpunto = new JLabel (" Punto:",SwingConstants.RIGHT);
		//final JLabel etiqColumnas = new JLabel ("Número de columnas:",SwingConstants.RIGHT);
		/*tabRegular.addActionListener(
				new ActionListener() {
					public void actionPerformed (ActionEvent evento) {
						campoColumnas.setEnabled(true);
						etiqColumnas.setEnabled(true);
					}
				}
		);
		tabIrregular.addActionListener(
				new ActionListener() {
					public void actionPerformed (ActionEvent evento) {
						campoColumnas.setEnabled(false);
						etiqColumnas.setEnabled(false);
					}
				}
		);*/
		//tipoTablero.add(tabRegular);
		//tipoTablero.add(tabIrregular);
		//panel.add(tabRegular);
		//panel.add(etiqFilas);
		//panel.add(campoFilas);
		//panel.add(tabIrregular);
		//panel.add(etiqColumnas);
		//panel.add(campoColumnas);
		panel.add(etiqpunto);
		panel.add(punto);
		validarPeticion (panel,punto );
		
		/*// TODO Auto-generated method stub
		JFrame ventana = new JFrame("INTRODUCE PUNTOS");
		setLocation(100,100);
		setSize(200,100);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		establecerCierre();*/
		/*_panelCentral=new JPanel();
		_panelNorte=new JPanel();
		_panelEste=new JPanel();
		_panelSur=new JPanel();
		_panelOeste=new JPanel();
		add(_panelCentral, BorderLayout.CENTER);
		add(_panelNorte, BorderLayout.NORTH);
		add(_panelSur, BorderLayout.SOUTH);
		add(_panelEste, BorderLayout.EAST);
		add(_panelOeste, BorderLayout.WEST);*/
		//configurarPanelNorte();
		//configurarPaneEste();
		//setJMenuBar(barraMenu);
		/*fin=false;
		solucion=true;
		//situacion=0;
		//construirVista(0);
		ventana.setVisible(true);*/
	}
	
	//private void validarPeticion (JPanel panel, JRadioButton tabRegular, JRadioButton tabIrregular, JTextField campoFilas, JTextField campoColumnas) {
	private void validarPeticion (JPanel panel,JTextField punto) {
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
		//if (opcion==0) _controlador.crearTablero(tabRegular.isSelected(),filas,columnas);
	}
	
	public boolean valida(String cadena)
	{
		boolean valido=false;
		return(valido);
	}
	
	
	private void limpia() {
		_panelCentral.removeAll();
		JPanel centro = new JPanel();
		centro.add(this.texto);
		this.texto = new JTextArea("CLASES CALCULADASSSSSSSS \n \n");
		if (this.situacion==2) this.texto.append(this.algoritmo.toString());
		this._panelCentral.add(this.texto);
		System.out.println("SE Aprendessssssssssssssss");
		construirVista(this.situacion);
		
	}
	
	private void aprender() {
		
		this.situacion=2;
		System.out.println(this.situacion);
		this.parametrica.Aprendizaje(this.algoritmo.getClases(), this.algoritmo.getCentros());
		//this.no_parametrica.Aprendizaje(this.algoritmo.getClases(), this.algoritmo.getCentros());
		this.lloyd.Aprendizaje(this.algoritmo.getClases(), this.algoritmo.getCentros());
		this.sof.Aprendizaje(this.algoritmo.getClases(), this.algoritmo.getCentros());
		System.out.println("SE Aprende");
	}
	
	private String estragias() {
		String aux= new String();
		switch(general)
		{
			case 1:aux+="Estrategia general AEstrella \n";break;
			case 2:aux+="Estrategia general BusquedaVoraz \n";break;
			case 3:aux+="Estrategia general CosteUniforme \n";break;
			case 4:aux+="Estrategia general EscaladaMaxima \n";break;
			case 5:aux+="Estrategia general EscaladaSimple \n";break;
			case 6:aux+="Estrategia general PrimeroAnchura\n";break;
			case 7:aux+="Estrategia general PrimeroPronfundidad \n";break;
		}
		switch(nogeneral)
		{
		case 1:aux+="Estrategia hanitacion AEstrella \n";break;
		case 2:aux+="Estrategia hanitacion BusquedaVoraz \n";break;
		case 3:aux+="Estrategia hanitacion CosteUniforme \n";break;
		case 4:aux+="Estrategia hanitacion EscaladaMaxima \n";break;
		case 5:aux+="Estrategia hanitacion EscaladaSimple \n";break;
		case 6:aux+="Estrategia hanitacion PrimeroAnchura\n";break;
		case 7:aux+="Estrategia hanitacion PrimeroPronfundidad \n";break;
		}
		return(aux);
	}
	/**
	 * metodo que resuelve el problema de la habitacion
	 * @param p de la clase problema que es el problema a resolver
	 * @param e de la clase estado
	 */
	private void problema_habitacion()
	{
		
		
		
	}
	
	/**
	 * Metodo para indicar que problema se debe resolver y despues resolverlo
	 */
	private void problema() {
		
		
		}
	
	
	/**
	 * Metodo que hace que avanzemos a la siguiente habitacion del problema general
	 */
	public void siguiente()
	{
		
		//if (this.fin==false)
		
	}
	

	 /**
	  * Getter de serialVersionUID
	  * @return serialVersionUID
	  */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	/**
	 * Getter de la etiqueta habitacion
	 * @return una etiueta
	 */
	public JLabel get_habitacion() {
		return _habitacion;
	}

	/**
	 * Getter de numero de abiertos
	 * @return el numero de abiertos
	 */
	public JLabel get_numabiertos() {
		return _numabiertos;
	}

	/**
	 * Getter de numero de cerrados
	 * @return el numero de cerrados
	 */
	public JLabel get_numcerrados() {
		return _numcerrados;
	}
	
	/**
	 * metodo princiopal de la aplicacion
	 * @param arg
	 */
	 public static void main(String[] arg)
	 {
	 new Ventana();

	 }

}
