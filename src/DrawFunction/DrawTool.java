package DrawFunction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import CordConverter.Point;

public class DrawTool implements DrawableFunction {

	
	private Point center;
	private int scale;
	private Color color;
	
	public DrawTool(Point center, int diameter)
	{
		this.center=center;
		this.scale = diameter;
		this.color=Color.black;
	}
	
//	public DrawTool(Point center, int diameter, Dimension windowSize)
//	{
//		this(center,diameter);
//		this.center.setX(this.center.getX() +(float)(windowSize.getWidth()/2));
//		this.center.setY((float)(windowSize.getHeight()/2)-this.center.getY());
//	}
	
	
	public void setColor(Color color)
	{
		this.color=color;
	}
	
	
	@Override
	public void draw(Graphics g) {
		
		drawTool(g, center.getX().intValue(), center.getY().intValue());
	}

	@Override
	public void drawInCenter(Graphics g, Dimension windowSize) {
		
		drawTool(g, center.getX().intValue() + (int)windowSize.getWidth()/2, - center.getY().intValue() + (int)windowSize.getHeight()/2);
		
	}
	
	
	private void drawTool(Graphics g, int x, int y)
	{
		
		//background
				g.setColor(Color.gray);
				g.fillOval(x-scale/2, y-scale/2, scale, scale);
				g.setColor(color);
				
				//lines 
				
				//left
				 g.drawLine(x-scale/2,y,x-2,y);
				 g.drawLine(x-scale/2+1,y+3,x-2,y+3);
				g.drawLine(x-2,y+3,x-2,y);
				
				//up
				 g.drawLine(x, y-scale/2, x, y-2);
				 g.drawLine(x-3, y-2, x-3, y-scale/2+1);
				 g.drawLine(x, y-2, x-3, y-2);
				
				//right
				 g.drawLine(x+2, y, x+scale/2, y);
				g.drawLine(x+2, y-3, x+scale/2-1, y-3);
				g.drawLine(x+2, y, x+2, y-3);
				
				//down
				 g.drawLine(x, y+scale/2, x, y+2);
				g.drawLine(x+3, y+2, x, y+2);
				g.drawLine(x+3, y+scale/2-1, x+3, y+2);
				
				//circle
				g.drawOval(x-scale/2, y-scale/2, scale, scale);

		
	}
}
