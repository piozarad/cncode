package NcStateWarnings;

import CordConverter.NcState;

public class M4Warning implements NcStateWarnings {

	@Override
	public boolean checkNcState(NcState ncState) {
		if(ncState.getmFunctionActive().contains(4))
			return true;
		else return false;
	}

	@Override
	public String getErrorMessage() {
		return "Lewe obroty wrzeciona";
	}

}
