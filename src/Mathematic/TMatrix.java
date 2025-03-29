package Mathematic;

import java.util.Optional;

import CordConverter.Function;
import CordConverter.Point;

public class TMatrix {
	
	public enum AXIS {X, Y, Z}
	
	private static TMatrix matrix;
	
	private static float x=0;
	private static float y=0;
	private static float z=0;

	
	public static TMatrix getInstance()
	{
		if(matrix == null) return new TMatrix();
		
		return matrix;
	}
	
	private static void update(Point p)
	{
		TMatrix.x=Optional.ofNullable(p.getX()).orElseGet(()->0f);
		TMatrix.y=Optional.ofNullable(p.getY()).orElseGet(()->0f);
		TMatrix.z=Optional.ofNullable(p.getZ()).orElseGet(()->0f);
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
		
		float y1 = y * cos - (sin * z);
		float z1 = y * sin + (cos * z);
		y=y1;
		z=z1;
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
		
		float x1 = x * cos + (sin * z);
		float z1 = -x* sin + (cos * z);
		x=x1;
		z=z1;
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
		
		float x1 = x * cos - (sin * y);
		float y1 = y * cos + (sin * x);
		x=x1;
		y=y1;
	}
	
	public static void rotateZ(float angle, Point p)
	{
		TMatrix.update(p);
		rotateZ(angle);
	}
	
	/**
	 * Rotates cordinates in given g code function arround given axis
	 * @param func g code function string
	 * @param angle in degrees. Positive angle is determined by right hand rule
	 * @param axis enum: X,Y or Z specify rotated axis
	 * @return
	 */
	public static Function rotateGCodeBlock(Function func, float angle, AXIS axis) {
		
		//rotate point
		if(func.getPoint()!=null)
		{
			Point point = func.getPoint();
			switch(axis)
			{
				case X:
					TMatrix.rotateX(angle, point);
					break;
				case Y:
					TMatrix.rotateY(angle, point);
					break;
				case Z:
					TMatrix.rotateZ(angle, point);
					break;
			}
			func.setPoint(TMatrix.getPoint());
			
			//rotate R in fixed cycle
			if(!(func.containsFunction(2,3)) && func.getCircle().get('R') != null && axis != AXIS.Z)
			{
				Point temp = new Point(point.getX(), point.getY(), func.getCircle().get('R'));
			
				if(axis == AXIS.X)
				{
					rotateX(angle, temp);
					func.setCircle(TMatrix.getPoint().getZ());
				}
				if(axis == AXIS.Y)
				{
					rotateY(angle, temp);
					func.setCircle(TMatrix.getPoint().getZ());
				}
			}
		}
		
		
		
		return func;
	}
}
