package CordConverter;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;

public class ToolListView extends JList<Narzedzie> {

	private DefaultListModel<Narzedzie> listModel; 
	
	
	public ToolListView()
	{
		listModel = new DefaultListModel<>();
		this.setModel(listModel);

	}

	/**
	 * 
	 * @param s added item t this combobox
	 */
	public void add(Narzedzie n)
	{
		this.listModel.addElement(n);	
	}
	
	/**
	 * @param s array of Strings which will be added to this JList
	 */
	public void addAll(Narzedzie[] n)
	{
		for(Narzedzie tool:n)
		{
			
			this.listModel.addElement(tool);
		}
		 
	}
	
	/**
	 *  removes all elements from this jList
	 */
	public void clearList()
	{
		this.listModel.clear();
	}
	
	
	public void addListener(ListSelectionListener al)
	{
		this.addListSelectionListener(al);
		
	}

	

	
	
	
	
	
	
}
