package ErrorWindow;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.junit.rules.TemporaryFolder;

import CordConverter.Edytor;
import CordConverter.FunctionAnalyzeUtilities;
import CordConverter.NcState;
import CordConverter.Wind;
import InvalidFunctionsError.InvalidFunctionError;
import NcStateErrors.BadNcState;
import NcStateWarnings.NcStateWarnings;
import ProgressWindow.ProgressWindow;
import syntaxError.SyntaxError;


public class ErrorProgressWindow extends JDialog {
	
	//labels
	JLabel syntaxErrorLabel;
	JLabel syntaxError;
	JLabel notValidFunctionLabel;
	JLabel notValidFunction;
	JLabel ncStateLabel;
	JLabel ncState;
	JLabel progressLabel;
	JLabel ncStateWarningsLabel;
	JLabel ncStateWarnings;
	
	//txt field
	JList<ProgramError> list;
	DefaultListModel<ProgramError> listModel;
	
	JScrollPane scrollPane;
	
	//progress bar
	JProgressBar progressbar;
	
	//Jbuttons
	JButton okButton;
	JButton search;
	
	//insets
	Insets normalInsets = new Insets(10,10,10,10);
	Insets tightInsets = new Insets(10,2,10,2);
	
	//dimensions
	Dimension buttonSize = new Dimension(75,25);
	
	private Edytor parent;
	
	private List<ProgramError> programErrorList;
	
	private int syntaxErrorNumber;
	private int ncStateWarningsNumber;
	private int badNcStateNumber;
	private int invalidFunctionNumber;
	
	
	public ErrorProgressWindow(Edytor parent)
	{
		this.parent=parent;
		setPreferredSize(new Dimension(500,300));
		setTitle("Analiza kodu");
		setVisible(true);
		setResizable(false);
		setLocation(500, 250);
		setAlwaysOnTop(true);
		
		programErrorList = new ArrayList<>();
		
		//layout
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints border = new GridBagConstraints();
		border.insets = normalInsets;
			
		border.anchor = GridBagConstraints.WEST;
		border.fill = GridBagConstraints.BOTH;
		
		//first row
		border.gridx=0;
		border.gridy=0;
		border.weightx=0;
		border.weighty=0;
		syntaxErrorLabel = new JLabel("Sk³adnia");
		add(syntaxErrorLabel,border);
		
		border.gridx=1;
		border.gridy=0;
		syntaxError= new JLabel("0");
		add(syntaxError,border);
		
		//secondRow
		border.gridx=0;
		border.gridy=1;
		notValidFunctionLabel = new JLabel("B³edny G kod");
		add(notValidFunctionLabel,border);
		
		border.gridx=1;
		notValidFunction = new JLabel("0");
		add(notValidFunction,border);
		
		//third row
		border.gridx=0;
		border.gridy=2;
		ncStateLabel = new JLabel("stan Nc");
		add(ncStateLabel,border);
		
		border.gridx=1;
		ncState = new JLabel("0");
		add(ncState,border);
		
		//fourth row
		border.gridx=0;
		border.gridy=3;
		ncStateWarningsLabel = new JLabel("Ostrze¿enia");
		add(ncStateWarningsLabel,border);
		
		border.gridx=1;
		ncStateWarnings = new JLabel("0");
		add(ncStateWarnings,border);
		
		//third collumn
		listModel= new DefaultListModel<>();
		list = new JList<>(listModel);
		scrollPane = new JScrollPane(list);
		scrollPane.setMinimumSize(new Dimension(250,250));
		border.gridx=2;
		border.gridy=0;
		border.weightx=1;
		border.weighty=1;
		border.gridheight=5;
		border.gridwidth=2;
		add(scrollPane,border);
		list.addListSelectionListener(selectionListener);
		
		okButton = new JButton("Ok");
		border.anchor=GridBagConstraints.CENTER;
		border.insets=normalInsets;
		border.gridx=2;
		border.gridy=6;
		border.weightx=0;
		border.weighty=0;
		border.fill=GridBagConstraints.NONE;
		okButton.setMinimumSize(buttonSize);
		add(okButton,border);
		
		search = new JButton("Szukaj");
		search.setToolTipText("Resetuj aktualnie znalezione b³êdy i analizuj kod od nowa");
		search.setMinimumSize(buttonSize);
		border.gridx=1;
		add(search,border);
		
		search.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						
						prepareWindowToSearch();
						ErrorProgressWindow.this.parent.searchForTools();
						ErrorProgressWindow.this.parent.analyze();
					if(ErrorProgressWindow.this.parent.getListLength()>1)
					{
						ProgressWindow progress = new ProgressWindow(ErrorProgressWindow.this.parent.getWind(), new ErrorDatabase(Wind.options));
						
						progress.setMaxValue(parent.getListLength()*4);

							SwingWorker<List<CordConverter.Error>,Integer> worker = new SwingWorker<List<CordConverter.Error>,Integer>() {
							List<CordConverter.Error> foundErrorList = new LinkedList<>();
							List<String> analyzedTextList =ErrorProgressWindow.this.parent.getTextAsList();
							
							@Override
							protected List<CordConverter.Error> doInBackground() throws Exception {	
								//syntax
								for(int i=0;i<parent.getListLength() ;i++)
								{
									for(SyntaxError err : progress.getErrorDatabase().getSyntaxErrorList())
									{

										if(err.checkError(FunctionAnalyzeUtilities.removeComment(analyzedTextList.get(i))))
												{
													syntaxErrorNumber++;
													ErrorProgressWindow.this.programErrorList.add(new ProgramError(i,err));
												}
									}
									
										Thread.sleep(10);
										publish(i);			
								}	
								
								//ncStatewarnings
								NcState state = new NcState(parent.getControls());
								List<NcStateWarnings> temporaryWarningsContainer = new LinkedList<>();
								List<NcStateWarnings> warningsList = new LinkedList<>();
								NcStateWarnings war;
								warningsList.addAll(progress.getErrorDatabase().getWarningsList());
								
								//use Iterator to be able to remove items while iterating through list
								ListIterator<NcStateWarnings> errorIterator;
								
								for(int i=0;i<parent.getListLength() ;i++)
								{
									//add all errors from temporary error container when tool change occurs
									if(parent.getfunction(i).getMFunctin()==6 && !temporaryWarningsContainer.isEmpty())
									{	
										warningsList.addAll(temporaryWarningsContainer);
										temporaryWarningsContainer.clear();
									}
								
									//update ncState so it can be analyzed
									state.updateNcState(parent.getfunction(i));
									
									 errorIterator= warningsList.listIterator();
									 
									while(errorIterator.hasNext())
									{
										war=errorIterator.next();
										if(war.checkNcState(state))
												{
													ncStateWarningsNumber++;
													ErrorProgressWindow.this.programErrorList.add(new ProgramError(i,war));
													//add error to temporary container and remove it from warningsList so it occurs only once per tool
													temporaryWarningsContainer.add(war);
													
													errorIterator.remove();
												}
									
									}
									
										Thread.sleep(10);
										publish(i);	
	
								}
								
								
								//badNcState
								state = new NcState(parent.getControls());
								List<BadNcState> temporaryBadNcStateContainer = new LinkedList<>();
								List<BadNcState> badNcStateList = new LinkedList<>();
								BadNcState foundNcStateError;
								badNcStateList.addAll(progress.getErrorDatabase().getNcStateErrorsList());
								
								//use Iterator to be able to remove items while iterating through list
								ListIterator<BadNcState> badNcIterator;
								for(int i=0;i<parent.getFunctionSize();i++)
								{	
									
									//add all errors from temporary error container when tool change occurs
									if(parent.getfunction(i).getMFunctin()==6 && !temporaryBadNcStateContainer.isEmpty())
									{
										
										badNcStateList.addAll(temporaryBadNcStateContainer);
										temporaryBadNcStateContainer.clear();
									}
									
									//update ncState so it can be analyzed
									state.updateNcState(parent.getfunction(i));
			
									badNcIterator= badNcStateList.listIterator();
								
									while(badNcIterator.hasNext())
									{
										foundNcStateError=badNcIterator.next();
										if(foundNcStateError.findError(state))
												{
											badNcStateNumber++;
											ErrorProgressWindow.this.programErrorList.add(new ProgramError(i,foundNcStateError));
													//add error to temporary container and remove it from warningsList so it occurs only once per tool
													temporaryBadNcStateContainer.add(foundNcStateError);
													
													badNcIterator.remove();
												}
									
									}
								
									
									
									
//									for(BadNcState badState : progress.getErrorDatabase().getNcStateErrorsList())
//									{
//										if(badState.findError(state))
//										{
//											
//										}
//										
//									}
									
										Thread.sleep(10);
										publish(i);			
								}
								
								//invalid function						
								for(int i=0;i<parent.getFunctionSize();i++)
								{								

									for(InvalidFunctionError invalidFunction : progress.getErrorDatabase().getInvalidFunctionErrorList())
									{
										if(invalidFunction.findError(parent.getfunction(i)))
										{
											invalidFunctionNumber++;
											ErrorProgressWindow.this.programErrorList.add(new ProgramError(i,invalidFunction));
										}
										
									}
									
										Thread.sleep(10);
										publish(i);	
										
										
								}
								
								

								return foundErrorList;
							}
 
							@Override
							protected void process(List<Integer> chunks) {
								
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
										progress.incrementBar(chunks.size());
							}
							
							@Override
							protected void done()
							{
								progress.dispose();
								endSearch();
							}
						};
						worker.execute();
					}
					}
				});
		
		
		okButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						ErrorProgressWindow.this.dispose();	
					}
				});
		
		pack();
		
	}
	
	private void endSearch()
	{
		ErrorProgressWindow.this.listModel.addAll(programErrorList);
		ErrorProgressWindow.this.list.setModel(listModel);
		ErrorProgressWindow.this.syntaxError.setText(Integer.toString(syntaxErrorNumber));
		ErrorProgressWindow.this.ncStateWarnings.setText(Integer.toString(ncStateWarningsNumber));
		ErrorProgressWindow.this.ncState.setText(Integer.toString(badNcStateNumber));
		ErrorProgressWindow.this.notValidFunction.setText(Integer.toString(invalidFunctionNumber));
		ErrorProgressWindow.this.list.addListSelectionListener(selectionListener);
		
	}
	
	private void prepareWindowToSearch()
	{
		ErrorProgressWindow.this.programErrorList.clear();
		ErrorProgressWindow.this.list.removeListSelectionListener(selectionListener);
		ErrorProgressWindow.this.list.clearSelection();
		ErrorProgressWindow.this.resetErrorCount();
		ErrorProgressWindow.this.listModel.clear();
	}
	
	
	
	@Override
	public void dispose()
	{
		setModal(false);
		this.setVisible(false);
		
	}
	private void resetErrorCount()
	{
		this.syntaxErrorNumber =0;
		this.ncStateWarningsNumber=0;
		this.invalidFunctionNumber=0;
		this.badNcStateNumber=0;
	}
	
	private ListSelectionListener selectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ErrorProgressWindow.this.parent.jumpToLine(list.getSelectedValue().getLineNumber());
			}
	};
	
	
	
}
