package NcStateErrors;

import CordConverter.NcState;

public class NoG80NcError implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		return (state.getActiveGCodes().getActiveGCodeInGroup(9)!=80 && state.getActiveGCodes().getActiveGCodeInGroup(1)!=-1 );
		
	}

	@Override
	public String getErrorMessage() {
		return "Nie odwo³ano cyklu sta³ego komend¹ G80";
	}

	@Override
	public String getExtendedMessage() {
		return "W przypadku nie odwo³ania cyklu komend¹ G80 nastêpne bloki bed¹ odczytywane tak jakby nale¿a³y do tego cyklu co mo¿e prowadziæ do kolizji."
				+ "<br> dodaj kod G80 na koñcu cyklu sta³ego aby go zakoñczyæ";
	}

}
