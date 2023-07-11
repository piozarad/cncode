package CordConverter;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class toolBar extends JToolBar{

	JLabel toolNumberLabel;
	JSpinner toolNumberSpinner;
	
	JLabel bRotation;
	JTextField rotationTxt;
	JButton cofnij;
	JButton dalej;
	JLabel baseLabel;
	JTextField basetxt;
	
	JLabel sterowanie;
	
	private static final Dimension dim = new Dimension(50,25);
	
	public toolBar()
	{

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints border = new GridBagConstraints();
		setLayout(layout);
		Insets def = new Insets(5,5,5,5);
		border.insets = def;
		border.gridx=0;
		border.gridy=0;
		
		this.setFloatable(false);
		
		
		
		
		toolNumberLabel = new JLabel("Nr. narzêdzia T");
		add(toolNumberLabel,border);
		
		
		
		border.gridx =1;
		SpinnerNumberModel model = new SpinnerNumberModel(1,1,999,1);
		toolNumberSpinner = new JSpinner(model);
		add(toolNumberSpinner,border);
		
	
		border.gridx=2;
		bRotation = new JLabel("Kat obrotu sto³u B");
		add(bRotation,border);
		
		border.gridx=3;
		border.fill=GridBagConstraints.NONE;
		border.anchor=GridBagConstraints.WEST;
		border.weightx=0;
		rotationTxt = new JTextField("0.000  ");
		rotationTxt.setMinimumSize(dim);
		rotationTxt.setSize(250, 25);
		add(rotationTxt,border);
		
		border.gridx=4;
		baseLabel = new JLabel("Baza");
		add(baseLabel, border);
		
		border.gridx=5;
		basetxt = new JTextField("G54");
		basetxt.setMinimumSize(dim);
		add(basetxt,border);
		
		JSeparator separator1 = new JSeparator(SwingConstants.VERTICAL);
		border.fill=GridBagConstraints.VERTICAL;
		border.gridx=6;
		border.weighty=0.5;
		add(separator1,border);
		
		
		
		cofnij = new JButton("<<");
		cofnij.setToolTipText("Cofnij");

		cofnij.setEnabled(false);
		border.gridx=7;
		border.fill=GridBagConstraints.NONE;
		border.weightx=0;
		border.insets= new Insets(5,1,5,0);
		add(cofnij,border);
		
		dalej = new JButton(">>");
		dalej.setToolTipText("Dalej");

		dalej.setEnabled(false);
		border.gridx=8;
		border.fill=GridBagConstraints.HORIZONTAL;
		border.weightx=0;
		add(dalej,border);
		
		border.insets = def;
		
		JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
		border.fill=GridBagConstraints.BOTH;
		border.gridx=9;
		border.weighty=0.5;
		border.weightx=0.5;
		add(separator2,border);
	
		
		
		
	}
	



public int getToolNumber()
{
	return Integer.parseInt(this.toolNumberSpinner.getValue().toString());
	
}

public String getBase()
{
	return this.basetxt.getText();
}


public float getRotation()
{
	float result = 0;
	
	try
	{
		result = Float.parseFloat(rotationTxt.getText());
	}
	
	catch(NumberFormatException e)
	{
		this.rotationTxt.setText("0.000");
		return 0.f;
		
	}
	
	return result;

}
	
	
}
