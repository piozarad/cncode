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
	private JLabel odchy³kaJednostka;
	private JLabel wartoscOdchy³kiJednostka;
	private JLabel wartoscOdchy³ki;
	private JLabel odchy³kaPlusMinusLabel;

	
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
	private final String klasy[]= {"f dobra", "m œrednia", "c zgrubna", "v bardzo zgrubna"};
	private final String typ[]= {"Wymiary liniowe", "Wymiary k¹towe", "Krawêdzie za³amane"};
	
	//variables
	private float nomina³;
	
	
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
		odchy³kaPlusMinusLabel = new JLabel("Odchy³ka:");
		add(odchy³kaPlusMinusLabel,border);
		
		
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
		
		//x1 y3 wartosc odchy³ki
		border.gridx=1;
		border.gridy=3;
		border.weightx=0;
		wartoscOdchy³ki = new JLabel("");
		add(wartoscOdchy³ki,border);
		
		
		//x1 y4 cofnij
		border.gridx=1;
		border.gridy=4;
		cofnij = new JButton("Powrót");
		cofnij.addActionListener(this);
		add(cofnij,border);
		
		
		//x2 y3 label jednostka nomina³
		border.gridx=2;
		border.gridy=2;
		odchy³kaJednostka = new JLabel("mm");
		add(odchy³kaJednostka,border);
		
		
		//x2 y4 jednostka odchy³ka
		border.gridx=2;
		border.gridy=3;
		wartoscOdchy³kiJednostka = new JLabel("mm");
		add(wartoscOdchy³kiJednostka,border);
		
		
	}
	
	
	
	
	
	
	
	private boolean isOk()
	{
		try
		{
			
			this.nomina³ =Float.parseFloat(wymiarNominalny.getText());
			
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this,"Z³y wymiar nominalny","B³¹d",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(nomina³<=0)
		{
			JOptionPane.showMessageDialog(this,"Wymiar nominalny nie mo¿e byæ mniejszy od zera","B³¹d",JOptionPane.WARNING_MESSAGE);
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
						if(nomina³<3)
						{
							
							wartoscOdchy³ki.setText(" ± 0.05");
						}
						else if(nomina³<6)
						{
							wartoscOdchy³ki.setText(" ± 0.05");
						}
						else if(nomina³<30)
						{
							wartoscOdchy³ki.setText(" ± 0.10");
						}
						else if(nomina³<120)
						{
							wartoscOdchy³ki.setText(" ± 0.15");
						}
						else if(nomina³<400)
						{
							wartoscOdchy³ki.setText(" ± 0.20");
						}
						else if(nomina³<1000)
						{
							wartoscOdchy³ki.setText(" ± 0.30");
						}
						else if(nomina³<2000)
						{
							wartoscOdchy³ki.setText(" ± 0.50");
						}
						else wartoscOdchy³ki.setText(" -");
					}
					if(klasaCombo.getSelectedIndex()==1)
					{
						if(nomina³<3)
						{
							
							wartoscOdchy³ki.setText(" ± 0.10");
						}
						else if(nomina³<6)
						{
							wartoscOdchy³ki.setText(" ± 0.10");
						}
						else if(nomina³<30)
						{
							wartoscOdchy³ki.setText(" ± 0.20");
						}
						else if(nomina³<120)
						{
							wartoscOdchy³ki.setText(" ± 0.30");
						}
						else if(nomina³<400)
						{
							wartoscOdchy³ki.setText(" ± 0.50");
						}
						else if(nomina³<1000)
						{
							wartoscOdchy³ki.setText(" ± 0.80");
						}
						else if(nomina³<2000)
						{
							wartoscOdchy³ki.setText(" ± 1.20");
						}
						else wartoscOdchy³ki.setText(" ± 2.0");
						
		
						
					}
					
					if(klasaCombo.getSelectedIndex()==2)
					{
						if(nomina³<3)
						{
							
							wartoscOdchy³ki.setText(" ± 0.15");
						}
						else if(nomina³<6)
						{
							wartoscOdchy³ki.setText(" ± 0.20");
						}
						else if(nomina³<30)
						{
							wartoscOdchy³ki.setText(" ± 0.50");
						}
						else if(nomina³<120)
						{
							wartoscOdchy³ki.setText(" ± 0.80");
						}
						else if(nomina³<400)
						{
							wartoscOdchy³ki.setText(" ± 1.20");
						}
						else if(nomina³<1000)
						{
							wartoscOdchy³ki.setText(" ± 2.0");
						}
						else if(nomina³<2000)
						{
							wartoscOdchy³ki.setText(" ± 3.0");
						}
						else wartoscOdchy³ki.setText(" ± 4.0");
						
						
						
					}
					
					
					if(klasaCombo.getSelectedIndex()==3)
					{
						if(nomina³<3)
						{
							
							wartoscOdchy³ki.setText(" -");
						}
						else if(nomina³<6)
						{
							wartoscOdchy³ki.setText(" ± 0.50");
						}
						else if(nomina³<30)
						{
							wartoscOdchy³ki.setText(" ± 1.0");
						}
						else if(nomina³<120)
						{
							wartoscOdchy³ki.setText(" ± 1.50");
						}
						else if(nomina³<400)
						{
							wartoscOdchy³ki.setText(" ± 2.50");
						}
						else if(nomina³<1000)
						{
							wartoscOdchy³ki.setText(" ± 4.0");
						}
						else if(nomina³<2000)
						{
							wartoscOdchy³ki.setText(" ± 6.0");
						}
						else wartoscOdchy³ki.setText(" ± 8.0");
						
						
						
					}
					
					
					
					
				}
		
				else if (this.typCombo.getSelectedIndex() == 1)
				
				{
					if(this.klasaCombo.getSelectedIndex()==0 || this.klasaCombo.getSelectedIndex()==1)
					
					{
						
						
						 if(nomina³<10)
						{
							wartoscOdchy³ki.setText(" ± 1");
							wartoscOdchy³kiJednostka.setText("stopni");
						}
						else if(nomina³<50)
						{
							wartoscOdchy³ki.setText(" ± 30'");
							wartoscOdchy³kiJednostka.setText("minut");
						}
						else if(nomina³<120)
						{
							wartoscOdchy³ki.setText(" ± 20'");
							wartoscOdchy³kiJednostka.setText("minut");
						}
						else if(nomina³<400)
						{
							wartoscOdchy³ki.setText(" ± 10'");
							wartoscOdchy³kiJednostka.setText("minut");
						}
						else {
							
							wartoscOdchy³ki.setText(" ± 5'");
							wartoscOdchy³kiJednostka.setText("minut");
						}
						
						
						
						
					}
					else if(this.klasaCombo.getSelectedIndex()==2)
					{
						if(nomina³<10)
						{
							wartoscOdchy³ki.setText(" ± 1.30");
							wartoscOdchy³kiJednostka.setText("stopni");
						}
						else if(nomina³<50)
						{
							wartoscOdchy³ki.setText(" ± 1");
							wartoscOdchy³kiJednostka.setText("stopni");
						}
						else if(nomina³<120)
						{
							wartoscOdchy³ki.setText(" ± 30'");
							wartoscOdchy³kiJednostka.setText("minut");
						}
						else if(nomina³<400)
						{
							wartoscOdchy³ki.setText(" ± 15'");
							wartoscOdchy³kiJednostka.setText("minut");
						}
						else {
							
							wartoscOdchy³ki.setText(" ± 10'");
							wartoscOdchy³kiJednostka.setText("minut");
						}
						
						
						
					}
					
					
					else if(this.klasaCombo.getSelectedIndex()==3)
					{
						if(nomina³<10)
						{
							wartoscOdchy³ki.setText(" ± 3");
							wartoscOdchy³kiJednostka.setText("stopni");
						}
						else if(nomina³<50)
						{
							wartoscOdchy³ki.setText(" ± 2'");
							wartoscOdchy³kiJednostka.setText("stopni");
						}
						else if(nomina³<120)
						{
							wartoscOdchy³ki.setText(" ± 1");
							wartoscOdchy³kiJednostka.setText("stopni");
						}
						else if(nomina³<400)
						{
							wartoscOdchy³ki.setText(" ± 30''");
							wartoscOdchy³kiJednostka.setText("minut");
						}
						else {
							
							wartoscOdchy³ki.setText(" ± 20'");
							wartoscOdchy³kiJednostka.setText("minut");
						}
			
					}
							
				}
				else if(this.typCombo.getSelectedIndex() == 2)
				{
					if(klasaCombo.getSelectedIndex()==0 || klasaCombo.getSelectedIndex()==1)
					{
						if(nomina³<0.5)
						{
							wartoscOdchy³ki.setText(" -");
						}
						else if(nomina³<3)
						{
							wartoscOdchy³ki.setText(" ± 0.20");
						}
						else if(nomina³<6)
						{
							wartoscOdchy³ki.setText(" ± 0.50");
						}
						
						else wartoscOdchy³ki.setText(" ± 1.0");
	
					}
					if(klasaCombo.getSelectedIndex()==2 || klasaCombo.getSelectedIndex()==3)
					{
						if(nomina³<0.5)
						{
							wartoscOdchy³ki.setText(" -");
						}
						else if(nomina³<3)
						{
							wartoscOdchy³ki.setText(" ± 0.40");
						}
						else if(nomina³<6)
						{
							wartoscOdchy³ki.setText(" ± 1.0");
						}
						
						else wartoscOdchy³ki.setText(" ± 2.0");
	
					}
				}
				Wind.log.writeInfoLog("tolerancje - wykonano", tolerancje.class.getSimpleName());
			}
	
		}
		else if(o == typCombo)
		{
			if (typCombo.getSelectedIndex() == 1)
			{
				odchy³kaJednostka.setText("stopni");
				srednicaLabel.setText("K¹t nominalny");
				wartoscOdchy³kiJednostka.setText("stopni");
			}
			else
			{
				odchy³kaJednostka.setText("mm");
				srednicaLabel.setText("Wymiar nominalny");
				wartoscOdchy³kiJednostka.setText("mm");
			}
		}
		
	}

}
