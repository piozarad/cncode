package Mathematic;

import CordConverter.Point;

public class TMatrix {
	
	private static TMatrix MATRIX;
	
	private static float x=0;
	private static float y=0;
	private static float z=0;

	
	
	public static TMatrix getInstance()
	{
		if(MATRIX == null) return new TMatrix();
		
		return MATRIX;
	}
	
	
	private static void update(Point p)
	{
		TMatrix.x=p.getX();
		TMatrix.y=p.getY();
		TMatrix.z=p.getZ();
	}
	
	public static Point getPoint()
	{
		return new Point(x,y,z);
	}
	
	private static void rotateX(float angle)
	{
		angle = angle * (float)Math.PI/180;
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		
		y = y * cos + (sin * y);
		z = -z * sin + (cos * z);
	}
	
	public static void rotateX(float angle, Point p)
	{
		TMatrix.update(p);
		rotateX(angle);
	}
	
	private static void rotateY(float angle)
	{
		angle = angle * (float)Math.PI/180;
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		
		x = x * cos - (sin * x);
		z = z * sin + (cos * z);
	}
	
	public static void rotateY(float angle, Point p)
	{
		TMatrix.update(p);
		rotateY(angle);
	}
	
	private static void rotateZ(float angle)
	{
		angle = angle * (float)Math.PI/180;
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		
		x = x * cos + (sin * x);
		y = y * cos - (sin * y);
	}
	
	public static void rotateZ(float angle, Point p)
	{
		TMatrix.update(p);
		rotateZ(angle);
	}
	
}
