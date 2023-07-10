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
import DrawFunction.DrawTool;
	
	public class Czop extends JFrame
	{	
		 Edytor parent;
		 
		 Sterowanie sterowanie;
		
		JLabel wspolrzedneXLabel;
		JLabel wspolrzedneYLabel;
		JLabel rodzajSpirali;			//	wew/zew
		JLabel srednicaNarzedziaLabel;
		JLabel srednicaOtworuLabel;
		JLabel zWejsciaLabel;
		JLabel zDnaLabel;
		JLabel posowLabel;
		JLabel obrotyLabel;
		JLabel krokLabel;
		
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
		
		JComboBox<String> rodzajCombo;

		
		float safeRetraction;
		
		
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
		
		static final String[] METODA_FREZOWANIA= {"Spiralnie", "Okrag-zag³ebienie"};
		
		
		
		
		
		Czop(Edytor parent)
		{
			setSize(450,600);
			setTitle("Frezuj Czop");
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setVisible(true);
			setLocationRelativeTo(null);
			
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
			border.insets = new Insets(15,5,15,5);
			border.weightx =0;
			border.weighty=0;
			border.anchor = GridBagConstraints.NORTHWEST;
			border.fill =GridBagConstraints.BOTH;
			
			//combo box
			rodzajCombo = new JComboBox<>(METODA_FREZOWANIA);
			
			
			// labels
			 wspolrzedneXLabel =  new JLabel("Wspó³rzêdna x czopu");
			 wspolrzedneYLabel = new JLabel("Wspó³rzêdna y czopu ");
			 srednicaNarzedziaLabel = new JLabel("Srednica narzêdzia ");
			 srednicaOtworuLabel = new JLabel("Srednica czopu ");
			 zWejsciaLabel= new JLabel("Z poczatkowe ");
			 zDnaLabel = new JLabel("Z dna ");
			 krokLabel = new JLabel("G³êbokoœæ skrawania ap");
			 posowLabel = new JLabel("Posuw");
			 posowLabel.setToolTipText("Prêdkoœæ posuwu Vf w mm/min. Mo¿na podac posow na zab i liczbe zebow w celu obliczenia predkosci posuwu wg schematu fn=wartosc z=wartosc");
			 obrotyLabel = new JLabel("Obroty");
			 obrotyLabel.setToolTipText("Prêdkoœæ obrotowa wrzeciona. Mozna podac predkosc skrawania w celu obliczenia obrotow wg formatu: Vc=wartosc");
			 rodzajSpirali = new JLabel("Metoda");
			 rodzajSpirali.setToolTipText("Zaprogramuj œcie¿kê spiralnie wokó³ czopa / œcie¿ka sk³adaj¹ca siê z okrêgów na osi Z");
			 
			// buttons 
			 oblicz = new JButton("Generuj");
			 oblicz.setToolTipText("Generuj program dla frezowania czopa na koñcu pola tekstowego");
			 oblicz.setSize(75, 75);
			 oblicz.addActionListener(e->oblicz());
			 cofnij = new JButton("Cofnij");
			 cofnij.setToolTipText("Powrót do poprzedniego okna");
			 cofnij.setSize(75, 75);
			 cofnij.addActionListener(e ->{ 
				 parent.spiral=null;
					this.dispose();	 
			 } 
			 );
			 
			 
			 border.gridx = 0;
			 border.gridy = 0;
			 add(wspolrzedneXLabel,border);
			 
			 border.gridy = 1;
			 add(wspolrzedneYLabel,border);
			 
			 border.gridy=2;
			 add(rodzajSpirali,border);
			 
			 border.gridy =3; 
			 add(srednicaNarzedziaLabel,border);
			 
			 border.gridy =4;
			 add(srednicaOtworuLabel,border);
			 
			 border.gridy = 5;
			 add(zWejsciaLabel,border);
			 
			 border.gridy = 6;
			 add(zDnaLabel,border);
			 
			 border.gridy=7;
			 add(krokLabel,border);
			 
			 border.gridy = 8;
			 add( posowLabel,border);
			 
			 border.gridy = 9;
			 add(obrotyLabel,border);
			 
			
			//txt
			 wspolrzedneXTxt = new JTextField("");
			 wspolrzedneXTxt.setSize(75,50);
			 wspolrzedneYTxt = new JTextField("");
			 wspolrzedneYTxt.setSize(75,50);
			 srednicaNarzedziaTxt = new JTextField("");
			 srednicaNarzedziaTxt.setSize(75,50);
			 srednicaOtworuTxt = new JTextField("");
			 srednicaOtworuTxt .setSize(75,50);
			 zWejsciaTxt = new JTextField("");
			 zWejsciaTxt.setSize(75,50);
			 zDnaTxt = new JTextField("");
			 zDnaTxt.setSize(75,50);
			 krokTxt = new JTextField("");
			 krokTxt.setSize(75,50);
			 posowTxt = new JTextField("");
			 posowTxt.setSize(75,50);
			 obrotyTxt = new JTextField("");
			 obrotyTxt.setSize(75,50);
			
			 border.gridx= 1;
			 border.gridy=0; 
			 add(wspolrzedneXTxt,border);
			 
			 border.gridy=1;
			 add(wspolrzedneYTxt,border);
			 
			 border.gridy = 3;
			 add(srednicaNarzedziaTxt,border);
			 
			 border.gridy = 4;
			 add(srednicaOtworuTxt,border);
			 
			 border.gridy = 5;
			 add(zWejsciaTxt,border);
			 
			 border.gridy = 6;
			 add(zDnaTxt,border);
			 
			 border.gridy=7;
			 add(krokTxt,border);
			 
			 border.gridy = 8;
			 add(posowTxt,border);
			 
			 border.gridy = 9;
			 add(obrotyTxt,border);
			 
			 border.gridx =0;
			 border.gridy=10;
			 add(oblicz,border);
			 
			 border.weightx=0;
			 border.gridx =1;
			 add(cofnij,border);
			 
			 //combo
			 border.gridx=1;
			 border.gridy=2;
			 add(rodzajCombo,border);
			 
			 
			 //rys
			 border.gridx=2;
			 border.gridy=0;
			 border.gridheight=10;
			 border.weightx=0.9;
			 border.weighty=0.5;
			 add(new canv(),border);
			 

		}
		
		
		
		class canv extends JPanel
		{
			
			
			canv()
			{
				setMinimumSize(new Dimension(250,600));
				setVisible(true);
			
			
			
				repaint();
				
			}
			
			
			
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				
				//okrag zew
				g.setColor(Color.GRAY);
				g.fillArc(20, 20, 80, 80, 0, 360);	
				
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
				
				//linia przerywana pionowa y
				g.drawString("X", 14, 55);
				for(int i=0;i<=88;)
				{
					g.drawLine(i+17,60,i+21,60);
					i+=8;	
				}
				//linia przerywana pozioma
				g.drawString("Y", 61, 8);
				for(int i=0;i<=88;i+=8)
				{
					g.drawLine(60,i+17,60,i+21);

				}
				
				//pozioma linia wymiarowa
				g.drawString("d",60,125);
				g.drawLine(20, 130, 100,130);
				g.drawLine(20, 128, 20, 132);
				g.drawLine(100, 128, 100, 132);
				
				
				//schemat ruchu narzêdzia
				g.setColor(Color.RED);
				g.drawArc(10, 10, 100, 100, 0, 180);	
				g.drawArc(10, 10, 100, 100, 0, -159);
				
				//strza³ka
				g.drawLine(12, 75, 19, 80);
				g.drawLine(12, 75, 12, 83);
				
				//ruch narzêdzia
				g.drawString("Œcie¿ka narzêdzia", 10, 195);
				g.drawLine(30, 175, 70, 175);
				g.drawLine(70, 175, 66, 171);
				g.drawLine(70, 175, 66, 179);	

			}
				
			}
		
		
		
		
		

		
		void generateCircles(float x, float y, float start,float bottom, float toolDiameter, float holeDiameter, float step, int sp_speed, float feed)
		{
			
			if(parent.getControls().equals(null))
			{
				JOptionPane.showMessageDialog(this, "Wybierz sterowanie w menu opcje", "B³ad", JOptionPane.NO_OPTION);
				
			}
			else if(step==0)
			{
				JOptionPane.showMessageDialog(this, "Skok nie mo¿e byæ równy 0", "B³ad", JOptionPane.NO_OPTION);
				
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
				
				
				
				System.out.println("N10 G0 X" + (leftX-5 -1 ) + " Y"+ y + " S" + sp_speed + " M3");
				System.out.println("N15 G1 Z"+ (start+5) + " F10000. M8");
				if(parent.getControls().isType(ControlTypes.OKUMA))
					System.out.println("N20 G1 G41 " + toolNumber+" X"+ (leftX-5) +" F2000.");
				else
					System.out.println("N20 G1 G41 "+ "D" + toolNumber+" X"+ (leftX-5) +" F2000.");
				
				
				
		
				int n = 25;
				
				while(start>bottom)
				{
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G1 Z%.3f %n", n, start );
					//increment 'start' towards bottom
					start -= step;
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f Y%.3f I2.5 J0. F%.1f %n",n,leftX,y,feed );
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f I%.2f J0. %n", n, -leftX, y,  i );
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f I%.2f J0. %n", n, leftX, y,  -i );
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G3 X%.2f Y%.2f I%.2f J0. %n", n, leftX-5, y, -2.5 );
				}
				
					//last pass
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G1 Z%.3f %n", n, bottom );
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G3 X%.3f  Y%.3f I2.5 J0. F%.1f %n",n,leftX,y,feed );
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f I%.2f J0. %n", n, -leftX, y,  i );
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f I%.2f J0. %n", n, leftX, y,  -i );
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G3 X%.2f Y%.2f I%.2f J0. %n", n, leftX-5, y, -2.5 );

				
				
				
				
		
				
				System.out.printf(Locale.CANADA,"N%d G1 G40 X%.3f Y%.3f%n",n+=5,leftX-5-2,(y-1));
				System.out.printf(Locale.CANADA,"N%d G0 Z%.1f M9%n",n+=5,safeRetraction);
				System.out.printf("N%d M5%n",n+=5);
				System.out.printf("N%d M1%n",n+=5);
				
			}
			
			
			
		}

		void generateOuterSpiralInterpolation(float x, float y, float start,float bottom, float toolDiameter, float holeDiameter, float step, int sp_speed, float feed)
		{
			
			if(parent.getControls().equals(null))
			{
				JOptionPane.showMessageDialog(this, "Wybierz sterowanie w menu opcje", "B³ad", JOptionPane.NO_OPTION);
				
			}
			else if(step==0)
			{
				JOptionPane.showMessageDialog(this, "Skok nie mo¿e byæ równy 0", "B³ad", JOptionPane.NO_OPTION);
				
			}
			else{
			
				float rotationB = this.parent.getToolBar().getRotation();
				int toolNumber = this.parent.getToolBar().getToolNumber();
				float leftX = x -(toolDiameter/2 + holeDiameter/2);
				float i = toolDiameter/2 + holeDiameter/2;
				String base = this.parent.getToolBar().getBase();
						
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
				
				System.out.println("N25 G3 X"+ leftX + " Y"+ y + " I5.0 J0. F"+feed +"\n");
				
		
				int n = 25;
				
				while(start >=bottom)
				{
					n+=5;
					System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f Z%.2f I%.2f J0. %n", n, leftX, y, start, i );
					start -= step;
				
				}
				if(Math.abs(start+step-bottom)>0.1)
				{
					System.out.printf(Locale.CANADA,"N%d G2 X%.2f Y%.2f Z%.2f I%.2f J0. %n", n, leftX, y, bottom, i );
				}
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
				JOptionPane.showMessageDialog(this, "Nieprawid³owy format danych wejœciowych", "B³¹d", JOptionPane.NO_OPTION);
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
				JOptionPane.showMessageDialog(this, "Nieprawid³owy format danych wejœciowych", "B³¹d", JOptionPane.NO_OPTION);
				return null; 
				
			}
		}
		
		
		
		
		private void oblicz()
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
				if(holeDiameter<toolDiameter && (this.rodzajCombo.getSelectedIndex()==0))
					JOptionPane.showMessageDialog(this, "Srednica narzedzia musi byæ mniejsza od otworu!", "Blad", JOptionPane.NO_OPTION);
				else if(start<bottom)
					JOptionPane.showMessageDialog(this, "Wspolrzedna dna otworu musi byæ mniejsza od wspó³rzêdnej Z pocz¹tkowej spirali", "B³¹d", JOptionPane.NO_OPTION);
				else if(spSpeed>8000)
				{
					JOptionPane.showMessageDialog(this, "Nie za wysokie te obroty?", "Bum", JOptionPane.NO_OPTION);
				}
				else if(feed>12000 || feed <10)
				{
					JOptionPane.showMessageDialog(this, "Coœ podejrzany ten posuw, wez to sprawdz lepiej", "???", JOptionPane.NO_OPTION);
				}
				else
				{
					
			
					
					
						if(this.rodzajCombo.getSelectedIndex()==1) {
							generateCircles(x, y, start, bottom,  toolDiameter, holeDiameter,step, spSpeed, feed);
							parent.writelog("Wygenerowano interpolacje spiralna dla otworu o wspo³rzêdnych X=" +x + " Y=" + y + " Zpocz=" +start+" Zkonc="+ bottom+".\n\tSrednica frezowanego otworu: "+ holeDiameter +"mm "+ " Srednica wykorzystanego narzedzia:"+toolDiameter + "mm"+"\n\tAp="+step+"mm"+"\n\tS"+spSpeed+" 1/s"+" F"+feed+"mm/min");
							Wind.log.writeInfoLog("Spiral inner - wygenerowano", Spiral.class.getSimpleName());
						}
						else if(this.rodzajCombo.getSelectedIndex()==0) {
							generateOuterSpiralInterpolation(x, y, start, bottom,  toolDiameter, holeDiameter,step, spSpeed, feed);
							parent.writelog("Wygenerowano interpolacje spiralna wokó³ trzpienia o wspo³rzêdnych X=" +x + " Y=" + y + " Zpocz=" +start+" Zkonc="+ bottom+".\n\tSrednica frezowanego trzpienia: "+ holeDiameter +"mm "+ " Srednica wykorzystanego narzedzia:"+toolDiameter + "mm"+"\n\tAp="+step+"mm"+"\n\tS"+spSpeed+" 1/s"+" F"+feed+"mm/min");
							Wind.log.writeInfoLog("Spiral outer - wygenerowano", Spiral.class.getSimpleName());
						}
				
						parent.czop=null;
						this.dispose();
				}
				}
				}
			}
		}
			
}

	
	

