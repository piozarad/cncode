package NcStateErrors;

import CordConverter.NcState;

public class NoG40Error implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		return (state.getmFunctionActive().contains(6) || state.getmFunctionActive().contains(30)) && state.getPreviousNcState().getActiveGCodes().getActiveGCodeInGroup(7)!=40;
			
	}

	@Override
	public String getErrorMessage() {
		return "Brak wyjœcia z korekcji G40";
	}

	@Override
	public String getExtendedMessage() {
		return "Brak wyjœcia z korekcji wywo³uje b³¹d na wiêkszoœci sterowników lub prowadzi do trudnych do wykrycia b³edów"
				+ "<br>Dopisz na koñcu pracy narzêdzia kod G1 G40 X Y";
	}

}
