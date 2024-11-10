package CordConverter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Przesun extends JFrame implements ActionListener{

	private Edytor parent;
	
	//labels
	private JLabel osLabel;
	private JLabel oWartoscLabel;
	private JLabel calyDokLabel;
	private JLabel odBloku;
	private JLabel doBloku;
	
	//combo
	private JComboBox<String> osCombo;
	private final String axisOptions[] = {"X", "Y", "Z"};
	
	//txtFields
	private JTextField oWartoscTxt;
	private JTextField odBlokuTxt;
	private JTextField doBlokuTxt;
	
	//buttons
	private JButton oblicz;
	private JButton cancel;
	
	//checkBox
	private JCheckBox calyDokCheck;
	
	
	
	public Przesun(Edytor parent)
	{
		
		this.parent=parent;
		setTitle("Przesuniêce wspó³rzêdnych");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(350,400);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		
		
		//exit on close
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				
				cancel.doClick();
			
			}		
		});
		
		//layout
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints border = new GridBagConstraints();
		border.fill = GridBagConstraints.HORIZONTAL;
		border.weightx=0.2;
		border.weighty=0.2;
		
		
		//kierunek
		osLabel = new JLabel("Przesuñ w osi");
		border.gridx=0;
		border.gridy=0;
		add(osLabel,border);
		
		//combo
		osCombo = new JComboBox<String>(axisOptions);
		border.gridx=1;
		add(osCombo,border);
		
		//oWartosc
		oWartoscLabel = new JLabel("Wartosc przesuniecia:");
		border.gridx=0;
		border.gridy=1;
		add(oWartoscLabel,border);
		
		//oWartoscTxt
		oWartoscTxt= new JTextField("");
		oWartoscTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=1;
		add(oWartoscTxt,border);
		
		//caly dokument checkBox Label
		calyDokLabel = new JLabel("Caly dokument");
		border.gridx=0;
		border.gridy=2;
		add(calyDokLabel,border);
		
		//caly dokument checkBox 
		calyDokCheck = new JCheckBox();
		calyDokCheck.setSelected(false);
		calyDokCheck.addActionListener(this);
		border.gridx=1;
		border.gridy=2;
		add(calyDokCheck,border);
		
		//odBloku
		odBloku= new JLabel("Od Bloku:");
		border.gridx=0;
		border.gridy=3;
		add(odBloku,border);
		
		//odBlokuTxt
		odBlokuTxt = new JTextField("");
		odBlokuTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=3;
		add(odBlokuTxt,border);
		
		//doBloku
		doBloku= new JLabel("Do bloku");
		border.gridx=0;
		border.gridy=4;
		add(doBloku,border);
		
		//doBlokuTxt
		doBlokuTxt= new JTextField("");
		doBlokuTxt.setSize(75,150);
		border.gridx=1;
		border.gridy=4;
		add(doBlokuTxt,border);
		
		//oblicz
		oblicz = new JButton("Przesuñ");
		oblicz.addActionListener(this);
		border.gridx=0;
		border.gridy=5;
		add(oblicz,border);
		
		//cofnij
		cancel = new JButton("Confij");
		cancel.addActionListener(this);
		border.gridx=1;
		border.gridy=5;
		add(cancel,border);
	}

	
	private int getStartingBlock()
	{
		int result=0;
		
		String s = this.odBlokuTxt.getText();
		if(s.length()>0){
			if(Character.toUpperCase(s.charAt(0))=='N')
				s=s.substring(1);
		
			try
			{
				result = Integer.parseInt(s);
			
			}
			catch (NumberFormatException w)
			{
				return -1;
			
			}
		}
		return result;
	}
	
	private int getEndBlock()
	{
		int result=0;
		
		String s = this.doBlokuTxt.getText();
		if(s.length()>0){
		if(Character.toUpperCase(s.charAt(0))=='N')
			s=s.substring(1);
		
		try
		{
			result = Integer.parseInt(s);
			
		}
		catch (NumberFormatException w)
		{
			return -1;
			
		}
		}
		return result;
	}
	

	private Float getAddingValue()
	{
		Float result = null;
		String s = this.oWartoscTxt.getText();
		
		try
		{
			result = Float.parseFloat(s);
	
		}
		
		catch(NumberFormatException e)
		{
			return null;
		}
		
		return result;

	}

	
	private Function addValueToPoint(Function f,Float value,char axis)
	{
		Point result = f.getPoint();
		
		if(result != null)
		{
			if(Character.toUpperCase(axis)=='X')
			{
				if(result.getX()!=null)
					result.addX(value);
				
			}
			else if(Character.toUpperCase(axis)=='Y')
			{
				if(result.getY()!=null)
					result.addY(value);
			}
			else if(Character.toUpperCase(axis)=='Z')
			{
				if(result.getZ()!=null)
					result.addZ(value);
							
			}
			
			
			f.setPoint(result);
		}
		
		
		
		return f; 
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o=arg0.getSource();
		
		if(o==calyDokCheck)
		{
			if(calyDokCheck.isSelected())
			{
				odBlokuTxt.setEditable(false);
				doBlokuTxt.setEditable(false);
			}
			else
			{
				odBlokuTxt.setEditable(true);
				doBlokuTxt.setEditable(true);
			}
			
		}
		else if(o==oblicz)
		{
			Float value = getAddingValue();
			int start;
			int end;
			parent.analyze();
			
			if(calyDokCheck.isSelected())
			{
				start =0;
				end = Integer.MAX_VALUE;
						
				
			}
			else 
			{
				start = getStartingBlock();
				end = getEndBlock();
			}	
			
				
			if(start != -1 && end != -1 && value != null)
			{
						
				
				Function f;
				char axis =  ((String)this.osCombo.getSelectedItem()).charAt(0);
				
				for(int i=0;i<parent.getListLength();i++)
				{
					f = parent.getfunction(i);
					if(f.getBlock()<start)
					{
						continue;
					}
					else if(f.getBlock()>end)
					{
						break;
					}
					else if(f.containsFunction(4))
						continue;
					else
					{
						if(f.getPoint() !=null)
						{
							
							f = addValueToPoint(f, value, axis);
							parent.repleaceFunction(f, i);
							
							if(axis == 'Z')
							{
								
									if(f.getRcycleParam()!=null)
									{
										f.addValueToRArray(value);
									}

							}
						}
												
					parent.txt.getTxtArea().setText("");
					parent.write();
						
					}
				}
			
				String wJakichBlokachKomentarz = (end ==Integer.MAX_VALUE) ? "We wszystkich blokach": ("W blokach " + start + " do "+ end);
					
				parent.writelog("Przesunieto obrobkê w osi " + this.osCombo.getSelectedItem() + " o wartosc " + this.getAddingValue() + "\n\t"+wJakichBlokachKomentarz);
				Wind.log.writeInfoLog("Przesun - wykonano", Przesun.class.getSimpleName());
			}
			else
			{
				JOptionPane.showMessageDialog(parent, "Podano zle dane", "Blad", JOptionPane.NO_OPTION);
			}
		}
		else if(o==cancel)
		{
			parent.p=null;
			this.dispose();
	
		}	
	}
}
