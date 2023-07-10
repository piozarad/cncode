package NcStateErrors;

import CordConverter.NcState;

public class NoG80NcError implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		return (state.getActiveGCodes().getActiveGCodeInGroup(9)!=80 && state.getActiveGCodes().getActiveGCodeInGroup(1)!=-1 );
		
	}

	@Override
	public String getErrorMessage() {
		return "Nie odwo�ano cyklu sta�ego komend� G80";
	}

	@Override
	public String getExtendedMessage() {
		return "W przypadku nie odwo�ania cyklu komend� G80 nast�pne bloki bed� odczytywane tak jakby nale�a�y do tego cyklu co mo�e prowadzi� do kolizji."
				+ "<br> dodaj kod G80 na ko�cu cyklu sta�ego aby go zako�czy�";
	}

}
