package NcStateWarnings;

import CordConverter.NcState;

public class HDoesntMatchToolNumberWarning implements NcStateWarnings {

	@Override
	public boolean checkNcState(NcState ncState) {
		try
		{
			int h=Integer.parseInt(ncState.getH());
			
			if(h==0)
				return false;
			else return  h!=ncState.getActualToolNumber();
			
		}
		
		catch(NumberFormatException e)
		{
			return false;
		}
		
	}

	@Override
	public String getErrorMessage() {
		return "Numer korekcji H niezgodny z numerem narzêdzia";
	}

}
