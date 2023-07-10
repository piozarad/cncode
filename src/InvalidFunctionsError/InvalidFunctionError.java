package InvalidFunctionsError;

import CordConverter.Function;

public interface InvalidFunctionError extends CordConverter.Error{
		
	public boolean findError(Function f);	
	
	public String getExtendedMessage();
}
