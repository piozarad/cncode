package instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionUtilities;

public class SeparateWhen extends Instruction {

	private static final String MARK = "?<>";
	
	public SeparateWhen(String changeTo, String changeT) {
		super(changeTo, changeT);
		
	}

	@Override
	protected boolean isTriggered(String line) {
		String trigger = getTrigger();
		
		if(!trigger.contains("%"))
			return line.contains(trigger);
		else
		{
			if(trigger.contains("%t"))
			{
				trigger = trigger.replace("%t",Integer.toString(getToolNumber()));
				
				return line.contains(trigger);
			}
			else if (trigger.contains("%l"))
			{
				trigger = trigger.replace("%l","\\d+\\.?\\d{0,} ");
				Pattern p = Pattern.compile(trigger);
				Matcher m =p.matcher(line);
				
				return m.find();
			}
			else return false;

		}
	}




	@Override
	public boolean hasmark(String line) {
		return line.contains("<>")&& line.contains("?");
	}

	@Override
	public String returnMark() {
		return MARK;
	}


	@Override
	public String applyChanges(String line) {
		
		updateToolNumber(line);
		line =FunctionUtilities.separateFunctions(line);	
		String changeTo = getChangeTo() ;
		
		
		
		if(isTriggered(line))
		{
		if(changeTo.contains("%l") )
		{
			changeTo=changeTo.substring(0, changeTo.indexOf('%'));
			
			Pattern p = Pattern.compile(changeTo + "\\d\\.?\\d{0,} ");
			Matcher m = p.matcher(line);
			
			if(m.find())
			{
				return line.substring( m.start(),m.end()) + "\n" +  line.substring(0,m.start()) + line.substring(m.end(), line.length());
			}
			else return line;
		}
		else
		{
			if(changeTo.contains("%t")) {
				changeTo = changeTo.replace("%t", Integer.toString(super.getToolNumber()));
			}
			
			if(line.contains(changeTo))
			{
				return changeTo +"\n"+ line.replace(changeTo, "");
			}
			else return line;
		}
	}
		else return line;
	}

	@Override
	public String toString() {
		return "Wyodrêbnij " + getChangeTo() + " kiedy " + getTrigger();
	}

}
