package DrawFunction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EmptyRectangle implements DrawableFunction {

	private int xCenter;
	private int yCenter;
	private int xLength;
	private int yLength;
	
	private int thickness;
	
	
	private EmptyRectangle(EmptyRectangleBuilder builder)
	{
		this.xCenter=builder.xCenter;
		this.yCenter=builder.yCenter;
		this.xLength=builder.xLength;
		this.yLength=builder.yLength;
		this.thickness=builder.thickness;

	}
	
	
	//builder
	public static class EmptyRectangleBuilder
	{
		private int xCenter;
		private int yCenter;
		private int xLength;
		private int yLength;
		
		private int thickness;
	
	
	
	public EmptyRectangleBuilder xCenter(int x)
	{
		this.xCenter=x;
		return this;
	}
	
	public EmptyRectangleBuilder yCenter(int y)
	{
		this.yCenter=y;
		return this;
	}
	
	public EmptyRectangleBuilder xLength(int lengthX)
	{
		this.xLength=lengthX;
		return this;
	}
	
	public EmptyRectangleBuilder yLength(int lengthY)
	{
		this.yLength=lengthY;
		return this;
	}
	
	public EmptyRectangleBuilder thickness(int t)
	{
		this.thickness=t;
		return this;
	}
	
	public EmptyRectangle build()
	{
		return new EmptyRectangle(this);
	}
	
	}
	
	
	@Override
	public void draw(Graphics g) {
		drawRectangle(g , xCenter,  yCenter);
	}


	@Override
	public void drawInCenter(Graphics g, Dimension windowSize) {
		drawRectangle(g , xCenter+ (int)windowSize.getWidth()/2,  yCenter+ (int)windowSize.getHeight()/2);
		
	}
	
	private void drawRectangle(Graphics g ,int x, int y)
	{
		g.setColor(Color.black);
		g.fillRect(x-EmptyRectangle.this.xLength/2 -EmptyRectangle.this.thickness/2, 
				y-EmptyRectangle.this.yLength/2 -EmptyRectangle.this.thickness/2,
				EmptyRectangle.this.xLength+EmptyRectangle.this.thickness,
				EmptyRectangle.this.yLength+EmptyRectangle.this.thickness);

		g.setColor(Color.white);
		g.fillRect(x-EmptyRectangle.this.xLength/2 , 
				y-EmptyRectangle.this.yLength/2 ,
				EmptyRectangle.this.xLength,
				EmptyRectangle.this.yLength);
	}

}
