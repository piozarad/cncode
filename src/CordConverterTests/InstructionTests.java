package CordConverterTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import instruction.Add;
import instruction.AddToNextLine;
import instruction.AddToUpperLine;
import instruction.ChangeTo;
import instruction.Delete;
import instruction.Instruction;
import instruction.InstructionFactory;
import instruction.Marks;
import instructionReadFromFile.InstructionDatabase;

public class InstructionTests {

	String line;
	String instruction ;
	InstructionDatabase instructionFromFile;
	
	@Test
	public void testAddtConstructor()
	{
		instruction = "G81+G98";
		String []temp = instruction.split("\\+");
		line = "G81 R2=30. R3=-1.3 F100";
		
		Add adder = new Add(temp[0],temp[1]);
		
		assertEquals("G81 R2=30. R3=-1.3 F100 G98", adder.applyChanges(line));
		
	}
	
	
	@Test
	public void testDeleteConstructor()
	{
		instruction = "G98";
		line = "G98 G81 R100. Z-10 F200.";
		
		Delete del = new Delete(instruction);
		
		assertEquals("G81 R100. Z-10 F200. ", del.applyChanges(line));
		
	}
	
	
	@Test
	public void testChangeToConstructor()
	{
		instruction = "G98=>M53";
		String []temp = instruction.split(Marks.CHANGE_TO.toString());
		line = "G98 G81 R100. Z-10 F200.";
		
		ChangeTo ct = new ChangeTo(temp[0],temp[1]);
		
		assertEquals("M53 G81 R100. Z-10 F200. ", ct.applyChanges(line));
		
	}
	
	@Test
	public void testAddtoNextLine()
	{
		instruction = "G81v+G80";
		String []temp = instruction.split("v\\+");
		line = "G98 G81 R100. Z-10 F200.";
		
		AddToNextLine add = new AddToNextLine(temp[0],temp[1]);
		
		
		assertEquals("G98 G81 R100. Z-10 F200. \nG80", add.applyChanges(line));
		
	}
	
	@Test
	public void testAddtoNextLineII()
	{
		instruction = "B%lv+M78";
		String []temp = instruction.split("v\\+");
		line = "G0 B0.0";
		
		AddToNextLine add = new AddToNextLine(temp[0],temp[1]);
		
		
		assertEquals("G0 B0.0 \nM78", add.applyChanges(line));
		
	}
	
	
	@Test
	public void testAddtoUpperLine()
	{
		instruction = "G81^+G71";
		String []temp = instruction.split("\\^\\+");
		line = "G81 R100. Z-10 F200.";
		
		AddToUpperLine add = new AddToUpperLine(temp[0],temp[1]);
	
		assertEquals("G71\nG81 R100. Z-10 F200. ", add.applyChanges(line));
		
	}
	
	@Test
	public void testAddtoUpperLineII()
	{
		instruction = "B%l^+M79";
		String []temp = instruction.split("\\^\\+");
		line = "G0 B0";
		
		AddToUpperLine add = new AddToUpperLine(temp[0],temp[1]);
	
		assertEquals("M79\nG0 B0 ", add.applyChanges(line));
		
	}
	
	//test creating instructions from a factory method
	
	@Test
	public void testCreatingAdd()
	{
		instruction = "G41 + D1";
		Instruction add = InstructionFactory.createInstuction(instruction);
		
		assertEquals(add.returnMark(),"+");
	}
	
	
	@Test
	public void testCreatingChangeWhen()
	{
		instruction = "G4 ? X=>P";
		line = "G98 G81 R100. Z-10 F200.";
		
		Instruction changeWhen = InstructionFactory.createInstuction(instruction);
		
		assertEquals(changeWhen.returnMark(),"?=>");
		assertEquals("ChangeWhen",changeWhen.getClass().getSimpleName());
		
	}
	
	@Test
	public void testCreatingDelete()
	{
		instruction = "/D1";
		Instruction delete = InstructionFactory.createInstuction(instruction);
		
		assertEquals(delete.returnMark(),"/");
		assertEquals("Delete",delete.getClass().getSimpleName());
	}
	
	@Test
	public void testCreatingDeleteWhen()
	{
		instruction = "G41 / D1";
		Instruction deleteWhen = InstructionFactory.createInstuction(instruction);
		
		assertEquals(deleteWhen.returnMark(),"/");
		assertEquals("DeleteWhen",deleteWhen.getClass().getSimpleName());
	}
	
	@Test
	public void testCreatingDeleteLine()
	{
		instruction = "KL> D1";
		Instruction deleteLine = InstructionFactory.createInstuction(instruction);
		
		assertEquals(deleteLine.returnMark(),"KL>");
		assertEquals("DeleteLine",deleteLine.getClass().getSimpleName());
	}
	
	@Test
	public void testCreatingChangeTo()
	{
		instruction = "G41 => G43";
		Instruction changeTo = InstructionFactory.createInstuction(instruction);
		
		assertEquals(changeTo.returnMark(),"=>");
	}
	
	
	
	@Test
	public void testAddToUpperLineIII()
	{
		instruction = "G81 ^+ G71 Z400";
		Instruction addToUpper = InstructionFactory.createInstuction(instruction);
		
		assertEquals(addToUpper.returnMark(),"^+");
	}
	@Test
	public void testAddToLowerLine()
	{
		instruction = "G81 v+ G80";
		Instruction addToLower = InstructionFactory.createInstuction(instruction);
		
		assertEquals(addToLower.returnMark(),"v+");
	}
	
	@Test
	public void testAddingToolNumberToInstructionI()
	{
		String line1="T11M6";
		String line2 = "G41 DA G0 X0 Y100.1";
		instruction = "DA=>D%t";
		
		Instruction changeTo = InstructionFactory.createInstuction(instruction);
		assertEquals("T11 M6 ",changeTo.applyChanges(line1));
		assertEquals("G41 D11 G0 X0 Y100.1 ",changeTo.applyChanges(line2));	
	}
	
	@Test
	public void testAddingToolNumberToInstructionII()
	{
		String line1="T11M6";
		String line2 = "G41 D12 G0 X0 Y100.1";
		instruction = "D%l=>D%t";
		
		Instruction changeTo = InstructionFactory.createInstuction(instruction);
		assertEquals("T11 M6 ",changeTo.applyChanges(line1));
		assertEquals("G41 D11 G0 X0 Y100.1 ",changeTo.applyChanges(line2));	
	}
	
	@Test
	public void testDeletingWhenI()
	{
		instruction = "G41/D%l";
		Instruction deleteWhen = InstructionFactory.createInstuction(instruction);
		String line = "G41 D100 X100 Y10";


		assertEquals("G41  X100 Y10 ",deleteWhen.applyChanges(line));
	}
	@Test
	public void testDeletingWhenII()
	{
		instruction = "D%l/G41";
		Instruction deleteWhen = InstructionFactory.createInstuction(instruction);
		String line = "G41 D100 X100 Y10";

		assertEquals(" D100 X100 Y10 ",deleteWhen.applyChanges(line));
	}
	@Test
	public void testDeletingLineI()
	{
		instruction = "KL>G30";
		Instruction deleteWhen = InstructionFactory.createInstuction(instruction);
		String line = "G91 G30 X0 Z0 Y0";

		assertEquals("",deleteWhen.applyChanges(line));
	}
	
	@Test
	public void testDeletingLineII()
	{
		instruction = "KL>G30";
		Instruction deleteWhen = InstructionFactory.createInstuction(instruction);
		String line = "G91 X0 Z0 Y0";

		assertEquals("G91 X0 Z0 Y0 ",deleteWhen.applyChanges(line));
	}
	
	@Test
	public void testDeletingWithlOperatorI()
	{
		instruction = "/D%l";
		Instruction delete = InstructionFactory.createInstuction(instruction);
		String line = "G41 D100 X100 Y10";

		assertEquals("G41  X100 Y10 ",delete.applyChanges(line));
	}
	@Test
	public void testDeletingWithlOperatorII()
	{
		instruction = "D%l=>DA";
		Instruction changeTo= InstructionFactory.createInstuction(instruction);
		String line = "G41 D100 X100 Y10";

		assertEquals("G41 DA X100 Y10 ",changeTo.applyChanges(line));
	}
	
	@Test
	public void testDeletingWithlOperatorIII()
	{
		instruction = "D%l=>D%t";
		Instruction changeTo= InstructionFactory.createInstuction(instruction);
		String line2 = "T3 M6";
		changeTo.applyChanges(line2);
		String line = "G41 D100 X100 Y10";

		assertEquals("G41 D3 X100 Y10 ",changeTo.applyChanges(line));
	}

	@Test
	public void testChangeTo()
	{
		instruction = "G54=>G15";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		String line = "G54";

		assertEquals("G15 ",changeWhen.applyChanges(line));
	}
	@Test
	public void testChangeToI()
	{
		instruction = "G4?X=>P";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		String line = "G4 X2";

		assertEquals("G4 P2 ",changeWhen.applyChanges(line));
	}
	@Test
	public void testChangeToII()
	{
		instruction = "G4?X=>P";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		String line = "G5 X2";

		assertEquals("G5 X2 ",changeWhen.applyChanges(line));
	}
	@Test
	public void testChangeToIII()
	{
		instruction = "G4?X=>P";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		String line = "G4 S2";

		assertEquals("G4 S2 ",changeWhen.applyChanges(line));
	}
	
	@Test
	public void testChangeToIV()
	{
		instruction = "H%l?G41=>G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		String line = "G41 H1";
		
		assertEquals("G43 H1 ",changeWhen.applyChanges(line));
	}
	
	@Test
	public void testChangeToV()
	{
		instruction = "H1?G%l=>G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		String line = "G4 H1";

		assertEquals("G43 H1 ",changeWhen.applyChanges(line));
	}

	@Test
	public void testAddToString()
	{
		instruction = "H1+G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		
		assertEquals("Kiedy H1 dodaj G43",changeWhen.toString());
	}
	
	@Test
	public void testAddToNextLineString()
	{
		instruction = "H1v+G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		
		assertEquals("Dodaj linijkê ni¿ej G43 kiedy H1 ",changeWhen.toString());
	}
	
	@Test
	public void testAddToUpperLineString()
	{
		instruction = "H1^+G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);
		
		assertEquals("Dodaj linijkê wy¿ej G43 kiedy H1 ",changeWhen.toString());
	}
	
	
	@Test
	public void testChangeToString()
	{
		instruction = "H1=>G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);

		assertEquals("Zamieñ H1 na G43",changeWhen.toString());
	}
	
	@Test
	public void testChangeWhenToString()
	{
		instruction = "H1?G41=>G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);

		assertEquals("Zamieñ G41 na G43 kiedy H1 ",changeWhen.toString());
	}
	
	@Test
	public void testDeleteToString()
	{
		instruction = "/G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);

		assertEquals("Kasuj G43 ",changeWhen.toString());
	}
	
	@Test
	public void testDeleteLineToString()
	{
		instruction = "KL>G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);

		assertEquals("Kasuj blok z G43 ",changeWhen.toString());
	}
	
	@Test
	public void testDeleteWhenToString()
	{
		instruction = "G41/G43";
		Instruction changeWhen= InstructionFactory.createInstuction(instruction);

		assertEquals("Kasuj G43 kiedy w bloku znajduje siê G41 ",changeWhen.toString());
	}
	
	@Test
	public void testMultipleInstructionsI()
	{
		instruction = "G41=>G42H1 D1";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "G90 G54 G41 G0 X0 Y0 ";	
		
		assertEquals("G90 G54 G42H1 D1 G0 X0 Y0 ",change.applyChanges(line));
	}
	
	@Test
	public void testMultipleInstructionsII()
	{
		instruction = "G41+H1D1";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "G90 G54 G41 G0 X0 Y0";	
		
		assertEquals("G90 G54 G41 G0 X0 Y0 H1D1",change.applyChanges(line));
	}
	
	@Test
	public void testMultipleInstructionsIII()
	{
		instruction = "G41v+H1D1";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "G90 G54 G41 G0 X0 Y0";	
		
		assertEquals("G90 G54 G41 G0 X0 Y0 \nH1D1",change.applyChanges(line));
	}
	
	@Test
	public void testMultipleInstructionsIV()
	{
		instruction = "G41^+H1D1";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "G90 G54 G41 G0 X0 Y0";	
		
		assertEquals("H1D1\nG90 G54 G41 G0 X0 Y0 ",change.applyChanges(line));
	}
	
	@Test
	public void testMultipleInstructionsV()
	{
		instruction = "G41?D1=>HADA";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "G90 G54 G41 D1 G0 X0 Y0 ";	
		
		assertEquals("G90 G54 G41 HADA G0 X0 Y0 ",change.applyChanges(line));
	}
	
	
	@Test
	public void testManyInstructionsOnOneBlockI()
	{
		String line = "G90 G54 G0 G43 H1";	
		instruction = "G43/H1";
		Instruction changeI= InstructionFactory.createInstuction(instruction);
		line = changeI.applyChanges(line);
		
		assertEquals("G90 G54 G0 G43  ",line);
		
		instruction = "/G43";
		Instruction changeII= InstructionFactory.createInstuction(instruction);
		line = changeII.applyChanges(line);
		
		assertEquals("G90 G54 G0  ",line);
		
		instruction = "G54+H1";
		Instruction changeIII= InstructionFactory.createInstuction(instruction);
		line = changeIII.applyChanges(line);
		
		assertEquals("G90 G54 G0  H1",line);

		instruction = "G54=>G15";
		Instruction changeIV= InstructionFactory.createInstuction(instruction);
		line = changeIV.applyChanges(line);
		
		assertEquals("G90 G15 G0  H1 ",line);
		
		instruction = "G15v+G56 HA DA";
		Instruction changeV= InstructionFactory.createInstuction(instruction);
		line = changeV.applyChanges(line);
		
		assertEquals("G90 G15 G0  H1 \nG56 HA DA",line);
	}
	
	@Test
	public void testAddingNextLineWhenTriggerIsLastFunctionInBlock()
	{
		instruction = "M6v+D%t";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "T1 M6";	
		
		
		assertEquals("T1 M6 \nD1",change.applyChanges(line));
	}
	
	@Test
	public void testAddingUpperLineWhenTriggerIsLastFunctionInBlock()
	{
		instruction = "M6^+D%t";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "T1 M6";	
		
		
		assertEquals("D1\nT1 M6 ",change.applyChanges(line));
	}
	
	@Test
	public void testAddingWhenTriggerIsLastFunctionInBlock()
	{
		instruction = "M6+T1";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "M6";	
		
		
		assertEquals("M6 T1",change.applyChanges(line));
	}
	
	@Test
	public void testChangeToWhenTriggerIsLastFunctionInBlock()
	{
		instruction = "M6=>T1";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "M6";	
		
		
		assertEquals("T1 ",change.applyChanges(line));
	}
	
	@Test
	public void testChangeWhenToWhenTriggerIsLastFunctionInBlock()
	{
		instruction = "T1?M6=>M1";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "T1M6";	
		
		
		assertEquals("T1 M1 ",change.applyChanges(line));
	}
	
	@Test
	public void testDeleteToWhenTriggerIsLastFunctionInBlock()
	{
		instruction = "/M6";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "M6";	
		
		
		assertEquals("",change.applyChanges(line));
	}
	@Test
	public void testDeleteLineToWhenTriggerIsLastFunctionInBlock()
	{
		instruction = "KL>M6";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "M6";	
		
		
		assertEquals("",change.applyChanges(line));
	}
	
	@Test
	public void testDeleteWhenLineToWhenTriggerIsLastFunctionInBlock()
	{
		instruction = "T1/M6";
		Instruction change= InstructionFactory.createInstuction(instruction);

		String line = "T1 M6";	
		
		
		assertEquals("T1  ",change.applyChanges(line));
	}
	
	@Test
	public void testSeparateFunctionI()
	{
		instruction = "<>T1 ";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		assertEquals("SeparateFunction",separate.getClass().getSimpleName());
	}
	
	@Test
	public void testSteparateFunctionII()
	{
		instruction = "<>T1";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		String line = "T1 M6";	
		
		assertEquals("T1 \nM6 ",separate.applyChanges(line));
	}
	@Test
	public void testSteparateFunction()
	{
		instruction = "<>T1";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		
		
		assertEquals("Wyodrêbnij T1 ",separate.toString());
	}
	
	@Test
	public void testSteparateFunctionIV()
	{	
		instruction = "<>D%t";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		String line0= "T3 M6";
		separate.applyChanges(line0);
		String line = "G41 D3";	
		
		assertEquals("D3 \nG41 ",separate.applyChanges(line));
	}
	@Test
	public void testSteparateFunctionV()
	{	
		instruction = "<>D%l";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		String line0= "T3 M6";
		separate.applyChanges(line0);
		String line = "G41 D3";	
		
		assertEquals("D3 \nG41 ",separate.applyChanges(line));
	}
	
	@Test
	public void testSteparateFunctionVI()
	{	
		instruction = "<>D%l";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		String line = "G56 HA DA";	
		
		assertEquals("G56 HA DA ",separate.applyChanges(line));
	}
	
	@Test
	public void testSeparateWhenI()
	{
		instruction = "M1?<>T1 ";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		assertEquals("SeparateWhen",separate.getClass().getSimpleName());
	}
	
	@Test
	public void testSteparateWhenII()
	{
		instruction = "M6?<>T1";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		String line = "T1 M9";	
		
		assertEquals("T1 M9 ",separate.applyChanges(line));
	}
	@Test
	public void testSteparateWhen()
	{
		instruction = "M9?<>T1";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		
		
		assertEquals("Wyodrêbnij T1 kiedy M9 ",separate.toString());
	}
	
	@Test
	public void testSteparateWhenIV()
	{	
		instruction = "G41?<>D%t";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		String line0= "T3 M6";
		separate.applyChanges(line0);
		String line = "G41 D3";	
		
		assertEquals("D3\nG41  ",separate.applyChanges(line));
	}
	
	@Test
	public void testSteparateWhenV()
	{	
		instruction = "G41?<>D%l";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		String line0= "T3 M6";
		separate.applyChanges(line0);
		String line = "G41 D3";	
		
		assertEquals("D3 \nG41 ",separate.applyChanges(line));
	}
	
	@Test
	public void testSteparateWhenVI()
	{	
		instruction = "G41?<>D%l";
		
		Instruction separate= InstructionFactory.createInstuction(instruction);
		
		String line = "G56 HA DA";	
		
		assertEquals("G56 HA DA ",separate.applyChanges(line));
	}
	
	@Test
	public void testAddingUntilNotTriggered()
	{
		instruction = "G81?G82vvG80";
		
		String line1= "N100 G82 R10 Z0 F100";
		String line2 = "G80";
		
		Instruction addUntil= InstructionFactory.createInstuction(instruction);
		
		
		assertEquals("N100 G82 R10 Z0 F100 ",addUntil.applyChanges(line1));
		assertEquals("G80 ",addUntil.applyChanges(line2));
	}
	
	@Test
	public void testAddingUntilI()
	{
		instruction = "G81?G82vvG80";
		
		String line1= "N100 G81 R10 Z0 F100";
		String line2 = "X40. Y32.25";
		String line3 = "G80";
		
		Instruction addUntil= InstructionFactory.createInstuction(instruction);
		
		
		assertEquals("N100 G81 R10 Z0 F100 ",addUntil.applyChanges(line1));
		assertEquals("G82 X40. Y32.25 ",addUntil.applyChanges(line2));
		assertEquals("G80 ",addUntil.applyChanges(line3));
	}
	
	@Test
	public void testAddingUntilII()
	{
		instruction = "G81?G82vvG80";
		
		String line1= "N100 G81 R10 Z0 F100";
		String line2 = "X40. Y32.25";
		String line3 = "G80";
		
		Instruction addUntil= InstructionFactory.createInstuction(instruction);
		
		
		assertEquals("N100 G81 R10 Z0 F100 ",addUntil.applyChanges(line1));
		assertEquals("G82 X40. Y32.25 ",addUntil.applyChanges(line2));
		assertEquals("G80 ",addUntil.applyChanges(line3));
	}
	
	@Test
	public void testAddingUntilIII()
	{
		instruction = "N0?N5vvT%t";
		
		String line1= "N0 G81 R10 Z0 F100";
		String line2 = "X40. Y32.25";
		String line3 = "Y60";
		String line4 = "T1 M6";
		
		Instruction addUntil= InstructionFactory.createInstuction(instruction);
		
		
		assertEquals("N0 G81 R10 Z0 F100 ",addUntil.applyChanges(line1));
		assertEquals("N5 X40. Y32.25 ",addUntil.applyChanges(line2));
		assertEquals("N5 Y60 ",addUntil.applyChanges(line3));
		assertEquals("T1 M6 ",addUntil.applyChanges(line4));
	}
	
	@Test
	public void testLimit1()
	{
		instruction = "Z<MAX>400";
		String line = "N500 G0Z-450 M5";
		
		Instruction limit= InstructionFactory.createInstuction(instruction);
		
		assertEquals("N500 G0 Z-450 M5 ",limit.applyChanges(line));
	}
	
	@Test
	public void testLimit2()
	{
		instruction = "Z<MAX>400";
		String line = "N500 G0 Z450 M5";
		
		Instruction limit= InstructionFactory.createInstuction(instruction);
		
		assertEquals("N500 G0 Z400 M5 ",limit.applyChanges(line));
	}
	
	@Test
	public void testLimit3()
	{
		instruction = "F<MAX>10000.100";
		String line = "N500 G1 F12000 M5";
		
		Instruction limit= InstructionFactory.createInstuction(instruction);
		
		assertEquals("N500 G1 F10000.100 M5 ",limit.applyChanges(line));
	}
	@Test
	public void testLimit4()
	{
		instruction = "F<MAX>10000.100";
		String line = "N500 G1 F9999.999 M5";
		
		Instruction limit= InstructionFactory.createInstuction(instruction);
		
		assertEquals("N500 G1 F9999.999 M5 ",limit.applyChanges(line));
	}
}
