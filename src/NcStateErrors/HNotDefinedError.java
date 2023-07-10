package NcStateErrors;

import CordConverter.NcState;

public class HNotDefinedError implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		return state.getH().equals("0") && state.getActiveGCodes().getActiveGCodeInGroup(1)!=0;
	}

	@Override
	public String getErrorMessage() {
		return "Nie zdefiniowano korekcji H dla tego narz�dzia";
	}

	@Override
	public String getExtendedMessage() {
		return "Zdefiniuj korektor d�ugo�ciowy dla aktualnego narz�dzia zgodnie z danym sterowaniem"
				+ "<br> np G1 G43 H1 Z400 (Fanuc)"
				+ "<br> lub G56 HA (Okuma)"
				+ "<br> D1 (Sinumeric)";
			
	}

	
}
