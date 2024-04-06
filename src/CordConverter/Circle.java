package CordConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Mathematic.QuadraticEquation;

public class Circle {
	
	private int CircleDirection;
	private Point circleCenter;
	private float radius;
	private Wektor w;
	private Point startingPoint;
	private Point endingPoint;
	private int plane = 17;

	
	//TODO dodac obsluge zmiennej wektora k
	
	
	private  Circle(Point endingPoint)
	{
		this.endingPoint=endingPoint;
	}
	public Circle(Point endingPoint,float radius)
	{
		this(endingPoint);
		this.radius=radius;
	}
	
	public Circle(Point endingPoint, float i, float j)
	{
		this(endingPoint);
		this.w = new Wektor(i,j);
		radius = (float)Math.sqrt(i*i + j*j);
		
		this.startingPoint = new Point();
	}
	

	
	public Circle(Point startingPoint,Point endingPoint,float i, float j)
	{
		this(endingPoint,i,j);
		this.startingPoint = startingPoint;
		this.circleCenter = new Point(startingPoint.getX()+i,startingPoint.getY()+j, TYPE.XY_POINT);

	}
	
	
	
	public static Circle createCircleWithCenterAndRadius(Point circleCenter, float radius)
	{
		Circle result = new Circle(null);
		result.circleCenter = circleCenter;
		result.radius=radius;
		return result;
	}
	
	
	
	public void setPlane(int gCodePlane)
	{
		if(gCodePlane!=17 || gCodePlane!=18 || gCodePlane!=19)
		{
			Wind.log.writeErrorLog("Milling plane not equal to 17,18 or 19", new IllegalArgumentException(), this.getClass().getSimpleName());
			throw new IllegalArgumentException();
		}
		else
		{
			this.plane=gCodePlane;
		}
		
		
	}
	
	
	/**
	 * 
	 * @param a first Circle
	 * @param b second Circle
	 * @return returns a point between given centers of given circles
	 */
	public static Point pointBetweenCircles(Circle a, Circle b)
	{
		return new Point(
							(a.getCircleCenter().getX() + b.getCircleCenter().getX())/2,
							(a.getCircleCenter().getY() + b.getCircleCenter().getY())/2,
							TYPE.XY_POINT
						);
		
	}
	
	/**
	 * 
	 * @param first while G2 or G3 active this is the first point of calculated circle
	 * @param second point which is endPoint of calculated circle
	 * @param radius of the circle
	 * @return returns list of points (one, two or zero) which can be used as circle center and calculated to IJ vector. Zero points means that circle cannot be calculated from given data 
	 */
	public static List<Point> points(Point first, Point second, float radius)
	{
		List<Point> result = new ArrayList<>();
		Circle firstCircle = Circle.createCircleWithCenterAndRadius(first, radius);
		Circle secondCircle = Circle.createCircleWithCenterAndRadius(second, radius);
		
		if(doCirclesIntersect(firstCircle, secondCircle))
		{
			Line line=null;
			float a;
			float b; 
			float c; 
			float constX=0f;
			
			//check if line is x=cons
			if(Math.abs(firstCircle.getCircleCenter().getY() - secondCircle.getCircleCenter().getY()) <0.01)
			{
				constX = (secondCircle.getCircleCenter().getX()+firstCircle.getCircleCenter().getX())/2;
				
				a=1;
				b=-2*firstCircle.getCircleCenter().getY();
				c=(float)(Math.pow(secondCircle.getCircleCenter().getY(),2) -Math.pow(radius, 2) + Math.pow( (constX - firstCircle.getCircleCenter().getX()) ,2) );
			}
			else
			{
				line = new Line(first, second);
				line = line.rotateArroundPoint(Circle.pointBetweenCircles(firstCircle, secondCircle));
				a=(float)(1 + Math.pow(line.getA(),2));
				b= -2 * firstCircle.getCircleCenter().getX() + 2*line.getA()*line.getB() -2*firstCircle.getCircleCenter().getY()*line.getA();
				c= (float)( Math.pow(line.getB(), 2) -2*firstCircle.getCircleCenter().getY()*line.getB() + Math.pow(firstCircle.getCircleCenter().getY(),2) + 
						Math.pow(firstCircle.getCircleCenter().getX(), 2) - Math.pow(radius, 2));
			}
			
			
			//System.out.println("a=" + a + " b=" + b + " c="+c);
			

		
		
		QuadraticEquation.getInstance().newEquation(a, b, c).solve();
		
		for(Float x : QuadraticEquation.getInstance().getResults())
		{
			if(line!=null)
			{
				result.add(new Point(x,line.getA()*x + line.getB(),TYPE.XY_POINT));
			}
			else
			{
				//if line isn't initialised y is calculated insted of x 
				result.add(new Point(constX,x,TYPE.XY_POINT));
			}
		}
		
		}
	return result;
}
	
	
	
	public static Point calculateCirclePoint(Point startingPoint, Point endingPoint, int function, float radius)
	{
		
		Optional<Point> result= Optional.empty();
		if(function==3 || function ==2)
		{
			List<Point> points = points(startingPoint, endingPoint, radius);
			//System.out.println("List size=" +points.size());
			
			if(points.size()==1) result = Optional.of(points.get(0));
			else if (points.size()!=0)
			{
				Point averagePoint = Point.averagePoint(startingPoint, endingPoint);
				boolean right = startingPoint.getX() < endingPoint.getX();
				boolean xGreater = averagePoint.getX() > points.get(0).getX();
				boolean yGreater = averagePoint.getY() > points.get(0).getY();
				
				if(function==3)
				{
					if((right && xGreater && !yGreater) ||
					(right && !xGreater && !yGreater)||
					(!right && xGreater && yGreater) ||
					(!right && !xGreater && yGreater)) result = Optional.of(points.get(0));
					else result = Optional.of(points.get(1));
					
				}
				else
				{
					if((right && !xGreater && yGreater) ||
							(right && xGreater && yGreater)||
							(!right && !xGreater && !yGreater) ||
							(!right && xGreater && !yGreater)) result = Optional.of(points.get(0));
							else result = Optional.of(points.get(1));
				}
			}
		}
		return result.orElse(new Point());
	}
	
	public Point getCircleCenter()
	{
		return this.circleCenter;
	}
	public float getRadius()
	{
		return this.radius;
	}
	public Point getEndingpoint()
	{
		return this.endingPoint;
	}
	
	
	
	private static boolean doCirclesIntersect(Circle a, Circle b)
	{
		return Point.distanceXY(a.getCircleCenter(), b.getCircleCenter())  <= a.getRadius() + b.getRadius() ;
	}
	
	
	
	@Override
	public String toString()
	{
		return "Circle with center in " + this.circleCenter + " R=" + this.radius;
	}
	
	

}
