package CordConverterTests;

import org.junit.Assert;
import java.util.List;

import org.junit.Test;

import Mathematic.QuadraticEquation;

public class TestMathematic {
	
	@Test
	public void testCubicEquationAZeroI()
	{
		float a = 0f;
		float b = 2f;
		float c = 4f;
		
		QuadraticEquation.getInstance().newEquation(a, b, c).solve();
		
		List<Float> resultList = QuadraticEquation.getInstance().getResults();
		
		Assert.assertEquals( -2f,resultList.get(0),0.01f);
		
	}
	
	@Test
	public void testCubicEquationBZeroI()
	{
		float a = 1f;
		float b = 0f;
		float c = -4f;
		
		QuadraticEquation.getInstance().newEquation(a, b, c).solve();
		List<Float> resultList =QuadraticEquation.getInstance().getResults();

	
		Assert.assertTrue(Math.abs(resultList.get(0))-2f < 0.01);
		Assert.assertTrue(Math.abs(resultList.get(1))-2f < 0.01);
		
	}
	
	@Test
	public void testCubicEquationCZeroI()
	{
		float a = 4f;
		float b = 2f;
		float c = 0f;
		
		QuadraticEquation.getInstance().newEquation(a, b, c).solve();
		List<Float> resultList =QuadraticEquation.getInstance().getResults();
		
		Assert.assertTrue(Math.abs(resultList.get(0)) < 0.01);
		Assert.assertTrue(Math.abs(resultList.get(1))-0.5f < 0.01);
		
	}
	
	@Test
	public void testCubicEquationABZeroI()
	{
		float a = 0f;
		float b = 0f;
		float c = 4f;
		
		QuadraticEquation.getInstance().newEquation(a, b, c).solve();
		List<Float> resultList =QuadraticEquation.getInstance().getResults();
		Assert.assertTrue(resultList.isEmpty());

		
	}
	
	@Test
	public void testCubicEquationDeltaZero()
	{
		float a = 2f;
		float b = 4f;
		float c = 2f;
		
		QuadraticEquation.getInstance().newEquation(a, b, c).solve();
		List<Float> resultList =QuadraticEquation.getInstance().getResults();
		
		Assert.assertTrue(resultList.size()==1);
		Assert.assertTrue(resultList.get(0)+1 <0.01);
		
	}
	
	@Test
	public void testCubicEquationDeltaLessThanZero()
	{
		float a = 2f;
		float b = 4f;
		float c = 8f;
		
		QuadraticEquation.getInstance().newEquation(a, b, c).solve();
		List<Float> resultList =QuadraticEquation.getInstance().getResults();
	
		Assert.assertTrue(resultList.isEmpty());	
	}
	
	@Test
	public void testCubicEquationTwoResults()
	{
		float a = 2f;
		float b = 6f;
		float c = 2.5f;
		
		QuadraticEquation.getInstance().newEquation(a, b, c).solve();
		List<Float> resultList =QuadraticEquation.getInstance().getResults();
	
		Assert.assertTrue(resultList.size()==2);
		Assert.assertTrue(resultList.get(0)+2.5 <0.01);
		Assert.assertTrue(resultList.get(0)+0.5 <0.01);
		
	}
}
