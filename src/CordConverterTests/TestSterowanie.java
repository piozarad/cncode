package CordConverterTests;

import org.junit.Test;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import BasicControls.SterowanieHitachi;
import BasicControls.SterowanieOkuma;
import BasicControls.SterowanieSinumeric;
import CordConverter.ControlTypes;

import static org.junit.Assert.*;

public class TestSterowanie {

	
	@Test
	public void testEqualsSterowaniefanuc()
	{
		Sterowanie testedControls = new SterowanieFanuc();
		SterowanieFanuc sterowanie = new SterowanieFanuc();
		
		boolean when = sterowanie.equals(testedControls);
		
		assertTrue(when);
	}
	
	@Test
	public void testEqualsSterowanieOkuma()
	{
		Sterowanie testedControls = new SterowanieOkuma();
		SterowanieOkuma sterowanie = new SterowanieOkuma();
		
		boolean when = sterowanie.equals(testedControls);
		
		assertTrue(when);
		
		
	}
	
	@Test
	public void testEqualsSterowanieSinumeric()
	{
		Sterowanie testedControls = new SterowanieSinumeric();
		SterowanieSinumeric sterowanie = new SterowanieSinumeric();
		
		boolean when = sterowanie.equals(testedControls);
		
		assertTrue(when);
	
	}
	
	@Test
	public void testEqualsSterowanieHitachi()
	{
		Sterowanie testedControls = new SterowanieHitachi();
		SterowanieHitachi sterowanie = new SterowanieHitachi();
		
		boolean when = sterowanie.equals(testedControls);
		
		assertTrue(when);
	
	}

	@Test
	public void testSterowanieFanucIsTypeMethod()
	{
		Sterowanie testedControls = new SterowanieFanuc();
		
		
		boolean when = testedControls.isType(ControlTypes.FANUC);
		
		assertTrue(when);
	}
	
	@Test
	public void testSterowanieOkumaIsTypeMethod()
	{
		Sterowanie testedControls = new SterowanieOkuma();
		
		
		boolean when = testedControls.isType(ControlTypes.OKUMA);
		
		assertTrue(when);
		
		
	}
	
	@Test
	public void testSterowanieSinumericIsTypeMethod()
	{
		Sterowanie testedControls = new SterowanieSinumeric();
		
		
		boolean when = testedControls.isType(ControlTypes.SINUMERIC);
		
		assertTrue(when);
	}
	
	@Test
	public void testHitachiFanucIsTypeMethod()
	{
		Sterowanie testedControls = new SterowanieHitachi();
		
		
		boolean when = testedControls.isType(ControlTypes.HITACHI);
		
		assertTrue(when);
	}
	
	
	
}
