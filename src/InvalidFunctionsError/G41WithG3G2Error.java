package InvalidFunctionsError;

import CordConverter.Function;

public class G41WithG3G2Error implements InvalidFunctionError {

	@Override
	public boolean findError(Function f) {
		return (f.getFunctionType().contains(41) ||f.getFunctionType().contains(42)  ||f.getFunctionType().contains(40)) && (f.getFunctionType().contains(2) ||f.getFunctionType().contains(3));
	}

	@Override
	public String getErrorMessage() {
		return "Najazd na korekcje w czasie interpolacji";
	}

	@Override
	public String getExtendedMessage() {
		return "Wczytanie korekcji promieniowej G41/G42 nie mo¿e siê odbywaæ w czasie interpolacji ko³owej G2/G3. Zamiast tego u¿yæ kodu G1";
	}

}
