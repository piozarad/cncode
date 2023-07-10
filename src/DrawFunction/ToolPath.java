package DrawFunction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import CordConverter.Point;
import CordConverter.Wektor;

public class ToolPath implements DrawableFunction {

	
	private Point actualPoint;
	private Point nextPoint;
	private int gCode;
	private Float iVector;
	private Float jVector;
	private Dimension windowSize;
	private Point localCs;
	
	
	private ToolPath()
	{
		this.actualPoint = new Point(0f,0f);
		this.nextPoint = new Point(0f,0f);
	}

	
	public ToolPath(Dimension canvasArea)
	{
		this();
		this.windowSize=canvasArea;
		this.localCs=new Point(0f,0f);
		
	}
	public ToolPath(Dimension canvasArea, Point startPoint)
	{
		this.windowSize=canvasArea;
		this.actualPoint = startPoint;
		this.nextPoint = startPoint;
		this.localCs=new Point(0f,0f);
	}
	
	public ToolPath(int width, int height)
	{
		this();
		this.windowSize=new Dimension(width,height);
		this.localCs=new Point(0f,0f);
	}
	
	public ToolPath(int width, int height,Point startPoint)
	{
		this.windowSize=new Dimension(width,height);
		this.actualPoint = this.nextPoint;
		this.nextPoint = startPoint;
	}
	
	public void move(Point nextPoint, int gCode)
	{
		this.actualPoint = this.nextPoint.clone();
		this.nextPoint=new Point(nextPoint.getX(), nextPoint.getY());
		this.gCode=gCode;
	}
	
	public void move(float x, float y, int gCode)
	{
		this.actualPoint = this.nextPoint.clone();
		this.nextPoint= new Point(x,y);
		this.gCode=gCode;
	}
	
	
	public void move(Point nextPoint, int gCode ,Float iVector, Float jVector )
	{
		this.actualPoint = this.nextPoint.clone();
		this.nextPoint=nextPoint;
		this.iVector=iVector;
		this.jVector=jVector;
		this.gCode=gCode;
	}
	
	public void move(Point nextPoint, int gCode , Float radius )
	{
		//TODO 	ogarnaæ ¿eby to przelicza³o promieñ na wektory I J
		this.actualPoint = this.nextPoint.clone();
		this.nextPoint=nextPoint;
		this.iVector=iVector;
		this.jVector=jVector;
		this.gCode=gCode;
	}
	
	public void move(float x, float y, int gCode ,Float iVector, Float jVector )
	{
		this.actualPoint = this.nextPoint.clone();
		this.nextPoint = new Point(x,y);
		this.iVector=iVector;
		this.jVector=jVector;
		this.gCode=gCode;
	}
	
	/*
	 * Sets local cordinate system to gives point and calculates new Point cordinates
	 */
	public void setLocalCs(Point localCs)
	{
		//return to standard 0,0 system
		this.actualPoint = new Point(actualPoint.getX(),actualPoint.getY());
		this.nextPoint = new Point(nextPoint.getX(),actualPoint.getY() );
		
		this.localCs=localCs;
		this.actualPoint = new Point(actualPoint.getX()+localCs.getX(),actualPoint.getY());
		this.nextPoint = new Point(nextPoint.getX(),actualPoint.getY() );
	}
	
	
	/**
	 * Sets local cordinate system to center of this panel
	 */
	public void resetLocalCs()
	{
		this.localCs=new Point((float)windowSize.getWidth()/2,(float)windowSize.getHeight()/2);
	}
	
	
	
	private boolean isValid()
	{
		return ((gCode==1 || gCode==0) && iVector==null && jVector==null) || ((gCode==2 || gCode==3) && iVector!=null && jVector!=null);
	
	}

	
	@Override
	public void draw(Graphics g)
	{
		drawToolPath( g,  actualPoint,  nextPoint,  new Dimension(0,0));
		
	}
	
	@Override
	public void drawInCenter(Graphics g, Dimension windowSize) {
		drawToolPath( g,  actualPoint,  nextPoint,  this.windowSize);
		
	}
	
	private void drawToolPath(Graphics g, Point actualPoint, Point NextPoint, Dimension canvasArea)
	{
		if(isValid()) {
			
			switch(gCode)
			{
				case 0:
					g.setColor(Color.blue);
					break;
				case 1:
				case 2:
				case 3:
					g.setColor(Color.red);
					break;
				default:
				g.setColor(Color.black);
			}
			
			
			if(gCode==0 || gCode==1)
			{
				g.drawLine((int)(actualPoint.getX()+localCs.getX()+canvasArea.getWidth()/2), (int)(-localCs.getY()-actualPoint.getY()+canvasArea.getHeight()/2),
						(int)(nextPoint.getX()+localCs.getX()+canvasArea.getWidth()/2), (int)(-localCs.getY() -nextPoint.getY()+canvasArea.getHeight()/2));
				
			}
			else
			{
				Point actualPointinLocalCs = new Point((float)(actualPoint.getX()-localCs.getX()+canvasArea.getWidth()/2),(float)(localCs.getY()-actualPoint.getY()+canvasArea.getHeight()/2));
				Point nextPointinLocalCs = new Point((float)(nextPoint.getX()-localCs.getX()+canvasArea.getWidth()/2),(float)(localCs.getY()-nextPoint.getY()+canvasArea.getHeight()/2));
				
				 Point circleCenter = new Point( (float)canvasArea.getWidth()/2 + actualPoint.getX()+iVector,(float)canvasArea.getHeight()/2 -actualPoint.getY()+ jVector);
				 float radius = (float) Math.sqrt(Math.pow(iVector, 2) + Math.pow(jVector, 2));
				
				Wektor first = new Wektor(circleCenter,actualPointinLocalCs);
				Wektor second = new Wektor(circleCenter,nextPointinLocalCs);
				
				
				//System.out.println(circleCenter + "   "+ actualPointinLocalCs +"   "+ nextPointinLocalCs+ "  " + first+ " "+  second);
				
				if(gCode==3 )
					g.drawArc((int)(circleCenter.getX()-radius), (int)(circleCenter.getY() - radius), (int)(radius*2), (int)(radius*2),
							(int)(first.katUkladuBiegunowego()), (first.katSkierowanyMiedzyWektorami(2, second).intValue()));
				if(gCode==2)
					g.drawArc((int)(circleCenter.getX()-radius),(int)( circleCenter.getY() - radius), (int)(radius*2), (int)(radius*2), 
							(int)(first.katUkladuBiegunowego()),-(first.katSkierowanyMiedzyWektorami(3, second).intValue()));
			}
		}
	}
	
	
}
