package NcStateErrors;

import CordConverter.NcState;

public class G1WithoutFeedError implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		return state.getActiveGCodes().getActiveGCodeInGroup(1) !=0 && state.getFeed()<=0;
		
	}

	@Override
	public String getErrorMessage() {
		return "Polecenie ruchu roboczego bez podanego posuwu";
	}

	@Override
	public String getExtendedMessage() {
		return "Podaj posuw dla ruchu roboczego";
	}

}
