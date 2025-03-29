package instruction;

import CordConverter.FunctionUtilities;

public class AddUntil extends Instruction {

private static final String MARK = "vv";
private boolean isTriggered = false;

	
	public AddUntil(String trigger, String changeFrom, String changeTo)
	{
		super(trigger,changeTo,changeFrom);
		

		
	}
	
	@Override
	public String applyChanges(String line) {
		updateToolNumber(line);
		
		line =FunctionUtilities.separateFunctions(line);	
			
		
		if(line.contains(getChangeFrom())) this.isTriggered=false;
		
		if(isTriggered)
		{
			return super.getChangeTo() +" "+ line;
	
		}
		
		if(line.contains(getTrigger())) this.isTriggered=true;
		
		
		return line;
		
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
		return "Dodawaj " + super.getChangeTo() + " kiedy spotkasz " + super.getTrigger() + " a¿ do " + super.getChangeFrom()  ;
	}
	
}
