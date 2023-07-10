package BasicControls;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import CordConverter.ControlTypes;
import CordConverter.Function;

public class SterowanieFanuc implements Sterowanie{

	
	private final int[] specialCycles= {73,74,76};
	
	@Override
	public String getDwellTimeFormat() {
		return "(?<=[XP])\\d+\\.?\\d{0,3}";
	}

	@Override
	public Float[] getCycleArrayFormat() {
		return new Float[4];
	}



	@Override
	public int[] getSpecialCylcesArray() {
		return specialCycles;
		
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o==this)
			return true;
		
		if(o instanceof Sterowanie )
		{
			Sterowanie sterowanie = (Sterowanie) o;

			return (sterowanie.toString().equals("Fanuc"));
		}

		else return false;

	}

	@Override
	public String toString() {
		return "Fanuc";
	}

	@Override
	public boolean isType(ControlTypes type)
	{
		return type==ControlTypes.FANUC;
	}

	@Override
	public void przygotowanieUkladuINarzedzia(int block, int toolNumber, float safeRetraction,float bRotation, String base) {
		System.out.printf(Locale.CANADA,"N%d T%d  M6%n",block,toolNumber);
		System.out.printf(Locale.CANADA,"N%d G90 %s G0%n",block+5, base);
		System.out.printf(Locale.CANADA,"N%d G43 H%d G0 Z%.1f%n",block+10,toolNumber ,safeRetraction);
		System.out.printf(Locale.CANADA,"N%d G0 B%.1f%n",block+15 ,bRotation );
		
	}

	@Override
	public int chlodzeniePrzezWrzeciono() {
		
		return 12;
	}
	@Override
	public int chlodzeniePrzezDysze() {
		
		return 8;
	}
	
	

	@Override
	public Map<String,String> updateGCodes() {
		
		
		Map<String,String> gCodeMap = new HashMap<>();
		
	
		
		gCodeMap.put("G5","(Fanuc) AI NANO");
		gCodeMap.put("G7", "(Fanuc)Interpolacja cylindryczna");
		gCodeMap.put("G20", "(Fanuc)Jednostki w calach");
		gCodeMap.put("G21", "(Fanuc)Jednostki w milimetrach");
		gCodeMap.put("G24","Powrót z punktu referencyjnego");
		gCodeMap.put("G27","(Fanuc) Sprawdzenie czy przejzd konczy sie w punkcie referencyjnym");
		gCodeMap.put("G28","(Fanuc) Przejazd do punktu referencyjnego");
		gCodeMap.put("G29","(Fanuc) Powrót z punktu referencyjnego");
		gCodeMap.put("G30","(Fanuc) Przejazd na 2,3,4 punkt referencyjny");
		gCodeMap.put("G43","(Fanuc) Korekcja d³ugoœci narzêdzia w kierunku Z+");
		gCodeMap.put("G44","(Fanuc) Korekcja d³ugoœci narzêdzia w kierunku Z-");
		gCodeMap.put("G49","(Fanuc) Odwo³anie korekcji d³ugoœciowej narzêdzia");
		gCodeMap.put("G50","(Fanuc) Wy³aczenie skalowania");
		gCodeMap.put("G51","(Fanuc) W³aczenie skalowania");
		gCodeMap.put("G50.1","(Fanuc) Lustrzane odbicie");
		gCodeMap.put("G51.1","(Fanuc) Lustrzane odbicie - wy³¹czenie");
		gCodeMap.put("G58", "Ustawienie uk³adu wspó³rzêdnych 58");
		gCodeMap.put("G59", "Ustawienie uk³adu wspó³rzêdnych 59");
		gCodeMap.put("G53","(Fanuc) Uzycie ukladu wspolrzednych maszyny");
		gCodeMap.put("G54.1","(Fanuc) Uzycie dodatkowych ukladow wspolrzednych");
		gCodeMap.put("G61","(Fanuc) Tryb dok³adnego zatrzymania (grupa G61 G62 G63 G64)");
		gCodeMap.put("G62","(Fanuc) Tryb Zmniejszenia posuwu w naro¿ach (grupa G61 G62 G63 G64)");
		gCodeMap.put("G63","(Fanuc) Tryb wykonywania gwintów (grupa G61 G62 G63 G64)");
		gCodeMap.put("G64","(Fanuc) Tryb sta³ego posuwu kosztem dokladnosci toru narzedzia (grupa G61 G62 G63 G64)");
		
		gCodeMap.put("M29","(Fanuc) Tryb gwintowania sztywnego");
		gCodeMap.put("M98","(Fanuc) Wywolanie podprogramu");
		gCodeMap.put("M99","(Fanuc) Zakonczenie podprogramu");
		
		
		gCodeMap.put("R","P³asczyzna odniesienia cyklu sta³ego");
		
		return gCodeMap;
	}

	@Override
	public int chlodzeniePrzezSufit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] baseDefinitionCode() {
		return new int[]{54,55,56,57,58,59};
	}
	
	


}
