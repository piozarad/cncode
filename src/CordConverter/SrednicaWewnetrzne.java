package CordConverter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class SrednicaWewnetrzne extends JFrame implements ActionListener {

	private JButton Oblicz;
	private JButton Cofnij;
	private JComboBox<String> typ;
	private JComboBox<String> klasa;
	private JTextField wymiar;
	private JLabel plus;
	private JLabel minus;
	
	private JLabel wymiarLabel;
	
	private Wind parent;
	
	
	
	
	//baza
	static final String typy[] ={"H","J","P","F","A","B","C","D"};
	
	static final String H[]={"6","7","8","9","10","11","12","13","14"};
	static final String J[]={"6","7","8"};
	static final String P[]={"6","7","8","9"};
	static final String F[]={"6","7","8","9"};
	static final String A[] ={"9","11","12","13"};
	static final String B[] ={"8","9","10","11","12","13"};
	static final String C[] ={"8","9","10","11"};
	static final String D[] ={"6","7","8","9","10","11","12"};
	
	static final int PPlus[][] = {
			{6,6,6,6},
			{0,8,12,12},
			{12,9,15,15},
			{15,11,18,18},
			{15,11,18,18},
			{18,14,22,22},
			{18,14,22,22},
			{21,17,26,26},
			{21,17,26,26},
			{26,21,32,32},
			{26,21,32,32},
			{30,24,37,37},
			{30,24,37,37},
			{36,28,43,43},
			{36,28,43,43},
			{36,28,43,43},
			{41,33,50,50},
			{41,33,50,50},
			{41,33,50,50},
			{47,36,56,56},
			{47,36,56,56},
			{51,41,62,62},
			{51,41,62,62},
			{55,45,68,68},
			{55,45,68,68}
			
			
			
			
	};
	
	static final int PMinus[][] = {
			{12,16,20,31},
			{17,20,30,42},
			{21,24,37,51},
			{26,29,45,61},
			{26,29,45,61},
			{31,35,55,74},
			{31,35,55,74},
			{37,42,65,88},
			{37,42,65,88},
			{45,51,78,106},
			{45,51,78,106},
			{52,59,91,124},
			{52,59,91,124},
			{61,68,106,143},
			{61,68,106,143},
			{61,68,106,143},
			{70,79,122,165},
			{70,79,122,165},
			{70,79,122,165},
			{79,88,137,186},
			{79,88,137,186},
			{87,98,151,202},
			{87,98,151,202},
			{95,108,165,223},
			{95,108,165,223},
			
			
			
			
	};
	
	
	
	
	static final int JPlus[][] = {
		{2, 5, 5, 6, 6, 8, 8, 8,10,10,13,13,16,16,16,18,18,18,22,22,25,25,29,29,33},
		{4, 6, 8,10,10,12,12,12,14,14,18,18,22,22,22,26,26,26,30,30,36,36,39,39,43},
		{6,10,12,15,15,20,20,20,24,24,28,28,34,34,34,41,41,41,47,47,55,55,60,60,66}
		
		
		
	};
	
	
	
	
	static final int JMinus[][] ={
		{4,3, 4, 5, 5, 5, 5, 5,  6,  6,  6,  6,  6,  6,  6,  7,  7,  7,  7,  7,  7,  7,  7,  7, 7},
		{6,6, 7, 8, 8, 9, 9, 9, 11, 11, 12, 12, 13, 13, 13, 14, 14, 14, 16, 16, 16, 16, 18, 18, 20},
		{8,8,10,12,12,13,13,13, 15, 15, 18, 18, 20, 20, 20, 22, 22, 22, 25, 25, 26, 26, 29, 29, 31}
		
	};
	
	
	static final int HPlus[][] = {
			{6, 10, 14, 25, 40, 60, 100, 140, 250},	
			{8, 12, 18, 30, 48, 75, 120, 180, 300},
			{9, 15, 22, 36, 58, 90, 150, 220, 360},
			{11, 18, 27, 43, 70, 110, 180, 270, 430},
			{11, 18, 27, 43, 70, 110, 180, 270, 430},
			{13, 21, 33, 53, 84, 130, 210, 330, 520},
			{13, 21, 33, 53, 84, 130, 210, 330, 520},
			{13, 21, 33, 53, 84, 130, 210, 330, 520},
			{16, 25, 39, 62, 100, 160, 250, 390, 620},
			{16, 25, 39, 62, 100, 160, 250, 390, 620},
			{19, 30, 46, 74, 120, 190, 300, 460, 740},
			{19, 30, 46, 74, 120, 190, 300, 460, 740},
			{22, 35, 54, 87, 140, 220, 350, 540, 870},
			{22, 35, 54, 87, 140, 220, 350, 540, 870},
			{22, 35, 54, 87, 140, 220, 350, 540, 870},
			{25, 40, 63, 100, 160, 250, 460, 630, 1000},
			{25, 40, 63, 100, 160, 250, 460, 630, 1000},
			{25, 40, 63, 100, 160, 250, 460, 630, 1000},
			{29, 46, 72, 115, 185, 290, 460, 720, 1150},
			{29, 46, 72, 115, 185, 290, 460, 720, 1150},
			{32, 52, 81, 130, 210, 320, 520, 810, 1300},
			{32, 52, 81, 130, 210, 320, 520, 810, 1300},
			{36, 57, 89, 140, 230, 360, 570, 890, 1400},
			{36, 57, 89, 140, 230, 360, 570, 890, 1400},
			{40, 63, 97, 155, 250, 400, 630, 970, 1550}
};
	
	
	
	static final int FPlus[][] = {
			{12,16,20,31},
			{17,20,30,42},
			{21,24,37,51},
			{26,29,45,61},
			{26,29,45,61},
			{31,35,55,74},
			{31,35,55,74},
			{37,42,65,88},
			{37,42,65,88},
			{45,51,78,106},
			{45,51,78,106},
			{52,59,91,124},
			{52,59,91,124},
			{61,68,106,143},
			{61,68,106,143},
			{61,68,106,143},
			{70,79,122,165},
			{70,79,122,165},
			{70,79,122,165},
			{79,88,137,186},
			{79,88,137,186},
			{87,98,151,202},
			{87,98,151,202},
			{95,108,165,223},
			{95,108,165,223}
			
			
			
		};
	
	static final int FMinus[][] = {
			{6,6,6,6},
			{10,10,10,10},
			{13,13,13,13},
			{16,16,16,16},
			{16,16,16,16},
			{20,20,20,20},
			{20,20,20,20},
			{20,20,20,20},
			{25,25,25,25},
			{25,25,25,25},
			{30,30,30,30},
			{30,30,30,30},
			{36,36,36,36},
			{36,36,36,36},
			{36,36,36,36},
			{43,43,43,43},
			{43,43,43,43},
			{43,43,43,43},
			{50,50,50,50},
			{50,50,50,50},
			{56,56,56,56},
			{56,56,56,56},
			{62,62,62,62},
			{62,62,62,62},
			{68,68,68,68}
			
			
			
		};
	
	static final int APlus[][] = {
			{295,330,370,410},
			{300,345,390,450},
			{316,370,430,500},
			{333,400,470,560},
			{333,400,470,560},
			{352,430,510,630},
			{352,430,510,630},
			{372,470,560,700},
			{382,480,570,710},
			{414,530,640,800},
			{434,550,660,820},
			{467,600,730,920},
			{497,630,760,950},
			{560,710,860,1090},
			{620,770,920,1150},
			{680,830,980,1210},
			{775,950,1120,1380},
			{855,1030,1200,1460},
			{935,1110,1280,1540},
			{1050,1240,1440,1730},
			{1180,1370,1570,1860},
			{1340,1560,1770,2090},
			{1490,1710,1920,2240},
			{1655,1900,2130,2470},
			{1805,2050,2280,2620}
			
			
			
		};
	
	static final int AMinus[][] = {
			{270,270,270,270},
			{270,270,270,270},
			{280,280,280,280},
			{290,290,290,290},
			{290,290,290,290},
			{300,300,300,300},
			{300,300,300,300},
			{310,310,310,310},
			{320,320,320,320},
			{340,340,340,340},
			{360,360,360,360},
			{380,380,380,380},
			{410,410,410,410},
			{460,460,460,460},
			{520,520,520,520},
			{580,580,580,580},
			{660,660,660,660},
			{740,740,740,740},
			{820,820,820,820},
			{920,920,920,920},
			{1050,1050,1050,1050},
			{1200,1200,1200,1200},
			{1350,1350,1350,1350},
			{1500,1500,1500,1500},
			{1650,1650,1650,1650}
			
			
			
		};
	
	static final int BPlus[][] = {
			{154,165,180,200,240,280},
			{158,170,188,215,260,320},
			{172,186,208,240,300,370},
			{177,193,220,260,330,420},
			{177,193,220,260,330,420},
			{193,212,244,290,370,490},
			{193,212,244,290,370,490},
			{209,232,270,330,420,560},
			{219,242,280,340,430,570},
			{236,264,310,380,490,650},
			{246,274,320,390,500,660},
			{274,307,360,440,570,760},
			{294,327,380,460,590,780},
			{323,360,420,510,660,890},
			{343,360,440,530,680,910},
			{373,410,470,560,710,940},
			{412,455,525,630,800,1060},
			{452,495,565,670,840,1100},
			{492,535,805,710,880,1140},
			{561,610,690,800,1000,1290},
			{621,670,750,860,1060,1350},
			{689,740,830,960,1170,1490},
			{769,820,910,1040,1250,1570},
			{857,915,1010,1160,1390,1730},
			{937,995,1090,1240,1470,1810}
			
			
			
		};
	
	static final int BMinus[][]= {
			{140,140,140,140,140,140},
			{140,140,140,140,140,140},
			{150,150,150,150,150,150},
			{150,150,150,150,150,150},
			{150,150,150,150,150,150},
			{160,160,160,160,160,160},
			{160,160,160,160,160,160},
			{170,170,170,170,170,170},
			{180,180,180,180,180,180},
			{190,190,190,190,190,190},
			{200,200,200,200,200,200},
			{220,220,220,220,220,220},
			{240,240,240,240,240,240},
			{260,260,260,260,260,260},
			{280,280,280,280,280,280},
			{310,310,310,310,310,310},
			{340,340,340,340,340,340},
			{380,380,380,380,380,380},
			{420,420,420,420,420,420},
			{480,480,480,480,480,480},
			{540,540,540,540,540,540},
			{600,600,600,600,600,600},
			{680,680,680,680,680,680},
			{760,760,760,760,760,760},
			{840,840,840,840,840,840}
			
			
			
		};
	static final int CPlus[][]= {
			{74,85,100,120},
			{88,100,118,145},
			{102,116,138,170},
			{122,138,165,205},
			{122,138,165,205},
			{143,162,194,240},
			{143,162,194,240},
			{159,182,220,280},
			{169,192,230,290},
			{186,214,260,330},
			{196,224,270,340},
			{224,257,310,390},
			{234,267,320,400},
			{263,300,360,450},
			{273,310,370,460},
			{293,330,390,480},
			{312,355,425,530},
			{332,375,445,550},
			{352,395,465,570},
			{381,430,510,620},
			{411,460,540,650},
			{449,500,590,720},
			{489,540,630,760},
			{537,595,690,840},
			{577,635,730,880}
			
			
			
		};
	
	static final int CMinus[][]= {
			{60,60,60,60},
			{70,70,70,70},
			{80,80,80,80},
			{95,95,95,95},
			{95,95,95,95},
			{110,110,110,110},
			{110,110,110,110},
			{120,120,120,120},
			{130,130,130,130},
			{140,140,140,140},
			{150,150,150,150},
			{170,170,170,170},
			{180,180,180,180},
			{200,200,200,200},
			{210,210,210,210},
			{230,230,230,230},
			{240,240,240,240},
			{260,260,260,260},
			{280,280,280,280},
			{300,300,300,300},
			{330,330,330,330},
			{360,360,360,360},
			{400,400,400,400},
			{440,440,440,440},
			{480,480,480,480}
			
			
			
		};
	
	static final int DPlus[][]= {
			{26,30,34,45,60,80,120},
			{38,42,48,60,78,105,150},
			{49,55,62,76,98,130,190},
			{61,68,77,93,120,160,230},
			{61,68,77,93,120,160,230},
			{78,86,98,117,149,195,275},
			{78,86,98,117,149,195,275},
			{78,86,98,117,149,195,275},
			{96,105,119,142,180,240,330},
			{96,105,119,142,180,240,330},
			{119,130,146,174,220,290,400},
			{119,130,146,174,220,290,400},
			{142,155,174,207,260,340,470},
			{142,155,174,207,260,340,470},
			{142,155,174,207,260,340,470},
			{170,185,208,245,305,395,545},
			{170,185,208,245,305,395,545},
			{170,185,208,245,305,395,545},
			{199,216,242,285,355,460,630},
			{199,216,242,285,355,460,630},
			{222,242,271,320,400,510,710},
			{222,242,271,320,400,510,710},
			{246,267,299,350,440,570,780},
			{246,267,299,350,440,570,780},
			{270,293,327,385,480,630,860}
			
			
			
		};
	
	
	static final int DMinus[][]= {
			{20,20,20,20,20,20,20},
			{30,30,30,30,30,30,30},
			{40,40,40,40,40,40,40},
			{50,50,50,50,50,50,50},
			{50,50,50,50,50,50,50},
			{65,65,65,65,65,65,65},
			{65,65,65,65,65,65,65},
			{65,65,65,65,65,65,65},
			{80,80,80,80,80,80,80},
			{80,80,80,80,80,80,80},
			{100,100,100,100,100,100,100},
			{100,100,100,100,100,100,100},
			{120,120,120,120,120,120,120},
			{120,120,120,120,120,120,120},
			{120,120,120,120,120,120,120},
			{145,145,145,145,145,145,145},
			{145,145,145,145,145,145,145},
			{145,145,145,145,145,145,145},
			{170,170,170,170,170,170,170},
			{170,170,170,170,170,170,170},
			{190,190,190,190,190,190,190},
			{190,190,190,190,190,190,190},
			{210,210,210,210,210,210,210},
			{210,210,210,210,210,210,210},
			{210,210,210,210,210,210,210},
		
			
			
			
		};
	
	
	
	public SrednicaWewnetrzne(Wind parent)
	{
		this.parent=parent;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(300,300);
		setVisible(true);
		setTitle("Tolerancje dla otworów");
		setLocationRelativeTo(null);
	
		
		//exit on close
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				
				Cofnij.doClick();
			
			}		
		});
		
		
		//layout
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints border = new GridBagConstraints();
		
		border.gridx=0;
		border.gridy=0;
		border.weightx=0.2;
		border.weighty=0.2;
		border.insets = new Insets(15,15,15,15);
		border.fill = GridBagConstraints.HORIZONTAL;
		
		
	//typy
		typ = new JComboBox<String>(typy);
		typ.addActionListener(this);
		add(typ,border);
		
	//klasy combo
		
		border.gridx=1;
		klasa = new JComboBox<String>(H);
		add(klasa,border);
	
		
	// wymiar Label
		
		border.gridx=0;
		border.gridy=1;
		wymiarLabel = new JLabel("Wymiar otworu");
		wymiarLabel.setToolTipText("Wymiar tolerowanego otworu podany w milimetrach");
		add(wymiarLabel,border);
		
	// wymiar txt
		border.gridx=1;
		wymiar = new JTextField("");
		wymiar.setSize(75,25);
		add(wymiar,border);
		
		
	//wyniki plus
		border.gridx=0;
		border.gridy=2;
		plus = new JLabel("+");
		add(plus,border);
		
	//wyniki minus
		border.gridy=3;
		minus = new JLabel("-");
		add(minus,border);
		
	//przyciski
		border.gridx=0;
		border.gridy=4;
		Oblicz = new JButton("Oblicz");
		Oblicz.addActionListener(this);
		add(Oblicz,border);
		
		border.gridx=1;
		Cofnij = new JButton("Cofnij");
		Cofnij.addActionListener(this);
		add(Cofnij,border);
		
		
		
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		
		if(o==typ)
		{
			klasa.removeAllItems();
			switch(typ.getSelectedIndex())
			{
			case 0:
				for(String s : H)
				{
					klasa.addItem(s);
				}
				plus.setText("+");
				minus.setText("-");
				break;
			case 1:
				for(String s: J)
				{
					klasa.addItem(s);
				}
				plus.setText("+");
				minus.setText("-");
				break;
			case 2:
				for(String s: P)
				{
					klasa.addItem(s);
				}
				plus.setText("-");
				minus.setText("-");
				break;
				
			case 3:
				for(String s : F)
				{
					klasa.addItem(s);
				}
				plus.setText("+");
				minus.setText("+");
			break;
			case 4:
					for(String s : A)
					{
						klasa.addItem(s);
					}
					plus.setText("+");
					minus.setText("+");
			case 5:
				for(String s : B)
				{
					klasa.addItem(s);
				}
				plus.setText("+");
				minus.setText("+");
			case 6:
				for(String s : C)
				{
					klasa.addItem(s);
				}
				plus.setText("+");
				minus.setText("+");
			case 7:
				for(String s : D)
				{
					klasa.addItem(s);
				}
				plus.setText("+");
				minus.setText("+");
			default:
				break;
			
			}
			
			
		}
		
		else if(o==Oblicz)
		{
			float dim=-1;
			float plus_value=0, minus_value=0;
			boolean ok =true;
			char plusSign='+';
			char minusSign='-';
			int firstIndex=Integer.parseInt(klasa.getSelectedItem().toString());
			//String tolearanceTable[];
			
			try
			{
				dim = Float.parseFloat(wymiar.getText());
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(this, "Podano z³y wymiar", "B³¹d", JOptionPane.NO_OPTION );
				ok = false;
			}
			
			if(ok)
			{
				if(this.typ.getSelectedItem().toString().equals("H"))
				{
					plusSign ='+';
					minusSign ='+';

					if(dim<10)
					{
						
						if(dim>6)
						{
							plus_value=HPlus[2][klasa.getSelectedIndex()]/1000.f;
						
									}
						else if(dim>3)
						{
							plus_value=HPlus[1][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>1)
						{
							plus_value=HPlus[0][klasa.getSelectedIndex()]/1000.f;
						
						}
						
						
					}
					else if(dim<100)
					{
						if(dim>80)
						{
							plus_value=HPlus[11][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>65)
						{
							plus_value=HPlus[10][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>50)
						{
							plus_value=HPlus[9][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>40)
						{
							plus_value=HPlus[8][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>30)
						{
							plus_value=HPlus[7][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>24)
						{
							plus_value=HPlus[6][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>18)
						{
							plus_value=HPlus[5][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>14)
						{
							plus_value=HPlus[4][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>10)
						{
							plus_value=HPlus[3][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else if(dim <500)
					{
						if(dim>450)
						{
							plus_value=HPlus[24][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>400)
						{
							plus_value=HPlus[23][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>355)
						{
							plus_value=HPlus[22][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>315)
						{
							plus_value=HPlus[21][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>280)
						{
							plus_value=HPlus[20][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>250)
						{
							plus_value=HPlus[19][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>225)
						{
							plus_value=HPlus[18][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>200)
						{
							plus_value=HPlus[17][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>180)
						{
							plus_value=HPlus[16][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>160)
						{
							plus_value=HPlus[15][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>140)
						{
							plus_value=HPlus[14][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>120)
						{
							plus_value=HPlus[13][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>100)
						{
							plus_value=HPlus[12][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else
						JOptionPane.showMessageDialog(this, "Zbyt du¿y lub zbyt ma³y rozmiar otworu panie", "B³¹d", JOptionPane.NO_OPTION );
					
					
						
					
					
					
					
					
				}
				
				else if(this.typ.getSelectedItem().toString().equals("P"))
				{
					plusSign ='-';
					minusSign ='-';
					
					if(dim<10)
					{
						if(dim>6)
						{
							plus_value=PPlus[2][firstIndex-6]/1000.f;
							minus_value=PMinus[2][firstIndex-6]/1000.f;
						
									}
						else if(dim>3)
						{
							plus_value=PPlus[1][firstIndex-6]/1000.f;
							minus_value=PMinus[1][firstIndex-6]/1000.f;
						
						}
						else if(dim>1)
						{
							plus_value=PPlus[0][firstIndex-6]/1000.f;
							minus_value=PMinus[0][firstIndex-6]/1000.f;
						
						}
						
						
					}
					else if(dim<100)
					{
						if(dim>80)
						{
							plus_value=PPlus[11][firstIndex-6]/1000.f;
							minus_value=PMinus[11][firstIndex-6]/1000.f;
							
						}
						else if(dim>65)
						{
							plus_value=PPlus[10][firstIndex-6]/1000.f;
							minus_value=PMinus[10][firstIndex-6]/1000.f;
						
						}
						else if(dim>50)
						{
							plus_value=PPlus[9][firstIndex-6]/1000.f;
							minus_value=PMinus[9][firstIndex-6]/1000.f;
							
						}
						else if(dim>40)
						{
							plus_value=PPlus[8][firstIndex-6]/1000.f;
							minus_value=PMinus[8][firstIndex-6]/1000.f;
							
						}
						else if(dim>30)
						{
							plus_value=PPlus[7][firstIndex-6]/1000.f;
							minus_value=PMinus[7][firstIndex-6]/1000.f;
							
						}
						else if(dim>24)
						{
							plus_value=PPlus[6][firstIndex-6]/1000.f;
							minus_value=PMinus[6][firstIndex-6]/1000.f;
							
						}
						else if(dim>18)
						{
							plus_value=PPlus[5][firstIndex-6]/1000.f;
							minus_value=PMinus[5][firstIndex-6]/1000.f;
						
						}
						else if(dim>14)
						{
							plus_value=PPlus[4][firstIndex-6]/1000.f;
							minus_value=PMinus[4][firstIndex-6]/1000.f;
							
						}
						else if(dim>10)
						{
							plus_value=PPlus[3][firstIndex-6]/1000.f;
							minus_value=PMinus[3][firstIndex-6]/1000.f;
							
						}
					}
					else if(dim <500)
					{
						if(dim>450)
						{
							plus_value=PPlus[24][firstIndex-6]/1000.f;
							minus_value=PMinus[24][firstIndex-6]/1000.f;
							
						}
						else if(dim>400)
						{
							plus_value=PPlus[23][firstIndex-6]/1000.f;
							minus_value=PMinus[23][firstIndex-6]/1000.f;
							
						}
						else if(dim>355)
						{
							plus_value=PPlus[22][firstIndex-6]/1000.f;
							minus_value=PMinus[22][firstIndex-6]/1000.f;
							
						}
						else if(dim>315)
						{
							plus_value=PPlus[21][firstIndex-6]/1000.f;
							minus_value=PMinus[21][firstIndex-6]/1000.f;
							
						}
						else if(dim>280)
						{
							plus_value=PPlus[20][firstIndex-6]/1000.f;
							minus_value=PMinus[20][firstIndex-6]/1000.f;
							
						}
						else if(dim>250)
						{
							plus_value=PPlus[19][firstIndex-6]/1000.f;
							minus_value=PMinus[19][firstIndex-6]/1000.f;
							
						}
						else if(dim>225)
						{
							plus_value=PPlus[18][firstIndex-6]/1000.f;
							minus_value=PMinus[18][firstIndex-6]/1000.f;
							
						}
						else if(dim>200)
						{
							plus_value=PPlus[17][firstIndex-6]/1000.f;
							minus_value=PMinus[17][firstIndex-6]/1000.f;
						
						}
						else if(dim>180)
						{
							plus_value=PPlus[16][firstIndex-6]/1000.f;
							minus_value=PMinus[16][firstIndex-6]/1000.f;
							
						}
						else if(dim>160)
						{
							plus_value=PPlus[15][firstIndex-6]/1000.f;
							minus_value=PMinus[15][firstIndex-6]/1000.f;
							
						}
						else if(dim>140)
						{
							plus_value=PPlus[14][firstIndex-6]/1000.f;
							minus_value=PMinus[14][firstIndex-6]/1000.f;
						
						}
						else if(dim>120)
						{
							plus_value=PPlus[13][firstIndex-6]/1000.f;
							minus_value=PMinus[13][firstIndex-6]/1000.f;
							
						}
						else if(dim>100)
						{
							plus_value=PPlus[12][firstIndex-6]/1000.f;
							minus_value=PMinus[12][firstIndex-6]/1000.f;
							
						}
					}
					else
						JOptionPane.showMessageDialog(this, "Zbyt du¿y lub zbyt ma³y rozmiar otworu panie", "B³¹d", JOptionPane.NO_OPTION );
					
					
						
					
					
					
				}
				
				else if(this.typ.getSelectedItem().toString().equals("A"))
				{
					
					plusSign ='+';
					minusSign ='+';
					if(dim<10)
					{
						if(dim>6)
						{
							plus_value=APlus[2][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[2][klasa.getSelectedIndex()]/1000.f;
						
									}
						else if(dim>3)
						{
							plus_value=APlus[1][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[1][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>1)
						{
							plus_value=APlus[0][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[0][klasa.getSelectedIndex()]/1000.f;
						
						}
						
						
					}
					else if(dim<100)
					{
						if(dim>80)
						{
							plus_value=APlus[11][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[11][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>65)
						{
							plus_value=APlus[10][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[10][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>50)
						{
							plus_value=APlus[9][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[9][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>40)
						{
							plus_value=APlus[8][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[8][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>30)
						{
							plus_value=APlus[7][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[7][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>24)
						{
							plus_value=APlus[6][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[6][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>18)
						{
							plus_value=APlus[5][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[5][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>14)
						{
							plus_value=APlus[4][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[4][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>10)
						{
							plus_value=APlus[3][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[3][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else if(dim <500)
					{
						if(dim>450)
						{
							plus_value=APlus[24][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[24][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>400)
						{
							plus_value=APlus[23][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[23][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>355)
						{
							plus_value=APlus[22][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[22][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>315)
						{
							plus_value=APlus[21][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[21][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>280)
						{
							plus_value=APlus[20][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[20][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>250)
						{
							plus_value=APlus[19][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[19][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>225)
						{
							plus_value=APlus[18][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[18][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>200)
						{
							plus_value=APlus[17][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[17][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>180)
						{
							plus_value=APlus[16][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[16][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>160)
						{
							plus_value=APlus[15][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[15][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>140)
						{
							plus_value=APlus[14][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[14][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>120)
						{
							plus_value=APlus[13][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[13][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>100)
						{
							plus_value=APlus[12][klasa.getSelectedIndex()]/1000.f;
							minus_value=AMinus[12][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else
						JOptionPane.showMessageDialog(this, "Zbyt du¿y lub zbyt ma³y rozmiar otworu panie", "B³¹d", JOptionPane.NO_OPTION );
					
					
						
					
					
					
				}
				else if(this.typ.getSelectedItem().toString().equals("B"))
				{
					
					plusSign ='+';
					minusSign ='+';
					if(dim<10)
					{
						if(dim>6)
						{
							plus_value=BPlus[2][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[2][klasa.getSelectedIndex()]/1000.f;
						
									}
						else if(dim>3)
						{
							plus_value=BPlus[1][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[1][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>1)
						{
							plus_value=BPlus[0][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[0][klasa.getSelectedIndex()]/1000.f;
						
						}
						
						
					}
					else if(dim<100)
					{
						if(dim>80)
						{
							plus_value=BPlus[11][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[11][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>65)
						{
							plus_value=BPlus[10][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[10][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>50)
						{
							plus_value=BPlus[9][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[9][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>40)
						{
							plus_value=BPlus[8][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[8][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>30)
						{
							plus_value=BPlus[7][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[7][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>24)
						{
							plus_value=BPlus[6][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[6][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>18)
						{
							plus_value=BPlus[5][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[5][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>14)
						{
							plus_value=BPlus[4][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[4][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>10)
						{
							plus_value=BPlus[3][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[3][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else if(dim <500)
					{
						if(dim>450)
						{
							plus_value=BPlus[24][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[24][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>400)
						{
							plus_value=BPlus[23][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[23][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>355)
						{
							plus_value=BPlus[22][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[22][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>315)
						{
							plus_value=BPlus[21][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[21][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>280)
						{
							plus_value=BPlus[20][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[20][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>250)
						{
							plus_value=BPlus[19][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[19][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>225)
						{
							plus_value=BPlus[18][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[18][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>200)
						{
							plus_value=BPlus[17][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[17][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>180)
						{
							plus_value=BPlus[16][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[16][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>160)
						{
							plus_value=BPlus[15][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[15][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>140)
						{
							plus_value=BPlus[14][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[14][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>120)
						{
							plus_value=BPlus[13][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[13][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>100)
						{
							plus_value=BPlus[12][klasa.getSelectedIndex()]/1000.f;
							minus_value=BMinus[12][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else
						JOptionPane.showMessageDialog(this, "Zbyt du¿y lub zbyt ma³y rozmiar otworu panie", "B³¹d", JOptionPane.NO_OPTION );
					
					
						
					
					
					
				}
				
				
				else if(this.typ.getSelectedItem().toString().equals("C"))
				{
					
					plusSign ='+';
					minusSign ='+';
					if(dim<10)
					{
						if(dim>6)
						{
							plus_value=CPlus[2][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[2][klasa.getSelectedIndex()]/1000.f;
						
									}
						else if(dim>3)
						{
							plus_value=CPlus[1][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[1][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>1)
						{
							plus_value=CPlus[0][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[0][klasa.getSelectedIndex()]/1000.f;
						
						}
						
						
					}
					else if(dim<100)
					{
						if(dim>80)
						{
							plus_value=CPlus[11][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[11][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>65)
						{
							plus_value=CPlus[10][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[10][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>50)
						{
							plus_value=CPlus[9][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[9][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>40)
						{
							plus_value=CPlus[8][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[8][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>30)
						{
							plus_value=CPlus[7][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[7][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>24)
						{
							plus_value=CPlus[6][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[6][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>18)
						{
							plus_value=CPlus[5][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[5][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>14)
						{
							plus_value=CPlus[4][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[4][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>10)
						{
							plus_value=CPlus[3][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[3][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else if(dim <500)
					{
						if(dim>450)
						{
							plus_value=CPlus[24][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[24][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>400)
						{
							plus_value=CPlus[23][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[23][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>355)
						{
							plus_value=CPlus[22][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[22][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>315)
						{
							plus_value=CPlus[21][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[21][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>280)
						{
							plus_value=CPlus[20][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[20][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>250)
						{
							plus_value=CPlus[19][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[19][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>225)
						{
							plus_value=CPlus[18][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[18][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>200)
						{
							plus_value=CPlus[17][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[17][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>180)
						{
							plus_value=CPlus[16][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[16][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>160)
						{
							plus_value=CPlus[15][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[15][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>140)
						{
							plus_value=CPlus[14][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[14][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>120)
						{
							plus_value=CPlus[13][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[13][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>100)
						{
							plus_value=CPlus[12][klasa.getSelectedIndex()]/1000.f;
							minus_value=CMinus[12][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else
						JOptionPane.showMessageDialog(this, "Zbyt du¿y lub zbyt ma³y rozmiar otworu panie", "B³¹d", JOptionPane.NO_OPTION );
				}
				else if(this.typ.getSelectedItem().toString().equals("D"))
				{
					
					plusSign ='+';
					minusSign ='+';
					if(dim<10)
					{
						if(dim>6)
						{
							plus_value=DPlus[2][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[2][klasa.getSelectedIndex()]/1000.f;
						
									}
						else if(dim>3)
						{
							plus_value=DPlus[1][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[1][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>1)
						{
							plus_value=DPlus[0][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[0][klasa.getSelectedIndex()]/1000.f;
						
						}
						
						
					}
					else if(dim<100)
					{
						if(dim>80)
						{
							plus_value=DPlus[11][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[11][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>65)
						{
							plus_value=DPlus[10][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[10][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>50)
						{
							plus_value=DPlus[9][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[9][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>40)
						{
							plus_value=DPlus[8][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[8][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>30)
						{
							plus_value=DPlus[7][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[7][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>24)
						{
							plus_value=DPlus[6][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[6][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>18)
						{
							plus_value=DPlus[5][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[5][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>14)
						{
							plus_value=DPlus[4][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[4][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>10)
						{
							plus_value=DPlus[3][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[3][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else if(dim <500)
					{
						if(dim>450)
						{
							plus_value=DPlus[24][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[24][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>400)
						{
							plus_value=DPlus[23][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[23][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>355)
						{
							plus_value=DPlus[22][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[22][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>315)
						{
							plus_value=DPlus[21][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[21][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>280)
						{
							plus_value=DPlus[20][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[20][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>250)
						{
							plus_value=DPlus[19][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[19][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>225)
						{
							plus_value=DPlus[18][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[18][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>200)
						{
							plus_value=DPlus[17][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[17][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>180)
						{
							plus_value=DPlus[16][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[16][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>160)
						{
							plus_value=DPlus[15][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[15][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>140)
						{
							plus_value=DPlus[14][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[14][klasa.getSelectedIndex()]/1000.f;
						
						}
						else if(dim>120)
						{
							plus_value=DPlus[13][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[13][klasa.getSelectedIndex()]/1000.f;
							
						}
						else if(dim>100)
						{
							plus_value=DPlus[12][klasa.getSelectedIndex()]/1000.f;
							minus_value=DMinus[12][klasa.getSelectedIndex()]/1000.f;
							
						}
					}
					else
						JOptionPane.showMessageDialog(this, "Zbyt du¿y lub zbyt ma³y rozmiar otworu panie", "B³¹d", JOptionPane.NO_OPTION );
				}
				
				else if(this.typ.getSelectedItem().toString().equals("J"))
				{
					
					plusSign ='+';
					minusSign ='-';
					
					if(dim<10)
					{
						if(dim>6)
						{
							plus_value=JPlus[firstIndex-6][2]/1000.f;
							minus_value=JMinus[firstIndex-6][2]/1000.f;
									}
						else if(dim>3)
						{
							plus_value=JPlus[firstIndex-6][1]/1000.f;
							minus_value=JMinus[firstIndex-6][1]/1000.f;
						}
						else if(dim>1)
						{
							plus_value=JPlus[firstIndex-6][0]/1000.f;
							minus_value=JMinus[firstIndex-6][0]/1000.f;
						}
						
						
					}
					else if(dim<100)
					{
						if(dim>80)
						{
							plus_value=JPlus[firstIndex-6][11]/1000.f;
							minus_value=JMinus[firstIndex-6][11]/1000.f;
						}
						else if(dim>65)
						{
							plus_value=JPlus[firstIndex-6][10]/1000.f;
							minus_value=JMinus[firstIndex-6][10]/1000.f;
						}
						else if(dim>50)
						{
							plus_value=JPlus[firstIndex-6][9]/1000.f;
							minus_value=JMinus[firstIndex-6][9]/1000.f;
						}
						else if(dim>40)
						{
							plus_value=JPlus[firstIndex-6][8]/1000.f;
							minus_value=JMinus[firstIndex-6][8]/1000.f;
						}
						else if(dim>30)
						{
							plus_value=JPlus[firstIndex-6][7]/1000.f;
							minus_value=JMinus[firstIndex-6][7]/1000.f;
						}
						else if(dim>24)
						{
							plus_value=JPlus[firstIndex-6][6]/1000.f;
							minus_value=JMinus[firstIndex-6][6]/1000.f;
						}
						else if(dim>18)
						{
							plus_value=JPlus[firstIndex-6][5]/1000.f;
							minus_value=JMinus[firstIndex-6][5]/1000.f;
						}
						else if(dim>14)
						{
							plus_value=JPlus[firstIndex-6][4]/1000.f;
							minus_value=JMinus[firstIndex-6][4]/1000.f;
						}
						else if(dim>10)
						{
							plus_value=JPlus[firstIndex-6][3]/1000.f;
							minus_value=JMinus[firstIndex-6][3]/1000.f;
						}
					}
					else if(dim <500)
					{
						if(dim>450)
						{
							plus_value=JPlus[firstIndex-6][24]/1000.f;
							minus_value=JMinus[firstIndex-6][24]/1000.f;
						}
						else if(dim>400)
						{
							plus_value=JPlus[firstIndex-6][23]/1000.f;
							minus_value=JMinus[firstIndex-6][23]/1000.f;
						}
						else if(dim>355)
						{
							plus_value=JPlus[firstIndex-6][22]/1000.f;
							minus_value=JMinus[firstIndex-6][22]/1000.f;
						}
						else if(dim>315)
						{
							plus_value=JPlus[firstIndex-6][21]/1000.f;
							minus_value=JMinus[firstIndex-6][21]/1000.f;
						}
						else if(dim>280)
						{
							plus_value=JPlus[firstIndex-6][20]/1000.f;
							minus_value=JMinus[firstIndex-6][20]/1000.f;
						}
						else if(dim>250)
						{
							plus_value=JPlus[firstIndex-6][19]/1000.f;
							minus_value=JMinus[firstIndex-6][19]/1000.f;
						}
						else if(dim>225)
						{
							plus_value=JPlus[firstIndex-6][18]/1000.f;
							minus_value=JMinus[firstIndex-6][18]/1000.f;
						}
						else if(dim>200)
						{
							plus_value=JPlus[firstIndex-6][17]/1000.f;
							minus_value=JMinus[firstIndex-6][17]/1000.f;
						}
						else if(dim>180)
						{
							plus_value=JPlus[firstIndex-6][16]/1000.f;
							minus_value=JMinus[firstIndex-6][16]/1000.f;
						}
						else if(dim>160)
						{
							plus_value=JPlus[firstIndex-6][15]/1000.f;
							minus_value=JMinus[firstIndex-6][15]/1000.f;
						}
						else if(dim>140)
						{
							plus_value=JPlus[firstIndex-6][14]/1000.f;
							minus_value=JMinus[firstIndex-6][14]/1000.f;
						}
						else if(dim>120)
						{
							plus_value=JPlus[firstIndex-6][13]/1000.f;
							minus_value=JMinus[firstIndex-6][13]/1000.f;
						}
						else if(dim>100)
						{
							plus_value=JPlus[firstIndex-6][12]/1000.f;
							minus_value=JMinus[firstIndex-6][12]/1000.f;
						}
						
					}
					else
						JOptionPane.showMessageDialog(this, "Zbyt du¿y lub zbyt ma³y rozmiar otworu panie", "B³¹d", JOptionPane.NO_OPTION );
				}
					else if(this.typ.getSelectedItem().toString().equals("F"))
					{
						
						plusSign ='+';
						minusSign ='+';
						
						if(dim<10)
						{
							if(dim>6)
							{
								plus_value=FPlus[2][firstIndex-6]/1000.f;
								minus_value=FMinus[2][firstIndex-6]/1000.f;
										}
							else if(dim>3)
							{
								plus_value=FPlus[1][firstIndex-6]/1000.f;
								minus_value=FMinus[1][firstIndex-6]/1000.f;
							}
							else if(dim>1)
							{
								plus_value=FPlus[0][firstIndex-6]/1000.f;
								minus_value=FMinus[0][firstIndex-6]/1000.f;
							}
							
							
						}
						else if(dim<100)
						{
							if(dim>80)
							{
								plus_value=FPlus[11][firstIndex-6]/1000.f;
								minus_value=FMinus[11][firstIndex-6]/1000.f;
							}
							else if(dim>65)
							{
								plus_value=FPlus[10][firstIndex-6]/1000.f;
								minus_value=FMinus[10][firstIndex-6]/1000.f;
							}
							else if(dim>50)
							{
								plus_value=FPlus[9][firstIndex-6]/1000.f;
								minus_value=FMinus[9][firstIndex-6]/1000.f;
							}
							else if(dim>40)
							{
								plus_value=FPlus[8][firstIndex-6]/1000.f;
								minus_value=FMinus[8][firstIndex-6]/1000.f;
							}
							else if(dim>30)
							{
								plus_value=FPlus[7][firstIndex-6]/1000.f;
								minus_value=FMinus[7][firstIndex-6]/1000.f;
							}
							else if(dim>24)
							{
								plus_value=FPlus[6][firstIndex-6]/1000.f;
								minus_value=FMinus[6][firstIndex-6]/1000.f;
							}
							else if(dim>18)
							{
								plus_value=FPlus[5][firstIndex-6]/1000.f;
								minus_value=FMinus[5][firstIndex-6]/1000.f;
							}
							else if(dim>14)
							{
								plus_value=FPlus[4][firstIndex-6]/1000.f;
								minus_value=FMinus[4][firstIndex-6]/1000.f;
							}
							else if(dim>10)
							{
								plus_value=FPlus[3][firstIndex-6]/1000.f;
								minus_value=FMinus[3][firstIndex-6]/1000.f;
							}
						}
						else if(dim <500)
						{
							if(dim>450)
							{
								plus_value=FPlus[24][firstIndex-6]/1000.f;
								minus_value=FMinus[24][firstIndex-6]/1000.f;
							}
							else if(dim>400)
							{
								plus_value=FPlus[23][firstIndex-6]/1000.f;
								minus_value=FMinus[23][firstIndex-6]/1000.f;
							}
							else if(dim>355)
							{
								plus_value=FPlus[22][firstIndex-6]/1000.f;
								minus_value=FMinus[22][firstIndex-6]/1000.f;
							}
							else if(dim>315)
							{
								plus_value=FPlus[21][firstIndex-6]/1000.f;
								minus_value=FMinus[21][firstIndex-6]/1000.f;
							}
							else if(dim>280)
							{
								plus_value=FPlus[20][firstIndex-6]/1000.f;
								minus_value=FMinus[20][firstIndex-6]/1000.f;
							}
							else if(dim>250)
							{
								plus_value=FPlus[19][firstIndex-6]/1000.f;
								minus_value=FMinus[19][firstIndex-6]/1000.f;
							}
							else if(dim>225)
							{
								plus_value=FPlus[18][firstIndex-6]/1000.f;
								minus_value=FMinus[18][firstIndex-6]/1000.f;
							}
							else if(dim>200)
							{
								plus_value=FPlus[17][firstIndex-6]/1000.f;
								minus_value=FMinus[17][firstIndex-6]/1000.f;
							}
							else if(dim>180)
							{
								plus_value=FPlus[16][firstIndex-6]/1000.f;
								minus_value=FMinus[16][firstIndex-6]/1000.f;
							}
							else if(dim>160)
							{
								plus_value=FPlus[15][firstIndex-6]/1000.f;
								minus_value=FMinus[15][firstIndex-6]/1000.f;
							}
							else if(dim>140)
							{
								plus_value=FPlus[14][firstIndex-6]/1000.f;
								minus_value=FMinus[14][firstIndex-6]/1000.f;
							}
							else if(dim>120)
							{
								plus_value=FPlus[13][firstIndex-6]/1000.f;
								minus_value=FMinus[13][firstIndex-6]/1000.f;
							}
							else if(dim>100)
							{
								plus_value=FPlus[12][firstIndex-6]/1000.f;
								minus_value=FMinus[12][firstIndex-6]/1000.f;
							}
							
						}
						else
							JOptionPane.showMessageDialog(this, "Zbyt du¿y lub zbyt ma³y rozmiar otworu panie", "B³¹d", JOptionPane.NO_OPTION );
					}
					
					
					
					
					
					
					
				
				}
				plus.setText(plusSign + Float.toString( plus_value) + " mm");
				minus.setText(minusSign + Float.toString(minus_value) + " mm");
				
			
			Wind.log.writeInfoLog("Srednice wewnetrzne - wykonano", SrednicaWewnetrzne.class.getSimpleName());
			
		}
		else if(o==Cofnij)
		{
			parent.sw =null;
			this.dispose();
		}
	}	
}
			
