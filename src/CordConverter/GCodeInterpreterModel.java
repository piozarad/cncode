package CordConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class GCodeInterpreterModel {

	private Map<String,String> gCodeEncyclopedia;
	String interpretation="";
	
	private float radius;
	
	
	public GCodeInterpreterModel()
	{
		this.gCodeEncyclopedia = new HashMap<>();
		initializeMap();
	}
	
	
	public void calculateR(Function f)
	{
		
	if(!f.getCircle().containsKey('R'))
	{
		Float i =f.getCircle().get('I');
		Float j =f.getCircle().get('J');
		Float k =f.getCircle().get('K');
		
		if(i==null)
			i=0f;
		if(j==null)
			j=0f;
		if(k==null)
			k=0f;
		
		
		
		
		this.radius= (float)Math.sqrt(i*i + j*j + k*k);
	}
	else 
		radius= f.getCircle().get('R');
	}
	
	
	public float getRadius(Function f)
	{
		calculateR(f);
		return this.radius;
	}
	
	
	
	
	
	
	public void updateGCodeDatabase(Map<String,String> gCodeDatabase)
	{
		this.gCodeEncyclopedia.clear();
		this.initializeMap();
		
		this.gCodeEncyclopedia.putAll(gCodeDatabase);
	}
	
	public String getInterpretation()
	{
		return this.interpretation;
	}
	
	
	
	public void explainGCodeBlock(Function gCodeLine)
	{
		StringJoiner joiner = new StringJoiner("\n");
		String key;
		
		for(int g: gCodeLine.getFunctionType())
		{
			key = ("G" + Integer.toString(g));
			if(gCodeEncyclopedia.containsKey(key))
					joiner.add(key+" "+gCodeEncyclopedia.get(key));
		}

		if( gCodeLine.getMFunctin() !=-1)
		{
			key = "M" + gCodeLine.getMFunctin();
			if(gCodeEncyclopedia.containsKey(key))
				joiner.add(key+" "+gCodeEncyclopedia.get(key));
				
		}

		this.interpretation = joiner.toString();
	}

	private void initializeMap()
	{

		//G Codes
		this.gCodeEncyclopedia.put("G0", "Przejazd ruchem szybkim");
		this.gCodeEncyclopedia.put("G1", "Przejazd roboczy z zadanym posuwem");
		this.gCodeEncyclopedia.put("G2", "Ruch po okrêgu w prawo");
		this.gCodeEncyclopedia.put("G3", "Ruch po okrêgu w lewo");
		this.gCodeEncyclopedia.put("G4", "Postój czasowy");
		this.gCodeEncyclopedia.put("G5", "Dok³adne zatrzymanie");
		
		
		this.gCodeEncyclopedia.put("G9", "Dokladne zatrzymanie");
		this.gCodeEncyclopedia.put("G10", "Programowe wprowadzenie danych");
		this.gCodeEncyclopedia.put("G10", "Programowe wprowadzenie danych - wy³");
		

		this.gCodeEncyclopedia.put("G17", "P³aszczyzna skrawania XY");
		this.gCodeEncyclopedia.put("G18", "P³aszczyzna skrawania XZ");
		this.gCodeEncyclopedia.put("G19", "P³aszczyzna skrawania YZ");
	
		
		this.gCodeEncyclopedia.put("G40", "Odwo³adnie kompensacji promieniowej narzêdzia");
		this.gCodeEncyclopedia.put("G41", "Kompensacja prawostronna promienia narzêdzia");
		this.gCodeEncyclopedia.put("G42", "Kompensacja lewostronna promienia narzêdzia");
		
		this.gCodeEncyclopedia.put("G49", "Odwo³anie kompensacji d³ugoœciowej narzêdzia");
		
		this.gCodeEncyclopedia.put("G53", "Ustawienie uk³adu wspó³rzêdnych maszyny");
		this.gCodeEncyclopedia.put("G54", "Ustawienie uk³adu wspó³rzêdnych 54");
		this.gCodeEncyclopedia.put("G55", "Ustawienie uk³adu wspó³rzêdnych 55");
		this.gCodeEncyclopedia.put("G56", "Ustawienie uk³adu wspó³rzêdnych 56");
		this.gCodeEncyclopedia.put("G57", "Ustawienie uk³adu wspó³rzêdnych 57");

		
		this.gCodeEncyclopedia.put("G61", "Tryb dok³adnej œcie¿ki przejazdu (odwo³uje G64)");
		
		this.gCodeEncyclopedia.put("G64", "Tryb sta³ej prêdkoœci przejazdu (Odwo³uje G61)");
		this.gCodeEncyclopedia.put("G65", "Wywo³anie makra");
		this.gCodeEncyclopedia.put("G66", "Modalne wywo³anie makra");
		this.gCodeEncyclopedia.put("G65", "Modalne wywo³anie makra - koniec");
		
		this.gCodeEncyclopedia.put("G81", "Cykl wiercenia");
		this.gCodeEncyclopedia.put("G82", "Cykl wnawiercania/pog³êbiania");
		this.gCodeEncyclopedia.put("G83", "Cykl wiercenia g³êbokiego");
		this.gCodeEncyclopedia.put("G84", "Cykl gwintowania");
		this.gCodeEncyclopedia.put("G85", "Cykl rozwiercania");
		this.gCodeEncyclopedia.put("G86", "Cykl wytaczania");
		this.gCodeEncyclopedia.put("G80", "Koniec cyklu sta³ego");
		
		this.gCodeEncyclopedia.put("G90", "Programowanie absolutne");
		this.gCodeEncyclopedia.put("G91", "Programowanie przyrostowe");
		
		this.gCodeEncyclopedia.put("G94", "Posuw minutowy");
		this.gCodeEncyclopedia.put("G95", "Posuw na obrót");
		
		//MCodes
		this.gCodeEncyclopedia.put("M0", "Zatrzymanie programu");
		this.gCodeEncyclopedia.put("M1", "Warunkowe zatrzymanie programu");
		this.gCodeEncyclopedia.put("M3", "Za³¹czenie prawych obrotów wrzeciona");
		this.gCodeEncyclopedia.put("M4", "Za³¹czenie lewych wrzeciona");
		this.gCodeEncyclopedia.put("M5", "Wy³¹czenie wrzeciona");
		this.gCodeEncyclopedia.put("M6", "Wymiana narzêdzia");
		
		this.gCodeEncyclopedia.put("M8", "W³¹czenie ch³odziwa");
		this.gCodeEncyclopedia.put("M9", "Wy³¹czenie podawania ch³odziwa");
		
		this.gCodeEncyclopedia.put("M19", "Pozycjonowanie wrzeciona");
		
		this.gCodeEncyclopedia.put("M30", "Koniec programu i przewiniêcie do pocz¹tku");
	}
	
	
	
	
	
	
	
	
	
}
