package DrawFunction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import CordConverter.Point;
import CordConverter.TYPE;

public class VerticalDimension implements DrawableFunction {


	private int length=0;
	private Point leftPoint;
	private Point rightPoint;
	private Color color;
	
	
	
	public VerticalDimension(Point left, Point right)
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
		drawVerticalDimension( g,  leftPoint,  rightPoint);
		
	}

	@Override
	public void drawInCenter(Graphics g, Dimension windowSize) {
		
		Point left = new Point(leftPoint.getX() + (float)windowSize.getWidth()/2, leftPoint.getY() + (float)windowSize.getHeight()/2,TYPE.XY_POINT);
		Point right = new Point(rightPoint.getX() + (float) windowSize.getWidth()/2, rightPoint.getY() + (float)windowSize.getHeight()/2,TYPE.XY_POINT);
		
		drawVerticalDimension( g,  left,  right);
		
	}
	
	private void drawVerticalDimension(Graphics g, Point leftPoint, Point rightPoint)
	{
		
		//color
				g.setColor(color);
				
				//main line
				g.drawLine(leftPoint.getX().intValue(),
						leftPoint.getY().intValue(), 
						rightPoint.getX().intValue(),
						rightPoint.getY().intValue());
				
				//lenght
				g.drawString(length +" mm",
						this.leftPoint.getX().intValue() + 5,(leftPoint.getY().intValue() + rightPoint.getY().intValue())/2);
				
				
				//upper arrow left
				g.drawLine(leftPoint.getX().intValue(),
							leftPoint.getY().intValue(),
							leftPoint.getX().intValue()-5,
							leftPoint.getY().intValue()+5);
				
				//upper arrow right
				g.drawLine(leftPoint.getX().intValue(),
						leftPoint.getY().intValue(),
						leftPoint.getX().intValue()+5,
						leftPoint.getY().intValue()+5);
				
				//down arrow right
				g.drawLine(rightPoint.getX().intValue(),
						rightPoint.getY().intValue(),
						rightPoint.getX().intValue()+5,
						rightPoint.getY().intValue()-5);
				//down arrow left
						g.drawLine(rightPoint.getX().intValue(),
								rightPoint.getY().intValue(),
								rightPoint.getX().intValue()-5,
								rightPoint.getY().intValue()-5);
		
	}

}
