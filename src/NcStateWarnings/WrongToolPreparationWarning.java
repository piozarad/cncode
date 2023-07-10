package NcStateWarnings;

import CordConverter.NcState;

public class WrongToolPreparationWarning implements NcStateWarnings {

	@Override
	public boolean checkNcState(NcState ncState) {
		
		return (ncState.getmFunctionActive().contains(6) &&(ncState.getPreviousNcState().getNextToolNumber()!=-1 ) && (ncState.getPreviousNcState().getNextToolNumber() != ncState.getActualToolNumber()));
	}

	@Override
	public String getErrorMessage() {
		return "Przygotowano z³y numer narzêdzia";
	}

}
