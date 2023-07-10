package CordConverter;

import java.awt.Dimension;

import javax.swing.JTextArea;

public class FunctionWindow extends JTextArea implements WritableMessage {

	
	private static final Dimension DEFAULT_WINDOW_DIMENSION = new Dimension(125,25);
	
	
	
	
	
	
	 FunctionWindow()
	{
		setLineWrap(true);
		setSize(DEFAULT_WINDOW_DIMENSION);
		setEditable(false);
	}
	
	
	
	
	
	@Override
	public void writeMessage(String text) {
	
		this.setText(text);
	}

}
