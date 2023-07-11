package instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionAnalyzeUtilities;

public class AddToUpperLine extends Instruction{
	
	Pattern p;
	Matcher m;
	

	private static final String MARK = "^+";
	
	public AddToUpperLine(String trigger, String addedLine) {
		super(trigger,addedLine);
		
	}

	@Override
	public String applyChanges(String line) {
		updateToolNumber(line);
		line =FunctionAnalyzeUtilities.separateFunctions(line);	
		//return 
		
		
		if(getTrigger().contains("%l"))
		{
			String regex = getTrigger().substring(0,getTrigger().indexOf('%'))+"\\d+\\.?\\d{0,}";
			 p = Pattern.compile(regex);
			 m = p.matcher(line);
			 
			if(m.find())
			{
				return super.getChangeTo() +"\n"+  line;
			}
			else return line;
			
			
		}
		else return (super.isTriggered(line) ?  super.getChangeTo() + "\n" : "")  + line;
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
		return "Dodaj linijkê wy¿ej " + super.getChangeTo() + " kiedy " + super.getTrigger();
	}
}
