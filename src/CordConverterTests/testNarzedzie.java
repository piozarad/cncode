package CordConverterTests;



import org.junit.Assert;
import org.junit.Test;

import BasicControls.SterowanieFanuc;
import BasicControls.SterowanieOkuma;
import CordConverter.Function;
import CordConverter.Narzedzie;




public class testNarzedzie {

	
	
	
	@Test
	public void testSzukajNarzedzia()
	{
		Function f = new Function("(GLOWICA FI125)");
		
		Narzedzie n = new Narzedzie();
		n.updateToolDiameter(f);
		
		Assert.assertEquals(125, n.getToolDiameter(), 0.01);
		
	}
	
	@Test
	public void testUpdatetoolInfoFalseOutcome()
	{
		Function functionM30 = new Function("N1005 M30");
		Function functionM99 = new Function("N1005 M99");
		Function functionM6One = new Function("N1005 T1 M6");
		Function functionM6Two = new Function("N1005 T2M6");
		
		
		Narzedzie n = new Narzedzie();
		//m30
		Assert.assertTrue(n.updateToolInfo(functionM30));
		//m99
		Assert.assertTrue(n.updateToolInfo(functionM99));
		//m6
		Assert.assertFalse(n.updateToolInfo(functionM6One));
		Assert.assertTrue(n.updateToolInfo(functionM6Two));
	}
	
	@Test
	public void testUpdatingToolNumber()
	{
		Function funtionWithToolChange = new Function("T1 M6");
		Function funtionWithToolChangeTwo = new Function("T2");
		
		Narzedzie n = new Narzedzie();
		Assert.assertEquals(-1, n.getToolNumber());
		
		n.updateToolNumber(funtionWithToolChange);
		n.updateToolNumber(funtionWithToolChangeTwo);
		
		Assert.assertEquals(1, n.getToolNumber());
		

	}
	
	@Test
	public void testUpdatingH()
	{
		Function functionWithH = new Function("G1 G43 H10 Z400",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		Assert.assertTrue(n.getH().equals("0"));
		n.updateH(functionWithH);
		
		Assert.assertTrue( n.getH().equals("10"));
	}
	
	
	@Test
	public void testUpdatingHOkuma()
	{
		Function functionWithH = new Function("G90 G56 HA DA",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		Assert.assertTrue(n.getH().equals("0"));
		n.updateH(functionWithH);
		
		Assert.assertTrue( n.getH().equals("A"));
		
		
	}
	
	@Test
	public void testUpdatingD()
	{
		Function functionWithD = new Function("G1 G41 D2 Z400",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		Assert.assertTrue(n.getD().equals("0"));
		n.updateD(functionWithD);
		
		Assert.assertTrue( n.getD().equals("2"));

	}
	
	@Test
	public void testUpdatingDOkuma()
	{
		Function functionWithD = new Function("G1 DB G41 X50",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		Assert.assertTrue(n.getD().equals("0"));
		n.updateD(functionWithD);
		
		Assert.assertTrue( n.getD().equals("B"));
	}
	
	
	@Test
	public void testUpdateNazwaNarzedzia1()
	{
		
		Function functionWithToolName = new Function("(FREZ FI18)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		Assert.assertTrue(n.getToolName().equals("Brak"));
		n.updateToolName(functionWithToolName);
		
		Assert.assertTrue( n.getToolName().equals("Frez"));

	}
	
	@Test
	public void testUpdateNazwaNarzedzia2()
	{
		
		Function functionWithToolName = new Function("(FREZ WALCOWO-CZOLOWY FI18)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);
		
		Assert.assertTrue( n.getToolName().equals("Frez w-c"));

	}
	
	@Test
	public void testUpdateNazwaNarzedzia3()
	{
		
		Function functionWithToolName = new Function("(FREZ TARCZOWY FI18)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);
		
		Assert.assertTrue( n.getToolName().equals("F. tarczowy"));

	}
	
	@Test
	public void testUpdateNazwaNarzedzia4()
	{		
		Function functionWithToolName = new Function("(FREZ SPECJALNY)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Frez specjalny"));
	}

	@Test
	public void testUpdateNazwaNarzedzia5()
	{		
		Function functionWithToolName = new Function("(FREZ TARCZOWY)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("F. tarczowy"));
	}
	
	@Test
	public void testUpdateNazwaNarzedzia6()
	{		
		Function functionWithToolName = new Function("(FREZ JEZOWY)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("F. je¿owy"));
	}
	
	@Test
	public void testUpdateNazwaNarzedzia7()
	{		
		Function functionWithToolName = new Function("(WIERTLO)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Wiertlo"));
	}
	
	@Test
	public void testUpdateNazwaNarzedzia8()
	{		
		Function functionWithToolName = new Function("(PILOT)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Wiertlo pilotowe"));
	}
	
	@Test
	public void testUpdateNazwaNarzedzia9()
	{		
		Function functionWithToolName = new Function("(WIERTLO PLYTKOWE)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Wiertlo"));
	}
	
	@Test
	public void testUpdateNazwaNarzedzia10()
	{		
		Function functionWithToolName = new Function("(GROT)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("W. Grot"));
	}
	
	@Test
	public void testUpdateNazwaNarzedzia11()
	{		
		Function functionWithToolName = new Function("(GLOWICA)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("G³owica"));
	}
	@Test
	public void testUpdateNazwaNarzedzia12()
	{		
		Function functionWithToolName = new Function("(POGLEBIACZ)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Pog³ebiacz"));
	}
	@Test
	public void testUpdateNazwaNarzedzia13()
	{		
		Function functionWithToolName = new Function("(OBTACZADLO)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Obtaczad³o"));
	}
	@Test
	public void testUpdateNazwaNarzedzia14()
	{		
		Function functionWithToolName = new Function("(WYTACZADLO)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Wytaczad³o"));
	}
	@Test
	public void testUpdateNazwaNarzedzia15()
	{		
		Function functionWithToolName = new Function("(ROZWIER)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Rozwiertak"));
	}
	@Test
	public void testUpdateNazwaNarzedzia16()
	{		
		Function functionWithToolName = new Function("(GWINT)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Gwintownik"));
	}
	@Test
	public void testUpdateNazwaNarzedzia17()
	{		
		Function functionWithToolName = new Function("(WYGNIAT)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Wygniatak"));
	}
	@Test
	public void testUpdateNazwaNarzedzia18()
	{		
		Function functionWithToolName = new Function("(DOGNI)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Dogniatak"));
	}
	@Test
	public void testUpdateNazwaNarzedzia19()
	{		
		Function functionWithToolName = new Function("(FAZA)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Fazownik"));
	}
	@Test
	public void testUpdateNazwaNarzedzia20()
	{		
		Function functionWithToolName = new Function("(KULA)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Frez kulowy"));
	}
	
	
	@Test
	public void testUpdateNazwaNarzedzia21()
	{		
		Function functionWithToolName = new Function("(SPEC)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Narzêdzie specjalne"));
	}
	
	@Test
	public void testUpdateNazwaNarzedzia22()
	{		
		Function functionWithToolName = new Function("(NAW)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Nawiertak"));
	}
	
	@Test
	public void testUpdateNazwaNarzedzia23()
	{		
		Function functionWithToolName = new Function("(NAKIELEK)",new SterowanieFanuc());
		
		Narzedzie n = new Narzedzie();
		n.updateToolName(functionWithToolName);

		Assert.assertTrue( n.getToolName().equals("Nakielek"));
	}
	
	// koniec testow nazwy narzedzia
	
	@Test
	public void testUpdatingToolDiameter()
	{
		Function function = new Function("(Frez FI20)");
		
		Narzedzie n = new Narzedzie();
		n.updateToolDiameter(function);
		
		
		Assert.assertTrue(n.getToolDiameter()-20 <0.0001);
	}
	
	
	@Test
	public void testUpdatingToolDiameter2()
	{
		Function function = new Function("(WIERTLO FI12.5)");
		
		Narzedzie n = new Narzedzie();
		n.updateToolDiameter(function);
		
		Assert.assertTrue(n.getToolDiameter()-12.5 <0.0001);
	}
	
	
	@Test
	public void testUpdatingCoolantTypeFanuc()
	{
		Function f = new Function("N305 G0 Z400 M12");
		Narzedzie n = new Narzedzie();
		n.updateCoolantType(f,new SterowanieFanuc());
		
		Assert.assertEquals(" przez wrzeciono",n.getCoolantType());
	}
	
	@Test
	public void testUpdatingCoolantTypeFanuc2()
	{
		Function f = new Function("N305 G0 Z400 M8");
		Narzedzie n = new Narzedzie();
		n.updateCoolantType(f,new SterowanieFanuc());
		
		Assert.assertEquals(" przez dysze",n.getCoolantType());
	}
	
	@Test
	public void testUpdatingCoolantTypeOkuma()
	{
		Function f = new Function("N305 G0 Z400 M8");
		Narzedzie n = new Narzedzie();
		n.updateCoolantType(f,new SterowanieOkuma());
		
		Assert.assertEquals(" przez dysze",n.getCoolantType());
	}
	
	@Test
	public void testUpdatingCoolantTypeOkuma2()
	{
		Function f = new Function("N305 G0 Z400 M51");
		Narzedzie n = new Narzedzie();
		n.updateCoolantType(f,new SterowanieOkuma());
		
		Assert.assertEquals(" przez wrzeciono",n.getCoolantType());
	}
	
	
}


