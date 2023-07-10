package CordConverterTests;

import org.junit.Assert;
import org.junit.Test;

import CordConverter.Point;
import CordConverter.Wektor;


public class TestWektor {
	
	//test dopuszczalny blad
	private final float DELTA= 0.001f;
	
	//PRAWO, LOWO STALE
	private final int PRAWO =2;
	private final int LEWO =3;
	
	
	// wektor constants
	Wektor pierwszy = new Wektor(4,0); 	//0st
	Wektor drugi = new Wektor(1,1);     //45st
	Wektor trzeci = new Wektor(0,3);    //90st
	Wektor czwarty = new Wektor(-1,0);  //180st
	Wektor piaty = new Wektor(-1,-1);   //135st
	

	//wektor class

	@Test
	public void testWektorEquality()
	{
		Wektor wektor = new Wektor(pierwszy.getStartingPoint(),pierwszy.getEndPoint());		
		Assert.assertTrue(pierwszy.equals(wektor));
	}
	
	
	@Test
	public void testOdwrocWektor()
	{
	
	Wektor wektor = new Wektor(pierwszy.getEndPoint(), pierwszy.getStartingPoint());

	
	wektor.odwrocWektor();
	//System.out.println("Pierwszy punkty: " + pierwszy.getStartingPoint() +" " + pierwszy.getEndPoint() +"  drugi: " + drugi.getStartingPoint() + " " + drugi.getEndPoint());
	
	Assert.assertEquals(pierwszy,wektor);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testKatNieskierowanyJedenWektorODlugosciZero()
	{
		Math.abs(pierwszy.calculateAngle(new Wektor()));				// nowy wektor ma dlugosc 0 powinien wyskoczyc blad
	}
	
	@Test
	public void testKatNieskierowany()
	{
		Assert.assertEquals(45,Math.abs(pierwszy.calculateAngle(drugi)), DELTA);	
		Assert.assertEquals(90,Math.abs(pierwszy.calculateAngle(trzeci)), DELTA);
		Assert.assertEquals(180,Math.abs(pierwszy.calculateAngle(czwarty)), DELTA);
		Assert.assertEquals(135,Math.abs(pierwszy.calculateAngle(piaty)), DELTA); 		//powinno wyjsc 360-225st =135
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testKatSkierowanyIllegalArgument()
	{
		Wektor pierwszy = new Wektor(4,0);
		pierwszy.katSkierowanyMiedzyWektorami(1,  new Wektor(1,0)); //zly kierunek - powinien wyskoczyc blad przez podana liczbe 1 w argumecie
	}

	@Test
	public void testKatSkierowany()
	{
		Assert.assertEquals(360,Math.abs(pierwszy.katSkierowanyMiedzyWektorami(LEWO,  new Wektor(1,0))),DELTA);
		Assert.assertEquals(45,Math.abs(pierwszy.katSkierowanyMiedzyWektorami(LEWO,  drugi)),DELTA);
		Assert.assertEquals(90,Math.abs(pierwszy.katSkierowanyMiedzyWektorami(LEWO,  trzeci)),DELTA);
		Assert.assertEquals(180,Math.abs(pierwszy.katSkierowanyMiedzyWektorami(LEWO,  czwarty)),DELTA);
		Assert.assertEquals(225,Math.abs(pierwszy.katSkierowanyMiedzyWektorami(LEWO, piaty)),DELTA);  	//mierzony w lewo
		Assert.assertEquals(135,Math.abs(pierwszy.katSkierowanyMiedzyWektorami(PRAWO,  piaty)),DELTA);	//mierzony w prawo
		Assert.assertEquals(315,Math.abs(pierwszy.katSkierowanyMiedzyWektorami(PRAWO,  drugi)),DELTA);	//mierzony w prawo
	}
	
	
	
	@Test
	public void testWektorEquals()
	{
		Wektor w = new Wektor(-50.2f,21.5f);
		
		Assert.assertEquals(new Wektor(-50.200002f, 21.5f),w);
	}
	
	@Test
	public void testcreatingWektorFromTwoPoints()
	{
		Point punktPoczatkowy = new Point(300f,250f);
		Point punktKoncowy = new Point(250f,300f);
		
		Wektor w = new Wektor(punktPoczatkowy,punktKoncowy);
		
		Assert.assertEquals(-50f,w.getX(),0.001f);
		Assert.assertEquals(50f,w.getY(),0.001f);
	}
	
	@Test
	public void testKadUkladuBiegunowego()
	{
		Point punktPoczatkowy = new Point(300f,300f);
		Point punktKoncowy = new Point(250f,300f);
	
		Wektor w = new Wektor(punktPoczatkowy,punktKoncowy);
		float kat = w.katUkladuBiegunowego();
		
		Assert.assertEquals(180f, kat, 0.001);
	}
	
	@Test
	public void testKadUkladuBiegunowegoI()
	{
		Point punktPoczatkowy = new Point(300f,300f);
		Point punktKoncowy = new Point(250f,250f);
	
		Wektor w = new Wektor(punktPoczatkowy,punktKoncowy);
		float kat = w.katUkladuBiegunowego();
		
		Assert.assertEquals(225f, kat, 0.001);
	}
	
	
}





















