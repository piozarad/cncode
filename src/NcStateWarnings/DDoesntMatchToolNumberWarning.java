package NcStateWarnings;

import CordConverter.NcState;

public class DDoesntMatchToolNumberWarning implements NcStateWarnings {

	@Override
	public boolean checkNcState(NcState ncState) {
		try
		{
			int d=Integer.parseInt(ncState.getD());
			
			if(d==0)
				return false;
			else return  d!=ncState.getActualToolNumber();
			
		}
		
		catch(NumberFormatException e)
		{
			return false;
		}
	}

	@Override
	public String getErrorMessage() {
		return "Korekcja D niezgodna z numerem narzêdzia";
	}




}
