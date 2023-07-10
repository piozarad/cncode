package CordConverter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import BasicControls.Sterowanie;
import DrawFunction.DrawCordinateSystem;
import DrawFunction.DrawTool;
import DrawFunction.EmptyRectangle;
import DrawFunction.HorizontalDimension;
import DrawFunction.ToolPath;
import DrawFunction.VerticalDimension;

public class Przepona extends JFrame implements ActionListener{

	
	//sterowanie
	Sterowanie sterowanie;
	
	
	//labels
	private JLabel wspolrzedneLabel;
	private JLabel xSrodkaLabel;
	private JLabel ySrodkaLabel;
	private JLabel apLabel;
	private JLabel obrotyLabel;
	private JLabel posuwLabel;
	private JLabel fiNarzedziaLabel;
	private JLabel szerokoscLabel;
	private JLabel wysokoscLabel;
	private JLabel punktPrzybraniaLabel;
	private JLabel zPoczLabel;
	private JLabel zKonLabel;
	
	//jednostki labels
	private JLabel apJednostka;
	private JLabel obrotyJednostka;
	private JLabel posuwJednostka;
	private JLabel fiNarzedziaJednostka;
	private JLabel szerokoscJednostka;
	private JLabel wysokoscJednostka;
	
	
	//txt fields
	private JTextField xSrodkaTxt;
	private JTextField ySrodkaTxt;
	private JTextField fiNarzedziaTxt;
	private JTextField apTxt;
	private JTextField obrotyTxt;
	private JTextField posuwTxt;
	private JTextField szerokoscTxt;
	private JTextField wysokoscTxt;
	private JTextField zPoczTxt;
	private JTextField zKonTxt;
	
	//buttons
	private JButton oblicz;
	private JButton cofnij;
	private JButton pktPrzybrania;
	
	//edytor
	private Edytor edytor;
	
	
	
	//stuff
	int toolnumber;
	float bRotation;
	float safeRetraction;
	String advancePoint;
	String base;
	

	
	//child
	JFrame p = null;
	
	//variables
	float height;
	float width;
	float feed;
	int sprindleSpeed;
	float ap;
	float toolDiameter;
	float x;
	float y;
	float z_pocz;
	float z_kon;
	
	
	
	//JPanels
	JPanel buttonPanel;
	JPanel visualPanel;

	//variables
	private float x0;
	private float y0;
	private float membraneHeight;
	private float membraneWidth;
	
	
	//Dimensions
	Dimension visualSize = new Dimension(600,600);
	private static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(90,25);
	private static final Dimension BUTTON_PANEL_SIZE =  new Dimension(400,500);
	
	
	static final String PRZYBIERANIE_TOOLTIP = "<html>Definiuje punkt w którym narzêdzie bêdzie siê zag³êbia³o w osi Z.<br>Punkt powinien znajdowaæ siê poza materia³em</html>";
	
	public Przepona(Edytor edytor)
	{
		this.edytor=edytor;
		this.sterowanie=edytor.getControls();
		this.toolnumber=edytor.getToolBar().getToolNumber();
		this.bRotation = edytor.getToolBar().getRotation();
		this.base= edytor.getToolBar().getBase();
		
		buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		buttonPanel.setPreferredSize(BUTTON_PANEL_SIZE);
		
		visualPanel = new Visual(visualSize);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setLocation(300, 200);
		setResizable(false);
		setTitle("Frezowanie przepony");
		setSize(1005,600);
		
		
		//initialize variables
		x0=visualSize.height/2f;
		y0=visualSize.width/2f;
		membraneHeight =150;
		membraneWidth = 150;
		
		//exit on close
				this.addWindowListener(new java.awt.event.WindowAdapter() 
				{
					@Override
					public void windowClosing(WindowEvent e)
					{
						cofnij.doClick();
					
					}		
				});
		
		//layout
		GridBagLayout layout= new GridBagLayout();
		setLayout(new BorderLayout());
		buttonPanel.setLayout(layout);
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(5,5,5,5);
		border.gridx=0;
		border.gridy=0;
		border.weightx=0;
		border.weighty=0;
		border.fill=GridBagConstraints.BOTH;
		
		//labels
		//x0
		//y0
		
		
		wspolrzedneLabel = new JLabel("Wspo³rzêdne œrodka przepony:");
		buttonPanel.add(wspolrzedneLabel,border);
		
		//y1
		xSrodkaLabel = new JLabel("X œrodka:");
		border.gridx=0;
		border.gridy=1;
		buttonPanel.add(xSrodkaLabel,border);
		
		//y2
		ySrodkaLabel = new JLabel("Y œrodka:");
		border.gridx=0;
		border.gridy=2;
		buttonPanel.add(ySrodkaLabel,border);
		
		//y3 szerokosc
		szerokoscLabel = new JLabel("Szerokoœæ przepony w osi x:");
		border.gridx=0;
		border.gridy=3;
		buttonPanel.add(szerokoscLabel,border);
		
		//y4 wysokoœæ
		wysokoscLabel = new JLabel("Wysokoœæ przepony w osi y:");
		border.gridx=0;
		border.gridy=4;
		buttonPanel.add(wysokoscLabel,border);
		
		zPoczLabel = new JLabel("Z poczatkowy");
		border.gridx=0;
		border.gridy=5;
		buttonPanel.add(zPoczLabel,border);
		
		zKonLabel = new JLabel("Z koncowy");
		border.gridx=0;
		border.gridy=6;
		buttonPanel.add(zKonLabel,border);
		

		
		//y7 fi narzedzia
		fiNarzedziaLabel = new JLabel("Œrednica narzedzia");
		border.gridx=0;
		border.gridy=7;
		buttonPanel.add(fiNarzedziaLabel,border);
		
		//y8
		apLabel = new JLabel("Zag³êbianie Ap:");
		border.gridx=0;
		border.gridy=8;
		buttonPanel.add(apLabel,border);
		
		//y9 przybieranie
		punktPrzybraniaLabel = new JLabel("Przybieranie");
		punktPrzybraniaLabel.setToolTipText(PRZYBIERANIE_TOOLTIP);
		border.gridx=0;
		border.gridy=9;
		buttonPanel.add(punktPrzybraniaLabel,border);
		
		//y10 obroty
		obrotyLabel = new JLabel("Obroty");
		obrotyLabel.setToolTipText("Prêdkoœæ obrotowa wrzeciona. Mozna podac predkosc skrawania w celu obliczenia obrotow wg formatu: Vc=wartosc");
		border.gridx=0;
		border.gridy=10;
		buttonPanel.add(obrotyLabel,border);
 		
		//y11 posuw
		posuwLabel = new JLabel("Posuw");
		posuwLabel.setToolTipText("Prêdkoœæ posuwu Vf w mm/min. Mo¿na podac posow na zab i liczbe zebow w celu obliczenia predkosci posuwu wg schematu fn=wartosc z=wartosc");
		border.gridx=0;
		border.gridy=11;
		buttonPanel.add(posuwLabel,border);
		
		//y12 oblicz
		oblicz = new JButton("Oblicz");
		oblicz.addActionListener(this);
		oblicz.setPreferredSize(DEFAULT_BUTTON_SIZE);
		border.gridx=0;
		border.gridy=12;
		buttonPanel.add(oblicz,border);

		
		//x1
		
		//x1 y0 - empty
		
		//y1 x srodka
		
		xSrodkaTxt = new JTextField("0");
		border.gridx=1;
		border.gridy=1;
		xSrodkaTxt.setPreferredSize(DEFAULT_BUTTON_SIZE);
		buttonPanel.add(xSrodkaTxt,border);
		xSrodkaTxt.addKeyListener(new KeyListener()
				{
					@Override
					public void keyTyped(KeyEvent e) {
						// unsused method
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						// unsused method	
					}

					@Override
					public void keyReleased(KeyEvent e) {
						try
						{
							Przepona.this.x0 = Przepona.this.visualSize.width/2f+
									Float.parseFloat(Przepona.this.xSrodkaTxt.getText());
						}
						catch(NumberFormatException ex)
						{
							Przepona.this.x0 = Przepona.this.visualSize.width/2f;
						}
						Przepona.this.repaint();
					}
				});
		
		//y2 y srodka
		ySrodkaTxt = new JTextField("0");
		border.gridx=1;
		border.gridy=2;
		xSrodkaTxt.setPreferredSize(DEFAULT_BUTTON_SIZE);
		buttonPanel.add(ySrodkaTxt,border);
		ySrodkaTxt.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) {
				// unsused method
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// unsused method	
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try
				{
					Przepona.this.y0 = Przepona.this.visualSize.height/2f-Float.parseFloat(Przepona.this.ySrodkaTxt.getText());
				}
				catch(NumberFormatException ex)
				{
					Przepona.this.y0 = Przepona.this.visualSize.height/2f;
				}
				
				Przepona.this.repaint();
			}
		});
		
		
		//y3 szerokosc
		szerokoscTxt = new JTextField("150");
		border.gridx=1;
		border.gridy=3;
		szerokoscTxt.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) {
				// unsused method
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// unsused method	
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try
				{
					Przepona.this.membraneWidth = Math.abs(Float.parseFloat(Przepona.this.szerokoscTxt.getText()));
				}
				catch(NumberFormatException ex)
				{
					Przepona.this.membraneWidth = 150;
				}
				Przepona.this.repaint();
			}
		});
		buttonPanel.add(szerokoscTxt,border);
		
		//y4 wysokosc przepony
		wysokoscTxt = new JTextField("150");
		border.gridx=1;
		border.gridy=4;
		wysokoscTxt.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) {
				// unsused method
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// unsused method	
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try
				{
					Przepona.this.membraneHeight = Math.abs(Float.parseFloat(Przepona.this.wysokoscTxt.getText()));
				}
				catch(NumberFormatException ex)
				{
					Przepona.this.membraneHeight = 150;
				}
				Przepona.this.repaint();
			}
		});
		buttonPanel.add(wysokoscTxt,border);
		
		// y5 z poczatkowy
		zPoczTxt = new JTextField("");
		border.gridx=1;
		border.gridy=5;
		 buttonPanel.add(zPoczTxt,border);
		
		// y6 z koncowy
		zKonTxt = new JTextField("");
	 	border.gridx=1;
		border.gridy=6;
		buttonPanel.add(zKonTxt,border);
		 
		
		//y7 srednica narzedzia
		fiNarzedziaTxt = new JTextField("");
		border.gridx=1;
		border.gridy=7;
		buttonPanel.add(fiNarzedziaTxt,border);
		
		//y8 ap 
		apTxt = new JTextField("");
		border.gridx=1;
		border.gridy=8;
		buttonPanel.add(apTxt,border);
		
		//y9 przybieranie
		pktPrzybrania = new JButton("Wybierz");
		pktPrzybrania.setToolTipText(PRZYBIERANIE_TOOLTIP);
		pktPrzybrania.addActionListener(this);
		border.gridx=1;
		border.gridy=9;
		buttonPanel.add(pktPrzybrania,border);
		
		//y10 obroty
		obrotyTxt = new JTextField("");
		border.gridx=1;
		border.gridy=10;
		buttonPanel.add(obrotyTxt,border);
		
		//y11 posuw
		posuwTxt = new JTextField("");
		border.gridx=1;
		border.gridy=11;
		buttonPanel.add(posuwTxt,border);
		
		//y12 cofnij
		cofnij = new JButton("Cofnij");
		cofnij.addActionListener(this);
		border.gridx=1;
		border.gridy=12;
		buttonPanel.add(cofnij,border);	

		//x2 jednostka
		
		//y0 empty
		//y1 -
		//y2 - 
		
		//y3 szerokosc jednostka
		szerokoscJednostka = new JLabel("mm");
		border.gridx=2;
		border.gridy=3;
		buttonPanel.add(szerokoscJednostka,border);
		
		//y4 wysokosc jednostka
		wysokoscJednostka = new JLabel("mm");
		border.gridx=2;
		border.gridy=4;
		buttonPanel.add(wysokoscJednostka,border);
		
	
		
		// y5 srednica narzedzia
		fiNarzedziaJednostka= new JLabel("mm");
		border.gridx=2;
		border.gridy=7;
		buttonPanel.add(fiNarzedziaJednostka,border);
		
		// y6 ap jednostka
		apJednostka =new JLabel("mm");
		border.gridx=2;
		border.gridy=8;
		buttonPanel.add(apJednostka,border);
		
		//y7 -
	
		
		//y10 obroty jednostka
		obrotyJednostka = new JLabel("1/s");
		border.gridx=2;
		border.gridy=10;
		buttonPanel.add(obrotyJednostka,border);
		
		//y11 posuw jednostka
		posuwJednostka = new JLabel("mm/min");
		border.gridx=2;
		border.gridy=11;
		buttonPanel.add(posuwJednostka,border);
		
		add(buttonPanel,BorderLayout.WEST);
		
		add(visualPanel,BorderLayout.EAST);
		
		
		pack();
	}
	
	class Visual extends JPanel
	{
		boolean drawCordinate = true;
		boolean showDimensions = true;
		boolean showLegend = true;
		
		private JButton showCordinatesButton;
		private JButton showDimensionsButton;
		private JButton showLegendButton;
		private Dimension visualSize;
		
		Visual(Dimension dim)
		{
			this.visualSize=dim;
			setVisible(true);
			setLayout(null);
			setPreferredSize(visualSize);
		
			//uklad wspolrzenych
			showCordinatesButton = new JButton();
			Image icon= new ImageIcon(this.getClass().getResource("/cs.png")).getImage();
			showCordinatesButton.setIcon(new ImageIcon(icon.getScaledInstance(22, 24, Image.SCALE_SMOOTH)));
			showCordinatesButton.setBounds(10, 2, 22, 24);
			showCordinatesButton.addActionListener(n->
			{
				Visual.this.drawCordinate = !drawCordinate;
				Przepona.this.repaint();
			});
			showCordinatesButton.setToolTipText("Poka¿ punkt zerowy uk³adu wspó³rzêdnych");
			add(showCordinatesButton);
			 
			
			//show dimensions button
			showDimensionsButton = new JButton();
			Image icon2= new ImageIcon(this.getClass().getResource("/ruler.png")).getImage();
			showDimensionsButton.setIcon(new ImageIcon(icon2.getScaledInstance(22, 24, Image.SCALE_SMOOTH)));
			showDimensionsButton.setBounds(32, 2, 22, 24);
			showDimensionsButton.addActionListener(n->
			{
				Visual.this.showDimensions=!showDimensions;
				Przepona.this.repaint();
			});
			showDimensionsButton.setToolTipText("Poka¿ wymiary");
			add(showDimensionsButton);
			
			
			//legend
			showLegendButton = new JButton();
			Image icon3= new ImageIcon(this.getClass().getResource("/question-mark.png")).getImage();
			showLegendButton.setIcon(new ImageIcon(icon3.getScaledInstance(22, 24, Image.SCALE_SMOOTH)));
			showLegendButton.setBounds(54, 2, 22, 24);
			showLegendButton.addActionListener(n->
			{
				Visual.this.showLegend=!showLegend;
				Przepona.this.repaint();
			});
			showLegendButton.setToolTipText("Poka¿ objaœnienia");
			add(showLegendButton);
			
			
			repaint();
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			
			EmptyRectangle rectangle = new EmptyRectangle.EmptyRectangleBuilder()
					.xCenter(visualSize.width/2)
					.yCenter(visualSize.height/2)
					.xLength((int)membraneWidth)
					.yLength((int) membraneHeight)
					.thickness(20)
					.build();
			rectangle.draw(g);
			
			
			
			ToolPath path=  new ToolPath(visualSize,new Point(0f,0f));
			path.move(new Point(80f,60f),0);
			path.draw(g);
			path.move(new Point(80f,80f),1);
			path.draw(g);
			path.move(new Point(80f,80f),2,20f,0f);
			path.draw(g);
			
			if(showLegend)
			{
				EmptyRectangle rectangleLegend = new EmptyRectangle.EmptyRectangleBuilder()
					.xCenter(20)
					.yCenter(visualSize.height-50)
					.xLength(10)
					.yLength(10)
					.thickness(15)
					.build();
				rectangleLegend.draw(g);
				g.setColor(Color.black);
				g.drawString("Pole robocze", 40, visualSize.height-45);
			}
			
			if(drawCordinate)
				new DrawCordinateSystem(new Point(Przepona.this.x0,Przepona.this.y0),Color.red).draw(g);
			if(showDimensions && Przepona.this.membraneHeight >20 && Przepona.this.membraneWidth>20)
			{
				new VerticalDimension(new Point(visualSize.width/2f-Przepona.this.membraneWidth/2 +15, visualSize.height/2f-Przepona.this.membraneHeight/2),
						new Point(visualSize.width/2f-Przepona.this.membraneWidth/2 +15, visualSize.height/2f+Przepona.this.membraneHeight/2))
						.draw(g);
				new HorizontalDimension(new Point(visualSize.width/2f-Przepona.this.membraneWidth/2, visualSize.height/2f + Przepona.this.membraneHeight/2 -15),
					new Point(visualSize.width/2f+Przepona.this.membraneWidth/2, visualSize.height/2f + Przepona.this.membraneHeight/2 -15))
					.draw(g);
			}
			
		}
	}
	
	
	class PrzybierzNaRampie extends JFrame
	{
		final Przepona edytor;
		
		
		PrzybierzNaRampie(Przepona edytor)
		{
			this.edytor=edytor;
			setTitle("Przybieraj po rampie");
			setLocationRelativeTo(null);
			setSize(320,100);
			setMinimumSize(new Dimension(320,100));
			setAlwaysOnTop(true);
			setResizable(false);
			setVisible(true);
			this.addWindowListener(new java.awt.event.WindowAdapter()
			{
				@Override
				public void windowClosing(WindowEvent e)
				{
					edytor.p.dispose();
					
					
				}

			});
			
			
			add(new RampaCanvas(this.edytor, this));

		}
	
	}
	

	class PrzybierzWPunkcie extends JFrame
	{
		final Przepona edytor;
		

		
		PrzybierzWPunkcie(Przepona edytor)
		{
			this.edytor=edytor;
			setTitle("Przybieraj Z w punkcie");
			setLocationRelativeTo(null);
			setSize(320,340);
			setMinimumSize(new Dimension(320,340));
			setAlwaysOnTop(true);
			setResizable(false);
			setVisible(true);
			this.addWindowListener(new java.awt.event.WindowAdapter()
			{
				@Override
				public void windowClosing(WindowEvent e)
				{
					edytor.p.dispose();
					
				}
			});
	
			
			add(new PrzybierzCanvas(this.edytor, this));
			
			pack();

			
			revalidate();
			repaint();
			
			
		}		

	}
	
	class RampaCanvas extends JPanel implements ActionListener
	{
		final Przepona edytor;
		final JFrame window;
		final String[] advancingOptions = {"X+","X-","Y+","Y-","X+ i X-","Y+ i Y-"};
		JLabel info;
		JButton ok;
		JComboBox <String> options;
		
		
		RampaCanvas(Przepona edytor, JFrame window)
		{
			this.edytor=edytor;
			this.window=window;
			setSize(300,100);
			setMaximumSize(new Dimension (300,100));
			setLayout(new FlowLayout());

			
			options = new JComboBox<>(advancingOptions);
			info = new JLabel("Kierunek przybierania osi Z");
			ok = new JButton("ok");
			ok.addActionListener(this);
			
			add(info);
			add(options);
			add(ok);
		}
		


		@Override
		public void actionPerformed(ActionEvent e) {
			Object o =e.getSource();
			
			
			if(o==ok)
			{
				String choice = (String) options.getSelectedItem();
				edytor.pktPrzybrania.setText(choice);
				edytor.punktPrzybraniaLabel.setText("Przybieranie w osi Z: Rampa");
				edytor.advancePoint = choice;
				
				
				window.dispose();
			}
			
		}
		
	}
	
	
	
	
	
	
	class PrzybierzCanvas extends JPanel implements ActionListener
	{
		
		final Przepona edytor; 
		final JFrame window;
		
		//bottons
			JToggleButton xZeroYPlus;
			JToggleButton x_plus_y_plus;
			JToggleButton x_plus_y_zero;
			JToggleButton x_plus_y_minus;
			JToggleButton x_zero_y_minus;
			JToggleButton x_minus_y_minus;
			JToggleButton x_minus_y_zero;
			JToggleButton xMinusYPlus;
			JToggleButton x_zero_y_zero;
		 
		 JToggleButton buttons[];
		 
		 
		PrzybierzCanvas(Przepona edytor, JFrame window)	
		{
			
		this.edytor=edytor;
		this.window = window;
			
		
		
		
		setSize(300,300);
		setVisible(true);
		setMinimumSize(new Dimension(300,300));
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(25,25,25,25);
		
		
		buttons = new JToggleButton[9];
		
		constraints.gridx=0;
		constraints.gridy=0;
		xMinusYPlus = new JToggleButton("</");
		xMinusYPlus.setSize(20,20);
		xMinusYPlus.setToolTipText("X -, Y + ");
		xMinusYPlus.addActionListener(this);
		buttons[0]=xMinusYPlus;
		add(xMinusYPlus,constraints);
		
		constraints.gridx=1;
		constraints.gridy=0;
		xZeroYPlus = new JToggleButton("^");
		xZeroYPlus.setSize(20,20);
		xZeroYPlus.setToolTipText("X w osi przepony, Y + ");
		xZeroYPlus.addActionListener(this);
		buttons[1] = xZeroYPlus;
		add(xZeroYPlus,constraints);
		
		constraints.gridy=0;
		constraints.gridx=2;
		x_plus_y_plus = new JToggleButton("\\>");
		x_plus_y_plus.setSize(20,20);
		x_plus_y_plus.setToolTipText("X +, Y + ");
		x_plus_y_plus.addActionListener(this);
		buttons[2] = x_plus_y_plus;
		add(x_plus_y_plus,constraints);
	
		constraints.gridx=2;
		constraints.gridy=1;
	
		x_plus_y_zero= new JToggleButton(">");
		x_plus_y_zero.setSize(20,20);
		x_plus_y_zero.setToolTipText("X +, Y w osi przepony ");
		x_plus_y_zero.addActionListener(this);
		buttons[3] = x_plus_y_zero;
		add(x_plus_y_zero,constraints);
		
		
		
		constraints.gridx=2;
		constraints.gridy=2;
		x_plus_y_minus = new JToggleButton("/>");
		x_plus_y_minus.setSize(20,20);
		x_plus_y_minus.setToolTipText("X +, Y - ");
		x_plus_y_minus.addActionListener(this);
		buttons[4] = x_plus_y_minus;
		add(x_plus_y_minus,constraints);
		
		constraints.gridx=1;
		constraints.gridy=2;
		x_zero_y_minus = new JToggleButton(" V ");
		x_zero_y_minus.setSize(20,20);
		x_zero_y_minus.setToolTipText("X w osi przepony, Y - ");
		x_zero_y_minus.addActionListener(this);
		buttons[5] = x_zero_y_minus;
		add(x_zero_y_minus,constraints);
		
		constraints.gridx=0;
		constraints.gridy=2;
		x_minus_y_minus = new JToggleButton("<\\");
		x_minus_y_minus.setSize(20,20);
		x_minus_y_minus.setToolTipText("X -, Y - ");
		x_minus_y_minus.addActionListener(this);
		buttons[6] = x_minus_y_minus;
		add(x_minus_y_minus,constraints);
		
		constraints.gridx=0;
		constraints.gridy=1;
		x_minus_y_zero = new JToggleButton("<");
		x_minus_y_zero.setSize(20,20);
		x_minus_y_zero.setToolTipText("X -, Y w osi przepony ");
		x_minus_y_zero.addActionListener(this);
		buttons[7] = x_minus_y_zero;
		add(x_minus_y_zero,constraints);
		
		constraints.gridx=1;
		constraints.gridy=1;
		x_zero_y_zero = new JToggleButton("+");
		x_zero_y_zero.setSize(20,20);
		x_zero_y_zero.setToolTipText("X w osi przepony, Y w osi przepony ");
		x_zero_y_zero.addActionListener(this);
		buttons[8] = x_zero_y_zero;
		add(x_zero_y_zero,constraints);
		
		

		
		}

		private String przybierz(ActionEvent e, JFrame p)
		{
			String result ="";
			
			for(int i =0 ; i<9;i++)
			{
				
				if(e.getSource() == buttons[i])
				{
					
					
					switch(i)
					{
					case 0:
						result= "xMinusYPlus";
						break;
					case 1: result = "xZeroYPlus";
						break;
					case 2: result = "x_plus_y_plus";
						break;
					case 3: result = "x_plus_y_zero";
						break;
					case 4: result = "x_plus_y_minus";
						break;
					case 5: result = "x_zero_y_minus";
						break;
					case 6: result = "x_minus_y_minus";
						break;
					case 7: result = "x_minus_y_zero";
						break;
					case 8: result = "x_zero_y_zero";
						break;
					}		
				}
				else
					buttons[i].setSelected(false);
			}
			
	
			p.dispose();
			
			
			return result;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			advancePoint=przybierz(e,window);
			edytor.punktPrzybraniaLabel.setText("Przybieranie w osi Z: Punkt");
			
			Object o = e.getSource();
			
			if(o==this.x_minus_y_minus)
			{
				edytor.pktPrzybrania.setText("X- Y-");
			}
			else if(o==this.xMinusYPlus)
			{
				edytor.pktPrzybrania.setText("X- Y+");
			}
			else if(o==this.x_minus_y_zero)
			{
				edytor.pktPrzybrania.setText("X-, Y w osi");
			}
			else if(o==this.x_plus_y_minus)	
			{
				edytor.pktPrzybrania.setText("X+ Y-");
			}
			else if(o==this.x_plus_y_plus)
			{
				edytor.pktPrzybrania.setText("X+ Y+");
			}
			else if(o==this.x_plus_y_zero)
			{
				edytor.pktPrzybrania.setText("X+, Y w osi");
			}
			else if(o==this.x_zero_y_minus)
			{
				edytor.pktPrzybrania.setText("X w osi, Y-");
			}
			else if (o==this.xZeroYPlus)
			{
				edytor.pktPrzybrania.setText("X w osi, Y+");
			}
			else if (o==this.x_zero_y_zero)
			{
				edytor.pktPrzybrania.setText("X w osi, Y w osi");
			}
			
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.drawLine(60, 75, 225, 75);
			g.drawLine(250, 75, 250, 225);
			g.drawLine(250, 225, 60, 225);
			g.drawLine(60, 225, 60, 75);
	
			
			//os x
			g.drawLine(10, 280, 50, 280);
			g.drawLine(50, 280, 40, 275);
			g.drawLine(50, 280, 40, 285);
			
			g.drawString("x", 60, 285);
			
			//os y
			g.drawLine(10, 280, 10, 240);
			g.drawLine(10, 240, 5, 250);
			g.drawLine(10, 240, 15, 250);
			
			g.drawString("y", 20, 245);
	
		}
		
		
	}
	 
	 private Point advancePoint()
	 {
		 if(advancePoint.equals("xZeroYPlus"))
			 return new Point(x,y+height/2-toolDiameter/2);
		
		 else if(advancePoint.equals("x_plus_y_plus"))
				 return new Point(x+width/2-toolDiameter/2,y+height/2-toolDiameter/2);
		 
		 else if(advancePoint.equals("x_plus_y_zero"))
			 return new Point(x+width/2-toolDiameter/2,y);
		 
		 else if(advancePoint.equals("x_plus_y_minus"))
			 return new Point(x+width/2-toolDiameter/2,y-height/2+toolDiameter/2);
		 
		 else if(advancePoint.equals("x_zero_y_minus"))
			 return new Point(x,y-height/2+toolDiameter/2);
		 
		 else if(advancePoint.equals("x_minus_y_minus"))
			 return new Point(x-width/2+toolDiameter/2,y-height/2+toolDiameter/2);
		 
		 
		 else if(advancePoint.equals("x_minus_y_zero"))
			 return new Point(x-width/2+toolDiameter/2,y);
		 
		 else if(advancePoint.equals("xMinusYPlus"))
			 return new Point(x-width/2+toolDiameter/2,y+height/2-toolDiameter/2);
		 
		 else if(advancePoint.equals("x_zero_y_zero"))
			 return new Point(x,y);
		 
		 else return new Point(x,y);
			 
			 
	 }
	 
	
	
	void generate()
	{ 
		int n=5;
		Point advance = advancePoint();
		
		int toolNumber = edytor.getToolBar().getToolNumber();
		float b = edytor.getToolBar().getRotation();
		
		PrintStream output = new PrintStream(edytor.txt);
		System.setOut(output);
		System.setErr(output);
		
		System.out.printf(Locale.CANADA,"(T%d GLOWICA %.1f)%n", toolNumber , toolDiameter);
		System.out.printf(Locale.CANADA,"N%d T%d M6%n", n=n+5, toolNumber);
		

		sterowanie.przygotowanieUkladuINarzedzia(n, toolNumber, safeRetraction,b,base);
		
		System.out.printf(Locale.CANADA,"N%d S%d M3 %n", n+=5, sprindleSpeed);
		System.out.printf(Locale.CANADA,"N%d M8%n", n+=5);
		

		if(this.advancePoint.equals("xZeroYPlus"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX(), advance.getY()-2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000. %n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(),feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,advance.getX());
				z_pocz-=ap;
				
			}
			if(z_pocz+ap != z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,advance.getX());
	
			}
	
			System.out.printf(Locale.CANADA,"N%d G40 G1 Y%.3f%n",n+=5,  advance.getY()-2);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przepony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
		}	
		else if(this.advancePoint.equals("x_plus_y_plus"))
			{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX()-2, advance.getY()-2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000.%n", n+=5, z_pocz+2) ;
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(),feed);
				while(z_pocz>=z_kon)
				{
					System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
					System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
					System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				
					z_pocz-=ap;
				
				}
				if(z_pocz+ap != z_kon)
				{
					System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);
					System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
					System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
					System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
					
					
				}
				
				
				
				
				System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f Y%.3f%n",n+=5, advance.getX()-2, advance.getY()-2);
				edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
			}
			
		else if(this.advancePoint.equals("x_plus_y_zero"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX()-2, advance.getY());
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000.%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(), feed);
			
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,advance.getY());
				z_pocz-=ap;
			}
			
			if(z_pocz+ap != z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);		
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,advance.getY());
			}
			
			
			
			
			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f%n",n+=5, advance.getX()-2);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
		
		}
		else if(this.advancePoint.equals("x_plus_y_minus"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX()-2, advance.getY()+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000.%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(),feed);
			
			while(z_pocz>=z_kon)
			{
			
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				z_pocz-=ap;
			
			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
			}

			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f Y%.3f%n",n+=5, advance.getX()-2, advance.getY()+2);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
		}	
		else if(this.advancePoint.equals("x_zero_y_minus"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX(), advance.getY()+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(),feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,advance.getX());
				z_pocz-=ap;
			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,advance.getX());
				

			}
			
			
			
		
			System.out.printf(Locale.CANADA,"N%d G40 G1 Y%.3f%n",n+=5, advance.getY()+2);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
		}	
		else if(this.advancePoint.equals("x_minus_y_minus"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX()+2, advance.getY()+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000.%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(),feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
			
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);				
		
				z_pocz-=ap;

			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);			
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
	
			}
		
			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f Y%.3f%n",n+=5, advance.getX()+2, advance.getY()+2);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
		
		}	
		else if(this.advancePoint.equals("x_minus_y_zero"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX()+2, advance.getY());
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000.%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(),feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);				
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);	
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,advance.getY());
				
				z_pocz-=ap;

			}
			
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);				
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);	
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,advance.getY());
	
			}
			
			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f%n",n+=5, advance.getX()+2);
		
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
		}		
		else if(this.advancePoint.equals("xMinusYPlus"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX()+2, advance.getY()-2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000.%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(),feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);		
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);			
		
				z_pocz-=ap;

			}
			
			
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);		
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);			
	
			}
		
			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f%n",n+=5, advance.getX()+2);
		
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
		}
		
		
		
		else if(this.advancePoint.equals("x_zero_y_zero"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, advance.getX()+2, advance.getY()+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000.%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, advance.getX(), advance.getY(),feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);		
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);			
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);	
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,advance.getX());
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,advance.getY());
				
		
				z_pocz-=ap;

			}
			
			
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 Z%.3f%n",n+=5,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);		
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);			
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);	
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,advance.getX());
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,advance.getY());			
	
			}
			System.out.printf(Locale.CANADA,"N%d G0 Z%.3f%n",n+=5,Wind.options.getSafeRetraction());	
			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f Y%.3f%n",n+=5, advance.getX()+2,advance.getY()+2);
		
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z w punkcie X" + advance.getX() + " Y"+ advance.getY() );
		}
		
		
	//Rampa
		
		
		else if(this.advancePoint.equals("X+"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, x, y+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, x, y+toolDiameter/2-height/2,feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f%n",n+=5,x-toolDiameter/2+width/2,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				z_pocz-=ap;
			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f %n",n+=5,x-toolDiameter/2+width/2,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
			
			}
			
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
			System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y);
		
			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f%n",n+=5, x);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z po rampie w kierunku X+");
			
		}
		else if(this.advancePoint.equals("X-"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, x, y+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, x, y+toolDiameter/2-height/2,feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f%n",n+=5,x+toolDiameter/2-width/2,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				z_pocz-=ap;
			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f%n",n+=5,x+toolDiameter/2-width/2,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);

			}
			
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
			System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
			System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y);

			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f%n",n+=5, x);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z po rampie w kierunku X-");
			
			
			
			
			
			
			
		}
		else if(this.advancePoint.equals("Y+")) 
		{	
			
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, x, y+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, x, y+toolDiameter/2-height/2,feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f%n",n+=5,y-toolDiameter/2+height/2,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				z_pocz-=ap;
			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f%n",n+=5,y-toolDiameter/2+height/2,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
	
			}
			
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
			System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x);
			

			System.out.printf(Locale.CANADA,"N%d G40 G1 Y%.3f%n",n+=5, y);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z po rampie w kierunku Y+");
			
			
			
		}
		else if(this.advancePoint.equals("Y-"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, x, y+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, x, y+toolDiameter/2-height/2,feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f%n",n+=5,y+toolDiameter/2-height/2,z_pocz);
				z_pocz-=ap;
			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f%n",n+=5,y+toolDiameter/2-height/2,z_kon);
	
			}
			
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
			System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
			System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x);

			System.out.printf(Locale.CANADA,"N%d G40 G1 Y%.3f%n",n+=5, y);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z po rampie w kierunku Y-");
			
			
			
		}
		else if(this.advancePoint.equals("X+ i X-"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, x, y+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, x, y+toolDiameter/2-height/2,feed);
			
			while(z_pocz>=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f%n",n+=5,x-toolDiameter/2+width/2,z_pocz+ap/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f%n",n+=5,x+toolDiameter/2-width/2,z_pocz);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				z_pocz-=ap;
			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f%n",n+=5,x-toolDiameter/2+width/2,z_kon+ap/4);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f Z%.3f%n",n+=5,x+toolDiameter/2-width/2,z_kon);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
		
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				
			}
			else 
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
				
			}
			
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
			System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y);
		
			System.out.printf(Locale.CANADA,"N%d G40 G1 X%.3f%n",n+=5, x);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z po rampie w kierunkach X+ i X-");
			
			
			
			
		}
		else if(this.advancePoint.equals("Y+ i Y-"))
		{
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f%n",n+=5, x, y+2);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.3f F10000%n", n+=5, z_pocz+2);
			System.out.printf(Locale.CANADA,"N%d G41 G1 X%.3f Y%.3f F%.1f%n",n+=5, x, y+toolDiameter/2-height/2,feed);
			
			while(z_pocz>=z_kon)
			{

				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f%n",n+=5,y-toolDiameter/2+height/2,z_pocz+ap/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f%n",n+=5,y+toolDiameter/2-height/2,z_pocz);
				z_pocz-=ap;
			}
			if(z_pocz+ap!=z_kon)
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f%n",n+=5,y-toolDiameter/2+height/2,z_kon+ap/4);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f Z%.3f%n",n+=5,y+toolDiameter/2-height/2,z_kon);
				
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
	
			}
			else 
			{
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x-toolDiameter/2+width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y-toolDiameter/2+height/2);
				System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x+toolDiameter/2-width/2);
				System.out.printf(Locale.CANADA,"N%d G1 Y%.3f%n",n+=5,y+toolDiameter/2-height/2);
	
			}
			
			System.out.printf(Locale.CANADA,"N%d G1 X%.3f%n",n+=5,x);
			System.out.printf(Locale.CANADA,"N%d G40 G1 Y%.3f%n",n+=5, y);
			edytor.writelog("Wygenerowano frezowanie przepony dla danych: %nŒrodek symetrii przerpony X" + x + " Y"+ y + "%nWymiary przepony: "+ width + " na " +height + "%nŒrednica u¿ytego narzêdzia: " + toolDiameter +"mm"+ "%nPrzybieranie w osi Z po rampie w kierunku Y+");

		}

		
		else JOptionPane.showMessageDialog(this.edytor, "Nieprzewidziana sytuacja - to na pewno wina Skoczylasa!", "B³¹d", JOptionPane.ERROR_MESSAGE);
		
		System.out.printf(Locale.CANADA,"N%d G0 Z%.1f M9%n",n+=5,400.f);
		System.out.printf(Locale.CANADA,"N%d M5%n",n+=5);
		System.out.printf(Locale.CANADA,"N%d M1%n",n+=5);	
		
		Wind.log.writeInfoLog("przepona - wykonano", Przepona.class.getSimpleName());
	
	}
	
	
	
	
	
	
	
	private boolean isOk()
	{
		boolean result = true;
		
		//point
		
			if(this.advancePoint==null)
			{
				JOptionPane.showMessageDialog(this, "Musisz zdefiniowaæ punkt w którym bêdzie przybierana oœ Z", "B³¹d", JOptionPane.OK_OPTION);
				
				return false;
			}

		
		//x
		try 
		{
			this.x=Float.parseFloat(xSrodkaTxt.getText());			
		}
		catch(NumberFormatException e)
		{		
			
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana wartoœæ wspó³rzêdnej x œrodka przepony", "B³¹d", JOptionPane.OK_OPTION);
			return false;
		}
		
		//y
		
				try 
				{
					this.y=Float.parseFloat(ySrodkaTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowana wartoœæ wspó³rzêdnej y œrodka przepony", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}
		
			//height
				
				
				try 
				{
					this.height=Float.parseFloat(this.wysokoscTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowana wartoœæ wysokoœci przepony", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}
				
		//width
	
				try 
				{
					this.width=Float.parseFloat(this.szerokoscTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowana wartoœæ szerokoœci przepony", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}
		//fi narzedzia
				try 
				{
					this.toolDiameter=Float.parseFloat(this.fiNarzedziaTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowana Srednica narzedzia", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}
		//ap

				
				
				try 
				{
					this.ap=Float.parseFloat(this.apTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowana wartoœæ ap", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}
		//speed
				if(Function.covertVcToN(this.obrotyTxt, this.toolDiameter)!=0) return false;
				try 
				{
					this.sprindleSpeed=Integer.parseInt(this.obrotyTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowana prêdkoœæ obrotowa wrzeciona", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}
		//feed
				if(Function.covertFnToVf(this.posuwTxt, this.sprindleSpeed)!=0) return false;
				try 
				{
					this.feed=Float.parseFloat(this.posuwTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowana wartoœæ posuwu", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}

				
			// z kon
				try 
				{
					this.z_kon=Float.parseFloat(this.zKonTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowany wspó³rzêdna Z koñcowa", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}
				
				
			//z kon	
				try 
				{
					this.z_pocz=Float.parseFloat(this.zPoczTxt.getText());			
				}
				catch(NumberFormatException e)
				{		
					
					JOptionPane.showMessageDialog(edytor, "Zle zdefiniowana wspó³rzêdna Z poczatkowa ", "B³¹d", JOptionPane.OK_OPTION);
					return false;
				}
				
				//tooldiameter <0
				if(toolDiameter <1)
				{
					JOptionPane.showMessageDialog(edytor,"Zbyt ma³a œrednica narzêdzia", "B³¹d",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				// tooldiameter > height
				
				if(toolDiameter > height)
				{
					JOptionPane.showMessageDialog(edytor,"Œrednica narzêdzia jest wiêksza od wysokoœci przepony", "B³¹d",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				// toolDiameter > width
				if(toolDiameter > width)
				{
					JOptionPane.showMessageDialog(edytor,"Œrednica narzêdzia jest wiêksza od szerokoœci przepony", "B³¹d",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				//zpocz < z_kon
				
				if(z_pocz < z_kon)
				{
					JOptionPane.showMessageDialog(edytor,"Z pocz¹tkowy jest mniejszy od Z koñcowego", "B³¹d",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				if((width-toolDiameter<=0) || (height-toolDiameter<=0) )
				{
					JOptionPane.showMessageDialog(this, "Zbyt ma³a œrednica narzêdzia");
					return false;
				}

				return result;
	}
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		
		if(o==oblicz)
		{
			if(isOk())
			{		
				if(z_pocz >z_kon)
				{
					generate();
					this.cofnij.doClick();
				}
				else
					JOptionPane.showMessageDialog(edytor, "Wspó³rzêdna pocz¹tkowa Z powinna byæ wiêksza od koñcowej", "B³¹d", JOptionPane.OK_OPTION);
			}
			
		}
		else if(o==pktPrzybrania)
		{
			
			Object []options= {"w punkcie","po rampie","confij"};
			int answer = JOptionPane.showOptionDialog(this, "Przybieraj ", "Wybierz sposób zag³êbiania siê w osi Z", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			
			
			if(answer ==0)
				p = new PrzybierzWPunkcie(this);
			else if(answer == 1)
				p= new PrzybierzNaRampie(this);
			else 
				{
					p=null;
				}
			
		}
		else if(o== cofnij)
		{
			if(this.p!=null)
			{
				
			}
			
			this.edytor.przep = null;
			this.dispose();
	
		}
		
	}
	
}
