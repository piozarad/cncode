package NcStateErrors;

import CordConverter.NcState;
import NcStateWarnings.NcStateWarnings;

public class DNotDefinedError implements BadNcState {


	@Override
	public boolean findError(NcState state) {
		return state.getActiveGCodes().getActiveGCodeInGroup(7)!=40 && state.getD().equals("0");
	}

	@Override
	public String getExtendedMessage() {
		return "Korekcja D musi zostaæ zdefiniowana w czasie wykowynwania kodu G41/G42";
	}

	@Override
	public String getErrorMessage() {
		return "Nie zadeklarowano korekcji D";
	}

}
