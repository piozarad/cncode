package CordConverter;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Wyszukaj extends JFrame {

	//buttons 
	private JButton znajdzNastepny;
	private JButton znajdzPoprzedni;
	private JButton cofnij;
	
	//label
	private JLabel szukajLabel;
	
	//JTextfield
	private JTextField szukajTxt;
	
	
	//stuff
	private final Wind parent;
	private static final Dimension BUTTONDIM = new Dimension(100,25);
	private static final Dimension TEXTDIM = new Dimension(100,25);
	
	
	// variables 
	private int actualCarretPosition=0;
	
	
	
	public Wyszukaj(Wind parent)
	{
		this.parent=parent;
		
		setTitle("Szukaj ci¹gu znaków");
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setPreferredSize(new Dimension(300,150));
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		
		this.addWindowListener( new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				parent.w.cofnij.doClick();
			
			}		
		}	
		);
		
		
		//layout
		GridBagConstraints border = new GridBagConstraints();
		border.insets = new Insets(5,5,5,5);
		 border.gridx=0;
		 border.gridy=0;
		 border.weightx=0.5;
		 border.weighty=0;
		 
		 
		 //szukaj od
		 znajdzPoprzedni = new JButton("Poprzedni");
		 znajdzPoprzedni.setPreferredSize(BUTTONDIM);
		 znajdzPoprzedni.addActionListener(new ActionListener()
				 {
			 			@Override
			 			public void actionPerformed(ActionEvent e)
			 			{
			 				if(getField()!=null)
			 				{
			 					String searchedValue = getField();

								 int[] result =findValueUp(searchedValue);
								 parent.panel.txt.getTxtArea().setCaretPosition(result[1]);
								 
								 parent.panel.txt.getTxtArea().moveCaretPosition(result[0]);
			 				}
			 			}

				 });
		 
		 add(znajdzPoprzedni,border);
				 
		 
		 //szukaj do
		 border.gridy=1;
		 znajdzNastepny = new JButton("Nastêpny");
		 znajdzNastepny.setPreferredSize(BUTTONDIM);
		 
		 znajdzNastepny.addActionListener(new ActionListener()
		 {
			@Override
			public void actionPerformed(ActionEvent e)
			 { 
				 if(getField()!=null)
				 {
					 
					 String searchedValue = getField();
					 
					 
					 
					 int[] result =findValueDown(searchedValue);
					 
					 
					 parent.panel.txt.getTxtArea().select(result[0], result[1]);
					 Wind.log.writeInfoLog("Wyszukaj - wykonano", Wyszukaj.class.getSimpleName());

					 
				 }
			 }
		 
		 });
		 
		 
		 add(znajdzNastepny,border);
		 
		 
		 //text file
		 border.gridx=1;
		 border.gridy=0;
		 szukajTxt = new JTextField("");
		 szukajTxt.setPreferredSize(TEXTDIM);
		 
		 add(szukajTxt,border);
		 
 
		 //cofnij
		 border.gridx=1;
		 border.gridy=1;			 
		 cofnij = new JButton("Powrót");
		 cofnij.setPreferredSize(BUTTONDIM);
		 
		 cofnij.addActionListener(new ActionListener()
				 {
			 	@Override
			 	public void actionPerformed(ActionEvent e)
			 	{	
			 		parent.w.dispose();
			 		parent.w=null;
			 		
			 		
			 	}
	
			 });
		add(cofnij,border);
		pack();
	
	}
	
	private int[] findValueDown(String searched)
			{
				int caretPosition = parent.panel.txt.getTxtArea().getCaretPosition(); 
				String text =parent.panel.txt.getTxtArea().getText().substring(caretPosition);
				
				Pattern p = Pattern.compile(searched);
				Matcher m = p.matcher(text);
					
				int[] result = new int[2];
				
				if(m.find())
				{
					result[0]=m.start()+caretPosition;
					result[1]=m.end()+caretPosition;
				}
				return result;
			}
	
	
	private int[] findValueUp(String searched)
	{
		int caretPosition = parent.panel.txt.getTxtArea().getCaretPosition(); 
		String text =parent.panel.txt.getTxtArea().getText().substring(0,caretPosition);
		
		
		StringBuilder search = new StringBuilder(searched);
		StringBuilder t = new StringBuilder(text);
		search.reverse();
		t.reverse();
		
		Pattern p = Pattern.compile(search.toString());
		Matcher m = p.matcher(t.toString());
			
		int result[] = new int[2];
		
		if(m.find())
		{
			result[0]=caretPosition-m.end();
			result[1]=caretPosition-m.start();
			
		}
		
		return result;

	}
	
	
	
	private String getField()
	{
		
		String temp = this.szukajTxt.getText();
		
		if(temp.length()>0)
			return temp;
		else return null;
	}
	
}
