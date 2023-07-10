package NcStateWarnings;

import CordConverter.NcState;

public class M1WithoutM5Warning implements NcStateWarnings{

	@Override
	public boolean checkNcState(NcState ncState) {
		return ncState.getmFunctionActive().contains(1) && !ncState.getmFunctionActive().contains(5);
	}

	@Override
	public String getErrorMessage() {
		return "Postój warunkowy M1 bez wy³acznia obrotów wrzeciona";
	}

}
