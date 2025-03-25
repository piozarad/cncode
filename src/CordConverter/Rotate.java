package CordConverter;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Mathematic.TMatrix;




public class Rotate extends JFrame  {

	private static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(80,20);
	
	private Edytor parent;
	
	//labels
	private JLabel axisLabel;
	private JLabel rotationValueLabel;

	//combo
	private JComboBox<String> axisCombo;
	private final String axisOptions[] = {"X", "Y", "Z"};
		
	//txtFields
	private JTextField axisRotationValue;
		
	//buttons
	private JButton ok;
	private JButton cancel;
		
	public Rotate(Edytor parent)
	{
		this.parent=parent;
		this.setTitle("Obrot ukladu");
		this.setPreferredSize(new Dimension(300,200));
		setVisible(true);
		setResizable(false);
		setLocation(300, 200);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5,5,5,5);
			
		setLayout(layout);
		
		//exit on close
				this.addWindowListener(new java.awt.event.WindowAdapter() 
				{
					@Override
					public void windowClosing(WindowEvent e)
					{
						cancel.doClick();
					}		
				});
			
		cons.gridx = 0;
		cons.gridy = 0; 
		axisLabel = new JLabel("Os");
		add(axisLabel, cons);
			
		cons.gridx++;
		axisCombo = new JComboBox<>(axisOptions);
		axisCombo.setPreferredSize(DEFAULT_BUTTON_SIZE);
		add(axisCombo, cons);
			

		cons.gridx = 0;
		cons.gridy++;
		rotationValueLabel = new JLabel("Wartosc przesuniecia");
		add(rotationValueLabel, cons);
			
		cons.gridx++;
		axisRotationValue = new JTextField("");
		axisRotationValue.setPreferredSize(DEFAULT_BUTTON_SIZE);
		add(axisRotationValue, cons);
			
		cons.gridx=0;
		cons.gridy++;
		ok = new JButton("Ok");
		ok.setPreferredSize(DEFAULT_BUTTON_SIZE);
		ok.addActionListener(e->{
			if(isAngleValueOk()) {
				LinkedList<Function> functionList = new LinkedList<>();
				TMatrix.AXIS axis = TMatrix.AXIS.values()[this.axisCombo.getSelectedIndex()];
				float angle = Float.parseFloat(this.axisRotationValue.getText());
				Point p = new Point();
				
				for(Function f : parent.getArrayFunction())
				{
					
					if(Optional.ofNullable(f.getPoint()).isPresent())
						{
							p.updatePoint(f.getPoint());
							f.setPoint(p);
							f = TMatrix.rotateGCodeBlock(f, angle, axis);
						}
						
					functionList.add(f);
				}
				parent.writeCode(functionList);
			}
			});
		add(ok, cons);
			
		cons.gridx++;
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(DEFAULT_BUTTON_SIZE);
		cancel.addActionListener(e->dispose());
		add(cancel, cons);

		
			
		pack();
	}
	
	
	private boolean isAngleValueOk()
	{
		try
		{
			Float.parseFloat(this.axisRotationValue.getText());
			 return true;
		}
		catch (NumberFormatException err)
		{
			JOptionPane.showMessageDialog(this, "Invalid rotation angle" , "Error" , JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
}
