package CordConverter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class Zamien extends JFrame implements ActionListener{

	//parent
	private Edytor parent;
	
	
	//labels
	private JLabel pierwotnyLabel;
	private JLabel zmienionyLabel;
	private JLabel calyDokumentLabel;
	private JLabel odBloku;
	private JLabel doBloku;
	
	
	//checkBox
	private JCheckBox calyDokumentBox;
	
	
	//txt fields
	private JTextField pierwotnyTxt;
	private JTextField zmienionyTxt;
	private JTextField odBlokuTxt;
	private JTextField doBlokuTxt;
	

	
	//buttons
	private JButton zamienButton;
	private JButton cofnijButton;
	
	
	//zmienne
	
	private String line;
	private int startingBlock;
	private int endBlock;
	private String changeFrom;
	private String changeTo;
	
	public Zamien(Edytor parent)
	{
		
		this.parent=parent;
		this.setLocationRelativeTo(null);
		setSize(250,320);
		setVisible(true);
		setTitle("Zamieñ");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		
		//exit on close
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				
				cofnijButton.doClick();
			
			}		
		});
		
		
		//layout
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(10,20,10,20);
		setLayout(layout);
		
		
		//labels 
		border.gridx=0;
		border.gridy=0;
		border.weightx= 0.1;
		border.weighty=0.1;
		border.fill=GridBagConstraints.BOTH;
		
		//z
		pierwotnyLabel = new JLabel("Zamieñ");
		add(pierwotnyLabel,border);
		
		//na
		border.gridy=1;
		zmienionyLabel = new JLabel("Na");
		add(zmienionyLabel,border);
		
		// caly dok
		border.gridy=2;
		calyDokumentLabel = new JLabel("Caly dokument");
		add(calyDokumentLabel,border);
		
		//od Bloku
		border.gridy=3;
		odBloku = new JLabel("Od bloku:");
		add(odBloku,border);
		
		//do bloku
		border.gridy=4;
		doBloku = new JLabel("Do bloku:");
		add(doBloku,border);
		
		
		
		// zamien
		border.gridy=5;
		zamienButton = new JButton("Zamieñ");
		zamienButton.addActionListener(this);
		add(zamienButton,border);
		
		
		
		
		//x=1
		
		//z txt
		border.gridx=1;
		border.gridy=0;
		pierwotnyTxt = new JTextField("");
		add(pierwotnyTxt,border);
		
		//na txt
		border.gridy=1;
		zmienionyTxt = new JTextField("");
		add(zmienionyTxt,border);
		
		//caly dok
		border.gridy=2;
		calyDokumentBox = new JCheckBox();
		calyDokumentBox.addActionListener(this);
		add(calyDokumentBox,border);
		
		//od Bloku
		border.gridy=3;
		odBlokuTxt = new JTextField("");
		add(odBlokuTxt,border);
		
		//do bloku
		border.gridy=4;
		doBlokuTxt = new JTextField("");
		add(doBlokuTxt,border);
		
		
		
		//cofnij
		border.gridy=5;
		cofnijButton = new JButton("Cofnij");
		cofnijButton.addActionListener(this);
		add(cofnijButton,border);
		
	}


	private boolean isOk()
	{
		this.changeFrom = this.pierwotnyTxt.getText();
		this.changeTo = this.zmienionyTxt.getText();
		
		
		if(calyDokumentBox.isSelected())
		{
			return true;
			
		}
		else
		{
			try
			{
				startingBlock = Integer.parseInt(odBlokuTxt.getText());
				
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this, "Zle zdefiniowany blok startowy, numer bloku powinien byæ liczb¹");
				return false;
			}
			
			try
			{
				endBlock = Integer.parseInt(doBlokuTxt.getText());
				
			}
			catch(NumberFormatException e)
			{
			JOptionPane.showMessageDialog(this, "Zle zdefiniowany blok koñcowy, numer bloku powinien byæ liczb¹");
			return false;	
			}
			
			return true;
			
		}
		
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		
		if(o==zamienButton)
		{
			if(isOk())
			{
				
				parent.analyze();
				int[] logInfo;
				
				
				if(this.calyDokumentBox.isSelected())
				{
					
					logInfo=parent.analyzeAndChange(this.changeFrom, this.changeTo);
					parent.write();
				}
			
				else 
				{
				
				
				logInfo=parent.analyzeAndChangeWithBlocks(this.changeFrom, this.changeTo,this.startingBlock,this.endBlock);
				parent.write();
				
				}
				
				
				parent.writelog("Zamieniono " + changeFrom + " na " + changeTo + "\n\tLiczba wyst¹pieñ: " + logInfo.length + "\n\tZmieniono w blokach: " + Arrays.toString(logInfo));
				Wind.log.writeInfoLog("Zamien - wykonano", Zamien.class.getSimpleName());
			}
		}
		else if(o==cofnijButton)
		{
			this.parent.z=null;
			this.dispose();
			
		}
		else if(o==calyDokumentBox)
		{
			if(calyDokumentBox.isSelected())
			{
				odBlokuTxt.setEnabled(false);
				doBlokuTxt.setEnabled(false);
				odBloku.setEnabled(false);
				doBloku.setEnabled(false);
				
			}
			else
			{
				odBlokuTxt.setEnabled(true);
				doBlokuTxt.setEnabled(true);
				odBloku.setEnabled(true);
				doBloku.setEnabled(true);
				
			}
		}
		
	}
	
}
