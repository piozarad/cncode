package instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionUtilities;

public class Limit extends Instruction{

	private static final String MARK = "<MAX>";

	
	public Limit(String trigger,String maxValue)
	{
		
		super(trigger,maxValue);
	}
	
	@Override
	public String applyChanges(String line) 
	{
		updateToolNumber(line);
		
		
		line =FunctionUtilities.separateFunctions(line);	
		
		String trigger = super.getTrigger().substring(0,super.getTrigger().length()-1);
		String regex = trigger+"\\d+\\.?\\d*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
			
			if(m.find())
			{
				double value = Double.parseDouble(line.substring(m.start()+1, m.end()+1));
				double limit = Double.parseDouble(super.getChangeTo());
				
				if(value>limit )
				{
					return line.replaceAll(regex, trigger+super.getChangeTo());
				}
			
			}
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
		return "Ustaw " + super.getTrigger()+ "na max " + super.getChangeTo() ;
	}

	

}
