package CordConverterTests;

import java.util.List;

import org.junit.Test;

import CordConverter.Circle;
import CordConverter.Point;
import CordConverter.TYPE;
import junit.framework.Assert;

import static org.junit.Assert.*;

public class TestCircle {

	Point first ;
	Point second ;
	
	@Test
	public void testTwoCirclesOnePointI()
	{
		first = new Point(1f,2f,TYPE.XY_POINT);
		second = new Point(3f,2f,TYPE.XY_POINT);
		List<Point> result = Circle.points(first, second, 1f);
		
		assertTrue(result.size()==1);
	}
	
	@Test
	public void testTwoCirclesTwoPointI()
	{
		first = new Point(1f,2f,TYPE.XY_POINT);
		second = new Point(2.5f,2f,TYPE.XY_POINT);
		List<Point> result = Circle.points(first, second, 2f);
		
		
		assertTrue(result.size()==2);
	}
	@Test
	public void testTwoCirclesNoPointI()
	{
		first = new Point(1f,2f,TYPE.XY_POINT);
		second = new Point(6f,2f,TYPE.XY_POINT);
		List<Point> result = Circle.points(first, second, 2f);
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testTwoCirclesOnePointII()
	{
		first = new Point(2f,2f,TYPE.XY_POINT);
		second = new Point(2f,4f,TYPE.XY_POINT);
		List<Point> result = Circle.points(first, second, 1f);
	
		
		assertTrue(result.size()==1);
	}
	
	@Test
	public void testTwoCirclesTwoPointII()
	{
		first = new Point(2f,2f,TYPE.XY_POINT);
		second = new Point(2f,2.5f,TYPE.XY_POINT);
		List<Point> result = Circle.points(first, second, 1.5f);
		
		assertTrue(result.size()==2);
	}
	@Test
	public void testTwoCirclesNoPointII()
	{
		first = new Point(2f,2f,TYPE.XY_POINT);
		second = new Point(2f,10f,TYPE.XY_POINT);
		List<Point> result = Circle.points(first, second, 2f);
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testCalculatingCirclePointI()
	{
		Point firstPoint = new Point(2f,2f,TYPE.XY_POINT);
		Point secondPoint = new Point(4f,0f,TYPE.XY_POINT);
		float radius = 2f;
		
		assertEquals(new Point(4f,2f,TYPE.XY_POINT),Circle.calculateCirclePoint(firstPoint, secondPoint, 3, radius));
	}
	
	@Test
	public void testCalculatingCirclePointII()
	{
		Point firstPoint = new Point(0f,0f,TYPE.XY_POINT);
		Point secondPoint = new Point(9f,0f,TYPE.XY_POINT);
		float radius = 4.5f;
		
		assertEquals(new Point(4.5f,0f,TYPE.XY_POINT),Circle.calculateCirclePoint(firstPoint, secondPoint, 3, radius));
	}
	
	
	@Test
	public void testCalculatingPointsBetweenCirclesI()
	{
		Circle firstCircle = Circle.createCircleWithCenterAndRadius(new Point(0f,0f,TYPE.XY_POINT), 2f);
		Circle secondCircle = Circle.createCircleWithCenterAndRadius(new Point(8f,0f,TYPE.XY_POINT), 2f);
		
		Point pointBetweenCircles = Circle.pointBetweenCircles(firstCircle,secondCircle);
	
		assertEquals(new Point(4f,0f,TYPE.XY_POINT), pointBetweenCircles);
	}
	
	@Test
	public void testCalculatingPointsBetweenCirclesII()
	{
		Circle firstCircle = Circle.createCircleWithCenterAndRadius(new Point(2f,4f,TYPE.XY_POINT), 2f);
		Circle secondCircle = Circle.createCircleWithCenterAndRadius(new Point(8f,2f,TYPE.XY_POINT), 2f);
		
		Point pointBetweenCircles = Circle.pointBetweenCircles(firstCircle,secondCircle);
	
		assertEquals(new Point(5f,3f,TYPE.XY_POINT), pointBetweenCircles);
	}
	@Test
	public void testCalculatingPointsBetweenCirclesIII()
	{
		Circle firstCircle = Circle.createCircleWithCenterAndRadius(new Point(0f,5f,TYPE.XY_POINT), 2f);
		Circle secondCircle = Circle.createCircleWithCenterAndRadius(new Point(0f,2f,TYPE.XY_POINT), 2f);
		
		Point pointBetweenCircles = Circle.pointBetweenCircles(firstCircle,secondCircle);
	
		assertEquals(new Point(0f,3.5f,TYPE.XY_POINT), pointBetweenCircles);
	}
	
}
