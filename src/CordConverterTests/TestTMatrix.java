package CordConverterTests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import CordConverter.Point;
import CordConverter.Function;
import Mathematic.TMatrix;
import Mathematic.TMatrix.AXIS;

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
	
	@Test
	public void testRotateAFunction()
	{
		Function f = new Function("N10 G0 X10 Y15 Z20");
		
		TMatrix.rotateGCodeBlock(f, 45, TMatrix.AXIS.X);
		
		Assert.assertEquals(new Point(10f, 21.213f, 0f), f.getPoint());
	}
	
	@Test
	public void testRotateBFunction()
	{
		Function f = new Function("N10 G0 X10 Y15 Z20");
		
		TMatrix.rotateGCodeBlock(f, 45, TMatrix.AXIS.Y);
		
		Assert.assertEquals(new Point(0f, 15f, 28.284f), f.getPoint());
	}
	

	@Test
	public void testRotateCFunction()
	{
		Function f = new Function("N10 G0 X10 Y15 Z20");
		
		TMatrix.rotateGCodeBlock(f, 45, TMatrix.AXIS.Z);
		
		Assert.assertEquals(new Point(14.142f, 0f, 20f), f.getPoint());
	}
	@Test
	public void testRotateZInFixedCycleXRotated()
	{
		Function f = new Function("N10 X10 Y20 G81 R10 Z5.2 F200.");
		
		TMatrix.rotateGCodeBlock(f, 10, TMatrix.AXIS.X);
		
		Assert.assertEquals(8.593f, f.getPoint().getZ(),0.001);
	}
	
	@Test
	public void testRotateZInFixedCycleYRotated()
	{
		Function f = new Function("N10 X10 Y20 G81 R10 Z5.2 F200.");
		
		TMatrix.rotateGCodeBlock(f, -10, TMatrix.AXIS.Y);
		
		Assert.assertEquals(6.857f, f.getPoint().getZ(),0.001);
	}
	
	@Test
	public void testRotateZInFixedCycleZRotated()
	{
		Function f = new Function("N10 G81 R10 Z5.2 F200.");
		
		TMatrix.rotateGCodeBlock(f, 10, TMatrix.AXIS.Z);
		
		Assert.assertEquals(5.2f, f.getPoint().getZ(), 0.001);
	}
	
	@Test
	public void testRotateRParamInFixedCycleXRotated()
	{
		Function f = new Function("N10 X10. Y20. G81 R10 Z5.2 F200.");
		
		TMatrix.rotateGCodeBlock(f, 10, TMatrix.AXIS.X);
		
		Assert.assertEquals(13.321f, f.getCircle().get('R'), 0.01);
		
	}
	@Test
	public void testRotateRParamInFixedCycleYRotated()
	{
		Function f = new Function("N10 X10. Y20. G81 R10 Z5.2 F200.");
	
		TMatrix.rotateGCodeBlock(f, 10, TMatrix.AXIS.Y);
	
		Assert.assertEquals(8.111f, f.getCircle().get('R'), 0.01);	
	}
	
	@Test
	public void testCircXZleRoZttation()
	{
		Function f = new Function("G3 X10 Y0 I-10 J0.");
		
		TMatrix.rotateGCodeBlock(f, 45, AXIS.Z);
		
		Assert.assertEquals(7.07f, f.getPoint().getX(), 0.01);	
		Assert.assertEquals(7.07f, f.getPoint().getY(), 0.01);	
	}
	
	
	
	
	
	
}
