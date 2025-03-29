package CordConverter;

import static CordConverter.FunctionUtilities.fixedCycleParameters;
import static CordConverter.FunctionUtilities.gFunctions;
import static CordConverter.FunctionUtilities.getCircleParam;
import static CordConverter.FunctionUtilities.getFloatResult;
import static CordConverter.FunctionUtilities.getIntResult;
import static CordConverter.FunctionUtilities.getStringResult;
import static CordConverter.FunctionUtilities.hasARotation;
import static CordConverter.FunctionUtilities.hasBRotation;
import static CordConverter.FunctionUtilities.hasBlockNumber;
import static CordConverter.FunctionUtilities.hasCRotation;
import static CordConverter.FunctionUtilities.hasComment;
import static CordConverter.FunctionUtilities.hasFeed;
import static CordConverter.FunctionUtilities.hasH;
import static CordConverter.FunctionUtilities.hasMFunction;
import static CordConverter.FunctionUtilities.hasMacro;
import static CordConverter.FunctionUtilities.hasP;
import static CordConverter.FunctionUtilities.hasQ;
import static CordConverter.FunctionUtilities.hasS;
import static CordConverter.FunctionUtilities.hasToolNumber;
import static CordConverter.FunctionUtilities.parsePoint;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import BasicControls.Sterowanie;

public class Function {


	Float[] circleArray;
	
	protected Point destinationPoint;
	private Float aRotation;
	private Float bRotation;
	private Float cRotation;
	private List<Integer> functionType;
	private int mFunctionType=-1;
	private int blockNumber;
	private float feed=-1;
	private int speed=-1;
	private int toolNumber=-1;
	private String comment;
	private String macro =null;
	private boolean isBlank = false;
	
	private String H = "0";
	private String D = "0";
	private String P;
	private Float Q;
	private Map<Character,Float> circle;
	private Map<String,Float> rCycleParam;
	
	
	//sterowanie
	private Sterowanie sterowanie;
	
	
	//constructors
	
	public Function()
	{
		this.functionType = new LinkedList<>();
		this.circle = new HashMap<>();
		this.rCycleParam = new HashMap<>();
	}

	
	public Function(String block)
	{
		this();
		
		if(block.length() ==0)
			this.isBlank = true;
		
		else 
		{
			
		if(hasComment(block))
		{
			fillComment(block);
			block=block.replaceAll("\\(.*\\)", "");
		}
			
		if(hasMacro(block))
			this.macro=block;
		else 
		{
			
		
			

			fillBlockNumber(block);
			
			functionType = gFunctions(block);
			destinationPoint = parsePoint(block);
			
			fillMFunction(block);
			fillARotation(block);
			fillBRotation(block);
			fillCRotation(block);
			fillFeed(block);
			fillS(block);
			fillP(block);
			fillH(block);
			fillD(block);
			fillToolNumber(block);
			fillQ(block);
			
			this.rCycleParam = fixedCycleParameters(block);

			circleArray = getCircleParam(block);
			if(circleArray[0]!=null)
				circle.put('I', circleArray[0]);
			if(circleArray[1]!=null)
				circle.put('J', circleArray[1]);
			if(circleArray[2]!=null)
				circle.put('K', circleArray[2]);
			if(circleArray[3]!=null)
				circle.put('R', circleArray[3]);
		}
		}
	}

	public Function(String block, Sterowanie sterowanie) {
		
		this(block);
		this.sterowanie=sterowanie;
	}

	

	//setters
	public void setBlockNumber(int i)
	{
		this.blockNumber=i;	
	}
	
	public void setMacro(String macro)
	{
		this.macro=macro;
	}
	
	public void setControls(Sterowanie sterowanie)
	{
		this.sterowanie=sterowanie;
	}
	
	public void setPoint(Point p)
	{
		this.destinationPoint=p;

	}
	
	//przesuniecie ukladu w osi z
	public void addValueToRArray(Float value)
	{
		if (!this.rCycleParam.isEmpty())
		{
			rCycleParam.replace("R", rCycleParam.get("R") + value);
			rCycleParam.replace("R2", rCycleParam.get("R2") + value);
			rCycleParam.replace("R3", rCycleParam.get("R3") + value);
			rCycleParam.replace("R10", rCycleParam.compute("R10", null) + value);
			}
	}	
	
	
	public void setMFunction(int m)
	{	
		this.mFunctionType = m;
	}

	public void setFeed(float feed)
	{
		this.feed=feed;
	}
	
	public void setQ(Float f)
	{
		this.Q=f;
	}
	public void setH(String h)
	{
		this.H=h;
	}
	public void setP(String p)
	{
		this.P=p;
	}
	public void setD(String d)
	{
		this.D=d;
	}
	
	public void setComment(String comment)
	{
		this.comment=comment;
	}
	
	public void setGFunction(int pos, int gFunction)
	{
			this.functionType.set(pos, gFunction);
	}
	
	/**
	 * Adds g function at the end of the list
	 * @param gFunction
	 */
	public void addGFunction(int gFunction)
	{
		this.functionType.add(gFunction);
	}
	
	/**
	 * Adds g function to the list at given index
	 * @param gFunction
	 * @param position
	 */
	public void addGFunction(int gFunction, int position)
	{
		this.functionType.add(position,gFunction);
	}
	
	public void replaceGFunction(int oldFunction, int newFunction)
	{
		if(oldFunction<0 || newFunction<0)
			throw new IllegalArgumentException("Argument musi byc wiekszy od zera");
		
		for(int i=0;i<functionType.size();i++)
		{
			if(functionType.get(i)==oldFunction)
			{
				functionType.set(i, newFunction);
				break;
			}	
		}

	}
	
	/**
	 * 
	 * @param Rparemeter Cycle parameter for ex R2
	 * @return return float value of given R parameter
	 */
	public Float getRParameter(String rParemeter)
	{
		return this.rCycleParam.get(rParemeter);
	}
	
	/** 
	 * Replaces G Function
	 * @param oldFunction 
	 * @param newFunction
	 */
	public void replaceGFunction(int oldFunction, float newFunction)
	{
		if(oldFunction<0 || newFunction<0)
			throw new IllegalArgumentException("Argument musi byc wiekszy od zera");
		int positionInList=-1;
		
		for(int i=0;i<functionType.size();i++)
		{
			if(functionType.get(i)==oldFunction)
				positionInList=i;
		}
		
		if(positionInList!=-1)
		{
			functionType.remove(positionInList);
			functionType.add(positionInList,(-FunctionUtilities.getSubvalue(newFunction)));
			functionType.add(positionInList,(int) newFunction);
			
		}	
	}
	
	/**
	 * Replaces G Function
	 * @param oldFunction 
	 * @param newFunction
	 */
	public void replaceGFunction(float oldFunction, int newFunction)
	{
		if(oldFunction<0 || newFunction<0)
			throw new IllegalArgumentException("Argument musi byc wiekszy od zera");
		int positionInList =-1;
		
		for(int i=0;i<functionType.size();i++)
		{
			if(functionType.get(i)==(int)oldFunction && (i< functionType.size()-1))
			{
				if(-functionType.get(i+1)==FunctionUtilities.getSubvalue(oldFunction))
					positionInList = i;
			}
		}
		
		if(positionInList!=-1)
		{
			functionType.remove(positionInList+1);
			functionType.remove(positionInList);
			functionType.add(positionInList,newFunction);		
		}	
	}

	/**
	 * Replaces G Function
	 * @param oldFunction 
	 * @param newFunction
	 */
	public void replaceGFunction(float oldFunction, float newFunction)
	{
		if(oldFunction<0 || newFunction<0)
			throw new IllegalArgumentException("Argument musi byc wiekszy od zera");
		
		int positionInList =-1;
		
		for(int i=0;i<functionType.size();i++)
		{
			if(functionType.get(i)==(int)oldFunction && (i< functionType.size()-1))
			{
				if(-functionType.get(i+1)==FunctionUtilities.getSubvalue(oldFunction))
					positionInList = i;
			}
		}
		
		if(positionInList!=-1)
		{
			functionType.remove(positionInList+1);
			functionType.remove(positionInList);
			functionType.add(positionInList,-FunctionUtilities.getSubvalue(newFunction));
			functionType.add(positionInList,(int)newFunction);
			
		}	

	}
	/**
	 * Ads more complex g function like G54.1 ot G5.2
	 * @param gFunction
	 * @throws IllegalArgumentException
	 */
	public void addGFunction(float gFunction)
	{
		if(gFunction<0)
			throw new IllegalArgumentException("Argument musi byc wiekszy od zera");
		
		
		//first function
		this.functionType.add((int) gFunction);
		
		//second function
		this.functionType.add((int)(-1*  ( 10*gFunction     -10*((int)gFunction) )));
	}
	
	/**
	 * 
	 * @param gFunction
	 * @param position
	 */
	public void addGFunction(float gFunction, int position)
	{
		if(gFunction<0 || position<0)
			throw new IllegalArgumentException("Argument musi byc wiekszy od zera");
		
		
		//second function after dot
				this.functionType.add(position,(int)(-1*  ( 10*gFunction     -10*((int)gFunction) )));
				
		//first function
		this.functionType.add(position,(int) gFunction );
	}
	
	/**
	 *  Removes given G function form block
	 * @param function
	 */
	public void removeGFunction(int function)
	{
		if(functionType.contains(function))
			functionType.remove(functionType.indexOf(function));

	}


	/**
	 *  Removes given G function form block
	 * @param function
	 */
	public void removeGFunction(float function)
	{
		if(this.containsFunction(function))
		{
			int deleteIndex=functionType.indexOf((int)function);
			
			functionType.remove(deleteIndex);
			functionType.remove(deleteIndex);
		}
		
			
	}
	
	public void setRCycleAray(String value,Float r)
	{
		rCycleParam.clear();
		rCycleParam.put(value, r);
		
	}
	/**
	 * 
	 * @param value Wartosc promienia okregu R.
	 * Ustawia wartosc promienia okregu na podana
	 */
	public void setCircle(Float value)
	{
		if(this.circle.containsKey('R'))
			circle.remove('R');
		
		 circle.put('R', value);
		
	}
	/**
	 * 
	 * @param key I J K lub R - informacje potrzebne do obliczenia okregu
	 * @param value Wartosc podanego klucza
	 * Ustawia wartosc wektora srodka okregu na podana
	 */
	public void setCircle(Character key, Float value)
	{
		if(this.circle.containsKey(key))
			circle.remove(key);
		
		circle.put(key, value);
		
	}
	
	
	
	public void addToFixedCycleArray(String key, Float value)
	{
		 rCycleParam.put(key,value);
		
	}
	
	/**
	 * @param Przyjmuje tablice int funkcji ktorych obecnosc ma byc sprawdzona w tym bloku 
	 * @return Zwraca true jesli ktorakolwiek z funkcji podanych jako argument znajduje siê w bloku, w przeciwnym wypadku zwraca false
	*/
	public boolean containsFunction(int ...vargs)
	{
		for(int i: this.functionType)
		{
			for(int j: vargs)
			{			
				if(j==i)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param int value of G function like G41
	 * @return return true if given funtion occurs in this block
	 */
	public boolean containsFunction(int function)
	{
		for(int i: functionType)
		{
			if(i==function)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param function float value of G function like 54.1
	 * @return return true if given funtion occurs in this block
	 */
	public boolean containsFunction(float function)
	{
		int first = (int) function;
		int second = -FunctionUtilities.getSubvalue(function);

		for(int i=0; i<functionType.size()-1;i++)
		{
			if(functionType.get(i)==first && functionType.get(i+1)==second)
				return true;
		}
		return false;
	}

	//getters

	/*
	 * return true if function is created from an empty block ""
	 */
	public boolean isBlank()
	{
		return this.isBlank;
	}
	
	public Sterowanie getSterowanie()
	{
		return this.sterowanie;
	}
	/**
	 * 
	 * @return Zwraca komentarz wystepujacy w bloku lub null jesli nie wystepuje
	 */
	public String getComment()
	{
		return this.comment;
	}
	
	public int getFunctionLength()
	{
		return this.toString().length();
	}
	
	/**
	 * 
	 * @return Zwraca punkt lub null jesli nie wystepuje on w bloku
	 */
	public Point getPoint()
	{
		return this.destinationPoint;
	}
	/**
	 * 
	 * @return Zwraca wartosc int numeru bloku lub 0 jesli nie jest on zdefiniowany w tym bloku
	 */
	
	public int getBlock()
	{
		return this.blockNumber;
		
	}
	
	public Map<String,Float> getRArray()
	{
		
		return this.rCycleParam;
	}
	
	/**
	 * 
	 * @return Zwraca wartosc Float funkcji A lub null jesli nie wystepuje ona w tym bloku
	 */
	public Float getARotation()
	{
		return this.aRotation;
	}

	/**
	 * 
	 * @return Zwraca wartosc Float funkcji B lub null jesli nie wystepuje ona w tym bloku
	 */
	public Float getBRotation()
	{
		return this.bRotation;
	}
	
	/**
	 * 
	 * @return Zwraca wartosc Float funkcji C lub null jesli nie wystepuje ona w tym bloku
	 */
	public Float getCRotation()
	{
		return this.cRotation;
	}
	
	/**\
	 * 
	 * @return Zwraca wartosc int  funkcji T numeru narzedzia lub -1 jesli nie wystepuje ona w tym bloku
	 */
	public int getToolNumber()
	{
		return this.toolNumber;
	}
	/**
	 * 
	 * @return Zwraca wartosc int  funkcji predkosci wrzeciona S lub -1 jesli nie wystepuje ona w tym bloku
	 */
	public int getSpeed()
	{
		return this.speed;
	}
	/**
	 * 
	 * @return Zwraca wartosc Float funkcji posuwu F lub -1 jesli nie wystepuje ona ty bloku
	 */
	public Float getFeed()
	{
		return this.feed;
	}
	/**
	 * 
	 * @return Zwraca typ String funkcji D lub "0" jesli nie wystepuje ona w tym bloku
	 */
	public String getD()
	{
		return this.D;
	}
	/**
	 * 
	 * @return Zwraca typ String funkcji H lub "0" jesli nie wystepuje ona w tym bloku
	 */
	public String getH()
	{
		return this.H;
	}
	public String getMacro()
	{
		return this.macro;
		
	}
	/**
	 * 	@return Zwraca wartosc int funkcji maszynowej M wystepujaca w tym bloku lub -1 jesli ona nie wystepuje
	 */
	public int getMFunctin()
	{
		return this.mFunctionType;
	}
	
	/** 
	 * @return Zwraca typ String funkcji P lub null jesli nie wystepuje w danym bloku
	 *  
	 */
	public String getP()
	{
		return this.P;
	}
	
	/**
	 * 
	 * @return Zwraca wartosc Q lub null jesli nie znajduje siê w danym bloku
	 */
	public Float getQ()
	{
		return this.Q;
	}
	/**
	 * 
	 * @return zwraca tablice typu int zawierajaca liste F funkcji znalezionych w bloku. Jesli nie zawira ¿adnych funkcji zwraca jednoelementow¹ tablice {-1}
	 */
	public List<Integer> getFunctionType()
	{
		return this.functionType;
	}
	
	public Float[] getRcycleParam()
	{
		if(this.rCycleParam.size()>0)
			return this.rCycleParam.values().toArray(new Float[0]);
		else return new Float[] {};
	}
	
	public Map<Character,Float> getCircle()
	{
		return this.circle;
	}
	
	
	private void fillBlockNumber(String block)
	{
		if(hasBlockNumber(block))
			blockNumber = getIntResult();
	}
	
	private void fillMFunction(String block)
	{
		if(hasMFunction(block))
			mFunctionType = getIntResult();	
	}
	private void fillBRotation(String block)
	{
		if(hasBRotation(block))
			bRotation = getFloatResult();
	}
	private void fillARotation(String block)
	{
		if(hasARotation(block))
			aRotation = getFloatResult();
	}
	private void fillComment(String block)
	{
		if(hasComment(block))
			this.comment = block.substring(FunctionUtilities.getStartIndex(), FunctionUtilities.getLastIndex());
	}
	
	private void fillCRotation(String block)
	{
		if(hasCRotation(block))
			cRotation = getFloatResult();
	}
	
	private void fillFeed(String block)
	{
		if(hasFeed(block))
			this.feed = getFloatResult();
		
	}
	
	private void fillS(String block)
	{	
		if(hasS(block))
			this.speed=getIntResult();	
	}
	
	private void fillP(String block)
	{
		if(hasP(block))
			this.P=getStringResult();	
	}

	private void fillH(String block)
	{
		if(hasH(block))
			this.H=getStringResult();
	}

	private void fillD(String block)
	{
		if(FunctionUtilities.hasD(block))
			this.D=getStringResult();
	}
	private void fillToolNumber(String block)
	{
		if(hasToolNumber(block))
			this.toolNumber = getIntResult();
	}
	
	private void fillQ(String block)
	{
		if(hasQ(block))
			this.Q = getFloatResult();
	}

	public boolean isProgramBorder()
	{
		return (this.macro!=null && this.macro.contains("%"));
		

	}
	public boolean isProgramName()
	{
			return this.macro!=null && (this.macro.contains("O") || this.macro.contains("MP"));

	}
	
	
	public boolean isFixedCycle()
	{
		if(this.sterowanie!=null)
		{
			return (this.containsFunction(sterowanie.getSpecialCylcesArray()));
		}
		else return (this.containsFunction(81,82,83,84,85,85,86,87,88,89));
		

	}
	
	
	
	
	
	
	/**
	 * 
	 * @param t Ramka JTextField z ktorej ma byæ pobrany ³¹ñcuch do analizy
	 * @param diameter srednica narziedzia potzebna do obliczenia predkosci skrawania
	 * @return zwraca wartosci obliczonej predkosci wrzeciona, 0 jesli format tekstu w ramce nie zawieral polecenia obliczen "vc=_liczba" lub -1 jesli wystapil blad
	 */
	public static int covertVcToN(JTextField t, Float diameter)
	{
		
		if(diameter==null)
			return -1;
		
		
		String s = t.getText();
		Pattern p = Pattern.compile("vc=?\\d+",Pattern.CASE_INSENSITIVE);
		Matcher m =p.matcher(s);
		int n =0;
		
		if(m.find())
		{
			
				try {
					n=  (s.charAt(m.start()+2) == '=') ? Integer.parseInt(s.substring(m.start()+3, m.end())) : Integer.parseInt(s.substring(m.start()+2, m.end()));
					n = ((int)((1000*n)/(Math.PI*diameter)));
					t.setText(Integer.toString(n));
					return 1;
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, "B³¹d przy próbie odczytu predkosci skrawania: prawid³owy format to np. Vc=0.1");
					return -1;
				}
		}
		else return 0;
		
		
	}
	/**
	 * 
	 * @param t Ramka JTextField z ktorej ma byæ pobrany ³¹ñcuch do analizy
	 * @param obroty obroty wrzeciona potrzebne do obliczenia posuwu
	 * @return zwraca wartosc posuwu, 0 jesli nie t nie zawiera "fz_liczba" lub -1 jesli wystapil blad
	 */
	
	
	public static int covertFnToVf(JTextField t, Integer obroty)
	{
		if(obroty==null)
			return -1;
		
		String s = t.getText();
		int  result=0;
		float posow=0;
		Pattern p = Pattern.compile("f[nz]=?\\d+\\.?\\d{0,3}",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(s);
		
		
		if(m.find())
		{
			
				try {
					posow = (s.charAt(m.start()+2)=='=') ? (Float.parseFloat(s.substring(m.start()+3, m.end()))) : (Float.parseFloat(s.substring(m.start()+2, m.end())));
					posow*=obroty;
					t.setText(Float.toString(Point.roundToThree(posow)));
					
				} 
				catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "B³¹d przy próbie odczytu posuwu na obrot: prawid³owy format to np. fn=0.1");
					return -1;
				}

				p= Pattern.compile("[^f]z=?\\d+",Pattern.CASE_INSENSITIVE);
				m= p.matcher(s);
				
				if(m.find())
				{
					try {
						posow *=(s.charAt(m.start()+2)=='=') ? (Float.parseFloat(s.substring(m.start()+3,m.end()))) : (Float.parseFloat(s.substring(m.start()+2,m.end())));
						
						t.setText(Float.toString(Point.roundToThree(posow)));
					
					}
					catch (NumberFormatException e)
					{	
						JOptionPane.showMessageDialog(null, "B³¹d przy próbie odczytu liczby ostrzy: prawid³owy format to np. z=1");
						return -1;
					}
				}
		}

		result = (int)posow;
		return result;
		
	}

	@Override
	public String toString()
	{
		
		StringJoiner functionString = new StringJoiner(" ");

		if(macro!=null)
		{		
			functionString.add(macro);
			if(comment!=null)
			{
				functionString.add(comment);
			}		
		}
		else
		{
			if(blockNumber != 0)
				functionString.add("N"+blockNumber);
			
			
			if(comment!=null)
			{
				functionString.add(comment);
			}
		
			if(toolNumber!=-1)
			{
				functionString.add(" T" + toolNumber);
			}
			
				if(!functionType.isEmpty())
			{
				for(int i=0;i<functionType.size();i++)
				{	
					if(i<functionType.size()-1 && functionType.get(i+1)<0)
					{
						functionString.add("G" + functionType.get(i)+'.'+(-functionType.get(i+1)));
						i++;
					}
					else
						functionString.add("G" + functionType.get(i));
				}				
			}
				
			if(!D.equals("0"))
			{
				functionString.add("D" + D);
			}
			if(!H.equals("0"))
			{
				functionString.add("H" + H);
			}

			if(destinationPoint!=null)
			{
				functionString.add(destinationPoint.toString());
				
			}	
					
			if(!rCycleParam.isEmpty())
			{
				Set<String> temporaryKeySet= new TreeSet<>(new Comparator<String>(){

					@Override
					public int compare(String o1, String o2) {
						
							int first;
							int second;
							
							try
							{
								first = Integer.parseInt(o1.substring(1));
								second = Integer.parseInt(o2.substring(1));

							}
							catch (NumberFormatException e)
							{
								return 0;
							}
							
							return first-second;

					}
				});
	
				temporaryKeySet.addAll(rCycleParam.keySet());          
				for(String code: temporaryKeySet)
				{
				
					functionString.add(code +"="+ rCycleParam.get(code));
				}

			}
			if(!circle.isEmpty())
				{
					
					if(circle.containsKey('R'))
						
						functionString.add(("R"+circle.get('R')));
					
						if(circle.containsKey('I'))
							functionString.add(("I"+circle.get('I')));
						if(circle.containsKey('J'))
							functionString.add(("J"+circle.get('J')));
						if(circle.containsKey('K'))
							functionString.add(("K"+circle.get('K')));
					
			}
			
			
			if(P!=null )
			{
				functionString.add( "P" + P);
			}
	
			if(Q!=null)
			{
				functionString.add("Q" + Q);
			}
			if(aRotation!=null)
			{
				functionString.add("A" + aRotation);
			}
			if(bRotation!=null)
			{
				functionString.add("B" + bRotation);
			}
			if(cRotation!=null)
			{
				functionString.add("C" + cRotation);
			}
		
			if(speed!=-1)
			{
				functionString.add("S" + speed);
			
			}
			if(mFunctionType!=-1)
			{
				functionString.add("M" + mFunctionType);
			}

			if(feed!=-1)
			{
				functionString.add("F" +feed);
			}
		
		}
			
	
			return (functionString.add("\n").toString()); 
		}
		
	
	
}
