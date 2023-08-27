package CordConverter;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import BasicControls.SterowanieOkuma;
import BasicControls.SterowanieSinumeric;
import ErrorWindow.ErrorProgressWindow;
import instructionReadFromFile.ReadFromFile;
import instructionReadFromFile.ReadInstructionsGui;
import toolNumerator.ToolNumeratorView;


public class Wind extends JFrame implements ActionListener {



	//fields
	private Sterowanie sterowanie;
	

	//LOGI
	public static Log log = new Log();

	// menu
	private JMenuBar mb;

	private JMenu plik;
	private JMenuItem zakoncz;
	private JMenuItem wczytaj;

	private JMenu narzedzia;
	private JMenu kalkulatory;
	private JMenu edycja;
	private JMenu info;
	private JMenu opcje;
	private JMenu tolerancje;
	private JMenu tabele;
	private JMenu dodaj;
	private JMenuItem obrotStolu;
	private JMenuItem oProgramie;
	private JMenuItem sprawdzPoprawnosc;
	private JMenuItem parametryPostojNaDnie;
	private JMenuItem parametryFrezowania;
	private JMenuItem parametryWytaczania;
	private JMenuItem parametryGwintowania;
	private JMenuItem eCofnij;
	private JMenuItem eDalej;
			JMenuItem zaznaczWszystko;
			JMenuItem wyszukaj;
	private JMenuItem otworyGwintowe;
	private JMenuItem otwory;
	private JMenuItem trzpienie;
	private JMenuItem norma;
	private JMenuItem tablicaKodowM;
	private	JMenuItem tablicaKodowG;
	private JMenuItem parametry;
	private JMenuItem sterowanieMenuItem;
	private JMenuItem konwertuj;
	private JMenuItem op;
	private JRadioButtonMenuItem fanuc;
	private JRadioButtonMenuItem sinumeric;
	private JRadioButtonMenuItem okuma;
	private JMenuItem czasCykluMenu;
	private JMenuItem toolNumerateItem;


	static final Dimension CALC = new Dimension(500, 700);
	static final Dimension EDIT = new Dimension(900, 800);

	 Edytor panel = null;
	
	 	//stuff
		public tolerancje n =null;
		public SrednicaWewnetrzne sw =null;
		private JDialog gwintowanieDialog =null;
		private JDialog chropowatoscDialog =null;
		private JDialog parametryDialog =null;
		private JDialog postojNaDnie =null;
		public ReadInstructionsGui k = null;
		public OptionsPanel optionPanel = null;
		public Wyszukaj w=null;
		private ErrorProgressWindow errorWindow;
		private ToolNumeratorView toolNumerate;
		

		
		
		
		
		public static Options options;
		

		public Wind() {

		setSize(CALC);
		setTitle("CNC_Kuznia");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//set window at the center of screen more or less
		setLocation(screenSize.width/2 - CALC.width +75,0);
		
		
		sterowanie = new SterowanieFanuc();
		
			
			try {
				loadFromFile();
			} catch (ClassNotFoundException e1) {
				log.writeErrorLog("Class not found", e1, this.getClass().getSimpleName());
			}
			
		
		
		
		
	this.addWindowListener(new WindowAdapter() {
			
	
			@Override
			public void windowClosing(WindowEvent e) {
				
			if
			(JOptionPane.showConfirmDialog(Wind.this,"Czy na pewno wyj¶æ z programu?","Wyj¶cie",JOptionPane.OK_CANCEL_OPTION) ==JOptionPane.YES_OPTION)	
				{
				saveToFile();
				dispose();
				}
			}	
		});

		
		setVisible(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

		// menu
		mb = new JMenuBar();

		plik = new JMenu("Plik");
		wczytaj = new JMenuItem("Wczytaj");
		wczytaj.setEnabled(false);
		plik.add(wczytaj);
		zakoncz = new JMenuItem("Zakoncz");
		zakoncz.addActionListener(this);
		plik.addSeparator();
		plik.add(zakoncz);
		mb.add(plik);
		
		edycja=new JMenu("Edycja");
		edycja.setEnabled(false);
		
		//zaznacz
		zaznaczWszystko = new JMenuItem("Zaznacz wszysko"); 
		zaznaczWszystko.setMnemonic(KeyEvent.VK_A);
		zaznaczWszystko.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		zaznaczWszystko.addActionListener(this);
		edycja.add(zaznaczWszystko);
		
		
		//separator
		edycja.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		//cofnij
		eCofnij= new JMenuItem("Cofnij");
		eCofnij.addActionListener(this);
		eCofnij.setEnabled(false);
		edycja.add(eCofnij);
		
		//dalej
		eDalej = new JMenuItem("Dalej");
		eDalej.setEnabled(false);
		eDalej.addActionListener(this);
		edycja.add(eDalej);
		
		// wyszukaj
		edycja.add(new JSeparator(SwingConstants.HORIZONTAL));
		wyszukaj = new JMenuItem("Wyszukaj");
		wyszukaj.addActionListener(this);
		wyszukaj.setMnemonic(KeyEvent.VK_F);
		wyszukaj.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		edycja.add(wyszukaj);
		
		
		mb.add(edycja);
		
		opcje = new JMenu("Opcje");
		sterowanieMenuItem = new JMenu("Sterowanie");
		fanuc = new JRadioButtonMenuItem("Fanuc");
		fanuc.addActionListener(this);
		sinumeric = new JRadioButtonMenuItem("Sinumeric");
		sinumeric.addActionListener(this);
		okuma = new JRadioButtonMenuItem("Okuma");
		okuma.addActionListener(this);
		sterowanieMenuItem.add(fanuc);
		sterowanieMenuItem.add(sinumeric);
		sterowanieMenuItem.add(okuma);
		opcje.add(sterowanieMenuItem);
		op= new JMenuItem("Preferencje");
		op.addActionListener(this);
		opcje.add(op);
		mb.add(opcje);
		
		
		
		
		narzedzia = new JMenu("Narzedzia");
		kalkulatory = new JMenu("Kalkulatory");
		parametryPostojNaDnie = new JMenuItem("Czas postoju na dnie otworu");
		parametryPostojNaDnie.addActionListener(this);
		parametryWytaczania = new JMenuItem("Wykoñczenie powierzchni");
		parametryWytaczania.addActionListener(this);
		parametryFrezowania = new JMenuItem("Frezowanie");
		parametryFrezowania.addActionListener(this);
		parametryGwintowania = new JMenuItem("Gwintowanie");
		parametryGwintowania.addActionListener(this);
		konwertuj = new JMenuItem("Konwertuj");
		konwertuj.setMnemonic(KeyEvent.VK_K);
		konwertuj.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,ActionEvent.CTRL_MASK));

		konwertuj.addActionListener(this);
		sprawdzPoprawnosc = new JMenuItem("Sprawdz poprawnosc kodu");
		sprawdzPoprawnosc.addActionListener(this);
		sprawdzPoprawnosc.setMnemonic(KeyEvent.VK_R);
		sprawdzPoprawnosc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
		
		czasCykluMenu = new JMenuItem("Oblicz czas cyklu");
		czasCykluMenu.addActionListener(this);
		

		kalkulatory.add(parametryPostojNaDnie);
		kalkulatory.add(parametryWytaczania);
		kalkulatory.add(parametryFrezowania);
		kalkulatory.add(parametryGwintowania);
		

		
		toolNumerateItem = new JMenuItem("Numeruj Narzedzia");
		toolNumerateItem.setMnemonic(KeyEvent.VK_N);
		toolNumerateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		toolNumerateItem.addActionListener(this);
		
		//Narzedzia - dodaj
		dodaj = new JMenu("Dodaj");
		obrotStolu = new JMenuItem("Obroty palet");
		obrotStolu.addActionListener(this);
		dodaj.add(obrotStolu);
		

		
		narzedzia.add(kalkulatory);
		narzedzia.add(konwertuj);
		narzedzia.add(sprawdzPoprawnosc);
		narzedzia.add(czasCykluMenu);
		narzedzia.add(toolNumerateItem);
		narzedzia.add(dodaj);
	
		mb.add(narzedzia);
		
		tolerancje = new JMenu("Tolerancje");
		norma = new JMenuItem("Tolerancje wymiarów wg. ISO 2768");
		norma.setEnabled(true);
		norma.addActionListener(this);
		tolerancje.add(norma);
		tolerancje.addSeparator();
		otwory = new JMenuItem("Srednice wewnêtrzne");
		otwory.addActionListener(this);
		tolerancje.add(otwory);
		trzpienie = new JMenuItem("Srednice zewnêtrzne");
		trzpienie.setEnabled(false);
		tolerancje.add(trzpienie);
		tolerancje.addSeparator();
		otworyGwintowe = new JMenuItem("Otwory gwintowe wstepne");
		otworyGwintowe.setEnabled(false);
		tolerancje.add(otworyGwintowe);
		
		mb.add(tolerancje);

		tabele = new JMenu("Pomoc");
		parametry = new JMenuItem("Parametry cykli");
		parametry.setEnabled(false);
		tabele.add(parametry);
		tabele.addSeparator();
		tablicaKodowG = new JMenuItem("Lista G kodów");
		tabele.add(tablicaKodowG);
		tablicaKodowG.setEnabled(false);
		tablicaKodowM = new JMenuItem("Lista M kodów");
		tablicaKodowM .setEnabled(false);
		tabele.add(tablicaKodowM);
		
		mb.add(tabele);
		
		info = new JMenu("Info");
		oProgramie = new JMenuItem("O programie");
		oProgramie.addActionListener(this);
		info.add(oProgramie);

		mb.add(info);

		setJMenuBar(mb);
		
		
		String[] controlOptions = {"Fanuc", "Sinumeric", "Okuma"};
	int i = JOptionPane.showOptionDialog(null, "Wybierz sterowanie", "", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, controlOptions, controlOptions[0] );
	
	
			switch(i)
			{
				case 0:
					this.sterowanie = new SterowanieFanuc();
					
					fanuc.setSelected(true);
					
					break;
				case 1:
					this.sterowanie = new SterowanieSinumeric();
					sinumeric.setSelected(true);
					
					break;
				case 2:
					this.sterowanie = new SterowanieOkuma();
					okuma.setSelected(true);
					
					break;
			}
			

			panel = new Edytor(sterowanie, this);
			add(panel);
			this.setPreferredSize(EDIT);
			this.edycja.setEnabled(true);
			pack();
			repaint();
			
			
			//zaladuj kody z wybranego sterowania do bazy
			this.panel.interpreter.updateGCodes(this.sterowanie);
			
	}
	
	//getters
	public String getControls()
	{
		return sterowanie.toString();
	}
	
	
	//setters
	public void setControls(Sterowanie sterowanie)
	{
		String s = sterowanie.toString();
		if(s.equals("Fanuc"))
			this.fanuc.doClick();
		else if(s.equals("Okuma"))
			this.okuma.doClick();
		else if(s.equals("Sinumeric"))
			this.sinumeric.doClick();	
	}

	
	public void saveToFile()
	{
		
		try(ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Config.txt"))))
				{
		
					outStream.writeObject(Wind.options);
				}
		catch (IOException e)
		{
			log.writeErrorLog("IO exception while trying to save option class", e, Wind.class.getSimpleName());
		}

	}
	
	
	public static void loadFromFile() throws ClassNotFoundException
	{

		try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Config.txt"))))
		{
			Wind.options=(Options)inputStream.readObject();
			
		}
		catch (IOException e)
		{	
			log.writeErrorLog("B³±d odczytu pliku z preferencjami", e, Wind.class.toString());
			Wind.options=new Options();
		}
	}
	

	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(Wind::new);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == parametryPostojNaDnie) {
			if(this.postojNaDnie==null)
				this.postojNaDnie = new ParametryPostojNaDnie();
			else postojNaDnie.setVisible(true);

		}  else if (e.getSource() == parametryWytaczania) {

			if(this.chropowatoscDialog==null)
				this.chropowatoscDialog = new Parametry_wytaczanie();
			else chropowatoscDialog.setVisible(true);

		} else if (e.getSource() == parametryFrezowania) {
			
			if(this.parametryDialog==null)
				this.parametryDialog = new Parametry_frezowanie();
			else parametryDialog.setVisible(true);
		}
			else if(e.getSource() == parametryGwintowania)
			{
				if(gwintowanieDialog!=null)
					gwintowanieDialog.setVisible(true);
				else
					gwintowanieDialog = new Parametry_gwintowanie();
					
			}
			else if(e.getSource() == otwory)
			{
				
				if(this.sw ==null)
					this.sw = new SrednicaWewnetrzne(this);
				else 
				{
					sw.setAlwaysOnTop(true);
				}
		}
		 else if(e.getSource()==fanuc)
		 {
			 this.sinumeric.setSelected(false);
			 this.okuma.setSelected(false);
			 this.sterowanie= new SterowanieFanuc();
			 this.panel.sterowanieLabel.setText(sterowanie.toString());
			 panel.interpreter.updateGCodes(this.sterowanie);
			 
			 
			 if(panel.getClass().getSimpleName().equals("Edytor"))
				 ( panel).setControls(new SterowanieFanuc());
		 }
		 else if(e.getSource().equals(sinumeric))
		 {
			 this.fanuc.setSelected(false);
			 this.okuma.setSelected(false);
			 this.sterowanie= new SterowanieSinumeric();
			 panel.interpreter.updateGCodes(this.sterowanie);
			 
			 this.panel.sterowanieLabel.setText(sterowanie.toString());
			 
			 if(panel.getClass().getSimpleName().equals("Edytor"))
				 ( panel).setControls( new SterowanieSinumeric());
		 }
		 else if(e.getSource()==okuma)
		 {
			 this.sinumeric.setSelected(false);
			 this.fanuc.setSelected(false);
			 this.sterowanie = new SterowanieOkuma();
			 this.panel.sterowanieLabel.setText(sterowanie.toString());
			 panel.interpreter.updateGCodes(this.sterowanie);
			 
			 if(panel.getClass().getSimpleName().equals("Edytor"))
				 ( panel).setControls( new SterowanieOkuma());
		 }
		 else if(e.getSource()==zaznaczWszystko)
			{
				try {	
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_A);
					r.keyRelease(KeyEvent.VK_CONTROL);
					r.keyRelease(KeyEvent.VK_A);
				} catch (AWTException err) {
					
					log.writeErrorLog("robot Error", err, Wind.class.toString());
				}
				
			}
		

		
		 else if(e.getSource() == toolNumerateItem)
		 {
			 if(this.toolNumerate==null)
					this.toolNumerate = new ToolNumeratorView(panel);
				else toolNumerate.setVisible(true);
		 }
		
		 else if(e.getSource() == norma)
		 {
			 if(this.n==null)
				 this.n = new tolerancje(this);
			 else n.setAlwaysOnTop(true);
			 
			 
		 }
		 else if(e.getSource()==konwertuj)
		 {
			 if(this.k==null)
			 {
				k=new ReadInstructionsGui(this);
				ReadFromFile instructionReader = new ReadFromFile(k,panel);
				
			 }
			 else
			 {
				 k.setVisible(true);		 
			 }
		 }
		 else if(e.getSource()==sprawdzPoprawnosc)
		 {
			
			 if(this.errorWindow==null)
				 this.errorWindow = new ErrorProgressWindow(this.panel);
			 else this.errorWindow.setVisible(true);
		 }
		 else if(e.getSource()==obrotStolu)
		 {
			 if(this.sterowanie.toString().equals("Sinumeric"))
			 {
				 JOptionPane.showMessageDialog(this, "Opcja niedostepna dla tego typu sterowania: Sinumeric");
			 }
			 else 
			 {
				 this.panel.analyze();
				 if(this.sterowanie.toString().equals("Fanuc"))
				 {
					 int i =0;
					 
				while(panel.getListLength()>i ) 
				{
					
					if(panel.getfunction(i).isProgramName() || panel.getfunction(i).isProgramBorder())
					{
						i++;
						continue;
					}
					 this.panel.addFunctionToList(new Function("G28 B0",new SterowanieFanuc()), i++);
					 this.panel.addFunctionToList(new Function("G91 G30 B0 Z0",new SterowanieFanuc()), i++);
					 this.panel.addFunctionToList(new Function("M127",new SterowanieFanuc()), i++);
					 this.panel.addFunctionToList(new Function("",new SterowanieFanuc()), i++);
					 break;
				 }
				i=panel.getListLength()-1;
				while(i>0) 
				{
					if(panel.getfunction(i).isProgramBorder() || panel.getfunction(i).containsFunction(30) || panel.getfunction(i).containsFunction(99))
					{
						i--;
						continue;
					}
					
					
					this.panel.addFunctionToList(new Function("M128",new SterowanieFanuc()), i);
					this.panel.addFunctionToList(new Function("G91 G30 B0 Z0",new SterowanieFanuc()), i);
					this.panel.addFunctionToList(new Function("G28 B0",new SterowanieFanuc()), i);
					this.panel.addFunctionToList(new Function("",new SterowanieFanuc()), i);
				 break;
				}
				
				panel.write();
					 
				 }
				 else if(this.sterowanie.toString().equals("Okuma"))
				 {
				 
					 int i=0;
					 while(i<panel.getListLength())
					 {
						if((panel.getfunction(i)).isProgramName() || panel.getfunction(i).isProgramBorder())
						{
							
							i++;
							continue;
						}
 
					 this.panel.addFunctionToList(new Function("IF [VPLNO EQ 1] NPRD",new SterowanieOkuma()), i++);
					 this.panel.addFunctionToList(new Function("M60",new SterowanieOkuma()), i++);
					 this.panel.addFunctionToList(new Function("NPRD",new SterowanieOkuma()), i++);
					 break;
					 }
					 panel.write();
				 }
 
			 }
}
		
		 else if(e.getSource()==op)
		 {
			 if(this.optionPanel==null)
			 {
				 optionPanel = new OptionsPanel(this);
 
			 }
			 else 
			 {
				 optionPanel.setVisible(true);
				
			 }
		 }
		 else if(e.getSource()==czasCykluMenu)
		 {
			 CzasCyklu.countCycleTime(this.panel);
		 }
		
		else if (e.getSource() == zakoncz) {
			dispose();
		}
		else if(e.getSource() == wyszukaj)
		{
			
			if(this.w==null)
			{
				this.w=new Wyszukaj(this);
			}
			else w.setAlwaysOnTop(true);
			
		}

		else if (e.getSource() == oProgramie) {

			JOptionPane.showMessageDialog(this, "v2.0.14 12.07.23");
		} 
		else 
		{
			
		}

	}

}
