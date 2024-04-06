package CordConverter;

public class Line {


	private float a;
	private float b;
	//y=ax+b
	
	
	
	public Line(float a,float b)
	{
		this.a=a;
		this.b=b;
		
	}
	public Line(Point start, Point end)
	{
		this.a=(end.getY()-start.getY())/(end.getX()-start.getX());
		this.b= end.getY() - (a * end.getX());

	}
	
	
	/**
	 * 
	 * @return  angle between line and 0X axis in degrees
	 */
	public float getAngle0x()
	{
		return 180*((float) ((Math.atan( a ))/Math.PI));
	}

	public float yValue(float xValue)
	{
		return a* xValue +b;
		
	}
	
	public float xValue(float yValue)
	{
		if(a==0)
			return b;
		else 
		return (yValue-b)/a;
		
	}
	
	public float getA()
	{
		return this.a;
		
	}
	
	public float getB()
	{
		return this.b;
	}
	
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public Line rotateArroundPoint(Point point)
	{
		Line resultLine = (Line) this.clone();
		
		resultLine.a = -1/resultLine.a;
		resultLine.b = point.getY()-resultLine.a * point.getX();
		
		return resultLine;
	}
	
//	/**
//	 * 
//	 * @param point Point around which line will rotate
//	 * @param angle in degrees,posive to the right, negative to left
//	 * @return new Line rotated around given point by given angle
//	 */
//	public Line rotateArroundPoint(Point point, float angle)
//	{
//		Line resultLine = (Line) this.clone();
//		
//		
//		//TODO dokonczyc metode
//		
//		resultLine.a = -1/resultLine.a;
//		resultLine.b = point.getY()-resultLine.a * point.getX();
//		
//		return resultLine;
//	}
	
	
	/**
	 * 
	 * @param distance dystans od punktu poczatkowego
	 * @param startingPoint punkt poczatkowy
	 * @return zwraca nowy punkt oddalony o podany dystans na prostej
	 */
	public Point distanceOnLine(float distance, Point startingPoint)
	{
		float deltaX = (float) (distance * Math.cos(Math.atan(a)));
		float deltaY = (float) (distance * Math.sin(Math.atan(a)));
		
		return new Point(startingPoint.getX() + deltaX, startingPoint.getY() + deltaY,TYPE.XY_POINT);
	}
	
	
	/**
	 * 
	 * @param p Point which is being checked 
	 * @return return true if given point is above this line
	 */
	
	public boolean isGreater(Point p)
	{
		return p.getY()>this.a*p.getX() +b;
	}
	
	
	
	
	
	
	@Override
	public Object clone()
	{
		return new Line(this.a,this.b);
		
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		
		if(!(o instanceof Line))
		{
			return false;
		}
		
		else
		{
			if(((Line)o).a - this.a <0.0001 && ((Line)o).b - this.b <0.0001)
				return true;
			else return false;
			

		}
	}
	

	
	
	@Override
	public String toString()
	{
		return Point.roundToThree(a) +"x+" + Point.roundToThree(b); 
		
	}

	
	
	
}
