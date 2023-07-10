package DrawFunction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import CordConverter.Point;

public class DrawCordinateSystem implements DrawableFunction {

	
	private int x;
	private int y;
	private float scale =1.0f;
	private Color color = Color.black;
	
	
	
	
	public DrawCordinateSystem(Point p)
	{
		this.x = p.getX().intValue();
		this.y=p.getY().intValue();
		
	}
	public DrawCordinateSystem(Point p, float scale)
	{
		this(p);
		this.scale=scale;
	}
	public DrawCordinateSystem(Point p, float scale ,Color color)
	{
		this(p,scale);
		this.color=color;
	}
	public DrawCordinateSystem(Point p ,Color color)
	{
		this(p);
		this.color=color;
	}
	
	@Override
	public void draw(Graphics graphics) {
		//uklad wspolrzednych
		//os y
		drawUpperLine(graphics,x,y);

		//os x
		drawHorizontalLine(graphics,x,y);

		//podpis
		drawString( graphics, x, y);
		
	}
	@Override
	public void drawInCenter(Graphics graphics, Dimension windowSize) {
		int x =(int) windowSize.getWidth()/2 +this.x;
		int y= (int) windowSize.getHeight()/2 + this.y;
		
		//uklad wspolrzednych
				//os y
		drawUpperLine(graphics,x,y);

		//os x
		drawHorizontalLine(graphics,x,y);

		//podpis
		drawString( graphics, x, y);
		
		
	}
	
	private void drawUpperLine(Graphics graphics, int x, int y)
	{
		graphics.setColor(color);
		graphics.drawLine(x, y, x, y-(int)(50*scale));  
		graphics.drawLine(x, y-(int)(50*scale), x+(int)(3*scale), y-(int)(45*scale));
		graphics.drawLine(x, y-(int)(50*scale),x -(int)(3*scale), y-(int)(45*scale));
		graphics.drawString("y", x-(int)(9*scale), y-(int)(52*scale));
	}
	
	private void drawHorizontalLine(Graphics graphics, int x, int y)
	{
		graphics.drawLine(x,y, x+(int)(50*scale), y);
		graphics.drawLine(x+(int)(50*scale), y, x+(int)(45*scale), y- (int)(5*scale));
		graphics.drawLine(x+(int)(50*scale),y, x+(int)(45*scale), y+ (int)(5*scale));
		graphics.drawString("x", x+(int)(52*scale), y+(int)(12*scale));
	}
	
	private void drawString(Graphics graphics, int x, int y)
	{
		graphics.drawString("x", x+(int)(52*scale), y+(int)(12*scale));
	}

}
