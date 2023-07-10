package CordConverter;

import java.util.ArrayList;
import java.util.List;

public class ToolListModel {

	private List<Narzedzie> toolList;
	private Edytor parent;
	
	
	
	
	public ToolListModel(Edytor parent)
	{
		this.toolList= new ArrayList<>();
		this.parent=parent;
			
	}

	public void addElementToList(Narzedzie element)
	{
		this.toolList.add(element);
	}
	
	
	/**
	 * 
	 * @return retrun actual list of detected tools
	 */

	public Narzedzie[] getToolList()
	{
		return this.toolList.toArray(new Narzedzie[0]);
		
	}
	
	public void updateToolWorkTime()
	{
		int start=0;
	
		for(Narzedzie n : this.toolList)
		{
		
			n.setWorkTime(CzasCyklu.countCycleTime(parent,start,  n.getEndingLine()-start));
			start = n.getEndingLine();			
		}
	}
	
	
	
	public void searchForTools(List<Function> functionList)
	{
		
		Narzedzie n = new Narzedzie();
		
		//wyczysc biezaca liste narzedzi przed aktualizacja
		this.toolList.clear();
	
		for(int i=0;i<functionList.size();i++)
		{
			if(i==functionList.size()-1 || n.updateToolInfo(functionList.get(i)))
			{
				n.setEndingLine(i);
				this.toolList.add(n);
	
				n= new Narzedzie();
				
				
				n.updateToolNumber(functionList.get(i));
				n.setToolBlockNumber(i);
				
				for(int index =i;index> Math.max(0, i-3);index--)
				{
					n.updateToolName(functionList.get(index));
				}			
			}
		}	
		
		updateToolWorkTime();
	}

}
