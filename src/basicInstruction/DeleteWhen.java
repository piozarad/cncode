package basicInstruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionAnalyzeUtilities;

public class DeleteWhen extends Instruction {
	
	private static final String MARK ="/";

	public DeleteWhen(String trigger, String changeTo) {
		super(trigger, changeTo, "");
		
	}

	@Override
	public boolean hasmark(String line) {
		return line.contains(MARK );
	}

	@Override
	public String returnMark() {
		return MARK;
	}

	@Override
	public String applyChanges(String line) {
		updateToolNumber(line);
		line =FunctionAnalyzeUtilities.separateFunctions(line);	

		if(line.contains(getTrigger()))
		{
			if(getChangeTo().contains("%l"))
			{
				String regex = getChangeTo().substring(0,getChangeTo().indexOf('%'))+"\\d+\\.?\\d{0,}";
				
				return line.replaceAll(regex, "");
			}
			else return line.replace(getChangeTo(), "");
		}
		
		else if(getTrigger().contains("%l"))
		{
			
			String regex = getTrigger().substring(0,getTrigger().indexOf('%'))+"\\d+\\.?\\d{0,}";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(line);
			
			if(m.find())
			{
				line=line.replaceAll(getChangeTo(),"");
			}
			return line;
		}
		else return line;
	}
	
	@Override
	public String toString()
	{
		return "Kasuj " + super.getChangeTo() + " kiedy w bloku znajduje siê " + super.getTrigger();
	}


}
