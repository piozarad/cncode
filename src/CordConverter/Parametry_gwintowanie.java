package CordConverter;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Parametry_gwintowanie extends JDialog implements ActionListener{

	//labels
	JLabel rodzaj;
	JLabel f;
	JLabel s;
	JLabel M;
	JLabel d_wiertla;
	
	//TextFields
	JTextField f_txt;
	JTextField s_txt;
	JTextField d_wiertla_txt;
	
	//Jednostki
	JLabel f_jednostka;
	JLabel s_jednostka;
	JLabel d_wiertla_jednostka;
	
	//przyciski
	JButton oblicz;

	//tablica
	Hashtable<String,Float> skoki;
	
	//comboBoxy
	static final String[] rodzaje = {"Metryczny M", "Drobnozwojowy MF"};
	static final String[] Gwinty_Metryczne = {"M1","M1.2","M1.4","M1.6","M1.8","M2","M2.2","M2.5","M3","M3.5","M4","M5","M6","M7", "M8","M9", "M10","M11", "M12","M14", "M16","M18","M20","M22","M24","M27","M30","M33","M36","M39","M42","M45","M48","M52","M56","M60","M64","M68" };
	static final String[] Gwinty_Drobnozwojowe = {"M3x0.35","M4x0.5","M5x0.5","M6x0.75","M7x0.75", "M8x0.75","M8x1.","M9x1.", "M10x0.75","M10x1.","M10x1.25","M12x1.", "M12x1.25","M12x1.5","M14x1.5", "M16x1","M16x1.5","M18x2.","M20x1.5","M20x2.","M22x1.5","M22x2.","M24x1.","M24x1.5","M24x2.","M27x1.5","M27x2.","M28x1.5","M30x2","M33x2","M36x1.5","M36x2.","M39x3","M42x1.5","M45x1.5","M48x1.5" };
	
		
	
	JComboBox<String> Combo_rodzaje;
	JComboBox<String> Combo_gwinty;
	
	public Parametry_gwintowanie()
	{
		setVisible(true);
		setPreferredSize(new Dimension(400,360));
		setResizable(false);
		setLocation(550, 250);
		setTitle("Gwintowanie");
		
		this.addWindowListener(new WindowAdapter()
				{
			@Override
			public void windowClosing(WindowEvent e) {

				 setVisible(false);


			}
			
				});
		
		
		
		
		GridBagLayout bagLayout= new GridBagLayout();
		setLayout(bagLayout);
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(15,15,15,15);
		border.anchor = GridBagConstraints.NORTHWEST;
		border.fill = GridBagConstraints.HORIZONTAL;
		
		border.gridx=0;
		border.gridy=0;
		rodzaj = new JLabel("Rodzaj gwintu");
		add(rodzaj,border);
		
		border.gridx=1;
		Combo_rodzaje = new JComboBox<>(rodzaje);
		Combo_rodzaje.addActionListener(this);
		add(Combo_rodzaje,border);
		
		border.gridx=0;
		border.gridy=1;
		M= new JLabel("Wymiar gwintu");
		add(M,border);
		
		border.gridx=1;
		Combo_gwinty = new JComboBox<>(Gwinty_Metryczne);
		add(Combo_gwinty,border);
		
		border.gridx=0;
		border.gridy=2;
		s= new JLabel("Obroty wrzeciona S");
		s.setToolTipText("Predkosc odbrotowa wrzeciona");
		add(s,border);
		
		border.gridx=1;
		s_txt = new JTextField("        ");
		s_txt.setSize(75, 45);
		add(s_txt,border);
		
		border.gridx=2;
		s_jednostka = new JLabel("1/min");
		add(s_jednostka,border);
		
		border.gridx=0;
		border.gridy=3;
		f=new JLabel("Posuw F");
		add(f,border);
		
		border.gridx=1;
		f_txt = new JTextField("      ");
		f_txt.setSize(75, 45);
		f_txt.setEditable(false);
		add(f_txt,border);
		
		border.gridx=2;

		f_jednostka = new JLabel("mm/min");
		add(f_jednostka,border);
		
		border.gridx=0;
		border.gridy=4;
		d_wiertla = new JLabel("Œrednica wiert³a");
		add(d_wiertla,border);
		
		border.gridx=1;
		d_wiertla_txt = new JTextField("      ");
		d_wiertla_txt.setSize(75, 45);
		d_wiertla_txt.setEditable(false);
		add(d_wiertla_txt,border);
		
		border.gridx=2;
		d_wiertla_jednostka = new JLabel("mm");
		add(d_wiertla_jednostka,border);
		
		border.fill=GridBagConstraints.NONE;
		border.gridx=1;
		border.gridy=5;
		oblicz=new JButton("Oblicz");
		oblicz.addActionListener(this);
		add(oblicz,border);
		
		
		pack();
		
		skoki = new Hashtable<>();
		skoki.put("M1", 0.25f);
		skoki.put("M1.2", 0.25f);
		skoki.put("M1.4", 0.3f);
		skoki.put("M1.6", 0.35f);
		skoki.put("M1.8", 0.35f);
		skoki.put("M2", 0.4f);
		skoki.put("M2.2", 0.45f);
		skoki.put("M2.5", 0.35f);
		skoki.put("M3", 0.5f);
		skoki.put("M3.5", 0.6f);
		skoki.put("M4", 0.7f);
		skoki.put("M5", 0.8f);
		skoki.put("M6", 1.f);
		skoki.put("M7", 1.f);
		skoki.put("M8", 1.25f);
		skoki.put("M9", 1.25f);
		skoki.put("M10", 1.5f);
		skoki.put("M12", 1.75f);
		skoki.put("M14", 2.f);
		skoki.put("M16", 2.f);
		skoki.put("M18", 2.5f);
		skoki.put("M20", 2.5f);
		skoki.put("M22", 2.5f);
		skoki.put("M24", 3.f);
		skoki.put("M27", 3.f);
		skoki.put("M30", 3.5f);
		skoki.put("M33", 3.5f);
		skoki.put("M36", 4.f);
		skoki.put("M39", 4.f);
		skoki.put("M42", 4.5f);
		skoki.put("M45", 4.5f);
		skoki.put("M48", 5.f);
		skoki.put("M52", 5.f);
		skoki.put("M56", 5.5f);
		skoki.put("M60", 5.5f);
		skoki.put("M64", 6.f);
		skoki.put("M68", 6.f);
		
	
		
		
	}

	
	@Override
	public String toString()
	{
		return "Parameterów skrawania - gwintowanie";
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
			
		
		Object o = e.getSource();
	if(o==Combo_rodzaje)	
	{
		if(Combo_rodzaje.getSelectedIndex()==0)
		{
				
			Combo_gwinty.removeAllItems();
			for(int i=0; i<Gwinty_Metryczne.length;i++)
			{
				Combo_gwinty.addItem(Gwinty_Metryczne[i]);	
			}
			
			
		}
		else if(Combo_rodzaje.getSelectedIndex()==1)
		{
			Combo_gwinty.removeAllItems();
			for(int i=0; i<Gwinty_Drobnozwojowe.length;i++)
			{
				Combo_gwinty.addItem(Gwinty_Drobnozwojowe[i]);
			}
			
		}
		else 
		{
			System.out.println("*Blad \nNieprzewidziana sytuacja");
		}
	
	}
	
	else if(o==oblicz)
	{
		float skok,rozmiar_wiertla;
		int posow,obroty;
		
		if(Combo_rodzaje.getSelectedIndex()==0)
		{
			skok = skoki.get(Combo_gwinty.getSelectedItem().toString());
			
			rozmiar_wiertla = Point.roundToThree(Float.parseFloat((Combo_gwinty.getSelectedItem().toString()).substring(1)));
			d_wiertla_txt.setText(Float.toString((rozmiar_wiertla-skok)));
			
		}
		else if(Combo_rodzaje.getSelectedIndex()==1)
		{
			String s = Combo_gwinty.getSelectedItem().toString();
			skok=Float.parseFloat(s.substring(s.indexOf('x')+1));

			
			rozmiar_wiertla = Float.parseFloat(s.substring(1,s.indexOf('x')));
			d_wiertla_txt.setText(Float.toString(Point.roundToThree((rozmiar_wiertla-skok))));
			
		}
		else{
				skok=0;
				posow=0;
				rozmiar_wiertla=0;
			}
		
		try
		{
			obroty = Integer.parseInt(s_txt.getText());
			
		}
		catch (NumberFormatException err)
		{
			obroty =200;
			s_txt.setText("200");
		}
		
		
		posow = (int)(obroty*skok);
		f_txt.setText(Integer.toString(posow));
	}

	
	
	}
}	
			
			
		
	







