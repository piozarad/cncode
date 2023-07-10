package ErrorWindow;

import java.util.ArrayList;
import java.util.List;

import CordConverter.Options;
import CordConverter.Wind;
import InvalidFunctionsError.*;
import NcStateErrors.*;
import NcStateWarnings.*;
import syntaxError.*;


public class ErrorDatabase {
	
	private List<NcStateWarnings> warnings;
	private List<BadNcState> ncErrors;
	private List<InvalidFunctionError> functionErrors;
	private List<SyntaxError> syntaxErrors;
	
	
	//TODO dopisac opcje dodawania z panelu opcji bledow do sprawdzenia przez program
	private final Options options;
	
	public ErrorDatabase(final Options options)
	{
		this.options=options;
		
		//initialize warnings
		warnings = new ArrayList<>();
		warnings.add(new DDoesntMatchToolNumberWarning());
		warnings.add(new HDoesntMatchToolNumberWarning());
		warnings.add(new M1WithoutM5Warning());
		warnings.add(new M4Warning());
		warnings.add(new NoBaseDeclaredAfterToolChangeWarning());
		warnings.add(new NoCoolantWarning());
		warnings.add(new WrongToolPreparationWarning());
		if(Wind.options.isbRotationCheck())
			warnings.add(new NoBDeclaredAfterToolChange());
		if(Wind.options.isG90Check())
			warnings.add(new G90ActiveWarning());
		if(Wind.options.isToolPreperationCheck())
			warnings.add(new NoToolPreparationWarning());
		
		//initialize nc errors
		ncErrors = new ArrayList<>();
		ncErrors.add(new BadCircleEndPointError());
		ncErrors.add(new CircleParametersNotDeclaredError());
		ncErrors.add(new G1WithoutFeedError());
		ncErrors.add(new HNotDefinedError());
		ncErrors.add(new DNotDefinedError());
		ncErrors.add(new NextToolNumberSameAsActualError());
		ncErrors.add(new NoBasePointDeclared());
		ncErrors.add(new NoG40Error());
		ncErrors.add(new NoG80NcError());
		
		//initialize invalid function list
		functionErrors = new ArrayList<>();
		functionErrors.add(new G41WithG3G2Error());
		functionErrors.add(new GCodeGroupError());
		functionErrors.add(new InvalidG4FunctionBlock());
		
		//initialize syntax errors
		syntaxErrors = new ArrayList<>();
		syntaxErrors.add(new DoubleACodeInSameBlockError());
		syntaxErrors.add(new DoubleBCodeInSameBlockError());
		syntaxErrors.add(new DoubleCCodeInSameBlockError());
		syntaxErrors.add(new DoubleDCodeInSameBlockError());
		syntaxErrors.add(new DoubleFCodeInSameBlockError());
		syntaxErrors.add(new DoubleHCodeInSameBlockError());
		syntaxErrors.add(new DoubleMCodeError());
		syntaxErrors.add(new DoubleNCodeInSameBlockError());
		syntaxErrors.add(new DoublePCodeInSameBlockError());
		syntaxErrors.add(new DoubleQCodeInSameBlockError());
		syntaxErrors.add(new DoubleSCodeInSameBlockError());
		syntaxErrors.add(new DoubleICodeInSameBlockError());
		syntaxErrors.add(new DoubleJCodeInSameBlockError());
		syntaxErrors.add(new DoubleKCodesInSameBlockError());
		syntaxErrors.add(new DoubleTCodeError());
		syntaxErrors.add(new NumberWithoutCodeError());
		syntaxErrors.add(new SlashNotAtBeginningError());
		syntaxErrors.add(new SWithDotError());
		syntaxErrors.add(new MultipleBracketsError());
		syntaxErrors.add(new LowerCaseCharactererror());
		
		
	}

	public List<NcStateWarnings> getWarningsList()
	{
		return this.warnings;
	}
	public List<BadNcState> getNcStateErrorsList()
	{
		return this.ncErrors;
	}
	public List<SyntaxError> getSyntaxErrorList()
	{
		return this.syntaxErrors;
	}
	public List<InvalidFunctionError> getInvalidFunctionErrorList()
	{
		return this.functionErrors;
	}

}
