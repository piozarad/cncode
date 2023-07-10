package CordConverter;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Parametry_wytaczanie extends JDialog {

	// Buttons
	JButton oblicz;

	// labels
	JLabel fn;
	JLabel r;
	JLabel Rt;

	// text Fields
	JTextField fn_txt;
	JTextField r_txt;
	
	JTextField Rt_txt;

	// jednostki
	
	JLabel fn_jednostka;
	JLabel r_jednostka;
	JLabel Rt_jednostka;

	
	
	
	//button size\
	private static final Dimension BUTTON_SIZE = new Dimension(75,25);
	private static final Dimension TEXT_FIELD_SIZE = new Dimension(40,25);

	public Parametry_wytaczanie() {
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints border =new GridBagConstraints();
		border.insets = new Insets(5,2,5,2);
		
		setVisible(true);
		setResizable(false);
		setTitle("Wykoñczenie powierzchni");
		setLocation(500,250);
		setPreferredSize(new Dimension(220,200));
		setLayout(layout);

		addWindowListener(new WindowAdapter()
				{
					@Override
					public void windowClosed(WindowEvent e)
					{
						setVisible(false);
					}
			
				});
		
		
		border.gridx=0;
		border.gridy=0;
		border.weightx=0.1;
		
		
	
		//// FIRST COLUMN/////
		border.anchor=GridBagConstraints.EAST;
		border.gridy=0;
		fn = new JLabel("fn");
		fn.setToolTipText("Posow na obrot");
		add(fn,border);
		
		border.gridy++;
		r = new JLabel("r");
		r.setToolTipText("Promien plytki skrawajacej");
		add(r,border);

		border.gridy++;
		Rt = new JLabel("Rt");
		Rt.setToolTipText("Teoretyczna wysokosc chropowatosci powierzchni Rt");
		add(Rt,border);


		
		////Second column//
		border.anchor=GridBagConstraints.CENTER;
		border.gridx=1;
		border.gridy=0;
		fn_txt = new JTextField(5);
		fn_txt.setPreferredSize(TEXT_FIELD_SIZE);
		add(fn_txt,border);
		
		border.gridy++;
		r_txt = new JTextField(5);
		r_txt.setPreferredSize(TEXT_FIELD_SIZE);
		add(r_txt,border);

		border.gridy++;
		Rt_txt = new JTextField(5);
		Rt_txt.setPreferredSize(TEXT_FIELD_SIZE);
		Rt_txt.setEditable(false);
		add(Rt_txt,border);

		
		
		////third column//
		border.anchor=GridBagConstraints.WEST;
		border.gridx=2;
		border.gridy=0;
		fn_jednostka = new JLabel("1/rev ");
		add(fn_jednostka,border);

	
		border.gridy++;
		r_jednostka = new JLabel("mm");
		add(r_jednostka,border);
		
		border.gridy++;
		Rt_jednostka = new JLabel("um");
		add(Rt_jednostka,border);

		// Buttons
		border.anchor=GridBagConstraints.CENTER;
		border.gridx=1;
		border.gridy=4;
		oblicz = new JButton("Oblicz");
		oblicz.setPreferredSize(BUTTON_SIZE);
		oblicz.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						float  Fn=0.1f, r=0.4f;
						boolean isOk =true;

						try {
							Fn = Float.parseFloat(fn_txt.getText());
						} catch (NumberFormatException er) {
							JOptionPane.showMessageDialog(Parametry_wytaczanie.this, "Z³e wartoœci");
							isOk=false;
						}


						try {
							r = Float.parseFloat(r_txt.getText());

						} catch (NumberFormatException er) {
							JOptionPane.showMessageDialog(Parametry_wytaczanie.this, "Z³e wartoœci");
							isOk=false;
						}

						if(isOk)
							Rt_txt.setText(Float.toString(Point.roundToThree(1000 * Fn * Fn / (8 * r))));

						
					}

				});
		add(oblicz,border);
		
		
		
		
		pack();

	}

	
	
		

			
			

			

	
}


