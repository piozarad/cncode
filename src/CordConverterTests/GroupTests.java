package CordConverterTests;

import static org.junit.Assert.*;

import org.junit.Test;

import CordConverter.Function;
import GCodeGroup.ActiveGCodes;


public class GroupTests {

	@Test
	public void testNullGroups()
	{
		ActiveGCodes gcodes = new ActiveGCodes();
		
		int result = gcodes.getActiveGCodeInGroup(18);
		
		assertEquals(-1, result);
	}
	
	@Test
	public void testDefaultValuesAfterConstruction()
	{
		ActiveGCodes gcodes = new ActiveGCodes();
		
		int result = gcodes.getActiveGCodeInGroup(1);
		
		assertEquals(-1, result);
	}
	
	@Test
	public void testFillingGCodeTable()
	{
		ActiveGCodes gcodes = new ActiveGCodes();
		Function f= new Function("N5 G90 G54 G0");
		
		gcodes.updateGroup(f.getFunctionType());
		
		assertEquals(0,gcodes.getActiveGCodeInGroup(1));
		assertEquals(90,gcodes.getActiveGCodeInGroup(3));
		assertEquals(54,gcodes.getActiveGCodeInGroup(14));
		
	}
	@Test
	public void testUpdatingGCodeTable()
	{
		ActiveGCodes gcodes = new ActiveGCodes();
		Function f= new Function("N5 G90 G54 G0");
		
		gcodes.updateGroup(f.getFunctionType());
		Function f2= new Function("N5 G91 G55 G1");
		gcodes.updateGroup(f2.getFunctionType());
		
		assertEquals(1,gcodes.getActiveGCodeInGroup(1));
		assertEquals(91,gcodes.getActiveGCodeInGroup(3));
		assertEquals(55,gcodes.getActiveGCodeInGroup(14));
		
	}
	
	@Test
	public void testUpdatingGCodeTableI()
	{
		ActiveGCodes gcodes = new ActiveGCodes();
		Function f= new Function("G3 X200 Y100 I0. J10");
		
		gcodes.updateGroup(f.getFunctionType());
		
		assertEquals(3,gcodes.getActiveGCodeInGroup(1));

		
	}
	
	@Test
	public void testFindingGCodeGroup()
	{
		ActiveGCodes gcodes = new ActiveGCodes();
		
		assertEquals(3,gcodes.getGroupNumber(90));
		assertEquals(1,gcodes.getGroupNumber(3));
		assertEquals(14,gcodes.getGroupNumber(56));
	}
	
	@Test
	public void testproperGroupNumber()
	{
		ActiveGCodes codes = new ActiveGCodes();
		
		for(int i=-1 ; i<99 ; i++)
			if(i==14) assertEquals(53,codes.getActiveGCodeInGroup(i));
			else
			assertEquals(-1,codes.getActiveGCodeInGroup(i));
	}
	
	
	
}
