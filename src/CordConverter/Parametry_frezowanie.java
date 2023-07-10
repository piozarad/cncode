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
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Parametry_frezowanie extends JDialog implements ActionListener{

	// Buttons
	JButton oblicz;

	// labels
	JLabel Vc;
	JLabel fn;
	JLabel n;
	JLabel Vf;
	JLabel D;
	JLabel cale;
	JLabel z;

	// text Fields
	JTextField Vc_txt;
	JTextField fn_txt;
	JTextField n_txt;
	JTextField Vf_txt;
	JTextField D_txt;
	JTextField z_txt;

	// jednostki
	JLabel Vc_jednostka;
	JLabel fn_jednostka;
	JLabel n_jednostka;
	JLabel Vf_jednostka;
	JLabel D_jednostka;

	// check boxy
	JCheckBox caleBox;
	
	
	
	static final Dimension TEXT_FIELD_SIZE = new Dimension(50,25);
	static final Dimension BUTTON_SIZE = new Dimension(75,150);

	public Parametry_frezowanie() {
		
		setTitle("Parametry skrawania");
		setVisible(true);
		setPreferredSize(new Dimension(350, 380));
		setLocation(550,250);
		setResizable(false);
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10,10,10,10);
		
		
		
		addWindowListener(new WindowAdapter()
				{
			
				@Override
				public void windowClosing(WindowEvent e)
				{
					setVisible(false);
				}
			
			
				});
		
		
		////first row////
		constraints.gridx=0;
		constraints.gridy=0;
		cale = new JLabel("Cale");
		cale.setToolTipText("Obliczenia w calach");
		add(cale,constraints);
		constraints.gridx=1;
		caleBox = new JCheckBox();
		caleBox.addActionListener(this);
		add(caleBox,constraints);

		
		
		
		////second row////
		constraints.gridx=0;
		constraints.gridy=1;
		Vc = new JLabel("Vc");
		Vc.setToolTipText("Predkosc skrawania");
		add(Vc,constraints);
		
		
		constraints.gridx=1;
		constraints.gridy=1;
		Vc_txt = new JTextField(7);
		Vc_txt.setMinimumSize(TEXT_FIELD_SIZE);
		add(Vc_txt,constraints);
		
		constraints.gridx=2;
		constraints.gridy=1;
		Vc_jednostka = new JLabel("m/min");
		add(Vc_jednostka,constraints);
		
		
		
		////third row////
		constraints.gridx=0;
		constraints.gridy=2;
		fn = new JLabel("fz");
		fn.setToolTipText("Posow na z¹b");
		add(fn,constraints);

		constraints.gridx=1;
		constraints.gridy=2;
		fn_txt = new JTextField(7);
		fn_txt.setMinimumSize(TEXT_FIELD_SIZE);
		add(fn_txt,constraints);
		
		constraints.gridx=2;
		constraints.gridy=2;
		fn_jednostka = new JLabel("1/rev");
		add(fn_jednostka,constraints);
		
		
		////fourth row////
		constraints.gridx=0;
		constraints.gridy=3;
		D = new JLabel("Œrednica narzêdzia D");
		add(D,constraints);
		
		
		constraints.gridx=1;
		constraints.gridy=3;
		D_txt = new JTextField(7);
		D_txt.setMinimumSize(TEXT_FIELD_SIZE);
		add(D_txt,constraints);
		
		constraints.gridx=2;
		constraints.gridy=3;
		D_jednostka = new JLabel("mm");
		add(D_jednostka,constraints);
		

		////fifth row////
		constraints.gridx=0;
		constraints.gridy=4;
		z = new JLabel("Liczba ostrzy z");
		z.setToolTipText("Liczba ostrzy bioracych udzial w skrawaniu");
		add(z,constraints);
		
		constraints.gridx=1;
		constraints.gridy=4;
		z_txt = new JTextField(7);
		z_txt.setMinimumSize(TEXT_FIELD_SIZE);
		add(z_txt,constraints);
		
		
		////sixt row////
		constraints.gridx=0;
		constraints.gridy=5;
		n = new JLabel("n");
		n.setToolTipText("Obroty");
		add(n,constraints);
		
		constraints.gridx=1;
		constraints.gridy=5;
		n_txt = new JTextField(7);
		n_txt.setMinimumSize(TEXT_FIELD_SIZE);
		n_txt.setEditable(false);
		add(n_txt,constraints);
		
		constraints.gridx=2;
		constraints.gridy=5;
		n_jednostka = new JLabel("obr/min");
		add(n_jednostka,constraints);

		
		
		////seventh row////
		constraints.gridx=0;
		constraints.gridy=6;
		Vf = new JLabel("Vf");
		Vf.setToolTipText("Predkosc posowu");
		Vf.setMinimumSize(TEXT_FIELD_SIZE);
		add(Vf,constraints);


		constraints.gridx=1;
		constraints.gridy=6;
		Vf_txt = new JTextField(7);
		Vf_txt.setMinimumSize(TEXT_FIELD_SIZE);
		Vf_txt.setEditable(false);
		add(Vf_txt,constraints);

		constraints.gridx=2;
		constraints.gridy=6;
		Vf_jednostka = new JLabel("mm/min");
		add(Vf_jednostka,constraints);
	
		
		////eight row ////
		constraints.gridx=1;
		constraints.gridy=7;

		oblicz = new JButton("Oblicz");
		oblicz.setMinimumSize(BUTTON_SIZE);
		oblicz.addActionListener(this);
		add(oblicz,constraints);


		pack();
	}

	@Override
	public String toString()
	{
		return "Parameterów skrawania - Frezowanie";
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object o = event.getSource();

		if (o == oblicz) {
			float vc, Fn, d;
			int N, VF, Z;
			int di;
			if (caleBox.isSelected())
				di = 12;
			else
				di = 1000;

			try {
				vc = Float.parseFloat(Vc_txt.getText());

			}

			catch (NumberFormatException e) {
				vc = 0;

			}

			try {
				Fn = Float.parseFloat(fn_txt.getText());
			} catch (NumberFormatException e) {
				Fn = 0;
			}
			try {
				d = Float.parseFloat(D_txt.getText());
			} catch (NumberFormatException e) {
				d = 1;
			}
			try {
				Z = Integer.parseInt(z_txt.getText());
			} catch (NumberFormatException e) {
				Z = 1;
			}

			N = (int) (vc * di / (d * (float) Math.PI));
			n_txt.setText(Integer.toString(N));

			VF = (int) (Fn * N * Z);
			Vf_txt.setText(Integer.toString(VF));

		}

		else if (o == caleBox) {
			if (caleBox.isSelected()) {
				float temp;
				Vc_jednostka.setText("stopy/min");
				try {
					temp = Float.parseFloat(Vc_txt.getText());
					Vc_txt.setText(Integer.toString((int) ((temp / 0.3048) + 1)));

				} catch (NumberFormatException e) {
					Vc_txt.setText("0");
				}

				fn_jednostka.setText("cale/obr");

				try {
					temp = Float.parseFloat(fn_txt.getText());
					fn_txt.setText(Float.toString((Point.roundToThree((float) (temp / 25.4)))));

				} catch (NumberFormatException e) {
					fn_txt.setText("0");
				}

				Vf_jednostka.setText("cale/min");
				try {
					temp = Float.parseFloat(Vf_txt.getText());
					Vf_txt.setText(Integer.toString((((int) (temp / 25.4)))));

				} catch (NumberFormatException e) {
					Vf_txt.setText("0");
				}

				D_jednostka.setText("cal");
				try {
					temp = Float.parseFloat(D_txt.getText());
					D_txt.setText(Float.toString((Point.roundToThree(temp / 25.4f))));

				} catch (NumberFormatException e) {
					D_txt.setText("1");
				}

			} else {
				float temp;
				Vc_jednostka.setText("m/min");
				try {
					temp = Float.parseFloat(Vc_txt.getText());
					Vc_txt.setText(Integer.toString((int) (temp * 0.3048)));

				} catch (NumberFormatException e) {
					Vc_txt.setText("0");
				}

				fn_jednostka.setText("mm/obr");

				try {
					temp = Float.parseFloat(fn_txt.getText());
					fn_txt.setText(Float.toString((Point.roundToThree((float) (temp * 25.4)))));

				} catch (NumberFormatException e) {
					fn_txt.setText("0");
				}
				Vf_jednostka.setText("mm/min");
				try {
					temp = Float.parseFloat(Vf_txt.getText());
					Vf_txt.setText(Integer.toString((((int) (temp * 25.4)))));

				} catch (NumberFormatException e) {
					Vf_txt.setText("0");
				}

				D_jednostka.setText("mm");

				try {
					temp = Float.parseFloat(D_txt.getText());
					D_txt.setText(Float.toString((Point.roundToThree(temp * 25.4f))));

				} catch (NumberFormatException e) {
					D_txt.setText("1");
				}

			}

			oblicz.doClick();

		}

	}

}
