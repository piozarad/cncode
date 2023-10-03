package cncCodeGeneratingFunctions;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JTextField;

import BasicControls.Sterowanie;
import CordConverter.ControlTypes;
import CordConverter.Edytor;
import CordConverter.Function;
import CordConverter.Wind;

public class Spiral extends JFrame implements ActionListener
{
	 Edytor parent;
	 
	 Sterowanie sterowanie;
	
	JLabel wspolrzedneXLabel;
	JLabel wspolrzedneYLabel;
	JLabel rodzajLabel;		
	JLabel srednicaNarzedziaLabel;
	JLabel srednicaOtworuLabel;
	JLabel zWejsciaLabel;
	JLabel zDnaLabel;
	JLabel posowLabel;
	JLabel obrotyLabel;
	JLabel krokLabel;
	JLabel macroLabel;
	
	JTextField wspolrzedneXTxt;
	JTextField wspolrzedneYTxt;
	JTextField srednicaNarzedziaTxt;
	JTextField srednicaOtworuTxt;
	JTextField zWejsciaTxt;
	JTextField zDnaTxt;
	JTextField posowTxt;
	JTextField obrotyTxt;
	JTextField krokTxt;
	
	JButton oblicz;
	JButton cofnij;
	
	JComboBox<String> metodaBox;
	JCheckBox macroCheck;
	
	float safeRetraction;
	
	private static final Dimension BUTTON_SIZE = new Dimension(150,50);
	private static final Dimension TEXT_FIELD_SIZE = new Dimension(75,50);
	
	Integer spSpeed;
	
	Float x;
	Float y;
	Float start;
	Float bottom;
	Float toolDiameter;
	Float holeDiameter; 
	Float feed;
	Float step;
	
	GridBagLayout layout;
	
	static final String []METODY = {"Wjazd-okrag","Spiralnie","Spiralnie-promieniowo"};
	
	
	public Spiral(Edytor parent)
	{
		setPreferredSize(new Dimension(330,450));
		setTitle("Generuj spirale");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setLocation(500, 25);
		setResizable(false);
		
		this.parent = parent;
		this.safeRetraction = Wind.options.getSafeRetraction();
		this.sterowanie = parent.getControls();
		
		//exit on close
				this.addWindowListener(new java.awt.event.WindowAdapter() 
				{
					@Override
					public void windowClosing(WindowEvent e)
					{	
						cofnij.doClick();
					}		
				});
		
		
		
		// layout
		layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(5,5,5,5);
		border.weightx =0;
		border.weighty=0;
		border.fill =GridBagConstraints.HORIZONTAL;
		
		//combo box
		metodaBox = new JComboBox<>(METODY);
		metodaBox.setSelectedIndex(1);
		metodaBox.addActionListener(e->{
			if(metodaBox.getSelectedIndex()==0)
			{
				parent.spiral=null;
				this.dispose();
				parent.pogl= new Poglebienie(parent);
			}
			else if(metodaBox.getSelectedIndex()==2)
			{
				parent.spiral=null;
			this.dispose();
			parent.wklad= new Wkladka(parent);
				
			}
		});
		
		// labels
		 wspolrzedneXLabel =  new JLabel("Wspolrzedna x otworu");
		 wspolrzedneYLabel = new JLabel("Wspolrzedna y otworu ");
		 srednicaNarzedziaLabel = new JLabel("Srednica narzedzia ");
		 srednicaOtworuLabel = new JLabel("Srednica otworu ");
		 zWejsciaLabel= new JLabel("Z poczatkowe ");
		 zDnaLabel = new JLabel("Z dna otworu ");
		 krokLabel = new JLabel("Krok (ap)");
		 posowLabel = new JLabel("Posuw");
		 posowLabel.setToolTipText("Predkosc posuwu Vf w mm/min. Mozna podac posow na zab i liczbe zebow w celu obliczenia predkosci posuwu wg schematu fn=wartosc z=wartosc");
		 obrotyLabel = new JLabel("Obroty");
		 obrotyLabel.setToolTipText("Predkosc obrotowa wrzeciona. Mozna podac predkosc skrawania w celu obliczenia obrotow wg formatu: Vc=wartosc");
		 rodzajLabel = new JLabel("Metoda");
		 rodzajLabel.setToolTipText("Sposob generowania œciezki dla tej operacji");
		 macroLabel = new JLabel("Generuj jako makro");
		 
		// buttons 
		 oblicz = new JButton("Generuj");
		 oblicz.setToolTipText("Generuj bloki dla spirali na koñcu pola tekstowego");
		 oblicz.setPreferredSize(BUTTON_SIZE);
		 oblicz.addActionListener(this);
		 cofnij = new JButton("Cofnij");
		 cofnij.setToolTipText("Powrot do poprzedniego okna");
		 cofnij.setPreferredSize(BUTTON_SIZE);
		 cofnij.addActionListener(this);
		 
		 macroCheck = new JCheckBox();
		 macroCheck.addActionListener(this);
		 
		 border.gridx = 0;
		 border.gridy = 0;
		 add(rodzajLabel,border);
		 
		 border.gridy++;
		 add(wspolrzedneXLabel,border);
		 
		 border.gridy++;
		 add(wspolrzedneYLabel,border);
		 
		 border.gridy++;
		 add(srednicaNarzedziaLabel,border);
		 
		 border.gridy++;
		 add(srednicaOtworuLabel,border);
		 
		 border.gridy++;
		 add(zWejsciaLabel,border);
		 
		 border.gridy++;
		 add(zDnaLabel,border);
		 
		 border.gridy++;
		 add(krokLabel,border);
		 
		 border.gridy++;
		 add( posowLabel,border);
		 
		 border.gridy++;
		 add(obrotyLabel,border);
		 
		 border.gridy++;
		 add(macroLabel,border);
		 
		
		//txt
		 wspolrzedneXTxt = new JTextField("");
		 wspolrzedneXTxt.setPreferredSize(TEXT_FIELD_SIZE);
		 wspolrzedneYTxt = new JTextField("");
		 wspolrzedneYTxt.setPreferredSize(TEXT_FIELD_SIZE);
		 srednicaNarzedziaTxt = new JTextField("");
		 srednicaNarzedziaTxt.setPreferredSize(TEXT_FIELD_SIZE);
		 srednicaOtworuTxt = new JTextField("");
		 srednicaOtworuTxt .setPreferredSize(TEXT_FIELD_SIZE);
		 zWejsciaTxt = new JTextField("");
		 zWejsciaTxt.setPreferredSize(TEXT_FIELD_SIZE);
		 zDnaTxt = new JTextField("");
		 zDnaTxt.setPreferredSize(TEXT_FIELD_SIZE);
		 krokTxt = new JTextField("");
		 krokTxt.setPreferredSize(TEXT_FIELD_SIZE);
		 posowTxt = new JTextField("");
		 posowTxt.setPreferredSize(TEXT_FIELD_SIZE);
		 obrotyTxt = new JTextField("");
		 obrotyTxt.setPreferredSize(TEXT_FIELD_SIZE);
		
		 border.gridx= 1;
		 border.gridy=0; 
		 add(metodaBox,border);
		 
		 border.gridy++;
		 add(wspolrzedneXTxt,border);
		 
		 border.gridy++;
		 add(wspolrzedneYTxt,border);
		 
		 border.gridy++;
		 add(srednicaNarzedziaTxt,border);
		 
		 border.gridy++;
		 add(srednicaOtworuTxt,border);
		 
		 border.gridy++;
		 add(zWejsciaTxt,border);
		 
		 border.gridy++;
		 add(zDnaTxt,border);
		 
		 border.gridy++;
		 add(krokTxt,border);
		 
		 border.gridy++;
		 add(posowTxt,border);
		 
		 border.gridy++;
		 add(obrotyTxt,border);
		 
		 border.gridy++;
		 add(macroCheck,border);
		 
		 border.gridx =0;
		 border.gridy++;
		 add(oblicz,border);
		 
		 border.weightx=0;
		 border.gridx =1;
		 add(cofnij,border);
		 
		 this.pack();
		
		 
	}
	
	
	private void generateInnerSpiralInterpolation(float x, float y, float start,float bottom, float toolDiameter, float holeDiameter, float step, int sp_speed, float feed)
	{
		
		if(parent.getControls().equals(null))
		{
			JOptionPane.showMessageDialog(this, "Wybierz sterowanie w menu opcje", "Blad", JOptionPane.NO_OPTION);
			
		}
		else{
		if(toolDiameter< holeDiameter)
		{
			int toolNumber = this.parent.getToolBar().getToolNumber();
			float rotationB = this.parent.getToolBar().getRotation();
			String base = this.parent.getToolBar().getBase();
			float rightX = x-toolDiameter/2 +holeDiameter/2;
			float i = (rightX - x)/2;
					
			PrintStream printStream = new PrintStream(parent.txt);
			System.setOut(printStream);	
			System.setErr(printStream);
			
			
			
			System.out.printf(Locale.CANADA,"(T%d GLOWICA FI%.1f)%n",toolNumber,toolDiameter);
		
			
			
			//przygotowanie bazy i korekcji narzedzia
			sterowanie.przygotowanieUkladuINarzedzia(5, toolNumber, safeRetraction,rotationB,base);
				
			System.out.println("N16 G0 X" + x + " Y"+ y + " S" + sp_speed + " M3");
			System.out.println("N18 G1 Z"+ start + " F10000. M8");
			if(parent.getControls().isType(ControlTypes.OKUMA))
				System.out.println("N20 G1 G41 X"+(x+1) +" F2000.");
			else
				System.out.println("N20 G1 G41 "+  "D" + toolNumber+" X"+ (x+1) +" F2000.");
			System.out.printf(Locale.CANADA,"N25 G3 X%.3f Y%.3f I%.3f J0. F%.1f%n",rightX,y,(i-0.5),feed);
			
	
			
			int n = 25;
			
			while(start >=bottom)
			{
				n+=5;
				System.out.printf(Locale.CANADA,"N%d G3 X%.2f Y%.2f Z%.2f I%.2f J0. %n", n, rightX, y, start, -(rightX-x) );
				start -= step;

				
			}
			if(start!=bottom)
			{
				System.out.printf(Locale.CANADA,"N%d G3 X%.2f Y%.2f Z%.2f I%.2f J0. %n", n, rightX, y, bottom, -(rightX-x) );
	
			}
			System.out.printf(Locale.CANADA,"N%d G3 X%.2f Y%.2f I%.2f J0. %n", n, rightX, y, -(rightX-x) );
			
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I-%.3f J0. %n",(n+5),x,y,i);
			System.out.printf(Locale.CANADA,"N%d G0 Z%.1f M9%n",n+10,safeRetraction);
			System.out.println("N"+ (n+20) + " G0 G40 X" + (x-1) + " Y" +(y-1));
			System.out.println("N"+ (n+20) + " M5");
			System.out.println("N"+ (n+25) + " M1");
			
		}
		
		}
		
		
	}

	void generateOuterSpiralInterpolation(float x, float y, float start,float bottom, float toolDiameter, float holeDiameter, float step, int sp_speed, float feed)
	{
		
		if(parent.getControls().equals(null))
		{
			JOptionPane.showMessageDialog(this, "Wybierz sterowanie w menu opcje", "Blad", JOptionPane.NO_OPTION);
			
		}
		else if(step==0)
		{
			JOptionPane.showMessageDialog(this, "Skok nie moze byc rowny 0", "Blad", JOptionPane.NO_OPTION);
			
		}
		else{
			float rotationB = this.parent.getToolBar().getRotation();
			int toolNumber = this.parent.getToolBar().getToolNumber();
			String base = this.parent.getToolBar().getBase();
			float leftX = x -(toolDiameter/2 + holeDiameter/2);
			float i = toolDiameter/2 + holeDiameter/2;
					
			PrintStream printStream = new PrintStream(parent.txt);
			System.setOut(printStream);	
			System.setErr(printStream);
			
			System.out.printf(Locale.CANADA,"(T%d GLOWICA FI%.1f)%n",toolNumber,toolDiameter);
			
			//przygotowanie bazy i korekcji narzedzia
			sterowanie.przygotowanieUkladuINarzedzia(5, toolNumber, safeRetraction,rotationB,base);		
			
			System.out.println("N10 G0 X" + (leftX-10 -1 ) + " Y"+ y + " S" + sp_speed + " M3");
			System.out.println("N15 G1 Z"+ start + " F10000. M8");
			if(parent.getControls().isType(ControlTypes.OKUMA))
				System.out.println("N20 G1 G41 " + toolNumber+" X"+ (leftX-10) +" F2000.");
			else
				System.out.println("N20 G1 G41 "+ "D" + toolNumber+" X"+ (leftX-10) +" F2000.");
			
			System.out.println("N25 G3 X"+ leftX + " Y"+ y + " I 5.0 J0. F"+feed +"\n");
			
			int n = 25;
			
			while(start >=bottom)
			{
				n+=5;
				System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f Z%.2f I%.2f J0. %n", n, leftX, y, start, i );
				start -= step;
			
			}
			System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f Z%.2f I%.2f J0. %n", n, leftX, y, bottom, i );
			System.out.printf(Locale.CANADA,"(PRZEJSCIE WYROWNUJACE CZOLO)%n");
			System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f Z%.2f I%.2f J0. %n", n+=5, leftX, y, bottom, i );
			
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I-5 J0.%n",n+=5,leftX-10,y);
			System.out.printf(Locale.CANADA,"N%d G1 G40 X%.3f Y%.3f%n",n+=5,leftX-10-2,(y-1));
			System.out.printf(Locale.CANADA,"N%d G0 Z%.1f M9%n",n+=5,safeRetraction);
			System.out.printf("N%d M5%n",n+=5);
			System.out.printf("N%d M1%n",n+=5);
			
		}
	}
	
	
	
	
	public Integer tryToGetInteger(JTextField t)
	{
		int result;
		try
		{
			result=Integer.parseInt(t.getText());
			return result;
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Nieprawidlowy format danych wejœciowych", "Blad", JOptionPane.NO_OPTION);
			return null;
		}
	}

	public Float tryToGetFloat(JTextField t)
	{
		Float result;
		
		try
		{
			result = Float.parseFloat(t.getText());
			return result;
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Nieprawidlowy format danych wejsciowych", "Blad", JOptionPane.NO_OPTION);
			return null; 
			
		}
	}
	


	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o = arg0.getSource();
		
		if(o==oblicz)
		{
			x = tryToGetFloat(this.wspolrzedneXTxt);
			y = tryToGetFloat(this.wspolrzedneYTxt);
			bottom = tryToGetFloat(this.zDnaTxt);
			start = tryToGetFloat(this.zWejsciaTxt);
			holeDiameter = tryToGetFloat(this.srednicaOtworuTxt);
			step  = tryToGetFloat(this.krokTxt);
			toolDiameter = tryToGetFloat(this.srednicaNarzedziaTxt);

	
			
			
			if(((Function.covertVcToN(this.obrotyTxt, toolDiameter))==0))	
			{		
				spSpeed = tryToGetInteger(this.obrotyTxt);
				
				
				if(Function.covertFnToVf(this.posowTxt, spSpeed)==0)
				{
				feed = tryToGetFloat(this.posowTxt);
			
			
				if(x!= null && y!= null && start!=null && bottom!= null && toolDiameter != null && holeDiameter!=null && step!=null && spSpeed!=null && feed!=null)
				{
				if(holeDiameter<toolDiameter )
					JOptionPane.showMessageDialog(this, "Srednica narzedzia musi byc mniejsza od otworu!", "Blad", JOptionPane.NO_OPTION);
				else if(start<bottom)
					JOptionPane.showMessageDialog(this, "Wspolrzedna dna otworu musi byc mniejsza od wspolrzednej Z poczatkowej spirali", "Blad", JOptionPane.NO_OPTION);
				else if(spSpeed>10000)
				{
					JOptionPane.showMessageDialog(this, "Nie za wysokie te obroty?", "Bum", JOptionPane.NO_OPTION);
				}
				else if(feed>15000 || feed <5)
				{
					JOptionPane.showMessageDialog(this, "Coœ podejrzany ten posuw, wez to sprawdz lepiej", "???", JOptionPane.NO_OPTION);
				}
				else
				{
					if(!this.macroCheck.isSelected())
					{
						generateInnerSpiralInterpolation(x, y, start, bottom,  toolDiameter, holeDiameter,step, spSpeed, feed);
						parent.writelog("Wygenerowano interpolacje spiralna dla otworu o wspolrzednych X=" +x + " Y=" + y + " Zpocz=" +start+" Zkonc="+ bottom+".\n\tSrednica frezowanego otworu: "+ holeDiameter +"mm "+ " Srednica wykorzystanego narzedzia:"+toolDiameter + "mm"+"\n\tAp="+step+"mm"+"\n\tS"+spSpeed+" 1/s"+" F"+feed+"mm/min");
						Wind.log.writeInfoLog("Spiral inner - wygenerowano", Spiral.class.getSimpleName());
					}
					
					else
					{
						
						generateInnerMacroSpiralInterpolation(x, y, start, bottom,  toolDiameter, holeDiameter,step, spSpeed, feed);
						parent.writelog("Wygenerowano makro do interpolacji spiralnej dla otworu o wspolrzednych X=" +x + " Y=" + y + " Zpocz=" +start+" Zkonc="+ bottom+".\n\tSrednica frezowanego otworu: "+ holeDiameter +"mm "+ " Srednica wykorzystanego narzedzia:"+toolDiameter + "mm"+"\n\tAp="+step+"mm"+"\n\tS"+spSpeed+" 1/s"+" F"+feed+"mm/min");
						Wind.log.writeInfoLog("Spiral inner macro - wygenerowano", Spiral.class.getSimpleName());
					}
					parent.spiral=null;
					this.dispose();
				}
				}
				}
			}
		}
		else if(o==macroCheck)
		{
			Sterowanie s = parent.getControls();
			if(!(s.isType(ControlTypes.FANUC) || s.isType(ControlTypes.HITACHI) || s.isType(ControlTypes.OKUMA)))
			{
				JOptionPane.showMessageDialog(this, "Opcja dostepna tylko na sterowaniu Fanuc lub Okuma", "Niedostepna opcja", JOptionPane.OK_OPTION);
				this.macroCheck.setSelected(false);
				this.macroCheck.setEnabled(false);
			}
		}
		
		else if(o==cofnij)
			{
				parent.spiral=null;
				this.dispose();
			}
		else
		{
			JOptionPane.showMessageDialog(this, "Blad");
			
		}
			
	}

	private void generateInnerMacroSpiralInterpolation(float x, float y, float start,float bottom, float toolDiameter, float holeDiameter, float step, int sp_speed, float feed)
	{
		
		if(parent.getControls().equals(null))
		{
			JOptionPane.showMessageDialog(this, "Wybierz sterowanie w menu opcje", "Blad", JOptionPane.NO_OPTION);
			
		}
		else{
		if(toolDiameter< holeDiameter)
		{
			int toolNumber = this.parent.getToolBar().getToolNumber();
			float rotationB = this.parent.getToolBar().getRotation();
			float rightX = x-toolDiameter/2 +holeDiameter/2;
			String base = this.parent.getToolBar().getBase();
			
			PrintStream printStream = new PrintStream(parent.txt);
			System.setOut(printStream);	
			System.setErr(printStream);
			

			System.out.printf(Locale.CANADA,"(T%d GLOWICA FI%.1f)%n",toolNumber,toolDiameter);
		
			//przygotowanie bazy i korekcji narzedzia
			sterowanie.przygotowanieUkladuINarzedzia(5, toolNumber, safeRetraction,rotationB,base);
			int n = 25;
			
			if(sterowanie.isType(ControlTypes.FANUC) || sterowanie.isType(ControlTypes.HITACHI))
			{
				
			//PREPARING VARIABLES
			System.out.printf(Locale.CANADA,"%n(POCZATEK SKRAWANIA OS Z)%n");
			System.out.printf(Locale.CANADA,"#1=%.2f %n",start);
			System.out.printf(Locale.CANADA,"%n(KONIEC OS Z)%n");
			System.out.printf(Locale.CANADA,"#2=%.2f %n",bottom);
			System.out.printf(Locale.CANADA,"%n(AP)%n");
			System.out.printf(Locale.CANADA,"#3=%.2f %n%n",step);
			
			System.out.printf(Locale.CANADA,"S%d M3 %n",sp_speed);
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f %n", n, x-2,y );
			n+=5;
			System.out.printf(Locale.CANADA,"N%d G0 Z%.2f %n", n, start+5 );
			n+=5;
			System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F10000. %n", n, start );
			n+=5;
			System.out.printf(Locale.CANADA,"N%d G41 X%.3f Y%.3f %n", n, x,y );
			n+=5;
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.2f J0. F%.1f %n", n, rightX, y,  (rightX-x)/2 , feed);
			n+=5;
			
			System.out.printf(Locale.CANADA,"WHILE[#1 GT #2] DO1%n");
			n+=5;
			System.out.printf(Locale.CANADA,"G3 X%.3f Y%.3f Z[#1] I%.2f J0. %n",rightX, y , -(rightX-x));
			n+=5;
			System.out.printf(Locale.CANADA,"#1=#1-#3%n");
			n+=5;
			System.out.printf(Locale.CANADA,"END1 %n");
			
			}
			
			if(sterowanie.isType(ControlTypes.OKUMA))
			{
				//PREPARING VARIABLES
				System.out.printf(Locale.CANADA,"%n(POCZATEK SKRAWANIA OS Z)%n");
				System.out.printf(Locale.CANADA,"LV1=%.2f %n",start);
				System.out.printf(Locale.CANADA,"%n(KONIEC OS Z)%n");
				System.out.printf(Locale.CANADA,"LV2=%.2f %n",bottom);
				System.out.printf(Locale.CANADA,"%n(AP)%n");
				System.out.printf(Locale.CANADA,"LV3=%.2f %n%n",step);
				
				System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f %n", n, x-1,y );
				n+=5;
				System.out.printf(Locale.CANADA,"N%d G0 Z%.2f %n", n, start+5 );
				n+=5;
				System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F10000. %n", n, start );
				n+=5;
				System.out.printf(Locale.CANADA,"N%d G41 X%.3f Y%.3f %n", n, x,y );
				n+=5;
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.2f J0. F%.1f %n", n, rightX, y,  (rightX-x)/2 , feed);
				n+=5;
				
				System.out.printf(Locale.CANADA,"NENA %n");
				n+=5;
				System.out.printf(Locale.CANADA,"G3 X%.3f Y%.3f Z[LV1] I%.2f J0. %n",rightX, y , -(rightX-x));
				n+=5;
				System.out.printf(Locale.CANADA,"LV1=[LV1-LV3]%n");
				n+=5;
				System.out.printf(Locale.CANADA,"IF[LV1 GT LV2] GOTO NENA%n");
				
				
			}
			
			n+=5;
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f Z[#2] I%.2f J0.%n", n, rightX, y , -(rightX-x) );
			n+=5;
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.2f J0.%n", n, rightX, y , -(rightX-x) );
			n+=5;
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.2f J0. %n", n, x, y,  -(rightX-x)/2  );
			n+=5;
			
			
			
			
			System.out.printf(Locale.CANADA,"N%d G0 Z%.1f M9%n",n+10,safeRetraction);
			n+=5;
			System.out.println("N"+ n + " G0 G40 X" + (x-1) + " Y" +(y-1));
			n+=5;
			System.out.println("N"+ n+ " M5");
			n+=5;
			System.out.println("N"+ n + " M1");
			
		}
		
		}
		
	}
	
}
