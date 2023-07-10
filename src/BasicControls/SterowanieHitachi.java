package BasicControls;

import java.util.Locale;
import java.util.Map;

import CordConverter.ControlTypes;
import CordConverter.Function;

public class SterowanieHitachi implements Sterowanie {

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
		return "Hitachi";
	}

	
	@Override
	public boolean equals(Object o)
	{
		if(o==this)
			return true;
		
		if( o instanceof Sterowanie)
		{
			Sterowanie sterowanie = (Sterowanie) o;
			
			return sterowanie.toString().equals("Hitachi");
		}
		else return false;
		
		
	}
	
	@Override
	public boolean isType(ControlTypes type)
	{
		return type==ControlTypes.HITACHI;
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
		
		return 47;
	}
	
	

	@Override
	public int chlodzeniePrzezSufit() {
		return 8;
	}

	@Override
	public Map<String, String> updateGCodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] baseDefinitionCode() {
		return new int[]{54,55,56,57,58,59};
	}



}
