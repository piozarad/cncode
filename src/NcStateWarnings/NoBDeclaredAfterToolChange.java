package NcStateWarnings;

import CordConverter.NcState;

public class NoBDeclaredAfterToolChange implements NcStateWarnings {

	@Override
	public String getErrorMessage() {
		return "Nie zadeklarowano obrotu B po zmianie narzêdzia";
	}

	@Override
	public boolean checkNcState(NcState ncState) {
		return !ncState.isBPreparationSet() && (ncState.getActiveGCodes().getActiveGCodeInGroup(1)!=0 || ncState.getActiveGCodes().getActiveGCodeInGroup(9)!=80);
	}

}
