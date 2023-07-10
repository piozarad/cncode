package CordConverterTests;

import org.junit.Test;

import syntaxError.BracketNotClosedError;
import syntaxError.DoubleICodeInSameBlockError;
import syntaxError.DoubleJCodeInSameBlockError;
import syntaxError.DoubleKCodesInSameBlockError;
import syntaxError.DoubleMCodeError;
import syntaxError.DoubleTCodeError;
import syntaxError.LowerCaseCharactererror;
import syntaxError.MultipleBracketsError;
import syntaxError.NumberWithoutCodeError;
import syntaxError.SWithDotError;
import syntaxError.SlashNotAtBeginningError;
import syntaxError.SyntaxError;

import static org.junit.Assert.*;

public class TestSyntaxErrors {
	
	@Test
	public void testDoubleTCodeError()
	{
		String badBlock = "N90 T15 M6 T5";
		String block = "N90 T15 M6";
	
		SyntaxError checkTCodeError = new DoubleTCodeError();
		
		assertTrue(checkTCodeError.checkError(badBlock));
		assertFalse(checkTCodeError.checkError(block));
		
	}
	

	@Test
	public void testDoubleMCodeError()
	{
		String badBlock = "N90 T15 M6 M3";
		String block = "N90 T15 M6";
	
		SyntaxError checkMCodeError = new DoubleMCodeError();
		
		assertTrue(checkMCodeError.checkError(badBlock));
		assertFalse(checkMCodeError.checkError(block));
		
	}
	
	@Test
	public void testSlashNotAtBeginning()
	{
		String badBlock = "N90 T15 / M6 M3";
		String block = " /N90 T15 M6";
	
		SyntaxError slash = new SlashNotAtBeginningError();
		
		assertTrue(slash.checkError(badBlock));
		assertFalse(slash.checkError(block));
		
	}
	
	@Test
	public void testNumberWithoutCodeError()
	{
		String badBlock = "N50 S 5000 M3";
		String block = "N55 S2000 M3";
		
		SyntaxError number = new NumberWithoutCodeError();
		
		assertTrue(number.checkError(badBlock));
		assertFalse(number.checkError(block));
	}
	
	
	@Test
	public void testSWithDotError()
	{
		String badBlock = "N50 S200.3";
		String block = "N55 S2000 M3";
		
		SyntaxError number = new SWithDotError();
		
		assertFalse(number.checkError(block));
		assertTrue(number.checkError(badBlock));
		
	}
	
	@Test
	public void testDoubleIInSameBlockError()
	{
		String block = "N55 G3 X10 Y0 I10 I0 J1";
		
		SyntaxError number = new DoubleICodeInSameBlockError();
		
		assertTrue(number.checkError(block));
	}
	@Test
	public void testDoubleIinSameBlockErrorI()
	{
		String block = "N55 G3 X10 Y0 I10 J0";
		
		SyntaxError number = new DoubleICodeInSameBlockError();
		
		assertFalse(number.checkError(block));
	}
	
	@Test
	public void testDoubleJSameBlockError()
	{
		String block = "N55 G3 X10 Y0 I10 J6 J1";
		
		SyntaxError number = new DoubleJCodeInSameBlockError();
		
		assertTrue(number.checkError(block));
	}
	@Test
	public void testDoubleJSameBlockErrorI()
	{
		String block = "N55 G3 X10 Y0 I10 J0";
		
		SyntaxError number = new DoubleJCodeInSameBlockError();
		
		assertFalse(number.checkError(block));
	}
	
	@Test
	public void testDoubleKSameBlockError()
	{
		String block = "N55 G3 X10 Y0 I10 K0 K5";
		
		SyntaxError number = new DoubleKCodesInSameBlockError();
		
		assertTrue(number.checkError(block));
	}
	@Test
	public void testDoubleKSameBlockErrorI()
	{
		String block = "N55 G3 X10 Y0 I10 K5";
		
		SyntaxError number = new DoubleKCodesInSameBlockError();
		
		assertFalse(number.checkError(block));
	}
	@Test
	public void testDoubleBracketI()
	{
		String block = "(KOMENTARZ) (DRUGI KOMENTARZ)";
		
		SyntaxError number = new MultipleBracketsError();
		
		assertFalse(number.checkError(block));
	}
	
	@Test
	public void testDoubleBracketII()
	{
		String block = "(KOMENTARZ(ZAGNIEZDZONY KOMENTARZ)) ";
		
		SyntaxError number = new MultipleBracketsError();
		
		assertTrue(number.checkError(block));
	}
	
	@Test
	public void testBracketNotClosedErrorI()
	{
		String block = "(KOMENTARZ ";
		
		SyntaxError number = new BracketNotClosedError();
		
		assertTrue(number.checkError(block));
	}
	@Test
	public void testBracketNotClosedErrorII()
	{
		String block = "KOMENTARZ) ";
		
		SyntaxError number = new BracketNotClosedError();
		
		assertTrue(number.checkError(block));
	}
	@Test
	public void testBracketNotClosedErrorIII()
	{
		String block = "(KOMENTARZ(ZAGNIEZDZONY KOMENTARZ)) ";
		
		SyntaxError number = new BracketNotClosedError();
		
		assertFalse(number.checkError(block));
	}
	
	@Test
	public void testLowerCaseCharacterI()
	{
		String block = "G90 G54 G0 G43 H1 Z400";
		
		SyntaxError number = new LowerCaseCharactererror();
		
		assertFalse(number.checkError(block));
	}
	
	@Test
	public void testLowerCaseCharacterII()
	{
		String block = "G90 G54 G0 G43 h1 Z400";
		
		SyntaxError number = new LowerCaseCharactererror();
		
		assertTrue(number.checkError(block));
	}
}
