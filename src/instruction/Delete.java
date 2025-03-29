package instruction;

import CordConverter.FunctionUtilities;

public class Delete extends Instruction {

	private static final String MARK = "/";
	
	
	public Delete(String trigger)
	{
		super(trigger);
	}
	
	@Override
	public String applyChanges(String line) {
		updateToolNumber(line);
		line =FunctionUtilities.separateFunctions(line);	
		
		if(getTrigger().contains("%l"))
		{
			
			String regex = getTrigger().substring(0,getTrigger().indexOf('%'))+"\\d+\\.?\\d{0,}";
			return line.replaceAll(regex, "");
		}
		else return line.replace(getTrigger(), "");
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
		return "Kasuj " + super.getTrigger() ;
	}

}
