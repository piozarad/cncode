package NcStateWarnings;

import CordConverter.NcState;

public class G90ActiveWarning implements NcStateWarnings {

	@Override
	public String getErrorMessage() {
		return "Aktywne programowanie przyrostowe G91";
	}

	@Override
	public boolean checkNcState(NcState ncState) {
		return ncState.getActiveGCodes().getActiveGCodeInGroup(3)==91 && ( ncState.getmFunctionActive().contains(3) || ncState.getmFunctionActive().contains(4));
	}

}
