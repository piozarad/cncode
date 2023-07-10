package CordConverterTests;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import CordConverter.Function;
import CordConverter.NcState;
import CordConverter.Options;
import CordConverter.Wind;
import NcStateErrors.BadCircleEndPointError;
import NcStateErrors.BadNcState;
import NcStateErrors.CircleParametersNotDeclaredError;
import NcStateErrors.DNotDefinedError;
import NcStateErrors.G1WithoutFeedError;
import NcStateErrors.HNotDefinedError;
import NcStateErrors.NextToolNumberSameAsActualError;
import NcStateErrors.NoBasePointDeclared;
import NcStateErrors.NoG40Error;
import NcStateErrors.NoG80NcError;
public class TestNcStateErrors {

	@Test
	public void testFunction()
	{
		Function f = new Function("G0 G1");
		
		assertTrue(f.containsFunction(0) && f.containsFunction(1));
	}
	
	@Test
	public void testG80Error()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f= new Function("G81 R100 Z-10 F1000",st);
		Function f2 = new Function("G80");
		Function f3 = new Function("G1 Z400");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f3);
		
		NoG80NcError error =new NoG80NcError();
		assertTrue(error.findError(badState));
		
		NcState goodState = new NcState(st);
		goodState.updateNcState(f);
		goodState.updateNcState(f2);
		goodState.updateNcState(f3);
		assertFalse(error.findError(goodState));
		
	}
	@Test
	public void testG1WithoutSError()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f= new Function("S2000 M3",st);
		Function f2 = new Function("M5");
		Function f3 = new Function("G1 Z400");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		badState.updateNcState(f3);
		
		BadNcState error =new G1WithoutFeedError();
		assertTrue(error.findError(badState));
		
	}
	@Test
	public void testG1WithoutSErrorII()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f= new Function("S2000 M3",st);
		Function f2 = new Function("M19");
		Function f3 = new Function("G1 Z400");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		badState.updateNcState(f3);
		
		BadNcState error =new G1WithoutFeedError();
		assertTrue(error.findError(badState));
	}
	 
	@Test
	public void testNoG40Error() {
	 Sterowanie st= new SterowanieFanuc();
		Function f= new Function("S2000 M3",st);
		Function f2 = new Function("G41 G1 X0 Y10.");
		Function f3 = new Function("T2 M6");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		badState.updateNcState(f3);
		
		BadNcState error =new NoG40Error();
		assertTrue(error.findError(badState));
		
	}
	
	@Test
	public void testHNotDefinedError() {
	 Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2 = new Function("G1 X0 Y10.");
		
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		
		
		BadNcState error =new HNotDefinedError();
		assertTrue(error.findError(badState));
		
	}
	
	@Test
	public void testHNotDefinedErrorII() {
	 Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2 = new Function("G1 G43 H1 X0 Y10.");
		
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		
		
		BadNcState error =new HNotDefinedError();
		assertFalse(error.findError(badState));
		
	}
	
	
	
	
	@Test
	public void testNextToolNumberSameAsActualError()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2 = new Function("T1");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		
	
		BadNcState error =new NextToolNumberSameAsActualError();
		assertTrue(error.findError(badState));
		
		
	}
	@Test
	public void testNextToolNumberSameAsActualErrorII()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2 = new Function("T3");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		
	
		BadNcState error =new NextToolNumberSameAsActualError();
		assertFalse(error.findError(badState));
		
		
	}
	@Test
	public void testNextToolNumberSameAsActualErrorIII()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2 = new Function("G0 Z400");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		
	
		BadNcState error =new NextToolNumberSameAsActualError();
		assertFalse(error.findError(badState));
		
		
	}
	
	
	@Test
	public void testNoBasePointDeclared()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2 = new Function("G1 X200");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		
	
		BadNcState error =new NoBasePointDeclared();
		assertTrue(error.findError(badState));
	}
	
	@Test
	public void testNoBasePointDeclaredII()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2= new Function("G90 G54 G0",st);
		Function f3 = new Function("G1 X200");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		badState.updateNcState(f3);
		
		BadNcState error =new NoBasePointDeclared();
		assertFalse(error.findError(badState));
	}
	
	
	@Test
	public void testBadCirclePointError()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f1 = new Function("T1 M6",st);
		Function f2 = new Function("G1 X0. Y0.");
		Function f3 = new Function("G3 X10. Y10. I0. J10. F200.");
	
		BadNcState error= new BadCircleEndPointError();
		NcState state = new NcState(st);

		state.updateNcState(f1);
		state.updateNcState(f2);
		state.updateNcState(f3);
		
		assertFalse(error.findError(state));
	}
	
	@Test
	public void testBadCircleEndPointErrorI()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f1 = new Function("T1 M6",st);
		Function f2 = new Function("G1 X100. Y12.5");
		Function f3 = new Function("G2 X1000. Y10. I2. J10. F200.");
	
		BadNcState error= new BadCircleEndPointError();
		NcState state = new NcState(st);
		Wind.options = new Options();
		Wind.options.setCircleTolerance(0.05f);
		
		state.updateNcState(f1);
		state.updateNcState(f2);
		state.updateNcState(f3);

		assertTrue(error.findError(state));
	}
	
	@Test
	public void testCircleParametersNotDeclaredErrorITestIJ()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f1 = new Function("T1 M6",st);
		Function f2 = new Function("G2 X100. Y12.5 I10.5 J52.2");
	
		BadNcState error= new CircleParametersNotDeclaredError();
		NcState state = new NcState(st);
		
		state.updateNcState(f1);
		state.updateNcState(f2);
		

		assertFalse(error.findError(state));
	}
	
	@Test
	public void testCircleParametersNotDeclaredErrorIITestIJ()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f1 = new Function("T1 M6",st);
		Function f2 = new Function("G2 X100. Y12.5 J52.2");
	
		BadNcState error= new CircleParametersNotDeclaredError();
		NcState state = new NcState(st);
		
		state.updateNcState(f1);
		state.updateNcState(f2);

		assertTrue(error.findError(state));
	}
	
	@Test
	public void testCircleParametersNotDeclaredErrorITestIK()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f1 = new Function("T1 M6 G18",st);
		Function f2 = new Function("G3 X100. Y12.5 I52.2 K5.");
	
		BadNcState error= new CircleParametersNotDeclaredError();
		NcState state = new NcState(st);
		
		state.updateNcState(f1);
		state.updateNcState(f2);

		assertFalse(error.findError(state));
	}
	
	@Test
	public void testCircleParametersNotDeclaredErrorIITestIK()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f1 = new Function("T1 M6 G18",st);
		Function f2 = new Function("G3 X100. Y12.5 I52.2");
	
		BadNcState error= new CircleParametersNotDeclaredError();
		NcState state = new NcState(st);
		
		state.updateNcState(f1);
		state.updateNcState(f2);

		assertTrue(error.findError(state));
	}
	
	@Test
	public void testCircleParametersNotDeclaredErrorITestJK()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f1 = new Function("T1 M6 G19",st);
		Function f2 = new Function("G3 X100. Y12.5 J52.2 K5.1");
	
		BadNcState error= new CircleParametersNotDeclaredError();
		NcState state = new NcState(st);
		
		state.updateNcState(f1);
		state.updateNcState(f2);

		assertFalse(error.findError(state));
	}
	@Test
	public void testCircleParametersNotDeclaredErrorIITestJK()
	{
		Sterowanie st= new SterowanieFanuc();
		Function f1 = new Function("T1 M6 G19",st);
		Function f2 = new Function("G3 X100. Y12.5 I52.2");
	
		BadNcState error= new CircleParametersNotDeclaredError();
		NcState state = new NcState(st);
		
		state.updateNcState(f1);
		state.updateNcState(f2);

		assertTrue(error.findError(state));
	}
	@Test
	public void testDNotDefinedError() {
	 Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2 = new Function("G1 G41 X0 Y10.");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		
		BadNcState	error  =new DNotDefinedError();
		assertTrue(error.findError(badState));
		
	}
	
	@Test
	public void testDNotDefinedErrorII() {
	 Sterowanie st= new SterowanieFanuc();
		Function f= new Function("T1 M6",st);
		Function f2 = new Function("G1 G41 D1 X0 Y10.");
		
		NcState badState = new NcState(st);
		badState.updateNcState(f);
		badState.updateNcState(f2);
		
		
		BadNcState warning =new DNotDefinedError();
		assertFalse(warning.findError(badState));
		
	}
	
}
