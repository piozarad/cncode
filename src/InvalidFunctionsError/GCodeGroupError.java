package InvalidFunctionsError;



import java.util.ArrayList;
import java.util.List;

import CordConverter.Function;
import GCodeGroup.ActiveGCodes;


public class GCodeGroupError implements InvalidFunctionError{
	
	private static ActiveGCodes gCodeGroup = new ActiveGCodes();
	
	private static List<Integer> list = new ArrayList<>();
	
	
	@Override
	public boolean findError(Function f) {
		int groupNumber=-1;
		list.clear();
		for(int i: f.getFunctionType())
		{
			groupNumber = gCodeGroup.getGroupNumber(i);
			if(list.contains(groupNumber))
			{
				return true;
			}
			else list.add(groupNumber);
		}
		return false;	
	}

	@Override
	public String getErrorMessage() {
		return "Dwa G kody z tej samej grupy w tym samym bloku";
	}

	@Override
	public String getExtendedMessage() {
		return "Tylko jeden kod z danej grupy mo�e znajdowa� si� w jednym bloku. "
				+ " <br>Np. blok N100 G1 G0 Z-10 F200 mo�e spowodowa� kolizje bo ruch roboczy G1 mo�e by� tutaj nadpisany przez ruch szybki G0 je�li sterownik nie wywo�a komukatu o b��dzie";
	}

}
