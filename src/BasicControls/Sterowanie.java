package BasicControls;

import java.util.Map;

import CordConverter.ControlTypes;
import CordConverter.Function;

public interface Sterowanie {



public void przygotowanieUkladuINarzedzia(int block, int toolNumber, float safeRetraction, float bRotation, String base);
	
public String getDwellTimeFormat();

public Float[] getCycleArrayFormat();

public int[] getSpecialCylcesArray();

public boolean isType(ControlTypes type);

public int chlodzeniePrzezWrzeciono();

public int chlodzeniePrzezDysze();

public int chlodzeniePrzezSufit();

public Map<String,String> updateGCodes();

public int[] baseDefinitionCode();






}
