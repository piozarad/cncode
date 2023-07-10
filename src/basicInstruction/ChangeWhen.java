package basicInstruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionAnalyzeUtilities;

public class ChangeWhen extends Instruction {

	public static final String MARK = "?=>";
	
	public ChangeWhen(String trigger, String changeTo, String changeFrom) {
		super(trigger, changeTo, changeFrom);
		
	}

	@Override
	public boolean hasmark(String line) {
		return line.contains("?");
	}

	@Override
	public String returnMark() {
		return MARK;
	}

	@Override
	public String applyChanges(String line) {
		updateToolNumber(line);
		line =FunctionAnalyzeUtilities.separateFunctions(line);	
		
		String trigger = getTrigger().replace(" ", "");
		String changeFrom = getChangeFrom().replace(" ", "");
		String changeTo = getChangeTo().replace(" ","");
		if(getTrigger().contains("%l") || getChangeFrom().contains("%l"))
		{
	
			
			if(line.contains(Character.toString(trigger.charAt(0))) && line.contains(Character.toString(changeFrom.charAt(0))))
			{
				Pattern p;
				Matcher m;
				
				if(trigger.contains("%"))
				{
					p=Pattern.compile(trigger.replace("%l", "\\d+\\.?\\d{0,}"));
					m=p.matcher(line);
							
							if(!m.find()) return line;
				}
				
				else 
					{
						if(!line.contains(trigger)) return line;
					}
				
				
				if(changeFrom.contains("%"))
				{
					p=Pattern.compile(changeFrom.replace("%l", "\\d+\\.?\\d{0,}"));
					m=p.matcher(line);
					
					if(m.find())
					{
						return line.replace(line.substring(m.start(),m.end()), changeTo);
					}
					else return line;
				}
				else if(line.contains(changeFrom)) return line.replace(changeFrom, changeTo); 
				else return line;
				
			}
			else return line;
			
		}
		else if(line.contains(getTrigger()) && line.contains(changeFrom))
		{
			return line.replace(changeFrom, changeTo);
		}
		else return line;
			
			
			
	}
	
	@Override
	public String toString()
	{
		return "Zamieñ " + super.getChangeFrom() + "na " + super.getChangeTo() +  " kiedy " + super.getTrigger();
	}

}
