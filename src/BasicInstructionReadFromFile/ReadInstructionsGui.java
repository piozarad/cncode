package BasicInstructionReadFromFile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import CordConverter.Wind;


//acts as View in MVC
public class ReadInstructionsGui extends JDialog {

	private final Wind parent;
	private JLabel zastosujInstrukcjeLabel;
	private JTextArea komentarzDoInstrukcji;

	private JButton wykonajButton;
	private JButton cofnijButton;
	
	private JComboBox<Postprocesor> instrukcje;
	
	private static final Dimension BUTTON_SIZE = new Dimension(100,25);
	
	public ReadInstructionsGui(Wind parent)
	{
		this.parent=parent;
		setVisible(true);
		setPreferredSize(new Dimension(450,250));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout(2,2));
		setTitle("Konwersja sterowania");
	
		
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				ReadInstructionsGui.this.cofnijButton.doClick();
			}}
				);
		
		GridBagLayout layout = new GridBagLayout();
		JPanel buttonPanel = new JPanel();
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(10,5,10,5);
		buttonPanel.setMinimumSize(new Dimension(200, 75));
		buttonPanel.setLayout(layout);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Opcje"));
		
		//x0 y0
		border.gridx=0; 
		border.gridy=0;
		border.weightx=0;
		border.weighty=0;
		border.anchor=GridBagConstraints.NORTH;
		border.fill= GridBagConstraints.NONE;
		zastosujInstrukcjeLabel = new JLabel("Postprocesory: ");
		buttonPanel.add(zastosujInstrukcjeLabel,border);
		
		//x1 y0
		border.gridx=1;
		instrukcje = new JComboBox<>();
		instrukcje.setPreferredSize(new Dimension(125,25));
		buttonPanel.add(instrukcje,border);
		
		//x0 y1
		border.gridx=0;
		border.gridy=1;
		border.gridheight=1;
		border.gridwidth=1;
		border.weightx=0.;
		border.weighty=0.;
		border.fill= GridBagConstraints.NONE;
		wykonajButton = new JButton("Wykonaj");
		wykonajButton.setPreferredSize(BUTTON_SIZE);
		buttonPanel.add(wykonajButton,border);
		
		//x1 y1
		border.gridx=1;
		cofnijButton = new JButton("Cofnij");
		cofnijButton.setPreferredSize(BUTTON_SIZE);
		cofnijButton.addActionListener(e->ReadInstructionsGui.this.setVisible(false));
		buttonPanel.add(cofnijButton,border);
	
		
		add(buttonPanel,BorderLayout.WEST);
		

		JPanel textArea = new JPanel(new BorderLayout());
		textArea.setBorder(BorderFactory.createTitledBorder("Instrukcje"));
		
		komentarzDoInstrukcji = new JTextArea(" ");
		JScrollPane scroll = new JScrollPane(komentarzDoInstrukcji);
		komentarzDoInstrukcji.setEditable(false);
		scroll.setMinimumSize(new Dimension(200,300));
		textArea.add(scroll,BorderLayout.CENTER);
				
		add(textArea);
		pack();

	}
	
	void updateComboBox(List<Postprocesor> list)
	{
		for(Postprocesor ins : list)
		{
			instrukcje.addItem(ins);
		}
		
	}
	void addInstructionListListener(ActionListener listenForComboChanges)
	{
		instrukcje.addActionListener(listenForComboChanges);
	}
	
	void addExecuteButtonListener(ActionListener executeButtonListener)
	{
		wykonajButton.addActionListener(executeButtonListener);
	}
	
	 void updateInstructionList(String text)
	{
		this.komentarzDoInstrukcji.setText(text);
	}
	
	 Postprocesor getComboBoxSelectedItem()
	 {
		 return (Postprocesor) this.instrukcje.getSelectedItem();
	 }
	
}
