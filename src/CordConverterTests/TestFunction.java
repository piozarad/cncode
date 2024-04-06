package CordConverterTests;

import org.junit.Test;

import BasicControls.SterowanieFanuc;

import static org.junit.Assert.*;

import java.util.Arrays;

import CordConverter.Point;
import CordConverter.TYPE;
import CordConverter.Function;
import CordConverter.FunctionAnalyzeUtilities;
public class TestFunction {

	
	private String macroWithIfAndGoto = "IF[#1NE0] GOTO9999";

	@Test 
	public void testZeroArgumentConstructor()
	{
		Function zeroArgumentConstructor = new Function();
		
		assertNotNull(zeroArgumentConstructor);
	}
	
	@Test
	public void testInicjalizationValues()
	{
		Function zeroArgumentConstructor = new Function();
		assertEquals(0,zeroArgumentConstructor.getBlock());
		assertNull(zeroArgumentConstructor.getBRotation());
		assertTrue(zeroArgumentConstructor.getCircle().isEmpty());
		assertNull(zeroArgumentConstructor.getComment());
		assertEquals("0",zeroArgumentConstructor.getD());
		assertEquals(-1,zeroArgumentConstructor.getFeed(),0.0001);
		assertTrue(zeroArgumentConstructor.getFunctionType().isEmpty());
		assertEquals("0",zeroArgumentConstructor.getH());
		assertNull(zeroArgumentConstructor.getMacro());
		assertEquals(-1,zeroArgumentConstructor.getMFunctin());
		assertNull(zeroArgumentConstructor.getP());
		assertNull(zeroArgumentConstructor.getPoint());
		assertNull(zeroArgumentConstructor.getQ());
		assertTrue(zeroArgumentConstructor.getRArray().isEmpty());
		assertEquals(-1,zeroArgumentConstructor.getSpeed());
		assertNull(zeroArgumentConstructor.getSterowanie());
		assertEquals(-1,zeroArgumentConstructor.getToolNumber());
	}
	
	@Test
	public void testOneArgumentContructor()
	{	
		Function oneArgumentConstructor = new Function("G90 G0 Z400");
		assertNotNull(oneArgumentConstructor);
		
	}
	@Test 
	public void testTwoArgumentContructor()
	{
		Function twoArgumentConstructor = new Function("G90 G0 Z400",new SterowanieFanuc());
		assertNotNull(twoArgumentConstructor);
	}
	
	
	@Test 
	public void testSetingBlockNumber()
	{
		Function blockNumber = new Function("N10 G90 G0 Z400");
		
		blockNumber.setBlockNumber(15);
		assertEquals(15,blockNumber.getBlock());
	}
	
	@Test
	public void testSettingMacro()
	{
		Function blockNumber = new Function("#1=#1+0.5");
		blockNumber.setMacro(macroWithIfAndGoto);
		assertEquals(macroWithIfAndGoto,blockNumber.getMacro());
	}
	
	@Test
	public void testSettingControls()
	{
		Function blockNumber = new Function("G90 G54 G0");
		blockNumber.setControls(new SterowanieFanuc());
		assertEquals(blockNumber.getSterowanie(),new SterowanieFanuc());
	}
	
	@Test
	public void testSettingComment()
	{
		Function comment = new Function();
		comment.setComment("(T1 WIERTLO FI9)");
		assertEquals("(T1 WIERTLO FI9)",comment.getComment());
	}
	
	@Test
	public void testSettingGFunctions()
	{
		Function gFunctions = new Function("G90 G54 G0");
		
		Integer[] gCodes = gFunctions.getFunctionType().toArray(new Integer[0]);

		assertTrue(gCodes[0].equals(90));
		assertTrue(gCodes[1].equals(54));
		assertTrue(gCodes[2].equals(0));
	}
	
	@Test
	public void testinitializingGCodeArray()
	{
		Function blockWIthoutGFunctions = new Function("X40. Y50 F500");

		assertTrue(blockWIthoutGFunctions.getFunctionType().isEmpty());
	}
	
	@Test
	public void testContaingGFunction()
	{
		Function gFunctions = new Function("G49 G80 G90 G54 G0");
		assertTrue(gFunctions.containsFunction(80,90));
		assertTrue(gFunctions.containsFunction(49,54,0));
	}
	
	@Test 
	public void testSettingD()
	{
		Function functionWithD = new Function("G41", new SterowanieFanuc());
		functionWithD.setD("3");
		assertEquals("3", functionWithD.getD());
		
	}
	
	@Test
	public void testSettingH()
	{
		Function functionWithH = new Function("G43", new SterowanieFanuc());
		functionWithH.setH("3");
		assertEquals("3", functionWithH.getH());
	}
	
	
	@Test
	public void testSettingQ()
	{
		Function functionWithQ = new Function("G76 R10 Z0", new SterowanieFanuc());
		functionWithQ.setQ(0.1f);
		assertEquals(0.1, functionWithQ.getQ(), 0.0001);
	}
	
	@Test
	public void testSettingP()
	{
		Function functionWithP = new Function("G82 R10 Z0", new SterowanieFanuc());
		functionWithP.setP("20");
		assertEquals("20", functionWithP.getP());
		
	}
	
	@Test
	public void testSettingCircle()
	{
		Function functionWithCircleIJ = new Function("G3 X10 Y10 I5 J0.15");
		functionWithCircleIJ.setCircle('I',10.0015f);
		functionWithCircleIJ.setCircle('J',0f);
		
		assertEquals(10.0015f,functionWithCircleIJ.getCircle().get('I'),0.0001);
		assertEquals(0f,functionWithCircleIJ.getCircle().get('J'),0.0001);
		
	}

	@Test
	public void testSettingR()
	{
		Function functionWithCircleIJ = new Function("G3 X10 Y10 R10.");
		functionWithCircleIJ.setCircle(10.51f);
	
		
		assertEquals(10.51f,functionWithCircleIJ.getCircle().get('R'),0.0001);
	}
	
	@Test
	public void testFindingProgramNameFanuc()
	{
		Function functionWithProgramNameFanuc = new Function("O9999");
		assertTrue(functionWithProgramNameFanuc.isProgramName());
		
	}
	
	@Test
	public void testFindingProgramNameSinumeric()
	{
		Function functionWithProgramNameSinumeric = new Function("%MPF9999");
		assertTrue(functionWithProgramNameSinumeric.isProgramName());	
	}
	
	@Test
	public void testFindingProgramBorder()
	{
		Function functionWithProgramBorder = new Function("%");
		assertTrue(functionWithProgramBorder.isProgramBorder());	
	}
	@Test
	public void testFindingGCodeWithDot()
	{
		Function testedFunction = new Function("G90 G54.1 P13 G0");
		
		assertEquals(54,(int)testedFunction.getFunctionType().get(1));
		assertEquals(-1,(int)testedFunction.getFunctionType().get(2));
		assertEquals(0,(int)testedFunction.getFunctionType().get(3));
	}

	@Test
	public void testAddingGFunctionAndIndex()
	{
		Function testedFunction = new Function("G90 G54 G49");
		
		testedFunction.addGFunction(0, 1);
		
		assertEquals(0,(int)testedFunction.getFunctionType().get(1));
		assertEquals(54,(int)testedFunction.getFunctionType().get(2));
	}
	
	@Test
	public void testAddingMoreComplexGFuncion()
	{
		Function testedFunction = new Function("G90");
		
		testedFunction.addGFunction(54.1f);
		
		assertEquals(54,(int)testedFunction.getFunctionType().get(1));
		assertEquals(-1,(int)testedFunction.getFunctionType().get(2));
	}
	
	@Test
	public void testsettingGFunciton()
	{
		Function testedFunction = new Function("G90 G54 G49");
		
		testedFunction.setGFunction(0, 91);
		
		assertEquals(91,(int)testedFunction.getFunctionType().get(0));
	}
	
	@Test
	public void testsettingGFuncitonWithPosition()
	{
		Function testedFunction = new Function("G90 G49");
		
		testedFunction.addGFunction(5.3f,1);
		
		assertEquals(5,(int)testedFunction.getFunctionType().get(1));
		assertEquals(-3,(int)testedFunction.getFunctionType().get(2));
	}
	
	@Test
	public void testReplacingSimpleGFunction()
	{
		Function testedFunction = new Function("G90 G49 G0 G49");
		
		testedFunction.replaceGFunction(0, 1);
		
		assertEquals(1,(int)testedFunction.getFunctionType().get(2));

	}
	
	@Test
	public void testReaplaceGFunctionWithFloatValue()
	{
		Function testedFunction = new Function("G90 G54 G0 G49");
		
		testedFunction.replaceGFunction(54, 54.1f);
		
		assertEquals(54,(int)testedFunction.getFunctionType().get(1));
		assertEquals(-1,(int)testedFunction.getFunctionType().get(2));
		assertEquals(0,(int)testedFunction.getFunctionType().get(3));
	}
	
	@Test
	public void testReaplaceGFunctionWithFloatValueII()
	{
		Function testedFunction = new Function("G1 G90 X0 Y0 Z0");
		
		testedFunction.replaceGFunction(1, 5.12f);
		
		assertEquals(5,(int)testedFunction.getFunctionType().get(0));
		assertEquals(-12,(int)testedFunction.getFunctionType().get(1));
		assertEquals(90,(int)testedFunction.getFunctionType().get(2));
	}
	
	@Test
	public void testReplaceGFunctionWithIntValue()
	{
		Function testedFunction = new Function("G1 G54.1 G49 X0 Y0 Z0");
		
		testedFunction.replaceGFunction(54.1f, 54);
		
		assertEquals(54,(int)testedFunction.getFunctionType().get(1));
		assertEquals(49,(int)testedFunction.getFunctionType().get(2));

	}
	
	@Test
	public void testReplaceGFunctionWithIntValueII()
	{
		Function testedFunction = new Function("G1 G5.12 G49 X0 Y0 Z0");
		
		testedFunction.replaceGFunction(5.12f, 1);
		
		assertEquals(1,(int)testedFunction.getFunctionType().get(1));
		assertEquals(49,(int)testedFunction.getFunctionType().get(2));
	}
	
	@Test
	public void testReplacingTwoFloatValuesGFunctions()
	{
		Function testedFunction = new Function("G1 G5.12 G49 X0 Y0 Z0");
		
		testedFunction.replaceGFunction(5.12f, 5.6f);
		
		assertEquals(5,(int)testedFunction.getFunctionType().get(1));
		assertEquals(-6,(int)testedFunction.getFunctionType().get(2));
		assertEquals(49,(int)testedFunction.getFunctionType().get(3));
	}
	
	@Test
	public void testRemovingIntFunctionFromList()
	{
		Function testedFunction = new Function("G1 G49 X0 Y0 Z0");
		
		testedFunction.removeGFunction(49);
		
		assertFalse(testedFunction.containsFunction(49));
	}
	
	@Test
	public void testRemovingFloatFunctionFromList()
	{
		Function testedFunction = new Function("G1 G49 X0 Y0 Z0");
		
		testedFunction.removeGFunction(49);
		
		assertFalse(testedFunction.containsFunction(49));
	}
	@Test
	public void testContainingFloatValueGFunctionI()
	{
		Function testedFunction = new Function("G1 G54.1 X0 Y0 Z0");
		
		boolean result=testedFunction.containsFunction(54.1f);
		
		assertTrue(result);
	}
	
	@Test
	public void testContainingFloatValueGFunctionII()
	{
		Function testedFunction = new Function("G1 G49 X0 Y0 G5.4");
		boolean result=testedFunction.containsFunction(5.4f);
		
		assertTrue(result);
	}
	
	@Test
	public void testContainingSingleIntValueGFuntion()
	{
		Function testedFunction = new Function("G1 G49 X0 Y0 G5.4");
		boolean result=testedFunction.containsFunction(49);
		
		assertTrue(result);
	}
	
	
	@Test
	public void testRemovingGFuntionIntValue()
	{
		Function testedFunction = new Function("G90 G54 G0 G49");
		assertTrue(testedFunction.containsFunction(0));
		
		testedFunction.removeGFunction(0);
		
		assertFalse(testedFunction.containsFunction(0));
	}
	
	
	@Test
	public void testRemoveGFunctionFloatValueI()
	{
		Function testedFunction = new Function("G5.4 X0 Y0");
		assertTrue(testedFunction.containsFunction(5.4f));
		
		testedFunction.removeGFunction(5.4f);
		
		assertFalse(testedFunction.containsFunction(5.4f));
	}
	
	@Test
	public void testRemoveGFunctionFloatValueII()
	{
		Function testedFunction = new Function("G90 G49 G0 G54.1");
		assertTrue(testedFunction.containsFunction(54.1f));
		
		testedFunction.removeGFunction(54.1f);
		
		assertFalse(testedFunction.containsFunction(54.1f));
	}
	
	@Test
	public void testGetingRCycleParametersI()
	{
		Function testedFunction = new Function("G3 X10 Y0.5 R10.534");
		
		Float[] rParameter = testedFunction.getRcycleParam();
		
		//dlugosc zero bo promien R powinien byæ obslugiwany przez funkcje circle
		assertTrue(rParameter.length==0);
	}
	
	@Test
	public void testGetingRCycleParametersII()
	{
		Function testedFunction = new Function("G81 R2=150 R3=100.234 R10=150 R11=.5");
		
		float r2Parameter = testedFunction.getRParameter("R2");
	
		assertEquals(150,r2Parameter,0.0001);
	}
	@Test
	public void testGetingRCycleParametersIII()
	{
		Function testedFunction = new Function("G81 R2=150 R3=-100.234 R10=150 R11=.5");
		
		float r3Parameter = testedFunction.getRParameter("R3");
	
		assertEquals(-100.234,r3Parameter,0.0001);
	}
	
	@Test
	public void testGetingRCycleParametersIV()
	{
		Function testedFunction = new Function("G81 R2=150 R3=100.234 R10=150 R11=.5");
		
		float r11Parameter = testedFunction.getRParameter("R11");
	
		assertEquals(0.5,r11Parameter,0.0001);
	}
	
	
	@Test
	public void testGetingXPoint()
	{
		Function testedFunction = new Function("G0 X0.156 Y124.354 Z500.5");
		
		Point testPoint = testedFunction.getPoint();
	
		assertEquals(0.156,testPoint.getX(),0.0001);
		
	}
	@Test
	public void testGetingYPoint()
	{
		Function testedFunction = new Function("G0 X0.156 Y124.354 Z500.5");
		
		Point testPoint = testedFunction.getPoint();
	
		assertEquals(124.354,testPoint.getY(),0.0001);
		
	}
	
	@Test
	public void testGetingZPoint()
	{
		Function testedFunction = new Function("G0 X0.156 Y124.354 Z500.5");
		
		Point testPoint = testedFunction.getPoint();
	
		assertEquals(500.5,testPoint.getZ(),0.0001);
		
	}
	
	@Test
	public void testFunctionAllElements()
	{
		Function testedFunction = new Function("N105 G90 G17 X0.156 Y124.354 Z500.5 G3 I0.567 J10.4 K123.34 M8 S200 T1 F200 P20 Q0.23 A100 B200.2 C12.8");
		
		assertEquals(105,testedFunction.getBlock());
		assertTrue(testedFunction.containsFunction(90));
		assertTrue(testedFunction.containsFunction(3));
		assertTrue(testedFunction.containsFunction(17));
		assertEquals(0.156,testedFunction.getPoint().getX(),0.0001);
		assertEquals(124.354,testedFunction.getPoint().getY(),0.0001);
		assertEquals(500.5,testedFunction.getPoint().getZ(),0.0001);
		assertEquals(100,testedFunction.getARotation(),0.0001);
		assertEquals(200.2,testedFunction.getBRotation(),0.0001);
		assertEquals(12.8,testedFunction.getCRotation(),0.0001);
		assertEquals(0.23,testedFunction.getQ(),0.0001);
		assertTrue(testedFunction.getP().equals("20"));
		assertEquals(200,testedFunction.getFeed(),0.0001);
		assertEquals(200,testedFunction.getSpeed());
		assertEquals(8,testedFunction.getMFunctin());
		assertEquals(1,testedFunction.getToolNumber());
		assertEquals(0.567,testedFunction.getCircle().get('I'),0.0001);
		assertEquals(10.4,testedFunction.getCircle().get('J'),0.0001);
		assertEquals(123.34,testedFunction.getCircle().get('K'),0.0001);
		
	}
	
	@Test
	public void testCharacterSubtractionMethod()
	{
		String block = "G1 G40 G0 X40.1 Y12.123 Z40 I10 J40. K123.234 F200.2";
		
		Function function = new Function(block);

		assertEquals(0,FunctionAnalyzeUtilities.characterCountDifference(block, function.toString()));
	}
	
	
	@Test
	public void testFillingComment()
	{
		String block = "T1 M6 (T1 GLOWICA 40)";
		
		Function function = new Function(block);
		
		assertEquals("(T1 GLOWICA 40)",function.getComment());
	}
	
	@Test
	public void testFillingValuesOtherThatCommentInCommentLine()
	{
		String block = "T1 M6 (T1 GLOWICA 40)";
		
		Function function = new Function(block);
		
		assertEquals(1,function.getToolNumber());
		assertEquals(6,function.getMFunctin());
	}
	
	@Test
	public void testBlankFunction()
	{
		String block = "";
		
		Function function = new Function(block);
		
		assertTrue(function.isBlank());
	}
	
	
	@Test
	public void testPointThatStartsWithDot()
	{
		String block = "X-.125 Y.999";
		Function function = new Function(block);
		
		Point comparingPoint = new Point(-0.125f,0.999f,TYPE.XY_POINT);
		
		assertEquals(function.getPoint(), comparingPoint);
		
	}
	
	
	
}
