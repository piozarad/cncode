package instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionAnalyzeUtilities;

public class SeparateFunction extends Instruction{

	
	private static final String MARK = "<>";
	
	public SeparateFunction(String trigger) {
		super(trigger.replace(" ", ""));
		
	}

	@Override
	public boolean hasmark(String line) {
		return line.contains("<>");
	}

	@Override
	public String returnMark() {
		return MARK;
	}

	@Override
	public String applyChanges(String line) {
		
		updateToolNumber(line);
		line =FunctionAnalyzeUtilities.separateFunctions(line);	
		String trigger = getTrigger() ;
		
		if(trigger.contains("%l") )
		{
			trigger=trigger.substring(0, trigger.indexOf('%'));
			
			Pattern p = Pattern.compile(trigger + "\\d+\\.?\\d{0,} ");
			Matcher m = p.matcher(line);
			
			if(m.find())
			{
				return  line.substring(m.start(),m.end()) + "\n" +  line.substring(0, m.start()) + line.substring(m.end(), line.length());
			}
			else return line;
		}
		else
		{
			if(trigger.contains("%t")) {
				trigger = trigger.replace("%t", Integer.toString(super.getToolNumber()));
			}
			
			if(line.contains(trigger))
			{
				return  trigger + "\n" + line.replace(trigger, "");
			}
			else return line;
		}
	}

	@Override
	public String toString() {
		return "Wyodrêbnij " + getTrigger();
	}

}
