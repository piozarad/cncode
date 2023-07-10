package CordConverterTests;


import org.junit.Test;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import BasicControls.SterowanieOkuma;
import CordConverter.Function;
import CordConverter.NcState;
import CordConverter.Point;

import static org.junit.Assert.*;


public class NcStateTests {

	@Test
	public void testCopyConstructor()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G90 G54 G0 X0.1 Y10.2 P10"));
		NcState secondState = new NcState(firstState);
		
		
		assertEquals(firstState.getP(), secondState.getP());
		assertTrue(firstState.getDestinationPoint().equals(secondState.getDestinationPoint()));
		
	}
	
	@Test
	public void testQ()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G98 G76 R10. Z-2.5 Q0.1"));
		
		assertEquals(0.1,firstState.getQ(),0.01);
	}
	@Test
	public void testRFanuc()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G98 G76 R10.2 Z-2.5 Q0.1"));
		
		assertEquals(10.2,firstState.getiJk()[3],0.001);
	}
	
	@Test
	public void testP()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G4 P4"));
		
		assertEquals("4",firstState.getP());
	}
	
	@Test
	public void testPFanuc()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G54.1 P1"));
		
		assertEquals("1",firstState.getP());
	}
	
	@Test
	public void testPoint()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G98 G76 R10.2 X101.15 Y-12.44 Z-2.5 Q0.1"));
		
		assertEquals(firstState.getDestinationPoint(),new Point(101.15f,-12.44f,-2.5f));
	}
	@Test
	public void testFeed()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G98 G76 R10.2 X101.15 Y-12.44 Z-2.5 Q0.1 F500"));
		
		assertEquals(500f,firstState.getFeed(),0.01);
	}
	@Test
	public void testSpeed()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 S2000 M3"));
		
		
		assertEquals(firstState.getSprindleSpeed(),2000);
	}
	
	@Test
	public void testD()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G41 D3 X10."));
		
		
		assertEquals("3",firstState.getD());
	}
	@Test
	public void testH()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		NcState firstState = new NcState(fanuc);
		firstState.updateNcState(new Function("N90 G43 H14 X10."));
		
		
		assertEquals("14",firstState.getH());
	}
	
	@Test
	public void testHOkuma()
	{
		Sterowanie okuma = new SterowanieOkuma();
		NcState firstState = new NcState(okuma);
		firstState.updateNcState(new Function("G56 HA DA"));
		
		
		assertEquals("A",firstState.getH());
	}
	
	@Test
	public void testDOkuma()
	{
		Sterowanie okuma = new SterowanieOkuma();
		NcState firstState = new NcState(okuma);
		firstState.updateNcState(new Function("G56 HA DB"));
		
		
		assertEquals("B",firstState.getD());
	}
	
	@Test
	public void testIJK()
	{
		Sterowanie okuma = new SterowanieOkuma();
		NcState firstState = new NcState(okuma);
		firstState.updateNcState(new Function("G3 X10 Y10 Z0 I10.1 J0.5 K-11.1"));
		
		
		assertEquals(10.1,firstState.getiJk()[0],0.001);
		assertEquals(0.5,firstState.getiJk()[1],0.001);
		assertEquals(-11.1,firstState.getiJk()[2],0.001);
		
	}
	
	@Test
	public void testIJKR()
	{
		Sterowanie okuma = new SterowanieOkuma();
		NcState firstState = new NcState(okuma);
		firstState.updateNcState(new Function("G3 X10 Y10 Z0 R10.123"));
		
		
		assertEquals(10.123,firstState.getiJk()[3],0.001);
	}
	
	@Test
	public void testMFunction()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		Function f = new Function("S2000 M3");
		NcState newState = new NcState(fanuc);
		
		newState.updateNcState(f);
		
		assertTrue(newState.getmFunctionActive().contains(3));
		
	}
	
	@Test
	public void testGFunction()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		Function f = new Function("G90 G54 G0");
		NcState newState = new NcState(fanuc);
		
		newState.updateNcState(f);
		
		assertTrue(newState.getActiveGCodes().getAll().contains(90));
		assertTrue(newState.getActiveGCodes().getAll().contains(54));
		assertTrue(newState.getActiveGCodes().getAll().contains(0));
		assertFalse(newState.getActiveGCodes().getAll().contains(98));
	}
	
	
	@Test
	public void testGFunctionActiveUpdating()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		Function f = new Function("G90 G54 G0");
		NcState newState = new NcState(fanuc);
		
		
		newState.updateNcState(f);
		Function f2 = new Function("G43 G1 H1 Z400 F10000");
		newState.updateNcState(f2);
		
		assertTrue(newState.getActiveGCodes().getAll().contains(43));
		assertTrue(newState.getActiveGCodes().getAll().contains(90));
		assertTrue(newState.getActiveGCodes().getAll().contains(54));
		assertTrue(newState.getActiveGCodes().getAll().contains(1));
		assertFalse(newState.getActiveGCodes().getAll().contains(98));
		assertFalse(newState.getActiveGCodes().getAll().contains(0));
	}
	
	@Test 
	public void testUpdatingFeed()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		Function f = new Function("G90 G54 G1 Z10 F10000");
		NcState newState = new NcState(fanuc);
		newState.updateNcState(f);
		
		Function f2 = new Function("G1 X0 Y0 Z-10 F123");
		newState.updateNcState(f2);
		
		assertEquals(123f,newState.getFeed(),0.001);
	}
	
	@Test 
	public void testUpdatingSpeed()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		Function f = new Function("S2000 M3");
		NcState newState = new NcState(fanuc);
		newState.updateNcState(f);
		
		Function f2 = new Function("S5000 M3");
		newState.updateNcState(f2);
		
		assertEquals(5000,newState.getSprindleSpeed());
	}
	
	@Test 
	public void testUpdatingMCode()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		Function f = new Function("S2000 M3");
		NcState newState = new NcState(fanuc);
		newState.updateNcState(f);
		
		Function f2 = new Function("M5");
		newState.updateNcState(f2);
		
		assertFalse(newState.getmFunctionActive().contains(3));
		assertTrue(newState.getmFunctionActive().contains(5));
	}
	
	@Test 
	public void testUpdatingMCodeII()
	{
		Sterowanie fanuc = new SterowanieFanuc();
		Function f = new Function("S2000 M3");
		NcState newState = new NcState(fanuc);
		newState.updateNcState(f);
		
		Function f2 = new Function("M19");
		newState.updateNcState(f2);
		
		assertFalse(newState.getmFunctionActive().contains(3));
		assertTrue(newState.getmFunctionActive().contains(19));
	}

	
	@Test
	public void testPreviousNcState()
	{
		Function f1 = new Function("G90 G54 G0 S2000 M3");
		Function f2 = new Function("G91 G55 G1 S200 M4");
		NcState state = new NcState(new SterowanieFanuc());
		
		state.updateNcState(f1);
		state.updateNcState(f2);
		
		
		assertEquals(200,state.getSprindleSpeed());
		assertEquals(2000,state.getPreviousNcState().getSprindleSpeed());
		assertTrue(state.getmFunctionActive().contains(4));
		assertTrue(state.getPreviousNcState().getmFunctionActive().contains(3));
		assertFalse(state.getPreviousNcState().getmFunctionActive().contains(4));
	}
	
	@Test
	public void testPreviousNcStateGCode()
	{
		Function f1 = new Function("G90 G54 G0");
		Function f2 = new Function("G91 G55 G1");
	
		NcState state = new NcState(new SterowanieFanuc());
		
		state.updateNcState(f1);
		state.updateNcState(f2);
		assertEquals(1,state.getActiveGCodes().getActiveGCodeInGroup(1));
		assertEquals(91,state.getActiveGCodes().getActiveGCodeInGroup(3));
		assertEquals(55,state.getActiveGCodes().getActiveGCodeInGroup(14));
		
		//previous
		assertEquals(0,state.getPreviousNcState().getActiveGCodes().getActiveGCodeInGroup(1));
		assertEquals(90,state.getPreviousNcState().getActiveGCodes().getActiveGCodeInGroup(3));
		assertEquals(54,state.getPreviousNcState().getActiveGCodes().getActiveGCodeInGroup(14));
	}
	
	
	@Test
	public void testPreviousStateH()
	{
		Function f1 = new Function("G90 G55 G43 H1 Z100 ");
		Function f2 = new Function("G90 G55 G43 H2 Z100 ");
		
		NcState state = new NcState(new SterowanieFanuc());
		state.updateNcState(f1);
		state.updateNcState(f2);
		
		assertEquals("2",state.getH());
		assertEquals("1",state.getPreviousNcState().getH());
	}
	
	
	@Test
	public void testActualToolNumberI()
	{
		Function f1 = new Function("T1");
		Function f2 = new Function("M6");
		
		NcState state = new NcState(new SterowanieFanuc());
		state.updateNcState(f1);
		state.updateNcState(f2);
		
		assertEquals(1,state.getActualToolNumber());
		assertEquals(1,state.getNextToolNumber());
	}
	
	@Test
	public void testActualToolNumberII()
	{
		Function f2 = new Function("T1 M6");
		Function f1 = new Function("T2");
		
		NcState state = new NcState(new SterowanieFanuc());
		state.updateNcState(f1);
		state.updateNcState(f2);
		
		assertEquals(1,state.getActualToolNumber());
		assertEquals(-1,state.getNextToolNumber());
	}
	
	@Test
	public void testActualToolNumberIII()
	{

		Function f1 = new Function("T2 M6");
		Function f2 = new Function("T1 M6");
		
		NcState state = new NcState(new SterowanieFanuc());
		state.updateNcState(f1);
		state.updateNcState(f2);
		
		
		assertEquals(1,state.getActualToolNumber());
		assertEquals(-1,state.getNextToolNumber());
	}
}
