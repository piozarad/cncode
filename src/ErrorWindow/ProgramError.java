package ErrorWindow;

import InvalidFunctionsError.InvalidFunctionError;
import NcStateErrors.BadNcState;
import NcStateWarnings.NcStateWarnings;
import syntaxError.SyntaxError;

public class ProgramError {

	
	private final int lineNumber;
	private final CordConverter.Error error;
	private final String errorType;
	
	
	public ProgramError(final int lineNumber, final CordConverter.Error error)
	{
		this.lineNumber=lineNumber;
		this.error=error;
		
		
		if(error instanceof SyntaxError)
		{
			errorType = "Sk³daniowy";
		}
		
		else if(error instanceof InvalidFunctionError)
		{
			errorType = "Niepoprawna funckcja";
		}
		else if(error instanceof BadNcState)
		{
			errorType = "Niedozwolony stan";
		}
		else if(error instanceof NcStateWarnings)
		{
			errorType = "Ostrze¿enie";
		}
		else errorType = "Nieznany b³¹d";

	}

	public int getLineNumber()
	{
		return lineNumber;
	}
	
	public String getErrorTypeMessage()
	{
		return this.errorType;
	}
	
	@Override public String toString()
	{
		return "[" + errorType+ "]"+this.error.getErrorMessage();
	}
}
