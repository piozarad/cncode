package DrawFunction;

import java.awt.Dimension;
import java.awt.Graphics;

import CordConverter.Point;

public class Hole implements DrawableFunction {

	
	private int diameter = 10;
 	private Point point;
 
	
	public Hole(Point p)
	{
		this.point=p;

	}
	
	public Hole(Point p, int diameter)
	{
		this(p);
		this.diameter=diameter;
	}
	
	
	@Override
	public void draw(Graphics graphics) {
		graphics.drawArc((this.point.getX().intValue())-diameter/2, -this.point.getY().intValue()-diameter/2, diameter, diameter, 0, 360);
	}

	@Override
	public void drawInCenter(Graphics graphics, Dimension windowSize) {
		graphics.drawArc((this.point.getX().intValue())+(int)windowSize.getWidth()/2-diameter/2,
				(int)windowSize.getHeight()/2-this.point.getY().intValue()-diameter/2, diameter, diameter, 0, 360);
		
	}

}
