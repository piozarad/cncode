package CordConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BasicControls.Sterowanie;

public class FunctionAnalyzeUtilities {

	static Pattern p;
	static Matcher m;
	static int start=0;
	static int last=0;
	static String lastBlock;
	
	private static final String ERROR_EMPTY_VARIABLE = "Blad zapisu boku w zmiennej lastBlock - zmienna jest pusta";
	
	static List<Integer>gCodeList = new LinkedList<>();
	
	private FunctionAnalyzeUtilities()
	{
		
	}
	
	/**
	 * @param block Function block
	 * @return true if given block is a comment, otherwise returns false
	 */
	public static boolean hasComment(String block)
	{
		p = Pattern.compile("\\(.*\\)",Pattern.COMMENTS);	
		m = p.matcher(block); 
		
		if(m.find())
		{
			lastBlock = block;
			start=m.start();
			last=m.end();
			
			return true;
		}
		else return false;
	}
	
	public static Matcher getMatcher()
	{
		return m;
	}
	
	/**
	 * 
	 * @return return int value of found field 
	 * @throws IllegalStateException
	 */
	
	public static int getIntResult() throws IllegalStateException
	{
		
		if(lastBlock==null)
			throw new IllegalStateException(ERROR_EMPTY_VARIABLE);
		
		return Integer.parseInt(lastBlock.substring(start,last));
	}
	

	public static float getFloatResult() throws IllegalStateException
	{
		if(lastBlock==null)
			throw new IllegalStateException(ERROR_EMPTY_VARIABLE);
		
		//return Float.parseFloat(lastBlock.substring(start+1,last));
		return Float.parseFloat(lastBlock.substring(start,last));
	}
	
	public static String getStringResult() throws IllegalStateException
	{
		if(lastBlock==null)
			throw new IllegalStateException(ERROR_EMPTY_VARIABLE);
		
		return lastBlock.substring(start,last);
	}
	
	/**
	 * @return start index of found sequence of characters
	 */
	public static int getStartIndex()
	{
		return start;
	}
	/**
	 * @return last index of found sequence of characters
	 */
	public static int getLastIndex()
	{
		return last;
	}
	
	/*
	 * returns Function String without chars in between "("... ")"
	 */
	public static String removeComment(String function)
	{
		if(function.contains("("))
		{
			
			return function.replaceAll("\\([^\\(]*\\)", "");
			
		//int last = Math.min(function.lastIndexOf(')')+1,function.length());
		
		//return function.substring(0,function.indexOf('(')) + function.substring(last,function.length());
		}
		else return function;

		
	}
	
	
	/**
	 * @return returns true if founds something and stores start and last index of sequence
	 */
	private static boolean seekAndFillVariables(String block)
	{
		m = p.matcher(block);
		if(m.find())
		{		
			lastBlock=block;
			start=m.start();
			last=m.end();
			return true;
		}
		else return false;
	}
	
	
	

	public static boolean hasMacro(String block)
	{	
		p = Pattern.compile(".*(if)|(while)|(do)|(goto)|(%?mpf)|(N\\D+)|(RTS)|(TL)|(%?O\\d{4})|(%)|(#\\d+).*",Pattern.CASE_INSENSITIVE);	
		
		return seekAndFillVariables(block);	
	}
	
	public static boolean hasH(String block)
	{
		p = Pattern.compile("(?<=H)(\\d+|[ABC])");

		return	seekAndFillVariables(block);
	}
	
	public static boolean hasBlockNumber(String block)
	{
		p=Pattern.compile("(?<=N)\\d+");
	
		return seekAndFillVariables(block);
	}
	
	public static boolean hasARotation(String block)
	{
		p=Pattern.compile("(?<=A)\\-?\\d+\\.?\\d{0,3}");
		
		return seekAndFillVariables(block);	
	}
	
	public static boolean hasBRotation(String block)
	{
		p=Pattern.compile("(?<=B)\\-?\\d+\\.?\\d{0,3}");
		
		return seekAndFillVariables(block);	
	}
	
	public static boolean hasCRotation(String block)
	{
		p=Pattern.compile("(?<=C)\\-?\\d+\\.?\\d{0,3}");
		
		return seekAndFillVariables(block);	
	}
	
	/**
	 * @param tab array of ints
	 * @param gCode code to be sought
	 * @return true if array tab contains given g code, false otherwise
	 */
	public static boolean contains(int[] tab, int gCode)
	{
		for(int i:tab)
		{
			if(i==gCode)
				return true;
		}	
		return false;
	}

	public static boolean hasD(String block)
	{
		p=Pattern.compile("(?<=D)(\\d+|[ABC])");
		
		return seekAndFillVariables(block);	
	}
	
	public static boolean hasP(String block)
	{
		p=Pattern.compile("(?<=P)\\d+");
		
		return seekAndFillVariables(block);	
	}
	
	public static boolean hasMFunction(String block) {
			p = Pattern.compile("(?<=M)\\d+");

			return seekAndFillVariables(block);
	}
	
	public static boolean hasToolNumber(String block) {
		 p = Pattern.compile("(?<=T)\\d+");

		 return seekAndFillVariables(block);
	}
	
	public static boolean hasFeed(String block)
	{
		 p = Pattern.compile("(?<=F)\\d+\\.?\\d{0,3}");
		return seekAndFillVariables(block);
	}
	
	public static boolean hasS(String block)
	{
		 p = Pattern.compile("(?<=S)\\d+");
		
		 return seekAndFillVariables(block); 
	}
	
	public static String getDwellTime(String block,Sterowanie sterowanie) throws IllegalArgumentException
	{

	 p = Pattern.compile(sterowanie.getDwellTimeFormat());
	 
	 m = p.matcher(block);
	 
	 if(m.find())
		 return block.substring(m.start(), m.end());
	
	 
	 else
	 	{
		 	throw new IllegalArgumentException("W podanym bloku powinien znajdowaæ siê postój warunkowy");
	 	}
	}
	public static boolean hasXCordinate(String block)
	{
		// znajdz x
		// p = Pattern.compile("X\\-?\\d+\\.?\\d{0,4}");
		 p = Pattern.compile("(?<=X)(\\-?\\d+\\.?\\d{0,4})|(?<=X)(\\-?\\.\\d{0,4})");
		
		 return seekAndFillVariables(block); 
	}
	
	public static boolean hasYCordinate(String block)
	{	
		// znajdz Y
		// p = Pattern.compile("Y\\-?\\d+\\.?\\d{0,4}");
		 p = Pattern.compile("(?<=Y)(\\-?\\d+\\.?\\d{0,4})|(?<=Y)(\\-?\\.\\d{0,4})");
		
		 return seekAndFillVariables(block); 
	}
	public static boolean hasZCordinate(String block)
	{	
		// znajdz Z
		// p = Pattern.compile("Z\\-?\\d+\\.?\\d{0,4}");
		 p = Pattern.compile("(?<=Z)(\\-?\\d+\\.?\\d{0,4})|(?<=Z)(\\-?\\.\\d{0,4})");
		
		 return seekAndFillVariables(block); 
	}
	
	public static boolean hasQ(String block)
	{
		 p = Pattern.compile("(?<=Q)\\-?\\d+\\.?\\d{0,3}");
		 
		 return seekAndFillVariables(block);
	}
	
	/**
	 * @param block with G code
	 * @param plane 17 or null (default) XY plane, G18 XZ plane, G19 YZ plane. If other int is passed plane is set to default
	 */
	public static Float[] getCircleParam(String block)
	{
		Float[] circleParamArray = new Float[4];
	
			p=Pattern.compile("(?<=I)\\-?\\d+\\.?\\d{0,3}");
			if(seekAndFillVariables(block))	
				circleParamArray[0]=getFloatResult();

			p=Pattern.compile("(?<=J)\\-?\\d+\\.?\\d{0,3}");
			if(seekAndFillVariables(block))
			{
				circleParamArray[1]=getFloatResult();
			}
			p=Pattern.compile("(?<=K)\\-?\\d+\\.?\\d{0,3}");
			if(seekAndFillVariables(block))
			{
				seekAndFillVariables(block);
				circleParamArray[2]=getFloatResult();
			}
			p=Pattern.compile("(?<=R)\\-?\\d+\\.?\\d{0,3}");
			if(seekAndFillVariables(block))
			{
				circleParamArray[3]=getFloatResult();
			}

		return circleParamArray;
		
	}
	/**
	 * @param block - line of program
	 * @return array of int containing G codes numbers that were found in given block
	 */
	public static List<Integer> gFunctions(String block) 
	{
		gCodeList.clear();
		p=Pattern.compile("G\\d+(\\.\\d{1,3})?");
		m=p.matcher(block);	
		
		while(m.find())
		{
			lastBlock=block;
			
			if(!m.group().contains("."))
				gCodeList.add(Integer.parseInt(block.substring(m.start()+1,m.end())));
			else
			{
				gCodeList.add(Integer.parseInt(m.group().substring(1,m.group().indexOf('.'))));
				gCodeList.add(-1*Integer.parseInt(m.group().substring(m.group().indexOf('.')+1,m.group().length())));
			}
		}	
		
		List<Integer> result = new LinkedList<>();
		result.addAll(gCodeList);
		

		return result;
		
	}

	/**
	 * 
	 * @param block analyzed block
	 * @return return Point found in given block
	 */
	public static Point parsePoint(String block)
	{
		Point result = null;
		
		if(block.contains("X") || block.contains("Y") || block.contains("Z"))
		{
			result = new Point();
			if(hasXCordinate(block))
				result.setX(getFloatResult());
			if(hasYCordinate(block))
				result.setY(getFloatResult());
			if(hasZCordinate(block))
				result.setZ(getFloatResult());
		}
			 return result; 
	}

	public static Map<String,Float> fixedCycleParameters(String block)
	{
		HashMap<String,Float> result = new HashMap<>();
		String regex="R\\d{1,2}=\\-?\\d{0,4}\\.?\\d{0,4}";
		String temp;
		
		block=block.replace(" ", "");
		
		p=Pattern.compile(regex);
		m=p.matcher(block);
		
			while(m.find())
			{
				temp=m.group();
				result.put(temp.substring(0,temp.indexOf('=')), Float.parseFloat(temp.substring(temp.indexOf('=')+1,temp.length())));
			}

		return result;
	}
		
	/**
	 * Zwraca cyfry znacz¹ce po przecinku jako liczba zackowita (zera sa pomijane)
	 * @param number
	 * @return
	 */
	public static int getSubvalue(float number)
	{
		number = (number%1) * 1000;
		int temp = Math.round(number);
		
		float result = (float) temp;
		
		while(result%10==0 && result >0)
			result = result/10;

		return (int) result;
	}

	private static int countCharacters(String s)
	{
		int result =0;
		for(char c : s.toCharArray())
		{
			if(c!= ' ' && c!= '.' && c!='\n')
			{	
				result++;
			}	
		}	
		return result;
	}
	

	/**
	 * @param block 
	 * @param Function
	 * @return int value given by subtraction of all characters in block and function
	 */
	public static int characterCountDifference(String block, String function)
	{
		block = block.replace(".0","");
		function = function.replace(".0","");
		
		return countCharacters(block) - countCharacters(function);	
	}
	
	/**
	 * 
	 * @param line String to be computed
	 * @return returns String with whitespaces ' ' between letters A-Z
	 */
	public static String separateFunctions(String line)
	{
		String comment = "";
		if(FunctionAnalyzeUtilities.hasComment(line))
		{
			comment = line.substring(line.indexOf('('),line.indexOf(')')+1);
			line = FunctionAnalyzeUtilities.removeComment(line);
		}
		line =line.toUpperCase();
		
		StringBuilder result = new StringBuilder();
	
		
		for(int i=0;i<line.length();i++)
		{
			result.append(line.charAt(i));
			
			//append " " to separate functions when i matches [0-9] and i+1 matches[A-Z]
			if(i<line.length()-1 && line.charAt(i)>='0' && line.charAt(i)<='9' && line.charAt(i+1) >='A' && line.charAt(i+1) <='Z')
			{
				result.append(" ");
			}
		}
		if(line.length()>0 && line.charAt(line.length()-1)!=' ')
			result.append(" ");
		
		return result.toString()  + comment;
	}
	

}
	
	
	
	

