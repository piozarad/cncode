package InvalidFunctionsError;

import CordConverter.Function;

public class InvalidG4FunctionBlock implements InvalidFunctionError{

	@Override
	public boolean findError(Function f) {
		return f.containsFunction(4) && f.getFunctionType().size()>1;
		
	}

	@Override
	public String getErrorMessage() {
		return "W bloku z kodem G4 nie moze siê znalezc ¿aden inny kod G";
	}

	@Override
	public String getExtendedMessage() {
		return "W przypadku u¿ycia kodu G4 powoduj¹cego postój czasowy w bloku nie mo¿e znajdowaæ siê ¿aden inny kod oprócz czasu postoju<br>Sterownik maszyny powinien wyœwietliæ kominukat o b³edzie.";
	}

}
