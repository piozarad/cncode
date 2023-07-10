package PlaneMachining;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import BasicControls.Sterowanie;
import CordConverter.Edytor;
import CordConverter.Point;
import CordConverter.Wind;

public class PlaneModel {








		//variables
		private float x0;
		private float y0;
		private float d;
		private float h;
		private float z0;
		private float zf;
		private float toolDiameter;
		private int S;
		private int F;
		private float ap;
		private float materialThickness;
		private float aemm;
		private float radius;
		private float ae;
		private float distanceFromObstacle;
		private boolean pathWithRadius = false;
		private boolean isFinishingPassChoosed = false;
		private float[] zAxisAdvancePointsArray;
		
		
		private float maxYDirection =1000f;
		private float minYDirection = -1000f;
		private float maxXDirection = 1000f;
		private float minXDirection = -1000f;
		
		
		private boolean upperDirectionLocked;
		private boolean leftDirectionLocked;
		private boolean rightDirectionLocked;
		private boolean bottomDirectionLocked;
		private Point toolStartPoint;
		private List<Point> toolpath;
	


		private boolean isDirectionChoosed = false;
		

		private START_DIRECTIONS directionChoosed;
		private int numberOfRadialPasses;
		private boolean retracted=false;
	
		
		
		
		public PlaneModel()
		{
			this.toolpath = new LinkedList<>();
		}
		
		
		

	/**
	 * Command that calculates aemm and number of radial passes
	 */
	private void dividePasses()
	{
		float shorterSize= Math.min(d, h);
		this.aemm = (ae/100) *toolDiameter;

		this.numberOfRadialPasses = (int) Math.floor((shorterSize / (2*aemm)));
		
		//cover some rare cases
		if(numberOfRadialPasses==0 && 	this.aemm < shorterSize) numberOfRadialPasses=1;
	
	}
	
	boolean checkForObstacleAndToolColishionsYAxis()
	{
		if(upperDirectionLocked && bottomDirectionLocked)
		{
			return h + this.distanceFromObstacle *2 > toolDiameter; 
		}
		else return true;
	}
	
	boolean checkForObstacleAndToolColishionsXAxis()
	{
		if(leftDirectionLocked && rightDirectionLocked)
		{
			return d + this.distanceFromObstacle *2 > toolDiameter; 
		}
		else return true;
	}
	
	
	 void calculateStartingPoint(START_DIRECTIONS startingPointComboBoxIndex)
	{
			
			dividePasses();
			
			switch(startingPointComboBoxIndex)
			{
			case LEFT:
				this.toolStartPoint = new Point(-this.d/2-20-toolDiameter/2,
					 (upperDirectionLocked ? 0f  : h/2+toolDiameter/2-aemm));
				
				isDirectionChoosed = true;
				break;  
			case RIGHT:
				this.toolStartPoint = new Point(d/2+20+toolDiameter/2,
					  (bottomDirectionLocked ? 0f  : -h/2-toolDiameter/2+aemm));
			
				isDirectionChoosed = true;
				break;
			case UP:
				toolStartPoint = new Point(rightDirectionLocked ? 0f : d/2 +toolDiameter/2 -aemm,
					toolDiameter/2 + 20 + h/2);
			
				isDirectionChoosed = true;
				break;
			case DOWN:
				toolStartPoint = new Point(leftDirectionLocked ? 0f: - d/2 - toolDiameter/2 +aemm,
					- toolDiameter/2 - 20 - this.h/2);
			
				isDirectionChoosed = true;
				break;
			default :
				toolStartPoint = new Point(0f,0f);
				isDirectionChoosed = false;
				break;
			}
	}
	

	 List<Point> toolpathInLocalCS()
	{
		List<Point> result = new LinkedList<>();
		
		Point p;
		
		for(int i=0; i<this.toolpath.size();i++)
		{
			p = toolpath.get(i);
			p.addX(-this.x0);
			p.addY(-this.y0);
			result.add(p);
		}
		return result;
	}

	 void update()
	 {
		 
		 calculateAdvanceInZAxispoints();
		 calculateBoundaries();
	 }
	
	 void calculateAdvanceInZAxispoints()
	{
		int numberOfPasses = (int)(materialThickness /ap);
		if(isFinishingPassChoosed) numberOfPasses +=1;		
		float[] result = new float[numberOfPasses];
		
		int index=numberOfPasses-1;
		result[index--] = this.z0;
		
		if(this.isFinishingPassChoosed)
		{
			result[index--] = z0+zf; 
		}
		
		for( ;index>=0; index--)
		{
			result[index] = result[index+1] + ap;
		}
		this.zAxisAdvancePointsArray = result;
	}
	
	 void calculateBoundaries()
	{
		
		//x+
		if(this.rightDirectionLocked)
			this.maxXDirection = d/2 - this.toolDiameter/2 + this.distanceFromObstacle;
		else this.maxXDirection = toolDiameter/2 + d/2-aemm;
		
		//x-
		if(this.leftDirectionLocked)
			this.minXDirection = -d/2 + this.toolDiameter/2 - this.distanceFromObstacle ;
		else this.minXDirection = -d/2 - toolDiameter/2 + aemm;
		
		//y+
		if(this.upperDirectionLocked)
			this.maxYDirection = h/2 - this.toolDiameter/2 + this.distanceFromObstacle ;
		else this.maxYDirection = h/2 + toolDiameter/2 - aemm;
		
		//y-
		if(this.bottomDirectionLocked)
			this.minYDirection = -h/2 + this.toolDiameter/2 - this.distanceFromObstacle ;
		else this.minYDirection = -h/2 - toolDiameter/2 + aemm;
		
	}
	


	
	
	/**
	 * 
	 * @param actualPoint actual point in toolpath
	 * @param x cordinate of next point
	 * @param y cordinate of next point
	 */
	private void updateAndClone(Point actualPoint,float x, float y)
	{

		if(!(Math.abs(actualPoint.getX() - x)<0.01 && Math.abs(actualPoint.getY() - y)<0.01))
		{
			actualPoint.updatePoint(new Point(x ,y)); 	
			this.toolpath.add(actualPoint.clone());
		}	
	}
	
	
	private void createSimpleOnePassFromUp(Point actualPoint)
	{
		updateAndClone(actualPoint,maxXDirection,Math.min(minYDirection, maxYDirection));// v
		if(this.aemm>d)
		{
				if(!this.bottomDirectionLocked)
				{
					//retraction right else retraction in Z axis
					updateAndClone(actualPoint,actualPoint.getX(),-h/2-5-toolDiameter/2);
				}	
		}
				// when aemm>h and h>>d
	 	else 
	 	{
	 			validateBounds();
	 			updateAndClone(actualPoint,minXDirection,actualPoint.getY());// <
	 			updateAndClone(actualPoint,actualPoint.getX(),maxYDirection);// ^
	 			updateAndClone(actualPoint,actualPoint.getX(),toolStartPoint.getY());// ^^
	 	}
		this.retracted = true;
	}
	
	private void createSimpleOnePassFromDown(Point actualPoint)
	{
		updateAndClone(actualPoint,minXDirection,Math.max(minYDirection, maxYDirection));// ^
		if(this.aemm>d)
		{
				if(!this.upperDirectionLocked)
				{
					//retraction right else retraction in Z axis
					updateAndClone(actualPoint,actualPoint.getX(),h/2+5+toolDiameter/2);
				}	
		}
				// when aemm>h and h>>d
	 	else 
	 	{
	 			validateBounds();
	 			updateAndClone(actualPoint,maxXDirection,actualPoint.getY());// >
	 			updateAndClone(actualPoint,actualPoint.getX(),minYDirection);// v
	 			updateAndClone(actualPoint,actualPoint.getX(),toolStartPoint.getY());// vv
	 	}
		this.retracted = true;
	}
	
	private void createSimpleOnePassFromLeft(Point actualPoint)
	{
		updateAndClone(actualPoint,Math.max(maxXDirection, minXDirection),maxYDirection);// >
		if(this.aemm>h)
		{
				if(!this.rightDirectionLocked)
				{
					//retraction right else retraction in Z axis
					updateAndClone(actualPoint,actualPoint.getX()+5+toolDiameter/2+aemm,maxYDirection);
				}	
		}
				// when aemm>h and h>>d
	 	else 
	 	{
	 			validateBounds();
	 			updateAndClone(actualPoint,actualPoint.getX(),minYDirection);// v
	 			updateAndClone(actualPoint,minXDirection,actualPoint.getY());// <
	 			updateAndClone(actualPoint,toolStartPoint.getX(),actualPoint.getY());// <
	 	}
		this.retracted = true;
	}
	
	
	private void createSimpleOnePassFromRight(Point actualPoint)
	{
		updateAndClone(actualPoint,Math.min(maxXDirection, minXDirection),actualPoint.getY());// <
		if(this.aemm>h)
		{
				if(!this.leftDirectionLocked)
				{
				
					//retraction left else retraction in Z axis
					updateAndClone(actualPoint,-d/2-5-toolDiameter/2,actualPoint.getY());
					
				}	
		}
				// when aemm>h and h>>d
	 	else 
	 	{
	 		validateBounds();
	 		updateAndClone(actualPoint,actualPoint.getX(),maxYDirection);// ^
	 		updateAndClone(actualPoint,maxXDirection,actualPoint.getY());// >
	 		updateAndClone(actualPoint,toolStartPoint.getX(),actualPoint.getY());// >
	 	}
		this.retracted = true;
	}
	
	private void retractHorizontal(Point actualPoint)
	{
		if(directionChoosed == START_DIRECTIONS.LEFT)
		{
		//retraction right
		if(!rightDirectionLocked) 
			{
				updateAndClone(actualPoint,d/2+toolDiameter/2 + 4,actualPoint.getY());
			}
		else
			{
			//retraction left
				updateAndClone(actualPoint,toolStartPoint.getX(),actualPoint.getY());
				
			}
		this.retracted = true;
		}
		else
		{
			//retraction left
			if(!leftDirectionLocked) 
				{
					updateAndClone(actualPoint,-d/2-toolDiameter/2 - 4,actualPoint.getY());
				}
			else
				{
				//retraction right
					updateAndClone(actualPoint,toolStartPoint.getX(),actualPoint.getY());		
				}
		}
	}
	
	private void retractVertical(Point actualPoint)
	{
		if(directionChoosed == START_DIRECTIONS.UP)
		{
		//retraction down
		if(!bottomDirectionLocked) 
			{	
				updateAndClone(actualPoint,actualPoint.getX(),-h/2-toolDiameter/2 - 4);
				System.out.println("pionowo 1: x" +actualPoint.getX() +" y"+ (-h/2-toolDiameter/2 - 4) );
			}
		else
			{
			//retraction up
				
			updateAndClone(actualPoint,actualPoint.getX(),toolStartPoint.getY());
			System.out.println("pionowo 2: x" +actualPoint.getX() +" y"+toolStartPoint.getY() );
			}
		this.retracted = true;
		}
		else
		{
			//start direction down
			if(!upperDirectionLocked) 
				{
					updateAndClone(actualPoint,actualPoint.getX(),h/2+toolDiameter/2 + 4);
					System.out.println("pionowo 3: x" +actualPoint.getX() +" y"+ (h/2+toolDiameter/2 + 4) );
				}
			else
				{
				//retraction down
					updateAndClone(actualPoint,actualPoint.getX(),toolStartPoint.getY());	
					System.out.println("pionowo 4: x" +actualPoint.getX() +" y"+ toolStartPoint.getY() );	
				}
		}
	}
	
	
	private void createFullPassFromLeft(Point actualPoint)
	{
		updateAndClone(actualPoint,Math.max(maxXDirection, minXDirection),maxYDirection);// >
		if(maxYDirection<=minYDirection )
		{
			retractHorizontal(actualPoint);
		}
		else 
		{
			updateAndClone(actualPoint,actualPoint.getX(),minYDirection);	// v
			updateAndClone(actualPoint,minXDirection,actualPoint.getY());	// <
			if(maxYDirection - aemm <=minYDirection && d>=h)
			{
				//wyjazd w lewo
				updateAndClone(actualPoint,toolStartPoint.getX(),actualPoint.getY());
			
				this.retracted = true;
			}
			else if(maxXDirection - aemm <= minXDirection&& h>=d)
			{
				//wyjazd w lewo
				updateAndClone(actualPoint,toolStartPoint.getX(),actualPoint.getY());
				numberOfRadialPasses--;
				this.retracted = true;
			}
			else
			{
				updateAndClone(actualPoint,actualPoint.getX(),maxYDirection-aemm);	// ^ - ae
				retracted = false;
			}
		}
	}
	
	private void createFullPassFromRight(Point actualPoint)
	{
		updateAndClone(actualPoint,Math.min(minXDirection,maxXDirection),minYDirection);// <
		if(maxYDirection<=minYDirection )
		{
			retractHorizontal(actualPoint);
		}
		else 
		{
			updateAndClone(actualPoint,actualPoint.getX(),maxYDirection);	// ^
			updateAndClone(actualPoint,maxXDirection,actualPoint.getY());	// >
			if(maxYDirection - aemm <=minYDirection && d>=h)
			{
				//wyjazd w prawo
				updateAndClone(actualPoint,toolStartPoint.getX(),actualPoint.getY());
			
				this.retracted = true;
			}
			else if(maxXDirection - aemm <= minXDirection&& h>=d)
			{
				//wyjazd w lewo
				updateAndClone(actualPoint,toolStartPoint.getX(),actualPoint.getY());
				numberOfRadialPasses--;
				this.retracted = true;
			}
			else
			{
				updateAndClone(actualPoint,actualPoint.getX(),minYDirection+aemm);	// v - ae
				retracted = false;
			}
		}
	}
	

	
	private void createFullPassFromUp(Point actualPoint)
	{
		updateAndClone(actualPoint,maxXDirection,Math.min(minYDirection,maxYDirection));// v
		if(maxXDirection<=minXDirection )
		{
			retractVertical(actualPoint);
		}
		else 
		{
			updateAndClone(actualPoint,minXDirection,actualPoint.getY());	// <
			updateAndClone(actualPoint,actualPoint.getX(),maxYDirection);	// ^
			if(maxXDirection - aemm <=minXDirection && h>=d)
			{
				//
				updateAndClone(actualPoint,actualPoint.getX(),toolStartPoint.getY());
		
				this.retracted = true;
			}
			else if(maxYDirection - aemm <= minYDirection&& d>=h)
			{
				//retract up
				updateAndClone(actualPoint,actualPoint.getX(),toolStartPoint.getY());
				numberOfRadialPasses--;
				this.retracted = true;
			}
			else
			{
				updateAndClone(actualPoint,maxXDirection - aemm,actualPoint.getY());	// > - ae
				retracted = false;
			}
		}
	}
	
	private void createFullPassFromDown(Point actualPoint)
	{
		updateAndClone(actualPoint,minXDirection,Math.max(minYDirection,maxYDirection));// ^
		if(maxXDirection<=minXDirection )
		{
			retractVertical(actualPoint);
		}
		else 
		{
			updateAndClone(actualPoint,maxXDirection,actualPoint.getY());	// >
			updateAndClone(actualPoint,actualPoint.getX(),minYDirection);	// v
			if(maxXDirection - aemm <=minXDirection && h>=d)
			{
				//
				updateAndClone(actualPoint,actualPoint.getX(),toolStartPoint.getY());
		
				this.retracted = true;
			}
			else if(maxYDirection - aemm <= minYDirection&& d>=h)
			{
				//retract up
				updateAndClone(actualPoint,actualPoint.getX(),toolStartPoint.getY());
				numberOfRadialPasses--;
				this.retracted = true;
			}
			else
			{
				updateAndClone(actualPoint,minXDirection + aemm ,actualPoint.getY());	// < - ae
				retracted = false;
			}
		}
	}
	

	/**
	 *  adds points to variable toolPath
	 */
	 void toolPathfromLeftStraightLines()
	{
		dividePasses();
		toolpath.add(toolStartPoint);
		Point actualPoint = toolStartPoint.clone();
		
		for(int i=0; i<=numberOfRadialPasses; i++)
		{
			updateAndClone(actualPoint,minXDirection,maxYDirection); // > / ^
			
			if(numberOfRadialPasses==0 || h<=2*aemm)
			{
				createSimpleOnePassFromLeft(actualPoint);
				break;
			}
			else {
				createFullPassFromLeft(actualPoint);	
			}
			
			incrementMachiningBoxSize();	
			
		}
		if(!retracted) retractHorizontal(actualPoint);
		//generateGCodeToolPathWithoutRadius(calculateAdvanceInZAxispoints(isFinishingPassChoosed));
		
	}
	
	 
	 private void validateBounds()
	 {
		 maxXDirection = Math.max(maxXDirection, minXDirection);
		 maxYDirection = Math.max(minYDirection, maxYDirection);
	 }
	 
	 
	 private void incrementMachiningBoxSize()
	 {
		 	minXDirection+=aemm;
			maxXDirection-=aemm;
			minYDirection+=aemm;
			maxYDirection-=aemm;
	 }
	 
	 void toolPathfromLeftWithRadius()
	{
		dividePasses();
		toolpath.add(toolStartPoint);
		Point actualPoint = toolStartPoint.clone();
		float r = this.radius;
		
		//first point in material
		actualPoint.updatePoint(new Point(minXDirection ,maxYDirection)); 
		
		System.out.println("r=" + r);
		for(int i=0; i<=numberOfRadialPasses; i++)
		{
			actualPoint.updatePoint(new Point(maxXDirection-r ,maxYDirection));	// >
			this.toolpath.add(actualPoint.clone());
			actualPoint.updatePoint(new Point(maxXDirection ,maxYDirection-r));	// /
			this.toolpath.add(actualPoint.clone());
			actualPoint.updatePoint(new Point(maxXDirection ,minYDirection+r)); 	// v
			this.toolpath.add(actualPoint.clone());
			actualPoint.updatePoint(new Point(maxXDirection -r ,minYDirection)); 	// /
			this.toolpath.add(actualPoint.clone());
			actualPoint.updatePoint(new Point(minXDirection +r,minYDirection)); 	// <
			this.toolpath.add(actualPoint.clone());
			
			if(numberOfRadialPasses==0)
			{
				//prosty wyjazd prz pojedynczym przejsciu
				actualPoint.updatePoint(new Point(toolStartPoint.getX(), actualPoint.getY() )); 	// < wyjazd
				this.toolpath.add(actualPoint.clone());		
			}
			else
			{
				//jesli nie jest to ostatni przejazd podjedz na pozycje do nast petli
				if(numberOfRadialPasses!=i)
				{
					actualPoint.updatePoint(new Point(minXDirection ,minYDirection+r)); 	// \
					this.toolpath.add(actualPoint.clone());
					actualPoint.updatePoint(new Point(minXDirection ,maxYDirection-aemm-r)); 	// ^ - ae
					this.toolpath.add(actualPoint.clone());
				}
					// jestli to ostatni - wyjazdy po prostej w lewo
				else
				{
					actualPoint.updatePoint(new Point(toolStartPoint.getX(),actualPoint.getY())); 	//wyjscie w lewo
					this.toolpath.add(actualPoint.clone());
				}
					
			minXDirection+=aemm;
			maxXDirection-=aemm;
			minYDirection+=aemm;
			maxYDirection-=aemm;	
			}
		}
	}
	
	void  toolPathfromRightStraightLines()
	 {
		dividePasses();
		toolpath.add(toolStartPoint);
		Point actualPoint = toolStartPoint.clone();
		
		for(int i=0; i<=numberOfRadialPasses; i++)
		{
			updateAndClone(actualPoint,maxXDirection,minYDirection); // < / v
			
			if(numberOfRadialPasses==0 || h<=2*aemm)
			{
				createSimpleOnePassFromRight(actualPoint);
				break;
			}
			else {
				createFullPassFromRight(actualPoint);	
			}
		
			incrementMachiningBoxSize();
			
		}
		if(!retracted) retractHorizontal(actualPoint);
		//generateGCodeToolPathWithoutRadius(calculateAdvanceInZAxispoints(isFinishingPassChoosed));
	 }
	
	 
	 
	void toolPathfromUpStraightLines()
	{
		dividePasses();
		toolpath.add(toolStartPoint);
		Point actualPoint = toolStartPoint.clone();
		
		for(int i=0; i<=numberOfRadialPasses; i++)
		{
			updateAndClone(actualPoint,maxXDirection,maxYDirection); // > / v
			
			if(numberOfRadialPasses==0 || h<=2*aemm)
			{
				createSimpleOnePassFromUp(actualPoint);
				break;
			}
			else {
				createFullPassFromUp(actualPoint);	
			}
		
			incrementMachiningBoxSize();
		}
		if(!retracted) retractVertical(actualPoint);
		//generateGCodeToolPathWithoutRadius(calculateAdvanceInZAxispoints(isFinishingPassChoosed));
	 
		
	}
	
	 
	
	
	void toolPathfromDownStraightLines()
	{
		dividePasses();
		toolpath.add(toolStartPoint);
		Point actualPoint = toolStartPoint.clone();
		
		for(int i=0; i<=numberOfRadialPasses; i++)
		{
			updateAndClone(actualPoint, minXDirection, minYDirection); // < / ^
			
			if(numberOfRadialPasses==0 || h<=2*aemm)
			{
				createSimpleOnePassFromDown(actualPoint);
				break;
			}
			else 
			{
				createFullPassFromDown(actualPoint);	
			}
		
			incrementMachiningBoxSize();
			
		}
		if(!retracted) retractVertical(actualPoint);
		//generateGCodeToolPathWithoutRadius(calculateAdvanceInZAxispoints(isFinishingPassChoosed));
	}
	 
	 
	//getters
	 
	 
	 Point getToolStartPoint()
	{
		return  Optional.ofNullable(this.toolStartPoint).orElse(new Point(0f,0f));
	}
	
	 float getAe() {
		return ae;
	}
	
	 float getRadius()
	{
		return this.radius;
	}
	
	 float getd()
	{
		return this.d;
		
	}
	
	 int getFeed()
	{
		return this.F;
	}
	
	float[] getAdvanceInZAxisArray()
	{
		return this.zAxisAdvancePointsArray;
	}
	 
	 int getSprindleSpeed()
	{
		return this.S;
	}
	 float geth()
	{
		return this.h;
	}
	
	 float getX0() {
		return x0;
	}

	 boolean isDirectionChoosed()
	{
		return this.isDirectionChoosed;
	}
	

	 boolean isPathWithRadius( ) {
		return this.pathWithRadius;
	}

	 float getY0() {
			return y0;
		
		}
	 
	  float getZ0() {
			return z0;
		}
	 
	 
	  float getZf() {
			return zf;
		}
	 
	 float getToolDiameter()
	 {
		 return this.toolDiameter;
	 }

	 List<Point> getToolpath() {
		return toolpath;
	}
	 
	public float getMaterialThickness()
	{
		return this.materialThickness;
	}
	
	//setters

	 void setX0(float x0) {
		this.x0 = x0;
	}

	 



	 void setFinishingPass(boolean finishigPass)
	{
		this.isFinishingPassChoosed=finishigPass;
	}
	



	 void setY0(float y0) {
		this.y0 = y0;
	}







	public void setZ0(float z0) {
		this.z0 = z0;
	}




	 void setZf(float zf) {
		this.zf = zf;
	}




	 void setD(float d) {
		this.d = d;
	}






	public void setH(float h) {
		this.h = h;
	}







	public void setToolDiameter(float toolDiameter) {
		this.toolDiameter = toolDiameter;
	}






	public void setS(int s) {
		S = s;
	}






	public void setF(int f) {
		F = f;
	}






	public void setAp(float ap) {
		this.ap = ap;
	}






	public void setMaterialThickness(float materialThickness) {
		this.materialThickness = materialThickness;
	}






	public void setAemm(float aemm) {
		this.aemm = aemm;
	}






	public void setRadius(float radius) {
		this.radius = radius;
	}






	public void setAe(float ae) {
		this.ae = ae;
	}



	public START_DIRECTIONS getDirectionChoosed()
	{
		return this.directionChoosed;
	}



	public void setDistanceFromObstacle(float distanceFromObstacle) {
		this.distanceFromObstacle = distanceFromObstacle;
	}






	public void setPathWithRadius(boolean pathWithRadius) {
		this.pathWithRadius = pathWithRadius;
	}





	public void setMaxYDirection(float maxYDirection) {
		this.maxYDirection = maxYDirection;
	}






	public void setMinYDirection(float minYDirection) {
		this.minYDirection = minYDirection;
	}






	public void setMaxXDirection(float maxXDirection) {
		this.maxXDirection = maxXDirection;
	}






	public void setMinXDirection(float minXDirection) {
		this.minXDirection = minXDirection;
	}






	public void setUpperDirectionLocked(boolean upperDirectionLocked) {
		this.upperDirectionLocked = upperDirectionLocked;
	}






	public void setLeftDirectionLocked(boolean leftDirectionLocked) {
		this.leftDirectionLocked = leftDirectionLocked;
	}






	public void setRightDirectionLocked(boolean rightDirectionLocked) {
		this.rightDirectionLocked = rightDirectionLocked;
	}






	public void setBottomDirectionLocked(boolean bottomDirectionLocked) {
		this.bottomDirectionLocked = bottomDirectionLocked;
	}






	public void setToolStartPoint(Point toolStartPoint) {
		this.toolStartPoint = toolStartPoint;
	}






	public void setDirectionChoosed(boolean isDirectionChoosed) {
		this.isDirectionChoosed = isDirectionChoosed;
	}





	public void setNumberOfRadialPasses(int numberOfRadialPasses) {
		this.numberOfRadialPasses = numberOfRadialPasses;
	}



	public void setRetracted(boolean retracted) {
		this.retracted = retracted;
	}



	public void setDirectionChoosed(START_DIRECTIONS direction)
	{
		this.directionChoosed = direction;
	}


	public void setToolpath(List<Point> toolpath) {
		this.toolpath = toolpath;
	}
	
	
	@Override public String toString()
	{
		return "LCS: X0=" + this.x0 +" Y0=" + this.y0 + "\nWykorzystane narzêdzie o D=" + String.format(Locale.CANADA,"%.2f", this.toolDiameter)
		+ "\nWymiary p³yty: " + d +"x"+ h;
	}
	

}
