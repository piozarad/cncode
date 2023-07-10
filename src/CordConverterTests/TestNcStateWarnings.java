package CordConverterTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import CordConverter.Function;
import CordConverter.NcState;
import NcStateErrors.BadNcState;
import NcStateErrors.DNotDefinedError;
import NcStateWarnings.DDoesntMatchToolNumberWarning;
import NcStateWarnings.G90ActiveWarning;
import NcStateWarnings.HDoesntMatchToolNumberWarning;
import NcStateWarnings.M1WithoutM5Warning;
import NcStateWarnings.M4Warning;
import NcStateWarnings.NcStateWarnings;
import NcStateWarnings.NoBDeclaredAfterToolChange;
import NcStateWarnings.NoBaseDeclaredAfterToolChangeWarning;
import NcStateWarnings.NoCoolantWarning;
import NcStateWarnings.NoToolPreparationWarning;
import NcStateWarnings.WrongToolPreparationWarning;



public class TestNcStateWarnings {

	Function f1;
	Function f2;
	Function f3;
	Function f4;
	Sterowanie fanuc = new SterowanieFanuc();
	NcState ncState = new NcState(fanuc);
	NcStateWarnings warning;
	
	@Test
	public void testM4()
	{
		f1 = new Function("S4000 M4");
		ncState.updateNcState(f1);
		
		warning = new M4Warning();
		
		assertTrue(warning.checkNcState(ncState));
	}
	

	@Test
	public void testM1WithM5()
	{
		f1 = new Function("S4000 M4");
		f2 = new Function("M1");
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		
		warning = new M1WithoutM5Warning();
		
		assertTrue(warning.checkNcState(ncState));
	}
	
	
	@Test
	public void testNoToolPreparationWarning()
	{
		f1 = new Function("T1 M6");
		f2 = new Function("T2 M6");
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		
		warning = new NoToolPreparationWarning();
		
		assertTrue(warning.checkNcState(ncState));
		
	}
	
	@Test
	public void testnoBPreparationWarning()
	{
		f1 = new Function("T1 M6");
		f2 = new Function("G1 X200");
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		
		warning = new NoBAxisPreparationWarning();
		
		assertTrue(warning.checkNcState(ncState));

	}
	@Test
	public void testnoBPreparationWarningI()
	{
		f1 = new Function("T1 M6");
		f2 = new Function("G0 B100");
		f3 = new Function("G1 X200");
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		
		warning = new NoBAxisPreparationWarning();
		
		assertFalse(warning.checkNcState(ncState));

	}
	
	@Test
	public void hDoesntMatchToolNumberWarning()
	{
		f1 = new Function("T1 M6");
		f2 = new Function("G0 G43 H1 Z400");
		f3 = new Function("G0 G43 H2 Z400");
		warning = new HDoesntMatchToolNumberWarning();
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		
		assertFalse(warning.checkNcState(ncState));

		ncState.updateNcState(f3);
		assertTrue(warning.checkNcState(ncState));
	}
	
	
	@Test
	public void dDoesntMatchToolNumberWarning()
	{
		f1 = new Function("T1 M6");
		f2 = new Function("G0 G41 D1 Z400");
		f3 = new Function("G0 G41 D2 Z400");
		warning = new DDoesntMatchToolNumberWarning();
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		
		assertFalse(warning.checkNcState(ncState));

		ncState.updateNcState(f3);
		assertTrue(warning.checkNcState(ncState));
	}
	
	@Test
	public void noColantWarningI()
	{
		f1 = new Function("M8");
		f2 = new Function("G1 Z20");
		f3 = new Function("M9");
		warning = new NoCoolantWarning();
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		
		assertFalse(warning.checkNcState(ncState));

		ncState.updateNcState(f3);
		ncState.updateNcState(f2);
		assertTrue(warning.checkNcState(ncState));
	}
	
	@Test
	public void noColantWarninginFixedCycle()
	{
		f1 = new Function("M8");
		f2 = new Function("G81 R100 Z-10 F50");
		
		warning = new NoCoolantWarning();
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		
		assertFalse(warning.checkNcState(ncState));

	}
	
	@Test
	public void noColantWarninginFixedCycleII()
	{
		f1 = new Function("M9");
		f2 = new Function("G81 R100 Z-10 F50");
		
		warning = new NoCoolantWarning();

		ncState.updateNcState(f1);
		ncState.updateNcState(f2);

		assertTrue(warning.checkNcState(ncState));

	}
	
	@Test
	public void noColantWarninginFixedCycleAfterToolChange()
	{
		f1 = new Function("M8");
		f2 = new Function("T1 M6");
		f3 = new Function("G81 R100 Z-10 F50");
		
		warning = new NoCoolantWarning();

		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		
		assertTrue(warning.checkNcState(ncState));

	}

	@Test
	public void testNoBaseDeclaredAfterToolChangeWarningI()
	{
		f1 = new Function("T1 M6");
		f2 = new Function("G54");
		f3 = new Function("G1 X0. Y50.5");
	
		warning = new NoBaseDeclaredAfterToolChangeWarning();

		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
	
		
		assertFalse(warning.checkNcState(ncState));
	}
	
	
	@Test
	public void testG90ActiveWarning()
	{
		f1= new Function("G90 G54 G0");
		f2 = new Function("S2000 M3");
		f3 = new Function("G0 Z400");
		
		warning = new G90ActiveWarning();
		
		
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		assertFalse(warning.checkNcState(ncState));

	}
	

	@Test
	public void testG90ActiveWarningII()
	{
		f1= new Function("G91 G54 G0");
		f2 = new Function("S2000 M3");
		f3 = new Function("G0 Z400");
		
		warning = new G90ActiveWarning();
		
		
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		assertTrue(warning.checkNcState(ncState));
		
		
		
	}
	
	@Test
	public void NoBDeclaredAfterToolChangeI()
	{
		f1= new Function("T1 M6");
		f2 = new Function("G91 G54 G0");
		f3 = new Function("G1 Z400 F100");
		
		warning = new NoBDeclaredAfterToolChange();
			
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		assertTrue(warning.checkNcState(ncState));
	}
	
	@Test
	public void NoBDeclaredAfterToolChangeII()
	{
		f1= new Function("T1 M6");
		f2 = new Function("G91 G54 G0");
		f3 = new Function("G0 Z400 F100");
		
		warning = new NoBDeclaredAfterToolChange();
			
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		assertFalse(warning.checkNcState(ncState));
	}
	
	@Test
	public void NoBDeclaredAfterToolChangeIII()
	{
		f1= new Function("T1 M6");
		f2 = new Function("G91 G54 G0");
		f3 = new Function("G0 B0");
		f4 = new Function("G81 R10 Z0 F500");
		
		warning = new NoBDeclaredAfterToolChange();
			
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		ncState.updateNcState(f4);
		assertFalse(warning.checkNcState(ncState));
	}
	
	@Test
	public void NoBDeclaredAfterToolChangeIV()
	{
		f1= new Function("T1 M6");
		f2 = new Function("G91 G54 G0");
		f3 = new Function("S2000 M3");
		f4 = new Function("G81 R10 Z0 F500");
		
		warning = new NoBDeclaredAfterToolChange();
			
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		ncState.updateNcState(f4);
		assertTrue(warning.checkNcState(ncState));
	}
	
	@Test
	public void NoBDeclaredAfterToolChangeV()
	{
		f1= new Function("T1 M6");
		f2 = new Function("G91 G54 G0");
		f3 = new Function("S2000 M3");
		f4 = new Function("G3 X10 Y10 I0 J0 F50");
		
		warning = new NoBDeclaredAfterToolChange();
			
		ncState.updateNcState(f1);
		ncState.updateNcState(f2);
		ncState.updateNcState(f3);
		ncState.updateNcState(f4);
		assertTrue(warning.checkNcState(ncState));
	}
	
	
	
}
