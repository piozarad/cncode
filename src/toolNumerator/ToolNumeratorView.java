package toolNumerator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import CordConverter.Edytor;

public class ToolNumeratorView extends JFrame {
	
	private Edytor parent;
	DefaultListModel<Integer> model;
	private JList<Integer> toolNumbersList;
	private JTextField txt;
	private JButton add;
	private JButton remove;
	private JLabel info;
	private JButton apply;
	private JButton cancel;
	private JButton up;
	private JButton down;
	private JButton removeAll;
	private JButton options;
	

	private boolean h=true;
	private boolean d=true;
	
	private GridBagLayout layout;
	
	private static final Dimension BUTTON_DIMENSION= new Dimension(100,25);
	
	public ToolNumeratorView(Edytor parent)
	{
		this.parent=parent;
		
		//layout
		layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints(); 
		constraints.insets = new Insets(5,5,5,5);
		
		
		
		
		//Frame
		setVisible(true);
		setPreferredSize(new Dimension(400,400));
		setTitle("Definicja numerow narzedzi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation(400,200);
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
		
		
		constraints.weightx=0;
		constraints.weighty=0.;
		constraints.fill=GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridwidth = 4;
		info = new JLabel("<html>Podaj wolne numery narzêdzi w takiej kolejnosci <br> w jakiej chcesz zeby wystepowaly w programie nastepnie wcisnij ok </html>");
		add(info,constraints);
		
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		txt = new JTextField();
		txt.setMinimumSize(new Dimension(25,25));
		add(txt,constraints);
		
		constraints.gridx=1;
		constraints.gridwidth=1;
		constraints.gridheight=1;
		add= new JButton(">>");
		add.setToolTipText("Dodaj narzedzie do listy");
		add.setMinimumSize(BUTTON_DIMENSION);
		add.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						try
						{
							int i = Integer.parseInt(ToolNumeratorView.this.txt.getText());
							model.addElement(i);
							txt.setText("");
							
							
						}
						catch(NumberFormatException ex)
						{
							
							JOptionPane.showMessageDialog(parent, "Wprowadzona wartosc musi byc liczba","Blad formatu danych",JOptionPane.ERROR_MESSAGE);
						}
						
					}
				
			
				}
		);
		
		add(add,constraints);
		
		constraints.gridy=2;
		remove = new JButton("<<");
		remove.setToolTipText("Usun narzedzie z listy");
		remove.setMinimumSize(BUTTON_DIMENSION);
		remove.addActionListener(e->{
			if(!this.toolNumbersList.isSelectionEmpty()) {
				this.model.remove(toolNumbersList.getSelectedIndex());
			}
		});
		add(remove,constraints);
		
		
		constraints.gridy=3;
		up = new JButton("do gory");
		up.setToolTipText("Przesun zaznaczene pole do gory");
		up.setMinimumSize(BUTTON_DIMENSION);
		up.addActionListener(e->
		{
			if(model.getSize()>1 && toolNumbersList.getSelectedIndex()>0) 
			{
				int number = toolNumbersList.getSelectedValue();
				int index = toolNumbersList.getSelectedIndex();
				model.remove(index);
				model.add(index-1, number);
				toolNumbersList.setSelectedIndex(index-1);
			}
		}
		);
		add(up,constraints);
		
		constraints.gridy=4;
		down = new JButton("w dol");
		down.setMinimumSize(BUTTON_DIMENSION);
		down.setToolTipText("Przesun zaznaczene pole w dol");
		down.addActionListener(e->{
			if(model.getSize()>1 && toolNumbersList.getSelectedIndex()<model.getSize()-1) 
			{
				int number = toolNumbersList.getSelectedValue();
				int index = toolNumbersList.getSelectedIndex();
				model.remove(index);
				model.add(index+1, number);
				toolNumbersList.setSelectedIndex(index+1);
			}
		});
		add(down,constraints);
		
		removeAll = new JButton("Czysc");
		constraints.gridy=5;
		removeAll.setMinimumSize(BUTTON_DIMENSION);
		removeAll.setToolTipText("Usun wszytkie elementy z listy");
		removeAll.addActionListener(e->model.clear());
		add(removeAll,constraints);
		
		options = new JButton("Opcje");
		constraints.gridy=6;
		options.setMinimumSize(BUTTON_DIMENSION);
		options.setToolTipText("Ustawienia numeracji korektorow");
		options.addActionListener(e->{
			new Options(this);
			
			
		});
		add(options,constraints);
		
		
		constraints.gridx=2;
		constraints.gridy=1;
		constraints.gridwidth=1;
		constraints.gridheight=7;
		model = new DefaultListModel<>();
		toolNumbersList = new JList<>(model);
		toolNumbersList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Narzedzia"));
		toolNumbersList.setMinimumSize(new Dimension(50,200));
		JScrollPane listScrollPane = new JScrollPane(toolNumbersList);
		add(listScrollPane,constraints);
		
		constraints.gridx=1;
		constraints.gridy=8;
		apply = new JButton("Ok");
		apply.setMinimumSize(BUTTON_DIMENSION);
		apply.addActionListener(e->{
			
			
			List<Integer> l = new ArrayList<>();
			//get List
			
			for(int j=0; j< model.getSize(); j++)
			{
				l.add(model.get(j));
				
			}

			
			int i=0;
			List<String> functionList = new ArrayList<>();
 			for(String s: parent.getTextAsList())
			{
				
				if(s.matches("^[^\\(]*[TDH]\\d+[^\\)]*$") && i<= l.size())
				{
					if(i< l.size())
					{
						s=s.replaceAll("T\\d++", "T" + l.get(i));
						if(s.contains("M6")) i++; 
					}
					
					if(this.h) s=s.replaceAll("H\\d++", "H" + l.get((i-1)<0 ? 0 : i-1));
					if(this.d) s=s.replaceAll("D\\d++", "D" + l.get((i-1)<0 ? 0 : i-1));
					
				}
				
				functionList.add(s);
			}
			
 			System.out.println(functionList);
 			
 			//write
 			
 				PrintStream stream = new PrintStream(parent.txt);
 				System.setOut(stream);
 				parent.txt.getTxtArea().setText("");
 				for(String s: functionList)
 				{
 					System.out.print(s + "\n");
 				}
 				
 				
 				System.setOut(System.out);
 				stream.close();
 				model.clear();
 				this.setVisible(false);
 				
			
		});
		add(apply,constraints);
		
		constraints.gridx=2;
		constraints.gridy=8;
		cancel = new JButton("Cofnij");
		cancel.setMinimumSize(BUTTON_DIMENSION);
		cancel.addActionListener(e->this.setVisible(false));
		add(cancel,constraints);
		
		
		pack();
	}
	
	
	
	
	class Options extends JFrame
	{
		ToolNumeratorView parent;
		JLabel dLabel;
		JLabel hLabel;
		JCheckBox d;
		JCheckBox h;
		JButton ok;
		Options(ToolNumeratorView parent)
		{
			this.parent=parent;
			setSize(150,150);
			setVisible(true);
			setLocation(600,350);
			setResizable(false);
			FlowLayout optionsLayout = new FlowLayout();
			setLayout(optionsLayout);
			
			dLabel = new JLabel("Korektor D");
			add(dLabel);
			
			d = new JCheckBox();
			d.setSelected(parent.d);
			add(d);
			
			
			
			hLabel = new JLabel("Korektor H");
			add(hLabel);
			
			h= new JCheckBox();
			h.setSelected(parent.h);
			add(h);
			
			ok = new JButton("OK");
			ok.addActionListener(e->{
				parent.h=h.isSelected();
				parent.d=d.isSelected();
				Options.this.dispose();
			});
			add(ok);
		}
	}
	
	
}
