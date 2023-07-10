package DrawFunction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import CordConverter.Point;

public class HorizontalDimension implements DrawableFunction {

	private int length=0;
	private Point leftPoint;
	private Point rightPoint;
	private Color color;
	
	
	
	public HorizontalDimension(Point left, Point right)
	{
		this.color=Color.black;
		this.leftPoint=left;
		this.rightPoint=right;
		this.length = (int)Point.distanceXY(left, right);
		
	}
	
	public void setColor(Color c)
	{
		this.color=c;
	}

	
	@Override
	public void draw(Graphics g) {
		drawHorizontaldimension( g,  this.leftPoint,  this.rightPoint);
		
	}

	@Override
	public void drawInCenter(Graphics g, Dimension windowSize) {
		Point left = new Point(this.leftPoint.getX() + (float)windowSize.getWidth()/2f , this.leftPoint.getY() + (float)windowSize.getHeight()/2);
		Point right = new Point(this.rightPoint.getX() + (float)windowSize.getWidth()/2f , this.rightPoint.getY() + (float)windowSize.getHeight()/2);
		
		drawHorizontaldimension( g, left, right);
		
	}
	
	private void drawHorizontaldimension(Graphics g, Point leftPoint, Point rightPoint)
	{
		//set Color
				g.setColor(color);
				
				//main line
				g.drawLine(leftPoint.getX().intValue(),
						leftPoint.getY().intValue(), 
						rightPoint.getX().intValue(),
						rightPoint.getY().intValue());
				
				//lenght
				g.drawString(length +" mm",
						(leftPoint.getX().intValue() + rightPoint.getX().intValue())/2 -5,
						this.leftPoint.getY().intValue() - 5);
				
				
				//left arrow up
				g.drawLine(leftPoint.getX().intValue(),
							leftPoint.getY().intValue(),
							leftPoint.getX().intValue()+5,
							leftPoint.getY().intValue()-5);
				
				//left arrow down
				g.drawLine(leftPoint.getX().intValue(),
						leftPoint.getY().intValue(),
						leftPoint.getX().intValue()+5,
						leftPoint.getY().intValue()+5);
				
				//right arrow up
				g.drawLine(rightPoint.getX().intValue(),
						rightPoint.getY().intValue(),
						rightPoint.getX().intValue()-5,
						rightPoint.getY().intValue()-5);
				//right arrow down
						g.drawLine(rightPoint.getX().intValue(),
								rightPoint.getY().intValue(),
								rightPoint.getX().intValue()-5,
								rightPoint.getY().intValue()+5);
	}
	

}
