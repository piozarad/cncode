package instruction;

import java.util.Arrays;

import CordConverter.Wind;
import instructionReadFromFile.InstructionDatabase;

public class InstructionFactory {

	
	private static String temp;
	private static String tempArray[];
	
	
	
	private InstructionFactory()
	{
		
	}
	
	public static Instruction createInstuction(String line)
	{
		
		temp=line;
		while(temp.contains("  "))
			temp=line.replace("  "," ");
		
		
		if(temp.contains("^+"))
		{
			tempArray= temp.split("\\^\\+");
			return new AddToUpperLine(tempArray[0],tempArray[1]);
		}
		else if(temp.contains("/"))
		{
			if(temp.startsWith("/"))return new Delete(temp.substring(temp.indexOf('/')+1,temp.length()));
			else {
				tempArray= temp.split("/");
				 return new DeleteWhen(tempArray[0],tempArray[1]);
			}
		}
		else if(temp.contains("?") && temp.contains("=>"))
		{
			
			tempArray = temp.split("\\?|=>");
			return new ChangeWhen(tempArray[0],tempArray[2],tempArray[1]);
		}
		else if(temp.contains("=>"))
		{
			tempArray= temp.split("=>");
			return new ChangeTo(tempArray[0],tempArray[1]);
		}
		else if(temp.contains("?") && temp.contains("vv"))
		{
			
			tempArray = temp.split("\\?|vv");
			return new AddUntil(tempArray[0],tempArray[2],tempArray[1]);
		}
		else if(temp.contains("v+"))
		{
			tempArray= temp.split("v\\+");
			return new AddToNextLine(tempArray[0],tempArray[1]);
		}
		
		else if(temp.contains("+"))
		{
			
			tempArray= temp.split("\\+");
			return new Add(tempArray[0],tempArray[1]);
		}
		else if(temp.contains("KL>"))
		{
			
			temp = temp.substring(temp.indexOf('>')+1,temp.length());
			return new DeleteLine(temp);
		}
		else if(temp.contains("?<>") )
		{
			tempArray = temp.split("\\?<>");
			if(tempArray.length<2)	return null;
			
			return new SeparateWhen(tempArray[0],tempArray[1]);
		}
		else if(temp.contains("<>") )
		{

			return new SeparateFunction(temp.replace("<>",""));
		}
		else if(temp.contains("<MAX>") )
		{
			tempArray = temp.split("<MAX>");
			
			try
			{
				Double.parseDouble(tempArray[1]);			// format check
				return new Limit(tempArray[0],tempArray[1]);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}
		
		else
		{
			Wind.log.writeInfoLog("Nie rozpoznano polecenia przy czytaniu pliku :" + temp, InstructionDatabase.class.getSimpleName());
			//TODO napisac to jako wyskakuj¹ce okienko//parent.writeWarninglog("B³¹d przy czytaniu pliku, nie rozpoznano instrukcji:" + temp);
			return null;
		}
		
	}
	
}
