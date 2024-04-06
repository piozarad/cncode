package cncCodeGeneratingFunctions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import BasicControls.Sterowanie;
import CordConverter.ControlTypes;
import CordConverter.Edytor;
import CordConverter.Point;
import CordConverter.TYPE;
import CordConverter.Wind;
import DrawFunction.DrawAbove;
import DrawFunction.DrawCordinateSystem;
import DrawFunction.Hole;

public class Drilling extends JFrame {

	Edytor parent;
	 static final String []types = {"G81 Wiercenie proste", "G82 Poglebienie/nawiercenie", "G83 Wiercenie glebokie", "G76/G1 Wytaczanie","G84 Gwintowanie" ,"G85 Rozwiercanie"};
	
	//JLabels
	JLabel typeLabel;
	JLabel obrotyLabel;
	JLabel posuwLabel;
	JLabel xWierceniaLabel;
	JLabel yWierceniaLabel;
	JLabel zPodjazdLabel;
	JLabel zWierceniaLabel;
	JLabel dodatkowyParametrLabel;
	
	//comboBox
	JComboBox<String> typeCombo;
	
	//JTExtFields
	JTextField obrotyTxt;
	JTextField posowTxt;
	JTextField xWierceniaTxt;
	JTextField yWierceniaTxt;
	JTextField zPodjazdTxt;
	JTextField zWierceniaTxt;
	JTextField dodatkowyParametrTxt;
	
	//buttons 
	JButton oblicz;
	JButton cofnij;
	JButton dodajWiecejOtworow;
	JButton usunOstatniButton;
	JButton podglad;
	JButton katPromienXY;
	
	
	//JRadio Button
	JRadioButton bezpiecznyOdjazdRadio;
	JRadioButton wiercZDwochStron;
	
	
	//variables
	Float x;
	Float y;
	Float z;
	Float r;
	Float feed;
	Float Q;
	int S;
	boolean angleRadius = false;
	
	
	//JList
	JList<Point> pointList;
	DefaultListModel<Point> model;
	
	
	int toolNumber;
	float rotationB;
	String base;
	
	boolean wiecejOtworow = false;
	
	transient Sterowanie sterowanie;
	
	private static final Dimension BUTTON_SIZE = new Dimension(170,25);
	
	
	public Drilling(Edytor parent)
	{
		
		this.parent=parent;
		this.sterowanie=parent.getControls();
		this. toolNumber = parent.getToolBar().getToolNumber();
		this. rotationB = parent.getToolBar().getRotation();
		this.base = parent.getToolBar().getBase();
		setSize(350,500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Generuj kod wiercenia");
		setVisible(true);
		setLocationRelativeTo(null);

		
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
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints border = new GridBagConstraints();
		
		border.gridx=0;
		border.gridy=0;
		border.insets.set(10, 10, 10, 10);
		border.weightx = 0.05;
		border.weighty = 0.5;
		border.fill = GridBagConstraints.BOTH;
		
		//labels
		typeLabel = new JLabel("Typ");
		typeLabel.setToolTipText("Wybierz cykl w okienku po prawej");
		add(typeLabel,border);
		
		//obroty
		border.gridy++;
		obrotyLabel = new JLabel("Obroty");
		obrotyLabel.setToolTipText("Predkosc obrotowa wrzeciona");
		add(obrotyLabel,border);
		
		//posuw
		border.gridy++;
		posuwLabel = new JLabel("Posuw");
		posuwLabel.setToolTipText("Posuw w mm/min. Jesli zostanie podany w mm/obr przeliczy automatycznie na mm/min");
		add(posuwLabel,border);
		
		
		//zsz
		border.gridy++;
		katPromienXY = new JButton("Wspolrzedne");
		katPromienXY.setToolTipText("Definiuj polozenie przez wspólrzedne lub kst i promien");
		add(katPromienXY,border);
		
		katPromienXY.addActionListener(p->{
		if(angleRadius)
		{
			xWierceniaLabel.setText("Wspolrzedna x");
			xWierceniaLabel.setToolTipText("Wspolrzedna x wierconego otworu");
			
			yWierceniaLabel.setText("Wspolrzedna y");
			yWierceniaLabel.setToolTipText("Wspolrzedna y wierconego otworu");
			katPromienXY.setText("Wspolrzedne");
			angleRadius=false;
		}
		else
		{
			xWierceniaLabel.setText("Promien");
			xWierceniaLabel.setToolTipText("Promien od sœrodka ukladu wspolrzednych do osi otworu");
			
			yWierceniaLabel.setText("Kat");
			yWierceniaLabel.setToolTipText("Kat mierzony od osi 0X do osi otworu");
			katPromienXY.setText("Promien-kat");
			angleRadius=true;
		}
			
		});
		
		
		//x
		border.gridy++;
		xWierceniaLabel = new JLabel("Wspolrzedna x");
		xWierceniaLabel.setToolTipText("Wspolrzedna x wierconego otworu");
		add(xWierceniaLabel,border);
		
		//y
		border.gridy++;
		yWierceniaLabel = new JLabel("Wspolrzedna y");
		yWierceniaLabel.setToolTipText("Wspolrzedna y wierconego otworu");
		add(yWierceniaLabel,border);
		
		//podjazd
		border.gridy++;
		zPodjazdLabel = new JLabel("Z poczatkowa");
		zPodjazdLabel.setToolTipText("<html> Wspolrzedna z do ktorej narzedzie podjezdza ruchem szybkim i zaczyna wierenie z zadanym posuwem. <br>Wspolrzedna musi znajdowac sie poza materialem </html>");
		add(zPodjazdLabel,border);
		
			//Z wiercenia
		border.gridy++;
		zWierceniaLabel= new JLabel("Z dna otworu");
		zWierceniaLabel.setToolTipText("<html>Z dna wierconego otworu. <br>Aby otrzymac wymagana glebokosc otworu mierzac wiertlo od czubla nalezy poglebic otwor o 0.3xsrednica wiertla </html>");
		add(zWierceniaLabel,border);
		
			//dodatkowy parametr
		border.gridy++;
		dodatkowyParametrLabel = new JLabel("Dodatkowe parametry");
		dodatkowyParametrLabel.setToolTipText("Parametr zalezny od wybranego cyklu ");
		add(dodatkowyParametrLabel,border);
		
			//oblicz button
		border.gridy++;
		oblicz = new JButton("Generuj");
		oblicz.setToolTipText("Generuj kod dla podanych parametrow");
		oblicz.setPreferredSize(BUTTON_SIZE);
		oblicz.addActionListener(e->{
			if(typeCombo.getSelectedIndex()==0 && wiercZDwochStron.isSelected() && getValues())
			{
				drillTwoSides();
				parent.writelog("Wygenerowano cykl staly dla glebokiego otworu - wiercenie z obu stron");
				this.cofnij.doClick();
			}
			else{
				if(wiecejOtworow)
				{
					if(getValues())
					{
						drillMultipleHoles();
						this.cofnij.doClick();
						parent.writelog("Wygenerowano cykl staly dla grupy otworów");
					}
				
				}
				else
				{
					if(getValues())
					{
						drill();
						parent.writelog("Wygenerowano cykl staly");
						this.cofnij.doClick();
					}
				}
				}

			Wind.log.writeInfoLog("wiercenie - wykonano", Drilling.class.getSimpleName());
			
		});
		add(oblicz,border);

		// grid x=1
		//combo type
		border.gridx=1;
		border.gridy=0;
		typeCombo = new JComboBox<>(types);
		typeCombo.setPreferredSize(BUTTON_SIZE);
		typeCombo.addActionListener(e->{
			int i= typeCombo.getSelectedIndex();
			
			if(i==0)
			{
				dodatkowyParametrLabel.setText("Dodatkowy parametr");
				dodatkowyParametrLabel.setToolTipText("Cykl G81 - brak dodatkowych paremetrow");
				dodatkowyParametrTxt.setText("");
				dodatkowyParametrTxt.setEditable(false);
				wiercZDwochStron.setEnabled(true);
			}
			else if(i==1)
			{
				dodatkowyParametrLabel.setText("Dodatkowy parametr");
				dodatkowyParametrLabel.setToolTipText("<html>Cykl G82 - brak dodatkowych paremetrow<br><Postój czasowy na dnie otworu liczony jest automatycznie dla 1.5 obrotu narzedzia na dnie otworu</html>");
				dodatkowyParametrTxt.setText("");
				dodatkowyParametrTxt.setEditable(false);
				wiercZDwochStron.setEnabled(false);
			}
			else if(i==2)
			{
				dodatkowyParametrLabel.setText("Glebokoœsc zaglebiania Q");
				dodatkowyParametrLabel.setToolTipText("Wiertlo bedzie sie zaglebialo o podan¹ wartoœc za kazdym razem nastepnie wyjezdzalo do punktu pocz¹tkowego przed material az do zakoñczenia wiercenia");
				dodatkowyParametrTxt.setEditable(true);
				wiercZDwochStron.setEnabled(false);
			}
			else if(i==3)
			{
				dodatkowyParametrLabel.setText("Odskok Q");
				dodatkowyParametrLabel.setToolTipText("<html>Na dnie otworu wrzeciono zatrzymuje sie i odsuwa o podan¹ wartoœc aby nie rysowac materialu podczas wyjazdu <br>Kierunek odjazdu zalezy od wybranego sterowania, dla Fanuca jest to -Y dla Sinumerica X+ </html>");
				dodatkowyParametrTxt.setEditable(true);
				wiercZDwochStron.setEnabled(false);
			}
			else if(i==4)
			{
				dodatkowyParametrLabel.setText("Dodatkowy parametr");
				dodatkowyParametrLabel.setToolTipText("Cykl G84 - brak dodatkowych paremetrow");
				dodatkowyParametrTxt.setText("");
				dodatkowyParametrTxt.setEditable(false);
				wiercZDwochStron.setEnabled(false);
			}
			else if(i==5)
			{
				dodatkowyParametrLabel.setText("Dodatkowy parametr");
				dodatkowyParametrLabel.setToolTipText("Cykl G85 - brak dodatkowych paremetrow");
				dodatkowyParametrTxt.setText("");
				dodatkowyParametrTxt.setEditable(false);
				wiercZDwochStron.setEnabled(false);
			}
		});
		add(typeCombo,border);
		
		//obroty txt
		border.gridy++;
		obrotyTxt = new JTextField("");
		add(obrotyTxt,border);
		
		//posuw txt
		border.gridy++;
		posowTxt = new JTextField("");
		add(posowTxt,border);
		
		//x wiercenia
		border.gridy+=2;
		xWierceniaTxt = new JTextField("");
		add(xWierceniaTxt,border);
		
		//y wiercenia txt
		border.gridy++;
		yWierceniaTxt = new JTextField("");
		add(yWierceniaTxt,border);
		
		// z dojazd
		border.gridy++;
		zPodjazdTxt = new JTextField("");
		add(zPodjazdTxt,border);
		
		// z dno otworu
		border.gridy++;
		zWierceniaTxt = new JTextField("");
		add(zWierceniaTxt,border);
		
		//dodatkowy paramwtr txt
		border.gridy++;
		dodatkowyParametrTxt = new JTextField("");
		dodatkowyParametrTxt.setEditable(false);
		add(dodatkowyParametrTxt,border);
		
		//cofnij
		border.gridy++;
		cofnij = new JButton("Cofnij");
		cofnij.addActionListener(e->{
			this.parent.w=null;
			dispose();
		});
		cofnij.setPreferredSize(BUTTON_SIZE);
		add(cofnij,border);
		
		//grid x= 2
		border.gridx=2;
		border.gridy=4;
		border.weightx =0;
		border.gridheight =2;
		dodajWiecejOtworow = new JButton(">");
		dodajWiecejOtworow.setSize(15,150);
		dodajWiecejOtworow.setToolTipText("Dodaj wiecej otworow do cyklu");
		dodajWiecejOtworow.addActionListener(e->{
			wiecejOtworow = true;
			usunOstatniButton.setEnabled(true);
			if(getPoint())
			{
				if(!angleRadius)
				{
					model.addElement(new Point(x,y,TYPE.XY_POINT));
				}
				else 
				{	
					Point point = new Point(x,y,TYPE.RADIUS_ANGLE_POINT);
					
					model.addElement(point);
				}
				pointList.setModel(model);
				this.xWierceniaTxt.setText("");
				this.yWierceniaTxt.setText("");
			}	
		});
		add(dodajWiecejOtworow,border);
		
		border.gridy=6;
		podglad = new JButton();
		podglad.setSize(15, 15);
		Image icon= new ImageIcon(this.getClass().getResource("/lupa.png")).getImage();
		podglad.setIcon(new ImageIcon(icon.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		border.gridheight=1;
		add(podglad,border);
		podglad.addActionListener(e-> new Display());
		
		//grid x=3
		border.gridx=3;
		border.gridy=0;
		border.weightx=1;
		border.gridheight=6;
		model= new DefaultListModel<>();
		pointList = new JList<>(model);
		pointList.setSize(100, 400);
		JScrollPane sp = new JScrollPane(pointList);
		add(sp,border);

		//usunOstatni
		border.gridy=6;
		border.gridheight=1;
		usunOstatniButton = new JButton("Usun otwor");
		usunOstatniButton.setToolTipText("Usuwa zaznaczony otwór z listy");
		usunOstatniButton.addActionListener(e->{
			if(model.getSize()!=0 && pointList.getSelectedIndex()!=-1)
			{	
				model.remove(pointList.getSelectedIndex());

				pointList.setModel(model);	
			}
			if(model.getSize()==0)
				usunOstatniButton.setEnabled(false);
		}
		);
		usunOstatniButton.setEnabled(false);
		add(usunOstatniButton,border);
		
		//bezpieczny odjazd radio
		border.gridy=7;
		border.gridheight=1;
		bezpiecznyOdjazdRadio = new JRadioButton("Bezpieczny odjazd");
		bezpiecznyOdjazdRadio.setToolTipText("Podczas wiercenia wiele otworów ustawia przejazd miedzy otworami na Z podjazdu +200");
		add(bezpiecznyOdjazdRadio,border);
		
		//wierc z 2 stron
		border.gridy=8;
		wiercZDwochStron = new JRadioButton("Wiecenie z dwóch stron");
		wiercZDwochStron.setToolTipText("Dzieli glebokie wiercenie na dwie strony obracaj¹c stól o 180 stopni");
		wiercZDwochStron.setEnabled(true);
		wiercZDwochStron.addActionListener(e->
			{
				if(wiercZDwochStron.isSelected())
			{
			
				dodajWiecejOtworow.setEnabled(false);
			}
		
			dodajWiecejOtworow.setEnabled(true);
		
			
			});
		add(wiercZDwochStron,border);
		
		pack();
	}
	
	private void drill()
	{
		Point p;
		PrintStream stream = new PrintStream(parent.txt);
		System.setOut(stream);
		System.setErr(stream);
		
		
		if(!this.angleRadius) p= new Point(x,y,TYPE.XY_POINT);
		else p= new Point(x,y,TYPE.RADIUS_ANGLE_POINT);
		
		int n = 0;
		if(this.typeCombo.getSelectedIndex()==3)
			System.out.printf("(T%d WYTACZADLO)%n",toolNumber);
		else if(this.typeCombo.getSelectedIndex()==4)
			System.out.printf("(T%d GWINTOWNIK)%n",toolNumber);
		else if(this.typeCombo.getSelectedIndex()==5)
			System.out.printf("(T%d ROZWIERTAK)%n",toolNumber);
		else
		System.out.printf("(T%d WIERTLO)%n",toolNumber);
		
		sterowanie.przygotowanieUkladuINarzedzia(5, toolNumber, Wind.options.getSafeRetraction(), rotationB,base);
		
		System.out.printf(Locale.CANADA,"N%d S%d M3%n",n+=5,S);
		
		
		
		
		
		System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f M8%n",n+=5,p.getX(),p.getY());
		
		if(this.parent.getControls().isType(ControlTypes.FANUC))
		System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F10000.%n",n+=5,r);
		
	if(this.parent.getControls().isType(ControlTypes.FANUC)){
		switch(this.typeCombo.getSelectedIndex())
		{
		case 0:			
			System.out.printf(Locale.CANADA,"N%d G98 G81 R%.2f Z%.2f F%.0f %n",n+=5,r,z,feed);
			break;
		case 1:
			System.out.printf(Locale.CANADA,"N%d G98 G82 R%.2f Z%.2f P%d F%.0f  %n",n+=5,r,z, (int)(1.5*60000/S),feed);
			break;
		case 2:
			System.out.printf(Locale.CANADA,"N%d G98 G83 R%.2f Z%.2f Q%.2f F%.0f %n",n+=5,r,z,Q,feed);
			break;
		case 3:
			System.out.printf(Locale.CANADA,"N%d G98 G76 R%.2f Z%.2f Q%.2f F%.0f %n",n+=5,r,z,Q,feed);
			break;
		case 4:
			System.out.printf(Locale.CANADA,"N%d M29 S%d%n",n+=5,S);
			System.out.printf(Locale.CANADA,"N%d G84 R%.2f Z%.2f  F%.3f  %n",n+=5,r,z,feed);
			break;
		
		case 5:
			System.out.printf(Locale.CANADA,"N%d G98 G85 R%.2f Z%.2f F%.0f %n",n+=5,r,z,feed);
			break;
			
		default :
			break;
		
		}
			System.out.printf(Locale.CANADA,"N%d G80%n",n+=5);
		}
	else if(this.parent.getControls().isType(ControlTypes.SINUMERIC))
	{
		switch(this.typeCombo.getSelectedIndex())
		{
		case 0:			
			System.out.printf(Locale.CANADA,"N%d G81 R2=%.2f R3=%.2f R10=250. R11=3 F%.0f %n",n+=5,r,z,feed);
			System.out.printf("N%d G80%n",n+=5);
			break;
		case 1:
			System.out.printf(Locale.CANADA,"N%d G82 R2=%.2f R3=%.2f R4=%.3f  R10=250. R11=3 F%.0f  %n",n+=5,r,z,(1.5*60/S),feed);
			System.out.printf("N%d G80%n",n+=5);
			break;
		case 2:
			System.out.printf(Locale.CANADA,"N%d G83 R1=%.2f R2=%.2f R3=%.2f R4=1. R5=%.2f R10=250. R11=3 F%.0f %n",n+=5,2*Q,r,z,Q,feed);
			System.out.printf("N%d G80%n",n+=5);
			break;
		case 3:
			System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F5000. %n",n+=5,r);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F%.0f %n",n+=5,z,feed);
			System.out.printf(Locale.CANADA,"N%d M5%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G4 X1%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d M19%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G4 X1%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 X%.2f%n",n+=5,x+0.1);
			break;
		case 4:
			System.out.printf(Locale.CANADA,"N%d G84 R2=%.2f R3=%.2f R4=1. R10=250. R11=3 F%.3f%n",n+=5,r,z,feed);
			System.out.printf("N%d G80%n",n+=5);
			break;
		
		case 5:
			System.out.printf(Locale.CANADA,"N%d G0 Z%.2f F5000. %n",n+=5,r);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F%.0f %n",n+=5,z,feed);
			System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F%.0f %n",n+=5,r,feed);
			break;
			
		default :
			break;
		
		}	
	}
	else if(this.parent.getControls().isType(ControlTypes.OKUMA))
	{
		System.out.printf(Locale.CANADA,"N%d G71 Z%.1f%n",n+=5,this.r +20);
		
		switch(this.typeCombo.getSelectedIndex())
		{
		case 0:			
			System.out.printf(Locale.CANADA,"N%d G81 R%.2f Z%.2f M53 F%.0f %n",n+=5,r,z,feed);
			System.out.printf(Locale.CANADA,"N%d G80 %n",n+=5);
			break;
		case 1:
			System.out.printf(Locale.CANADA,"N%d G82 R%.2f Z%.2f P%.3f M53 F%.0f  %n",n+=5,r,z, 1.5*60/S,feed);
			System.out.printf(Locale.CANADA,"N%d G80 %n",n+=5);
			break;
		case 2:
			System.out.printf(Locale.CANADA,"N%d G83 R%.2f Z%.2f Q%.2f M53 F%.0f %n",n+=5,r,z,Q,feed);
			System.out.printf(Locale.CANADA,"N%d G80 %n",n+=5);
			break;
		case 3:
			System.out.printf(Locale.CANADA,"N%d G76 R%.2f Z%.2f J%.2f M53 F%.0f %n",n+=5,r,z,Q,feed);
			System.out.printf(Locale.CANADA,"N%d G80 %n",n+=5);
			break;
		case 4:
			System.out.printf(Locale.CANADA,"N%d G84 R%.2f Z%.2f M53 F%.3f  %n",n+=5,r,z,feed);
			System.out.printf(Locale.CANADA,"N%d G80 %n",n+=5);
			break;
		
		case 5:
			System.out.printf(Locale.CANADA,"N%d G85 R%.2f Z%.2f M53 F%.0f %n",n+=5,r,z,feed);
			System.out.printf(Locale.CANADA,"N%d G80 %n",n+=5);
			break;	

		default :
			break;		
		}
	}
		System.out.printf(Locale.CANADA,"N%d G0 Z%d M5%n",n+=5,Wind.options.getSafeRetraction());
		System.out.printf(Locale.CANADA,"N%d M9%n",n+=5);
		System.out.printf(Locale.CANADA,"N%d M1%n",n+=5);
	}
	
	private void drillTwoSides()
	{
		PrintStream stream = new PrintStream(parent.txt);
		System.setOut(stream);
		System.setErr(stream);
		
		int n=0;

		sterowanie.przygotowanieUkladuINarzedzia(5, toolNumber, Wind.options.getSafeRetraction(), rotationB,base);		
		
		System.out.printf(Locale.CANADA,"N%d S%d M3%n",n+=5,S);
		System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f M8%n",n+=5,x,y);
				
		if(parent.getControls().isType(ControlTypes.FANUC))
		{
			System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F10000%n",n+=5,r);
			System.out.printf(Locale.CANADA,"N%d G81 R%.2f Z%.2f F%.2f %n",n+=5,r,r-Math.abs(z)-2,feed);
			System.out.printf(Locale.CANADA,"N%d G80%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 Z400%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 B%.1f%n",n+=5,rotationB+180);
			System.out.printf(Locale.CANADA,"N%d G90 G54 %n",n+=5);
			System.out.printf(Locale.CANADA,"N%d S%d M3%n",n+=5,S);
			System.out.printf(Locale.CANADA,"N%d G0 X%.2f Y%.2f M8%n",n+=5,-x,y);
			System.out.printf(Locale.CANADA,"N%d G81 R%.2f Z%.2f F%.2f%n",n+=5,r,r-Math.abs(z)-2,feed);
			System.out.printf(Locale.CANADA,"N%d G80%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 Z%d M9%n",n+=5,Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N%d M5%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d M1%n",n+=5);
		}
		else if(parent.getControls().isType(ControlTypes.SINUMERIC))
		{
			System.out.printf(Locale.CANADA,"N%d G81 R2=%.2f R3=%.2f R10=350. R11=3 F%.2f %n",n+=5,r,r-Math.abs(z)-2,feed);
			System.out.printf(Locale.CANADA,"N%d G80%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 Z%d%n",n+=5,Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N%d G0 B180%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G90 G54 %n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 X%.2f Y%.2f %n",n+=5,-x,y);
			System.out.printf(Locale.CANADA,"N%d G81 R2=%.2f R3=%.2f F%.2f%n",n+=5,r,r-Math.abs(z)-2,feed);
			System.out.printf(Locale.CANADA,"N%d G80%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 Z%d M9%n",n+=5,Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N%d M5%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d M1%n",n+=5);
		}
		else if(parent.getControls().isType(ControlTypes.OKUMA))
		{
			System.out.printf(Locale.CANADA,"N%d G0 G71 Z%.2f%n",n+=5,r);
			System.out.printf(Locale.CANADA,"N%d G81 R%.2f Z%.2f M53 F%.2f %n",n+=5,r,r-Math.abs(z)-2,feed);
			System.out.printf(Locale.CANADA,"N%d G80%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 Z%d%n",n+=5,Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N%d G0 B%.1f%n",n+=5,rotationB+180);
			System.out.printf(Locale.CANADA,"N%d G90 G15 H1 %n",n+=5);
			System.out.printf(Locale.CANADA,"N%d S%d M3%n",n+=5,S);
			System.out.printf(Locale.CANADA,"N%d G0 X%.2f Y%.2f M8%n",n+=5,-x,y);
			System.out.printf(Locale.CANADA,"N%d G0 G71 Z%.2f%n",n+=5,r);
			System.out.printf(Locale.CANADA,"N%d G81 R%.2f Z%.2f M53 F%.2f%n",n+=5,r,r-Math.abs(z)-2,feed);
			System.out.printf(Locale.CANADA,"N%d G80%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d G0 Z%d M9%n",n+=5,Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N%d M5%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d M1%n",n+=5);
		}
	}
	
	private void drillMultipleHoles()
	{
		if(this.wiecejOtworow && model.getSize()!=0)
		{
			
			
			PrintStream stream = new PrintStream(parent.txt);
			System.setOut(stream);
			System.setErr(stream);
			Point p;
			
			if(!this.angleRadius) p= new Point(x,y,TYPE.XY_POINT);
			else p= new Point(x,y,TYPE.RADIUS_ANGLE_POINT);
			
			int safe;
			int n = 0;
			
			System.out.printf("(T%d WIERTLO )%n",toolNumber);	
			
			sterowanie.przygotowanieUkladuINarzedzia(5, toolNumber, Wind.options.getSafeRetraction(), rotationB,base);
			
			
			System.out.printf(Locale.CANADA,"N%d S%d M3%n",n+=5,S);
			System.out.printf(Locale.CANADA,"N%d G0 X%.3f Y%.3f M8%n",n+=5,p.getX(),p.getY());
			
		if(this.parent.getControls().isType(ControlTypes.FANUC)){
			
			if(bezpiecznyOdjazdRadio.isSelected()) 
			{
				safe=98;
				System.out.printf(Locale.CANADA,"N%d G0 Z%.2f %n",n+=5,r+200);
			}
			else
			{
				safe=99;
			}
			
			switch(this.typeCombo.getSelectedIndex())
			{
			case 0:			
				System.out.printf(Locale.CANADA,"N%d G%d G81 R%.2f Z%.2f F%.0f %n",n+=5,safe,r,z,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 1:
				System.out.printf(Locale.CANADA,"N%d G%d G82 R%.2f Z%.2f P%d F%.0f  %n",n+=5,safe,r,z, (int)(1.5*60000/S),feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 2:
				System.out.printf(Locale.CANADA,"N%d G%d G83 R%.2f Z%.2f Q%.2f F%.0f %n",n+=5,safe,r,z,Q,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 3:
				System.out.printf(Locale.CANADA,"N%d G%d G76 R%.2f Z%.2f Q%.2f F%.0f %n",n+=5,safe,r,z,Q,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 4:
				System.out.printf(Locale.CANADA,"N%d M29 S%d M3%n",n+=5,S);
				System.out.printf(Locale.CANADA,"N%d G%d G84 R%.2f Z%.2f  F%.3f  %n",n+=5,safe,r,z,feed);
				System.out.printf("N%d G80%n",n+=5);
				break;
			
			case 5:
				System.out.printf(Locale.CANADA,"N%d G%d G85 R%.2f Z%.2f F%.0f %n",n+=5,safe,r,z,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
				
			default :
				break;
			
			}
			}
		else if(this.parent.getControls().isType(ControlTypes.OKUMA)){
			
			if(bezpiecznyOdjazdRadio.isSelected()) 
			{
				System.out.printf(Locale.CANADA,"N%d G71 Z%.2f %n",n+=5,r+200);
			}
			else	System.out.printf(Locale.CANADA,"N%d G71 Z%.2f %n",n+=5,r);
			
			switch(this.typeCombo.getSelectedIndex())
			{
			case 0:			
				System.out.printf(Locale.CANADA,"N%d G81 R%.2f Z%.2f M53 F%.0f %n",n+=5,r,z,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 1:
				System.out.printf(Locale.CANADA,"N%d G82 R%.2f Z%.2f P%d M53 F%.0f  %n",n+=5,r,z, (int)(1.5*60000/S),feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 2:
				System.out.printf(Locale.CANADA,"N%d G83 R%.2f Z%.2f Q%.2f M53 F%.0f %n",n+=5,r,z,Q,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 3:
				System.out.printf(Locale.CANADA,"N%d G76 R%.2f Z%.2f Q%.2f M53 F%.0f %n",n+=5,r,z,Q,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 4:
				System.out.printf(Locale.CANADA,"N%d G84 R%.2f Z%.2f M53 F%.3f  %n",n+=5,r,z,feed);
				System.out.printf("N%d G80%n",n+=5);
				break;
			
			case 5:
				System.out.printf(Locale.CANADA,"N%d G85 R%.2f Z%.2f M53 F%.0f %n",n+=5,r,z,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;	
			default :
				break;
			
			}
			}
		else if(this.parent.getControls().isType(ControlTypes.SINUMERIC))
		{
			if(bezpiecznyOdjazdRadio.isSelected())
			{
				r+=200;
			}
			
			switch(this.typeCombo.getSelectedIndex())
			{
			case 0:			
				System.out.printf(Locale.CANADA,"N%d G81 R2=%.2f R3=%.2f R10=250. R11=3 F%.0f %n",n+=5,r,z,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 1:
				System.out.printf(Locale.CANADA,"N%d G82 R2=%.2f R3=%.2f R4=%0.3f  R10=250. R11=3 F%.0f  %n",n+=5,r,z, (int)(1.5*60000000/S),feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 2:
				System.out.printf(Locale.CANADA,"N%d G83 R1=%0.2f R2=%.2f R3=%.2f R4=1. R5=%.2f R10=250. R11=3 F%.0f %n",n+=5,2*Q,r,z,Q,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			case 3:
				
				System.out.printf(Locale.CANADA,"N%d G84 R2=%.2f R3=%.2f R6=4 R7=3 R10=250. R11=3 F%.0f %n",n+=5,r,z,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
				

			case 4:
				System.out.printf(Locale.CANADA,"N%d G84 R2=%.2f R3=%.2f R4=1. R10=250. R11=3 F%.3f%n",n+=5,r,z,feed);
				n=getAllThosePointsOverHere(n+5);
				System.out.printf("N%d G80%n",n+=5);
				break;
			
			case 5:
				System.out.printf(Locale.CANADA,"N%d G0 Z%.2f F5000.%n",n+=5,r);
				System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F%.0f %n",n+=5,z,feed);
				System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F%.0f %n",n+=5,r,feed);
				
				if(model.getSize()!=0)
				{
					
					for(int i=0; i<model.getSize();i++)
					{
						p=model.get(i);
						
						System.out.printf(Locale.CANADA,"N%d G0 Z%.2f%n",n+=5,(bezpiecznyOdjazdRadio.isSelected() ? r+200 : r));
						System.out.printf(Locale.CANADA,"N%d G0 X%.2f Y%.2f%n",n+=5, p.getX(), p.getY() );
						System.out.printf(Locale.CANADA,"N%d G0 Z%.2f F5000. %n",n+=5,r);
						System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F%.0f %n",n+=5,z,feed);
						System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F%.0f %n",n+=5,r,feed);
					}
				}
				
				break;
				
			default :
				break;
			
			}	
		}
			System.out.printf(Locale.CANADA,"N%d G0 Z%d M5%n",n+=5,Wind.options.getSafeRetraction());
			System.out.printf(Locale.CANADA,"N%d M8%n",n+=5);
			System.out.printf(Locale.CANADA,"N%d M1%n",n+=5);
		}

		}
			
	private int getAllThosePointsOverHere(int actualN)
	{
		Point it;
		for(int i=0;i<model.size();i++)
		{
			it=model.get(i);
			System.out.printf(Locale.CANADA,"N%d X%.2f Y%.2f%n",actualN+=5,it.getX(),it.getY());
		}
		return actualN;
	}
	
	
	private Optional<Point> tryToGetPoint()
	{
		try
		{
			this.x= Float.parseFloat(xWierceniaTxt.getText());
		}
		catch (NumberFormatException e)
		{
			return Optional.empty();
		}
		try
		{
			this.y= Float.parseFloat(yWierceniaTxt.getText());
		}
		catch (NumberFormatException e)
		{
			return Optional.empty();
		}
		
		return  Optional.of(new Point(x,y,TYPE.XY_POINT));
	}

	private boolean getPoint()
	{
		try
		{
			this.x= Float.parseFloat(xWierceniaTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana wspólrzedna x", "Blad", JOptionPane.NO_OPTION);
			return false;
		}
		try
		{
			this.y= Float.parseFloat(yWierceniaTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana wspólrzedna y", "Blad", JOptionPane.NO_OPTION);
			return false;
		}
		
		 
		
		
		return true;
	}
	
	private boolean getValues()
	{
		try
		{
			this.S=Math.abs(Integer.parseInt(this.obrotyTxt.getText()));
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana predkoœc wrzeciona", "Blad", JOptionPane.NO_OPTION);
			return false;
		}
		try
		{
			this.feed=Float.parseFloat(this.posowTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana predkoœc posuwu", "Blad", JOptionPane.NO_OPTION);
			return false;
		}
		
		
		try
		{
			this.r = Float.parseFloat(zPodjazdTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowany Podjazd", "Blad", JOptionPane.NO_OPTION);
			return false;
		}
		try
		{
			this.z = Float.parseFloat(zWierceniaTxt.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowana wspólrzedna dna otworu", "Blad", JOptionPane.NO_OPTION);
			return false;
		}
		try
		{
			this.Q = Math.abs(Float.parseFloat(this.dodatkowyParametrTxt.getText()));
		}
		catch (NumberFormatException e)
		{
			
		}

		if(r<=z)
		{
			JOptionPane.showMessageDialog(this, "Podjazd zbyt maly w stosunku do wspólrzednej Z wiercenia", "Juz byœ przydzwonil", JOptionPane.NO_OPTION);
			return false;
		}
		
		if(S>8000)
		{
			JOptionPane.showMessageDialog(this, "Zbyt wysokie obroty wrzeciona", "Blad", JOptionPane.NO_OPTION);
			return false;
			
		}
		if(feed<1)
		{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowany posuw", "Blad", JOptionPane.NO_OPTION);
			return false;
			
		}

		return getPoint();
		
	}
	
	class Display extends JFrame
	{
		Canv canvas;
		JList<Point> list;
		DefaultListModel<Point> displayListModel;
		
		Display()
		{
			super("Podglad");
			setSize(1000,800);
			setVisible(true);
			setResizable(false);
			setLayout(new FlowLayout());
			
			displayListModel = cloneModel();
			addLastPoint();
			list = new JList<>(displayListModel);
			canvas = new Canv(list);
			
			list.setPreferredSize(new Dimension(200, 800));
			list.addListSelectionListener(p->repaint());
			this.add(list);
			this.add(canvas);
	
			pack();
		}
		
		DefaultListModel<Point> cloneModel()
		{
			DefaultListModel<Point> result = new DefaultListModel<>();
			
			for(int i=0; i< Drilling.this.model.size(); i++)
			{
				result.addElement(Drilling.this.model.getElementAt(i));
			}
			return result;
		}
			
		void addLastPoint()
		{
			if(tryToGetPoint().isPresent())
			{
				if(!angleRadius)
					this.displayListModel.addElement(tryToGetPoint().get());
				else 
				{
					Point point = tryToGetPoint().get();
					this.displayListModel.addElement(new Point(point.getX(),point.getY(),TYPE.RADIUS_ANGLE_POINT));
					
				}
			}
		}
	
	 class Canv extends JPanel
	{
		private JList<Point> m;
		private final Dimension WINDOW_SIZE=new Dimension(800, 800);
		
		Canv(JList<Point> m)
		{
			this.m=m;
			setVisible(true);
			setPreferredSize(WINDOW_SIZE);
			
			
			repaint();
		}
		

		
		@Override
		public void paintComponent(Graphics g)
		{
			
				for(int i=0;i<m.getModel().getSize();i++)
				{
					if(m.getSelectedIndex()==i)
					{
						g.setColor(Color.red);
						new DrawAbove(m.getModel().getElementAt(i).toString(),m.getModel().getElementAt(i)).drawInCenter(g, WINDOW_SIZE);;
					}
					else 
					{
						g.setColor(Color.black);						
					}		
					new Hole(m.getModel().getElementAt(i)).drawInCenter(g, WINDOW_SIZE);			
				}	
				new DrawCordinateSystem(new Point(10f,740f,TYPE.XY_POINT)).draw(g);
		}
	}
	}
}

