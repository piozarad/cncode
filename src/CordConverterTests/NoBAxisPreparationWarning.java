package CordConverterTests;

import CordConverter.NcState;
import NcStateWarnings.NcStateWarnings;

public class NoBAxisPreparationWarning implements NcStateWarnings {

	@Override
	public boolean checkNcState(NcState ncState) {
		return !ncState.isBPreparationSet() && (ncState.getActiveGCodes().getActiveGCodeInGroup(1)!=0);
	}


	@Override
	public String getErrorMessage() {
		return "Brak komendy B po obrocie sto³u";
	}

}
