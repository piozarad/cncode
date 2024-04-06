package PlaneMachining;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;

import CordConverter.Point;
import CordConverter.TYPE;
import DrawFunction.DrawCordinateSystem;
import DrawFunction.DrawTool;
import DrawFunction.RectangleWithDimensions;
import DrawFunction.ToolPath;

public class PlaneView extends JFrame {

	//x0
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel methodLabel;
	private JLabel x0Label;
	private JLabel y0Label;
	private JLabel baseLabel;
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel zUpperLabel;
	private JLabel zBottomLabel;
	private JLabel apLabel;
	private JLabel srednicaNarzedziaLabel;
	private JLabel sprindleSpeedLabel;
	private JLabel feedLabel;
	private JLabel promienLabel;
	private JLabel numberOfTeethLabel;
	private JLabel vcLabel;
	private JLabel fnLabel;
	private JLabel finishingPassLabel;
	private JToggleButton obstacleFromLeftSideButton;
	private JLabel distanceLabel;
	private JLabel aeLabel;
	
	//x1
	private JComboBox<String> method;
	private JTextField x0Text;
	private JTextField y0Text;
	private JComboBox<String> baza;
	private JTextField partWidth;
	private JTextField partHeight;
	private JTextField zUpper;
	private JTextField zBottom;
	private JTextField apTxtField;
	private JTextField finishingPassTextField;
	private JTextField toolDiameterTextField;
	private JTextField sprindleSpeed;
	private JTextField feedTextField;
	private JButton executeButton;
	private JButton generateButton;
	private JCheckBox smoothenCheckBox;
	private JTextField radiusField;
	private JSpinner numberOfTeeth;
	private JTextField vc;
	private JTextField fn;
	private JButton reverse;
	private JCheckBox finishingPassCheckBox;
	private JToggleButton upperObstacleButton;
	private JToggleButton bottomObstacleButton;
	private JTextField distanceField;
	private JTextField aeTextField;
	
	
	//x2 jednostki
	private JLabel widthUnit;
	private JLabel heightUnit;
	private JLabel apUnit;
	private JLabel materialThicknessUnit;
	private JLabel toolDiameterUnit;
	private JLabel sprindleSpeedUnit;
	private JLabel feedUnit;
	private JLabel radiusUnit;
	private JLabel vcUnit;
	private JLabel fnUnit;
	private JLabel finishingPassunit;
	private JButton cancelButton;
	private JToggleButton obstacleFromRightSideButton;
	private JLabel obstacleUnit;
	private JLabel startingPointsLabel;
	private JComboBox<String> startingPointsComboBox;
	private JLabel aeUnit;
	
	Visual canvas;
	private List<Point> toolpath;
	
	private float baseXPosition;
	private float baseYPosition;
	private boolean parameters = true;
	private Point toolStartPoint;
	private boolean isToolPathGenerated = false;
	
	
	private static final String[] METHOD = {"Do srodka"};
	private static final String[] BASE = {"X w osi Y w osi plyty"};
	private static final String[] PUNKTY_STARTU = {"lewy","prawy","góra","dó³"};
	
	
	private static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(90,25);
	private static final Dimension CANVAS_SIZE = new Dimension(600,500);
	
	private int directionsLocked=0;
	
	
	public PlaneView()
	{
		
		setTitle("Frezuj Plaszczyzne");
		this.setResizable(false);
		this.setLocation(500, 160);
		setVisible(true);
		setSize(500,500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(new FlowLayout());
		
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				PlaneView.this.cancelButton.doClick();
			}
		});
		
		
		JTabbedPane pane = new JTabbedPane(); 
		pane.setPreferredSize(new Dimension(340,320));
		
		JPanel leftPanel = new JPanel();
		JPanel parametryPanel = new JPanel();
		JPanel methodPanel = new JPanel();
		JPanel narzedziePanel = new JPanel();
		JPanel przeszkodyPanel = new JPanel();
		JPanel geometriaPlytyPanel = new JPanel();
		JPanel bazyPanel = new JPanel();
		JPanel przyciskiPanel=new JPanel();
		JPanel naddatkiPanel = new JPanel();
		JPanel punktyStartuPanel = new JPanel();
		
		GridBagLayout layout = new GridBagLayout();
		
		BorderLayout borderLayout = new BorderLayout();
		
		leftPanel.setLayout(borderLayout);
		leftPanel.setPreferredSize(new Dimension(350,450));
		geometriaPlytyPanel.setLayout(layout);
		bazyPanel.setLayout(layout);

		
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(5,5,5,5);
		

		BorderLayout geometriaLayout = new BorderLayout();
		JPanel geometriaPanel = new JPanel();
		geometriaPanel.setLayout(geometriaLayout);
		
		
		//****PUNKTY STARTU****//	
		border.gridx=0;
		border.gridy=0;
		punktyStartuPanel.setLayout(layout);
		
		//x0 y0
		startingPointsLabel = new JLabel("Punkty startu:");
		startingPointsLabel.setToolTipText("Ustal poczatkowe ustawienie narzedzia i stronê od jakiej ma wejsc w material");
		punktyStartuPanel.add(startingPointsLabel,border);
		
		//x1
		border.gridx++;
		startingPointsComboBox = new JComboBox<>(PUNKTY_STARTU);
		startingPointsComboBox.setSelectedItem(null);

		punktyStartuPanel.add(startingPointsComboBox,border);
		
		//****PRZESZKODY****//
		border.gridx=0;
		border.gridy=0;
		przeszkodyPanel.setLayout(layout);
		
		//x1 y0
		border.gridx=1;
		upperObstacleButton = new JToggleButton("^");
		upperObstacleButton.setToolTipText("Przeszkoda od gory plyty");
		upperObstacleButton.setPreferredSize(DEFAULT_BUTTON_SIZE);
		upperObstacleButton.addActionListener(n->{
			if(this.upperObstacleButton.isSelected() )
			{
				if(validateNumberOfDirectionsLocked())
				{
				
					this.directionsLocked++;
				}
				else upperObstacleButton.setSelected(false);	
				}
			else {
				this.directionsLocked--;	
			
			}
			PlaneView.this.startingPointsComboBox.setSelectedItem(null);
			setToolPathGeneratedFlag(false);
			PlaneView.this.repaint();
			
		});
		przeszkodyPanel.add(upperObstacleButton,border);
		
		//x0 y1
		border.gridx=0;
		border.gridy=1;
		obstacleFromLeftSideButton = new JToggleButton("<");
		obstacleFromLeftSideButton.setToolTipText("Przeszkoda z lewej strony");
		obstacleFromLeftSideButton.setPreferredSize(DEFAULT_BUTTON_SIZE);
		obstacleFromLeftSideButton.addActionListener(n->{
			if(this.obstacleFromLeftSideButton.isSelected())
			{
				if(validateNumberOfDirectionsLocked())
				{
				
					this.directionsLocked++;
				}
				else obstacleFromLeftSideButton.setSelected(false);	
				}
			else {
				this.directionsLocked--;	
			
			}
			PlaneView.this.startingPointsComboBox.setSelectedItem(null);
			setToolPathGeneratedFlag(false);
			PlaneView.this.repaint();
		});
		przeszkodyPanel.add(obstacleFromLeftSideButton,border);
		
		//x2 y1
		border.gridx=2;
		border.gridy=1;
		obstacleFromRightSideButton = new JToggleButton(">");
		obstacleFromRightSideButton.setToolTipText("Przeszkoda z prawej strony");
		obstacleFromRightSideButton.setPreferredSize(DEFAULT_BUTTON_SIZE);
		obstacleFromRightSideButton.addActionListener(n->{
			if(obstacleFromRightSideButton.isSelected())
			{
				if(validateNumberOfDirectionsLocked())
				{
			
					this.directionsLocked++;
				}
				else obstacleFromRightSideButton.setSelected(false);	
				}
			else {
				this.directionsLocked--;	
			}
			PlaneView.this.startingPointsComboBox.setSelectedItem(null);
			setToolPathGeneratedFlag(false);
			PlaneView.this.repaint();
			
		});
		przeszkodyPanel.add(obstacleFromRightSideButton,border);
		
		//x1 y2
		border.gridx=1;
		border.gridy=2;
		bottomObstacleButton = new JToggleButton("v");
		bottomObstacleButton.setToolTipText("Przeszkoda od do³u");
		bottomObstacleButton.setPreferredSize(DEFAULT_BUTTON_SIZE);
		bottomObstacleButton.addActionListener(n->{
			if(bottomObstacleButton.isSelected())
			{
				if(validateNumberOfDirectionsLocked())
				{
					this.directionsLocked++;
				}
				else bottomObstacleButton.setSelected(false);	
				}
			else {
				this.directionsLocked--;	
			
			}
			PlaneView.this.startingPointsComboBox.setSelectedItem(null);
			setToolPathGeneratedFlag(false);
			PlaneView.this.repaint();
			
		});
		przeszkodyPanel.add(bottomObstacleButton,border);
		
		
		//y3 przyciski
		border.gridx=0;
		border.gridy=4;
		distanceLabel = new JLabel("Odleg³¶æ od p³yty");
		distanceLabel.setToolTipText("Odsuwa obroke o podana odleglosc od zaznaczonych przeszkod");
		przeszkodyPanel.add(distanceLabel,border);
		
		border.gridx=1;
		distanceField = new JTextField("4");
		distanceField.setPreferredSize(DEFAULT_BUTTON_SIZE);
		distanceField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// not used
			}
			@Override
			public void keyPressed(KeyEvent e) {
				// not used	
			}
			@Override
			public void keyReleased(KeyEvent e) {
				resetToolStartingPointComboBox();
			}
			
		});
		przeszkodyPanel.add(distanceField,border);
		
		border.gridx=2;
		obstacleUnit = new JLabel("mm");
		przeszkodyPanel.add(obstacleUnit,border);
		
		//****BAZY ****//
		border.gridx=0;
		border.gridy=0;
		bazyPanel.setBorder(BorderFactory.createTitledBorder("Punkt bazowy"));
		
		x0Label = new JLabel("X0");
		bazyPanel.add(x0Label,border);
		
		border.gridy++;
		y0Label = new JLabel("Y0 ");
		bazyPanel.add(y0Label,border);
		
		border.gridy++;
		baseLabel = new JLabel("Punkt bazowy");
		baseLabel.setToolTipText("Okresla umiejscowienie srodka ukladu wspolrzêdnych");
		bazyPanel.add(baseLabel,border);
		
		//X1
		border.gridx=1;
		border.gridy=0;

		x0Text = new JTextField("0");
		x0Text.setPreferredSize(DEFAULT_BUTTON_SIZE);
		x0Text.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				//unused 	
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//unused 
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try
				{
					PlaneView.this.baseXPosition = Float.parseFloat(x0Text.getText());
				}
				catch(NumberFormatException ex)
				{
					PlaneView.this.baseXPosition = 0;
				}
				
				PlaneView.this.repaint();
			}});
		bazyPanel.add(x0Text,border);
		
		border.gridy++;
		y0Text = new JTextField("0");
		y0Text.setPreferredSize(DEFAULT_BUTTON_SIZE);
		y0Text.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				//unused 
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//unused 
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try
				{
					PlaneView.this.baseYPosition = Float.parseFloat(y0Text.getText());
				}
				catch(NumberFormatException ex)
				{
					PlaneView.this.baseYPosition = 0;
				}
				
				PlaneView.this.repaint();
			}});
		bazyPanel.add(y0Text,border);
		
		border.gridy++;
		baza = new JComboBox<>(BASE);
		baza.setPreferredSize(DEFAULT_BUTTON_SIZE);
		bazyPanel.add(baza,border);
		
		geometriaPanel.add(bazyPanel,BorderLayout.NORTH);
	
		//****GEOMETRIA PLYTY****//
		geometriaPlytyPanel.setBorder(BorderFactory.createTitledBorder("Geometria p³yty"));
		//x0
		border.gridx=0;
		border.gridy=0;
		widthLabel = new JLabel("Szeroko¶c plyty");
		geometriaPlytyPanel.add(widthLabel,border);
	
		border.gridy++;
		heightLabel = new JLabel("Wysokosc plyty");
		geometriaPlytyPanel.add(heightLabel,border);

		border.gridy++;
		zBottomLabel = new JLabel("Wpolrzêdna Z na gotowo");
		zBottomLabel.setToolTipText("Wspolrzedna Z plaszczyzny przefrezowanej");
		geometriaPlytyPanel.add(zBottomLabel,border);
		
		geometriaPanel.add(geometriaPlytyPanel,BorderLayout.SOUTH);

		//x1
		border.gridx=1;
		border.gridy=0;
		partWidth = new JTextField("50");
		partWidth.setPreferredSize(DEFAULT_BUTTON_SIZE);
		partWidth.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) {
				// UNUSED METHOD	
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// UNUSED METHOD
			}

			@Override
			public void keyReleased(KeyEvent e) {
				canvas.update();
				
			}}
		);
		geometriaPlytyPanel.add(partWidth,border);
		
		
		border.gridy++;
		partHeight = new JTextField("50");
		partHeight.setPreferredSize(DEFAULT_BUTTON_SIZE);
		partHeight.addKeyListener(new KeyListener()
				{

					@Override
					public void keyTyped(KeyEvent e) {
						// UNUSED METHOD		
					}

					@Override
					public void keyPressed(KeyEvent e) {
						// UNUSED METHOD	
					}
					@Override
					public void keyReleased(KeyEvent e) {
						canvas.update();
						
					}}
				);
		geometriaPlytyPanel.add(partHeight,border);
		
	
		border.gridy++;
		zBottom = new JTextField("200");
		zBottom.setPreferredSize(DEFAULT_BUTTON_SIZE);
		geometriaPlytyPanel.add(zBottom,border);



		//x2 jednostki
		border.gridx=2;
		border.gridy=0;
		widthUnit = new JLabel("mm");
		geometriaPlytyPanel.add(widthUnit,border);
	
		border.gridy++;
		heightUnit = new JLabel("mm");
		geometriaPlytyPanel.add(heightUnit,border);

		
		//****NADDATKI****//
		naddatkiPanel.setLayout(layout);
		border.gridx=0;
		border.gridy=0;
		
		//x0
		apLabel = new JLabel("ap");
		apLabel.setToolTipText("G³êbokosc skrawania");
		naddatkiPanel.add(apLabel,border);
		
		border.gridy++;
		aeLabel = new JLabel("ae");
		aeLabel.setToolTipText("Szerokosc skrawania jako % srednicy uzytego narzedzia");
		naddatkiPanel.add(aeLabel,border);
		
		
		border.gridy++;
		zUpperLabel = new JLabel("Grubosc materialu do zebrania");
		zUpperLabel.setToolTipText("<html>Okresl grubosc materialu do wybrania w osi Z.<br>np. jeœli plyta przed obrobka ma 23mm grubosci a wymagana jest 20mm wpisz 3mm</html>");
		naddatkiPanel.add(zUpperLabel,border);
		
		//x1
		border.gridx=1;
		border.gridy=0;
		
		apTxtField = new JTextField("1");
		apTxtField.setPreferredSize(DEFAULT_BUTTON_SIZE);
		naddatkiPanel.add(apTxtField,border);
		
		border.gridy++;
		aeTextField = new JTextField("60");
		aeTextField.setPreferredSize(DEFAULT_BUTTON_SIZE);
		aeTextField.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e) {
				// unused method
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// unused method
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				resetToolStartingPointComboBox();
				canvas.update();
				
			}
			
			
		});
		naddatkiPanel.add(aeTextField,border);
		
		border.gridy++;
		zUpper = new JTextField("2");
		zUpper.setPreferredSize(DEFAULT_BUTTON_SIZE);
		naddatkiPanel.add(zUpper,border);
		
		
		//x2
		border.gridx=2;
		border.gridy=0;
		apUnit = new JLabel("mm");
		naddatkiPanel.add(apUnit,border);
	
		border.gridy++;
		aeUnit = new JLabel("%D");
		naddatkiPanel.add(aeUnit,border);
		
		border.gridy++;
		materialThicknessUnit =new JLabel("mm");
		naddatkiPanel.add(materialThicknessUnit,border);
		
		//****NARZEDZIE****//
		narzedziePanel.setLayout(layout);
		border.gridx=0;
		border.gridy=0;
		
		srednicaNarzedziaLabel = new JLabel("Srednica narzedzia");
		srednicaNarzedziaLabel.setToolTipText("Srednica wykorzystywanego narzêdzia w mm");
		narzedziePanel.add(srednicaNarzedziaLabel,border);
		
		border.gridy++;
		numberOfTeethLabel = new JLabel("Ilo¶æ zêbów z=");
		narzedziePanel.add(numberOfTeethLabel,border);
		
		
		border.gridx=1;
		border.gridy=0;
		toolDiameterTextField = new JTextField("80");
		toolDiameterTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// unused 
			}

			@Override
			public void keyPressed(KeyEvent e) {
				startingPointsComboBox.setSelectedItem(null);
				PlaneView.this.repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// unused 	
			}
			
		});
		toolDiameterTextField.setPreferredSize(DEFAULT_BUTTON_SIZE);
		narzedziePanel.add(toolDiameterTextField,border);
		
		border.gridy++;
		numberOfTeeth = new JSpinner(new SpinnerNumberModel(4,1,16,1));
		numberOfTeeth.addChangeListener(n->{
			vc.setText("");
			sprindleSpeed.setText("");
			fn.setText("");
			feedTextField.setText("");
			
		});
		narzedziePanel.add(numberOfTeeth,border);
		
		
		border.gridx=2;
		border.gridy=0;
		toolDiameterUnit = new JLabel("mm");
		narzedziePanel.add(toolDiameterUnit,border);
		
		//****method****//
		methodPanel.setLayout(layout);
		border.gridx=0;
		border.gridy=0;
		methodLabel = new JLabel("Metoda");
		methodLabel.setToolTipText("Okresla sposob frezowania tej p³aszczyzny");
		methodPanel.add(methodLabel,border);
		
		
		border.gridy++;
		smoothenCheckBox = new JCheckBox("Wygladz przejscia",false);
		smoothenCheckBox.setToolTipText("Zaokragla przejscia pod katem 90stopni tak aby glowica przechodzi³a plynnie po okrêgu z zadanym nizej promieniem");
		smoothenCheckBox.addItemListener(n->{
			if(n.getStateChange()==ItemEvent.SELECTED)
			{
				JOptionPane.showMessageDialog(this, "Opcja w trakcie implementacji");
				smoothenCheckBox.setSelected(false);
				//promienLabel.setEnabled(true);
				//radiusField.setEnabled(true);
				
			}
			else 
			{
				//smoothenCheckBox.setSelected(false);
				//promienLabel.setEnabled(false);
				//radiusField.setEnabled(false);
				
			}
		});
		
		
		
		methodPanel.add(smoothenCheckBox,border);
		
		border.gridy++;
		promienLabel = new JLabel("Promien R=");
		promienLabel.setToolTipText("Promien zaokraglenia œciezki - max 50% srednicy narzêdzia");
		promienLabel.setEnabled(false);
		methodPanel.add(promienLabel,border);

		border.gridy++;
		finishingPassCheckBox = new JCheckBox("Przej¶cie wykañczaj±ce",false);
		finishingPassCheckBox.setToolTipText("Ustal gleboko¶æ skrawania na ostanim przejsciu");
		finishingPassCheckBox.addItemListener(n->{
			if(n.getStateChange()==ItemEvent.SELECTED)
			{
				finishingPassTextField.setEnabled(true);
				finishingPassLabel.setEnabled(true);
			}
			else 
			{
				finishingPassTextField.setEnabled(false);
				finishingPassLabel.setEnabled(false);
			}
		});
		methodPanel.add(finishingPassCheckBox,border);
		
		border.gridy++;
		finishingPassLabel = new JLabel("Glêbokoœæ d=");
		finishingPassLabel.setEnabled(false);
		finishingPassLabel.setToolTipText("Ustal g³ebokosc skrawania na ostanim przejsciu");
		methodPanel.add(finishingPassLabel,border);
		
		//x1
		border.gridx=1;
		border.gridy=0;
		method=new JComboBox<>(METHOD);
		method.setPreferredSize(DEFAULT_BUTTON_SIZE);
		methodPanel.add(method,border);
		
		
		border.gridy++;
		
		border.gridy++;
		radiusField = new JTextField("5");
		radiusField.setPreferredSize(DEFAULT_BUTTON_SIZE);
		radiusField.setEnabled(false);
		methodPanel.add(radiusField,border);
		
		border.gridy++;
		border.gridy++;
		finishingPassTextField = new JTextField();
		finishingPassTextField.setEnabled(false);
		finishingPassTextField.setPreferredSize(DEFAULT_BUTTON_SIZE);
		methodPanel.add(finishingPassTextField,border);
		
		//x2
		border.gridx=2;
		border.gridy=2;
		radiusUnit = new JLabel("mm");
		methodPanel.add(radiusUnit,border);
		
		border.gridy++;
		border.gridy++;
		border.gridy++;
		finishingPassunit = new JLabel("mm");
		methodPanel.add(finishingPassunit,border);
		
		
		//****PARAMETRY****//
		parametryPanel.setLayout(layout);
		border.gridx=0;
		border.gridy=0;
		
		//x0
		sprindleSpeedLabel = new JLabel("Obroty");
		sprindleSpeedLabel.setToolTipText("Predkosc obrotowa wrzeciona 1/min");
		parametryPanel.add(sprindleSpeedLabel,border);

		border.gridy++;
		feedLabel = new JLabel("Posuw");
		feedLabel.setToolTipText("Predkosc posuwu Vf w mm/min");
		parametryPanel.add(feedLabel,border);

		border.gridy++;
		border.gridy++;
		vcLabel = new JLabel("Predkosc skrawania Vc");
		vcLabel.setEnabled(false);
		parametryPanel.add(vcLabel,border);
		
		border.gridy++;
		fnLabel = new JLabel("Posuw na z±b fz");
		fnLabel.setEnabled(false);
		parametryPanel.add(fnLabel,border);
		
		//x1
		border.gridx=1;
		border.gridy=0;
		sprindleSpeed = new JTextField("2000");
		sprindleSpeed.setPreferredSize(DEFAULT_BUTTON_SIZE);
		parametryPanel.add(sprindleSpeed,border);
		
		border.gridy++;
		feedTextField = new JTextField("500");
		feedTextField.setPreferredSize(DEFAULT_BUTTON_SIZE);
		parametryPanel.add(feedTextField,border);
		
		border.gridy++;
		Image icon =  new ImageIcon(this.getClass().getResource("/up-and-down-arrows.png")).getImage();
		reverse = new JButton();
		reverse.setPreferredSize(DEFAULT_BUTTON_SIZE);
		reverse.setIcon(new ImageIcon(icon.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		parametryPanel.add(reverse,border);
		
		border.gridy++;
		vc = new JTextField();
		vc.setPreferredSize(DEFAULT_BUTTON_SIZE);
		vc.setEditable(false);
		parametryPanel.add(vc,border);
		
		border.gridy++;
		fn = new JTextField();
		fn.setPreferredSize(DEFAULT_BUTTON_SIZE);
		fn.setEditable(false);
		parametryPanel.add(fn,border);
		
		//x2
		border.gridx=2;
		border.gridy=0;
		sprindleSpeedUnit = new JLabel("1/min");
		parametryPanel.add(sprindleSpeedUnit,border);
		
		border.gridy++;
		feedUnit = new JLabel("mm/min");
		parametryPanel.add(feedUnit,border);
		
		border.gridy=3;
		vcUnit = new JLabel("m/min");
		vcUnit.setEnabled(false);
		parametryPanel.add(vcUnit,border);
		
		border.gridy=4;
		fnUnit = new JLabel("mm");
		fnUnit.setEnabled(false);
		parametryPanel.add(fnUnit,border);
	
		
		pane.add("Geometria",geometriaPanel);

		pane.addTab("Narzedzie",narzedziePanel);
		pane.addTab("Parametry skrawania", parametryPanel);
		pane.addTab("Metoda", methodPanel);
		pane.addTab("Przeszkody", przeszkodyPanel);
		pane.addTab("Naddatki", naddatkiPanel);
		pane.addTab("Punkt startu", punktyStartuPanel);
		
		
		
		leftPanel.add(pane,BorderLayout.NORTH);
		
		
		//buttons
		executeButton = new JButton("Oblicz");
		executeButton.setPreferredSize(DEFAULT_BUTTON_SIZE);
		przyciskiPanel.add(executeButton);
		
		generateButton = new JButton("Generuj");
		generateButton.setPreferredSize(DEFAULT_BUTTON_SIZE);
		generateButton.setEnabled(isToolPathGenerated);
		przyciskiPanel.add(generateButton);
		
		
		cancelButton = new JButton("Cofnij");
		cancelButton.setPreferredSize(DEFAULT_BUTTON_SIZE);
		przyciskiPanel.add(cancelButton);
		cancelButton.addActionListener(n->
		{
			this.dispose();
		
		});
		
		leftPanel.add(przyciskiPanel,BorderLayout.SOUTH);

		add(leftPanel);
		
		canvas = new Visual();
		canvas.setBorder(BorderFactory.createEtchedBorder());
		add(canvas);
		
		pack();
	}
	
	public int getNumberOfDirectionsLocked()
	{
		return this.directionsLocked;
	}
	
	
	public void reverse()
	{
		vcLabel.setEnabled(parameters);
		vc.setEditable(parameters);
		vcUnit.setEnabled(parameters);
		fnLabel.setEnabled(parameters);
		fn.setEditable(parameters);
		fnUnit.setEnabled(parameters);
		
		sprindleSpeedLabel.setEnabled(!parameters);
		sprindleSpeed.setEditable(!parameters);
		sprindleSpeedUnit.setEnabled(!parameters);
		feedLabel.setEnabled(!parameters);
		feedTextField.setEditable(!parameters);
		feedUnit.setEnabled(!parameters);
		
		this.parameters= !parameters;
	}
	
	 void setStartingpoint(Point toolStartPoint)
	 {
		 this.toolStartPoint=toolStartPoint;
	 }
	
	 void setToolPath(List<Point> toolPath)
	 {
		 this.toolpath=toolPath;
	 }
	
	
	
	
	class Visual extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		RectangleWithDimensions rectangle;
		JButton showCordinatesButton;
		JButton showDimensionsButton;
		private boolean showCordinate = true;
		private boolean showDimensions = true;
		private boolean showToolPath=false;
	
		
		

		Visual()
		{
			setVisible(true);
			setLayout(null);
			setPreferredSize(CANVAS_SIZE);
			rectangle = new RectangleWithDimensions();
			
			//show cordinates button
			showCordinatesButton = new JButton();
			Image icon= new ImageIcon(this.getClass().getResource("/cs.png")).getImage();
			showCordinatesButton.setIcon(new ImageIcon(icon.getScaledInstance(22, 24, Image.SCALE_SMOOTH)));
			showCordinatesButton.setBounds(2, 2, 22, 24);
			showCordinatesButton.addActionListener(n->
			{
				Visual.this.showCordinate = !showCordinate;
				PlaneView.this.repaint();
			});
			showCordinatesButton.setToolTipText("Pokaz punkt zerowy uk³adu wspolrzednych");
			add(showCordinatesButton);
			
			//show dimenstions button
			showDimensionsButton = new JButton();
			Image icon2= new ImageIcon(this.getClass().getResource("/ruler.png")).getImage();
			showDimensionsButton.setIcon(new ImageIcon(icon2.getScaledInstance(22, 24, Image.SCALE_SMOOTH)));
			showDimensionsButton.setBounds(26, 2, 22, 24);
			showDimensionsButton.addActionListener(n->
			{
				Visual.this.showDimensions=!showDimensions;
				Visual.this.rectangle.showDimensions(showDimensions);
				PlaneView.this.repaint();
			});
			showDimensionsButton.setToolTipText("Pokaz wymiary");
			add(showDimensionsButton);
		}
		
		
		void update()
		{
			String w = PlaneView.this.partHeight.getText();
			String s = PlaneView.this.partWidth.getText();
			
			if(rectangle.validate(w,s,"300","250")) 
			{
				resetToolStartingPointComboBox();
				PlaneView.this.repaint();
			}
		}

		private void paintObstacle(Graphics g, float distanceFromObstacle, float d, float h)
		{
			if(PlaneView.this.directionsLocked!=0 )
			{
				g.setColor(Color.gray);
				int xCenter = PlaneView.this.canvas.getWidth()/2;
				int yCenter = PlaneView.this.canvas.getHeight()/2;
				int blockHeight = (int) h;
				int blockWidth = (int) d;
				
				if(PlaneView.this.upperObstacleButton.isSelected())
				{
					g.fillRect(xCenter -blockWidth/2 -25, yCenter -blockHeight/2 -20- (int)distanceFromObstacle, blockWidth + 50, 20);
				}
				if(PlaneView.this.bottomObstacleButton.isSelected()) 
				{
					g.fillRect(xCenter - blockWidth/2 -25 , yCenter + blockHeight/2+(int)distanceFromObstacle, blockWidth + 50, 20);
				}
				if(PlaneView.this.obstacleFromLeftSideButton.isSelected())
				{
					g.fillRect(xCenter - blockWidth/2 -20 -(int)distanceFromObstacle , yCenter - blockHeight/2-45, 20, blockHeight + 90);
				}
				if(PlaneView.this.obstacleFromRightSideButton.isSelected())
				{
					g.fillRect(xCenter + blockWidth/2  +(int)distanceFromObstacle, yCenter - blockHeight/2-45,20, blockHeight + 90);
				}
			}
		}
		
		private void drawCordinateSystem(Graphics g, float baseXPosition, float baseYPosition)
		{
			if(Visual.this.showCordinate)
			{	
				new DrawCordinateSystem(new Point((int)baseXPosition+300f,-(int)baseYPosition+250f,TYPE.XY_POINT),Color.red).draw(g);	
			}
		}
		
		private void paintTool(Graphics g,Point toolStartPoint,float toolDiameter)
		{
			if(PlaneView.this.startingPointsComboBox.getSelectedIndex()!=-1)
			{
				Point toolPoint = toolStartPoint.clone();

				new DrawTool(toolPoint, (int)toolDiameter).drawInCenter(g, CANVAS_SIZE);
			}
		}

		
		public void toolPathWithoutRadius(Graphics g,List<Point>toolpath,Point toolStartPoint)
		{
			ToolPath t = new ToolPath(CANVAS_SIZE,toolStartPoint);
			t.setLocalCs(new Point(0f,0f,TYPE.XY_POINT));
			
			//showToolPath
			if(showToolPath)
			{
				for(Point p : toolpath)
				{
					t.move(p, 1);
					t.drawInCenter(g,CANVAS_SIZE);
				}
			}
			
			showToolPath=false;
		}
		
		private void toolPathWithRadius(Graphics g, Point toolStartPoint, float radius, List<Point>toolpath)
		{
			
			ToolPath t = new ToolPath(CANVAS_SIZE,toolStartPoint);
			t.setLocalCs(new Point(PlaneView.this.baseXPosition,PlaneView.this.baseYPosition,TYPE.XY_POINT));
			
			
			Point p;
			for(int i=0; i<toolpath.size();i++) 
			{
				p=toolpath.get(i);
				if(i%2==0 && i!=0) 
					{
						t.move(p,3,radius);
					}
				else t.move(p, 1);
				
				t.drawInCenter(g,CANVAS_SIZE);
			}
			
			showToolPath=false;
		}
		
		
		@Override
		protected void paintComponent(Graphics g) 
		{	
			 paintObstacle(g,PlaneView.this.getDistanceFromObstacle().orElse(5f),PlaneView.this.getd().orElse(0f),PlaneView.this.geth().orElse(0f));
				
			rectangle.draw(g);
				
			drawCordinateSystem(g,PlaneView.this.getX0().orElse(0f),PlaneView.this.getY0().orElse(0f));
				
			paintTool(g,toolStartPoint,PlaneView.this.getToolDiameter().orElse(20f));
			
			if(showToolPath)
			{
				if(smoothenCheckBox.isSelected() && showToolPath)
				{
					toolPathWithRadius(g, toolStartPoint, PlaneView.this.getRadius().orElse(0f), toolpath);
				}
				else
				{
					toolPathWithoutRadius(g, toolpath, toolStartPoint);
				}
			}
		}
		
		
		public void showToolPath(boolean value)
		{
			this.showToolPath=value;
		}
	}
		
		
	//add action listener to control this combo box from PlaneControler
	void addpaintToolListener(ActionListener al)
	{
		startingPointsComboBox.addActionListener(al);
	}
	
	private boolean validateNumberOfDirectionsLocked()
	{
		if(this.directionsLocked>=3)
		{
			JOptionPane.showMessageDialog(this, "Zablokowano zbyt duzo kierunkow", "B³±d", JOptionPane.NO_OPTION);
			return false;
		}
		else return true;
	}
	
	
	//add action listener to execute button from PlaneControler
	 void addExecuteButtonListeners(ActionListener actionListener)
	{
		executeButton.addActionListener(actionListener);
		reverse.addActionListener(actionListener);
	}
	 
	void addGenerateButtonLister(ActionListener actionListener)
	{
		generateButton.addActionListener(actionListener);
	}
	 
	void addPlaneViewKeyListeners(KeyListener keyListener)
	 {
		 this.feedTextField.addKeyListener(keyListener);
		 this.sprindleSpeed.addKeyListener(keyListener);
		 this.vc.addKeyListener(keyListener);
		 this.fn.addKeyListener(keyListener);
	 }
	 
	 //getters
	 
	 public boolean isLeftSideLocked()
	 {
		return obstacleFromLeftSideButton.isSelected(); 
	 }
	 
	 public boolean isToolPathGenerated()
	 {
		 return this.isToolPathGenerated;
	 }
	 
	 public boolean isRightSideLocked()
	 {
		return obstacleFromRightSideButton.isSelected(); 
	 }
	 public boolean isUpperSideLocked()
	 {
		return upperObstacleButton.isSelected(); 
	 }
	 public boolean isBottomSideLocked()
	 {
		return bottomObstacleButton.isSelected(); 
	 }
	 

	 public boolean isFinishingPass()
	 {
		 return this.finishingPassCheckBox.isSelected();
	 }
	 
	 public final JTextField getSprindleSpeedTextField()
	 {
		 return this.sprindleSpeed;
	 }
	 
	 public final JTextField getFeedPerRevTextField()
	 {
		 return this.fn;
	 }
	 
	 public final JTextField getVcTextField()
	 {
		 return this.vc;
	 }
	 public final JTextField getFeedTextField()
	 {
		 return this.feedTextField;
	 }
	 
	 public final JButton getReverseButton()
	 {
		 return this.reverse;
	 }
	 public final JButton getExecuteButton()
	 {
		 return this.executeButton;
	 }
	 

		//functions to get variables from txt fields
		
			public  Optional<Float> getX0()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.x0Text.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
	
				}
				return result;
			}

			
			public Optional<Float> getY0()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.y0Text.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();

				}
				return result;
			}
			

			public boolean pathWithRadius()
			{
				return this.smoothenCheckBox.isSelected();
			}
			
			
			public Optional<Integer> getVc()
			{
				Optional<Integer> result;
				try {
					result=Optional.of(Integer.parseInt(this.vc.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
				}
				return result;
			}
		
			
			
			public Optional<Float> getRadius()
			{
				Optional<Float> result;
				try
				{
					result = Optional.of(Float.parseFloat(this.radiusField.getText()));
				}
				catch(NumberFormatException e)
				{

					result=Optional.empty();
				}
				
				
				return result;
			}
			
			
			public Optional<Float> getDephZ()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.zUpper.getText()));
				}
				catch(NumberFormatException e)
				{
					
					result = Optional.empty();
				}
				return result;
			}

			public Optional<Float> getDistanceFromObstacle()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Math.abs(Float.parseFloat(this.distanceField.getText())));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
				}
				return result;
			}
			public Optional<Float> getAp()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.apTxtField.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();	
				}
				return result;
			}
			
			public Optional<Integer> getS()
			{
				Optional<Integer> result;
				try {
					result=Optional.of(Integer.parseInt(this.sprindleSpeed.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
	
				}
				return result;
			}
			public Optional<Integer> getFeed()
			{
				Optional<Integer> result;
				try {
					result=Optional.of(Integer.parseInt(this.feedTextField.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
				}
				return result;
			}
			
			public Optional<Integer> getNumberOfCutingEdges()
			{
				return Optional.ofNullable((Integer)this.numberOfTeeth.getValue());
			}
			
			public Optional<Float> getToolDiameter()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.toolDiameterTextField.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
				}
				return result;
			}
			
			public Optional<Float> getz()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.zBottom.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
					
				}
				return result;
			}
			
			public Optional<Float> getzf()
			{
				Optional<Float> result;
				if(!this.finishingPassCheckBox.isSelected()) 
					{
						return Optional.of(0.f);	
					}
				
				try {
					result=Optional.of(Float.parseFloat(this.finishingPassTextField.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
					
				}
				return result;
			}
			
			public Optional<Float> geth()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.partHeight.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
					
				}
				return result;
			}
			
			public Optional<Float> getfz()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.fn.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
					
				}
				return result;
			}
			
			public Optional<Float> getd()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.partWidth.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
					
				}
				return result;
			}
			
			public Optional<Float> getAe()
			{
				Optional<Float> result;
				try {
					result=Optional.of(Float.parseFloat(this.aeTextField.getText()));
				}
				catch(NumberFormatException e)
				{
					result=Optional.empty();
				}
				return result;
			}
	public START_DIRECTIONS getDirectionChoosed()
	{
		switch(this.startingPointsComboBox.getSelectedIndex())
		{
		case 0:
			return START_DIRECTIONS.LEFT;
		case 1:
			return START_DIRECTIONS.RIGHT;
		case 2:
			return START_DIRECTIONS.UP;
		case 3:
			 return START_DIRECTIONS.DOWN;
		default : return START_DIRECTIONS.NONE;
		
		}
	}
			
			
	//setters
			
	void resetToolStartingPointComboBox()
	{
		startingPointsComboBox.setSelectedItem(null);
		setToolPathGeneratedFlag(false);
	}
	void setToolPathGeneratedFlag(boolean isToolPathGenerated)
	{
		this.isToolPathGenerated=isToolPathGenerated;
		this.generateButton.setEnabled(isToolPathGenerated);
	}
	
	void setAeTextField(String value)
	{
		this.aeTextField.setText(value);
	}
	
	void setVcTextField(String value)
	{
		this.vc.setText(value);
	}
	void setSprindleSpeedTextField(String value)
	{
		this.sprindleSpeed.setText(value);
	}
			
	void setFeedTextField(String value)
	{
		this.feedTextField.setText(value);
	}
	
	void setfzTextField(String value)
	{
		this.fn.setText(value);
	}
		
}
