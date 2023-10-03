package controls;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import CordConverter.Edytor;

public class ControlsGui extends JFrame {
	
	
	private Edytor parent;
	private JButton edit;
	private JButton create;
	private JButton remove;
	private JButton showDictionary;
	private JList<ControlsModel> controlsList;
	
private static final Dimension BUTTON_SIZE = new Dimension(150,75);
	
	
	public ControlsGui(Edytor parent, List<ControlsModel> listOfControls)
	{
		setSize(450,600);
		setTitle("Sterowania");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	
		this.parent = parent;
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints border = new GridBagConstraints();
		Insets insets = new Insets(5,5,5,5);
		
		border.gridx=0;
		border.gridy=0;
		edit = new JButton("Edutyj");
		edit.setPreferredSize(BUTTON_SIZE);
		add(edit,border);
		
		border.gridy++;
		create = new JButton("Nowe");
		create.setPreferredSize(BUTTON_SIZE);
		add(create,border);
		
		border.gridy++;
		remove = new JButton("Usun");
		remove.setPreferredSize(BUTTON_SIZE);
		add(remove,border);
		
		border.gridy++;
		showDictionary = new JButton("Pokaz liste kodow");
		showDictionary.setPreferredSize(BUTTON_SIZE);
		add(showDictionary,border);
		
		//column 2
		border.gridx=0;
		border.gridy=0;
		DefaultListModel<ControlsModel> model = new DefaultListModel<>();
		this.controlsList = new JList<>();
		controlsList.setModel(model);
		model.addAll(listOfControls);
		JScrollPane scroll = new JScrollPane(controlsList);
		add(scroll,border);
		
		
	}
}
