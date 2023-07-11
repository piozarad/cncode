package toolNumerator;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SpringLayout.Constraints;

import CordConverter.Edytor;

public class ToolNumeratorView extends JFrame {
	
	private Edytor parent;
	private JList<Integer> toolNumbersList;
	private JTextField txt;
	private JButton add;
	private JButton remove;
	private JLabel info;
	private JButton aply;
	private JButton cancel;
	
	private GridBagLayout layout;
	
	
	public ToolNumeratorView(Edytor parent)
	{
		this.parent=parent;
		
		//layout
		layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints(); 
		constraints.insets = new Insets(5,5,5,5);
		
		
		
		
		//Frame
		setVisible(true);
		setPreferredSize(new Dimension(400,400));
		setTitle("Definicja numerow narzedzi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation(200,200);
		setLayout(layout);
		
		
		constraints.weightx=0;
		constraints.weighty=0.;
		constraints.fill=GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth = 4;
		info = new JLabel("<html>Podaj wolne numery narzêdzi w takiej kolejnosci <br> w jakiej chcesz zeby wystepowaly w programie nastepnie wcisnij ok </html>");
		add(info,constraints);
		
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		txt = new JTextField();
		txt.setMinimumSize(new Dimension(25,25));
		add(txt,constraints);
		
		
		constraints.gridx=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		add= new JButton(">>");
		add.setMinimumSize(new Dimension(50,25));
		add(add,constraints);
		
		constraints.gridy=2;
		remove = new JButton("<<");
		remove.setMinimumSize(new Dimension(50,25));
		add(remove,constraints);
		
		constraints.gridx=2;
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=3;
		toolNumbersList = new JList<>();
		toolNumbersList.setMinimumSize(new Dimension(50,200));
		
		add(toolNumbersList,constraints);
		
		
		pack();
	}
	
	
	
}
