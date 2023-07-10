package NcStateWarnings;

import CordConverter.NcState;

public class NoCoolantWarning implements NcStateWarnings {

	@Override
	public boolean checkNcState(NcState ncState) {
		return ncState.getmFunctionActive().contains(9) &&(ncState.getActiveGCodes().getActiveGCodeInGroup(1)!=0 || ncState.getActiveGCodes().getActiveGCodeInGroup(9)!=80 );
	}

	@Override
	public String getErrorMessage() {
		return "Ruch roboczy bez w³¹czonego ch³odzenia";
	}

}
