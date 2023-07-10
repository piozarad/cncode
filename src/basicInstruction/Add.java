package basicInstruction;

import CordConverter.FunctionAnalyzeUtilities;

public class Add extends Instruction{

	private static final String MARK = "+";

	
	public Add(String trigger,String addition)
	{
		super(trigger,addition);
	}
	
	@Override
	public String applyChanges(String line) {
		updateToolNumber(line);
		
		line =FunctionAnalyzeUtilities.separateFunctions(line);	
		
		return  isTriggered(line) ? line + super.getChangeTo() : line;
	}

	@Override
	public boolean hasmark(String line) {
		return line.contains(MARK);
	}

	@Override
	public String returnMark() {
		return MARK;
	}
	
	@Override
	public String toString()
	{
		return "Kiedy " + super.getTrigger()+ "dodaj " + super.getChangeTo() ;
	}

	

}
