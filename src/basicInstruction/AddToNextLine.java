package basicInstruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionAnalyzeUtilities;

public class AddToNextLine extends Instruction{

	private static final String MARK = "v+";
	private Pattern p;
	private Matcher m;
	
	public AddToNextLine(String trigger, String changeTo) {
		super(trigger, changeTo);
		
	}

	@Override
	public String applyChanges(String line) {
		updateToolNumber(line);
		line =FunctionAnalyzeUtilities.separateFunctions(line);	
		
		if(getTrigger().contains("%l"))
		{
			String regex = getTrigger().substring(0,getTrigger().indexOf('%'))+"\\d+\\.?\\d{0,}";
			 p = Pattern.compile(regex);
			 m = p.matcher(line);
			if(m.find())
			{
				return line + "\n"+super.getChangeTo();
			}
			else return line;
			
			
		}
		else return line + (super.isTriggered(line) ? "\n"+super.getChangeTo()  : "");
		
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
		return "Dodaj linijkê ni¿ej " + super.getChangeTo() + " kiedy " + super.getTrigger();
	}
	


}
