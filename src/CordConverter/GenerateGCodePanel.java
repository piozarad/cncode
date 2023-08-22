package CordConverter;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import PlaneMachining.PlaneControler;
import PlaneMachining.PlaneModel;
import PlaneMachining.PlaneView;
import cncCodeGeneratingFunctions.Pocket;
import cncCodeGeneratingFunctions.Poglebienie;
import cncCodeGeneratingFunctions.Przepona;
import cncCodeGeneratingFunctions.Czop;
import cncCodeGeneratingFunctions.Drilling;
import cncCodeGeneratingFunctions.Faza;

public class GenerateGCodePanel extends JDialog{

	
	private JLabel poglebienie;
	private JLabel czop;
	private JLabel otwory;
	private JLabel faza;
	private JLabel kieszen;
	private JLabel plaszczyzna;
	private JLabel przepona;
	
	private JButton poglebienieButton;
	private JButton czopButton;
	private JButton otworyButton;
	private JButton fazaButton;
	private JButton kieszenButton;
	private JButton plaszczyznaButton;
	private JButton przeponaButton;
	
	private Edytor parent;
	
	
	GridBagLayout layout;
	
	private static final int BUTTON_WIDTH = 140;
	private static final int BUTTON_HEIGHT = 110;
	private static final Dimension DEFAULTBUTTONSIZE = new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
	private static final int SCALLINT_TYPE = Image.SCALE_SMOOTH;
	
	public GenerateGCodePanel(Edytor parent)
	{
		this.parent=parent;
		
		this.setPreferredSize(new Dimension(1150,250));
		this.setVisible(true);
		setTitle("Wybierz rodzaj cechy");
		setLocation(200, 200);
		setResizable(false);
		
		
		addWindowListener(new WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent e)
					{
						GenerateGCodePanel.this.setVisible(false);
					}
				});
		
		
		//layout
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		//gridBagConstraints
		GridBagConstraints border = new GridBagConstraints();
		
		//insets
		border.insets = new Insets(5,5,5,5);
		
		border.gridx=0;
		border.gridy=0;
		
		//buttons
		
		//poglebienie
		poglebienieButton = new JButton();
		poglebienieButton.setPreferredSize(DEFAULTBUTTONSIZE);
		Image poglebienieImage = new ImageIcon(this.getClass().getResource("/poglebienie.jpg")).getImage();
		poglebienieButton.addActionListener(e->
		{
			if(parent.pogl==null)
				parent.pogl=new Poglebienie(parent);
			else parent.pogl.setVisible(true);
			GenerateGCodePanel.this.setVisible(false);
		});
			
		
		poglebienieButton.setIcon(new ImageIcon(poglebienieImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, SCALLINT_TYPE)));
		this.add(poglebienieButton,border);
		
		
		//czopButton
		czopButton = new JButton();
		czopButton.setPreferredSize(DEFAULTBUTTONSIZE);
		Image czopImage = new ImageIcon(this.getClass().getResource("/czop.jpg")).getImage();
		czopButton.setIcon(new ImageIcon(czopImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, SCALLINT_TYPE)));
		czopButton.addActionListener(e->
		{
			if(parent.czop==null)
				parent.czop=new Czop(parent);
			else parent.czop.setVisible(true);
			GenerateGCodePanel.this.setVisible(false);
		});
		border.gridx=1;
		this.add(czopButton,border);
		
		
		//otworyButton
		otworyButton = new JButton();
		otworyButton.setPreferredSize(DEFAULTBUTTONSIZE);
		Image otworyImage = new ImageIcon(this.getClass().getResource("/otwory.jpg")).getImage();
		otworyButton.setIcon(new ImageIcon(otworyImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, SCALLINT_TYPE)));
		otworyButton.addActionListener(e->
		{
			if(parent.w==null)
				parent.w=new Drilling(parent);
			else parent.w.setVisible(true);
			GenerateGCodePanel.this.setVisible(false);
		});
		border.gridx=2;
		this.add(otworyButton,border);
		
		//fazaButton
		fazaButton = new JButton();
		fazaButton.setPreferredSize(DEFAULTBUTTONSIZE);
		Image fazaImage = new ImageIcon(this.getClass().getResource("/Faza.jpg")).getImage();
		fazaButton.setIcon(new ImageIcon(fazaImage.getScaledInstance(BUTTON_WIDTH,BUTTON_HEIGHT, SCALLINT_TYPE)));
		fazaButton.addActionListener(e->
		{
			if(parent.F==null)
				parent.F=new Faza(parent);
			else parent.F.setVisible(true);
			GenerateGCodePanel.this.setVisible(false);
		});
		border.gridx=3;
		this.add(fazaButton,border);
		
		//kieszenButton
		kieszenButton = new JButton();
		kieszenButton.setPreferredSize(DEFAULTBUTTONSIZE);
		Image kieszenImage = new ImageIcon(this.getClass().getResource("/Kieszeñ.jpg")).getImage();
		kieszenButton.setIcon(new ImageIcon(kieszenImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, SCALLINT_TYPE)));
		kieszenButton.addActionListener(e->
		{
			if(parent.poc==null)
				parent.poc=new Pocket(parent);
			else parent.poc.setVisible(true);
			GenerateGCodePanel.this.setVisible(false);
		});
		border.gridx=4;
		this.add(kieszenButton,border);
		
		//plaszczyna
		plaszczyznaButton = new JButton();
		plaszczyznaButton.setPreferredSize(DEFAULTBUTTONSIZE);
		Image plaszczyznaImage = new ImageIcon(this.getClass().getResource("/P³aszczyzna_czo³owa.jpg")).getImage();
		plaszczyznaButton.setIcon(new ImageIcon(plaszczyznaImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, SCALLINT_TYPE)));
		plaszczyznaButton.addActionListener(e->
		{
			if(parent.plaszczyzna==null)
			{
				PlaneModel model = new PlaneModel();
				parent.plaszczyzna = new PlaneView();
				PlaneControler controler = new PlaneControler(model,parent.plaszczyzna,parent);
			}
			else parent.plaszczyzna.setVisible(true);
			GenerateGCodePanel.this.setVisible(false);
		});
		border.gridx=5;
		this.add(plaszczyznaButton,border);
		
		//przepona
		przeponaButton = new JButton();
		przeponaButton.setPreferredSize(DEFAULTBUTTONSIZE);
		Image przeponaImage = new ImageIcon(this.getClass().getResource("/przepona.png")).getImage();
		przeponaButton.setIcon(new ImageIcon(przeponaImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, SCALLINT_TYPE)));
		przeponaButton.addActionListener(e->
		{
			if(parent.przep==null)
				parent.przep= new Przepona(parent);
			else
			{
				parent.przep.setAlwaysOnTop(true);
			}
		});
		
		border.gridx=6;
		this.add(przeponaButton,border);
		
		//Labels
		
		//poglebienie
		poglebienie = new JLabel("Frezowanie pog³êbienia/otworu");
		border.gridx=0;
		border.gridy=1;
		this.add(poglebienie,border);
		
		//czop
		czop = new JLabel("Frezowanie czopa");
		border.gridx++;
		this.add(czop,border);
		
		//otwory
		otwory = new JLabel("Wiercenie otworów");
		border.gridx++;
		this.add(otwory,border);
		
		//faza
		faza = new JLabel("Faza");
		border.gridx++;
		this.add(faza,border);
		
		//kieszen
		kieszen = new JLabel("Frezowanie kieszeni");
		border.gridx++;
		this.add(kieszen,border);
		
		//plaszczyzna
		plaszczyzna = new JLabel("Plaszczyzna");
		border.gridx++;
		this.add(plaszczyzna,border);
		
		//przpona
		przepona = new JLabel("Frezowanie p³askie / Przepona");
		border.gridx++;
		this.add(przepona,border);
		
		pack();
		
		
	}
	
	
}
