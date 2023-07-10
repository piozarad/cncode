package CordConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class txtArea extends OutputStream {

	private JTextArea txt;
	private int caretLinePosition;
	
	public txtArea(JTextArea txt)
	{
		this.txt=txt;
		this.caretLinePosition=0;

	}
	
	//getters
	public JTextArea getTxtArea()
	{
		return this.txt;
	}
	
	public int getCaretLinePosition()
	{
		return this.caretLinePosition;

	}
	
	//setters
	
	@Override
	public void write(int b) throws IOException {
		txt.append(String.valueOf((char)b));
		txt.setCaretPosition(txt.getDocument().getLength());
	}

	public void addInterpreterListener(MouseListener listener)
	{
		txt.addMouseListener(listener);
	}

	public void updateCaretLinePosition() 
	{
		
		try {
			this.caretLinePosition = txt.getLineOfOffset(txt.getCaretPosition());
		} catch (BadLocationException e) {
			Wind.log.writeErrorLog("upadate CaretPosition error", e, txtArea.class.getSimpleName());
		}
		
	}

}
