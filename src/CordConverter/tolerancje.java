package CordConverter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class tolerancje extends JFrame implements ActionListener {

	//labels
	private JLabel srednicaLabel;
	private JLabel klasaLabel;
	private JLabel typLabel;
	private JLabel odchy�kaJednostka;
	private JLabel wartoscOdchy�kiJednostka;
	private JLabel wartoscOdchy�ki;
	private JLabel odchy�kaPlusMinusLabel;

	
	//txt  
	private JTextField  wymiarNominalny;
	
	
	//JCombo
	private JComboBox<String> typCombo;
	private JComboBox<String> klasaCombo;
	
	//buttons
	private JButton oblicz;
	private JButton cofnij;
	
	//parent
	private final Wind parent;
	
	//constrant combo 
	private final String klasy[]= {"f dobra", "m �rednia", "c zgrubna", "v bardzo zgrubna"};
	private final String typ[]= {"Wymiary liniowe", "Wymiary k�towe", "Kraw�dzie za�amane"};
	
	//variables
	private float nomina�;
	
	
	public tolerancje(Wind parent)
	{
		this.parent=parent;
		
		setTitle("Tolerancje wg. ISO 2768-1");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(400,300);
		setResizable(false);
		
		
		//exit on close
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				
				cofnij.doClick();
			
			}		
		});
		
		
		//layout
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(15,15,15,15);
		border.weightx=0.;
		border.weighty=0.;
		border.fill = GridBagConstraints.HORIZONTAL;
		
		
		//x0 y0 typLabel
		border.gridx=0;
		border.gridy=0;
		typLabel = new JLabel("Typ");
		add(typLabel,border);
		
		//x0 y1 klasaLabel
		border.gridx=0;
		border.gridy=1;
		klasaLabel = new JLabel("Klasa tolerancji");
		add(klasaLabel,border);
		
		// x0 y2 srednica
		border.gridx=0;
		border.gridy=2;
		srednicaLabel = new JLabel("Wymiar nominalny");
		add(srednicaLabel,border);
		
		
		//x0 y3 wynik
		border.gridx=0;
		border.gridy=3;
		odchy�kaPlusMinusLabel = new JLabel("Odchy�ka:");
		add(odchy�kaPlusMinusLabel,border);
		
		
		//x0 y4 oblicz
		border.gridx=0;
		border.gridy=4;
		oblicz = new JButton("Sprawdz");
		oblicz.addActionListener(this);
		add(oblicz,border);
		
		//x1 y0 typCombo
		border.gridx=1;
		border.gridy=0;
		typCombo = new JComboBox<String>(typ);
		typCombo.setSelectedIndex(0);
		typCombo.addActionListener(this);
		add(typCombo,border);
		
		//x1 y1 klasa Combo
		border.gridx=1;
		border.gridy=1;
		klasaCombo = new JComboBox<String>(klasy);
		klasaCombo.setSelectedIndex(1);
		klasaCombo.addActionListener(this);
		add(klasaCombo,border);
		
		
		//x1 y2; wymiar nominalny txt
		border.gridx=1;
		border.gridy=2;
		border.weightx=0.1;
		wymiarNominalny = new JTextField("");
		//wymiarNominalny.setSize(75,150);
		add(wymiarNominalny,border);
		
		//x1 y3 wartosc odchy�ki
		border.gridx=1;
		border.gridy=3;
		border.weightx=0;
		wartoscOdchy�ki = new JLabel("");
		add(wartoscOdchy�ki,border);
		
		
		//x1 y4 cofnij
		border.gridx=1;
		border.gridy=4;
		cofnij = new JButton("Powr�t");
		cofnij.addActionListener(this);
		add(cofnij,border);
		
		
		//x2 y3 label jednostka nomina�
		border.gridx=2;
		border.gridy=2;
		odchy�kaJednostka = new JLabel("mm");
		add(odchy�kaJednostka,border);
		
		
		//x2 y4 jednostka odchy�ka
		border.gridx=2;
		border.gridy=3;
		wartoscOdchy�kiJednostka = new JLabel("mm");
		add(wartoscOdchy�kiJednostka,border);
		
		
	}
	
	
	
	
	
	
	
	private boolean isOk()
	{
		try
		{
			
			this.nomina� =Float.parseFloat(wymiarNominalny.getText());
			
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this,"Z�y wymiar nominalny","B��d",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(nomina�<=0)
		{
			JOptionPane.showMessageDialog(this,"Wymiar nominalny nie mo�e by� mniejszy od zera","B��d",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		
		if (o==cofnij)
		{
			parent.n = null;
			this.dispose();
		}
		else if(o== oblicz)
		{
			
			if(isOk())
			{
				if(this.typCombo.getSelectedIndex() == 0)
				{
					if(this.klasaCombo.getSelectedIndex()==0)
					{
						if(nomina�<3)
						{
							
							wartoscOdchy�ki.setText(" � 0.05");
						}
						else if(nomina�<6)
						{
							wartoscOdchy�ki.setText(" � 0.05");
						}
						else if(nomina�<30)
						{
							wartoscOdchy�ki.setText(" � 0.10");
						}
						else if(nomina�<120)
						{
							wartoscOdchy�ki.setText(" � 0.15");
						}
						else if(nomina�<400)
						{
							wartoscOdchy�ki.setText(" � 0.20");
						}
						else if(nomina�<1000)
						{
							wartoscOdchy�ki.setText(" � 0.30");
						}
						else if(nomina�<2000)
						{
							wartoscOdchy�ki.setText(" � 0.50");
						}
						else wartoscOdchy�ki.setText(" -");
					}
					if(klasaCombo.getSelectedIndex()==1)
					{
						if(nomina�<3)
						{
							
							wartoscOdchy�ki.setText(" � 0.10");
						}
						else if(nomina�<6)
						{
							wartoscOdchy�ki.setText(" � 0.10");
						}
						else if(nomina�<30)
						{
							wartoscOdchy�ki.setText(" � 0.20");
						}
						else if(nomina�<120)
						{
							wartoscOdchy�ki.setText(" � 0.30");
						}
						else if(nomina�<400)
						{
							wartoscOdchy�ki.setText(" � 0.50");
						}
						else if(nomina�<1000)
						{
							wartoscOdchy�ki.setText(" � 0.80");
						}
						else if(nomina�<2000)
						{
							wartoscOdchy�ki.setText(" � 1.20");
						}
						else wartoscOdchy�ki.setText(" � 2.0");
						
		
						
					}
					
					if(klasaCombo.getSelectedIndex()==2)
					{
						if(nomina�<3)
						{
							
							wartoscOdchy�ki.setText(" � 0.15");
						}
						else if(nomina�<6)
						{
							wartoscOdchy�ki.setText(" � 0.20");
						}
						else if(nomina�<30)
						{
							wartoscOdchy�ki.setText(" � 0.50");
						}
						else if(nomina�<120)
						{
							wartoscOdchy�ki.setText(" � 0.80");
						}
						else if(nomina�<400)
						{
							wartoscOdchy�ki.setText(" � 1.20");
						}
						else if(nomina�<1000)
						{
							wartoscOdchy�ki.setText(" � 2.0");
						}
						else if(nomina�<2000)
						{
							wartoscOdchy�ki.setText(" � 3.0");
						}
						else wartoscOdchy�ki.setText(" � 4.0");
						
						
						
					}
					
					
					if(klasaCombo.getSelectedIndex()==3)
					{
						if(nomina�<3)
						{
							
							wartoscOdchy�ki.setText(" -");
						}
						else if(nomina�<6)
						{
							wartoscOdchy�ki.setText(" � 0.50");
						}
						else if(nomina�<30)
						{
							wartoscOdchy�ki.setText(" � 1.0");
						}
						else if(nomina�<120)
						{
							wartoscOdchy�ki.setText(" � 1.50");
						}
						else if(nomina�<400)
						{
							wartoscOdchy�ki.setText(" � 2.50");
						}
						else if(nomina�<1000)
						{
							wartoscOdchy�ki.setText(" � 4.0");
						}
						else if(nomina�<2000)
						{
							wartoscOdchy�ki.setText(" � 6.0");
						}
						else wartoscOdchy�ki.setText(" � 8.0");
						
						
						
					}
					
					
					
					
				}
		
				else if (this.typCombo.getSelectedIndex() == 1)
				
				{
					if(this.klasaCombo.getSelectedIndex()==0 || this.klasaCombo.getSelectedIndex()==1)
					
					{
						
						
						 if(nomina�<10)
						{
							wartoscOdchy�ki.setText(" � 1");
							wartoscOdchy�kiJednostka.setText("stopni");
						}
						else if(nomina�<50)
						{
							wartoscOdchy�ki.setText(" � 30'");
							wartoscOdchy�kiJednostka.setText("minut");
						}
						else if(nomina�<120)
						{
							wartoscOdchy�ki.setText(" � 20'");
							wartoscOdchy�kiJednostka.setText("minut");
						}
						else if(nomina�<400)
						{
							wartoscOdchy�ki.setText(" � 10'");
							wartoscOdchy�kiJednostka.setText("minut");
						}
						else {
							
							wartoscOdchy�ki.setText(" � 5'");
							wartoscOdchy�kiJednostka.setText("minut");
						}
						
						
						
						
					}
					else if(this.klasaCombo.getSelectedIndex()==2)
					{
						if(nomina�<10)
						{
							wartoscOdchy�ki.setText(" � 1.30");
							wartoscOdchy�kiJednostka.setText("stopni");
						}
						else if(nomina�<50)
						{
							wartoscOdchy�ki.setText(" � 1");
							wartoscOdchy�kiJednostka.setText("stopni");
						}
						else if(nomina�<120)
						{
							wartoscOdchy�ki.setText(" � 30'");
							wartoscOdchy�kiJednostka.setText("minut");
						}
						else if(nomina�<400)
						{
							wartoscOdchy�ki.setText(" � 15'");
							wartoscOdchy�kiJednostka.setText("minut");
						}
						else {
							
							wartoscOdchy�ki.setText(" � 10'");
							wartoscOdchy�kiJednostka.setText("minut");
						}
						
						
						
					}
					
					
					else if(this.klasaCombo.getSelectedIndex()==3)
					{
						if(nomina�<10)
						{
							wartoscOdchy�ki.setText(" � 3");
							wartoscOdchy�kiJednostka.setText("stopni");
						}
						else if(nomina�<50)
						{
							wartoscOdchy�ki.setText(" � 2'");
							wartoscOdchy�kiJednostka.setText("stopni");
						}
						else if(nomina�<120)
						{
							wartoscOdchy�ki.setText(" � 1");
							wartoscOdchy�kiJednostka.setText("stopni");
						}
						else if(nomina�<400)
						{
							wartoscOdchy�ki.setText(" � 30''");
							wartoscOdchy�kiJednostka.setText("minut");
						}
						else {
							
							wartoscOdchy�ki.setText(" � 20'");
							wartoscOdchy�kiJednostka.setText("minut");
						}
			
					}
							
				}
				else if(this.typCombo.getSelectedIndex() == 2)
				{
					if(klasaCombo.getSelectedIndex()==0 || klasaCombo.getSelectedIndex()==1)
					{
						if(nomina�<0.5)
						{
							wartoscOdchy�ki.setText(" -");
						}
						else if(nomina�<3)
						{
							wartoscOdchy�ki.setText(" � 0.20");
						}
						else if(nomina�<6)
						{
							wartoscOdchy�ki.setText(" � 0.50");
						}
						
						else wartoscOdchy�ki.setText(" � 1.0");
	
					}
					if(klasaCombo.getSelectedIndex()==2 || klasaCombo.getSelectedIndex()==3)
					{
						if(nomina�<0.5)
						{
							wartoscOdchy�ki.setText(" -");
						}
						else if(nomina�<3)
						{
							wartoscOdchy�ki.setText(" � 0.40");
						}
						else if(nomina�<6)
						{
							wartoscOdchy�ki.setText(" � 1.0");
						}
						
						else wartoscOdchy�ki.setText(" � 2.0");
	
					}
				}
				Wind.log.writeInfoLog("tolerancje - wykonano", tolerancje.class.getSimpleName());
			}
	
		}
		else if(o == typCombo)
		{
			if (typCombo.getSelectedIndex() == 1)
			{
				odchy�kaJednostka.setText("stopni");
				srednicaLabel.setText("K�t nominalny");
				wartoscOdchy�kiJednostka.setText("stopni");
			}
			else
			{
				odchy�kaJednostka.setText("mm");
				srednicaLabel.setText("Wymiar nominalny");
				wartoscOdchy�kiJednostka.setText("mm");
			}
		}
		
	}

}
