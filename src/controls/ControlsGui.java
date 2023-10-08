package controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import CordConverter.Edytor;

public class ControlsGui extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3332947222679672612L;
	private Edytor parent;
	private JButton edit;
	private JButton create;
	private JButton remove;
	private JButton showDictionary;
	private JList<ControlsModel> controlsList;
	
private static final Dimension BUTTON_SIZE = new Dimension(150,25);
	
	
	public ControlsGui(Edytor parent, List<ControlsModel> listOfControls)
	{
		setSize(400,600);
		setTitle("Sterowania");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		
		//exit on close
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{	
				ControlsGui.this.dispose();
			}		
		});
	
		this.parent = parent;
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints border = new GridBagConstraints();
		 border.insets = new Insets(5,5,5,5);
		
		border.gridx=0;
		border.gridy=0;
		border.weighty=0;
		border.fill=GridBagConstraints.VERTICAL;
		edit = new JButton("Edytuj");
		edit.setPreferredSize(BUTTON_SIZE);
		add(edit,border);
		
		border.gridy++;
		create = new JButton("Nowe");
		create.setPreferredSize(BUTTON_SIZE);
		create.addActionListener(e-> new addNewControlGui(this));
		add(create,border);
		
		border.gridy++;
		remove = new JButton("Usun");
		remove.setPreferredSize(BUTTON_SIZE);
		add(remove,border);
		
		border.gridy++;
		showDictionary = new JButton("Pokaz liste kodow");
		showDictionary.setPreferredSize(BUTTON_SIZE);
		showDictionary.addActionListener(e-> {
			if(controlsList.getSelectedValue()!=null)
			{
				new EditCodesGui(this);
			}
				else  JOptionPane.showMessageDialog(this,"Nie zaznaczono zadnego elementu","Blad",JOptionPane.NO_OPTION);
			});
		add(showDictionary,border);
		
		//column 2
		border.gridx=1;
		border.gridy=0;
		border.gridheight=5;
		DefaultListModel<ControlsModel> model = new DefaultListModel<>();
		this.controlsList = new JList<>();
		controlsList.setModel(model);
		model.addAll(listOfControls);
		JScrollPane scroll = new JScrollPane(controlsList);
		scroll.setPreferredSize(new Dimension(200,400));
		scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Sterowania lista"));
		
		add(scroll,border);
		
		
		pack();
	}
	
	
	class EditCodesGui extends JFrame
	{
		Map<String,String> codeMap;
		ControlsGui parent;
		
		public EditCodesGui(ControlsGui parent)
		{
			this.parent=parent;
			setVisible(true);
			setResizable(false);
			setSize(500,500);
			setLocationRelativeTo(parent);
			
			//exit on close
			this.addWindowListener(new java.awt.event.WindowAdapter() 
			{
				@Override
				public void windowClosing(WindowEvent e)
				{	
					EditCodesGui.this.dispose();
				}		
			});
			
			codeMap=parent.controlsList.getSelectedValue().getCodeMap();
			
			DefaultTableModel tableModel = new DefaultTableModel();
			tableModel.addColumn("Kod", codeMap.keySet().toArray());
			tableModel.addColumn("Opis dzia³ania", codeMap.values().toArray() );
			JTable codeTable = new JTable(tableModel);
			
			add(new JScrollPane(codeTable));
			
			pack();
			
		}
	}
	
	
	class addNewControlGui extends JFrame
	{
		ControlsGui parent;
		
		
		private JLabel name;
		private JTextField nameField;
		private JLabel toolLengthCompensationFormat;
		private JTextField toolLengthCompensationFormatField;
		private JLabel dwellFormat;
		private JTextField dwellFormatField;
		private JLabel retractionFormatLowerLimit;
		private JTextField retractionFormatLowerLimitField;
		private JLabel retractionFormatUpperLimit;
		private JTextField retractionFormatUpperLimitField;
		private JLabel drillingBottomFormat;
		private JTextField drillingBottomFormatField;
		private JLabel boringretractionFormat;
		private JTextField boringretractionFormatField;
		private JLabel drillingDwellFormat;
		private JTextField drillingDwellFormatField;
		private JLabel longHolesCycleAdvanceFormat;
		private JTextField longHolesCycleAdvanceFormatField;
		private JLabel sprindleCollantCode;
		private JTextField sprindleCollantCodeField;
		private JLabel defaultCollantCode;
		private JTextField defaultCollantCodeField;
		private JLabel baseDefinitionCode;
		private JTextField baseDefinitionCodeField;
		private JLabel tappingAdditionalParametrs;
		private JTextField tappingAdditionalParametrsField;
		
		private JLabel cyclesParametersLabel;
		
		
		 addNewControlGui(ControlsGui parent)
		{
			 this.parent=parent;
				setVisible(true);
				setResizable(false);
				setSize(300,500);
				setLocationRelativeTo(parent);
				setTitle("Definicja nowego sterowania");
			
				//exit on close
				this.addWindowListener(new java.awt.event.WindowAdapter() 
				{
					@Override
					public void windowClosing(WindowEvent e)
					{	
						addNewControlGui.this.dispose();
					}		
				});
				
				GridBagLayout layout = new GridBagLayout();
				setLayout(layout);
				GridBagConstraints border = new GridBagConstraints();
				border.insets = new Insets(5,5,5,5);
			
				border.gridx=0;
				border.gridy=0;
				name = new JLabel("Nazwa");
				add(name,border);
				
				
				border.gridx=1;
				border.gridy=0;
				nameField = new JTextField();
				nameField.setPreferredSize(BUTTON_SIZE);
				add(nameField,border);
				
				border.gridx=0;
				border.gridy=1;
				baseDefinitionCode = new JLabel("Definicja bazy");
				add(baseDefinitionCode,border);
				
				border.gridx=1;
				border.gridy=1;
				baseDefinitionCodeField= new JTextField();
				baseDefinitionCodeField.setPreferredSize(BUTTON_SIZE);
				add(baseDefinitionCodeField,border);
				
				border.gridx=0;
				border.gridy=2;
				toolLengthCompensationFormat = new JLabel("Korekcji dlugosci narzedzia");
				add(toolLengthCompensationFormat,border);
				
				border.gridx=1;
				border.gridy=2;
				toolLengthCompensationFormatField= new JTextField();
				toolLengthCompensationFormatField.setPreferredSize(BUTTON_SIZE);
				add(toolLengthCompensationFormatField,border);
		
				border.gridx=0;
				border.gridy=3;
				dwellFormat = new JLabel("Postoj czasowy");
				add(dwellFormat,border);
				
				border.gridx=1;
				border.gridy=3;
				dwellFormatField= new JTextField();
				dwellFormatField.setPreferredSize(BUTTON_SIZE);
				add(dwellFormatField,border);
				
				border.gridx=0;
				border.gridy=4;
				sprindleCollantCode = new JLabel("Chlodzenie przez wrzeciono");
				add(sprindleCollantCode,border);
				
				border.gridx=1;
				border.gridy=4;
				sprindleCollantCodeField= new JTextField();
				sprindleCollantCodeField.setPreferredSize(BUTTON_SIZE);
				add(sprindleCollantCodeField,border);

				border.gridx=0;
				border.gridy=5;
				defaultCollantCode = new JLabel("Chlodzenie przez dysze");
				add(defaultCollantCode,border);
				
				border.gridx=1;
				border.gridy=5;
				defaultCollantCodeField= new JTextField();
				defaultCollantCodeField.setPreferredSize(BUTTON_SIZE);
				add(defaultCollantCodeField,border);
				
				
				border.gridx=0;
				border.gridy=6;
				cyclesParametersLabel = new JLabel("Parametry cykli");
				add(cyclesParametersLabel,border);
				
				//fixed cycles
				
				border.gridx=0;
				border.gridy=7;
				retractionFormatLowerLimit = new JLabel("Retrakcja - dolny limit");
				add(retractionFormatLowerLimit,border);
				
				border.gridx=1;
				border.gridy=7;
				retractionFormatLowerLimitField= new JTextField();
				retractionFormatLowerLimitField.setPreferredSize(BUTTON_SIZE);
				add(retractionFormatLowerLimitField,border);
				
			
				border.gridx=0;
				border.gridy=8;
				retractionFormatUpperLimit = new JLabel("Retrakcja - górny limit");
				add(retractionFormatUpperLimit,border);
				
				border.gridx=1;
				border.gridy=8;
				retractionFormatUpperLimitField= new JTextField();
				retractionFormatUpperLimitField.setPreferredSize(BUTTON_SIZE);
				add(retractionFormatUpperLimitField,border);
				
				border.gridx=0;
				border.gridy=9;
				drillingBottomFormat = new JLabel("Cykl staly - dno cyklu");
				add(drillingBottomFormat,border);
				
				border.gridx=1;
				border.gridy=9;
				drillingBottomFormatField= new JTextField();
				drillingBottomFormatField.setPreferredSize(BUTTON_SIZE);
				add(drillingBottomFormatField,border);
				
				border.gridx=0;
				border.gridy=10;
				drillingDwellFormat = new JLabel("Cykl G82 - format postoju");
				add(drillingDwellFormat,border);
				
				border.gridx=1;
				border.gridy=10;
				drillingDwellFormatField= new JTextField();
				drillingDwellFormatField.setPreferredSize(BUTTON_SIZE);
				add(drillingDwellFormatField,border);
				
				border.gridx=0;
				border.gridy=11;
				longHolesCycleAdvanceFormat = new JLabel("Cykl G83 - format zaglebiania");
				add(longHolesCycleAdvanceFormat,border);
				
				border.gridx=1;
				border.gridy=11;
				longHolesCycleAdvanceFormatField= new JTextField();
				longHolesCycleAdvanceFormatField.setPreferredSize(BUTTON_SIZE);
				add(longHolesCycleAdvanceFormatField,border);
				
				border.gridx=0;
				border.gridy=11;
				tappingAdditionalParametrs = new JLabel("Cykl G84 - format zaglebiania");
				add(tappingAdditionalParametrs,border);
				
				border.gridx=1;
				border.gridy=11;
				tappingAdditionalParametrsField= new JTextField();
				tappingAdditionalParametrsField.setPreferredSize(BUTTON_SIZE);
				add(tappingAdditionalParametrsField,border);
				
				border.gridx=0;
				border.gridy=12;
				boringretractionFormat = new JLabel("Cykl G76 - format odbicia");
				add(boringretractionFormat,border);
				
				border.gridx=1;
				border.gridy=12;
				boringretractionFormatField= new JTextField();
				boringretractionFormatField.setPreferredSize(BUTTON_SIZE);
				add(boringretractionFormatField,border);
		
		
	
				
				
				pack();
		}
		
	}
	
	
}
