package cncCodeGeneratingFunctions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import BasicControls.SterowanieSinumeric;
import CordConverter.ControlTypes;
import CordConverter.Edytor;
import CordConverter.Function;
import CordConverter.Wind;

public class Poglebienie extends JFrame implements ActionListener{

	//Labels
	JLabel wspolrzednaXLabel;
	JLabel wspolrzednaYLabel;
	JLabel srednicaOtworuLabel;
	JLabel srednicaFrezaLabel;
	JLabel startLabel;
	JLabel dnoLabel;
	JLabel obrotyLabel;
	JLabel posuwLabel;
	JLabel podzielPrzejsciaLabel;
	JLabel iloscPrzejscLabel;
	JLabel dodajPrzejscieNaGotowoLabel;
	JLabel metodaLabel;
	
	//JTextFields
	JTextField wspolrzednaXTxt;
	JTextField wspolrzednaYTxt;
	JTextField srednicaOtworuTxt;
	JTextField srednicaFrezaTxt;
	JTextField startTxt;
	JTextField dnoTxt;
	JTextField obrotyTxt;
	JTextField posuwTxt;
	
	//checkBox
	JCheckBox podzielPrzejsciaCheck;
	JCheckBox dodajPrzejscieNaGotowoCheck;
	
	//spinner
	JSpinner ilePrzejscSpinner;
	
	//JButton
	JButton Oblicz;
	JButton Cofnij;
	
	Edytor parent;
	
	//JComboBox
	JComboBox<String> metodaBox;
	
	//fields
	int obroty;
	float feed;
	float X;
	float Y;
	float srednicaFreza;
	float srednicaOtworu;
	float poczatekPoglebienia;
	float dnoPoglebienia;
	int iloscPrzejsc;
	boolean podziel;
	boolean	przejscieNaGotowo;
	
	int toolNumber;
	float rotationB;
	String base;
	
	Sterowanie sterowanie;
	
	static final String []METODY = {"Wjazd-okrag","Spiralnie","Spiralnie-promieniowo"};
	
	public Poglebienie(Edytor parent)
	{
		this.parent = parent;
		this.sterowanie=parent.getControls();
		setPreferredSize(new Dimension(550,400));
		setVisible(true);
		setResizable(false);
		setTitle("Poglebinie walcowe");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout layout = new GridBagLayout();
		setLocation(450, 200);
		setLayout(layout);
		
		this.toolNumber = parent.getToolBar().getToolNumber();
		this.rotationB = parent.getToolBar().getRotation();
		this.base = parent.getToolBar().getBase();
		
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				
				Cofnij.doClick();
			
			}		
		});
		
		
		//layout
		GridBagConstraints border = new GridBagConstraints();
		border.weightx=0.1;
		border.weighty=0.1;
		border.insets.set(5, 5, 5, 5);
		border.fill=GridBagConstraints.BOTH;
		
		
		//labels
		
		border.gridx=0;
		border.gridy=0;
		metodaLabel = new JLabel("Metoda ");
		metodaLabel.setToolTipText("Sposób generowania œcie¿ki dla tej operacji");
		add(metodaLabel,border);
		
		//X
		border.gridx=0;
		border.gridy++;
		wspolrzednaXLabel = new JLabel("Wspolrzedna X");
		add(wspolrzednaXLabel,border);
		
		//Y
		border.gridy++;
		wspolrzednaYLabel = new JLabel("Wspolrzedna Y");
		add(wspolrzednaYLabel,border);
		
		//srednicaOtwtoru
		border.gridy++;
		srednicaOtworuLabel = new JLabel("Srednica frezowanego otworu d");
		add(srednicaOtworuLabel,border);
		
		//srednicaFreza
		border.gridy++;
		srednicaFrezaLabel = new JLabel("Srednica freza");
		add(srednicaFrezaLabel,border);
		
		//start
		border.gridy++;
		startLabel = new JLabel("Z poczatkowy");
		add(startLabel,border);
		
		//dnoOtworu
		border.gridy++;
		dnoLabel = new JLabel("Z dna otworu");
		add(dnoLabel,border);
		
		//obroty
		border.gridy++;
		obrotyLabel = new JLabel("Obroty");
		obrotyLabel.setToolTipText("Prêdkoœæ obrotowa wrzeciona. Mozna podac predkosc skrawania w celu obliczenia obrotow wg formatu: Vc=wartosc");
		add(obrotyLabel,border);
		
		//posow
		border.gridy++;
		posuwLabel = new JLabel("Posuw");
		posuwLabel.setToolTipText("Prêdkoœæ posuwu Vf w mm/min. Mo¿na podac posow na zab i liczbe zebow w celu obliczenia predkosci posuwu wg schematu fn=wartosc z=wartosc");
		add(posuwLabel,border);
		
		//PodzielPrzejscia
		border.gridy++;
		podzielPrzejsciaLabel = new JLabel("Podziel na przejscia");
		podzielPrzejsciaLabel.setToolTipText("Dzieli poglebianie otworu na kilka przejsc jesli poglebienie jest zbyt glebokie");
		add(podzielPrzejsciaLabel,border);
		
		//iloscprzejsc
		border.gridy++;
		iloscPrzejscLabel = new JLabel("Ilosc przejsc");
		iloscPrzejscLabel.setEnabled(false);
		add(iloscPrzejscLabel,border);
		
		border.gridy++;
		dodajPrzejscieNaGotowoLabel = new JLabel("Dodaj przejscie wykañczaj±ce");
		dodajPrzejscieNaGotowoLabel.setToolTipText("Ustawnia naddatek na ostatnim przejsciu ap=0.5mm");
		dodajPrzejscieNaGotowoLabel.setEnabled(false);
		add(dodajPrzejscieNaGotowoLabel,border);
		
		//oblicz przycisk
		border.gridy++;
		Oblicz = new JButton("Oblicz");
		Oblicz.addActionListener(this);
		add(Oblicz,border);
		
		
		//------------- x=1
		
		//metody combo
		border.gridx=1;
		border.gridy=0;
		metodaBox = new JComboBox<>(METODY);
		metodaBox.setSelectedIndex(0);
		add(metodaBox,border);
		
		metodaBox.addActionListener(e->
		{
			if(metodaBox.getSelectedIndex()==1) {
			parent.spiral=new Spiral(parent);
			parent.pogl=null;
			this.dispose();
			}
			else if(metodaBox.getSelectedIndex()==2)
			{
				parent.wklad = new Wkladka(parent);
				parent.pogl=null;
				this.dispose();
			}
		});
		
		
		//Xtxt
		border.gridy++;
		wspolrzednaXTxt = new JTextField("");
		wspolrzednaXTxt.setSize(75,25);
		add(wspolrzednaXTxt,border);
		
		//YTxt
		border.gridy++;
		wspolrzednaYTxt = new JTextField("");
		wspolrzednaYTxt.setSize(75,25);
		add(wspolrzednaYTxt,border);
		
		//srednicaOtw
		border.gridy++;
		srednicaOtworuTxt = new JTextField("");
		srednicaOtworuTxt.setSize(75, 25);
		add(srednicaOtworuTxt,border);

		//srednicaFreza
		border.gridy++;
		srednicaFrezaTxt = new JTextField("");
		srednicaFrezaTxt.setSize(75, 25);
		add(srednicaFrezaTxt,border);
		
		//start
		border.gridy++;
		startTxt = new JTextField("");
		startTxt.setSize(75, 25);
		add(startTxt,border);
		
		//dno otworu
		border.gridy++;
		dnoTxt = new JTextField("");
		dnoTxt.setSize(75, 25);
		add(dnoTxt,border);
		
		//obroty
		border.gridy++;
		obrotyTxt= new JTextField("");
		obrotyTxt.setSize(75,25);
		add(obrotyTxt,border);
		
		//posow
		border.gridy++;
		posuwTxt = new JTextField("");
		posuwTxt.setSize(75,25);
		add(posuwTxt,border);
		
		//check box podziel
		border.gridy++;
		podzielPrzejsciaCheck = new JCheckBox();
		podzielPrzejsciaCheck.addActionListener(this);
		add(podzielPrzejsciaCheck,border);
		
		//spinner ile przejsc
		border.gridy++;
		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100, 1);
		ilePrzejscSpinner = new JSpinner(model);
		ilePrzejscSpinner.setEnabled(false);
		add(ilePrzejscSpinner,border);
		
		border.gridy++;
		dodajPrzejscieNaGotowoCheck = new JCheckBox();
		dodajPrzejscieNaGotowoCheck.setEnabled(false);
		add(dodajPrzejscieNaGotowoCheck,border);
		
		//button cofnij
		border.gridy++;
		Cofnij = new JButton("Cofnij");
		Cofnij.addActionListener(this);
		add(Cofnij,border);
		
		
		
		//rys
		border.weightx=1;
		border.weighty=1;
		
		border.gridheight=12;
		border.gridwidth=2;
		border.gridx=2;
		border.gridy=0;
		add(new canv(),border);
		
		
		
		pack();
		
		
	}
	
	
	private boolean getFieldValues()
	{
	
		try
		{
			this.X = Float.parseFloat(wspolrzednaXTxt.getText());
		}
		
		catch (NumberFormatException e )
		{
			
			JOptionPane.showMessageDialog(this, "Niepoprawna wspolrzedna X");
			return false;
			
		}
		
		try
		{
			this.Y = Float.parseFloat(wspolrzednaYTxt.getText());
		}
		
		catch (NumberFormatException e )
		{
			
			JOptionPane.showMessageDialog(this, "Niepoprawna wspolrzedna Y");
			return false;
			
		}
		
		try
		{
			this.srednicaFreza = Float.parseFloat(srednicaFrezaTxt.getText());
		}
		
		catch (NumberFormatException e )
		{
			
			JOptionPane.showMessageDialog(this, "Niepoprawna srednica narzedzia");
			return false;
			
		}
		
		try
		{
			this.srednicaOtworu = Float.parseFloat(srednicaOtworuTxt.getText());
		}
		
		catch (NumberFormatException e )
		{
			
			JOptionPane.showMessageDialog(this, "Niepoprawna srednica otworu");
			return false;
			
		}
		
		try
		{
			this.poczatekPoglebienia = Float.parseFloat(startTxt.getText());
		}
		
		catch (NumberFormatException e )
		{
			
			JOptionPane.showMessageDialog(this, "Niepoprawna wspolrzedna Z poczatakowa");
			return false;
			
		}
		
		try
		{
			this.dnoPoglebienia = Float.parseFloat(dnoTxt.getText());
		}
		
		catch (NumberFormatException e )
		{
			
			JOptionPane.showMessageDialog(this, "Niepoprawna wspolrzedna Z dna poglebienia");
			return false;
			
		}
		if(Function.covertVcToN(this.obrotyTxt, this.srednicaFreza)!=0) return false;
		
		try
		{
			this.obroty= Integer.parseInt(obrotyTxt.getText());
		}
		
		catch (NumberFormatException e )
		{
			
			JOptionPane.showMessageDialog(this, "Niepoprawna predkosc wrzeciona");
			return false;
			
		}
		if(Function.covertFnToVf(this.posuwTxt, this.obroty)!=0) return false;
		
		try
		{
			this.feed = Float.parseFloat(posuwTxt.getText());
		}
		
		catch (NumberFormatException e )
		{
			
			JOptionPane.showMessageDialog(this, "Niepoprawna wartosc posuwu");
			return false;
			
		}
		
	
		if(srednicaOtworu<=1)
		{
			JOptionPane.showMessageDialog(this, "Nieprawid³owa œrednica narzêdzia");
			return false;
		}
		
		
		if(srednicaFreza>srednicaOtworu)
		{
			JOptionPane.showMessageDialog(this, "Srednica narzedzia musi byc mniejsza od frezowanego otworu");
			return false;
		}
		
		if(podzielPrzejsciaCheck.isSelected())
		{
			
			
			this.podziel =true;
			try
			{
				this.iloscPrzejsc= (int)ilePrzejscSpinner.getValue();
				
			}
			
			catch (NumberFormatException e )
			{
				
				JOptionPane.showMessageDialog(this, "Niepoprawna ilosc przejsc");
				return false;
				
			}
			if(dodajPrzejscieNaGotowoCheck.isSelected())
			{
				this.przejscieNaGotowo = true;
			}
			else this.przejscieNaGotowo = false;
			
			
		}
		else this.podziel = false;

		return true;
	}
	
	class canv extends JPanel
	{
		
		
		canv()
		{
			setPreferredSize(new Dimension(150,400));
			setVisible(true);
		
		
			repaint();
			
		}
		
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			//uklad wspolrzednych
			//os y
			g.setColor(Color.BLACK);
			g.drawLine(10, 250, 10, 210);  
			g.drawLine(10, 210, 13, 217);
			g.drawLine(10, 210, 7, 217);
			g.drawString("y", 1, 217);
			
			//os x
			g.drawLine(10, 250, 50, 250);
			g.drawLine(50, 250, 45, 245);
			g.drawLine(50, 250, 45, 255);
			g.drawString("x", 36, 260);
			
			
			//okrag zew
			g.drawArc(20, 20, 80, 80, 0, 360);
			
			//linia przerywana pionowa y
			g.drawString("X", 10, 55);
			for(int i=0;i<=88;)
			{
				g.drawLine(i+17,60,i+21,60);
				i+=8;	
			}
			//linia przerywana pozioma
			g.drawString("Y", 61, 10);
			for(int i=0;i<=88;)
			{
				g.drawLine(60,i+17,60,i+21);
				i+=8;	
			}
			
			//pozioma linia wymiarowa
			g.drawString("d",60,125);
			g.drawLine(20, 130, 100,130);
			g.drawLine(20, 128, 20, 132);
			g.drawLine(100, 128, 100, 132);
			
			
			g.setColor(Color.RED);
			//schemat ruchu narzêdzia
			g.drawArc(60, 40, 35, 40, 180, 180);
			g.drawArc(25, 25, 70, 70, 0, 270);
			//strza³ka
			g.drawLine(60, 95, 56, 91);
			g.drawLine(60, 95, 56, 99);
			
			//ruch narzêdzia
			g.drawString("Œcie¿ka narzêdzia", 10, 195);
			g.drawLine(30, 175, 70, 175);
			g.drawLine(70, 175, 66, 171);
			g.drawLine(70, 175, 66, 179);
			
	
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	private void generatePocketMilling(float X, float Y,float d, float t, float R, float Z, int speed, float feed)
	{
		PrintStream p = new PrintStream(parent.txt);
		float najazd = (srednicaOtworu - srednicaFreza)/8;
		System.setOut(p);
		System.setErr(p);
		
		
		System.out.printf(Locale.CANADA,"(T%d FREZ FI%.2f RMAX=%.2f)%n", toolNumber,t, najazd);
		
		
		sterowanie.przygotowanieUkladuINarzedzia(5, toolNumber, Wind.options.getSafeRetraction(), rotationB,base);
		
		System.out.printf(Locale.CANADA,"N35 S%d M3%n",speed);
		System.out.printf(Locale.CANADA,"N40 G0 X%.3f Y%.3f M8%n",X,Y);
		System.out.printf(Locale.CANADA,"N45 G0 Z%.3f%n",R+100);
		
		if(parent.getControls()==new SterowanieSinumeric())
			System.out.printf(Locale.CANADA,"N50 G0 Z%.3f%n",R);
		else
			System.out.printf(Locale.CANADA,"N50 G1 Z%.3f F10000.%n",R);
		
		if(!this.podziel)
		{
		
			System.out.printf(Locale.CANADA,"N55 G1 Z%.3f F50%n", Z);
		
			if(parent.getControls().isType(ControlTypes.FANUC))
				System.out.printf(Locale.CANADA,"N60 G41 D%d G1 X%.3f F%.1f%n", toolNumber, X+najazd,feed);
			else
					System.out.printf(Locale.CANADA,"N60 G41 G1 X%.3f F%.1f%n", X+najazd,feed);
			
			System.out.printf(Locale.CANADA,"N65 G3 X%.3f Y%.3f I%.3f J0.%n", X+((d/2)-(t/2)),Y,(((d/2)-(t/2))-najazd)/2);
			System.out.printf(Locale.CANADA,"N70 G3 X%.3f Y%.3f I-%.3f J0.%n", X-((d/2)-(t/2)),Y,((d/2)-(t/2)));
			System.out.printf(Locale.CANADA,"N75 G3 X%.3f Y%.3f I%.3f J0.%n", X+((d/2)-(t/2)),Y,((d/2)-(t/2)));
			System.out.printf(Locale.CANADA,"N80 G3 X%.3f Y%.3f I-%.3f J0.%n", X+najazd,Y,(((d/2)-(t/2))-najazd)/2);
			System.out.printf(Locale.CANADA,"N85 G1 X%.3f Y%.3f %n", X,Y);
			System.out.printf(Locale.CANADA,"N90 G1 G40 X%.3f Y%.3f %n", X-najazd,Y);
			System.out.printf(Locale.CANADA,"N95 G0 Z%d M9%n",Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N105 M5%n");
			System.out.printf(Locale.CANADA,"N110 M1%n");

		}
		
		else
		{
			
			int n=40;
			
			float przejscie = R -Z;
			if(przejscieNaGotowo)
				przejscie -=0.5;
			
			przejscie = przejscie/iloscPrzejsc;
			
			
			for(int i=1; i<= iloscPrzejsc;i++)
			{
				R = R - przejscie;
				if(i!=0)
					System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F50%n", n+=5,R);
				
				
				
				if(parent.getControls().isType(ControlTypes.SINUMERIC))
					System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f F%.1f%n", n+=5,X+najazd,feed);
				else System.out.printf(Locale.CANADA,"N%d G41 D%d G1 X%.3f Y%.3f F%.1f%n",n+=5,toolNumber ,X+najazd, Y, feed);
				
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n", n+=5,X+((d/2)-(t/2)),Y,(((d/2)-(t/2))-najazd)/2);
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I-%.3f J0.%n",n+=5, X-((d/2)-(t/2)),Y,((d/2)-(t/2)));
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5, X+((d/2)-(t/2)),Y,((d/2)-(t/2)));
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I-%.3f J0.%n",n+=5,X+najazd,Y,(((d/2)-(t/2))-najazd)/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Y%.3f %n",n+=5, X,Y);
				System.out.printf(Locale.CANADA,"N%d G1 G40 X%.3f Y%.3f %n",n+=5, X-najazd,Y);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Y%.3f %n",n+=5, X,Y);
		
		
			}
			if(this.przejscieNaGotowo)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F50%n",n+=5, Z);
		
				if(parent.getControls().isType(ControlTypes.FANUC))
					System.out.printf(Locale.CANADA,"N%d G41 D%d G1 X%.3f F%.1f%n", n+=5,toolNumber, X+najazd,feed);
				else 
					System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f F%.1f %n", n+=5, X+najazd,feed);
			
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n", n+=5, X+((d/2)-(t/2)),Y,(((d/2)-(t/2))-najazd)/2);
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I-%.3f J0.%n", n+=5, X-((d/2)-(t/2)),Y,((d/2)-(t/2)));
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n", n+=5, X+((d/2)-(t/2)),Y,((d/2)-(t/2)));
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I-%.3f J0.%n", n+=5, X+najazd,Y,(((d/2)-(t/2))-najazd)/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Y%.3f %n",n+=5, X,Y);
				System.out.printf(Locale.CANADA,"N%d G1 G40 X%.3f Y%.3f %n",n+=5, X-najazd,Y);
			}
			System.out.printf(Locale.CANADA,"N%d G0 Z%d%n",n+=5,Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N%d M5%n", n+=5);
			System.out.printf(Locale.CANADA,"N%d M1%n", n+=5);	

		}
		this.Cofnij.doClick();
		Wind.log.writeInfoLog("poglebienie - wykonano", Poglebienie.class.getSimpleName());
		parent.writelog("Wygenerowano kod dla pog³êbienia walcowego dla wspó³rzêdnych: X" + X + " Y" + Y + " \n\tSrednica poglebienia=" + srednicaOtworu + " Maksymalna mo¿liwa korekcja promieniowa R=" + najazd +"\n\tIloœæ przejœæ(nie licz¹c przejœcia na gotowo): "+ iloscPrzejsc+ "\n\tPrzejscie na gotowo: "+ (przejscieNaGotowo?" 0.5mm":"Nie"));
	}
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o==Oblicz)
		{
			if(getFieldValues())
			{
				generatePocketMilling(X, Y,srednicaOtworu, srednicaFreza, poczatekPoglebienia, dnoPoglebienia, obroty, feed);
				
				
			}
	
		}
		else if(o==podzielPrzejsciaCheck)
		{
			if(podzielPrzejsciaCheck.isSelected())
			{
				iloscPrzejscLabel.setEnabled(true);
				ilePrzejscSpinner.setEnabled(true);
				dodajPrzejscieNaGotowoLabel.setEnabled(true);
				dodajPrzejscieNaGotowoCheck.setEnabled(true);
				
			}
			else
			{
				iloscPrzejscLabel.setEnabled(false);
				ilePrzejscSpinner.setEnabled(false);
				dodajPrzejscieNaGotowoLabel.setEnabled(false);
				dodajPrzejscieNaGotowoCheck.setEnabled(false);
			}
			
		}
		else if(o==Cofnij)
		{
			parent.pogl=null;
			this.dispose();
			
		}
		
	}

}
