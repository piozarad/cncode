package CordConverterTests;

import org.junit.Assert;
import org.junit.Test;

import CordConverter.Line;
import CordConverter.Point;

public class TestLine {

	
	@Test
	public void testLineConstructor()
	{
		Line l = new Line(1f, 1f);
		Assert.assertNotNull(l);

	}
	@Test
	public void testConstructor2()
	{
		Line l = new Line(new Point(1f,1f),new Point(2f,2f));
		
		Assert.assertNotNull(l);
				
	}
	
	@Test
	public void testToStringFunction()
	{
		Line l = new Line(1,2);
		
		Assert.assertTrue(l.toString().equals("1.0x+2.0"));
		
	}
	@Test
	public void testToStringFunction2()
	{
		
		Line l = new Line(new Point(1f,1f),new Point(2f,2f));
		
		Assert.assertTrue(l.toString().equals("1.0x+0.0"));
		
	}
	@Test
	public void testComputing0xAngle()
	{
		Line l = new Line(new Point(1f,1f),new Point(2f,2f));

		Assert.assertEquals(45.0f, l.getAngle0x(), 0.001);
		
	}
	
	@Test
	public void testCalculatingYValueI()
	{
		Line l = new Line(1.5f,1.0f);
		Assert.assertEquals(l.yValue(1), 2.5,0.0001);
	}
	
	@Test
	public void testCalculatingYValueII()
	{
		Line l = new Line(0f,3.5f);
		Assert.assertEquals(l.yValue(3.5f), 3.5,0.0001);
	}
	
	@Test
	public void testCalculatingYValueIII()
	{
		Line l = new Line(1.256f,0);
		Assert.assertEquals(l.yValue(2), 2.512,0.0001);
	}
	
	@Test
	public void testCalculatingXValue()
	{
		Line l = new Line(1.256f,2);
		Assert.assertEquals(l.xValue(3), 0.7961783,0.0001);
	}
	@Test
	public void testCalculatingXValueII()
	{
		Line l = new Line(1.256f,0);
		Assert.assertEquals(l.xValue(2), 1.592356f,0.0001);	}
	@Test
	public void testCalculatingXValueIII()
	{
		Line l = new Line(0,2.12f);
		Assert.assertEquals(l.xValue(2), 2.12,0.0001);
	}
	
	@Test
	public void testEquals()
	{
		Line first = new Line(1f,4f);
		
		Line second = new Line(new Point(2f,6f),new Point(10f,14f));
		
		Assert.assertEquals(first,second);
				
	}
	
	
	
	@Test 
	public void testRotateArroundPoint()
	{
		Line line = new Line(1.5f,4f);
		
		Point p = new Point(2f,5f);
		
		Line secondLine = new Line(-0.6667f,6.33334f);
		line = line.rotateArroundPoint(p);
		
		Assert.assertEquals(line,secondLine);
	}
	
	@Test
	public void testDistanceFromPoint()
	{
		Line newLine = new Line(0.75f,1f);
		
		Point startingPoint = new Point (0f,1f);
		
		Assert.assertEquals(new Point(4f,4f),newLine.distanceOnLine(5, startingPoint));
	}
	
	
	@Test
	public void testIsGreater()
	{
		Line line  = new Line(2.5f,10f);
		
		Point point = new Point(-1f,-10f);
		
		Assert.assertFalse(line.isGreater(point));
	}
	
	@Test
	public void testIsGreaterII()
	{
		Line line  = new Line(2.5f,10f);
		
		Point point = new Point(0f,11f);
		
		Assert.assertTrue(line.isGreater(point));
	}
	
	
}
