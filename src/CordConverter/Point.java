package CordConverter;

public class Point implements Cloneable {

	private Float x;
	private Float y;
	private Float z;

	private boolean xVar=false;
	private boolean yVar=false;
	private boolean zVar=false;
	
	public Point() {

	}
	public Point(Float x, Float y) {
		
		this.x = roundToThree(x);
		this.y = roundToThree(y);
		
}
	public Point(Float x, Float y, boolean xV, boolean yV) {
	
			this.x = roundToThree(x);
			this.y = roundToThree(y);
			
			
			if(xV)
				xVar=true;
			if(yV)
				yVar =true;
	}
	public Point(Float x,Float y, Float z)
	{
		this.x = roundToThree(x);
		this.y = roundToThree(y);
		this.z = roundToThree(z);
	}

	public Point(Float z, boolean zV) {
		this.z = roundToThree(z);
		if(zV)
			zVar =true;
		
	}
	public Point(Float z) {
		
		this.z = roundToThree(z);
	}
	/**
	 * 
	 * @return Wartosc Float wspolrzednej X lub null
	 */
	
	public Float getX() {
		return this.x;

	}

	/**
	 * 
	 * @return Wartosc Float wspolrzednej Y lub null
	 */
	public Float getY() {
		return this.y;

	}

	/**
	 * 
	 * @return Wartosc Float wspolrzednej Z lub null
	 */
	public Float getZ() {
		return this.z;

	}

	public void setX(Float x) {
		this.x = roundToThree(x);

	}

	public void setY(Float y) {
		this.y = roundToThree(y);

	}

	public void setZ(Float z) {
		this.z = roundToThree(z);

	}

	public void addX(Float x) {
		this.x += roundToThree(x);

	}

	public void addY(Float y)

	{

		this.y += roundToThree(y);

	}

	public void addZ(Float z)

	{

		this.z += roundToThree(z);

	}
	
	public int cwiartka()
	{
		if(this.x>0)
		{
			if(this.y>=0)
				return 1;
			else 
				{
					return 4;
				}
			
		}
		else 
		{
			if(this.y>0)
				return 2;
			else if(this.x==0 && this.y<0)
			{
				return 4;
			}
			else return 3;
		}
		
		
	}
	
	
	@Override
	/**
	 * 
	 * @param p compared point
	 * @return true if both points have same cordinates X Y Z. if Points have different cordinates method returns false
	 */
	public boolean equals(Object p)
	{
		if(p==this) return true;
		
		if(!(p instanceof Point)) return false;
	
		Point point = (Point)p;	
		float tolerance =0.1f;
		float result =0f;
		
		if(this.x!=null) result +=this.x;
		if(point.x!=null) result -=point.x;
		if(this.y!=null) result +=this.y;
		if(point.y!=null) result -=point.y;
		if(this.z!=null) result +=this.z;
		if(point.z!=null) result -=point.z;
		
		return Math.abs(result) <tolerance;
	}
	
	
	 
	

	@Override
	public int hashCode() {
		int result = 31;
		
		if(this.x!=null) result += Float.hashCode(x);
		if(this.y!=null) result+= Float.hashCode(y);
		if(this.z!=null) result += Float.hashCode(z);		
		
		return result;
	}
	
	
	public static float roundToThree(float a) {
		return (Math.round(a * 1000) / 1000.f);
	}
	
	/**
	 * 
	 * @param previousPoint punkt poczatkowy
	 * @param destinationPoint punkt koncowy
	 * @param feed posuw 
	 * @return Zwraca czas podany w sekundach na pokonanie danego dystansu. Poniewaz wszystkie osie moga poruszac sie niezaleznie od siebie czas calkowity jest zalezny tylko od osi w ktorej mierzony dystans do pokonania jest najwiekszy.
	 * Zwraca 0 jesli polozenie w korejkolwiek z osi X Y Z  punktu previousPoint nie jest zdefiniowane
	 */
	public static float travelTime(Point previousPoint, Point destinationPoint, float feed)
	{
		
		previousPoint.replaceNullValues();
		destinationPoint.replaceNullValues();
		
		return (distanceXYZ(previousPoint, destinationPoint)/feed) * 60;
		
		

		
		
		
//		if(previousPoint.getX()!=null && previousPoint.getY()!=null && previousPoint.getZ() !=null && destinationPoint.getX()!=null && destinationPoint.getY()!=null && destinationPoint.getZ() != null)
//		{
//			result= (float)(Math.max(Math.max(Math.abs(destinationPoint.getX()-previousPoint.getX()), Math.abs(destinationPoint.getY()-previousPoint.getY())), Math.abs(destinationPoint.getZ()-previousPoint.getZ()))/feed);
//		}
//		// z== null
//		else if(previousPoint.getX()!=null && previousPoint.getY()!=null && previousPoint.getZ() !=null && destinationPoint.getX()!=null && destinationPoint.getY()!=null )
//		{
//			result=  (float)(Math.max(Math.abs(destinationPoint.getX()-previousPoint.getX()), Math.abs(destinationPoint.getY()-previousPoint.getY()))/feed);
//		}
//		// x==null
//		else if(previousPoint.getX()!=null && previousPoint.getY()!=null && previousPoint.getZ() !=null  && destinationPoint.getY()!=null && destinationPoint.getZ() != null)
//		{
//			result=  (float)(Math.max( Math.abs(destinationPoint.getY()-previousPoint.getY()), Math.abs(destinationPoint.getZ()-previousPoint.getZ()))/feed);
//		}
//		//y== null
//		else if(previousPoint.getX()!=null && previousPoint.getY()!=null && previousPoint.getZ() !=null && destinationPoint.getX()!=null && destinationPoint.getZ() != null )
//		{
//			result=  (float)(Math.max(Math.abs(destinationPoint.getX()-previousPoint.getX()),Math.abs(destinationPoint.getZ()-previousPoint.getZ()))/feed);
//		}
//		//x== null y==null
//		else if(destinationPoint.getX()==null && destinationPoint.getY()==null)
//			result=  (float) (Math.abs(destinationPoint.getZ()-previousPoint.getZ())/feed);
//		//x== null z==null
//		else if(destinationPoint.getX()==null && destinationPoint.getZ()==null)
//			result=  (float) (Math.abs(destinationPoint.getY()-previousPoint.getY())/feed);
//		//y==null z==null
//		else if(destinationPoint.getY()==null && destinationPoint.getZ()==null)
//			result=  (float)(Math.abs(destinationPoint.getX()-previousPoint.getX())/feed);
//		else return 0;
		
		//System.out.println("TARGET: " + destinationPoint + " ORIGIN: " + previousPoint + " == " + Math.abs(result)  + " FEED" + feed);
//		return result*60;
	}
	
	/**
	 * 
	 * @param p Punkt ktory jest uzyty do uaktualnienia polozenia
	 * @return Zwraca ten sam punkt ktorego polozenie jest uaktualnione o komende ruchu do polozenia punktu p. Dla przykladu dla punktu bazowego (50,50,50) i podanego punktu p(X = 75 ) metoda zwroci punkt (75,50,50)
	 */

	public Point updatePoint(Point p)
	{
		if(p.getX()!=null)
			this.setX(p.getX());
		if(p.getY()!=null)
			this.setY(p.getY());
		if(p.getZ()!=null)
			this.setZ(p.getZ());
		return this;
	}
	/**
	 * 
	 * @param previous - poprzedni punkt
	 * @param curent - punkt docelowy
	 * @return zwraca wartosc float odleglosci miedzy podanymi punktami w plaszczyznie XY lub 0 zero jesli ktorys z punktow nie jest poprawnie zdefiniowany
	 */
	public static float distanceXY(Point previous, Point curent)
	{
	
		if(previous.getX()!=null && previous.getY()!=null && curent.getX()!=null && curent.getY()!=null)
		{
			return (float) Math.sqrt(Math.pow(previous.getX()-curent.getX(), 2)+Math.pow(previous.getY()-curent.getY(), 2));
		
		}
		else return 0;

	}
	
	
	public Point updatePositionInLocalCS(Point localCSZero)
	{
		return new Point(localCSZero.getX()+this.getX(),localCSZero.getY()+this.getY());
		
	}
	
	public static float distanceXYZ(Point previous, Point current)
	{
		
		previous.replaceNullValues();
		current.replaceNullValues();
		
		
			return (float)  Math.sqrt(Math.pow(previous.getX()-current.getX(), 2)
					+ Math.pow(previous.getY()-current.getY(), 2)
					+ Math.pow(previous.getZ()-current.getZ(), 2));
		

	}
	
	/**
	 * 
	 * @param point 
	 * @param center center of circle or start of local cordinate system
	 * @return returns angle between X0 axis and given point. 0 angle is represented by X0 axis. Positive angle increases clockwise.
	 */
	
	public static float calcualteAngle(Point p, Point center)
	{
		if(p.equals(center))
			return 0;
		else 
		{
			// tempPoint representes given point 'p' in local cordintate system where center (x0, y0) is set at given point 'center' 
			Point tempPoint = new Point(p.getX()-center.getX(), p.getY()-center.getY());
			Wektor wektor = new Wektor(new Point(0f,0f), tempPoint);
			
			return wektor.katUkladuBiegunowego();
		}
	}
	
	public static Point averagePoint(Point first, Point second)
	{
		return new Point((first.getX()+second.getX())/2, (first.getY()+second.getY())/2);

	}
	
	
	/**
	 * Replaces null cordinates values with 0
	 */
	private void replaceNullValues()
	{
		if(this.getX()==null)
			this.x=0f;
		if(this.getY()==null)
			this.y=0f;
		if(this.getZ()==null)
			this.z=0f;

	}	
	
	
	@Override
	public Point clone()
	{
		Point result = new Point();
		
		if(this.getX()!=null)
			result.setX(this.getX());
		if(this.getY()!=null)
			result.setY(this.getY());
		if(this.getZ()!=null)
			result.setZ(this.getZ());
		
		
		return result;
	}
	
	
	

	@Override
	public String toString() {
	
	if(!xVar && !yVar && !zVar)
	{
		if (x == null && y == null && z != null)
			return "Z" + roundToThree(z);
		else if (x != null && y != null && z == null)
			return "X" + roundToThree(x) + " Y" + roundToThree(y);
		else if (x == null && y != null && z == null)
			return "Y" + roundToThree(y);
		else if (x != null && y == null && z == null)
			return "X" + roundToThree(x);
		else if (x == null && y == null && z == null)
			return "";
		else if (x != null && y == null && z != null)
			return "X" + roundToThree(x) + " Z" + roundToThree(z);
		else if (x == null && y != null && z != null)
			return "Y" + roundToThree(y) + " Z" + roundToThree(z);

		else if (x != null && y != null && z != null)
			return "X" + roundToThree(x) + " Y" + roundToThree(y) + " Z" + roundToThree(z);
		else
			return "error";
	}
	
	else
	{
		if (x == null && y == null && z != null)
			return  zVar?( "Z#" + z.intValue() + " "):( " Z" + roundToThree(z));
		else if (x != null && y != null && z == null)
			return xVar?( "X#" + x.intValue() + " "):( " X" + roundToThree(x) + " ") + (yVar?( " Y#" + y.intValue() + " "):( " Y" + roundToThree(y)));
		else if (x == null && y != null && z == null)
			return  yVar?( "Y#" + y.intValue() + " "):( " Y" + roundToThree(y));
		else if (x != null && y == null && z == null)
			return  xVar?( "X#" + x.intValue() + " "):( " X" + roundToThree(x));
		else if (x == null && y == null && z == null)
			return " ";
		else if (x != null && y == null && z != null)
			return xVar?( "X#" + x.intValue() + " "):( " X" + roundToThree(x) + " ") + (zVar?( " Z#" + z.intValue() + " "):( " Z" + roundToThree(z)));
		else if (x == null && y != null && z != null)
			return yVar?( "Y#" + y.intValue() + " "):( " Y" + roundToThree(y) + " ") + (zVar?( " Z#" + z.intValue() + " "):( " Z" + roundToThree(z)));

		else if (x != null && y != null && z != null)
			return (xVar?( "X#" + x.intValue() + " "):( " X" + roundToThree(x) + " ")) + (yVar?( " Y#" + y.intValue() + " "):( " Y" + roundToThree(y) + " ")) + (zVar?( " Z#" + z.intValue() + " "):( " Z" + roundToThree(z)));
		else
			return "error";
		
		
		
		
		
		
		
		
		
	}	
		
	}
}
