package NcStateErrors;

import CordConverter.NcState;

public class NextToolNumberSameAsActualError implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		
		return !state.getmFunctionActive().contains(6) && (state.getActualToolNumber() == state.getNextToolNumber());
	}

	@Override
	public String getErrorMessage() {
		return "Number narzêdzia do wywo³ania z magazynu jest taki sam jak aktualnego narzêdzia";
	}

	@Override
	public String getExtendedMessage() {
		return "Zmien numer narzêdzia które ma byæ podstawione w magazynie narzedzi jako nastêpne";
	}

}
