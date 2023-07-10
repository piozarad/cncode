package CordConverter;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import BasicControls.SterowanieOkuma;
import BasicControls.SterowanieSinumeric;
import PlaneMachining.PlaneView;


public class Edytor extends JPanel implements ActionListener {

	//toolbar
	private toolBar tb;
	
	// text
	private JScrollPane sp;
	private JScrollPane logPane;
	public txtArea txt;
	private JTextArea logtxt;

	// buttons
	private JButton count;
	private JButton generate;
	private JButton przesun;
	private JButton zamienButton;
	private JButton obroc;
	private JButton aktualizujNarzedzia;
	private JButton generuj;
	
	//related to txt area
	private List<Function> f;
	
	
	//labels
	private JLabel funkcje;

	//stuff
	JLabel sterowanieLabel;
	public Spiral spiral =null;
	public Przesun p = null;
	public Poglebienie pogl = null;
	public wiercenie w = null;
	public Zamien z =  null;
	public Pocket poc =null;
	public Przepona przep =null;
	public Wkladka wklad = null;
	public Faza F = null;
	private JLabel narzedziaField = null;
	public Czop czop =null;
	private GenerateGCodePanel generateGCode = null;
	PlaneView plaszczyzna =null;

	private ToolListView okienkoNarzedzi = null;
	private FunctionWindow okienkoFunkcji = null;
	private JScrollPane okienkoFunkcjiScrollPane = null;
	private JLabel rLabel;
	private JLabel circleCenterLabel;
	
	//constants
	private static final Dimension BUTTON_PREFFERED_SIZE = new Dimension(175,50);

	
	//controls
	private Sterowanie sterowanie; 
	
	//parent
	private Wind parent;
	
	//tool list
	ToolListController toolListControler;
	
	// g code Interpreters MVC
	GCodeInterpreterModel model;
	GCodeInterpreterControler interpreter;
	

	public Edytor(Sterowanie sterowanie, Wind parent) {
		this.sterowanie=sterowanie;
		this.parent=parent;
		f = new LinkedList<>();
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(5, 10, 5, 10);
		border.anchor = GridBagConstraints.NORTHWEST;

		txt = new txtArea(new JTextArea(300, 900));
		sp = new JScrollPane(txt.getTxtArea());
		sp.setSize(400, 1100);
		
		border.gridx=0;
		border.gridy=0;
		border.gridwidth = 6;
		border.gridheight = 1;
		border.weightx = 1.;
		border.weighty = 0;
		border.fill = GridBagConstraints.HORIZONTAL;
		
		tb = new toolBar();
		add(tb,border);
		
		border.fill = GridBagConstraints.BOTH;
		border.weightx = 1.0;
		border.weighty = 1.0;
		border.gridheight = 19;
		border.gridwidth = 4;
		border.gridx = 2;
		border.gridy = 1;

		add(sp, border);

		border.gridx=2;
		border.gridy=23;
		border.gridheight =2;
		border.gridwidth = 2;
		border.weighty= 0.5;
		border.fill = GridBagConstraints.BOTH;
		logtxt = new JTextArea("Log\n");
		logtxt.setMinimumSize(new Dimension(100,150));
		logtxt.setEditable(false);
		logPane = new JScrollPane(logtxt);
		logPane.setEnabled(false);
		add(logPane,border);
		
		
		border.gridheight = 1;
		border.gridwidth = 1;
		border.weightx = 0;
		border.weighty = 0.;
		border.fill = GridBagConstraints.HORIZONTAL;
		
		sterowanieLabel = new JLabel(sterowanie.toString());
		border.gridx = 6;
		border.gridy = 0;
		add(sterowanieLabel, border);
		
	
		count = new JButton("Numeruj");
		count.setToolTipText("Numeruje bloki programu zgodnie z kolejnoscia");
		count.setPreferredSize(BUTTON_PREFFERED_SIZE);
		border.gridx = 6;
		border.gridy++;
		count.addActionListener(this);
		add(count, border);

		funkcje = new JLabel("Funkcje");
		funkcje.setPreferredSize(BUTTON_PREFFERED_SIZE);
		border.gridx = 6;
		border.gridy++;
		add(funkcje,border);
		
//		wiercenie = new JButton("Wiercenie");
//		wiercenie.setToolTipText("Generauje cykl dla nawiercania, wiercenia prostego, wytaczania itp");
//		wiercenie.setPreferredSize(BUTTON_PREFFERED_SIZE);
//
//		border.fill = GridBagConstraints.HORIZONTAL;
//		border.gridx=6;
//		border.gridy=3;
//		wiercenie.addActionListener(this);
//		add(wiercenie,border);
		
//		generate = new JButton("Spirala");
//		generate.setToolTipText("Generuj kod dla interpalacji spiralnej o podanych paramertach");
//		generate.setPreferredSize(BUTTON_PREFFERED_SIZE);
//		border.gridx = 6;
//		border.gridy++;
//		generate.addActionListener(this);
//		add(generate, border);
		
		
//		kieszen = new JButton("Kieszeñ");
//		kieszen.setPreferredSize(BUTTON_PREFFERED_SIZE);
//		kieszen.setToolTipText("Generuje kod dla kieszeni o podanych wymiarach");
//		kieszen.addActionListener(this);
//		border.gridx = 6;
//		border.gridy=5;
//		add(kieszen,border);
//		
//		poglebienie = new JButton("Poglebienie");
//		poglebienie.setToolTipText("Generuje kod dla frezowanego poglebienia o przekroju ko³owym w 3 osiach");
//		poglebienie.setPreferredSize(BUTTON_PREFFERED_SIZE);
//		border.gridx=6;
//		border.gridy=6;
//		poglebienie.addActionListener(this);
//		add(poglebienie,border);
//		
//		//przepona 
//		przepona = new JButton("Przepona");
//		przepona.setToolTipText("Cykl do rozfrezowania przepony. Przejscia w osi X,Y po prostok¹cie, zag³êbienie w osi Z");
//		przepona.setPreferredSize(BUTTON_PREFFERED_SIZE);
//		border.gridx=6;
//		border.gridy=7;
//		przepona.addActionListener(this);
//		add(przepona,border);
//		
//		//faza
//		faza = new JButton("Faza");
//		faza.setToolTipText("Generuje kod fazowania krawêdzi dla narzêdzia o podanej geometrii");
//		faza.setPreferredSize(BUTTON_PREFFERED_SIZE);
//		border.gridx =6;
//		border.gridy=8;
//		faza.addActionListener(this);
//		add(faza,border);
//		
//		//wkladka
//		wkladka = new JButton("Wkladka");
//		wkladka.setToolTipText("Generuje kod do rozfrezowania p³ytkiej okr¹g³ej wk³adki");
//		wkladka.setPreferredSize(BUTTON_PREFFERED_SIZE);
//		border.gridx = 6;
//		border.gridy=9;
//		wkladka.addActionListener(this);
//		add(wkladka,border);
		
		//generuj
		generuj = new JButton("Generuj");
		generuj.setToolTipText("Generuj kod");
		generuj.setPreferredSize(BUTTON_PREFFERED_SIZE);
		generuj.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						if(Edytor.this.generateGCode!=null) generateGCode.setVisible(true);
						else generateGCode = new GenerateGCodePanel(Edytor.this);
					}
				});
		border.gridx=6;
		border.gridy++;
		add(generuj,border);
		
		//----------------
		//separator
		JSeparator s = new JSeparator();
		border.weightx=0.002;
		border.fill = GridBagConstraints.HORIZONTAL;
		border.gridx=6;
		border.gridy++;
		add(s,border);
		
		//Przesun
		przesun = new JButton("Przesuñ");
		przesun.setToolTipText("Przesuwa obróbkê w danej osi o podan¹ wartoœæ");
		przesun.setPreferredSize(BUTTON_PREFFERED_SIZE);
		border.gridx=6;
		border.gridy++;
		przesun.addActionListener(this);
		add(przesun,border);
			
		// Zamien	
		zamienButton = new JButton("Zamieñ");
		zamienButton.setToolTipText("Zamieñ podana wartosc na zefiniowana przez uzytkownika dla wielu blokow programu");
		zamienButton.setPreferredSize(BUTTON_PREFFERED_SIZE);
		zamienButton.addActionListener(this);
		border.gridx=6;
		border.gridy++;
		add(zamienButton,border);
		
		//obroc
		obroc= new JButton("Obróæ");
		obroc.setToolTipText("[Funkcja w przygotowaniu] Obraca uk³ad wspó³rzêdnych o podany k¹t wzglêdem podanego punktu");
		obroc.setEnabled(false);
		obroc.setPreferredSize(BUTTON_PREFFERED_SIZE);
		border.gridx=6;
		border.gridy++;
		add(obroc,border);
		
		
		//wykaz narzedzi Label
		narzedziaField = new JLabel("Narzedzia");
		border.gridx = 0;
		border.gridy=1;
		border.gridwidth=2;
		add(narzedziaField,border);
		
		// wykaz narzedzi okienko
		okienkoNarzedzi = new ToolListView();
		okienkoNarzedzi.setBorder(BorderFactory.createEtchedBorder());
		ToolListModel toolListModel = new ToolListModel(this);
		okienkoFunkcji = new FunctionWindow();
		toolListControler = new ToolListController(okienkoFunkcji, okienkoNarzedzi, toolListModel,this);
		
		//add this in jscrolPane
		JScrollPane okienkoNarzedziSP = new JScrollPane(okienkoNarzedzi);
		border.gridx=0;
		border.gridy=2;
		border.gridheight=5;
		border.weighty=0;
		border.fill=GridBagConstraints.BOTH;
		
		add(okienkoNarzedziSP,border);
		
		//okienko Funkcji
		okienkoFunkcjiScrollPane = new JScrollPane(okienkoFunkcji,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		okienkoFunkcjiScrollPane.setMinimumSize(new Dimension(250,25));
		border.gridx=4;
		border.gridy=23;
		border.gridwidth = 2;
		border.gridheight=3;
		add(okienkoFunkcjiScrollPane,border);
		
		rLabel = new JLabel("R");
		rLabel.setToolTipText("Promien okregu");
		border.gridx=0;
		border.gridy=6;
		border.gridheight=1;
		add(rLabel,border);

		circleCenterLabel = new JLabel("O");
		circleCenterLabel.setToolTipText("Punkt œrodka okregu");
		border.gridx=0;
		border.gridy=7;
		add(circleCenterLabel,border);

		//szukaj narzedzi button
		this.aktualizujNarzedzia = new JButton("Aktualizuj");
		border.gridx=0;
		border.gridy=8;
		aktualizujNarzedzia.addActionListener((ActionEvent e) -> searchForTools());
		
		add(aktualizujNarzedzia,border);
		
		//txt add code to GCodeInterpreter
		this.model = new GCodeInterpreterModel();
		this.interpreter = new GCodeInterpreterControler(model, txt,okienkoFunkcji, this,rLabel,circleCenterLabel);
		
		this.parent.pack();	
	}

	//getters
	public toolBar getToolBar()
	{
		return this.tb;
	}
	public String getText()
	{
		return this.txt.getTxtArea().getText();
	}
	
	public List<String> getTextAsList()
	{
		return Arrays.asList(this.getText().split("\n"));

	}
	
	public int getFunctionSize()
	{
		return this.f.size();
	}
	
	public Wind getWind()
	{
		return this.parent;	
	}
	
	public Function[] getArrayFunction()
		{
			int size = this.f.size();
			
				Function[] result = new Function[size];
					
			for(int i=0; i<size;i++)
			{
				result[i]=f.get(i);				
			}
			return result;	
		}
	public Sterowanie getControls()
	{
		return this.sterowanie;
	}
	
	public int getListLength()
	{
		return f.size();
	}
	
	//setters
	public void setControls(Sterowanie sterowanie)
	{
		this.sterowanie=sterowanie;
	}
	
	public void addFunctionToList(Function fun, int id)
	{
		this.f.add(id,fun);
	}
	public void deleteFunctionFromList(int id)
	{
		this.f.remove(id);
		
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		Object o = ev.getSource();

		if(o==count)
		{
			String[] numerujCo ={"5","10","20"};
			int countValue =0;
			//gdyby ktos wcisnal cancel:
			boolean error = false;
			String result =(String) JOptionPane.showInputDialog(this, "Numeruj bloki programu co", "Numeracja blokow", JOptionPane.PLAIN_MESSAGE, null, numerujCo, numerujCo[0]);
			
			analyze();
			
			try 
			{
				 countValue = Integer.parseInt(result);
			}
			catch (NumberFormatException e)
			{
				error =true;	
			}
			
			if(!error)
			{
				countBlocks(countValue);
				write();
			}
		}
		else if(o==generate)
		{
		
				if(spiral==null)
				{
					spiral = new Spiral(this);
				}
				else spiral.setAlwaysOnTop(true);
	
		}
		else if(o==przesun)
		{
			if(p==null)
			{
				JOptionPane.showMessageDialog(this, "Aby bezpiecznie przesun¹æ obróbkê w obrêbie danych bloków nale¿y upewniæ siê, ¿e s¹ one prawid³owo ponumerowane lub u¿yæ funkcji 'numeruj'");
				p = new Przesun(this); 
				
			}
			else p.setAlwaysOnTop(true);
			
			
		}
//		else if(o==faza)
//		{
//			if(this.F==null)
//			{
//				
//				F=new Faza(this);
//			}
//			else 
//				F.setAlwaysOnTop(true);
//}
		
		else if(o==zamienButton)
		{
			if(this.z==null)
			{
				z= new Zamien(this);
			}
			else 
				z.setAlwaysOnTop(true);
					
		}
//		else if(o==poglebienie)
//		{
//			if(pogl==null)
//			{				
//				pogl = new Poglebienie(this);
//				
//			}
//			else pogl.setAlwaysOnTop(true);
//			
//		}
//		else if(o==wiercenie)
//		{
//			if(w==null)
//			{
//				w = new wiercenie(this);
//				
//			}
//			else w.setAlwaysOnTop(true);
//		}
//		else if(o==kieszen)
//		{		
//			if(poc==null)
//			{
//				poc = new Pocket(this);
//			}
//			else poc.setAlwaysOnTop(true);
//		}
//		else if(o==przepona)
//		{
//			if(przep==null)
//			{
//				przep= new Przepona(this);
//			}
//			else przep.setAlwaysOnTop(true);
//		
//		}
//		else if(o==wkladka)
//		{
//			if(wklad ==null)
//			{
//				wklad = new Wkladka(this);
//			}
//			else 
//			{
//				wklad.setAlwaysOnTop(true);				
//			}		
//		}
	}
	
	
	 void write()
	{
		txt.getTxtArea().setText("");
		for (Function fun : f)
		{
			txt.getTxtArea().append(fun.toString());
		}
	}
	
	public Function getfunction(int id)
	{
		
		return f.get(id);
		
	}
	
	public void repleaceFunction(Function f, int id)
	{
		
		this.f.remove(id);
		this.f.add(id, f);
		
	}
	

	public void analyzeWithNoControls()
	{
		if(!f.isEmpty())
			f.clear();
		
		String []input = this.txt.getTxtArea().getText().split("\\n"); 
		
		for(int i=0; i<input.length;i++)
		{
			f.add(new Function(input[i]));
			
		}

	}

	
	/**
	 *  order toolList controler to do tool search
	 */
	public void searchForTools()
	{	
		analyze();
		
		toolListControler.orderToolSearch(this.f);
	}
	
	boolean analyzeProgram()
	{
		analyze();
				
		int warnings =0;
		int errors =0;
		int surpassWarningOne =0;
		int surpassWarningTwo =0;
		int surpassWarningthree =0;
		int surpassWarningfive =0;
		int surpassWarningsix=0;
		int surpassWarningseventh=0;
		int surpassWarningEight =0;
		
		//active stuff
		int activeTool=-1;
		int nextTool=-1;
		String activeD="0";
		String activeH="0";
		Point previousPoint= new Point();
		Point destinationPoint = new Point();
		float activeFeed =-1;
		int activeBase =-1;
		Float activeBRotation =null;
		boolean mOne =false;
	
		/*
		 * 	array
		 * [0] - aktywna funkcja ruchu G0 G1 G2 G3 G80 do G89 G76 G73
		 * [1] - aktywne programowanie G90 / G91
		 * [2] - aktywna baza G54 , G55  lub G54.1 P12 == 112
		 * [3] - aktywna kompensacja promieniowa narzêdzia G41 , G42 lub G40 jeœli nieaktywna
		 * [4] - postój czasowy G4 lub 0 jeœli brak
		 * [5] - aktywny cykl sta³y
		 * [6] - powrót do punktu referencyjnego G28 G30
		 * [7] -  aktywna korekcja 43 (fanuc) lub 15 (okuma)
		 * [8] - 
		 */
		int[] activeGCode = new int[9];
	
		/*
		 * array m codes
		 * [0] - obroty wrzeciona M3 -prawe lub M4 -lewe M5 - brak
		 * [1] - rodzaj aktywnego ch³odzenia narzêdzia M8 M12/M51 M9:  1-w³¹czone 0-wy³aczone
		 * [2] - M30 lub M99
		 * [1] - M1
		 */
		
		int[] activeMCode = new int[4];
		activeMCode[0]=5;
		activeMCode[1]=9;
				
		int activeSpeed = -1;
		String activeP =null;
		
		int activeBlock = 0;
		
		boolean result = true;
		
	
	for(Function fu : this.f)	{
		
		
		
		activeBlock = fu.getBlock();
		
		if(fu.getComment()==null && fu.getMacro()==null)
		{
		
		//B rotation
		if(fu.getBRotation()!=null)
			activeBRotation=fu.getBRotation();
	
		//m functions
		if( fu.getMFunctin() != -1)
		{
			if(fu.getMFunctin()==5)
			{
				activeSpeed =(-1);
				activeMCode[0]=5;
			}
		
			else if(fu.getMFunctin()== 3 || fu.getMFunctin()== 4 || (getControls().isType(ControlTypes.FANUC) && fu.getMFunctin()==29))
			{
				activeSpeed =1;
				activeMCode[0]=fu.getMFunctin();
				
				if(fu.getMFunctin()==4)
				{
					this.writeWarninglog(" Lewe obroty wrzeciona  (N" + activeBlock + ")");
					warnings++;
					
				}
			}
			 
				else if( fu.getMFunctin() == 1 )
				{
					mOne=false;
					
					if(activeGCode[3]==41 || activeGCode[3]==42)
					{
						this.writeErrorMessage(" Brak wyjœcia z komensacji G41 lub G42 promieniowej narzêdzia (Brak G40) (N" + activeBlock + ")");
						errors++;
						result=false;
					}
					
					
					if(activeMCode[0]!=5)
					{
						this.writeWarninglog(" Postój warunkowy bez wy³¹czenia obrotów wrzeciona  (N" + activeBlock + ")");
						warnings++;
					}
					
					if(Wind.options.isbRotationCheck() && activeBRotation == null)
					{
						this.writeWarninglog(" Brak komendy ustawienia sto³u w osi B na tym narzêdziu  (N" + activeBlock + ")");
						warnings++;
					}
				}
				
				
				else if(fu.getMFunctin() == 6)
				{
					activeMCode[0]=5;
					activeBase =(-1);
					activeGCode[7]=0;
					activeBRotation = null;
					activeH="0";
					
				
						activeFeed =(-1);
					
						if(mOne)
						{
							
							this.writeWarninglog(" Brak postoju warunkowego M1 po zakoñczeniu pracy narzêdzia  (N" + activeBlock + ")");
							warnings++;						
							
						}
						mOne=true;
						
						
						if(fu.getToolNumber()==-1)
						{							
							activeTool=nextTool;
						
							nextTool=-1;						
							
						}						
						else
						{
							
							if(nextTool!=fu.getToolNumber() && nextTool!=-1 && Wind.options.isToolPreperationCheck()) 
							{
								this.writeWarninglog(" Brak wczeœniejszego przygotowania narzêdzia do wymiany  (N" + activeBlock + ")");
								warnings++;
							}
							
							
							activeTool=fu.getToolNumber();
							nextTool=-1;						
						}		
				}
				
			else 	if(fu.getMFunctin() == 8 || (getControls().isType(ControlTypes.FANUC) && fu.getMFunctin() == 12 ) || ((getControls().isType(ControlTypes.OKUMA) && fu.getMFunctin()==50 || fu.getMFunctin() == 51)) )
			{
				activeMCode[1]=1;
				
			}
			
			else	if( fu.getMFunctin() == 9)
			{
				activeMCode[1]=9;
				
			}
	
		}
		
		//feed
		if(fu.getFeed()!=-1 && fu.getMFunctin()==-1)
		{
			activeFeed=fu.getFeed();
		}
		
		
	
		//tool
		if(fu.getToolNumber()!=-1)
		{		
		nextTool = fu.getToolNumber();
		}
	
		
		//H
				if(!fu.getH().equals("0") && !(fu.getH().equals("A")||fu.getH().equals("B")||fu.getH().equals("C")))
				{
					activeH = fu.getH();
					if(activeTool != Integer.parseInt(activeH) && !this.getControls().isType(ControlTypes.OKUMA) )
					{
						this.writeWarninglog(" Numer korekcji H nie zgodny z aktywnym numerem narzêdzia!  (N" + activeBlock + ")");
						warnings++;
					}
					
					
					
				
				}
				
		//D
				
				if(!fu.getD().equals("0"))
				{
					
					activeD = fu.getD();
					if(!((fu.getD().equals("A")||fu.getD().equals("B")||fu.getD().equals("C"))) && ( activeTool != Integer.parseInt(activeD)))
					{
						
						this.writeWarninglog(" Numer korekcji D nie zgodny z aktywnym numerem narzêdzia!  (N" + activeBlock + ")");
						warnings++;
			
					}
					if(getControls().isType(ControlTypes.SINUMERIC))
					{
						activeGCode[7]=1;
					}
				}
		
		
				if(!fu.containsFunction(4)  && fu.getPoint()!=null)
				{			
		
					if(fu.getPoint().getX()!=null && fu.getPoint().getY()==null )
					{
						previousPoint=destinationPoint.clone();
						destinationPoint.setX(fu.getPoint().getX());
					}
					else if (fu.getPoint().getX()==null && fu.getPoint().getY()!=null )
					{
						previousPoint=destinationPoint.clone();
						destinationPoint.setY(fu.getPoint().getY());

					}
					else if (fu.getPoint().getX()!=null && fu.getPoint().getY()!=null)
					{
						previousPoint=destinationPoint.clone();
						destinationPoint.setX(fu.getPoint().getX());
						destinationPoint.setY(fu.getPoint().getY());
						
					}	
	
				}		
				
				
				if(surpassWarningfive<1 && fu.getPoint()!=null && ((fu.getPoint().getZ()!=null || fu.getPoint().getX()!=null || fu.getPoint().getY()!=null ) && activeBase==-1))
				{
					boolean ok =false;
					
					for(int i : fu.getFunctionType())
						if(i==28 || i==30)
						{
							ok = true;
						}
					if(!ok)
					{
						this.writeErrorMessage(" Nie zadeklarowano bazy po zmianie narzêdzia (N" + activeBlock + ")");
						errors++;
						result=false;
						surpassWarningfive++;
						break;
					}
				}

		// G code
		if(fu.getFunctionType()!=null)
		{
			
			Integer[] g = fu.getFunctionType().toArray(new Integer[0]);
			for(int i : g )
			{
				
				if(i==56 && getControls().isType(ControlTypes.OKUMA))
				{
					activeGCode[7]=56;
				}
				else if(i==43 && getControls().isType(ControlTypes.FANUC))
				{
					activeGCode[7]=43;
				}
			
				if(i==0 || i==1 || i==2 || i==3 || i==80 || i==73 || i== 76 || (i>80 && i<89))
				{
					
					
					if(i==80)
					{
						activeGCode[5]=0;
					}
					else if((i>80 && i<89) || i==76 || i==73)
						activeGCode[5]=1;
					
					
			
					
					
					//b³êdy zwi¹zane z cyklami
					if(activeGCode[5]==1)
					{
						
						if(activeMCode[1]==9 && (surpassWarningthree<2))
						{
							this.writeWarninglog(" Cykl sta³y bez w³¹czonego ch³odziwa (N" + activeBlock + ")");	
							warnings++;
							surpassWarningthree++;

						}
						if(i>=0 && i<=3 && (surpassWarningTwo<2)) 
						{
							this.writeErrorMessage(" Brak zakoñczonego cyklu komend¹ G80 (N" + activeBlock + ")");
							errors++;
							result=false;
							surpassWarningTwo++;
						}
					}

					//przypisanie aktywnego kodu G
					if(i>=0 && i<=3)
						activeGCode[0]=i;
					
						
					
					if((activeSpeed==-1 ) && (i!=0) && (i!=80))
					{

							this.writeErrorMessage(" Polecenie ruchu roboczego bez w³¹czonych obrotów wrzeciona (N" + activeBlock + ")");
							errors++;
							result=false;
	
					}
					
					if(i==2 || i==3)
					{
						Float[] circle = fu.getCircle().values().toArray(new Float[0]);
						
						try {
						if(Math.abs( Math.pow((destinationPoint.getX() -  (previousPoint.getX()+circle[0] )), 2)    +    Math.pow(( destinationPoint.getY() - (previousPoint.getY()+circle[1]) ), 2)                               -((circle[0]*circle[0])+(circle[1]*circle[1])))>0.05)
							{	
								this.writeErrorMessage(" Nieprawid³owy koñcowy punkt okrêgu (N" + activeBlock + ")");
								errors++;
								result=false;
		}
						}
						catch(NullPointerException e)
						{
							
							this.writeErrorMessage("Nie zdefiniowano wszystkich parametrów interpolacji ko³owej (N" + activeBlock + ")");
							errors++;
							result=false;
						}
	
					}
					

					if(activeGCode[1]==91 && surpassWarningOne<5 && Wind.options.isG90Check())
					{
						this.writeWarninglog(" Aktywne programowanie przyrostowe G91 (N" + activeBlock + ")");	
						warnings++;
						surpassWarningOne++;
					}
					
					if( !getControls().isType(ControlTypes.SINUMERIC) && activeH.equals("0") && i!=80 && surpassWarningsix<1  && fu.getPoint()!=null &&fu.getPoint().getZ()!=null )
					{
						
						this.writeErrorMessage(" Nie zadeklarowano korekcji H (N" + activeBlock + ")");
						errors++;
						surpassWarningsix++;
						result = false;					
					}	
		
				}
				else if(i==90 || i==91)
				{
					activeGCode[1]=i;
					
					
				}
				else if(i>=54 && i<=59)
				{
					activeBase = i;
					if(i==54 && getControls().isType(ControlTypes.FANUC))
					{
						activeP = fu.getP();
						
					}

				}
				else if(i==41 || i==40 || i==42)
				{
					activeGCode[3]=i;
					
					if(activeD.equals("0"))
					{
						this.writeErrorMessage(" Nie zadeklarowano korekcji D (N" + activeBlock + ")");
						errors++;
						result = false;
					}
	
				}
				else if(i==43 && (getControls().isType(ControlTypes.FANUC)))
				{
					activeGCode[7]=43;
					if((fu.getH().equals("0")) && (activeH.equals("0")))
					{
						this.writeErrorMessage(" Nie zadeklarowano korekcji H (N" + activeBlock + ")");
						errors++;
						result = false;
					}
					if(surpassWarningseventh <1 && fu.getPoint()==null)
					{
						surpassWarningseventh++;
						this.writeErrorMessage(" Nie wykonano najazdu na oœ Z podczas wczytania korekcji d³ugoœci narzêdzia (brak wspó³rzêdnej Z) (N" + activeBlock + ")");
						errors++;
						result = false;
	
					}

				}
			}
	
		}

		//S
		
		if(fu.getSpeed() != -1)
		{
			
			activeSpeed=fu.getSpeed();
			
			
			
			if(fu.getMFunctin()!=3 && fu.getMFunctin()!=4 && fu.getMFunctin()!=29 )
			{
					this.writeErrorMessage(" Zadanie obrotów wrzeciona bez kierunku M3/M4  (N" + activeBlock + ")");
					errors++;
					result=false;
			}
		}
		
	if(fu.getPoint()!=null && surpassWarningEight<1 && !fu.containsFunction(30) && !fu.containsFunction(28)) 
	{
		if(fu.getPoint().getZ()!=null && (activeGCode[7]==0 ||((getControls().isType(ControlTypes.FANUC) && activeGCode[7]!=43) || (getControls().isType(ControlTypes.OKUMA)&& activeGCode[7]!=56))))
		{
			
				surpassWarningEight++;
				this.writeErrorMessage(" Brak wczytanej korekcji d³ugoœciowej narzêdzia (N" + activeBlock + ")");
				errors++;
				result=false;
			
		}
	}
		//functions that cannot be in same block
		{
			boolean isGFourtyOneOrTwo =false;
			boolean isGTwoOrThree = false;
			
			if(fu.getMacro()!=null && fu.getComment()!=null)
			{
				for(int j : fu.getFunctionType())
				{
			
					if(j==41 || j==42)
						isGFourtyOneOrTwo=true;
					else if(j==2 || j==3)
						isGTwoOrThree = true;
				}
			}
			if(isGFourtyOneOrTwo && isGTwoOrThree )
			{
				errors++;
				result=false;
				writeErrorMessage(" Najazd z korekcj¹ promieniow¹ nie mo¿e odbywaæ siê podczas ruchu po interpolacji G2/G3 (N" + activeBlock + ")");
			}
		}
		}
	}
	
		JOptionPane.showMessageDialog(this, "Analiza programu: \n" + ((errors>0)?("Wykryto b³êdy wymagaj¹ce poprawy - szczegó³y patrz log"):("")) + "\nB³êdy: "+ errors+ "\nOstrze¿enia:" + warnings, "Sprawdzanie popranoœci kodu", JOptionPane.OK_OPTION, UIManager.getIcon("OptionPane.warningIcon"));
		return result;
		
	}

	
	/**
	 *  Zapisuje wszystkie bloki programu w edytorze w liscie do analizy
	 */
	public void analyze()
	{

		if(!f.isEmpty())
			f.clear();
		
		String[] input = this.txt.getTxtArea().getText().split("\\n"); 
	
		
			for(int i=0; i<input.length;i++)
			{
				if(input[i]!=null)
					f.add(new Function(input[i],sterowanie));
				
				//log
				if(FunctionAnalyzeUtilities.characterCountDifference(input[i], f.get(i).toString()) != 0)
				{
					Wind.log.writeInfoLog("Character count difference First:" + input[i]  + " Second:" + f.get(i).toString()+ " COUNT:" 
				+FunctionAnalyzeUtilities.characterCountDifference(input[i], f.get(i).toString()), Edytor.class.toString());
					
				}
					
			}
	
	}
	
	public int[] analyzeAndChange(String changeFrom, String changeTo)
	{
		if(!f.isEmpty())
			f.clear();
		String[] input = this.txt.getTxtArea().getText().split("\\n"); 
		String temp;
		List<Integer> blocks = new LinkedList<>();
		
		for(int i=0; i<input.length;i++)
		{
			if(input[i].contains(changeFrom))
			{
				if(FunctionAnalyzeUtilities.hasBlockNumber(input[i]))
				{
					blocks.add(FunctionAnalyzeUtilities.getIntResult());
					temp = input[i].replace(changeFrom, changeTo);
					f.add(new Function(temp,sterowanie));
				}
				
			}
			else f.add(new Function(input[i],sterowanie));
		
		}
		
		int result[] = new int[blocks.size()];
		
		for(int i = 0; i<result.length;i++)
		{
			result[i]=blocks.get(i);
		}
		
		
		return result;
		
	}
	
	public  int[] analyzeAndChangeWithBlocks( String changeFrom, String changeTo, int startingBlock, int endBlock)
	{
		if(!f.isEmpty())
			f.clear();
		this.analyze();
		
		String temp;
		Function fun;
		List<Integer> blocks = new LinkedList<>();
		
		for(int i=0;i<f.size();i++)
		{
			fun = f.get(i);
			
			if(fun.getBlock()>= endBlock)
				break;
			
			if(fun.getBlock() >= startingBlock)
			{
				temp= fun.toString();
				if(temp.contains(changeFrom) && FunctionAnalyzeUtilities.hasBlockNumber(temp))
				{
					
						blocks.add(FunctionAnalyzeUtilities.getIntResult());
						temp=temp.replace(changeFrom,changeTo);
				
						f.remove(i);
						f.add(i, new Function(temp,this.sterowanie));
					
				}
			}
		}
		
	int[] result = new int[blocks.size()];
		
		for(int i = 0; i<result.length;i++)
		{
			result[i]=blocks.get(i);		
		}

		return result;	
	}
	
	
	private  void countBlocks(int counter)
	{
		
		int i=0;
		for(Function fun:f)
		{
			fun.setBlockNumber(i);
			i+=counter;
			
		}
	}
	
	public void writelog(String s)
	{
		
		LocalTime d = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm:ss");
		
		this.logtxt.append("\n"+d.format(formatter) + ": " +s);
		
		
	}
	
	public void writeWarninglog(String s)
	{
		this.logtxt.append("\n\t(*UWAGA* )" +s);
		
	}
	
	public void writeErrorMessage(String s)
	{
		
		this.logtxt.append("\n\t(**B£¥D) " +s);
	}

	
	public void jumpToBlock(int toolBlock) {
		

		
			char[] temp = this.txt.getTxtArea().getText().toCharArray();
		
		//calculate how much chars we have to jump
			int charCounter=0;
			int rowCounter=0;

			while(rowCounter<toolBlock && charCounter<temp.length)
			{
				if(temp[charCounter++]=='\n')
					rowCounter++;
			}
			
			this.txt.getTxtArea().requestFocus();
			this.txt.getTxtArea().setCaretPosition(charCounter);
			
			int i=0;
			while(charCounter+i < temp.length && temp[charCounter + i]!='\n')
				i++;
			
			
			this.txt.getTxtArea().moveCaretPosition(charCounter+i);
	
	}
	
	
	public void jumpToLine(int lineNumber)
	{
		int charStart=0;
		int charEnd =0;
	
		char[] temp = this.txt.getTxtArea().getText().toCharArray();
		
		
		for(int i=0;i<temp.length;i++)
		{
			if(lineNumber==0)
				break;
			
			if(temp[i]=='\n')
			{
				lineNumber--;
			}
			charStart++;
			
			
			
		}
		
		charEnd=+charStart;
		int i=0;
		while((charStart+i++) <temp.length && temp[charStart+i]!='\n' )
			charEnd++;
		
		
		this.txt.getTxtArea().setCaretPosition(charStart);
		this.txt.getTxtArea().moveCaretPosition(charEnd);
	}

	
	public Sterowanie wykryjSterowanie()
	{
			final String WYKRYTO_OKUMA = "Wykryto sterowanie: Okuma";
			final String WYKRYTO_FANUC = "Wykryto sterowanie: Fanuc";
			final String WYKRYTO_SINUMERIC = "Wykryto sterowanie: Sinumeric";
	
		boolean name=false;
		analyzeWithNoControls();
		for(Function fu : this.f)
		{
			if(!fu.getD().equals("0") && (fu.getD().equals("A") || fu.getD().equals("B") || fu.getD().equals("C")))
			{	
				
						this.writelog(WYKRYTO_OKUMA);
						return new SterowanieOkuma();
			}
			
			if(fu.getMFunctin()!=-1)
			{
				if(fu.getMFunctin()==51 || fu.getMFunctin()==52 || fu.getMFunctin()==53)
				{
					this.writelog(WYKRYTO_OKUMA);
					return new SterowanieOkuma();
				}
				else if(fu.getMFunctin()==98 ||fu.getMFunctin()==99 || fu.getMFunctin()==130 || fu.getMFunctin()==127 || fu.getMFunctin()==128)
				{
					this.writelog(WYKRYTO_FANUC);
					return new SterowanieFanuc();
				}

			}
			
			if(fu.getFunctionType()!=null)
			{
				for (int i : fu.getFunctionType())
				{
					if(i==15 && !fu.getH().equals("0"))
					{
						this.writelog(WYKRYTO_OKUMA);
						return new SterowanieOkuma();
						
					}
					else if(i==43)
					{			
						this.writelog(WYKRYTO_FANUC);
						return new SterowanieFanuc();
					}
					else if(i>=81 && i<90)
					{
						if(fu.getMFunctin() >51 && fu.getMFunctin() <55)
						{
							this.writelog(WYKRYTO_OKUMA);
							return new SterowanieOkuma();
						}
						
						
						if(fu.getRcycleParam().length>1)
						{
							this.writelog("Wykryto sterowanie: Sinemeric");
							return new SterowanieSinumeric();
						}
					}
		
					else if(i==76)
					{
						if(fu.getQ()!=null)
						{
							this.writelog(WYKRYTO_FANUC);
							return new SterowanieFanuc();
						}
						else 
						{												
							if(fu.getCircle()!=null) {
								this.writelog(WYKRYTO_OKUMA);
								return new SterowanieOkuma(); 
							}
						}
					}
					else if(i==71 || (i==56 && !fu.getH().equals("0")))
					{	
						this.writelog(WYKRYTO_OKUMA);
						return new SterowanieOkuma(); 
					}
					
					else if(i==54 && fu.getP()!=null)
					{
						this.writelog(WYKRYTO_FANUC);
						return new SterowanieFanuc();
					}				
				}
			}
		
		if(fu.getMacro()!=null)
		{
			if(fu.getMacro().length()>3 && fu.getMacro().substring(0,4).equals("%MPF"))
			{
				this.writelog(WYKRYTO_SINUMERIC);
				return new SterowanieSinumeric(); 
			}
			else if(fu.getMacro().length()==1 && fu.getMacro().equals("%"))
				name = true;
			
			else if(name && fu.getMacro().length()>3 && fu.getMacro().substring(0,1).equals("O"))	
					return new SterowanieFanuc();
		}	
		}	
		this.writelog(WYKRYTO_SINUMERIC);
		return new SterowanieSinumeric();
	}


}
