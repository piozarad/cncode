package CordConverter;

import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ToolListController{

	
	
	private ToolListView view;
	private ToolListModel model;
	private ListSelectionListener listSelectionListener;
	
	
	
	public ToolListController(WritableMessage outputText, ToolListView view, ToolListModel model,Edytor parent)
	{
		
		this.model=model;
		this.view=view;
		
		this.listSelectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				outputText.writeMessage(view.getSelectedValue().display());
				parent.jumpToBlock(view.getSelectedValue().getToolBlock());
	
			}
		};
		view.addListener(this.listSelectionListener);
	
	}
	public void orderToolSearch(List<Function> functionList)
	{
		model.searchForTools(functionList);

		//update List
		view.removeListSelectionListener(this.listSelectionListener);
		view.clearList();
		view.addAll(model.getToolList());
		view.addListSelectionListener(this.listSelectionListener);
	}

	
	public String writeLogInfo() {
		return view.getSelectedValue().display();
	}
	
	
	
	
}
