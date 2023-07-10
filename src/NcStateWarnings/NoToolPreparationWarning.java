package NcStateWarnings;

import CordConverter.NcState;

public class NoToolPreparationWarning implements NcStateWarnings {

	@Override
	public String getErrorMessage() {
		return "Brak przygotowania narzêdzia";
	}

	@Override
	public boolean checkNcState(NcState ncState) {
		return ncState.getmFunctionActive().contains(6) && !ncState.getPreviousNcState().getToolPreparation() &&(ncState.getActualToolNumber() != ncState.getPreviousNcState().getActualToolNumber());
	}

}
