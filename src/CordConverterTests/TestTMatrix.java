package CordConverterTests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import CordConverter.Point;
import Mathematic.TMatrix;

public class TestTMatrix {
	
	@Test
	public void testSingletonGetInstanceI()
	{
		TMatrix matrix = null;
		matrix = TMatrix.getInstance();
		
		Assert.assertNotNull(matrix);
	}
	
	@Test
	public void testUpdatingPoint() 
	{
		TMatrix matrix = TMatrix.getInstance();
		Point p = new Point(1f,1f,1f);
		Method method;
		try {
			method = TMatrix.class.getDeclaredMethod("update", CordConverter.Point.class);
			method.setAccessible(true);
			method.invoke(matrix, p);
			
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
		
			e.printStackTrace();
		}
		
		Assert.assertEquals(new Point(1f,1f,1f), TMatrix.getPoint());

	}
	
	@Test
	public void testRorateArroundX()
	{
		Point p = new Point(2f,3f,4f);
		Float angle = 30.f;
		
		TMatrix.rotateX(angle, p);
		
		Assert.assertEquals(new Point(2f, 4.098f, 1.464f), TMatrix.getPoint() );
	}
	
	@Test
	public void testRorateArroundY()
	{
		Point p = new Point(2f,3f,4f);
		Float angle = 30.f;
		
		TMatrix.rotateY(angle, p);
		
		Assert.assertEquals(new Point(0.732f, 3.f, 5.464f), TMatrix.getPoint() );
	} 
	
	@Test
	public void testRorateArroundZ()
	{
		Point p = new Point(2f,3f,4f);
		Float angle = 30.f;
		
		TMatrix.rotateZ(angle, p);
		
		Assert.assertEquals(new Point(2.732f, 1.098f, 4f), TMatrix.getPoint() );
	}
}
