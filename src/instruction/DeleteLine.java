package instruction;

import CordConverter.FunctionUtilities;

public class DeleteLine extends Instruction{

	public static final String MARK = "KL>";
	public DeleteLine(String trigger) {
		super(trigger);
	
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
	public String applyChanges(String line) {
		updateToolNumber(line);
		line =FunctionUtilities.separateFunctions(line);	
		
		if(getTrigger().contains("%l"))
		{
			String temp = getTrigger();
			temp = temp.replace(" ","");
			if(line.contains(temp.substring(0, temp.indexOf('%'))))
				return "";
			else return line;
		}
		else if(line.contains(getTrigger()))
		{
			return "";
		}
		else return line;
	}

	
	@Override
	public String toString()
	{
		return "Kasuj blok z " + super.getTrigger();
	}
}
