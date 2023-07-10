package BasicControls;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import CordConverter.ControlTypes;
import CordConverter.Function;

public class SterowanieSinumeric implements Sterowanie{

	@Override
	public String getDwellTimeFormat() {
		// TODO Auto-generated method stub
		return null;
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
		return "Sinumeric";
	}

	@Override
	public boolean isType(ControlTypes type)
	{
		return type==ControlTypes.SINUMERIC;
	}
	

	
	@Override
	public boolean equals(Object o)
	{
	if(o==this)
		return true;
	
	if( o instanceof Sterowanie)
	{
		Sterowanie sterowanie = (Sterowanie) o;
		
		return sterowanie.toString().equals("Sinumeric");
	}
	else return false;

	
}

	@Override
	public void przygotowanieUkladuINarzedzia(int block, int toolNumber, float safeRetraction,float bRotation) {
		
		System.out.printf(Locale.CANADA,"N%d T%d M6 %n",block,toolNumber);
		System.out.printf(Locale.CANADA,"N%d G90 G54 G0 %n",block+5);
		System.out.printf(Locale.CANADA,"N%d D%d %n",block+10,toolNumber);
		System.out.printf(Locale.CANADA,"N%d G0 Z%.1f %n",block+15 ,safeRetraction);
		System.out.printf(Locale.CANADA,"N%d G0 B%.1f%n",block+20,bRotation);
	}

	@Override
	public int chlodzeniePrzezWrzeciono() {
		return 7;
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
		
		gCodeMap.put("G10","(Sin.) Interpolacja liniowa z szybkim posuwem - wsp. biegunowe");
		gCodeMap.put("G11","(Sin.) Interpolacja liniowa z szybkim F - wsp. biegunowe");
		gCodeMap.put("G12","(Sin.) Interpolacja ko³owa zgodnie z ruchem wsk. zegara");
		gCodeMap.put("G13","(Sin.) Interpolacja ko³owa przeciwnie do ruchu wsk. zegara");

		gCodeMap.put("G50","(Sin.) G51-wy³¹czenie");
		gCodeMap.put("G51","(Sin.) Zmiana wsp. skali");
		gCodeMap.put("G52","(Sin.) Ustawienie uk³adu wspó³rzêdnych maszyny");
		gCodeMap.put("G58","(Sin.)) Przesuniêcie punktu zerowego");
		gCodeMap.put("G59","(Sin.) Przesuniêcie punktu zerowego");
		gCodeMap.put("G60","(Sin.)) zdjêcie prêdkoœci, dok³adnoœæ zatrzymanie zgrubna (grupa G61 G62 G63 G64)");
		gCodeMap.put("G62","(Sin.) sterowanie odcinkowe, zmiana prêdkoœci z redukcj¹ (grupa G61 G62 G63 G64)");
		gCodeMap.put("G63","(Sin.) gwintowanie bez przetwornika (grupa G61 G62 G63 G64)");
		gCodeMap.put("G64","(Sin.) sterowanie odcinkowe, zmiana bloku bez redukcji prêdkoœci (grupa G61 G62 G63 G64)");
		gCodeMap.put("G71","(Sin.) System metryczny");
		gCodeMap.put("G70","(Sin.) System calowy");
		gCodeMap.put("G110","(Sin.) Przyjêcie jako nowego ponktu œrodkowego osi¹gniêtej wartoœci zadanej");
		gCodeMap.put("G111","(Sin.) Przyjêcie ponktu œrodkowego przy pomocy k¹ta i promienia bez ruchu w osiach");
		
		gCodeMap.put("M17","(Sin.) Koniec podprogramu");
		gCodeMap.put("M27","(Sin.) Wyzêbienie wrzeciona");
		gCodeMap.put("M36","(Sin.) M37 - odwo³anie");
		gCodeMap.put("M37","(Sin.) Przestawienie wartoœci posuwu");
		
		gCodeMap.put("R0","(Sin.) Postój czsowy w punkcie pocz¹tkowym");
		gCodeMap.put("R1","(Sin.) Pierwsza g³êbokoœc wiercenia(przyrostowo)");
		gCodeMap.put("R2","(Sin.) P³aszczyzna odniesienia");
		gCodeMap.put("R3","(Sin.) G³êbokoœæ wiercenia");
		gCodeMap.put("R4","(Sin.) Czasowy postój");
		gCodeMap.put("R5","(Sin.) Wartoœæ zag³êbiania(przyrostowo)");
		gCodeMap.put("R6","(Sin.) Zmiana kierunku obrotów wrzeciona(M3/M4)");
		gCodeMap.put("R7","(Sin.) Uruchomienie wrzeciona(M3/M4)");
		gCodeMap.put("R9","(Sin.) Skok gwintu");
		gCodeMap.put("R10","(Sin.) P³aszczyzna bazowa");
		gCodeMap.put("R11","(Sin.) Number osi wiercenia");
		gCodeMap.put("R12","(Sin.) Odskok w osi X(przyrostowo)");
		gCodeMap.put("R13","(Sin.) Odskok w osi Y(przyrostowo)");
		gCodeMap.put("R16","(Sin.) Posów wg³êbny");
		gCodeMap.put("R17","(Sin.) Posów powrotny");
		gCodeMap.put("R18","(Sin.) Odleg³oœæ od punktu œrodkowego");
		gCodeMap.put("R19","(Sin.) Odleg³óœæ miêdzy otworami");
		gCodeMap.put("R22","(Sin.) Punkt œrodka - oœ X");
		gCodeMap.put("R23","(Sin.) Punkt œrodka - oœ Y");
		gCodeMap.put("R24","(Sin.) Promieñ okrêgu");
		gCodeMap.put("R25","(Sin.) K¹t pocz¹tkowy");
		gCodeMap.put("R26","(Sin.) Podzia³ka k¹towa");
		gCodeMap.put("R28","(Sin.) Number cyklu wiercenia");
		
		
		return gCodeMap;
	}

	@Override
	public int[] baseDefinitionCode() {
		return new int[]{54,55,56,57,58};
	}

}
