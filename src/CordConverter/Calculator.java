package CordConverter;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Calculator extends JFrame{

	
	
	
	
	public Calculator(JDialog content)
	{
		
		
		
		setSize(400,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		add(content);
		setTitle(content.toString());
		revalidate();
		
	}
}
