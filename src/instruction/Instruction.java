package instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CordConverter.FunctionUtilities;


public abstract class Instruction {
	
	private String trigger;
	private String changeFrom;
	private String changeTo;
	private static int actualToolNumber=0;
	private static int nextToolNumber;

	
	public String getChangeFrom()
	{
		return applyActualT(changeFrom);
	}
	public String getTrigger()
	{

		return applyActualT(trigger);
	}
	public String getChangeTo()
	{

		return applyActualT(changeTo);
	}
	
	public Instruction(String trigger)
	{
		this.trigger= FunctionUtilities.removeComment(trigger)+ " ";	
	}
	public Instruction(String trigger,String changeTo)
	{
		this(trigger);
		this.changeTo=changeTo;
	}
	public Instruction(String trigger,String changeTo,String changeFrom)
	{
		this(trigger,changeTo);
		this.changeFrom=changeFrom + " ";
	}
	
	protected int getToolNumber()
	{
		return actualToolNumber;
	}
	
	protected static void updateToolNumber(String line)
	{
		if(line.contains("T") || line.contains("M6"))
		{
		CordConverter.FunctionUtilities.removeComment(line);
		Pattern p = Pattern.compile("T\\d+");
		Matcher m =p.matcher(line);
		
		//je¿eli znajdzie kod T to ustaw nastêpne narzedzie na ten numer
		if(m.find()) nextToolNumber=Integer.parseInt(line.substring(m.start()+1,m.end()));
	
		//je¿eli w linii jest komenda M6 to ustaw nastêpne narzêdzie na aktualne narzêdzie 
		if(line.contains("M6")) actualToolNumber= nextToolNumber;
		}
	}
	
	

	
	
	private String applyActualT(String s)
	{
		return s.contains("%t") ? s.replace("%t", Integer.toString(actualToolNumber)) : s; 
	}


	protected boolean isTriggered(String line)
	{
		
		return line.contains(trigger);
		
	}
	

	/*
	 * returns true if given line contains characters which are required to create specific instuction object
	 */
	public abstract boolean hasmark(String line);
	public abstract String returnMark();
	
	
		
	public abstract String applyChanges(String line);
	
	@Override
	public String toString()
	{
		return "trigger: " + trigger + "\nChange from: " + changeFrom + "\nChange to: "+ changeTo;
	}
	
}
