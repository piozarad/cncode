package NcStateErrors;

import CordConverter.NcState;

public class NextToolNumberSameAsActualError implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		
		return !state.getmFunctionActive().contains(6) && (state.getActualToolNumber() == state.getNextToolNumber());
	}

	@Override
	public String getErrorMessage() {
		return "Number narz�dzia do wywo�ania z magazynu jest taki sam jak aktualnego narz�dzia";
	}

	@Override
	public String getExtendedMessage() {
		return "Zmien numer narz�dzia kt�re ma by� podstawione w magazynie narzedzi jako nast�pne";
	}

}
