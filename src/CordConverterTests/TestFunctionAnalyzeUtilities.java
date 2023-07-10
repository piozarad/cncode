package CordConverterTests;

import static CordConverter.FunctionAnalyzeUtilities.contains;
import static CordConverter.FunctionAnalyzeUtilities.fixedCycleParameters;
import static CordConverter.FunctionAnalyzeUtilities.gFunctions;
import static CordConverter.FunctionAnalyzeUtilities.getCircleParam;
import static CordConverter.FunctionAnalyzeUtilities.getDwellTime;
import static CordConverter.FunctionAnalyzeUtilities.getFloatResult;
import static CordConverter.FunctionAnalyzeUtilities.getIntResult;
import static CordConverter.FunctionAnalyzeUtilities.getStringResult;
import static CordConverter.FunctionAnalyzeUtilities.hasBRotation;
import static CordConverter.FunctionAnalyzeUtilities.hasBlockNumber;
import static CordConverter.FunctionAnalyzeUtilities.hasComment;
import static CordConverter.FunctionAnalyzeUtilities.hasD;
import static CordConverter.FunctionAnalyzeUtilities.hasFeed;
import static CordConverter.FunctionAnalyzeUtilities.hasH;
import static CordConverter.FunctionAnalyzeUtilities.hasMFunction;
import static CordConverter.FunctionAnalyzeUtilities.hasMacro;
import static CordConverter.FunctionAnalyzeUtilities.hasP;
import static CordConverter.FunctionAnalyzeUtilities.hasQ;
import static CordConverter.FunctionAnalyzeUtilities.hasS;
import static CordConverter.FunctionAnalyzeUtilities.hasXCordinate;
import static CordConverter.FunctionAnalyzeUtilities.hasYCordinate;
import static CordConverter.FunctionAnalyzeUtilities.hasZCordinate;
import static CordConverter.FunctionAnalyzeUtilities.parsePoint;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import CordConverter.FunctionAnalyzeUtilities;
import CordConverter.Point;

public class TestFunctionAnalyzeUtilities {

	//Tested controls
	Sterowanie fanuc= new SterowanieFanuc();
	
	private final String comment = "(T2 GLOWICA FI40)";
	private final String notAComment = "T2 GLOWICA FI40";
	private final String macro = "#1=#1+2";
	private final String whileMacro= "while";
	private final String goToMacro= "GOTO";
	private final String macroWithDo = "DO1";
	private final String ifGoToMacro= "IF[]";
	private final String programName = "O0101";
	private final String blockWithProgramNameSinemuric = "%MPF0101";
	private final String endOfProgramChar = "%";
	private final String okumaToolLifeCommand = "TLFON";
	private final String okumaEndOfSubprogram = "RTS";
	private final String blockwithH = "G90 G43 H21 Z-400.123";
	private final String blockWithHAndError = "G90 G43 Hr5 Z400";
	private final String blockToolOkumaA = "G56 HA DA";
	private final String blockToolOkumaB = "G56 HB DB";
	private final String blockToolOkumaC = "G56 HC DC";
	private final String blockToolOkumaError = "G56 HD DD";
	private final String blockNumber = "N10G90 G54 G0";
	private final String blockNumberWithError = "NdG90 G54 G0";
	private final String blockWithBRotation = "G0 B400";
	private final String blockWithBRotationNegative = "G0 B-40";
	private final String blockWithBRotationMorePrecise = "G0 B264.375";
	private final String blockWithBRotationWithError = "G0 BA44";
	private final String blockWithD = "G1 G41 D18";
	private final String blockWithDAndError = "G1 G41 Dp8";
	private final String blockWithP = "G82 R10 Z0 F200 P256";
	private final String blockWithPAndBase = "G90 G54.1 P12";
	private final String blockWithPAndError = "G0 Pa2";
	private final String blockWithMFunction = "S2000 M3";
	private final String blockWithMFunctionWithError = " Mw9";
	private final String blockWithToolNumber = "T9 M6";
	private final String blockWithToolNumberWithError = "TR";
	private final String blockWithFeed = "G1 Z400 F10000";
	private final String blockWithPreciseFeed = "G1 Z20 F232.34";
	private final String blockWithFeedWithError = "G1 Z400 Fg0000";
	private final String blockWithSpeedWithError = "Sg000";
	private final String blockWithDwell = "G4 X200";
	private final String blockWithDwellWithIllegalArgument = "P200";
	private final String blockWithXCordinate = "G0 X0";
	private final String blockWithXCordinateWithError = "G0 XR";
	private final String blockWithYCordinate = "G0 Y10";
	private final String blockWithYCordinateWithError = "G0 Yr1";
	private final String blockWithZCordinateWithError = "G0 Zr1";
	private final String blockWithQ = "G76 R10 Z20 Q0.2";
	private final String blockWithQNegative = "G76 R10 Z20 Q-0.2";
	private final String blockWithQWithError = "G76 R10 Z20 QI.2";
	private final String blockWithLotsOfGCodes = "G80 G49 G90 G54 G17 G0";
	private final String blockWithLotsOfGCodesWithError = "G80 G49 G90 G54 GH5 G17 G0";
	private final String blockWithIJCircle = "G3 X0 Y10 I2.5 J10.21";
	private final String blockWithXYZ = "G0 X10.151 Y20.22 Z100.5";
	private final String blockWithXYZNegative = "G0 X-10.151 Y-20.22 Z-100.5";
	private final String blockWithLotsOfGCodesWithDots = "G80 G49 G90 G54.1 P13 G17 G0 G5.8 G5.122";
			
	int[] expectedGCodeArray = {80,49,90,54,17,0};
			
	@Test 
	public void testFindingComment()
	{
		assertTrue(hasComment(comment));
		assertFalse(hasComment(notAComment));

	}	
	
	@Test
	public void testFindingMacro()
	{
		assertTrue(hasMacro(macro));
	}
	
	@Test 
	public void testRecognizingDoasMacro()
	{
		assertTrue(hasMacro(macroWithDo));
		
	}
	
	@Test
	public void testRecognizingPeogramNameAsMacro()
	{
		assertTrue(hasMacro(programName));
	}
	
	
	@Test
	public void testFindingWhileMacro()
	{
		assertTrue(hasMacro(whileMacro));
	}
	
	@Test
	public void testFindingIfMacro()
	{
		assertTrue(hasMacro(ifGoToMacro));
	}
	
	@Test
	public void testFindingGoToMacro()
	{
		assertTrue(hasMacro(goToMacro));
	}
	
	@Test
	public void testFindingProgramNameSinemuric()
	{
		assertTrue(hasMacro(blockWithProgramNameSinemuric));
	}
	
	
	@Test 
	public void testFindingStartOfProgramCharacter()
	{
		assertTrue(hasMacro(endOfProgramChar));
	}
	@Test
	public void testFindingOkumaToolLifeCommand()
	{
		assertTrue(hasMacro(okumaToolLifeCommand));
	}
	@Test
	public void testFindingOkumaEndOfSubprogram()
	{
		assertTrue(hasMacro(okumaEndOfSubprogram));
	}
	
	
	@Test
	public void testDistinguishingMacroFromComment()
	{
		assertFalse(hasMacro(comment));	
	}
	
	@Test
	public void testFindingH()
	{	
		assertTrue(hasH(blockwithH));
		assertFalse(hasH(blockWithHAndError));
	}
	@Test
	public void testFindingHOkuma()
	{
		assertTrue(hasH(blockToolOkumaA));
		assertTrue(hasH(blockToolOkumaB));
		assertTrue(hasH(blockToolOkumaC));
		assertFalse(hasH(blockToolOkumaError));
	}
	
	@Test 
	public void testFindingBlockNumber()
	{
		assertTrue(hasBlockNumber(blockNumber));
		assertFalse(hasBlockNumber(blockNumberWithError));	
	}
	
	
	@Test
	public void testFindingBRotation()
	{
		
		assertTrue(hasBRotation(blockWithBRotation));
		assertFalse(hasBRotation(blockWithBRotationWithError));
	}
	
	@Test
	public void testFindingBRotationNegative()
	{
		assertTrue(hasBRotation(blockWithBRotationNegative));
		
		
	}
	@Test
	public void testFindingPreciseBRotation()
	{
		assertTrue(hasBRotation(blockWithBRotationMorePrecise));
	}
	
	
	@Test 
	public void testFindingD()
	{
		assertTrue(hasD(blockWithD));	
		assertFalse(hasD(blockWithPAndBase));
		assertFalse(hasD(blockWithDAndError));
	}
	
	@Test
	public void testfindingDOkuma()
	{
		assertTrue(hasD(blockToolOkumaA));
		assertTrue(hasD(blockToolOkumaB));
		assertTrue(hasD(blockToolOkumaC));
	}

	@Test
	public void testFindingP()
	{
		assertTrue(hasP(blockWithP));
		assertTrue(hasP(blockWithPAndBase));
		assertFalse(hasP(blockWithPAndError));
		
	}
	
	
	
	@Test
	public void testFindingMFunction()
	{
		assertTrue(hasMFunction(blockWithMFunction));
		assertFalse(hasMFunction(blockWithMFunctionWithError));
	}
	
	@Test
	public void testFindingToolNumber()
	{
		assertTrue(hasMFunction(blockWithToolNumber));
		assertFalse(hasMFunction(blockWithToolNumberWithError));
		
	}
	
	
	@Test
	public void testFindingFeed()
	{
		assertTrue(hasFeed(blockWithFeed));
		assertFalse(hasFeed(blockWithFeedWithError));
		
	}
	
	@Test 
	public void testDindingPreciseFees()
	{
		assertTrue(hasFeed(blockWithPreciseFeed));	
	}
	
	@Test
	public void testFindingSpeed()
	{
		assertTrue(hasS(blockWithMFunction));
		assertFalse(hasS(blockWithSpeedWithError));
	}
	
	//TODO dokonczyc test - czytana predkosc wrzeciona musi byc liczba calkowita
//	@Test
//	public void testFindingSpeedAndReturningInteger()
//	{
//		asertEquals();
//	}
	
	@Test 
	public void testDwell()
	{
		assertEquals("200",getDwellTime(blockWithDwell,fanuc));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDwellWithError()
	{
		getDwellTime(blockWithFeed,fanuc);
	}
	
	
	@Test public void testFindingXCordinate() 
	{
		
		assertTrue(hasXCordinate(blockWithXCordinate));
		assertFalse(hasXCordinate(blockWithXCordinateWithError));
	}
	@Test
	public void testFindingYCordinate()
	{
		assertTrue(hasYCordinate(blockWithYCordinate));
		assertFalse(hasYCordinate(blockWithYCordinateWithError));
	}
	@Test
	public void testFindingZCordinate()
	{
		assertTrue(hasZCordinate(blockwithH));
		assertFalse(hasZCordinate(blockWithZCordinateWithError));
	}
	
	@Test
	public void testFindingIJCircle()
	{
		Float[] testedBlock = getCircleParam(blockWithIJCircle);
		assertNull(testedBlock[2]);
		assertNull(testedBlock[3]);
	}
	
	@Test
	public void testFindingQ()
	{
		assertTrue(hasQ(blockWithQ));
		assertFalse(hasQ(blockWithQWithError));
	}
	@Test
	public void testFindingQNegative()
	{
		assertTrue(hasQ(blockWithQNegative));
	}
	
	@Test
	public void testGCodeListFinding()
	{
		
		Integer[] arrayToBeChecked = gFunctions(blockWithLotsOfGCodes).toArray(new Integer[0]);
		Integer[] arrayToBeCheckedWithError = gFunctions(blockWithLotsOfGCodesWithError).toArray(new Integer[0]);

		for(int i=0;i<expectedGCodeArray.length;i++)
		{
			assertEquals(expectedGCodeArray[i], (int)arrayToBeChecked[i]);
			assertEquals(expectedGCodeArray[i], (int)arrayToBeCheckedWithError[i]);
		}
	}
	
	@Test 
	public void testFunctionContainsGCode()
	{
		int seekedCode = 54;
		
		assertTrue(contains(expectedGCodeArray,seekedCode));
		
	}
	@Test
	public void testFuncitonDoesntContainGCode()
	{
		assertFalse(contains(expectedGCodeArray,99));
	}

	@Test
	public void testGetIntResult()
	{
		int expectedH = 21;

		hasH(blockwithH);
		assertEquals(expectedH,getIntResult());
	}
	
	@Test
	public void testGetFloatResult()
	{
		float expectedQ = 0.2f;

		hasQ(blockWithQ);
		assertEquals(expectedQ,getFloatResult(),0.001);	
	}
	
	@Test 
	public void testFindingPoint()
	{
		Point expectedPoint = new Point(10.151f, 20.22f, 100.5f);
		Point testedPoint=  parsePoint(blockWithXYZ);
		
		assertNull(parsePoint(blockWithToolNumber));
		assertEquals(expectedPoint.getX(),testedPoint.getX(),0.0001);
		assertEquals(expectedPoint.getY(),testedPoint.getY(),0.0001);
		assertEquals(expectedPoint.getZ(),testedPoint.getZ(),0.0001);
	}
	@Test
	public void testFindingPointWithNegativeCordinates()
	{
		Point expectedPoint = new Point(-10.151f, -20.22f, -100.5f);
		Point testedPoint=  parsePoint(blockWithXYZNegative);
		
		assertNull(parsePoint(blockWithToolNumber));
		assertEquals(expectedPoint.getX(),testedPoint.getX(),0.0001);
		assertEquals(expectedPoint.getY(),testedPoint.getY(),0.0001);
		assertEquals(expectedPoint.getZ(),testedPoint.getZ(),0.0001);
	}
	
	
	@Test
	public void testGetStringResult()
	{
		String expectedString = "256";
		
		hasP(blockWithP);
		
		assertEquals(expectedString,getStringResult());
	}
	
	
	@Test
	public void testFindingRArray()
	{
		String block = "G2 X0 Y10.3 I10.3 J0.1 K5.567 R10.231";
		
		Float[] circle =getCircleParam(block);
		
		assertEquals(10.3,circle[0],0.0001);
		assertEquals(0.1,circle[1],0.0001);
		assertEquals(5.567,circle[2],0.0001);
		assertEquals(10.231,circle[3],0.0001);
	}
	

	@Test
	public void testFindingFicedCycleParametersArray()
	{
		String block = "G83 R0=0.1 R1=1 R2=30.5 R3=-0.055 R4=0.5 R5=10 R6=2 R7=4 R8=5 R9=-9.001 R10=200.101 R11=3 R12=1 R13=2 R14=3 ";
		
		Map<String,Float> parameters = fixedCycleParameters(block);
		
		assertEquals(0.1,parameters.get("R0"),0.0001);
		assertEquals(1,parameters.get("R1"),0.0001);
		assertEquals(30.5,parameters.get("R2"),0.0001);
		assertEquals(-0.055,parameters.get("R3"),0.0001);
		assertEquals(0.5,parameters.get("R4"),0.0001);
		assertEquals(10,parameters.get("R5"),0.0001);
		assertEquals(2,parameters.get("R6"),0.0001);
		assertEquals(4,parameters.get("R7"),0.0001);
		assertEquals(5,parameters.get("R8"),0.0001);
		assertEquals(-9.001,parameters.get("R9"),0.0001);
		assertEquals(200.101,parameters.get("R10"),0.0001);
		assertEquals(3,parameters.get("R11"),0.0001);
		assertEquals(1,parameters.get("R12"),0.0001);
		assertEquals(2,parameters.get("R13"),0.0001);
		assertEquals(3,parameters.get("R14"),0.0001);
		
	}
	
	
	@Test
	public void testDindingMorePreciseGCodes()
	{
		
		int[] expectedArray = {80,49,90,54,-1,17,0,5,-8,5,-122};
		
		Integer[] checkedArray = gFunctions("G80 G49 G90 G54.1 P13 G17 G0 G5.8 G5.122").toArray(new Integer[0]);
		
		assertEquals(expectedArray.length,checkedArray.length);
		
		for(int i=0;i<expectedArray.length;i++)
		{
			assertEquals(expectedArray[i],(int) checkedArray[i]);
		}
	}
	
	@Test
	public void testGetSubvalueMethod()
	{
		float number = 1.500f;
		
		int result = FunctionAnalyzeUtilities.getSubvalue(number);
		
		assertEquals(5,result);
	}
	
	@Test
	public void testComputingSubvalue()
	{
		float gCode = 5.12f;
		
		int result =  FunctionAnalyzeUtilities.getSubvalue(gCode);
		
		assertEquals(12,result);
	}
	
	
	@Test
	public void testRemoveComment()
	{
		String function = "M1(costamcostam)";
		
		function = FunctionAnalyzeUtilities.removeComment(function);
		
		
		assertEquals("M1", function);
	}
	
	@Test
	public void testRemoveCommentI()
	{
		String function = "N100 (costamcostam) M1 ";
		
		function = FunctionAnalyzeUtilities.removeComment(function);
		
		
		assertEquals("N100  M1 ", function);
	}
	
	@Test
	public void testSeparatingLettersI()
	{
		String function = "N100G90G54G0X0Z0";
		
		function = FunctionAnalyzeUtilities.separateFunctions(function);
		
		assertEquals("N100 G90 G54 G0 X0 Z0 ",function);
	}
	
	@Test
	public void testSeparatingLettersII()
	{
		String function = "N100G90G54G0X0Z0(To jest funkcja)";
		
		function = FunctionAnalyzeUtilities.separateFunctions(function);
		
		assertEquals("N100 G90 G54 G0 X0 Z0 (To jest funkcja)",function);
	}
	
	@Test
	public void testSeparatingLettersIII()
	{
		String function = "N100G56HADA";
		
		function = FunctionAnalyzeUtilities.separateFunctions(function);
		
		assertEquals("N100 G56 HADA ",function);
	}
	
	
	
	
}
