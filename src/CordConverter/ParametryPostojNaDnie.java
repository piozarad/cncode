package CordConverter;

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

public class ParametryPostojNaDnie extends JDialog {

	// Buttons
	JButton oblicz;

	// labels
	
	JLabel n;
	JLabel rev;
	JLabel P;

	// text Fields
	JTextField n_txt;
	JTextField rev_txt;
	JTextField P_txt;

	// jednostki

	JLabel n_jednostka;
	JLabel P_jednostka;


	//zmienne
	float revolutions;
	int s;



	public ParametryPostojNaDnie() {
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(5,5,5,5);
		
		setLayout(layout);
		setVisible(true);
		setResizable(false);
		setSize(300, 300);
		setLocation(550,400);
		setTitle("Postój");

		
		addWindowListener(new WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent e)
					{
						ParametryPostojNaDnie.this.setVisible(false);
					}
				});


		////first column////
		border.gridx=0;
		border.gridy=0;
		border.weightx=0.1;
		border.anchor=GridBagConstraints.EAST;
		
		n = new JLabel("n");
		n.setToolTipText("Obroty wrzeciona");
		add(n,border);

		
		border.gridy=1;
		rev = new JLabel("rev");
		rev.setToolTipText("Liczba obrotow na dnie otworu - domyslnie 1.5");
		add(rev,border);

		border.gridy=2;
		P = new JLabel("P");
		P.setToolTipText("Czas postoju na dnie otworu cyklu G82 w milisekundach - Fanuc P, Sinum R4");
		add(P,border);

		
		
		
		////second column////
		border.anchor=GridBagConstraints.CENTER;
		border.gridx=1;
		border.gridy=0;
		n_txt = new JTextField(5);
		add(n_txt,border);

		
		border.gridy=1;
		rev_txt = new JTextField(5);
		rev_txt.setText("1.5");
		add(rev_txt,border);

		border.gridy=2;
		P_txt = new JTextField(5);
		P_txt.setEditable(false);
		add(P_txt,border);
		
		border.gridy=3;
		oblicz = new JButton("Oblicz");
		oblicz.addActionListener( new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(getValues())
						{
							
							P_txt.setText(Integer.toString((int) (revolutions * 60000 / s)));
									
						}

					}
			
				});
		add(oblicz,border);

	
		
		

		// //third collumn////
		border.gridx=2;
		border.gridy=0;
		border.anchor=GridBagConstraints.WEST;
		n_jednostka = new JLabel("obr/min");
		add(n_jednostka,border);

		border.gridy=2;
		P_jednostka = new JLabel("ms");
		add(P_jednostka,border);

		
		pack();
	}

	@Override
	public String toString()
	{
		return "Parameterów skrawania - nawiercanie";
	}
	
	
	
	private boolean getValues()
	{
	
		
		try
		{
			this.s=Integer.parseInt(this.n_txt.getText());
			
			
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Niepoprawna wartoœæ - obroty wrzeciona n");
			return false;
		}
		
		try
		{
			this.revolutions=Float.parseFloat(this.rev_txt.getText());
			
			
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Niepoprawna wartoœæ - iloœæ obrotów narzêdzia na dnie otworu");
			return false;
		}
		
		return true;
		
	}
	

		


		

	

}
