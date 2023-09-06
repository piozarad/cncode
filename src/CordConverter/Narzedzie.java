package CordConverter;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BasicControls.Sterowanie;

public class Narzedzie {


	private int toolNumber;
	private String h;
	private String d;
	private HashMap<Integer, Float>bazaKat;
	private String nazwaNarzedzia;
	private float srednicaNarzedzia;
	private float workTime;
	private String chlodzenie;
	private int endingLine=0;

	
	private int toolBlock=0;
	
	public Narzedzie()
	{
		toolNumber=-1;
		h="0";
		d="0";
		bazaKat = new HashMap<>();
		nazwaNarzedzia="Brak";
		srednicaNarzedzia=0;
		workTime=0;
		chlodzenie="Brak";
		
	}
	

	
	//getters
	
	/**
	 * 
	 * @return Zwraca typ chlodzenia dla danego narzedzia, "Brak" jeœli nie wystêpuje dla danego narzêdzia"
	 */
	
	public String getCoolantType()
	{
		return this.chlodzenie;
	}
	
	
	/**
	 * 
	 * @return Zwraca œrednice narzêdzia lub 0 jeœli nie zosta³a okreœlona
	 */
	public float getToolDiameter()
	{
		return this.srednicaNarzedzia;
	}
	
	
	/**
	 * 
	 * @return zwraca numer narzedzia lub -1 jeœli nie jest zdefiniowany w tym narzedziu
	 */
	public int getToolNumber()
	{
		
		return this.toolNumber;
		
	}
	
	/**
	 * 
	 * @return Zwraca nazwê narzêdzia lub "Brak" jeœli nie zosta³a okreœlona
	 */
	public String getToolName()
	{
		return this.nazwaNarzedzia;
	}
	
	/**
	 * 
	 * @return zwraca indeks na liscie od ktorego zaczyna prace to narzedzie
	 */
	public int getToolBlock()
	{
		return this.toolBlock;
	
	}
	
	/**
	 * 
	 * @return zwraca wartosc korekcji dlugosciowej narzedzia H lub "0" jeœli nie zosta³a zdefiniowa dla tego narzedzia
	 */
	public String getH()
	{
		
		return this.h;
	}
	
	/**
	 * 
	 * @return zwraca wartosc korekcji promieniowej D lub zwraca "0" jeœli nie zosta³a zdefiniowana dla tego narzedzia
	 * 
	 */
	public String getD()
	{
		
		return this.d;
	}
	
	
	
	/**
	 * 
	 * @return last line of this tool in editor
	 */
	public int getEndingLine()
	{
		return this.endingLine;
	}
	
	/**
	 *  Sets this tool starting index in program list
	 * @param index 
	 */
	public void setToolBlockNumber(int index)
	{
		this.toolBlock=index;
	}
	
	
	/**
	 * 
	 * @param time in seconds
	 */
	public void setWorkTime(int time)
	{
		this.workTime=time;
		
	}
	
	/**
	 * 
	 * @param endingLine sets ending line for this tool
	 */
	public void setEndingLine(int endingLine)
	{
		this.endingLine=endingLine;
	}

	
	
	/**
	 * 
	 * @param f Function
	 */
	public void updateToolNumber(Function f)
	{
		
		if(f.getToolNumber()!=-1 && f.getMFunctin()==6)
		{
			if(this.toolNumber==-1)
			{
				this.toolNumber=f.getToolNumber();
							
			}
			
		}
	
	}
	
	/**
	 * 
	 * @param f Function
	 */
	public void updateH(Function f)
	{
		if(!f.getH().equals("0"))
		{
			if(this.h.equals("0"))
			{
				this.h=f.getH();
				
			}
			else
			{
				this.h=" !Ró¿ne";
			}
		}
	
	}
	
	/**
	 * 
	 * @param f Function
	 */
	public void updateD(Function f)
	{
		if(!f.getD().equals("0"))
		{
			if(this.d.equals("0"))
			{
				this.d=f.getD();
				
			}
			else
			{
				this.d=" !Ró¿ne";
			}
		}
	
	}

	
	
	
	public void updateToolName(Function f)
	{
		
		if(f.getComment()!=null)
		{
			
		
			if(this.nazwaNarzedzia.equals("Brak"))
			{
				String temp = f.getComment().toUpperCase();
				
				
				if(temp.contains("FREZ"))
				{
					
					if(temp.contains("JEZ"))
						this.nazwaNarzedzia = "F. je¿owy";
					else if(temp.contains("TARCZ"))
						this.nazwaNarzedzia = "F. tarczowy";
					else if(temp.contains("TEO"))
						this.nazwaNarzedzia = "F. teowy";
					else if(temp.contains("WALC") && temp.contains("CZOLO"))
						this.nazwaNarzedzia = "Frez w-c";
					else if(temp.contains("PALCOWY"))
						this.nazwaNarzedzia = "F. palcowy";
					else if(temp.contains("WALCOWY"))
						this.nazwaNarzedzia = "F. walcowy";
					else if(temp.contains("SPEC"))
						this.nazwaNarzedzia = "Frez specjalny";
					
					else this.nazwaNarzedzia = "Frez";
					
					updateToolDiameter(f);
	}
					
				
				else if(temp.contains("WIERT"))
				{
					if(temp.contains("PLYT"))
						this.nazwaNarzedzia = "Wiertlo";		
					else if(temp.contains("PROF"))
						this.nazwaNarzedzia = "Wiertlo prof";
					else if(temp.contains("SPEC"))
						this.nazwaNarzedzia = "Wiertlo spec";
					else this.nazwaNarzedzia = "Wiertlo";
					
					updateToolDiameter(f);

				}
				
				else if(temp.contains("GROT"))
					this.nazwaNarzedzia =  "W. Grot";
				
				else if(temp.contains("PILOT"))
					this.nazwaNarzedzia = "Wiertlo pilotowe";
				else if(temp.contains("GLOWICA"))
					this.nazwaNarzedzia = "G³owica";
				else if(temp.contains("POGLEBIACZ"))
					this.nazwaNarzedzia = "Pog³ebiacz";
				else if(temp.contains("WYTACZA"))
					this.nazwaNarzedzia = "Wytaczad³o";
				else if(temp.contains("OBTACZ"))
					this.nazwaNarzedzia = "Obtaczad³o";
				else if(temp.contains("ROZWIER"))
					this.nazwaNarzedzia = "Rozwiertak";
				else if(temp.contains("GWINT"))
					this.nazwaNarzedzia = "Gwintownik";
				else if(temp.contains("WYGN"))
					this.nazwaNarzedzia = "Wygniatak";
				else if(temp.contains("DOGN"))
					this.nazwaNarzedzia = "Dogniatak";
				else if(temp.contains("FAZ"))
					this.nazwaNarzedzia = "Fazownik";
				else if(temp.contains("KULA"))
					this.nazwaNarzedzia = "Frez kulowy";	
				else if(temp.contains("SPEC"))
					this.nazwaNarzedzia = "Narzêdzie specjalne";
				else if(temp.contains("NAW"))
					this.nazwaNarzedzia = "Nawiertak";
				else if(temp.contains("NAK"))
					this.nazwaNarzedzia = "Nakielek";
				
				updateToolDiameter(f);
				
				
				
			}
		}
	}
	
	
	/**
	 * 
	 * @param f Funkcja, która jesli posiada komentarz to podejmowana jest proboa odczukania z niego informacji na temat srednicy uzytego narzedzia
	 */
	public void updateToolDiameter(Function f)
	{
		if(f.getComment()!=null)
		{
			String temp = f.getComment();
			temp = temp.toUpperCase();
			

				temp = temp.replaceAll("[ _]","");
				
				Pattern p = Pattern.compile("(FI)?\\d{1,3}\\.?\\d{0,2}");

				Matcher m = p.matcher(temp);
				
				if(m.find())
				{
					try {
					
						//this.srednicaNarzedzia = Float.parseFloat(temp.substring(m.start()+1,m.end()));
						this.srednicaNarzedzia = Float.parseFloat(temp.substring(m.start(),m.end()));
					}
					
					catch(NumberFormatException e)
					{
						this.srednicaNarzedzia = Float.parseFloat(temp.substring(m.start()+2,m.end()));
					}	
				}	
		}
}
	
	//TODO dopisaæ chlodzenie przez sufit
	public void updateCoolantType(Function f,Sterowanie s)
	{
		if(s!=null && (f.getMFunctin()==s.chlodzeniePrzezWrzeciono() || f.getMFunctin()==s.chlodzeniePrzezDysze()))
		{
			if(!this.chlodzenie.equals("Brak"))
				this.chlodzenie="Ró¿ne rodzaje";
					
			if(f.getMFunctin() == s.chlodzeniePrzezWrzeciono())
			{
				this.chlodzenie=" przez wrzeciono";
			}
			else if(f.getMFunctin()==s.chlodzeniePrzezDysze())
				this.chlodzenie=" przez dysze";
		}
	}
	
	
	
	
	

	/**
	 * 
	 * @param f block analizowanego kodu
	 * @return wartoœæ zwracana true œwiadczy o zakoñczeniu pracy danego narzêdzia tj zawiera przynajmnije jedn¹ z funkcji: M30, M99, M6( wywo³ane ponownie)
	 */
	public boolean updateToolInfo(Function f)
	{
				
		if( f.getMFunctin() == 30 || f.getMFunctin()==99 || (this.toolNumber!=-1 && f.getMFunctin()==6 )) 	
			return true;
		else 
		{
			this.updateToolNumber(f);
			this.updateD(f);
			this.updateH(f);
			this.updateToolName(f);
			this.updateCoolantType(f, f.getSterowanie());
		
						
			return false;
		}	
	}
	
	
	public String display()
	{
		char fi = 216;
		return "Typ narzêdzia: "	 + this.nazwaNarzedzia + " "+
				fi + srednicaNarzedzia + "mm\n"+
				"T" +this.toolNumber + " \n" +
				"H" + h  + " D" + d +"\n"+
				"Ch³odzone: " + chlodzenie + "\n" +
				"Czas pracy: " + workTime + "s\n"+
				"Baza - k¹t:" + bazaKat;
	
	}

	
	@Override
	public String toString()
	{
		return "T"+this.toolNumber +" " + this.nazwaNarzedzia +" Fi"+ srednicaNarzedzia;

	}
	
	
}
