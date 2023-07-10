package NcStateErrors;

import CordConverter.NcState;

public class NoG40Error implements BadNcState {

	@Override
	public boolean findError(NcState state) {
		return (state.getmFunctionActive().contains(6) || state.getmFunctionActive().contains(30)) && state.getPreviousNcState().getActiveGCodes().getActiveGCodeInGroup(7)!=40;
			
	}

	@Override
	public String getErrorMessage() {
		return "Brak wyj�cia z korekcji G40";
	}

	@Override
	public String getExtendedMessage() {
		return "Brak wyj�cia z korekcji wywo�uje b��d na wi�kszo�ci sterownik�w lub prowadzi do trudnych do wykrycia b�ed�w"
				+ "<br>Dopisz na ko�cu pracy narz�dzia kod G1 G40 X Y";
	}

}
