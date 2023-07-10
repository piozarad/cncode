package NcStateWarnings;

import CordConverter.NcState;

public class NoBaseDeclaredAfterToolChangeWarning implements NcStateWarnings {

	@Override
	public boolean checkNcState(NcState ncState) {
		return (ncState.getActiveGCodes().getActiveGCodeInGroup(1)!=0 || ncState.getActiveGCodes().getActiveGCodeInGroup(9)!=80) && !ncState.isBaseDeclared();
	}

	@Override
	public String getErrorMessage() {
		return "Nie zadeklarowano bazy po zmianie narzêdzia";
		
	}

}
