package CordConverter;


import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import CordConverter.Machine.MachineBuilder;


public class OptionsPanel extends JDialog
{
	
	private static final long serialVersionUID = -3555852567415649492L;
	private static final String OPTIONS_POPRAWNOSC = "Analiza kodu"; 
	private static final String OPTIONS_GENERATE = "Generowanie funkcji";
	private static final String OPTIONS_TIME = "Czas cyklu";
	private static final String MACHINE_TYPE = "Obrabiarka";
	private static final Insets INSET = new Insets(15,10,15,10);
	private static final Dimension TEXTFIELDIM = new Dimension(75,25);
	private static final Dimension WINDOWSIZE = new Dimension(400,450);
	private static final Dimension COMBO_BOX_DEFAULT_SIZE = new Dimension(150,25);
	private static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(150,25);
	
	
	//gui
	
		//labels
	 JLabel punktOkregu;
	 JLabel  odjazdZ;
	 JLabel przygotowanieLabel;
	 JLabel przyrostoweOstrzezenieLabel;
	 JLabel katObrotuStoluLabel;
	 JLabel redukcjaPusuwuLabel;
	 JLabel czasZmianyNarzedziaLabel;
	 JLabel czasZmianyNarzedziaJednistka;
	 JLabel efektywnyCzasPracyLabel;
	 JLabel efektywnyCzasPracyJednostka;
	 JLabel posowG0Label;
	 JLabel posowG0jednostka;
	 JLabel predkoscObrotuStoluLabel;
	 JLabel predkoscObrotuStoluJednostka;
	 JLabel czasZamocowaniaElementuLabel;
	 JLabel czasZamocowaniaElementuJednostka;
	 JLabel nazwaMaszyny;
	 JLabel maxZLabel;
	 JLabel maxToolLabel;
	
	//Buttons
	 JButton kasuj;
	 JButton nowy;
	
	//checkBoxes
	 JCheckBox przygotowanieBox;
	 JCheckBox przyrostoweOstrzezenieBox;
	 JCheckBox katObrotuStoluBox;
	
	//combo boxes
	JComboBox<Machine> machineCombo;

		//text fields
	 JTextField odjazdZTxt;
	 JTextField punktOkreguTxt;
	 JTextField czasZmianyNarzedziaField;
	 JTextField efektywnyCzasPracyField;
	 JTextField posowG0Field;
	 JTextField predkoscObrotuStoluField;
	 JTextField czasZamocowaniaElementuField;
	 JTextField maxZField;
	 JTextField maxToolNumberField;
	
	//spinners
	 JSpinner zwolnieniePosuwuSpinner;
	
	
	//panels
	 JPanel functionPanel;
	 JPanel analyzePanel;
	 JPanel timePanel;
	 JPanel machinePanel;
	
	
	

	
	public OptionsPanel(JFrame parent)
	{
		
		super(parent,"Opcje",false);
		
		
		setVisible(true);
		setResizable(false);
		setLocation(350,250);
		setPreferredSize(new Dimension(500,500));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		
		
		//Window closing
		this.addWindowListener(new java.awt.event.WindowAdapter()
				{
			
			
					@Override
					public void windowClosing(WindowEvent e) {

						 updateGlobalValues();
						 setVisible(false);

	
					}
				});
		
		
		//tab
		 JTabbedPane pane = new JTabbedPane(); 

		
		
		//panel 1 opcje generatora funkcji
		functionPanel = new JPanel();
		functionPanel.setSize(WINDOWSIZE);
		functionPanel.setLayout(new GridBagLayout());
		GridBagConstraints borderOne= new GridBagConstraints();
		borderOne.gridx=0;
		borderOne.gridy=0;
		borderOne.insets = INSET;
		
		
		//x0 y0 odjazd w osi Z Label
		odjazdZ = new JLabel("Bezpieczny odjazd w osi Z");
		odjazdZ.setToolTipText("Definiuje bezpieczny odjazd narzêdzia w osi Z po zakoñczeniu pracy narzêdzia lub podczas obrotu sto³u w osi B");
		functionPanel.add(odjazdZ,borderOne);
		
		//x1 y0 odjazd w osi Z text field
		borderOne.gridx=1;
		borderOne.gridy=0;
		odjazdZTxt = new JTextField(Integer.toString(Wind.options.getSafeRetraction()));
		odjazdZTxt.setPreferredSize(TEXTFIELDIM);
		functionPanel.add(odjazdZTxt,borderOne);

		
		//x0 y1 zwolnienie posuwu Label
		borderOne.gridx=0;
		borderOne.gridy=1;
		redukcjaPusuwuLabel = new JLabel("Redukcja posuwu po okrêgu");
		redukcjaPusuwuLabel.setToolTipText("Redukuje posuw mno¿¹c go o podan¹ wartoœæ podczas ruchu po okrêgu (aktywne przy frezowaniu kieszeni)");
		functionPanel.add(redukcjaPusuwuLabel,borderOne);
		
		//x1 y1 redukcja posuwu spinner
		borderOne.gridx=1;
		SpinnerNumberModel model = new SpinnerNumberModel(Wind.options.getFeedRateReductionOnCircle(), 0.1, 1, 0.1);
		zwolnieniePosuwuSpinner = new JSpinner(model);
		functionPanel.add(zwolnieniePosuwuSpinner,borderOne);
		
		
		
		pane.add(functionPanel,OPTIONS_GENERATE);
		
	
		
		
		//panel 2 opcje analizy kodu
		analyzePanel = new JPanel();
		analyzePanel.setSize(WINDOWSIZE);
		pane.add(analyzePanel,OPTIONS_POPRAWNOSC);
		analyzePanel.setLayout(new GridBagLayout());
		GridBagConstraints borderTwo = new GridBagConstraints();
		borderTwo.gridx=0;
		borderTwo.gridy=0;
		borderTwo.insets = INSET;
		
		
		//x0 y0 Tolerancja po³o¿enia punktu na okrêgu
		punktOkregu = new JLabel("Tolerancja po³o¿enia punktu na okrêgu");
		punktOkregu.setToolTipText("Ustala maksymalne odchylenie punktu koñcowego w funkcji G2/G3 od zdefiniowanego okrêgu");
		analyzePanel.add(punktOkregu,borderTwo);
		
		//x1 y0
		punktOkreguTxt = new JTextField(Float.toString(Wind.options.getCircleTolerance()));
		punktOkreguTxt.setPreferredSize(TEXTFIELDIM);
		borderTwo.gridx=1;
		borderTwo.gridy=0;
		analyzePanel.add(punktOkreguTxt,borderTwo);
		
		
		//x0 y1 przygotowanie narzêdzia do wymiany
		przygotowanieLabel = new JLabel("Sprawdzaj  przygotowanie narzêdzi");
		przygotowanieLabel.setToolTipText("Sprawdzaj podczas komendy M6 czy dane narzêdzie zosta³o wczeœniej podstawione do wymiany");
		borderTwo.gridx=0;
		borderTwo.gridy=1;
		analyzePanel.add(przygotowanieLabel,borderTwo);
		
		
		//x1 y1 przygotowanie checkBox
		przygotowanieBox = new JCheckBox();
		if(Wind.options.isToolPreperationCheck())
			przygotowanieBox.setSelected(true);
		borderTwo.gridx=1;
		borderTwo.gridy=1;
		analyzePanel.add(przygotowanieBox,borderTwo);
		
		//x0 y2 ostrze¿enie przed aktywnym programowaniem przyrostowym
		przyrostoweOstrzezenieLabel = new JLabel("Osrzegaj w razie wywo³ania G91");
		przyrostoweOstrzezenieLabel.setToolTipText("Ostrzegaj w wypadku aktywowania programowania przyrostowego G91 w razie gdyby zosta³o b³êdnie wywo³ane zamiast programowania absolutnego G90");
		borderTwo.gridx=0;
		borderTwo.gridy=2;
		analyzePanel.add(przyrostoweOstrzezenieLabel,borderTwo);
		
		//x1 Y2 ostrze¿enie przez programowaniem przyrostowym checkBox
		przyrostoweOstrzezenieBox = new JCheckBox();
		if(Wind.options.isG90Check())
			przyrostoweOstrzezenieBox.setSelected(true);
		borderTwo.gridx=1;
		borderTwo.gridy=2;
		analyzePanel.add(przyrostoweOstrzezenieBox,borderTwo);
		
		//x0 y3 kat obrotu sto³u
		katObrotuStoluLabel = new JLabel("Sprawdzaj kat obrotu sto³u po ka¿dej wymianie narzêdzia");
		katObrotuStoluLabel.setToolTipText("<html>Po ka¿dej wymianie narzêdzia sprawdzaj obecnoœæ komendy ustawienia sto³u w osi B aby upewniæ siê ¿e program zostanie bezpiecznie puszczony w razie wczytania od bloku. <br>Aby funkcja dzia³a³a poprawnie nale¿y na koñcu pracy narzêdzia dodaæ komendê M1</html>");
		borderTwo.gridx=0;
		borderTwo.gridy=3;
		analyzePanel.add(katObrotuStoluLabel,borderTwo);
		
		
		// x1 y3 kat obrotu sto³u check box
		katObrotuStoluBox = new JCheckBox();
		if(Wind.options.isbRotationCheck())
			katObrotuStoluBox .setSelected(true);
		borderTwo.gridx=1;
		borderTwo.gridy=3;
		analyzePanel.add(katObrotuStoluBox,borderTwo);
		

		
		//panel 3 time panel
		timePanel = new JPanel();
		timePanel.setSize(WINDOWSIZE);
		pane.add(timePanel,OPTIONS_TIME);
		timePanel.setLayout(new GridBagLayout());
		GridBagConstraints borderThree = new GridBagConstraints();
		borderThree.gridx=0;
		borderThree.gridy=0;
		borderThree.insets = INSET;
		
		
		
		
		//x0 y0 czas pracy label
		borderThree.gridx=0;
		borderThree.gridy=0;
		efektywnyCzasPracyLabel = new JLabel("Efektywny czas pracy na 8h");
		timePanel.add(efektywnyCzasPracyLabel,borderThree);
		
		//x1 y0 text field czas pracy
		borderThree.gridx=1;
		borderThree.gridy=0;
		efektywnyCzasPracyField = new JTextField(Integer.toString(Wind.options.getEffectiveWorkTime()));
		efektywnyCzasPracyField.setPreferredSize(TEXTFIELDIM);
		timePanel.add(efektywnyCzasPracyField,borderThree);
		
		//x2 y0 jednostaka czas pracy
		borderThree.gridx=2;
		borderThree.gridy=0;
		efektywnyCzasPracyJednostka =new JLabel("minuty");
		timePanel.add(efektywnyCzasPracyJednostka,borderThree);
		
		

		
		//x0 y1 czas zamowcowania detalu label
		borderThree.gridx=0;
		borderThree.gridy=1;
		czasZamocowaniaElementuLabel = new JLabel("Czas mocowania detalu");
		timePanel.add(czasZamocowaniaElementuLabel,borderThree);
		
		//x1 y1 czas zamocowania detalu field
		borderThree.gridx=1;
		czasZamocowaniaElementuField = new JTextField(Integer.toString(Wind.options.getPartFixureTime()));
		czasZamocowaniaElementuField.setPreferredSize(TEXTFIELDIM);
		timePanel.add(czasZamocowaniaElementuField, borderThree);
		
		//x2 y4 czas zamocowania detalu jednostka
		borderThree.gridx=2;
		czasZamocowaniaElementuJednostka = new JLabel("s");
		timePanel.add(czasZamocowaniaElementuJednostka,borderThree);

		
		//PANEL4 typ maszyny
		machinePanel = new JPanel();
		machinePanel.setSize(WINDOWSIZE);
		pane.add(machinePanel,MACHINE_TYPE);
		GridBagConstraints borderFour = new GridBagConstraints();
		machinePanel.setLayout(new GridBagLayout());
		borderFour.gridx=0;
		borderFour.gridy=0;
		borderFour.insets = INSET;
		
		//x0 y0 machine name label
		nazwaMaszyny = new JLabel("Maszyna");
		machinePanel.add(nazwaMaszyny,borderFour);
		
		//y0 x1 machine combo box
		borderFour.gridx=1;
		machineCombo = new JComboBox<>(Wind.options.getMachineArray());
		machineCombo.setSelectedIndex(Wind.options.getMachineComboSelectedItem());
		machineCombo.setPreferredSize(COMBO_BOX_DEFAULT_SIZE);
		machinePanel.add(machineCombo,borderFour);
		
		// xo y1 czas zmiany narzedzia
		borderFour.gridx=0;
		borderFour.gridy=1;
		czasZmianyNarzedziaLabel = new JLabel("Czas zmiany narzedzia");
		machinePanel.add(czasZmianyNarzedziaLabel,borderFour);
						
		// x1 y1 czas zmiany narzedzia field
		borderFour.gridx=1;
		czasZmianyNarzedziaField = new JTextField(Integer.toString(Wind.options.getToolChangeTime()));
		czasZmianyNarzedziaField.setPreferredSize(TEXTFIELDIM);
		machinePanel.add(czasZmianyNarzedziaField,borderFour);
				
		// x2 y1 jednostka
		borderFour.gridx=2; 
		czasZmianyNarzedziaJednistka = new JLabel("s");
		machinePanel.add(czasZmianyNarzedziaJednistka,borderFour);
		
		//x0 y2 posow szybki label
		borderFour.gridx=0;
		borderFour.gridy=2;
		posowG0Label = new JLabel("Posow G0");
		machinePanel.add(posowG0Label,borderFour);
				
		//x1 y2 posow szybki txt
		borderFour.gridx=1;
		posowG0Field = new JTextField(Integer.toString(Wind.options.getMaxFeed()));
		posowG0Field.setPreferredSize(TEXTFIELDIM);
		machinePanel.add(posowG0Field,borderFour);
				
		//x2 y2 posow szybki jednostka
		borderFour.gridx=2;
		posowG0jednostka = new JLabel("mm/min");
		machinePanel.add(posowG0jednostka,borderFour);
		
		//x0 y3 predkosc obrotu stolu label
		borderFour.gridx=0;
		borderFour.gridy=3;
		predkoscObrotuStoluLabel = new JLabel("Predkosc obrotu stolu w osi B");
		machinePanel.add(predkoscObrotuStoluLabel,borderFour);
				
		//x1 y3 predkosc obrotu stolu field
		borderFour.gridx=1;
		predkoscObrotuStoluField = new JTextField(Integer.toString(Wind.options.getABCAxisFeedRate()));
		predkoscObrotuStoluField.setPreferredSize(TEXTFIELDIM);
		machinePanel.add(predkoscObrotuStoluField,borderFour);
				
				
		//x2 y3 predkosc obrotu stolu jednostka
		borderFour.gridx=2;
		predkoscObrotuStoluJednostka = new JLabel("mm/min");
		machinePanel.add(predkoscObrotuStoluJednostka,borderFour);
		
		//x0 y4 max Z cordinate label
		borderFour.gridx=0;
		borderFour.gridy=4;
		maxZLabel = new JLabel("Maksymalna wspó³rzêdna osi Z");
		machinePanel.add(maxZLabel,borderFour);
		
		//x1 y4 max Z cordinal text field
		borderFour.gridx=1;
		maxZField = new JTextField(Integer.toString(Wind.options.getMaxZAxisValue()));
		maxZField.setPreferredSize(TEXTFIELDIM);
		machinePanel.add(maxZField,borderFour);
		
		//x0 y5 max tool number label
		borderFour.gridx=0;
		borderFour.gridy=5;
		maxToolLabel = new JLabel("Pojemnoœæ magazynu");
		machinePanel.add(maxToolLabel,borderFour);
		
		//x1 y5 max tool number text field
		borderFour.gridx=1;
		maxToolNumberField = new JTextField(Integer.toString(Wind.options.getMaxToolNumber()));
		maxToolNumberField.setPreferredSize(TEXTFIELDIM);
		machinePanel.add(maxToolNumberField,borderFour);
		
		//x0 y6 JButton Nowy
		borderFour.gridx=0;
		borderFour.gridy=6;
		nowy = new JButton("Nowy");
		nowy.setPreferredSize(DEFAULT_BUTTON_SIZE);
		machinePanel.add(nowy,borderFour);
		nowy.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						
						String machineName =JOptionPane.showInputDialog(OptionsPanel.this,"","Wpisz nazwê maszyny",JOptionPane.OK_CANCEL_OPTION);
						if(machineName!=null)
						{
							machineCombo.addItem(new Machine(machineName));
							machineCombo.setSelectedIndex(machineCombo.getModel().getSize()-1);
							
						}
						
					}
				});
		
		
		//x1 y6
		borderFour.gridx=1;
		kasuj = new JButton("Kasuj");
		kasuj.setPreferredSize(DEFAULT_BUTTON_SIZE);
		machinePanel.add(kasuj,borderFour);
		kasuj.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						if(machineCombo.getItemCount()>0)
						{
							machineCombo.removeItem(machineCombo.getSelectedItem());
							if(machineCombo.getItemCount()>0)
							{
								machineCombo.setSelectedIndex(0);
								viewMachineSpec();
						
							}
						}
					
						
					}
				});
		
		

		
		this.machineCombo.addItemListener(new ItemListener() {


			@Override
			public void itemStateChanged(ItemEvent e) {
				viewMachineSpec();
				
			}
			
		});
		
		this.machineCombo.addMouseListener(new MouseListener() {


			@Override
			public void mouseClicked(MouseEvent e) {
				//Unsuported
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				//Unsuported
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//Unsuported
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(machineCombo.getItemCount()>0)
				{
					machineCombo.getModel().getElementAt(machineCombo.getSelectedIndex()).updateValues(getValues());
		
				}
	
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//Unsuported
				
			}

		});
		
		
		this.add(pane);
		this.pack();
		setVisible(false);
		setVisible(true);
		
		
		
	}



	private void viewMachineSpec()
	{
		
		this.czasZmianyNarzedziaField.setText(Integer.toString(((Machine)this.machineCombo.getSelectedItem()).getToolChangeTime()));
		this.predkoscObrotuStoluField.setText(Integer.toString(((Machine)this.machineCombo.getSelectedItem()).getAbcAxisRotationFeed()));
		this.maxZField.setText(Integer.toString(((Machine)this.machineCombo.getSelectedItem()).getMaxZ()));
		this.posowG0Field.setText(Integer.toString(((Machine)this.machineCombo.getSelectedItem()).getMaxFeed()));
		this.maxToolNumberField.setText(Integer.toString(((Machine)this.machineCombo.getSelectedItem()).getMaxToolNumber()));
		
	}

	
	private Machine getValues() {

		int maxZ;
		int czasZmianyNarze;
		int predkoscObrotuStolu;
		int posowSzybki;
		int maxToolNumber;
		
		try
		{
			maxZ=Integer.parseInt(this.maxZField.getText());
		}
		catch(NumberFormatException e)
		{
			maxZ=400;
		}
		
		try
		{
			czasZmianyNarze=Integer.parseInt(this.czasZmianyNarzedziaField.getText());
		}
		catch(NumberFormatException e)
		{
			czasZmianyNarze=2;
		}
		
		try
		{
			predkoscObrotuStolu=Integer.parseInt(this.predkoscObrotuStoluField.getText());
		}
		catch(NumberFormatException e)
		{
			predkoscObrotuStolu=4000;
		}
		
		try
		{
			posowSzybki=Integer.parseInt(this.posowG0Field.getText());
		}
		catch(NumberFormatException e)
		{
			posowSzybki=8000;
		}
		
		try
		{
			maxToolNumber=Integer.parseInt(this.maxToolNumberField.getText());
		}
		catch(NumberFormatException e)
		{
			maxToolNumber=39;
		}
		
		
		return new MachineBuilder()
				.name(defaultMachineName())
				.abcAxisRotationFeed(predkoscObrotuStolu)
				.maxFeed(posowSzybki)
				.maxToolNumber(maxToolNumber)
				.maxZ(maxZ)
				.toolChangeTime(czasZmianyNarze)
				.build();
		
	}
	
	private String defaultMachineName()
	{
		String result;
		try
		{
			result= ((Machine)this.machineCombo.getSelectedItem()).getName();
		}
		catch (NullPointerException e)
		{
			result ="Domyœlna maszyna";
		}
		
		return result;
	}
	
	
	//updates values from text fields or with default values if an error occures
	public void updateGlobalValues()
	{
		try {
			Wind.options.setSafeRetraction(Integer.parseInt(this.odjazdZTxt.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setSafeRetraction(400);
		}
		
		try {
			Wind.options.setToolChangeTime(Integer.parseInt(this.czasZmianyNarzedziaField.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setToolChangeTime(3);
		}
		
		try {
			Wind.options.setCircleTolerance(Float.parseFloat(this.punktOkreguTxt.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setCircleTolerance(0.03f);
		}
			
		
		try {
			Wind.options.setMaxFeed(Integer.parseInt(this.posowG0Field.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setMaxFeed(10000);
		}
				

		try {
			Wind.options.setEffectiveWorkTime(Integer.parseInt(this.efektywnyCzasPracyField.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setEffectiveWorkTime(390);
		}

		try {
			Wind.options.setABCAxisFeedRate(Integer.parseInt(this.predkoscObrotuStoluField.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setABCAxisFeedRate(4000);
		}

		try {
			Wind.options.setPartFixureTime(Integer.parseInt(this.czasZamocowaniaElementuField.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setPartFixureTime(120);
		}

		try {
			Wind.options.setMaxZAxisValue(Integer.parseInt(this.maxZField.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setMaxZAxisValue(400);
		}

		try {
			Wind.options.setMaxToolNumber(Integer.parseInt(this.maxToolNumberField.getText()));
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setMaxToolNumber(40);
		}
		

		try {
			Wind.options.setFeedRateReductionOnCircle((double)this.zwolnieniePosuwuSpinner.getValue());
		}	
		catch (NumberFormatException e)
		{
			Wind.options.setFeedRateReductionOnCircle(40);
		}
		
		
		Wind.options.setMachineList(this.machineCombo.getModel());
		
		
		
		
		
		Wind.options.setToolPreperationCheck(przygotowanieBox.isSelected());
		Wind.options.setG90Check(przyrostoweOstrzezenieBox.isSelected());
		Wind.options.setbRotationCheck(katObrotuStoluBox.isSelected());
		Wind.options.setMachineComboSelectedItem(this.machineCombo.getSelectedIndex());
	
		



	}
	
	
	


}
