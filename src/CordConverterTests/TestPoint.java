package CordConverterTests;

import org.junit.Assert;
import org.junit.Test;

import CordConverter.Point;

public class TestPoint {
	
	
	 // point constants
		private Point pierwszyPunkt = new Point(1.5f,2.66f);
		private Point drugiPunkt = new Point(1.5f,2.66f);
		private Point punktDoSklonowania= new Point(1.66f,222.1f);
		
	
	
	
	
	//point class
	
		@Test
		public void testPointEquality()
		{
		
			Assert.assertTrue(pierwszyPunkt.equals(drugiPunkt));
		}
		@Test
		public void testUpdatePoint()
		{

			Assert.assertTrue(pierwszyPunkt.equals(drugiPunkt));
			drugiPunkt.updatePoint(new Point(1.2f,10f));
			Assert.assertTrue(!pierwszyPunkt.equals(drugiPunkt));
		}
		@Test
		public void testClonePoint()
		{
			Assert.assertTrue(punktDoSklonowania.equals(punktDoSklonowania.clone()));
		}
		
		
		
		@Test
		public void testTravelTimeZ()
		{
			Point previousPoint = new Point(0f,0f,400f);
			Point destinationPoint = new Point(0f,0f,10f);
			float feed = 10000f;
			
			float time = Point.travelTime(previousPoint, destinationPoint, feed);
			
			Assert.assertEquals(time, 2.34f, 0.01);
		}
		
		@Test
		public void testTravelTimeX()
		{
			Point previousPoint = new Point(400f,0f,400f);
			Point destinationPoint = new Point(-20f,0f,400f);
			float feed = 5000f;
			
			float time = Point.travelTime(previousPoint, destinationPoint, feed);
		

			Assert.assertEquals(time, 5.04f, 0.01);
		}
		
		@Test
		public void testTravelTimeY()
		{
			Point previousPoint = new Point(0f,20f,400f);
			Point destinationPoint = new Point(0f,220f,400f);
			float feed = 5000f;
			
			float time = Point.travelTime(previousPoint, destinationPoint, feed);
		

			Assert.assertEquals(time, 2.4f, 0.01);
		}
		
		@Test
		public void testTravelTimeXYZ()
		{
			Point previousPoint = new Point(0f,20f,0f);
			Point destinationPoint = new Point(20f,220f,400f);
			float feed = 5000f;
			
			float time = Point.travelTime(previousPoint, destinationPoint, feed);
		

			Assert.assertEquals(time, 5.371f, 0.01);
		}
		
		@Test
		public void testAveragePoint()
		{
			Point previousPoint = new Point(-15f,20f);
			Point nextPoint = new Point(15f,42f);
			
			Point average = Point.averagePoint(previousPoint, nextPoint);
			
			Assert.assertEquals(new Point(0f, 31f), average);
		
		}	
		

}
