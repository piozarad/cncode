package NcStateErrors;

import CordConverter.NcState;

public class NoBasePointDeclared implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		return state.getActiveGCodes().getActiveGCodeInGroup(1)!=0 && state.getActiveGCodes().getActiveGCodeInGroup(14)==53;
	}

	@Override
	public String getErrorMessage() {
		return "Brak wczytanego punktu bazowego";
	}

	@Override
	public String getExtendedMessage() {
		return "Zdefiniuj punkt bazowy dla obróbki";
	}
}
