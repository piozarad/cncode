package CordConverterTests;
import org.junit.Test;
import static org.junit.Assert.*;
import CordConverter.Function;
import InvalidFunctionsError.G41WithG3G2Error;
import InvalidFunctionsError.GCodeGroupError;
import InvalidFunctionsError.InvalidG4FunctionBlock;

public class TestInvalidFunctionError {

	@Test
	public void testInvalidG4Code()
	{
		Function badFunction = new Function("G4 G0 X2");
		Function function = new Function("G4 P1000");
		
		InvalidG4FunctionBlock error = new InvalidG4FunctionBlock();
		
		assertTrue(error.findError(badFunction));
		assertFalse(error.findError(function));
		
	}
	
	
	@Test
	public void testGCodeGroupError()
	{
		Function badFunction = new Function("G1 G0 Z400");
		Function function = new Function("G90 G54 G0 Z400");
		
		GCodeGroupError error = new GCodeGroupError();
		
		assertTrue(error.findError(badFunction));
		assertFalse(error.findError(function));
	}
	
	

	@Test
	public void testG41WithG3G2Error()
	{
		Function badFunction = new Function("G1 G41 Z400");
		Function function = new Function("G3 G42 G0 Z400");
		
		G41WithG3G2Error error = new G41WithG3G2Error();
		
		assertFalse(error.findError(badFunction));
		assertTrue(error.findError(function));
	}
	@Test
	public void testG40WithG3G2Error()
	{
		Function badFunction = new Function("G1 G41 Z400");
		Function function = new Function("G3 G40 G0 Z400");
		
		G41WithG3G2Error error = new G41WithG3G2Error();
		
		assertFalse(error.findError(badFunction));
		assertTrue(error.findError(function));
	}
	
}
