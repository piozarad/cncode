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
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import DrawFunction.DrawTool;

public class Pocket extends JFrame implements ActionListener{

	private final Edytor parent;
	Sterowanie sterowanie;
	float safeRetraction;
	int toolNumber;
	
	// machining parameters
	private Point startingPoint;
	private  float width;
	private float height;
	private int speed;
	private float feed;
	private float start;
	private float end;
	private float toolsize;
	private float ap;
	
		//Swing
	
	
	//labels
	private JLabel startingPointLabel;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel startingZ;
	private JLabel endZ;
	private JLabel toolSizeLabel;
	private JLabel speedLabel;
	private JLabel FeedLabel;
	private JLabel apLabel;
	private JLabel turnLabel;
	
	private JLabel widthUnitLabel;
	private JLabel heightUnitLabel;
	private JLabel speedUnitLabel;
	private JLabel feedUnitLabel;
	private JLabel apUnitLabel;
	private JLabel toolSizeUnit;
	
	
	//check boxes
	private JCheckBox turnCombo;
	
	//text fields
	private JTextField xStartingPointTxt;
	private JTextField yStartingPointTxt;
	private JTextField widthTxt;
	private JTextField heightTxt;
	private JTextField startingZTxt;
	private JTextField endZTxt;
	private JTextField toolSizeTxt;
	private JTextField speedTxt;
	private JTextField feedTxt;
	private JTextField apTxt;
	
	
	//buttons
	private JButton oblicz;
	private JButton cofnij;
	
	
	//rysunek
	JPanel c=null;
	
	
	
	//constraints
	GridBagConstraints border;
	
	
	public Pocket(Edytor parent)
	{
		this.parent=parent;
		this.toolNumber=parent.getToolBar().getToolNumber();
		this.sterowanie=parent.getControls();
		this.safeRetraction=Wind.options.getSafeRetraction();
		setTitle("Frezowanie kieszeni");
		setSize(950,400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		
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
	
		
		
		
	 border = new GridBagConstraints();
		border.weightx=0.1;
		border.fill=GridBagConstraints.HORIZONTAL;
		border.insets = new Insets(5,5,5,5);
		
		
		//x0 y0
		startingPointLabel = new JLabel("Œrodek symetrii kieszeni");
		startingPointLabel.setToolTipText("Punkt przeciêcia osi symetrii osi X i Y kieszeni");
		border.gridx=0;
		border.gridy=0;
		add(startingPointLabel,border);
		
		//x1
		xStartingPointTxt = new JTextField("(Wspó³rzêdna X0)");
		xStartingPointTxt.setSize(75,150);
		xStartingPointTxt.addActionListener(this);
		border.gridx=1;
		border.gridy=0;
		add(xStartingPointTxt,border);
		
		//x2
		yStartingPointTxt = new JTextField("(Wspó³rzêdna Y0)");
		yStartingPointTxt.setSize(75,150);
		yStartingPointTxt.addActionListener(this);
		border.gridx=2;
		border.gridy=0;
		add(yStartingPointTxt,border);
		
		
		//x0 y1
		//width
		widthLabel = new JLabel("Szerokoœæ rowka d");
		widthLabel.setToolTipText("Szerokoœæ frezowanej kieszeni w mm");
		border.gridx=0;
		border.gridy=1;
		add(widthLabel,border);
		
		//x1 y1
		//widthTXt
		widthTxt = new JTextField("");
		widthTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=1;
		add(widthTxt,border);
		
		//x2 y1
		//widthLabel
		widthUnitLabel = new JLabel("mm");
		border.gridx=2;
		border.gridy=1;
		add(widthUnitLabel,border);
		
		//x0 y2
		//height
		heightLabel = new JLabel("Wysokoœæ rowka h");
		heightLabel.setToolTipText("Wysokoœæ frezowanego rowka w mm");
		border.gridx=0;
		border.gridy=2;
		add(heightLabel,border);
		
		//x1 y2
		//heightTxt
		heightTxt = new JTextField("");
		heightTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=2;
		add(heightTxt,border);
		
		//x2 y2
		//height unit label
		heightUnitLabel = new JLabel("mm");
		border.gridx=2;
		border.gridy=2;
		add(heightUnitLabel,border);
		
		//x0 y3
		// z pocz
		startingZ = new JLabel("Z poczatkowy");
		startingZ.setToolTipText("Wspó³rzêdna, która okreœla pocz¹tek frezowania kieszeni w osi Z");
		border.gridx=0;
		border.gridy=3;
		add(startingZ,border);
		
		//x1 y3
		// zpocz Txt
		startingZTxt = new JTextField("");
		startingZTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=3;
		add(startingZTxt,border);
		
		//x0 y4
		// Z kon Label
		endZ = new JLabel("Z koñcowy");
		endZ.setToolTipText("Z dna frezowanej kiszeni");
		border.gridx=0;
		border.gridy=4;
		add(endZ,border);
		
		//x1 y4
		//z kon Text
		endZTxt = new JTextField("");
		endZTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=4;
		add(endZTxt,border);
		
		//x0 y5
		toolSizeLabel = new JLabel("Œrednica narzêdzia");
		toolSizeLabel.setToolTipText("Srednica u¿ytego narzêdzia podana w mm");
		border.gridx=0;
		border.gridy=5;
		add(toolSizeLabel,border);
		
		//x1 y5
		toolSizeTxt = new JTextField("");
		toolSizeTxt.setSize(75, 150);
		border.gridx=1;
		border.gridy=5;
		add(toolSizeTxt,border);
		
		//x2 y5
		toolSizeUnit = new JLabel("mm");
		add(toolSizeUnit,border);
		
		//x0 y6
		speedLabel = new JLabel("Obroty");
		speedLabel.setToolTipText("Prêdkoœæ obrotowa wrzeciona. Mozna podac predkosc skrawania w celu obliczenia obrotow wg formatu: Vc=wartosc");
		border.gridx=0;
		border.gridy=6;
		add(speedLabel,border);
		
		//x1 y6
		//speed txt
		speedTxt = new JTextField("");
		speedTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=6;
		add(speedTxt,border);
		
		//x2 y6
		//speed unit
		speedUnitLabel = new JLabel("1/min");
		speedUnitLabel.setToolTipText("obroty na minutê");
		border.gridx=2;
		border.gridy=6;
		add(speedUnitLabel,border);
		
		//x0 y7 posow
		FeedLabel = new JLabel("Posuw");
		FeedLabel.setToolTipText("Prêdkoœæ posuwu Vf w mm/min. Mo¿na podac posow na zab i liczbe zebow w celu obliczenia predkosci posuwu wg schematu fn=wartosc z=wartosc");
		border.gridx=0;
		border.gridy=7;
		add(FeedLabel,border);
		
		//x1 y7
		//feed txt
		feedTxt = new JTextField("");
		feedTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=7;
		add(feedTxt,border);
		
		//x2 y7
		//feed unit
		feedUnitLabel = new JLabel("mm/min");
		feedUnitLabel.setToolTipText("milimetry na minutê");
		border.gridx=2;
		border.gridy=7;
		add(feedUnitLabel,border);
		
		
		//x0 y8
		//ap
		apLabel = new JLabel("ap");
		apLabel.setToolTipText("G³êbokoœæ skrawania - zag³êbiania siê narzêdzia na ka¿dym przejœciu  w osi Z");
		border.gridx=0;
		border.gridy=8;
		add(apLabel,border);
		
		//x1 y8
		// ap Txt
		apTxt = new JTextField("");
		apTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=8;
		add(apTxt,border);
		
		//x2 y7
		//ap unit label
		apUnitLabel = new JLabel("mm");
		border.gridx=2;
		border.gridy=8;
		add(apUnitLabel,border);
		
		//x0 y9
		//obroc
		turnLabel = new JLabel("Obroc kieszen");
		turnLabel.setToolTipText("Zamienia kieszen poziom¹ na pionow¹");
		border.gridx=0;
		border.gridy=9;
		add(turnLabel,border);
		
		//x1 y9
		turnCombo = new JCheckBox();
		turnCombo.setToolTipText("Zamienia kieszen poziom¹ na pionow¹");
		turnCombo.addActionListener(this);
		turnCombo.setSelected(false);
		border.gridx=1;
		border.gridy=9;
		add(turnCombo,border);
		
		//x0 y10
		//przyciski
		oblicz = new JButton("Oblicz");
		oblicz.addActionListener(this);
		border.gridx=0;
		border.gridy=10;
		add(oblicz,border);
		
		//x1 y9
		cofnij = new JButton("Cofnij");
		cofnij.addActionListener(this);
		border.gridx=1;
		border.gridy=10;
		add(cofnij,border);
		
		//rysunek
		//x3/y0
		border.gridx=3;
		border.gridy=0;
		border.weightx=1;
		border.weighty=1;
		border.gridheight=10;
		border.gridwidth=1;	
		c = new canv();
		add(c,border);
		c.repaint();
		pack();
		
		
		
	}

	
	private class canva extends JPanel
	{
		
		
		//pionowa kieszen
		public canva()
		{
			setPreferredSize(new Dimension(300,350));
			setVisible(true);
			pack();
			
			
			repaint();
			
		}

			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				//uklad wspolrzednych
				//os y
				g.setColor(Color.BLACK);
				g.drawLine(10, 340, 10, 300);  
				g.drawLine(10, 300, 13, 307);
				g.drawLine(10, 300, 7, 307);
				g.drawString("y", 1, 317);
				
				//os x
				g.drawLine(10, 340, 50, 340);
				g.drawLine(50, 340, 45, 335);
				g.drawLine(50, 340, 45, 345);
				g.drawString("x", 36, 350);
				
				
				
				//kieszen od lewego górnego rogu
				g.drawArc(70, 20, 80, 80, 0, 180);
				g.drawLine(150, 60, 150, 160);
				g.drawArc(70, 120, 80, 80, 0, -180);
				g.drawLine(70, 60, 70, 160);
				
				
				//pozioma linia wymiarowa d
				g.drawLine(70, 233, 70, 227);
				g.drawLine(70, 230, 150, 230);
				g.drawLine(150, 233, 150, 227);
				g.drawString("d", 110, 225);
				
				//pionowa linia wymiarowa h
				g.drawLine(187, 20, 193, 20);
				g.drawLine(190, 20, 190, 200);
				g.drawLine(187, 200, 193, 200);
				g.drawString("h", 180, 110);
				
				
				//pionowa przerywana
			for(int p=10;p<204;)
			{
				p+=5;
				g.drawLine(110, p, 110, p+2);
			}
			g.drawString("X0", 115, 15);
				
				
				
				//pozioma przerywana
			for(int p=60;p<155;)
			{
				p+=5;
				g.drawLine(p, 110, p+2, 110);
			}
			g.drawString("Y0", 45, 110);
			
			
			
			
			
			//œcie¿ka rys
			g.setColor(Color.RED);
			g.drawLine(110, 110, 110, 160);
			g.drawArc(110, 140, 35, 35, 180, 180);
			g.drawLine(145, 60, 145, 160);
			g.drawArc(75, 25, 70, 70, 0, 180);
			g.drawLine(75, 60, 75, 160);
			g.drawArc(75, 125, 70, 70, 180, 90);
			
			
			g.drawString("a", 135, 105);
			g.drawString("a - prosta zag³êbiania siê osi Z", 30, 290);
			
			
			//pierwsza strza³ka
			g.drawLine(110, 160, 106, 156);
			g.drawLine(110, 160, 114, 156);
			
			//druga strza³ka
			g.drawLine(110, 195, 107, 191);
			g.drawLine(110, 195, 107, 199);
			
			//œciezka narzêdzia
			g.drawString("Œcie¿ka narzêdzia", 70, 260);
			g.drawLine(30, 255, 60, 255);
			g.drawLine(60, 255, 56, 251);
			g.drawLine(60, 255, 56, 259);
			
			//narzedzie rysunek
			new DrawTool(new Point(110f,110f),30).draw(g);
			
			
			}
			

		
	}
	
	
	private class canv extends JPanel
	{
	
		public canv()
		{
			setPreferredSize(new Dimension(300,350));
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
			g.drawLine(10, 340, 10, 300);  
			g.drawLine(10, 300, 13, 307);
			g.drawLine(10, 300, 7, 307);
			g.drawString("y", 1, 317);
			
			//os x
			g.drawLine(10, 340, 50, 340);
			g.drawLine(50, 340, 45, 335);
			g.drawLine(50, 340, 45, 345);
			g.drawString("x", 36, 350);
			
			
			
			//kieszen od lewego górnego rogu
			g.drawLine(80, 20, 180, 20);
			g.drawArc(140, 20, 80, 80, -90, 180);
			g.drawLine(80, 100, 180, 100);
			g.drawArc(40, 20, 80, 80, -90, -180);
			
			
			//pozioma linia wymiarowa d
			g.drawLine(40, 127, 40, 133);
			g.drawLine(220, 127, 220, 133);
			g.drawLine(40, 130, 220, 130);
			g.drawString("d", 130, 125);
			
			//pionowa linia wymiarowa h
			g.drawLine(227, 20, 233, 20);
			g.drawLine(230, 20, 230, 100);
			g.drawLine(227, 100, 233, 100);
			g.drawString("h", 233, 60);
			
			
			//pionowa przerywana
		for(int p=10;p<105;)
		{
			p+=5;
			g.drawLine(130, p, 130, p+2);
		}
		g.drawString("X0", 130, 9);
			
			
			
			//pozioma przerywana
		for(int p=30;p<217;)
		{
			p+=5;
			g.drawLine(p,60, p+2, 60);
		}
		g.drawString("Y0", 15, 60);
		
		
		
		
		//œcie¿ka narz¹dzia
		g.setColor(Color.red);
		g.drawLine(130, 60, 80, 60);
		//strza³ka nr 1
		g.drawLine(80, 60, 84, 56);
		g.drawLine(80, 60, 84, 66);
		
		//³uk nr 1
		g.drawArc(62, 60, 35, 35, 90, 180);
		
		g.drawLine(80, 95, 180, 95);
		g.drawString("a", 120, 90);
		
		g.drawArc(145, 25, 70, 70, 90, -180);
		g.drawLine(80, 25, 180, 25);
		//strza³ka nr 2
		g.drawLine(80, 25, 84, 21);
		g.drawLine(80, 25, 84, 29);
		
		//rysunek narzêdzia
		new DrawTool(new Point(130f,60f),30).draw(g);
		
		//legenda
		g.drawString("Œcie¿ka narzêdzia", 80, 180);
		g.drawLine(50, 175, 70, 175);
		g.drawLine(70, 175, 66, 171);
		g.drawLine(70, 175, 66, 179);
		g.drawString("a - prosta zag³ebiania siê narz¹dzia w osi Z", 50, 220);
		
		
		}

	}
	
	
	

	
	private boolean isOk()
	{
		float x,y;
		try
		{
			 x = Float.parseFloat(xStartingPointTxt.getText());
			 y = Float.parseFloat(yStartingPointTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(parent,"Zle zdefiniowany punkt œrodkowy symetrii");
			return false;
		}
		this.startingPoint = new Point(x,y);
		
		try
		{
			this.toolsize = Float.parseFloat(this.toolSizeTxt.getText());
			
			
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		try
		{
			width = Float.parseFloat(this.widthTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(parent,"Zle zdefiniowana szerokoœæ kieszeni");
			return false;
		}
		
		try
		{
			this.height = Float.parseFloat(heightTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(parent,"Zle zdefiniowana wysokoœæ rowka");
			return false;
		}
		try
		{
			this.ap = Float.parseFloat(apTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(parent,"Zle zdefiniowana ap");
			return false;
		}
		
		
		if(Function.covertVcToN(this.speedTxt, this.toolsize)!=0) return false;
		

		try
		{
			this.speed = Integer.parseInt(this.speedTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(parent,"Zle zdefiniowane obroty wrzeciona");
			return false;
		}
		
		if(Function.covertFnToVf(this.feedTxt, this.speed)!=0) return false;
		
		try
		{
			this.feed = Float.parseFloat(this.feedTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(parent,"Zle zdefiniowany posuw");
			return false;
		}
		
		try
		{
			this.start = Float.parseFloat(startingZTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(parent,"Zle zdefiniowana wspó³rzêdna startowa Z");
			return false;
		}
		try
		{
			this.end = Float.parseFloat(this.endZTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(parent,"Zle zdefiniowana wspó³rzêdna dna kieszeni");
			return false;
		}

		
		if(toolsize>width -0.5 || toolsize>height-0.5)
		{
			JOptionPane.showMessageDialog(this, "Srednica narzêdzia za ma³a na tak¹ kieszeñ");
			
			return false;
		}
		
		if(width <11)
		{
			JOptionPane.showMessageDialog(this, "Za ma³a szerokoœæ kieszeni");
		
			return false;
		}
		if(height <11)
		{
			JOptionPane.showMessageDialog(this, "Za ma³a wysokoœæ kieszeni");
		
			return false;
		}
		
		if(this.turnCombo.isSelected() && this.width >this.height)
		{
			JOptionPane.showMessageDialog(this, "Wysokoœæ kieszeni powinna byæ wiêksza od jej d³ugoœci");
		
			return false;
		}
		
		if(!this.turnCombo.isSelected() && this.width <this.height)
		{
			JOptionPane.showMessageDialog(this, "Wysokoœæ kieszeni powinna byæ mniejsza od jej d³ugoœci");
		
			return false;
		}

		return true;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o==oblicz)
		{
			if(isOk())
			{
				
				PrintStream ps= new PrintStream(parent.txt);
				System.setOut(ps);
				System.setErr(ps);
				
				int n=65;
				int toolNumber = parent.getToolBar().getToolNumber();
				float i=start;
				double zwolnienie = Wind.options.getFeedRateReductionOnCircle();	
				
				
				System.out.printf(Locale.CANADA,"(T%d GLOWICA FI%.2f )%n", toolNumber,toolsize);
				
				sterowanie.przygotowanieUkladuINarzedzia(15, this.toolNumber, safeRetraction,this.parent.getToolBar().getRotation(),this.parent.getToolBar().getBase());
				
				System.out.printf(Locale.CANADA,"N15 S%d M3%n",speed);
				
				
				if(!this.turnCombo.isSelected())	
					System.out.printf(Locale.CANADA,"N40 G0 X%.3f Y%.3f M8%n",this.startingPoint.getX()-(toolsize/5),this.startingPoint.getY());
				else 
					System.out.printf(Locale.CANADA,"N40 G0 X%.3f Y%.3f M8%n",this.startingPoint.getX(),this.startingPoint.getY()-(toolsize/5));
				
				
				if(this.parent.getControls().isType(ControlTypes.FANUC))
					System.out.printf(Locale.CANADA,"N45 G1 Z%.2f F10000.%n",this.start);
				else
					System.out.printf(Locale.CANADA,"N45 G0 Z%.2f%n",this.start);
				
				
				
			if(!this.turnCombo.isSelected())	
			{
				if(parent.getControls().isType(ControlTypes.FANUC))
					System.out.printf(Locale.CANADA,"N50 G41 D%d G1 X%.3f Y%.3f F%.1f%n",toolNumber,this.startingPoint.getX(),this.startingPoint.getY(),feed);
				else
					System.out.printf(Locale.CANADA,"N50 G41 G1 X%.3f Y%.3f F%.1f%n",this.startingPoint.getX(),this.startingPoint.getY(),feed);
				
				
				System.out.printf(Locale.CANADA,"N55 G3 X%.3f Y%.3f I0. J%.3f F%.1f%n",this.startingPoint.getX(), this.startingPoint.getY()+((this.height/2)-(toolsize/2)),((this.height/2)-(toolsize/2))/2, zwolnienie*feed);
				System.out.printf(Locale.CANADA,"N60 G1 X%.3f F%.1f%n",this.startingPoint.getX()-width/2+height/2,feed);
				System.out.printf(Locale.CANADA,"N65 G3 X%.3f Y%.3f I0. J%.3f F%.1f%n",this.startingPoint.getX()-width/2+height/2, this.startingPoint.getY()-(height/2-toolsize/2),-(height/2-toolsize/2),zwolnienie*feed);
				
				while(i > end)
				{
					i-=ap;
					System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f F%.1f%n",n+=5,this.startingPoint.getX()+width/2-height/2, i, feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f F%.1f %n",n+=5,this.startingPoint.getX()+width/2-toolsize/2, this.startingPoint.getY(), (height/2-toolsize/2),zwolnienie*feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0. %n",n+=5,this.startingPoint.getX()+width/2-height/2, this.startingPoint.getY()+(height/2-toolsize/2), -(height/2-toolsize/2));
					System.out.printf(Locale.CANADA,"N%d G1 X%.3f F%.1f %n",n+=5,this.startingPoint.getX()-width/2+height/2, feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f F%.1f%n",n+=5,this.startingPoint.getX()-width/2+toolsize/2, this.startingPoint.getY(),-(height/2-toolsize/2),zwolnienie*feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,this.startingPoint.getX()-width/2+height/2, this.startingPoint.getY()-(height/2-toolsize/2),(height/2-toolsize/2));
				}
				if(i!=end)
				{
					System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f F%.1f%n",n+=5,this.startingPoint.getX()+width/2-height/2, end, feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f F%.1f %n",n+=5,this.startingPoint.getX()+width/2-toolsize/2, this.startingPoint.getY(), (height/2-toolsize/2),zwolnienie*feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0. %n",n+=5,this.startingPoint.getX()+width/2-height/2, this.startingPoint.getY()+(height/2-toolsize/2), -(height/2-toolsize/2));
					System.out.printf(Locale.CANADA,"N%d G1 X%.3f F%.1f %n",n+=5,this.startingPoint.getX()-width/2+height/2, feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f F%.1f%n",n+=5,this.startingPoint.getX()-width/2+toolsize/2, this.startingPoint.getY(),-(height/2-toolsize/2),zwolnienie*feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0.%n",n+=5,this.startingPoint.getX()-width/2+height/2, this.startingPoint.getY()-(height/2-toolsize/2),(height/2-toolsize/2));
					
				}
				
				
				//wyjscie
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f F%.1f%n",n+=5,this.startingPoint.getX()+width/2-height/2, feed);
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f%n",n+=5,this.startingPoint.getX()+width/2-height/2, this.startingPoint.getY(),(height-toolsize)/4);
				System.out.printf(Locale.CANADA,"N%d G1 G40 X%.3f%n",n+=5,this.startingPoint.getX()+5);
			}
			
			
			else 
			{
				if(this.parent.getControls().isType(ControlTypes.FANUC))
					System.out.printf(Locale.CANADA,"N50 G41 D%d G1 X%.3f Y%.3f%n",toolNumber,this.startingPoint.getX(),this.startingPoint.getY());
				else System.out.printf(Locale.CANADA,"N50 G41 G1 X%.3f Y%.3f%n",this.startingPoint.getX(),this.startingPoint.getY());
				
				System.out.printf(Locale.CANADA,"N55 G3 X%.3f Y%.3f I%.3f J0. F%.1f%n",this.startingPoint.getX()+(this.width/2-toolsize/2), this.startingPoint.getY(),(this.width/2-toolsize/2)/2 ,feed);
				System.out.printf(Locale.CANADA,"N60 G1 Y%.3f %n",this.startingPoint.getY()-width/2+height/2-toolsize/2);
				System.out.printf(Locale.CANADA, "N65 G3 X%.3f Y%.3f I%.3f J0.%n",this.startingPoint.getX()-(width/2-toolsize/2), this.startingPoint.getY()-width/2+height/2-toolsize/2,-(width/2-toolsize/2));
				
				
				
				
				while(i > end)
				{
					i-=ap;
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f F%.1f %n",n+=5,this.startingPoint.getY()-(height/2)+(width/2), i,feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0. F%.1f%n",n+=5,this.startingPoint.getX(), this.startingPoint.getY()-height/2+toolsize/2,(width/2-toolsize/2),zwolnienie*feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f %n",n+=5,this.startingPoint.getX()+width/2-toolsize/2, this.startingPoint.getY()-height/2+width/2,(width/2-toolsize/2));
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f F%.1f%n",n+=5,this.startingPoint.getY()-width/2+height/2,feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f  I%.3f J0. F%.1f%n",n+=5,this.startingPoint.getX(),this.startingPoint.getY()-toolsize/2+height/2,-(width/2-toolsize/2),feed*zwolnienie);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f%n",n+=5,this.startingPoint.getX()-(width/2)+toolsize/2,this.startingPoint.getY()-width/2+height/2,-(width/2-toolsize/2));
				}
				
				if(i!=end)
				{
					i-=ap;
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f F%.1f %n",n+=5,this.startingPoint.getY()-(height/2)+(width/2), end ,feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0. F%.1f%n",n+=5,this.startingPoint.getX(), this.startingPoint.getY()-height/2+toolsize/2,(width/2-toolsize/2),zwolnienie*feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f %n",n+=5,this.startingPoint.getX()+width/2-toolsize/2, this.startingPoint.getY()-height/2+width/2,(width/2-toolsize/2));
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f F%.1f%n",n+=5,this.startingPoint.getY()-width/2+height/2,feed);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0. F%.1f%n",n+=5,this.startingPoint.getX(),this.startingPoint.getY()-toolsize/2+height/2,-(width/2-toolsize/2),feed*zwolnienie);
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I0. J%.3f%n",n+=5,this.startingPoint.getX()-(width/2)+toolsize/2,this.startingPoint.getY()-width/2+height/2,-(width/2-toolsize/2));
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f F%.1f %n",n+=5,this.startingPoint.getY()-(height/2)+(width/2),feed);
				}

				//wyjscie
				
				
			//	System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,this.startingPoint.getY());
				System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I%.3f J0. %n",n+=5,this.startingPoint.getX(),this.startingPoint.getY()-(height/2)+(width/2),(width/2-toolsize/2)/2);
				System.out.printf(Locale.CANADA,"N%d G1 G40 Y%.3f%n",n+=5,this.startingPoint.getY()-5);
				
		
			}
			System.out.printf(Locale.CANADA,"N%d G0 Z%d M9%n",n+=5,Wind.options.getSafeRetraction());	
			System.out.printf(Locale.CANADA,"N%d M5%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d M1%n",n+=5);
			
			this.parent.writelog("Wygenerowano kieszeñ o wymiarach:" + width + " na " + height + " mm na wspó³rzêdnych X" + this.startingPoint.getX() + " Y" + this.startingPoint.getY() + "%n\tZ poczatkowe=" + this.start + " Z koncowe:" + this.end + "%n\tMaksymalna korekcja promieniowa dla narzêdzia o œrednicy "+ this.toolsize +"mm :" + this.toolsize/5);
			Wind.log.writeInfoLog("Pocket - wykonano", Pocket.class.getSimpleName());
			this.cofnij.doClick();
				
			}
		}
		else if(o==this.turnCombo)
		{
			if(turnCombo.isSelected())
			{
				this.c=null;
				this.c= new canva();
				
				add(c,border);
				this.revalidate();
				
			}
				
			else
			{
				
				this.c=null;
				this.c= new canv();
				
				add(c,border);
				this.revalidate();
	
			}

		}
		
		
		
		
		
		
		else if(o== cofnij)
		{ 
			this.parent.poc=null;
			this.dispose();
		}
		
	}
	
}
