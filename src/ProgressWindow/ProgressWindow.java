package ProgressWindow;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import CordConverter.Wind;
import ErrorWindow.ErrorDatabase;



public class ProgressWindow extends JDialog {

	@Override
	public void dispose() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
	
			e.printStackTrace();
		}
		this.value=0;
		this.setVisible(false);
	}
	
	
	//JLabels
	JLabel progressLabel;
	
	
	//progressbar
	JProgressBar progressBar;
	
	//JButton
	JButton cancel;
	
	private int value;

	private int maxValue;
	private ErrorDatabase errorDatabase;

	public ProgressWindow(Wind parent, ErrorDatabase errorDatabase)
	{
		
		super(parent,"Przetwarzanie...",ModalityType.APPLICATION_MODAL);
		this.errorDatabase=errorDatabase;
		this.value=0;
		this.maxValue=0;
		setSize(250,75);
		setLocation(600, 300);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setAlwaysOnTop(true);
		

		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		
		progressLabel = new JLabel("Stan:");
		add(progressLabel);
		
		progressBar = new JProgressBar(0,100);
		progressBar.setStringPainted(true);
		add(progressBar);
		
		cancel = new JButton("Cofnij");
		add(cancel);
		
		pack();

	}
	
	
	@Override
	public void setVisible(final boolean b) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ProgressWindow.super.setVisible(b);
			}});
	}


	public void updateBar(int value) 
	{
		this.value=value;
		
		if(this.value<maxValue)
		{
			progressBar.setValue(this.value);
		}
		else progressBar.setValue(100);
					
	}
	public void incrementBar(int incrementValue) 
	{
		ProgressWindow.this.value+=incrementValue;
		
		float temp = (float)value/maxValue*100;
		
		if(value<maxValue)
		{
			progressBar.setValue((int)temp);
		}
		else progressBar.setValue(100);
					
	}
	
	public void setMaxValue(int maxValue)
	{
		this.maxValue=maxValue;
	}
	
	
	public ErrorDatabase getErrorDatabase()
	{
		return this.errorDatabase;
	}
	
	
	


}
