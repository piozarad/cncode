package CordConverter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import BasicControls.SterowanieSinumeric;

public class Wkladka extends JFrame implements ActionListener {

	
	//sterowanie
	Sterowanie sterowanie;
	
	//labels
	private JLabel diameterLabel;
	private JLabel xLabel;
	private JLabel yLabel;
	private JLabel toolDiameterLabel;
	private JLabel startLabel;
	private JLabel endLabel;
	private JLabel speedLabel;
	private JLabel feedLabel;
	private JLabel aeLabel;
	private JLabel metodaLabel;

	
	
	//txt
	private JTextField diameterTxt;
	private JTextField xTxt;
	private JTextField yTxt;
	private JTextField toolDiameterTxt;
	private JTextField startTxt;
	private JTextField endTxt;
	private JTextField speedTxt;
	private JTextField feedTxt;
	private JTextField aeTxt;
	
	//units
	private JLabel diameterUnit;
	private JLabel toolDiameterUnit;
	private JLabel speedUnit;
	private JLabel feedUnit;
	private JLabel aeUnit;
	

	//buttons
	private JButton calculate;
	private JButton cancel;
	
	
	private JComboBox<String> metodaBox;

	private float diameter;
	private float toolDiameter;
	private int speed;
	private float feed;
	private float start;
	private float end;
	private float x;
	private float y;
	private float ae;
	
	
	//rys
	private canv c;
	
	static final String []METODY = {"Wjazd-okrag","Spiralnie","Spiralnie-promieniowo"};
	
	private float safeRetract;
	private int toolNumber;
	private String base;
	private float rotationB;
	
	
	private final Edytor parent;
	
	public Wkladka(Edytor parent)
	{
		this.parent=parent;
		setSize(540,400);
		setTitle("Rozfrezowanie okraglej wkladki");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
		
		this.toolNumber = this.parent.getToolBar().getToolNumber();
		this.rotationB = this.parent.getToolBar().getRotation();
		this.base = this.parent.getToolBar().getBase();
		
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		
		//exit on close
				this.addWindowListener(new java.awt.event.WindowAdapter() 
				{
					@Override
					public void windowClosing(WindowEvent e)
					{
						
						cancel.doClick();
					
					}		
				});
		
		//layout
		GridBagConstraints border = new GridBagConstraints();
		border.insets =new Insets(15,15,5,15);
		border.weightx=0;
		border.weighty=0;
	
		border.fill=GridBagConstraints.BOTH;
		
		
		//labels
		
		//metoda
		
		
		
		
		//x0 y0 
		border.gridx=0;
		border.gridy=0;
		metodaLabel = new JLabel("Metoda ");
		metodaLabel.setToolTipText("Spos�b generowania �cie�ki dla tej operacji");
		add(metodaLabel,border);
		
		border.gridx=0;
		border.gridy++;
		xLabel = new JLabel("Wsp�rz�dna x");
		xLabel.setToolTipText("Wsp�rz�dna x �rodka frezowanej wk�adki");
		add(xLabel,border);
		
		// x0 y1   y cordinate
		border.gridx=0;
		border.gridy++;
		yLabel = new JLabel("Wsp�rz�dna y");
		yLabel.setToolTipText("Wsp�rz�dna y �rodka frezowanej wk�adki");
		add(yLabel,border);
		
		//x0 y2 Diameter
		border.gridx=0;
		border.gridy++;
		diameterLabel = new JLabel("�rednica wk�adki d"); 
		diameterLabel.setToolTipText("No to ta �rednica wk�adki co j� chcesz rozfrezowa�, czego nie rozumiesz?");
		add(diameterLabel,border);
		
		
		//x0 y3  start Z Label
		border.gridx=0;
		border.gridy++;
		startLabel = new JLabel("Z pocz�tkowe");
		startLabel.setToolTipText("Z do kt�rego podje�d�a narz�dzie przed rozpocz�ciem pracy. Wsp�rz�dn� nale�y ustawi� przed materia�em");
		add(startLabel,border);
		
		
		//x0 y4 end Z Label
		border.gridx=0;
		border.gridy++;
		endLabel=new JLabel("Z Ko�cowe");;
		endLabel.setToolTipText("Wsp�rz�dna Z dna frezowanej wk�adki");
		add(endLabel,border);
		
		
		// x0 y5 tool Diameter
		border.gridx=0;
		border.gridy++;
		toolDiameterLabel = new JLabel("�rednica narz�dzia");
		toolDiameterLabel.setToolTipText("�rednica narz�dzia wykorzystanego do operacji podana w mm");
		add(toolDiameterLabel,border);
		

		//x0 y7 ae label
		border.gridx=0;
		border.gridy++;
		border.gridy++;
		aeLabel = new JLabel("ae");
		aeLabel.setToolTipText("Szeroko�� skrawania w stosunku do srednicy narz�dzia. Np. 0.1 przy srednicy narzedzia 25 daje ae=2.5");
		add(aeLabel,border);
		
		//x0 y8 speed
		border.gridx=0;
		border.gridy++;
		speedLabel = new JLabel("Obroty");
		speedLabel.setToolTipText("Pr�dko�� obrotowa wrzeciona. Mozna podac predkosc skrawania w celu obliczenia obrotow wg formatu: Vc=wartosc");
		add(speedLabel,border);
		
		//x0 y9 feed 
		border.gridx=0;
		border.gridy++;
		feedLabel = new JLabel("Posuw");
		feedLabel.setToolTipText("Pr�dko�� posuwu Vf w mm/min. Mo�na podac posow na zab i liczbe zebow w celu obliczenia predkosci posuwu wg schematu fn=wartosc z=wartosc");
		add(feedLabel,border);
		
		//x0 y10 calculate
		border.gridx=0;
		border.gridy++;
		calculate = new JButton("Oblicz");
		calculate.setToolTipText("Generuj program dla podanych parametr�w");
		calculate.addActionListener(this);
		add(calculate,border);
		
		

		//metody combo
		border.gridx=1;
		border.gridy=0;
		metodaBox = new JComboBox<>(METODY);
		metodaBox.setSelectedIndex(2);
		add(metodaBox,border);
		
		metodaBox.addActionListener(e->
		{
			if(metodaBox.getSelectedIndex()==1) {
			parent.spiral=new Spiral(parent);
			parent.wklad=null;
			this.dispose();
			}
			else if(metodaBox.getSelectedIndex()==0)
			{
				parent.pogl = new Poglebienie(parent);
				parent.wklad=null;
				this.dispose();
			}
		});
				
		// txt field x1 txt
		border.gridy++;	
		xTxt=new JTextField("");
		add(xTxt,border);
		
		// x1 y1 y txt
		border.gridx=1;
		border.gridy++;
		yTxt = new JTextField("");
		add(yTxt,border);
		
		
		// x1 y2 diameter txt
		border.gridx=1;
		border.gridy++;
		diameterTxt = new JTextField("");
		add(diameterTxt,border);
		
		// x1 y3 start z txt
		border.gridx =1;
		border.gridy++;
		startTxt = new JTextField("");
		add(startTxt,border);
		
		// x1 y4 end Z txt
		border.gridx=1;
		border.gridy++;
		endTxt = new JTextField("");
		add(endTxt,border);
		
		// x1 y5 tool diamter txt
		border.gridx =1;
		border.gridy++;
		toolDiameterTxt = new JTextField("");
		add(toolDiameterTxt,border);
		

		
		//x1 y7 ae txt
		border.gridx=1;
		border.gridy++;
		border.gridy++;
		aeTxt = new JTextField("");
		add(aeTxt,border);
			
		//x1 y8 speed txt
		border.gridx =1;
		border.gridy++;
		speedTxt = new JTextField("");
		add(speedTxt,border);
		
		//x1 y9 feed txt
		border.gridx=1;
		border.gridy++;
		feedTxt = new JTextField("");
		add(feedTxt,border);
		
		//x1 y10 cancel
		border.gridx=1;
		border.gridy++;
		cancel = new JButton("Cofnij");
		cancel.addActionListener(this);
		add(cancel,border);
		
		
		
		
		// units
		
		//x2 y2 diameter
		border.gridx=2;
		border.gridy=3;
		diameterUnit = new JLabel("mm");
		add(diameterUnit,border);
		
		
		//x2 y5  tooldiameter
		border.gridx=2;
		border.gridy++;
		border.gridy++;
		border.gridy++;
		toolDiameterUnit = new JLabel("mm");
		add(toolDiameterUnit,border);
		
		//x2 y7 ae unit
		border.gridx=2;
		border.gridy++;
		border.gridy++;
		aeUnit = new JLabel("*100% D");
		add(aeUnit,border);
		
		//x2 y8 speed 
		border.gridx=2;
		border.gridy++;
		speedUnit = new JLabel("1/min");
		speedUnit.setToolTipText("obroty na minut�");
		add(speedUnit,border);
		
		
		
		//x2 y9 feed
 		border.gridx=2;
 		border.gridy++;
 		feedUnit = new JLabel("mm/min");
 		feedUnit.setToolTipText("milimetry na minut�");
 		add(feedUnit,border);
 		
 		
 		//rys
 		border.gridx=3;
 		border.gridy=0;
 		border.weightx=1;
 		border.weighty=1;
 		border.gridheight=10;
 		border.gridwidth=1;
 		c= new canv();
 		add(c,border);
 		c.repaint();
 		pack();
 		
 		
 		
	}
	
	private class canv extends JPanel
	{
		canv()
		{
			setVisible(true);
			setPreferredSize(new Dimension(200,300));
	

		}
		
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			//uklad wspolrzednych
			//os y
			g.setColor(Color.BLACK);
			g.drawLine(10, 300, 10, 260);  
			g.drawLine(10, 260, 13, 267);
			g.drawLine(10, 260, 7, 267);
			g.drawString("y", 1, 277);
			
			//os x
			g.drawLine(10, 300, 50, 300);
			g.drawLine(50, 300, 45, 295);
			g.drawLine(50, 300, 45, 305);
			g.drawString("x", 36, 310);
			
			
			//okrag glownny
			g.drawArc(30, 30, 150, 150, 0, 360);
			
			// pozioma linia wymiarowa
			g.drawLine(30, 220, 180, 220);
			g.drawLine(30,218,30,222);
			g.drawLine(180,218,180,222);
			
			g.drawString("d", 105, 210);
			
			
			//pionowa o�
			g.drawString("y", 105, 15);
			for(int i=0;i<160;)
			{
				i+=10;
				g.drawLine(105, i+18, 105, 23+i);
				
				
			}
			//pozioma o�
			g.drawString("x", 15, 105);
			for(int i=0;i<160;i+=10)
			{
				g.drawLine(28+i, 105, 33+i, 105);

			}
			
			//schemat ruchu narz�dzia
			g.setColor(Color.RED);
			g.drawArc(105, 97, 15, 15, 180, 180);
			g.drawArc(90, 90, 30, 30, 0, 180);
			g.drawArc(90, 83, 45, 45, 180, 180);
			g.drawArc(75, 76, 60, 60, 0, 180);
			g.drawArc(75, 69, 75, 75, 180, 180);
			g.drawArc(60, 62, 90, 90, 0, 180);
			g.drawArc(60, 55, 105, 105, 180, 180);
			g.drawArc(45, 48, 120, 120, 0, 180);
			g.drawArc(45, 41, 135, 135, 180, 85);
			
			
			
			//strza�ka nr 1 od �rodka
			g.drawLine(90, 105, 86, 99);
			g.drawLine(90, 105, 96, 99);
			
			//strza�ka nr 2 od �rodka
			g.drawLine(106, 175, 101, 171);
			g.drawLine(106, 175, 101, 179);
			
			//schemat ruchu narzedzia txt
			g.drawString("�cie�ka narz�dzia", 40, 250);
			g.drawLine(145, 246, 175, 246);
			g.drawLine(175, 246, 171, 242);
			g.drawLine(175, 246, 171, 250);
			
		}
		
		
		
	}
	
	
	
	
	private boolean isOk()
	{
		try
		{
			this.start=Float.parseFloat(this.startTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le zdefiniowany Z pocztkowy");
			return false;
		}
		try
		{
			this.end=Float.parseFloat(this.endTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le zdefiniowany Z ko�cowy");
			return false;
		}
		
		if(start <end)
		{
			JOptionPane.showMessageDialog(this, "Wsp�rz�dna Z pocz�tkowa musi by� wi�kszy od Z dna wk�adki");
			return false;
		}
		try
		{
			this.toolDiameter=Float.parseFloat(this.toolDiameterTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le zdefiniowana �rednica narz�dzia");
			return false;
		}
		try
		{
			this.x=Float.parseFloat(this.xTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le zdefiniowana wsp�rz�dna x");
			return false;
		}
		try
		{
			this.ae=Float.parseFloat(this.aeTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le podane ae");
			return false;
		}
		try
		{
			this.y=Float.parseFloat(this.yTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le zdefiniowana wsp�rz�dna y");
			return false;
		}
		
		
		if(Function.covertVcToN(this.speedTxt, this.toolDiameter)!=0) return false;
		
		try
		{
			this.speed=Integer.parseInt(this.speedTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le zdefiniowana pr�dko�� obrotowa wrzeciona");
			return false;
		}
		
		if(Function.covertFnToVf(this.feedTxt, this.speed) !=0) return false;
		try
		{
			this.feed=Float.parseFloat(this.feedTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le zdefiniowana pr�dko�� posuwu");
			return false;
		}
		
		if(speed <0)
		{
			JOptionPane.showMessageDialog(this, "Pr�dko�� wrzeciona nie mo�e by� ujemna");
			return false;
		}
		if(feed <0)
		{
			JOptionPane.showMessageDialog(this, "Pr�dko�� posuwu nie mo�e by� ujemna");
			return false;
		}
		if(ae <=0 || ae>0.601)
		{
			JOptionPane.showMessageDialog(this, "Szeroko�� skrawania ae powinna mie�cic si� w przedziale (0 ; 0.6>");
			return false;
		}
		
		if(toolDiameter <0)
		{
			JOptionPane.showMessageDialog(this, "�rednica narz�dzia nie mo�e by� ujemna");
			return false;
		}
		try
		{
			this.diameter=Float.parseFloat(this.diameterTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "�le zdefiniowana �rednica wk��dki narz�dzia");
			return false;
		}
		
		if(toolDiameter > diameter)
		{
			JOptionPane.showMessageDialog(this, "�rednia narz�dzia jest wi�ksza od �rednicy frezowanej wk�adki, jakby nie pacze� - nie pojedzie");
			
			return false;
		}
		
		

		if((diameter<=toolDiameter*2))
		{
			JOptionPane.showMessageDialog(this, "Za ma�a �rednica, u�yj funkcji pog��bienia zamiast frezowania wk�adki do wygenerowania kodu");
			
			return false;
		}
		
		
		
		return true;		
	}
	
	
	
	
	private void generate()
	{
		PrintStream ps = new PrintStream(parent.txt);
		System.setOut(ps);
		System.setErr(ps);
		
		
		
		int n =55;
		
		System.out.printf(Locale.CANADA,"(T%d FREZ FI%.2f)%n", toolNumber,toolDiameter);

		//	przygotowanie baz i korekcji
		parent.getControls().przygotowanieUkladuINarzedzia(5, toolNumber, safeRetract,rotationB,base);
		
		System.out.printf(Locale.CANADA,"N30 S%d M3%n",speed);
		System.out.printf(Locale.CANADA,"N35 G0 X%.3f Y%.3f M8%n",x-3,y);
		System.out.printf(Locale.CANADA,"N40 G0 Z%.3f%n",start+100);
		if(parent.getControls()==new SterowanieSinumeric())
			System.out.printf(Locale.CANADA,"N45 G0 Z%.3f%n",start);
		else
			System.out.printf(Locale.CANADA,"N45 G1 Z%.3f F10000.%n",start);
		
		if(this.parent.getControls()==new SterowanieFanuc())
		System.out.printf(Locale.CANADA,"N50 G1 G41 D%d X%.3f Y%.3f F100. %n",toolNumber,x-1,y);
		
		else 
			System.out.printf(Locale.CANADA,"N50 G1 G41 X%.3f Y%.3f F100. %n",x-1,y);
		
		float i = start;
		while (i>end)
		{
			i-=0.5;
			if(i<end) i=end;
			
			
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I1. J0. Z%.3f F%.3f%n",n+=5,x-1,y,i,feed/4);		
	
		
		}
		System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I1. J0 F%.3f%n",n+=5,x-1,y,feed/2);
		System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0.5 J0 F%.3f%n",n+=5,x,y,feed);
		
		
		ae = toolDiameter*ae;
		int j=-1;
		
		
		System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0%n",n+=5,x+ae,y,ae/2);
		
		
		while(ae*j<diameter)
		{
			j+=1;
			if(2*ae+ae*j>diameter/2-toolDiameter/2)
			{
				break;
			}
			
			
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,x-(3*ae/2+(ae*j)),y,-((5*ae/4)+(ae*j)));
			System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,x+2*ae+ae*j,y,((7*ae/4)+(ae*j)));
			
		}

		System.out.printf(Locale.CANADA,"N%d G1 X%.3f Y%.3f %n",n+=5,x+diameter/2-toolDiameter/2-0.2,y);
		System.out.printf(Locale.CANADA, "(PRZEJSCIE ZGRUBNE SREDNICA %.2f)%n",diameter-0.4);
		System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,x-diameter/2+toolDiameter/2+0.2,y,-(diameter/2-toolDiameter/2)+0.2);
		System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,x+diameter/2-toolDiameter/2-0.2,y,diameter/2-toolDiameter/2-0.2);		
		System.out.printf(Locale.CANADA, "(PRZEJSCIE NA GOTOWO SREDNICA %.2f)%n",diameter);
		System.out.printf(Locale.CANADA,"N%d G1 X%.3f Y%.3f %n",n+=5,x+diameter/2-toolDiameter/2,y);
		System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,x-diameter/2+toolDiameter/2,y,-(diameter/2-toolDiameter/2));
		System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,x+diameter/2-toolDiameter/2,y,diameter/2-toolDiameter/2);
		
		System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,x,y,-(diameter/2-toolDiameter/2)/2);
		
		
		System.out.printf(Locale.CANADA,"N%d G1 X%.3f Y%.3f F1000.%n",n+=5,x+2,y);
		System.out.printf(Locale.CANADA,"N%d G0 Z%.3f%n",n+=5,end+0.2);
		System.out.printf(Locale.CANADA,"N%d G1 G40 X%.3f Y%.3f%n",n+=5,x,y);
		
		System.out.printf(Locale.CANADA,"N%d G0 Z%d M5%n",n+=5,Wind.options.getSafeRetraction());
		System.out.printf(Locale.CANADA,"N%d M9 %n",n+=5);
		System.out.printf(Locale.CANADA,"N%d M1 %n",n+=5);
		
		
		
		
		parent.writelog("Wygenerowano kod dla rozfrezowania okr�g�ej wk�adki o �rednicy "+diameter+"mm na wsp�rz�dnych X"+x+ " Y"+y+ "\n\t�rednica u�ytego narz�dzia: "+toolDiameter+"mm. Mo�liwa korekcja promieniowa do 2mm ");
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o== calculate)
		{
			if(isOk())
			{
				generate();
				Wind.log.writeInfoLog("Wkladka -wykonano", Wkladka.class.getSimpleName());
				this.cancel.doClick();
			}
			
			
			
		}
		else if(o==cancel)
		{
			parent.wklad=null;
			this.dispose();
			
		}
		
	}

}
