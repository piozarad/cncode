package cncCodeGeneratingFunctions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import BasicControls.Sterowanie;
import CordConverter.ControlTypes;
import CordConverter.Edytor;
import CordConverter.Function;
import CordConverter.Point;
import CordConverter.Wind;
import DrawFunction.DrawCordinateSystem;

public class Faza extends JFrame implements ActionListener {

	
	//parent
	private final Edytor parent;
	
	//sterowanie
	Sterowanie sterowanie;
	
	//labels
	private JLabel obrotyLabel;
	private JLabel posowLabel;
	private JLabel zPlaszczyznyLabel;
	private JLabel glebokoscFazyLabel;
	private JLabel srednicaNarzedziaLabel;
	private JLabel srednicaOtworuLabel;
	private JLabel wspolrzednaXLabel;
	private JLabel wspolrzednaYLabel;
	
	//JTextField
	private JTextField wspolrzednaXField;
	private JTextField wspolrzednaYField;
	private JTextField obrotyField;
	private JTextField posowField;
	private JTextField zPlaszczyznyField;
	private JTextField glebokoscFazyField;
	private JTextField srednicaNarzedziaField;
	private JTextField srednicaOtworuField;
	
	//JButtons
	private JButton oblicz;
	private JButton cofnij;
	
	//jednostki
	private JLabel obrotyLabelJednostka;
	private JLabel posowLabelJednostka;
	private JLabel zPlaszczyznyJednostka;
	private JLabel glebokoscFazyJednostka;
	private JLabel srednicaNarzedziaJednostka;
	private JLabel srednicaOtworuJednostka;
	
	
	
	//zmienne
	private float wspolrzednaX;
	private float wspolrzednaY;
	private float zPlaszczyzny;
	private float glebokoscFazy;
	private float srednicaNarzedzia;
	private float srednicaOtworu;
	private int obroty;
	private float posuw;
	
	
	
	
	
	
	
	
	
	private int toolNumber;
	private float safeRetraction;
	private float rotation;
	private String base;
	
	private static final Dimension DEFDIM = new Dimension(75,20);
	
	
	public Faza(Edytor parent)
	{
		this.parent=parent;
		this.sterowanie=parent.getControls();
		this.safeRetraction=Wind.options.getSafeRetraction();
		this.toolNumber = parent.getToolBar().getToolNumber();
		this.rotation = parent.getToolBar().getRotation();
		this.base = parent.getToolBar().getBase();
		
		setTitle("Fazowanie otworu");
		setSize(new Dimension(280,300));
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		
		this.addWindowListener(new java.awt.event.WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent e)
					{						
						cofnij.doClick();				
					}
				});
		
		
		//layout
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);	
		GridBagConstraints border = new GridBagConstraints();
		border.weightx=0.1;
		border.weighty=0.1;
		border.insets = new Insets(10,10,10,10);
		border.fill = GridBagConstraints.BOTH;

		//x0 y0 wspolrzedna x label
		wspolrzednaXLabel = new JLabel("Wpolrzedna X");
		border.gridx=0;
		border.gridy=0;
		add(wspolrzednaXLabel,border);
		
		
		
		//x0 y1 wpolrzedna y label
		wspolrzednaYLabel = new JLabel("Wpolrzedna Y");
		border.gridy=1;
		add(wspolrzednaYLabel,border);
		
		
		//x0 y2	 srednica narzedzia label
		border.gridy=2;
		srednicaNarzedziaLabel = new JLabel("Srednica narzedzia");
		srednicaNarzedziaLabel.setToolTipText("Srednica narzedzia zmierzona na ostrzu bioracym udzial w skrawaniu. Dlugosc narzedzia nalezy zmierzyc w tym samym punkcie");
		add(srednicaNarzedziaLabel,border);
		
		//x0 y3 srednica otworu label
		border.gridy=3;
		srednicaOtworuLabel = new JLabel("Srednica otworu d");
		srednicaOtworuLabel.setToolTipText("Srednica surowego otworu do fazowania");
		add(srednicaOtworuLabel,border);
		
		//x0 y4 glebokosc fazy label
		border.gridy=4;
		glebokoscFazyLabel = new JLabel("Glebokosc fazy z");
		glebokoscFazyLabel.setToolTipText("Glebokosc fazu podana w mm mierzona rownolegle do osi otworu");
		add(glebokoscFazyLabel,border);
		
		//x0 y5 wspol z otowru label
		border.gridy=5;
		zPlaszczyznyLabel = new JLabel("Wspolrzedna Z poczatku otworu");
		zPlaszczyznyLabel.setToolTipText("Wspolrzedna Z plaszczyzny w ktorej wywiercony jest otwor przeznaczony do fazowania");
		add(zPlaszczyznyLabel,border);
		
		//x0 y6 obroty label
		border.gridy=6;
		obrotyLabel = new JLabel("Obroty");
		obrotyLabel.setToolTipText("Predkosc obrotowa wrzeciona. Mozna podac predkosc skrawania w celu obliczenia obrotow wg formatu: Vc=wartosc");
		add(obrotyLabel,border);
		
		//x0 y7 posow label
		border.gridy=7;
		posowLabel = new JLabel("Posuw");
		posowLabel.setToolTipText("Predkosc posuwu Vf w mm/min. Mozna podac posow na zab i liczbe zebow w celu obliczenia predkosci posuwu wg schematu fn=wartosc z=wartosc");
		add(posowLabel,border);
		
		
		//x0 y8 oblicz
		border.gridy=8;
		oblicz = new JButton("Oblicz");
		oblicz.addActionListener(this);
		add(oblicz,border);
		
		
		
		//x1 y0 wspolrzedna x field
		border.gridx=1;
		border.gridy=0;
		wspolrzednaXField = new JTextField("");
		wspolrzednaXField.setPreferredSize(DEFDIM);
		add(wspolrzednaXField,border);
		
		//x1 y1 wspolrzedna y field
		border.gridy=1;
		wspolrzednaYField = new JTextField("");
		wspolrzednaYField.setPreferredSize(DEFDIM);
		add(wspolrzednaYField,border);

		
		//x1 y2 srednica narzedzia txt
		border.gridx=1;
		border.gridy=2;
		srednicaNarzedziaField = new JTextField("");
		srednicaNarzedziaField.setPreferredSize(DEFDIM);
		add(srednicaNarzedziaField,border);
		
		//x1 y3 srednica fazowanego otworu txt
		border.gridy=3;
		srednicaOtworuField = new JTextField("");
		srednicaOtworuField.setPreferredSize(DEFDIM);
		add(srednicaOtworuField,border);
		
		//x1 y4 glebokosc fazy txt
		border.gridy=4;
		glebokoscFazyField = new JTextField("");
		glebokoscFazyField.setPreferredSize(DEFDIM);
		add(glebokoscFazyField,border);
		
		//x1 y5 z plaszczyzny txt
		border.gridy=5;
		zPlaszczyznyField = new JTextField("");
		zPlaszczyznyField.setPreferredSize(DEFDIM);
		add(zPlaszczyznyField,border);
		
		//x1 y6 obroty
		border.gridy=6;
		obrotyField = new JTextField("");
		obrotyField.setPreferredSize(DEFDIM);
		add(obrotyField,border);
		
		//x1 y7
		border.gridy=7;
		posowField = new JTextField("");
		posowField.setPreferredSize(DEFDIM);
		add(posowField,border);
		
		//x1 y8 cofnij
		border.gridy=8;
		cofnij = new JButton("Cofnij");
		cofnij.addActionListener(this);
		add(cofnij, border);
		
		
		//x2 jednostki
		border.gridx=2;
		
		
		border.gridy=2;
		srednicaNarzedziaJednostka = new JLabel("mm");
		add(srednicaNarzedziaJednostka,border);
		
		border.gridy=3;
		srednicaOtworuJednostka = new JLabel("mm");
		add(srednicaOtworuJednostka,border);
		
		border.gridy=4;
		zPlaszczyznyJednostka = new JLabel("mm");
		add(zPlaszczyznyJednostka,border);
		
		border.gridy=5;
		glebokoscFazyJednostka = new JLabel("mm");
		add(glebokoscFazyJednostka,border);
		
		border.gridy=6;
		obrotyLabelJednostka = new JLabel("1/s");
		add(obrotyLabelJednostka,border);
		
		border.gridy=7;
		posowLabelJednostka = new JLabel("mm/min");
		add(posowLabelJednostka,border);
		
		
		
		border.weightx=1;
		border.weighty=1;
		border.gridheight=8;
		border.gridwidth=2;
		
		
		
		//x3 y0 schemat
		
	border.gridx=3;
	border.gridy=0;
	add(new canv(),border);
	repaint();
	pack();
	}
	
	
	class canv extends JComponent
	{
		Dimension windowSize =new Dimension(240,280);
		
		canv()
		{
			setVisible(true);
			setPreferredSize(windowSize);
			
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			//uklad wspolrzednych
			new DrawCordinateSystem(new Point(10f,220f)).draw(g);
			
			//zew okr¹g
			g.drawArc(4, 14, 80, 80, 0, 360);
			//wew
			g.drawArc(14, 24, 60, 60, 0, 360);
			
			//osie pionowa
			for(int i=1;i<90; )
			{
				g.drawLine(44, i, 44,i+5);
				i+=10;
			}
			g.drawString("Y", 48, 10);
			
			//oœ pozioma
			for(int i=1;i<90;)
			{			
				g.drawLine(i, 54, i+5,54);
				i+=10;
	
			}
			g.drawString("X", 90, 54);
			
			//podpisany wew okrag
			g.drawLine(14, 125, 74, 125);
			g.drawLine(74, 123, 74, 127);
			g.drawLine(14, 123, 14, 127);
			g.drawString("d", 42, 123);
			
			
			//rzut z boku
			
			//od dolu
			for(int i=120;i<210;)
			{
				g.drawLine(i, 110, i+4, 110);
				i+=10;
				
			}
			g.drawLine(180, 84, 120, 84);
			g.drawLine(200, 110, 200, 94);
			g.drawLine(200, 94, 180, 84);
			
			//linie wymiarowe  glebokosc fazy
			g.drawLine(200, 140, 180, 140);
			g.drawLine(200, 138, 200, 142);
			g.drawLine(180, 138, 180, 142);
			g.drawString("z", 188, 125);
			
			
			Polygon p = new Polygon(new int[]{120,120,180,200,200}, new int[]{110,84,84,94,110},5);
			
			g.fillPolygon(p);
			//oœ symetrii Y
			for (int i=110;i<210;)
			{
				g.drawLine(i, 54, i+4, 54);
				i+=10;
			}
			
			g.drawLine(120, 24, 180, 24);
			g.drawLine(200, 14, 180, 24);
			g.drawLine(200, 1, 200, 14);
			
			p =  new Polygon(new int[]{120,200,200,180,120}, new int[]{10,10,14,24,24},5);
			
			g.fillPolygon(p);
			//pionowa lewa linia przerywana
			for (int i=-5;i<120;)
			{
				g.drawLine(120, i, 120, i+4);
				i+=10;
			}		
		}
	}

	private void generuj()
	{
		PrintStream output = new PrintStream(parent.txt);
		System.setOut(output);
		System.setErr(output);
		
		float najazd = (srednicaOtworu - srednicaNarzedzia)/4;
		

		System.out.printf(Locale.CANADA,"(T%d FAZOWNIK POMIAR NA SREDNICY FI%.1f)%n", toolNumber,srednicaNarzedzia);

		//przygotowanie baz o korekcji
		sterowanie.przygotowanieUkladuINarzedzia(5, toolNumber, safeRetraction,this.rotation,base);
		
		System.out.printf(Locale.CANADA,"N35 S%d M3%n",obroty);
		System.out.printf(Locale.CANADA,"N40 G0 X%.3f Y%.3f M8%n",wspolrzednaX,wspolrzednaY);
		System.out.printf(Locale.CANADA,"N45 G0 Z%.3f%n",zPlaszczyzny+100);
		
		if(parent.getControls().isType(ControlTypes.SINUMERIC))
			System.out.printf(Locale.CANADA,"N50 G0 Z%.3f%n",zPlaszczyzny);
		else
			System.out.printf(Locale.CANADA,"N50 G1 Z%.3f F10000.%n",zPlaszczyzny-glebokoscFazy);
		

			if(parent.getControls().isType(ControlTypes.FANUC))
				System.out.printf(Locale.CANADA,"N55 G41 D%d G1 X%.3f F%.1f%n", toolNumber, wspolrzednaX+najazd,posuw);
				else
					System.out.printf(Locale.CANADA,"N55 G41 G1 X%.3f F%.1f%n", wspolrzednaX+najazd,posuw);
			
			System.out.printf(Locale.CANADA,"N60 G3 X%.3f Y%.3f I%.3f J0.%n", wspolrzednaX+najazd*2,wspolrzednaY,najazd/2);
			System.out.printf(Locale.CANADA,"N65 G3 X%.3f Y%.3f I-%.3f J0.%n", wspolrzednaX-najazd*2,wspolrzednaY,najazd*2);
			System.out.printf(Locale.CANADA,"N70 G3 X%.3f Y%.3f I%.3f J0.%n", wspolrzednaX+najazd*2,wspolrzednaY,najazd*2);
			System.out.printf(Locale.CANADA,"N75 G3 X%.3f Y%.3f I-%.3f J0.%n", wspolrzednaX+najazd,wspolrzednaY,najazd/2);
			System.out.printf(Locale.CANADA,"N80 G40 G1 X%.3f Y%.3f %n", wspolrzednaX,wspolrzednaY);
			System.out.printf(Locale.CANADA,"N85 G0 Z%d M9%n",Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N90 M5%n");
			System.out.printf(Locale.CANADA,"N95 M1%n");

			parent.writelog("Wygenerowano kod dla fazowania otworu o Srednicy " + String.format(Locale.CANADA,"%.2f", this.srednicaOtworu) +"mm"
			+ "\n\t glebokosc fazy: " + String.format(Locale.CANADA,"%.1f", glebokoscFazy)+ "mm"
			+ "\n\t pomiar narzedzia na Srednicy: " + String.format(Locale.CANADA,"%.2f", this.srednicaNarzedzia) +"mm"
					);
	}
	



	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		
		
		if(o==oblicz)
		{
			if(isOk())
			{
				generuj();
				cofnij.doClick();
			}
			
			
		}
		else if(o==cofnij)
		{
			this.parent.F.dispose();
			this.parent.F=null;
			
			
		}

	}
	
	
	private boolean isOk()
	{

		
		try 
		{
			this.wspolrzednaX=Float.parseFloat(this.wspolrzednaXField.getText());
				
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana wspolrzedna X");
			return false;
		}
		
		try 
		{
			this.wspolrzednaY=Float.parseFloat(this.wspolrzednaYField.getText());
				
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana wspolrzedna Y");
			return false;
		}
		
		try 
		{
			this.srednicaNarzedzia=Float.parseFloat(this.srednicaNarzedziaField.getText());
			
			if(srednicaNarzedzia<1)
			{
				JOptionPane.showMessageDialog(this, "Srednica narzedzia podejrzanie mala");
				return false;
			}
				
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana srednica narzedzia");
			return false;
		}
		
		try 
		{
			this.srednicaOtworu=Float.parseFloat(this.srednicaOtworuField.getText());
			
			if(srednicaOtworu<1)
			{
				JOptionPane.showMessageDialog(this, "srednica otworu podejrzanie mala");
				return false;
			}		
				
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana srednica otworu");
			return false;
		}
		
		
		
		
		
		try 
		{
			this.glebokoscFazy=Float.parseFloat(this.glebokoscFazyField.getText());
				
			glebokoscFazy = Math.abs(glebokoscFazy);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana glebokosc fazy");
			return false;
		}
		
		if(Function.covertVcToN(this.obrotyField, this.srednicaNarzedzia)!=0)
			return false;
		
		try 
		{
			this.obroty=Integer.parseInt(this.obrotyField.getText());
			obroty=Math.abs(obroty);
			
			if(obroty>6000)
			{
				JOptionPane.showMessageDialog(this, "Zbyt wysokie obroty");
				return false;
			}
			
			
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowane obroty wrzeciona");
			return false;
		}
		
		
		if(Function.covertFnToVf(this.posowField, obroty)!=0)
			return false;
		try 
		{
			this.posuw=Float.parseFloat(this.posowField.getText());
			posuw=Math.abs(posuw);
			
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowany posuw");
			return false;
		}
		
		try 
		{
			this.zPlaszczyzny=Float.parseFloat(this.zPlaszczyznyField.getText());
			posuw=Math.abs(posuw);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowany posuw");
			return false;
		}
		
		
		if(this.srednicaNarzedzia +1 >this.srednicaOtworu)
		{
			JOptionPane.showMessageDialog(this, "Zbyt male narzedzie do tego otworu");
			return false;
		}
		
		
		return true;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
