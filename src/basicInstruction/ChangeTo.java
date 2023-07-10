package basicInstruction;

import CordConverter.FunctionAnalyzeUtilities;

public class ChangeTo extends Instruction {


	private static final String MARK = "=>";
	
	public ChangeTo(String trigger, String changeTo)
	{
		super(trigger,changeTo);
		
	}
	
	@Override
	public String applyChanges(String line) {
		updateToolNumber(line);
		
		line =FunctionAnalyzeUtilities.separateFunctions(line);	
			
		
		if(getTrigger().contains("%l"))
		{
			String regex = getTrigger().substring(0,getTrigger().indexOf('%'))+"\\d+\\.?\\d{0,} ";
			return line.replaceAll(regex, super.getChangeTo() + " ");
		}
		else return line.replace(getTrigger(), super.getChangeTo() + " ");
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
		return "Zamieñ " + super.getTrigger() + "na " + super.getChangeTo();
	}
	
}