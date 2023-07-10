package BasicControls;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import CordConverter.ControlTypes;
import CordConverter.Function;

public class SterowanieOkuma implements Sterowanie {

	@Override
	public String getDwellTimeFormat() {
		return "(?<=P)\\d+\\.?\\d{0,3}";
	}

	@Override
	public Float[] getCycleArrayFormat() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int[] getSpecialCylcesArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Okuma";
	}

	@Override
	public boolean isType(ControlTypes type)
	{
		return type==ControlTypes.OKUMA;
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o==this)
			return true;
		
		if( o instanceof Sterowanie)
		{
			Sterowanie sterowanie = (Sterowanie) o;
			
			return sterowanie.toString().equals("Okuma");
		}
		else return false;
		
		
	}

	@Override
	public void przygotowanieUkladuINarzedzia(int block, int toolNumber, float safeRetraction,float bRotation) {
		System.out.printf(Locale.CANADA,"N%d T%d M6%n",block,toolNumber);
		System.out.printf(Locale.CANADA,"N%d G90 G15 H1%n",block+5);
		System.out.printf(Locale.CANADA,"N%d G56 DA HA%n",block+10 );
		System.out.printf(Locale.CANADA,"N%d G0 Z%.3f%n",block+15,safeRetraction);
		System.out.printf(Locale.CANADA,"N%d G0 B%.1f%n",block+20,bRotation);
		
	}

	@Override
	public int chlodzeniePrzezWrzeciono() {
		return 51;
	}
	@Override
	public int chlodzeniePrzezDysze() {
		
		return 8;
	}

	@Override
	public int chlodzeniePrzezSufit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, String> updateGCodes() {
Map<String,String> gCodeMap = new HashMap<>();
		
	
		
		gCodeMap.put("G9","(Okuma) Dok³adne zatrzymanie");
		gCodeMap.put("G10", "(Okuma) Przekszta³cenie uk³adu wspó³rzêdnych");
		gCodeMap.put("G11", "(Okuma) Odwo³anie G10");
		gCodeMap.put("G15","(Okuma) Wczytanie bazy pod numerem H");
		gCodeMap.put("G20", "(Okuma) Jednostki w calach");
		gCodeMap.put("G21","(Okuma) Jednostki w milimetrach");
		gCodeMap.put("G30","(Okuma) Przejazd na punkt referencyjny");
		gCodeMap.put("G53", "(Okuma) Odwo³anie kompensacji narzêdzia");
		gCodeMap.put("G54", "(Okuma) Wczytanie d³ugoœci narzêdzia oœ X");
		gCodeMap.put("G55", "(Okuma) Wczytanie d³ugoœci narzêdzia oœ Y");
		gCodeMap.put("G56", "(Okuma) Wczytanie d³ugoœci narzêdzia oœ Z");
		gCodeMap.put("G57", "(Okuma) Wczytanie d³ugoœci narzêdzia czwarta oœ");
		gCodeMap.put("G58", "(Okuma) Wczytanie d³ugoœci narzêdzia pi¹ta oœ");
		gCodeMap.put("G59", "(Okuma) Wczytanie d³ugoœci narzêdzia szusta oœ");
		
		gCodeMap.put("G61","(Okuma) Tryb dok³adnego zatrzymania (grupa G61 G64)");
		gCodeMap.put("G64","(Okuma) Tryb sta³ego posuwu kosztem dokladnosci toru narzedzia (grupa G61 G64)");
		
		gCodeMap.put("G71","(Okuma) Definicja punktu retrakcji dla M53");
		gCodeMap.put("G284", "(Okuma) Gwintowanie sztywne (cykl)");
		
		gCodeMap.put("M52","(Okuma) P³aszczyzna retrakcji - górny limit");
		gCodeMap.put("M53","(Okuma) P³aszczyzna retrakcji zdefiniowana przez G71");
		gCodeMap.put("M54","(Okuma) P³aszczyzna retrakcji - punkt R");
		
		gCodeMap.put("M60","(Okuma) Zmiana palety");
		gCodeMap.put("M63","(Okuma) Przygotowanie pustego kubka na narzêdzie");
		
		gCodeMap.put("R","P³aszczyzna odniesienia cyklu sta³ego");
		
		return gCodeMap;
	}

	@Override
	public int[] baseDefinitionCode() {
		return new int[]{16};
	}
	



}
